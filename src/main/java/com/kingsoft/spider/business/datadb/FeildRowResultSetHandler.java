package com.kingsoft.spider.business.datadb;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by wangyujie on 2018/3/19.
 */
public class FeildRowResultSetHandler implements ResultSetHandler<Integer> {
    @Override
    public Integer handle(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return  resultSet.getInt("totalCount");
    }
}
