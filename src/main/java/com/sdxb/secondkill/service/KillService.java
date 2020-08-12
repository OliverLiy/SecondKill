package com.sdxb.secondkill.service;

/**
 * @ClassName: KillService
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/25 21:18
 */
public interface KillService {
    Boolean KillItem(Integer killId,Integer userId) throws Exception;
    Boolean KillItemV2(Integer killId,Integer userId) throws Exception;
    Boolean KillItemV3(Integer killId,Integer userId) throws Exception;
    Boolean KillItemV4(Integer killId,Integer userId) throws Exception;
    Boolean KillItemV5(Integer killId,Integer userId) throws Exception;
}
