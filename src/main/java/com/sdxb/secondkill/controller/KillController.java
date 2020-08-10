package com.sdxb.secondkill.controller;

import com.sdxb.secondkill.dto.KillDto;
import com.sdxb.secondkill.enums.StatusCode;
import com.sdxb.secondkill.response.BaseResponse;
import com.sdxb.secondkill.service.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: KillController
 * @Description: TODO 执行抢购功能的controller
 * @Create by: Liyu
 * @Date: 2020/7/25 16:03
 */

@Controller
public class KillController {
    private static final String prefix="kill";
    @Autowired
    private KillService killService;
    @RequestMapping(value = prefix+"/execute",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse execute(@RequestBody @Validated KillDto killDto, BindingResult result, HttpSession httpSession){
        if (result.hasErrors()||killDto.getKillid()<0){
            return new BaseResponse(StatusCode.InvalidParam);
        }
        //修改前
        //Integer userid=10;
        //添加登陆功能后使用这段代码
        Object uid=httpSession.getAttribute("uid");
        if (uid==null){
            return new BaseResponse(StatusCode.UserNotLog);
        }
        Integer userid= (Integer) uid;

        try {
            Boolean res=killService.KillItem(killDto.getKillid(),userid);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"商品已经抢购完或您已抢购过该商品");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseResponse baseResponse=new BaseResponse(StatusCode.Success);
        return baseResponse;
    }
    @RequestMapping(value = prefix+"/execute/success",method = RequestMethod.GET)
    public String killsuccess(){
        return "killsuccess";
    }
    @RequestMapping(value = prefix+"/execute/fail",method = RequestMethod.GET)
    public String killfail(){
        return "killfail";
    }

    @RequestMapping(value = prefix+"/test/execute",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse testexecute(@RequestBody @Validated KillDto killDto, BindingResult result, HttpSession httpSession){
        if (result.hasErrors()||killDto.getKillid()<0){
            return new BaseResponse(StatusCode.InvalidParam);
        }
        try {
            Boolean res=killService.KillItem(killDto.getKillid(),killDto.getUserid());
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"商品已经抢购完或您已抢购过该商品");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseResponse baseResponse=new BaseResponse(StatusCode.Success);
        baseResponse.setData("抢购成功");
        return baseResponse;
    }

    //测试mysql优化的版本
    @RequestMapping(value = prefix+"/test/execute2",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse testexecute2(@RequestBody @Validated KillDto killDto, BindingResult result, HttpSession httpSession){
        if (result.hasErrors()||killDto.getKillid()<0){
            return new BaseResponse(StatusCode.InvalidParam);
        }
        try {
            Boolean res=killService.KillItemV2(killDto.getKillid(),killDto.getUserid());
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"商品已经抢购完或您已抢购过该商品");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseResponse baseResponse=new BaseResponse(StatusCode.Success);
        baseResponse.setData("抢购成功");
        return baseResponse;
    }


}
