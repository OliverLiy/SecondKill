package com.sdxb.secondkill.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Item
 * @Description: TODO Item实体类
 * @Create by: Liyu
 * @Date: 2020/7/22 17:28
 */
@Data
public class Item {
    private Integer id;

    private String name;

    private String code;

    private Long stock;

    private Date purchaseTime;

    private Integer isActive;

    private Date createTime;

    private Date updateTime;
}
