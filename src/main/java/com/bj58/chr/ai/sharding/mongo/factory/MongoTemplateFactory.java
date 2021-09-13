package com.bj58.chr.ai.sharding.mongo.factory;

import com.bj58.chr.ai.sharding.mongo.config.MongoMultipleProperties;
import com.bj58.chr.ai.sharding.mongo.context.CollectionNameProvider;
import com.bj58.chr.ai.sharding.mongo.context.MongoTemplateContextHolder;
import com.bj58.chr.ai.sharding.mongo.repository.ShardingMetaDataRepository;
import com.mongodb.MongoClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Map;

/**
 * MongoTemplate 进行创建
 * @author liuwenqing02
 */
public class MongoTemplateFactory implements ApplicationContextAware, InitializingBean, DisposableBean {

    private ApplicationContext context;

    private MongoTemplateContextHolder mongoTemplateContextHolder = MongoTemplateContextHolder.getInstance();

    private MongoMultipleProperties mongoMultipleProperties;

    private CollectionNameProvider provider;

    public MongoTemplateFactory(MongoMultipleProperties mongoMultipleProperties, CollectionNameProvider provider) {
        this.mongoMultipleProperties = mongoMultipleProperties;
        this.provider = provider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(mongoMultipleProperties == null){
            throw new RuntimeException("mongoMultipleProperties must is not null");
        }
        /**
         * 初始化多数据库的连接
         */
        initMongoTemplate();
    }

    public void initMongoTemplate() throws ClassNotFoundException {
        Map<String, MongoProperties> dataSource = mongoMultipleProperties.getDataSource();
        if(dataSource == null){
            throw new RuntimeException("dataSource config is null");
        }
        /**
         * 1、记录元数据
         */
        ShardingMetaDataRepository.setDbShardingTables(mongoMultipleProperties.getDbShardingTables());
        ShardingMetaDataRepository.setShardingTables(mongoMultipleProperties.getShardingTables());
        ShardingMetaDataRepository.setTableSize(mongoMultipleProperties.getTableSize());
        /**
         * 2、初始化数据库列表
         */
        for (Map.Entry<String, MongoProperties> entry : dataSource.entrySet()) {
            String key = entry.getKey();
            MongoProperties value = entry.getValue();
            MongoClient mongoClient = MongoTemplateFactoryUtil.mongo(value);
            mongoTemplateContextHolder.addMongoClient(key, mongoClient);
            MongoDbFactory mongoDbFactory = MongoTemplateFactoryUtil.mongoDbFactory(mongoClient, null, value);
            MongoCustomConversions conversions = MongoTemplateFactoryUtil.mongoCustomConversions();
            MongoMappingContext mongoMappingContext = MongoTemplateFactoryUtil.mongoMappingContext(conversions, context, value, provider);
            MappingMongoConverter mappingMongoConverter = MongoTemplateFactoryUtil.mappingMongoConverter(mongoDbFactory, mongoMappingContext, conversions);
            MongoTemplate mongoTemplate = MongoTemplateFactoryUtil.createMongoTemplate(mongoDbFactory, mappingMongoConverter);
            mongoTemplateContextHolder.addMongoTemplate(key, mongoTemplate);
        }
        mongoTemplateContextHolder.initFinished();
    }

    @Override
    public void destroy() throws Exception {
        mongoTemplateContextHolder.closeClient();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


}