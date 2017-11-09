package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.domain.persistence.iface.EnumPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaTrouble;
import com.safetys.nbyhpc.domain.model.DaTroubleCompany;
import com.safetys.nbyhpc.domain.model.DaTroubleFile;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DeptPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TroubleCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TroubleFilePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TroublePersistenceIface;
import com.safetys.nbyhpc.facade.iface.TroubleFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.Nbyhpc;

public class TroubleFacadeImpl implements TroubleFacadeIface {

	private TroublePersistenceIface troublePersistenceIface;
	
	private TroubleFilePersistenceIface troubleFilePersistenceIface;
	
	private TroubleCompanyPersistenceIface troubleCompanyPersistenceIface;
	
	private DeptPersistenceIface deptPersistenceIface;
	
	private CompanyPersistenceIface companyPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	private EnumPersistenceIface enumPersistenceIface;
	
	private AreaPersistenceIface areaPersistenceIface;
	
	public List<DaCompany> loadCompanies (DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",userDetail.getFirstArea()));
		}
		if(company != null){
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea()));
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
							}
						}
					}
				}
			}
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel )"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaCompany> loadTroubleUp (DaCompany company,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",userDetail.getFirstArea()));
		}
		if(company != null){
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress",company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea",company.getFirstArea()));
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
					if (company.getThirdArea() != null && company.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
						if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
							if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
							}
						}
					}
				}
			}
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in ("+trade+"))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel )"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}
	
	public List<DaDept> loadTroubleDown (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDept.class, "dd");
		detachedCriteriaProxy.createCriteria("daTrouble", "dt");
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.secondArea",userDetail.getSecondArea()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.findDeptTrade",userDetail.getUserIndustry()));
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("dd.daDept.id"));
		

		if(trouble != null){
			if (trouble.getTroubleCompanyName() != null && !"".equals(trouble.getTroubleCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dt.troubleCompanyName", trouble.getTroubleCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (trouble.getFirstArea() != null && trouble.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",trouble.getFirstArea()));
				if (trouble.getSecondArea() != null && trouble.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea", trouble.getSecondArea()));
					if (trouble.getThirdArea() != null && trouble.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea", trouble.getThirdArea()));
						if (trouble.getFouthArea() != null && trouble.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea", trouble.getFouthArea()));
							if (trouble.getFifthArea() != null && trouble.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea", trouble.getFifthArea()));
							}
						}
					}
				}
			}
		}
		if(dept != null){
			if (dept.getType() != null && ("1".equals(dept.getType().trim())||"2".equals(dept.getType().trim()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.back",dept.getBack()));
			}else if(dept.getType() != null && ("3".equals(dept.getType().trim())||"4".equals(dept.getType().trim()))) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.result",dept.getResult()));
			}
			if (dept.getTroubleCopyType() != null && dept.getTroubleCopyType() !=0 ) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.troubleCopyType",dept.getTroubleCopyType()));
			}
		}	
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dt.id"));
		return deptPersistenceIface.loadDeptes(detachedCriteriaProxy, pagination);
	}

	public Statistic loadStatisticOfDownByUser(UserDetailWrapper userDetail)throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sql = "select count(distinct(dt.id)),sum(dd.is_back),sum(dd.is_result) from da_trouble dt "
			+ " left join da_dept dd on dd.trouble_id = dt.id"
			+ " where dt.is_deleted=0 and dd.trouble_copy_type=1 and dd.second_area= "
			+userDetail.getSecondArea()+" and dd.find_dept_trade = '"+userDetail.getUserIndustry()+"' and dd.dept_id is null";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(statistics != null && statistics.size()>0)
			return statistics.get(0);
		return null;
	}
	
	public String loadStatisticOfDownXmlByUser(UserDetailWrapper userDetail)throws ApplicationAccessException {
		Statistic st = new Statistic();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0'?>");
		buffer.append("<graph yAxisName='' rotateYAxisName='0' showNames='1' yAxisMaxValue='10' numdivlines='9' decimalPrecision='0' formatNumberScale='0' baseFont='宋体' baseFontSize='12' palette='10' useRoundEdges='1' caption='已抄告隐患统计图'>");
		st = loadStatisticOfDownByUser(userDetail);
		buffer.append("<set name='已抄告隐患数' value='"+ st.getTotal() +"' color='8BBA00'/>");
		buffer.append("<set name='已抄告未回复隐患数' value='"+ ( st.getTotal()-st.getInhere()) +"' color='9D080D'/>");
		buffer.append("<set name='已抄告未反馈隐患数' value='"+  (st.getTotal()-st.getNumber())+"' color='F6BD0F'/>");
		buffer.append("</graph>");
		return buffer.toString();
	}
	
	public Statistic loadStatisticOfUpByUser(UserDetailWrapper userDetail)throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sql = "select count(distinct(map.dtId)),count(distinct(nb.trouble_id)),count(distinct(nr.trouble_id))  "
			+ " from (select dt.id dtId,trouble_id,dd.id deptId from da_trouble dt left join  da_dept dd  "
			+ " on dd.trouble_id = dt.id where dt.is_deleted=0 and dd.dept_id is null and dd.mostly_company_area = "+userDetail.getSecondArea()
			+ " and (dd.mostly_company = '"+userDetail.getUserIndustry()+"' or dd.copy_company like '%"+userDetail.getUserIndustry()+"%')) map "
			+ " left join (select trouble_id,dept_id from da_dept  where trouble_copy_type = 4) nb on nb.dept_id = map.deptId "
			+ " left join (select trouble_id,dept_id from da_dept  where trouble_copy_type = 5) nr on nr.dept_id = map.deptId "
			+ " where  map.deptId is not null";
		ResultSet res = companyPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setTotal(res.getInt(1));
				statistic.setInhere(res.getInt(2));
				statistic.setNumber(res.getInt(3));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(statistics != null && statistics.size()>0)
			return statistics.get(0);
		return null;
	}
	
	public String loadStatisticOfUpXmlByUser(UserDetailWrapper userDetail)throws ApplicationAccessException {
		Statistic st = new Statistic();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version='1.0'?>");
		buffer.append("<graph yAxisName='' rotateYAxisName='0' showNames='1' yAxisMaxValue='10' numdivlines='9' decimalPrecision='0' formatNumberScale='0' baseFont='宋体' baseFontSize='12' palette='10' useRoundEdges='1' caption='已接收隐患统计图'>");
		st = loadStatisticOfUpByUser(userDetail);
		buffer.append("<set name='已接收隐患数' value='"+ st.getTotal() +"' color='008ED6'/>");
		buffer.append("<set name='已接收未回复隐患数' value='"+  (st.getTotal()-st.getInhere()) +"' color='D64646'/>");
		buffer.append("<set name='已接收未反馈隐患数' value='"+  (st.getTotal()-st.getNumber())+"' color='588526'/>");
		buffer.append("</graph>");
		return buffer.toString();
	}
	
	public List<DaDept> loadTroubleUp (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDept.class, "dd");
		detachedCriteriaProxy.createCriteria("daTrouble", "dt");
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.mostlyCompanyArea", userDetail.getSecondArea()));
		detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dd.mostlyCompany",userDetail.getUserIndustry().trim()),
				RestrictionsProxy.like("dd.copyCompany",userDetail.getUserIndustry().trim(),MatchMode.ANYWHERE)));
		if(trouble != null){
			if (trouble.getTroubleCompanyName() != null && !"".equals(trouble.getTroubleCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dt.troubleCompanyName", trouble.getTroubleCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (trouble.getFirstArea() != null && trouble.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",trouble.getFirstArea()));
				if (trouble.getSecondArea() != null && trouble.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea", trouble.getSecondArea()));
					if (trouble.getThirdArea() != null && trouble.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea", trouble.getThirdArea()));
						if (trouble.getFouthArea() != null && trouble.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea", trouble.getFouthArea()));
							if (trouble.getFifthArea() != null && trouble.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea", trouble.getFifthArea()));
							}
						}
					}
				}
			}
		}
		if(dept != null){
			if (dept.getType() != null && "1".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dept_id from da_dept where trouble_copy_type=4 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "2".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id  in (select dept_id from da_dept where trouble_copy_type=4 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "3".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dept_id from da_dept where trouble_copy_type=5 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "4".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dept_id from da_dept where trouble_copy_type=5 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dt.id"));
		return deptPersistenceIface.loadDeptes(detachedCriteriaProxy, pagination);
	}
	
	public List<DaDept> loadTroubleDownOrUp (DaDept dept,DaTrouble trouble,Pagination pagination,UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDept.class, "dd");
		detachedCriteriaProxy.createCriteria("daTrouble", "dt");
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea",userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea",userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea",userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea",userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.mostlyCompanyArea", userDetail.getSecondArea()));
		detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dd.mostlyCompany",userDetail.getUserIndustry().trim()),
				RestrictionsProxy.like("dd.copyCompany",userDetail.getUserIndustry().trim(),MatchMode.ANYWHERE)));
		if(trouble != null){
			if (trouble.getTroubleCompanyName() != null && !"".equals(trouble.getTroubleCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dt.troubleCompanyName", trouble.getTroubleCompanyName().trim(),MatchMode.ANYWHERE));
			}
			if (trouble.getFirstArea() != null && trouble.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.firstArea",trouble.getFirstArea()));
				if (trouble.getSecondArea() != null && trouble.getSecondArea() != 0L) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.secondArea", trouble.getSecondArea()));
					if (trouble.getThirdArea() != null && trouble.getThirdArea() != 0L) {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.thirdArea", trouble.getThirdArea()));
						if (trouble.getFouthArea() != null && trouble.getFouthArea() != 0L) {
							detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fouthArea", trouble.getFouthArea()));
							if (trouble.getFifthArea() != null && trouble.getFifthArea() != 0L) {
								detachedCriteriaProxy.add(RestrictionsProxy.eq("dt.fifthArea", trouble.getFifthArea()));
							}
						}
					}
				}
			}
		}
		if(dept != null){
			if (dept.getType() != null && "1".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dept_id from da_dept where trouble_copy_type=4 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "2".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id  in (select dept_id from da_dept where trouble_copy_type=4 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "3".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dept_id from da_dept where trouble_copy_type=5 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.trouble_id not in (select trouble_id from da_dept where trouble_copy_type in (2,3) and dept_id is null and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}else if(dept.getType() != null && "4".equals(dept.getType().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dept_id from da_dept where trouble_copy_type=5 and second_area= "+userDetail.getSecondArea()+" and find_dept_trade = '"+userDetail.getUserIndustry()+"')"));
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dt.id"));
		return deptPersistenceIface.loadDeptes(detachedCriteriaProxy, pagination);
	}
	
	public List<DaIndustryParameter> loadTradeTypesForCompany() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type",Nbyhpc.COMPANY_TRADE));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,null);
	}
	
	public List<FkEnum> loadEnums(UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkEnum.class, "fe");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.father_id in (select id from fk_enum where enum_Code='"+Nbyhpc.CHAOGAO_DEPT+"')"));
		detachedCriteriaProxy.add(RestrictionsProxy.not(Expression.eq("fe.enumCode", userDetail.getUserIndustry())));
		return enumPersistenceIface.loadEnums(detachedCriteriaProxy);
	}
	
	public List<FkEnum> loadAllEnums(UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkEnum.class, "fe");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.father_id in (select id from fk_enum where enum_Code='"+Nbyhpc.CHAOGAO_DEPT+"')"));
		return enumPersistenceIface.loadEnums(detachedCriteriaProxy);
	}
	
	public FkEnum loadEnum(UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkEnum.class, "fe");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.father_id in (select id from fk_enum where enum_Code='"+Nbyhpc.CHAOGAO_DEPT+"')"));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("fe.enumCode", userDetail.getUserIndustry()));
		List<FkEnum> enums = enumPersistenceIface.loadEnums(detachedCriteriaProxy);
		if(enums != null && enums.size() > 0)
			return enums.get(0);
		return null;
	}
	
	public FkArea loadArea(Long areaCode) throws ApplicationAccessException{
		if(areaCode != null && areaCode != 0L){
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("fa.areaCode",areaCode));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("fa.sortNum"));
			List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
			if(areas != null && areas.size() > 0)
				return areas.get(0);
			}
		return null;
	}
	
	public List<FkArea> loadSecondAreas() throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.father_id in (select id from fk_area where area_Code="+Nbyhpc.AREA_CODE+")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("fa.sortNum"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}
	
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	private void createTrouble(DaTrouble trouble) throws ApplicationAccessException {
		troublePersistenceIface.createTrouble(trouble);
	}
	
	private String createTroubleFile(DaTroubleFile troubleFile) throws ApplicationAccessException {
		return troubleFilePersistenceIface.createTroubleFile(troubleFile).toString();
	}
	
	private void createTroubleCompany(DaTroubleCompany troubleCompany) throws ApplicationAccessException {
		troubleCompanyPersistenceIface.createTroubleCompany(troubleCompany);
	}
	
	private void createDept(DaDept dept) throws ApplicationAccessException {
		deptPersistenceIface.createDept(dept);
	}
	
	private void updateBack(DaDept dept) throws ApplicationAccessException {
		String hql = "update DaDept set back = 1  where id = "+dept.getId();
		companyPersistenceIface.executeHQLUpdate(hql);
	}
	
	private void updateResult(DaDept dept) throws ApplicationAccessException {
		String hql = "update DaDept set result = 1  where id = "+dept.getId();
		companyPersistenceIface.executeHQLUpdate(hql);
	}
	
	public String createTroubleDept (DaTroubleCompany troubleCompany,DaTrouble trouble,DaDept dept,List<DaTroubleFile> troubleFiles)throws ApplicationAccessException {
		String err = null;
		List<DaTroubleFile> files = new ArrayList<DaTroubleFile>();
		if(troubleFiles != null && troubleFiles.size() > 0){
			for(DaTroubleFile f : troubleFiles){
				Map map = FileUpload.UploadFile(f.getFile(), f.getFileFileName(),
						FileUpload.UPLOAD_PATH_EMERGENCY, FileUpload.ALLOW_TYPE_WORD, false);
				err = map.get("error").toString();
				if (err != null && !"".equals(err.trim())) {
					return err;
				}
				f.setFilePath(map.get("filePath")+"");
				f.setFileName(f.getFileFileName());
				files.add(f);
			}
		}
		if(troubleCompany != null){
			if(troubleCompany.getId() == -1L){
				createTroubleCompany(troubleCompany);
			}
		}
		if(dept.getTroubleCopyType()!=4 && dept.getTroubleCopyType()!=5){
			trouble.setDaTroubleCompany(troubleCompany);
			createTrouble(trouble);
		} 
		dept.setDaTrouble(trouble);
		createDept(dept);
		if(dept.getTroubleCopyType()==4){
			updateBack(dept.getDaDept());
		}
		if(dept.getTroubleCopyType()==5){
			updateResult(dept.getDaDept());
		}
		if(files != null && files.size() > 0){
			for(DaTroubleFile file : files){
				file.setDaTrouble(trouble);
				file.setUserId(trouble.getUserId());
				err = createTroubleFile(file);
			}
		}
		return err;
	}
	
	public void createTroubleByDownOrUpOrBackOrResult (DaTrouble trouble,DaDept dept)throws ApplicationAccessException {
		DaDept deptBack = new DaDept();
		dept.setDaTrouble(trouble);
		deptBack.setBack(dept.getBack());
		deptBack.setCopyCompany(dept.getCopyCompany());
		deptBack.setDaDept(dept.getDaDept());
		deptBack.setDaTrouble(dept.getDaTrouble());
		deptBack.setDescription(dept.getDescription());
		deptBack.setFindDeptName(dept.getFindDeptName());
		deptBack.setFindDeptTrade(dept.getFindDeptTrade());
		deptBack.setFirstArea(dept.getFirstArea());
		deptBack.setLinkMan(dept.getLinkMan());
		deptBack.setLinkTel(dept.getLinkTel());
		deptBack.setPassMan(dept.getPassMan());
		deptBack.setReceiptTime(new Date());
		deptBack.setResult(dept.getResult());
		deptBack.setSecondArea(dept.getSecondArea());
		deptBack.setUserId(dept.getUserId());
		deptBack.setThirdArea(dept.getThirdArea());
		deptBack.setSubmit(dept.getSubmit());
		deptBack.setSubmitTime(new Date());
		updateBack(dept.getDaDept());
		if(dept.getTroubleCopyType()==5){
			updateResult(dept.getDaDept());
		}
		if(dept.getTroubleCopyType() != null && (dept.getTroubleCopyType() == 2 || dept.getTroubleCopyType() == 3)){
			dept.setDaDept(null);
		}
		if(validatorIsBack(deptBack) && dept.getTroubleCopyType() != null && dept.getTroubleCopyType() != 4){
			deptBack.setTroubleCopyType(4);
			deptBack.setMostlyCompany(null);
			deptBack.setMostlyCompanyArea(0L);
			List<DaDept> deptes = new ArrayList<DaDept>();
			deptes.add(dept);
			deptes.add(deptBack);
			for(DaDept d : deptes){
				createDept(d);
			}
		}else{
			createDept(dept);
		}
	}
	
	private boolean validatorIsBack(DaDept dept)throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDept.class, "dd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.daTrouble.id",dept.getDaTrouble().getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.troubleCopyType",4));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.findDeptTrade",dept.getFindDeptTrade()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.secondArea",dept.getSecondArea()));
		List<DaDept> deptes = deptPersistenceIface.loadDeptes(detachedCriteriaProxy, null);
		if (deptes != null && deptes.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	

	private void updateTrouble(DaTrouble trouble) throws ApplicationAccessException {
		DaTrouble oldDanger = loadTrouble(trouble);
		trouble.setCreateTime(oldDanger.getCreateTime());
		troublePersistenceIface.mergeTrouble(trouble);
	}
	
	private void updateDept(DaDept dept) throws ApplicationAccessException {
		DaDept oldDept = deptPersistenceIface.loadDept(dept);
		dept.setCreateTime(oldDept.getCreateTime());
		deptPersistenceIface.mergeDept(dept);
	}
	
	private void deleteTroubleFilesByHql(DaTrouble trouble) throws ApplicationAccessException {
		String hql = "delete from DaTroubleFile tf where tf.daTrouble.id = "+trouble.getId();
		companyPersistenceIface.executeHQLUpdate(hql);
	}
	
	
	public void updateTroubleDept(DaTrouble trouble,DaDept dept,List<DaTroubleFile> troubleFiles)throws ApplicationAccessException {
		updateTrouble(trouble);
		dept.setDaTrouble(trouble);
		updateDept(dept);
		deleteTroubleFilesByHql(trouble);
		if(troubleFiles != null && troubleFiles.size() > 0){
			for(DaTroubleFile f : troubleFiles){
				f.setDaTrouble(trouble);
				f.setUserId(trouble.getUserId());
				createTroubleFile(f);
			}
		}
	}
	
	public void deleteTroubles(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			troublePersistenceIface.deleteTrouble(new DaTrouble(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteTrouble(DaTrouble trouble) throws ApplicationAccessException {
		troublePersistenceIface.deleteTrouble(trouble);
	}
	
	public DaTrouble loadTrouble(DaTrouble trouble) throws ApplicationAccessException {
		return troublePersistenceIface.loadTrouble(trouble);
	}
	
	public DaDept loadDept(DaDept dept) throws ApplicationAccessException {
		return deptPersistenceIface.loadDept(dept);
	}
	
	public List<DaDept> loadDeptes(DaDept dept,Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaDept.class, "dd");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.daDept.id",dept.getId()));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dd.troubleCopyType",dept.getTroubleCopyType()));
		return deptPersistenceIface.loadDeptes(detachedCriteriaProxy,pagination);
	}
	
	public List<DaTroubleFile> loadTroubleFilesByTrouble(DaTrouble trouble,Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaTroubleFile.class, "dtf");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dtf.daTrouble.id",trouble.getId()));
		return troubleFilePersistenceIface.loadTroubleFiles(detachedCriteriaProxy,pagination);
	}
	
	public DaDept loadDeptById(DaDept dept) throws ApplicationAccessException {
		List<DaDept> deptes = new ArrayList<DaDept>();
		deptes = loadDeptes(dept,null);
		if(deptes != null && deptes.size()>0)
			return deptes.get(0);
		return null;
	}
	
	public void setDeptPersistenceIface(DeptPersistenceIface deptPersistenceIface) {
		this.deptPersistenceIface = deptPersistenceIface;
	}
	public void setTroubleFilePersistenceIface(
			TroubleFilePersistenceIface troubleFilePersistenceIface) {
		this.troubleFilePersistenceIface = troubleFilePersistenceIface;
	}
	public void setTroublePersistenceIface(
			TroublePersistenceIface troublePersistenceIface) {
		this.troublePersistenceIface = troublePersistenceIface;
	}

	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setEnumPersistenceIface(EnumPersistenceIface enumPersistenceIface) {
		this.enumPersistenceIface = enumPersistenceIface;
	}

	public void setTroubleCompanyPersistenceIface(
			TroubleCompanyPersistenceIface troubleCompanyPersistenceIface) {
		this.troubleCompanyPersistenceIface = troubleCompanyPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}	

}
