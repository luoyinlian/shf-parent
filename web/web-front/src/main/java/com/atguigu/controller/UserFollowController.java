package com.atguigu.controller;


import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author luoyin
 * @Date 10:21 2022/8/25
 **/
@Controller
@RequestMapping("/userFollow")
public class UserFollowController{

    @Reference
    private UserFollowService userFollowService;

    /*
     * 添加关注
     * @param: houseId 房源id
     * @Param: session 从session中取出保存的用户信息
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/auth/follow/{houseId}")
    public Result addFollow(@PathVariable Long houseId , HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("existUser");
        /*if(userInfo == null){
            return Result.build(null,ResultCodeEnum.LOGIN_AUTH);
        }*/
        UserFollow userFollow = new UserFollow();
        userFollow.setHouseId(houseId);
        userFollow.setUserId(userInfo.getId());
        userFollowService.insert(userFollow);
        return Result.ok();
    }

    /*
     * 取消关注
     * @param: houseId 房源id
     * @Param: session 从session中取出保存的用户信息
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/auth/cancelFollow/{houseId}")
    public Result cancelFollow(@PathVariable Long houseId , HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("existUser");
        /*if(userInfo == null){
            return Result.build(null,ResultCodeEnum.LOGIN_AUTH);
        }*/
        UserFollow userFollow = userFollowService.getByUserIdAndHouseId(userInfo.getId(), houseId);
        userFollowService.delete(userFollow.getId());
        return Result.ok();
    }

    /*
     * 我的关注分页展示
     * @param: pageNum  当前页数
     * @Param: pageSize 每页记录数
     * @Param: session
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("existUser");
        /*if(userInfo == null){
            return Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        }*/
        PageInfo<UserFollowVo> page = userFollowService.findListPage(pageNum,pageSize,userInfo.getId());
        return Result.ok(page);
    }

    /*
     * 根据关注id 取消关注
     * @param: id
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/auth/cancelFollowById/{id}")
    public Result cancelFollowById(@PathVariable Long id){
        userFollowService.delete(id);
        return Result.ok();
    }
}
