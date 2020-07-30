package com.sdxb.secondkill.enums;

/**
 * @ClassName: SysConstant
 * @Description: TODO 订单状态实体类
 * @Create by: Liyu
 * @Date: 2020/7/26 10:21
 */
public class SysConstant {
    public enum OrderStatus{
        //订单无效
        Invalid(-1,"无效"),
        //订单成功未付款
        SuccessNotPayed(0,"成功-未付款"),
        //订单已付款
        HasPayed(1,"已付款"),
        //订单已取消
        Cancel(2,"已取消"),
        ;

        private Integer code;
        private String msg;

        OrderStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
