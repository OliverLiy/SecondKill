package com.sdxb.secondkill.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName: ItemKillSuccess
 * @Description: TODO ItemKillSuccess实体类
 * @Create by: Liyu
 * @Date: 2020/7/22 17:29
 */

@Data
@ToString
public class ItemKillSuccess {
    private String code;

    private Integer itemId;

    private Integer killId;

    private String userId;

    private Byte status;

    private Date createTime;

    private Integer diffTime;

}
