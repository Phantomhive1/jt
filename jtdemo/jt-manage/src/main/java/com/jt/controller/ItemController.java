package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyTableUI;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jt.service.ItemService;

import javax.security.sasl.SaslServer;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/query")
	public EasyTableUI findItemByPage(Integer page, Integer rows) {
		return itemService.findItemByPage(page, rows);
	}

	@RequestMapping("/save")
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.saveItem(item, itemDesc);
			return SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	@RequestMapping("/update")
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		itemService.updateItem(item, itemDesc);
		return SysResult.success();
	}

	@RequestMapping("/delete")
	public SysResult deleteByIds(@RequestParam Long[] ids) {
		itemService.deleteByIds(ids);
		return SysResult.success();
	}

	@RequestMapping("/{status}")
	public SysResult updateStatus(@PathVariable("status") Integer status, Long[] ids) {
		itemService.updateStatus(status, ids);
		return SysResult.success();
	}

	@RequestMapping("/item/query/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable("itemId") Long itemId) {
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		return SysResult.success();
	}

	
	
}
