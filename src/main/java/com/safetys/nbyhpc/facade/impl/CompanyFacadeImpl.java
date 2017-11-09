package com.safetys.nbyhpc.facade.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.MatchMode;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.domain.persistence.iface.AreaPersistenceIface;
import com.safetys.framework.domain.persistence.iface.EnumPersistenceIface;
import com.safetys.framework.domain.persistence.iface.RolePersistenceIface;
import com.safetys.framework.domain.persistence.iface.UserPersistenceIface;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.OrderProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.security.spring.encoding.PasswordEncoderIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaCompanyPassRel;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.model.TCaches;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.model.TCoreCompany;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyAcountPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPassRelPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.DepartmentPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PointTypePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.MonthQueryHelper;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.util.RoleType;
import com.safetys.nbyhpc.vo.CompanyVo;

public class CompanyFacadeImpl implements CompanyFacadeIface {

	private CompanyPersistenceIface companyPersistenceIface;

	private TCompanyPersistenceIface tcompanyPersistenceIface;

	private UserPersistenceIface userPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private AreaPersistenceIface areaPersistenceIface;

	private CompanyAcountPersistenceIface companyAcountPersistenceIface;

	private CompanyPassPersistenceIface companyPassPersistenceIface;

	private CompanyPassRelPersistenceIface companyPassRelPersistenceIface;

	private RolePersistenceIface rolePersistenceIface;

	private PasswordEncoderIface passwordEncoderIface;

	private PointTypePersistenceIface pointTypePersistenceIface;

	private PubCompanyPersistenceIface pubCompanyPersistenceIface;

	// 枚举service add by huangjl
	private EnumPersistenceIface enumPersistenceIface;

	private DepartmentPersistenceIface departmentPersistenceIface;

	// private ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface;

	// private VDaComIndRelPersistenceIface vdaComIndRelPersistenceIface;
	
	private ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface;

	/**
	 * 初始化新增企业造成的错误数据（临时使用，用完可以删除）
	 * 
	 * @Description:类的作用、修改内容、改进的Bug等
	 * @author:liulj
	 * @throws ApplicationAccessException
	 * @time:2013-10-16
	 */
	public String initErrorCompanyInfo(Long startId, Long endId) throws ApplicationAccessException {
//		System.out.println("DA_company  ==>更新  T_company");
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_  " + " where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) " + "and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id " + "from da_company_industry_rel dcir) and this_.IS_SYN=1 "));// 此处一部分一部分的执行。
		List<DaCompany> companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
//		System.out.println("company.size() : " + companys.size());
		if (companys.size() > 0) {
			for (int i = 0; i < companys.size(); i++) {
				DaCompany company = companys.get(i);
				uptOrIntTCompany(company);
			}
		}
		return null;
	}

	/**
	 * 新增企业
	 * 
	 * @Description:类的作用、修改内容、改进的Bug等
	 * @author:liulj
	 * @time:2013-10-16
	 */
	public String createCompany(DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException {
		List<DaCompany> coms = loadCompanyByName(company);
		if (coms != null && coms.size() > 0) { // 企业存在,但不是隐患企业
			company.setId(coms.get(0).getId());
			company.getDaCompanyPass().setId(coms.get(0).getId());
			return updateCompany(company, userDetail).toString();
		} else {
			if (validatorCompanyName(company.getCompanyName().trim(), null, company.getSetupNumber(), company.getRegNum())) {
				if (company.getHzTradeTypes() != null) {// 当创建的企业所选行业不为空
					if (userDetail.getUserIndustry() != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {// 其他部门用户添加企业时，该企业为审核通过状态
						if (company.getDaCompanyPass() != null)
							company.getDaCompanyPass().setAffirm(true);
						else
							return "manipulate.transgress";
					} else if (Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())) {
						// else if
						// (Nbyhpc.USER_INDUSTRY_AJJ.equals(userDetail.getUserIndustry())&&
						// validatorChooseOnlyIndustry(//
						// 安监用户添加企业时，没有选择其他部门的行业，则企业为审核通过状态
						// new
						// ArrayList<DaIndustryParameter>(company.getHzTradeTypes()),Nbyhpc.USER_INDUSTRY_AJJ))
						// {
						company.getDaCompanyPass().setAffirm(true);
					}
				}
				company.getDaCompanyPass().setDaCompany(company);

				// 给企业设置未定义的行业类别
				if (company != null && company.getTradeTypes() != null) {
					this.setUndefinedIndustry(company);
				}

				if (company.getCompanyCode() == null || "".equals(company.getCompanyCode())) {
					company.setCompanyCode(company.getSetupNumber());
				}

				if ("0".equals(company.getHaveRegNum())) {
					company.setRegNum(null);
				}
				Long id = companyPersistenceIface.createCompany(company);

				// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
				synDange(company);
				// ------------------同时更新TCompany表中的记录----------------------------------//

				uptOrIntTCompany(company);

				return id.toString();
			} else
				return "company.already.exists";
		}
	}

	/**
	 * 给企业设置未定义的行业类别
	 * 
	 * @author:huangjl
	 * @time:2014-03-11
	 */
	public void setUndefinedIndustry(DaCompany company) {

		// 首先获得行业类型字符串
		String tradeType = company.getTradeTypes();
		StringBuffer industrySql = new StringBuffer("from DaIndustryParameter parameter where parameter.id in (0");
		for (String trade : tradeType.split(",")) {
			if (Long.parseLong(trade.trim()) != -1L) {
				industrySql.append("," + trade.trim() + "");
			}
		}
		industrySql.append(")");

//		System.out.println("industrySql: " + industrySql);
		try {
			List<DaIndustryParameter> daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(industrySql.toString());
//			System.out.println("daIndustryParameters.size(): " + daIndustryParameters.size());
			// 循环得到部门的类型
			DaIndustryParameter firstDaIndustryParameter = null;
			if (daIndustryParameters != null && daIndustryParameters.size() > 0) {
				for (DaIndustryParameter daIndustryParameter : daIndustryParameters) {
					if (daIndustryParameter.getDaIndustryParameter() == null) {
						firstDaIndustryParameter = daIndustryParameter;
						break;
					}
				}

				if (firstDaIndustryParameter != null) {
					// 部门类型不为空的情况下，判断是否选择了二级行业类型。
					DaIndustryParameter secondDaIndustryParameter = null;
					for (DaIndustryParameter daIndustryParameter : daIndustryParameters) {
						DaIndustryParameter tempDaIndustryParameter = daIndustryParameter.getDaIndustryParameter();
						if (tempDaIndustryParameter != null) {
							if (firstDaIndustryParameter.getId().longValue() == tempDaIndustryParameter.getId().longValue()) {
								secondDaIndustryParameter = daIndustryParameter;

								break;
							}
						}
					}

					// 二级行业类型不存在的情况下，将其设置到一级部门对应的未定义类型。存在的情况下，再查看二级行业类型是否有对应的三级类型子类
					if (secondDaIndustryParameter != null) {
						// 二级行业类型不为空的情况下，判断是否选择了三级行业类型。
						DaIndustryParameter thirdDaIndustryParameter = null;

						for (DaIndustryParameter daIndustryParameter : daIndustryParameters) {
							DaIndustryParameter tempDaIndustryParameter = daIndustryParameter.getDaIndustryParameter();
							if (tempDaIndustryParameter != null) {
								if (secondDaIndustryParameter.getId().longValue() == tempDaIndustryParameter.getId().longValue()) {
									thirdDaIndustryParameter = daIndustryParameter;
									break;
								}
							}
						}
						if (thirdDaIndustryParameter == null) {
							// 不存在的情况,得到二级行业对应的未定义类型。未定义类型的code规律是：对应的父级的code+_undefined，可以根据这个规律来查找
							String code = secondDaIndustryParameter.getCode() + "_undefined";
							String getIndustryByCodeSql = "from DaIndustryParameter parameter where parameter.code='" + code + "'";
							List<DaIndustryParameter> undefinedDaIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(getIndustryByCodeSql);
							if (undefinedDaIndustryParameters != null && undefinedDaIndustryParameters.size() > 0) {
								tradeType += "," + undefinedDaIndustryParameters.get(0).getId();
								company.setTradeTypes(tradeType);
							}
						}
					} else {
						// 不存在的情况,得到一级行业部门对应的未定义类型。未定义类型的code规律是：对应的父级的code+_undefined，可以根据这个规律来查找
						String code = firstDaIndustryParameter.getCode() + "_undefined";
						String getIndustryByCodeSql = "from DaIndustryParameter parameter where parameter.code='" + code + "'";
						List<DaIndustryParameter> undefinedDaIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(getIndustryByCodeSql);
						if (undefinedDaIndustryParameters != null && undefinedDaIndustryParameters.size() > 0) {
							tradeType += "," + undefinedDaIndustryParameters.get(0).getId();
							company.setTradeTypes(tradeType);
						}

					}
				}

			}

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除企业信息 liulj 2013-10-25
	 */

	public String delCompanyHy(DaCompany company) throws ApplicationAccessException {
		// 删除前先插入数据
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp_str = sdf.format(dt);
//		System.out.println("temp_str:" + temp_str);

		List<Long> list = new ArrayList<Long>();
		ResultSet rs = null;
		Long mid = 0l;
		// 插入前先删除
		pubCompanyPersistenceIface.executeSQLUpdate("delete from DA_COMPANY_INDUSTRY_RELL t  where t.par_da_com_id=" + company.getId());
		String sql = "select PAR_DA_COM_ID,PAR_DA_IND_ID from DA_COMPANY_INDUSTRY_REL where PAR_DA_COM_ID=" + company.getId();
		rs = companyPersistenceIface.findBySql(sql);
		try {
			while (rs.next()) {
				Long parDaComId = rs.getLong("PAR_DA_COM_ID");
				Long parDaIndId = rs.getLong("PAR_DA_IND_ID");
				pubCompanyPersistenceIface.executeSQLUpdate("insert into DA_COMPANY_INDUSTRY_RELL(PAR_DA_COM_ID,PAR_DA_IND_ID) values(" + parDaComId + "," + parDaIndId + ")");
			}

			// 查询有无未治理的重大隐患
			// ResultSet res_bigdouble =
			// companyPersistenceIface.findBySql("select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person,t.user_id  from da_danger t where t.par_da_com_id = "
			// + company.getId() +
			// "  and t.id not in (select  d.par_da_dan_id   from da_danger_gorver d  where d.is_deleted=0)   and  t.is_deleted=0  and t.id  in  (select r.par_da_dan_id  from DA_ROLLCALL_COMPANY r) ");
			ResultSet res_bigdouble = companyPersistenceIface.findBySql("select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person,t.user_id  from da_danger t left join DA_ROLLCALL_COMPANY r on r.par_da_dan_id=t.id left join da_danger_gorver d on d.par_da_dan_id=t.id where t.par_da_com_id = " + company.getId() + "    and  t.is_deleted=0  and r.par_da_dan_id is  null and d.par_da_dan_id is  null ");
			// System.out.println("查询有无未治理的重大隐患: " +
			// "select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person from da_danger t where t.par_da_com_id = "
			// + oldCompany.getId() +
			// "  and t.id not in (select  d.par_da_dan_id   from da_danger_gorver d  where d.is_deleted=0)   and  t.is_deleted=0");
			while (res_bigdouble.next()) {
				ResultSet rr = companyPersistenceIface.findBySql("select id  from  da_danger_gorver  order  by  id desc");
				if (rr.next()) {
					mid = rr.getLong(1);
				}
				mid++;
				// 重大隐患未治理变已治理
				String mobile = res_bigdouble.getString(5);
				if (mobile == null)
					mobile = "";
				String s1 = "insert  into    da_danger_gorver(id,par_da_dan_id,danger_add,gorver_content,finish_date,money, user_id,link_man,link_tel,link_mobile,charge_person,fill_date,fill_man,IS_DELETED,MODIFY_TIME,CREATE_TIME,IS_EVALUATE,IS_EVALUATE_EXPERT,IS_EVALUATE_GOVERNMENT)  values(" + mid + "," + res_bigdouble.getInt(1) + ",'" + res_bigdouble.getString(2) + "','无',to_date('" + temp_str + "','yyyy-MM-dd')," + res_bigdouble.getString(6) + "," + res_bigdouble.getString(10) + ",'" + res_bigdouble.getString(3) + "','" + res_bigdouble.getString(4) + "','" + mobile + "','" + res_bigdouble.getString(9) + "',to_date('" + res_bigdouble.getString(7).substring(0, 11) + "','yyyy-MM-dd'),'" + res_bigdouble.getString(8) + "',0,sysdate,sysdate,1,0,0)";
				// System.out.println("重大隐患sql:" + s1);

				pubCompanyPersistenceIface.executeSQLUpdate(s1);

				// 将这些重大隐患信息的同步状态设置为未同步，再次同步
				String s2 = "update DA_DANGER set IS_SYNCHRO=1 where id=" + res_bigdouble.getLong(1) + "";
				pubCompanyPersistenceIface.executeSQLUpdate(s2);
			}

			// 查询有无未治理的一般隐患
			// System.out.println("一般隐患: " +
			// "select t.id from da_normal_danger t  where  t.par_da_com_id=" +
			// oldCompany.getId() +
			// " and t.is_repaired=0  and  t.is_deleted=0");
			ResultSet res_bigdouble1 = companyPersistenceIface.findBySql("select t.id from da_normal_danger t  where  t.par_da_com_id=" + company.getId() + " and t.is_repaired=0  and  t.is_deleted=0");
			while (res_bigdouble1.next()) {

				// 一般未治理变已治理,添加上同步状态为未同步的代码
				String hql = "update DaNomalDanger set repaired=1,completedDate=SYSDATE,isSynchro=1 where id=" + res_bigdouble1.getInt(1) + "";
				// System.out.println("hql: " + hql);
				pubCompanyPersistenceIface.executeHQLUpdate(hql);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		pubCompanyPersistenceIface.executeSQLUpdate("delete from DA_COMPANY_INDUSTRY_REL t  where t.par_da_com_id=" + company.getId());

		// 更新中心库的企业隐患信息
		company = companyPersistenceIface.loadCompany(company);
		if (company != null) {
			this.deleteYHOfTCompany(company);
		}

		return "";
	}

	/**
	 * 新增企业的时候同步新增中心数据库企业信息
	 * 
	 * @param companyId
	 * @throws ApplicationAccessException
	 */
	public void createCoreCompany(String companyId) {
		try {
			TCoreCompany com = new TCoreCompany();
			com.setId(Long.parseLong(companyId));
			if (null == pubCompanyPersistenceIface.loadCompany(com)) {// 中心数据库没有这家企业则新增（判断条件：企业ID与隐患排查系统的企业ID相等）
				DaCompany com_0 = companyPersistenceIface.loadCompany(new DaCompany(Long.parseLong(companyId)));
				if (null != com_0) {
					String sql = " insert into T_CORE_COMPANY(ID, COMPANY_NAME, REG_ADDRESS, PRINCIPAL_PERSON," + " PRINCIPAL_MOBILE, PRINCIPAL_TELEPHONE," + " FAX, BUSINESS_REG_NUM, ORG_CODE," + " IS_ENTERPRISE, EMPLOYEE_NUM," + " IS_FOCUS_FIRE_UNITS," + " BUSINESS_SCOPE," + " IS_GATHER_DATA)" + " values " + " (" + com_0.getId() + ", '" + com_0.getCompanyName() + "', '" + com_0.getRegAddress() + "', '" + com_0.getFddelegate() + "'," + " '" + com_0.getPhoneCode() + "', ''," + " '', '" + com_0.getRegNum() + "', '" + com_0.getSetupNumber() + "'," + " '" + ((com_0.getDaCompanyPass() != null && com_0.getDaCompanyPass().isEnterprise()) ? 1 : 0) + "', '" + com_0.getId() + "'," + " '" + (com_0.getEmployeeNumber() != null ? com_0.getEmployeeNumber() : 0) + "'," + " '" + ((com_0.getFocusFireUnits() != null && com_0.getFocusFireUnits() == 1) ? 1 : 0) + "'," + " '" + com_0.getBusinessScope() + "')";
//					System.out.println("sql: " + sql);
					pubCompanyPersistenceIface.executeSQLUpdate(sql);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCompanyPassword(FkUser fkUser) throws ApplicationAccessException {
		FkUser temp = this.userPersistenceIface.loadUser(fkUser.getId());

		String encodeNewPass = this.passwordEncoderIface.encodePassword(fkUser.getUserPass());

		temp.setUserPass(encodeNewPass);

		temp.setUserName(fkUser.getUserName());

		this.userPersistenceIface.updateUser(temp);
	}

	public void delCache(TCaches cache) throws ApplicationAccessException {
		MemCached ch = MemCached.getInstance();
		ch.delete(cache.getName());
	}

	public boolean getUserByUserName(String userName) throws Exception {

		// ResultSet rs = null;
		//
		// String sql = "select ID from FK_USER where USER_NAME='" + userName +
		// "'  and is_deleted=0";
		// rs = companyPersistenceIface.findBySql(sql);
		// if (rs.next()) {
		//
		// // 如果这个用户名存在，还要判断这个用户名是否有效，有效了则不能再添加，无效的话，删除这个用户信息(现需求)
		//
		// // 如果这个用户名存在,将原来删除(需求变动)
		//
		// //
		// companyPersistenceIface.findBySql("update FK_USER   set is_deleted=1 ,USER_NAME='"+userName+"_del'    where USER_NAME='"
		// // + userName + "'  and is_deleted=0");
		//
		// return true;
		// }
		//
		// return false;

		ResultSet rs = null;
		ResultSet rs1 = null;
		String sql = "select ID from FK_USER where USER_NAME='" + userName + "'  and is_deleted=0";
		rs = companyPersistenceIface.findBySql(sql);
		if (rs.next()) {

			// 如果这个用户名存在，还要判断这个用户名是否有效，有效了则不能再添加，无效的话，删除这个用户信息(现需求)

			sql = "select t.ID from DA_COMPANY t, DA_COMPANY_PASS p, FK_USER f,da_company_industry_rel  r  where t.id = p.par_da_com_id and f.id = p.com_user_id   and r.par_da_com_id=t.id   and p.com_user_id=" + rs.getLong(1);
			rs1 = companyPersistenceIface.findBySql(sql);
			if (rs1.next()) {
				return true;
			} else {

				companyPersistenceIface.findBySql("update FK_USER   set is_deleted=1 ,USER_NAME='" + userName + "_del'    where USER_NAME='" + userName + "'  and is_deleted=0");
				return false;

			}
			// 如果这个用户名存在,将原来删除(需求变动)

			// companyPersistenceIface.findBySql("update FK_USER   set is_deleted=1 ,USER_NAME='"+userName+"_del'    where USER_NAME='"
			// + userName + "'  and is_deleted=0");

		}

		return false;

	}

	public void updateCompanyUserName(FkUser fkUser) throws ApplicationAccessException {
		FkUser temp = this.userPersistenceIface.loadUser(fkUser.getId());
		temp.setUserName(fkUser.getUserName());
		this.userPersistenceIface.updateUser(temp);
	}

	/**
	 * 添加企业用户名
	 * 
	 * @param fkUser
	 * @param fkUserInfo
	 * @param roleIds
	 * @throws ApplicationAccessException
	 */
	private void createUser(FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkRole.class);
		detachedCriteriaProxy.add(RestrictionsProxy.in("id", roleIds));
		List<FkRole> fkRoles = rolePersistenceIface.loadRoles(detachedCriteriaProxy);
		Set<FkRole> set = new HashSet<FkRole>(fkRoles);
		fkUser.setFkRoles(set);
		String encodePass = passwordEncoderIface.encodePassword(fkUser.getUserPass());
		fkUser.setUserPass(encodePass);
		fkUserInfo.setFkUser(fkUser);
		fkUser.setFkUserInfo(fkUserInfo);
		userPersistenceIface.createUser(fkUser);
	}

	/**
	 * 将企业用户名ID与企业信息关联
	 * 
	 * @param company
	 * @param fkUser
	 * @param fkUserInfo
	 * @param roleIds
	 * @throws ApplicationAccessException
	 */
	private void updateCompanyPassByUserId(DaCompany company, FkUser fkUser) throws ApplicationAccessException {
		String hql = "update DaCompanyPass set comUserId = " + fkUser.getId() + "  where id = " + company.getId();
		companyPersistenceIface.executeHQLUpdate(hql);
	}

	/**
	 * 添加企业用户名
	 * 
	 * @param company
	 * @param fkUser
	 * @throws ApplicationAccessException
	 */
	public void createUserForCompany(DaCompany company, FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds) throws ApplicationAccessException {
		createUser(fkUser, fkUserInfo, roleIds);
		updateCompanyPassByUserId(company, fkUser);
	}

	/**
	 * 查询企业用户名
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkUser loadCompanyUserName(FkUser fkUser) throws ApplicationAccessException {
		return userPersistenceIface.loadUser(fkUser.getId());
	}

	/**
	 * 根据userName得到fkUser对象
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkUser loadCompanyUserName(String userName) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkUser.class, "user");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("user.userName", userName));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("user.deleted", false));
		if (userPersistenceIface.loadUsers(detachedCriteriaProxy).size() != 0) {
			FkUser user = userPersistenceIface.loadUsers(detachedCriteriaProxy).get(0);
			return user;

		} else {
			return null;
		}

	}

	/**
	 * 根据userName得到fkUser对象
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public Map<String, Object> loadCompanyUserInfoBySql(String userName) throws ApplicationAccessException, SQLException {
		Map<String, Object> userInfo = null;
		String sql = "select t.id,uinfo.user_industry from fk_user t inner join fk_user_info uinfo on t.id=uinfo.id where (t.user_name='" + userName + "'  or  t.cas_user_name='" + userName + "'   ) and t.is_deleted=0 and uinfo.is_deleted=0";
//		System.out.println("sql: "+sql);
		ResultSet rs = companyPersistenceIface.findBySql(sql);
		if (rs.next()) {
			userInfo = new HashMap<String, Object>();
			userInfo.put("industry", rs.getString(2));
			// 根据角色判断这家企业用户是否是管道用户
			String sql2 = "select rol.role_name,rol.role_code from fk_user_role_rel rel left join fk_role rol on rel.role_id=rol.id  where rel.user_id=" + rs.getLong(1) + "  and rol.role_code='ROLE_PIPECOMPANY'";
			ResultSet rs2 = companyPersistenceIface.findBySql(sql2);
			if (rs2.next()) {
				userInfo.put("isPipecompany", "1");
			} else {
				userInfo.put("isPipecompany", "0");
				// 不是管道用户，还要判断企业是否删除了
				String sql3 = " select rel.par_da_com_id from da_company_industry_rel rel left join da_company_pass pa on rel.par_da_com_id=pa.par_da_com_id where pa.is_deleted=0 and pa.is_affirm='1' and pa.com_user_id=" + rs.getLong(1) + "";
				ResultSet rs3 = companyPersistenceIface.findBySql(sql3);
				if (rs3.next()) {
					userInfo.put("tradeTypes", "1");
				} else {
					userInfo.put("tradeTypes", "0");
				}
				rs3.close();
			}
			rs2.close();
		}
		rs.close();

		return userInfo;
	}
	
	
	
	/**
	 * 根据工商注册号和组织机构代码获得有效的用户信息
	 * 
	 * @param fkUser
	 * @return
	 * @throws SQLException 
	 * @throws ApplicationAccessException 
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public String loadCompanyUser(String regNum,String setupNumber) throws ApplicationAccessException, SQLException  {
		String userName=null;
		//先根据工商注册号查找用户信息
		if(regNum!=null&&!"".equals(regNum)){
			userName=this.getCompanyUser(regNum);
		}

		if(userName!=null){
			//如果查询到了有效的用户信息，返回用户名
			return userName;
		}else{
			//如果查询不到，再根据组织机构编码查询
			if(setupNumber!=null&&!"".equals(setupNumber)){
				userName=this.getCompanyUser(setupNumber);
			}
			if(userName!=null){
				return userName;
			}else{
				return null;
			}
		}
	}

	
	/**
	 * 根据传人值得到用户信息
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public String getCompanyUser(String u) throws ApplicationAccessException, SQLException {
		//定义用户名变量
		String userName=null;
		//先传人值查找用户信息
		String sql = "select t.id,t.user_name from fk_user t inner join fk_user_info uinfo on t.id=uinfo.id where (t.user_name='" + u + "'  or  t.cas_user_name='" + u + "'   ) and t.is_deleted=0 and uinfo.is_deleted=0 and uinfo.user_industry='qiye' ";
//		System.out.println("sql: "+sql);
		ResultSet rs = companyPersistenceIface.findBySql(sql);
		while (rs.next()) {
			// 根据角色判断这家企业用户是否是管道用户
			String sql2 = "select rol.role_name,rol.role_code from fk_user_role_rel rel left join fk_role rol on rel.role_id=rol.id  where rel.user_id=" + rs.getLong(1) + "  and rol.role_code='ROLE_PIPECOMPANY'";
			ResultSet rs2 = companyPersistenceIface.findBySql(sql2);
			if (rs2.next()) {
                //是有效管道用户信息，取当前用户名，跳出循环
				userName= rs.getString(2);
				break;
			} else {
				//不是有效管道用户的话，判断这个用户是否是有效的隐患企业用户
				String sql3 = " select rel.par_da_com_id from da_company_industry_rel rel left join da_company_pass pa on rel.par_da_com_id=pa.par_da_com_id where pa.is_deleted=0 and pa.is_affirm='1' and pa.com_user_id=" + rs.getLong(1) + "";
				ResultSet rs3 = companyPersistenceIface.findBySql(sql3);
				if (rs3.next()) {
					//有效的企业用户,取当前用户名，跳出循环
					userName= rs.getString(2);
					break;
				} 
				rs3.close();
			}
			rs2.close();
		}
		rs.close();
		return userName;
	}
	/**
	 * 根据userName得到fkUser对象
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public String loadCompanyUserIndustry(String userName) throws ApplicationAccessException, SQLException {
		String industry = "";
		String sql = "select uinfo.user_industry from fk_user t inner join fk_user_info uinfo on t.id=uinfo.id and t.user_name='" + userName + "' and t.is_deleted=0 and uinfo.is_deleted=0";
		ResultSet rs = companyPersistenceIface.findBySql(sql);
		if (rs.next()) {
			if (rs.getString(1) != null) {
				industry = rs.getString(1).toString();
			}

		}
		rs.close();
		return industry;
	}

	/**
	 * 根据用户id获得企业信息
	 * 
	 * @param Long
	 * @return DaCompany
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompanyByUserId(Long userId) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId", userId));
		if (companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null).size() != 0) {
			DaCompanyPass daCompanyPass = companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null).get(0);
			return daCompanyPass.getDaCompany();
		} else {
			return null;
		}

	}

	/**
	 * 查询企业列表
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompanies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		// detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		// detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		List<DaCompany> companys;
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		return companys;
	}

	public List<DaCompany> loadCompanyList(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
			if (company.getOff() != null) {
				if (company.getOff() != 1) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_com_id from da_company_logout where is_deleted=0 and type=1)"));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_logout where is_deleted=0 and type=" + company.getOff() + ")"));
				}
			}
			if (company.getAffrim() != null) {
				if (company.getAffrim() != 1) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm =1)"));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_pass where is_deleted=0 and is_affirm=" + company.getAffrim() + ")"));
				}
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.deleted", false));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	/**
	 * 取消确认
	 */
	public void deleteCompanyPass(String ids) throws ApplicationAccessException {
		DaCompanyPass companyPass = null;
		for (int i = 0; i < ids.split(",").length; i++) {
			companyPass = loadCompanyPassByCompanyId(Long.parseLong(ids.split(",")[i]));
			companyPassPersistenceIface.deleteCompanyPass(companyPass);
		}
	}

	public void updateAffirm(String ids, Boolean state) throws ApplicationAccessException {
		for (int i = 0; i < ids.split(",").length; i++) {
			DaCompanyPass companyPass = loadCompanyPassByCompanyId(Long.parseLong(ids.split(",")[i]));
			companyPass.setAffirm(state);
			companyPassPersistenceIface.updateCompanyPass(companyPass);
		}
	}

	public boolean isAllowDelete(String ids) throws ApplicationAccessException {
		List<Long> list = loadBagCompanyIds();
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < ids.split(",").length; j++) {
				if (Long.parseLong(ids.split(",")[j]) == list.get(i)) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	private List<Long> loadBagCompanyIds() throws ApplicationAccessException {
		List<Long> list = new ArrayList<Long>();
		ResultSet rs = null;
		String sql = "select PAR_DA_COM_ID from DA_BAG_COMPANY_REL where IS_DELETED=0";
		rs = companyPersistenceIface.findBySql(sql);
		try {
			while (rs.next()) {
				list.add(rs.getLong("PAR_DA_COM_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private DaCompanyPass loadCompanyPassByCompanyId(Long id) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.daCompany.id", id));
		if (companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null).size() != 0) {
			return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null).get(0);
		} else {
			return null;
		}
	}

	/**
	 * 加载已确认企业
	 */
	public List<DaCompany> loadCheckedCompanies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry());
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
		}

		// detachedCriteriaProxy
		// .add(RestrictionsProxy
		// .sqlRestriction("this_.id not in (select t.PAR_DA_COM_ID from DA_BAG_COMPANY_REL t where IS_DELETED=0)"));
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.modifyTime"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	/**
	 * 加载企业异地经营台帐
	 */
	public List<DaCompanyAcount> loadCompanyAcounts(DaCompany company, DaCompanyAcount companyAcount, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyAcount.class, "dca");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dca.daCompany.id", company.getId()));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dca.modifyTime"));
		return companyAcountPersistenceIface.loadCompanyAcounts(detachedCriteriaProxy, pagination);
	}

	/**
	 * 创建异地经营台帐
	 */
	public void createCompanyAcount(DaCompanyAcount companyAcount) throws ApplicationAccessException {
		companyAcountPersistenceIface.createCompanyAcount(companyAcount);
		DaCompany company = companyPersistenceIface.loadCompany(companyAcount.getDaCompany());
		company.setFirstArea(companyAcount.getFirstArea());
		company.setSecondArea(companyAcount.getSecondArea());
		company.setThirdArea(companyAcount.getThirdArea());
		companyPersistenceIface.mergeCompany(company);
	}

	/**
	 * 确认企业
	 */
	public void createCompanyPass(String companyIds) throws ApplicationAccessException {
		DaCompany company = null;
		for (int i = 0; i < companyIds.split(",").length; i++) {
			DaCompanyPass companyPass = new DaCompanyPass();
			company = companyPersistenceIface.loadCompany(new DaCompany(Long.parseLong(companyIds.split(",")[i].trim())));
			if (company.getDaCompanyPass() != null) {
				company.getDaCompanyPass().setDaCompany(company);
			} else {
				company.setDaCompanyPass(companyPass);
				company.getDaCompanyPass().setDaCompany(company);
			}
			companyPersistenceIface.updateCompany(company);
		}
	}

	/**
	 * 加载未确认企业
	 */
	public List<DaCompany> loadUnCheckedCompanies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry());
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dcir.par_da_com_id from da_company_pass dcir )"));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select dcir.par_da_com_id from da_company_logout dcir )"));
		// List<Long> logoutIds = loadCompanyLogoutIds();
		// List<Long> passIds = loadCompanyPassIds();
		// if (logoutIds.size() != 0) {
		// detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy
		// .in("dc.id", logoutIds)));
		// }
		// if (passIds.size() != 0) {
		// detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy
		// .in("dc.id", passIds)));
		// }
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.modifyTime"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	private List<Long> loadCompanyLogoutIds() throws ApplicationAccessException {
		ResultSet rs = null;
		List<Long> ids = new ArrayList<Long>();
		String sql = "select PAR_DA_COM_ID from DA_COMPANY_LOGOUT where IS_DELETED=0";
		rs = companyPersistenceIface.findBySql(sql);
		try {
			while (rs.next()) {
				ids.add(rs.getLong("PAR_DA_COM_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	private List<Long> loadCompanyPassIds() throws ApplicationAccessException {
		ResultSet rs = null;
		List<Long> ids = new ArrayList<Long>();
		String sql = "select PAR_DA_COM_ID from DA_COMPANY_PASS where IS_DELETED=0";
		rs = companyPersistenceIface.findBySql(sql);
		try {
			while (rs.next()) {
				ids.add(rs.getLong("PAR_DA_COM_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public List<DaCompany> loadCompanyByName(DaCompany company) throws ApplicationAccessException {
		String sql = "";
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (company != null) {

			sql += "( 1=2 ";
			if (company.getSetupNumber() != null && !"".equals(company.getSetupNumber().trim())) {
				sql += " or   this_.setup_Number='" + company.getSetupNumber().trim() + "' ";
			}
			if (company.getRegNum() != null && !"".equals(company.getRegNum().trim())) {
				sql += " or   this_.regNum='" + company.getRegNum().trim() + "' ";
			}

			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				sql += " or   this_.COMPANY_NAME='" + company.getCompanyName().trim() + "' ";
			}

			sql += ")";

		}

		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in (select par_da_com_id from da_company_industry_rel) "));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	/**
	 * 验证安监部门是否选择本部门行业，并且未选择其他部门的行业时，企业信息为确认状态
	 * 
	 * @param industries
	 * @param industryDepic
	 * @return
	 */
	private boolean validatorChooseOnlyIndustry(List<DaIndustryParameter> industries, String industryDepic) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
		String ids = "";

		for (DaIndustryParameter industry : industries) {
			ids += "".equals(ids) ? industry.getId() : "," + industry.getId();
		}
		String[] cids = ids.split(",");
		Long[] dcIds = new Long[cids.length];
		for (int i = 0; i < cids.length; i++) {
			dcIds[i] = Long.valueOf(cids[i]);
		}
		detachedCriteriaProxy.add(RestrictionsProxy.isNull("dip.daIndustryParameter"));
		detachedCriteriaProxy.add(RestrictionsProxy.in("dip.id", dcIds));
		List<DaIndustryParameter> indus = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
		for (DaIndustryParameter ind : indus) {
			if (ind.getDepiction() != null && !industryDepic.equals(ind.getDepiction())) {// 判断行业描述不为空，并且行业描述不匹配当前用户的行业
				return false;
			}
		}
		return true;
	}

	public String deleteCompanies(String ids, UserDetailWrapper userDetail) throws ApplicationAccessException {
		Set<FkRole> roles = userPersistenceIface.loadUser((long) userDetail.getUserId()).getFkRoles();
		boolean isAdmin = RoleType.isRoleByCode(RoleType.ADMINISTRATOR, roles) || RoleType.isRoleByCode(RoleType.MANAGER, roles);
		if (isAdmin) {
			for (int i = 0; i < ids.split(",").length; i++) {
				companyPersistenceIface.deleteCompany(new DaCompany(Long.parseLong(ids.split(",")[i].trim())));
			}
			return null;
		} else {// 非管理员在删除企业时，只允许删除此用户添加的企业单位
			Long[] idList = new Long[ids.split(",").length];
			for (int i = 0; i < ids.split(",").length; i++) {
				idList[i] = Long.parseLong(ids.split(",")[i].trim());
			}
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
			detachedCriteriaProxy.add(RestrictionsProxy.in("id", idList));
			List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
			String noAllowCompany = "";
			for (DaCompany company : companies) {// 验证必须删除本用户添加的企业
				// if (company.getUserId() == null
				// || !company.getUserId().equals(
				// (long) (userDetail.getUserId()))) {
				// noAllowCompany += noAllowCompany.equals("") ? company
				// .getCompanyName() : "," + company.getCompanyName();
				// } else {
				companyPersistenceIface.deleteCompany(company);
				// }
			}
			return noAllowCompany;
		}
	}

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException {
		return companyPersistenceIface.loadCompany(company);
	}

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompanyPass.class, "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.comUserId", (long) userDetail.getUserId()));
		return companyPassPersistenceIface.loadCompanyPasses(detachedCriteriaProxy, null);
	}

	public List<DaCompany> loadUnAuditingCompanise(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", false));
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userIndustry, Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id not in " + "(select dcpr.par_da_com_id from da_company_pass_rel dcpr where dcpr.is_deleted=0 and dcpr.is_pass=1 and dcpr.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		} else {
			return null;
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	public void doAuditingCompanyise(String companyIds, UserDetailWrapper userDetail) throws ApplicationAccessException {
		for (int i = 0; i < companyIds.split(",").length; i++) {
			DaCompanyPassRel companyPassRel = new DaCompanyPassRel();
			DaCompany company = new DaCompany(Long.valueOf(companyIds.split(",")[i].trim()));
			company = companyPersistenceIface.loadCompany(company);
			companyPassRel.setDaCompany(company);
			companyPassRel.setDaIndustryParameter(tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry()));
			companyPassRel.setPass(true);
			companyPassRel.setContent("审核通过");
			companyPassRelPersistenceIface.creatCompanyPassRel(companyPassRel);
			company.getDaCompanyPass().setAffirm(true);
			companyPersistenceIface.mergeCompany(company);
			/**
			 * try { DetachedCriteriaProxy detachedCriteria =
			 * DetachedCriteriaProxy.forClass(DaCompanyPassRel.class);
			 * detachedCriteria
			 * .add(RestrictionsProxy.sqlRestriction("this_.par_da_com_id="
			 * +companyIds.split(",")[i].trim())); List<DaCompanyPassRel> dcprs
			 * = companyPassRelPersistenceIface.loadCompanyPassRels(
			 * detachedCriteria, null); DetachedCriteriaProxy
			 * detachedCriteriaProxy =
			 * DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
			 * detachedCriteriaProxy
			 * .add(RestrictionsProxy.isNotNull("dip.depiction"));
			 * detachedCriteriaProxy.add(RestrictionsProxy .sqlRestriction(
			 * "this_.id in (select PAR_DA_IND_ID from da_company_industry_rel where PAR_DA_COM_ID="
			 * + companyIds.split(",")[i].trim() + ")"));
			 * List<DaIndustryParameter> dips =
			 * tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy,
			 * null); } catch (Exception e) { e.printStackTrace(); }
			 */
		}
	}

	/**
	 * 修改日期 2013-12-27 修改人 maying
	 * 
	 * @return
	 */
	public List<DaCompany> loadCompaniesAffirm(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("dc.daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		List<DaCompany> companys;
		if (company != null) {

			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFddelegate() != null && !"".equals(company.getFddelegate().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.fdDelegate", company.getFddelegate().trim(), MatchMode.ANYWHERE));
			}
			// 高风险作业查询条件--start
			if (company.getIsHighRiskWork() != null && !"".equals(company.getIsHighRiskWork().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.isHighRiskWork", company.getIsHighRiskWork().trim()));
			}
			if (company.getHighRiskWork() != null && !"".equals(company.getHighRiskWork().trim())) {
				// String[] highRiskWork=company.getHighRiskWork().split(";");
				// if(highRiskWork!=null&&highRiskWork.length>0){
				// for(int i=0;i<highRiskWork.length;i++){
				// detachedCriteriaProxy.add(RestrictionsProxy.like("dc.highRiskWork",
				// highRiskWork[i].trim(), MatchMode.ANYWHERE));
				// }
				// }
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.highRiskWork", company.getHighRiskWork().trim()));
			}
			// 高风险作业查询条件--end
			if (company.getUuid() != null && !"".equals(company.getUuid().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.uuid", company.getUuid().trim()));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
				if (company.getSecondArea() != null && company.getSecondArea() != 0L) {
//					System.out.println("r" + company.getSecondArea());
					// 二级区域为其他的时候，code=330285000000，则查询以及区域存在，二级区域为空的情况。

					if (company.getSecondArea() != null && "330285000000".equals(company.getSecondArea().toString())) {

						detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.SECOND_AREA is null or this_.SECOND_AREA=0)"));
					} else {
						detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", company.getSecondArea()));
						if (company.getThirdArea() != null && company.getThirdArea() != 0L) {

							if (company.getThirdArea().toString().trim().equals("100000000000")) {

								detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.third_area is null or this_.third_area=0)"));
							} else {

								detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", company.getThirdArea()));
							}

							if (company.getFouthArea() != null && company.getFouthArea() != 0L) {
								// 添加FOUTH_AREA为空的判断start
								if ("200000000000".equals(company.getFouthArea().toString().trim())) {
									detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("(this_.FOUTH_AREA is null or this_.FOUTH_AREA=0)"));
								} else {
									detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", company.getFouthArea()));
									if (company.getFifthArea() != null && company.getFifthArea() != 0L) {
										detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", company.getFifthArea()));
									}
								}
								// end

							}
						}
					}

				}
			}
			if (company.getTradeTypes() != null && !"".equals(company.getTradeTypes())) {
				// String tradeTypess = company.getTradeTypes();
				String trade = "";
				for (int i = company.getTradeTypes().split(",").length - 1; i >= 0; i--) {
					if (!"".equals(company.getTradeTypes().split(",")[i].trim())) {
						trade = company.getTradeTypes().split(",")[i].trim();
						break;
					}
				}
				if (!"".equals(trade)) {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select par_da_com_id from da_company_industry_rel where    par_da_com_id=this_.id and   par_da_ind_id in (" + trade + "))"));
				}
			}
			
			if (company.getRegNum() != null && !company.getRegNum().trim().equals("")) {
				if (company.getType() == 1) { // 工商注册号
					detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regNum", company.getRegNum().trim(), MatchMode.ANYWHERE));
				} else if (company.getType() == 2) { // 组织机构代码
					detachedCriteriaProxy.add(RestrictionsProxy.like("dc.setupNumber", company.getRegNum().trim(), MatchMode.ANYWHERE));
				}
			}
			
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
			// System.out.println("company.getProductionScale(): "+company.getProductionScale());
			if (company.getProductionScale() != null && !"".equals(company.getProductionScale().trim()) && !company.getProductionScale().equals("0")) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.productionScale", company.getProductionScale().trim(), MatchMode.ANYWHERE));
			}
		}
		
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)&&!"admin".equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userIndustry, Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where  dcir.par_da_com_id=this_.id  and    dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists " + "(select dcir.par_da_com_id from da_company_industry_rel dcir  where  dcir.par_da_com_id=this_.id)"));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("createTime"));
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		for (DaCompany com : companys) {
			if (com.getDaCompanyPass() != null) {
				if (com.getDaCompanyPass().getComUserId() != null && com.getDaCompanyPass().getComUserId() != 0) {
					FkUser fkUser = new FkUser();
					fkUser.setId(com.getDaCompanyPass().getComUserId());
					fkUser = loadCompanyUserName(fkUser);
					if (fkUser != null) {
//						System.out.println("fkUser.isDeleted(): " + fkUser.isDeleted());
						com.setUserName(fkUser.getUserName());
					} else {
						com.setUserName("");
					}
				} else {
					com.setUserName("");
				}
			} else {
				com.setUserName("");
			}

			com.setRegAddress((com.getRegAddress() != null && !com.getRegAddress().equals("null")) ? com.getRegAddress() : "");
			com.setCompanyName((com.getCompanyName() != null && !com.getCompanyName().equals("null")) ? com.getCompanyName() : "");
			com.setFddelegate((com.getFddelegate() != null && !com.getFddelegate().equals("null")) ? com.getFddelegate() : "");
			com.setPhoneCode((com.getPhoneCode() != null && !com.getPhoneCode().equals("null")) ? com.getPhoneCode() : "");

		}
		return companys;

	}

	public String updateCompany(DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DaCompany com = loadCompany(company);
		if (!company.getCompanyName().trim().equals(com.getCompanyName())) {
			if (!validatorCompanyName(company.getCompanyName().trim(), company.getId(), company.getSetupNumber(), company.getRegNum())) {
				return "company.already.exists";
			}
		}
		Long mid = 0l;
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp_str = sdf.format(dt);

		DaCompany oldCompany = loadCompany(company);
		String userIndustry = userDetail.getUserIndustry();
		if (company.getUserId() == null)
			company.setUserId(oldCompany.getUserId());
		else if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {// 除管理员外用户在修改权限范围内的企业信息时，不得修改此企业单位对应的外部门行业。
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
			// String gradeWhere = "";
			// gradeWhere +=
			// " grade_path like (select grade_path||'%' from da_industry_parameter where depiction='"+
			// userIndustry + "' and type="+Nbyhpc.COMPANY_TRADE+") ";
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.depiction = '"
			// + userIndustry + "' "+ gradeWhere));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" grade_path like (select grade_path||'%' from da_industry_parameter where depiction='" + userIndustry + "' and type=" + Nbyhpc.COMPANY_TRADE + ") "));
			List<DaIndustryParameter> induSons = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);// 当前用户拥有权限操作的行业分类集合
			ArrayList<DaIndustryParameter> tempIndus = new ArrayList<DaIndustryParameter>();
			for (DaIndustryParameter indu : oldCompany.getHzTradeTypes()) {
				for (DaIndustryParameter ind : induSons) {
					if (ind.getId().equals(indu.getId())) {
						tempIndus.add(indu);
					}
				}
			}
			oldCompany.getHzTradeTypes().removeAll(tempIndus);
			company.getHzTradeTypes().addAll(oldCompany.getHzTradeTypes());
		}
		if (company.getHzTradeTypes() != null) {
			if (company.getDaCompanyPass() != null && company.getDaCompanyPass().getId() != null) {
				Long companyUserId = null;
				try {
					ResultSet pa = companyPersistenceIface.findBySql("select pa.com_user_id  from  da_company_pass pa where pa.par_da_com_id='" + company.getDaCompanyPass().getId() + "' and pa.is_deleted=0 ");
					if (pa.next()) {
						if (pa.getObject(1) != null) {
							companyUserId = Long.parseLong(pa.getObject(1).toString());
						}

					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (companyUserId != null) {
					company.getDaCompanyPass().setComUserId(companyUserId);
				}
				company.getDaCompanyPass().setAffirm(true);
			} else {
				company.getDaCompanyPass().setId(-1L);
			}

			// liulj 如果行业删除,则将原行业存入DA_COMPANY_INDUSTRY_RELl(数据交换)
			// 行业删除时,判断该行业下的一般隐患及重大隐患是否已治理,如未治理,则设为已治理

			if (company.getHzTradeTypes().size() <= 0) {
				Iterator<?> iterator = oldCompany.getHzTradeTypes().iterator();
				while (iterator.hasNext()) {
					try {
						DaIndustryParameter d = (DaIndustryParameter) iterator.next();
						// 查询有无未治理的重大隐患
						ResultSet res_bigdouble = companyPersistenceIface.findBySql("select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person,t.user_id from da_danger t where t.par_da_com_id = " + oldCompany.getId() + "  and t.id not in (select  d.par_da_dan_id   from da_danger_gorver d  where d.is_deleted=0)   and  t.is_deleted=0");
						// System.out.println("查询有无未治理的重大隐患: " +
						// "select t.id,t.danger_add,t.link_man,t.link_tel,t.link_mobile,t.govern_money,t.fill_date,t.fill_man,t.charge_person from da_danger t where t.par_da_com_id = "
						// + oldCompany.getId() +
						// "  and t.id not in (select  d.par_da_dan_id   from da_danger_gorver d  where d.is_deleted=0)   and  t.is_deleted=0");
						if (res_bigdouble.next()) {
							ResultSet rr = companyPersistenceIface.findBySql("select id  from  da_danger_gorver  order  by  id desc");
							if (rr.next()) {
								mid = rr.getLong(1);
							}
							mid++;
							// 重大隐患未治理变已治理

							String s1 = "insert  into    da_danger_gorver t  (id,t.par_da_dan_id,t.danger_add,t.gorver_content,t.finish_date,t.money, t.user_id,t.link_man,t.link_tel,t.link_mobile,t.charge_person,t.fill_date,t.fill_man)  values(" + mid + "," + res_bigdouble.getInt(1) + ",'" + res_bigdouble.getString(2) + "','无',to_date('" + temp_str + "','yyyy-MM-dd')," + res_bigdouble.getString(6) + "," + res_bigdouble.getString(10) + ",'" + res_bigdouble.getString(3) + "','" + res_bigdouble.getString(4) + "','" + res_bigdouble.getString(5) + "','" + res_bigdouble.getString(9) + "',to_date('" + res_bigdouble.getString(7).substring(0, 11) + "','yyyy-MM-dd'),'" + res_bigdouble.getString(8) + "')";
							// System.out.println("重大隐患sql:" + s1);

							companyPersistenceIface.findBySql(s1);

						}

						// 查询有无未治理的一般隐患
						// System.out.println("一般隐患: " +
						// "select t.id from da_normal_danger t  where  t.par_da_com_id="
						// + oldCompany.getId() +
						// " and t.is_repaired=0  and  t.is_deleted=0");
						ResultSet res_bigdouble1 = companyPersistenceIface.findBySql("select t.id from da_normal_danger t  where  t.par_da_com_id=" + oldCompany.getId() + " and t.is_repaired=0  and  t.is_deleted=0");
						if (res_bigdouble1.next()) {

							// 一般未治理变已治理
							String hql = "update DaNomalDanger set repaired=1,completedDate=SYSDATE where id=" + res_bigdouble1.getInt(1) + "";
							// System.out.println("hql: " + hql);
							companyPersistenceIface.executeHQLUpdate(hql);

						}

						companyPersistenceIface.findBySql("insert  into    DA_COMPANY_INDUSTRY_RELL  (PAR_DA_COM_ID,PAR_DA_IND_ID)  values(" + oldCompany.getId() + "," + d.getId() + ")");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
		company.getDaCompanyPass().setDaCompany(company);
		// 创建时间取新的创建时间
		// company.setCreateTime(oldCompany.getCreateTime());
		companyPersistenceIface.mergeCompany(company);

		// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
		synDange(company);

		// ------------------同时更新TCompany表中的记录----------------------------------//
		uptOrIntTCompany(company);
		return company.getId().toString();
	}

	public String updateCompany(DaCompany company, TCompany tcompany, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DaCompany com = loadCompany(company);
		if (!company.getCompanyName().trim().equals(com.getCompanyName())) {
			if (!validatorCompanyName(company.getCompanyName().trim(), company.getId(), company.getSetupNumber(), company.getRegNum())) {
				return "company.already.exists";
			}
		}
		DaCompany oldCompany = loadCompany(company);
		String userIndustry = userDetail.getUserIndustry();
		if (company.getUserId() == null)
			company.setUserId(oldCompany.getUserId());
		else if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_COMPANY.equals(userIndustry)) {// 除管理员外用户在修改权限范围内的企业信息时，不得修改此企业单位对应的外部门行业。
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
			// String gradeWhere = "";
			// gradeWhere +=
			// " grade_path like (select grade_path||'%' from da_industry_parameter where depiction='"+
			// userIndustry + "' and type="+Nbyhpc.COMPANY_TRADE+") ";
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.depiction = '"
			// + userIndustry + "' "+ gradeWhere));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" grade_path like (select grade_path||'%' from da_industry_parameter where depiction='" + userIndustry + "' and type=" + Nbyhpc.COMPANY_TRADE + ") "));
			List<DaIndustryParameter> induSons = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);// 当前用户拥有权限操作的行业分类集合
			ArrayList<DaIndustryParameter> tempIndus = new ArrayList<DaIndustryParameter>();
			for (DaIndustryParameter indu : oldCompany.getHzTradeTypes()) {
				for (DaIndustryParameter ind : induSons) {
					if (ind.getId().equals(indu.getId())) {
						tempIndus.add(indu);
					}
				}
			}
			oldCompany.getHzTradeTypes().removeAll(tempIndus);
			company.getHzTradeTypes().addAll(oldCompany.getHzTradeTypes());
		}
		if (company.getHzTradeTypes() != null) {
			if (company.getDaCompanyPass() != null && company.getDaCompanyPass().getId() != null) {
				company.getDaCompanyPass().setAffirm(true);
			} else {
				company.getDaCompanyPass().setId(-1L);
			}

			// liulj 如果行业删除,则将原行业存入DA_COMPANY_INDUSTRY_RELl(数据交换)
			if (company.getHzTradeTypes().size() <= 0) {
				Iterator iterator = oldCompany.getHzTradeTypes().iterator();
				while (iterator.hasNext()) {
					DaIndustryParameter d = (DaIndustryParameter) iterator.next();
					companyPersistenceIface.findBySql("insert  into    DA_COMPANY_INDUSTRY_RELL  (PAR_DA_COM_ID,PAR_DA_IND_ID)  values(" + oldCompany.getId() + "," + d.getId() + ")");
				}
			}
		}
		company.getDaCompanyPass().setDaCompany(company);
		company.setCreateTime(oldCompany.getCreateTime());
		// 给企业设置未定义的行业类别
		if (company != null && company.getTradeTypes() != null)
			this.setUndefinedIndustry(company);

		if (company.getCompanyCode() == null || "".equals(company.getCompanyCode())) {
			company.setCompanyCode(company.getSetupNumber());
		}

		if ("0".equals(company.getHaveRegNum())) {
			company.setRegNum(null);
		}
		companyPersistenceIface.mergeCompany(company);

		// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
		synDange(company);

		// ------------------同时更新TCompany表中的记录----------------------------------//

		uptOrIntTCompany(company);

		return company.getId().toString();
	}

	public void updateCompanyLevel(DaCompany company) throws ApplicationAccessException {
		DaCompany oldCompany = loadCompany(company);
		oldCompany.setDaPointTypes(company.getDaPointTypes());
		oldCompany.setCompanyPoint(company.getCompanyPoint());
		oldCompany.setCompanyLevel(companyLevel(company));
		companyPersistenceIface.mergeCompany(oldCompany);
	}

	/**
	 * 计算企业级别
	 * 
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	private String companyLevel(DaCompany company) throws ApplicationAccessException {
		String level = "";
		if (company != null) {
			if (company.getCompanyPoint() >= 80) {
				level = "A";
			} else if (company.getCompanyPoint() < 80 && company.getCompanyPoint() >= 60) {
				level = "B";
			} else if (company.getCompanyPoint() < 60 && company.getCompanyPoint() >= 40) {
				level = "C";
			} else if (company.getCompanyPoint() < 40) {
				level = "D";
			}
		}
		return level;
	}

	/**
	 * 查询分级评估企业列表
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompaniesForLevel(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getCompanyLevel() != null && !"".equals(company.getCompanyLevel().trim())) {
				String[] levels = new String[company.getCompanyLevel().split(",").length];
				;
				for (int i = 0; i < company.getCompanyLevel().split(",").length; i++) {
					levels[i] = company.getCompanyLevel().split(",")[i].trim();
				}
				detachedCriteriaProxy.add(RestrictionsProxy.in("dc.companyLevel", levels));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);
			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	/**
	 * 根据企业名称及序号查看是否重复企业名称
	 * 
	 * @param companyName
	 * @param id
	 * @return
	 * @throws ApplicationAccessException
	 */
	private boolean validatorCompanyName(String companyName, Long id, String setupNumber, String regNum) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		if (id != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("companyName", companyName));

			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id  in (select   r.par_da_com_id   from  da_company_industry_rel  r)"));
		} else {
			String sql = "( 1=2  ";

			if (companyName != null && !companyName.trim().equals("")) {
				sql += "  or  this_.company_Name='" + companyName.trim() + "'";
			}
			if (setupNumber != null && !setupNumber.trim().equals("")) {
				sql += "  or  this_.setup_Number='" + setupNumber.trim() + "'";
			}
			if (regNum != null && !regNum.trim().equals("")) {
				sql += "  or  this_.regNum='" + regNum.trim() + "'";
			}
			sql += ")";
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sql));

		}

		if (id != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.not(RestrictionsProxy.eq("id", id)));
		}

		List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		if (companies.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public List<DaIndustryParameter> loadTradeTypesForCompanyLevel() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "htt");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("par_da_ind_id in (select id from da_industry_parameter where depiction='company_level')"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("sort"));
		return tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
	}

	public List<DaPointType> loadTradePointTypesForTradeType() throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaPointType.class, "wtpt");
		detachedCriteriaProxy.addOrder(OrderProxy.asc("wtpt.daIndustryParameter.id"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("wtpt.sortNum"));
		return pointTypePersistenceIface.loadPointTypes(detachedCriteriaProxy, null);
	}

	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException {
		// liulj 加入缓存
		List<DaIndustryParameter> inparm = null;
		String userIndustry = "";
		if (userDetail != null) {
			userIndustry = userDetail.getUserIndustry();
		}
		MemCached cache = MemCached.getInstance();
		String cacheName = "loadTradeTypesForCompany_" + userIndustry;
		// cache.delete(cacheName);
		if (cache.get(cacheName) != null) {
//			System.out.println("缓存查询条件中行业: " + cacheName);
			inparm = (List<DaIndustryParameter>) cache.get(cacheName);
		} else {
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.COMPANY_TRADE));

			if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_COMPANY.equals(userIndustry)) { // &&
				detachedCriteriaProxy.add(RestrictionsProxy.eq("depiction", userIndustry));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.code not like  '%" + Nbyhpc._UNDEFINED + "'"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sort"));
			inparm = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
			cache.add(cacheName, inparm);
		}
		return inparm;
	}

	// loadTradeTypesForCompany(UserDetailWrapper userDetail) 功能一样 自动缓存所用
	public List<DaIndustryParameter> loadTradeTypesForCompany1(String userIndustry) throws ApplicationAccessException {
		// liulj 加入缓存
		List<DaIndustryParameter> inparm = null;
		MemCached cache = MemCached.getInstance();
		String cacheName = "loadTradeTypesForCompany_" + userIndustry;
		// cache.delete(cacheName);
		if (cache.get(cacheName) != null) {
//			System.out.println("缓存查询条件中行业: " + cacheName);
			inparm = (List<DaIndustryParameter>) cache.get(cacheName);
		} else {
			DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaIndustryParameter.class, "dip");
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dip.type", Nbyhpc.COMPANY_TRADE));

			if (userIndustry != null && !"".equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry) && !Nbyhpc.USER_INDUSTRY_COMPANY.equals(userIndustry)) { // &&
				detachedCriteriaProxy.add(RestrictionsProxy.eq("depiction", userIndustry));
			}
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.par_da_ind_id is null"));
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.code not like  '%" + Nbyhpc._UNDEFINED + "'"));
			detachedCriteriaProxy.addOrder(OrderProxy.asc("sort"));
			inparm = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy, null);
			cache.add(cacheName, inparm);
		}
		return inparm;
	}

	public List<FkArea> loadAreas(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.father_id = (select id from fk_area where area_code=" + areaCode + ")"));
		detachedCriteriaProxy.addOrder(OrderProxy.asc("gradePath"));
		return areaPersistenceIface.loadAreas(detachedCriteriaProxy);
	}

	public FkArea loadArea(Long areaCode) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(FkArea.class, "fa");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("areaCode", areaCode));
		List<FkArea> areas = areaPersistenceIface.loadAreas(detachedCriteriaProxy);
		if (areas != null && areas.size() == 1) {
			return areas.get(0);
		}
		return null;
	}

	public FkArea loadArea(UserDetailWrapper userDetail) throws ApplicationAccessException {
		long areaCode = -1L;
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			areaCode = userDetail.getFifthArea();
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			areaCode = userDetail.getFouthArea();
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			areaCode = userDetail.getThirdArea();
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			areaCode = userDetail.getSecondArea();
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			areaCode = userDetail.getFirstArea();
		} else {
			return null;
		}
		return loadArea(areaCode);
	}

	public DaCompany loadCompanyByCompanyName(DaCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("companyName", company.getCompanyName()));
		List<DaCompany> companies = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		if (companies != null && companies.size() > 0) {
			company = companies.get(0);
		}
		return company;
	}

	public List<DaCompany> loadCompanyByCompanyName(DaCompany company, Pagination pagination) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.add(RestrictionsProxy.like("companyName", company.getCompanyName(), MatchMode.ANYWHERE));
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	public void checkDuplicateUser(String userName) throws ApplicationAccessException {
		// companyPersistenceIface.executeHQLUpdate(hql);
		String userID = "";
		ResultSet res = companyPersistenceIface.findBySql(" select id from fk_user where  user_name='" + userName + "'"); // da_company
		try {
			if (res.next()) { // 有重复
				userID = res.getString(1);
				// 判断在DA_COMPANY_PASS中是否有数据 有则存在用户名, 没有 则删除该记录 表示不存在该用户名
				ResultSet res1 = companyPersistenceIface.findBySql(" select com_user_id from DA_COMPANY_PASS where  com_user_id=" + userID); // da_company
				if (res.next()) {

					throw new ApplicationAccessException("User with this name[" + userName + "] already exists.");
				} else {
					pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + userName + "'");
					return;
				}
			} else {
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='"
		// + company.getBusinessRegNum() + "'");

		// if (((fkUsers =
		// this.userPersistenceIface.loadUsers(detachedCriteriaProxy)) == null)
		// || (fkUsers.size() <= 0)) {
		// return; //无重复
		// }

		throw new ApplicationAccessException("User with this name[" + userName + "] already exists.");
	}

	/**
	 * 查询分类评估企业列表
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompaniesForClassic(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		detachedCriteriaProxy.createCriteria("daCompanyPass", "dcp");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("dcp.affirm", true));
		if (company != null) {
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.companyName", company.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (company.getCompanyLevel() != null && !"".equals(company.getCompanyLevel().trim())) {
				String[] levels = new String[company.getCompanyLevel().split(",").length];
				;
				for (int i = 0; i < company.getCompanyLevel().split(",").length; i++) {
					levels[i] = company.getCompanyLevel().split(",")[i].trim();
				}
				detachedCriteriaProxy.add(RestrictionsProxy.in("dc.companyLevel", levels));
			}
			if (company.getRegAddress() != null && !"".equals(company.getRegAddress().trim())) {
				detachedCriteriaProxy.add(RestrictionsProxy.like("dc.regAddress", company.getRegAddress().trim(), MatchMode.ANYWHERE));
			}
			if (company.getFirstArea() != null && company.getFirstArea() != 0L) {
				detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", company.getFirstArea()));
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
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (" + trade + "))"));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in (select par_da_com_id from da_company_industry_rel where par_da_ind_id in (404800))")); // 默认机械

				}
			}
			if (company.getOrderProperty() != null) {
				String orderProperty = company.getOrderProperty();
				detachedCriteriaProxy.addOrder(company.isOrderType() ? OrderProxy.asc(orderProperty) : OrderProxy.desc(orderProperty));
			}
		}
		if (userDetail.getFifthArea() != null && !userDetail.getFifthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fifthArea", userDetail.getFifthArea()));
		} else if (userDetail.getFouthArea() != null && !userDetail.getFouthArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.fouthArea", userDetail.getFouthArea()));
		} else if (userDetail.getThirdArea() != null && !userDetail.getThirdArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.thirdArea", userDetail.getThirdArea()));
		} else if (userDetail.getSecondArea() != null && !userDetail.getSecondArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.secondArea", userDetail.getSecondArea()));
		} else if (userDetail.getFirstArea() != null && !userDetail.getFirstArea().equals(0L)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("dc.firstArea", userDetail.getFirstArea()));
		}
		String userIndustry = userDetail.getUserIndustry();
		if (userIndustry != null && !"".equals(userDetail.getUserIndustry()) && !Nbyhpc.USER_INDUSTRY_AJJ.equals(userIndustry)) {
			DaIndustryParameter industryParamenter = tradeTypePersistenceIface.loadTradeType(userDetail.getUserIndustry(), Nbyhpc.COMPANY_TRADE);

			if (industryParamenter != null) {
				detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id in " + "(select dcir.par_da_com_id from da_company_industry_rel dcir where dcir.par_da_ind_id =" + industryParamenter.getId() + ")"));
			} else {
				return null;
			}
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("dc.id"));

		// ---------------------村级区域处理(批量操作)
		// // FK_AREA表中169个街道 f=1 已处理 f=2 正处理 f is null 未处理
		// String insert = "";
		// long xh = 17992580;
		// String sql =
		// "select t.area_name, t.id,t.father_id  from FK_AREA t  where t.father_id in  (select t.id from FK_AREA t where t.father_id = 3020)  and t.is_deleted =0  and  t.f=2   order  by  t.area_name  desc";
		// System.out.println("sql: " + sql);
		// ResultSet rel = companyPersistenceIface.findBySql(sql);
		// try {
		// while (rel.next()) { // 对应t_area中的街道
		// String sql1 =
		// "select t.name,t.code from T_AREA t where t.pid in  (select t.id from T_AREA t where t.pid in ( select t.id from T_AREA t where t.pid=5322) and t.name  ='"
		// + rel.getString(1) + "'  )";
		// System.out.println("sql1: " + sql1);
		// ResultSet rel1 = companyPersistenceIface.findBySql(sql1);
		// while (rel1.next()) {
		// insert =
		// " insert  into fk_area (father_id,area_code,area_name,grade_path,is_deleted)values("
		// + rel.getLong(2) + "," + rel1.getLong(2) + ",'" + rel1.getString(1) +
		// "','/0/1/3020/" + rel.getLong(3) + "/" + rel.getLong(2) + "/" + xh +
		// "',0) ";
		// System.out.println("insert: "+insert);
		// companyPersistenceIface.findBySql(insert);
		// xh++;
		// }
		//
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// -----------------------------

		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setUserPersistenceIface(UserPersistenceIface userPersistenceIface) {
		this.userPersistenceIface = userPersistenceIface;
	}

	public void setAreaPersistenceIface(AreaPersistenceIface areaPersistenceIface) {
		this.areaPersistenceIface = areaPersistenceIface;
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public void setCompanyPassRelPersistenceIface(CompanyPassRelPersistenceIface companyPassRelPersistenceIface) {
		this.companyPassRelPersistenceIface = companyPassRelPersistenceIface;
	}

	public CompanyAcountPersistenceIface getCompanyAcountPersistenceIface() {
		return companyAcountPersistenceIface;
	}

	public void setCompanyAcountPersistenceIface(CompanyAcountPersistenceIface companyAcountPersistenceIface) {
		this.companyAcountPersistenceIface = companyAcountPersistenceIface;
	}

	public CompanyPassPersistenceIface getCompanyPassPersistenceIface() {
		return companyPassPersistenceIface;
	}

	public void setCompanyPassPersistenceIface(CompanyPassPersistenceIface companyPassPersistenceIface) {
		this.companyPassPersistenceIface = companyPassPersistenceIface;
	}

	public List<DaCompany> loadCheckedCompanies(DaCompany daCompany, Pagination pagination) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DaCompany> loadUnCheckedCompanies(DaCompany daCompany, Pagination pagination) throws ApplicationAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public int loadCompanyCountByArea(FkArea area) {
		int answer = 0;
		String sql = "select count(*) from da_company_pass where par_da_com_id in ( " + "select id from da_company where ( "
		// + first_area = area.getAreaCode()or
				+ "  second_area = " + area.getAreaCode() + " or third_area = " + area.getAreaCode() + ") and is_deleted = 0 and second_Area != 0 and third_area != 0 )  ";
		try {
//			System.out.println("sql: " + sql);
			ResultSet res = companyPersistenceIface.findBySql(sql);
			if (res.next()) {
				answer = res.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return answer;
	}

	public List<DaCompany> loadCompanysByArea(FkArea area, Pagination pagination, String type) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "dc");
		List<DaCompany> companys;

		// System.out.println(" area.getAreaCode(): "+ area.getAreaCode());

		if (area != null && area.getAreaCode() != null) {
			// System.out.println("4444444");
			detachedCriteriaProxy.add(RestrictionsProxy.or(RestrictionsProxy.eq("dc.firstArea", area.getAreaCode()), RestrictionsProxy.or(RestrictionsProxy.eq("dc.secondArea", area.getAreaCode()), RestrictionsProxy.eq("dc.thirdArea", area.getAreaCode()))));
		}
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("id in " + "(select par_da_com_id from da_company_pass " + " where is_deleted = 0)" + " and second_area != 0" + " and third_area  != 0"));
		// =====================加上行业分类
		if (type != null) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("id in  (select di.par_da_com_id  from  da_company_industry_rel di where  di.par_da_ind_id = " + type + " ) "));
		}
		detachedCriteriaProxy.addOrder(OrderProxy.desc("id"));
		companys = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, pagination);
		return companys;
	}

	public TCompany loadCoreCompany(TCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(TCompany.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("deleted", false));

		Integer flag = company.getFlag();
		String businessRegNum = company.getBusinessRegNum();
		String orgCode = company.getOrgCode();

		if (flag == 1) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("businessRegNum", businessRegNum));
		} else {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("orgCode", orgCode));
		}

		company = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy);
		if (company == null) {
			DetachedCriteriaProxy2 detachedCriteriaProxy2 = DetachedCriteriaProxy2.forClass(TCompany.class);
			detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", true));
			if (flag == 1) {
				detachedCriteriaProxy2.add(RestrictionsProxy.eq("businessRegNum", businessRegNum));
			} else {
				detachedCriteriaProxy2.add(RestrictionsProxy.eq("orgCode", orgCode));
			}
			company = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy2);
		}

		return company;

	}
	
	
	public TCompany getTCompany(TCompany company) throws ApplicationAccessException {
		List<TCompany> list=this.loadTCompany(company);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	

	public List<TCompany> loadTCompany(TCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(TCompany.class);
		detachedCriteriaProxy.add(RestrictionsProxy.eq("deleted", false));
		
		if(company.getCompanyName()!=null&&!"".equals(company.getCompanyName().trim())){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("companyName", company.getCompanyName().trim()));
		}
		if(company.getBusinessRegNum()!=null&&!"".equals(company.getBusinessRegNum().trim())){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("businessRegNum", company.getBusinessRegNum().trim()));
		}
		if(company.getOrgCode()!=null&&!"".equals(company.getOrgCode().trim())){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("orgCode", company.getOrgCode().trim()));
		}
		if(company.getUuid()!=null&&!"".equals(company.getUuid().trim())){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("uuid", company.getUuid().trim()));
		}
		if(company.getId()!=null&&company.getId()>0){
			detachedCriteriaProxy.add(RestrictionsProxy.eq("id", company.getId()));
		}
		
		return tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}
	
	
	
	

	public String loadCompanyInfo(TCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class);
		DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaCompany.class);
		DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(DaCompany.class);
		Long companyId = -1l;
		Long userId = -1l;
		Long com_user_id = 0l;
		String date = "";
		String hy = ""; // 二级行业
		String businessRegNum = "";
		String establishmentDay = "";
		if (company.getEstablishmentDay() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			establishmentDay = "to_date('" + format.format(company.getEstablishmentDay()) + "','yyyy-MM-dd')";
		}
		if (company.getManagementLevel1() != null && !company.getManagementLevel1().equals("") && !company.getManagementLevel1().equals("null")) {
			if (company.getManagementLevel1().equals("123")) { // 烟花爆竹
				hy = "1401";
			} else if (company.getManagementLevel1().equals("122")) { // 非煤矿山
				hy = "1400";
			} else if (company.getManagementLevel1().equals("AJ04")) { // 冶金行业
				hy = "404802";
			} else if (company.getManagementLevel1().equals("AJ05")) { // 有色金属
				hy = "404801";
			} else if (company.getManagementLevel1().equals("AJ07")) { // 机械制造
				hy = "404800";
			} else if (company.getManagementLevel1().equals("AJ09")) { // 纺织行业
				hy = "404803";
			} else if (company.getManagementLevel1().equals("121")) { // 危险化学品
				hy = "1390";
			} else if (company.getManagementLevel1().equals("AJ12")) { // 其它行业
				hy = "434589";
			}
		}

		int enterprise = 0;
		String sql1 = "";
		String flag = "";// 返回信息
		// 当前日期
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		date = matter1.format(dt);

		// modify by huangjl --首先修改企业为隐患企业
		String t_sql = " update T_COMPANY t set t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
		pubCompanyPersistenceIface.executeSQLUpdate(t_sql);

		// 先根据uuid查找
		DaCompany daCompany = null;
		if (company.getUuid() != null) {
			detachedCriteriaProxy2.add(RestrictionsProxy.eq("uuid", company.getUuid().trim()));
			daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy2);
		}
		// modify by huangjl --先根据企业名称查找，再根据工商注册号

		if (daCompany == null) {
			detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", company.getCompanyName().trim()));
			daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy1);
			if (daCompany == null) {
				if (company.getFlag() == 1) {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("regNum", company.getBusinessRegNum()));
				} else {
					detachedCriteriaProxy.add(RestrictionsProxy.eq("setupNumber", company.getOrgCode()));
				}
				daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy);
			}

		}

		// System.out.println("daCompany: " + daCompany);
		if (daCompany != null) { // 隐患系统中已有该企业 更新
//			System.out.println("隐患系统中已有该企业 更新");
			String hql = " update DaCompany  set is_syn=1, companyName='" + company.getCompanyName() + "',secondArea='" + company.getSecondArea() + "',uuid='" + company.getUuid() + "'";
			if (company.getThirdArea() != null && !company.getThirdArea().equals("")) {
				hql += " ,thirdArea='" + company.getThirdArea() + "'";
			}
			if (company.getFouthArea() != null && !company.getFouthArea().equals("")) {
				hql += " ,fouth_area='" + company.getFouthArea() + "'";
			}
			if (company.getBusinessRegNum() != null && !company.getBusinessRegNum().equals("")) {
				hql += " ,regNum='" + company.getBusinessRegNum() + "'";

			}

			if (company.getOrgCode() != null && !company.getOrgCode().equals("") && !company.getOrgCode().equals("null")) {
				hql += " ,setupNumber='" + (company.getOrgCode() != null ? company.getOrgCode() : "") + "'";

			}
			// if (company.getLegalPerson() != null &&
			// !company.getLegalPerson().equals("")) {
			// hql += " ,fddelegate='" + (company.getLegalPerson() != null ?
			// company.getLegalPerson() : "") + "'";
			// }

			// 判断主要负责人
			if (company.getPrincipalPerson() != null && !"".equals(company.getPrincipalPerson()) && !"null".equals(company.getPrincipalPerson())) {
				hql += " ,fddelegate='" + (company.getPrincipalPerson() != null ? company.getPrincipalPerson() : "") + "'";
			}
			// 判断主要负责人联系电话
			if (company.getPrincipalTelephone() != null && !"".equals(company.getPrincipalTelephone()) && !"null".equals(company.getPrincipalTelephone())) {
				hql += " ,phoneCode='" + (company.getPrincipalTelephone() != null ? company.getPrincipalTelephone() : "") + "'";
			}

			if (company.getProductionScale() != null && !company.getProductionScale().equals("")) {
				hql += " ,productionScale='" + (company.getProductionScale() != null ? company.getProductionScale() : "") + "'";
			}

			if (company.getSafetyMngPerson() != null && !company.getSafetyMngPerson().equals("")) {
				hql += " ,SAFETY_MANAGEMENT_PERSON='" + (company.getSafetyMngPerson() != null ? company.getSafetyMngPerson() : "") + "'";
			}
			if (company.getSafetyMngPersonPhone() != null && !company.getSafetyMngPersonPhone().equals("")) {
				hql += " ,SAFETY_MANAGEMENT_PERSON_PHONE='" + (company.getSafetyMngPersonPhone() != null ? company.getSafetyMngPersonPhone() : "") + "'";
			}
			if (company.getFocusFireUnits() != null) {
				hql += " ,IS_FOCUS_FIRE_UNITS=" + company.getFocusFireUnits() + "";
			}

			if (company.getEstablishmentDay() != null && !company.getEstablishmentDay().equals("")) {
				hql += " ,ESTABLISHMENT_DAY=" + (establishmentDay != null ? establishmentDay : "") + "";
			}
			if (company.getBusinessScope() != null && !company.getBusinessScope().equals("")) {
				hql += " ,BUSINESS_SCOPE='" + (company.getBusinessScope() != null ? company.getBusinessScope() : "") + "'";
			}
			if (company.getRegAddress() != null && !company.getRegAddress().equals("")) {
				hql += " ,REG_ADDRESS='" + (company.getRegAddress() != null ? company.getRegAddress() : "") + "'";
			}
			if (company.getBusinessAddress() != null && !company.getBusinessAddress().equals("")) {
				hql += " ,address='" + (company.getBusinessAddress() != null ? company.getBusinessAddress() : "") + "'";
			}
			if (company.getNationalEconomicType1() != null && !company.getNationalEconomicType1().equals("")) {
				hql += " ,NATIONAL_ECONOMIC_TYPE1='" + (company.getNationalEconomicType1() != null ? company.getNationalEconomicType1() : "") + "'";
			}
			if (company.getNationalEconomicType2() != null && !company.getNationalEconomicType2().equals("")) {
				hql += " ,NATIONAL_ECONOMIC_TYPE2='" + (company.getNationalEconomicType2() != null ? company.getNationalEconomicType2() : "") + "'";
			}
			if (company.getEconomicType1() != null && !company.getEconomicType1().equals("")) {
				hql += " ,ECONOMIC_TYPE1='" + (company.getEconomicType1() != null ? company.getEconomicType1() : "") + "'";
			}
			if (company.getEconomicType2() != null && !company.getEconomicType2().equals("")) {
				hql += " ,ECONOMIC_TYPE2='" + (company.getEconomicType2() != null ? company.getEconomicType2() : "") + "'";
			}

			hql += " ,CREATE_TIME =sysdate where  id=" + daCompany.getId();
			companyId = daCompany.getId();
			ResultSet res = companyPersistenceIface.findBySql("select com_user_id from DA_COMPANY_PASS t  where   t.is_deleted=0  and  t.par_da_com_id=" + daCompany.getId());
			try {
				if (res.next()) { // 已审核 DA_COMPANY_PASS中有值

					ResultSet res1 = companyPersistenceIface.findBySql("select t.par_da_ind_id from DA_COMPANY_INDUSTRY_REL t  where    t.par_da_com_id=" + daCompany.getId());
					if (res1.next()) { // 有行业 DA_COMPANY_INDUSTRY_REL有值
						// ，则隐患系统中企业已存在
						flag = "该企业已存在隐患排查企业库中，请联系当地乡镇安监站（所）或技术公司查询";

					} else { // 无行业 ，则更新及 添加行业及用户表

//						System.out.println("无行业 ，则更新及 添加行业及用户表 ");
						companyPersistenceIface.executeHQLUpdate(hql);

						// ------------------同时更新TCompany表中的记录----------------------------------//
						pubCompanyPersistenceIface.executeSQLUpdate("update t_company set  hidden_company_date=sysdate,is_hidden=1,is_synchro=1,company_name='" + company.getCompanyName() + "',second_area=" + company.getSecondArea() + ",third_area=" + company.getThirdArea() + ",fouth_area=" + company.getFouthArea() + "  where  uuid='" + company.getUuid() + "' ");

						sql1 = "insert into DA_COMPANY_INDUSTRY_REL  (PAR_DA_COM_ID,par_da_ind_id) values (" + companyId + ",1389)";
						pubCompanyPersistenceIface.executeSQLUpdate(sql1);

						// add by huangjl 调用修改中心库企业的行业
						String t_sql2 = " update T_COMPANY t set t.DEPT_CODE='anjian',t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
						pubCompanyPersistenceIface.executeSQLUpdate(t_sql2);

						if (!hy.equals("")) {
							sql1 = "insert into DA_COMPANY_INDUSTRY_REL  (PAR_DA_COM_ID,par_da_ind_id) values (" + companyId + "," + hy + ")";
							pubCompanyPersistenceIface.executeSQLUpdate(sql1);

							// add by huangjl 调用修改中心库企业的行业
							// 得到二级行业

							DaIndustryParameter sd = new DaIndustryParameter();
							sd.setId(Long.valueOf(hy));
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(sd);
							if (d != null && d.getCode() != null) {
								String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1='" + d.getCode() + "',t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
								pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
							} else {
								String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1=null,t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
								pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
							}

						}

						res = companyPersistenceIface.findBySql(" select hibernate_sequence.nextval from dual"); // da_company
						try {
							if (res.next()) {
								userId = res.getLong(1);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if (company.getBusinessRegNum() != null && !company.getBusinessRegNum().equals("")) {
							pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getBusinessRegNum() + "'");
						}
						if (company.getOrgCode() != null && !company.getOrgCode().equals("")) {
							pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getOrgCode() + "'");
						}
						sql1 = "insert into  FK_USER (ID,USER_NAME, USER_PASS, IS_DELETED, CREATE_TIME, MODIFY_TIME )  values (" + userId + ", '" + (company.getBusinessRegNum() != null ? company.getBusinessRegNum() : company.getOrgCode()) + "',  'afebcea066335641f231b74ad10ff0a1a6a89c1e', 0, to_date('" + date + "','yyyy-MM-dd'),to_date('" + date + "','yyyy-MM-dd'))";
						pubCompanyPersistenceIface.executeSQLUpdate(sql1);

						sql1 = "insert into  FK_USER_INFO (ID,FIRST_AREA, SECOND_AREA, THIRD_AREA, IS_DELETED,  CREATE_TIME, MODIFY_TIME,FACT_NAME, USER_COMPANY,USER_INDUSTRY)  values (" + userId + ",330200000000, " + company.getSecondArea() + ", " + company.getThirdArea() + ", 0, to_date('" + date + "','yyyy-MM-dd'), to_date('" + date + "','yyyy-MM-dd'),'" + company.getCompanyName() + "','" + company.getCompanyName() + "','qiye' )";
						pubCompanyPersistenceIface.executeSQLUpdate(sql1);

						sql1 = "insert into  FK_USER_ROLE_REL (USER_ID,ROLE_ID)  values (" + userId + ",413 )";
						pubCompanyPersistenceIface.executeSQLUpdate(sql1);

						// 删除已有用户

						pubCompanyPersistenceIface.executeSQLUpdate("update  FK_USER t set t.is_deleted=1 where  t.id=" + com_user_id + "  ");

						pubCompanyPersistenceIface.executeSQLUpdate("update  FK_USER_INFO t set t.is_deleted=1 where  t.id=" + com_user_id + "  ");

						// 更新DA_COMPANY_PASS
						pubCompanyPersistenceIface.executeSQLUpdate("update  DA_COMPANY_PASS t set  t.com_user_id=" + userId + "  where  t.par_da_com_id=" + companyId + "  ");

						// try {
						// businessRegNum=new
						// String((company.getBusinessRegNum() != "" ?
						// company.getBusinessRegNum() :
						// company.getOrgCode()).getBytes("ISO-8859-1"),
						// "utf-8");
						businessRegNum = (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode());

						// } catch (UnsupportedEncodingException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }

						// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
						synDange(daCompany);

						flag = "注册成功，登录帐号为" + (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode()) + "，密码为123456　&nbsp;&nbsp;　<a class='wBox_close' href=javascript:setUser('" + businessRegNum + "');  style='color:red;text-decoration: underline;'>立即登陆</a>";

					}
					return flag;

				} else { // 隐患系统中企业存在, DA_COMPANY_PASS中无值 但无用户名时 添加帐户

//					System.out.println("隐患系统中企业存在, DA_COMPANY_PASS中无值 但无用户名时 添加帐户 ");
					companyPersistenceIface.executeHQLUpdate(hql);

					// ------------------同时更新TCompany表中的记录----------------------------------//
					pubCompanyPersistenceIface.executeSQLUpdate("update t_company set  hidden_company_date=sysdate, is_hidden=1,is_synchro=1,company_name='" + company.getCompanyName() + "',second_area=" + company.getSecondArea() + ",third_area=" + company.getThirdArea() + ",fouth_area=" + company.getFouthArea() + "  where  uuid='" + company.getUuid() + "' ");

					res = companyPersistenceIface.findBySql(" select hibernate_sequence.nextval from dual"); // da_company
					try {
						if (res.next()) {
							userId = res.getLong(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getBusinessRegNum() + "'");
					pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getOrgCode() + "'");
					sql1 = "insert into  FK_USER (ID,USER_NAME, USER_PASS, IS_DELETED, CREATE_TIME, MODIFY_TIME )  values (" + userId + ", '" + (company.getBusinessRegNum() != null ? company.getBusinessRegNum() : company.getOrgCode()) + "',  'afebcea066335641f231b74ad10ff0a1a6a89c1e', 0, to_date('" + date + "','yyyy-MM-dd'),to_date('" + date + "','yyyy-MM-dd'))";
					pubCompanyPersistenceIface.executeSQLUpdate(sql1);

					sql1 = "insert into  FK_USER_INFO (ID,FIRST_AREA, SECOND_AREA, THIRD_AREA, IS_DELETED,  CREATE_TIME, MODIFY_TIME,FACT_NAME, USER_COMPANY,USER_INDUSTRY)  values (" + userId + ",330200000000, " + company.getSecondArea() + ", " + company.getThirdArea() + ", 0, to_date('" + date + "','yyyy-MM-dd'), to_date('" + date + "','yyyy-MM-dd'),'" + company.getCompanyName() + "','" + company.getCompanyName() + "','qiye' )";
					pubCompanyPersistenceIface.executeSQLUpdate(sql1);

					sql1 = "insert into  FK_USER_ROLE_REL (USER_ID,ROLE_ID)  values (" + userId + ",413 )";
					pubCompanyPersistenceIface.executeSQLUpdate(sql1);
					pubCompanyPersistenceIface.executeSQLUpdate("delete  from DA_COMPANY_PASS t  where  t.par_da_com_id=" + companyId);

					if (company.getEnterprise() != null && !"".equals(company.getEnterprise())) {
						enterprise = company.getEnterprise();
					} else {
						enterprise = 0;
					}

					sql1 = "insert into  DA_COMPANY_PASS (PAR_DA_COM_ID, USER_ID, IS_ENTERPRISE, IS_ORGA,  IS_DELETED, IS_AFFIRM,COM_USER_ID)  values (" + companyId + ",1," + enterprise + ",0,0,1, " + userId + " )";
					pubCompanyPersistenceIface.executeSQLUpdate(sql1);

					pubCompanyPersistenceIface.executeSQLUpdate("delete  from DA_COMPANY_INDUSTRY_REL t  where  t.par_da_com_id=" + companyId);

					sql1 = "insert into DA_COMPANY_INDUSTRY_REL  (PAR_DA_COM_ID,par_da_ind_id) values (" + companyId + ",1389)";
					pubCompanyPersistenceIface.executeSQLUpdate(sql1);

					// add by huangjl 调用修改中心库企业的行业
					String t_sql2 = " update T_COMPANY t set  hidden_company_date=sysdate,t.DEPT_CODE='anjian',t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
					pubCompanyPersistenceIface.executeSQLUpdate(t_sql2);

					if (!hy.equals("")) {
						sql1 = "insert into DA_COMPANY_INDUSTRY_REL  (PAR_DA_COM_ID,par_da_ind_id) values (" + companyId + "," + hy + ")";
						pubCompanyPersistenceIface.executeSQLUpdate(sql1);

						// add by huangjl 调用修改中心库企业的行业
						// 得到二级行业

						DaIndustryParameter sd = new DaIndustryParameter();
						sd.setId(Long.valueOf(hy));
						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(sd);
						if (d != null && d.getCode() != null) {
							String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1='" + d.getCode() + "',t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
							pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
						} else {
							String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1=null,t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
							pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
						}

					}

					// try {
					// businessRegNum=new String((company.getBusinessRegNum() !=
					// "" ? company.getBusinessRegNum() :
					// company.getOrgCode()).getBytes("ISO-8859-1"), "utf-8");
					businessRegNum = (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode());
					// } catch (UnsupportedEncodingException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

					// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
					synDange(daCompany);

					flag = "注册成功，登录帐号为" + (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode()) + "，密码为123456 &nbsp;&nbsp;  <a class='wBox_close' href=javascript:setUser('" + businessRegNum + "');   style='color:red;text-decoration: underline;'>立即登陆</a>";
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else { // 隐患系统中无此企业 新增企业、账号
//			System.out.println(" 隐患系统中无此企业 新增企业、账号");
			ResultSet res = companyPersistenceIface.findBySql(" select hibernate_sequence.nextval from dual"); // da_company
			// 新增id
			try {
				if (res.next()) {
					companyId = res.getLong(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (company.getEnterprise() != null && !"".equals(company.getEnterprise())) {
				enterprise = company.getEnterprise();
			} else {
				enterprise = 0;
			}

			String legalPerson = null;
			// 当法定代表人为空的时候，再判断主要负责人
			if (company.getPrincipalPerson() != null && !"".equals(company.getPrincipalPerson()) && !"null".equals(company.getPrincipalPerson())) {
				legalPerson = company.getPrincipalPerson();
			}
			String phone = null;
			if (company.getPrincipalTelephone() != null && !"".equals(company.getPrincipalTelephone()) && !"null".equals(company.getPrincipalTelephone())) {
				// 主要负责人联系电话不为空
				phone = company.getPrincipalTelephone();
			}
			String orgCode = "";
			if (company.getOrgCode() != null && !"".equals(company.getOrgCode()) && !"null".equals(company.getOrgCode())) {
				// 组织机构代码不为空
				orgCode = company.getOrgCode();
			}

			sql1 = "insert into  DA_COMPANY (ID,COMPANY_NAME, SETUP_NUMBER, REGNUM, FDDELEGATE, FIRST_AREA, SECOND_AREA, THIRD_AREA, fouth_area,  IS_DELETED, CREATE_TIME, MODIFY_TIME, USER_ID,  uuid ,PHONE_CODE,PRODUCTION_SCALE,SAFETY_MANAGEMENT_PERSON,SAFETY_MANAGEMENT_PERSON_PHONE,IS_FOCUS_FIRE_UNITS,ESTABLISHMENT_DAY,BUSINESS_SCOPE,REG_ADDRESS,NATIONAL_ECONOMIC_TYPE1,NATIONAL_ECONOMIC_TYPE2,address,ECONOMIC_TYPE1,ECONOMIC_TYPE2,is_syn)  values (" + companyId + ", '" + company.getCompanyName() + "', '" + orgCode + "', '" + company.getBusinessRegNum() + "', '" + (legalPerson != null ? legalPerson : "") + "', 330200000000, " + company.getSecondArea() + ", " + company.getThirdArea() + "," + company.getFouthArea() + ", 0, to_date('" + date + "','yyyy-MM-dd'), to_date('" + date + "','yyyy-MM-dd'), 1, '" + company.getUuid() + "','" + (phone != null ? phone : "") + "','" + (company.getProductionScale() != null ? company.getProductionScale() : "") + "'" + ",'"
					+ (company.getSafetyMngPerson() != null ? company.getSafetyMngPerson() : "") + "','" + (company.getSafetyMngPersonPhone() != null ? company.getSafetyMngPersonPhone() : "") + "'," + (company.getFocusFireUnits()) + "," + (!establishmentDay.equals("") ? establishmentDay : null) + ",'" + (company.getBusinessScope() != null ? company.getBusinessScope() : null) + "','" + (company.getRegAddress() != null ? company.getRegAddress() : null) + "','" + (company.getNationalEconomicType1() != null ? company.getNationalEconomicType1() : null) + "','" + (company.getNationalEconomicType2() != null ? company.getNationalEconomicType2() : null) + "','" + (company.getBusinessAddress() != null ? company.getBusinessAddress() : null) + "','" + (company.getEconomicType1() != null ? company.getEconomicType1() : null) + "','" + (company.getEconomicType2() != null ? company.getEconomicType2() : null) + "',1)";

			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			// ------------------同时更新TCompany表中的记录----------------------------------//
			pubCompanyPersistenceIface.executeSQLUpdate("update t_company set  hidden_company_date=sysdate,is_hidden=1,is_synchro=1,company_name='" + company.getCompanyName() + "',second_area=" + company.getSecondArea() + ",third_area=" + company.getThirdArea() + ",fouth_area=" + company.getFouthArea() + "  where  uuid='" + company.getUuid() + "' ");

			res = companyPersistenceIface.findBySql(" select hibernate_sequence.nextval from dual"); // da_company
			// 新增id
			try {
				if (res.next()) {
					userId = res.getLong(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getBusinessRegNum() + "'");
			pubCompanyPersistenceIface.executeSQLUpdate("delete  from FK_USER t  where  t.USER_NAME='" + company.getOrgCode() + "'");

			sql1 = "insert into  FK_USER (ID,USER_NAME, USER_PASS, IS_DELETED, CREATE_TIME, MODIFY_TIME )  values (" + userId + ", '" + (company.getBusinessRegNum() != null ? company.getBusinessRegNum() : company.getOrgCode()) + "',  'afebcea066335641f231b74ad10ff0a1a6a89c1e', 0, to_date('" + date + "','yyyy-MM-dd'),to_date('" + date + "','yyyy-MM-dd'))";
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			sql1 = "insert into  FK_USER_INFO (ID,FIRST_AREA, SECOND_AREA, THIRD_AREA, IS_DELETED,  CREATE_TIME, MODIFY_TIME,FACT_NAME, USER_COMPANY,USER_INDUSTRY)  values (" + userId + ",330200000000, " + company.getSecondArea() + ", " + company.getThirdArea() + ", 0, to_date('" + date + "','yyyy-MM-dd'), to_date('" + date + "','yyyy-MM-dd'),'" + company.getCompanyName() + "','" + company.getCompanyName() + "','qiye' )";
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			sql1 = "insert into  FK_USER_ROLE_REL (USER_ID,ROLE_ID)  values (" + userId + ",413 )";
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			sql1 = "insert into  DA_COMPANY_PASS (PAR_DA_COM_ID, USER_ID, IS_ENTERPRISE, IS_ORGA,  IS_DELETED, IS_AFFIRM,COM_USER_ID)  values (" + companyId + ",1, " + enterprise + ",0,0,1, " + userId + " )";
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			sql1 = "insert into DA_COMPANY_INDUSTRY_REL  (PAR_DA_COM_ID,par_da_ind_id) values (" + companyId + ",1389)";
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);

			// add by huangjl 调用修改中心库企业的行业
			String t_sql2 = " update T_COMPANY t set t.DEPT_CODE='anjian',t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
			pubCompanyPersistenceIface.executeSQLUpdate(t_sql2);

			if (!hy.equals("")) {

				sql1 = "insert into  DA_COMPANY_INDUSTRY_REL (PAR_DA_COM_ID, PAR_DA_IND_ID)  values (" + companyId + "," + hy + ")";
				pubCompanyPersistenceIface.executeSQLUpdate(sql1);

				// add by huangjl 调用修改中心库企业的行业
				// 得到二级行业
				DaIndustryParameter sd = new DaIndustryParameter();
				sd.setId(Long.valueOf(hy));
				DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(sd);
				if (d != null && d.getCode() != null) {
					String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1='" + d.getCode() + "',t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
					pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
				} else {
					String t_sql3 = " update T_COMPANY t set t.MANAGEMENT_LEVEL1=null,t.MANAGEMENT_LEVEL2=null,t.IS_SYNCHRO='1',t.IS_HIDDEN='1',t.IS_DELETED=0 where t.uuid='" + company.getUuid() + "' ";
					pubCompanyPersistenceIface.executeSQLUpdate(t_sql3);
				}
			}

			businessRegNum = (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode());
			flag = "注册成功，登录帐号为" + (company.getBusinessRegNum() != "" ? company.getBusinessRegNum() : company.getOrgCode()) + "，密码为123456  &nbsp;&nbsp; <a class='wBox_close' href=javascript:setUser('" + businessRegNum + "');  style='color:red;text-decoration: underline;'>立即登陆</a>";
		}
		return flag;
	}

	public void synDange(DaCompany daCompany) {

		if (daCompany != null && daCompany.getId() != null) {
			// 查找当前月这家企业上报的一般隐患和重大隐患信息,让他们再次同步一下,这样才能保证中心库的企业和隐患的区域一样
			Calendar now = Calendar.getInstance();
//			System.out.println("年: " + now.get(Calendar.YEAR));
//			System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//			System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
			int year = now.get(Calendar.YEAR);
			// int month=now.get(Calendar.MONTH) + 1;
			// String sMonth="";
			// if(month<10){
			// sMonth="0"+month;
			// }else{
			// sMonth=String.valueOf(month);
			// }
			// String currentMonth=year+"-"+month;
			// 修改成修改当前年份的企业所上报的隐患区域

			try {
				ResultSet nor_res = companyPersistenceIface.findBySql("select n.id from da_normal_danger n where  to_char(n.create_time,'YYYY')='" + year + "' and n.is_deleted=0 and n.par_da_com_id=" + daCompany.getId());
				while (nor_res.next()) {
					// 一般隐患重新同步
					pubCompanyPersistenceIface.executeHQLUpdate("update DaNomalDanger set isSynchro=1 where id=" + nor_res.getInt(1) + "");
				}

				ResultSet res_bigdouble1 = companyPersistenceIface.findBySql("select t.id from da_danger t  where  t.par_da_com_id=" + daCompany.getId() + " and to_char(t.create_time,'YYYY')='" + year + "'  and  t.is_deleted=0");
				while (res_bigdouble1.next()) {
					// 重大隐患重新同步
					pubCompanyPersistenceIface.executeHQLUpdate("update DaDanger set isSynchro=1 where id=" + res_bigdouble1.getInt(1) + "");
				}

			} catch (Exception e) {
//				System.out.println("修改当前月的一般隐患,重大隐患同步状态失败!");
				e.printStackTrace();
			}
		}

	}

	public List<FkEnum> getEnumByParentCode(String code) {

		StringBuffer sb = new StringBuffer(" from FkEnum enum where enum.fatherId =(select id from FkEnum enum1 where enum1.enumCode='" + code + "') and deleted=0  order by enum.sortNum");

		List<FkEnum> fkenums = enumPersistenceIface.loadEnumsByHql(sb.toString());

		return fkenums;

	}

	public List<DaIndustryParameter> getDaIndustryParameterByDepiction(String depiction) {

		StringBuffer sb = new StringBuffer(" from DaIndustryParameter daIndustryParameter where daIndustryParameter.depiction = '" + depiction + "'");
		List<DaIndustryParameter> daIndustryParameters = null;
		try {
			daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(sb.toString());
		} catch (ApplicationAccessException e) {

			e.printStackTrace();
		}

		return daIndustryParameters;

	}

	public void uptOrIntTCompany(DaCompany company) throws ApplicationAccessException {
		TCompany tcompany = null;

		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(TCompany.class);
		if (company.getUuid() != null && !company.getUuid().trim().equals("")) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("uuid", company.getUuid()));
			tcompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy);
		} else {
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("tc.companyName",
			// company.getCompanyName().trim()));
			// detachedCriteriaProxy.add(RestrictionsProxy.eq("deleted",
			// false));
			// StringBuffer sb=new StringBuffer(" (COMPANY_NAME='" +
			// company.getCompanyName().trim() + "' ");
			// if(company.getRegNum()!=null&&!"".equals(company.getRegNum())&&!"null".equals(company.getRegNum())){
			// sb.append(" or  BUSINESS_REG_NUM='" + company.getRegNum() +
			// "' ");
			// }

			// if(company.getSetupNumber()!=null&&!"".equals(company.getSetupNumber())&&!"null".equals(company.getSetupNumber())){
			// sb.append(" or  ORG_CODE='" + company.getSetupNumber() + "' ");
			// }

			// sb.append(")");
			// detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(sb.toString()));
			// tcompany =
			// tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy);

			// 先根据名称
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName()) && !"null".equals(company.getCompanyName())) {
				DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("deleted", false));
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", company.getCompanyName().trim()));
				tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
			}
			// 为空，再根据工商注册号匹配
			if (tcompany == null) {
				if (company.getRegNum() != null && !"".equals(company.getRegNum()) && !"null".equals(company.getRegNum())) {
					DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
					detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", false));
					detachedCriteriaProxy2.add(RestrictionsProxy.eq("businessRegNum", company.getRegNum().trim()));
					tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy2);
				}
			}

			// 为空，再根据组织机构代码
			if (tcompany == null) {
				if (company.getSetupNumber() != null && !"".equals(company.getSetupNumber()) && !"null".equals(company.getSetupNumber())) {
					DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
					detachedCriteriaProxy3.add(RestrictionsProxy.eq("deleted", false));
					detachedCriteriaProxy3.add(RestrictionsProxy.eq("orgCode", company.getSetupNumber().trim()));
					tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy3);
				}

			}

		}
		// 如果删除的企业查找不到，哪么再对应中心库已经删除的企业
		if (tcompany == null) {
			// DetachedCriteriaProxy2 detachedCriteriaProxy2=
			// DetachedCriteriaProxy2.forClass(TCompany.class);
			// detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted",
			// true));
			//
			// StringBuffer sb=new StringBuffer(" (COMPANY_NAME='" +
			// company.getCompanyName().trim() + "' ");
			// if(company.getRegNum()!=null&&!"".equals(company.getRegNum())&&!"null".equals(company.getRegNum())){
			// sb.append(" or  BUSINESS_REG_NUM='" + company.getRegNum() +
			// "' ");
			// }
			//
			// if(company.getSetupNumber()!=null&&!"".equals(company.getSetupNumber())&&!"null".equals(company.getSetupNumber())){
			// sb.append(" or  ORG_CODE='" + company.getSetupNumber() + "' ");
			// }
			//
			// sb.append(")");
			//
			// detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(sb.toString()));
			// tcompany =
			// tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy2);

			// 先根据名称
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName()) && !"null".equals(company.getCompanyName())) {
				DetachedCriteriaProxy2 detachedCriteriaProxy4 = DetachedCriteriaProxy2.forClass(TCompany.class);
				detachedCriteriaProxy4.add(RestrictionsProxy.eq("deleted", true));
				detachedCriteriaProxy4.add(RestrictionsProxy.eq("companyName", company.getCompanyName().trim()));
				tcompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy4);
			}
			// 为空，再根据工商注册号匹配
			if (tcompany == null) {
				if (company.getRegNum() != null && !"".equals(company.getRegNum()) && !"null".equals(company.getRegNum())) {
					DetachedCriteriaProxy2 detachedCriteriaProxy5 = DetachedCriteriaProxy2.forClass(TCompany.class);
					detachedCriteriaProxy5.add(RestrictionsProxy.eq("deleted", true));
					detachedCriteriaProxy5.add(RestrictionsProxy.eq("businessRegNum", company.getRegNum().trim()));
					tcompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy5);
				}
			}

			// 为空，再根据组织机构代码
			if (tcompany == null) {
				if (company.getSetupNumber() != null && !"".equals(company.getSetupNumber()) && !"null".equals(company.getSetupNumber())) {
					DetachedCriteriaProxy2 detachedCriteriaProxy6 = DetachedCriteriaProxy2.forClass(TCompany.class);
					detachedCriteriaProxy6.add(RestrictionsProxy.eq("deleted", true));
					detachedCriteriaProxy6.add(RestrictionsProxy.eq("orgCode", company.getSetupNumber().trim()));
					tcompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy6);
				}

			}
		}

		// 行业分类
		// 由于行业类别表DaIndustryParameter和企业表DaCompany是多对多的关系，
		// TCompany表中只需要保存一级行业类别和二级行业类别，所已实例化两个DaIndustryParameter
		// 一个用来保存一级行业，一个用来保存二级行业。

		DaIndustryParameter daIndustryParameter0 = null;
		DaIndustryParameter daIndustryParameter1 = null;
		DaIndustryParameter daIndustryParameter2 = null;

		if (tcompany != null) { // 更新tcompany
//			System.out.println("更新tcompany");
			//tcompany.setCompanyName(company.getCompanyName());// 单位名称
			tcompany.setRegAddress(company.getRegAddress());// 注册地址
			tcompany.setBusinessAddress(company.getAddress());// 所在地址
			tcompany.setFirstArea(company.getFirstArea());// 一级区域编码
			tcompany.setSecondArea(company.getSecondArea());// 二级区域编码
			tcompany.setThirdArea(company.getThirdArea());// 三级区域编码
			tcompany.setFouthArea(company.getFouthArea());// 四级区域编码
			tcompany.setFifthArea(company.getFifthArea());// 五级区域编码
			tcompany.setOrgCode(company.getSetupNumber());// 机构代码
			tcompany.setBusinessRegNum(company.getRegNum());// 工商注册号
			tcompany.setEstablishmentDay(company.getEstablishmentDay());// 成立日期
			tcompany.setPrincipalPerson(company.getFddelegate());// 法定代表人或主要负责人
			tcompany.setPrincipalTelephone(company.getPhoneCode());// 联系电话
			tcompany.setSecurityPrincipalPerson(company.getSafetyMngPerson());// 安管负责人
			tcompany.setSecurityPrincipalTel(company.getSafetyMngPersonPhone());// 安管负责人联系电话
			tcompany.setProductionScale(company.getProductionScale());// daCompany企业规模对应tcompany生产规模
			if (company.getDaCompanyPass() != null) {
				tcompany.setEnterprise(company.getDaCompanyPass().isEnterprise() == true ? 1 : 0);// 规模以上企业
			}

			// 先找出所有的行业分类
			Set<DaIndustryParameter> hzTradeTypes = company.getHzTradeTypes();

			// 找出父节点为空的，即为行业部门。
			for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {

				DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);

				if (d != null && d.getDaIndustryParameter() == null) {

					daIndustryParameter0 = d;
				}
			}

			// 找出行业部门下面的一级行业。
			if (daIndustryParameter0 != null) {
				for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
					DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
					if (d != null && d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
						daIndustryParameter1 = d;
					}
				}
			}

			// 找出行业部门下面的二级行业。
			if (daIndustryParameter1 != null) {
				for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
					DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
					if (d != null && d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
						daIndustryParameter2 = d;
					}
				}
			}

//			System.out.println("daIndustryParameter2: " + daIndustryParameter2);

			if (daIndustryParameter0 != null) {
				tcompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
			}

			if (daIndustryParameter1 != null && daIndustryParameter1.getCode() != null && daIndustryParameter1.getCode().indexOf("undefined") < 0) {
				tcompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
			} else {
				tcompany.setManagementLevel1("");
			}

			if (daIndustryParameter2 != null && daIndustryParameter2.getCode() != null && daIndustryParameter2.getCode().indexOf("undefined") < 0) {
				tcompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
			} else {
				tcompany.setManagementLevel2("");
			}

			tcompany.setNationalEconomicType1(company.getNaEcoType1());// 国民经济分类1
			tcompany.setNationalEconomicType2(company.getNaEcoType2());// 国民经济分类2
			tcompany.setEconomicType1(company.getEconomicType1());// 经济类型1
			tcompany.setEconomicType2(company.getEconomicType2());// 经济类型2
			tcompany.setBusinessScope(company.getBusinessScope());// 经营范围
			tcompany.setIsSynchro(1); // 更新同步状态
			tcompany.setIsHidden(1); // 是否隐患系统
			tcompany.setIsHighRiskWork(company.getIsHighRiskWork());
			tcompany.setHighRiskWork(company.getHighRiskWork());
			
			tcompany.setHiddenCompanyDate(company.getCreateTime());
			// 将企业的删除状态修改为正常状态
			tcompany.setDeleted(false);
			tcompanyPersistenceIface.updateCompany(tcompany);

			if (company != null) {
				// 如果da_company的uuid为空的话，此处设置uuid并修改
				if (tcompany.getUuid() != null && !"".equals(tcompany.getUuid())) {
					if (company.getUuid() == null || "".equals(company.getUuid())) {
						// 更新前先查找一下uuid是否已经存在
						// DetachedCriteriaProxy detachedCriteriaProxy9 =
						// DetachedCriteriaProxy.forClass(DaCompany.class, "d");
						// detachedCriteriaProxy9.add(RestrictionsProxy.eq("uuid",
						// tcompany.getUuid()));
						// DaCompany
						// dac=companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy9);
						// if(dac==null){
						// 为空才允许更新uuid不然会重复
						company.setUuid(tcompany.getUuid());
						// }
					}
				}
				company.setIsSYN(0L);
				companyPersistenceIface.mergeCompany(company);
			}

		} else { // 新增t_company
//			System.out.println("新增t_company");
			tcompany = new TCompany();
			tcompany.setCompanyName(company.getCompanyName());
			tcompany.setBusinessRegNum(company.getRegNum());
			tcompany.setRegAddress(company.getRegAddress());
			tcompany.setEstablishmentDay(company.getEstablishmentDay());
			tcompany.setOrgCode(company.getSetupNumber());
			tcompany.setPrincipalPerson(company.getFddelegate());
			tcompany.setEconomicType1(company.getEconomicType1());
			tcompany.setEconomicType2(company.getEconomicType2());
			tcompany.setNationalEconomicType1(company.getNaEcoType1());
			tcompany.setNationalEconomicType2(company.getNaEcoType2());
			tcompany.setBusinessScope(company.getBusinessScope());
			tcompany.setBusinessAddress(company.getAddress());
			tcompany.setFirstArea(company.getFirstArea());
			tcompany.setSecondArea(company.getSecondArea());
			tcompany.setThirdArea(company.getThirdArea());
			tcompany.setPrincipalTelephone(company.getPhoneCode());
			tcompany.setSecurityPrincipalPerson(company.getSafetyMngPerson());
			tcompany.setSecurityPrincipalTel(company.getSafetyMngPersonPhone());
			tcompany.setProductionScale(company.getProductionScale());
			tcompany.setCreateTime(company.getCreateTime());
			tcompany.setModifyTime(company.getModifyTime());
			tcompany.setDeleted(false);
			// tcompany.setIsGatherData(company.getDaCompanyPass().isEnterprise()
			// == true ? 1 : 0); // 重点规模企业
			if (company.getDaCompanyPass() != null) {
				tcompany.setEnterprise(company.getDaCompanyPass().isEnterprise() == true ? 1 : 0);// 规模以上企业
			}
			tcompany.setIsHidden(1);
			tcompany.setIsSynchro(1);
			tcompany.setUuid(company.getUuid());

			// 先找出所有的行业分类
			Set<DaIndustryParameter> hzTradeTypes = company.getHzTradeTypes();

			// 找出父节点为空的，即为行业部门。
			for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {

				DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);

				if (d != null && d.getDaIndustryParameter() == null) {

					daIndustryParameter0 = d;
				}
			}

			// 找出行业部门下面的一级行业。
			if (daIndustryParameter0 != null) {
				for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
					DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
					if (d != null && d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
						daIndustryParameter1 = d;
					}
				}
			}

			// 找出行业部门下面的二级行业。
			if (daIndustryParameter1 != null) {
				for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
					DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
					if (d != null && d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
						daIndustryParameter2 = d;
					}
				}
			}

//			System.out.println("daIndustryParameter2: " + daIndustryParameter2);

			if (daIndustryParameter0 != null) {
				tcompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
			}

			if (daIndustryParameter1 != null && daIndustryParameter1.getCode() != null && daIndustryParameter1.getCode().indexOf("undefined") < 0) {
				tcompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
			} else {
				tcompany.setManagementLevel1("");
			}

			if (daIndustryParameter2 != null && daIndustryParameter2.getCode() != null && daIndustryParameter2.getCode().indexOf("undefined") < 0) {
				tcompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
			} else {
				tcompany.setManagementLevel2("");
			}
			// int x = 0;
			// String hycode = "";
			// // 行业、部门
			// List<VDaCompanyIndustryRel> vdu =
			// vdaComIndRelPersistenceIface.getHy(company.getId());
			// if (vdu.size() > 0) {
			// x = 0;
			// hycode = "";
			// for (VDaCompanyIndustryRel r : vdu) {
			// System.out.println("r.getParDaIndId(): " + r.getParDaIndId());
			// hycode = etlDaCompanyPersistenceIface.ftrans(r.getParDaIndId());
			// System.out.println("hycode: " + hycode);
			// if (hycode != null && !hycode.equals("")) {
			// if (x == 0) { // 部门
			// tcompany.setDeptCode(hycode);
			// if (!hycode.equals("anjian")) { // 若不为安监,则退出,因为中心库没有对应值
			// break;
			// }
			// } else if (x == 1) { // 一级行业
			// tcompany.setManagementLevel1(hycode);
			// } else if (x == 2) {// 二级行业
			// tcompany.setManagementLevel2(hycode);
			// }
			// }
			// x++;
			// }
			// }
			tcompany.setIsHighRiskWork(company.getIsHighRiskWork());
			tcompany.setHighRiskWork(company.getHighRiskWork());
			// 将企业的删除状态设置为正常状态
			tcompany.setDeleted(false);
			tcompany.setHiddenCompanyDate(new Date());
			tcompanyPersistenceIface.createCompany(tcompany);

			if (company != null) {
				company.setIsSYN(0L);
				companyPersistenceIface.mergeCompany(company);
			}
		}
		return;
	}

	public void deleteYHOfTCompany(DaCompany company) throws ApplicationAccessException {
		TCompany tcompany = null;

		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(TCompany.class);
		// 只有当uuid存在的时候这条企业信息才有可能是隐患企业
		if (company.getUuid() != null && !company.getUuid().trim().equals("")) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("uuid", company.getUuid()));
			tcompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy);

		} else {
			// 通过uuid查询不到的话，说明还没有同步到中心库，此时可以通过名称等信息来查询
			// 先根据名称
			if (company.getCompanyName() != null && !"".equals(company.getCompanyName()) && !"null".equals(company.getCompanyName())) {
				DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("deleted", false));
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", company.getCompanyName().trim()));
				detachedCriteriaProxy1.add(RestrictionsProxy.sqlRestriction("  uuid is null "));
				tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
			}
			// 为空，再根据工商注册号匹配
			if (tcompany == null) {
				if (company.getRegNum() != null && !"".equals(company.getRegNum()) && !"null".equals(company.getRegNum())) {
					DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
					detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", false));
					detachedCriteriaProxy2.add(RestrictionsProxy.eq("businessRegNum", company.getRegNum().trim()));
					detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction("  uuid is null "));
					tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy2);
				}
			}

			// 为空，再根据组织机构代码
			if (tcompany == null) {
				if (company.getSetupNumber() != null && !"".equals(company.getSetupNumber()) && !"null".equals(company.getSetupNumber())) {
					DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
					detachedCriteriaProxy3.add(RestrictionsProxy.eq("deleted", false));
					detachedCriteriaProxy3.add(RestrictionsProxy.eq("orgCode", company.getSetupNumber().trim()));
					detachedCriteriaProxy3.add(RestrictionsProxy.sqlRestriction("  uuid is null "));
					tcompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy3);
				}

			}

		}

		if (tcompany != null) { // 更新tcompany
//			System.out.println("更新tcompany的隐患类型为不是隐患企业");
			tcompany.setIsSynchro(1); // 更新同步状态
			tcompany.setIsHidden(0); // 是否隐患系统
			tcompanyPersistenceIface.updateCompany(tcompany);
		}

	}

	public List<DaIndustryParameter> getDaIndustryParameterByCode(String code) {

		StringBuffer sb = new StringBuffer(" from DaIndustryParameter daIndustryParameter where daIndustryParameter.code = '" + code + "'");
		List<DaIndustryParameter> daIndustryParameters = null;
		try {
			daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(sb.toString());
		} catch (ApplicationAccessException e) {

			e.printStackTrace();
		}

		return daIndustryParameters;

	}

	@Override
	public DaIndustryParameter getHiddenTypeByNormalCode(String code) {
		StringBuffer sb = new StringBuffer(" from DaIndustryParameter obj where obj.deleted = false and type=8 and obj.code = '" + code + "'");
		List<DaIndustryParameter> daIndustryParameters = null;
		try {
			daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(sb.toString());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return daIndustryParameters != null && daIndustryParameters.size() > 0 ? daIndustryParameters.get(0) : null;
	}

	public List<DaIndustryParameter> getDaIndustryParameterByParentDepiction(String depiction) {

		StringBuffer sb = new StringBuffer(" from DaIndustryParameter daIndustryParameter where daIndustryParameter.daIndustryParameter.id in (select id from  DaIndustryParameter daIndustryParameter1 where daIndustryParameter1.depiction = '" + depiction + "') and daIndustryParameter.deleted=0  order by sort asc");
		List<DaIndustryParameter> daIndustryParameters = null;
		try {
			daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(sb.toString());
		} catch (ApplicationAccessException e) {

			e.printStackTrace();
		}

		return daIndustryParameters;

	}

	public List<DaIndustryParameter> getDaIndustryParameterByParentIds(List<DaIndustryParameter> parentDaIndustryParameters) {

		StringBuffer sb = new StringBuffer(" from DaIndustryParameter daIndustryParameter where  daIndustryParameter.deleted=0 and daIndustryParameter.daIndustryParameter.id in (select id from  DaIndustryParameter daIndustryParameter1 where daIndustryParameter1.id in (0");

		if (parentDaIndustryParameters != null && parentDaIndustryParameters.size() > 0) {
			for (int i = 0; i < parentDaIndustryParameters.size(); i++) {
				DaIndustryParameter daIndustryParameter = parentDaIndustryParameters.get(i);
				sb.append("," + daIndustryParameter.getId());
			}
		}
		sb.append(")) order by sort asc");
		List<DaIndustryParameter> daIndustryParameters = null;
		try {
			daIndustryParameters = tradeTypePersistenceIface.executeHQLQuery(sb.toString());
		} catch (ApplicationAccessException e) {

			e.printStackTrace();
		}

		return daIndustryParameters;

	}

	public List<FkEnum> getFkEnumByParentIds(List<FkEnum> fkEnums) {

		StringBuffer sb = new StringBuffer(" from FkEnum enum  where  enum.deleted=0 and enum.fatherId in (select id from   FkEnum enum1 where enum1.id in (0");

		if (fkEnums != null && fkEnums.size() > 0) {
			for (int i = 0; i < fkEnums.size(); i++) {
				FkEnum fkEnum = fkEnums.get(i);
				sb.append("," + fkEnum.getId());
			}
		}
		sb.append(")) order by enum.sortNum");
		List<FkEnum> enums = null;
		try {
			enums = enumPersistenceIface.loadEnumsByHql(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enums;

	}

	public TCompany getCoreCompany(TCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(TCompany.class, "t");

		String businessRegNum = company.getBusinessRegNum();
		String companyName = company.getCompanyName();
		// 先根据工商注册号查找，如果查不到的话，再根据名称查找。
		if (businessRegNum != null && !"".equals(businessRegNum)) {
			detachedCriteriaProxy.add(RestrictionsProxy.eq("businessRegNum", businessRegNum));
			company = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy);
		}

		if (company == null) {
			if (companyName != null && !"".equals(companyName)) {
				DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "t");
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", companyName));
				company = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
			}

		}
		return company;
	}

	public Department getDepartmentByCode(String code) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(Department.class, "t");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("t.code", code));
		return departmentPersistenceIface.loadDepartment(detachedCriteriaProxy);
	}

	/**
	 * 修改日期 2013-12-27 修改人 maying
	 * 
	 * @return
	 */
	public List<TCaches> loadCaches(TCaches cache, Pagination pagination) throws ApplicationAccessException {
		List<TCaches> caches = new ArrayList<TCaches>();
		if (cache != null) {
			if (cache.getName() != null && !"".equals(cache.getName().trim())) {
				// detachedCriteriaProxy.add(RestrictionsProxy.like("dc.getName",
				// cache.getName().trim(), MatchMode.ANYWHERE));
			}
		}
		ResultSet set = companyPersistenceIface.findBySql("select  t.name,t.content,t.id  from T_CACHES t  where  rownum <  10  order  by id  desc ");
		try {
			while (set.next()) {
				TCaches c = new TCaches();
				c.setId(set.getLong(3));
				c.setName(set.getString(1));
				c.setContent(set.getString(2));
				caches.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caches;

	}

	public List<DaCompany> checkSetupNumber(DaCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "c");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("c.setupNumber", company.getSetupNumber().trim()));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("   this_.id   in (select   r.par_da_com_id  from    da_company_industry_rel  r   )  "));
		if (company.getId() != null && company.getId() != 0l) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id != " + company.getId() + " "));
		}
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	public List<DaCompany> checkRegNum(DaCompany company) throws ApplicationAccessException {
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "c");
		detachedCriteriaProxy.add(RestrictionsProxy.eq("c.regNum", company.getRegNum().trim()));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("   this_.id   in (select   r.par_da_com_id  from    da_company_industry_rel  r   )  "));
		if (company.getId() != null && company.getId() != 0l) {
			detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id != " + company.getId() + " "));
		}
		return companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
	}

	public List<CompanyVo> findUnReportCompany4CurrentMonth(Long secondArea) {
		List<CompanyVo> list = new ArrayList<CompanyVo>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		MonthQueryHelper helper = new MonthQueryHelper(year, 0, month);
		StringBuffer sql = new StringBuffer();
		String beginDate = DateUtils.date2Str(helper.getBeginDate(), null);
		String endDate = DateUtils.date2Str(helper.getEndDate(), null);
		sql.append("select dc.phone_code, dc.safety_management_person_phone from da_company dc")
		   .append(" right join da_company_pass dcp on (dc.id = dcp.par_da_com_id  and dcp.is_affirm = 1 and dcp.is_deleted = 0)")
		   .append(" where not exists (")
		   .append("   select 1 from (")
		   .append("     select dnd.par_da_com_id from da_normal_danger dnd where dnd.is_deleted = 0")
		   .append("       and dnd.create_time between to_date('" + beginDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')")
		   .append("   union all")
		   .append("   select dd.par_da_com_id from da_danger dd where dd.is_deleted = 0")
		   .append("       and dd.create_time between to_date('" + beginDate + "','yyyy-MM-dd') and to_date('" + endDate + "','yyyy-MM-dd')")
		   .append("       and par_da_com_id not in (")
		   .append("         select par_da_com_id from da_normal_danger where is_deleted = 0")
		   .append("           and user_id in (select id from fk_user_info where is_deleted = 0 and user_industry = 'qiye')")
		   .append("           and create_time between to_date('" + beginDate + "', 'yyyy-MM-dd') and to_date('" + endDate + "', 'yyyy-MM-dd')")
		   .append("       )")
		   .append("   ) v where v.par_da_com_id = dc.id")
		   .append(" )")
		   .append(" and exists ( select 1 from da_company_industry_rel dcir where dcir.par_da_com_id = dc.id)")
		   .append(" and dc.is_deleted = 0 and dc.create_time < to_date('" + beginDate + "', 'yyyy-MM-dd')")
		   .append(" and dc.second_area = " + secondArea);
		try {
			ResultSet rs = companyPersistenceIface.findBySql(sql.toString());
			while (rs.next()) {
				CompanyVo vo = new CompanyVo();
				vo.setPhoneCode(rs.getString(1));
				vo.setSafetyMngPersonPhone(rs.getString(2));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 1、4、7、10月份 获取上一个季度没有上报隐患季报的企业
	 * @param secondArea
	 * @return
	 */
	public List<CompanyVo> findUnReportCompany4PreQuarter(Long secondArea) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Integer pre_quarter = null;
		Integer quarter_month = null;//每个季度的最后一个月
		if (month == 1 || month == 2 || month == 3) {
			year = year - 1;//上一年的年份
			pre_quarter = 4;
			quarter_month = 12;
		} else if (month == 4 || month == 5 || month == 6) {
			pre_quarter = 1;
			quarter_month = 3;
		} else if (month == 7 || month == 8 || month == 9) {
			pre_quarter = 2;
			quarter_month = 6;
		} else if (month == 10 || month == 11 || month == 12) {
			pre_quarter = 3;
			quarter_month = 9;
		}
		String BACKUP_DATE = null;
		if (pre_quarter == 1) {
			BACKUP_DATE = year + "03";
		} else if (pre_quarter == 2) {
			BACKUP_DATE = year + "06";
		} else if (pre_quarter == 3) {
			BACKUP_DATE = year + "09";
		} else if (pre_quarter == 4) {
			BACKUP_DATE = year + "12";
		}
		List<CompanyVo> list = new ArrayList<CompanyVo>();
		if (pre_quarter != null && quarter_month != null && BACKUP_DATE != null) {
			MonthQueryHelper helper = new MonthQueryHelper(year, pre_quarter, quarter_month);
			String beginDate = DateUtils.date2Str(helper.getBeginDate(), null);
			StringBuffer sql = new StringBuffer();
			sql.append("select dc.phone_code, dc.safety_management_person_phone from da_company_his dc")
			   .append(" right join da_company_pass_his dcp on (dc.id = dcp.par_da_com_id  and dcp.is_affirm = 1 and dcp.is_deleted = 0 and dcp.backup_date=" + BACKUP_DATE + ")")
			   .append(" where not exists (")
			   .append("   select 1 from da_company_quarter_report dcqr")
			   .append("     where year = " + year + " and quarter = " + pre_quarter + " and dc.id = dcqr.company_id")
			   .append(" )")
			   .append(" and exists (select 1 from da_company_industry_rel_his dcir where dcir.par_da_com_id = dc.id and dcir.backup_date=" + BACKUP_DATE + ")")
			   .append(" and dc.is_deleted= 0 and dc.create_time < to_date('" + beginDate +"', 'yyyy-MM-dd') and dc.backup_date =" + BACKUP_DATE)
			   .append(" and dc.second_area = " + secondArea);
			try {
				ResultSet rs = companyPersistenceIface.findBySql(sql.toString());
				while (rs.next()) {
					CompanyVo vo = new CompanyVo();
					vo.setPhoneCode(rs.getString(1));
					vo.setSafetyMngPersonPhone(rs.getString(2));
					list.add(vo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public boolean existUnReportPreQuarter(Long companyId) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Integer pre_quarter = null;
		Integer quarter_month = null;//每个季度的最后一个月
		if (month == 1 || month == 2 || month == 3) {
			year = year - 1;//上一年的年份
			pre_quarter = 4;
			quarter_month = 12;
		} else if (month == 4 || month == 5 || month == 6) {
			pre_quarter = 1;
			quarter_month = 3;
		} else if (month == 7 || month == 8 || month == 9) {
			pre_quarter = 2;
			quarter_month = 6;
		} else if (month == 10 || month == 11 || month == 12) {
			pre_quarter = 3;
			quarter_month = 9;
		}
		String BACKUP_DATE = null;
		if (pre_quarter == 1) {
			BACKUP_DATE = year + "03";
		} else if (pre_quarter == 2) {
			BACKUP_DATE = year + "06";
		} else if (pre_quarter == 3) {
			BACKUP_DATE = year + "09";
		} else if (pre_quarter == 4) {
			BACKUP_DATE = year + "12";
		}
		if (pre_quarter != null && quarter_month != null && BACKUP_DATE != null) {
			MonthQueryHelper helper = new MonthQueryHelper(year, pre_quarter, quarter_month);
			String beginDate = DateUtils.date2Str(helper.getBeginDate(), null);
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from da_company_his dc")
			   .append(" right join da_company_pass_his dcp on (dc.id = dcp.par_da_com_id  and dcp.is_affirm = 1 and dcp.is_deleted = 0 and dcp.backup_date=" + BACKUP_DATE + ")")
			   .append(" where not exists (")
			   .append("   select 1 from da_company_quarter_report dcqr")
			   .append("     where year = " + year + " and quarter = " + pre_quarter + " and dc.id = dcqr.company_id")
			   .append(" )")
			   .append(" and exists (select 1 from da_company_industry_rel_his dcir where dcir.par_da_com_id = dc.id and dcir.backup_date=" + BACKUP_DATE + ")")
			   .append(" and dc.is_deleted= 0 and dc.create_time < to_date('" + beginDate +"', 'yyyy-MM-dd') and dc.backup_date =" + BACKUP_DATE)
			   .append(" and dc.id = " + companyId);
			try {
				ResultSet rs = companyPersistenceIface.findBySql(sql.toString());
				while (rs.next()) {
					return rs.getLong(1) > 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void companyBinding(DaCompany company,TCompany tcompany) throws Exception{
		try {
			//获得要绑定的隐患企业信息
			DaCompany daCompany=this.loadCompany(company);
			//获得要绑定的中心库企业信息
			TCompany newTcompany=this.getTCompany(tcompany);
			//获得原绑定的中心库企业信息
			TCompany oldTcompany =new TCompany();
			oldTcompany.setUuid(company.getUuid());
			oldTcompany=this.getTCompany(oldTcompany);
			
			//开始修改记录
			//1、将原绑定的中心库企业信息is_hidden=0，IS_SYNCHRO=1
			oldTcompany.setIsHidden(0);
			oldTcompany.setIsSynchro(1);
			oldTcompany.setModifyTime(new Date());
			tcompanyPersistenceIface.updateCompany(oldTcompany);
			
			//2、将要绑定的中心库企业信息修改is_hidden=1，IS_SYNCHRO=1
			newTcompany.setIsHidden(1);
			newTcompany.setIsSynchro(1);
			newTcompany.setModifyTime(new Date());
			tcompanyPersistenceIface.updateCompany(newTcompany);
			
			//3将要绑定的中心库企业信息更新到隐患企业信息中（此处企业名称的优先级是以隐患为高，是否要修改成以中心库的优先级为高呢，待确认？）
			etlDaCompanyPersistenceIface.loadSynDaCompany(daCompany,newTcompany);
			//4将隐患的企业信息更新到中心库
			etlDaCompanyPersistenceIface.loadSynTCompany(daCompany,newTcompany);
			
			//以下业务数据的修改直接使用sql语句进行修改，比较方便
			//5根据企业id，修改一般隐患信息为未同步状态
			String sql1="update da_normal_danger da set da.is_synchro=1 where da.par_da_com_id="+daCompany.getId()+"";
//			System.out.println("一般隐患sql="+sql1);
			pubCompanyPersistenceIface.executeSQLUpdate(sql1);
			
			//6根据企业id，修改重大隐患信息为未同步状态
			String sql2="update da_danger dg set dg.is_synchro=1 where dg.par_da_com_id="+daCompany.getId()+"";
//			System.out.println("重大隐患sql="+sql2);
			pubCompanyPersistenceIface.executeSQLUpdate(sql2);
			
			//7根据企业id，修改挂牌隐患为未同步状态
			String sql3="update  DA_ROLLCALL_COMPANY dc set dc.is_synchro=1 where dc.par_da_dan_id in (select g.id from da_danger g where g.par_da_com_id="+daCompany.getId()+")";
//			System.out.println("挂牌隐患sql="+sql3);
			pubCompanyPersistenceIface.executeSQLUpdate(sql3);
			
			
			//8根据企业id，修改季报信息为未同步状态
			String sql4="update DA_COMPANY_QUARTER_REPORT r set r.is_synchro=1 where r.company_id="+daCompany.getId()+"";
//			System.out.println("季报信息sql="+sql4);
			pubCompanyPersistenceIface.executeSQLUpdate(sql4);
			
			//9根据企业id，修改危化品信息为未同步状态
			String sql5="update WH_COMPANY_INFO w set w.is_synchro=1 where w.companyid="+daCompany.getId()+"";
//			System.out.println("危化品信息sql="+sql5);
			pubCompanyPersistenceIface.executeSQLUpdate(sql5);
			
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCompany(DaCompany company) throws ApplicationAccessException {
		companyPersistenceIface.updateCompany(company);
	}

	public void setPasswordEncoderIface(PasswordEncoderIface passwordEncoderIface) {
		this.passwordEncoderIface = passwordEncoderIface;
	}

	public void setRolePersistenceIface(RolePersistenceIface rolePersistenceIface) {
		this.rolePersistenceIface = rolePersistenceIface;
	}

	public void setPointTypePersistenceIface(PointTypePersistenceIface pointTypePersistenceIface) {
		this.pointTypePersistenceIface = pointTypePersistenceIface;
	}

	public void setPubCompanyPersistenceIface(PubCompanyPersistenceIface pubCompanyPersistenceIface) {
		this.pubCompanyPersistenceIface = pubCompanyPersistenceIface;
	}

	public TCompanyPersistenceIface getTcompanyPersistenceIface() {
		return tcompanyPersistenceIface;
	}

	public void setTcompanyPersistenceIface(TCompanyPersistenceIface tcompanyPersistenceIface) {
		this.tcompanyPersistenceIface = tcompanyPersistenceIface;
	}

	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	public PubCompanyPersistenceIface getPubCompanyPersistenceIface() {
		return pubCompanyPersistenceIface;
	}

	public EnumPersistenceIface getEnumPersistenceIface() {
		return enumPersistenceIface;
	}

	public void setEnumPersistenceIface(EnumPersistenceIface enumPersistenceIface) {
		this.enumPersistenceIface = enumPersistenceIface;
	}

	public DepartmentPersistenceIface getDepartmentPersistenceIface() {
		return departmentPersistenceIface;
	}

	public void setDepartmentPersistenceIface(DepartmentPersistenceIface departmentPersistenceIface) {
		this.departmentPersistenceIface = departmentPersistenceIface;
	}

	public TradeTypePersistenceIface getTradeTypePersistenceIface() {
		return tradeTypePersistenceIface;
	}

	/**
	 * @return the etlDaCompanyPersistenceIface
	 */
	public ETLDaCompanyPersistenceIface getEtlDaCompanyPersistenceIface() {
		return etlDaCompanyPersistenceIface;
	}

	/**
	 * @param etlDaCompanyPersistenceIface the etlDaCompanyPersistenceIface to set
	 */
	public void setEtlDaCompanyPersistenceIface(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface) {
		this.etlDaCompanyPersistenceIface = etlDaCompanyPersistenceIface;
	}
	
}
