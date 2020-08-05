package com.sdxb.secondkill.dto;

import com.sdxb.secondkill.entity.ItemKillSuccess;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: UserSuccessUserDto
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/8/4 17:33
 */

@Data
@ToString
public class KillSuccessUserDto extends ItemKillSuccess implements Serializable {
    private String code;
    private String userName;
    private String phone;
    private String email;
    private String itemName;
}
