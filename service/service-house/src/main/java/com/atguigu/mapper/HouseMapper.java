package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper extends BaseMapper<House> {
    int updateDefaultImageUrl(@Param("id") Long id, @Param("defaultImageUrl") String defaultImageUrl);

    List<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
