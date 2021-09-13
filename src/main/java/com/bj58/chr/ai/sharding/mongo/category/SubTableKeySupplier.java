package com.bj58.chr.ai.sharding.mongo.category;

/**
 * 进行分集合
 *  进行hashcode 计算集合名的键值
 * @author liuwenqing02
 */
public interface SubTableKeySupplier {

    /**
     * 分表判断的key
     * @return
     */
    Object value();

}