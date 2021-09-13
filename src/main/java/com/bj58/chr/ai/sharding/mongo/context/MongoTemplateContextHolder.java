package com.bj58.chr.ai.sharding.mongo.context;

import com.bj58.chr.ai.sharding.mongo.constant.NumberUtils;
import com.google.common.collect.Maps;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 *
 * 管理所有的MongoTemplate MongoClient
 *
 * @author liuwenqing02
 */
public final class MongoTemplateContextHolder{

    private Map<String, MongoTemplate> mongoTemplates;

    private MongoTemplate defaultMongoTemplate;

    private Map<String, MongoClient> mongoClients;

    private static String keyPrefix;

    private MongoTemplateContextHolder(){

    }

    public static class MongoTemplateContextHolderInstance{
        public static final MongoTemplateContextHolder mongoContext = new MongoTemplateContextHolder();
    }

    public static MongoTemplateContextHolder getInstance(){
        return MongoTemplateContextHolderInstance.mongoContext;
    }

    public synchronized void addMongoClient(String name, MongoClient mongoClient){
        if(StringUtils.isEmpty(name) || mongoClient == null){
            return ;
        }
        if(mongoClients == null){
            mongoClients = Maps.newTreeMap();
        }
        mongoClients.put(name, mongoClient);
    }

    public synchronized void addMongoTemplate(String name, MongoTemplate mongoTemplate){
        if(StringUtils.isEmpty(name) || mongoTemplate == null){
            return ;
        }
        if(mongoTemplates == null){
            mongoTemplates = Maps.newTreeMap();
        }
        mongoTemplates.put(name, mongoTemplate);
    }

    public void initFinished(){
        /**
         * 寻找配置的属性 设置默认值
         */
        for (Map.Entry<String, MongoTemplate> entry : mongoTemplates.entrySet()) {
            String name = entry.getKey();
            /**
             * 获取数据库配置前缀
             */
            calculatePrefix(name);
            if(this.defaultMongoTemplate == null){
                defaultMongoTemplate = entry.getValue();
            }
            break;
        }
    }

    public MongoTemplate getMongoTemplate(String key){
        return mongoTemplates.get(key);
    }

    public MongoTemplate getDefaultMongoTemplate() {
        return defaultMongoTemplate;
    }

    public int dbSize(){
        return mongoTemplates.size();
    }

    public String keyPrefix(){
        return keyPrefix;
    }

    public void closeClient(){
        if(mongoClients != null){
            mongoClients.forEach((key, value) -> {
                try{
                    value.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }

    private void calculatePrefix(String value){
        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < value.length(); pos++) {
            Character character = value.charAt(pos);
            if(NumberUtils.isContain(character)){
                break;
            }
            sb.append(character);
        }
        keyPrefix = sb.toString();
    }



}