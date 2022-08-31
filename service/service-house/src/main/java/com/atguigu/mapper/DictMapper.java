package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @Author luoyin
 * @Date 22:00 2022/8/22
 **/
public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> findListByParentId(Long parentId);

    Long findCountByParentId(Long id);

    Long getIdByDictCode(String dictCode);
}
