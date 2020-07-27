package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {
    ItemCat findItemCatById(Long itemCatId);

    List<EasyUITree> findItemCatByParentId(Long parentId);

    List<EasyUITree> findItemCatByCache(Long parentId);

}
