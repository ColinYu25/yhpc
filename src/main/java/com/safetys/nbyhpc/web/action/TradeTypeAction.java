package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.facade.iface.TradeTypeFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) TradeTypeAction.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class TradeTypeAction extends DaAppAction {

	/**
	 * 行业信息
	 */
	private static final long serialVersionUID = -7537374276276098212L;

	private TradeTypeFacadeIface tradeTypeFacadeIface;// 行业信息的业务接口

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	private DaIndustryParameter tradeType;// 行业

	private Pagination pagination;// 分页对象

	private String ids;// 行业序号的集合，针对类似于批量删除操作

	/**
	 * 加载创建行业信息的页面
	 * 
	 * @return
	 */
	public String createTradeTypeInit() {
		return SUCCESS;
	}

	/**
	 * 创建行业信息
	 * 
	 * @return
	 */
	public String createTradeType() {
		try {
			if (ids != null && !"".equals(ids)) {
				tradeType.setDaIndustryParameter(new DaIndustryParameter(Long
						.parseLong(ids)));
			} else {

			}
			tradeType.setUserId(getUserId());
			tradeType.setId(tradeTypeFacadeIface.createTradeType(tradeType));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 加载一个行业信息的内容，或用于显示或预备修改
	 * 
	 * @return
	 */
	public String loadTradeType() {
		try {
			tradeType = tradeTypeFacadeIface.loadTradeType(tradeType);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改行业信息
	 * 
	 * @return
	 */
	public String updateTradeType() {
		try {
			tradeType.setUserId(getUserId());
			tradeTypeFacadeIface.updateTradeType(tradeType);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 删除行业信息
	 * 
	 * @return
	 */
	public String deleteTradeType() {
		try {
			tradeTypeFacadeIface.deleteTradeType(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		setUrl(getContextPath() + "/redirectServlet");
		return SUCCESS;
	}

	/**
	 * 加载行业信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadTradeTypes() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(50);
		}
		try {
			tradeTypes = tradeTypeFacadeIface.loadTradeTypes(tradeType,
					getUserDetail(), pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void setTradeTypeFacadeIface(
			TradeTypeFacadeIface tradeTypeFacadeIface) {
		this.tradeTypeFacadeIface = tradeTypeFacadeIface;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaIndustryParameter getTradeType() {
		return tradeType;
	}

	public void setTradeType(DaIndustryParameter tradeType) {
		this.tradeType = tradeType;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
