package com.atguigu.controller;

import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author luoyin
 * @Date 20:43 2022/8/24
 **/
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    UserInfoService userInfoService;
    /*
     * 生成验证码
     * @param: phone 手机号
     * @Param: session
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/sendCode/{phone}")
    public Result sendPhoneCode(@PathVariable String phone,HttpSession session){
        session.setAttribute("CODE","4444");
        return Result.ok();
    }
    /*
     * 用户注册
     * @param: registerBo 注册信息
     * @Param: session
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo,HttpSession session){

        //校验验证码
        if(!registerBo.getCode().equals(session.getAttribute("CODE"))){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        //校验手机号是否已经注册
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        if(userInfo != null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        userInfo = new UserInfo();
        userInfo.setPhone(registerBo.getPhone());
        userInfo.setPassword(registerBo.getPassword());
        userInfo.setNickName(registerBo.getNickName());

        //注册手机号
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    /*
     * 登录
     * @param: loginBo 登录信息
     * @Param: session
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session){

        //判断用户是否存在
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());
        if(userInfo == null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断密码是否正确
        if(!userInfo.getPassword().equals(loginBo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断账户状态是否被锁定
        if(userInfo.getStatus() == 0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        session.setAttribute("existUser",userInfo);
        return Result.ok(userInfo);
    }

    /*
     * 退出登录
     * @param: session
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/logout")
    public Result login(HttpSession session){
        session.removeAttribute("existUser");
        return Result.ok();
    }
}
