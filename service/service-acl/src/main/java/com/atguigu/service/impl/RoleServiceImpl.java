package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.RoleService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 15:17 2022/8/18
 **/
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Override
    public BaseMapper<Role> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public void saveAdminRole(Long adminId, List<Long> roleIds) {
        //删除adminId的所有角色
        adminRoleMapper.deleteByAdminId(adminId);
        //批量添加roleIds
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleMapper.insert(adminRole);
        }
    }

    @Override
    public Role getRoleNameByAdminId(Long adminId) {
        return roleMapper.getRoleNameByAdminId(adminId);
    }
}
