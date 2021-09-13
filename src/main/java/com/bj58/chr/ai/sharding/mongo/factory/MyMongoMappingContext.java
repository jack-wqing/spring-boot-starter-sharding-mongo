package com.bj58.chr.ai.sharding.mongo.factory;

import com.bj58.chr.ai.sharding.mongo.context.CollectionNameProvider;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.util.TypeInformation;

/**
 * @author liuwenqing02
 */
public class MyMongoMappingContext extends MongoMappingContext {

    private CollectionNameProvider provider;

    public MyMongoMappingContext(CollectionNameProvider provider) {
        this.provider = provider;
    }

    @Override
    protected <T> BasicMongoPersistentEntity<T> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new MyBasicMongoPersistentEntity<T>(typeInformation, provider);
    }
}