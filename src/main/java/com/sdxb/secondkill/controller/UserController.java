package com.sdxb.secondkill.controller;

import com.sdxb.secondkill.entity.User;
import com.sdxb.secondkill.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserController
 * @Description: TODO 用户登陆注册Controller
 * @Create by: Liyu
 * @Date: 2020/7/28 16:25
 */
@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = {"/login","unauth"},method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, Model model){
        System.out.println(username+password);
        String msg="";
        try {
            if (!SecurityUtils.getSubject().isAuthenticated()){
                UsernamePasswordToken token=new UsernamePasswordToken(username,password);
                SecurityUtils.getSubject().login(token);
            }
        }catch (UnknownAccountException e){
            msg=e.getMessage();
        }catch (DisabledAccountException e){
            msg=e.getMessage();
        }catch (IncorrectCredentialsException e){
            msg=e.getMessage();
        }catch (Exception e){
            msg="用户登陆异常";
            e.printStackTrace();
        }
        if (StringUtils.isBlank(msg)){
            return "redirect:/item";
        }else {
            model.addAttribute("errormsg",msg);
            return "login";
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String toRegister(){
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String checkRegister(@RequestParam("username")String username,@RequestParam("password")String password,Model model){
        String msg="";
        User user = userMapper.SelectByName(username);
        if (user!=null){
            msg="当前用户已存在";
            model.addAttribute("errormsg",msg);
            return "register";
        }
        else {
            int res=userMapper.insertuser(username,password);
            if (res>0){
                return "login";
            }else {
                msg="用户创建失败，请联系管理员处理";
                model.addAttribute("errormsg",msg);
                return "register";
            }
        }
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String Logout(){
        SecurityUtils.getSubject().logout();
        return "login";
    }
}
