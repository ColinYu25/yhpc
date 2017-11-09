package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.safetys.constant.SystemConstant;
import cn.safetys.sync.mq.SyncTriggerService;

import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaBag;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaSeasonReport;
import com.safetys.nbyhpc.domain.model.DaSeasonReportDetail;
import com.safetys.nbyhpc.facade.iface.BagFacadeIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.SeasonReportFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) SeasonReportAction.java
 * @date 2009-08-21
 * @author tangzg
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class SeasonReportAction extends DaAppAction {

	/**
	 * 季报信息
	 */
	private static final long serialVersionUID = -7537374276276098212L;

	private SeasonReportFacadeIface seasonReportFacadeIface;// 季报信息的业务接口

	private CompanyFacadeIface companyFacadeIface;

	private BagFacadeIface bagFacadeIface;

	private NomalDangerFacadeIface nomalDangerFacadeIface;

	private List<DaCompany> companies;// 企业单位集合

	private List<DaCompanyPass> companyPasses;

	private List<DaBag> bags; // 打包企业集合

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	private List<DaSeasonReport> seasonReports;// 季报集合

	private List<DaSeasonReportDetail> daSeasonReportDetails;//

	private List<DaNomalDanger> daNomalDangers;

	private List<DaNomalDanger> daNomalDangers1;

	private List<DaNomalDanger> nomalDangers;

	private DaNomalDanger daNomalDanger;

	private DaCompany company;// 企业单位

	private DaBag bag;// 打包企业

	private DaSeasonReport seasonReport;// 季报

	private Pagination pagination;// 分页对象

	private String ids;// 季报序号的集合，针对类似于批量删除操作

	private int[] compIds = null; // 企业序号集合。

	private String nomalDangerIds;

	private String seasonReportType;

	private int year = 0; // 查询季报By年份

	private int seasonNumber; // 查询季报By季数

	private String isSee; // 是否查看

	private int companyOrBag; // 判断非打包企业或是打包企业

	private int isQuarter;

	private int otherTradeDanger = 0;

	private int repair; // 加载一般隐患列表时判断是否治理

	private int normalDangerType; // 加载一般隐患列表时隐患类别的条件

	private String hazardSourceCode;// 隐患来源编码

	private Integer secondType = 0;// 二级隐患分类

	private Integer type = 0;
	// add by huangjl 2014-1-8
	private List<FkEnum> hazardSourceEnums; // 隐患来源枚举类型
	
	private List<DaIndustryParameter> parentDaIndustryParameters; // 隐患类别大类
	
	private List<DaIndustryParameter> childDaIndustryParameters; // 隐患类别子类
	
	private int nowYhCount;  //当月一般隐患条数

	private SyncTriggerService syncTriggerService;
	private SystemConstant systemConstant;

	/**
	 * 部门一般隐患未治理列表
	 * 
	 * @return
	 */
	/**
	 * public String loadSeasonReportsUnGorver() { if (pagination == null) {
	 * pagination = new Pagination(); pagination.setPageSize(10); } try {
	 * if(daNomalDanger == null){ daNomalDanger = new DaNomalDanger(); Calendar
	 * cal = Calendar.getInstance();
	 * daNomalDanger.setNowYear(cal.get(Calendar.YEAR)); }
	 * daNomalDanger.setRepair(0); tradeTypes =
	 * seasonReportFacadeIface.loadTradeTypesForSeasonReport(); companies =
	 * nomalDangerFacadeIface.loadCompanysByIsRepair(company, daNomalDanger,
	 * getUserDetail(), pagination); } catch (ApplicationAccessException e) {
	 * e.printStackTrace(); return ERROR; } return SUCCESS; }
	 */

	public String loadSeasonReportsUnGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (daNomalDanger == null) {
				daNomalDanger = new DaNomalDanger();
				Calendar cal = Calendar.getInstance();
				daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			daNomalDanger.setRepair(0);
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = nomalDangerFacadeIface.loadCompanysByIsRepairNew(company, daNomalDanger, getUserDetail(), pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 部门一般隐患已治理列表
	 * 
	 * @return
	 */
	// public String loadSeasonReportsGorver() {
	// if (pagination == null) {
	// pagination = new Pagination();
	// pagination.setPageSize(10);
	// }
	// try {
	// if(daNomalDanger == null){
	// daNomalDanger = new DaNomalDanger();
	// Calendar cal = Calendar.getInstance();
	// daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
	// }
	// daNomalDanger.setRepair(1);
	// tradeTypes = seasonReportFacadeIface.loadTradeTypesForSeasonReport();
	// companies = nomalDangerFacadeIface.loadCompanysByIsRepair(company,
	// daNomalDanger, getUserDetail(), pagination);
	// } catch (ApplicationAccessException e) {
	// e.printStackTrace();
	// return ERROR;
	// }
	// return SUCCESS;
	// }

	public String loadSeasonReportsGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (daNomalDanger == null) {
				daNomalDanger = new DaNomalDanger();
				Calendar cal = Calendar.getInstance();
				daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			daNomalDanger.setRepair(1);
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			companies = nomalDangerFacadeIface.loadCompanysByIsRepairNew(company, daNomalDanger, getUserDetail(), pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载已确认企业集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadCompanies() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			if (companyOrBag == 1) {
				companies = seasonReportFacadeIface.companiseAffirmToSeasonIsExist(company, pagination, getUserDetail());
				return "loadCompanys";
			} else if (companyOrBag == 2) {
				bags = seasonReportFacadeIface.bagsToSeasonIsExist(bag, pagination, getYear(), seasonNumber, getUserDetail(), isQuarter);
				return "loadBags";
			}

		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e2) {
			e2.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadInputFrame() {
		try {
			if (companyOrBag == 1) {
				seasonReport = seasonReportFacadeIface.loadSeasonReport(company, getYear(), seasonNumber);
			} else if (companyOrBag == 2) {
				seasonReport = seasonReportFacadeIface.loadSeasonReport(bag, getYear(), seasonNumber);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 加载创建季报信息的页面
	 * 
	 * @return
	 */
	public String createSeasonReportInit() {
		try {
			tradeTypes = seasonReportFacadeIface.loadTradeTypesForSeasonReport();
			if (company != null) {
				company = companyFacadeIface.loadCompany(company);
				seasonReport = seasonReportFacadeIface.loadSeasonReport(company, getYear(), seasonNumber);
			}
			if (bag != null) {
				bag = bagFacadeIface.loadBag(bag);
				seasonReport = seasonReportFacadeIface.loadSeasonReport(bag, getYear(), seasonNumber);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if ("1".equals(seasonReportType))
			return "seasonReportFine";
		else if ("2".equals(seasonReportType))
			return "seasonReportWide";
		return SUCCESS;
	}

	/**
	 * 创建季报信息
	 * 
	 * @return
	 */
	public String createSeasonReport() {
		try {
			seasonReport.setUserId(getUserId());
			if (company != null) {
				seasonReport.setCompanyPassId(company.getId());
			}
			if (bag != null) {
				seasonReport.setBagId(bag.getId());
			}
			if (seasonReport.getSeasonReportDetails() != null && seasonReport.getSeasonReportDetails().size() > 0) {
				seasonReportFacadeIface.createSeasonReport(seasonReport, seasonReport.getSeasonReportDetails());
			} else {
				seasonReportFacadeIface.createSeasonReport(seasonReport);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载一个季报信息的内容，或用于显示或预备修改
	 * 
	 * @return
	 */
	public String loadSeasonReport() {
		try {
			if (seasonReport == null) {
				return INPUT;
			}
			seasonReport = seasonReportFacadeIface.loadSeasonReport(seasonReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 修改季报信息
	 * 
	 * @return
	 */
	public String updateSeasonReport() {
		try {
			seasonReport.setUserId(getUserId());
			if (company != null) {
				seasonReport.setCompanyPassId(company.getId());
			}
			if (bag != null) {
				seasonReport.setBagId(bag.getId());
			}
			seasonReportFacadeIface.updateSeasonReport(seasonReport);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 删除季报信息
	 * 
	 * @return
	 */
	public String deleteSeasonReport() {
		try {
			seasonReportFacadeIface.deleteSeasonReport(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载季报信息集合，或搜索或全部显示
	 * 
	 * @return
	 */
	public String loadSeasonReports() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(20);
		}
		try {
			seasonReports = seasonReportFacadeIface.loadSeasonReports(seasonReport, getUserDetail(), pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 企业一般隐患未治理列表
	 * 
	 * @return
	 */
	public String loadNomalDangersUnGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			if (daNomalDanger == null) {
				daNomalDanger = new DaNomalDanger();
				Calendar cal = Calendar.getInstance();
				daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			daNomalDanger.setRepair(0);
			company = companyFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。

			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;

			}

			daNomalDangers = nomalDangerFacadeIface.loadNomalDangers(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);
			if (daNomalDangers != null && daNomalDangers.size() > 0) {
				daNomalDanger = daNomalDangers.get(0);
			}
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
			daNomalDanger.setCurrentTime(time.format(new Date()).toString());
			// 获得隐患来源枚举类型
			hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
			// 获得隐患分类大类
			parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
			// 获得隐患分类子类
			childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private int getYearMonth() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		return Integer.valueOf(year + month);
	}

	/**
	 * 企业一般隐患已治理列表
	 * 
	 * @return
	 */
	public String loadNomalDangersGorver() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			if (daNomalDanger == null) {
				daNomalDanger = new DaNomalDanger();
				Calendar cal = Calendar.getInstance();
				daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			daNomalDanger.setRepair(1);
			company = companyFacadeIface.loadCompany(company);
			// add by huangjl 企业没有修改信息的话，跳转到提示页面。
			String returnURL = this.check(company);
			if (returnURL != null) {
				return returnURL;
			}

			daNomalDangers = nomalDangerFacadeIface.loadNomalDangers(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);
			if (daNomalDangers != null && daNomalDangers.size() > 0)
				daNomalDanger = daNomalDangers.get(0);

			// 获得隐患来源枚举类型
			hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
			// 获得隐患分类大类
			parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
			// 获得隐患分类子类
			childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 企业、部门一般隐患添加列表
	 * 
	 * @return
	 */
	// public String loadNomalDangers(){
	// if(pagination==null){
	// pagination=new Pagination();
	// pagination.setPageSize(10);
	// }
	// try {
	// if(company == null){
	// company = new DaCompany();
	// company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
	// }
	// if(daNomalDanger == null){
	// daNomalDanger = new DaNomalDanger();
	// Calendar cal = Calendar.getInstance();
	// daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
	// }
	// daNomalDanger.setRepair(2);
	// company = companyFacadeIface.loadCompany(company);
	// daNomalDangers=nomalDangerFacadeIface.loadNomalDangers(company,
	// getUserDetail(), pagination,daNomalDanger,otherTradeDanger);
	// } catch (ApplicationAccessException e) {
	// e.printStackTrace();
	// }
	// return SUCCESS;
	// }

	public String loadNomalDangers() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (company == null) {
				company = new DaCompany();
				company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
			}
			if (daNomalDanger == null) {
				daNomalDanger = new DaNomalDanger();
				Calendar cal = Calendar.getInstance();
				daNomalDanger.setNowYear(cal.get(Calendar.YEAR));
			}
			daNomalDanger.setRepair(2);
			company = companyFacadeIface.loadCompany(company);

			// add by huangjl 企业没有修改信息的话，跳转到提示页面。
			String returnURL = this.check(company);
//			System.out.println("returnURL: " + returnURL);
			if (returnURL != null) {
				return returnURL;
			}

			// if (company.getSecondArea()==null){
			// company.setSecondArea(0l);
			// }

			if(daNomalDanger!=null&&"1".equals(daNomalDanger.getJbFlag())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if (daNomalDanger.getJbYear() == 0) {
					Calendar cal = Calendar.getInstance();
					String mDateTime = formatter.format(cal.getTime());
					daNomalDanger.setJbYear(cal.get(Calendar.YEAR));
					if (mDateTime.compareTo(daNomalDanger.getJbYear() + "-01-01") >= 0 && mDateTime.compareTo(daNomalDanger.getJbYear() + "-04-01") < 0) {
						daNomalDanger.setJbQuarter(1);
					} else if (mDateTime.compareTo(daNomalDanger.getJbYear() + "-04-01") >= 0 && mDateTime.compareTo(daNomalDanger.getJbYear() + "-07-01") < 0) {
						daNomalDanger.setJbQuarter(2);
					} else if (mDateTime.compareTo(daNomalDanger.getJbYear() + "-07-01") >= 0 && mDateTime.compareTo(daNomalDanger.getJbYear() + "-10-01") < 0) {
						daNomalDanger.setJbQuarter(3);
					} else if (mDateTime.compareTo(daNomalDanger.getJbYear() + "-10-01") >= 0 && mDateTime.compareTo(daNomalDanger.getJbYear() + "-12-31") <= 0) {
						daNomalDanger.setJbQuarter(4);
					}
				}
				
				// 按明细(全部)
				daNomalDangers = nomalDangerFacadeIface.loadNomalDangers(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);

				// 按明细(当月隐患数)
				//nowYhCount = nomalDangerFacadeIface.loadnowYhCount(company, getUserDetail());

				// 按个数
				//daNomalDangers1 = nomalDangerFacadeIface.loadNomalDangers1(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);
			}else{
				// 按明细(全部)
				daNomalDangers = nomalDangerFacadeIface.loadNomalDangers(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);

				// 按明细(当月隐患数)
				nowYhCount = nomalDangerFacadeIface.loadnowYhCount(company, getUserDetail());

				// 按个数
				daNomalDangers1 = nomalDangerFacadeIface.loadNomalDangers1(company, getUserDetail(), pagination, daNomalDanger, otherTradeDanger);
			}
			

			if (daNomalDangers != null && daNomalDangers.size() > 0)
				daNomalDanger = daNomalDangers.get(0);
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
			daNomalDanger.setCurrentTime(time.format(new Date()).toString());

			// 获得隐患来源枚举类型
			hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
			// 获得隐患分类大类
			parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
			// 获得隐患分类子类
			childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String createNomalDangerInit() {

		try {
			tradeTypes = seasonReportFacadeIface.loadTradeTypesForSeasonReport();
			if (company != null)
				company = companyFacadeIface.loadCompany(company);
			else if (bag != null)
				bag = bagFacadeIface.loadBag(bag);
			if (daNomalDanger != null)
				daNomalDanger = nomalDangerFacadeIface.loadNomalDanger(daNomalDanger);
			else {
				daNomalDanger = nomalDangerFacadeIface.loadLinkManByBefore(company);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * public String createNomalDanger(){ try { if(company!=null){
	 * nomalDangerFacadeIface
	 * .createNomalDanger(daNomalDanger,company,getUserDetail()); return
	 * "byCompany"; } else if(bag!=null){
	 * nomalDangerFacadeIface.createNomalDanger
	 * (daNomalDanger,bag,getUserDetail()); return "byBag"; } } catch
	 * (ApplicationAccessException e) { e.printStackTrace(); return ERROR; }
	 * return SUCCESS; }
	 */
	public String createNomalDanger() {
//		System.out.println("保存: " + daNomalDangers.size());
		try {
			if (company != null && daNomalDangers != null) {
				// System.out.println("===daNomalDanger："+daNomalDanger);
				// System.out.println("===getIsSynchro："+daNomalDanger.getIsSynchro());

//				System.out.println("录入方式: " + daNomalDanger.getInputType());

				int yhcount = daNomalDangers.get(0).getYhcount();

//				System.out.println("个数: " + yhcount);

				if (daNomalDanger.getInputType() == 2 && daNomalDangers.get(0).getYhcount() > 0) { // 按个数

					nomalDangerFacadeIface.createCountNomalDanger(daNomalDanger, company, getUserDetail(), yhcount);

				} else { // 按明细

					nomalDangerFacadeIface.createMoreNomalDanger(daNomalDanger, company, getUserDetail(), daNomalDangers);
				}
				if(systemConstant.isDataSeparation()){
					if(systemConstant.isEnterprise()){
						syncTriggerService.updateNomalDangerEs();
					}else if(systemConstant.isGovernment()){
						syncTriggerService.updateNomalDangerGov();
					}
				}
				
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		if ("qiye".equals(getUserDetail().getUserIndustry())  &&  daNomalDanger.getInputType()==1 )
			return "byQiye1";
		else  if ("qiye".equals(getUserDetail().getUserIndustry())  &&  daNomalDanger.getInputType()==2 )
			return "byQiye2";
		else
			return "byCompany";
	}

	public String updateNomalDanger() {
		if (daNomalDanger.getUserId()!=null && (daNomalDanger.getUserId()) != 430371l && (getUserDetail().getUserId()) == 430371l) {
			daNomalDanger.setUserId(daNomalDanger.getUserId());
		} else {
			daNomalDanger.setUserId((long) getUserDetail().getUserId());
		}

		daNomalDanger.setDanger(true);
		try {
			if (company != null) {
				// if(daNomalDanger.getType() == null || daNomalDanger.getType()
				// == 0)
				// daNomalDanger.setType(Nbyhpc.NOMAL_DANGER_TYPE);

				daNomalDanger.setCompanyPassId(company.getId());
				daNomalDanger.setDanger(true);
				nomalDangerFacadeIface.updateNomalDanger(daNomalDanger);

			}
			// else if(bag!=null){
			// daNomalDanger.setBagId(bag.getId());
			// nomalDangerFacadeIface.updateNomalDanger(daNomalDanger);
			// return "byBag";
			// }
			if(systemConstant.isDataSeparation()){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateNomalDangerEs();
				}else if(systemConstant.isGovernment()){
					syncTriggerService.updateNomalDangerGov();
				}
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "byCompany";
	}

	public String deleteNomalDanger() {
		try {
			nomalDangerFacadeIface.deleteNormalDanger(nomalDangerIds);
			return "byCompany";
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String deleteNomalDanger1() {
		try {
			nomalDangerFacadeIface.deleteNormalDanger1(daNomalDanger);
			return "byCompany";
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public void setSeasonReportFacadeIface(SeasonReportFacadeIface seasonReportFacadeIface) {
		this.seasonReportFacadeIface = seasonReportFacadeIface;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaSeasonReport getSeasonReport() {
		return seasonReport;
	}

	public void setSeasonReport(DaSeasonReport seasonReport) {
		this.seasonReport = seasonReport;
	}

	public List<DaSeasonReport> getSeasonReports() {
		return seasonReports;
	}

	public void setSeasonReports(List<DaSeasonReport> seasonReports) {
		this.seasonReports = seasonReports;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<DaCompany> getCompanies() {
		return companies;
	}

	public void setCompanies(List<DaCompany> companies) {
		this.companies = companies;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public int getYear() {
		if (year == 0) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			year = cal.get(Calendar.YEAR);
		}
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public List<DaSeasonReportDetail> getDaSeasonReportDetails() {
		return daSeasonReportDetails;
	}

	public void setDaSeasonReportDetails(List<DaSeasonReportDetail> daSeasonReportDetails) {
		this.daSeasonReportDetails = daSeasonReportDetails;
	}

	public String getSeasonReportType() {
		return seasonReportType;
	}

	public void setSeasonReportType(String seasonReportType) {
		this.seasonReportType = seasonReportType;
	}

	public boolean isOneSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		Date startTime = cal.getTime();
		cal.set(Calendar.MONTH, 4);
		Date endTime = cal.getTime();
		Date now = new Date();
		if (getYear() != year)
			return false;
		else if (now.getTime() >= startTime.getTime() && now.getTime() < endTime.getTime()) {
			return true;
		} else
			return false;
	}

	public boolean isTwoSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.MONTH, 3);
		cal.set(Calendar.DATE, 1);
		Date startTime = cal.getTime();
		cal.set(Calendar.MONTH, 7);
		Date endTime = cal.getTime();
		Date now = new Date();
		if (getYear() != year)
			return false;
		else if (now.getTime() >= startTime.getTime() && now.getTime() < endTime.getTime()) {
			return true;
		} else
			return false;
	}

	public boolean isThreeSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DATE, 1);
		Date startTime = cal.getTime();
		cal.set(Calendar.MONTH, 10);
		Date endTime = cal.getTime();
		Date now = new Date();
		if (getYear() != year)
			return false;
		else if (now.getTime() >= startTime.getTime() && now.getTime() < endTime.getTime()) {
			return true;
		} else
			return false;
	}

	public boolean isFourSeason() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DATE, 1);
		Date startTime = cal.getTime();
		cal.add(Calendar.YEAR, 1);
		cal.set(Calendar.MONTH, 1);
		Date endTime = cal.getTime();
		Date now = new Date();
		if (getYear() != year)
			return false;
		else if (now.getTime() >= startTime.getTime() && now.getTime() < endTime.getTime()) {
			return true;
		} else
			return false;
	}

	public String getIsSee() {
		return isSee;
	}

	public void setIsSee(String isSee) {
		this.isSee = isSee;
	}

	public List<DaBag> getBags() {
		return bags;
	}

	public void setBags(List<DaBag> bags) {
		this.bags = bags;
	}

	public DaBag getBag() {
		return bag;
	}

	public void setBag(DaBag bag) {
		this.bag = bag;
	}

	public int getCompanyOrBag() {
		return companyOrBag;
	}

	public void setCompanyOrBag(int companyOrBag) {
		this.companyOrBag = companyOrBag;
	}

	public void setBagFacadeIface(BagFacadeIface bagFacadeIface) {
		this.bagFacadeIface = bagFacadeIface;
	}

	public int getIsQuarter() {
		return isQuarter;
	}

	public void setIsQuarter(int isQuarter) {
		this.isQuarter = isQuarter;
	}

	public int[] getCompIds() {
		return compIds;
	}

	public void setCompIds(int[] compIds) {
		this.compIds = compIds;
	}

	public void setNomalDangerFacadeIface(NomalDangerFacadeIface nomalDangerFacadeIface) {
		this.nomalDangerFacadeIface = nomalDangerFacadeIface;
	}

	public List<DaNomalDanger> getDaNomalDangers() {
		return daNomalDangers;
	}

	public void setDaNomalDangers(List<DaNomalDanger> daNomalDangers) {
		this.daNomalDangers = daNomalDangers;
	}

	public DaNomalDanger getDaNomalDanger() {
		return daNomalDanger;
	}

	public void setDaNomalDanger(DaNomalDanger daNomalDanger) {
		this.daNomalDanger = daNomalDanger;
	}

	public String getNomalDangerIds() {
		return nomalDangerIds;
	}

	public void setNomalDangerIds(String nomalDangerIds) {
		this.nomalDangerIds = nomalDangerIds;
	}

	public int getOtherTradeDanger() {
		return otherTradeDanger;
	}

	public void setOtherTradeDanger(int otherTradeDanger) {
		this.otherTradeDanger = otherTradeDanger;
	}

	public int getRepair() {
		return repair;
	}

	public void setRepair(int repair) {
		this.repair = repair;
	}

	public int getNormalDangerType() {
		return normalDangerType;
	}

	public void setNormalDangerType(int normalDangerType) {
		this.normalDangerType = normalDangerType;
	}

	public List<DaCompanyPass> getCompanyPasses() {
		return companyPasses;
	}

	public void setCompanyPasses(List<DaCompanyPass> companyPasses) {
		this.companyPasses = companyPasses;
	}

	public List<DaNomalDanger> getNomalDangers() {
		return nomalDangers;
	}

	public void setNomalDangers(List<DaNomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
	}

	public String getHazardSourceCode() {
		return hazardSourceCode;
	}

	public void setHazardSourceCode(String hazardSourceCode) {
		this.hazardSourceCode = hazardSourceCode;
	}

	public Integer getSecondType() {
		return secondType;
	}

	public void setSecondType(Integer secondType) {
		this.secondType = secondType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<FkEnum> getHazardSourceEnums() {
		return hazardSourceEnums;
	}

	public void setHazardSourceEnums(List<FkEnum> hazardSourceEnums) {
		this.hazardSourceEnums = hazardSourceEnums;
	}

	public List<DaIndustryParameter> getParentDaIndustryParameters() {
		return parentDaIndustryParameters;
	}

	public void setParentDaIndustryParameters(List<DaIndustryParameter> parentDaIndustryParameters) {
		this.parentDaIndustryParameters = parentDaIndustryParameters;
	}

	public List<DaIndustryParameter> getChildDaIndustryParameters() {
		return childDaIndustryParameters;
	}

	public void setChildDaIndustryParameters(List<DaIndustryParameter> childDaIndustryParameters) {
		this.childDaIndustryParameters = childDaIndustryParameters;
	}

	public List<DaNomalDanger> getDaNomalDangers1() {
		return daNomalDangers1;
	}

	public void setDaNomalDangers1(List<DaNomalDanger> daNomalDangers1) {
		this.daNomalDangers1 = daNomalDangers1;
	}

	public int getNowYhCount() {
		return nowYhCount;
	}

	public void setNowYhCount(int nowYhCount) {
		this.nowYhCount = nowYhCount;
	}

	public void setSyncTriggerService(SyncTriggerService syncTriggerService) {
		this.syncTriggerService = syncTriggerService;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}


}
