package com.bj58.chr.ai.sharding.mongo.category;

/**
 * 分库提供值
 *  计算方式会根据提供值计算出HashCode
 *   然后与总库进行取余操作
 * @author liuwenqing02
 */

public interface SubDatabaseKeySupplier {

    /**
     * 分库 - key - 值
     * @return
     */
    Object value();

}