package com.atguigu.controller;

import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author luoyin
 * @Date 17:18 2022/8/18
 **/
@Controller
public class FrameController {

    private static final String PAGE_INDEX = "frame/index";
    private static final String PAGE_MAIN = "frame/main";
    private static final String PAGE_LOGIN = "frame/login";
    private static final String PAGE_AUTH = "frame/auth";

    @Reference
    AdminService adminService;
    @Reference
    PermissionService permissionService;
    @Reference
    RoleService roleService;
    /*
     * 
     * 框架首页
     * @return:
     **/
    @GetMapping("/")
    public String index(Model model){
        //admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByUserName(user.getUsername());
        model.addAttribute("admin",admin);
        //role
        Role role = roleService.getRoleNameByAdminId(admin.getId());
        model.addAttribute("role",role);
        //permissionList
        List<Permission> permissionList =  permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }

    /*
     * 框架主页
     * @return
     **/
    @GetMapping("main")
    public String main(){
        return PAGE_MAIN;
    }

    @RequestMapping("/login")
    public String login(){
        return PAGE_LOGIN;
    }

    /*
     * 没有权限返回的页面
     * @return:java.lang.String
     **/
    @RequestMapping("/auth")
    public String auth(){
        return PAGE_AUTH;
    }
}
