
package com.bj58.chr.ai.sharding.mongo.proxy.factory;

import com.bj58.chr.ai.sharding.mongo.context.MongoTemplateProvider;
import com.bj58.chr.ai.sharding.mongo.proxy.source.TargetSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author liuwenqing02
 */
public class CglibProxy implements Proxy, MethodInterceptor,Serializable {

    private static final long serialVersionUID = -6095302175317147777L;


    protected static final Log logger = LogFactory.getLog(CglibProxy.class);


	protected TargetSource source;

	private final MongoTemplateProvider provider;

	public CglibProxy(TargetSource source, MongoTemplateProvider provider){
        this.source = source;
        this.provider = provider;
    }

	@Override
	public Object getProxy() {
		return getProxy(ClassUtils.getDefaultClassLoader());
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
        Class<?> rootClass = this.source.getTargetClass();
        Class<?> proxySuperClass = rootClass;
        Enhancer enhancer = createEnhancer();
        enhancer.setClassLoader(classLoader);
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setInterfaces(proxySuperClass.getInterfaces());
        enhancer.setCallback(this);
        return createProxyClassAndInstance(enhancer);
    
	}

	protected Object createProxyClassAndInstance(Enhancer enhancer) {
        Object obj = source.getTarget();
	    MongoTemplate origin = (MongoTemplate) obj;
		return enhancer.create(new Class[]{MongoDbFactory.class}, new Object[]{origin.getMongoDbFactory()});
	}
	
	protected Enhancer createEnhancer() {
		return new Enhancer();
	}

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MongoTemplate mongoTemplate = provider.provide();
        if(mongoTemplate != null){
            obj = mongoTemplate;
        }
        Object retVal = method.invoke(obj, args);
        return retVal;
    }

}
