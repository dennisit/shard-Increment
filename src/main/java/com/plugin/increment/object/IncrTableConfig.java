
//--------------------------------------------------------------------------
// Copyright (c) 2010-2020, En.dennisit or Cn.苏若年
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
// Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the dennisit nor the names of its contributors
// may be used to endorse or promote products derived from this software
// without specific prior written permission.
// Author: dennisit@163.com | dobby | 苏若年
//--------------------------------------------------------------------------
package com.plugin.increment.object;

import java.io.Serializable;

/**
 * Description:
 * @author dennisit@163.com
 * @version 1.0
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
