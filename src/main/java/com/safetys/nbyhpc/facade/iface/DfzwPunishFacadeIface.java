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
import com.safetys.nbyhpc.domain.model.ZjDfzwPunishDetail;
import com.safetys.nbyhpc.web.axis2.model.DfzwPunish;
import com.safetys.nbyhpc.web.axis2.model.DfzwPunishDetail;
import com.safetys.nbyhpc.web.axis2.model.DfzwReport;
import com.safetys.nbyhpc.web.axis2.model.DfzwReportDetail;

/**
 * 打非治违处罚周报表业务层
 * 
 * author lih
 * 
 * @since 2012-5-10
 * @version 1.0.0
 */
public interface DfzwPunishFacadeIface { 

	public void create(ZjDfzwPunish entity, List<ZjDfzwPunishDetail> details) throws Exception;

	public List<ZjDfzwPunish> loadByUser(ZjDfzwPunish entity) throws Exception;

	public List<ZjDfzwPunish> load(ZjDfzwPunish entity, Pagination pagination) throws Exception;

	public List<ZjDfzwPunish> loadForCity(ZjDfzwPunish entity, Pagination pagination) throws Exception;

	public List<ZjDfzwPunishDetail> loadDetails(Long id, Pagination pagination) throws Exception;

	public List<ZjDfzwPunish> loadByCity(FkUser fkUser) throws Exception;

	public ZjDfzwPunish load(long id) throws ApplicationAccessException;

	public List<ZjDfzwPunishDetail> loadDetailsByDfzwPunish(ZjDfzwPunish entity) throws Exception;

	public void update(ZjDfzwPunish entity, List<ZjDfzwPunishDetail> details) throws Exception;

	public void deleteDetails(Long id) throws Exception;

	public void deleteDetail(ZjDfzwPunishDetail detail) throws Exception;

	public void delete(ZjDfzwPunish entity) throws Exception;

	public void update(ZjDfzwPunish entity) throws Exception;

	public boolean is_all_reported(String areaType, Long areaCode, ZjDfzwPunish entity);

	public List<ZjDfzwPunishDetail> loadDetailsByUser(ZjDfzwPunish entity) throws Exception;

	public List<ZjDfzwPunishDetail> loadDetailsForProvince(ZjDfzwPunish entity) throws Exception;

	public List<ZjDfzwPunish> loadForCitySelf(ZjDfzwPunish entity, Pagination pagination) throws Exception;

	public List<ZjDfzwPunishDetail> loadDetailsForCityInit(ZjDfzwPunish entity) throws Exception;

	public List<FkArea> loadCityAreas(Long cityAreaCode) throws ApplicationAccessException;

	public void loadDetailsSetRollback(ZjDfzwPunish entity) throws Exception;

	public int isReport(String roleDepic, Date reportDate) throws Exception;

	public int getIsAllReport();

	/**
	 * 省级查看县级上报列表
	 * 
	 * @param mineReport
	 * @param pagination
	 * @return
	 */
	public List<ZjDfzwPunish> loadCountyReports(ZjDfzwPunish entity, Pagination pagination) throws ApplicationAccessException;

	/**
	 * 检测用户是否可以进行创建数据的操作，主要防止同一时间，创建了多次数据
	 * 
	 * @author lih
	 * @since 2012-5-14
	 * @param entity
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkAllowCreate(ZjDfzwPunish entity) throws ApplicationAccessException;

	public boolean checkAllowCreate1(ZjDfzwPunish entity) throws ApplicationAccessException;

	public boolean checkAllowCreate2(ZjDfzwPunish entity) throws ApplicationAccessException;

	public boolean checkAllowCreat3(ZjDfzwPunish entity) throws ApplicationAccessException;

	public List<DfzwPunishDetail> loadDetailsForAxis2(Long areaCode, ZjDfzwPunish entity) throws Exception;
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
	public List<ZjDfzwPunish> loadCityReport(ZjDfzwPunish entity, Pagination pagination) throws Exception;
}
