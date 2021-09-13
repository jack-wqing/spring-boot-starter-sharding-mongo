package com.bj58.chr.ai.sharding.mongo.context;

import com.bj58.chr.ai.sharding.mongo.category.SubDatabaseKeySupplier;
import com.bj58.chr.ai.sharding.mongo.repository.DbShardingThreadLocal;
import com.bj58.chr.ai.sharding.mongo.repository.ShardingMetaDataRepository;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.MongoTemplate;


/**
 * @author liuwenqing02
 */
public class DefaultMongoTemplateProvider implements MongoTemplateProvider {

    private SubDatabaseKeySupplier supplier;

    public DefaultMongoTemplateProvider(SubDatabaseKeySupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public MongoTemplate provide() {
        /**
         * 没提供分库数据
         *    执行默认的MongoTemplate
         */
        if(supplier == null){
            return MongoTemplateContextHolder.getInstance().getDefaultMongoTemplate();
        }

        /**
         * 如果设置默认使用默认的MongoTemplate 默认是配置文件第一个
         * 如果访问的表 没有进行配置，则返回默认的MongoTemplate 默认是配置文件第一个
         */
        Class<?> cls = DbShardingThreadLocal.get();
        if(cls == null || !ShardingMetaDataRepository.isDbShardingTableContain(cls)){
            return MongoTemplateContextHolder.getInstance().getDefaultMongoTemplate();
        }

        String key = produce();
        if(key == null){
            return MongoTemplateContextHolder.getInstance().getDefaultMongoTemplate();
        }
        return MongoTemplateContextHolder.getInstance().getMongoTemplate(key);
    }

    public String produce(){
        int size = MongoTemplateContextHolder.getInstance().dbSize();
        String keyPrefix = MongoTemplateContextHolder.getInstance().keyPrefix();
        Object obj = supplier.value();
        if(obj == null){
            return null;
        }
        int dbIndex = Objects.hashCode(obj) % size;
        return keyPrefix + dbIndex;
    }

}