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
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.persistence.iface.BagPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.facade.iface.BagFacadeIface;

public class BagFacadeImpl implements BagFacadeIface {

	private BagPersistenceIface bagPersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	public boolean isAllowBag(String ids) throws ApplicationAccessException{
		List<Long>  list = loadCompanyPassesIds();
		boolean flag=false;
		for(int i=0;i<list.size();i++){
			for(int j=0;j<ids.split(",").length;j++){
				if(Long.parseLong(ids.split(",")[j])==list.get(i)){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	private List<Long> loadCompanyPassesIds() throws ApplicationAccessException{
		List<Long>  list = new ArrayList<Long>();
		ResultSet rs=null;
		String sql=	"select PAR_DA_COM_ID from DA_COMPANY_PASS";
		rs=bagPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				list.add(rs.getLong("PAR_DA_COM_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public List<DaCompany> loadBagCompanies(DaCompany company,Pagination pagination,String bagId) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaCompany.class,"dc");
		if(company!=null){
			if (company.getCompanyName() != null
					&& !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"dc.companyName", company.getCompanyName().trim(),
						MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null
					&& !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.address",
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
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("id in (select par_da_com_id from da_bag_company_rel where is_deleted=0 and par_da_bag_id="+new Long(bagId)+")") );
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	/**
	 * 加载已打包
	 */
	public List<DaBag> loadAlreadyBags(DaBag bag, Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaBag.class,"db");
		if(bag!=null){
			if(bag.getName()!=null&&!"".equals(bag.getName())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.name", bag.getName(), MatchMode.ANYWHERE));
			}
			if(bag.getRegAddress()!=null&&!"".equals(bag.getRegAddress())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.regAddress", bag.getRegAddress(), MatchMode.ANYWHERE));
			}
		}
		if (userDetail.getFifthArea() != null
				&& !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fifthArea",
					userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null
				&& !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fouthArea",
					userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null
				&& !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.thirdArea",
					userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null
				&& !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.secondArea",
					userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null
				&& !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.firstArea",
					userDetail.getFirstArea()));
		} else {

		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("id in (select par_da_bag_id from da_bag_company_rel where is_deleted=0)") );
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return bagPersistenceIface.loadBags(detachedCriteriaProxy, pagination);
	}
	
	public List<DaBag> loadBagsByBagType(String tp,UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaBag> list=new ArrayList<DaBag>();
		ResultSet rs=null;
		String sql="select id,NAME from DA_BAG where BAG_TYPE= \'"+tp+"\'and IS_DELETED=0";
		if (userDetail.getFifthArea() != null
				&& !userDetail.getFifthArea().equals(0L)) {
			sql=sql+" and FIFTH_AREA= "+userDetail.getFifthArea();
		} else if (userDetail.getFouthArea() != null
				&& !userDetail.getFouthArea().equals(0L)) {
			sql=sql+" and FOUTH_AREA= "+userDetail.getFouthArea();
		} else if (userDetail.getThirdArea() != null
				&& !userDetail.getThirdArea().equals(0L)) {
			sql=sql+" and THIRD_AREA= "+userDetail.getThirdArea();
		} else if (userDetail.getSecondArea() != null
				&& !userDetail.getSecondArea().equals(0L)) {
			sql=sql+" and SECOND_AREA= "+userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null
				&& !userDetail.getFirstArea().equals(0L)) {
			sql=sql+" and FIRST_AREA= "+userDetail.getFirstArea();
		} else {

		}
		rs=bagPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				DaBag bag=new DaBag();
				bag.setName(rs.getString("NAME"));
				bag.setId(rs.getLong("id"));
				list.add(bag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<DaCompany> loadUnBagCompanies(DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaCompany.class,"dc");
		if(company!=null){
			if (company.getCompanyName() != null
					&& !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like(
						"dc.companyName", company.getCompanyName().trim(),
						MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null
					&& !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.address",
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
		List<Long> list1=loadCompanyPassIds();
		List<Long> list2=loadBsgCompanyRelIds();
		if(list1.size()!=0){
			detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.in("dc.id", list1)));
		}
		if(list2.size()!=0){
			detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.in("dc.id", list2)));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.enterprise", false));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.orga", false));
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	private List<Long> loadCompanyPassIds() throws ApplicationAccessException{
		List<Long> list=new ArrayList<Long>();
		ResultSet rs=null;
		String sql="select par_da_com_id from da_company_industry_rel group by par_da_com_id";
		rs=companyPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				list.add(rs.getLong("par_da_com_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private List<Long> loadBsgCompanyRelIds() throws ApplicationAccessException{
		List<Long> list=new ArrayList<Long>();
		ResultSet rs=null;
		String sql="select par_da_com_id from da_bag_company_rel where is_deleted=0 group by par_da_com_id ";
		rs=companyPersistenceIface.findBySql(sql);
		try {
			while(rs.next()){
				list.add(rs.getLong("par_da_com_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Long createBag(DaBag bag) throws ApplicationAccessException {
		return bagPersistenceIface.createBag(bag);
	}

	public void deleteBag(String ids) throws ApplicationAccessException {
		for(int i=0;i<ids.split(",").length;i++){
			bagPersistenceIface.deleteBag(new DaBag(Long
					.parseLong(ids.split(",")[i].trim())));
		}
	}

	public DaBag loadBag(DaBag bag) throws ApplicationAccessException {
		return bagPersistenceIface.loadBag(bag);
	}

	public List<DaBag> loadBags(DaBag bag, Pagination pagination,UserDetailWrapper userDetail)
			throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy=DetachedCriteriaProxy.forClass(DaBag.class,"db");
		if(bag!=null){
			if(bag.getName()!=null&&!"".equals(bag.getName())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.name", bag.getName(), MatchMode.ANYWHERE));
			}
			if(bag.getRegAddress()!=null&&!"".equals(bag.getRegAddress())){
				detachedCriteriaProxy.add(RestrictionsProxy.like("db.regAddress", bag.getRegAddress(), MatchMode.ANYWHERE));
			}
		}
		if (userDetail.getFifthArea() != null
				&& !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fifthArea",
					userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null
				&& !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.fouthArea",
					userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null
				&& !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.thirdArea",
					userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null
				&& !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.secondArea",
					userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null
				&& !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("db.firstArea",
					userDetail.getFirstArea()));
		} else {

		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("modifyTime"));
		return bagPersistenceIface.loadBags(detachedCriteriaProxy, pagination);
	}

	public void updateBag(DaBag bag) throws ApplicationAccessException {
		bagPersistenceIface.updateBag(bag);
	}

	public BagPersistenceIface getBagPersistenceIface() {
		return bagPersistenceIface;
	}

	public void setBagPersistenceIface(BagPersistenceIface bagPersistenceIface) {
		this.bagPersistenceIface = bagPersistenceIface;
	}

	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

}
