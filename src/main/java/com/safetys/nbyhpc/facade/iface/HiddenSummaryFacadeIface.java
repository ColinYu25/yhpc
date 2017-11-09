package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import net.sf.json.JSONObject;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.HiddenSummary;
import com.safetys.nbyhpc.vo.HiddenVo;

public interface HiddenSummaryFacadeIface extends BaseFacadeIface<HiddenSummary> {

	public List<HiddenSummary> findByHiddenVo(HiddenVo hiddenVo, Pagination page);
	
	/**
	 * 把中心库网格化，标准化，隐患系统的隐患更新到隐患汇总表
	 * @param jsonData
	 */
	public void updateFromCenterDB(String fromTable, JSONObject jsonData);
	
	/**
	 * 把中心库执法的隐患同步到隐患系统
	 * @param jsonData
	 */
	public void updateFromCenterDB4Xzzf(JSONObject jsonData);

}
