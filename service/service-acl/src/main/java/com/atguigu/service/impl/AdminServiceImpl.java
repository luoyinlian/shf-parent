package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.AdminService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 10:03 2022/8/19
 **/
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AdminRoleMapper adminRoleMapper;
    @Override
    public BaseMapper getBaseMapper() {
        return adminMapper;
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        HashMap<String, Object> map = new HashMap<>();

        //查询所有角色
        List<Role> roleList = roleMapper.findAll();
        //查询已经选择的角色id
        List<Long> adminRoleList = adminRoleMapper.findRoleIdByAdminId(adminId);
        ArrayList<Role> assignRoleList = new ArrayList<>();
        ArrayList<Role> unAssignRoleList = new ArrayList<>();
        for (Role role : roleList) {
            if(adminRoleList.contains(role.getId())){
                //assignRoleList   已选择的角色
                assignRoleList.add(role);
            }else {
                //unAssignRoleList 未选择的角色
                unAssignRoleList.add(role);
            }
        }
        map.put("assignRoleList",assignRoleList);
        map.put("unAssignRoleList",unAssignRoleList);
        return map;
    }

    @Override
    public Admin getByUserName(String username) {
        return adminMapper.getByUserName(username);
    }
}
