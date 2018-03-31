package com.kingsoft.spider.business.datadb;


import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2018/3/15.
 */
public class CommonResultSetHandler implements ResultSetHandler<List<List<String>>> {

    @Override
    public List<List<String>> handle(ResultSet resultSet) throws SQLException {
        List<List<String>> data=new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> columnName=new ArrayList<>();
        boolean flag=true;
        while (resultSet.next())
        {
            List<String> item=new ArrayList<>();
            if (flag)
            {
                for (int i = 1; i <= columnCount; i++) {
                    columnName.add(metaData.getColumnName(i));
                }
                data.add(columnName);
                flag=false;
            }
            for (int i = 1; i <= columnCount; i++) {
                item.add(resultSet.getString(i));
            }
            data.add(item);
        }
        return data;
    }
}
