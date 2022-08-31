package com.atguigu.base;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    /*
     * 查所有
     **/
    List<T> findAll();

    /*
     * 添加
     **/
    int insert(T t);

    /*
     * 分页查询
     **/
    List<T> findPage(Map filters);

    /*
     * 根据id查询
     **/
    T getById(Long id);

    /*
     * 修改
     **/
    int update(T t);

    /*
     * 删除
     **/
    int delete(Long id);
}
