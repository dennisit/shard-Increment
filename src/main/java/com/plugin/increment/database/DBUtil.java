
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
