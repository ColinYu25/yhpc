package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import cn.safetys.constant.SystemConstant;
import cn.safetys.sync.mq.SyncTriggerService;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.ajax.json.JsonUtil;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.model.Message;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.facade.iface.DangerFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) DangerAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class DangerAction extends DaAppAction {

	/**
	 * 重大隐患信息
	 */
	private static final long serialVersionUID = -8970302687851485075L;

	private DangerFacadeIface dangerFacadeIface;// 重大隐患信息的业务接口

	private List<DaDanger> dangers;// 重大隐患集合

	private List<DaCompany> companies;// 企业信息集合

	private List<DaIndustryParameter> industryParameters;// 行业集合

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	private List statistics;

	private DaCompany company;// 企业单位

	private DaDanger danger;// 重大隐患

	private Pagination pagination;// 分页对象

	private String ids;// 重大隐患序号的集合，针对类似于批量删除操作

	private long count; // 未治理重大隐患数

	private long count1;// 季度报表数量

	private String departmentName;

	private String num;  //工商注册号或组织机构代码

	private String result;

	// add by huangjl 2014-1-8
	private CompanyFacadeIface companyFacadeIface;
	private List<FkEnum> hazardSourceEnums; // 隐患来源枚举类型
	// private List<DaIndustryParameter> parentDaIndustryParameters; //隐患类别大类
	private List<DaIndustryParameter> childDaIndustryParameters; // 隐患类别子类
	
	private List<DaDangerImage> daDangerImageList;
	
	private DaDangerImageFacadeIface daDangerImageFacadeIface;

	private SyncTriggerService syncTriggerService;
	private SystemConstant systemConstant;
	
	/**
	 * 加载一个重大隐患信息的内容
	 * 
	 * @return
	 */
	public String createDangerInit() {
		try {
			// industryParameters = dangerFacadeIface.loadIndustrysForDanger();
			company = dangerFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			// 获得隐患来源枚举类型
			hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
			// 获得隐患分类大类
			industryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.DANGER_TYPE);
			// 获得隐患分类子类
			childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(industryParameters);

			if (company != null && company.getSecondArea() != null) {
				FkArea area = null;
				UserDetailWrapper userDetailWrapper = this.getUserDetail();
				String userIndustry = userDetailWrapper.getUserIndustry();
				if (userIndustry != null) {
					Department department = companyFacadeIface.getDepartmentByCode(userIndustry);
					if (department != null) {
						if (company.getSecondArea() != null && company.getSecondArea() != 0) {
							area = companyFacadeIface.loadArea(company.getSecondArea());
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
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载一个重大隐患信息的内容，或用于显示或预备修改
	 * 
	 * @return
	 */
	public String loadDanger() {
		try {
			industryParameters = dangerFacadeIface.loadIndustrysForDanger();
			danger = dangerFacadeIface.loadDanger(danger);
			company = dangerFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));

			// 获得隐患来源枚举类型
			hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
			// 获得隐患分类大类
			industryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.DANGER_TYPE);
			// 获得隐患分类子类
			childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(industryParameters);
			daDangerImageList = daDangerImageFacadeIface.findByDanger(danger);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 创建重大隐患信息
	 * 
	 * @return
	 */
	public String createDanger() {
		try {
			if (danger.getListFile() != null) {
				String msg = FileUpload.ValidateFile(danger.getListFile(), FileUpload.ALLOW_TYPE_JPG);
				if (StringUtils.isNotEmpty(msg)) {
					this.setMessageKey(msg);
					return MESSAGE_TO_BACK;
				}
			}
			danger.setUserId(getUserId());
			dangerFacadeIface.createDanger(danger);
			
			if(systemConstant.isDataSeparation()&&danger.getId()!=null&&danger.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateDangerEs(danger.getId());
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGov(danger.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if (getUserDetail().getUserIndustry().equals("qiye"))
			return "qiye";
		else
			return "success";
	}

	/**
	 * 修改重大隐患信息
	 * 
	 * @return
	 */
	public String updateDanger() {
		try {
			if (danger.getListFile() != null) {
				String msg = FileUpload.ValidateFile(danger.getListFile(), FileUpload.ALLOW_TYPE_JPG);
				if (StringUtils.isNotEmpty(msg)) {
					this.setMessageKey(msg);
					return MESSAGE_TO_BACK;
				}
			}
			danger.setUserId(getUserId());
			dangerFacadeIface.updateDanger(danger);
			
			if(systemConstant.isDataSeparation()&&danger.getId()!=null&&danger.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateDangerEs(danger.getId());
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGov(danger.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 删除重大隐患信息
	 * 
	 * @return
	 */
	public String deleteDanger() {
		try {
			dangerFacadeIface.deleteDanger(danger);
			
			if(systemConstant.isDataSeparation()&&danger.getId()!=null&&danger.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateDangerEs(danger.getId());
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateDangerGov(danger.getId());
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String deleteDangers() {
		try {

			dangerFacadeIface.deleteDangers(ids);
			
			if(systemConstant.isDataSeparation()&&StringUtils.isNotBlank(ids)){
				String[] strs = StringUtils.split(ids,",");
				try {
					for(int i=0;i<strs.length;i++){
						if(systemConstant.isEnterprise()){
							syncTriggerService.updateDangerEs(Long.valueOf(strs[i]));
						}else if(systemConstant.isGovernment()){
							syncTriggerService.updateDangerGov(Long.valueOf(strs[i]));
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载重大隐患信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadDangers() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(dangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			company = dangerFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。
			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;
			}
			
			if(danger!=null&&"1".equals(danger.getJbFlag())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if (danger.getJbYear() == 0) {
					Calendar cal = Calendar.getInstance();
					String mDateTime = formatter.format(cal.getTime());
					danger.setJbYear(cal.get(Calendar.YEAR));
					if (mDateTime.compareTo(danger.getJbYear() + "-01-01") >= 0 && mDateTime.compareTo(danger.getJbYear() + "-04-01") < 0) {
						danger.setJbQuarter(1);
					} else if (mDateTime.compareTo(danger.getJbYear() + "-04-01") >= 0 && mDateTime.compareTo(danger.getJbYear() + "-07-01") < 0) {
						danger.setJbQuarter(2);
					} else if (mDateTime.compareTo(danger.getJbYear() + "-07-01") >= 0 && mDateTime.compareTo(danger.getJbYear() + "-10-01") < 0) {
						danger.setJbQuarter(3);
					} else if (mDateTime.compareTo(danger.getJbYear() + "-10-01") >= 0 && mDateTime.compareTo(danger.getJbYear() + "-12-31") <= 0) {
						danger.setJbQuarter(4);
					}
				}
				
				dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
			}else{
				dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
			}
			
			// System.out.println("11111111111111111111");
			// System.out.println(pagination.getTotalCount());
			// long count=pagination.getTotalCount();
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangersOfCompanyUnGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(dangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}

			if (danger == null) {
				danger = new DaDanger();
			}
			danger.setIsGorver("0");
			company = dangerFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。

			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;
			}
			dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangersOfCompanyGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(dangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}

			if (danger == null) {
				danger = new DaDanger();
			}
			danger.setIsGorver("1");
			company = dangerFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。
			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;
			}

			dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 隐患到期提醒记录
	 * 
	 * @return
	 */
	public String loadDangersOfCompanyTimeOut() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(dangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			if (danger == null) {
				danger = new DaDanger();
			}
			danger.setIsGorver("2");
			company = dangerFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。
			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;
			}

			dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载未治理重大隐患企业信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadDangersUnGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (danger == null) {
				danger = new DaDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("0");
			tradeTypes = dangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			dangers = dangerFacadeIface.loadDangersOfCompanies(danger, company, pagination, getUserDetail());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载未治理重大隐患数量
	 */
	public void loadDangersUnGorverCount() {
		try {
			if (danger == null) {
				danger = new DaDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("0");
			long count = dangerFacadeIface.loadDangers4Count(danger, company, pagination, getUserDetail());
			getResponse().getWriter().print(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载已治理重大隐患企业信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadDangersGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (danger == null) {
				danger = new DaDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}

			danger.setIsGorver("1");
//			System.out.println("getUserDetail().getThirdArea(): " + getUserDetail().getThirdArea());
			if (getUserDetail().getThirdArea() == null || getUserDetail().getThirdArea() == 0l) {
				danger.setHc(1l);
			} else {
				danger.setHc(0l);
			}
			tradeTypes = dangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			dangers = dangerFacadeIface.loadDangersOfCompanies(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载重大隐患将到期企业信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadDangersTimeOut() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (danger == null) {
				danger = new DaDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("2");
			tradeTypes = dangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			dangers = dangerFacadeIface.loadDangersOfCompanies(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载重大隐患挂牌列表，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadDangersRollcall() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (danger == null) {
				danger = new DaDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}
			tradeTypes = dangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			dangers = dangerFacadeIface.loadDangersOfCompanies(danger, company, pagination, getUserDetail());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载重大隐患排查数量
	 * 
	 * @return
	 */
	public String loadDangercount() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
			}
			company = dangerFacadeIface.loadCompany(company);
			dangers = dangerFacadeIface.loadDangers(danger, company, pagination, getUserDetail());
			count1 = dangerFacadeIface.loadCompanyQuarterlyAccount(company);
			count = pagination.getTotalCount();
			// System.out.println(count1);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 测试json 数据
	 */

	public String loadJson() {

		return SUCCESS;
	}

	/**
	 * 隐患到期提醒记录Json
	 * 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public String loadDangersTimeOutJson() throws ApplicationAccessException {
		List<Message> mapList = new ArrayList<Message>();
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(100);
		}
		company = new DaCompany();

		List<DaCompany> c = dangerFacadeIface.loadCompanyIdByNum(num);

		if (c.size() > 0) {
			company.setId(c.get(0).getId());
			if (danger == null) {
				danger = new DaDanger();
			}
			danger.setIsGorver("2");
			
			company = dangerFacadeIface.loadCompany(company);
			dangers = dangerFacadeIface.loadDangers(danger, company, pagination, null);
			for (DaDanger danger : dangers) {
				Message  map = new Message();
				map.setName("重大隐患到期提醒");
				map.setProjectLike("http://60.190.2.247:7011/nbyhpc/cas.jsp?type=dangersTimeOut&companyId="+company.getId());
				map.setReleasetime(danger.getFinishDate().toString());
				mapList.add(map);
			}
		
		}
		
		result = JsonUtil.list2json(mapList);
		result=StringEscapeUtils.unescapeJava(result);
		
		return SUCCESS;
	}

	public void setDangerFacadeIface(DangerFacadeIface dangerFacadeIface) {
		this.dangerFacadeIface = dangerFacadeIface;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaDanger getDanger() {
		return danger;
	}

	public void setDanger(DaDanger danger) {
		this.danger = danger;
	}

	public List<DaDanger> getDangers() {
		return dangers;
	}

	public void setDangers(List<DaDanger> dangers) {
		this.dangers = dangers;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public List getStatistics() {
		return statistics;
	}

	public void setStatistics(List statistics) {
		this.statistics = statistics;
	}

	public List<DaIndustryParameter> getIndustryParameters() {
		return industryParameters;
	}

	public void setIndustryParameters(List<DaIndustryParameter> industryParameters) {
		this.industryParameters = industryParameters;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getCount1() {
		return count1;
	}

	public void setCount1(long count1) {
		this.count1 = count1;
	}

	public List<FkEnum> getHazardSourceEnums() {
		return hazardSourceEnums;
	}

	public void setHazardSourceEnums(List<FkEnum> hazardSourceEnums) {
		this.hazardSourceEnums = hazardSourceEnums;
	}

	public List<DaIndustryParameter> getChildDaIndustryParameters() {
		return childDaIndustryParameters;
	}

	public void setChildDaIndustryParameters(List<DaIndustryParameter> childDaIndustryParameters) {
		this.childDaIndustryParameters = childDaIndustryParameters;
	}

	public CompanyFacadeIface getCompanyFacadeIface() {
		return companyFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setSyncTriggerService(SyncTriggerService syncTriggerService) {
		this.syncTriggerService = syncTriggerService;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}

	public List<DaDangerImage> getDaDangerImageList() {
		return daDangerImageList;
	}

	public void setDaDangerImageList(List<DaDangerImage> daDangerImageList) {
		this.daDangerImageList = daDangerImageList;
	}

}
