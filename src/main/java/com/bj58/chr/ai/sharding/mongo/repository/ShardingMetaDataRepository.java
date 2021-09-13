package com.bj58.chr.ai.sharding.mongo.repository;

import org.assertj.core.util.Lists;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.util.ClassTypeInformation;

import java.util.List;

/**
 * 管理分库 分表的元数据
 *   1、是否分库 集合判断
 *   2、是否分表 集合判断
 * @author liuwenqing02
 */
public final class ShardingMetaDataRepository {

    private static List<String> dbShardingTables = Lists.newArrayList();

    private static List<String> shardingTables = Lists.newArrayList();

    private static int tableSize;

    public static boolean isShardingTableContain(String table){
        if(shardingTables != null){
            return shardingTables.contains(table);
        }
        return false;
    }

    public static boolean isDbShardingTableContain(Class<?> cls){
        if(dbShardingTables != null){
            BasicMongoPersistentEntity persistentEntity = new BasicMongoPersistentEntity(ClassTypeInformation.from(cls));
            String argCollectionName = persistentEntity.getCollection();
            return dbShardingTables.contains(argCollectionName);
        }
        return false;
    }


    public static void setDbShardingTables(List<String> dbShardingTables) {
        ShardingMetaDataRepository.dbShardingTables = dbShardingTables;
    }


    public static void setShardingTables(List<String> shardingTables) {
        ShardingMetaDataRepository.shardingTables = shardingTables;
    }

    public static int getTableSize() {
        return tableSize;
    }

    public static void setTableSize(int tableSize) {
        ShardingMetaDataRepository.tableSize = tableSize;
    }
}