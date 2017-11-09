package com.safetys.nbyhpc.web.action;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;
import com.safetys.nbyhpc.facade.iface.ItemFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) ItemAction.java
 * @date 2009-08-11
 * @author zhangxu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ItemAction extends DaAppAction{

	/**
	 * 工程项目
	 */
	private static final long serialVersionUID = -737085950683842251L;
	
	private ItemFacadeIface itemFacadeIface;
	
	private DaItem item;
	
	private List<DaItem> items;
	
	private Pagination pagination;
	
	private String ids;
	
	private String year;//季报用
	
	private String jd;
	
	private String flag;//区别用户部门（建委，交通，水利，市政）
	
	private String fg;//如果是管理员用这个区别这个部门。
	
	public String updateItem(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				item.setType(Nbyhpc.CONSTRUCTION_PROJECT);
			}else{
				if("jianwei".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.CONSTRUCTION_PROJECT);
				}else if("jiaotong".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.TRAFFIC_PROJECT);
				}else if("shuili".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.WATER_PROJECT);
				}else if("chengguan".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.CITY_PROJECT);
				}
			}
			item.setUserId(getUserId());
			itemFacadeIface.updateItem(item);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadItem(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			item=itemFacadeIface.loadItem(item);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载已整治隐患
	 * @return
	 */
	public String loadGoverItems(){
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
			items=itemFacadeIface.loadGoverItems(item,pagination,flag,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载未整治隐患
	 * @return
	 */
	public String loadUnGoverItems(){
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
			items=itemFacadeIface.loadUnGoverItems(item,pagination,flag,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载工程项目
	 * @return
	 */
	public String loadItems(){
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
			
			if(year==null || "".equals(year)){
				Calendar cd=Calendar.getInstance();
				year=cd.get(Calendar.YEAR)+"";
			}else{
				year=year+"";
			}
			
			Calendar cd=Calendar.getInstance();
			if(cd.get(Calendar.MONTH)>-1 && cd.get(Calendar.MONTH)<3){//一季度
				jd = "1";
			}else if(cd.get(Calendar.MONTH)>2 && cd.get(Calendar.MONTH)<6){
				jd = "2";
			}else if(cd.get(Calendar.MONTH)>5 && cd.get(Calendar.MONTH)<9){
				jd = "3";
			}else{
				jd = "4";
			}
			
			if(year==null || "".equals(year)){
				year=cd.get(Calendar.YEAR)+"";
			}else{
				year=year+"";
			}
			items=itemFacadeIface.loadItems(item,pagination,year,flag,getUserDetail());
			
			for(DaItem i : items){
//				System.out.println("i.getDaItemSeasonReports(): "+i.getDaItemSeasonReports());
				Set<DaItemSeasonReport> set = i.getDaItemSeasonReports();
				for(DaItemSeasonReport s : set){
//					System.out.println("s.getType():"+s.getType());
					if(null!=s.getType() && s.getType().length()>=4){
						String tyear = s.getType().substring(0, 4);
						if(year.equals(tyear) && s.getType().length()>=5){
							
							String sp = s.getType().substring(4, 5);
//							System.out.println("sp: "+sp);
							if(sp.equals("1")){
								i.setSp1(true);
								i.setSp1_id(s.getId());
							}else if(sp.equals("2")){
								i.setSp2(true);
								i.setSp2_id(s.getId());
							}else if(sp.equals("3")){
								i.setSp3(true);
								i.setSp3_id(s.getId());
							}else if(sp.equals("4")){
								i.setSp4(true);
								i.setSp4_id(s.getId());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化工程项目
	 * @return
	 */
	public String createItemInit(){
		if(getUserDetail().getUsername().trim().equals("admin")){
			flag="jianwei";
		}else{
			flag=getUserDetail().getUserIndustry().trim();
		}
		return SUCCESS;
	}
	
	/**
	 * 创建工程项目
	 * @return
	 */
	public String createItem(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				item.setType(Nbyhpc.CONSTRUCTION_PROJECT);
			}else{
				if("jianwei".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.CONSTRUCTION_PROJECT);
				}else if("jiaotong".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.TRAFFIC_PROJECT);
				}else if("shuili".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.WATER_PROJECT);
				}else if("chengguan".equals(getUserDetail().getUserIndustry().trim())){
					item.setType(Nbyhpc.CITY_PROJECT);
				}
			}
			item.setUserId(getUserId());
			itemFacadeIface.createItem(item);
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

	public DaItem getItem() {
		return item;
	}

	public void setItem(DaItem item) {
		this.item = item;
	}

	public ItemFacadeIface getItemFacadeIface() {
		return itemFacadeIface;
	}

	public void setItemFacadeIface(ItemFacadeIface itemFacadeIface) {
		this.itemFacadeIface = itemFacadeIface;
	}

	public List<DaItem> getItems() {
		return items;
	}

	public void setItems(List<DaItem> items) {
		this.items = items;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFg() {
		return fg;
	}

	public void setFg(String fg) {
		this.fg = fg;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}
	
	
}
