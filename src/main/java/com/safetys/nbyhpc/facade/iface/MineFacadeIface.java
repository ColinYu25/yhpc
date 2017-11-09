package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
/**
 * @(#)MineAction.java  
 * Date 2008-9-16
 * Copyright (c) 2008 Safetys.cn.
 * @author lihu
 *
 */
public interface MineFacadeIface {
	
	/**
	 * 创建一条矿山记录
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public long createMineReport(ZjMineReport mineReport) 
			throws ApplicationAccessException;
	
	/**
	 * 修改一个矿山记录
	 * @param mineReport
	 * @throws ApplicationAccessException
	 */
	public void updateMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException;
	
	/**
	 * 删除一个矿山记录
	 * @param mineReport
	 * @throws ApplicationAccessException
	 */
	public void deleteMineReport(ZjMineReport mineReport)
			throws ApplicationAccessException;
	
	public ZjMineReport loadMineReports(Long areaCode, String reportTime, Integer type) throws ApplicationAccessException;
	
	public ZjMineReport loadMineReports1(Long areaCode, String reportTime, Integer type) throws ApplicationAccessException;
	
	/**
	 * 根据条件查询矿山记录集合，并分页
	 * @param mineReport
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReport> loadMineReports(ZjMineReport mineReport,
				Pagination pagination) throws ApplicationAccessException;
	
	public List<ZjMineReport> loadMineReports(ZjMineReport mineReport,
			Pagination pagination,boolean country) throws ApplicationAccessException;
	
	/**
	 * 根据矿山序号获取一条矿山记录信息
	 * @param id
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjMineReport loadMineReport(long id) 
			throws ApplicationAccessException;
	
	/**
	 * 根据用户获取所有矿山或其他记录
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjMineReport> loadMineReportsByUser(ZjMineReport mineReport)
			throws ApplicationAccessException;

	/**
	 * 省级查看县级上报列表
	 * @param mineReport
	 * @param pagination
	 * @return
	 */
	public List<ZjMineReport> loadCountyReports(ZjMineReport mineReport, Pagination pagination) throws ApplicationAccessException;
	/**
	 * @author seak.lv
	 * @modified:2008-9-30
	 * @depiction:判断下一级的区域用户是否已经都上报
	 * @param areaType 判断哪一级是否上报
	 * @param areaType 当前用户的行政区划代码
	 */
	public boolean is_all_reported(String areaType,Long areaCode,ZjMineReport mineReport);
	
	 public List<FkArea> loadAreas(Long fatherId, Pagination pagination) throws ApplicationAccessException;
	
	public List<FkArea> loadCityAreas(Long cityAreaCode) throws ApplicationAccessException;

	/**
	 * 根据父区域的区域代码获取子区域集合
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadChildAreas(Long fatherAreaCode) throws ApplicationAccessException;

	/**
	 * 根据用户区域查找出对应的记录
	 * @param fkUser
	 * @param reportMonth
	 * @return
	 */
	public List<ZjMineReport> loadMineReportByCityAndMonth(FkUser fkUser, Integer type) 
		throws ApplicationAccessException;
	
	/**
	 * 根据重大隐患获取其对应的矿山表记录
	 * @param bigTrouble
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjMineReport loadMineReportByBigTrouble(ZjBigTrouble bigTrouble) throws ApplicationAccessException;
	
	
	 /**
	 * 根据gradePath查询符合要求的区域
	 * 
	 * @param gradePath
	 *        层级结构
	 * @return
	 * @throws ApplicationAccessException
	 */
     public  List<FkArea> loadAreasByGradePath(String gradePath) throws ApplicationAccessException;
     
     /**
		 * 根据区域代码和级别显示这个地区不同区域级别的所有区域
		 * 
		 * @param areaCode
		 *        地区代码
		 * @param grade
		 *        区域级别
		 * @return
		 * @throws ApplicationAccessException
		 */
	 public  List<FkArea> loadAreasByGrade(Long areaCode,String grade) throws ApplicationAccessException;
}
