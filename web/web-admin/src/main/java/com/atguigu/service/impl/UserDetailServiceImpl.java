package com.atguigu.service.impl;

import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author luoyin
 * @Date 14:28 2022/8/27
 **/
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    AdminService adminService;
    @Reference
    PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByUserName(username);
        if(admin == null){
            return null;
        }
        //该用户的权限
        List<Permission> permissionList =  permissionService.findListPermissionByAdminId(admin.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission : permissionList) {
            if(permission.getCode() == null || permission.getCode() == ""){
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(permission.getCode()));
        }

        return new User(admin.getUsername(),admin.getPassword(),authorities);
    }
}
