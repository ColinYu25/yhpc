package com.safetys.nbyhpc.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;

import com.bjsxt.hibernate.MemCached;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.TCharts;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.CompanyQuarterReportFacadeIface;
import com.safetys.nbyhpc.facade.iface.DangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.DangerGorverFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.facade.iface.WhCompanyInfoFacadeIface;
import com.safetys.nbyhpc.util.ConnectionFactory;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

public class StatisticAction extends DaAppAction {

	/**
	 * 统计报表
	 */
	private static final long serialVersionUID = -314744610392228865L;

	private StatisticFacadeIface statisticFacadeIface;

	private WhCompanyInfoFacadeIface companyInfoFacadeIface;

	private CompanyFacadeIface companyFacadeIface;

	private List<Statistic> statistics;

	private List<Statistic> statisticsForPaicha;

	private List<Statistic> statisticsForDanger;

	private List<DaDept> statisticUpAndDownDepts;// 抄告、接收隐患集合

	private List result;

	private FkArea areaThird;// 区域对象

	private String index;// 统计表级数

	private List<FkArea> areas;// 区域集合

	private List<FkArea> areasByGroupNum;// 区域集合

	private List<DaCompany> companies;// 企业单位集合

	private List<DaNomalDanger> nomalDangers;

	private List<DaDanger> dangers;

	private List<DaItem> items;

	private List<DaItemDanger> itemDangers;

	private List<DaNomalDanger> daNomalDangers;

	private List<DaRollcallCompany> rollcallCompanies;

	private List<DaDanger> daDangers;

	private List<DaIndustryParameter> industryParameters;

	private DaDanger danger;

	private DaNomalDanger daNomalDanger;

	private Statistic statistic;

	private Statistic statisticForCompany;

	private DaCompany company;// 企业单位

	private int current_quarter;// 当前季度

	private FkArea area;// 区域对象

	private List<DaDangerGorver> dangerGorvers;// 重大隐患整改集合

	private DaDangerGorver dangerGorver;// 重大隐患整改

	private CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface;

	private NomalDangerFacadeIface nomalDangerFacadeIface;

	private UserFacadeIface userFacadeIface;

	private DangerFacadeIface dangerFacadeIface;

	private DangerGorverFacadeIface dangerGorverFacadeIface;

	private DaCompanyQuarterReprot companyQuarterReprot;

	private List<DaCompanyQuarterReprot> companyQuarterReprots;

	private Pagination pagination;// 分页对象

	private String remark;

	private String reportDate;

	private List<DaIndustryParameter> dpList;

	private List<List<Integer>> dpMap;

	private String type1;

	private int refresh; // 是否有刷新按钮

	private String refreshDate; // 刷新时间
	private String areaLevel;
	private static ConnectionFactory cFactory = new ConnectionFactory();
	private TCompany corecompany;// 中心库企业单位

	private List<DaIndustryParameter> tradeTypes;// 行业集合

	// 是否是二级统计
	private String secondStatistic = "1";

	private TCharts chart; // 隐患分类统计图

	private String fileName = "";

	private InputStream inputStream = null;
	private String uuid;
	private Long companyId=0l; 
	
	
	//报表类型。1：省局发送报表第一张。2：省局发送报表第二张
	private String sType;
	//缓存名称
	private String catchName;
	//发送月份
	private Integer sendMonth;
	/**
	 * 读取已上报季报列表
	 * 
	 * @author lisl
	 * @return
	 * @throws Exception
	 */
	public String findCompanyQuarterReport() throws Exception {
		if (company == null) {
			company = new DaCompany();
			company.setId(nomalDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
		}
		if (companyQuarterReprot == null) {
			companyQuarterReprot = new DaCompanyQuarterReprot();
			Calendar cal = Calendar.getInstance();
			companyQuarterReprot.setYear(cal.get(Calendar.YEAR));
		}
		try {
			companyQuarterReprots = companyQuarterReportFacadeIface.find(companyQuarterReprot, company, null);
		} catch (Exception e) {
			// //e.printStackTrace();
			LOG.error("读取已上报季报列表出错！", e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanyLevelPoint() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadCompanyLevelPoint(area);
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangerOfStatistic() {
		try {
			if (danger == null) {
				danger = new DaDanger();
			}
			danger.setId(statistic.getCompanyId());
			industryParameters = statisticFacadeIface.loadIndustrysForDanger();
			danger = statisticFacadeIface.loadDangerOfStatistic(danger);
			company = statisticFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangerByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			dangers = statisticFacadeIface.loadDangerByIndustryList(statistic, pagination, area);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangerByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadDangerByIndustry(area, statistic);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				// super.setUrl("loadDangerByIndustry.xhtml");
				return MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadNomalDangerByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		if (statistic == null) {
			statistic = new Statistic();
		}
		try {
			companies = statisticFacadeIface.loadNomalDangerByIndustryList(area, statistic, pagination);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadNomalDangersOfStatistic() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		if (statistic == null) {
			statistic = new Statistic();
		}
		try {
			if (company == null) {
				company = new DaCompany();
			}
			company.setId(statistic.getCompanyId());
			company = statisticFacadeIface.loadCompany(company);
			daNomalDangers = statisticFacadeIface.loadNomalDangersOfStatistic(statistic, pagination);
			if (daNomalDangers != null && daNomalDangers.size() > 0)
				daNomalDanger = daNomalDangers.get(0);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
		}
		return SUCCESS;
	}

	public String loadNomalDangerByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadNomalDangerByIndustry(area, statistic);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				// super.setUrl("loadDangerByIndustry.xhtml");
				return super.MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 隐患抄告汇总表
	 */
	public String loadTroubleUpAndDownByUser() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			if (index == null)
				index = "2";
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			if (index.equals("2")) {
				statistics = statisticFacadeIface.loadTroubleUpAndDownByUser(area, statistic);
				// 把宁波市也加入到统计表中
				FkArea tmpArea = new FkArea();
				tmpArea = statisticFacadeIface.loadArea(Nbyhpc.AREA_CODE);
				areas.add(tmpArea);
			}
			if (index.equals("3")) {
				statistics = statisticFacadeIface.loadTroubleUpAndDownByUserThird(area, statistic);
			}

		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = null;
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = null;
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoBackByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = null;
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoBackByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = null;
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoBackByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoResultByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoResultByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleDownNoResultByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("1");
			dept.setTroubleCopyType(1);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleDownNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoBackByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoBackByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoBackByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}

			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("4");
			dept.setTroubleCopyType(4);
			dept.setBack(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoBackByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoResultByUser() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("5");
			dept.setTroubleCopyType(5);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			if (areaThird != null && areaThird.getAreaCode() != null) {
				userDetail.setThirdArea(areaThird.getAreaCode());
			} else {
				// if(areaThird != null && areaThird != null &&
				// !areaThird.getAreaCode().equals(""))
				userDetail.setThirdArea(0L);
			}
			if (area != null && area.getAreaCode() != null) {

				userDetail.setSecondArea(area.getAreaCode());
			} else {
				// if(area != null && area.getAreaCode() != null && !
				// area.getAreaCode().equals(""))
				userDetail.setSecondArea(0L);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoResultByUserTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("5");
			dept.setTroubleCopyType(5);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleUpNoResultByUserThirdTotal() {
		try {
			if (pagination == null) {
				pagination = new Pagination();
				pagination.setPageSize(10);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
			}
			DaDept dept = new DaDept();
			dept.setType("5");
			dept.setTroubleCopyType(5);
			dept.setResult(false);

			UserDetailWrapper userDetail = getUserDetail();
			userDetail.setSecondArea(null);
			userDetail.setThirdArea(null);
			userDetail.setFirstArea(Nbyhpc.AREA_CODE);
			userDetail.setSecondArea(area.getAreaCode());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statisticUpAndDownDepts = statisticFacadeIface.loadTroubleUpNoResultByUser(statistic, dept, pagination, userDetail);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByCompany() {
		try {
			
			if (company == null) {
				company = new DaCompany();
			}else{
				if(company.getId()!=null&&company.getId()>0){
					company=companyFacadeIface.loadCompany(company);
				}
			}
			
			
			if (uuid!=null &&  !uuid.equals("")){
				//company.setUuid(uuid);
				//由于company的uuid 现在一个企业存在多个，导致查询的企业不是想要的企业。所以此处要通过userId进行查询
				Long userId=null;
			    try {
					userId=this.getUserId();
				} catch (Exception e) {
					//此处为了兼容档案系统不让异常抛出，不然档案调用会失败的
//		            System.out.println("不存在用户信息"); 
				}
				if(userId!=null&&userId>0){
					company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
					//如果是政府用户的话，此处compangy为空，要判断
					if(company==null){
						company = new DaCompany();
						company.setUuid(uuid);
					}
				}else{
					company.setUuid(uuid);
				}
			}else if (companyId!=0l){
				company.setId(companyId);
				company=companyFacadeIface.loadCompany(company);
			}else{
                if(company!=null&&company.getId()!=null&&company.getId()>0){
					
				}else{
					company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
				}
				// add by huangjl 企业没有修改信息的话，跳转到提示页面。
				String returnURL = this.check(company);
				if (returnURL != null) {
					return returnURL;
				}
			}
			
			
			if (statistic == null) {
				statistic = new Statistic();
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistic.setIsAllYear(0);
			statisticForCompany = statisticFacadeIface.loadReportByCompany(company, statistic);
			if (statisticForCompany != null) {
				// company.setCompanyName(statisticForCompany.getCompanyName());
				// companies =
				// companyFacadeIface.loadCompanyByCompanyName(company,pagination);
				company.setId(statisticForCompany.getCompanyId());
				company = companyFacadeIface.loadCompany(company);
				// if (companies != null && companies.size() > 0) {
				// company = companies.get(0);
				// }
			}

			reportDate = formatter.format(new Date());

		} catch (Exception e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByCompanyAll() {
		try {
			if (company == null) {
				company = new DaCompany();
			}else{
				if(company.getId()!=null&&company.getId()>0){
					company=companyFacadeIface.loadCompany(company);
				}
			}
			if (uuid!=null &&  !uuid.equals("")){
				//company.setUuid(uuid);
				//由于company的uuid 现在一个企业存在多个，导致查询的企业不是想要的企业。所以此处要通过userId进行查询
				Long userId=null;
			    try {
					userId=this.getUserId();
				} catch (Exception e) {
					//此处为了兼容档案系统不让异常抛出，不然档案调用会失败的
//		            System.out.println("不存在用户信息"); 
				}
				if(userId!=null){
					company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
					//如果是政府用户的话，此处compangy为空，要判断
					if(company==null){
						company = new DaCompany();
						company.setUuid(uuid);
					}
				}else{
					company.setUuid(uuid);
				}
			}else if (companyId!=0l){
				company.setId(companyId);
				company=companyFacadeIface.loadCompany(company);
			}else{
				if(company!=null&&company.getId()!=null&&company.getId()>0){
					
				}else{
					company = companyInfoFacadeIface.loadCompanyByUserId(this.getUserId());
				}
				
				
			}
			
			
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistic.setIsAllYear(1);
			statisticForCompany = statisticFacadeIface.loadReportByCompany(company, statistic);
			if (statisticForCompany != null) {
				// company.setCompanyName(statisticForCompany.getCompanyName());
				// companies =
				// companyFacadeIface.loadCompanyByCompanyName(company,pagination);
				company.setId(statisticForCompany.getCompanyId());
				company = companyFacadeIface.loadCompany(company);
				// if (companies != null && companies.size() > 0) {
				// company = companies.get(0);
				// }
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			reportDate = formatter.format(new Date());
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByOther() {
		try {
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			statistic.setAreaCode(getUserDetail().getSecondArea());
			statistic.setRemark(getUserDetail().getUserIndustry());
			statistics = statisticFacadeIface.loadStatisticsOfSeasonReportOther(statistic, current_quarter);
			if (statistic.getYear() == nowY && current_quarter == statistic.getQuarter()) {// 是否最新年份
				refresh = 1;
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getAreaCode() + "" + statistic.getRemark() + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByAj() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			statistic.setRemark("anjian_whp");
			statistic.setRemark2("anjian_fire");
			//添加非煤礦山
			statistic.setRemark3("anjian_mine");
			statistic.setIsAllYear(0);

			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter);
				statistic.setRemark("anjian_other");
				statistic.setRemark2("anjian_machine");
				//去掉非煤礦山
				statistic.setRemark3(null);
				//设置父节点id，只查安监下面的类
				statistic.setFatherId(1389L);
				statisticsForPaicha = statisticFacadeIface.loadReportByAjOther(area, statistic, current_quarter);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return super.MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByAjAll() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			statistic.setRemark("anjian_whp");
			statistic.setRemark2("anjian_fire");
			statistic.setIsAllYear(1);
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);

//			System.out.println("area.getAreaCode(): " + area.getAreaCode());
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter);
				statistic.setRemark("anjian_other");
				statistic.setRemark2("anjian_machine");
				statistic.setRemark3("anjian_mine");
				statisticsForPaicha = statisticFacadeIface.loadReportByAjOther(area, statistic, current_quarter);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return super.MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByItemAndInd() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}

			if ("jiaotong".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.TRAFFIC_PROJECT);
			} else if ("chengguan".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.CITY_PROJECT);
			} else if ("shuili".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.WATER_PROJECT);
			}

			statistic.setRemark(getUserDetail().getUserIndustry());
			statistic.setIsAllYear(0);
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter);
				statisticsForPaicha = statisticFacadeIface.loadReportByItem(area, statistic);
				statisticsForDanger = statisticFacadeIface.loadReportByItemTrouble(area, statistic);
				if ("shuili".equals(getUserDetail().getUserIndustry())) {
					areasByGroupNum = statisticFacadeIface.loadAreas(area.getAreaCode());
				} else {
					areasByGroupNum = statisticFacadeIface.loadAreasByGroupNum(area.getAreaCode());
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return super.MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByItemAndIndAll() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					Long areaCode = getUserDetail().getSecondArea();
					if (330218000000l == areaCode || 330219000000l == areaCode || 330215000000l == areaCode) {
						area.setAreaCode(Nbyhpc.AREA_CODE);
					} else {
						area.setAreaCode(areaCode);
					}
				}
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}

			if ("jiaotong".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.TRAFFIC_PROJECT);
			} else if ("chengguan".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.CITY_PROJECT);
			} else if ("shuili".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.WATER_PROJECT);
			}

			statistic.setRemark(getUserDetail().getUserIndustry());
			statistic.setIsAllYear(1);
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter); // 未改
				statisticsForPaicha = statisticFacadeIface.loadReportByItem(area, statistic);
				statisticsForDanger = statisticFacadeIface.loadReportByItemTrouble(area, statistic);
				if ("shuili".equals(getUserDetail().getUserIndustry())) {
					areasByGroupNum = statisticFacadeIface.loadAreas(area.getAreaCode());
				} else {
					areasByGroupNum = statisticFacadeIface.loadAreasByGroupNum(area.getAreaCode());
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return super.MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByItem() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if ("jianwei".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.CONSTRUCTION_PROJECT);
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistic.setIsAllYear(0);
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistic.setRemark("jianwei");
			statistics = statisticFacadeIface.loadReportByItem(area, statistic);
			statisticsForDanger = statisticFacadeIface.loadReportByItemTrouble(area, statistic);
			List<FkArea> areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum(area.getAreaCode());
			if(area.getAreaCode()==330200000000L){
				if ("jianwei".equals(getUserDetail().getUserIndustry())) {
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByItemAll() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if ("jianwei".equals(getUserDetail().getUserIndustry())) {
				statistic.setType(Nbyhpc.CONSTRUCTION_PROJECT);
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistic.setIsAllYear(1);
			statistic.setRemark("jianwei");
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadReportByItem(area, statistic);
			statisticsForDanger = statisticFacadeIface.loadReportByItemTrouble(area, statistic);
			List<FkArea> areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum(area.getAreaCode());
			if(area.getAreaCode()==330200000000L){
				if ("jianwei".equals(getUserDetail().getUserIndustry())) {
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}
			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadReportByInd() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statistic.setIsAllYear(0);
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter); // 未改
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 未改
	public String loadReportByIndAll() {
		try {
			if (area == null) {
				area = new FkArea();
				if (getUserDetail().getSecondArea() == 0L) {
					area.setAreaCode(Nbyhpc.AREA_CODE);
				} else {
					area.setAreaCode(getUserDetail().getSecondArea());
				}
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			statistic.setRemark(getUserDetail().getUserIndustry());
			statistic.setIsAllYear(1);
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadReportByInd(area, statistic, current_quarter); // 未改
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 累计排查进度统计
	 * 
	 * @return
	 */
	public String loadPaiChaOfCompany() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			// add by huangjl 当statistic为空的时候，默认给一个当前季度的月份。
			// int current_quarter_last_month=0;
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);

			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
				// current_quarter_last_month=3;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
				// current_quarter_last_month=6;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
				// current_quarter_last_month=9;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
				// current_quarter_last_month=12;
			}

			if (statistic == null) {
				statistic = new Statistic();
				statistic.setYear(nowY);
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			} else if (statistic != null && statistic.getYear() == 0) {
				statistic.setYear(nowY);
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}

			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas1_his(area.getAreaCode(), statistic, current_quarter);
			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadPaiChaOfCompany(area, statistic, current_quarter, secondStatistic);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}

			// add by huangjl
			// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
			if (statistic != null && nowY == statistic.getYear() && (statistic.getQuarter() == current_quarter || statistic.getQuarter() == 0) && statistic.getMonth() == 0) {
				statistic.setMonth(cal.get(Calendar.MONTH) + 1);
			}

			if (statistic.getYear() == nowY && statistic.getMonth() == month && current_quarter == statistic.getQuarter()) { // 是否最新月份
				refresh = 1;

				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadPaiChaOfCompany_" + area.getAreaCode() + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	

	/**
	 * 将已经生产缓存的报表记录，补录到数据库中
	 * 
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String saveStatistic() throws SQLException, IOException {
		PreparedStatement pState1 = cFactory.createConnection().prepareStatement("select  id from DA_STATISTIC  where S_TYPE='"+this.sType+"' and IS_DELETED=0  and SENDMONTH=" + this.sendMonth + " ");
		ResultSet rs1 = pState1.executeQuery();
		if (!rs1.next()) {
		    //不存在的情况下，查找缓存，插入数据库
			MemCached cache = MemCached.getInstance();
			Map<String, Object> map = new HashMap<String, Object>();
			map = (Map<String, Object>) cache.get(this.catchName);
//			System.out.println("读取缓存: " + this.catchName);
			if(map!=null){
				ResultSet rs = null;
				Iterator<Entry<String, Object>> it=map.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, Object> entity=it.next();
					String key=entity.getKey();
					Statistic sta=(Statistic) entity.getValue();
					
					String insertSql="insert  into da_statistic " +
							"(ID,SID,ENUM_NAME,COMPANY_NUM,TROUB_NUM,TROUB_CLEAN_NUM,BIG_TROUB_NUM," +
							"BIG_TROUB_CLEAN_NUM,TARGET,GOODS,S_RESOURCE,FINISH_DATE,SAFETY_METHOD," +
							"DDNG5_NUM,PROVICERC_NUM,CITYRC_NUM,ALLCOMPANY_NUM,QTRC_NUM,GOVERN_MONEY," +
							"NORMALGOVERN_MONEY,S_TYPE,SENDMONTH,CATCHNAME,IS_DELETED,CREATE_TIME,MODIFY_TIME)" +
							" values(hibernate_sequence.nextval,'"+key+"','"+sta.getEnumName()+"',"+sta.getCompanyNum()+","+sta.getTroubNum()+","+sta.getTroubCleanNum()+
							","+sta.getBigTroubNum()+","+sta.getBigTroubCleanNum()+","+sta.getTarget()+","+sta.getGoods()+""+
							","+sta.getResource()+","+sta.getFinishDate()+","+sta.getSafetyMethod()+","+sta.getDdng5Num()+"" +
							","+sta.getProviceRcNum()+","+sta.getCityRcNum()+","+sta.getAllCompanyNum()+","+sta.getQtRcNum()+"" +
							","+sta.getGovernMoney()+","+sta.getNormalGovernMoney()+",'"+this.sType+"'" +
							","+this.sendMonth+",'"+this.catchName+"',0,sysdate,sysdate)";
					
					System.err.println("insertSql="+insertSql);
					PreparedStatement pState = cFactory.createConnection().prepareStatement(insertSql);
					rs = pState.executeQuery();
				}
				rs.getStatement().close();
				rs.close();
				cFactory.releaseConnection();
			}
		}
		rs1.close();
		rs1.getStatement().close();
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().print("缓存数据保存到数据库成功!");
		return null;
	}

	public String loadQuarter() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas1_his(area.getAreaCode(), statistic, current_quarter);

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadQuarterOfCompany(area, statistic, current_quarter);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}

			// add by huangjl
			int nowY = cal.get(Calendar.YEAR);
			// 如果当前年份和季度都相等的话，则过滤到当年月的数据;或者年度相等，季度为0，也要过滤掉
			if (statistic != null && nowY == statistic.getYear() && (current_quarter == statistic.getQuarter() || statistic.getQuarter() == 0)) {
				statistic.setMonth(cal.get(Calendar.MONTH) + 1);
			}

			// 是否最新季度或前一季度,如是则加刷新按钮
			if (statistic.getYear() == cal.get(Calendar.YEAR)) {
				if (current_quarter == statistic.getQuarter()) { // 当前季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarter_" + statistic.getRemark() + area.getAreaCode() + "0" + "'";
				} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																			// 前一季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarter_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}

			} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
				refresh = 1;
				sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarter_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
			}
			if (!sql.equals("")) {
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 累计排查进度统计企业列表
	 * 
	 * @return
	 */
	public String loadPaiChaOfCompanyList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				// liulj 加上历史数据判断
				if (statistic == null) {
					statistic = new Statistic();
				}

				Calendar cal = Calendar.getInstance();
				int month = cal.get(Calendar.MONTH) + 1;
				if (month == 1 || month == 2 || month == 3) {
					current_quarter = 1;
				} else if (month == 4 || month == 5 || month == 6) {
					current_quarter = 2;
				} else if (month == 7 || month == 8 || month == 9) {
					current_quarter = 3;
				} else if (month == 10 || month == 11 || month == 12) {
					current_quarter = 4;
				}
				statistic.setYear(company.getYear());
				statistic.setQuarter(company.getQuarter());

				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}

			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}
			statistics = statisticFacadeIface.loadPaiChaOfCompanyList(company, pagination, area, current_quarter, secondStatistic, null);
		} catch (Exception e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 未上报月报企业列表 liulj
	 * 
	 * @return
	 */
	public String loadNotMonthReportList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}
		UserDetailWrapper userDetail = getUserDetail();

		try {

			area = new FkArea();

			if (company != null) {
				if (company.getStaticDate() != null) {

					company.setYear(Integer.parseInt(company.getStaticDate().split("-")[0]));
					company.setMonth(Integer.parseInt(company.getStaticDate().split("-")[1]));

					if (company.getMonth() == 1 || company.getMonth() == 2 || company.getMonth() == 3) {
						company.setQuarter(1);
					} else if (company.getMonth() == 4 || company.getMonth() == 5 || company.getMonth() == 6) {
						company.setQuarter(2);
					} else if (company.getMonth() == 7 || company.getMonth() == 8 || company.getMonth() == 9) {
						company.setQuarter(3);
					} else if (company.getMonth() == 10 || company.getMonth() == 11 || company.getMonth() == 12) {
						company.setQuarter(4);
					}

					if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
						area.setAreaCode(userDetail.getThirdArea());
						company.setThirdArea(userDetail.getThirdArea());
						company.setSecondArea(userDetail.getSecondArea());
					} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
						area.setAreaCode(userDetail.getSecondArea());
						company.setSecondArea(userDetail.getSecondArea());
					}

					if (company.getFouthArea() != null && company.getFouthArea() != 0) {
						area.setAreaCode(company.getFouthArea());
					} else if (company.getThirdArea() != null && company.getThirdArea() != 0) {
						area.setAreaCode(company.getThirdArea());
					} else if (company.getSecondArea() != null && company.getSecondArea() != 0) {
						area.setAreaCode(company.getSecondArea());
					} else {
						area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
					}
				}

			} else {

				company = new DaCompany();
				if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
					area.setAreaCode(userDetail.getThirdArea());
				} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
					area.setAreaCode(userDetail.getSecondArea());
				} else {
					area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
				}
				company.setYear(year);
				company.setMonth(month);
				company.setStaticDate("" + year + "-" + month + "");
				company.setSecondArea(userDetail.getSecondArea());
				company.setThirdArea(userDetail.getThirdArea());
				company.setQuarter(current_quarter);
			}
			company.setCompanyTrade("c");
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				// liulj 加上历史数据判断
				if (statistic == null) {
					statistic = new Statistic();
				}

				statistic.setYear(company.getYear());
				statistic.setQuarter(company.getQuarter());

				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}

			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}

			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			statistics = statisticFacadeIface.loadPaiChaOfCompanyList(company, pagination, area, current_quarter, secondStatistic, getUserDetail());
		} catch (Exception e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 未上报季报企业列表 liulj
	 * 
	 * @return
	 */
	public String loadNotQuarterReportList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}
		UserDetailWrapper userDetail = getUserDetail();
		try {

			area = new FkArea();

			if (company != null) {
				if (company.getYear() != 0l) {

					if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
						area.setAreaCode(userDetail.getThirdArea());
						company.setThirdArea(userDetail.getThirdArea());
						company.setSecondArea(userDetail.getSecondArea());
					} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
						area.setAreaCode(userDetail.getSecondArea());
						company.setSecondArea(userDetail.getSecondArea());
					}
					if (company.getFouthArea() != null && company.getFouthArea() != 0) {
						area.setAreaCode(company.getFouthArea());
					} else if (company.getThirdArea() != null && company.getThirdArea() != 0) {
						area.setAreaCode(company.getThirdArea());
					} else if (company.getSecondArea() != null && company.getSecondArea() != 0) {
						area.setAreaCode(company.getSecondArea());
					} else {
						area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
					}
				}

			} else {

				company = new DaCompany();
				if (company.getFouthArea() != null && company.getFouthArea() != 0) {
					area.setAreaCode(company.getFouthArea());
				} else if (userDetail.getThirdArea() != null && userDetail.getThirdArea() != 0) {
					area.setAreaCode(userDetail.getThirdArea());
				} else if (userDetail.getSecondArea() != null && userDetail.getSecondArea() != 0) {
					area.setAreaCode(userDetail.getSecondArea());
				} else {
					area.setAreaCode(Nbyhpc.AREA_CODE); // 宁波市
				}
				company.setYear(year);
				company.setSecondArea(userDetail.getSecondArea());
				company.setThirdArea(userDetail.getThirdArea());
				company.setQuarter(current_quarter);
			}

			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				// liulj 加上历史数据判断
				if (statistic == null) {
					statistic = new Statistic();
				}
				statistic.setYear(company.getYear());
				statistic.setQuarter(company.getQuarter());

				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}

			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}
			tradeTypes = companyFacadeIface.loadTradeTypesForCompany(getUserDetail());
			if (userDetail.getUserIndustry().equals("anwei") && ((company.getTradeTypes() != null && !"".equals(company.getTradeTypes()) && company.getTradeTypes().split(",")[0].trim().equals("")) || company.getTradeTypes() == null)) {
				company.setCompanyTrade("wq");
				// 安委
				statistics = statisticFacadeIface.loadPaiChaOfCompanyList(company, pagination, area, current_quarter, secondStatistic, null);
			} else {
				company.setCompanyTrade("q");
				statistics = statisticFacadeIface.loadCompanyByIndustryList1(company, pagination, area, current_quarter, getUserDetail());
			}

		} catch (Exception e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询企业一般隐患列表（特定时间段里） lisl
	 * 
	 * @return
	 */
	public String loadNomalDangers() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea(area.getAreaCode());
			}
			daNomalDangers = statisticFacadeIface.loadNomalDangers(company, pagination, "");
			int year = company.getYear();
			int quarter = company.getQuarter();
			int month = company.getMonth();
			company = statisticFacadeIface.loadCompany(company);
			company.setYear(year);
			company.setQuarter(quarter);
			company.setMonth(month);
			// Long com_user_id = statisticFacadeIface.loadCompanyUser(company);
			// company.setUserId(com_user_id);
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询企业重大隐患列表（特定时间段里） lisl
	 * 
	 * @return
	 */
	public String loadDangers() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea(area.getAreaCode());
			}
			daDangers = decorationDangers(statisticFacadeIface.loadDangers(company, pagination, ""));
			int year = company.getYear();
			int quarter = company.getQuarter();
			int month = company.getMonth();
			company = statisticFacadeIface.loadCompany(company);
			company.setYear(year);
			company.setQuarter(quarter);
			company.setMonth(month);
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 装饰Dangers
	 * 
	 * @param dangers
	 * @return
	 */
	public List<DaDanger> decorationDangers(List<DaDanger> dangers) {
		List<DaDanger> daDangers_0 = new ArrayList<DaDanger>();
		DaDanger danger = null;
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			for (DaDanger d : dangers) {
				danger = new DaDanger();
				danger.setId(d.getId());
				danger.setDaCompanyPass(d.getDaCompanyPass());
				danger.setDangerAdd(d.getDangerAdd());
				danger.setDangerNo(d.getDangerNo());
				danger.setFinishDate(d.getFinishDate());
				if (null != d.getDaDangerGorvers() && d.getDaDangerGorvers().size() > 0) {
					List<DaDangerGorver> temp = statisticFacadeIface.loadDangerGorversOfOne(danger);
					if (temp.size() > 0)
						danger.setDaDangerGorver(temp.get(0));
					if (null != danger.getDaDangerGorver() && null != danger.getDaDangerGorver().getFinishDate()) {
						if (null != danger.getFinishDate()) {
							danger.setIsOver(isOver(df.format(danger.getFinishDate()), df.format(danger.getDaDangerGorver().getFinishDate())));
						} else {
							danger.setIsOver("0");
						}
					} else {
						if (null != danger.getFinishDate()) {
							danger.setIsOver(isOver(df.format(danger.getFinishDate()), df.format(new Date())));
						} else {
							danger.setIsOver("0");
						}
					}
				} else {
					if (null != danger.getFinishDate()) {
						danger.setIsOver(isOver(df.format(danger.getFinishDate()), df.format(new Date())));
					} else {
						danger.setIsOver("0");
					}
				}
				danger.setDaRollcallCompanies(d.getDaRollcallCompanies());
				daDangers_0.add(danger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return daDangers_0;
	}

	/**
	 * 查询监管部门检查发现隐患情况列表（特定时间段里） lisl
	 * 
	 * @return
	 */
	public String loadDangersAndNomalDangers() {
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea(area.getAreaCode());
			}
			daNomalDangers = statisticFacadeIface.loadNomalDangers(company, null, "1");
			daDangers = statisticFacadeIface.loadDangers(company, null, "1");
			List<DaNomalDanger> nomalDangers_0 = new ArrayList<DaNomalDanger>();
			DaNomalDanger nomalDanger_0 = null;
			for (DaNomalDanger nd : daNomalDangers) {
				nomalDanger_0 = new DaNomalDanger();
				nomalDanger_0.setId(nd.getId());
				nomalDanger_0.setDescription(nd.getDescription());
				nomalDanger_0.setCreateTime(nd.getCreateTime());
				nomalDanger_0.setLinkMan(nd.getLinkMan());
				nomalDanger_0.setLinkTel(nd.getLinkTel());
				nomalDanger_0.setRepaired(nd.isRepaired());
				if (nd.getUserId() != null && !"".equals(nd.getUserId())) {
					nomalDanger_0.setIndustryName(findUserCompanyName(nd.getUserId()));
				}
				nomalDangers_0.add(nomalDanger_0);
			}

			List<DaDanger> dangers_0 = new ArrayList<DaDanger>();
			DaDanger danger_0 = null;
			for (DaDanger d : daDangers) {
				danger_0 = new DaDanger();
				danger_0.setId(d.getId());
				danger_0.setDescription(d.getDescription());
				danger_0.setCreateTime(d.getCreateTime());
				danger_0.setLinkMan(d.getLinkMan());
				danger_0.setLinkTel(d.getLinkTel());
				danger_0.setFinishDate(d.getFinishDate());
				danger_0.setDaDangerGorvers(d.getDaDangerGorvers());
				if (d.getUserId() != null && !"".equals(d.getUserId())) {
					danger_0.setDangerAdd(findUserCompanyName(d.getUserId()));
				}
				dangers_0.add(danger_0);
			}
			daNomalDangers = nomalDangers_0;
			daDangers = dangers_0;
			int year = company.getYear();
			int quarter = company.getQuarter();
			int month = company.getMonth();
			company = statisticFacadeIface.loadCompany(company);
			company.setYear(year);
			company.setQuarter(quarter);
			company.setMonth(month);
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 获取检查单位名称
	 * 
	 * @param String
	 * @return
	 */
	public String findUserCompanyName(Long user_id) {
		FkUser user = null;
		try {
			user = userFacadeIface.loadUser(user_id);
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return user == null ? "" : user.getFkUserInfo().getUserCompany();
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
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 加载一个重大隐患整改信息的内容
	 * 
	 * @return
	 */
	public String loadDangerGorver() {
		try {
			danger = dangerGorverFacadeIface.loadDanger(new DaDanger(dangerGorver.getDaDanger().getId()));
			company = dangerGorverFacadeIface.loadCompany(new DaCompany(danger.getDaCompanyPass().getId()));
			dangerGorvers = dangerGorverFacadeIface.loadDangerGorversOfOne(dangerGorver);
			if (dangerGorvers.size() > 0) {
				dangerGorver = dangerGorvers.get(0);
			}
		} catch (ApplicationAccessException e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询重大隐患挂牌列表
	 * 
	 * @return
	 */
	public String loadRollcallCompanies() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			rollcallCompanies = statisticFacadeIface.loadRollcallCompanies(danger, pagination);
		} catch (ApplicationAccessException e) {
			// ////e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 判断时间大小
	 * 
	 * @param s1
	 *            (当前日期或者预计完成时间)
	 * @param s2
	 *            (完成治理时间)
	 * @return
	 */
	public String isOver(String s1, String s2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		int result = c1.compareTo(c2);
		if (result == 0) {// 时间S1 = 时间S2 未超期
			return "0";
		} else if (result < 0) {// 时间S1 < 时间S2 超期
			return "1";
		} else {// 时间S1 > 时间S2 未超期
			return "0";
		}
	}

	/**
	 * 排查质量统计所有
	 * 
	 * @return
	 */
	public String loadMassAll() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}

			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas1_his(area.getAreaCode(), statistic, current_quarter);

			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadMassAll(area, statistic, current_quarter);
				if (statistic.getYear() == nowY && (statistic.getMonth() == month || current_quarter == statistic.getQuarter())) { // 是否最新月份
					// 如是则加刷新按钮
					refresh = 1;
					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadMassAll_" + area.getAreaCode() + "0'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}

			// add by huangjl
			// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
			if (statistic != null && nowY == statistic.getYear() && (statistic.getQuarter() == current_quarter || statistic.getQuarter() == 0) && statistic.getMonth() == 0) {
				statistic.setMonth(cal.get(Calendar.MONTH) + 1);
			}
		} catch (Exception e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 排查质量统计根据行业
	 * 
	 * @return
	 */
	public String loadMassByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}
			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}
			// add by huangjl
			if (secondStatistic == null) {
				secondStatistic = "1";
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadMassByIndustry(area, statistic, current_quarter);
				if (statistic.getYear() == nowY && (statistic.getMonth() == month || current_quarter == statistic.getQuarter())) { // 是否最新月份
					// 如是则加刷新按钮
					refresh = 1;

					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadMassByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
			// add by huangjl
			// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
			if (statistic != null && nowY == statistic.getYear() && (statistic.getQuarter() == current_quarter || statistic.getQuarter() == 0) && statistic.getMonth() == 0) {
				statistic.setMonth(cal.get(Calendar.MONTH) + 1);
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// public String loadCompanyMassByIndustryList(){11
	// if (pagination == null) {
	// pagination = new Pagination();
	// pagination.setPageSize(10);
	// }
	// try {
	// companies = statisticFacadeIface.loadCompanyMassByIndustryList(company,
	// pagination,area);
	// } catch (ApplicationAccessException e) {
	// ////e.printStackTrace();
	// return ERROR;
	// }
	// return SUCCESS;
	// }
	//
	/**
	 * 排查质量进度统计企业列表
	 * 
	 * @return
	 */
	public String loadCompanyMassByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}

		if (statistic == null) {
			statistic = new Statistic();
		}
		statistic.setYear(company.getYear());
		statistic.setQuarter(company.getQuarter());
		statistic.setMonth(company.getMonth());

		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}
			statistics = statisticFacadeIface.loadCompanyMassByIndustryList(company, pagination, area, current_quarter);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 行业统计
	 * 
	 * @return
	 */
	public String loadCompanyByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}

			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadCompanyByIndustry(area, statistic, current_quarter);
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}

			if (statistic.getYear() == cal.get(Calendar.YEAR) && (statistic.getMonth() == month && current_quarter == statistic.getQuarter())) { // 是否最新月份
				// 如是则加刷新按钮
				refresh = 1;
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadQuarterByIndustry() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}

			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas1_his(area.getAreaCode(), statistic, current_quarter);

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadQuarterByIndustry(area, statistic, current_quarter);

				// 是否最新季度或前一季度,如是则加刷新按钮
				if (statistic.getYear() == cal.get(Calendar.YEAR)) {
					if (current_quarter == statistic.getQuarter()) { // 当前季度
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0" + "'";
					} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
					}

				} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}
				if (!sql.equals("")) {
					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 安监行业统计
	 * 
	 * @return
	 */
	public String loadCompanyByIndustryOfAj() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);

			statistics = statisticFacadeIface.loadCompanyByIndustry(area, statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			// 页面上是否有刷新按钮(最新报表数据)
			if (statistic.getYear() == cal.get(Calendar.YEAR) && (statistic.getMonth() == month && current_quarter == statistic.getQuarter())) { // 是否最新月份
																																					// ,如是则加刷新按钮
				refresh = 1;
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 安监其他行业统计
	 * 
	 * @return
	 */
	public String loadCompanyByIndustryOfAjOther() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);

			statistics = statisticFacadeIface.loadCompanyByIndustryOfOther(area, statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);

			// 页面上是否有刷新按钮(最新报表数据)
			if (statistic.getYear() == cal.get(Calendar.YEAR) && (statistic.getMonth() == month && current_quarter == statistic.getQuarter())) { // 是否最新月份
																																					// ,如是则加刷新按钮
				refresh = 1;
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'");
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadQuarterByIndustryOfAj() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);

			statistics = statisticFacadeIface.loadQuarterByIndustry(area, statistic, current_quarter);

			// 是否最新季度或前一季度,如是则加刷新按钮
			if (statistic.getYear() == cal.get(Calendar.YEAR)) {
				if (current_quarter == statistic.getQuarter()) { // 当前季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'";
				} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																			// 前一季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}

			} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
				refresh = 1;
				sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + (statistic.getQuarter() + 12) + "'";
			}
			if (!sql.equals("")) {
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 安监其他行业统计
	 * 
	 * @return
	 */
	public String loadQuarterByIndustryOfAjOther() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);

			statistics = statisticFacadeIface.loadQuarterByIndustryOfAjOther(area, statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);

			// 是否最新季度或前一季度,如是则加刷新按钮
			if (statistic.getYear() == cal.get(Calendar.YEAR)) {
				if (current_quarter == statistic.getQuarter()) { // 当前季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'";
				} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																			// 前一季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}

			} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
				refresh = 1;
				sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
			}
			if (!sql.equals("")) {
				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 行业统计企业列表
	 * 
	 * @return
	 */
	public String loadCompanyByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {

				// liulj 加上历史数据判断
				if (statistic == null) {
					statistic = new Statistic();
				}

				Calendar cal = Calendar.getInstance();
				int month = cal.get(Calendar.MONTH) + 1;
				if (month == 1 || month == 2 || month == 3) {
					current_quarter = 1;
				} else if (month == 4 || month == 5 || month == 6) {
					current_quarter = 2;
				} else if (month == 7 || month == 8 || month == 9) {
					current_quarter = 3;
				} else if (month == 10 || month == 11 || month == 12) {
					current_quarter = 4;
				}
				statistic.setYear(company.getYear());
				statistic.setQuarter(company.getQuarter());
				statistic.setMonth(company.getMonth());
				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}
			statistics = statisticFacadeIface.loadCompanyByIndustryList1(company, pagination, area, current_quarter, null);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 隐患整治统计
	 * 
	 * @return
	 */
	public String loadTroubleByNomalAndHiddenAndRollcall() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}
			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setBeg_month(1);
				statistic.setEnd_month(month);
			}
			statistic.setMonth(statistic.getEnd_month());
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas1_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statisticsForDanger = statisticFacadeIface.loadTroubleByNomalAndHidden(area, statistic, current_quarter);
				statistics = statisticFacadeIface.loadTroubleByRollcall(area, statistic, current_quarter);

				if (statistic.getYear() == nowY && (statistic.getMonth() == month || current_quarter == statistic.getQuarter())) { // 是否最新月份
					// 如是则加刷新按钮
					refresh = 1;
					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadTroubleByNomalAndHiddenAndRollcall_" + statistic.getRemark() + "_rollcall_" + area.getAreaCode() + "0'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
			// add by huangjl
			// 如果当前年份和季度都相等的话，并且月度为0。则过滤到当年月的数据;或者年度相等，季度和月份都为0，也要过滤掉
			if (statistic != null && nowY == statistic.getYear() && (statistic.getQuarter() == current_quarter || statistic.getQuarter() == 0) && statistic.getMonth() == 0) {
				statistic.setMonth(cal.get(Calendar.MONTH) + 1);
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 一般隐患列表
	 * 
	 * @return
	 */
	public String loadNomalTroubleByTypeList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}
		statistic.setMonth(statistic.getEnd_month());

		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}
			nomalDangers = statisticFacadeIface.loadNomalTroubleByTypeList(statistic, pagination, area, current_quarter);
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 重大隐患列表
	 * 
	 * @return
	 */
	public String loadDangerTroubleByTypeList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}
		statistic.setMonth(statistic.getEnd_month());

		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			}

			dangers = decorationDangers(statisticFacadeIface.loadDangerTroubleByTypeList(statistic, pagination, area, current_quarter));
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 打非统计
	 * 
	 * @return
	 */
	public String loadThreeIllegal() {
		try {
			if (statistic == null) {
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				statistic.setYear(cal.get(Calendar.YEAR));
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistics = statisticFacadeIface.loadThreeIllegal(statistic);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 重大隐患类型统计
	 * 
	 * @return
	 */
	public String loadDangerTypeByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadDangerTypeByIndustry(area, remark);
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadDangerTypeByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			dangers = statisticFacadeIface.loadDangerTypeByIndustryList(statistic, pagination, area);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 工程项目统计
	 * 
	 * @return
	 */
	public String loadItemByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
				if ("jianwei".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.CONSTRUCTION_PROJECT);
				}
			}
			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadItemByIndustry(area, statistic, current_quarter);
				statisticsForDanger = statisticFacadeIface.loadItemTroubleByIndustry(area, statistic, current_quarter);
				List<FkArea> areasByGroupNumTemp=null;
				areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum_his(area.getAreaCode(), statistic, current_quarter);
				//添加未明确区域
				if(area.getAreaCode()==330200000000L){
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}else{
					areasByGroupNum=areasByGroupNumTemp;
				}
				if (statistic.getYear() == cal.get(Calendar.YEAR) && (statistic.getMonth() == month && current_quarter == statistic.getQuarter())) { // 是否最新月份
					// 如是则加刷新按钮
					refresh = 1;
					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_jw" + statistic.getRemark() + area.getAreaCode() + "0'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadQuarterItem() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
				if ("jianwei".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.CONSTRUCTION_PROJECT);
				}
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadItemByIndustry(area, statistic, current_quarter);
				statisticsForDanger = statisticFacadeIface.loadItemTroubleByIndustry(area, statistic, current_quarter);
				List<FkArea> areasByGroupNumTemp=null;
				areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum_his(area.getAreaCode(), statistic, current_quarter);
				//添加未明确区域
				if(area.getAreaCode()==330200000000L){
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}else{
					areasByGroupNum=areasByGroupNumTemp;
				}

				// 是否最新季度或前一季度,如是则加刷新按钮
				if (statistic.getYear() == cal.get(Calendar.YEAR)) {
					if (current_quarter == statistic.getQuarter()) { // 当前季度
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_jw" + statistic.getRemark() + area.getAreaCode() + "0'";
					} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																				// 前一季度
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_jw" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
					}

				} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_jw" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}
				if (!sql.equals("")) {

					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 行业、项目统计
	 * 
	 * @return
	 */
	public String loadCompanyAndItemByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
				if ("jiaotong".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.TRAFFIC_PROJECT);
				} else if ("chengguan".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.CITY_PROJECT);
				} else if ("shuili".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.WATER_PROJECT);
				}
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setMonth(month);
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadCompanyByIndustry(area, statistic, current_quarter);
				statisticsForPaicha = statisticFacadeIface.loadItemByIndustry(area, statistic, current_quarter);
				statisticsForDanger = statisticFacadeIface.loadItemTroubleByIndustry(area, statistic, current_quarter);
				List<FkArea> areasByGroupNumTemp=null;
				if ("shuili".equals(statistic.getRemark())) {
					areasByGroupNumTemp = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
				} else {
					areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum_his(area.getAreaCode(), statistic, current_quarter);
				}
				
				if(area.getAreaCode()==330200000000L){
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}else{
					areasByGroupNum=areasByGroupNumTemp;
				}
				
				if (statistic.getYear() == cal.get(Calendar.YEAR) && (statistic.getMonth() == month && current_quarter == statistic.getQuarter())) { // 是否最新月份
					refresh = 1;

					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement("select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadCompanyByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0'");
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						cFactory.releaseConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	private void addNoSecondArea(List<FkArea> areasByGroupNumTemp){
		
		if(areasByGroupNumTemp!=null&&areasByGroupNumTemp.size()>0){
			areasByGroupNum=new ArrayList<FkArea>();
			//添加未明确区域
			//禅道#17710 住建报表修改  BUG #20783 市级维护
//			if (!"jianwei".equals(statistic.getRemark())) {
				FkArea fkArea=new FkArea();
				fkArea.setAreaCode(Nbyhpc.noSecondAreaCode);
				fkArea.setAreaName(Nbyhpc.noSecondAreaName);
				areasByGroupNum.add(fkArea);
//			}
			//添加正常区域
			for(int i=0;i<areasByGroupNumTemp.size();i++){
				FkArea fkArea_new=new FkArea();
				BeanUtils.copyProperties(areasByGroupNumTemp.get(i), fkArea_new);
				areasByGroupNum.add(fkArea_new);
			}
		}
		
	}

	public String loadQuarterAndItemByIndustry() {
		String sql = "";
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
				if ("jiaotong".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.TRAFFIC_PROJECT);
				} else if ("chengguan".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.CITY_PROJECT);
				} else if ("shuili".equals(statistic.getRemark())) {
					statistic.setType(Nbyhpc.WATER_PROJECT);
				}
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}
			area = statisticFacadeIface.loadArea_his(area.getAreaCode(), statistic, current_quarter);
			areas = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);

			if (null != areas && areas.size() > 0) {
				statistics = statisticFacadeIface.loadQuarterByIndustry(area, statistic, current_quarter);
				statisticsForPaicha = statisticFacadeIface.loadItemByIndustry(area, statistic, current_quarter);
				statisticsForDanger = statisticFacadeIface.loadItemTroubleByIndustry(area, statistic, current_quarter);
				List<FkArea> areasByGroupNumTemp=null;
				if ("shuili".equals(statistic.getRemark())) {
					areasByGroupNumTemp = statisticFacadeIface.loadAreas_his(area.getAreaCode(), statistic, current_quarter);
				} else {
					areasByGroupNumTemp = statisticFacadeIface.loadAreasByGroupNum_his(area.getAreaCode(), statistic, current_quarter);
				}
				if(area.getAreaCode()==330200000000L){
					//添加未明确区域
					addNoSecondArea(areasByGroupNumTemp);
				}else{
					areasByGroupNum=areasByGroupNumTemp;
				}
				
				// 是否最新季度或前一季度,如是则加刷新按钮
				if (statistic.getYear() == cal.get(Calendar.YEAR)) {
					if (current_quarter == statistic.getQuarter()) { // 当前季度
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + "0" + "'";
					} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																				// 前一季度
						refresh = 1;
						sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
					}

				} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getRemark() + area.getAreaCode() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}
				if (!sql.equals("")) {

					try {
						PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
						ResultSet rs = pState.executeQuery();
						if (rs.next()) {
							refreshDate = rs.getString(1);
						}
						rs.close();
						rs.getStatement().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (Exception e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 项目排查进度、一般隐患统计列表
	 * 
	 * @return
	 */
	public String loadItemByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		if (statistic == null) {
			statistic = new Statistic();
		}

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}

		if (statistic.getYear() == 0) {
			statistic.setYear(cal.get(Calendar.YEAR));
			statistic.setQuarter(current_quarter);
		}
		if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
			if ("jianwei".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.CONSTRUCTION_PROJECT);
			} else if ("jiaotong".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.TRAFFIC_PROJECT);
			} else if ("chengguan".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.CITY_PROJECT);
			} else if ("shuili".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.WATER_PROJECT);
			}
		}
		try {
			items = statisticFacadeIface.loadItemByIndustryList(statistic, pagination, area, current_quarter);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 项目重大隐患统计列表
	 * 
	 * @return
	 */
	public String loadItemTroubleByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		if (statistic == null) {
			statistic = new Statistic();
		}

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			current_quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			current_quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			current_quarter = 3;
		} else if (month == 10 || month == 11 || month == 12) {
			current_quarter = 4;
		}

		if (statistic.getYear() == 0) {
			statistic.setYear(cal.get(Calendar.YEAR));
			statistic.setQuarter(current_quarter);
		}
		if (statistic.getRemark() != null && !"".equals(statistic.getRemark())) {
			if ("jianwei".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.CONSTRUCTION_PROJECT);
			} else if ("jiaotong".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.TRAFFIC_PROJECT);
			} else if ("chengguan".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.CITY_PROJECT);
			} else if ("shuili".equals(statistic.getRemark())) {
				statistic.setIndustryId(Nbyhpc.WATER_PROJECT);
			}
		}
		try {
			itemDangers = statisticFacadeIface.loadItemTroubleByIndustryList(statistic, pagination, area, current_quarter);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 一般隐患类型统计
	 * 
	 * @return
	 */
	public String loadNomalDangerTypeByIndustry() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadNomalDangerTypeByIndustry(area, remark);
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadNomalDangerTypeByIndustryList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			nomalDangers = statisticFacadeIface.loadNomalDangerTypeByIndustryList(statistic, pagination, area);
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 其他季报统计
	 * 
	 * @return
	 */
	public String loadStatisticsOfSeasonReportOther() {
		try {
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			statistics = statisticFacadeIface.loadStatisticsOfSeasonReportOther(statistic, current_quarter); // 未改
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadQuarterOther() {
		String sql = "";
		try {
			if (statistic == null) {
				statistic = new Statistic();
			}

			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int nowY = cal.get(Calendar.YEAR);
			if (month == 1 || month == 2 || month == 3) {
				current_quarter = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				current_quarter = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				current_quarter = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				current_quarter = 4;
			}

			if (statistic.getYear() == 0) {
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setQuarter(current_quarter);
			}

			statistics = statisticFacadeIface.loadStatisticsOfSeasonReportOther(statistic, current_quarter);

			// 是否最新季度或前一季度,如是则加刷新按钮
			if (statistic.getYear() == cal.get(Calendar.YEAR)) {
				if (current_quarter == statistic.getQuarter()) { // 当前季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getAreaCode() + statistic.getRemark() + "0" + "'";
				} else if (current_quarter == statistic.getQuarter() + 1) { // 同年
																			// 前一季度
					refresh = 1;
					sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getAreaCode() + statistic.getRemark() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
				}

			} else if (current_quarter == 1 && (statistic.getYear() + 1) == cal.get(Calendar.YEAR) && statistic.getQuarter() == 4) {// 当前季度是第一季度，前一季度为第四季度
				refresh = 1;
				sql = "select t.uptdate from T_CACHES t    where  t.name='nbyhpc_loadQuarterByIndustry_" + statistic.getAreaCode() + statistic.getRemark() + statistic.getYear() + (statistic.getQuarter() + 12) + "'";
			}

			if (!sql.equals("")) {

				try {
					PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
					ResultSet rs = pState.executeQuery();
					if (rs.next()) {
						refreshDate = rs.getString(1);
					}
					rs.close();
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 分级评估统计
	 * 
	 * @return
	 */
	public String loadCompanyByCompanyLevel() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statistics = statisticFacadeIface.loadCompanyByCompanyLevel(area);
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 分级评估统计
	 * 
	 * @return
	 */
	public String loadCompanyByCompanyLevelList() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			companies = statisticFacadeIface.loadCompanyByCompanyLevelList(statistic, pagination, area);
		} catch (ApplicationAccessException e) {
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanysForColligation() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);

		}

		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadAreaByAreaCodeZhjg(area.getAreaCode());
			}
			if (null != company && null != company.getCompanyName() && !"".equals(company.getCompanyName())) {
				companies = companyFacadeIface.loadCompanyByCompanyName(company, pagination);
			} else {
				company = new DaCompany();
				companies = companyFacadeIface.loadCompanysByArea(area, pagination, type1);
			}
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 综合监管平台的普通隐患排查查询
	 * 
	 * @return
	 */
	public String loadNomalTroubleForColligation() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea(area.getAreaCode());
			}
			nomalDangers = statisticFacadeIface.loadNomalTroubleByTypeList(statistic, pagination, area, current_quarter); // 未改
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 综合监管平台的重大隐患排查查询
	 * 
	 * @return
	 */
	public String loadDangerTroubleForColligation() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (area != null && area.getAreaCode() != null && area.getAreaCode() != 0l) {
				area = statisticFacadeIface.loadArea(area.getAreaCode());
			}
			dangers = decorationDangers(statisticFacadeIface.loadDangerTroubleByTypeList(statistic, pagination, area, current_quarter)); // 未改
		} catch (ApplicationAccessException e) {
			// //e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 隐患整治统计
	 * 
	 * @return
	 */
	public String loadTroubleForColligation() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setBeg_month(1);
				statistic.setEnd_month(12);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			statisticsForDanger = statisticFacadeIface.loadTroubleByNomalAndHiddenColligation(area, statistic);
			statistics = statisticFacadeIface.loadTroubleByRollcallColligation(area, statistic);
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadTroubleForColligationJson() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try {
			if (null == area) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			Map<String, Object> oMap = statisticFacadeIface.loadTroubleByNomalAndHiddenJson(area, statistic);
			Map<String, Object> oMap_1 = statisticFacadeIface.loadTroubleByRollcallJson(area, statistic);
			resultJSON.put("returnMap", oMap);
			resultJSON.put("returnMap_1", oMap_1);
			resultJSON.put("isTrue", true);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			resultJSON.put("isTrue", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}

	/**
	 * 查询企业数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadCompanyCountForColligation() {
		try {
			result = new ArrayList();
			// 设置地区查询条件
			// 如果没有地区,默认为宁波
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			// 地区信息
			// 暂存综合监管平台的area_code,然后找到区域,并查出下属区域,最后再赋值回去
			// newAreaCode作为暂存变量
			Long newAreaCode = area.getAreaCode();
			area = statisticFacadeIface.loadAreaByAreaCodeZhjg(area.getAreaCode());
			// 下级地区
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());

			area.setAreaCode(newAreaCode);

			// 查询出相应地区的企业
			int sum = 0;
			for (FkArea a : areas) {
				int temp = companyFacadeIface.loadCompanyCountByArea(a);
				// sum += temp;
				result.add(temp);
			}

			sum = companyFacadeIface.loadCompanyCountByArea(area);
			// sum += temp;
//			System.out.println("sum:  " + sum);

			// 更改子区域的area_code
			loadAreasFromXml(areas);
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 查询一般问题和重大问题的总数
	 * 
	 * @return
	 */
	public String loadTroubleCountForColligation() {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if (statistic == null) {
				statistic = new Statistic();
			}
			if (statistic.getYear() == 0) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(cal.getTime());
				statistic.setYear(cal.get(Calendar.YEAR));
				if (mDateTime.compareTo(statistic.getYear() + "-01-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-04-01") < 0) {
					statistic.setQuarter(1);
				} else if (mDateTime.compareTo(statistic.getYear() + "-04-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-07-01") < 0) {
					statistic.setQuarter(2);
				} else if (mDateTime.compareTo(statistic.getYear() + "-07-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-10-01") < 0) {
					statistic.setQuarter(3);
				} else if (mDateTime.compareTo(statistic.getYear() + "-10-01") >= 0 && mDateTime.compareTo(statistic.getYear() + "-12-31") <= 0) {
					statistic.setQuarter(4);
				}
			}
			Long newAreaCode = area.getAreaCode();
			area = statisticFacadeIface.loadAreaByAreaCodeZhjg(area.getAreaCode());
			List<Statistic> temp = statisticFacadeIface.loadTroubleByNomalAndHidden(area, statistic, current_quarter); // 未改
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			area.setAreaCode(newAreaCode);
			result = new ArrayList<Statistic>();
			for (int i = 0; i < areas.size(); i++) {
				FkArea fa = areas.get(i);
				for (int j = 0; j < temp.size(); j++) {
					Statistic s = temp.get(j);
					if (fa.getAreaCode().equals(s.getAreaCode())) {
						result.add(s);
						break;
					}
				}
			}
			loadAreasFromXml(areas);
		} catch (ApplicationAccessException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadCompanyListForColligation() {
		try {
			if (null == area) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
				area.setAreaName("宁波市");
				areaLevel = "firstArea";
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		// dpList = statisticFacadeIface.loadCompanyTypes();
		// dataMap = statisticFacadeIface.loadCompaniesByAreaAndType(dpList,
		// areas, area);
		return SUCCESS;
	}

	public String loadCompanyListForColligationJson() throws IOException, MapperException {
		Map<String, Object> resultJSON = new HashMap<String, Object>();
		try {
			if (null == area) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			// area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = statisticFacadeIface.loadAreas(area.getAreaCode());
			Map<String, Object> oMap = statisticFacadeIface.loadCompaniesByAreaAndType(areas, area, areaLevel, statisticFacadeIface.loadCompanyTypes());
			resultJSON.put("returnMap", oMap);
			resultJSON.put("isTrue", true);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			resultJSON.put("isTrue", false);
		}
		getResponse().setContentType("text/plain;charset=UTF-8");
		getResponse().getWriter().write(JSONMapper.toJSON(resultJSON).render(false));
		return null;
	}

	public String loadCompanysForColligationShowDetails() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if (null == area) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			companies = statisticFacadeIface.loadCompanysForColligationShowDetails(company, type1, areaLevel, area, pagination);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 第一次登陆的登陆验证,并将综合监管平台上的地区跟隐患排查的地区做匹配
	 * 
	 * @return
	 */
	public String firstlogin() {
		if (area == null) {
			area = new FkArea();
			area.setAreaCode(Nbyhpc.AREA_CODE);
		}
		return SUCCESS;
	}

	/**
	 * 返回地区列表,并匹配综合监管平台的area_code
	 * 
	 * @param areas
	 */
	public void loadAreasFromXml(List<FkArea> areas) {
		this.areas = new ArrayList<FkArea>();
		for (int i = 0; i < areas.size(); i++) {
			FkArea area = areas.get(i);
			area.setAreaCode(statisticFacadeIface.loadAreaCodeZhjgByAreaCode(area.getAreaCode()));
			this.areas.add(area);
		}
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
			resultJSON.put("uuid", corecompany.getUuid());
			resultJSON.put("legalPerson", corecompany.getLegalPerson());
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

	public String loadStaticNomalFusionCharts() throws ApplicationAccessException {
		chart = new TCharts();
		if (statistic == null)
			statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		statistic.setYear(cal.get(Calendar.YEAR));
		statistic.setMonth(cal.get(Calendar.MONTH) + 1);
		// 2014_330200000000_chengguan_all_normal_danger1
		String title = statistic.getYear() + "_";
		if (getUserDetail().getThirdArea() != null && getUserDetail().getThirdArea() != 0 && !getUserDetail().getThirdArea().equals("")) {
			title += getUserDetail().getThirdArea() + "_";
		} else if (getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0 && !getUserDetail().getSecondArea().equals("")) {
			title += getUserDetail().getSecondArea() + "_";
			area = statisticFacadeIface.loadArea(getUserDetail().getSecondArea());
			if (area != null) {
				chart.setNum1(area.getAreaName());
			}
			chart.setNum2("街道");
		} else if (getUserDetail().getFirstArea() != null && getUserDetail().getFirstArea() != 0 && !getUserDetail().getFirstArea().equals("")) {
			title += getUserDetail().getFirstArea() + "_";
			chart.setNum1("宁波市");
			chart.setNum2("县区");
		}
		chart.setTitle1(title + getUserDetail().getUserIndustry() + "_all_normal_danger0");
		chart.setTitle2(title + getUserDetail().getUserIndustry() + "_all_normal_danger1");
		chart.setTitle3(title + getUserDetail().getUserIndustry() + "_normal_danger");
		return SUCCESS;
	}

	public String loadStaticDngFusionCharts() throws ApplicationAccessException {
		chart = new TCharts();
		if (statistic == null)
			statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		statistic.setYear(cal.get(Calendar.YEAR));
		statistic.setMonth(cal.get(Calendar.MONTH) + 1);
		// 2014_330200000000_chengguan_all_normal_danger1
		String title = statistic.getYear() + "_";
		if (getUserDetail().getThirdArea() != null && getUserDetail().getThirdArea() != 0 && !getUserDetail().getThirdArea().equals("")) {
			title += getUserDetail().getThirdArea() + "_";
		} else if (getUserDetail().getSecondArea() != null && getUserDetail().getSecondArea() != 0 && !getUserDetail().getSecondArea().equals("")) {
			title += getUserDetail().getSecondArea() + "_";
			area = statisticFacadeIface.loadArea(getUserDetail().getSecondArea());
			if (area != null) {
				chart.setNum1(area.getAreaName());
			}
			chart.setNum2("街道");
		} else if (getUserDetail().getFirstArea() != null && getUserDetail().getFirstArea() != 0 && !getUserDetail().getFirstArea().equals("")) {
			title += getUserDetail().getFirstArea() + "_";
			chart.setNum1("宁波市");
			chart.setNum2("县区");
		}
		chart.setTitle1(title + getUserDetail().getUserIndustry() + "_all_da_danger0");
		chart.setTitle2(title + getUserDetail().getUserIndustry() + "_all_da_danger1");
		chart.setTitle3(title + getUserDetail().getUserIndustry() + "_da_danger");
		return SUCCESS;
	}

	

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
	}

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public List<DaDanger> getDangers() {
		return dangers;
	}

	public void setDangers(List<DaDanger> dangers) {
		this.dangers = dangers;
	}

	public List<DaNomalDanger> getNomalDangers() {
		return nomalDangers;
	}

	public void setNomalDangers(List<DaNomalDanger> nomalDangers) {
		this.nomalDangers = nomalDangers;
	}

	public Statistic getStatisticForCompany() {
		return statisticForCompany;
	}

	public void setStatisticForCompany(Statistic statisticForCompany) {
		this.statisticForCompany = statisticForCompany;
	}

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	public List<Statistic> getStatisticsForDanger() {
		return statisticsForDanger;
	}

	public void setStatisticsForDanger(List<Statistic> statisticsForDanger) {
		this.statisticsForDanger = statisticsForDanger;
	}

	public List<Statistic> getStatisticsForPaicha() {
		return statisticsForPaicha;
	}

	public void setStatisticsForPaicha(List<Statistic> statisticsForPaicha) {
		this.statisticsForPaicha = statisticsForPaicha;
	}

	public List<DaItemDanger> getItemDangers() {
		return itemDangers;
	}

	public void setItemDangers(List<DaItemDanger> itemDangers) {
		this.itemDangers = itemDangers;
	}

	public List<DaItem> getItems() {
		return items;
	}

	public void setItems(List<DaItem> items) {
		this.items = items;
	}

	public List<FkArea> getAreasByGroupNum() {
		return areasByGroupNum;
	}

	public void setAreasByGroupNum(List<FkArea> areasByGroupNum) {
		this.areasByGroupNum = areasByGroupNum;
	}

	public DaNomalDanger getDaNomalDanger() {
		return daNomalDanger;
	}

	public void setDaNomalDanger(DaNomalDanger daNomalDanger) {
		this.daNomalDanger = daNomalDanger;
	}

	public List<DaNomalDanger> getDaNomalDangers() {
		return daNomalDangers;
	}

	public void setDaNomalDangers(List<DaNomalDanger> daNomalDangers) {
		this.daNomalDangers = daNomalDangers;
	}

	public DaDanger getDanger() {
		return danger;
	}

	public void setDanger(DaDanger danger) {
		this.danger = danger;
	}

	public List<DaIndustryParameter> getIndustryParameters() {
		return industryParameters;
	}

	public void setIndustryParameters(List<DaIndustryParameter> industryParameters) {
		this.industryParameters = industryParameters;
	}

	public List<DaDept> getStatisticUpAndDownDepts() {
		return statisticUpAndDownDepts;
	}

	public void setStatisticUpAndDownDepts(List<DaDept> statisticUpAndDownDepts) {
		this.statisticUpAndDownDepts = statisticUpAndDownDepts;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public FkArea getAreaThird() {
		return areaThird;
	}

	public void setAreaThird(FkArea areaThird) {
		this.areaThird = areaThird;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public DaCompanyQuarterReprot getCompanyQuarterReprot() {
		return companyQuarterReprot;
	}

	public void setCompanyQuarterReprot(DaCompanyQuarterReprot companyQuarterReprot) {
		this.companyQuarterReprot = companyQuarterReprot;
	}

	public List<DaCompanyQuarterReprot> getCompanyQuarterReprots() {
		return companyQuarterReprots;
	}

	public void setCompanyQuarterReprots(List<DaCompanyQuarterReprot> companyQuarterReprots) {
		this.companyQuarterReprots = companyQuarterReprots;
	}

	public void setCompanyQuarterReportFacadeIface(CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface) {
		this.companyQuarterReportFacadeIface = companyQuarterReportFacadeIface;
	}

	public void setNomalDangerFacadeIface(NomalDangerFacadeIface nomalDangerFacadeIface) {
		this.nomalDangerFacadeIface = nomalDangerFacadeIface;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public List<DaDanger> getDaDangers() {
		return daDangers;
	}

	public void setDaDangers(List<DaDanger> daDangers) {
		this.daDangers = daDangers;
	}

	public void setDangerFacadeIface(DangerFacadeIface dangerFacadeIface) {
		this.dangerFacadeIface = dangerFacadeIface;
	}

	public List<DaDangerGorver> getDangerGorvers() {
		return dangerGorvers;
	}

	public void setDangerGorvers(List<DaDangerGorver> dangerGorvers) {
		this.dangerGorvers = dangerGorvers;
	}

	public DaDangerGorver getDangerGorver() {
		return dangerGorver;
	}

	public void setDangerGorver(DaDangerGorver dangerGorver) {
		this.dangerGorver = dangerGorver;
	}

	public void setDangerGorverFacadeIface(DangerGorverFacadeIface dangerGorverFacadeIface) {
		this.dangerGorverFacadeIface = dangerGorverFacadeIface;
	}

	public List<DaRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public List<DaIndustryParameter> getDpList() {
		return dpList;
	}

	public List<List<Integer>> getDpMap() {
		return dpMap;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public TCompany getCorecompany() {
		return corecompany;
	}

	public void setCorecompany(TCompany corecompany) {
		this.corecompany = corecompany;
	}

	public WhCompanyInfoFacadeIface getCompanyInfoFacadeIface() {
		return companyInfoFacadeIface;
	}

	public void setCompanyInfoFacadeIface(WhCompanyInfoFacadeIface companyInfoFacadeIface) {
		this.companyInfoFacadeIface = companyInfoFacadeIface;
	}

	public String getSecondStatistic() {
		return secondStatistic;
	}

	public void setSecondStatistic(String secondStatistic) {
		this.secondStatistic = secondStatistic;
	}

	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public String getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
	}

	public TCharts getChart() {
		return chart;
	}

	public void setChart(TCharts chart) {
		this.chart = chart;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the sType
	 */
	public String getsType() {
		return sType;
	}

	/**
	 * @param sType the sType to set
	 */
	public void setsType(String sType) {
		this.sType = sType;
	}

	/**
	 * @return the catchName
	 */
	public String getCatchName() {
		return catchName;
	}

	/**
	 * @param catchName the catchName to set
	 */
	public void setCatchName(String catchName) {
		this.catchName = catchName;
	}

	/**
	 * @return the sendMonth
	 */
	public Integer getSendMonth() {
		return sendMonth;
	}

	/**
	 * @param sendMonth the sendMonth to set
	 */
	public void setSendMonth(Integer sendMonth) {
		this.sendMonth = sendMonth;
	}

	/**
	 * @return the current_quarter
	 */
	public int getCurrent_quarter() {
		return current_quarter;
	}

	/**
	 * @param currentQuarter the current_quarter to set
	 */
	public void setCurrent_quarter(int currentQuarter) {
		current_quarter = currentQuarter;
	}

	
	

}
