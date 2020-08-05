package com.sdxb.secondkill.service;

import com.sdxb.secondkill.dto.KillSuccessUserDto;
import com.sdxb.secondkill.entity.ItemKillSuccess;
import com.sdxb.secondkill.mapper.ItemKillSuccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RabbitReceiveService
 * @Description: TODO 消息消费者
 * @Create by: Liyu
 * @Date: 2020/8/4 21:53
 */
@Service
public class RabbitReceiveService {
    public static final Logger log= LoggerFactory.getLogger(RabbitSenderService.class);
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    /**
     * 用户秒杀成功后超时未支付-监听者
     * @param info
     */
    @RabbitListener(queues = {"${mq.kill.item.success.kill.dead.real.queue}"},containerFactory = "singleListenerContainer")
    public void consumeExpireOrder(KillSuccessUserDto info){
        try {
            log.info("用户秒杀成功后超时未支付-监听者-接收消息:{}",info);

            if (info!=null){
                ItemKillSuccess entity=itemKillSuccessMapper.selectByPrimaryKey(info.getCode());
                if (entity!=null && entity.getStatus().intValue()==0){
                    itemKillSuccessMapper.expireOrder(info.getCode());
                }
            }
        }catch (Exception e){
            log.error("用户秒杀成功后超时未支付-监听者-发生异常：",e.fillInStackTrace());
        }
    }
}
