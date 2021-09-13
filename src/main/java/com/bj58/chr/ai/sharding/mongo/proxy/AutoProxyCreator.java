package com.bj58.chr.ai.sharding.mongo.proxy;

import com.bj58.chr.ai.sharding.mongo.context.MongoTemplateProvider;
import com.bj58.chr.ai.sharding.mongo.proxy.factory.DefaultProxyFactory;
import com.bj58.chr.ai.sharding.mongo.proxy.factory.Proxy;
import com.bj58.chr.ai.sharding.mongo.proxy.factory.ProxyFactory;
import com.bj58.chr.ai.sharding.mongo.proxy.source.SingletonTargetSource;
import com.bj58.chr.ai.sharding.mongo.proxy.source.TargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author liuwenqing02
 */
public class AutoProxyCreator extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, BeanClassLoaderAware {

    private BeanFactory beanFactory;
    
    private ClassLoader classLoader;

    private MongoTemplateProvider provider;

    public AutoProxyCreator(MongoTemplateProvider provider) {
        this.provider = provider;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Object proxy;
        if(bean instanceof MongoTemplate) {
            proxy = createProxy(cls, beanName, new SingletonTargetSource(bean));
        }else {
            proxy = bean;
        }
        return proxy;
    }
    
    protected Object createProxy(Class<?> beanClass, String beanName, TargetSource targetSource) {
        ProxyFactory proxyFactory = new DefaultProxyFactory();
        Proxy proxy = proxyFactory.createWmbProxy(targetSource, provider);
        return proxy.getProxy(this.classLoader);
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
