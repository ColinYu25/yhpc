package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaSeasonReportOther;
import com.safetys.nbyhpc.facade.iface.SeasonReportOtherFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#) SeasonReportOtherAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class SeasonReportOtherAction extends DaAppAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8375845644337363743L;

	private SeasonReportOtherFacadeIface seasonReportOtherFacadeIface;//打非信息的业务接口
	
	private List seasonReportOthers;//打非集合
	
	private List<DaIndustryParameter> daIndustryParameters;
	
	private DaSeasonReportOther seasonReportOther;//打非
	
	private Pagination pagination;//分页对象
	
	private String ids;//打非序号的集合，针对类似于批量删除操作
	
	private DaCompany company;
	
	private List statistics;
	
	public List getStatistics() {
		return statistics;
	}
	public void setStatistics(List statistics) {
		this.statistics = statistics;
	}
	public DaCompany getCompany() {
		return company;
	}
	public void setCompany(DaCompany company) {
		this.company = company;
	}
	/**
	 * 加载一个打非信息的内容
	 * @return
	 */
	public String createSeasonReportOtherInit(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			daIndustryParameters = seasonReportOtherFacadeIface.loadIndustryParameters(getUserDetail());
			seasonReportOthers = seasonReportOtherFacadeIface.loadSeasonReportOthers(seasonReportOther, pagination,getUserDetail(),company);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 创建打非信息
	 * @return
	 */
	public String createSeasonReportOther(){
		try {
			seasonReportOther.setUserId(getUserId());
			seasonReportOther.setFirstArea(getUserDetail().getFirstArea());
			seasonReportOther.setSecondArea(getUserDetail().getSecondArea());
			seasonReportOther.setThirdArea(getUserDetail().getThirdArea());
			seasonReportOtherFacadeIface.createSeasonReportOther(seasonReportOther);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个打非信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadSeasonReportOther(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			daIndustryParameters = seasonReportOtherFacadeIface.loadIndustryParameters(getUserDetail());
			seasonReportOther = seasonReportOtherFacadeIface.loadSeasonReportOther(seasonReportOther);
			seasonReportOthers = seasonReportOtherFacadeIface.loadSeasonReportOthers(seasonReportOther, pagination,getUserDetail(),null);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改打非信息
	 * @return
	 */
	public String updateSeasonReportOther(){
		try {
			seasonReportOther.setUserId(getUserId());
			seasonReportOtherFacadeIface.updateSeasonReportOther(seasonReportOther);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除打非信息
	 * @return
	 */
	public String deleteSeasonReportOther(){
		try {
			seasonReportOtherFacadeIface.deleteSeasonReportOther(seasonReportOther);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 隐患列表
	 * @return
	 */
	public String loadSeasonReportOtherList(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			daIndustryParameters = seasonReportOtherFacadeIface.loadIndustryParameters(getUserDetail());
			seasonReportOthers = seasonReportOtherFacadeIface.loadSeasonReportOtherList(seasonReportOther, pagination, getUserDetail(),company);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 隐患统计
	 * @return
	 */
	public String loadSeasonReportOtherStatistic(){
		try{
			statistics=seasonReportOtherFacadeIface.loadSeasonReportOtherStatistic(getUserDetail());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 隐患区域统计
	 * @return
	 */
	public String loadSeasonReportOtherAreaStatistic(){
		try{
			statistics=seasonReportOtherFacadeIface.loadSeasonReportOtherAreaStatistic(getUserDetail());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 加载打非信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadSeasonReportOthers(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			seasonReportOthers = seasonReportOtherFacadeIface.loadSeasonReportOthers(seasonReportOther, pagination,getUserDetail(),null);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 矿山等报表
	 * @return
	 */
	public String loadHiddenGovernanceReportStatistic(){
		try{
			if(seasonReportOther == null){
				seasonReportOther = new DaSeasonReportOther();
				Calendar cal = Calendar.getInstance();
				seasonReportOther.setNowYear(cal.get(Calendar.YEAR));
			}
			seasonReportOthers=seasonReportOtherFacadeIface.loadHiddenGovernanceReportStatistic(seasonReportOther);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 其他重点行业等报表
	 * */
	public String loadHiddenKeyIndustriesReportStatistic(){
		try{
			if(seasonReportOther == null){
				seasonReportOther = new DaSeasonReportOther();
				Calendar cal = Calendar.getInstance();
				seasonReportOther.setNowYear(cal.get(Calendar.YEAR));
			}
			seasonReportOthers=seasonReportOtherFacadeIface.loadHiddenKeyIndustriesReportStatistic(seasonReportOther);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public void setSeasonReportOtherFacadeIface(
			SeasonReportOtherFacadeIface seasonReportOtherFacadeIface) {
		this.seasonReportOtherFacadeIface = seasonReportOtherFacadeIface;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaSeasonReportOther getSeasonReportOther() {
		return seasonReportOther;
	}

	public void setSeasonReportOther(DaSeasonReportOther seasonReportOther) {
		this.seasonReportOther = seasonReportOther;
	}

	@SuppressWarnings("unchecked")
	public List<DaSeasonReportOther> getSeasonReportOthers() {
		return seasonReportOthers;
	}
	
	public void setSeasonReportOthers(List<DaSeasonReportOther> seasonReportOthers) {
		this.seasonReportOthers = seasonReportOthers;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<DaIndustryParameter> getDaIndustryParameters() {
		return daIndustryParameters;
	}
	public void setDaIndustryParameters(
			List<DaIndustryParameter> daIndustryParameters) {
		this.daIndustryParameters = daIndustryParameters;
	}

}
