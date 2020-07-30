package com.sdxb.secondkill.response;

import com.sdxb.secondkill.enums.StatusCode;

/**
 * @ClassName: BaseResponse
 * @Description: TODO 描述回复信息的类
 * @Create by: Liyu
 * @Date: 2020/7/25 16:01
 */
public class BaseResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(StatusCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BaseResponse(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

