//package com.example.orderapi.config;
//
//
//import javax.annotation.PostConstruct;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.test.context.TestConfiguration;
//import redis.embedded.RedisServer;
//
//@TestConfiguration
//public class TestRedisConfig {
//
//    public RedisServer redisServer;
//
//    public TestRedisConfig(RedisProperties redisProperties) {
//        this.redisServer = RedisServer.builder()
//            .port(redisProperties.getPort())
//            .build();
//
//    }
//
//    @PostConstruct
//    public void startRedis() {
//        redisServer.start();
//    }
//
//}
