package com.sdxb.secondkill.service.Impl;

import com.sdxb.secondkill.entity.ItemKill;
import com.sdxb.secondkill.entity.ItemKillSuccess;
import com.sdxb.secondkill.enums.SysConstant;
import com.sdxb.secondkill.mapper.ItemKillMapper;
import com.sdxb.secondkill.mapper.ItemKillSuccessMapper;
import com.sdxb.secondkill.service.KillService;
import com.sdxb.secondkill.service.RabbitSenderService;
import com.sdxb.secondkill.utils.SnowFlake;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: KillServiceImpl
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/25 21:23
 */
@Service
public class KillServiceImpl implements KillService {

    private SnowFlake snowFlake=new SnowFlake(2,3);
    @Autowired
    private ItemKillMapper itemKillMapper;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Override
    public Boolean KillItem(Integer killId, Integer userId) throws Exception {
        Boolean result=false;
        //判断当前用户是否抢购过该商品
        if (itemKillSuccessMapper.countByKillUserId(killId,userId)<=0){
            //获取商品详情
            ItemKill itemKill=itemKillMapper.selectByid(killId);
            if (itemKill!=null&&itemKill.getCanKill()==1){
                int res=itemKillMapper.updateKillItem(killId);
                if (res>0){
                    commonRecordKillSuccessInfo(itemKill,userId);
                    result=true;
                }
            }
        }else {
            System.out.println("您已经抢购过该商品");
        }
        return result;
    }

    private void commonRecordKillSuccessInfo(ItemKill itemKill, Integer userId) {
        ItemKillSuccess entity=new ItemKillSuccess();
        String orderNo=String.valueOf(snowFlake.nextId());
        entity.setCode(orderNo);
        entity.setItemId(itemKill.getItemId());
        entity.setKillId(itemKill.getId());
        entity.setUserId(userId.toString());
        entity.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        entity.setCreateTime(DateTime.now().toDate());
        if (itemKillSuccessMapper.countByKillUserId(itemKill.getId(),userId) <= 0){
            int res=itemKillSuccessMapper.insertSelective(entity);
            if(res>0){
                //处理抢购成功后的流程
                //这里的业务可以自己加
                //将订单送入死信队列
                rabbitSenderService.sendKillSuccessOrderExpireMsg(orderNo);
            }
        }
    }

    //mysql优化
    @Override
    public Boolean KillItemV2(Integer killId, Integer userId) throws Exception {
        Boolean result=false;
        //判断当前用户是否抢购过该商品
        if (itemKillSuccessMapper.countByKillUserId(killId,userId)<=0){
            //获取商品详情
            ItemKill itemKill=itemKillMapper.selectByidV2(killId);
            if (itemKill!=null&&itemKill.getCanKill()==1 && itemKill.getTotal()>0){
                int res=itemKillMapper.updateKillItemV2(killId);
                if (res>0){
                    commonRecordKillSuccessInfo(itemKill,userId);
                    result=true;
                }
            }
        }else {
            System.out.println("您已经抢购过该商品");
        }
        return result;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //redis分布式锁
    @Override
    public Boolean KillItemV3(Integer killId, Integer userId) throws Exception {

        //借助Redis的原子操作实现分布式锁
        ValueOperations valueOperations=stringRedisTemplate.opsForValue();
        final String key=new StringBuffer().append(killId).append(userId).toString();
        final String value= String.valueOf(snowFlake.nextId());
        Boolean result = valueOperations.setIfAbsent(key, value);
        if (result){
            stringRedisTemplate.expire(key,30, TimeUnit.SECONDS);
            //判断当前用户是否抢购过该商品
            if (itemKillSuccessMapper.countByKillUserId(killId,userId)<=0){
                //获取商品详情
                ItemKill itemKill=itemKillMapper.selectByidV2(killId);
                if (itemKill!=null&&itemKill.getCanKill()==1 && itemKill.getTotal()>0){
                    int res=itemKillMapper.updateKillItemV2(killId);
                    if (res>0){
                        commonRecordKillSuccessInfo(itemKill,userId);
                        return true;
                    }
                }
            }else {
                System.out.println("您已经抢购过该商品");
            }
            //释放锁
            if (value.equals(valueOperations.get(key).toString())){
                stringRedisTemplate.delete(key);
            }
        }
        return false;
    }

    @Autowired
    private RedissonClient redissonClient;
    //redisson的分布式锁
    @Override
    public Boolean KillItemV4(Integer killId, Integer userId) throws Exception {
        Boolean result=false;
        final String key=new StringBuffer().append(killId).append(userId).toString();
        RLock lock=redissonClient.getLock(key);

        Boolean cacheres=lock.tryLock(30,10,TimeUnit.SECONDS);
        if (cacheres){
            //判断当前用户是否抢购过该商品
            if (itemKillSuccessMapper.countByKillUserId(killId,userId)<=0){
                //获取商品详情
                ItemKill itemKill=itemKillMapper.selectByidV2(killId);
                if (itemKill!=null&&itemKill.getCanKill()==1 && itemKill.getTotal()>0){
                    int res=itemKillMapper.updateKillItemV2(killId);
                    if (res>0){
                        commonRecordKillSuccessInfo(itemKill,userId);
                        result=true;
                    }
                }
            }else {
                System.out.println("您已经抢购过该商品");
            }
            lock.unlock();
        }
        return result;
    }

    @Autowired
    private CuratorFramework curatorFramework;
    private final String path="/seckill/zookeeperlock/";
    //zookeeper的分布式锁
    @Override
    public Boolean KillItemV5(Integer killId, Integer userId) throws Exception{
        Boolean result=false;
        InterProcessMutex mutex=new InterProcessMutex(curatorFramework,path+killId+userId+"-lock");
        if (mutex.acquire(10L,TimeUnit.SECONDS)){
            //判断当前用户是否抢购过该商品
            if (itemKillSuccessMapper.countByKillUserId(killId,userId)<=0){
                //获取商品详情
                ItemKill itemKill=itemKillMapper.selectByidV2(killId);
                if (itemKill!=null&&itemKill.getCanKill()==1 && itemKill.getTotal()>0){
                    int res=itemKillMapper.updateKillItemV2(killId);
                    if (res>0){
                        commonRecordKillSuccessInfo(itemKill,userId);
                        result=true;
                    }
                }
            }else {
                System.out.println("您已经抢购过该商品");
            }
            if (mutex!=null){
                mutex.release();
            }
        }

        return result;
    }
}
