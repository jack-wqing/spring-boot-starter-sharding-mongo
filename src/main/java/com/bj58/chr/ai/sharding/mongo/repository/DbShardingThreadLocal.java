package com.bj58.chr.ai.sharding.mongo.repository;

/**
 * 分库线程设置：
 *   为什么要使用此类：
 *    1、实现方式是使用代理，但是代理中获取不到集合名
 *    2、需要在进行分库表的操作时，记录将要操作的分库的集合名，然后判断是否接下来的操作是否需要分库操作
 *    3、每次操作都需要写入，设置的值=只能使用一次
 *
 *   使用时机：
 *     在进行MongoTemplate操作之前 进行设置
 *
 * @author liuwenqing02
 */
public class DbShardingThreadLocal {

    private static final ThreadLocal<Class<?>> dbShardingThreadLocal = new ThreadLocal<>();

    /**
     * 设置Thread共享变量你
     * @param cls
     */
    public static void set(Class<?> cls){
        dbShardingThreadLocal.set(cls);
    }

    /**
     * 获取线程共性变量
     * @return
     */
    public static Class<?> get(){
        return dbShardingThreadLocal.get();
    }

}