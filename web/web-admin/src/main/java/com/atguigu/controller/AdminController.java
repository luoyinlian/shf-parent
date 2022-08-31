package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author luoyin
 * @Date 9:49 2022/8/19
 **/
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private static final String PAGE_INDEX = "admin/index";
    private static final String PAGE_CREATE = "admin/create";
    private static final String PAGE_EDIT = "admin/edit";
    private static final String LIST_ACTION = "redirect:/admin";
    private static final String PAGE_UPLOAD = "admin/upload";
    private static final String PAGE_ASSIGNSHOW = "admin/assignShow";

    @Reference
    AdminService adminService;
    @Reference
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    /*
     * 跳转到 admin/index.html页面
     * @return:java.lang.String
     **/
    @RequestMapping
    @PreAuthorize("hasAnyAuthority('admin.show')")
    public String index(@RequestParam Map filters, Model model){
        PageInfo<Admin> page = adminService.findPage(filters);
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }
    /*
     *  跳转到 admin/create.html页面
     * @return:
     **/
    @PreAuthorize("hasAnyAuthority('admin.create')")
    @GetMapping("/create")
    public String create(){

        return PAGE_CREATE;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('admin.create')")
    public String save(Admin admin,Model model){
        String passWord = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(passWord);
        adminService.insert(admin);
        return successPage(model,"添加用户成功!");
    }
    /*
     * 跳转到admin/edit页面
     * @param: id 根据id查询用户,为了回显
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('admin.edit')")
    public String edit(@PathVariable Long id, Model model){
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    /*
     * 添加用户
     * @param: model
     * @Param: admin 用户信息
     * @return:java.lang.String
     **/
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin.edit')")
    public String update(Model model,Admin admin){
        adminService.update(admin);
        return successPage(model,"修改用户成功!");
    }

    /*
     * 根据id删除用户
     * @param: id 用户id
     * @return:java.lang.String
     **/
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('admin.delete')")
    public String delete(@PathVariable Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    @PreAuthorize("hasAnyAuthority('admin.edit')")
    public String uploadShow(@PathVariable Long id , Model model){

        model.addAttribute("id",id);
        return PAGE_UPLOAD;
    }
    @GetMapping("/updateShow/{id}")
    public String updateShow(@PathVariable Long id , Model model){

        model.addAttribute("id",id);
        return PAGE_UPLOAD;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id , MultipartFile file ,Model model) throws IOException {
        String sufName = file.getOriginalFilename().split("\\.")[1];
        String fixName = UUID.randomUUID().toString().replace("-","");
        String fileName = fixName + "."+sufName;
        QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl("http://rh3t0txl3.hn-bkt.clouddn.com/"+fileName);
        adminService.update(admin);
        return successPage(model,"头像上传成功!");
    }


    /*
     * 跳转到 admin/assignShow.html
     * @param: id  adminId
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/assignShow/{id}")
    @PreAuthorize("hasAnyAuthority('admin.assign')")
    public String assignShow(@PathVariable Long id,Model model){
        model.addAttribute("adminId",id);
        //unAssignRoleList 未选择的角色
        //assignRoleList   已选择的角色
        Map<String , Object> map =  adminService.findRolesByAdminId(id);
        model.addAllAttributes(map);
        return PAGE_ASSIGNSHOW;
    }

    @PostMapping("/assignRole")
    @PreAuthorize("hasAnyAuthority('admin.assign')")
    public String assignRole(Long adminId ,@RequestParam("roleIds") List<Long> roleIds,Model model){
        roleService.saveAdminRole(adminId,roleIds);
        return successPage(model,"分配角色成功!");
    }
}
