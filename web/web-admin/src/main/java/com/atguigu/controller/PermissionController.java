package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

/**
 * @Author luoyin
 * @Date 18:59 2022/8/26
 **/
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private static final String PAGE_CREATE = "permission/create";
    private static final String PAGE_INDEX = "permission/index";
    private static final String PAGE_EDIT = "permission/edit";
    private static final String LIST_ACTION = "redirect:/permission";
    @Reference
    PermissionService permissionService;

    @RequestMapping
    @PreAuthorize("hasAnyAuthority('permission.show')")
    public String index(Model model){
        //list  所有权限
        List<Permission> list = permissionService.findAllMenus();
        model.addAttribute("list",list);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('permission.create')")
    public String create(Permission permission,Model model){
        model.addAttribute("permission",permission);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('permission.create')")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
        return successPage(model,"权限添加成功!");
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('permission.delete')")
    public String delete(@PathVariable Long id , Model model){
        permissionService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('permission.edit')")
    public String edit(@PathVariable Long id ,Model model){
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('permission.edit')")
    public String update(@PathVariable Long id , Permission permission, Model model){
        permission.setId(id);
        permissionService.update(permission);
        return successPage(model,"修改权限成功!");
    }
}
