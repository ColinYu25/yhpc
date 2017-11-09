package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemSeasonReport;
import com.safetys.nbyhpc.facade.iface.ItemSeasonReportFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class ItemSeasonReportAction extends DaAppAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4060166177560012209L;
	
	private ItemSeasonReportFacadeIface itemSeasonReportFacadeIface;
	
	private DaItemSeasonReport itemSeasonReport;
	
	private List<DaItemSeasonReport> itemSeasonReports;
	
	private Pagination pagination;
	
	private String ids;
	
	private DaItem item;
	
	private String type;
	
	private String flag;//区别用户部门（建委，交通，水利）
	
	public String updateItemSeasonReport(){
		try {
			itemSeasonReport.setUserId(getUserId());
			itemSeasonReportFacadeIface.updateItemSeasonReport(itemSeasonReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public String createItemSeasonReportInit(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			if(itemSeasonReport!=null){
				itemSeasonReport=itemSeasonReportFacadeIface.loadItemSeasonReport(itemSeasonReport);
			}
			item=itemSeasonReportFacadeIface.loadItem(item);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadItemSeasonReport(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			itemSeasonReport=itemSeasonReportFacadeIface.loadItemSeasonReport(itemSeasonReport);
			item=itemSeasonReport.getDaItem();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return 	SUCCESS;
	}
	
	public String createItemSeasonReport(){
		try {
			if(getUserDetail().getUsername().trim().equals("admin")){
				flag="jianwei";
			}else{
				flag=getUserDetail().getUserIndustry().trim();
			}
			Calendar de=Calendar.getInstance();
			itemSeasonReport.setType(de.get(Calendar.YEAR)+type);
			itemSeasonReport.setUserId(getUserId());
			itemSeasonReportFacadeIface.createItemSeasonReport(itemSeasonReport);
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

	public DaItemSeasonReport getItemSeasonReport() {
		return itemSeasonReport;
	}

	public void setItemSeasonReport(DaItemSeasonReport itemSeasonReport) {
		this.itemSeasonReport = itemSeasonReport;
	}

	public ItemSeasonReportFacadeIface getItemSeasonReportFacadeIface() {
		return itemSeasonReportFacadeIface;
	}

	public void setItemSeasonReportFacadeIface(
			ItemSeasonReportFacadeIface itemSeasonReportFacadeIface) {
		this.itemSeasonReportFacadeIface = itemSeasonReportFacadeIface;
	}

	public List<DaItemSeasonReport> getItemSeasonReports() {
		return itemSeasonReports;
	}

	public void setItemSeasonReports(List<DaItemSeasonReport> itemSeasonReports) {
		this.itemSeasonReports = itemSeasonReports;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public DaItem getItem() {
		return item;
	}

	public void setItem(DaItem item) {
		this.item = item;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
