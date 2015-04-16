
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
package com.plugin.increment.context;

import com.plugin.increment.object.IncrTableConfig;

/**
 * Description:
 * @author dennisit@163.com
 * @version 1.0
 */
public class SQLBuilder {

    private SQLBuilder(){
        throw  new UnsupportedOperationException();
    }

    /**
     * build query sql with {@code IncrTableConfig} <br/>
     * following is sql with {@code IncrTableConfig} default value
     * "    select incrementId from tb_sequence where businessName = ?  "
     *
     * @return
     */
    public static String buildQuery(IncrTableConfig incrTableConfig){
        return new StringBuilder()
            .append(" select ").append(incrTableConfig.getBusinessId())
            .append(" from ").append(incrTableConfig.getTableName())
            .append(" where ").append(incrTableConfig.getBusinessPK())
            .append(" =  ?")
            .toString();
    }

    /**
     * build query sql with {@code IncrTableConfig} <br/>
     * following is sql with {@code IncrTableConfig} default value
     * "    update tb_sequence set incrementId = ?  where businessName = ? and incrementId = ?    "
     *
     * @return  U
     */
    public static String buildUpdate(IncrTableConfig incrTableConfig){
        return new StringBuilder()
            .append(" update ").append(incrTableConfig.getTableName())
            .append(" set ").append(incrTableConfig.getBusinessId()).append(" = ?")
            .append(" where ").append(incrTableConfig.getBusinessPK()).append(" = ?")
            .append(" and ").append(incrTableConfig.getBusinessId()).append("= ?")
            .toString();
    }

    /**
     * build query sql with {@code IncrTableConfig} <br/>
     * following is sql with {@code IncrTableConfig} default value
     * "    insert into tb_sequence(businessName,incrementId) values (?,?)    "
     * @return
     */
    public static String buildInsert(IncrTableConfig incrTableConfig){
        return new StringBuilder()
            .append(" insert into ").append(incrTableConfig.getTableName())
            .append(" (").append(incrTableConfig.getBusinessPK()).append(" , ").append(incrTableConfig.getBusinessId()).append(" )")
            .append(" values ( ? , ? )")
            .toString();
    }

    public static void main(String[] args){
        IncrTableConfig incrTableConfig = new IncrTableConfig();
        System.out.println("create:" + SQLBuilder.buildInsert(incrTableConfig));
        System.out.println("update:" + SQLBuilder.buildUpdate(incrTableConfig));
        System.out.println("select:" + SQLBuilder.buildQuery(incrTableConfig));
    }
}
