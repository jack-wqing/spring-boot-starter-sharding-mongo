package com.bj58.chr.ai.sharding.mongo.factory;

import com.bj58.chr.ai.sharding.mongo.context.CollectionNameProvider;
import com.bj58.chr.ai.sharding.mongo.repository.ShardingMetaDataRepository;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.util.TypeInformation;

/**
 * @author liuwenqing02
 */
public class MyBasicMongoPersistentEntity<T> extends BasicMongoPersistentEntity<T> {

    private CollectionNameProvider provider;

    /**
     * Creates a new {@link BasicMongoPersistentEntity} with the given {@link TypeInformation}. Will default the
     * collection name to the entities simple type name.
     *
     * @param typeInformation must not be {@literal null}.
     */
    public MyBasicMongoPersistentEntity(TypeInformation<T> typeInformation, CollectionNameProvider provider) {
        super(typeInformation);
        this.provider = provider;
    }

    @Override
    public String getCollection() {
        String collectionName = super.getCollection();
        if(ShardingMetaDataRepository.isShardingTableContain(collectionName)){
            if(provider != null){
                Integer value = provider.value();
                if(value != null){
                    collectionName = collectionName + provider.value();
                }
            }
        }
        return collectionName;
    }
}