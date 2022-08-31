package com.atguigu.controller;

import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 22:05 2022/8/22
 **/
@Controller
@RequestMapping("/dict")
public class DictController {

    private static final String PAGE_INDEX = "dict/index";

    @Reference
    DictService dictService;
    /*
     * 跳转到dict/index.html页面
     * @return:java.lang.String
     **/
    @RequestMapping
    @PreAuthorize("hasAnyAuthority('dict.show')")
    public String index(){
        return PAGE_INDEX;
    }

    /*
     * 根据parentId查询数据节点
     * @param: id
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/findZnodes")
    @PreAuthorize("hasAnyAuthority('dict.show')")
    public Result findZnodes(@RequestParam(defaultValue = "0") Long id){
        List<Map<String,Object>> znodes =  dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    /*
     * 根据区域id查询板块信息
     * @param: areaId
     * @return:com.atguigu.result.Result
     **/
    @PreAuthorize("hasAnyAuthority('dict.show')")
    @GetMapping("/findDictListByParentId/{areaId}")
    @ResponseBody
    public Result findDictListByParentId(@PathVariable Long areaId){
        return Result.ok(dictService.findDictListByParentId(areaId));
    }
}
