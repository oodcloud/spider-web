package com.kingsoft.spider.business.spidercore.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.datadb.DbUseHandleUtils;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangyujie on 2018/2/10.
 */
public abstract class AbstractDataBaseBoot {
    private final Logger logger = LoggerFactory.getLogger(AbstractDataBaseBoot.class);
    private DruidDataSource dataSource;
    protected Connection connection;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private ReentrantLock lock=new ReentrantLock();
    private ReentrantLock checkLock=new ReentrantLock();
    private volatile int count=0;

    public DruidDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DruidDataSource dataSource) {
        if (this.dataSource == null) {
            this.dataSource = dataSource;
        }
        setConnection(this.dataSource);
    }

    private void setConnection(DruidDataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            DbUtils.close(this.dataSource.getConnection());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void template(List<SpiderConfigInfoDto.MatchField> matchFields, ResultItems resultItems, String tableName) {
        try {
            if (atomicInteger.get() <= 1) {
                atomicInteger.getAndIncrement();
                checkLock.lock();
                this.setTableName(tableName);
                this.setMatchFields(matchFields);
                if (!isExistTable()) {
                    String address = this.dataSource.getUrl().split("/")[2];
                    String database = this.dataSource.getUrl().split("/")[3].split("\\?")[0];
                    logger.info("主机：" + address + "数据库：" + database + "正在创建新表在：" + tableName);
                    this.createTable();
                }
                checkLock.unlock();
            }
            Map<String, Object> datas = resultItems.getAll();
            List<String> alertColumnName = new ArrayList<>();
            Iterator<String> vars = datas.keySet().iterator();
            if (count<3)
            {
                lock.lock();
                List<String> dbTableColumnCount = DbUseHandleUtils.getColumnCount(this.dataSource.getConnection(), tableName);
                while (vars.hasNext()) {
                    String var = vars.next();
                    if (!dbTableColumnCount.contains(var)) {
                        alertColumnName.add(var);
                    }
                }
                if (alertColumnName.size()!=0)
                {
                    this.addColumn(alertColumnName);
                }
                count++;
                lock.unlock();
            }

            try {
                this.insertResultItems(resultItems);
            } catch (SQLException e) {
                String address = this.dataSource.getUrl().split("/")[2];
                String database = this.dataSource.getUrl().split("/")[3].split("\\?")[0];
                logger.error("异常产生开始----------------------------------------------------------");
                logger.error("主机：" + address + "数据库：" + database + "表在：" + tableName);
                logger.error("异常数据：" + JSON.toJSONString(resultItems));
                e.printStackTrace();
                logger.error("异常产生结束----------------------------------------------------------");
                close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
    }

    abstract void setTableName(String tableName);

    abstract void setMatchFields(List<SpiderConfigInfoDto.MatchField> matchFields);

    abstract void createTable() throws SQLException;

    abstract boolean isExistTable() throws SQLException;

    abstract int insertResultItems(ResultItems resultItems) throws SQLException;

    abstract void addColumn(List<String> cols) throws SQLException;


}
