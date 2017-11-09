package com.safetys.nbyhpc.web.action.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.FkAreaFacadeIface;
import com.safetys.nbyhpc.facade.iface.SeasonReportFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.mobile.vo.CompanyVo;

public class AdCompanyAction extends AndroidBaseAction {

	private static final long serialVersionUID = 6501764762205828504L;

	private CompanyFacadeIface companyFacadeIface;
	
	private FkAreaFacadeIface fkAreaFacadeIface;
	
	private SeasonReportFacadeIface seasonReportFacadeIface;
	
	private DaCompany company;
	
	private CompanyVo companyVo;
	
	/**
	 * 
	 * @return
	 */
	public String list() {
		try {
			if (isAuthKeyValidate()) {
				List<DaCompany> list = seasonReportFacadeIface.companiseAffirmToSeasonIsExist(company, getPagination(), getUserDetail());
				List<CompanyVo> companyVoList = new ArrayList<CompanyVo>();
				if (list != null) {
					for (DaCompany c : list) {
						CompanyVo vo = new CompanyVo();
						vo.buildByCompany(c, false);
						companyVoList.add(vo);
					}
				}
				jsonResult.setData(companyVoList);
				jsonResult.setTotalCount(getPagination().getTotalCount());
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	/**
	 * params : company.id
	 * 企业基本信息
	 * @return
	 */
	public String show() {
		try {
			if (isAuthKeyValidate()) {
				company = companyFacadeIface.loadCompany(company);
				// 获得经济类型大类
				List<FkEnum> economyKindEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.ECONOMYKIND);
				// 经济类型大类不为空的情况下，查找子类的经济类型
				List<FkEnum> childEconomyKindEnums = companyFacadeIface.getFkEnumByParentIds(economyKindEnums);
				// 获得国民经济分类大类
				List<FkEnum> nationalEconomicEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.NATIONAL_ECONOMIC);
				// 国民经济分类大类不为空的情况下，查找二级国民经济分类
				List<FkEnum> childNationalEconomicEnums = companyFacadeIface.getFkEnumByParentIds(nationalEconomicEnums);
				// 获得企业规模
				List<FkEnum> companyScaleEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.COMPANYSCALE);
				Map<String, Object> data = new HashMap<String, Object>();
				companyVo = new CompanyVo();
				companyVo.buildByCompany(company, true);
				if (companyVo.getFirstArea() != null && companyVo.getFirstArea() > 0) {
					companyVo.setFirstAreaName(fkAreaFacadeIface.findByCode(companyVo.getFirstArea()).getAreaName());
				}
				if (companyVo.getSecondArea() != null && companyVo.getSecondArea() > 0) {
					companyVo.setSecondAreaName(fkAreaFacadeIface.findByCode(companyVo.getSecondArea()).getAreaName());
				}
				if (companyVo.getThirdArea() != null && companyVo.getThirdArea() > 0) {
					companyVo.setThirdAreaName(fkAreaFacadeIface.findByCode(companyVo.getThirdArea()).getAreaName());
				}
				if (companyVo.getFouthArea() != null && companyVo.getFouthArea() > 0) {
					companyVo.setFouthAreaName(fkAreaFacadeIface.findByCode(companyVo.getFouthArea()).getAreaName());
				}
				data.put("company", companyVo);
				data.put("economyKindEnums", buildMaintenance(economyKindEnums));
				data.put("childEconomyKindEnums", buildMaintenance(childEconomyKindEnums));
				data.put("nationalEconomicEnums", buildMaintenance(nationalEconomicEnums));
				data.put("childNationalEconomicEnums", buildMaintenance(childNationalEconomicEnums));
				data.put("companyScaleEnums", buildMaintenance(companyScaleEnums));
				jsonResult.setData(data);
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	/**
	 * params : companyVo.xx
	 * @return
	 */
	public String save() {
		try {
			if (isAuthKeyValidate()) {
				company = new DaCompany();
				company.setId(companyVo.getId());
				company = companyFacadeIface.loadCompany(company);
				company.buildByCompanyVo(companyVo);
				companyFacadeIface.updateCompany(company);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public CompanyVo getCompanyVo() {
		return companyVo;
	}

	public void setCompanyVo(CompanyVo companyVo) {
		this.companyVo = companyVo;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public void setFkAreaFacadeIface(FkAreaFacadeIface fkAreaFacadeIface) {
		this.fkAreaFacadeIface = fkAreaFacadeIface;
	}

	public void setSeasonReportFacadeIface(SeasonReportFacadeIface seasonReportFacadeIface) {
		this.seasonReportFacadeIface = seasonReportFacadeIface;
	}

}
