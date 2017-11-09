package com.safetys.nbyhpc.web.action.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.mobile.util.AuthKeyGenerator;
import com.safetys.nbyhpc.util.Nbyhpc;
import com.safetys.nbyhpc.web.action.mobile.vo.DaNormalDangerVo;
import com.safetys.nbyhpc.web.action.mobile.vo.DangerImageVo;

/**
 * 一般隐患
 * @author yangb
 *
 */
public class AdNormalDangerAction extends AndroidBaseAction {

	private static final long serialVersionUID = 3996880460410120982L;

	private NomalDangerFacadeIface nomalDangerFacadeIface;
	private CompanyFacadeIface companyFacadeIface;
	private DaDangerImageFacadeIface daDangerImageFacadeIface;
	
	private DaNomalDanger daNomalDanger;
	
	private DaCompany company;
	
	private List<File> upFiles = new ArrayList<File>();
	
	private String deletedFileIds;
	
	public String list() {
		try {
			if (isAuthKeyValidate()) {
				company = companyFacadeIface.loadCompany(company);
				if (daNomalDanger == null) {
					daNomalDanger = new DaNomalDanger();
				}
				daNomalDanger.setRepair(2);//查询所有的，暂时
				List<FkEnum> hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
				List<DaNomalDanger> daNomalDangers = nomalDangerFacadeIface.loadNomalDangers(company, getPagination(), daNomalDanger, getUserDetail());
				List<DaNormalDangerVo> dangerList = new ArrayList<DaNormalDangerVo>();
				for (DaNomalDanger daNomalDanger : daNomalDangers) {
					DaNormalDangerVo vo = new DaNormalDangerVo();
					vo.buildByNormalDanger(daNomalDanger, hazardSourceEnums, getUserId());
					dangerList.add(vo);
				}
				jsonResult.setData(dangerList);
				jsonResult.setTotalCount(getPagination().getTotalCount());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	public String show() {
		try {
			if (isAuthKeyValidate()) {
				// 获得隐患来源枚举类型
				List<FkEnum> hazardSourceEnums = companyFacadeIface.getEnumByParentCode(Nbyhpc.SYS_HAZARD_SOURCE);
				// 获得隐患分类大类
				List<DaIndustryParameter> parentDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentDepiction(Nbyhpc.NEW_NOMAL_DANGER_TYPE);
				// 获得隐患分类子类
				List<DaIndustryParameter> childDaIndustryParameters = companyFacadeIface.getDaIndustryParameterByParentIds(parentDaIndustryParameters);
				Map<String, Object> data = new HashMap<String, Object>();
				if (daNomalDanger != null && daNomalDanger.getId() > 0) {
					DaNormalDangerVo vo = new DaNormalDangerVo();
					daNomalDanger = nomalDangerFacadeIface.loadNomalDanger(daNomalDanger);
					vo.buildByNormalDanger(daNomalDanger, hazardSourceEnums, getUserId());
					vo.setCompanyName(daNomalDanger.getDaCompanyPass().getDaCompany().getCompanyName());
					List<DaDangerImage> imageList = daDangerImageFacadeIface.findByNormalDanger(daNomalDanger);
					List<DangerImageVo> list = new ArrayList<DangerImageVo>();
					for (DaDangerImage daDangerImage : imageList) {
						DangerImageVo dangerImageVo = new DangerImageVo();
						dangerImageVo.buildByDangerImage(daDangerImage);
						list.add(dangerImageVo);
					}
					vo.setImageList(list);
					data.put("daNomalDanger", vo);
				}
				data.put("hazardSourceEnums", buildMaintenance(hazardSourceEnums));
				data.put("parentDaIndustryParameters", buildMaintenance4IP(parentDaIndustryParameters));
				data.put("childDaIndustryParameters", buildMaintenance4IP(childDaIndustryParameters));
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
	 * 注意这里修改只能是谁创建，谁修改
	 * description
	 * hazardSourceCode
	 * secondType
	 * governMoney
	 * repaired
	 * type
	 * @return
	 */
	public String save() {
		try {
			if (isAuthKeyValidate()) {
				Long userId = AuthKeyGenerator.extractId(super.getAuthKey());
				company = companyFacadeIface.loadCompany(company);
				nomalDangerFacadeIface.saveNormalDanger(daNomalDanger, userId, company, upFiles, deletedFileIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	public String delete() {
		try {
			if (isAuthKeyValidate()) {
				nomalDangerFacadeIface.deleteNormalDanger(String.valueOf(daNomalDanger.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}

	public DaNomalDanger getDaNomalDanger() {
		return daNomalDanger;
	}

	public void setDaNomalDanger(DaNomalDanger daNomalDanger) {
		this.daNomalDanger = daNomalDanger;
	}

	public void setNomalDangerFacadeIface(NomalDangerFacadeIface nomalDangerFacadeIface) {
		this.nomalDangerFacadeIface = nomalDangerFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List<File> getUpFiles() {
		return upFiles;
	}

	public void setUpFiles(List<File> upFiles) {
		this.upFiles = upFiles;
	}

	public String getDeletedFileIds() {
		return deletedFileIds;
	}

	public void setDeletedFileIds(String deletedFileIds) {
		this.deletedFileIds = deletedFileIds;
	}
	
}
