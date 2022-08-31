package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

/**
 * @Author luoyin
 * @Date 20:11 2022/8/23
 **/
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {
    private static final String PAGE_CREATE = "houseUser/create";
    private static final String PAGE_EDIT = "houseUser/edit";
    private static final String SHOW_ACTION = "redirect:/house/";

    @Reference
    HouseUserService houseUserService;

    /*
     * 跳转到 houseUser/create.html页面
     * @param: houseUser
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('house.editUser')")
    public String create(HouseUser houseUser, Model model){
        model.addAttribute("houseUser",houseUser);
        return PAGE_CREATE;
    }
    /*
     * 修改房东
     * @param: model
     * @Param: houseUser 房东信息
     * @return:java.lang.String
     **/
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('house.editUser')")
    public String save(Model model , HouseUser houseUser){
        houseUserService.insert(houseUser);
        return successPage(model,"添加房东成功!");
    }

    /*
     * 跳转到 houseUser/edit.html 页面
     * @param: id 房东id
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('house.editUser')")
    public String edit(@PathVariable Long id,Model model){
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }
    /*
     * 修改房东信息
     * @param: model
     * @Param: houseUser  要更改的信息
     * @return:java.lang.String
     **/
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('house.editUser')")
    public String update(Model model , HouseUser houseUser){
        houseUserService.update(houseUser);
        return successPage(model,"修改房东信息成功!");
    }

    /*
     * 删除房东
     * @param: houseId
     * @Param: id
     * @return:java.lang.String
     **/
    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAnyAuthority('house.editUser')")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseUserService.delete(id);
        return SHOW_ACTION + houseId;
    }
}
