package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjMineReportDetail;
/**
 * @(#)MineAction.java  
 * Date 2008-9-16
 * Copyright (c) 2008 Safetys.cn.
 * @author lihu
 *
 */
public interface MineDetailFacadeIface {
	/**
	 * 创建一个矿山或其他的详细信息，并返回创建记录的序号
	 * @param mineReportDetail
	 * @return long
	 * @throws ApplicationAccessException
	 */
	public long createMineReportDetail(ZjMineReportDetail mineReportDetail) 
			throws ApplicationAccessException;
	
	/**
	 * 修改矿山或其他的详细信息
	 * @param mineReportDetail
	 * @throws ApplicationAccessException
	 */
	public void updateMineReportDetail(ZjMineReportDetail mineReportDetail)
			throws ApplicationAccessException;
	
	/**
	 * 删除矿山或其他的详细信息
	 * @param mineReportDetail
	 * @throws ApplicationAccessException
	 */
	public void deleteMineReportDetail(ZjMineReportDetail mineReportDetail)
			throws ApplicationAccessException;
	
	/**
	 * 根据某种条件（例如：搜索）查询矿山详细表的记录集合
	 * @param mineReportDetail
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReportDetail> loadMineReportDetails(ZjMineReportDetail mineReportDetail,
				Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 根据序号去查询出一条矿山详细记录
	 * @param id
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjMineReportDetail loadMineReportDetail(long id) 
			throws ApplicationAccessException;

	/**
	 * 根据页面传入的集合和矿山表的关联序号批量创建矿山详细记录
	 * @param mineReportDetails
	 * @param id
	 * @throws ApplicationAccessException
	 */
	public void createMineReportDetails(List<ZjMineReportDetail> mineReportDetails, long id)
	 		throws ApplicationAccessException;

	/**
	 * 根据矿山表的相应序号获取矿山详细表的记录集合
	 * @param id
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsByMine(Long id, Pagination pagination)
			throws ApplicationAccessException;
	
	/**
	 * 根据矿山表的相应序号批量删除矿山详细表的对应集合
	 * @param id
	 */
	public void deleteMineReportDetailsByMine(Long id)
			throws ApplicationAccessException;
	
	/**
	 * 根据用户查看统计的矿山详细信息集合
	 * @param mineReport
	 * @return
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException;

	/**
	 * 市级用户上报，修改县级数据上报状态
	 * @param user
	 */
	public void doneCityReport(ZjMineReport mineReport) throws ApplicationAccessException;

	/**
	 * 获取省级上报数据统计
	 * @param mineReport
	 * @return
	 */
	public List<ZjMineReportDetail> loadMineReportDetailsForProvince(ZjMineReport mineReport)
			throws ApplicationAccessException;
}
