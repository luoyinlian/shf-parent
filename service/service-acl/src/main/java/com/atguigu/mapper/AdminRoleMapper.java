package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    List<Long> findRoleIdByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);

}
