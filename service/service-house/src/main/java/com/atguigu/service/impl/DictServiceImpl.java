package com.atguigu.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author luoyin
 * @Date 22:16 2022/8/22
 **/
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    DictMapper dictMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public BaseMapper<Dict> getBaseMapper() {
        return dictMapper;
    }

    @Override
    public List<Map<String, Object>> findZnodes(Long parentId) {
        List<Dict> dictList = findDictListByParentId(parentId);
        List<Map<String,Object>> znodes = new ArrayList<>();
        for (Dict dict : dictList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",dictMapper.findCountByParentId(dict.getId()) > 0);
            znodes.add(map);
        }
        return znodes;
    }

    /*
     * 通过父id查询dict信息
     * @param: parentId
     * @return:java.util.List<com.atguigu.entity.Dict>
     **/
    @Override
    public List<Dict> findDictListByParentId(long parentId) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get("shf:dict:parentId:" + parentId);
        if(value != null ){
            jedis.close();
            //List list = JSON.parseObject(value, List.class);
            return JSON.parseArray(value, Dict.class);
        }
        List<Dict> dictList = dictMapper.findListByParentId(parentId);
        if(dictList != null){
            jedis.set("shf:dict:parentId:" + parentId, JSON.toJSONString(dictList));
        }
        jedis.close();
        return dictList;
    }

    /*
     * 根据 dictCode 查询 dict 信息
     * @param: dictCode
     * @return:java.util.List<com.atguigu.entity.Dict>
     **/
    @Override
    public List<Dict> findDictListByParentDictCode(String dictCode) {
        Long id = dictMapper.getIdByDictCode(dictCode);
        return findDictListByParentId(id);
    }
}
