package com.sdxb.secondkill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: BaseController
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/25 10:55
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String error(){
        return "error";
    }
}
