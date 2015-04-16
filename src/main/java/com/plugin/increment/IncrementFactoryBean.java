
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
