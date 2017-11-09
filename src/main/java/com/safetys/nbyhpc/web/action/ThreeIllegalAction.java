package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaThreeIllegal;
import com.safetys.nbyhpc.facade.iface.ThreeIllegalFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#) ThreeIllegalAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class ThreeIllegalAction extends DaAppAction{

	/**
	 * 打非信息
	 */
	private static final long serialVersionUID = 3975779810690321614L;

	private ThreeIllegalFacadeIface threeIllegalFacadeIface;//打非信息的业务接口
	
	private List<DaThreeIllegal> threeIllegals;//打非集合
	
	private List<DaIndustryParameter> daIndustryParameters;
	
	private DaThreeIllegal threeIllegal;//打非
	
	private Pagination pagination;//分页对象
	
	private String ids;//打非序号的集合，针对类似于批量删除操作
	
	/**
	 * 加载一个打非信息的内容
	 * @return
	 */
	public String createThreeIllegalInit(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			daIndustryParameters = threeIllegalFacadeIface.loadIndustryParameters(getUserDetail());
			threeIllegals = threeIllegalFacadeIface.loadThreeIllegals(threeIllegal, pagination,getUserDetail());
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
	public String createThreeIllegal(){
		try {
			threeIllegal.setUserId(getUserId());
			threeIllegal.setFirstArea(getUserDetail().getFirstArea());
			threeIllegal.setSecondArea(getUserDetail().getSecondArea());
			threeIllegal.setThirdArea(getUserDetail().getThirdArea());
			threeIllegalFacadeIface.createThreeIllegal(threeIllegal);
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
	public String loadThreeIllegal(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			daIndustryParameters = threeIllegalFacadeIface.loadIndustryParameters(getUserDetail());
			threeIllegal = threeIllegalFacadeIface.loadThreeIllegal(threeIllegal);
			threeIllegals = threeIllegalFacadeIface.loadThreeIllegals(threeIllegal, pagination,getUserDetail());
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
	public String updateThreeIllegal(){
		try {
			threeIllegal.setUserId(getUserId());
			threeIllegal.setFirstArea(getUserDetail().getFirstArea());
			threeIllegal.setSecondArea(getUserDetail().getSecondArea());
			threeIllegal.setThirdArea(getUserDetail().getThirdArea());
			threeIllegalFacadeIface.updateThreeIllegal(threeIllegal);
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
	public String deleteThreeIllegal(){
		try {
			threeIllegalFacadeIface.deleteThreeIllegal(threeIllegal);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载打非信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadThreeIllegals(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			threeIllegals = threeIllegalFacadeIface.loadThreeIllegals(threeIllegal, pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String loadThreeIllegalStatistic(){
		
		return SUCCESS;
	}
	
	public void setThreeIllegalFacadeIface(
			ThreeIllegalFacadeIface threeIllegalFacadeIface) {
		this.threeIllegalFacadeIface = threeIllegalFacadeIface;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaThreeIllegal getThreeIllegal() {
		return threeIllegal;
	}

	public void setThreeIllegal(DaThreeIllegal threeIllegal) {
		this.threeIllegal = threeIllegal;
	}

	public List<DaThreeIllegal> getThreeIllegals() {
		return threeIllegals;
	}
	
	public void setThreeIllegals(List<DaThreeIllegal> threeIllegals) {
		this.threeIllegals = threeIllegals;
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
