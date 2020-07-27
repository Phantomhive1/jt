package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyTableUI;

public interface ItemService {

    EasyTableUI findItemByPage(Integer page, Integer rows);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item, ItemDesc itemDesc);

    void deleteByIds(Long[] ids);

    void updateStatus(Integer status, Long[] ids);

    ItemDesc findItemDescById(Long itemId);
}
