package com.bj58.chr.ai.sharding.mongo.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * mongo: 数据元配置
 * @author liuwenqing02
 */
@ConfigurationProperties(prefix = "spring.multiple.mongo")
public class MongoMultipleProperties {

    private Map<String, MongoProperties> dataSource;

    private List<String> dbShardingTables;

    private List<String> shardingTables;

    private int tableSize;

    public Map<String, MongoProperties> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, MongoProperties> dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getDbShardingTables() {
        return dbShardingTables;
    }

    public void setDbShardingTables(List<String> dbShardingTables) {
        this.dbShardingTables = dbShardingTables;
    }

    public List<String> getShardingTables() {
        return shardingTables;
    }

    public void setShardingTables(List<String> shardingTables) {
        this.shardingTables = shardingTables;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

}