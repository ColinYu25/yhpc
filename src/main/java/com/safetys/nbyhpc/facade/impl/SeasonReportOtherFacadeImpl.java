package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaSeasonReportOther;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.iface.SeasonReportOtherPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.SeasonReportOtherFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class SeasonReportOtherFacadeImpl implements SeasonReportOtherFacadeIface {

	private SeasonReportOtherPersistenceIface seasonReportOtherPersistenceIface;
	
	private TradeTypePersistenceIface tradeTypePersistenceIface;
	
	public List<DaIndustryParameter> loadIndustryParameters(UserDetailWrapper userDetail) throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		if(!Nbyhpc.USER_INDUSTRY_AJJ.equals( userDetail.getUserIndustry())){
			detachedCriteriaProxy.add(RestrictionsProxy.like("dip.depiction", userDetail.getUserIndustry(), MatchMode.ANYWHERE));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.QUARTER_REPORT_TRADE_OTHER));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public void createSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		seasonReportOtherPersistenceIface.createSeasonReportOther(seasonReportOther);
	}

	public void deleteSeasonReportOthers(String ids) throws ApplicationAccessException {
		for(int i=0; i<ids.split(",").length; i++) {
			seasonReportOtherPersistenceIface.deleteSeasonReportOther(new DaSeasonReportOther(Long.parseLong(ids.split(",")[i].trim())));
		}		
	}

	public void deleteSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		seasonReportOtherPersistenceIface.deleteSeasonReportOther(seasonReportOther);
	}
	
	public DaSeasonReportOther loadSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		return seasonReportOtherPersistenceIface.loadSeasonReportOther(seasonReportOther);
	}
	public DaSeasonReportOther loadSeasonReportOtheres(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		return seasonReportOtherPersistenceIface.loadSeasonReportOther(seasonReportOther);
	}
	public List<DaSeasonReportOther> loadSeasonReportOthers(DaSeasonReportOther seasonReportOther, Pagination pagination,UserDetailWrapper userDetail,DaCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaSeasonReportOther.class, "dti");
		if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = (select third_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = (select second_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = (select first_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		}
		
		if(company !=null){
			if(company.getSecondArea() != null && !company.getSecondArea().equals(0L)){
				if(company.getThirdArea() != null && !company.getThirdArea().equals(0L)){
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = "+company.getThirdArea()+" )"));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = "+company.getSecondArea()+" and third_area is null or third_area = 0)"));
				}
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = "+company.getFirstArea()+" and second_area is null or second_area =0 )"));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry = '"+userDetail.getUserIndustry()+"')"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dti.modifyTime"));
		return seasonReportOtherPersistenceIface.loadSeasonReportOthers(detachedCriteriaProxy, pagination);
	}
	
	public List<DaSeasonReportOther> loadSeasonReportOtherList(DaSeasonReportOther seasonReportOther,
			Pagination pagination, UserDetailWrapper userDetail,DaCompany company)
			throws ApplicationAccessException{
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaSeasonReportOther.class, "dti");
		if(userDetail.getSecondArea()!=null&&!userDetail.getSecondArea().equals(0L)){
			if(userDetail.getThirdArea()!=null&&!userDetail.getThirdArea().equals(0L)){
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = (select third_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = (select second_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
			}
		}else{
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = (select first_area from fk_user_info where id ="+userDetail.getUserId()+" ))"));
		}
		if(seasonReportOther!=null&&seasonReportOther.getDaIndustryParameter().getId()!=-1l){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dti.daIndustryParameter.id", seasonReportOther.getDaIndustryParameter().getId()));
		}
		if(company !=null){
			if(company.getSecondArea() != null && !company.getSecondArea().equals(0L)){
				if(company.getThirdArea() != null && !company.getThirdArea().equals(0L)){
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where third_area = "+company.getThirdArea()+" )"));
				}else{
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where second_area = "+company.getSecondArea()+" and third_area is null or third_area = 0)"));
				}
			}else{
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where first_area = "+company.getFirstArea()+" and second_area is null or second_area =0 )"));
			}
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.user_id in (select id from fk_user_info where user_industry = '"+userDetail.getUserIndustry()+"')"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dti.modifyTime"));
		return seasonReportOtherPersistenceIface.loadSeasonReportOthers(detachedCriteriaProxy, pagination);
	}
	
	public List<Statistic> loadSeasonReportOtherStatistic(UserDetailWrapper userDetail)throws ApplicationAccessException {
		String ar="";
		if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			ar=" where second_area ="+userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			
		}
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String sql = "select i.name,sum(o.company_num),sum(o.troub_num),sum(o.troub_clean_num), "
			+ " sum(o.big_troub_num),sum(o.big_troub_clean_num),sum(o.target),sum(o.goods), "
			+ " sum(o.resources),sum(o.finish_date),sum(o.safety_method),sum(o.govern_money) from "
			+ " (select id,name from da_industry_parameter where type=7 and is_deleted=0 and depiction ='"+userDetail.getUserIndustry()+"') i  "
			+ " left join  (select * from da_season_report_other  "+ar+") o on o.par_da_ind_id=i.id and o.is_deleted=0 group by i.id,i.name order by i.id";
		ResultSet res = seasonReportOtherPersistenceIface.findBySql(sql);
		try {
			while (res.next()) {
				statistic = new Statistic();
				statistic.setEnumName(res.getString(1));
				statistic.setCompanyNum(res.getInt(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setBigTroubCleanNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}	

	
	@SuppressWarnings("unchecked")
	public List loadHiddenGovernanceReportStatistic(DaSeasonReportOther seasonReportOther)throws ApplicationAccessException{
		List statistics = new ArrayList();
		
		/**
		String sql="select d.des,sum(d.cid),sum(d.nid),sum(d.czid),sum(d.did),sum(d.dgid),sum(d.target),sum(d.goods),sum(d.resources)," +
				"sum(d.did),sum(d.method),sum(d.money)  from (select b.description des, b.seq seq,count(distinct(a.cid)) cid,count(a.nid) nid," +
				"0 czid,count(distinct(a.did)) did,count(distinct(a.dgid)) dgid,sum(a.target) target,sum(a.goods) goods,sum(a.resources) resources," +
				"sum(a.method)  method,sum(a.money) money from (select id,description,seq from da_industry_parameter " +
				"where sort like '%"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC+"%' or sort like '%"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC_OTHER+"%' and is_deleted =0 ) b left join (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des," +
				"com.seq seq,com.nid nid,com.did did,com.dgid,com.target target,com.goods goods,com.resources resources,com.method method," +
				"com.money money from (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq seq,com.nid nid,da.id did," +
				"dg.id dgid,da.target target,da.goods goods,da.resources resources,da.safety_method method,da.govern_money money from " +
				"(select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq seq,da.id nid " +
				"from (select com.id cid,com.company_name cname,ind.id iid,ind.name iname,ind.description des,ind.seq seq from da_company com " +
				"left join da_company_industry_rel rel on com.id=rel.par_da_com_id and com.is_deleted=0 " +
				"left join da_industry_parameter ind on rel.par_da_ind_id=ind.id ) com left join " +
				"(select da.par_da_com_id,da.id from da_normal_danger da " ;
		if(seasonReportOther!=null){
			if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
				sql+="where create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
			}
		}
		sql+=") da on com.cid=da.par_da_com_id ) com left join da_danger da on com.cid=da.par_da_com_id  " +
				"left join (select dg.par_da_dan_id,dg.id,dg.is_deleted from da_danger_gorver dg " ;
		if(seasonReportOther!=null){
			if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
				sql+="where create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
			}
		}		
		sql+=	") dg on da.id=dg.par_da_dan_id and dg.is_deleted=0 " ;
		if(seasonReportOther!=null){
			if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
				sql+="and da.create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
			}
		}	
			sql+=") com " +
				"where com.nid is not null or com.did is not null ) a on b.id=a.iid group by b.id,b.description,b.seq union " +
				"select  b.description des, b.seq seq,0 cid ,0 nid,count(a.nid) czid,0 did,0 dgid,0 target,0 goods,0 resources,0 method," +
				"0 money from (select id,description,seq from da_industry_parameter where sort like '%"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC+"%' or sort like '%"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC_OTHER+"%' and is_deleted =0 ) b " +
				"left join (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq seq,com.nid nid,com.did did from " +
				"(select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq seq,com.nid nid,da.id did,dg.id dgid " +
				"from (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq seq,da.id nid " +
				"from (select com.id cid,com.company_name cname,ind.id iid,ind.name iname,ind.description des,ind.seq seq from da_company com " +
				"left join da_company_industry_rel rel on com.id=rel.par_da_com_id and com.is_deleted=0 " +
				"left join da_industry_parameter ind on rel.par_da_ind_id=ind.id ) com left join (select par_da_com_id,id from da_normal_danger where completed_date <(select sysdate from dual)" ;
			if(seasonReportOther!=null){
				if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
					sql+="and create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
				}
			}
			sql+=	") da " +
				"on com.cid=da.par_da_com_id ) com left join da_danger da on com.cid=da.par_da_com_id  " +
				"left join da_danger_gorver dg on da.id=dg.par_da_dan_id and dg.is_deleted=0) com " +
				"where com.nid is not null or com.did is not null ) a on b.id=a.iid group by b.id,b.description,b.seq ) d " +
				"group by d.des,d.seq order by d.seq ";
			
		*/
		/**
		 * @author lvx
		 * @date 2009-12-15
		 * begin
		 */
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
        String mDateTime=formatter.format(cal.getTime());
        Integer nextYear = seasonReportOther.getNowYear()+1;
		String sql = "select val.des,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val, "
        	+ " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, "
        	+ " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money from (  "
        	+ " select com.des,count(distinct(d.par_da_com_id)) com_val,0 dnd_val,0 dndg_val, "
        	+ " count(distinct(dd.id)) dd_val,count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target, "
        	+ " sum(dd_ng.goods) goods,sum(dd_ng.resources) resources,count(distinct(dd_ng.finish_date)) finish, "
        	+ " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,com.seq,1 type "
        	+ " from (select c.id ,ind.description des,ind.seq seq  "
        	+ " from (select i.id,i.description,i.seq from da_industry_parameter i  "
        	+ " where i.is_deleted=0 and i.sort = '"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC+"' or sort = '"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC_OTHER+"') ind    "
        	+ " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id  "  
        	+ " left join da_company c on c.id=rel.par_da_com_id and c.is_deleted=0) com    "
        	+ " left join (select id,par_da_com_id from  da_danger where " 
        	+ "  is_deleted=0 and  create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) dd on dd.par_da_com_id = com.id  "
        	+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money  "
        	+ " from da_danger d left join da_danger_gorver g on g.par_da_dan_id=d.id where d.is_deleted=0  "
        	+ " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id    " 
        	+ " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0 " 
        	+ " and d.create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd') " 
        	
        	+ " union select n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " 
        	
        	+ " and n.create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) d on d.par_da_com_id=com.id    "
        	
        	+ " group by com.des,com.seq  "
        	+ " union   "
        	+ " select com.des,0 com_val,count(distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val, "
        	+ " 0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,com.seq,2 type "
        	+ " from  (select c.id ,ind.description des,ind.seq seq  "
        	+ " from (select i.id,i.description,i.seq from da_industry_parameter i  "
        	+ " where i.is_deleted=0 and i.sort = '"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC+"' or sort = '"+Nbyhpc.GOVERNANCE_REPORT_STATISTIC_OTHER+"') ind    "
        	+ " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id    "
        	+ " left join da_company c on c.id=rel.par_da_com_id and c.is_deleted=0) com    "
        	+ " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and " 
        	+ " create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) dnd on dnd.par_da_com_id = com.id " 
        	+ " left join (select id from da_normal_danger where is_deleted = 0 and     " 
        	+ " completed_date < to_date('"+mDateTime+"','yyyy-MM-dd')) dnd_g on dnd_g.id = dnd.id  "
        	+ " group by com.des,com.seq ) val group by val.des,val.seq order by val.seq";
		/**
		 * end
		 */
		ResultSet res = seasonReportOtherPersistenceIface.findBySql(sql);
		int i=1;
		Statistic sta = new Statistic();
		try {
			while (res.next()) {
				Statistic statistic = new Statistic();
				statistic.setEnumName(res.getString(1));
				statistic.setCompanyNum(res.getInt(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setBigTroubCleanNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				if(i==5){
					sta=statistic;
				}else if(i==6){
					Statistic newsta = new Statistic();
					newsta.setEnumName("经营企业和单位");
					newsta.setCompanyNum(statistic.getCompanyNum()+sta.getCompanyNum());
					newsta.setTroubNum(statistic.getTroubNum()+sta.getTroubNum());
					newsta.setTroubCleanNum(statistic.getTroubCleanNum()+sta.getTroubCleanNum());
					newsta.setBigTroubNum(statistic.getBigTroubNum()+sta.getBigTroubNum());
					newsta.setBigTroubCleanNum(statistic.getBigTroubCleanNum()+sta.getBigTroubCleanNum());
					newsta.setTarget(statistic.getTarget()+sta.getTarget());
					newsta.setGoods(statistic.getGoods()+sta.getGoods());
					newsta.setResource(statistic.getResource()+sta.getResource());
					newsta.setFinishDate(statistic.getFinishDate()+sta.getFinishDate());
					newsta.setSafetyMethod(statistic.getSafetyMethod()+sta.getSafetyMethod());
					newsta.setGovernMoney(statistic.getGovernMoney()+sta.getGovernMoney());
					statistics.add(newsta);
				}else{
					statistics.add(statistic);
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statistics;
	}
	
	@SuppressWarnings("unchecked")
	public List loadHiddenKeyIndustriesReportStatistic(DaSeasonReportOther seasonReportOther)throws ApplicationAccessException{
		List statistics = new ArrayList<Statistic>();
		/**
		String sql="select d.des,sum(d.cid),sum(d.nid),sum(d.czid),sum(d.did),sum(d.dgid),sum(d.target),sum(d.goods),sum(d.resources)," +
				"sum(d.did),sum(d.method),sum(d.money)  from (select b.description2 des, b.seq2 seq2,count(distinct(a.cid)) cid,count(a.nid) nid," +
				"0 czid,count(a.did) did,count(distinct(a.dgid)) dgid,sum(a.target) target,sum(a.goods) goods,sum(a.resources) resources," +
				"sum(a.method)  method,sum(a.money) money from (select id,description2,seq2 from da_industry_parameter " +
				"where sort like '%"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC+"%' or sort like '%"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC_OTHER+"%' and is_deleted =0 ) b left join (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des," +
				"com.seq2 seq2,com.nid nid,com.did did,com.dgid,com.target target,com.goods goods,com.resources resources,com.method method," +
				"com.money money from (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq2 seq2,com.nid nid,da.id did," +
				"dg.id dgid,da.target target,da.goods goods,da.resources resources,da.safety_method method,da.govern_money money from " +
				"(select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq2 seq2,da.id nid " +
				"from (select com.id cid,com.company_name cname,ind.id iid,ind.name iname,ind.description des,ind.seq2 seq2 from da_company com " +
				"left join da_company_industry_rel rel on com.id=rel.par_da_com_id and com.is_deleted=0 " +
				"left join da_industry_parameter ind on rel.par_da_ind_id=ind.id ) com left join (select da.par_da_com_id,da.id from da_normal_danger da" ;
				if(seasonReportOther!=null){
					if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
						sql+=" where create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
					}
				}
				
			sql+=" ) da on com.cid=da.par_da_com_id ) com left join da_danger da on com.cid=da.par_da_com_id  " +
				"left join (select dg.par_da_dan_id,dg.id,dg.is_deleted from da_danger_gorver dg " ;
			if(seasonReportOther!=null){
					if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
						sql+=" where create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
					}
			}	
			sql+=") dg on da.id=dg.par_da_dan_id and dg.is_deleted=0" ;
			if(seasonReportOther!=null){
				if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
					sql+="and da.create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
				}
			}
			sql+=") com where com.nid is not null or com.did is not null ) a on b.id=a.iid group by b.id,b.description2,b.seq2 union " +
				"select  b.description2 des, b.seq2 seq2,0 cid ,0 nid,count(a.nid) czid,0 did,0 dgid,0 target,0 goods,0 resources,0 method," +
				"0 money from (select id,description2,seq2 from da_industry_parameter where sort like '%"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC+"%' or sort like '%"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC_OTHER+"%' and is_deleted =0 ) b " +
				"left join (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq2 seq2,com.nid nid,com.did did from " +
				"(select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq2 seq2,com.nid nid,da.id did,dg.id dgid " +
				"from (select com.cid cid,com.cname cname,com.iid iid,com.iname iname,com.des des,com.seq2 seq2,da.id nid " +
				"from (select com.id cid,com.company_name cname,ind.id iid,ind.name iname,ind.description des,ind.seq2 seq2 from da_company com " +
				"left join da_company_industry_rel rel on com.id=rel.par_da_com_id and com.is_deleted=0 " +
				"left join da_industry_parameter ind on rel.par_da_ind_id=ind.id ) com left join (select par_da_com_id,id from da_normal_danger where completed_date <(select sysdate from dual)" ;
			if(seasonReportOther!=null){
				if(seasonReportOther.getBeginDate()!=null&&seasonReportOther.getEndDate()!=null){
					sql+="and create_time between to_date('"+seasonReportOther.getBeginDate()+"','yyyy-mm-dd') and to_date('"+seasonReportOther.getEndDate()+"','yyyy-mm-dd')";
				}
			}
			sql+=	") da " +
				"on com.cid=da.par_da_com_id ) com left join da_danger da on com.cid=da.par_da_com_id  " +
				"left join da_danger_gorver dg on da.id=dg.par_da_dan_id and dg.is_deleted=0) com " +
				"where com.nid is not null or com.did is not null ) a on b.id=a.iid group by b.id,b.description2,b.seq2 ) d " +
				"group by d.des,d.seq2 order by d.seq2 ";
		*/	
		/**
		 * @author lvx
		 * @date 2009-12-15
		 * begin
		 */
		Integer nextYear = seasonReportOther.getNowYear()+1;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
        String mDateTime=formatter.format(cal.getTime());
        String sql = "select val.des,sum(com_val) com_val,sum(dnd_val) dnd_val,sum(dndg_val) dndg_val, "
        	+ " sum(dd_val) dd_val,sum(ddng_val) ddng_val,sum(target) target,sum(goods) goods, "
        	+ " sum(resources) resources,sum(finish) finish,sum(method) method,sum(money) money from (  "
        	+ " select com.des,count(distinct(d.par_da_com_id)) com_val,0 dnd_val,0 dndg_val, "
        	+ " count(distinct(dd.id)) dd_val,count(distinct(dd_ng.id)) ddng_val,sum(dd_ng.target) target, "
        	+ " sum(dd_ng.goods) goods,sum(dd_ng.resources) resources,count(distinct(dd_ng.finish_date)) finish, "
        	+ " sum(dd_ng.safety_method) method,sum(dd_ng.govern_money) money,com.seq,1 type "
        	+ " from (select c.id ,ind.description des,ind.seq seq  "
        	+ " from (select i.id,i.description,i.seq from da_industry_parameter i  "
        	+ " where i.is_deleted=0 and i.sort = '"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC+"' or sort = '"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC_OTHER+"') ind    "
        	+ " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id  "  
        	+ " left join da_company c on c.id=rel.par_da_com_id and c.is_deleted=0) com    "
        	+ " left join (select id,par_da_com_id from  da_danger where " 
        	+ " is_deleted=0 and  create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) dd on dd.par_da_com_id = com.id "
        	+ " left join ( select d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money  "
        	+ " from da_danger d left join da_danger_gorver g on g.par_da_dan_id=d.id where d.is_deleted=0  "
        	+ " and g.par_da_dan_id is null and d.id is not null) dd_ng on dd_ng.id=dd.id    " 
        	+ " left join (select d.par_da_com_id from da_danger d where d.is_deleted=0 " 
        	+ " and d.create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd') " 
        	
        	+ " union select n.par_da_com_id from da_normal_danger n where n.is_deleted=0 " 
        	
        	+ " and n.create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) d on d.par_da_com_id=com.id    "
        	
        	+ " group by com.des,com.seq  "
        	+ " union   "
        	+ " select com.des,0 com_val,count(distinct(dnd.id)) dnd_val,count(distinct(dnd_g.id)) dndg_val, "
        	+ " 0 dd_val,0 ddng_val,0 target,0 goods,0 resources,0 finish,0 method,0 money,com.seq,2 type "
        	+ " from  (select c.id ,ind.description des,ind.seq seq  "
        	+ " from (select i.id,i.description,i.seq from da_industry_parameter i  "
        	+ " where i.is_deleted=0 and i.sort = '"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC+"' or sort = '"+Nbyhpc.KEY_INDUSTRIES_REPROT_STATISTIC_OTHER+"') ind    "
        	+ " left join da_company_industry_rel rel on rel.par_da_ind_id=ind.id    "
        	+ " left join da_company c on c.id=rel.par_da_com_id and c.is_deleted=0) com    "
        	+ " left join (select id,par_da_com_id from da_normal_danger where is_deleted=0 and " 
        	+ " create_time between to_date('"+seasonReportOther.getNowYear()+"-01-01','yyyy-MM-dd') and to_date('"+nextYear+"-01-01','yyyy-MM-dd')) dnd on dnd.par_da_com_id = com.id  " 
        	+ " left join (select id from da_normal_danger where is_deleted = 0 and     " 
        	+ " completed_date < to_date('"+mDateTime+"','yyyy-MM-dd')) dnd_g on dnd_g.id = dnd.id  "
        	+ " group by com.des,com.seq ) val group by val.des,val.seq order by val.seq";
		/**
		 * end
		 */
		ResultSet res = seasonReportOtherPersistenceIface.findBySql(sql);
		try{
			while(res.next()){
				Statistic statistic = new Statistic();
				statistic.setEnumName(res.getString(1));
				statistic.setCompanyNum(res.getInt(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setBigTroubCleanNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				statistics.add(statistic);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return statistics;
	}
	
	@SuppressWarnings("unchecked")
	public List<Statistic> loadSeasonReportOtherAreaStatistic(UserDetailWrapper userDetail)throws ApplicationAccessException{
		String ar="";
		if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			ar=" area_code ="+userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			ar= " father_id =(select id from fk_area where area_code="+userDetail.getFirstArea()+")";
		}
		String areaSql="select area_code , area_name from fk_area where "+ar+" order by sort_num";
		ResultSet areaRes = seasonReportOtherPersistenceIface.findBySql(areaSql);
		List <Statistic>areaList =new ArrayList();
		try{
			while(areaRes.next()){
				Statistic	statistic = new Statistic();
				statistic.setAreaCode(areaRes.getLong(1));
				statistic.setAreaName(areaRes.getString(2));
				areaList.add(statistic);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		List list=new ArrayList();
		for(Statistic sta:areaList){
			List<Statistic> statistics = new ArrayList<Statistic>();
			String sql = "select i.name,sum(o.company_num),sum(o.troub_num),sum(o.troub_clean_num), "
				+ " sum(o.big_troub_num),sum(o.big_troub_clean_num),sum(o.target),sum(o.goods), "
				+ " sum(o.resources),sum(o.finish_date),sum(o.safety_method),sum(o.govern_money) from "
				+ " (select id,name from da_industry_parameter where type=7 and is_deleted=0 and depiction='"+userDetail.getUserIndustry()+"') i  "
				+ " left join  (select * from da_season_report_other where second_area ="+sta.getAreaCode()+") "
				+"o on o.par_da_ind_id=i.id and o.is_deleted=0 group by i.id,i.name order by i.id";
			ResultSet res = seasonReportOtherPersistenceIface.findBySql(sql);
			try {
				while (res.next()) {
					Statistic	statistic = new Statistic();
					statistic.setAreaName(sta.getAreaName());
					statistic.setEnumName(res.getString(1));
					statistic.setCompanyNum(res.getInt(2));
					statistic.setTroubNum(res.getInt(3));
					statistic.setTroubCleanNum(res.getInt(4));
					statistic.setBigTroubNum(res.getInt(5));
					statistic.setBigTroubCleanNum(res.getInt(6));
					statistic.setTarget(res.getInt(7));
					statistic.setGoods(res.getInt(8));
					statistic.setResource(res.getInt(9));
					statistic.setFinishDate(res.getInt(10));
					statistic.setSafetyMethod(res.getInt(11));
					statistic.setGovernMoney(res.getDouble(12));
					statistics.add(statistic);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list.add(statistics);
		}
		
		return list;
	}

	public void updateSeasonReportOther(DaSeasonReportOther seasonReportOther) throws ApplicationAccessException {
		DaSeasonReportOther oldSeasonReportOther = loadSeasonReportOther(seasonReportOther);
		seasonReportOther.setCreateTime(oldSeasonReportOther.getCreateTime());
		seasonReportOtherPersistenceIface.mergeSeasonReportOther(seasonReportOther);
	}

	public void setSeasonReportOtherPersistenceIface(
			SeasonReportOtherPersistenceIface seasonReportOtherPersistenceIface) {
		this.seasonReportOtherPersistenceIface = seasonReportOtherPersistenceIface;
	}

	public void setTradeTypePersistenceIface(
			TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}	


}
