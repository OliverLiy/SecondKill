package com.sdxb.secondkill.service;

import com.sdxb.secondkill.entity.ItemKillSuccess;
import com.sdxb.secondkill.mapper.ItemKillSuccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @ClassName: SchedulerService
 * @Description: TODO 处理定时业务
 * @Create by: Liyu
 * @Date: 2020/8/5 10:16
 */

@Configuration
public class SchedulerService {
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private Environment env;

    //定时获取status=0的订单并判断是否超过了支付订单时间，然后进行失效
    @Scheduled(cron = "0/30 * * * * ?")
    public void schedulerExpireOrders() {
        List<ItemKillSuccess> list = itemKillSuccessMapper.selectExpireOrders();
        if(list!=null&&!list.isEmpty()){
            for (ItemKillSuccess item : list) {
                if (item!=null && item.getStatus().intValue()==0 && item.getDiffTime()>env.getProperty("scheduler.expire.orders.time",Integer.class)){
                    itemKillSuccessMapper.expireOrder(item.getCode());
                }
            }
        }
    }
}
