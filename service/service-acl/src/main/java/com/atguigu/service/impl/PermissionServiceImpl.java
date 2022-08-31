package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 16:39 2022/8/26
 **/
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public BaseMapper<Permission> getBaseMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionListByRoleId(Long roleId) {
        List<Map<String, Object>> permissionList = new ArrayList<>();
        //获取所有权限
        List<Permission> permissionListAll = permissionMapper.findAll();
        //获取该角色的所有权限id
        List<Long> rolePermissionList = rolePermissionMapper.findPermissionIdByRoleId(roleId);
        //该角色没有的权限
        for (Permission permission : permissionListAll) {
            Map<String, Object> zNode = new HashMap<>();
            if(rolePermissionList.contains(permission.getId())){
                //有权限
                zNode.put("checked",true);
            }else{
                //没有权限
                zNode.put("checked",false);
            }
            zNode.put("id",permission.getId());
            zNode.put("pId",permission.getParentId());
            zNode.put("name",permission.getName());
            zNode.put("open",true);
            permissionList.add(zNode);
        }
        return permissionList;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIds) {
        //删除角色的所有权限
        rolePermissionMapper.deleteByRoleId(roleId);
        //遍历权限,给角色加权限
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {

        List<Permission>  permissionList = permissionMapper.findListPermissionByAdminId(adminId);
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findAllMenus() {
        List<Permission> permissionList = permissionMapper.findAll();
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findListPermissionByAdminId(Long id) {
        return permissionMapper.findListPermissionByAdminId(id);
    }
}
