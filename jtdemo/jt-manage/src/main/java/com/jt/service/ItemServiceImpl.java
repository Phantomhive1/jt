package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyTableUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;


	@Override
	public EasyTableUI findItemByPage(Integer page, Integer rows) {
		int total = itemMapper.selectCount(null);
		int start = (page - 1) * rows;
		List<Item> itemList = itemMapper.selectItemByPage(start, rows);
		return new EasyTableUI(total, itemList);
	}

	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1)
				.setCreated(new Date())
				.setUpdated(item.getCreated());
		itemMapper.insert(item);

		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);

		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public void deleteByIds(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}

	@Override
	public void updateStatus(Integer status, Long[] ids) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());

		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}
}
