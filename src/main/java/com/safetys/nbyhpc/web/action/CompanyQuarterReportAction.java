package com.safetys.nbyhpc.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.safetys.constant.SystemConstant;
import cn.safetys.sync.mq.SyncTriggerService;

import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyQuarterReprot;
import com.safetys.nbyhpc.facade.iface.CompanyQuarterReportFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class CompanyQuarterReportAction extends DaAppAction {

	/**
	 * 企业季报
	 */
	private static final long serialVersionUID = -5062251518050087562L;

	private CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface;

	private DaCompany company;

	private DaCompanyQuarterReprot companyQuarterReprot;

	private NomalDangerFacadeIface nomalDangerFacadeIface;

	private DaCompanyQuarterReprot entity;

	private List<DaCompanyQuarterReprot> companyQuarterReprots;

	private SyncTriggerService syncTriggerService;
	private SystemConstant systemConstant;
	
	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		String msg = "success";

		Date reportDate = new SimpleDateFormat("yyyy-MM-dd").parse(entity.getReportDate1());
		
		entity.setReportDate(reportDate);
		try {
			entity.setIsSynchro(1);
			companyQuarterReportFacadeIface.save(entity);
			
			if(systemConstant.isDataSeparation()&&entity.getId()!=null&&entity.getId()>0){
				if(systemConstant.isEnterprise()){
					syncTriggerService.updateQuarterReportEs(entity.getId());
				}
			}
		} catch (Exception e) {
			msg = "fail";
			e.printStackTrace();
			LOG.error("季报汇总出错", e);
		}
		response.getWriter().write(msg);
		return NONE;
	}

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

	public void setCompanyQuarterReportFacadeIface(CompanyQuarterReportFacadeIface companyQuarterReportFacadeIface) {
		this.companyQuarterReportFacadeIface = companyQuarterReportFacadeIface;
	}

	public DaCompanyQuarterReprot getEntity() {
		return entity;
	}

	public void setEntity(DaCompanyQuarterReprot entity) {
		this.entity = entity;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public DaCompanyQuarterReprot getCompanyQuarterReprot() {
		return companyQuarterReprot;
	}

	public void setCompanyQuarterReprot(DaCompanyQuarterReprot companyQuarterReprot) {
		this.companyQuarterReprot = companyQuarterReprot;
	}

	public void setNomalDangerFacadeIface(NomalDangerFacadeIface nomalDangerFacadeIface) {
		this.nomalDangerFacadeIface = nomalDangerFacadeIface;
	}

	public List<DaCompanyQuarterReprot> getCompanyQuarterReprots() {
		return companyQuarterReprots;
	}

	public void setCompanyQuarterReprots(List<DaCompanyQuarterReprot> companyQuarterReprots) {
		this.companyQuarterReprots = companyQuarterReprots;
	}

	public void setSyncTriggerService(SyncTriggerService syncTriggerService) {
		this.syncTriggerService = syncTriggerService;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}
	
	
}
