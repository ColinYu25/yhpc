package com.safetys.nbyhpc.web.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.facade.iface.UserFacadeIface;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.CompanyQuarterReportFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeDangerGorverFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeNomalDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeStatisticFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
import com.sdicons.json.mapper.JSONMapper;

public class PipeStatisticAction extends DaAppAction {
	
	/**
	 * 管道统计报表
	 */
	private static final long serialVersionUID = -314744610392228865L;
	
	@SuppressWarnings("unused")
	private CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface;
	
	private PipeDangerGorverFacadeIface pipeDangerGorverFacadeIface;
	
	private PipeNomalDangerFacadeIface pipeNomalDangerFacadeIface;
	
	private PipeStatisticFacadeIface pipeStatisticFacadeIface;
	
	private StatisticFacadeIface statisticFacadeIface;
	
	private PipeDangerFacadeIface pipeDangerFacadeIface;
	
	private CompanyFacadeIface companyFacadeIface;
	
	private UserFacadeIface userFacadeIface;
	
	private List<DaIndustryParameter> industryParameters;
	
	private List<FkArea> areas;// 区域集合
	
	private List<Statistic> statistics;
	
	private List<Statistic> statisticsForDanger;
	
	private List<DaPipeDanger> pipeDangers;
	
	private List<DaPipeNomalDanger> daPipeNomalDangers;
	
	private List<DaPipeRollcallCompany> rollcallCompanies;
	
	private List result;
	
	private DaPipeDanger danger;
	
	private DaNomalDanger daNomalDanger;
	
	private Statistic statistic;
	
	private DaCompany company;// 企业单位
	
	private FkArea area;// 区域对象
	
	private DaPipeDangerGorver dangerGorver;//重大隐患整改
	
	private List<DaPipeDangerGorver> dangerGorvers;

	private Pagination pagination;// 分页对象
	
	private Map<String, Object> map;
	
	private String jdata;
	
	/**
	 * 一般隐患统计
	 * 
	 * */
	public String loadNomalDanger() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
				statistic.setType(-1);
			}
			areas = pipeStatisticFacadeIface.loadAreas2(statistic.getAreaCode());
			map = pipeStatisticFacadeIface.loadNomalDanger(statistic);
			jdata = JSONMapper.toJSON(map).render(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患统计详情查询
	 * 
	 * */
	public String loadNomalDangerDetail() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
				statistic.setType(-1);
			}
			result = pipeStatisticFacadeIface.loadNomalDangerDetail(statistic, this.getPagination());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 重大隐患统计
	 * 
	 * */
	public String loadDanger3() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
				statistic.setType(-1);
			}
			areas = pipeStatisticFacadeIface.loadAreas2(statistic.getAreaCode());
			map = pipeStatisticFacadeIface.loadDanger(statistic);
			jdata = JSONMapper.toJSON(map).render(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 重大隐患统计详情查询
	 * 
	 * */
	public String loadDangerDetail() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
				statistic.setType(-1);
			}
			result = pipeStatisticFacadeIface.loadDangerDetail(statistic, this.getPagination());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 月报统计
	 * 
	 * */
	public String loadMonth() {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				statistic.setMonth(currentMonth);
				statistic.setQuarter(DateUtils.getQuarter(currentMonth));
				statistic.setRemark("");//各地
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
			}
			areas = pipeStatisticFacadeIface.loadAreas2(statistic.getAreaCode());
			map = pipeStatisticFacadeIface.loadMonth(statistic);
			jdata = JSONMapper.toJSON(map).render(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 企业列表
	 * @return
	 */
	public String companyList() throws Exception{
		if(null == statistic){
			statistic = new Statistic();
		}
		result = pipeStatisticFacadeIface.loadCompanyListByArea(statistic, true, this.getPagination());
		return "company-list";
	}
	
	/**
	 * 企业列表
	 * @return
	 */
	public String unCompanyList() throws Exception{
		if(null == statistic){
			statistic = new Statistic();
		}
		result = pipeStatisticFacadeIface.loadCompanyListByArea(statistic, false, this.getPagination());
		return "company-list";
	}
	
	/**
	 * 油气管道列表
	 * @return
	 */
	public String pipelineList() throws Exception{
		if(null == statistic){
			statistic = new Statistic();
		}
//		System.out.println("statistic.getLineType(): "+statistic.getLineType());
		result = pipeStatisticFacadeIface.loadPipLineByArea(statistic, this.getPagination());
		if(null!=statistic.getLineType() && "0".equals(statistic.getLineType())){
			return "rq-pipline-list";
		}else{
			return "yq-pipline-list";
		}
	}

	/**
	 * 排查质量统计
	 * 
	 * */
	public String loadQuarter() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				statistic.setMonth(currentMonth);
				statistic.setQuarter(DateUtils.getQuarter(currentMonth));
				statistic.setRemark("");//各地
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
			}
			areas = pipeStatisticFacadeIface.loadAreas2(statistic.getAreaCode());
			map = pipeStatisticFacadeIface.loadQuarter(statistic);
			jdata = JSONMapper.toJSON(map).render(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 季报统计
	 * 
	 * */
	public String loadMass() throws Exception {
		try {
			if(null == statistic){
				statistic = new Statistic();
				Calendar cal = Calendar.getInstance();
				statistic.setYear(cal.get(Calendar.YEAR));
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				statistic.setMonth(currentMonth);
				statistic.setQuarter(DateUtils.getQuarter(currentMonth));
				statistic.setRemark("");//各地
				statistic.setAreaCode(Nbyhpc.AREA_CODE);
			}
			areas = pipeStatisticFacadeIface.loadAreas2(statistic.getAreaCode());
			map = pipeStatisticFacadeIface.loadMass(statistic);
			jdata = JSONMapper.toJSON(map).render(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 隐患治理统计
	 * 
	 * */
	public String loadDanger() throws Exception {
		try {
			if (area == null) {
				area = new FkArea();
				area.setAreaCode(Nbyhpc.AREA_CODE);
			}
			if(statistic == null){
				statistic = new Statistic();
			}
			if(statistic.getYear() ==0){
				Calendar cal = Calendar.getInstance();
	        	statistic.setYear(cal.get(Calendar.YEAR));
	        	statistic.setBeg_month(1);
	        	statistic.setEnd_month(12);
			}
			area = statisticFacadeIface.loadArea(area.getAreaCode());
			areas = pipeStatisticFacadeIface.loadAreas2(area.getAreaCode());
			if(null!=areas && areas.size()>0){
				statisticsForDanger = pipeStatisticFacadeIface.loadTroubleByNomalAndHidden(area,statistic);
				statistics = pipeStatisticFacadeIface.loadTroubleByRollcall(area,statistic);
			}else{
				setMessageKey("您选择查看的区域目前不存在下属子区域！");
				return MESSAGE_TO_BACK;
			}
		} catch (ApplicationAccessException e) {
			//e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 一般隐患列表
	 * @return
	 */
	public String loadNomalTroubleByTypeList(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if(area!=null && area.getAreaCode()!=null && area.getAreaCode()!=0l){
				area =  statisticFacadeIface.loadArea(area.getAreaCode());
			}
			daPipeNomalDangers = pipeStatisticFacadeIface.loadNomalTroubleByTypeList(statistic,pagination,area);
		} catch (ApplicationAccessException e) {
			//e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	

	/**
	 * 重大隐患列表
	 * @return
	 */
	public String loadDangerTroubleByTypeList(){
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		try {
			if(area!=null && area.getAreaCode()!=null && area.getAreaCode()!=0l){
				area =  statisticFacadeIface.loadArea(area.getAreaCode());
			}
			pipeDangers = decorationDangers(pipeStatisticFacadeIface.loadDangerTroubleByTypeList(statistic,pagination,area));
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 装饰pipeDangers
	 * @param dangers
	 * @return
	 */
	public List<DaPipeDanger> decorationDangers(List<DaPipeDanger> dangers){
		List<DaPipeDanger> daDangers_0 = new ArrayList<DaPipeDanger>();
		DaPipeDanger danger = null;
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		try{
		for(DaPipeDanger d : dangers){
			danger = new DaPipeDanger();
			danger.setId(d.getId());
			danger.setPipeLine(d.getPipeLine());
			danger.setDangerAdd(d.getDangerAdd());
			danger.setDangerNo(d.getDangerNo());
			danger.setFinishDate(d.getFinishDate());
			if(null!=d.getDaPipeDangerGorvers() && d.getDaPipeDangerGorvers().size()>0){
				List<DaPipeDangerGorver> temp = pipeStatisticFacadeIface.loadDangerGorversOfOne(danger);
				if(temp.size() > 0)
					danger.setDaPipeDangerGorver(temp.get(0));
				if(null != danger.getDaPipeDangerGorver() && null!=danger.getDaPipeDangerGorver().getFinishDate()){
					if(null!=danger.getFinishDate()){
						danger.setIsOver(isOver(df.format(danger.getFinishDate()),df.format(danger.getDaPipeDangerGorver().getFinishDate())));
					}else{
						danger.setIsOver("0");
					}
				}else{
					if(null!=danger.getFinishDate()){
						danger.setIsOver(isOver(df.format(danger.getFinishDate()),df.format(new Date())));
					}else{
						danger.setIsOver("0");
					}
				}
			}else{
				if(null!=danger.getFinishDate()){
					danger.setIsOver(isOver(df.format(danger.getFinishDate()),df.format(new Date())));
				}else{
					danger.setIsOver("0");
				}
			}
			danger.setDaPipeRollcallCompanies(d.getDaPipeRollcallCompanies());
			daDangers_0.add(danger);
		}}catch (Exception e){
			e.printStackTrace();
		}
		return daDangers_0;
	}
	
	/**
	 * 判断时间大小
	 * @param s1(当前日期或者预计完成时间)
	 * @param s2(完成治理时间)
	 * @return
	 */
	public String isOver(String s1, String s2){
		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Calendar c1=java.util.Calendar.getInstance();
		java.util.Calendar c2=java.util.Calendar.getInstance();
		try{
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2)); 
		}catch(java.text.ParseException e){
			e.printStackTrace();
		}
		int result=c1.compareTo(c2);
		if(result==0){//时间S1	=   时间S2  		未超期
			return "0";
		}else if(result<0){//时间S1	<   时间S2	超期
			return "1";
		}else{//时间S1	>   时间S2 	未超期
			return "0";
		}
	}
	
	/**
	 * 加载一个重大隐患信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadDanger2(){
		try {
			industryParameters = pipeDangerFacadeIface.loadIndustrysForDanger();
			danger = pipeDangerFacadeIface.loadDanger(danger);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个重大隐患整改信息的内容
	 * @return
	 */
	public String loadDangerGorver(){
		try {
			industryParameters = pipeDangerFacadeIface.loadIndustrysForDanger();
			danger = pipeDangerFacadeIface.loadDanger(dangerGorver.getDaPipeDanger());
			dangerGorvers = pipeDangerGorverFacadeIface.loadDangerGorversOfOne(dangerGorver);
			if (dangerGorvers.size() > 0) {
				dangerGorver = dangerGorvers.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询重大隐患挂牌列表
	 * @return
	 */
	public String loadRollcallCompanies(){
		if(pagination==null){
			pagination=new Pagination();
			pagination.setPageSize(10);
		}
		try {
			rollcallCompanies = pipeStatisticFacadeIface.loadRollcallCompanies(danger, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 加载一个重大隐患信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadDangerInfo(){
		try {
			industryParameters = pipeDangerFacadeIface.loadIndustrysForDanger();
			danger = pipeDangerFacadeIface.loadDanger(danger);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public List<DaIndustryParameter> getIndustryParameters() {
		return industryParameters;
	}

	public void setIndustryParameters(List<DaIndustryParameter> industryParameters) {
		this.industryParameters = industryParameters;
	}

	public List<FkArea> getAreas() {
		return areas;
	}

	public void setAreas(List<FkArea> areas) {
		this.areas = areas;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public DaNomalDanger getDaNomalDanger() {
		return daNomalDanger;
	}

	public void setDaNomalDanger(DaNomalDanger daNomalDanger) {
		this.daNomalDanger = daNomalDanger;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public FkArea getArea() {
		return area;
	}

	public void setArea(FkArea area) {
		this.area = area;
	}

	public DaPipeDangerGorver getDangerGorver() {
		return dangerGorver;
	}

	public void setDangerGorver(DaPipeDangerGorver dangerGorver) {
		this.dangerGorver = dangerGorver;
	}

	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(15);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setPipeDangerGorverFacadeIface(
			PipeDangerGorverFacadeIface pipeDangerGorverFacadeIface) {
		this.pipeDangerGorverFacadeIface = pipeDangerGorverFacadeIface;
	}

	public void setPipeNomalDangerFacadeIface(
			PipeNomalDangerFacadeIface pipeNomalDangerFacadeIface) {
		this.pipeNomalDangerFacadeIface = pipeNomalDangerFacadeIface;
	}

	public void setPipeStatisticFacadeIface(
			PipeStatisticFacadeIface pipeStatisticFacadeIface) {
		this.pipeStatisticFacadeIface = pipeStatisticFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public void setPipeDangerFacadeIface(PipeDangerFacadeIface pipeDangerFacadeIface) {
		this.pipeDangerFacadeIface = pipeDangerFacadeIface;
	}

	public void setUserFacadeIface(UserFacadeIface userFacadeIface) {
		this.userFacadeIface = userFacadeIface;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getJdata() {
		return jdata;
	}

	public void setJdata(String jdata) {
		this.jdata = jdata;
	}

	public void setCompanyQuarterReportFacadeIface(
			CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface) {
		this.companyQuarterReportFacadeIface = companyQuarterReportFacadeIface;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
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

	public List<DaPipeDanger> getPipeDangers() {
		return pipeDangers;
	}

	public void setPipeDangers(List<DaPipeDanger> pipeDangers) {
		this.pipeDangers = pipeDangers;
	}

	public List<DaPipeRollcallCompany> getRollcallCompanies() {
		return rollcallCompanies;
	}

	public void setRollcallCompanies(List<DaPipeRollcallCompany> rollcallCompanies) {
		this.rollcallCompanies = rollcallCompanies;
	}

	public DaPipeDanger getDanger() {
		return danger;
	}

	public void setDanger(DaPipeDanger danger) {
		this.danger = danger;
	}

	public List<DaPipeDangerGorver> getDangerGorvers() {
		return dangerGorvers;
	}

	public void setDangerGorvers(List<DaPipeDangerGorver> dangerGorvers) {
		this.dangerGorvers = dangerGorvers;
	}

	public List<DaPipeNomalDanger> getDaPipeNomalDangers() {
		return daPipeNomalDangers;
	}

	public void setDaPipeNomalDangers(List<DaPipeNomalDanger> daPipeNomalDangers) {
		this.daPipeNomalDangers = daPipeNomalDangers;
	}
}
