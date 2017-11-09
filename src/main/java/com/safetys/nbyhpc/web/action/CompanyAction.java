package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.safetys.constant.SystemConstant;
import cn.safetys.sync.mq.SyncTriggerService;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.FkRole;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.model.TCaches;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

/**
 * @(#) CompanyAction.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class CompanyAction extends DaAppAction {

	/**
	 * 企业基本信息
	 */
	private static final long serialVersionUID = 6683641507146857298L;

	private CompanyFacadeIface companyFacadeIface;// 企业基本信息的业务接口

	private List<DaCompany> companies;// 企业单位集合

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	private List<DaIndustryParameter> tradeTypesAllow;// 允许修改的行业

	private List<DaIndustryParameter> tradeTypesForCompanyLevel;
	
	private UserFacadeIface userFacadeIface;

	private List<DaPointType> pointTypes;

	private DaCompany company;// 企业单位

	private TCompany corecompany;// 中心库企业单位

	private FkUser fkUser;

	private FkUserInfo fkUserInfo;

	private Long[] roleIds;

	private String confirmpass;

	private Pagination pagination;// 分页对象

	private FkArea area;// 区域对象

	private String ids;// 企业序号的集合，针对类似于批量删除操作

	private String[] AuditingcompanyIds;

	private String readonly;

	private String companyIds;

	private Boolean state;

	private DaCompanyAcount companyAcount;

	private List<DaCompanyAcount> companyAcounts;

	private boolean flag;

	private List<FkEnum> economyKindEnums; // 经济类型枚举类型
	private List<FkEnum> childEconomyKindEnums; // 经济类型子类枚举类型

	private List<FkEnum> tradeTypeEnums; // 行业分类枚举类型
	private List<FkEnum> childTradeTypeEnums; // 行业分类子类枚举类型

	private List<FkEnum> nationalEconomicEnums; // 国民经济分类枚举类型
	private List<FkEnum> childNationalEconomicEnums; // 国民经济分类子类枚举类型

	private List<FkEnum> companyScaleEnums; // 企业规模枚举类型

	private List<FkEnum> highRiskWorkEnums; // 高风险作业枚举类型

	private String departmentName;
	private String oldUserName;

	// 添加三个参数用来保存中心库企业表行业类别代码id，为的是页面上根据中心库行业类别初始化企业信息
	private Long tradetypeId1;// 根据中心库DEPT_CODE查找的DA_INDUSTRY_PARAMETER的id
	private Long tradetypeId2;// 根据中心库MANAGEMENT_LEVEL1查找的DA_INDUSTRY_PARAMETER的id
	private Long tradetypeId3;// 根据中心库MANAGEMENT_LEVEL2查找的DA_INDUSTRY_PARAMETER的id

	// 判读是否是当前
	private Boolean isAnweiUser;

	private TCaches cache;

	private List<TCaches> caches;// 缓存集合
	// 操作提示
	private String msg = "ok";
	private boolean success = true;

	private String industry = "";

	
	private String regNum;
	private String setupNumber;
	private TCompany tcompany;// 中心库企业单位
	

	private SyncTriggerService syncTriggerService;
	private SystemConstant systemConstant;
	
	private String oldPassword;
	/**
	 * 判断企业用户是否删除
	 * 
	 * @return
	 */
	public String checkCompanyDelete_() {

		if (fkUser != null && fkUser.getUserName() != null) {

			try {

				boolean isPipecompany = false;
				FkUser user = companyFacadeIface.loadCompanyUserName(fkUser.getUserName());
				// 首先判断这个用户是否是管道隐患的用户，是的话，则可以登录。

				if (user != null)
					industry = user.getFkUserInfo().getUserIndustry();
				else
					industry = "";

				if (user.getFkRoles() != null && user.getFkRoles().size() > 0) {

					for (FkRole role : user.getFkRoles()) {// 验证必须删除本用户添加的企业
						if ("ROLE_PIPECOMPANY".equals(role.getRoleCode())) {
							isPipecompany = true;
							break;
						}
					}
					// 是管道企业，可以登录
					if (isPipecompany) {
						msg = "ok";
						return SUCCESS;
					} else {
						// 不是管道企业，要判断企业是否删除了
						DaCompany com = companyFacadeIface.loadCompanyByUserId(user.getId());
						if (com != null && com.getHzTradeTypes() != null && com.getHzTradeTypes().size() > 0) {
							msg = "ok";
						} else {
							msg = "isDelete";
						}

					}
				} else {
					msg = "ok";
					success = true;
					return SUCCESS;
				}

			} catch (ApplicationAccessException e) {
				success = false;
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * 判断企业用户是否删除
	 * 
	 * @return
	 * @throws IOException 
	 */
	public void checkCompanyDelete() throws IOException {
		getResponse().setContentType("text/plain;charset=UTF-8");
		JSONObject jo = new JSONObject();
		PrintWriter writer = getResponse().getWriter();
		jo.put("success", success);
		if (fkUser != null && fkUser.getUserName() != null) {

			try {
				Map<String, Object> userInfo = companyFacadeIface.loadCompanyUserInfoBySql(fkUser.getUserName().trim());
				if (userInfo != null) {
					industry = userInfo.get("industry") == null ? "" : userInfo.get("industry").toString();
					jo.put("industry", industry);
					String isPipecompany = userInfo.get("isPipecompany") == null ? "" : userInfo.get("isPipecompany").toString();
					// 是管道企业，可以登录
					if ("1".equals(isPipecompany)) {
						msg = "ok";
						jo.put("msg", msg);
						writer.print(jo.toString());
						return;
					} else {
						// 不是管道企业，要判断企业是否删除了
						String tradeTypes = userInfo.get("tradeTypes") == null ? "" : userInfo.get("tradeTypes").toString();
						if ("1".equals(tradeTypes)) {
							msg = "ok";
						} else {
							msg = "isDelete";
						}
					}
				}
			} catch (ApplicationAccessException e) {
				msg = "faile";
				success = false;
				e.printStackTrace();
			} catch (SQLException e) { 
				msg = "faile";
				success = false;
				e.printStackTrace();
			}
		}
		jo.put("msg", msg);
		jo.put("success", success);
		jo.put("industry", industry);
		writer.print(jo.toString());
		return;
	}

	/**
	 * 判断企业用户是否删除_企业服务平台登录判断,与 checkCompanyDelete 方法一致
	 * 
	 * @return
	 * @throws IOException
	 */
	public String checkCompanyDeleteQy() throws IOException {

		if (fkUser != null && fkUser.getUserName() != null) {
			try {
				Map<String, Object> userInfo = companyFacadeIface.loadCompanyUserInfoBySql(fkUser.getUserName().trim());
				if (userInfo != null) {
					industry = userInfo.get("industry") == null ? "" : userInfo.get("industry").toString();
					if (industry.equals("qiye")) {
						String isPipecompany = userInfo.get("isPipecompany") == null ? "" : userInfo.get("isPipecompany").toString();
						// 是管道企业，可以登录
						if ("1".equals(isPipecompany)) {
							msg = "ok";
						} else {
							// 不是管道企业，要判断企业是否删除了
							String tradeTypes = userInfo.get("tradeTypes") == null ? "" : userInfo.get("tradeTypes").toString();
							if ("1".equals(tradeTypes)) {
								msg = "ok";
							} else {
								msg = "isDelete";
							}
						}
					} else {
						msg = "zf";
					}
				}
			} catch (ApplicationAccessException e) {
				msg = "faile";
				success = false;
				e.printStackTrace();
			} catch (SQLException e) {
				msg = "faile";
				success = false;
				e.printStackTrace();
			}
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(msg);
		return null;
	}

	
	/**
	 * 根据工商注册号和组织机构编码获得用户信息
	 * 
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 */
	public String loadUserName() throws IOException {

		String userName;
		try {
			userName = companyFacadeIface.loadCompanyUser(regNum, setupNumber);
			//如果用户名为空的话，则把工商注册好默认赋值给用户名
			if(userName==null){
				userName=regNum;
			}
		} catch (Exception e) {
			userName=regNum;
			e.printStackTrace();
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(userName);
		return null;
	}

	
	/**
	 * 返回类型
	 * 
	 * @return
	 */
	public String loadUserType() {
		if (fkUser != null && fkUser.getUserName() != null) {

			try {
				try {
					industry = companyFacadeIface.loadCompanyUserIndustry(fkUser.getUserName().trim());
				} catch (SQLException e) {
					success = false;
					e.printStackTrace();
				}

			} catch (ApplicationAccessException e) {
				success = false;
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 修改企业用户密码
	 * 
	 * @return
	 */

	public String updateCompanyPasswordInit() {
		try {
			fkUser = companyFacadeIface.loadCompanyUserName(fkUser);
			company = companyFacadeIface.loadCompanyByUserId(fkUser.getId());

//			System.out.println("企业id=" + company.getRegNum() + "333=" + company.getCompanyName());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String updateCompanyPassword() {
		try {

			// 新用户名原用户名不同的时候，要判断是否存在
			if (!fkUser.getUserName().equals(oldUserName)) {
				boolean b = companyFacadeIface.getUserByUserName(fkUser.getUserName());
				if (b) {
					return "exist";
				}
			}

			companyFacadeIface.updateCompanyPassword(fkUser);
			
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()){
				syncTriggerService.updateFkUserGov(fkUser.getId());
			}
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String resetCompanyPassword() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			fkUser.setUserPass(Nbyhpc.PASSWORD);
			companyFacadeIface.updateCompanyPassword(fkUser);
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()){
				syncTriggerService.updateFkUserGov(fkUser.getId());
			}
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return NONE;
	}

	public String updateCompanyUserName() {
		try {

			// 新用户名原用户名不同的时候，要判断是否存在
			if (!fkUser.getUserName().equals(oldUserName)) {

				// 如果已经存在,提示不能修改
				boolean b = companyFacadeIface.getUserByUserName(fkUser.getUserName());
//				System.out.println("b:" + b);
				if (b) {
					return "exist";
				}

				// companyFacadeIface.getUserByUserName(fkUser.getUserName());

			}

			companyFacadeIface.updateCompanyUserName(fkUser);
		} catch (ApplicationAccessException ae) {
			if (log.isDebugEnabled()) {
				log.debug(ae.getOurStackTrace());
			}
			addActionError(ae.getOurMessage());
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询企业信息到用户添加页面
	 * 
	 * @return
	 */
	public String loadCompanyForUser() {
		try {
			company = companyFacadeIface.loadCompany(company);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改企业用户名
	 * 
	 * @return
	 */
	public String updaeUserName() {
		try {
			Integer userId = this.getUserDetail().getUserId();
			if (userId != null && userId > 0) {
				company = companyFacadeIface.loadCompanyByUserId(new Long(userId.toString()));
			}
			if (fkUser == null) {
				fkUser = new FkUser();
				fkUser.setId(new Long(userId.toString()));
				fkUser = companyFacadeIface.loadCompanyUserName(fkUser);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询企业用户名
	 * 
	 * @return
	 */
	public String loadCompanyUserName() {
		try {
			fkUser = companyFacadeIface.loadCompanyUserName(fkUser);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 添加企业用户名并关联企业信息
	 * 
	 * @return
	 */
	public String createCompanyUserName() {
		try {
			companyFacadeIface.createUserForCompany(company, fkUser, fkUserInfo, roleIds);
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()){
				syncTriggerService.updateFkUserGov(fkUser.getId());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadAllowCompanyPass() {
		try {
			flag = companyFacadeIface.isAllowDelete(companyIds);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载一个确认企业，修改查看用
	 * 
	 * @return
	 */
	public String loadCheckedCompany() {
		try {
			company = companyFacadeIface.loadCompany(company);
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null);
			tradeTypesAllow = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 取消确认
	 * 
	 * @return
	 */
	public String deleteCompanyPass() {
		try {
			if (companyFacadeIface.isAllowDelete(companyIds)) {
				companyFacadeIface.deleteCompanyPass(companyIds);
			} else {
				setMessageKey("company.isBag");
				return MESSAGE;
			}

		} catch (Exception e2) {
			setMessageKey("companyPass.delete.noAllow");
			return MESSAGE;
		}
		return SUCCESS;
	}

	/**
	 * 取消确认、确认
	 * 
	 * @return
	 */
	public String updateAffirm() {
		try {
			companyFacadeIface.updateAffirm(companyIds, state);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 加载确认企业
	 * 
	 * @return
	 */
	public String loadCheckedCompanies() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies = companyFacadeIface.loadCheckedCompanies(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载异地经营台帐
	 * 
	 * @return
	 */
	public String loadCompanyAcounts() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companyAcounts = companyFacadeIface.loadCompanyAcounts(company, companyAcount, pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanyArea() {
		try {
			company = companyFacadeIface.loadCompany(company);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String createCompanyAcount() {
		try {
			companyFacadeIface.createCompanyAcount(companyAcount);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String createCompanyPass() {
		try {
			companyFacadeIface.createCompanyPass(companyIds);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 待确认企业
	 * 
	 * @return
	 */
	public String loadUnCheckedCompanies() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies = companyFacadeIface.loadUnCheckedCompanies(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载创建企业基本信息的页面
	 * 
	 * @return
	 */
	public String createCompanyInit() {
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null);
			tradeTypesAllow = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());

			if (corecompany == null) {
				corecompany = new TCompany();
			}

			// 获得企业规模
			companyScaleEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.COMPANYSCALE);

			// 高风险作业枚举类型
			highRiskWorkEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.HIGH_RISK_WORK);

			UserDetailWrapper userDetailWrapper = this.getUserDetail();
			String userIndustry = userDetailWrapper.getUserIndustry();
			if (company != null && company.getSecondArea() != null) {
				if (userIndustry != null) {
					Department department = companyFacadeIface.getDepartmentByCode(userIndustry);
					if (department != null) {
						if (company.getSecondArea() != null && company.getSecondArea() != 0) {
							FkArea area = companyFacadeIface.loadArea(company.getSecondArea());
							if (area != null) {
								departmentName = area.getAreaName() + department.getCountyLevel();
							} else {
								departmentName = department.getCityLevel();
							}

						} else {
							departmentName = department.getCityLevel();
						}

					}

				}
			} else {
				if (userIndustry != null) {
					Department department = companyFacadeIface.getDepartmentByCode(userIndustry);
					departmentName = department.getCityLevel();
				}

			}

			// 获得经济类型大类
			economyKindEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.ECONOMYKIND);
			if (economyKindEnums != null && economyKindEnums.size() > 0) {
				// 经济类型大类不为空的情况下，查找子类的经济类型
				childEconomyKindEnums = companyFacadeIface.getFkEnumByParentIds(economyKindEnums);
			}

			// 获得国民经济分类大类
			nationalEconomicEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.NATIONAL_ECONOMIC);
			if (nationalEconomicEnums != null && nationalEconomicEnums.size() > 0) {
				// 国民经济分类大类不为空的情况下，查找二级国民经济分类
				childNationalEconomicEnums = companyFacadeIface.getFkEnumByParentIds(nationalEconomicEnums);
			}

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 创建企业基本信息
	 * 
	 * @return
	 */
	public String createCompany() {
		try {
			company.setUserId(getUserId());
			// add by huangjl
			// if (getUserDetail().getUserIndustry().equals("qiye")) {
			// company.setIsModify(1);
			// } else {
			// company.setIsModify(0);
			// }

			if (company.getFirstArea() == null || company.getFirstArea() == 0l) {
				company.setFirstArea(getUserDetail().getFirstArea());
				if ((company.getSecondArea() == null || company.getSecondArea() == 0l) && getUserDetail().getSecondArea() != null) {
					company.setSecondArea(getUserDetail().getSecondArea());

					if ((company.getThirdArea() == null || company.getThirdArea() == 0l) && getUserDetail().getThirdArea() != null) {
						company.setThirdArea(getUserDetail().getThirdArea());
					}

				}
			}

			company.setCreateTime(new Date());
			String companyId = companyFacadeIface.createCompany(company, getUserDetail());
			try {
				company.setId(Long.parseLong(companyId));
				// companyFacadeIface.createCoreCompany(companyId);//
				// 同步新增老中心数据库的企业信息
			} catch (Exception e) {
				setMessageKey(companyId);
				company = companyFacadeIface.loadCompanyByCompanyName(company);
				setMessageKey("company.already.exists");
				return MESSAGE;
			}
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()
					&&company.getId()!=null&&company.getId()>0){
				syncTriggerService.updateCompanyGov(company.getId());
			}
			
			
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e2) {
			e2.printStackTrace();
			return ERROR;
		}
		return company.getHzTradeTypes() == null ? "notAffirm" : "affirm";
	}

	/**
	 * 删除企业信息
	 * 
	 * @return
	 */
	public String delCompanyHy() {
		try {
//			System.out.println("company.getId(): " + company.getId());
//			System.out.println("--------------------------------------------------");
			companyFacadeIface.delCompanyHy(company);
			
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()
					&&company.getId()!=null&&company.getId()>0){
				syncTriggerService.updateCompanyGov(company.getId());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e2) {
			e2.printStackTrace();
			return ERROR;
		}
		return "affirm";
	}

	/**
	 * 加载一个企业基本信息的内容，或用于显示或预备修改
	 * 
	 * @return
	 */
	public String loadCompany() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(companyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = companyFacadeIface.loadCompany(company);
			
			/*下列 if 判断代码 为避免不必要更新 ---后加*/
			if(!company.getCompanyName().trim().equals(company.getCompanyName())){
				company.setCompanyName(company.getCompanyName().trim());
			}
			if(company.getAddress()==null||company.getAddress().equals("null")){
				company.setAddress((company.getAddress() != null && !company.getAddress().equals("null")) ? company.getAddress() : "");
			}
			if(company.getSafetyMngPerson()==null||company.getSafetyMngPerson().equals("null")){
				company.setSafetyMngPerson((company.getSafetyMngPerson() != null && !company.getSafetyMngPerson().equals("null")) ? company.getSafetyMngPerson() : "");
			}
			if(company.getSafetyMngPersonPhone()==null||company.getSafetyMngPersonPhone().equals("null")){
				company.setSafetyMngPersonPhone((company.getSafetyMngPersonPhone() != null && !company.getSafetyMngPersonPhone().equals("null")) ? company.getSafetyMngPersonPhone() : "");
			}
			if(company.getRegAddress()==null||company.getRegAddress().equals("null")){
				company.setRegAddress((company.getRegAddress() != null && !company.getRegAddress().equals("null")) ? company.getRegAddress() : "");
			}
			
			// System.out.println("company.getSetupNumber:" +
			// company.getSetupNumber());

			if (corecompany == null) {
				// corecompany = new TCompany();
				// corecompany.setBusinessRegNum(company.getRegNum());
				// corecompany.setCompanyName(company.getCompanyName());
				// corecompany = companyFacadeIface.getCoreCompany(corecompany);
			}
			// 根据中心库企业的部门编码，管理分类一级，管理分类二级编码获得对应本系统的行业类别的id
			if (corecompany != null) {
				if (corecompany.getDeptCode() != null) {
					List<DaIndustryParameter> daIndustryParameters = companyFacadeIface.getDaIndustryParameterByCode(corecompany.getDeptCode());
					if (daIndustryParameters != null && daIndustryParameters.size() > 0) {
						tradetypeId1 = daIndustryParameters.get(0).getId();
					}
				}

				if (corecompany.getManagementLevel1() != null) {
					List<DaIndustryParameter> daIndustryParameters = companyFacadeIface.getDaIndustryParameterByCode(corecompany.getManagementLevel1());
					if (daIndustryParameters != null && daIndustryParameters.size() > 0) {
						tradetypeId2 = daIndustryParameters.get(0).getId();
					}
				}

				if (corecompany.getManagementLevel2() != null) {
					List<DaIndustryParameter> daIndustryParameters = companyFacadeIface.getDaIndustryParameterByCode(corecompany.getManagementLevel2());
					if (daIndustryParameters != null && daIndustryParameters.size() > 0) {
						tradetypeId3 = daIndustryParameters.get(0).getId();
					}
				}
			}

			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null);
			tradeTypesAllow = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			// 获得经济类型大类
			economyKindEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.ECONOMYKIND);
			if (economyKindEnums != null && economyKindEnums.size() > 0) {
				// 经济类型大类不为空的情况下，查找子类的经济类型
				childEconomyKindEnums = companyFacadeIface.getFkEnumByParentIds(economyKindEnums);
			}

			// 获得行业分类大类
			// tradeTypeEnums=companyFacadeIface.getEnumByParentCode(Nbyhpc.TRADETYPE);
			// if(tradeTypeEnums!=null&&tradeTypeEnums.size()>0){
			// 行业分类大类不为空的情况下，查找二级行业分类
			// childTradeTypeEnums=companyFacadeIface.getFkEnumByParentIds(tradeTypeEnums);
			// }

			// 获得国民经济分类大类
			nationalEconomicEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.NATIONAL_ECONOMIC);
			if (nationalEconomicEnums != null && nationalEconomicEnums.size() > 0) {
				// 国民经济分类大类不为空的情况下，查找二级国民经济分类
				childNationalEconomicEnums = companyFacadeIface.getFkEnumByParentIds(nationalEconomicEnums);
			}

			// 获得企业规模
			companyScaleEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.COMPANYSCALE);
			// 高风险作业
			highRiskWorkEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.HIGH_RISK_WORK);
			if (company != null && company.getSecondArea() != null) {
				UserDetailWrapper userDetailWrapper = this.getUserDetail();
				String userIndustry = userDetailWrapper.getUserIndustry();

				// add by huangjl 企业没有修改信息的话，跳转到提示页面。
				if ("qiye".equals(getUserDetail().getUserIndustry())||"admin".equals(getUserDetail().getUserIndustry())) {
					// 如果是企业用户的话，根据企业的区域和所属的行业生成
					Set<DaIndustryParameter> hzTradeTypes = company.getHzTradeTypes();
					for (DaIndustryParameter daIndustryParameter : hzTradeTypes) {
						if (daIndustryParameter.getDaIndustryParameter() == null) {
							String code = daIndustryParameter.getCode();
							if (code != null && !"".equals(code)) {
								Department department = companyFacadeIface.getDepartmentByCode(code);

								if (department != null) {
									if (company.getSecondArea() != null && company.getSecondArea() != 0) {
										FkArea area = companyFacadeIface.loadArea(company.getSecondArea());
										if (area != null) {
											departmentName = area.getAreaName() + department.getCountyLevel();
										} else {
											departmentName = department.getCityLevel();
										}

									} else {
										if (company.getFirstArea() != null && company.getFirstArea() != 0) {
											area = companyFacadeIface.loadArea(company.getFirstArea());
											departmentName = area.getAreaName() + department.getCityLevel();
										} else {
											departmentName = department.getCityLevel();
										}
									}
								}
							}
						}
					}

				} else {

					if (userIndustry != null) {
						Department department = companyFacadeIface.getDepartmentByCode(userIndustry);
						if (department != null) {
							if (company.getSecondArea() != null && company.getSecondArea() != 0) {
								FkArea area = companyFacadeIface.loadArea(company.getSecondArea());
								if (area != null) {
									departmentName = area.getAreaName() + department.getCountyLevel();
								} else {
									departmentName = department.getCityLevel();
								}

							} else {
								if (company.getFirstArea() != null && company.getFirstArea() != 0) {
									area = companyFacadeIface.loadArea(company.getFirstArea());
									departmentName = area.getAreaName() + department.getCityLevel();
								} else {
									departmentName = department.getCityLevel();
								}
							}
						}
					}
				}

			}

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}

	
	/**
	 * 加载一个企业基本信息的内容，用于重新绑定
	 * 
	 * @return
	 */
	public String loadCompanyForBinding() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(companyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = companyFacadeIface.loadCompany(company);
			company.setCompanyName(company.getCompanyName().trim());
			company.setAddress((company.getAddress() != null && !company.getAddress().equals("null")) ? company.getAddress() : "");
			company.setSafetyMngPerson((company.getSafetyMngPerson() != null && !company.getSafetyMngPerson().equals("null")) ? company.getSafetyMngPerson() : "");
			company.setSafetyMngPersonPhone((company.getSafetyMngPersonPhone() != null && !company.getSafetyMngPersonPhone().equals("null")) ? company.getSafetyMngPersonPhone() : "");
			company.setRegAddress((company.getRegAddress() != null && !company.getRegAddress().equals("null")) ? company.getRegAddress() : "");
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}
	public String updateCompanyLevel() {
		try {
			companyFacadeIface.updateCompanyLevel(company);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadCompanyForLevel() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(companyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = companyFacadeIface.loadCompany(company);
			tradeTypesForCompanyLevel = companyFacadeIface.loadTradeTypesForCompanyLevel();
			pointTypes = companyFacadeIface.loadTradePointTypesForTradeType();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompaniesForLevel() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = companyFacadeIface.loadCompaniesForLevel(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改企业基本信息
	 * 
	 * @return
	 */
	public String updateCompany() {

		try {
			if (!isAdmin(getUserDetail()))
				company.setUserId(getUserId());

			// add by huangjl
			// if (getUserDetail().getUserIndustry().equals("qiye")) {
			// company.setIsModify(1);
			// } else {
			// company.setIsModify(0);
			// }

			if (company.getFirstArea() == null || company.getFirstArea() == 0l) {
				company.setFirstArea(getUserDetail().getFirstArea());
				if ((company.getSecondArea() == null || company.getSecondArea() == 0l) && getUserDetail().getSecondArea() != null) {
					company.setSecondArea(getUserDetail().getSecondArea());

					if ((company.getThirdArea() == null || company.getThirdArea() == 0l) && getUserDetail().getThirdArea() != null) {
						company.setThirdArea(getUserDetail().getThirdArea());
					}

				}
			}

			String companyId = companyFacadeIface.updateCompany(company, corecompany, getUserDetail());
			try {
				company.setId(Long.parseLong(companyId));
			} catch (Exception e) {
				setMessageKey(companyId);
				company = companyFacadeIface.loadCompanyByCompanyName(company);
				return MESSAGE;
			}
			if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()
					&&company.getId()!=null&&company.getId()>0){
				syncTriggerService.updateCompanyGov(company.getId());
			}else if(systemConstant.isDataSeparation()&&systemConstant.isEnterprise()
					&&company.getId()!=null&&company.getId()>0){
				syncTriggerService.updateCompanyEs(company.getId());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e2) {
			e2.printStackTrace();
			return ERROR;
		}

		if (getUserDetail().getUserIndustry().equals("qiye")) {
			return "info";
		}
		return SUCCESS;
	}

	/**
	 * 删除企业基本信息
	 * 
	 * @return
	 */
	public String deleteCompanies() {
		try {
			String noAllowCompanies = companyFacadeIface.deleteCompanies(ids, getUserDetail());
			if (noAllowCompanies != null && !"".equals(noAllowCompanies)) {
				setMessageKey(getText("company.delete.noAllow", new String[] { noAllowCompanies }));
				return MESSAGE;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载已审核企业集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadCompaniesAffirm() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = companyFacadeIface.loadCompaniesAffirm(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}

		// 判读是否是安委的用户
		if (getUserDetail().getUserIndustry().equals("anwei")) {
			this.isAnweiUser = true;
		} else {
			this.isAnweiUser = false;
		}

		// 获得企业规模
		companyScaleEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.COMPANYSCALE);

		// 高风险作业枚举类型
		highRiskWorkEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.HIGH_RISK_WORK);
		return SUCCESS;
	}

	/**
	 * 加载已审核企业集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadCompaniesForAdmin() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies = companyFacadeIface.loadCompaniesAffirm(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载企业基本信息
	 * 
	 * @return
	 */
	public String loadCompanies() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies = companyFacadeIface.loadCompanyList(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 加载待审核企业集合
	 */
	public String loadUnAuditingCompanise() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = companyFacadeIface.loadUnAuditingCompanise(company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 通过审核
	 * 
	 * @return
	 */
	public String updateDoAuditingCompanise() {

		try {
			companyFacadeIface.doAuditingCompanyise(ids, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 企业注册页面
	 * 
	 * @author:liulj
	 * @time:2013-10-15
	 */
	public String loadCompanyRegister() {

		return SUCCESS;
	}

	/**
	 * 企业验证功能
	 * 
	 * @author:liulj
	 * @throws MapperException
	 * @time:2013-10-15
	 */
	public String loadCheckCompany() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		String establishmentDay = "";
		try {
			corecompany = companyFacadeIface.loadCoreCompany(corecompany);
		} catch (ApplicationAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (corecompany != null) {
			resultJSON.put("companyName", corecompany.getCompanyName());
			resultJSON.put("businessRegNum", corecompany.getBusinessRegNum());
			resultJSON.put("orgCode", corecompany.getOrgCode());
			resultJSON.put("secondArea", corecompany.getSecondArea());
			resultJSON.put("thirdArea", corecompany.getThirdArea());
			resultJSON.put("fouthArea", corecompany.getFouthArea());
			resultJSON.put("uuid", corecompany.getUuid());
			resultJSON.put("legalPerson", corecompany.getLegalPerson());

			resultJSON.put("businessAddress", corecompany.getBusinessAddress());
			resultJSON.put("phone", corecompany.getPhone());
			resultJSON.put("productionScale", corecompany.getProductionScale());
			resultJSON.put("enterprise", corecompany.getEnterprise());

			resultJSON.put("managementLevel1", corecompany.getManagementLevel1());
			resultJSON.put("managementLevel2", corecompany.getManagementLevel2());

			resultJSON.put("economicType1", corecompany.getEconomicType1());
			resultJSON.put("economicType2", corecompany.getEconomicType2());

			resultJSON.put("safetyMngPerson", corecompany.getSafetyMngPerson());
			resultJSON.put("safetyMngPersonPhone", corecompany.getSafetyMngPersonPhone());
			resultJSON.put("focusFireUnits", corecompany.getFocusFireUnits());

			if (corecompany.getEstablishmentDay() != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				establishmentDay = format.format(corecompany.getEstablishmentDay());
			}
			resultJSON.put("establishmentDay", establishmentDay);

			resultJSON.put("businessScope", corecompany.getBusinessScope());
			resultJSON.put("regAddress", corecompany.getRegAddress());
			resultJSON.put("nationalEconomicType1", corecompany.getNationalEconomicType1());
			resultJSON.put("nationalEconomicType2", corecompany.getNationalEconomicType2());

			resultJSON.put("principalPerson", corecompany.getPrincipalPerson());
			resultJSON.put("legalTelephone", corecompany.getLegalTelephone());
			resultJSON.put("principalTelephone", corecompany.getPrincipalTelephone());
			resultJSON.put("principalMobile", corecompany.getPrincipalMobile());
		}
		// else{
		// resultJSON.put("companyName", "");
		// resultJSON.put("secondArea", "");
		// resultJSON.put("thirdArea", "");
		// }

		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}

	/**
	 * 企业注册功能
	 * 
	 * @author:liulj
	 * @throws MapperException
	 * @throws IOException
	 * @time:2013-10-16
	 */
	public String registerCompany() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		String flag = "";
		try {
			flag = companyFacadeIface.loadCompanyInfo(corecompany);
		} catch (ApplicationAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (company.getFlag().equals("1")){
		// resultJSON.put("flag", "该企业已存在隐患排查企业库中，请联系当地乡镇安监站（所）或技术公司查询");
		// }else if (company.getFlag().equals("2")){
		// resultJSON.put("flag",
		// "注册成功 ，企业初始帐号为"+company.getUserName()+",密码为123456！");
		// }
		resultJSON.put("flag", flag);
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}

	/**
	 * 清除缓存功能
	 * 
	 * @return
	 */
	public String loadCaches() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			caches = companyFacadeIface.loadCaches(cache, pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 清除缓存功能
	 * 
	 * @return
	 */
	public String delCaches() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			companyFacadeIface.delCache(cache);
			response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return NONE;
	}

	public String loadCompaniesForClassify() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			// companies = companyFacadeIface.loadCompaniesForLevel(company,
			// pagination, getUserDetail());

			companies = companyFacadeIface.loadCompaniesForClassic(company, pagination, getUserDetail());

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	public String loadCompaniesForClassify1() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			// companies = companyFacadeIface.loadCompaniesForLevel(company,
			// pagination, getUserDetail());
			companies = companyFacadeIface.loadCompaniesForClassic(company, pagination, getUserDetail());

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanyForClassify() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(companyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = companyFacadeIface.loadCompany(company);
			tradeTypesForCompanyLevel = companyFacadeIface.loadTradeTypesForCompanyLevel();
			pointTypes = companyFacadeIface.loadTradePointTypesForTradeType();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanyForClassify1() {
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(companyFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = companyFacadeIface.loadCompany(company);
			tradeTypesForCompanyLevel = companyFacadeIface.loadTradeTypesForCompanyLevel();
			pointTypes = companyFacadeIface.loadTradePointTypesForTradeType();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String checkSetupNumber() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			List<DaCompany> c = companyFacadeIface.checkSetupNumber(company);
			if (c != null && c.size() > 0) {
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return NONE;
	}

	public String checkRegNum() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
//			System.out.println("company.getRegNum(): " + company.getRegNum());
			List<DaCompany> c = companyFacadeIface.checkRegNum(company);
			if (c != null && c.size() > 0) {
				response.getWriter().write("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return NONE;
	}

	
	public String updateCompanyForBinding() {
			if (company == null) {
//				System.out.print("企业为空，错误。");
				return ERROR;
			}
			
			if (tcompany == null) {
//				System.out.print("要绑定的中心库企业为空，错误。");
				return ERROR;
			}
		
			//调用绑定方法
			try {
				companyFacadeIface.companyBinding(company, tcompany);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return SUCCESS;
	}
	
	public void updatePassword() throws IOException {
		String msg = "true";
		try {
			getFkUser().setId(getUserId());
			userFacadeIface.updatePassword(getFkUser(), this.oldPassword);
			getRequest().getSession().removeAttribute("easyPassword");
		} catch (ApplicationAccessException ae) {
			msg = ae.getOurMessage();
		}
		getResponse().getWriter().print(msg);
	}
	
	/**
	 * getter and setter
	 * 
	 */
	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<DaCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(List<DaCompany> companies) {
		this.companies = companies;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public List<DaIndustryParameter> getTradeTypesAllow() {
		return tradeTypesAllow;
	}

	public void setTradeTypesAllow(List<DaIndustryParameter> tradeTypesAllow) {
		this.tradeTypesAllow = tradeTypesAllow;
	}

	public String[] getAuditingcompanyIds() {
		return AuditingcompanyIds;
	}

	public void setAuditingcompanyIds(String[] companyIds) {
		this.AuditingcompanyIds = companyIds;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

	public DaCompanyAcount getCompanyAcount() {
		return companyAcount;
	}

	public void setCompanyAcount(DaCompanyAcount companyAcount) {
		this.companyAcount = companyAcount;
	}

	public List<DaCompanyAcount> getCompanyAcounts() {
		return companyAcounts;
	}

	public void setCompanyAcounts(List<DaCompanyAcount> companyAcounts) {
		this.companyAcounts = companyAcounts;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public FkUser getFkUser() {
		return fkUser;
	}

	public void setFkUser(FkUser fkUser) {
		this.fkUser = fkUser;
	}

	public FkUserInfo getFkUserInfo() {
		return fkUserInfo;
	}

	public void setFkUserInfo(FkUserInfo fkUserInfo) {
		this.fkUserInfo = fkUserInfo;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getConfirmpass() {
		return confirmpass;
	}

	public void setConfirmpass(String confirmpass) {
		this.confirmpass = confirmpass;
	}

	public List<DaPointType> getPointTypes() {
		return pointTypes;
	}

	public void setPointTypes(List<DaPointType> pointTypes) {
		this.pointTypes = pointTypes;
	}

	public List<DaIndustryParameter> getTradeTypesForCompanyLevel() {
		return tradeTypesForCompanyLevel;
	}

	public void setTradeTypesForCompanyLevel(List<DaIndustryParameter> tradeTypesForCompanyLevel) {
		this.tradeTypesForCompanyLevel = tradeTypesForCompanyLevel;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public TCompany getCorecompany() {
		return corecompany;
	}

	public void setCorecompany(TCompany corecompany) {
		this.corecompany = corecompany;
	}

	public List<FkEnum> getEconomyKindEnums() {
		return economyKindEnums;
	}

	public void setEconomyKindEnums(List<FkEnum> economyKindEnums) {
		this.economyKindEnums = economyKindEnums;
	}

	public List<FkEnum> getChildEconomyKindEnums() {
		return childEconomyKindEnums;
	}

	public void setChildEconomyKindEnums(List<FkEnum> childEconomyKindEnums) {
		this.childEconomyKindEnums = childEconomyKindEnums;
	}

	public List<FkEnum> getTradeTypeEnums() {
		return tradeTypeEnums;
	}

	public void setTradeTypeEnums(List<FkEnum> tradeTypeEnums) {
		this.tradeTypeEnums = tradeTypeEnums;
	}

	public List<FkEnum> getChildTradeTypeEnums() {
		return childTradeTypeEnums;
	}

	public void setChildTradeTypeEnums(List<FkEnum> childTradeTypeEnums) {
		this.childTradeTypeEnums = childTradeTypeEnums;
	}

	public List<FkEnum> getNationalEconomicEnums() {
		return nationalEconomicEnums;
	}

	public void setNationalEconomicEnums(List<FkEnum> nationalEconomicEnums) {
		this.nationalEconomicEnums = nationalEconomicEnums;
	}

	public List<FkEnum> getChildNationalEconomicEnums() {
		return childNationalEconomicEnums;
	}

	public void setChildNationalEconomicEnums(List<FkEnum> childNationalEconomicEnums) {
		this.childNationalEconomicEnums = childNationalEconomicEnums;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getTradetypeId1() {
		return tradetypeId1;
	}

	public void setTradetypeId1(Long tradetypeId1) {
		this.tradetypeId1 = tradetypeId1;
	}

	public Long getTradetypeId2() {
		return tradetypeId2;
	}

	public void setTradetypeId2(Long tradetypeId2) {
		this.tradetypeId2 = tradetypeId2;
	}

	public Long getTradetypeId3() {
		return tradetypeId3;
	}

	public void setTradetypeId3(Long tradetypeId3) {
		this.tradetypeId3 = tradetypeId3;
	}

	public List<FkEnum> getCompanyScaleEnums() {
		return companyScaleEnums;
	}

	public void setCompanyScaleEnums(List<FkEnum> companyScaleEnums) {
		this.companyScaleEnums = companyScaleEnums;
	}

	public Boolean getIsAnweiUser() {
		return isAnweiUser;
	}

	public void setIsAnweiUser(Boolean isAnweiUser) {
		this.isAnweiUser = isAnweiUser;
	}

	public String getOldUserName() {
		return oldUserName;
	}

	public void setOldUserName(String oldUserName) {
		this.oldUserName = oldUserName;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TCaches getCache() {
		return cache;
	}

	public void setCache(TCaches cache) {
		this.cache = cache;
	}

	public List<TCaches> getCaches() {
		return caches;
	}

	public void setCaches(List<TCaches> caches) {
		this.caches = caches;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry
	 *            the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the highRiskWorkEnums
	 */
	public List<FkEnum> getHighRiskWorkEnums() {
		return highRiskWorkEnums;
	}

	/**
	 * @param highRiskWorkEnums
	 *            the highRiskWorkEnums to set
	 */
	public void setHighRiskWorkEnums(List<FkEnum> highRiskWorkEnums) {
		this.highRiskWorkEnums = highRiskWorkEnums;
	}

	/**
	 * @return the regNum
	 */
	public String getRegNum() {
		return regNum;
	}

	/**
	 * @param regNum the regNum to set
	 */
	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	/**
	 * @return the setupNumber
	 */
	public String getSetupNumber() {
		return setupNumber;
	}

	/**
	 * @param setupNumber the setupNumber to set
	 */
	public void setSetupNumber(String setupNumber) {
		this.setupNumber = setupNumber;
	}

	/**
	 * @return the tcompany
	 */
	public TCompany getTcompany() {
		return tcompany;
	}

	/**
	 * @param tcompany the tcompany to set
	 */
	public void setTcompany(TCompany tcompany) {
		this.tcompany = tcompany;
	}

	public void setSyncTriggerService(SyncTriggerService syncTriggerService) {
		this.syncTriggerService = syncTriggerService;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}


}
