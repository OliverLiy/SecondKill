package com.sdxb.secondkill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: testController
 * @Description: TODO  测试controller
 * @Create by: Liyu
 * @Date: 2020/7/21 10:03
 */

@Controller
public class testController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String index(){
        return "test";
    }
}
