package com.bj58.chr.ai.sharding.mongo.config;

import com.bj58.chr.ai.sharding.mongo.category.SubDatabaseKeySupplier;
import com.bj58.chr.ai.sharding.mongo.category.SubTableKeySupplier;
import com.bj58.chr.ai.sharding.mongo.context.DefaultCollectionNameProvider;
import com.bj58.chr.ai.sharding.mongo.context.DefaultMongoTemplateProvider;
import com.bj58.chr.ai.sharding.mongo.factory.MongoTemplateFactory;
import com.bj58.chr.ai.sharding.mongo.proxy.AutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;

/**
 * @author liuwenqing02
 */
@Configuration
@EnableConfigurationProperties({MongoMultipleProperties.class})
@ConditionalOnProperty(prefix = "spring.multiple.mongo", name = "enable", havingValue = "true")
public class MyMongoAutoConfig {

    private MongoMultipleProperties mongoMultipleProperties;

    public MyMongoAutoConfig(MongoMultipleProperties mongoMultipleProperties) {
        this.mongoMultipleProperties = mongoMultipleProperties;
    }

    /**
     * subTableKeySupplier 可以为空：为空则不会进行分表
     * @param subTableKeySupplier
     * @return
     */
    @Bean
    public MongoTemplateFactory mongoTemplateFactory(@Nullable SubTableKeySupplier subTableKeySupplier){
        MongoTemplateFactory mongoTemplateFactory = new MongoTemplateFactory(mongoMultipleProperties,
                new DefaultCollectionNameProvider(subTableKeySupplier));
        return mongoTemplateFactory;
    }

    /**
     * subDatabaseKeySupplier 可以为空：为空则使用默认的MongoTemplate
     * @param subDatabaseKeySupplier
     * @return
     */
    @Bean
    public AutoProxyCreator autoProxyCreator(@Nullable SubDatabaseKeySupplier subDatabaseKeySupplier){
        AutoProxyCreator autoProxyCreator = new AutoProxyCreator(new DefaultMongoTemplateProvider(subDatabaseKeySupplier));
        return autoProxyCreator;
    }

}