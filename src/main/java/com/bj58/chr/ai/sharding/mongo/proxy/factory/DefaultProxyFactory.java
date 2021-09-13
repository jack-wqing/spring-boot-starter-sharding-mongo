package com.bj58.chr.ai.sharding.mongo.proxy.factory;

import com.bj58.chr.ai.sharding.mongo.context.MongoTemplateProvider;
import com.bj58.chr.ai.sharding.mongo.proxy.source.TargetSource;

import java.io.Serializable;

/**
 * @author liuwenqing02
 */
@SuppressWarnings("serial")
public class DefaultProxyFactory implements ProxyFactory, Serializable {

	@Override
	public Proxy createWmbProxy(TargetSource source, MongoTemplateProvider provider){
        return new CglibProxy(source, provider);
	}
   
}
