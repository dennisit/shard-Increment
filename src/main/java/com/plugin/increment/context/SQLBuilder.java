/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment.context;

import com.plugin.increment.object.IncrTableConfig;

/**
 * Description:
 *
 * @author pudongping
 * @version 1.0.1
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
