package com.bj58.chr.ai.sharding.mongo.proxy.factory;

import com.bj58.chr.ai.sharding.mongo.context.MongoTemplateProvider;
import com.bj58.chr.ai.sharding.mongo.proxy.source.TargetSource;

/**
 * @author liuwenqing02
 */
public interface ProxyFactory {

    /**
     * 创建代理实现
     * @param source
     * @param provider
     * @return
     */
    Proxy createWmbProxy(TargetSource source, MongoTemplateProvider provider);

}
