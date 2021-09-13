基于spring-boot-starter-sharding-mongo的说明：
1、不使用分库分表，和引入spring-boot-starter-data-mongodb 一样
     正常使用
2、使用分库分表：
     需要启动分库分表的功能：启动属性：spring.multiple.mongo.enable=true

   配置方式：
    #spring mongo的正常使用uri配置
   spring:
     data:
       mongodb:
         uri: mongodb0
     #mongo分库分表配置
     multiple:
       mongo:
         #启动分库分表功能
         enable: true
         #需要分库的表
         dbShardingTables:
           - sharding_shardingDbModel
           - sharding_shardingDbTableModel
         #需要分表的表
         shardingTables:
           - sharding_shardingTableModel
           - sharding_shardingDbTableModel
         #每个库总共分多少个表
         tableSize: 2
         #多数据源配置
         dataSource:
           ds0:
             uri: mongodb0
           ds1:
             uri: mongodb1
   1-不进行分库分表使用方式
       NoShardingModel noShardingModel = new NoShardingModel();
       noShardingModel.setName("不分库分表第1条");
       mongoTemplate.save(noShardingModel);
   2-进行分表操作：
       1、需要提供com.bj58.chr.ai.sharding.mongo.category.SubTableKeySupplier实现
   3-分库操作
       1、需要提供com.bj58.chr.ai.sharding.mongo.category.SubDatabaseKeySupplier实现
       并且在进行MongoTemplate操作是需要设置操作的class
       例：DbShardingThreadLocal.set(ShardingDbTableModel.class);
          mongoTemplate.save(shardingDbTableModel);
   
注：在进行MongoTemplate操作时：不要使用带集合名的操作，负责会使用指定的集合名
   分库中设置的第一个数据源 就是默认的数据源，当启动分库分表是，如果不是分库，分表，则使用默认的数据源进行操作