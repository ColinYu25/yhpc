package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.facade.iface.ItemDangerFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) ItemDangerAction.java
 * @date 2009-08-11
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ItemDangerAction extends DaAppAction{

	/**
	 * 工程项目重大隐患
	 */
	private static final long serialVersionUID = 507593860430222107L;
	
	private DaItemDanger itemDanger;
	
	private ItemDangerFacadeIface itemDangerFacadeIface;
	
	private List<DaItemDanger> itemDangers;
	
	private List<DaIndustryParameter> tradeTypes;
	
	private Pagination pagination;
	
	private String ids;
	
	private DaItem item;
	
	private String flag;//区别用户部门（建委，交通，水利）
	
	public String loadItemExpiredDangers(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			itemDangers=itemDangerFacadeIface.loadItemExpiredDangers(itemDanger, pagination, flag,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 删除重大隐患
	 * @return
	 */
	public String deleteItemDanger(){
		try {
			boolean flag=itemDangerFacadeIface.getItemDangerGover(ids);
			if(!flag){
				setMessageKey("item.danger.delete.notAllow");
				return MESSAGE;
			}else{
				itemDangerFacadeIface.deleteItemDanger(ids);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改工程项目重大隐患
	 * @return
	 */
	public String updateItemDanger(){
		try {
			itemDanger.setUserId(getUserId());
			itemDangerFacadeIface.updateItemDanger(itemDanger);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载工程项目重大隐患
	 * @return
	 */
	public String loadItemDanger(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			tradeTypes=itemDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			itemDanger=itemDangerFacadeIface.loadItemDanger(itemDanger);
			item=itemDanger.getDaItem();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载所有的工程项目隐患
	 * @return
	 */
	public String loadItemDangers(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			item=itemDangerFacadeIface.loadItem(item);
			itemDangers=itemDangerFacadeIface.loadItemDangers(itemDanger, pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String createItemDanger(){
		try {
			itemDanger.setGovern(new Integer(0));
			itemDanger.setUserId(getUserId());
			itemDangerFacadeIface.createItemDanger(itemDanger);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化工程项目隐患
	 * @return
	 */
	public String createItemDangerInit(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			tradeTypes=itemDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			item=itemDangerFacadeIface.loadItem(item);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public DaItem getItem() {
		return item;
	}

	public void setItem(DaItem item) {
		this.item = item;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public DaItemDanger getItemDanger() {
		return itemDanger;
	}

	public void setItemDanger(DaItemDanger itemDanger) {
		this.itemDanger = itemDanger;
	}

	public ItemDangerFacadeIface getItemDangerFacadeIface() {
		return itemDangerFacadeIface;
	}

	public void setItemDangerFacadeIface(ItemDangerFacadeIface itemDangerFacadeIface) {
		this.itemDangerFacadeIface = itemDangerFacadeIface;
	}

	public List<DaItemDanger> getItemDangers() {
		return itemDangers;
	}

	public void setItemDangers(List<DaItemDanger> itemDangers) {
		this.itemDangers = itemDangers;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
