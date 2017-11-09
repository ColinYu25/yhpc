package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.dao.criterion.DetachedCriteriaProxy2;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.model.VDaCompanyIndustryRel;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.PubCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.TradeTypePersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.VDaComIndRelPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public class ETLDaCompanyPersistenceImpl implements ETLDaCompanyPersistenceIface {

	private CompanyPersistenceIface companyPersistenceIface;

	private TCompanyPersistenceIface tcompanyPersistenceIface;

	private TradeTypePersistenceIface tradeTypePersistenceIface;

	private PubCompanyPersistenceIface pubCompanyPersistenceIface;

	private VDaComIndRelPersistenceIface vdaComIndRelPersistenceIface;

	/**
	 * da_company与t_company同步(定时)
	 * 按业务规则初始化企业基础信息；其中[工商注册号]、[注册地址]、[成立日期]、[组织机构代码]、[法定代表人（主要负责人）]、[行业分类(管理分类
	 * )]、[ 经济类型]、 [国民经济分类]、[经营范围]信息修改以中心库为高优先级，其它字段以隐患排查为高优先级。
	 * @return
	 * @throws ApplicationAccessException
	 */
	
	public boolean loadSynDaCompany() throws Exception {
		System.out.println("t_company  ==>更新  da_company");
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(TCompany.class); 
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSYN", 1l));
		//detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select  d.id  from  da_company d  where  d.uuid=this_.uuid) "));  //and   this_.id<=650000
		//不能根据uuid，因为da_company中有的uuid为空。可以根据isHidden来判断
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isHidden", 1)); 
		List<TCompany> company = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy, null);
		//从配置文件中获取工商质检检查控制变量开关，0表示不需要这个条件过滤，1标识需要这个条件过滤
		String checkFlag = ConfigUtil.getPropertyValue("GS_ZJ_CHECK_FLSG");
		if (company.size() > 0) {
			for (TCompany t : company) {
				DaCompany daCompany =null;
				if( t.getUuid()!=null&&!"".equals(t.getUuid())&&!"null".equals(t.getUuid())){
					DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
					detachedCriteriaProxy1.add(RestrictionsProxy.eq("d.uuid", t.getUuid()));
					daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy1);
					
				}
				if (daCompany != null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());
					System.out.println("中心库gs_id为:___________" + t.getGsId());
					System.out.println("中心库zj_id为:___________" + t.getZjId());
					//是否要同步中心库优先级高的数据
					boolean flag=getCheckFlag(t,checkFlag);
					if(flag){
						// ----以下以中心库为高优先级，直接覆盖隐患系统 (重要)
						if (t.getCompanyName() != null && !t.getCompanyName().equals("")&& !t.getCompanyName().equals("null")) {
							daCompany.setCompanyName(t.getCompanyName()); // 企业名称
						}
						
						if (t.getBusinessRegNum() != null && !t.getBusinessRegNum().equals("")&& !t.getBusinessRegNum().equals("null")) {
							daCompany.setRegNum(t.getBusinessRegNum()); // 工商注册号
							//设置是否有工商注册号为有
							daCompany.setHaveRegNum("1");
						}
						if (t.getRegAddress() != null && !t.getRegAddress().equals("")) {
							daCompany.setRegAddress(t.getRegAddress()); // 注册地址
						}
						if (t.getEstablishmentDay() != null && !t.getEstablishmentDay().equals("")) {
							daCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
						}
						if (t.getOrgCode() != null && !t.getOrgCode().equals("")&&!t.getOrgCode().equals("null")) {
							daCompany.setSetupNumber(t.getOrgCode()); // 组织机构代码
						}
						if (t.getPrincipalPerson() != null && !t.getPrincipalPerson().equals("")) {
							daCompany.setFddelegate(t.getPrincipalPerson()); // 法定代表人（主要负责人）
						}
						if (t.getEconomicType1() != null && !t.getEconomicType1().equals("")) {
							daCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
						}
						if (t.getEconomicType2() != null && !t.getEconomicType2().equals("")) {
							daCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
						}
						if (t.getNationalEconomicType1() != null && !t.getNationalEconomicType1().equals("")) {
							daCompany.setNaEcoType1(t.getNationalEconomicType1());// 国民经济分类1
						}
						if (t.getNationalEconomicType2() != null && !t.getNationalEconomicType2().equals("")) {
							daCompany.setNaEcoType2(t.getNationalEconomicType2());// 国民经济分类2
						}
						if (t.getBusinessScope() != null && !t.getBusinessScope().equals("")) {
							daCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
						}
					}
					// -------------------------- 以下隐患系统中若为空则更新,否则不更新,隐患系统为高优先级
//					if (daCompany.getCompanyName() == null || "".equals(daCompany.getCompanyName())) {
//						daCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (daCompany.getAddress() == null || "".equals(daCompany.getAddress())) {
						daCompany.setAddress(t.getBusinessAddress()); // 所在地址
					}
					if (daCompany.getFirstArea() == null || "".equals(daCompany.getFirstArea())) {
						daCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (daCompany.getSecondArea() == null || "".equals(daCompany.getSecondArea())) {
						daCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					if (daCompany.getThirdArea() == null || "".equals(daCompany.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证隐患的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断隐患当前的二级区域是否和中心库的二级区域相等，因为中心库可以保证区域之间是父子关系
						if(daCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&daCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							daCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (daCompany.getFouthArea() == null || "".equals(daCompany.getFouthArea())) {
						//此处同上添加判断条件
						if(daCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&daCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							daCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (daCompany.getFifthArea() == null || "".equals(daCompany.getFifthArea())) {
						//此处同上添加判断条件
						if(daCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&daCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							daCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (daCompany.getPhoneCode() == null || "".equals(daCompany.getPhoneCode())) {
						if(t.getPrincipalTelephone()!=null&&!"".equals(t.getPrincipalTelephone())&&!"null".equals(t.getPrincipalTelephone())){
							daCompany.setPhoneCode(t.getPrincipalTelephone()); // 联系电话
						}
					}
					if (daCompany.getSafetyMngPerson() == null || "".equals(daCompany.getSafetyMngPerson())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalPerson()); // 安管负责人
					}
					if (daCompany.getSafetyMngPersonPhone() == null || "".equals(daCompany.getSafetyMngPersonPhone())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalTel()); // 安管负责人联系电话
					}
					if (daCompany.getProductionScale() == null || "".equals(daCompany.getProductionScale())) {
						daCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}
					companyPersistenceIface.updateCompany(daCompany);
					// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
					updateHyBeforeCheck(t, daCompany);
					// 将TCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update TCompany com set com.isSYN=0,com.deleted=0  where com.id=" + t.getId());
				} else { // uuid查询不到,根据企业名称、工商注册号、组织机构代码更新
					System.out.println(" uuid查询不到,根据企业名称、工商注册号、组织机构代码更新: " + t.getCompanyName());
					//StringBuffer sb=new StringBuffer(" (COMPANY_NAME='" + t.getCompanyName().trim() + "' ");
					//if(t.getBusinessRegNum()!=null&&!"".equals(t.getBusinessRegNum())&&!"null".equals(t.getBusinessRegNum())){
					//	sb.append(" or  REGNUM='" + t.getBusinessRegNum() + "' ");
					//}
					//if(t.getOrgCode()!=null&&!"".equals(t.getOrgCode())&&!"null".equals(t.getOrgCode())){
					//	sb.append(" or  SETUP_NUMBER='" + t.getOrgCode() + "' ");
					//}
					//sb.append(")");
					//detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(sb.toString()));
					//还是的按照匹配顺序进行匹配，不然会造成多条数据匹配的问题
					//新根据名称，再根据工商注册号，再更加组织机构代码
					String comonSql=" exists (select 1   from DA_COMPANY_PASS dcp1_  where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1)  and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir) and this_.uuid is  null ";
					if(t.getCompanyName()!=null&&!"".equals(t.getCompanyName().trim())){
						DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
						detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(comonSql));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.deleted",false));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.companyName",t.getCompanyName().trim()));
						daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy2);
					}
					
					if(daCompany==null){
						//再根据工商注册号查询
						if(t.getBusinessRegNum()!=null&&!"".equals(t.getBusinessRegNum().trim())){
							DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
							detachedCriteriaProxy3.add(RestrictionsProxy.sqlRestriction(comonSql));
							detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.deleted",false));
							detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.regNum",t.getBusinessRegNum().trim()));
							daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy3);
						}
					}
					
					if(daCompany==null){
						//再根据组织机构代码查询
						if(t.getOrgCode()!=null&&!"".equals(t.getOrgCode().trim())){
							DetachedCriteriaProxy detachedCriteriaProxy4 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
							detachedCriteriaProxy4.add(RestrictionsProxy.sqlRestriction(comonSql));
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.deleted",false));
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.setupNumber",t.getOrgCode().trim()));
							daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy4);
						}
					}
					
					if (daCompany != null) { // 根据uuid更新
						System.out.println("id: " + daCompany.getId());
						// ----以下以中心库为高优先级，不为空则 直接覆盖隐患系统
						if(daCompany.getUuid()==null||"".equals(daCompany.getUuid())){
							daCompany.setUuid(t.getUuid());
						}
						//是否要同步中心库优先级高的数据
						boolean flag=getCheckFlag(t,checkFlag);
						if(flag){
							
							if (t.getCompanyName() != null && !t.getCompanyName().equals("")&& !t.getCompanyName().equals("null")) {
								daCompany.setCompanyName(t.getCompanyName()); // 企业名称
							}
							if (t.getBusinessRegNum() != null && !t.getBusinessRegNum().equals("")&& !t.getBusinessRegNum().equals("null")) {
								daCompany.setRegNum(t.getBusinessRegNum()); // 工商注册号
								//设置是否有工商注册号为有
								daCompany.setHaveRegNum("1");
							}
							if (t.getRegAddress() != null && !t.getRegAddress().equals("")) {
								daCompany.setRegAddress(t.getRegAddress()); // 注册地址
							}
							if (t.getEstablishmentDay() != null && !t.getEstablishmentDay().equals("")) {
								daCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
							}
							if (t.getOrgCode() != null && !t.getOrgCode().equals("") && !t.getOrgCode().equals("null")) {
								daCompany.setSetupNumber(t.getOrgCode()); // 组织机构代码
							}
							if (t.getPrincipalPerson() != null && !t.getPrincipalPerson().equals("")) {
								daCompany.setFddelegate(t.getPrincipalPerson()); // 法定代表人（主要负责人）
							}
							if (t.getEconomicType1() != null && !t.getEconomicType1().equals("")) {
								daCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
							}
							if (t.getEconomicType2() != null && !t.getEconomicType2().equals("")) {
								daCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
							}
							if (t.getNationalEconomicType1() != null && !t.getNationalEconomicType1().equals("")) {
								daCompany.setNaEcoType1(t.getNationalEconomicType1());// 国民经济分类1
							}
							if (t.getNationalEconomicType2() != null && !t.getNationalEconomicType2().equals("")) {
								daCompany.setNaEcoType2(t.getNationalEconomicType2());// 国民经济分类2
							}
							if (t.getBusinessScope() != null && !t.getBusinessScope().equals("")) {
								daCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
							}
						}
						// -------------------------- 以下隐患系统中若为空则更新,否则不更新
//						if (daCompany.getCompanyName() == null || daCompany.getCompanyName().equals("")) {
//							daCompany.setCompanyName(t.getCompanyName()); // 企业名称
//						}
						if (daCompany.getAddress() == null || daCompany.getAddress().equals("")) {
							daCompany.setAddress(t.getBusinessAddress()); // 所在地址
						}
						if (daCompany.getFirstArea() == null || daCompany.getFirstArea().equals("")) {
							daCompany.setFirstArea(t.getFirstArea()); // 所在区域一
						}
						if (daCompany.getSecondArea() == null || daCompany.getSecondArea().equals("")) {
							daCompany.setSecondArea(t.getSecondArea()); // 所在区域二
						}
						if (daCompany.getThirdArea() == null || "".equals(daCompany.getThirdArea())) {
							//此处添加判断，更新三级区域的时候，为了保证隐患的二级区域能和要更新的三级区域是父子关系。
							//此处只需判断隐患当前的二级区域是否和中心库的二级区域相等，因为中心库可以保证区域之间是父子关系
							if(daCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&daCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
								daCompany.setThirdArea(t.getThirdArea()); // 所在区域三
							}
						}
						if (daCompany.getFouthArea() == null || "".equals(daCompany.getFouthArea())) {
							//此处同上添加判断条件
							if(daCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&daCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
								daCompany.setFouthArea(t.getFouthArea()); // 所在区域四
							}
						}
						if (daCompany.getFifthArea() == null || "".equals(daCompany.getFifthArea())) {
							//此处同上添加判断条件
							if(daCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&daCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
								daCompany.setFifthArea(t.getFifthArea()); // 所在区域五
							}
						}
						if (daCompany.getPhoneCode() == null || "".equals(daCompany.getPhoneCode())) {
							if(t.getPrincipalTelephone()!=null&&!"".equals(t.getPrincipalTelephone())&&!"null".equals(t.getPrincipalTelephone())){
								daCompany.setPhoneCode(t.getPrincipalTelephone()); // 联系电话
							}
						}
						if (daCompany.getSafetyMngPerson() == null || daCompany.getSafetyMngPerson().equals("")) {
							daCompany.setSafetyMngPerson(t.getSafetyMngPerson()); // 安管负责人
						}
						if (daCompany.getSafetyMngPersonPhone() == null || daCompany.getSafetyMngPersonPhone().equals("")) {
							daCompany.setSafetyMngPerson(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
						}
						if (daCompany.getProductionScale() == null || daCompany.getProductionScale().equals("")) {
							daCompany.setProductionScale(t.getProductionScale()); // 企业规模
						}
						companyPersistenceIface.updateCompany(daCompany);
						// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
						updateHyBeforeCheck(t, daCompany);
						// 将TCompany的同步状态变为0
						tcompanyPersistenceIface.executeHQLUpdate("update TCompany com set com.isSYN=0,com.deleted=0   where com.id=" + t.getId());
					}
				}
			}
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 将具体的某个da_company与t_company同步
	 */
	
	public boolean loadSynDaCompany(DaCompany company,TCompany tcompany) throws Exception {
		        System.out.println("t_company  ==>更新  da_company");

		        String checkFlag = ConfigUtil.getPropertyValue("GS_ZJ_CHECK_FLSG");
	
		        DaCompany daCompany=company;
		        TCompany t=tcompany;
				if (t!=null&daCompany!=null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());
					System.out.println("中心库gs_id为:___________" + t.getGsId());
					System.out.println("中心库zj_id为:___________" + t.getZjId());
					//首先设置uuid
					daCompany.setUuid(t.getUuid());
					
					//是否要同步中心库优先级高的数据
					boolean flag=getCheckFlag(t,checkFlag);
					if(flag){
						// ----以下以中心库为高优先级，直接覆盖隐患系统 (重要)
						if (t.getCompanyName() != null && !t.getCompanyName().equals("")&& !t.getCompanyName().equals("null")) {
							daCompany.setCompanyName(t.getCompanyName()); // 企业名称
						}
						if (t.getBusinessRegNum() != null && !t.getBusinessRegNum().equals("")&& !t.getBusinessRegNum().equals("null")) {
							daCompany.setRegNum(t.getBusinessRegNum()); // 工商注册号
							//设置是否有工商注册号为有
							daCompany.setHaveRegNum("1");
						}
						if (t.getRegAddress() != null && !t.getRegAddress().equals("")) {
							daCompany.setRegAddress(t.getRegAddress()); // 注册地址
						}
						if (t.getEstablishmentDay() != null && !t.getEstablishmentDay().equals("")) {
							daCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
						}
						if (t.getOrgCode() != null && !t.getOrgCode().equals("")&&!t.getOrgCode().equals("null")) {
							daCompany.setSetupNumber(t.getOrgCode()); // 组织机构代码
						}
						if (t.getPrincipalPerson() != null && !t.getPrincipalPerson().equals("")) {
							daCompany.setFddelegate(t.getPrincipalPerson()); // 法定代表人（主要负责人）
						}
						if (t.getEconomicType1() != null && !t.getEconomicType1().equals("")) {
							daCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
						}
						if (t.getEconomicType2() != null && !t.getEconomicType2().equals("")) {
							daCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
						}
						if (t.getNationalEconomicType1() != null && !t.getNationalEconomicType1().equals("")) {
							daCompany.setNaEcoType1(t.getNationalEconomicType1());// 国民经济分类1
						}
						if (t.getNationalEconomicType2() != null && !t.getNationalEconomicType2().equals("")) {
							daCompany.setNaEcoType2(t.getNationalEconomicType2());// 国民经济分类2
						}
						if (t.getBusinessScope() != null && !t.getBusinessScope().equals("")) {
							daCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
						}
					}
					// -------------------------- 以下隐患系统中若为空则更新,否则不更新,隐患系统为高优先级
//					if (daCompany.getCompanyName() == null || "".equals(daCompany.getCompanyName())) {
//						daCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (daCompany.getAddress() == null || "".equals(daCompany.getAddress())) {
						daCompany.setAddress(t.getBusinessAddress()); // 所在地址
					}
					if (daCompany.getFirstArea() == null || "".equals(daCompany.getFirstArea())) {
						daCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (daCompany.getSecondArea() == null || "".equals(daCompany.getSecondArea())) {
						daCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					if (daCompany.getThirdArea() == null || "".equals(daCompany.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证隐患的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断隐患当前的二级区域是否和中心库的二级区域相等，因为中心库可以保证区域之间是父子关系
						if(daCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&daCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							daCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (daCompany.getFouthArea() == null || "".equals(daCompany.getFouthArea())) {
						//此处同上添加判断条件
						if(daCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&daCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							daCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (daCompany.getFifthArea() == null || "".equals(daCompany.getFifthArea())) {
						//此处同上添加判断条件
						if(daCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&daCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							daCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (daCompany.getPhoneCode() == null || "".equals(daCompany.getPhoneCode())) {
						if(t.getPrincipalTelephone()!=null&&!"".equals(t.getPrincipalTelephone())&&!"null".equals(t.getPrincipalTelephone())){
							daCompany.setPhoneCode(t.getPrincipalTelephone()); // 联系电话
						}
					}
					if (daCompany.getSafetyMngPerson() == null || "".equals(daCompany.getSafetyMngPerson())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalPerson()); // 安管负责人
					}
					if (daCompany.getSafetyMngPersonPhone() == null || "".equals(daCompany.getSafetyMngPersonPhone())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalTel()); // 安管负责人联系电话
					}
					if (daCompany.getProductionScale() == null || "".equals(daCompany.getProductionScale())) {
						daCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}
					
					daCompany.setModifyTime(new Date());
					companyPersistenceIface.updateCompany(daCompany);
					// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
					updateHyBeforeCheck(t, daCompany);
					// 将TCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update TCompany com set com.isSYN=0,com.deleted=0  where com.id=" + t.getId());
				} 
			
		
		return true;
	}
	
	
	
	/**
	 * da_company  ==>更新  t_company
	 */
	public boolean loadUuidDaCompany() throws Exception {

		System.out.println("T_company 初始化 DA_company uuid ");

		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t"); 
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_  " +
				" where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) " +
				"and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id " +
				"from da_company_industry_rel dcir) and this_.uuid is  null "));//此处一部分一部分的执行。
		List<DaCompany> company = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		System.out.println("company.size() : "+company.size());
		if (company.size() > 0) {
			for (DaCompany t : company) {
				DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("deleted", false));
				TCompany tCompany = null;
				
				//先根据名称匹配
				if(t.getCompanyName()!=null){
					detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", t.getCompanyName()));
					tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
				}
				//为空，再根据工商注册号
				if(tCompany==null){
					if(t.getRegNum()!=null){
						DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", false));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("businessRegNum", t.getRegNum()));
						tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy2);
					}
				
				}
				
				//为空，再根据组织机构代码
				if(tCompany==null){
					if(t.getSetupNumber()!=null){
						DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
						detachedCriteriaProxy3.add(RestrictionsProxy.eq("deleted", false));
						detachedCriteriaProxy3.add(RestrictionsProxy.eq("orgCode", t.getSetupNumber()));
						tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy3);
					}
				
				}
			   
				if (tCompany != null) { 
					System.out.println("uuid:___________" + tCompany.getUuid());
				
		            t.setUuid(tCompany.getUuid());
		            companyPersistenceIface.updateCompany(t);
					
				} 
			} 
				
			}
		System.out.println("T_company 初始化 DA_company uuid 完成");
		return false;
	}

	
	
	
	/**
	 * da_company  ==>更新  t_company
	 */
	public boolean loadSynTCompany() throws Exception {

		
		System.out.println("DA_company  ==>更新  T_company");
		DaIndustryParameter daIndustryParameter0 = null;
		DaIndustryParameter daIndustryParameter1 = null;
		DaIndustryParameter daIndustryParameter2 = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t"); 
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_   where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir) "));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select  id  from  t_company d  where   d.uuid=this_.uuid) "));  //and   this_.id<=650000
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSYN", 1l)); 
		
		
		List<DaCompany> company = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		System.out.println("company.size() : "+company.size());
		if (company.size() > 0) {
			for (DaCompany t : company) {
				DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("uuid", t.getUuid()));
				TCompany tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
				if (tCompany != null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());
					// ----以下以中心库为高优先级,为空则覆盖
					
					if (tCompany.getCompanyName()== null ||"".equals(tCompany.getCompanyName())) {
						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
					}
					
					if (tCompany.getBusinessRegNum()== null ||"".equals(tCompany.getBusinessRegNum())) {
						tCompany.setBusinessRegNum(t.getRegNum()); // 工商注册号
					}
					if (tCompany.getRegAddress() == null || "".equals(tCompany.getRegAddress())) {
						tCompany.setRegAddress(t.getRegAddress()); // 注册地址
					}
					if (tCompany.getEstablishmentDay() == null || "".equals(tCompany.getEstablishmentDay())) {
						tCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
					}
					if (tCompany.getOrgCode() == null ||"".equals(tCompany.getOrgCode())) {
						tCompany.setOrgCode(t.getSetupNumber()); // 组织机构代码
					}
					if (tCompany.getPrincipalPerson() == null || "".equals(tCompany.getPrincipalPerson())) {
						tCompany.setPrincipalPerson(t.getFddelegate()); // 主要负责人
					}
					
					if (tCompany.getEconomicType1() == null || "".equals(tCompany.getEconomicType1())) {
						tCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
					}
					if (tCompany.getEconomicType2() == null || "".equals(tCompany.getEconomicType2())) {
						tCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
					}
					if (tCompany.getNationalEconomicType1()== null || "".equals(tCompany.getNationalEconomicType1())) {
						tCompany.setNationalEconomicType1(t.getNaEcoType1());// 国民经济分类1
					}
					if (tCompany.getNationalEconomicType2() == null || "".equals(tCompany.getNationalEconomicType2())) {
						tCompany.setNationalEconomicType2(t.getNaEcoType2());// 国民经济分类2
					}
					if (tCompany.getBusinessScope()== null|| "".equals(tCompany.getBusinessScope())) {
						tCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
					}

					// -------------------------- 以下隐患系统为高优先级,不为空则 覆盖中心库

//					if (t.getCompanyName()!= null && !"".equals(t.getCompanyName())) {
//						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (t.getAddress()!= null && !"".equals(t.getAddress())) {
						tCompany.setBusinessAddress(t.getAddress()); // 所在地址
					}
					if (t.getFirstArea()!= null &&!"".equals(t.getFirstArea())&&!"0".equals(t.getFirstArea())) {
						tCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (t.getSecondArea()!= null && !"".equals(t.getSecondArea())&& !"0".equals(t.getSecondArea())) {
						tCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					
					
					if (t.getThirdArea()!= null &&!"".equals(t.getThirdArea())&&!"0".equals(t.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证中心库的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断中心库当前的二级区域是否和隐患的二级区域相等，因为隐患可以保证区域之间是父子关系
						if(tCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&tCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							tCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (t.getFouthArea() != null&& !"".equals(t.getFouthArea())&& !"0".equals(t.getFouthArea())) {
						if(tCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&tCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							tCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (t.getFifthArea() != null && !"".equals(t.getFifthArea())&& !"0".equals(t.getFifthArea())) {
						if(tCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&tCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							tCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (t.getPhoneCode()!= null&& !"".equals(t.getPhoneCode())) {
						tCompany.setPrincipalTelephone(t.getPhoneCode()); // 主要负责人联系电话
					}
				
					if (t.getSafetyMngPerson()!= null && !"".equals(t.getSafetyMngPerson())) {
						tCompany.setSecurityPrincipalPerson(t.getSafetyMngPerson()); // 安管负责人
					}
					if (t.getSafetyMngPersonPhone() != null && !"".equals(t.getSafetyMngPersonPhone())) {
						tCompany.setSecurityPrincipalTel(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
					}
					if (t.getProductionScale() != null&& !"".equals(t.getProductionScale())) {
						tCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}

					if(t.getDaCompanyPass()!=null){
						tCompany.setEnterprise(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0);
					}
					/**
					// 先找出所有的行业分类
					Set<DaIndustryParameter> hzTradeTypes =  t.getHzTradeTypes();
				
					// 找出父节点为空的，即为行业部门。
					for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);

						if (d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}
                   **/
					
					List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(t.getId());
					// 找出父节点为空的，即为行业部门。
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
                        
						if (d!=null&&d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}

					System.out.println("daIndustryParameter2: " + daIndustryParameter2);

				
					if (daIndustryParameter0 != null) {
						tCompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
					}

					if (daIndustryParameter1 != null &&daIndustryParameter1.getCode()!=null&& daIndustryParameter1.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
					} else {
						tCompany.setManagementLevel1("");
					}

					if (daIndustryParameter2 != null &&daIndustryParameter2.getCode()!=null&& daIndustryParameter2.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
					} else {
						tCompany.setManagementLevel2("");
					}

					
					tcompanyPersistenceIface.updateCompany(tCompany);

					// 将DaCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update DaCompany com set com.isSYN=0  where com.id=" + t.getId());
				} 
			} 
				
			}
		return false;
	}
	
	
	
	/**
	 * 指定某个da_company  ==>更新  t_company
	 */
	public boolean loadSynTCompany(DaCompany dacompany,TCompany company) throws Exception {

		
		            System.out.println("DA_company  ==>更新  T_company");
	
				    DaCompany t=dacompany;
				    TCompany tCompany = company;
		
					System.out.println("uuid:___________" + t.getUuid());
					// ----以下以中心库为高优先级,为空则覆盖
					if (tCompany.getCompanyName()== null ||"".equals(tCompany.getCompanyName())) {
						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
					}
					if (tCompany.getBusinessRegNum()== null ||"".equals(tCompany.getBusinessRegNum())) {
						tCompany.setBusinessRegNum(t.getRegNum()); // 工商注册号
					}
					if (tCompany.getRegAddress() == null || "".equals(tCompany.getRegAddress())) {
						tCompany.setRegAddress(t.getRegAddress()); // 注册地址
					}
					if (tCompany.getEstablishmentDay() == null || "".equals(tCompany.getEstablishmentDay())) {
						tCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
					}
					if (tCompany.getOrgCode() == null ||"".equals(tCompany.getOrgCode())) {
						tCompany.setOrgCode(t.getSetupNumber()); // 组织机构代码
					}
					if (tCompany.getPrincipalPerson() == null || "".equals(tCompany.getPrincipalPerson())) {
						tCompany.setPrincipalPerson(t.getFddelegate()); // 主要负责人
					}
					
					if (tCompany.getEconomicType1() == null || "".equals(tCompany.getEconomicType1())) {
						tCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
					}
					if (tCompany.getEconomicType2() == null || "".equals(tCompany.getEconomicType2())) {
						tCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
					}
					if (tCompany.getNationalEconomicType1()== null || "".equals(tCompany.getNationalEconomicType1())) {
						tCompany.setNationalEconomicType1(t.getNaEcoType1());// 国民经济分类1
					}
					if (tCompany.getNationalEconomicType2() == null || "".equals(tCompany.getNationalEconomicType2())) {
						tCompany.setNationalEconomicType2(t.getNaEcoType2());// 国民经济分类2
					}
					if (tCompany.getBusinessScope()== null|| "".equals(tCompany.getBusinessScope())) {
						tCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
					}

					// -------------------------- 以下隐患系统为高优先级,不为空则 覆盖中心库

//					if (t.getCompanyName()!= null && !"".equals(t.getCompanyName())) {
//						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (t.getAddress()!= null && !"".equals(t.getAddress())) {
						tCompany.setBusinessAddress(t.getAddress()); // 所在地址
					}
					if (t.getFirstArea()!= null &&!"".equals(t.getFirstArea())&&!"0".equals(t.getFirstArea())) {
						tCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (t.getSecondArea()!= null && !"".equals(t.getSecondArea())&& !"0".equals(t.getSecondArea())) {
						tCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					
					
					if (t.getThirdArea()!= null &&!"".equals(t.getThirdArea())&&!"0".equals(t.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证中心库的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断中心库当前的二级区域是否和隐患的二级区域相等，因为隐患可以保证区域之间是父子关系
						if(tCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&tCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							tCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (t.getFouthArea() != null&& !"".equals(t.getFouthArea())&& !"0".equals(t.getFouthArea())) {
						if(tCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&tCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							tCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (t.getFifthArea() != null && !"".equals(t.getFifthArea())&& !"0".equals(t.getFifthArea())) {
						if(tCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&tCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							tCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (t.getPhoneCode()!= null&& !"".equals(t.getPhoneCode())) {
						tCompany.setPrincipalTelephone(t.getPhoneCode()); // 主要负责人联系电话
					}
				
					if (t.getSafetyMngPerson()!= null && !"".equals(t.getSafetyMngPerson())) {
						tCompany.setSecurityPrincipalPerson(t.getSafetyMngPerson()); // 安管负责人
					}
					if (t.getSafetyMngPersonPhone() != null && !"".equals(t.getSafetyMngPersonPhone())) {
						tCompany.setSecurityPrincipalTel(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
					}
					if (t.getProductionScale() != null&& !"".equals(t.getProductionScale())) {
						tCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}

					if(t.getDaCompanyPass()!=null){
						tCompany.setEnterprise(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0);
					}
				
					DaIndustryParameter daIndustryParameter0 = null;
					DaIndustryParameter daIndustryParameter1 = null;
					DaIndustryParameter daIndustryParameter2 = null;
					List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(t.getId());
					// 找出父节点为空的，即为行业部门。
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
                        
						if (d!=null&&d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}

					System.out.println("daIndustryParameter2: " + daIndustryParameter2);

				
					if (daIndustryParameter0 != null) {
						tCompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
					}

					if (daIndustryParameter1 != null &&daIndustryParameter1.getCode()!=null&& daIndustryParameter1.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
					} else {
						tCompany.setManagementLevel1("");
					}

					if (daIndustryParameter2 != null &&daIndustryParameter2.getCode()!=null&& daIndustryParameter2.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
					} else {
						tCompany.setManagementLevel2("");
					}

					
					tcompanyPersistenceIface.updateCompany(tCompany);

					// 将DaCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update DaCompany com set com.isSYN=0  where com.id=" + t.getId());
				 
		
		            return true;
	}

	/**
	 * da_company中存在 t_company不存在 插入 (初始化)
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean loadInsertSynDaCompany() throws Exception {

		System.out.println("da_company中存在   t_company不存在");
		int x = 0;
		String hycode = "";
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t");
		//detachedCriteriaProxy.add(RestrictionsProxy.eq("id", 361l)); // 奉化市维达斯制衣厂
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" exists (select 1 from da_company_pass p  where this_.id = p.par_da_com_id  and  this_.is_deleted=0  and p.is_deleted = 0 and p.is_affirm = 1)　and exists (select 1 from DA_COMPANY_INDUSTRY_REL d where d.par_da_com_id = this_.id) and not exists (select 1  from t_company c where c.uuid = this_.uuid  )  and  not  exists  (select 1 from t_company c  where  c.company_name=this_.company_name   and  c.uuid is null  ) "));
		List<DaCompany> dacompany = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		if (dacompany.size() > 0) {
			for (DaCompany t : dacompany) {
				TCompany tcompany = new TCompany();
				
				tcompany.setCompanyName(t.getCompanyName());
				tcompany.setBusinessRegNum(t.getRegNum());
				tcompany.setRegAddress(t.getRegAddress());
				tcompany.setEstablishmentDay(t.getEstablishmentDay());
				tcompany.setOrgCode(t.getSetupNumber());
				tcompany.setPrincipalPerson(t.getFddelegate());
				tcompany.setEconomicType1(t.getEconomicType1());
				tcompany.setEconomicType2(t.getEconomicType2());
				tcompany.setNationalEconomicType1(t.getNaEcoType1());
				tcompany.setNationalEconomicType2(t.getNaEcoType2());
				tcompany.setBusinessScope(t.getBusinessScope());
				tcompany.setBusinessAddress(t.getAddress());
				tcompany.setFirstArea(t.getFirstArea());
				tcompany.setSecondArea(t.getSecondArea());
				tcompany.setThirdArea(t.getThirdArea());
				tcompany.setPrincipalTelephone(t.getPhoneCode());
				tcompany.setSecurityPrincipalPerson(t.getSafetyMngPerson());
				tcompany.setSecurityPrincipalTel(t.getSafetyMngPersonPhone());
				tcompany.setProductionScale(t.getProductionScale());
				tcompany.setCreateTime(t.getCreateTime());
				tcompany.setModifyTime(t.getModifyTime());
				tcompany.setDeleted(false);
				//tcompany.setIsGatherData(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0); // 重点规模企业
				
				if(t.getDaCompanyPass()!=null){
					tcompany.setEnterprise(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0);
				}
				
				tcompany.setIsHidden(1);
				tcompany.setIsSynchro(1);
				tcompany.setUuid(t.getUuid());
				
				
				//add by huangjl
				tcompany.setIsSYN(new Long(0));

				// 行业、部门
//				List<VDaCompanyIndustryRel> vdu = vdaComIndRelPersistenceIface.getHy(t.getId());
//				if (vdu.size() > 0) {
//					x = 0;
//					hycode = "";
//					for (VDaCompanyIndustryRel r : vdu) {
//
//						hycode = ftrans(r.getParDaIndId());
//						
//						if (hycode!=null  && !hycode.equals("")) {
//							if (x == 0) { // 部门
//								tcompany.setDeptCode(hycode);
//								if (!hycode.equals("anjian")) { // 若不为安监,则退出,因为中心库没有对应值
//									break;
//								}
//							} else if (x == 1) { // 一级行业
//								tcompany.setManagementLevel1(hycode);
//							} else if (x == 2) {// 二级行业
//								tcompany.setManagementLevel2(hycode);
//							}
//						}
//						x++;
//					}
//				}
				
				DaIndustryParameter daIndustryParameter0 = null;
				DaIndustryParameter daIndustryParameter1 = null;
				DaIndustryParameter daIndustryParameter2 = null;
				List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(t.getId());
				// 找出父节点为空的，即为行业部门。
				for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {

					DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());

					if (d!=null&&d.getDaIndustryParameter() == null) {

						daIndustryParameter0 = d;
					}
				}

				// 找出行业部门下面的一级行业。
				if (daIndustryParameter0 != null) {
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
						if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
							daIndustryParameter1 = d;
						}
					}
				}

				// 找出行业部门下面的二级行业。
				if (daIndustryParameter1 != null) {
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
						if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
							daIndustryParameter2 = d;
						}
					}
				}

				System.out.println("daIndustryParameter2: " + daIndustryParameter2);

				if (daIndustryParameter0 != null) {
					tcompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
				}

				if (daIndustryParameter1 != null &&daIndustryParameter1.getCode()!=null&& daIndustryParameter1.getCode().indexOf("undefined") < 0) {
					tcompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
				} else {
					tcompany.setManagementLevel1("");
				}

				if (daIndustryParameter2 != null &&daIndustryParameter2.getCode()!=null&& daIndustryParameter2.getCode().indexOf("undefined") < 0) {
					tcompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
				} else {
					tcompany.setManagementLevel2("");
				}


				
				System.out.println("tcompany.getCompanyName(): "+tcompany.getCompanyName());
				tcompanyPersistenceIface.createCompany(tcompany);

			}
		}
		return false;
	}

	
	
	
	
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * da_company与t_company同步(定时)
	 * 按业务规则初始化企业基础信息；其中[工商注册号]、[注册地址]、[成立日期]、[组织机构代码]、[法定代表人（主要负责人）]、[行业分类(管理分类
	 * )]、[ 经济类型]、 [国民经济分类]、[经营范围]信息修改以中心库为高优先级，其它字段以隐患排查为高优先级。
	 * @return
	 * @throws ApplicationAccessException
	 */
	
	public boolean loadSynDaCompany(Long startId,Long endId) throws Exception {
		System.out.println("t_company  ==>更新  da_company");
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(TCompany.class); 
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSYN", 1l)); 
		//detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select  id  from  da_company d  where  d.uuid=this_.uuid) and  this_.id<="+endId+" and this_.id>="+startId+""));  //and   this_.id<=650000
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" this_.id<="+endId+" and this_.id>="+startId+" "));
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isHidden", 1)); 
		List<TCompany> company = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy, null);
		//从配置文件中获取工商质检检查控制变量开关，0表示不需要这个条件过滤，1标识需要这个条件过滤
		String checkFlag = ConfigUtil.getPropertyValue("GS_ZJ_CHECK_FLSG");
		if (company.size() > 0) {
			for (TCompany t : company) {
				DaCompany daCompany =null;
				if( t.getUuid()!=null&&!"".equals(t.getUuid())&&!"null".equals(t.getUuid())){
					DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
					detachedCriteriaProxy1.add(RestrictionsProxy.eq("d.uuid", t.getUuid()));
				    daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy1);
				}
			
				if (daCompany != null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());
					System.out.println("中心库gs_id为:___________" + t.getGsId());
					System.out.println("中心库zj_id为:___________" + t.getZjId());
					//是否要同步中心库优先级高的数据
					boolean flag=getCheckFlag(t,checkFlag);
					if(flag){
						// ----以下以中心库为高优先级，直接覆盖隐患系统 (重要)
						
						if (t.getCompanyName()!= null && !t.getCompanyName().equals("")&& !t.getCompanyName().equals("null")) {
							daCompany.setCompanyName(t.getCompanyName()); // 企业名称
						}
						
						if (t.getBusinessRegNum() != null && !t.getBusinessRegNum().equals("")&& !t.getBusinessRegNum().equals("null")) {
							daCompany.setRegNum(t.getBusinessRegNum()); // 工商注册号
							//设置是否有工商注册号为有
							daCompany.setHaveRegNum("1");
						}
						if (t.getRegAddress() != null && !t.getRegAddress().equals("")) {
							daCompany.setRegAddress(t.getRegAddress()); // 注册地址
						}
						if (t.getEstablishmentDay() != null && !t.getEstablishmentDay().equals("")) {
							daCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
						}
						if (t.getOrgCode() != null && !t.getOrgCode().equals("")&&!t.getOrgCode().equals("null")) {
							daCompany.setSetupNumber(t.getOrgCode()); // 组织机构代码
						}
						if (t.getPrincipalPerson() != null && !t.getPrincipalPerson().equals("")) {
							daCompany.setFddelegate(t.getPrincipalPerson()); // 法定代表人（主要负责人）
						}
						if (t.getEconomicType1() != null && !t.getEconomicType1().equals("")) {
							daCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
						}
						if (t.getEconomicType2() != null && !t.getEconomicType2().equals("")) {
							daCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
						}
						if (t.getNationalEconomicType1() != null && !t.getNationalEconomicType1().equals("")) {
							daCompany.setNaEcoType1(t.getNationalEconomicType1());// 国民经济分类1
						}
						if (t.getNationalEconomicType2() != null && !t.getNationalEconomicType2().equals("")) {
							daCompany.setNaEcoType2(t.getNationalEconomicType2());// 国民经济分类2
						}
						if (t.getBusinessScope() != null && !t.getBusinessScope().equals("")) {
							daCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
						}
					}
					// -------------------------- 以下隐患系统中若为空则更新,否则不更新,隐患系统为高优先级
//					if (daCompany.getCompanyName() == null || "".equals(daCompany.getCompanyName())) {
//						daCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (daCompany.getAddress() == null || "".equals(daCompany.getAddress())) {
						daCompany.setAddress(t.getBusinessAddress()); // 所在地址
					}
					if (daCompany.getFirstArea() == null || "".equals(daCompany.getFirstArea())) {
						daCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (daCompany.getSecondArea() == null || "".equals(daCompany.getSecondArea())) {
						daCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					if (daCompany.getThirdArea() == null || "".equals(daCompany.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证隐患的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断隐患当前的二级区域是否和中心库的二级区域相等，因为中心库可以保证区域之间是父子关系
						if(daCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&daCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							daCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (daCompany.getFouthArea() == null || "".equals(daCompany.getFouthArea())) {
						//此处同上添加判断条件
						if(daCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&daCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							daCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (daCompany.getFifthArea() == null || "".equals(daCompany.getFifthArea())) {
						//此处同上添加判断条件
						if(daCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&daCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							daCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (daCompany.getPhoneCode() == null || "".equals(daCompany.getPhoneCode())) {
						if(t.getPrincipalTelephone()!=null&&!"".equals(t.getPrincipalTelephone())&&!"null".equals(t.getPrincipalTelephone())){
							daCompany.setPhoneCode(t.getPrincipalTelephone()); // 联系电话
						}
					}
					if (daCompany.getSafetyMngPerson() == null || "".equals(daCompany.getSafetyMngPerson())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalPerson()); // 安管负责人
					}
					if (daCompany.getSafetyMngPersonPhone() == null || "".equals(daCompany.getSafetyMngPersonPhone())) {
						daCompany.setSafetyMngPerson(t.getSecurityPrincipalTel()); // 安管负责人联系电话
					}
					if (daCompany.getProductionScale() == null || "".equals(daCompany.getProductionScale())) {
						daCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}
					companyPersistenceIface.updateCompany(daCompany);
					// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
					updateHyBeforeCheck(t, daCompany);
					// 将TCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update TCompany com set com.isSYN=0  where com.id=" + t.getId());
				} else { // uuid查询不到,根据企业名称、工商注册号、组织机构代码更新
					System.out.println(" uuid查询不到,根据企业名称、工商注册号、组织机构代码更新: " + t.getCompanyName());
					//StringBuffer sb=new StringBuffer(" (COMPANY_NAME='" + t.getCompanyName().trim() + "' ");
					//if(t.getBusinessRegNum()!=null&&!"".equals(t.getBusinessRegNum())&&!"null".equals(t.getBusinessRegNum())){
					//	sb.append(" or  REGNUM='" + t.getBusinessRegNum() + "' ");
					//}
					//if(t.getOrgCode()!=null&&!"".equals(t.getOrgCode())&&!"null".equals(t.getOrgCode())){
					//	sb.append(" or  SETUP_NUMBER='" + t.getOrgCode() + "' ");
					//}
					//sb.append(")");
					//detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(sb.toString()));
					//还是的按照匹配顺序进行匹配，不然会造成多条数据匹配的问题
					//新根据名称，再根据工商注册号，再更加组织机构代码
					String comonSql=" exists (select 1   from DA_COMPANY_PASS dcp1_  where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1)  and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir) and this_.uuid is  null ";
					if(t.getCompanyName()!=null&&!"".equals(t.getCompanyName().trim())){
						DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
						detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(comonSql));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.deleted",false));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.companyName",t.getCompanyName().trim()));
						daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy2);
					}
					
					if(daCompany==null){
						//再根据工商注册号查询
						if(t.getBusinessRegNum()!=null&&!"".equals(t.getBusinessRegNum().trim())){
							DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
							detachedCriteriaProxy3.add(RestrictionsProxy.sqlRestriction(comonSql));
							detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.deleted",false));
							detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.regNum",t.getBusinessRegNum().trim()));
							daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy3);
						}
					}
					
					if(daCompany==null){
						//再根据组织机构代码查询
						if(t.getOrgCode()!=null&&!"".equals(t.getOrgCode().trim())){
							DetachedCriteriaProxy detachedCriteriaProxy4 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
							detachedCriteriaProxy4.add(RestrictionsProxy.sqlRestriction(comonSql));
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.deleted",false));
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.setupNumber",t.getOrgCode().trim()));
							daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy4);
						}
					}
					
					
					if (daCompany != null) { // 根据uuid更新
						System.out.println("id: " + daCompany.getId());
						// ----以下以中心库为高优先级，不为空则 直接覆盖隐患系统
						if(daCompany.getUuid()==null||"".equals(daCompany.getUuid())){
							daCompany.setUuid(t.getUuid());
						}
						//是否要同步中心库优先级高的数据
						boolean flag=getCheckFlag(t,checkFlag);
						if(flag){
							if (t.getCompanyName()!= null && !t.getCompanyName().equals("")&& !t.getCompanyName().equals("null")) {
								daCompany.setCompanyName(t.getCompanyName()); // 企业名称
							}
							
							if (t.getBusinessRegNum() != null && !t.getBusinessRegNum().equals("")&& !t.getBusinessRegNum().equals("null")) {
								daCompany.setRegNum(t.getBusinessRegNum()); // 工商注册号
								//设置是否有工商注册号为有
								daCompany.setHaveRegNum("1");
							}
							if (t.getRegAddress() != null && !t.getRegAddress().equals("")) {
								daCompany.setRegAddress(t.getRegAddress()); // 注册地址
							}
							if (t.getEstablishmentDay() != null && !t.getEstablishmentDay().equals("")) {
								daCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
							}
							if (t.getOrgCode() != null && !t.getOrgCode().equals("") && !t.getOrgCode().equals("null")) {
								daCompany.setSetupNumber(t.getOrgCode()); // 组织机构代码
							}
							if (t.getPrincipalPerson() != null && !t.getPrincipalPerson().equals("")) {
								daCompany.setFddelegate(t.getPrincipalPerson()); // 法定代表人（主要负责人）
							}
							if (t.getEconomicType1() != null && !t.getEconomicType1().equals("")) {
								daCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
							}
							if (t.getEconomicType2() != null && !t.getEconomicType2().equals("")) {
								daCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
							}
							if (t.getNationalEconomicType1() != null && !t.getNationalEconomicType1().equals("")) {
								daCompany.setNaEcoType1(t.getNationalEconomicType1());// 国民经济分类1
							}
							if (t.getNationalEconomicType2() != null && !t.getNationalEconomicType2().equals("")) {
								daCompany.setNaEcoType2(t.getNationalEconomicType2());// 国民经济分类2
							}
							if (t.getBusinessScope() != null && !t.getBusinessScope().equals("")) {
								daCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
							}
						}
						// -------------------------- 以下隐患系统中若为空则更新,否则不更新
//						if (daCompany.getCompanyName() == null || daCompany.getCompanyName().equals("")) {
//							daCompany.setCompanyName(t.getCompanyName()); // 企业名称
//						}
						if (daCompany.getAddress() == null || daCompany.getAddress().equals("")) {
							daCompany.setAddress(t.getBusinessAddress()); // 所在地址
						}
						if (daCompany.getFirstArea() == null || daCompany.getFirstArea().equals("")) {
							daCompany.setFirstArea(t.getFirstArea()); // 所在区域一
						}
						if (daCompany.getSecondArea() == null || daCompany.getSecondArea().equals("")) {
							daCompany.setSecondArea(t.getSecondArea()); // 所在区域二
						}
						if (daCompany.getThirdArea() == null || "".equals(daCompany.getThirdArea())) {
							//此处添加判断，更新三级区域的时候，为了保证隐患的二级区域能和要更新的三级区域是父子关系。
							//此处只需判断隐患当前的二级区域是否和中心库的二级区域相等，因为中心库可以保证区域之间是父子关系
							if(daCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&daCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
								daCompany.setThirdArea(t.getThirdArea()); // 所在区域三
							}
						}
						if (daCompany.getFouthArea() == null || "".equals(daCompany.getFouthArea())) {
							//此处同上添加判断条件
							if(daCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&daCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
								daCompany.setFouthArea(t.getFouthArea()); // 所在区域四
							}
						}
						if (daCompany.getFifthArea() == null || "".equals(daCompany.getFifthArea())) {
							//此处同上添加判断条件
							if(daCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&daCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
								daCompany.setFifthArea(t.getFifthArea()); // 所在区域五
							}
						}
						if (daCompany.getPhoneCode() == null || "".equals(daCompany.getPhoneCode())) {
							if(t.getPrincipalTelephone()!=null&&!"".equals(t.getPrincipalTelephone())&&!"null".equals(t.getPrincipalTelephone())){
								daCompany.setPhoneCode(t.getPrincipalTelephone()); // 联系电话
							}
						}
						if (daCompany.getSafetyMngPerson() == null || daCompany.getSafetyMngPerson().equals("")) {
							daCompany.setSafetyMngPerson(t.getSafetyMngPerson()); // 安管负责人
						}
						if (daCompany.getSafetyMngPersonPhone() == null || daCompany.getSafetyMngPersonPhone().equals("")) {
							daCompany.setSafetyMngPerson(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
						}
						if (daCompany.getProductionScale() == null || daCompany.getProductionScale().equals("")) {
							daCompany.setProductionScale(t.getProductionScale()); // 企业规模
						}
						companyPersistenceIface.updateCompany(daCompany);
						// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
						updateHyBeforeCheck(t, daCompany);
						// 将TCompany的同步状态变为0
						tcompanyPersistenceIface.executeHQLUpdate("update TCompany com set com.isSYN=0  where com.id=" + t.getId());
					}
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * da_company  ==>更新  t_company
	 */
	public boolean loadUuidDaCompany(Long startId,Long endId) throws Exception {

		System.out.println("T_company 初始化 DA_company uuid ");

		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t"); 
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_  " +
				" where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) " +
				"and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id " +
				"from da_company_industry_rel dcir) and this_.uuid is  null and  this_.id<="+endId+" and this_.id>="+startId+" "));//此处一部分一部分的执行。
		List<DaCompany> company = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		System.out.println("company.size() : "+company.size());
		if (company.size() > 0) {
			for (DaCompany t : company) {
				
				TCompany tCompany = null;
				
				//先根据名称
			     if(t.getCompanyName()!=null&&!"".equals(t.getCompanyName())&&!"null".equals(t.getCompanyName())){
						DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
						detachedCriteriaProxy1.add(RestrictionsProxy.eq("deleted", false));
						detachedCriteriaProxy1.add(RestrictionsProxy.eq("companyName", t.getCompanyName()));
						tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy1);
				}
				//为空，再根据工商注册号匹配
				if(tCompany==null){
					if(t.getRegNum()!=null&&!"".equals(t.getRegNum())&&!"null".equals(t.getRegNum())){
						DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", false));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("businessRegNum", t.getRegNum()));
						tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy2);
					}
				}
				
				//为空，再根据组织机构代码
				if(tCompany==null){
					if(t.getSetupNumber()!=null&&!"".equals(t.getSetupNumber())&&!"null".equals(t.getSetupNumber())){
						DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(TCompany.class, "d");
						detachedCriteriaProxy3.add(RestrictionsProxy.eq("deleted", false));
						detachedCriteriaProxy3.add(RestrictionsProxy.eq("orgCode", t.getSetupNumber()));
						tCompany = tcompanyPersistenceIface.loadCompanies(detachedCriteriaProxy3);
					}
				
				}
				
				//为空，再根据名称匹配
				if(tCompany==null){
					if(t.getCompanyName()!=null&&!"".equals(t.getCompanyName())&&!"null".equals(t.getCompanyName())){
						//在查询删除的
						DetachedCriteriaProxy2 detachedCriteriaProxy6 = DetachedCriteriaProxy2.forClass(TCompany.class);
						detachedCriteriaProxy6.add(RestrictionsProxy.eq("deleted", true));
						detachedCriteriaProxy6.add(RestrictionsProxy.eq("companyName", t.getCompanyName()));
						tCompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy6);
					}
				}
				
				//为空，再根据组织机构代码
				if(tCompany==null){
					//先根据工商注册号
					if(t.getRegNum()!=null&&!"".equals(t.getRegNum())&&!"null".equals(t.getRegNum())){
						    DetachedCriteriaProxy2 detachedCriteriaProxy4 = DetachedCriteriaProxy2.forClass(TCompany.class);
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("deleted", true));
							detachedCriteriaProxy4.add(RestrictionsProxy.eq("businessRegNum", t.getRegNum()));
							tCompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy4);
					}
				}
				//为空，再根据组织机构代码
				if(tCompany==null){
					if(t.getSetupNumber()!=null&&!"".equals(t.getSetupNumber())&&!"null".equals(t.getSetupNumber())){
						DetachedCriteriaProxy2 detachedCriteriaProxy5 = DetachedCriteriaProxy2.forClass(TCompany.class);
						detachedCriteriaProxy5.add(RestrictionsProxy.eq("deleted", true));
						detachedCriteriaProxy5.add(RestrictionsProxy.eq("orgCode", t.getSetupNumber()));
						tCompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy5);
					}
				}
				if (tCompany != null) { 
					System.out.println("uuid:___________" + tCompany.getUuid());
					
					//更新前先查找一下uuid是否已经存在
					DetachedCriteriaProxy detachedCriteriaProxy9 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
					detachedCriteriaProxy9.add(RestrictionsProxy.eq("uuid", tCompany.getUuid()));
					DaCompany dac=companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy9);
					if(dac==null){
						//不存在才允许更新，不然会重复掉
					     t.setUuid(tCompany.getUuid());
				         companyPersistenceIface.updateCompany(t);	
					}
				} 
			} 
				
			}
		System.out.println("T_company 初始化 DA_company uuid 完成");
		return false;
	}

	
	
	
	/**
	 * da_company  ==>更新  t_company
	 */
	public boolean loadSynTCompany(Long startId,Long endId) throws Exception {

		
		System.out.println("DA_company  ==>更新  T_company");
		DaIndustryParameter daIndustryParameter0 = null;
		DaIndustryParameter daIndustryParameter1 = null;
		DaIndustryParameter daIndustryParameter2 = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t"); 
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_   where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir) "));
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  ((this_.MODIFY_TIME is not null and this_.MODIFY_TIME >= to_date('2014-05-01','yyyy-MM-dd')) or (this_.MODIFY_TIME is null and this_.CREATE_TIME >= to_date('2014-05-01','yyyy-MM-dd'))) "));  
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select  id  from  t_company d  where   d.uuid=this_.uuid) "));  //and   this_.id<=650000
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSYN", 1l));
		
		
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id<="+endId+" and this_.id>="+startId+""));//此处一部分一部分的执行。
		List<DaCompany> company = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		System.out.println("company.size() : "+company.size());
		if (company.size() > 0) {
			for (DaCompany t : company) {
				DetachedCriteriaProxy2 detachedCriteriaProxy1 = DetachedCriteriaProxy2.forClass(TCompany.class);
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("uuid", t.getUuid()));
				TCompany tCompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy1);
				if (tCompany != null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());
					// ----以下以中心库为高优先级,为空则覆盖
					
					if (tCompany.getCompanyName()== null || "".equals(tCompany.getCompanyName())) {
						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
					}
					
					if (tCompany.getBusinessRegNum()== null ||"".equals(tCompany.getBusinessRegNum())) {
						if(t.getRegNum()!=null&&!"null".equals(t.getRegNum())){
							tCompany.setBusinessRegNum(t.getRegNum()); // 工商注册号
						}
					}
					if (tCompany.getRegAddress() == null || "".equals(tCompany.getRegAddress())) {
						tCompany.setRegAddress(t.getRegAddress()); // 注册地址
					}
					if (tCompany.getEstablishmentDay() == null || "".equals(tCompany.getEstablishmentDay())) {
						tCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
					}
					if (tCompany.getOrgCode() == null ||"".equals(tCompany.getOrgCode())) {
						if(t.getSetupNumber()!=null&&!"null".equals(t.getSetupNumber())){
							tCompany.setOrgCode(t.getSetupNumber()); // 组织机构代码
						}
					}
					if (tCompany.getPrincipalPerson()== null || "".equals(tCompany.getPrincipalPerson())) {
						tCompany.setPrincipalPerson(t.getFddelegate()); // 法定代表人（主要负责人）
					}
					if (tCompany.getEconomicType1() == null || "".equals(tCompany.getEconomicType1())) {
						tCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
					}
					if (tCompany.getEconomicType2() == null || "".equals(tCompany.getEconomicType2())) {
						tCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
					}
					if (tCompany.getNationalEconomicType1()== null || "".equals(tCompany.getNationalEconomicType1())) {
						tCompany.setNationalEconomicType1(t.getNaEcoType1());// 国民经济分类1
					}
					if (tCompany.getNationalEconomicType2() == null || "".equals(tCompany.getNationalEconomicType2())) {
						tCompany.setNationalEconomicType2(t.getNaEcoType2());// 国民经济分类2
					}
					if (tCompany.getBusinessScope()== null|| "".equals(tCompany.getBusinessScope())) {
						tCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
					}

					// -------------------------- 以下隐患系统为高优先级,不为空则 覆盖中心库

//					if (t.getCompanyName()!= null && !"".equals(t.getCompanyName())) {
//						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
//					}
					if (t.getAddress()!= null && !"".equals(t.getAddress())) {
						tCompany.setBusinessAddress(t.getAddress()); // 所在地址
					}
					if (t.getFirstArea()!= null &&!"".equals(t.getFirstArea())&&!"0".equals(t.getFirstArea())) {
						tCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (t.getSecondArea()!= null && !"".equals(t.getSecondArea())&& !"0".equals(t.getSecondArea())) {
						tCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					if (t.getThirdArea()!= null &&!"".equals(t.getThirdArea())&&!"0".equals(t.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证中心库的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断中心库当前的二级区域是否和隐患的二级区域相等，因为隐患可以保证区域之间是父子关系
						if(tCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&tCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							tCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (t.getFouthArea() != null&& !"".equals(t.getFouthArea())&& !"0".equals(t.getFouthArea())) {
						if(tCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&tCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							tCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (t.getFifthArea() != null && !"".equals(t.getFifthArea())&& !"0".equals(t.getFifthArea())) {
						if(tCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&tCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							tCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (t.getPhoneCode()!= null&& !"".equals(t.getPhoneCode())) {
						tCompany.setPrincipalTelephone(t.getPhoneCode()); // 联系电话
					}
					if (t.getSafetyMngPerson()!= null && !"".equals(t.getSafetyMngPerson())) {
						tCompany.setSecurityPrincipalPerson(t.getSafetyMngPerson()); // 安管负责人
					}
					if (t.getSafetyMngPersonPhone() != null && !"".equals(t.getSafetyMngPersonPhone())) {
						tCompany.setSecurityPrincipalTel(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
					}
					if (t.getProductionScale() != null&& !"".equals(t.getProductionScale())) {
						tCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}

					if(t.getDaCompanyPass()!=null){
						tCompany.setEnterprise(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0);
					}
					
					/**
					// 先找出所有的行业分类
					Set<DaIndustryParameter> hzTradeTypes =  t.getHzTradeTypes();
				
					// 找出父节点为空的，即为行业部门。
					for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);

						if (d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}
                   **/
					
					List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(t.getId());
					// 找出父节点为空的，即为行业部门。
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
                        
						if (d!=null&&d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}

					System.out.println("daIndustryParameter2: " + daIndustryParameter2);

					if (daIndustryParameter0 != null) {
						tCompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
					}

					if (daIndustryParameter1 != null &&daIndustryParameter1.getCode()!=null&& daIndustryParameter1.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
					} else {
						tCompany.setManagementLevel1("");
					}

					if (daIndustryParameter2 != null &&daIndustryParameter2.getCode()!=null&& daIndustryParameter2.getCode().indexOf("undefined") < 0) {
						tCompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
					} else {
						tCompany.setManagementLevel2("");
					}

					
					tcompanyPersistenceIface.updateCompany(tCompany);

					// 将DaCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update DaCompany com set com.isSYN=0  where com.id=" + t.getId());
				} 
			} 
				
			}
		return false;
	}
	
	
	
	/**
	 * da_company  ==>更新  t_company
	 */
	public boolean loadSynTCompanyLow(Long startId,Long endId) throws Exception {
		System.out.println("DA_company  ==>更新  T_company");
		DaIndustryParameter daIndustryParameter0 = null;
		DaIndustryParameter daIndustryParameter1 = null;
		DaIndustryParameter daIndustryParameter2 = null;
		DetachedCriteriaProxy detachedCriteriaProxy = DetachedCriteriaProxy.forClass(DaCompany.class, "t"); 
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select 1   from DA_COMPANY_PASS dcp1_   where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1) and this_.IS_DELETED = 0 and this_.FIRST_AREA = '330200000000' and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir)  "));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  ((this_.MODIFY_TIME is not null and this_.MODIFY_TIME < to_date('2014-05-01','yyyy-MM-dd')) or (this_.MODIFY_TIME is null and this_.CREATE_TIME< to_date('2014-05-01','yyyy-MM-dd'))) "));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("  exists (select  id  from  t_company d  where   d.uuid=this_.uuid) "));  //and   this_.id<=650000
		detachedCriteriaProxy.add(RestrictionsProxy.eq("isSYN", 1l));
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction("this_.id<="+endId+" and this_.id>="+startId+""));//此处一部分一部分的执行。
		
		List<DaCompany> company = companyPersistenceIface.loadCompanies(detachedCriteriaProxy, null);
		System.out.println("company.size() : "+company.size());
		if (company.size() > 0) {
			for (DaCompany t : company) {
				DetachedCriteriaProxy2 detachedCriteriaProxy1 = DetachedCriteriaProxy2.forClass(TCompany.class);
				detachedCriteriaProxy1.add(RestrictionsProxy.eq("uuid", t.getUuid()));
				TCompany tCompany = tcompanyPersistenceIface.loadCompanies2(detachedCriteriaProxy1);
				if (tCompany != null) { // 根据uuid更新
					System.out.println("uuid:___________" + t.getUuid());

					if (tCompany.getCompanyName()== null || "".equals(tCompany.getCompanyName())) {
						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
					}
					
					// ----以下以中心库为高优先级,为空则覆盖
					if (tCompany.getBusinessRegNum()== null ||"".equals(tCompany.getBusinessRegNum())) {
						if(t.getRegNum()!=null&&!"null".equals(t.getRegNum())){
							tCompany.setBusinessRegNum(t.getRegNum()); // 工商注册号
						}
					}
					if (tCompany.getRegAddress() == null || "".equals(tCompany.getRegAddress())) {
						tCompany.setRegAddress(t.getRegAddress()); // 注册地址
					}
					if (tCompany.getEstablishmentDay() == null || "".equals(tCompany.getEstablishmentDay())) {
						tCompany.setEstablishmentDay(t.getEstablishmentDay()); // 成立日期
					}
					if (tCompany.getOrgCode() == null ||"".equals(tCompany.getOrgCode())) {
						if(t.getSetupNumber()!=null&&!"null".equals(t.getSetupNumber())){
							tCompany.setOrgCode(t.getSetupNumber()); // 组织机构代码
						}
					}
					if (tCompany.getPrincipalPerson()== null || "".equals(tCompany.getPrincipalPerson())) {
						tCompany.setPrincipalPerson(t.getFddelegate()); // 法定代表人（主要负责人）
					}
					if (tCompany.getEconomicType1() == null || "".equals(tCompany.getEconomicType1())) {
						tCompany.setEconomicType1(t.getEconomicType1());// 经济类型1
					}
					if (tCompany.getEconomicType2() == null || "".equals(tCompany.getEconomicType2())) {
						tCompany.setEconomicType2(t.getEconomicType2());// 经济类型2
					}
					if (tCompany.getNationalEconomicType1()== null || "".equals(tCompany.getNationalEconomicType1())) {
						tCompany.setNationalEconomicType1(t.getNaEcoType1());// 国民经济分类1
					}
					if (tCompany.getNationalEconomicType2() == null || "".equals(tCompany.getNationalEconomicType2())) {
						tCompany.setNationalEconomicType2(t.getNaEcoType2());// 国民经济分类2
					}
					if (tCompany.getBusinessScope()== null|| "".equals(tCompany.getBusinessScope())) {
						tCompany.setBusinessScope(t.getBusinessScope()); // 经营范围
					}


					if (tCompany.getCompanyName()== null ||"".equals(tCompany.getCompanyName())) {
						tCompany.setCompanyName(t.getCompanyName()); // 企业名称
					}
					if (tCompany.getBusinessAddress()== null || "".equals(tCompany.getBusinessAddress())) {
						tCompany.setBusinessAddress(t.getAddress()); // 所在地址
					}
					if (tCompany.getFirstArea()== null ||"".equals(tCompany.getFirstArea())) {
						tCompany.setFirstArea(t.getFirstArea()); // 所在区域一
					}
					if (tCompany.getSecondArea()== null || "".equals(tCompany.getSecondArea())) {
						tCompany.setSecondArea(t.getSecondArea()); // 所在区域二
					}
					if (tCompany.getThirdArea()== null ||"".equals(tCompany.getThirdArea())) {
						//此处添加判断，更新三级区域的时候，为了保证中心库的二级区域能和要更新的三级区域是父子关系。
						//此处只需判断中心库当前的二级区域是否和隐患的二级区域相等，因为隐患可以保证区域之间是父子关系
						if(tCompany.getSecondArea()!=null&&t.getSecondArea()!=null&&tCompany.getSecondArea().longValue()==t.getSecondArea().longValue()){
							tCompany.setThirdArea(t.getThirdArea()); // 所在区域三
						}
					}
					if (tCompany.getFouthArea() == null|| "".equals(tCompany.getFouthArea())) {
						if(tCompany.getThirdArea()!=null&&t.getThirdArea()!=null&&tCompany.getThirdArea().longValue()==t.getThirdArea().longValue()){
							tCompany.setFouthArea(t.getFouthArea()); // 所在区域四
						}
					}
					if (tCompany.getFifthArea() == null || "".equals(tCompany.getFifthArea())) {
						if(tCompany.getFouthArea()!=null&&t.getFouthArea()!=null&&tCompany.getFouthArea().longValue()==t.getFouthArea().longValue()){
							tCompany.setFifthArea(t.getFifthArea()); // 所在区域五
						}
					}
					if (tCompany.getPrincipalTelephone()== null|| "".equals(tCompany.getPrincipalTelephone())) {
						tCompany.setPrincipalTelephone(t.getPhoneCode()); // 联系电话
					}
					if (tCompany.getSafetyMngPerson()== null || "".equals(tCompany.getSafetyMngPerson())) {
						tCompany.setSecurityPrincipalPerson(t.getSafetyMngPerson()); // 安管负责人
					}
					if (tCompany.getSafetyMngPersonPhone() == null || "".equals(tCompany.getSafetyMngPersonPhone())) {
						tCompany.setSecurityPrincipalTel(t.getSafetyMngPersonPhone()); // 安管负责人联系电话
					}
					if (tCompany.getProductionScale() == null|| "".equals(tCompany.getProductionScale())) {
						tCompany.setProductionScale(t.getProductionScale()); // 企业规模
					}
					if (tCompany.getEnterprise() == null|| "".equals(tCompany.getEnterprise())) {
						if(t.getDaCompanyPass()!=null){
							tCompany.setEnterprise(t.getDaCompanyPass().isEnterprise() == true ? 1 : 0);
						}
					}
					
					
					/**
					// 先找出所有的行业分类
					Set<DaIndustryParameter> hzTradeTypes =  t.getHzTradeTypes();
				
					// 找出父节点为空的，即为行业部门。
					for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);

						if (d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter);
							if (d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}
                   **/
					
					List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(t.getId());
					// 找出父节点为空的，即为行业部门。
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {

						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
                        
						if (d!=null&&d.getDaIndustryParameter() == null) {

							daIndustryParameter0 = d;
						}
					}

					// 找出行业部门下面的一级行业。
					if (daIndustryParameter0 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
								daIndustryParameter1 = d;
							}
						}
					}

					// 找出行业部门下面的二级行业。
					if (daIndustryParameter1 != null) {
						for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
							DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
							if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter1.getId())) {
								daIndustryParameter2 = d;
							}
						}
					}

					System.out.println("daIndustryParameter2: " + daIndustryParameter2);

					if(tCompany.getDeptCode()==null||"".equals(tCompany.getDeptCode())){
						if (daIndustryParameter0 != null) {
							tCompany.setDeptCode(daIndustryParameter0.getCode());// 部门code
						}
					}
					
					if(tCompany.getManagementLevel1()==null||"".equals(tCompany.getManagementLevel1())){
						if (daIndustryParameter1 != null &&daIndustryParameter1.getCode()!=null&& daIndustryParameter1.getCode().indexOf("undefined") < 0) {
							//此处添加判断企业表的一级管理行业是否和当前要设置的二级管理分类的父亲一样
							if(tCompany.getDeptCode()!=null&&tCompany.getDeptCode().equals(daIndustryParameter0.getCode())){
								tCompany.setManagementLevel1(daIndustryParameter1.getCode());// 管理分类1
							}
						} else {
							tCompany.setManagementLevel1("");
						}

					}
					if(tCompany.getManagementLevel2()==null||"".equals(tCompany.getManagementLevel2())){
						if (daIndustryParameter2 != null &&daIndustryParameter2.getCode()!=null&& daIndustryParameter2.getCode().indexOf("undefined") < 0) {
							
							//此处添加判断企业表的一级管理行业是否和当前要设置的二级管理分类的父亲一样
							if(tCompany.getManagementLevel1()!=null&&tCompany.getManagementLevel1().equals(daIndustryParameter1.getCode())){
								tCompany.setManagementLevel2(daIndustryParameter2.getCode());// 管理分类2
							}
							
						} else {
							tCompany.setManagementLevel2("");
						}
					}
					

					
					tcompanyPersistenceIface.updateCompany(tCompany);

					// 将DaCompany的同步状态变为0
					tcompanyPersistenceIface.executeHQLUpdate("update DaCompany com set com.isSYN=0  where com.id=" + t.getId());
				} 
			} 
				
			}
		return false;
	}


	/**
	 * da_company中存在 t_company不存在 插入 (初始化)
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean loadInsertSynDaCompany(Long startId,Long endId) throws Exception {
//    System.out.println("da_company中删除   t_company不存在");
		
		DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(DaCompany.class);
		detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" is_wx_flag='1' and uuid is null "));
		List<DaCompany> dacompany = companyPersistenceIface.loadCompanies2(detachedCriteriaProxy);
		if (dacompany.size() > 0) {
			for (DaCompany company : dacompany) {
				System.out.println("新增t_company");
				TCompany tcompany  = new TCompany();
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
				if(company.getDaCompanyPass()!=null){
					tcompany.setEnterprise(company.getDaCompanyPass().isEnterprise() == true ? 1 : 0);// 规模以上企业
				}
				tcompany.setIsHidden(0);
				tcompany.setIsSynchro(1);
				tcompany.setUuid(company.getUuid());
				tcompany.setState("UNYH");


				tcompany.setIsHighRiskWork(company.getIsHighRiskWork());
				tcompany.setHighRiskWork(company.getHighRiskWork());
				tcompanyPersistenceIface.createCompany(tcompany);

				// 将DaCompany的同步状态变为0
				pubCompanyPersistenceIface.executeSQLUpdate("update t_company com set com.IS_WX_FLAG=1  where com.id=" +tcompany.getId());
			}
		}
		return false;
	}

	
	

	
	
	////////////////////////////////////////////////////////////////
	/**
	 * t_compan y中的管理行业转换为本地的行业id
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public long trans(String thy) throws ApplicationAccessException {
		long hy = 0;

		DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
		detachedCriteriaProxy1.add(RestrictionsProxy.eq("code", thy));
		List<DaIndustryParameter> res = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy1, null);
		if (res.size() > 0) {
			hy = res.get(0).getId();
		}

		return hy;

	}
	
	
	
	
	/**
	 * 是否同步中心库优先级高的字段
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean getCheckFlag(TCompany t,String checkFlag) throws ApplicationAccessException {
		//是否要同步中心库优先级高的数据，默认是
		boolean flag=true;
		//为了保证数据的准确性，当gs_id和zj_id都不为空的情况向才要更新
		
		//先检查配置文件中的开关定义，当开关的值为0表示不需要这个过滤条件,flag要设为true
		if("0".equals(checkFlag)){
			flag=true;
		}else{
			//当开关定义为1时，表示需要这个过滤条件。此时就需要判断工商质检的值是否符合要求
			if((t.getGsId()!=null&&!"".equals(t.getGsId()))||(t.getZjId()!=null&&!"".equals(t.getZjId()))){
				//当工商质检的值都存在的情况下，可以同步优先级高的，flag设为true
				flag=true;
			}else{
				//当工商质检的值有一个为空的情况下，就不可以同步中心库优先级高的，flag设为false
				flag=false;
			}
		}
		return flag;
	
	}
	
	
	
	/**
	 * 本地的行业id转换为t_compan y中的管理行业
	 * 
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public String ftrans(long hy) throws ApplicationAccessException {
		String hycode = "";
		DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
		detachedCriteriaProxy1.add(RestrictionsProxy.eq("id", hy));
		List<DaIndustryParameter> res = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy1, null);
		if (res.size() > 0) {
			hycode = res.get(0).getCode();
		}

		return hycode;

	}
	/**
	 * 行业处理前先进行判断，隐患的优先级高
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void updateHyBeforeCheck(TCompany t, DaCompany daCompany) throws ApplicationAccessException {
		// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
		//首先判断daCompany中的一级行业（对应中心库的部门字段）是否存在，如果不存在的话，则中心库覆盖本地。
		//如果存在的话，再判断一级行业是否和中心库的部门字段对应相等，不相等的话，已隐患为优先级。不覆盖。
		//相等的话再判断daCompany中的二级行业（对应中心的一级管理分类）是否存在，不如不存在的话，中心库覆盖本地，存在，则已隐患为优先级。不覆盖。
		DaIndustryParameter daIndustryParameter0 = null;
		DaIndustryParameter daIndustryParameter1 = null;
		List<VDaCompanyIndustryRel> hzTradeTypes = vdaComIndRelPersistenceIface.getHy(daCompany.getId());
		// 找出父节点为空的，即为行业部门。
		for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
			DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
			if (d!=null&&d.getDaIndustryParameter() == null) {
				daIndustryParameter0 = d;
			}
		}
		if(daIndustryParameter0==null||"".equals(daIndustryParameter0)){
			//隐患企业部门为空，中心库不覆盖。没有部门，表示企业删除，不用操作
			//updateHy(t, daCompany);
		}else{
			//判断一级行业和部门是否相等
			if(t.getDeptCode()!=null&&t.getDeptCode().equals(daIndustryParameter0.getCode())){
				//相等
				// 找出行业部门下面的一级行业。
				if (daIndustryParameter0 != null) {
					for (VDaCompanyIndustryRel daIndustryParameter : hzTradeTypes) {
						DaIndustryParameter d = tradeTypePersistenceIface.loadTradeType(daIndustryParameter.getParDaIndId());
						if (d!=null&&d.getDaIndustryParameter() != null && d.getDaIndustryParameter().getId().equals(daIndustryParameter0.getId())) {
							daIndustryParameter1 = d;
						}
					}
				}
				//部门相等，一级行业为空，则中心库覆盖本地。
				if(daIndustryParameter1==null||"".equals(daIndustryParameter1)){
					updateHy(t, daCompany,daIndustryParameter0.getId());
				}
			}
			
		}
		return;
	}
	/**
	 * 隐患企业表中，一级行业部门不存在时行业处理
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void updateHy(TCompany t, DaCompany daCompany) throws ApplicationAccessException {
		Long hy1 = 0l;
		Long hy2 = 0l;

		// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
		if (t.getManagementLevel1() != null) {

			if (t.getManagementLevel1().equals("P82")) { // 教育
				// 存在,则不操作,不存在,则全删插入一个

				
				List<VDaCompanyIndustryRel> vdu = vdaComIndRelPersistenceIface.getHy(daCompany.getId(), 1406);//modify by huangjl
				if (vdu.size() <= 0) { // 不存在
					pubCompanyPersistenceIface.executeSQLUpdate("delete    DA_COMPANY_INDUSTRY_REL   where  par_da_com_id=" + daCompany.getId());
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + ",1406)");
				}

			} else {

				hy1 = trans(t.getManagementLevel1());

				if (t.getManagementLevel2() != null) {
					// 过滤以下几个,隐患中细分,中心库未细分,但代码一致 ,不进行对应
					if (t.getManagementLevel2().equals("C37") || t.getManagementLevel2().equals("C22") || t.getManagementLevel2().equals("C17") || t.getManagementLevel2().equals("D44")) {
						hy2 = 0l;
					} else {
						hy2 = trans(t.getManagementLevel2());
					}
				}

				if (hy2 != 0) { // 二级行业不为空 全删除 后插入
					pubCompanyPersistenceIface.executeSQLUpdate("delete    DA_COMPANY_INDUSTRY_REL   where  par_da_com_id=" + daCompany.getId() + "  ");
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + ",1389)");
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + "," + hy1 + ")");
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + "," + hy2 + ")");
				} else if (hy1 != 0) { // 二级行业为空
					// hy1是否存在，存在，不操作, 不存在则 全删除 后插入 hy1

					List<VDaCompanyIndustryRel> vdu = vdaComIndRelPersistenceIface.getHy(daCompany.getId(), hy1);
					if (vdu.size() <= 0) { // 不存在
						pubCompanyPersistenceIface.executeSQLUpdate("delete    DA_COMPANY_INDUSTRY_REL   where  par_da_com_id=" + daCompany.getId() + " ");
						pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + ",1389)");
						pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + "," + hy1 + ")");
					}
				}
			}
		}

		return;

	}
	
	
	/**
	 * 部门信息能和中心库对应的情况行业处理
	 * @param thy
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void updateHy(TCompany t, DaCompany daCompany,Long firstHyId) throws ApplicationAccessException {
		// -------行业分类保存到表DA_COMPANY_INDUSTRY_REL
		if (t.getManagementLevel1() != null) {
			DaIndustryParameter daIndustryParameter1=null;
			//先查询出一级行业
			DetachedCriteriaProxy detachedCriteriaProxy1 = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
			detachedCriteriaProxy1.add(RestrictionsProxy.eq("code", t.getManagementLevel1()));
			detachedCriteriaProxy1.add(RestrictionsProxy.eq("deleted", false));
			List<DaIndustryParameter> res = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy1, null);
			if (res.size() > 0) {
				daIndustryParameter1 = res.get(0);
			}
			//判断行业二级行业父id是否和firstHyId相等
			if(daIndustryParameter1!=null&&daIndustryParameter1.getDaIndustryParameter()!=null&&daIndustryParameter1.getDaIndustryParameter().getId().longValue()==firstHyId.longValue()){
					//行业相同，可以修改
					pubCompanyPersistenceIface.executeSQLUpdate("delete    DA_COMPANY_INDUSTRY_REL   where  par_da_com_id=" + daCompany.getId() + "  ");
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + ","+firstHyId.longValue()+")");
					pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + "," + daIndustryParameter1.getId() + ")");
				
				    //在查找三级行业是否存在
					if (t.getManagementLevel2() != null) {
						
						DaIndustryParameter daIndustryParameter2=null;
						//先查询出一级行业
						DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(DaIndustryParameter.class);
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("code", t.getManagementLevel2()));
						detachedCriteriaProxy2.add(RestrictionsProxy.eq("deleted", false));
						List<DaIndustryParameter> res2 = tradeTypePersistenceIface.loadTradeTypes(detachedCriteriaProxy2, null);
						if (res.size() > 0) {
							daIndustryParameter2 = res2.get(0);
						}
						
						//在查找三级行业父id是否和二级行业父id相等
						if(daIndustryParameter2!=null&&daIndustryParameter2.getDaIndustryParameter()!=null&&daIndustryParameter2.getDaIndustryParameter().getId().longValue()==daIndustryParameter1.getId().longValue()){
							pubCompanyPersistenceIface.executeSQLUpdate("insert  into    DA_COMPANY_INDUSTRY_REL  (par_da_com_id,PAR_DA_IND_ID)  values(" + daCompany.getId() + "," + daIndustryParameter2.getId() + ")");
						}
						
					}
			}
		}
		return;

	}
	

	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	public void setCompanyPersistenceIface(CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public TCompanyPersistenceIface getTcompanyPersistenceIface() {
		return tcompanyPersistenceIface;
	}

	public void setTcompanyPersistenceIface(TCompanyPersistenceIface tcompanyPersistenceIface) {
		this.tcompanyPersistenceIface = tcompanyPersistenceIface;
	}

	public TradeTypePersistenceIface getTradeTypePersistenceIface() {
		return tradeTypePersistenceIface;
	}

	public void setTradeTypePersistenceIface(TradeTypePersistenceIface tradeTypePersistenceIface) {
		this.tradeTypePersistenceIface = tradeTypePersistenceIface;
	}

	public PubCompanyPersistenceIface getPubCompanyPersistenceIface() {
		return pubCompanyPersistenceIface;
	}

	public void setPubCompanyPersistenceIface(PubCompanyPersistenceIface pubCompanyPersistenceIface) {
		this.pubCompanyPersistenceIface = pubCompanyPersistenceIface;
	}

	public VDaComIndRelPersistenceIface getVdaComIndRelPersistenceIface() {
		return vdaComIndRelPersistenceIface;
	}

	public void setVdaComIndRelPersistenceIface(VDaComIndRelPersistenceIface vdaComIndRelPersistenceIface) {
		this.vdaComIndRelPersistenceIface = vdaComIndRelPersistenceIface;
	}

}
