package com.sdxb.secondkill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO User实体类
 * @Create by: Liyu
 * @Date: 2020/7/22 17:29
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String password;

    private String phone;

    private String email;

    private Byte isActive;

    private Date createTime;

    private Date updateTime;
}
