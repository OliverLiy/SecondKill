package com.sdxb.secondkill.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: KillDto
 * @Description: TODO 订单号和用户id
 * @Create by: Liyu
 * @Date: 2020/7/26 10:55
 */
@Data
@ToString
public class KillDto implements Serializable {
    private Integer killid;
    private Integer userid;

    public KillDto() {
    }

    public KillDto(Integer killid, Integer userid) {
        this.killid = killid;
        this.userid = userid;
    }
}

