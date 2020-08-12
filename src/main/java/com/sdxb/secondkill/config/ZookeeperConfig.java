package com.sdxb.secondkill.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName: ZookeeperConfig
 * @Description: TODO Zookeeper配置
 * @Create by: Liyu
 * @Date: 2020/8/12 20:29
 */
@Configuration
public class ZookeeperConfig {
    @Autowired
    private Environment environment;

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder()
                .connectString(environment.getProperty("zk.host"))
                .namespace(environment.getProperty("zk.namespace" ))
                .retryPolicy(new RetryNTimes(5,1000))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
