package com.atguigu.controller;

import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author luoyin
 * @Date 18:54 2022/8/24
 **/
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    DictService dictService;

    /*
     * 根据 dictCode 查询 dict 列表
     * @param: dictCode
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/findDictListByParentDictCode/{dictCode}")
    public Result findDictListByParentDictCode(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findDictListByParentDictCode(dictCode);
        return Result.ok(dictList);
    }

    /*
     * 根据区域id查询板块
     * @param: id  区域id
     * @return:com.atguigu.result.Result
     **/
    @ResponseBody
    @GetMapping("/findDictListByParentId/{id}")
    public Result findDictListByParentId(@PathVariable Long id){
        List<Dict> dictList = dictService.findDictListByParentId(id);
        return Result.ok(dictList);
    }
}
