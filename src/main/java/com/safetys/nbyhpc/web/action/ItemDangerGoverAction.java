package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaItemDangerGover;
import com.safetys.nbyhpc.facade.iface.ItemDangerGoverFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) ItemDangerGoverAction.java
 * @date 2009-08-12
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ItemDangerGoverAction extends DaAppAction{

	/**
	 * 工程项目隐患治理表
	 */
	private static final long serialVersionUID = 8173890034372152220L;

	private DaItemDangerGover itemDangerGover;
	
	private ItemDangerGoverFacadeIface itemDangerGoverFacadeIface;
	
	private List<DaItemDangerGover> itemDangerGovers;
	
	private Pagination pagination;
	
	private String ids;
	
	private DaItemDanger itemDanger;
	
	private DaItem item;
	
	private String flag;//区别用户部门（建委，交通，水利）
	
	/**
	 * 修改项目隐患整治
	 * @return
	 */
	public String updateItemDangerGover(){
		try {
			itemDangerGover.setUserId(getUserId());
			itemDangerGoverFacadeIface.updateItemDangerGover(itemDangerGover);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 删除隐患整治
	 * @return
	 */
	public String deleteItemDangerGover(){
		try {
			itemDangerGoverFacadeIface.deleteItemDangerGover(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 创建隐患整治
	 * @return
	 */
	public String createItemDangerGover(){
		try {
			itemDangerGover.setUserId(getUserId());
			itemDangerGoverFacadeIface.createItemDangerGover(itemDangerGover);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化隐患整治
	 * @return
	 */
	public String createItemDangerGoverInit(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			if(itemDangerGoverFacadeIface.getItemDangerGover(itemDanger.getId())){
				itemDangerGover=itemDangerGoverFacadeIface.loadItemDangerGover(itemDanger.getId());
			}
			itemDanger=itemDangerGoverFacadeIface.loadDaItemDanger(itemDanger);
			item=itemDanger.getDaItem();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public DaItemDangerGover getItemDangerGover() {
		return itemDangerGover;
	}

	public void setItemDangerGover(DaItemDangerGover itemDangerGover) {
		this.itemDangerGover = itemDangerGover;
	}

	public ItemDangerGoverFacadeIface getItemDangerGoverFacadeIface() {
		return itemDangerGoverFacadeIface;
	}

	public void setItemDangerGoverFacadeIface(
			ItemDangerGoverFacadeIface itemDangerGoverFacadeIface) {
		this.itemDangerGoverFacadeIface = itemDangerGoverFacadeIface;
	}

	public List<DaItemDangerGover> getItemDangerGovers() {
		return itemDangerGovers;
	}

	public void setItemDangerGovers(List<DaItemDangerGover> itemDangerGovers) {
		this.itemDangerGovers = itemDangerGovers;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaItemDanger getItemDanger() {
		return itemDanger;
	}

	public void setItemDanger(DaItemDanger itemDanger) {
		this.itemDanger = itemDanger;
	}
	
	public DaItem getItem() {
		return item;
	}

	public void setItem(DaItem item) {
		this.item = item;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
