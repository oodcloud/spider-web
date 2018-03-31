package com.kingsoft.spider.business.datadb;

import com.alibaba.druid.pool.DruidDataSource;
import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by wangyujie on 2018/3/15.
 */
public class DbUse {
    protected QueryRunner runner;
    private DruidDataSource druidDataSource;

    public DbUse() {
        this.runner = new QueryRunner();
        this.druidDataSource = new DruidDataSource();
    }

    protected DbUse setDataSource(DataManageDbInfoDto dto) {
        this.druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        this.druidDataSource.setUrl("jdbc:mysql://" + dto.getDbAddress() + "/" + dto.getDbName() + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true");
        this.druidDataSource.setUsername(dto.getDbUsername());
        this.druidDataSource.setPassword(dto.getDbPassword());
        return this;
    }


    public Connection getConnection() throws SQLException {
        return this.druidDataSource.getConnection();
    }


    public void close() {
        try {
            DbUtils.close(getConnection());
            druidDataSource.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


}
