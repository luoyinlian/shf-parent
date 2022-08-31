package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 20:32 2022/8/18
 **/
public abstract class BaseServiceImpl<T> {

    public abstract BaseMapper<T> getBaseMapper();
    public List<T> findAll() {
        return getBaseMapper().findAll();
    }


    public int insert(T t) {
        return getBaseMapper().insert(t);
    }


    public PageInfo<T> findPage(Map filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"),5);
        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<T>(getBaseMapper().findPage(filters));
    }

    public T getById(Long id) {
        return getBaseMapper().getById(id);
    }

    public int update(T t) {
        return getBaseMapper().update(t);
    }

    public int delete(Long id) {
        return getBaseMapper().delete(id);
    }
}
