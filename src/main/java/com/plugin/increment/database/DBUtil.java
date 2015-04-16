/*
 * Copyright (c) 2015, www.jd.com. All rights reserved.
 *
 * 警告：本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部、以及其他
 * 任何侵害著作权人权益的行为，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。
 *
 */
package com.plugin.increment.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description:
 *
 * @author pudongping
 * @version 1.0.1
 */
public class DBUtil {

    private DBUtil() {
        throw  new UnsupportedOperationException();
    }

    /**
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void close(Connection connection, PreparedStatement statement,ResultSet resultSet){
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("close resultset error!");
            }
        }

        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("close statement error!");
            }
        }

        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("close connection error!");
            }
        }
    }
}
