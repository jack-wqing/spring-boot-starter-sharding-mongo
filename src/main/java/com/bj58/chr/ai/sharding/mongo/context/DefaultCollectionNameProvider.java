package com.bj58.chr.ai.sharding.mongo.context;

import com.bj58.chr.ai.sharding.mongo.category.SubTableKeySupplier;
import com.bj58.chr.ai.sharding.mongo.repository.ShardingMetaDataRepository;
import com.google.common.base.Objects;

/**
 * @author liuwenqing02
 */
public class DefaultCollectionNameProvider implements CollectionNameProvider {

    private SubTableKeySupplier supplier;

    public DefaultCollectionNameProvider(SubTableKeySupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public Integer value() {
        if(supplier == null){
            return null;
        }
        int size = ShardingMetaDataRepository.getTableSize();
        Object obj = supplier.value();
        if(obj == null){
            return null;
        }
        int tableSize = Objects.hashCode(obj) % size;
        return tableSize;
    }

}