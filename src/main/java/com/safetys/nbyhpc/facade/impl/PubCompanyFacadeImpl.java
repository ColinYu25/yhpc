package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.TCoreBzh;
import com.safetys.nbyhpc.domain.model.TCoreXzxk;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;
import com.safetys.nbyhpc.facade.iface.PubCompanyFacadeIface;

public class PubCompanyFacadeImpl implements PubCompanyFacadeIface {
	
	private PubCompanyPersistenceIface pubCompanyPersistenceIface;
	/**
	 * 更新企业信息
	 * @param tableName 表名
	 * @param fieldName 字段名
	 * @param id 记录ID
	 * @param value 赋值
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean updateSqlVal(String tableName, String fieldName, int id, String value) throws ApplicationAccessException{
		boolean flag = false;
		String date = "";
		if("true".equals(value))
			value = "1";
		if("false".equals(value))
			value = "0";
		if (value.length() == 10) {
			if ("-".equals(value.substring(4, 5))){
				date = "to_date('" + value +"','yyyy-mm-dd')";
			}
		}
		try {
			if(!"".equals(tableName) && "t_core_company".equals(tableName)){
				if (null!=date && !"".equals(date)) {
					pubCompanyPersistenceIface.executeSQLUpdate("update " + tableName + " set " + fieldName + "=" + date +" where id=" + id);
				} else {
					pubCompanyPersistenceIface.executeSQLUpdate("update " + tableName + " set " + fieldName + "='" + value +"' where id=" + id);
				}
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public int executeSQLUpdate(String sql)throws ApplicationAccessException {
		return pubCompanyPersistenceIface.executeSQLUpdate(sql);
	}
	
	public Map<String, Object> loadCoreCompanyInfo(Long companyId) throws Exception {
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.ID, c.COMPANY_NAME, c.REG_ADDRESS, c.FIRST_AREA, c.SECOND_AREA, c.THIRD_AREA,c.FOUTH_AREA,");
		sql.append("c.ZIP_CODE, c.Na_ECO_LEVEL1,c.Na_ECO_LEVEL2,c.MANAGEMENT_LEVEL1,c.MANAGEMENT_LEVEL2,c.ECONOMIC_TYPE1,ECONOMIC_TYPE2,");
		sql.append("c.ESTABLISHMENT_DAY,c.BUSINESS_REG_NUM,c.ORG_CODE,c.REG_CAPITAL,c.YEAR_SALES,LEGAL_PERSON,FLOOR_AREA,c.PRINCIPAL_PERSON,");
		sql.append("PRINCIPAL_MOBILE,c.PRINCIPAL_TELEPHONE,c.FAX,c.IS_ENTERPRISE,c.IS_FOCUS_FIRE_UNITS,c.IS_FOCUS_TOWN_UNITS,c.EMPLOYEE_NUM,");
		sql.append("c.INSURED_NUM,c.BUSINESS_SCOPE,c.HAVE_SECURITY_ORG,c.SECURITY_PERSON,c.SPECIAL_BOILER,c.SPECIAL_PRESSURE_VESSEL,");
		sql.append("c.SPECIAL_LIFT,c.SPECIAL_LIFTING_MACHINE,c.SPECIAL_MOTOR_VEHICLE,c.ANP_RECORDS_DATE,c.ANP_VALIDITY_END,c.MAJOR_GRADE,");
		sql.append("c.MAJOR_RECORDS_DATE,c.EMERGENCY_RECORDS_DATE,c.EMERGENCY_DRILL,c.YH_QUARTER_REPORTS_DATE,c.YH_NO_MANAGE_NUM,c.YH_HIDDEN_DANGER_LICENSES,");
		sql.append("c.CHECK_JUSTICE_NUM,c.CHECK_DOCUMENT_NUM,c.CHECK_USUAL_NUM,c.CHECK_FIND_TROUBLE,c.ACC_NON_FIRE_DEATH_NUM,");
		sql.append("c.ACC_NON_FIRE_DEATHS,c.ACC_NON_FIRE_CASUALTIES,c.ACC_NON_FIRE_DEATH_LOSSES,c.ACC_NON_FIRE_INJURY_NUM,");
		sql.append("c.ACC_NON_LAST_ACCIDENT_RATE,c.ACC_NON_FIRE_INJURIES,c.ACC_NON_FIRE_INJURIE_LOSSES,c.ACC_FIRE_NUM,c.ACC_FIRE_DEATHS,");
		sql.append("c.ACC_FIRE_INJURIES,c.ACC_FIRE_LOSSES,c.CREDIT_GRADE,c.CREDIT_PROMISE_DATE,c.CREDIT_PERFORM_DATE");
		sql.append(" From T_CORE_COMPANY c where c.id = " + companyId);
		Map<String, Object> map = new HashMap();
		try{
			ResultSet res = pubCompanyPersistenceIface.findBySql(sql.toString());
			while (res.next()){
				map.put("company_id", res.getLong(1));
				map.put("company_name", res.getString(2));
				map.put("reg_address", res.getString(3));
				map.put("first_area", res.getLong(4));
				map.put("second_area", res.getLong(5));
				map.put("third_area", res.getLong(6));
				map.put("fouth_area", res.getLong(7));
				map.put("zip_code", res.getString(8));
				map.put("na_eco_level1", res.getString(9));
				map.put("na_eco_level2", res.getString(10));
				map.put("management_level1", res.getString(11));
				map.put("management_level2", res.getString(12));
				map.put("economic_type1", res.getString(13));
				map.put("economic_type2", res.getString(14));
				map.put("establishment_day", res.getString(15));
				map.put("business_reg_num", res.getString(16));
				map.put("org_code", res.getString(17));
				map.put("reg_capital", res.getFloat(18));
				map.put("year_sales", res.getFloat(19));
				map.put("legal_person", res.getString(20));
				map.put("floor_area", res.getFloat(21));
				map.put("principal_person", res.getString(22));
				map.put("principal_mobile", res.getString(23));
				map.put("principal_telephone", res.getString(24));
				map.put("fax", res.getString(25));
				map.put("is_enterprise", res.getBoolean(26));
				map.put("is_focus_fire_units", res.getBoolean(27));
				map.put("is_focus_town_units", res.getBoolean(28));
				map.put("employee_num", res.getInt(29));
				map.put("insured_num", res.getInt(30));
				map.put("business_scope", res.getString(31));
				map.put("have_security_org", res.getBoolean(32));
				map.put("security_person", res.getInt(33));
				map.put("special_boiler", res.getInt(34));
				map.put("special_pressure_vessel", res.getInt(35));
				map.put("special_lift", res.getInt(36));
				map.put("special_lifting_machine", res.getInt(37));
				map.put("special_motor_vehicle", res.getInt(38));
				map.put("anp_records_date", res.getString(39));
				map.put("anp_validity_end", res.getString(40));
				map.put("major_grade", res.getString(41));
				map.put("major_records_date", res.getString(42));
				map.put("emergency_records_date", res.getString(43));
				map.put("emergency_drill", res.getBoolean(44));
				map.put("yh_quarter_reports_date", res.getString(45));
				map.put("yh_no_manage_num", res.getInt(46));
				map.put("yh_hidden_danger_licenses", res.getInt(47));
				map.put("check_justice_num", res.getInt(48));
				map.put("check_document_num", res.getInt(49));
				map.put("check_usual_num", res.getInt(50));
				map.put("check_find_trouble", res.getBoolean(51));
				map.put("acc_non_fire_death_num", res.getInt(52));
				map.put("acc_non_fire_deaths", res.getInt(53));
				map.put("acc_non_fire_casualties", res.getInt(54));
				map.put("acc_non_fire_death_losses", res.getFloat(55));
				map.put("acc_non_fire_injury_num", res.getInt(56));
				map.put("acc_non_last_accident_rate", res.getFloat(57));
				map.put("acc_non_fire_injuries", res.getInt(58));
				map.put("acc_non_fire_injurie_losses", res.getFloat(59));
				map.put("acc_fire_num", res.getInt(60));
				map.put("acc_fire_deaths", res.getInt(61));
				map.put("acc_fire_injuries", res.getInt(62));
				map.put("acc_fire_losses", res.getFloat(63));
				map.put("credit_grade", res.getString(64));
				map.put("credit_promise_date", res.getString(65));
				map.put("credit_perform_date", res.getString(66));
				if(res.getLong(1)>0){
					List<TCoreXzxk> xzxks = new ArrayList<TCoreXzxk>();
					StringBuilder sql_xzxk = new StringBuilder();
					sql_xzxk.append("SELECT ID,COMPANY_ID,XK_TYPE,LICENCE,VALIDITY_END,PERMIT_SCOPE,USER_ID,IS_DELETED,CREATE_TIME,MODIFY_TIME FROM T_CORE_XZXK WHERE  IS_DELETED = 0 AND COMPANY_ID = ").append(res.getLong(1));
					ResultSet re_xzxk = pubCompanyPersistenceIface.findBySql(sql_xzxk.toString());
					while(re_xzxk.next()){
						TCoreXzxk xzxk = new TCoreXzxk();
						xzxk.setId(re_xzxk.getLong("ID"));
						xzxk.setXkType(re_xzxk.getString("XK_TYPE"));
						xzxk.setLicence(re_xzxk.getString("LICENCE"));
						xzxk.setValidityEnd(new Date(re_xzxk.getDate("VALIDITY_END").getTime()));
						xzxk.setPermitScope(re_xzxk.getString("PERMIT_SCOPE"));
//						xzxk.setUserId(Long.parseLong(re_xzxk.getString("USER_ID")));
//						xzxk.setCreateTime(new Date(re_xzxk.getDate("CREATE_TIME").getTime()));
//						xzxk.setModifyTime(new Date(re_xzxk.getDate("MODIFY_TIME").getTime()));
						xzxks.add(xzxk);
					}
					map.put("xzxks", xzxks);
					
					List<TCoreBzh> bzhs = new ArrayList<TCoreBzh>();
					StringBuilder sql_bzh = new StringBuilder();
					sql_bzh.append("SELECT ID,COMPANY_ID,BZH_TYPE,LICENCE,VALIDITY_END,BZH_GRADE,USER_ID,IS_DELETED,CREATE_TIME,MODIFY_TIME FROM T_CORE_BZH WHERE  IS_DELETED = 0 AND COMPANY_ID = ").append(res.getLong(1));
					ResultSet re_bzh = pubCompanyPersistenceIface.findBySql(sql_bzh.toString());
					while(re_bzh.next()){
						TCoreBzh bzh = new TCoreBzh();
						bzh.setId(re_bzh.getLong("ID"));
						bzh.setBzhType(re_bzh.getString("BZH_TYPE"));
						bzh.setLicence(re_bzh.getString("LICENCE"));
						bzh.setValidityEnd(new Date(re_bzh.getDate("VALIDITY_END").getTime()));
						bzh.setBzhGrade(re_bzh.getString("BZH_GRADE"));
//						bzh.setUserId(Long.parseLong(re_bzh.getString("USER_ID")));
//						bzh.setCreateTime(new Date(re_bzh.getDate("CREATE_TIME").getTime()));
//						bzh.setModifyTime(new Date(re_bzh.getDate("MODIFY_TIME").getTime()));
						bzhs.add(bzh);
					}
					map.put("bzhs", bzhs);
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public long ajaxAddXzxk(TCoreXzxk xzxk) throws ApplicationAccessException {
		long id = 0L;
		if(xzxk!=null && xzxk.getCoreCompany()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO T_CORE_XZXK (ID,COMPANY_ID,XK_TYPE,LICENCE,VALIDITY_END,PERMIT_SCOPE,IS_DELETED) VALUES (S_T_CORE_XZXK.Nextval,")
					.append(xzxk.getCoreCompany().getId()).append(",'")
					.append(xzxk.getXkType()).append("','")
					.append(xzxk.getLicence()).append("',to_date('").append(sdf.format(xzxk.getValidityEnd())).append("','yyyy-mm-dd'),'")
					.append(xzxk.getPermitScope()).append("',0)");
			int i = pubCompanyPersistenceIface.executeSQLUpdate(sql.toString());
			if(i==1){
				ResultSet re = pubCompanyPersistenceIface.findBySql("SELECT MAX(ID) FROM T_CORE_XZXK WHERE COMPANY_ID = "+xzxk.getCoreCompany().getId()+" AND IS_DELETED = 0");
				try {
					while(re.next()){
						id = re.getLong(1);
						break;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	public long ajaxAddBzh(TCoreBzh bzh) throws ApplicationAccessException {
		long id = 0L;
		if(bzh!=null && bzh.getCoreCompany()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO T_CORE_BZH (ID,COMPANY_ID,BZH_TYPE,LICENCE,VALIDITY_END,BZH_GRADE,IS_DELETED) VALUES (S_T_CORE_BZH.Nextval,")
					.append(bzh.getCoreCompany().getId()).append(",'")
					.append(bzh.getBzhType()).append("','")
					.append(bzh.getLicence()).append("',to_date('").append(sdf.format(bzh.getValidityEnd())).append("','yyyy-mm-dd'),'")
					.append(bzh.getBzhGrade()).append("',0)");
			int i = pubCompanyPersistenceIface.executeSQLUpdate(sql.toString());
			if(i==1){
				ResultSet re = pubCompanyPersistenceIface.findBySql("SELECT MAX(ID) FROM T_CORE_BZH WHERE COMPANY_ID = "+bzh.getCoreCompany().getId()+" AND IS_DELETED = 0");
				try {
					while(re.next()){
						id = re.getLong(1);
						break;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	public PubCompanyPersistenceIface getPubCompanyPersistenceIface() {
		return pubCompanyPersistenceIface;
	}
	public void setPubCompanyPersistenceIface(
			PubCompanyPersistenceIface pubCompanyPersistenceIface) {
		this.pubCompanyPersistenceIface = pubCompanyPersistenceIface;
	}
}
