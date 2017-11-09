package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.facade.iface.RollcallCompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#) RollcallCompanyAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class RollcallCompanyAction extends DaAppAction{

	/**
	 * 挂牌信息
	 */
	private static final long serialVersionUID = 495423561252522818L;

	private RollcallCompanyFacadeIface rollcallCompanyFacadeIface;//挂牌信息的业务接口
	
	private List<DaRollcallCompany> rollcallCompanies;//挂牌集合
	
	private StatisticFacadeIface statisticFacadeIface;
	
	private List<FkEnum> fkEnums;
	
	private List<FkArea> areas;
	
	private List<DaCompany> companies;
	
	private List<DaIndustryParameter> tradeTypes;// 行业集合
	
	private FkEnum fkEnum;
	
	private FkArea area;
	
	private DaCompany company;
	
	private DaDanger danger;
	
	private DaRollcallCompany rollcallCompany;//挂牌
	
	private Pagination pagination;//分页对象
	
	private String ids;//挂牌序号的集合，针对类似于批量删除操作
	
	/**
	 * 加载一个挂牌信息的内容
	 * @return
	 */
	public String createRollcallCompanyInit(){
		try {
			fkEnums = rollcallCompanyFacadeIface.loadEnums(getUserDetail());
			UserDetailWrapper userDetail = getUserDetail();
			Long areaCode = 0l;
			if(userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0L) {
				areaCode = userDetail.getThirdArea();
			}else if(userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0L) {
				areaCode = userDetail.getSecondArea();
			}else if(userDetail.getFirstArea() != null && userDetail.getFirstArea() != 0L) {
				areaCode = userDetail.getFirstArea();
			}
			area = statisticFacadeIface.loadArea(areaCode);
			areas = rollcallCompanyFacadeIface.loadAreas(getUserDetail());
			rollcallCompanies = rollcallCompanyFacadeIface.loadRollcallCompanies(rollcallCompany.getDaDanger().getId());
			if (rollcallCompanies.size() > 0) {
				rollcallCompany = rollcallCompanies.get(0);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 创建挂牌信息
	 * @return
	 */
	public String createRollcallCompany(){
		try {
			rollcallCompany.setUserId(getUserId());
			rollcallCompany.setIsSynchro(1);
			rollcallCompanyFacadeIface.createRollcallCompany(rollcallCompany);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个挂牌信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadRollcallCompany(){
		try {
			fkEnums = rollcallCompanyFacadeIface.loadEnums(getUserDetail());
			areas = rollcallCompanyFacadeIface.loadAreas(getUserDetail());
			rollcallCompany = rollcallCompanyFacadeIface.loadRollcallCompany(rollcallCompany);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改挂牌信息
	 * @return
	 */
	public String updateRollcallCompany(){
		try {
			rollcallCompany.setUserId(getUserId());
			rollcallCompanyFacadeIface.updateRollcallCompany(rollcallCompany);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 取消挂牌
	 * @return
	 */
	public String deleteRollcallCompany() {
		try {
			rollcallCompanyFacadeIface.deleteRollcallCompany(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载挂牌信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadRollcallCompanies(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(rollcallCompany == null){
				rollcallCompany = new DaRollcallCompany();
			}
			if(rollcallCompany.getNowYear() == 0){
				Calendar cal = Calendar.getInstance();
				rollcallCompany.setNowYear(cal.get(Calendar.YEAR));
			}
			rollcallCompanies = rollcallCompanyFacadeIface.loadRollcallCompaniesOfLevel(rollcallCompany,company, pagination,getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallCompanyOfCompaniesByNotice(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = rollcallCompanyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = rollcallCompanyFacadeIface.loadCompanysByNotice(company,getUserDetail(),pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadRollcallCompanyForNotice(){
		try {
			rollcallCompany = rollcallCompanyFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallCompanyFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallCompanyFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String createRollcallCompanyForNoticeInit(){
		try {
			rollcallCompany = rollcallCompanyFacadeIface.loadRollcallCompany(rollcallCompany);
			danger = rollcallCompanyFacadeIface.loadDanger(new DaDanger(rollcallCompany.getDaDanger().getId()));
			company = rollcallCompanyFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String updateRollcallCompanyForNotice(){
		try {
			rollcallCompany.setNotice(true);
			rollcallCompanyFacadeIface.updateRollcallCompanyForNotice(rollcallCompany);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String loadRollcallCompaniesByCompany(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(company == null){
				company = new DaCompany();
				company.setId(rollcallCompanyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			rollcallCompanies = rollcallCompanyFacadeIface.loadRollcallCompaniesByCompany(company, pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public void setRollcallCompanyFacadeIface(
			RollcallCompanyFacadeIface rollcallCompanyFacadeIface) {
		this.rollcallCompanyFacadeIface = rollcallCompanyFacadeIface;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaRollcallCompany getRollcallCompany() {
		return rollcallCompany;
	}

	public void setRollcallCompany(DaRollcallCompany rollcallCompany) {
		this.rollcallCompany = rollcallCompany;
	}

	public List<DaRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}
	
	public void setRollcallCompanies(List<DaRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public FkArea getArea() {
		return area;
	}
	public void setArea(FkArea area) {
		this.area = area;
	}
	public List<FkArea> getAreas() {
		return areas;
	}
	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}
	public FkEnum getFkEnum() {
		return fkEnum;
	}
	public void setFkEnum(FkEnum fkEnum) {
		this.fkEnum = fkEnum;
	}
	public List<FkEnum> getFkEnums() {
		return fkEnums;
	}
	public void setFkEnums(List<FkEnum> fkEnums) {
		this.fkEnums = fkEnums;
	}
	public DaCompany getCompany() {
		return company;
	}
	public void setCompany(DaCompany company) {
		this.company = company;
	}
	public List<DaCompany> getCompanies() {
		return companies;
	}
	public void setCompanies(List<DaCompany> companies) {
		this.companies = companies;
	}
	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	public DaDanger getDanger() {
		return danger;
	}
	public void setDanger(DaDanger danger) {
		this.danger = danger;
	}
	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
	}

}
