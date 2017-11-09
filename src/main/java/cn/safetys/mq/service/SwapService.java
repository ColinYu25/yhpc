package cn.safetys.mq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.safetys.center.db.cxf.WsLoader;
import cn.safetys.center.db.cxf.aqsc.IAQSC;
import cn.safetys.center.db.cxf.company.ICompany;
import cn.safetys.center.db.cxf.yh.IYH;
import cn.safetys.center.db.cxf.yh.gpdb.IYHGPDB;
import cn.safetys.center.db.cxf.yh.nb.IYHNB;
import cn.safetys.center.db.cxf.yh.wzlzd.IYHWZLZD;
import cn.safetys.center.db.cxf.yh.xx.IYHXX;
import cn.safetys.util.CommonUtil;

import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.orm.hibernate3.criterion.RestrictionsProxy;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.model.VDanger;
import com.safetys.nbyhpc.domain.model.VGpDanger;
import com.safetys.nbyhpc.domain.model.VNormalDanger;
import com.safetys.nbyhpc.domain.model.VReport;
import com.safetys.nbyhpc.domain.model.VWh;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLGpDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLNormalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLReportPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLWhPersistenceIface;

/**
 * @author liulj
 * @E-mail uuhui@163.com
 * @date 创建时间: 2014年5月17日
 * @Description: 数据交换辅助类
 * @date 修改时间：
 * @modifyContent 修改内容:
 */
public class SwapService {
	/**
	 * 定义日志信息变量，用于记录日志信息
	 */
	private static Logger log = LoggerFactory.getLogger(SwapService.class);
	/**
	 * 企业基本信息字段、属性map集合
	 */
	private static Map<String, String> STATIC_COMPANYMAP = null;
	private static Map<String, String> STATIC_AQSCMAP = null;
	private static Map<String, String> STATIC_NormalDANGER = null;
	private static Map<String, String> STATIC_DANGER = null;
	private static Map<String, String> STATIC_GPDANGER = null;
	private static Map<String, String> STATIC_REPORT = null;
	private static Map<String, String> STATIC_WH = null;
	static {
		// 初始化企业基本信息字段、属性map集合
		initCompanyMap();
		initAqscMap();
		initNormalDangerMap();
		initDangerMap();
		initGpDangerMap();
		initReportMap();
		initWhMap();
	}

	/**
	 * 初始化企业基本信息字段、属性map集合
	 */
	public static void initCompanyMap() {
		STATIC_COMPANYMAP = new HashMap<String, String>();
		STATIC_COMPANYMAP.put("QYID", "id"); // 中心库,隐患
		STATIC_COMPANYMAP.put("UUID", "uuid");
		STATIC_COMPANYMAP.put("COMPANY_NAME", "companyName");
		STATIC_COMPANYMAP.put("LEGAL_PERSON", "legalPerson"); // 法定代表人
		STATIC_COMPANYMAP.put("REG_ADDRESS", "regAddress");// 注册地址
		STATIC_COMPANYMAP.put("BUSINESS_ADDRESS", "businessAddress"); // 经营场所详细地址
		STATIC_COMPANYMAP.put("FIRST_AREA", "firstArea");
		STATIC_COMPANYMAP.put("SECOND_AREA", "secondArea");
		STATIC_COMPANYMAP.put("THIRD_AREA", "thirdArea");
		STATIC_COMPANYMAP.put("FOUTH_AREA", "fouthArea");
		STATIC_COMPANYMAP.put("FIFTH_AREA", "fifthArea");
		STATIC_COMPANYMAP.put("ORG_CODE", "orgCode");// 组织机构代码
		STATIC_COMPANYMAP.put("BUSINESS_REG_NUM", "businessRegNum"); // 工商注册号
		STATIC_COMPANYMAP.put("ESTABLISHMENT_DAY", "establishmentDay"); // 成立时间
		STATIC_COMPANYMAP.put("PHONE", "phone"); // 单位电话
		STATIC_COMPANYMAP.put("ECONOMIC_TYPE1", "economicType1"); // 经济类型
		STATIC_COMPANYMAP.put("ECONOMIC_TYPE2", "economicType2");
		STATIC_COMPANYMAP.put("NATIONAL_ECONOMIC_TYPE1","nationalEconomicType1"); // 国民经济分类
		STATIC_COMPANYMAP.put("NATIONAL_ECONOMIC_TYPE2","nationalEconomicType2");
		STATIC_COMPANYMAP.put("PRODUCTION_SCALE", "productionScale"); // 企业规模
		STATIC_COMPANYMAP.put("IS_ENTERPRISE", "enterprise"); // 规模以上工业企业 1
		STATIC_COMPANYMAP.put("MANAGEMENT_LEVEL1", "managementLevel1"); // 管理分类
		STATIC_COMPANYMAP.put("MANAGEMENT_LEVEL2", "managementLevel2");
		STATIC_COMPANYMAP.put("BUSINESS_SCOPE", "businessScope"); // 经营范围
		STATIC_COMPANYMAP.put("IS_HIDDEN", "isHidden"); // 隐患企业标志
		STATIC_COMPANYMAP.put("DEPT_CODE", "deptCode"); // 部门
		STATIC_COMPANYMAP.put("IS_HIGH_RISK_WORK", "isHighRiskWork"); // 是否高风险作业
		STATIC_COMPANYMAP.put("HIGH_RISK_WORK", "highRiskWork"); // 高风险作业内容
		STATIC_COMPANYMAP.put("TYPE", "type"); // 机构类型
		STATIC_COMPANYMAP.put("STATE", "state"); // 机构类型
		STATIC_COMPANYMAP.put("IS_DELETED", "deleted"); // 是否删除
		STATIC_COMPANYMAP.put("HIDDEN_COMPANY_DATE", "hiddenCompanyDate"); // 隐患创建时间
	}


	/**
	 * 初始化安全生产状况信息字段、属性map集合
	 */
	public static void initAqscMap() {
		STATIC_AQSCMAP = new HashMap<String, String>();
		STATIC_AQSCMAP.put("ID", "id");
		STATIC_AQSCMAP.put("COMPANY_UUID", "uuid");
		STATIC_AQSCMAP.put("COMPANY_NAME", "companyName");
		STATIC_AQSCMAP.put("AQSC_ID", "aqscId"); //
		STATIC_AQSCMAP.put("SECURITY_PRINCIPAL_PERSON","securityPrincipalPerson"); // 安管负责人
		STATIC_AQSCMAP.put("PRINCIPAL_TELEPHONE", "securityPrincipalTel"); // 安管负责人
		STATIC_AQSCMAP.put("MODIFY_TIME", "modifyTime");
	}

	/**
	 * 初始化一般隐患信息字段、属性map集合
	 */
	// {"ID":"61","HIDDEN_DES":"eeee","HIDDEN_TYPE1":"","HIDDEN_TYPE2":"","IN_TYPE":"","RESOLVE_STATE":"","YH_ID":"111111","RECORD_STATE":"2","IS_DELETED":"0","CREATE_TIME":"2014-04-28 17:00:59","MODIFY_TIME":"2014-04-28 17:01:35","COMPANY_ID":"205126",
	// "HIDDEN_FIND_TIME":"","HIDDEN_COMPLETE_DATE":"","LSZJ":"","FIRST_AREA":"","SECOND_AREA":"","THIRD_AREA":"","COMPANY_NAME":"\u5B81\u6CE2\u5317\u4ED1\u5357\u535A\u6A21\u5177\u6709\u9650\u516C\u53F8","COMPANY_UUID":"1E4ABC9F254745B8B081A35AABEB9FEA"}
	public static void initNormalDangerMap() {
		STATIC_NormalDANGER = new HashMap<String, String>();
		STATIC_NormalDANGER.put("YH_ID", "yhid"); // 中心库,业务系统
		STATIC_NormalDANGER.put("COMPANY_UUID", "uuid");
		STATIC_NormalDANGER.put("COMPANY_UUID1", "uuid1");
		STATIC_NormalDANGER.put("HIDDEN_DES", "description");
		STATIC_NormalDANGER.put("IS_DELETED", "deleted");
		STATIC_NormalDANGER.put("HIDDEN_TYPE1", "type");
		STATIC_NormalDANGER.put("HIDDEN_TYPE2", "second_type");
		STATIC_NormalDANGER.put("HIDDEN_FIND_TIME", "createTime");
		STATIC_NormalDANGER.put("HIDDEN_COMPLETE_DATE", "completedDate");
		STATIC_NormalDANGER.put("LSZJ", "Governmoney");
		STATIC_NormalDANGER.put("FIRST_AREA", "firstArea");
		STATIC_NormalDANGER.put("SECOND_AREA", "secondArea");
		STATIC_NormalDANGER.put("THIRD_AREA", "thirdArea");
		STATIC_NormalDANGER.put("RESOLVE_STATE", "repaired");
		STATIC_NormalDANGER.put("IN_TYPE", "inType"); // 代报or自报
		STATIC_NormalDANGER.put("HAZARD_SOURCE_CODE", "hazardSourceCode");// 隐患来源编码
	}

	/**
	 * 初始化重大隐患信息字段、属性map集合
	 */

	// {"ID":"61","HIDDEN_DES":"eeee","HIDDEN_TYPE1":"","HIDDEN_TYPE2":"","IN_TYPE":"","RESOLVE_STATE":"","YH_ID":"111111","RECORD_STATE":"2","IS_DELETED":"0","CREATE_TIME":"2014-04-28 17:00:59","MODIFY_TIME":"2014-04-28 17:01:35","COMPANY_ID":"205126",
	// "HIDDEN_FIND_TIME":"","HIDDEN_COMPLETE_DATE":"","LSZJ":"","FIRST_AREA":"","SECOND_AREA":"","THIRD_AREA":"","COMPANY_NAME":"\u5B81\u6CE2\u5317\u4ED1\u5357\u535A\u6A21\u5177\u6709\u9650\u516C\u53F8","COMPANY_UUID":"1E4ABC9F254745B8B081A35AABEB9FEA"}
	public static void initDangerMap() {
		STATIC_DANGER = new HashMap<String, String>();
		STATIC_DANGER.put("YH_ID", "yhid"); // 中心库,业务系统
		STATIC_DANGER.put("COMPANY_UUID", "uuid");
		STATIC_DANGER.put("COMPANY_UUID1", "uuid1");
		STATIC_DANGER.put("HIDDEN_DES", "description");
		STATIC_DANGER.put("HIDDEN_NO", "dangerNo");
		STATIC_DANGER.put("IS_DELETED", "deleted");
		STATIC_DANGER.put("FIRST_AREA", "firstArea");
		STATIC_DANGER.put("SECOND_AREA", "secondArea");
		STATIC_DANGER.put("THIRD_AREA", "thirdArea");
		STATIC_DANGER.put("HAPPEN_TIME", "happenDate");
		STATIC_DANGER.put("PLAN_FINISH_TIME", "planDate");
		STATIC_DANGER.put("FINISH_TIME", "finishDate");
		STATIC_DANGER.put("CREATE_TIME", "createTime");
		STATIC_DANGER.put("MODIFY_TIME", "modifyTime");
		STATIC_DANGER.put("IS_NORMAL_CHANGE", "IsNormalChange");
		STATIC_DANGER.put("HIDDEN_POSITION", "dangerAdd");
		STATIC_DANGER.put("RESOLVE_STATE", "resolveState");
		STATIC_DANGER.put("GORVER_CONTENT", "gorverContent");
		STATIC_DANGER.put("HIDDEN_TYPE1", "hiddenType1");
		STATIC_DANGER.put("HIDDEN_TYPE2", "hiddenType2");
	}

	/**
	 * 初始化挂牌隐患信息字段、属性map集合
	 * {"IS_DELETED":"1","MODIFY_TIME":"2013-12-23 15:29:20",
	 * "RECORD_STATE":"3","ID"
	 * :"377582","HIDDEN_DES":"33333","HIDDEN_POSITION":"222",
	 * "PUT_LEVEL":"","DEADLINE_PROCESS"
	 * :"2013-12-03 00:00:00","SUPERVISE_ORG":"1"
	 * ,"UNDERTAKE_DEPARTMENT":"222","COMPLETE_TIME":"",
	 * "COMPANY_ID":"467597","YH_ID"
	 * :"1111111111","CREATE_TIME":"2013-12-03 10:00:06", "COMPANY_NAME":"\u5B81\u6CE2\u4E1C\u94B1\u6E56\u65C5\u6E38\u5EA6\u5047\u533A\u548C\u4E5F\u5E8A\u4E0A\u7528\u54C1\u5546\u5E97"
	 * , "COMPANY_UUID":"3860F4A92ACA4EBB8F3C84D43AE31B67"}
	 * 
	 */
	public static void initGpDangerMap() {
		STATIC_GPDANGER = new HashMap<String, String>();
		STATIC_GPDANGER.put("YH_ID", "yhid"); // 中心库,业务系统
		STATIC_GPDANGER.put("COMPANY_UUID", "uuid");
		STATIC_GPDANGER.put("COMPANY_UUID1", "uuid1");
		STATIC_GPDANGER.put("IS_DELETED", "deleted");
		STATIC_GPDANGER.put("CREATE_TIME", "createTime");
		STATIC_GPDANGER.put("MODIFY_TIME", "modifyTime");
		STATIC_GPDANGER.put("PUT_LEVEL", "levels");
		STATIC_GPDANGER.put("DEADLINE_PROCESS", "completeTime");
		STATIC_GPDANGER.put("SUPERVISE_ORG", "govment");
		STATIC_GPDANGER.put("UNDERTAKE_DEPARTMENT", "department");
		STATIC_GPDANGER.put("GP_TIME", "createTime");
	}

	/**
	 * 初始化季报信息字段、属性map集合
	 * {"IS_DELETED":"0","MODIFY_TIME":"2014-05-05 09:22:42","RECORD_STATE"
	 * :"2","ID":"523425","YEAR":"2013","QUARTER":"2",
	 * "REPORT_DATE":"2014-04-11 00:00:00"
	 * ,"COMPANY_ID":"162583","YH_ID":"1405169"
	 * ,"CREATE_TIME":"2013-12-30 17:30:25", "COMPANY_NAME":
	 * "\u5B81\u6CE2\u6C47\u660C\u7269\u6D41\u6709\u9650\u516C\u53F8"
	 * "COMPANY_UUID":"F77D071287184DA5855BB680EBD2ACCA"}
	 */
	public static void initReportMap() {
		STATIC_REPORT = new HashMap<String, String>();
		STATIC_REPORT.put("YH_ID", "yhid"); // 中心库,业务系统
		STATIC_REPORT.put("COMPANY_UUID", "uuid");
		STATIC_REPORT.put("COMPANY_UUID1", "uuid1");
		STATIC_REPORT.put("YEAR", "year");
		STATIC_REPORT.put("QUARTER", "quarter");
		STATIC_REPORT.put("REPORT_DATE", "reportDate");
	}

	/**
	 * 初始化危化信息字段、属性map集合
	 * 
	 */
	public static void initWhMap() {
		STATIC_WH = new HashMap<String, String>();
		STATIC_WH.put("ENTITY_ID", "whid"); // 中心库,业务系统
		STATIC_WH.put("COMPANY_UUID", "uuid");
		STATIC_WH.put("COMPANY_UUID1", "uuid1");
		STATIC_WH.put("YEAR", "year");
		STATIC_WH.put("ANNUAL_INPUT", "annualInput");
		STATIC_WH.put("IS_DELETED", "deleted");
		STATIC_WH.put("CREATE_TIME", "createTime");
		STATIC_WH.put("MODIFY_TIME", "modifyTime");
	}

	/**
	 * 将企业基本信息交换到中心平台
	 * 
	 * @param serviceBean
	 *            企业基本信息业务逻辑接口
	 * @param token
	 *            令牌
	 */
	public static void swapCompanyService(
			ETLCompanyInfoPersistenceIface serviceBean,CompanyPersistenceIface companyPersistenceIface, String token)
			throws Exception {

		List<TCompany> list = serviceBean.getSwapList();
		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Iterator<Entry<String, String>> iterator1 = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.aqsc.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.aqsc.MapEntry mapEntry = null;
			// 定义MapEntry变量
			cn.safetys.center.db.cxf.company.MapConvertor mapConvertor1 = null;
			cn.safetys.center.db.cxf.company.MapEntry mapEntry1 = null;
			// 定义MapEntry变量
			List<cn.safetys.center.db.cxf.aqsc.MapEntry> mapEntryList = null;
			// 定义编号集合变量
			List<cn.safetys.center.db.cxf.company.MapEntry> mapEntryList1 = null;
			// 定义编号集合变量
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("企业基本信息中有" + list.size() + "数据需要同步");
			log.info("企业基本信息中有" + list.size() + "数据需要同步");
			for (TCompany entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.aqsc.MapConvertor();
				mapConvertor1 = new cn.safetys.center.db.cxf.company.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				mapEntryList1 = mapConvertor1.getEntries();
				iterator = STATIC_AQSCMAP.entrySet().iterator();
				iterator1 = STATIC_COMPANYMAP.entrySet().iterator();
				//切记此处绝对不能写在最外面
				String returnStr = null;
				String returnStr1 = null;
				Long id = entity.getId();
				String uuid = entity.getUuid();
				//String aqscId1 = entity.getAqscId();

				// 遍历实体字段、属性集合
				while (iterator1.hasNext()) {
					mapEntry1 = new cn.safetys.center.db.cxf.company.MapEntry();
					entry = (Map.Entry<String, String>) iterator1.next();
					mapEntry1.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					
					if (entry.getKey().equalsIgnoreCase("QYID")) {
						id = Long.parseLong(propertyValue + "");
					} else if (entry.getKey().equals("UUID")) {
						uuid = propertyValue + "";
					} else if (entry.getKey().equals("TYPE")) {
						mapEntry1.setValue("NP_9");
					}else if (entry.getKey().equals("IS_DELETED")) {
						//if(propertyValue!=null&&!"".equals(propertyValue)){
							
						//}else{
						    //隐患的全部为非删除的企业
							mapEntry1.setValue("0");
						//}
					} else {
						mapEntry1.setValue(propertyValue == null ? null
								: propertyValue + "");// 将属性转换成String
					}
					// log.info("key:" + entry.getKey() + " value:" +
					// propertyValue);
					mapEntryList1.add(mapEntry1);
				}
				for(int j=0 ;j<mapConvertor1.getEntries().size();j++){
					cn.safetys.center.db.cxf.company.MapEntry  m=mapConvertor1.getEntries().get(j);
					log.info(m.getKey()+":"+m.getValue());
				}
				// 将企业数据通过中心平台发布服务交换到中心平台
				returnStr = WsLoader.getInterface(ICompany.class).modifyCompanyInfo(token, uuid, mapConvertor1);

				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.aqsc.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());

					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					
					if (entry.getKey().equalsIgnoreCase("ID")) {
						id = Long.parseLong(propertyValue + "");
					} else if (entry.getKey().equals("COMPANY_UUID")) {
						if (propertyValue == null) { // 新增 uuid为空
							if (returnStr != null) {
								JSONObject jsonObject = JSONObject
										.fromObject(returnStr);
								JSONObject jo = jsonObject
										.getJSONObject("data");
								uuid = jo.getString("UUID");
							}
						} else {
							uuid = propertyValue + "";
						}
					}
					//else if (entry.getKey().equals("AQSC_ID")) {
					//	aqscId = propertyValue + "";
					//} 
					else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + "");// 将属性转换成String
					}
					mapEntryList.add(mapEntry);
				}

				try {

					// 将安全状况数据通过中心平台发布服务交换到中心平台
					returnStr1 = WsLoader.getInterface(IAQSC.class).modifyInfo(token, uuid, "", "", mapConvertor);

					if (returnStr != null && returnStr1 != null) {
						JSONObject jsonObject1 = JSONObject
								.fromObject(returnStr1);
						JSONObject jo1 = jsonObject1.getJSONObject("data");

						JSONObject jsonObject = JSONObject
								.fromObject(returnStr);
						JSONObject jo = jsonObject.getJSONObject("data");

						// 此处要判断企业的uuid是否存在，如果企业的uuid不存在的话，还要添加uuid
						if (entity.getUuid() != null
								&& !"".equals(entity.getUuid())) {
//							serviceBean
//									.executeHQLUpdate("update TCompany com set com.aqscId='"
//											+ jo1.getString("AQSC_ID")
//											+ "' where com.id =" + entity.getId());
						} else {
//							serviceBean
//									.executeHQLUpdate("update TCompany com set com.uuid='"
//											+ jo.getString("UUID")
//											+ "', com.aqscId='"
//											+ jo1.getString("AQSC_ID")
//											+ "' where com.id =" + entity.getId());
							serviceBean
							.executeHQLUpdate("update TCompany com set com.uuid='"+ jo.getString("UUID")+ "' where com.id =" + entity.getId());
							// 由于uuid这个字段比较特殊，uuid是连接TCompany和DaCompany最直接的关系，
							// 虽然定时器会定时执行IncSynService.loadSynDaCompany(etlDaCompanyPersistenceIface)方法将TCompany同步到DaCompany表中，
							// DaCompany表中uuid不存在的情况也会根据企业名称，工商注册号，组织机构代码进行匹配，修改DaCompany中的uuid和其他字段。
							// 但是如果同步的数据量大的时候，要较长时间才能同步到这这些uuid为空的企业信息。如果在这中间产生一些操作，如删除企业，
							// 就只是根据TCompany和DaCompany的uuid进行匹配，来设置TCompany的”是否隐患字段“为不是隐患信息。此种现象可能在其他操作也会存在，
							// 所以此处最好是当TCompany设置uuid的时候将DaCompany的uuid一同设置完毕。保证两边能及时通过uuid匹配上。
							//还是的按照匹配顺序进行匹配，不然会造成多条数据匹配的问题
							//新根据名称，再根据工商注册号，再更加组织机构代码
							
							try {
								String comonSql=" exists (select 1   from DA_COMPANY_PASS dcp1_  where dcp1_.par_da_com_id = this_.id    and dcp1_.IS_DELETED = 0  and dcp1_.IS_AFFIRM = 1)  and this_.id in (select dcir.par_da_com_id from da_company_industry_rel dcir) and this_.uuid is  null ";
	                            DaCompany daCompany=null;
								if( entity.getCompanyName()!=null&&!"".equals( entity.getCompanyName().trim())){
									DetachedCriteriaProxy detachedCriteriaProxy2 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
									detachedCriteriaProxy2.add(RestrictionsProxy.sqlRestriction(comonSql));
									detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.deleted",false));
									detachedCriteriaProxy2.add(RestrictionsProxy.eq("d.companyName", entity.getCompanyName().trim()));
									daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy2);
								}
								
								if(daCompany==null){
									//再根据工商注册号查询
									if( entity.getBusinessRegNum()!=null&&!"".equals( entity.getBusinessRegNum().trim())){
										DetachedCriteriaProxy detachedCriteriaProxy3 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
										detachedCriteriaProxy3.add(RestrictionsProxy.sqlRestriction(comonSql));
										detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.deleted",false));
										detachedCriteriaProxy3.add(RestrictionsProxy.eq("d.regNum", entity.getBusinessRegNum().trim()));
										daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy3);
									}
								}
						
								if(daCompany==null){
									//再根据组织机构代码查询
									if( entity.getOrgCode()!=null&&!"".equals( entity.getOrgCode().trim())){
										DetachedCriteriaProxy detachedCriteriaProxy4 = DetachedCriteriaProxy.forClass(DaCompany.class, "d");
										detachedCriteriaProxy4.add(RestrictionsProxy.sqlRestriction(comonSql));
										detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.deleted",false));
										detachedCriteriaProxy4.add(RestrictionsProxy.eq("d.setupNumber", entity.getOrgCode().trim()));
										daCompany = companyPersistenceIface.loadCompanyInfo(detachedCriteriaProxy4);
									}
								}
								
								
//								DetachedCriteriaProxy2 detachedCriteriaProxy = DetachedCriteriaProxy2.forClass(DaCompany.class);
//								detachedCriteriaProxy.add(RestrictionsProxy.sqlRestriction(" is_wx_flag='1' and uuid is null and company_name='"+entity.getCompanyName()+"' "));
//								
//								List<DaCompany> dacompany = companyPersistenceIface.loadCompanies2(detachedCriteriaProxy);
//								DaCompany daCompany=null;
//								if (dacompany.size() > 0) {
//									daCompany=dacompany.get(0);
//								}
								if (daCompany != null) { 
									//daCompany.setUuid(jo.getString("UUID"));
									//companyPersistenceIface.updateCompany(daCompany);
									 serviceBean.executeHQLUpdate("update DaCompany da set da.uuid='" +jo.getString("UUID") +"' where id="+daCompany.getId()+" ");
								}
							} catch (Exception e) {
								log.info("错误原因:" + e.toString());
								
								log.info("error="+e.toString());
								e.printStackTrace();
							}
							
							/***
							 * 暂时先注释掉 serviceBean .executeHQLUpdate(
							 * "update DaCompany da set da.uuid='" +
							 * jo.getString("UUID") +
							 * "' where da.deleted=0 and da.uuid is null and (da.companyName='"
							 * + entity.getCompanyName() + "' or da.regNum='" +
							 * entity.getBusinessRegNum() +
							 * "' or da.setupNumber='" + entity.getOrgCode() +
							 * "')");
							 ***/

						}

						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(id);// 将ID添加idList集合中
							i++;
							log.info("企业基本信息成功同步了" + i + "条数据");
							log.info("企业基本信息成功同步了" + i + "条数据");
							if (idList.size() == 50) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("企业基本信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	/**
	 * 将一般隐患信息交换到中心平台
	 * 
	 * @param serviceBean
	 *            一般隐患业务逻辑接口
	 * @param token
	 *            令牌
	 */
	public static void swapNormalDangerService(
			ETLNormalDangerPersistenceIface serviceBean, String token)
			throws Exception {

		List<VNormalDanger> list = serviceBean.getSwapList();
		log.info("list.size(): " + list.size());

		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.yh.xx.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.yh.xx.MapEntry mapEntry = null;
			List<cn.safetys.center.db.cxf.yh.xx.MapEntry> mapEntryList = null;
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("一般隐患信息中有" + list.size() + "数据需要同步");
			log.info("一般隐患信息中有" + list.size() + "数据需要同步");
			for (VNormalDanger entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.yh.xx.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				iterator = STATIC_NormalDANGER.entrySet().iterator();

				String returnStr = null;
				String yhid = null;
				String uuid = null;

				// 遍历实体字段、属性集合
				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.yh.xx.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					log.info("entity:" + entity + "===value:"
							+ entry.getValue() + "===propertyValue: "
							+ propertyValue);
					if (entry.getKey().equalsIgnoreCase("YH_ID")) {
						yhid = propertyValue + "";
					} else if (entry.getKey().equals("COMPANY_UUID")) {
						uuid = propertyValue + "";
						if (uuid == null || uuid.equals("")
								|| uuid.equals("null")) {
							uuid = CommonUtil.getPropertyValue(entity, "uuid1")
									+ "";
						}
					} else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + "");// 将属性转换成String
					}
					mapEntryList.add(mapEntry);
				}

				try {

					// 将企业数据通过中心平台发布服务交换到中心平台
					// String modifyInfo(String token, String uuid, String
					// yhxxId, @XmlJavaTypeAdapter(MapAdapter.class)Map<String,
					// String> info);
					log.info("uuid: " + uuid);
					log.info("yhid: " + yhid);
					if (uuid != null && !"".equals(uuid)
							&& !"null".equals(uuid)) {
						returnStr = WsLoader.getInterface(IYHXX.class)
								.modifyInfo(token, uuid, yhid, mapConvertor);
					}
					log.info("安全生产returnStr: " + returnStr);
					log.info("处理结果:" + returnStr);

					if (returnStr != null) {
						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(yhid);// 将ID添加idList集合中
							i++;
							log.info("一般隐患信息成功同步了" + i + "条数据");
							log.info("一般隐患信息成功同步了" + i + "条数据");
							if (idList.size() == 100) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("一般隐患信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				} catch (Error e) {
					log.info("一般隐患信息交换失败，原因：" + e.getMessage());
					e.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	/**
	 * 将重大隐患信息交换到中心平台
	 * 
	 * @param serviceBean
	 *            重大隐患业务逻辑接口
	 * @param token
	 *            令牌
	 */
	public static void swapDangerService(ETLDangerPersistenceIface serviceBean, String token) throws Exception {
		String returnStr = null;
		long yhid = 0;
		String uuid = null;
		List<VDanger> list = serviceBean.getSwapList();
		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.yh.wzlzd.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.yh.wzlzd.MapEntry mapEntry = null;
			List<cn.safetys.center.db.cxf.yh.wzlzd.MapEntry> mapEntryList = null;
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("重大隐患信息中有" + list.size() + "数据需要同步");
			for (VDanger entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.yh.wzlzd.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				iterator = STATIC_DANGER.entrySet().iterator();

				// 遍历实体字段、属性集合
				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.yh.wzlzd.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					if (entry.getKey().equalsIgnoreCase("YH_ID")) {
						yhid = Long.parseLong(propertyValue + "");
					} else if (entry.getKey().equals("COMPANY_UUID")) {
						uuid = propertyValue + "";
						if (uuid == null || uuid.equals("")
								|| uuid.equals("null")) {
							uuid = CommonUtil.getPropertyValue(entity, "uuid1")
									+ "";
						}
					} else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + "");// 将属性转换成String
					}
					mapEntryList.add(mapEntry);
				}

				try {

					// 将企业数据通过中心平台发布服务交换到中心平台
					if (uuid != null && !"".equals(uuid)
							&& !"null".equals(uuid)) {
						returnStr = WsLoader.getInterface(IYHWZLZD.class)
								.modifyInfo(token, uuid, yhid, mapConvertor);
					}
					log.info("处理结果:" + returnStr);

					if (returnStr != null) {
						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(yhid);// 将ID添加idList集合中
							i++;
							log.info("重大隐患信息成功同步了" + i + "条数据");
							if (idList.size() == 100) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("重大隐患信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				} catch (Error e) {
					log.info("重大隐患信息交换失败，原因：" + e.getMessage());
					e.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	/**
	 * 将挂牌隐患信息交换到中心平台
	 * 
	 * @param serviceBean
	 *            重大隐患业务逻辑接口
	 * @param token
	 *            令牌
	 */
	public static void swapGpDangerService(
			ETLGpDangerPersistenceIface serviceBean, String token)
			throws Exception {
		String returnStr = null;
		long yhid = 0;
		String uuid = null;
		List<VGpDanger> list = serviceBean.getSwapList();
		log.info("list.size(): " + list.size());

		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.yh.gpdb.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.yh.gpdb.MapEntry mapEntry = null;
			List<cn.safetys.center.db.cxf.yh.gpdb.MapEntry> mapEntryList = null;
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("挂牌信息中有" + list.size() + "数据需要同步");
			log.info("挂牌信息中有" + list.size() + "数据需要同步");
			for (VGpDanger entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.yh.gpdb.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				iterator = STATIC_GPDANGER.entrySet().iterator();

				// 遍历实体字段、属性集合
				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.yh.gpdb.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					log.info("entity:" + entity + "===value:"
							+ entry.getValue() + "===propertyValue: "
							+ propertyValue);
					if (entry.getKey().equalsIgnoreCase("YH_ID")) {
						yhid = Long.parseLong(propertyValue + "");
					} else if (entry.getKey().equals("COMPANY_UUID")) {
						uuid = propertyValue + "";
						if (uuid == null || uuid.equals("")
								|| uuid.equals("null")) {
							uuid = CommonUtil.getPropertyValue(entity, "uuid1")
									+ "";
						}
					} else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + "");// 将属性转换成String
					}
					mapEntryList.add(mapEntry);
				}

				try {

					// 将企业数据通过中心平台发布服务交换到中心平台
					if (uuid != null && !"".equals(uuid)
							&& !"null".equals(uuid)) {
						returnStr = WsLoader.getInterface(IYHGPDB.class)
								.modifyInfo(token, uuid, yhid, mapConvertor);
					}
					log.info("安全生产returnStr: " + returnStr);
					log.info("处理结果:" + returnStr);

					if (returnStr != null) {
						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(yhid);// 将ID添加idList集合中
							i++;
							log.info("挂牌信息成功同步了" + i + "条数据");
							log.info("挂牌信息成功同步了" + i + "条数据");
							if (idList.size() == 100) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("挂牌信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	/**
	 * 将季报信息交换到中心平台
	 * 
	 * @param serviceBean
	 * @param token
	 * @throws Exception
	 */
	public static void swapReportService(ETLReportPersistenceIface serviceBean,
			String token) throws Exception {
		String returnStr = null;
		long yhid = 0;
		String uuid = null;
		List<VReport> list = serviceBean.getSwapList();
		log.info("list.size(): " + list.size());

		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.yh.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.yh.MapEntry mapEntry = null;
			List<cn.safetys.center.db.cxf.yh.MapEntry> mapEntryList = null;
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("季报信息中有" + list.size() + "数据需要同步");
			log.info("季报信息中有" + list.size() + "数据需要同步");
			for (VReport entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.yh.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				iterator = STATIC_REPORT.entrySet().iterator();

				// 遍历实体字段、属性集合
				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.yh.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					log.info("key: " + entry.getKey());

					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					if (entry.getKey().equalsIgnoreCase("YH_ID")) {
						yhid = Long.parseLong(propertyValue + "");
					} 
//					else if (entry.getKey().equalsIgnoreCase("REPORT_DATE")) {
//						if (propertyValue != null
//								&& !propertyValue.toString().equals("")) {
//							
//							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//							propertyValue= format.format(entity.getReportDate());
//							mapEntry.setValue(propertyValue.toString());
//						}
//						
//					} 
					else if (entry.getKey().equals("COMPANY_UUID")) {
						uuid = propertyValue + "";
						if (uuid == null || uuid.equals("")
								|| uuid.equals("null")) {
							uuid = CommonUtil.getPropertyValue(entity, "uuid1")
									+ "";
						}
					} else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + ""); // 将属性转换成String
					}

					log.info("value: " + propertyValue == null ? null
							: propertyValue + "");

					// log.info("entity:" + entity + "===value:" +
					// entry.getValue() + "===propertyValue: " + propertyValue);

					mapEntryList.add(mapEntry);
					
				}

				
				try {

					// 将企业数据通过中心平台发布服务交换到中心平台
					log.info("yhid: " + yhid);
					if (uuid != null && !"".equals(uuid)
							&& !"null".equals(uuid)) {
						
						// 将企业数据通过中心平台发布服务交换到中心平台
						for(int j=0 ;j<mapConvertor.getEntries().size();j++){
							cn.safetys.center.db.cxf.yh.MapEntry  m=mapConvertor.getEntries().get(j);
							log.info(m.getKey()+":"+m.getValue());
						}
						log.info("uuid:"+uuid);
						log.info("yhid:"+yhid);
						
						returnStr = WsLoader.getInterface(IYH.class)
								.modifyInfo(token, uuid, yhid, mapConvertor);
					}
					log.info("安全生产returnStr: " + returnStr);
					log.info("处理结果:" + returnStr);

					if (returnStr != null) {
						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(yhid);// 将ID添加idList集合中
							i++;
							log.info("季报信息成功同步了" + i + "条数据");
							log.info("季报信息成功同步了" + i + "条数据");
							if (idList.size() == 100) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("季报信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	/**
	 * 将危化报告信息交换到中心平台
	 * 
	 * @param serviceBean
	 * @param token
	 * @throws Exception
	 */
	public static void swapWhService(ETLWhPersistenceIface serviceBean,
			String token) throws Exception {
		String returnStr = null;
		long yhid = 0;
		String uuid = null;
		List<VWh> list = serviceBean.getSwapList();
		log.info("list.size(): " + list.size());

		if (list.size() != 0) {// 存在需要交换到中心平台的数据
			Iterator<Entry<String, String>> iterator = null;
			Map.Entry<String, String> entry = null;
			Object propertyValue = null;// 定义属性值
			cn.safetys.center.db.cxf.yh.nb.MapConvertor mapConvertor = null;
			cn.safetys.center.db.cxf.yh.nb.MapEntry mapEntry = null;
			List<cn.safetys.center.db.cxf.yh.nb.MapEntry> mapEntryList = null;
			List<Object> idList = new ArrayList<Object>();
			int i = 0;
			log.info("危化报告信息中有" + list.size() + "数据需要同步");
			log.info("危化报告信息中有" + list.size() + "数据需要同步");
			for (VWh entity : list) {
				mapConvertor = new cn.safetys.center.db.cxf.yh.nb.MapConvertor();
				mapEntryList = mapConvertor.getEntries();
				iterator = STATIC_WH.entrySet().iterator();

				// 遍历实体字段、属性集合
				while (iterator.hasNext()) {
					mapEntry = new cn.safetys.center.db.cxf.yh.nb.MapEntry();
					entry = (Map.Entry<String, String>) iterator.next();
					mapEntry.setKey(entry.getKey());

					propertyValue = CommonUtil.getPropertyValue(entity, entry
							.getValue());
					if(propertyValue!=null){
						propertyValue=propertyValue.toString().replaceAll("\\s+","");
					}
					log.info("entity:" + entity + "===value:"
							+ entry.getValue() + "===propertyValue: "
							+ propertyValue);
					if (entry.getKey().equalsIgnoreCase("ENTITY_ID")) {
						yhid = Long.parseLong(propertyValue + "");
					} else if (entry.getKey().equals("COMPANY_UUID")) {
						uuid = propertyValue + "";
						if (uuid == null || uuid.equals("")
								|| uuid.equals("null")) {
							uuid = CommonUtil.getPropertyValue(entity, "uuid1")
									+ "";
						}
					} else {
						mapEntry.setValue(propertyValue == null ? null
								: propertyValue + ""); // 将属性转换成String
					}
					mapEntryList.add(mapEntry);
				}

				try {

					// 将企业数据通过中心平台发布服务交换到中心平台
					log.info("yhid: " + yhid);
					if (uuid != null && !"".equals(uuid)
							&& !"null".equals(uuid)) {
						returnStr = WsLoader.getInterface(IYHNB.class)
								.modifyInfo(token, uuid, yhid, mapConvertor);
					}
					log.info("安全生产returnStr: " + returnStr);
					log.info("处理结果:" + returnStr);

					if (returnStr != null) {
						if (CommonUtil.getJsonMsg(returnStr)) {
							idList.add(yhid);// 将ID添加idList集合中
							i++;
							log.info("危化报告信息成功同步了" + i + "条数据");
							log.info("危化报告信息成功同步了" + i + "条数据");
							if (idList.size() == 100) {
								// 根据编号更新本地同步状态
								serviceBean.updateSynStatus(idList);
								idList.clear();
							}
						}
					}
				} catch (Exception ex) {
					log.info("危化报告信息交换失败，原因：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			// 根据编号更新本地同步状态
			if (idList.size() != 0) {
				serviceBean.updateSynStatus(idList);
			}
		}

	}

	public static void main(String[] args) {
//		cn.safetys.center.db.cxf.company.MapConvertor convertor = new cn.safetys.center.db.cxf.company.MapConvertor();
//		List<cn.safetys.center.db.cxf.company.MapEntry> ers = convertor
//				.getEntries();
//		cn.safetys.center.db.cxf.company.MapEntry e2 = new cn.safetys.center.db.cxf.company.MapEntry();
//		e2.setKey("xxxxxx");
//		e2.setValue("1");
//		ers.add(e2);
//		log.info(WsLoader.getInterface(ICompany.class)
//				.modifyCompanyInfo("#NBYH#2013#",
//						"668EE8429F3E4EA799B0718BA78337A1", convertor));
//		
//		    String a=" a b c ";
//			a=a.replaceAll("\\s+","");
//			log.info(a);
//			
			
			
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s= format.format(new Date());
			log.info(s);

	}

}
