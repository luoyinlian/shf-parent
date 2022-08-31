package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 23:22 2022/8/22
 **/
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";
    @Reference
    CommunityService communityService;
    @Reference
    DictService dictService;

    /*
     * 通过父id查询区域信息列表,并存进请求域中
     * @param: model
     * @return:void
     **/
    private void getAreaListByParentId(Model model) {
        List<Dict> areaList = dictService.findDictListByParentId(110000l);
        model.addAttribute("areaList",areaList);
    }
    /*
     * 跳转到community/index.html页面
     * @param: filters areaId,plateId,pagesize,pagenum
     * @Param: model
     * @return:java.lang.String
     **/
    @RequestMapping
    @PreAuthorize("hasAnyAuthority('community.show')")
    public String index(@RequestParam Map filters, Model model){
        if(!filters.containsKey("areaId")){
            filters.put("areaId","");
        }
        if(!filters.containsKey("plateId")){
            filters.put("plateId","");
        }
        model.addAttribute("filters",filters);
        PageInfo<Community> page = communityService.findPage(filters);
        model.addAttribute("page",page);
        getAreaListByParentId(model);
        return PAGE_INDEX;
    }


    /*
     * 跳转到community/create.html页面
     * @return:java.lang.String
     **/
    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('community.create')")
    public String create(Model model){
        getAreaListByParentId(model);
        return PAGE_CREATE;
    }

    /*
     * 添加小区信息
     * @param: community
     * @Param: model
     * @return:java.lang.String
     **/
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('community.create')")
    public String save(Community community,Model model){
        communityService.insert(community);
        return successPage(model,"添加小区信息成功!");
    }

    /*
     * 跳到 community/edit.html页面
     * @return:java.lang.String
     **/
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('community.edit')")
    public String edit(@PathVariable Long id ,Model model){
        //回显
        getAreaListByParentId(model);
        Community community = communityService.getById(id);
        model.addAttribute("community",community);
        return PAGE_EDIT;
    }

    /*
     * 修改小区信息
     * @param: community
     * @Param: model
     * @return:java.lang.String
     **/
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('community.edit')")
    public String update(Community community,Model model){
        communityService.update(community);
        return successPage(model,"修改小区信息成功!");
    }

    /*
     * 删除小区信息,重定向小区页面
     * @param: id
     * @return:java.lang.String
     **/
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('community.delete')")
    public String delete(@PathVariable Long id){
        communityService.delete(id);
        return LIST_ACTION;
    }

}
