package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {
     Role getRoleNameByAdminId(Long adminId) ;
}
