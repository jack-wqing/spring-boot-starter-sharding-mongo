package com.bj58.chr.ai.sharding.mongo.context;

/**
 * @author liuwenqing02
 */
public interface CollectionNameProvider {
    /**
     * 计算分表 值
     */
    Integer value();

}