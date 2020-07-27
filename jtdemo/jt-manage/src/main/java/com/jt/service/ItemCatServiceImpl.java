package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.aop.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired(required = false)
    private JedisCluster jedisCluster;

//    @Autowired(required = false)
//    private Jedis jedis;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public ItemCat findItemCatById(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId);
    }

    @Override
    @CacheFind(key = "ITEM_CAT_LIST")
    public List<EasyUITree> findItemCatByParentId(Long parentId) {
        QueryWrapper<ItemCat> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<ItemCat> itemCatList = itemCatMapper.selectList(wrapper);
        List<EasyUITree> treeList = new ArrayList<>();
        for (ItemCat itemCat : itemCatList) {
            Long id = itemCat.getId();
            String text = itemCat.getName();
            String state = itemCat.getIsParent()?"closed":"open";
            EasyUITree uiTree = new EasyUITree(id, text, state);
            treeList.add(uiTree);
        }
        return treeList;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EasyUITree> findItemCatByCache(Long parentId) {

        String key = "ITEM_CAT_LIST::"+parentId;
        List<EasyUITree> treeList = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        if (jedisCluster.exists(key)) {
            String json = jedisCluster.get(key);
            Long endTime = System.currentTimeMillis();
            treeList = ObjectMapperUtil.toObject(json, treeList.getClass());
            System.out.println("Redis查询缓存的时间为："+(endTime - startTime));
        } else {
            treeList = findItemCatByParentId(parentId);
            Long endTime = System.currentTimeMillis();
            String json = ObjectMapperUtil.toJSON(treeList);
            jedisCluster.set(key, json);
            System.out.println("查询数据库时间为："+(endTime - startTime)+"毫秒");
        }

        return treeList;
    }
}
