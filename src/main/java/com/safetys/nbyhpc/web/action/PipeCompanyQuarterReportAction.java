package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeCompanyQuarterReprot;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeCompanyQuarterReportFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class PipeCompanyQuarterReportAction extends DaAppAction{
	
	/**
	 * 管道季报
	 */
	private static final long serialVersionUID = -5062251518050087562L;

	private PipeCompanyQuarterReportFacadeIface pipeCompanyQuarterReportFacadeIface;
    
	private DaCompany company;
	
	private DaPipeCompanyQuarterReprot entity;
	
	private PipeLineFacadeIface pipeLineFacadeIface;
	
	private List result;
	
	private Statistic statistic;
	
	private Statistic statisticForCompany;
	
	private CompanyFacadeIface companyFacadeIface;
	
	private String reportDate;
	
	private Pagination pagination;//分页对象
	
	public String editInit(){
		try {
			if(statistic == null){
				statistic = new Statistic();
			}
			if(company == null){
				company = new DaCompany();
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        if(statistic.getYear() == 0){
	        	Calendar cal = Calendar.getInstance();
	        	statistic.setYear(cal.get(Calendar.YEAR));
	        	String mDateTime=formatter.format(cal.getTime());
	        	if(mDateTime.compareTo(statistic.getYear()+"-01-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-04-01")<0){
	        		statistic.setQuarter(1);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-04-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-07-01")<0){
	            	statistic.setQuarter(2);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-07-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-10-01")<0){
	            	statistic.setQuarter(3);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-10-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-12-31")<=0){
	            	statistic.setQuarter(4);
	            }
	        }
        	statistic.setIsAllYear(0);
        	statisticForCompany = pipeCompanyQuarterReportFacadeIface.loadReportByCompany(getUserDetail(),statistic);
        	if (statisticForCompany != null) {
        		company.setId(statisticForCompany.getCompanyId());
        		company = companyFacadeIface.loadCompany(company);
        		statistic.setIsReport(pipeCompanyQuarterReportFacadeIface.checkIsSaveed(statistic, statisticForCompany.getCompanyId()));
        	}
        	reportDate = formatter.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String editAllInit(){
		try {
			if(statistic == null){
				statistic = new Statistic();
			}
	        if(statistic.getYear() == 0){
	        	Calendar cal = Calendar.getInstance();
	        	statistic.setYear(cal.get(Calendar.YEAR));
	        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        	String mDateTime=formatter.format(cal.getTime());
	        	if(mDateTime.compareTo(statistic.getYear()+"-01-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-04-01")<0){
	        		statistic.setQuarter(1);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-04-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-07-01")<0){
	            	statistic.setQuarter(2);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-07-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-10-01")<0){
	            	statistic.setQuarter(3);
	            }else if(mDateTime.compareTo(statistic.getYear()+"-10-01")>=0 && mDateTime.compareTo(statistic.getYear()+"-12-31")<=0){
	            	statistic.setQuarter(4);
	            }
	        }
        	statistic.setIsAllYear(1);
        	statisticForCompany = pipeCompanyQuarterReportFacadeIface.loadReportByCompany(getUserDetail(),statistic);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String save() throws Exception {
		try {
			pipeCompanyQuarterReportFacadeIface.save(entity);
			this.setMessageKey("上报成功!");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMessageKey("上报失败，请重新上报!");
		}
		this.getRequest().setAttribute("url", "editInit.xhtml?statistic.year=" + entity.getYear() + "&statistic.quarter=" + entity.getQuarter());
		return MESSAGE_TO_REDIRECT;
	}
	
	/**
	 * 读取已上报季报列表
	 * @author lisl
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		if(company == null){
			company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
		}
		if(entity == null){
			entity = new DaPipeCompanyQuarterReprot();
			Calendar cal = Calendar.getInstance();
			entity.setYear(cal.get(Calendar.YEAR));
		}
		try {
			result = pipeCompanyQuarterReportFacadeIface.find(entity, company, getPagination());
		} catch (Exception e) {
			////e.printStackTrace();
			LOG.error("读取已上报季报列表出错！", e);
			return ERROR;
		}
		return SUCCESS;
	}

	public void setEntity(DaPipeCompanyQuarterReprot entity) {
		this.entity = entity;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public DaPipeCompanyQuarterReprot getEntity() {
		return entity;
	}

	public void setPipeCompanyQuarterReportFacadeIface(
			PipeCompanyQuarterReportFacadeIface pipeCompanyQuarterReportFacadeIface) {
		this.pipeCompanyQuarterReportFacadeIface = pipeCompanyQuarterReportFacadeIface;
	}

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public Statistic getStatisticForCompany() {
		return statisticForCompany;
	}

	public void setStatisticForCompany(Statistic statisticForCompany) {
		this.statisticForCompany = statisticForCompany;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
}
