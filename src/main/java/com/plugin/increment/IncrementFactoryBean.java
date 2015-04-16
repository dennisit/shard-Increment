/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment;


import com.plugin.increment.core.IncrSequence;
import com.plugin.increment.object.IncrTableConfig;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;

/**
 * Description:
 *
 * @author pudongping
 * @version 1.0.1
 */
public class IncrementFactoryBean implements FactoryBean{

    // incrementId start value
    private long incBeginId = 0;

    // step width , default value is five
    private int stepWidth = 5;

    // DataSource
    private DataSource dataSource;

    // init increment table config
    private IncrTableConfig incrTableConfig;


    @Override
    public Object getObject() throws Exception {
        if(null == dataSource){
            throw new ExceptionInInitializerError("IncrementFactory init parameter dataSource is empty,please check spring config file!");
        }
        if(null == incrTableConfig ){
            return new IncrSequence(incBeginId,stepWidth,dataSource);
        }else{
            return new IncrSequence(incBeginId,stepWidth,dataSource,incrTableConfig);
        }
    }

    @Override
    public Class<?> getObjectType() {
        return IncrSequence.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public long getIncBeginId() {
        return incBeginId;
    }

    public void setIncBeginId(long incBeginId) {
        this.incBeginId = incBeginId;
    }

    public int getStepWidth() {
        return stepWidth;
    }

    public void setStepWidth(int stepWidth) {
        this.stepWidth = stepWidth;
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
}
