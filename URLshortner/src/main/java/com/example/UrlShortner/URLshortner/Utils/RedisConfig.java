package com.example.UrlShortner.URLshortner.Utils;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnection(){
        RedisStandaloneConfiguration standAlone = new RedisStandaloneConfiguration("localhost",6379);
        return new LettuceConnectionFactory(standAlone);
    }
    @Bean
    @Primary
    public RedisTemplate<String,String> redisTemp(){
        RedisTemplate<String,String> myRedisTemplate = new RedisTemplate<String,String>();
        myRedisTemplate.setConnectionFactory(redisConnection());
        myRedisTemplate.setKeySerializer(new StringRedisSerializer());
        myRedisTemplate.setValueSerializer(new StringRedisSerializer());
        return myRedisTemplate;
    }
}
