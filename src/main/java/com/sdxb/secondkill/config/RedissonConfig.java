package com.sdxb.secondkill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName: RedissonConfig
 * @Description: TODO Redisson配置
 * @Create by: Liyu
 * @Date: 2020/8/12 20:26
 */
@Configuration
public class RedissonConfig {
    @Autowired
    private Environment environment;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress(environment.getProperty("redis.config.host"));
//                .setPassword(environment.getProperty("spring.redis.password"));
        RedissonClient client= Redisson.create(config);
        return client;
    }
}
