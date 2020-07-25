package com.sdxb.secondkill.service;

import com.sdxb.secondkill.entity.ItemKill;

import java.util.List;

/**
 * @ClassName: ItemService
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/22 17:34
 */
public interface ItemService {
    List<ItemKill> getKillItems();
    ItemKill getKillDetail(Integer id) throws Exception;
}
