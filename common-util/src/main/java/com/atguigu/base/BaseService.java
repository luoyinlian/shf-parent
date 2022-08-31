package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {
    List<T> findAll();

    int insert(T t);

    PageInfo<T> findPage(Map filters);

    T getById(Long id);

    int update(T t);

    int delete(Long id);
}
