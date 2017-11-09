package com.safetys.nbyhpc.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.persistence.impl.CompanyQuarterReportPersistenceImpl;
import com.safetys.nbyhpc.facade.iface.CompanyQuarterReportFacadeIface;

public class CompanyQuarterReportFacadeImpl implements CompanyQuarterReportFacadeIface {
	
	static String FIND_REPORT = "select count (obj.id) from DaCompanyQuarterReprot obj where obj.year = ? and obj.quarter = ? and obj.company.id = ?";
		
	private CompanyQuarterReportPersistenceImpl companyQuarterReportPersistence;

	public void setCompanyQuarterReportPersistence(CompanyQuarterReportPersistenceImpl companyQuarterReportPersistence) {
		this.companyQuarterReportPersistence = companyQuarterReportPersistence;
	}



	public long create(DaCompanyQuarterReprot entity) throws Exception {
		return companyQuarterReportPersistence.create(entity);
	}



	public void delete(DaCompanyQuarterReprot entity) throws Exception {
		companyQuarterReportPersistence.delete(entity);
	}



	public List<DaCompanyQuarterReprot> find(DaCompanyQuarterReprot entity, Pagination pagination) throws Exception {
		
		return null;
	}

	public List<DaCompanyQuarterReprot> find(DaCompanyQuarterReprot entity, DaCompany company, Pagination pagination) throws Exception {
//		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyQuarterReprot.class, "dcqr");
//		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcqr.company", company));
//		
//		if (entity != null) {
//			if (entity.getQuarter() != 0) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dcqr.quarter", entity.getQuarter()));
//			}
//			if (entity.getYear() != 0) {
//				detachedCriteriaProxy.add(RestrictionsProxy.eq("dcqr.year", entity.getYear()));
//			}
//		}
//		
//		detachedCriteriaProxy.addOrder(OrderProxy.desc("dcqr.year"));
//		detachedCriteriaProxy.addOrder(OrderProxy.desc("dcqr.quarter"));
		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DaCompanyQuarterReprot dcqr where");
		hql.append(" dcqr.company = ?");
		params.add(company);
		if (entity != null) {
			if (entity.getQuarter() != 0) {
				hql.append(" and dcqr.quarter = ?");
				params.add(entity.getQuarter());
			}
			if (entity.getYear() != 0) {
				hql.append(" and dcqr.year = ?");
				params.add(entity.getYear());
			}
		}
		hql.append(" order by dcqr.year desc,dcqr.quarter desc");
		
		return companyQuarterReportPersistence.find(hql.toString(), params.toArray(), pagination);
	}

	public DaCompanyQuarterReprot findById(DaCompanyQuarterReprot entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void update(DaCompanyQuarterReprot entity) throws Exception {
		companyQuarterReportPersistence.update(entity);
	}



	public void save(DaCompanyQuarterReprot entity) throws Exception {
		
		if (entity == null || entity.getCompany() == null || entity.getCompany().getId() == null 
				|| entity.getYear() <= 0 || entity.getQuarter() <= 0 ){
			return ;
		}
		
		@SuppressWarnings("unchecked")
		List<Long> list = companyQuarterReportPersistence.find("select obj.id from DaCompanyQuarterReprot obj where obj.year = ? and obj.quarter = ? and obj.company.id = ?", 
				new Object[]{entity.getYear(), entity.getQuarter(), entity.getCompany().getId()}, null);
		if (list == null || list.size() == 0 || list.get(0) <= 0){
			this.create(entity);
		}else{
//			System.out.println("id: "+list.get(0));
//			System.out.println("getYear: "+entity.getYear());
//			System.out.println("getQuarter: "+entity.getQuarter());
//			System.out.println("getCompanyid: "+entity.getCompany().getId());
			entity.setId(list.get(0));
			this.update(entity);
		}
	}

}
