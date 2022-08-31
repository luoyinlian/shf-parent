package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author luoyin
 * @Date 12:59 2022/8/23
 **/
@Service
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    HouseMapper houseMapper;
    @Override
    public BaseMapper<House> getBaseMapper() {
        return houseMapper;
    }

    /*
     * 修改房源状态
     * @param: id  要修改的id
     * @Param: status  目标状态
     * @return:int
     **/
    @Override
    public int publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        int update = houseMapper.update(house);
        System.out.println("update = " + update);
        return update;
    }

    @Override
    public int updateDefaultImageUrl(Long id, String defaultImageUrl) {
        return houseMapper.updateDefaultImageUrl(id,defaultImageUrl);
    }

    @Override
    public PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo) {
        PageHelper.startPage(pageNum,pageSize);
        List<HouseVo> pageList = houseMapper.findListPage(houseQueryBo);
        return new PageInfo<>(pageList);
    }
}
