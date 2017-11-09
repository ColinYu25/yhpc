package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.WhAccident;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;
import com.safetys.nbyhpc.domain.persistence.impl.WhCompanyInfoPersistenceImpl;
import com.safetys.nbyhpc.facade.iface.WhCompanyInfoFacadeIface;

public class WhCompanyInfoFacadeImpl implements WhCompanyInfoFacadeIface {
	
	private WhCompanyInfoPersistenceImpl companyInfoPersistence;
	Logger log = Logger.getLogger(this.getClass());

	public long create(WhCompanyInfo entity, List<WhAccident> accidents) throws Exception {
		DaCompany company = (DaCompany)companyInfoPersistence.findById(DaCompany.class, entity.getCompany().getId());
		entity.setCompany(company);
		companyInfoPersistence.create(entity);
		if (accidents != null && accidents.size() > 0){
			for (WhAccident item : accidents) {
				if (item.getType() <= 0){//如果没有填写，则跳过循环
					continue;
				}
				item.setCompanyInfo(entity);
				companyInfoPersistence.create(item);
			}
		}
		return 0;
	}

	public void delete(WhCompanyInfo entity) throws Exception {
		companyInfoPersistence.delete(entity);
	}

	public List<WhCompanyInfo> find(WhCompanyInfo entity, Pagination pagination) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public WhCompanyInfo findById(long id) throws Exception {
		return companyInfoPersistence.findById(id);
	}

	public void update(WhCompanyInfo entity, List<WhAccident> accidents) throws Exception {
		companyInfoPersistence.update(entity);
		companyInfoPersistence.executeHql("delete WhAccident obj where obj.companyInfo.id = ? ", new Object[]{entity.getId()});
		if (accidents != null && accidents.size() > 0){
			for (WhAccident item : accidents) {
				if (item.getType() <= 0){//如果没有填写，则跳过循环
					continue;
				}
				item.setCompanyInfo(entity);
				companyInfoPersistence.create(item);
			}
		}
	}

	public void setCompanyInfoPersistence(WhCompanyInfoPersistenceImpl companyInfoPersistence) {
		this.companyInfoPersistence = companyInfoPersistence;
	}

	public WhCompanyInfo findByUserId(long userId, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	public DaCompany loadCompanyByUserId(Long userId) {
		String hql = " from DaCompanyPass obj where obj.comUserId = ? and obj.deleted = false";
		List<DaCompanyPass> list = companyInfoPersistence.find(hql, new Long[]{userId}, null);
		if (list == null || list.size() == 0){
			return null;
		}
		return list.get(0).getDaCompany();
	}

	public List<WhCompanyInfo> loadCompanyInfoByUserId(long userId, Pagination pagination) throws Exception {
		String hql = " from WhCompanyInfo obj where obj.company.daCompanyPass.comUserId = ? and obj.deleted  = false ";
		List<WhCompanyInfo> list = companyInfoPersistence.find(hql, new Long[]{userId}, pagination);
		return list;
	}

	public boolean hasReported(long id) throws Exception {
		WhCompanyInfo info = this.findById(id);
		if (info == null || info.getState() == null || info.getState() == UN_REPORT){
			return false;
		}
		return true;
	}

	public boolean report(long id) throws Exception {
		WhCompanyInfo obj = this.findById(id);
		if (obj == null){
			return false;
		}
		obj.setState(REPORTED);
		companyInfoPersistence.update(obj);
		return true;
	}

	public boolean back(long id) throws Exception {
		WhCompanyInfo obj = this.findById(id);
		if (obj == null){
			return false;
		}
		obj.setState(UN_REPORT);
		companyInfoPersistence.update(obj);
		return true;
	}

	public String getAreaNameById(long id) throws Exception {
		String hql = "from FkArea obj where obj.areaCode = ? and obj.deleted = false";
		List<FkArea> list = companyInfoPersistence.find(hql, new Object[]{id}, null);
		
		if (list == null || list.size() == 0){
			return null;
		}
		return list.get(0).getAreaName();
	}
	
	/**
	 * 统计“生产储存”、“使用危险化学品构成重大危险源”两类企业的上报
	 */
	public List statistic(String year) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(distinct(da.id)) inhere, count(distinct(info.companyId)) num," +
				" map.area_code from (select fa.area_code, dip.id dip_id, dip.grade_path, dip.name" +
				" from fk_area fa full join (select id, name, grade_path from da_industry_parameter where is_deleted = 0" +
				" and depiction in ('anjian_zdwxy', 'anjian_sccc') and type = 1) dip on 1 != 2 where fa.is_deleted = 0 and fa.father_id =" +
				" (select id from fk_area where area_code = 330200000000)) map left join da_company_industry_rel dcir on dcir.par_da_ind_id = map.dip_id" +
				" left join (select dc.id, dc.second_area, dc.third_area from da_company dc left join da_company_pass dcp on dcp.par_da_com_id = dc.id" +
				" where dc.is_deleted = 0 and dcp.is_deleted = 0 and dcp.is_affirm = 1 and dc.create_time < to_date('"+ year +"-12-31', 'yyyy-MM-dd')" +
				" and dc.second_area != 0) da on da.second_area = map.area_code and da.id = dcir.par_da_com_id" +
				" left join (select companyId from WH_COMPANY_INFO where is_deleted = 0 and state = 1  " +
				"and year = "+ year +") info on info.companyId = da.id" +
				" group by map.area_code order by map.area_code ");
		//log.info(sql.toString());
		ResultSet res = companyInfoPersistence.findBySql(sql.toString());
		List<Statistic> statistics = new ArrayList<Statistic>();
		while (res.next()) {
			Statistic statistic = new Statistic();
			statistic.setInhere(res.getInt(1));
			statistic.setNumber(res.getInt(2));
			statistic.setAreaCode(res.getLong(3));
			//statistic.setIndustryId(res.getInt(4));
			//statistic.setEnumName(res.getString(5));
			statistics.add(statistic);
		}
		return statistics;
	}
	
	public boolean hasExistedByYear(int year, long companyId, long id){
		String hql = "select count(obj.id) from WhCompanyInfo obj where obj.year = ? and obj.deleted = false and obj.company.id = ? and obj.id != ?";
		List<Long> list = companyInfoPersistence.find(hql, new Object[]{year, companyId, id}, null);
		if (list == null || list.size() == 0 || list.get(0) <= 0){
			return false;
		}
		return true;
	}

	public List<DaCompany> loadReportedCompanyList(Long areaCode, int year , Pagination pagination) throws Exception {
		String hql = "select obj from WhCompanyInfo obj inner join obj.company c inner join c.hzTradeTypes yh " +
				"where obj.deleted = false and c.secondArea = ? and obj.year = ? and yh.depiction in ('anjian_zdwxy', 'anjian_sccc') " +
				" and obj.state = ? order by obj.id ";
		return companyInfoPersistence.find(hql, new Object[]{areaCode, year, WhCompanyInfoFacadeIface.REPORTED}, pagination);
	}
	
	
	/**
	 * 加载未上报企业
	 * @param areaCode
	 * @param year
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadUnreportCompanyList(Long areaCode, int year , Pagination pagination) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select pass from DaCompanyPass pass inner join pass.daCompany c inner join c.hzTradeTypes yh " +
				" where pass.deleted = false and c.deleted = false and c.secondArea = ? and ")
		.append("c.id not in (select obj.company.id from WhCompanyInfo obj where obj.deleted = false ")
		.append(" and obj.company.secondArea = ? and obj.year = ? and obj.state = ?) " +
				"and yh.depiction in ('anjian_zdwxy', 'anjian_sccc') order by c.id ");
		
		return companyInfoPersistence.find(hql.toString(), new Object[]{areaCode, areaCode, year, WhCompanyInfoFacadeIface.REPORTED}, pagination);
	}
	
	
	
}
