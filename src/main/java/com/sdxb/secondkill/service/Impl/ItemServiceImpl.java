package com.sdxb.secondkill.service.Impl;

import com.sdxb.secondkill.entity.ItemKill;
import com.sdxb.secondkill.mapper.ItemKillMapper;
import com.sdxb.secondkill.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ItemServiceImpl
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/22 17:35
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemKillMapper itemKillMapper;

    //获取待秒杀商品的列表
    @Override
    public List<ItemKill> getKillItems() {
        List<ItemKill> list = itemKillMapper.selectAll();
        return list;
    }

    //获取秒杀详情
    @Override
    public ItemKill getKillDetail(Integer id) throws Exception {
        ItemKill itemKill=itemKillMapper.selectByid(id);
        if (itemKill==null){
            throw new Exception("秒杀详情记录不存在");
        }
        return itemKill;
    }
}
