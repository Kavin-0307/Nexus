package com.nexus.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.RedisClient;
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;
    @Bean
    public RedisClient redisClient() {
		return RedisClient.builder().hostAndPort(redisHost,redisPort).build();

    	
    }
    
	
}
