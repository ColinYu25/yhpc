package com.safetys.nbyhpc.web.action.mobile;

import java.util.ArrayList;
import java.util.List;

import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.web.action.mobile.vo.MaintenanceVo;


/**
 * 手机APP后台
 * @author yangb
 *
 */
public abstract class MBaseAction extends BaseAppAction {

	private static final long serialVersionUID = -6789069839726905811L;

	protected List<MaintenanceVo> buildMaintenance(List<FkEnum> fkEnumList) {
		List<MaintenanceVo> list = new ArrayList<MaintenanceVo>();
		for (FkEnum fkEnum : fkEnumList) {
			MaintenanceVo vo = new MaintenanceVo();
			vo.setId(fkEnum.getId());
			vo.setFatherId(fkEnum.getFatherId());
			vo.setCode(fkEnum.getEnumCode());
			vo.setName(fkEnum.getEnumName());
			list.add(vo);
		}
		return list;
	}
	
	protected List<MaintenanceVo> buildMaintenance4IP(List<DaIndustryParameter> industryParameterList) {
		List<MaintenanceVo> list = new ArrayList<MaintenanceVo>();
		for (DaIndustryParameter ip : industryParameterList) {
			MaintenanceVo vo = new MaintenanceVo();
			vo.setId(ip.getId());
			DaIndustryParameter father = ip.getDaIndustryParameter();
			if (father != null) {
				vo.setFatherId(father.getId());
			}
			vo.setCode(ip.getCode());
			vo.setName(ip.getName());
			list.add(vo);
		}
		return list;
	}
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
//		System.out.println("URL =.= " + getRequest().getRequestURI());
//		Enumeration names = getRequest().getParameterNames();
//		System.out.println("===========================================");
//		while (names.hasMoreElements()) {
//			String name = (String) names.nextElement();
//			System.out.println(name + " =.= " + getRequest().getParameter(name));
//		}
//		System.out.println("===========================================");
	}

}
