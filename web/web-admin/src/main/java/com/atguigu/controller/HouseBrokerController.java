package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
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
 * @Date 19:08 2022/8/23
 **/
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {

    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";
    @Reference
    HouseBrokerService houseBrokerService;
    @Reference
    AdminService adminService;

    /*
     * 跳转到houseBroker/create.html 页面
     * @param: houseBroker 包含房源id
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('house.editBroker')")
    public String create(HouseBroker houseBroker , Model model){
        //houseBroker
        model.addAttribute("houseBroker",houseBroker);
        //adminList
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        return PAGE_CREATE;
    }
    /*
     *
     * @param: model
     * @Param: houseBroker 包含houseId,brokerId=adminId
     * @return:java.lang.String
     **/
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('house.editBroker')")
    public String save(Model model,HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return successPage(model,"添加经纪人成功!");
    }

    /*
     * 跳转到houseBroker/edit.html页面
     * @param: id house_broker的主建
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('house.editBroker')")
    public String edit(@PathVariable Long id,Model model){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        //houseBroker
        model.addAttribute("houseBroker",houseBroker);
        //adminList
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        return PAGE_EDIT;
    }

    /*
     * 修改经纪人
     * @param: id   house_broker的主建
     * @Param: brokerId   = adminId
     * @Param: model
     * @return:java.lang.String
     **/
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('house.editBroker')")
    public String update(@PathVariable Long id ,Long brokerId , Model model){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        Admin admin = adminService.getById(brokerId);
        houseBroker.setBrokerId(brokerId);
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return successPage(model,"修改经纪人成功!");
    }

    /*
     * 删除经纪人
     * @param: houseId 房源id
     * @Param: id   经纪人id(主键)
     * @return:java.lang.String 重定向到show页面(要传参)
     **/
    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAnyAuthority('house.editBroker')")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseBrokerService.delete(id);
        return SHOW_ACTION + houseId;
    }

}
