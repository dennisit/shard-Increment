/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment.object;

import java.io.Serializable;

/**
 * Description:
 *
 * @author pudongping
 * @version 1.0.1
 */
public class IncrTableConfig implements Serializable {

    /**
     * default increment table name
     */
    public static final String DEFAULT_TABLE_NAME = "tb_sequence";
    /**
     * default increment business name column
     */
    public static final String DEFAULT_BUSINESS_PK = "businessName";
    /**
     * default increment id column
     */
    public static final String DEFAULT_BUSINESS_ID = "incrementId";

    // table name
    private String tableName = DEFAULT_TABLE_NAME;
    // table column bind business name
    private String businessPK = DEFAULT_BUSINESS_PK;
    // table column bind business Id
    private String businessId = DEFAULT_BUSINESS_ID;

    /**
     * construct dependency
     * increment now value and business key store in which table <br/>
     *
     * <h4>SQL :</h4>
     * <pre>
     *      CREATE TABLE tb_sequence (
     *           businessName varchar(50) NOT NULL COMMENT 'business name',
     *           incrementId int(11) NOT NULL COMMENT 'increment id value',
     *           PRIMARY KEY (`businessName`)
     *      ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ID自增表';
     * </pre>
     *
     */
    public IncrTableConfig(){
        this(DEFAULT_TABLE_NAME,DEFAULT_BUSINESS_PK,DEFAULT_BUSINESS_ID);
    }

    public IncrTableConfig(String tableName, String businessPK, String businessId) {
        this.tableName = tableName;
        this.businessPK = businessPK;
        this.businessId = businessId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBusinessPK() {
        return businessPK;
    }

    public void setBusinessPK(String businessPK) {
        this.businessPK = businessPK;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


}
