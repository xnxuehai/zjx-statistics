package com.zjx.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author Aaron
 * @date 2020/6/15 14:15
 */
@Configuration
public class RedisConfig {
    /**
     * @param redisConnectionFactory redis 顶层链接工厂
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 实例化 RedisTemplate
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置链接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置 keySerializer 序列化方式为 stringSerializer
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // 设置 hashKeySerializer 序列化方式为 stringSerializer
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // 实例化 FastJson 序列化对象
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // 设置 valueSerializer 序列化方式为 fastJsonRedisSerializer
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        // 设置 hashValueSerializer 序列化方式为 fastJsonRedisSerializer
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置默认序列化为 fastJsonRedisSerializer
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }

    /**
     * 自定义静态内部类，用来实现 FastJson 序列化
     */
    public static class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
        private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
        private Class<T> clazz;

        public FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (null == t) {
                return new byte[0];
            }
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (null == bytes || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, DEFAULT_CHARSET);
            return (T) JSON.parseObject(str, clazz);
        }
    }
}