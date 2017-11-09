package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.persistence.impl.PipeCompanyQuarterReportPersistenceImpl;
import com.safetys.nbyhpc.facade.iface.PipeCompanyQuarterReportFacadeIface;

public class PipeCompanyQuarterReportFacadeImpl implements PipeCompanyQuarterReportFacadeIface {
	
	static String FIND_REPORT = "select count (obj.id) from DaPipeCompanyQuarterReprot obj where obj.year = ? and obj.quarter = ? and obj.company.id = ?";
		
	private PipeCompanyQuarterReportPersistenceImpl pipeCompanyQuarterReportPersistence;

	/**
	 * 企业月报统计 
	 */
	public Statistic loadReportByCompany(UserDetailWrapper userDetail, Statistic st) throws ApplicationAccessException {
		List<Statistic> statistics = new ArrayList<Statistic>();
		Statistic statistic = new Statistic();
		String startDate = "";
		String endDate = "";
		if (st != null) {
			int nextYear = st.getYear() + 1;
			if (st.getIsAllYear() == 1) {
				startDate = st.getYear() + "-01-01";
				endDate = nextYear + "-01-01";
			} else {
				if (st.getQuarter() == 1) {
					startDate = st.getYear() + "-01-01";
					endDate = st.getYear() + "-04-01";
				} else if (st.getQuarter() == 2) {
					startDate = st.getYear() + "-04-01";
					endDate = st.getYear() + "-07-01";
				} else if (st.getQuarter() == 3) {
					startDate = st.getYear() + "-07-01";
					endDate = st.getYear() + "-10-01";
				} else if (st.getQuarter() == 4) {
					startDate = st.getYear() + "-10-01";
					endDate = nextYear + "-01-01";
				}
			}
		}
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String mDateTime = formatter.format(cal.getTime());
//		if (mDateTime.compareTo(endDate) > 0) mDateTime = endDate;
		String sqlType = " and create_time between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByCom = " and create_time <= to_date('" + endDate + "','yyyy-MM-dd')";
		sqlTypeByCom = sqlType;
//		String sqlTypeByCom1 = " and FILL_DATE <= to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByCom1 = " and FILL_DATE between to_date('" + startDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		String sqlTypeByDangerGorver = " and create_time between to_date('" + st.getYear() + "-01-01','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')";
		StringBuffer sql = new StringBuffer("");
		
		sql.append("select map.company_name,map.reg_address,sum(map.dnd),sum(map.dndg),sum(map.dd), sum(map.ddg),sum(map.target) target ");
		sql.append(" ,sum(map.goods) goods,sum(map.resources) resources, sum(map.finish) finish,sum(map.method) method,sum(map.money) money ");
		sql.append(" ,map.second_areaName,map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, map.id");
		sql.append(" ,(select wm_concat(pi.name) from da_company dc left join da_pipeline_companyinfo pc on dc.id=pc.company_id left join da_pipeline_info pi on pc.id=pi.company_id where dc.id=map.id), sum(map.dndg1) from ( "); 
		
		sql.append("      select dc.company_name,dc.reg_address,count(distinct(dnd.id)) dnd,count(distinct (dndg.id)) dndg,count(distinct(dndg1.id)) dndg1,0 dd, 0 ddg,0 target ");
		sql.append("      ,0 goods,0 resources, 0 finish,0 method,0 money,  (select fa.area_name from fk_area fa where fa.area_code = dc.second_area ");
		sql.append("      and fa.area_code != 0) second_areaName, (select fa.area_name from fk_area fa where fa.area_code = dc.third_area ");
		sql.append("      and fa.area_code != 0) third_areaName, u.fact_name,u.user_phone,u.user_mobile, dc.id "); 
		sql.append("      from da_company dc left join da_company_pass dcp on dcp.par_da_com_id = dc.id left join fk_user_info u on u.id = dcp.com_user_id ");
		sql.append("      left join da_pipeline_companyinfo pc on dc.id=pc.company_id left join da_pipeline_info pi on pc.id=pi.company_id ");

		sql.append("      left join ( ");
		sql.append("          select id,par_da_pipe_id from da_pipe_normal_danger where is_deleted=0 and is_danger=1 " + sqlTypeByCom);
		sql.append("      ) dndg1 on pi.id=dndg1.par_da_pipe_id left join ( ");
		sql.append("          select id from da_pipe_normal_danger where is_deleted=0 and is_danger=1 " + sqlTypeByCom1 + " and (is_repaired=1 or is_danger=0) ");
		sql.append("      ) dndg on dndg1.id=dndg.id ");
		
		sql.append("      left join ( ");
		sql.append("          select id,par_da_pipe_id from da_pipe_normal_danger where is_deleted=0 and is_danger=1 " + sqlType);
		sql.append("      ) dnd on dndg1.id = dnd.id");
		
		
		
//		sql.append("      left join ( ");
//		sql.append("          select id,par_da_pipe_id from da_pipe_normal_danger where is_deleted=0 and is_danger=1 " + sqlTypeByCom);
//		sql.append("      ) dnd on pi.id=dnd.par_da_pipe_id left join ( ");
//		sql.append("          select id from da_pipe_normal_danger where is_deleted=0 and is_danger=1 " + sqlTypeByCom1 + " and (is_repaired=1 or is_danger=0) ");
//		sql.append("      ) dndg on dnd.id=dndg.id ");
		sql.append(" where dcp.com_user_id=" + userDetail.getUserId() + " and dcp.is_affirm=1 and u.is_deleted=0 and dcp.is_deleted=0 ");
		sql.append("      and dc.is_deleted=0 group by dc.id,dc.company_name,dc.reg_address ");
		//sql.append("      and dc.is_deleted=0 " + sqlTypeByCom + " group by dc.id,dc.company_name,dc.reg_address ");
		sql.append("      ,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile ");      
		
		sql.append("      union ");
		
		sql.append("      select dc.company_name,dc.reg_address,0 dnd,0 dndg, 0 dndg1, ");
		sql.append("      count(distinct(dd.id)) dd, count(distinct(ddg.id)) ddg,sum(ddg.target) target,sum(ddg.goods) goods,sum(ddg.resources) resources, "); 
		sql.append("      count(ddg.finish_date) finish,sum(ddg.safety_method) method,sum(ddg.govern_money) money, (select fa.area_name from fk_area fa ");
		sql.append("      where  fa.area_code = dc.second_area and fa.area_code != 0) second_areaName, (select fa.area_name from fk_area fa where ");
		sql.append("      fa.area_code = dc.third_area and fa.area_code != 0) third_areaName,u.fact_name,u.user_phone,u.user_mobile, dc.id ");
		sql.append("      from da_company dc left join da_company_pass dcp on dcp.par_da_com_id = dc.id left join fk_user_info u on u.id = dcp.com_user_id ");
		sql.append("      left join da_pipeline_companyinfo pc on dc.id=pc.company_id left join da_pipeline_info pi on pc.id=pi.company_id ");
		sql.append("      left join ( ");
		sql.append("          select distinct id,par_da_pipe_id from da_pipe_danger where is_deleted=0 " + sqlType);
		sql.append("      ) dd on pi.id=dd.par_da_pipe_id left join ( ");
		sql.append("          select distinct d.id,d.target,d.goods,d.resources,d.safety_method,d.finish_date,d.govern_money from da_pipe_danger d left join ( ");
		sql.append("             select par_da_pipe_dan_id from da_pipe_danger_gorver where is_deleted=0 " + sqlTypeByDangerGorver);
		sql.append("          ) g on d.id=g.par_da_pipe_dan_id where d.is_deleted=0 and g.par_da_pipe_dan_id is null and d.id is not null ");
		sql.append("      ) ddg on ddg.id = dd.id where dcp.com_user_id=" + userDetail.getUserId() + " and dcp.is_affirm=1 ");
		sql.append("      and u.is_deleted=0 and  dcp.is_deleted=0  and dc.is_deleted=0 ");
		//sql.append("      and u.is_deleted=0 and  dcp.is_deleted=0  and dc.is_deleted=0 " + sqlTypeByCom);
		sql.append("      group by  dc.id,dc.company_name,dc.reg_address,dc.second_area,dc.third_area,u.fact_name,u.user_phone,u.user_mobile ");
		
		sql.append(") map ");
		sql.append("group by map.company_name,map.reg_address, map.second_areaName, map.third_areaName,map.fact_name,map.user_phone,map.user_mobile, ");
		sql.append("map.id ");
//		System.out.println("季报sql: "+sql);
		try {
			ResultSet res = pipeCompanyQuarterReportPersistence.findBySql(sql.toString());
			while (res.next()) {
				statistic = new Statistic();
				statistic.setCompanyName(res.getString(1));
				statistic.setAddress(res.getString(2));
				statistic.setTroubNum(res.getInt(3));
				statistic.setTroubCleanNum(res.getInt(20)-res.getInt(4));
				statistic.setBigTroubNum(res.getInt(5));
				statistic.setPlanBigTroubNum(res.getInt(6));
				statistic.setTarget(res.getInt(7));
				statistic.setGoods(res.getInt(8));
				statistic.setResource(res.getInt(9));
				statistic.setFinishDate(res.getInt(10));
				statistic.setSafetyMethod(res.getInt(11));
				statistic.setGovernMoney(res.getDouble(12));
				statistic.setAreaName((res.getString(13) == null ? "" : res.getString(13)) + (res.getString(14) == null ? "" : res.getString(14)));
				statistic.setLinkMan(res.getString(15));
				statistic.setLinkTel(res.getString(16));
				statistic.setLinkMobile(res.getString(17));
				statistic.setCompanyId(res.getLong(18));
				statistic.setPipeNames(res.getString(19));
				statistics.add(statistic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (statistics != null && statistics.size() > 0) {
			return statistics.get(0);
		} else {
			return null;
		}
	}

	public void delete(DaPipeCompanyQuarterReprot entity) throws Exception {
		pipeCompanyQuarterReportPersistence.delete(entity);
	}

	public List<DaPipeCompanyQuarterReprot> find(DaPipeCompanyQuarterReprot entity, Pagination pagination) throws Exception {
		return null;
	}

	public List<DaPipeCompanyQuarterReprot> find(DaPipeCompanyQuarterReprot entity, DaCompany company, Pagination pagination) throws Exception {
		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DaPipeCompanyQuarterReprot dcqr where");
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
		
		return pipeCompanyQuarterReportPersistence.find(hql.toString(), params.toArray(), pagination);
	}

	public DaPipeCompanyQuarterReprot findById(DaPipeCompanyQuarterReprot entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(DaPipeCompanyQuarterReprot entity) throws Exception {
		pipeCompanyQuarterReportPersistence.update(entity);
	}
	
	public Boolean checkIsSaveed(Statistic statistic, Long companyId) throws Exception {
		if (statistic == null || companyId == null || companyId<=0 || statistic.getYear() <= 0 || statistic.getQuarter() <= 0 ){
			return false;
		}
		List<Long> list = pipeCompanyQuarterReportPersistence.find("select count (obj.id) from DaPipeCompanyQuarterReprot obj where obj.year = ? and obj.quarter = ? and obj.company.id = ?", 
				new Object[]{statistic.getYear(), statistic.getQuarter(), companyId}, null);
		if (list != null && list.size()>0 && list.get(0) > 0){
			return true;
		}else{
			return false;
		}
	}

	public void save(DaPipeCompanyQuarterReprot entity) throws Exception {
		if (entity == null || entity.getCompany() == null || entity.getCompany().getId() == null 
				|| entity.getYear() <= 0 || entity.getQuarter() <= 0 ){
			return ;
		}
		List<Long> list = pipeCompanyQuarterReportPersistence.find("select count (obj.id) from DaPipeCompanyQuarterReprot obj where obj.year = ? and obj.quarter = ? and obj.company.id = ?", 
				new Object[]{entity.getYear(), entity.getQuarter(), entity.getCompany().getId()}, null);
		if (list == null || list.size() == 0 || list.get(0) <= 0){
			pipeCompanyQuarterReportPersistence.create(entity);
		}
	}

	public PipeCompanyQuarterReportPersistenceImpl getPipeCompanyQuarterReportPersistence() {
		return pipeCompanyQuarterReportPersistence;
	}

	public void setPipeCompanyQuarterReportPersistence(
			PipeCompanyQuarterReportPersistenceImpl pipeCompanyQuarterReportPersistence) {
		this.pipeCompanyQuarterReportPersistence = pipeCompanyQuarterReportPersistence;
	}

}
