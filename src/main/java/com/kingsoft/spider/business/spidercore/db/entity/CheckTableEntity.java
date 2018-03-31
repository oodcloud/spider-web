package com.kingsoft.spider.business.spidercore.db.entity;

/**
 * Created by vog1g on 2018/3/25.
 */
public class CheckTableEntity {
    private String tableName;
    private String tableSchema;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
