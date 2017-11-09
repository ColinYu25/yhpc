package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.List;

import cn.safetys.constant.SystemConstant;
import cn.safetys.sync.mq.SyncTriggerService;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.facade.iface.DangerGorverFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#) DangerGorverAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DangerGorverAction extends DaAppAction{

	/**
	 * 重大隐患整改信息
	 */
	private static final long serialVersionUID = 6806059411616329986L;
	
	private FkUser fkUser;//用户信息
	
	private FkUserInfo fkUserInfo;//企业具体信息
	
	private Department department;//行业部门
	
	private DangerGorverFacadeIface dangerGorverFacadeIface;//重大隐患整改信息的业务接口
	
	private DangerPersistenceIface dangerPersistenceIface;
	
	private List<DaDangerGorver> dangerGorvers;//重大隐患整改集合
	
	private DaDangerGorver dangerGorver;//重大隐患整改
	
	private DaDanger danger;
	
	private DaCompany company;
	
	private Pagination pagination;//分页对象
	
	private String ids;//重大隐患整改序号的集合，针对类似于批量删除操作
	
	private SyncTriggerService syncTriggerService;
	private SystemConstant systemConstant;
	
	/**
	 * 加载一个重大隐患整改信息的内容
	 * @return
	 */
	public String createDangerGorverInit(){
		try {
			danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			company = dangerGorverFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			dangerGorvers = dangerGorverFacadeIface.loadDangerGorversOfOne(dangerGorver);
		if (dangerGorvers.size() > 0) {
			dangerGorver = dangerGorvers.get(0);
		}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个重大隐患核查信息的内容
	 * 日期  2013-12-31
	 * author  maying
	 * @return
	 */
	public String checkDangerGorverInit(){
		try {
	
			danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			company = dangerGorverFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			dangerGorvers = dangerGorverFacadeIface.loadDangerGorversOfOne(dangerGorver);
			fkUserInfo = new FkUserInfo();
			fkUserInfo.setUserIndustry(getUserDetail().getUserIndustry());
			department = dangerGorverFacadeIface.getCountyLevel(fkUserInfo);
			
		if (dangerGorvers.size() > 0) {
			dangerGorver = dangerGorvers.get(0);
		}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 创建重大隐患整改信息
	 * @return
	 */
	public String createDangerGorver(){
		try {
			dangerGorver.setUserId(getUserId());
			if(danger==null){
				danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			}
			//liulj  更新同步状态
			danger.setIsSynchro(1);
			dangerGorver.setDaDanger(danger);
			
			dangerGorverFacadeIface.createDangerGorver(dangerGorver);
			
			if(systemConstant.isDataSeparation()&&dangerGorver.getId()!=null&&dangerGorver.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateDangerGorverEs(dangerGorver.getId());
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGorverGov(dangerGorver.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个重大隐患整改信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadDangerGorver(){
		try {
			dangerGorver = dangerGorverFacadeIface.loadDangerGorver(dangerGorver);
			danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			company = dangerGorverFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改重大隐患整改信息
	 * @return
	 */
	public String updateDangerGorver(){
		try {
			danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			
			//liulj  更新同步状态
			danger.setIsSynchro(1);
			dangerGorver.setDaDanger(danger);
			
			dangerGorver.setUserId(getUserId());
			dangerGorverFacadeIface.updateDangerGorver(dangerGorver);
			
			if(systemConstant.isDataSeparation()&&dangerGorver.getId()!=null&&dangerGorver.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateDangerGorverEs(dangerGorver.getId());
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGorverGov(dangerGorver.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 核查重大隐患整改信息
	 * 日期  2013-12-31
	 * author  maying
	 * @return
	 */
	public String updateCheckDangerGorver(){
		try {
			dangerGorver.setUserId(getUserId());
			dangerGorverFacadeIface.updateDangerGorver(dangerGorver);
			if(systemConstant.isDataSeparation()&&dangerGorver.getId()!=null&&dangerGorver.getId()>0){
				if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGorverGov(dangerGorver.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除重大隐患整改信息
	 * @return
	 */
	public String deleteDangerGorver(){
		try {
			dangerGorverFacadeIface.deleteDangerGorver(dangerGorver);
			if(systemConstant.isDataSeparation()&&dangerGorver.getId()!=null&&dangerGorver.getId()>0){
				if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGorverGov(dangerGorver.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public DaDanger getDanger() {
		return danger;
	}
	public void setDanger(DaDanger danger) {
		this.danger = danger;
	}
	public DaDangerGorver getDangerGorver() {
		return dangerGorver;
	}
	public void setDangerGorver(DaDangerGorver dangerGorver) {
		this.dangerGorver = dangerGorver;
	}
	public List<DaDangerGorver> getDangerGorvers() {
		return dangerGorvers;
	}
	public void setDangerGorvers(List<DaDangerGorver> dangerGorvers) {
		this.dangerGorvers = dangerGorvers;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public void setDangerGorverFacadeIface(
			DangerGorverFacadeIface dangerGorverFacadeIface) {
		this.dangerGorverFacadeIface = dangerGorverFacadeIface;
	}
	public DaCompany getCompany() {
		return company;
	}
	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public FkUser getFkUser() {
		return fkUser;
	}

	public void setFkUser(FkUser fkUser) {
		this.fkUser = fkUser;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public DangerPersistenceIface getDangerPersistenceIface() {
		return dangerPersistenceIface;
	}

	public void setDangerPersistenceIface(DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}

	public void setSyncTriggerService(SyncTriggerService syncTriggerService) {
		this.syncTriggerService = syncTriggerService;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}
	
	

}
