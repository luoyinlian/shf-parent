package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @Date 12:35 2022/8/23
 **/
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";
    private static final String PAGE_UPLOAD = "house/upload2";

    @Reference
    DictService dictService;
    @Reference
    CommunityService communityService;
    @Reference
    HouseService houseService;
    @Reference
    HouseImageService houseImageService;
    @Reference
    HouseBrokerService houseBrokerService;
    @Reference
    HouseUserService houseUserService;
    /*
     * 根据dictCode获取列表数据,并存储在请求域中
     * @param: model
     * @return:void
     **/
    private void saveAllDictToRequestScope(Model model) {
        //小区列表信息 communityList
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList", communityList);

        //户型信息 houseTypeList
        List<Dict> houseTypeList = dictService.findDictListByParentDictCode("houseType");
        model.addAttribute("houseTypeList", houseTypeList);

        //楼层信息  floorList
        List<Dict> floorList = dictService.findDictListByParentDictCode("floor");
        model.addAttribute("floorList", floorList);

        //建筑结构信息  buildStructureList
        List<Dict> buildStructureList = dictService.findDictListByParentDictCode("buildStructure");
        model.addAttribute("buildStructureList", buildStructureList);

        //朝向  directionList
        List<Dict> directionList = dictService.findDictListByParentDictCode("direction");
        model.addAttribute("directionList", directionList);

        //装修情况  decorationList
        List<Dict> decorationList = dictService.findDictListByParentDictCode("decoration");
        model.addAttribute("decorationList", decorationList);

        //房屋用途  houseUseList
        List<Dict> houseUseList = dictService.findDictListByParentDictCode("houseUse");
        model.addAttribute("houseUseList", houseUseList);
    }

    /*
     * 跳转到house/index.html页面
     * @param: filters
     * @Param: model
     * @return:java.lang.String
     **/
    @RequestMapping
    @PreAuthorize("hasAnyAuthority('house.show')")
    public String index(@RequestParam Map filters, Model model) {
        //回显
        model.addAttribute("filters", filters);
        saveAllDictToRequestScope(model);

        //分页展示  page
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page", page);
        return PAGE_INDEX;
    }


    /*
     * 跳到 house/create页面
     * @return:java.lang.String
     **/
    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('house.create')")
    public String create(Model model) {
        saveAllDictToRequestScope(model);
        return PAGE_CREATE;
    }

    /*
     * 添加房源信息
     * @param: house
     * @Param: model
     * @return:java.lang.String
     **/
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('house.create')")
    public String save(House house, Model model) {
        houseService.insert(house);
        return successPage(model, "添加房源信息成功!");
    }

    /*
     * 跳转到 house/edit.html 页面
     * @param: id 房源id
     * @Param: model
     * @return:java.lang.String
     **/
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('house.edit')")
    public String edit(@PathVariable Long id, Model model) {
        saveAllDictToRequestScope(model);
        //回显
        House house = houseService.getById(id);
        model.addAttribute("house", house);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('house.edit')")
    public String update(House house, Model model) {
        houseService.update(house);
        return successPage(model, "修改房源信息成功!");
    }

    /*
     * 根据房源id删除房源信息
     * @param: id 房源id
     * @return:java.lang.String
     **/
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('house.delete')")
    public String delete(@PathVariable Long id) {
        houseService.delete(id);
        return LIST_ACTION;
    }

    /*
     * 修改房源状态  发布.未发布
     * @param: id 房源id
     * @Param: status 要修改的目标状态
     * @return:java.lang.String
     **/
    @GetMapping("/publish/{id}/{status}")
    @PreAuthorize("hasAnyAuthority('house.publish')")
    public String publish(@PathVariable Long id, @PathVariable Integer status) {
        houseService.publish(id, status);
        return LIST_ACTION;
    }

    /*
     * 跳转到house/show.html页面
     * 通过房源id查询详细信息
     * @param: id 房源id
     * @return:java.lang.String
     **/
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('house.show')")
    public String show(@PathVariable Long id,Model model){
        //房屋信息 house
        House house = houseService.getById(id);
        model.addAttribute("house",house);

        //小区信息 community
        Community community = communityService.getById(house.getCommunityId());
        model.addAttribute("community",community);

        //房源图片信息 houseImage1List
        List<HouseImage> houseImage1List = houseImageService.findHouseImageListByHouseIdAndType(id,1);
        model.addAttribute("houseImage1List",houseImage1List);

        //房产图片信息 houseImage2List
        List<HouseImage> houseImage2List = houseImageService.findHouseImageListByHouseIdAndType(id,2);
        model.addAttribute("houseImage2List",houseImage2List);

        //经纪人信息 houseBrokerList
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        model.addAttribute("houseBrokerList",houseBrokerList);

        //房东信息 houseUserList
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        model.addAttribute("houseUserList",houseUserList);
        return PAGE_SHOW;
    }

    @GetMapping("/uploadShow/{id}")
    @PreAuthorize("hasAnyAuthority('house.editImage')")
    public String uploadShow(@PathVariable Long id ,Model model){
        model.addAttribute("id",id);
        return PAGE_UPLOAD;
    }

    @PostMapping("/upload/{id}")
    @PreAuthorize("hasAnyAuthority('house.editImage')")
    public String upload(@PathVariable Long id , MultipartFile file ,Model model) throws IOException {
        String sufName = file.getOriginalFilename().split("\\.")[1];
        String fixName = UUID.randomUUID().toString().replace("-","");
        String fileName = fixName + "."+sufName;
        QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
        String defaultImageUrl = "http://rh3t0txl3.hn-bkt.clouddn.com/"+fileName;
        houseService.updateDefaultImageUrl(id,defaultImageUrl);
        return successPage(model,"上传头像成功!");
    }
}
