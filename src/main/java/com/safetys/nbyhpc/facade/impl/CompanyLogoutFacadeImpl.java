package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyLogout;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyLogoutPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyLogoutFacadeIface;

public class CompanyLogoutFacadeImpl implements CompanyLogoutFacadeIface{

	private CompanyLogoutPersistenceIface companyLogoutPersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;

	public List<DaCompanyLogout> loadCompanyLogouts(DaCompanyLogout companyLogout, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
		.forClass(DaCompanyLogout.class, "dcl");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dcl.modifyTime"));
		return companyLogoutPersistenceIface.loadCompanyLogouts(detachedCriteriaProxy, pagination);
	}

//	public List<DaCompany> loadUnCheckedCompanies(DaCompany daCompany,Pagination pagination) throws ApplicationAccessException{
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
//		.forClass(DaCompany.class, "dc");
//		List<DaCompany> list=new ArrayList<DaCompany>();
//		List<Long> ids=loadCompanyLogoutIds("1");
//		StringBuffer str=new StringBuffer();
//		for(int i=0;i<ids.size();i++){
//			str.append(ids.get(i)+",");
//		}
//		try {
//			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("id not in ("+str.substring(0, str.length()-1)+")"));
//			detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.modifyTime"));
//			list=companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	public List<Long> loadCompanyLogoutIds(String type) throws ApplicationAccessException {
		ResultSet rs=null;
		List<Long> ids=new ArrayList<Long>();
		String sql="select PAR_DA_COM_ID from DA_COMPANY_LOGOUT where IS_DELETED=0 ";
		if(type.equals("2")){
			sql=sql+"and type=2";
		}
		if(type.equals("1")){
			sql=sql+"and type=1";
		}
		rs=companyLogoutPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				ids.add(rs.getLong("PAR_DA_COM_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public Long createCompanyLogout(String ids,String type,Long userId) throws ApplicationAccessException {
		Long lg=null;
		try {
			for(int i=0;i<ids.split(",").length;i++){
				DaCompanyLogout companyLogout=new DaCompanyLogout();
				companyLogout.setDaCompany(new DaCompany(Long.parseLong(ids.split(",")[i])));
				companyLogout.setType(new Integer(type));
				companyLogout.setUserId(userId);
				lg=companyLogoutPersistenceIface.createCompanyLogout(companyLogout);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lg;
	}

	public void deleteCompanyLogout(String ids) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			companyLogoutPersistenceIface.deleteCompanyLogout(new DaCompanyLogout(
					Long.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaCompanyLogout loadCompanyLogout(DaCompanyLogout companyLogout) throws ApplicationAccessException {
		return companyLogoutPersistenceIface.loadCompanyLogout(companyLogout);
	}

	public List<DaCompany> loadCompanies(DaCompany company,List<Long> ids, Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy
			.forClass(DaCompany.class, "dc");
			if(ids.size()==0){
				return null;
			}else{
				if (company != null) {
					if (company.getCompanyName() != null
							&& !"".equals(company.getCompanyName().trim())) {
						detachedCriteriaProxy.add(RestrictionsProxy.like(
								"dc.companyName", company.getCompanyName().trim(),
								MatchMode.ANYWHERE));
					}
					if (company.getRegAddress() != null
							&& !"".equals(company.getRegAddress().trim())) {
						detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",
								company.getRegAddress().trim(), MatchMode.ANYWHERE));
					}
					if (company.getFddelegate() != null
							&& !"".equals(company.getFddelegate().trim())) {
						detachedCriteriaProxy.add(RestrictionsProxy.like(
								"dc.fdDelegate", company.getFddelegate().trim(),
								MatchMode.ANYWHERE));
					}
					if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
								company.getFirstArea()));
						if (company.getSecondArea() != null
								&& company.getSecondArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq(
									"dc.secondArea", company.getSecondArea()));
							if (company.getThirdArea() != null
									&& company.getThirdArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq(
										"dc.thirdArea", company.getThirdArea()));
								if (company.getFouthArea() != null
										&& company.getFouthArea() != 0L) {
									detachedCriteriaProxy.add(RestrictionsProxy.eq(
											"dc.fouthArea", company.getFouthArea()));
									if (company.getFifthArea() != null
											&& company.getFifthArea() != 0L) {
										detachedCriteriaProxy
												.add(RestrictionsProxy.eq(
														"dc.fifthArea", company
																.getFifthArea()));
									}
								}
							}
						}
					}
				}
				if (userDetail.getFifthArea() != null
						&& !userDetail.getFifthArea().equals(0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",
							userDetail.getFifthArea()));
				} else if (userDetail.getFouthArea() != null
						&& !userDetail.getFouthArea().equals(0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",
							userDetail.getFouthArea()));
				} else if (userDetail.getThirdArea() != null
						&& !userDetail.getThirdArea().equals(0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",
							userDetail.getThirdArea()));
				} else if (userDetail.getSecondArea() != null
						&& !userDetail.getSecondArea().equals(0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",
							userDetail.getSecondArea()));
				} else if (userDetail.getFirstArea() != null
						&& !userDetail.getFirstArea().equals(0L)) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",
							userDetail.getFirstArea()));
				} else {

				}
				detachedCriteriaProxy.add(RestrictionsProxy.in("dc.id", ids));
				detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
				return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
			}
	}
	
	public void updateOff(String ids,String type,Long userId)throws ApplicationAccessException{
		for(int i=0;i<ids.split(",").length;i++){
			//注销
			if("true".equals(type)){
				DaCompanyLogout daCompanyLogout = new DaCompanyLogout();
				daCompanyLogout.setDaCompany(new DaCompany(Long.parseLong(ids.split(",")[i])));
				daCompanyLogout.setType(new Integer(1));
				daCompanyLogout.setUserId(userId);
				companyLogoutPersistenceIface.createCompanyLogout(daCompanyLogout);
			}
			//取消注销
			if("false".equals(type)){
				String sql="delete DaCompanyLogout where par_da_com_id="+ids.split(",")[i];
				companyLogoutPersistenceIface.updateBySql(sql);
			}
		}
	}

	public void updateCompanyLogout(DaCompanyLogout companyLogout) throws ApplicationAccessException {
		companyLogoutPersistenceIface.updateCompanyLogout(companyLogout);
	}

	public CompanyLogoutPersistenceIface getCompanyLogoutPersistenceIface() {
		return companyLogoutPersistenceIface;
	}

	public void setCompanyLogoutPersistenceIface(
			CompanyLogoutPersistenceIface companyLogoutPersistenceIface) {
		this.companyLogoutPersistenceIface = companyLogoutPersistenceIface;
	}

	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

}
