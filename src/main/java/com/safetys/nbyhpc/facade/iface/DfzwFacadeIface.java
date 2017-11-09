/*
 * Copyright (c) 2002-2011 by ZheJiang AnSheng Information Technology Co.,Ltd.
 * All rights reserved.
 */
package com.safetys.nbyhpc.facade.iface;

import java.util.Date;
import java.util.List;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.model.ZjDfzwReportDetail;
import com.safetys.nbyhpc.web.axis2.model.DfzwReport;
import com.safetys.nbyhpc.web.axis2.model.DfzwReportDetail;

/**
 * 打非治违周报表业务层
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public interface DfzwFacadeIface { 

	public void create(ZjDfzwReport entity, List<ZjDfzwReportDetail> details) throws Exception;

	public List<ZjDfzwReport> loadByUser(ZjDfzwReport entity) throws Exception;

	public List<ZjDfzwReport> load(ZjDfzwReport entity, Pagination pagination) throws Exception;
	
	public List<ZjDfzwReport> loadForCitySelf(ZjDfzwReport entity, Pagination pagination) throws Exception;
	
	/**
	 * 市级填报(各个部门只能查看各个市级部门填报的数据)
	 */
	public List<ZjDfzwReport> loadForCity(ZjDfzwReport entity, Pagination pagination) throws Exception;

	public List<ZjDfzwReportDetail> loadDetails(Long id, Pagination pagination) throws Exception;

	public List<ZjDfzwReport> loadByCity(FkUser fkUser) throws Exception;

	public ZjDfzwReport load(long id) throws ApplicationAccessException;

	public void update(ZjDfzwReport entity, List<ZjDfzwReportDetail> details) throws Exception;

	public void deleteDetails(Long id) throws Exception;

	public void deleteDetail(ZjDfzwReportDetail detail) throws Exception;

	public void delete(ZjDfzwReport entity) throws Exception;

	public void update(ZjDfzwReport entity) throws Exception;

	public boolean is_all_reported(String areaType, Long areaCode, ZjDfzwReport entity);

	public List<ZjDfzwReportDetail> loadDetailsByUser(ZjDfzwReport entity) throws Exception;
	
	public List<ZjDfzwReportDetail> loadDetailsByDfzw(ZjDfzwReport entity) throws Exception;

	public List<ZjDfzwReportDetail> loadDetailsForProvince(ZjDfzwReport entity) throws Exception;
	
	public List<ZjDfzwReportDetail> loadDetailsForCityInit(ZjDfzwReport entity) throws Exception;

	public List<FkArea> loadCityAreas(Long cityAreaCode) throws ApplicationAccessException;
	
	public void loadDetailsSetRollback(ZjDfzwReport entity) throws Exception;
	
	public int getIsAllReport();
	
	public int isReport(String roleDepic,Date reportDate) throws Exception;

	/**
	 * 省级查看县级上报列表
	 * 
	 * @param mineReport
	 * @param pagination
	 * @return
	 */
	public List<ZjDfzwReport> loadCountyReports(ZjDfzwReport entity, Pagination pagination)
			throws ApplicationAccessException;

	/**
	 * 检测用户是否可以进行创建数据的操作，主要防止同一时间，创建了多次数据
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate(ZjDfzwReport entity) throws ApplicationAccessException;
	
	/**
	 * 检测用户是否可以进行创建数据的操作，主要防止同一时间，创建了多次数据
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate1(ZjDfzwReport entity) throws ApplicationAccessException;
	
	/**
	 * 检测用户是否可以进行创建数据的操作，主要防止同一时间，创建了多次数据
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate2(ZjDfzwReport entity) throws ApplicationAccessException;
	
	public boolean checkAllowCreate3(ZjDfzwReport entity) throws ApplicationAccessException;
	
	public List<DfzwReportDetail> loadDetailsForAxis2(Long areaCode, ZjDfzwReport entity) throws Exception;
	
	/**
	 * 由于统计上报--》市级统计只查看安委办的数据，所以新增方法，只返回安委办汇总的打非治违信息。
	 * 
	 * @author huangjl
	 * @since 2015-5-5
	 * @param entity
	 * @param pagination
	 * @return List<ZjDfzwReport>
	 * @throws Exception
	 */
	public List<ZjDfzwReport> loadCityReport(ZjDfzwReport entity, Pagination pagination) throws Exception;
}
