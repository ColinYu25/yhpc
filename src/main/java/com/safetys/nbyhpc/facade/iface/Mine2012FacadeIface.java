package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
import com.safetys.nbyhpc.domain.model.ZjStatistic;
import com.safetys.nbyhpc.util.State;

/**
 * 2012年新表格样式（只读）
 * 
 * author lih
 * 
 * @since 2012-6-18
 * @version 1.0.0
 */
public interface Mine2012FacadeIface extends MineFacadeIface {

	/**
	 * 根据主表获取子表集合（并经过部分处理，返回期望结果）
	 * 
	 * @author lih
	 * @since 2012-6-18
	 * @param mineReport
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsByMine(ZjMineReport mineReport, Pagination pagination)
			throws ApplicationAccessException;

	/**
	 * 
	 * 获取子表统计后的结果（市级）
	 * @author lih
	 * @since 2012-6-18
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException;

	/**
	 * 获取子表统计后的结果（省级）
	 * @author lih
	 * @since 2012-6-18
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsForProvince(ZjMineReport mineReport)
			throws ApplicationAccessException;

	/**
	 * 获取重大隐患的统计结果
	 * @author lih
	 * @since 2012-6-18
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjStatistic> loadStatisticForMine(ZjMineReport mineReport) throws ApplicationAccessException;

	/**
	 * 获取矿山行业集合
	 * @author lih
	 * @since 2012-6-18
	 * @return
	 */
	public List<State> getMineTypeMap();

	/**
	 * 获取其他行业集合
	 * @author lih
	 * @since 2012-6-18
	 * @return
	 */
	public List<State> getOtherTypeMap();

}
