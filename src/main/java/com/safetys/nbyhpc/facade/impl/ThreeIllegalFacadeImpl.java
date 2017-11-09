package com.safetys.nbyhpc.facade.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaThreeIllegal;
import com.safetys.nbyhpc.domain.persistence.iface.ThreeIllegalPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.ThreeIllegalFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class ThreeIllegalFacadeImpl implements ThreeIllegalFacadeIface {

	private ThreeIllegalPersistenceIface threeIllegalPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	public List<DaIndustryParameter> loadIndustryParameters(UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.like("dip.depiction", userDetail.getUserIndustry(), MatchMode.ANYWHERE));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.THREE_ILLEGAL_TRADE));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public void createThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		threeIllegalPersistenceIface.createThreeIllegal(threeIllegal);
	}

	public void deleteThreeIllegals(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			threeIllegalPersistenceIface.deleteThreeIllegal(new DaThreeIllegal(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		threeIllegalPersistenceIface.deleteThreeIllegal(threeIllegal);
	}
	
	public DaThreeIllegal loadThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		return threeIllegalPersistenceIface.loadThreeIllegal(threeIllegal);
	}
	public DaThreeIllegal loadThreeIllegales(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		return threeIllegalPersistenceIface.loadThreeIllegal(threeIllegal);
	}
	public List<DaThreeIllegal> loadThreeIllegals(DaThreeIllegal threeIllegal, Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaThreeIllegal.class, "dti");
		if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = (select third_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = (select second_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = (select first_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry = '"+userDetail.getUserIndustry()+"')"));
		
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dti.modifyTime"));
		return threeIllegalPersistenceIface.loadThreeIllegals(detachedCriteriaProxy, pagination);
	}

	public List<DaThreeIllegal> loadThreeIllegalForAreas(DaThreeIllegal threeIllegal, Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaThreeIllegal.class, "dti");
		if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = (select third_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = (select second_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = (select first_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry = '"+userDetail.getUserIndustry()+"')"));
		
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dti.modifyTime"));
		return threeIllegalPersistenceIface.loadThreeIllegals(detachedCriteriaProxy, pagination);
	}
	
	public void updateThreeIllegal(DaThreeIllegal threeIllegal) throws ApplicationAccessException {
		DaThreeIllegal oldThreeIllegal = loadThreeIllegal(threeIllegal);
		threeIllegal.setCreateTime(oldThreeIllegal.getCreateTime());
		threeIllegalPersistenceIface.mergeThreeIllegal(threeIllegal);
	}

	public void setThreeIllegalPersistenceIface(
			ThreeIllegalPersistenceIface threeIllegalPersistenceIface) {
		this.threeIllegalPersistenceIface = threeIllegalPersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}	


}
