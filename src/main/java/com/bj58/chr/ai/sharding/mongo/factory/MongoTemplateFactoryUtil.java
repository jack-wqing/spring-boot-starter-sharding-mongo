package com.bj58.chr.ai.sharding.mongo.factory;

import com.bj58.chr.ai.sharding.mongo.context.CollectionNameProvider;
import com.mongodb.MongoClient;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoDbFactorySupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

/**
 * @author liuwenqing02
 */
public class MongoTemplateFactoryUtil {

    public static final MongoTemplate createMongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter converter){
        return new MongoTemplate(mongoDbFactory, converter);
    }

    public static MongoDbFactorySupport<?> mongoDbFactory(MongoClient mongo,
                                                   com.mongodb.client.MongoClient mongoClient, MongoProperties properties) {
        MongoClient preferredClient = mongo;
        if (preferredClient != null) {
            return new SimpleMongoDbFactory(preferredClient, properties.getMongoClientDatabase());
        }
        com.mongodb.client.MongoClient fallbackClient = mongoClient;
        if (fallbackClient != null) {
            return new SimpleMongoClientDbFactory(fallbackClient, properties.getMongoClientDatabase());
        }
        throw new IllegalStateException("Expected to find at least one MongoDB client.");
    }

    public static MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context,
                                                       MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }

    public static MongoMappingContext mongoMappingContext(MongoCustomConversions conversions, ApplicationContext applicationContext,
                                                          MongoProperties properties, CollectionNameProvider provider) throws ClassNotFoundException {
        MongoMappingContext context = new MyMongoMappingContext(provider);
        context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class, Persistent.class));
        Class<?> strategyClass = properties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    public static MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }

    public static MongoClient mongo(MongoProperties properties) {
        MongoClientFactory factory = new MongoClientFactory(properties, null);
        return factory.createMongoClient(null);
    }

}