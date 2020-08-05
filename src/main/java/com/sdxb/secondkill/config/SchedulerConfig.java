package com.sdxb.secondkill.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @ClassName: SchedulerConfig
 * @Description: TODO 定时任务配置文件
 * @Create by: Liyu
 * @Date: 2020/8/5 10:13
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //通过线程池注册定时任务
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
