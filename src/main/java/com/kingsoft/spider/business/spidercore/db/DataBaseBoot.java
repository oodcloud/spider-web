package com.kingsoft.spider.business.spidercore.db;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.db.entity.CheckTableEntity;
import org.apache.commons.dbutils.QueryRunner;
import us.codecraft.webmagic.ResultItems;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyujie on 2018/2/23.
 */
public class DataBaseBoot extends AbstractDataBaseBoot {
    private List<SpiderConfigInfoDto.MatchField> matchFields;
    private String tableName;
    private static QueryRunner runner;

    static {
        runner = new QueryRunner();
    }

    @Override
    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    void setMatchFields(List<SpiderConfigInfoDto.MatchField> matchFields) {
        this.matchFields = matchFields;
    }

    @Override
    void createTable() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "(" +
                "  id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,");
        for (SpiderConfigInfoDto.MatchField field : matchFields) {
            if (field.getFieldName() == null || "".equals(field.getFieldName())) {
                sql.append(field.getFieldEnglishName()).append("  text,");
            } else {
                sql.append(field.getFieldEnglishName()).append("  text COMMENT '").append(field.getFieldName()).append("',");
            }
        }
        sql.append("`creat_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '生成时间',`site_url` varchar(255) COMMENT '当前网页url',  PRIMARY KEY (`id`),  UNIQUE KEY `" + tableName + "_site_url` (`site_url`) USING BTREE" + ")ENGINE=InnoDB DEFAULT CHARSET =utf8;");
        runner.update(connection, sql.toString());
    }

    @Override
    boolean isExistTable() throws SQLException {
        String sql = "SELECT table_name,TABLE_SCHEMA FROM information_schema.TABLES WHERE table_name ='" + tableName + "';";
        List<CheckTableEntity> checkTableEntityList = runner.query(connection, sql, new CheckTableHandler());
        if (checkTableEntityList != null) {
            String database = getDataSource().getUrl().split("/")[3].split("\\?")[0];
            for (CheckTableEntity checkTableEntity : checkTableEntityList) {
                if (checkTableEntity.getTableName().equals(tableName) && checkTableEntity.getTableSchema().equals(database)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    int insertResultItems(ResultItems resultItems) throws SQLException {
        // 避免插入重复数据 使用 insert into  on duplicate key update，另外需要在该表设置唯一键这里的是site_url
        String sql = "INSERT INTO " + tableName;
        Iterator<Map.Entry<String, Object>> iterator = resultItems.getAll().entrySet().iterator();
        StringBuilder field = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        StringBuilder updates = new StringBuilder(" on duplicate key update ");
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            field.append(entry.getKey()).append(",");
            values.append("'").append(entry.getValue()).append("',");
            updates.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("',");
        }
        field = field.deleteCharAt(field.length() - 1).append(") VALUES ");
        values = values.deleteCharAt(values.length() - 1).append(")");
        updates.deleteCharAt(updates.length() - 1);
        sql = sql + field + values + updates;
        return runner.update(connection, sql);
    }

    @Override
    void addColumn(List<String> cols) throws SQLException {
        System.out.println("要创建的列名:" + JSON.toJSONString(cols));
        for (String col : cols) {
            String sql = "ALTER TABLE " + tableName + " ADD  " + col + " text ";
            runner.update(connection, sql);
        }
    }

}
