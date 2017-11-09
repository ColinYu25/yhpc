package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerGorverPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DangerPersistenceIface;
import com.safetys.nbyhpc.facade.iface.DangerGorverFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class DangerGorverFacadeImpl implements DangerGorverFacadeIface {

	private DangerGorverPersistenceIface dangerGorverPersistenceIface;
	
	private DangerPersistenceIface dangerPersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	private Department department;

	public DaDanger loadDanger(DaDanger danger) throws ApplicationAccessException{
		return dangerPersistenceIface.loadDanger(danger);
	}
	public void createDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		dangerGorverPersistenceIface.createDangerGorver(dangerGorver);
	}
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	public void deleteDangerGorvers(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			dangerGorverPersistenceIface.deleteDangerGorver(new DaDangerGorver(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		dangerGorverPersistenceIface.deleteDangerGorver(dangerGorver);
	}
	
	public DaDangerGorver loadDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		return dangerGorverPersistenceIface.loadDangerGorver(dangerGorver);
	}
	
	public List<DaDangerGorver> loadDangerGorversOfOne(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDangerGorver.class, "ddg");
		if(dangerGorver != null) {
			if(dangerGorver.getDaDanger() != null){
				detachedCriteriaProxy.add(RestrictionsProxy.eq("ddg.daDanger.id", dangerGorver.getDaDanger().getId()));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return dangerGorverPersistenceIface.loadDangerGorvers(detachedCriteriaProxy, null);
	}
	

	public void updateDangerGorver(DaDangerGorver dangerGorver) throws ApplicationAccessException {
		DaDangerGorver oldDanger = loadDangerGorver(dangerGorver);
		dangerGorver.setCreateTime(oldDanger.getCreateTime());
		dangerGorverPersistenceIface.mergeDangerGorver(dangerGorver);
	}	

	public void setDangerGorverPersistenceIface(
			DangerGorverPersistenceIface dangerGorverPersistenceIface) {
		this.dangerGorverPersistenceIface = dangerGorverPersistenceIface;
	}

	public void setDangerPersistenceIface(
			DangerPersistenceIface dangerPersistenceIface) {
		this.dangerPersistenceIface = dangerPersistenceIface;
	}
	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}
	
	/*
	 * 获取隐患审核初始化获得部门行业
	 * author  maying
	 * 2014-01-15  
	 * 
	 */
	public Department getCountyLevel(FkUserInfo fkUserInfo) {
		String sql = "select county_level from department where code = '"+fkUserInfo.getUserIndustry()+"' ";
		try {
			ResultSet res = companyPersistenceIface.findBySql(sql);
			while(res.next()) {
				department = new Department();
				department.setCountyLevel(res.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}


}
