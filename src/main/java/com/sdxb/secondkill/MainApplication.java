package com.sdxb.secondkill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * @ClassName: MainApplication
 * @Description: TODO  启动类
 * @Create by: Liyu
 * @Date: 2020/7/21 9:32
 */

@SpringBootApplication
@ImportResource(value = {"classpath:spring/spring-jdbc.xml"})
public class MainApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
