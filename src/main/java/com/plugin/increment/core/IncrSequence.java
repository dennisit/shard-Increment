/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment.core;

import com.plugin.increment.context.SQLBuilder;
import com.plugin.increment.database.DBUtil;
import com.plugin.increment.object.IncrTableConfig;
import com.plugin.increment.object.IncrementStep;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author pudongping
 * @version 1.0.1
 */
public class IncrSequence{

    private static final Logger LOG = Logger.getLogger(IncrSequence.class);

    // incrementId start value
    private long incBeginId = 0L;

    // step width , default value is five
    private int stepWidth = 5;

    // DataSource
    private DataSource dataSource;

    // init increment table config
    private IncrTableConfig incrTableConfig;

    // inner small data map , simple cache
    private Map<String,IncrementStep> sequenceIncreStepCache = new ConcurrentHashMap<String, IncrementStep>();

    /**
     * construct with default incrTableConfig
     * @param incBeginId
     * @param stepWidth
     * @param dataSource
     */
    public IncrSequence(long incBeginId, int stepWidth, DataSource dataSource) {
        this(incBeginId, stepWidth,dataSource,new IncrTableConfig());
    }

    /**
     * construct with all param user setting
     * @param incBeginId
     * @param stepWidth
     * @param dataSource
     * @param incrTableConfig
     */
    public IncrSequence(long incBeginId, int stepWidth, DataSource dataSource, IncrTableConfig incrTableConfig) {
        this.incBeginId = incBeginId;
        this.stepWidth = stepWidth;
        this.dataSource = dataSource;
        this.incrTableConfig = incrTableConfig;
    }

    /**
     * get incrementId by sequenceName or businessName
     * @return
     */
    public synchronized long getIncrement(String businessName) {
        IncrementStep incrementStep = getIncrementStep(businessName);
        // get increment Id from cache [currentIncrementId, currentIncrementId + stepWidth]
        if (incrementStep.getBeginValue() < incrementStep.getEndValue()) {
            return incrementStep.incrementAndGet();
        }
        // update [currentIncrementId, currentIncrementId + stepWidth] to db, and return (++incrementId)
        for (int i = 0; i < this.stepWidth; i++) {
            boolean updateBlockedIncrToDB = getNextBlock(businessName);
            if (updateBlockedIncrToDB == true) {
                return incrementStep.incrementAndGet();
            }
        }
        throw new RuntimeException("No more value.");
    }

    /**
     * get IncrementStep from cache
     * @param businessName
     * @return
     */
    private IncrementStep getIncrementStep(String businessName){
        IncrementStep incrementStep = sequenceIncreStepCache.get(businessName);
        if(null == incrementStep) {
            Long initIncrBeginId = this.getIncBeginId();
            //从数据库中获取,如果数据库中获取的结果为0,则使用配置的进行初始化并写入到数据库
            Long incBeginIdInDB = getIncrIdFromDB(businessName);
            if(null != incBeginIdInDB){
                initIncrBeginId = incBeginIdInDB.longValue();
                incrementStep = new IncrementStep(initIncrBeginId,initIncrBeginId + stepWidth);
                sequenceIncreStepCache.put(businessName, incrementStep);
                updateIncrValue(initIncrBeginId,businessName);
            }else{
                incrementStep = new IncrementStep(initIncrBeginId,initIncrBeginId + stepWidth);
                sequenceIncreStepCache.put(businessName, incrementStep);
                initIncrIdToDB(businessName,incrementStep);
            }
        };
        return incrementStep;
    }

    /**
     * if get incrementId from db result is null, do init increment id with default start incrementId <br/>
     * else get current incrementId in db, and do update incrementId = (current incrementId + step block)
     * @param businessName the same as sequenceName
     * @return  add incrementId success(return true) or failure(return false)
     */
    private boolean getNextBlock(String businessName) {
        Long incrementId = getIncrIdFromDB(businessName);
        boolean updateResult = updateIncrValue(incrementId, businessName) == 1;
        if (updateResult == true) {
            //update mapping in cache
            IncrementStep incrementStep = new IncrementStep(incrementId,incrementId + this.stepWidth);
            sequenceIncreStepCache.put(businessName, incrementStep);
        }
        return updateResult;
    }


    /**
     * increment incrId value and update to table
     * @param currIncrValue current incr value
     * @param businessName the same as sequenceName
     * @return effect of number of rows
     */
    private int updateIncrValue(long currIncrValue, String businessName) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            // update tb_sequence set incrementId = ?  where businessName = ? and incrementId = ?
            statement = connection.prepareStatement(SQLBuilder.buildUpdate(incrTableConfig));
            statement.setLong(1, currIncrValue + this.stepWidth);
            statement.setString(2, businessName);
            statement.setLong(3, currIncrValue);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOG.error("update IncrValue error!", e);
            throw new RuntimeException("update IncrValue error!", e);
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }

    /**
     * get increment id stored in increment table with business Name
     * @param businessName the same as sequenceName
     * @return increment id (increment value)
     */
    private Long getIncrIdFromDB(String businessName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            // select incrementId from tb_sequence where businessName = ?
            statement = connection.prepareStatement(SQLBuilder.buildQuery(incrTableConfig));
            statement.setString(1, businessName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(incrTableConfig.getBusinessId());
            }
        } catch (Exception e) {
            LOG.error("get incrementId in db error!", e);
            throw new RuntimeException("get incrementId in db error!", e);
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    /**
     * store init start increment value to db
     * @param businessName the same as sequenceName
     * @return Initial finish start increment value
     */
    private Long initIncrIdToDB(String businessName, IncrementStep incrementStep) {
        Long incrStartValue = incrementStep.getBeginValue();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            // insert into tb_sequence(businessName,incrementId) values (?,?)
            statement = connection.prepareStatement(SQLBuilder.buildInsert(this.incrTableConfig));
            statement.setString(1, businessName);
            statement.setLong(2, incrStartValue);
            statement.executeUpdate();
        } catch (Exception e) {
            LOG.error("init Increment Value with default incrTableConfig value to db error!", e);
            throw new RuntimeException("initIncrValue error!", e);
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return incrStartValue;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public IncrTableConfig getIncrTableConfig() {
        return incrTableConfig;
    }

    public void setIncrTableConfig(IncrTableConfig incrTableConfig) {
        this.incrTableConfig = incrTableConfig;
    }

    public Long getIncBeginId() {
        return incBeginId;
    }

    public void setIncBeginId(Long incBeginId) {
        this.incBeginId = incBeginId;
    }

    public Integer getStepWidth() {
        return stepWidth;
    }

    public void setStepWidth(Integer stepWidth) {
        this.stepWidth = stepWidth;
    }


}
