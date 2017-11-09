package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.facade.iface.PointTypeFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#)			PointTypeAction.java
 * @date		2009-10-23
 * @author		lvx
 * @copyright	(c) 2009 Safetys.cn
 * All rights reserved.
 *
 */
public class PointTypeAction extends DaAppAction{

	/**
	 * 行业信息
	 */
	private static final long serialVersionUID = 1L;
	
	private PointTypeFacadeIface pointTypeFacadeIface;//行业信息的业务接口

	private List<DaPointType> pointTypes;//行业集合
	
	private DaPointType pointType;//行业
	
	private Pagination pagination;//分页对象
	
	private String ids;//行业序号的集合，针对类似于批量删除操作
	
	
	/**
	 * 加载创建行业信息的页面
	 * @return
	 */
	public String createPointTypeInit(){
		return SUCCESS;
	}
	
	/**
	 * 创建行业信息
	 * @return
	 */
	public String createPointType() {
		try {
			pointType.setUserId(getUserId());
			pointTypeFacadeIface.createPointType(pointType);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个行业信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadPointType(){
		try {
			pointType = pointTypeFacadeIface.loadPointType(pointType);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改行业信息
	 * @return
	 */
	public String updatePointType(){
		try {
			pointType.setUserId(getUserId());
			pointTypeFacadeIface.updatePointType(pointType);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除行业信息
	 * @return
	 */
	public String deletePointType(){
		try {
			pointTypeFacadeIface.deletePointType(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载行业信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadPointTypes(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			pointTypes = pointTypeFacadeIface.loadPointTypes(pointType,pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public void setPointTypeFacadeIface(
			PointTypeFacadeIface pointTypeFacadeIface) {
		this.pointTypeFacadeIface = pointTypeFacadeIface;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaPointType getPointType() {
		return pointType;
	}

	public void setPointType(DaPointType pointType) {
		this.pointType = pointType;
	}

	public List<DaPointType> getPointTypes() {
		return pointTypes;
	}
	
	public void setPointTypes(List<DaPointType> pointTypes) {
		this.pointTypes = pointTypes;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
