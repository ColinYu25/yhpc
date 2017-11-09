package com.safetys.nbyhpc.web.action.summary;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.HiddenSummary;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.HiddenSummaryFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.vo.HiddenVo;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * 隐患汇总
 * @author yangb
 *
 */
public class HiddenSummaryAction extends DaAppAction{

	private static final long serialVersionUID = -6262253744984345608L;

	private static final String LIST = "list";
	private static final String REPORT = "report";
	private static final String VIEW = "view";
	
	private HiddenSummaryFacadeIface hiddenSummaryFacadeIface;
	private CompanyFacadeIface companyFacadeIface;
	
	private HiddenSummary entity;
	
	private List<HiddenSummary> hiddenSummaryList;
	
	private HiddenVo hiddenVo;
	
	private String reportName;
	
	protected Pagination pagination;// 分页对象
	
	private int backNum; //因为点击页面搜索按钮会刷新页面，所以记录要退回的次数
	
	public String list() {
		backNum++;
		hiddenSummaryList = hiddenSummaryFacadeIface.findByHiddenVo(hiddenVo, getPagination());
		if (hiddenSummaryList != null) {
			// 获得隐患分类大类
			List<DaIndustryParameter> parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
			JSONObject jo = buildJson(parentDaIndustryParameters);
			// 获得隐患分类子类
//			List<DaIndustryParameter> childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
			for (HiddenSummary hiddenSummary : hiddenSummaryList) {
				if (StringUtils.isNotBlank(hiddenSummary.getType1())) {
					hiddenSummary.setType1Text(jo.getString(hiddenSummary.getType1()));
				}
			}
		}
		return LIST;
	}
	
	public String view() {
		entity = hiddenSummaryFacadeIface.findById(entity.getId());
		if (StringUtils.isNotBlank(entity.getType1())) {
			List<DaIndustryParameter> parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
			for (DaIndustryParameter daIndustryParameter : parentDaIndustryParameters) {
				if (daIndustryParameter.getCode().equals(entity.getType1())) {
					entity.setType1Text(daIndustryParameter.getName());
				}
			}
			if (StringUtils.isNotBlank(entity.getType2())) {
				List<DaIndustryParameter> childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
				for (DaIndustryParameter daIndustryParameter : childDaIndustryParameters) {
					if (daIndustryParameter.getCode().equals(entity.getType2())) {
						entity.setType2Text(daIndustryParameter.getName());
					}
				}
			}
		}
		return VIEW;
	}
	
	public String report() {
		reportName = "nbyhpc/hiddenSummary.cpt";
		return REPORT;
	}
	
	public String chart() {
		reportName = "nbyhpc/hiddenSummaryChart.cpt";
		return REPORT;
	}
	
	public JSONObject buildJson(List<DaIndustryParameter> list) {
		JSONObject jo = new JSONObject();
		for (DaIndustryParameter daIndustryParameter : list) {
			jo.put(daIndustryParameter.getCode(), daIndustryParameter.getName());
		}
		return jo;
	}

	public List<HiddenSummary> getHiddenSummaryList() {
		return hiddenSummaryList;
	}

	public void setHiddenSummaryList(List<HiddenSummary> hiddenSummaryList) {
		this.hiddenSummaryList = hiddenSummaryList;
	}

	public HiddenVo getHiddenVo() {
		return hiddenVo;
	}

	public void setHiddenVo(HiddenVo hiddenVo) {
		this.hiddenVo = hiddenVo;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Pagination getPagination() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public void setHiddenSummaryFacadeIface(HiddenSummaryFacadeIface hiddenSummaryFacadeIface) {
		this.hiddenSummaryFacadeIface = hiddenSummaryFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public HiddenSummary getEntity() {
		return entity;
	}

	public void setEntity(HiddenSummary entity) {
		this.entity = entity;
	}

	public int getBackNum() {
		return backNum;
	}

	public void setBackNum(int backNum) {
		this.backNum = backNum;
	}

}
