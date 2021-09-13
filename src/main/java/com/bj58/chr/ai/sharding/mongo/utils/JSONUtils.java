package com.bj58.chr.ai.sharding.mongo.utils;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author liuwenqing02
 */
public class JSONUtils {

    private final static Logger Log = LoggerFactory.getLogger(JSONUtils.class);

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public final static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * 把json转换成对象
     *
     * @param json
     *            json数据
     * @param cls
     *            要转换的class
     * @return 转换好的对象
     */
    public final static <T> T readValue(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (Exception e) {
            Log.error(json, e);
        }
        return null;
    }

    public final static <T> T readValue(byte[] bytes, Class<T> cls) {
        try {
            return mapper.readValue(bytes, cls);
        } catch (Exception e) {
            Log.error("byte:" + bytes, e);
        }
        return null;
    }

    /**
     * 把json转换成对象
     *
     * @param json
     *            json数据
     * @return 转换好的对象
     */
    @SuppressWarnings({ "rawtypes" })
    public final static <T> T readValue(String json, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            Log.error(json, e);
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes" })
    public final static <T> T readValue(byte[] bytes, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(bytes, valueTypeRef);
        } catch (Exception e) {
            Log.error("byte:" + bytes, e);
        }
        return null;
    }

    /**
     * 把对象转换的json数据
     *
     * @param entity
     *            要转换的对象
     * @return 转换好的数据
     */
    public final static String writeValue(Object entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            Log.error(entity.toString(), e);
        }
        return null;
    }

    public final static byte[] writeByteValue(Object entity) {
        try {
            return mapper.writeValueAsBytes(entity);
        } catch (Exception e) {
            Log.error(entity.toString(), e);
        }
        return null;
    }
}
