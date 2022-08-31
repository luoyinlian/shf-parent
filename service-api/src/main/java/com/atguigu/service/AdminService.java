package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.Map;

public interface AdminService extends BaseService<Admin> {
    Map<String, Object> findRolesByAdminId(Long adminId);


    Admin getByUserName(String username);
}
