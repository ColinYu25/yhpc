package com.safetys.nbyhpc.facade.iface;

import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaActualTableNotice;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyHis;
import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerGorver;
import com.safetys.nbyhpc.domain.model.DaDept;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaItem;
import com.safetys.nbyhpc.domain.model.DaItemDanger;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaRollcallCompany;
import com.safetys.nbyhpc.domain.model.Statistic;

public interface StatisticFacadeIface {
	/**
	 * lisl(提取重大隐患最新的治理时间)
	 * 
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaDangerGorver> loadDangerGorversOfOne(DaDanger daDanger) throws ApplicationAccessException;
 
	public Long createActualizeProject(DaActualTableNotice daActualTableNotice) throws ApplicationAccessException;

	public List<FkArea> loadAreas(Long areaCode) throws ApplicationAccessException;

	public List<FkArea> loadAreas_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<FkArea> loadAreas1(Long areaCode) throws ApplicationAccessException;

	public FkArea loadArea_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException;

	public FkArea loadArea_his(Long areaCode, int backupDate) throws ApplicationAccessException;
	
	public List<FkArea> loadAreas1_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<FkArea> loadAreasByGroupNum(Long areaCode) throws ApplicationAccessException;

	public List<FkArea> loadAreasByGroupNum_his(Long areaCode, Statistic st, int current_quarter) throws ApplicationAccessException;

	public FkArea loadAreaByAreaCodeZhjg(Long areaCode) throws ApplicationAccessException;

	public Long loadAreaCodeZhjgByAreaCode(Long areaCode);

	public FkArea loadArea(Long areaCode) throws ApplicationAccessException;
	public FkArea loadArea(Long areaCode,Boolean reFresh) throws ApplicationAccessException;

	public List<Statistic> loadPaiChaOfCompany(FkArea area, Statistic st, int current_quarter,String statisicLevel) throws ApplicationAccessException;

	public List<Statistic> loadQuarterOfCompany(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadPaiChaOfCompanyList(DaCompany company, Pagination pagination, FkArea area, int current_quarter,String statisicLevel,UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<Statistic> loadCompanyByIndustryList1(DaCompany company, Pagination pagination, FkArea area, int current_quarter,UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangers(DaCompany company, Pagination pagination, String flag) throws ApplicationAccessException;

	public List<DaDanger> loadDangers(DaCompany company, Pagination pagination, String flag) throws ApplicationAccessException;

	public List<Statistic> loadCompanyByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadCompanyByIndustryOfOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<DaCompany> loadCompanyByIndustryList(DaCompany company, Pagination pagination, FkArea area) throws ApplicationAccessException;

	public List<Statistic> loadTroubleByRollcallColligation(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadTroubleByNomalAndHiddenColligation(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadTroubleByRollcall(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadTroubleByNomalAndHidden(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	/**
	 * 一般隐患、重大隐患整治统计(Json)
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadTroubleByNomalAndHiddenJson(FkArea area, Statistic st) throws ApplicationAccessException;

	/**
	 * 挂牌重大隐患整治统计(Json)
	 * 
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadTroubleByRollcallJson(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadThreeIllegal(Statistic st) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalTroubleByTypeList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException;

	public List<DaDanger> loadDangerTroubleByTypeList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadDangerTypeByIndustry(FkArea area, String remark) throws ApplicationAccessException;

	public List<DaDanger> loadDangerTypeByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException;

	public List<DaRollcallCompany> loadRollcallCompanies(DaDanger danger, Pagination pagination) throws ApplicationAccessException;

	public Long loadCompanyUser(DaCompany company) throws ApplicationAccessException;

	public List<Statistic> loadItemByIndustry(FkArea area, Statistic statistic, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadItemTroubleByIndustry(FkArea area, Statistic statistic, int current_quarter) throws ApplicationAccessException;

	public List<DaItem> loadItemByIndustryList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException;

	public List<DaItemDanger> loadItemTroubleByIndustryList(Statistic statistic, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadNomalDangerTypeByIndustry(FkArea area, String remark) throws ApplicationAccessException;

	public List<Statistic> loadStatisticsOfSeasonReportOther(Statistic statistic, int current_quarter) throws ApplicationAccessException;

	public List<DaCompany> loadCompanyByCompanyLevelList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException;

	public List<Statistic> loadCompanyByCompanyLevel(FkArea area) throws ApplicationAccessException;

	public void loadNormalDangerByIndustryAndArea(FkArea area, String remark, String param) throws ApplicationAccessException;

	public void loadDangerByIndustryAndArea(FkArea area, String remark, String param) throws ApplicationAccessException;
	
	public void loadNormalDangerByIndustryAndArea1(String remark) throws ApplicationAccessException;

	public void loadDangerByIndustryAndArea1(String remark) throws ApplicationAccessException;
	
	public void loadNormalDangerByIndustryAndArea2(String remark) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangerTypeByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException;

	public List<Statistic> loadReportByInd(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadReportByItemTrouble(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadReportByItem(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadReportByAjOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public Statistic loadReportByCompany(DaCompany company, Statistic st) throws ApplicationAccessException;

	public List<Statistic> loadNomalDangerByIndustry(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<DaCompany> loadNomalDangerByIndustryList(FkArea area, Statistic statistic, Pagination pagination) throws ApplicationAccessException;

	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;

	public List<DaNomalDanger> loadNomalDangersOfStatistic(Statistic statistic, Pagination pagination) throws ApplicationAccessException;

	public List<Statistic> loadDangerByIndustry(FkArea area, Statistic st) throws ApplicationAccessException;

	public List<DaDanger> loadDangerByIndustryList(Statistic statistic, Pagination pagination, FkArea area) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadIndustrysForDanger() throws ApplicationAccessException;

	public DaDanger loadDangerOfStatistic(DaDanger danger) throws ApplicationAccessException;

	public List<Statistic> loadCompanyLevelPoint(FkArea area) throws ApplicationAccessException;

	public List<Statistic> loadMassAll(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadMassByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadCompanyMassByIndustryList(DaCompany company, Pagination pagination, FkArea area, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> decorationStatistic1(List<DaCompanyHis> companies, DaCompany company, int backup_date) throws ApplicationAccessException;

	/**
	 * 显示市隐患抄告汇总表
	 */
	public List<Statistic> loadTroubleUpAndDownByUser(FkArea area, Statistic st) throws ApplicationAccessException;

	/**
	 * 显示乡镇隐患抄告汇总表
	 */
	public List<Statistic> loadTroubleUpAndDownByUserThird(FkArea area, Statistic st) throws ApplicationAccessException;

	/**
	 * 获得已抄告列表
	 */
	public List<DaDept> loadTroubleDownByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 获得已抄告未回复列表
	 */
	public List<DaDept> loadTroubleDownNoBackByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 获得已抄告未反馈列表
	 */
	public List<DaDept> loadTroubleDownNoResultByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 获得已回复列表
	 */
	public List<DaDept> loadTroubleUpByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 获得已接收未回复列表
	 */
	public List<DaDept> loadTroubleUpNoBackByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 获得已接收未反馈列表
	 */
	public List<DaDept> loadTroubleUpNoResultByUser(Statistic st, DaDept dept, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public List<Statistic> loadQuarterByIndustry(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<Statistic> loadQuarterByIndustryOfAjOther(FkArea area, Statistic st, int current_quarter) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadCompanyTypes();

	public Map<String, Object> loadCompaniesByAreaAndType(List<FkArea> areas, FkArea area, String areaLevel, List<DaIndustryParameter> types);

	public List<DaCompany> loadCompanysForColligationShowDetails(DaCompany company, String type, String areaLevel, FkArea area, Pagination pagination) throws ApplicationAccessException;

	public Statistic loadReportByCompanyId(Long companyId, Statistic st) throws ApplicationAccessException;

}
