package com.atguigu.controller;

import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 19:06 2022/8/24
 **/
@Controller
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private UserFollowService userFollowService;

    /*
     * 房源信息分页
     * @param: pageNum  当前页数
     * @Param: pageSize  每页记录数
     * @Param: houseQueryBo 搜索条件
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum , @PathVariable Integer pageSize , @RequestBody HouseQueryBo houseQueryBo){

        PageInfo<HouseVo> page = houseService.findListPage(pageNum,pageSize,houseQueryBo);
        return Result.ok(page);
    }

    /*
     * 加载房屋详情页面
     * @param: id 房源id
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id , HttpSession session){

        //房源信息 house
        House house = houseService.getById(id);

        //小区信息 community
        Community community = communityService.getById(house.getCommunityId());

        //经纪人信息 houseBrokerList
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);

        //房屋图片列表 houseImage1List
        List<HouseImage> houseImage1List = houseImageService.findHouseImageListByHouseIdAndType(id, 1);

        //是否关注  isFollow
        UserInfo userInfo = (UserInfo) session.getAttribute("existUser");
        Boolean isFollow = false;
        if(userInfo != null){
            UserFollow userFollow = userFollowService.getByUserIdAndHouseId(userInfo.getId(), id);
            isFollow = userFollow != null ;
        }

        Map<String ,Object> map = new HashMap<>();
        map.put("isFollow",isFollow);
        map.put("community",community);
        map.put("house",house);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        return Result.ok(map);
    }
}
