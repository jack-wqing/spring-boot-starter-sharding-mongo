package com.bj58.chr.ai.sharding.mongo.context;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author liuwenqing02
 */
public interface MongoTemplateProvider {
    /**
     * 获取 MontoTemplate
     * @return
     */
    MongoTemplate provide();

}