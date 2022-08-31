package com.atguigu.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 15:11 2022/8/18
 **/
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PAGE_INDEX = "role/index";
    private static final String PAGE_CREATE = "role/create";
    private static final String PAGE_EDIT = "role/edit";
    private static final String LIST_ACTION = "redirect:/role";
    private static final String PAGE_ASSIGNSHOW = "role/assignShow";
    @Reference
    RoleService roleService;
    @Reference
    PermissionService permissionService;

    /*
     *
     * @param: filters 包含 roleName (想要查询的名字),pageSize 每页记录数, pageNum 当前页数
     * @Param: model
     * @return:跳到首页
     **/
    @RequestMapping
    @PreAuthorize("hasAnyAuthority('role.show')")
    public String index(@RequestParam Map filters, Model model){
        PageInfo<Role> page = roleService.findPage(filters);
        model.addAttribute("page",page);
        //搜索回显
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }
    /*
     *跳到添加页面
     * @return:
     **/
    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create(){

        return PAGE_CREATE;
    }
    /*
     *添加角色
     * @return:成功页面
     **/
    @PreAuthorize("hasAnyAuthority('role.create')")
    @PostMapping("/save")
    public String save(Role role,Model model){
        roleService.insert(role);

        return successPage(model,"添加角色成功");
    }
    /*
     * @return:修改角色页面
     **/
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return PAGE_EDIT;
    }

    /*
     * 修改角色
     * @return:修改成功
     **/
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping("/update")
    public String update(Role role,Model model){
        roleService.update(role);
        return successPage(model,"修改角色成功");
    }
    /*
     * 删除角色
     * @return:
     **/
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        roleService.delete(id);
        return LIST_ACTION;
    }

    /*
     * 跳转到 role/assignShow.html 页面
     * @param: id 角色id
     * @return:java.lang.String
     **/
    @GetMapping("/assignShow/{id}")
    @PreAuthorize("hasAnyAuthority('role.assign')")
    public String assignShow(@PathVariable Long id ,Model model){
        model.addAttribute("roleId",id);
        //zNodes 获取节点集合
        List<Map<String, Object>> zNodes = permissionService.findPermissionListByRoleId(id);
        model.addAttribute("zNodes", JSON.toJSONString(zNodes));
        return PAGE_ASSIGNSHOW;
    }

    /*
     * 分配权限
     * @param: roleId 角色id
     * @Param: permissionIds 选中的权限列表
     * @Param: model
     * @return:java.lang.String
     **/
    @PostMapping("/assignPermission")
    @PreAuthorize("hasAnyAuthority('role.assign')")
    public String assignPermission(Long roleId , @RequestParam("permissionIds") List<Long> permissionIds,Model model){
        permissionService.saveRolePermission(roleId,permissionIds);
        return successPage(model,"权限已经分配完毕!");
    }
}
