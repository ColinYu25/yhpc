package com.safetys.nbyhpc.facade.iface;

import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;
import com.safetys.nbyhpc.domain.model.DaPipeRollcallCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.Statistic;

public interface PipeStatisticFacadeIface {
	
	/**
	 * 管道一般隐患统计
	 */
	public Map<String, Object> loadNomalDanger(Statistic st) throws ApplicationAccessException;
	
	/**
	 * 管道一般隐患统计详情
	 */
	public List<DaPipeNomalDanger> loadNomalDangerDetail(Statistic st, Pagination page) throws ApplicationAccessException;
	
	/**
	 * 管道重大隐患统计
	 */
	public Map<String, Object> loadDanger(Statistic st) throws ApplicationAccessException;
	
	/**
	 * 管道重大隐患统计详情
	 */
	public List<DaPipeDanger> loadDangerDetail(Statistic st, Pagination page) throws ApplicationAccessException;
	
	/**
	 * 月报统计
	 */
	public Map<String, Object> loadMonth(Statistic st) throws ApplicationAccessException;
	
	/**
	 * 根据管道类型、区域加载企业信息
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyListByArea(Statistic st, Boolean flag, Pagination page) throws Exception;
	
	/**
	 * 根据区域、管道类型，加载管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipLineByArea(Statistic st, Pagination page) throws Exception;
	
	/**
	 * 季报统计
	 */
	public Map<String, Object> loadQuarter(Statistic st) throws ApplicationAccessException;
	
	/**
	 * 排查情况统计
	 */
	public Map<String, Object> loadMass(Statistic st) throws ApplicationAccessException;
	
	/**
	 * 查询区域集合
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas(Long areaCode) throws ApplicationAccessException;
	
	/**
	 * 查询区域集合(适合管道季报和月报、报送情况统计) 查询包含本级区域和子区域
	 * 
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreas2(Long areaCode) throws ApplicationAccessException;
	
	/**
	 * 一般隐患和重大隐患统计
	 * @param area
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadTroubleByNomalAndHidden(FkArea area,Statistic st)throws ApplicationAccessException;
	
	/**
	 * 提取重大隐患最新的治理时间
	 * 
	 * @param dangerGorver
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeDangerGorver> loadDangerGorversOfOne(DaPipeDanger daPipeDanger) throws ApplicationAccessException;
	

	public List<DaPipeRollcallCompany> loadRollcallCompanies(DaPipeDanger danger, Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 挂牌统计
	 * @param area
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<Statistic> loadTroubleByRollcall(FkArea area,Statistic st)throws ApplicationAccessException;
	
	/**
	 * 重大隐患列表
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeDanger> loadDangerTroubleByTypeList(Statistic statistic, Pagination pagination,FkArea area)throws ApplicationAccessException;
	
	/**
	 * 一般隐患列表
	 * @param statistic
	 * @param pagination
	 * @param area
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaPipeNomalDanger> loadNomalTroubleByTypeList(Statistic statistic, Pagination pagination,FkArea area)throws ApplicationAccessException;
	
//	/**
//	 * lisl(提取重大隐患最新的治理时间)
//	 * @param dangerGorver
//	 * @return
//	 * @throws ApplicationAccessException
//	 */
//	public List<DaPipeDangerGorver> loadDangerGorversOfOne(DaPipeDanger daPipeDanger) throws ApplicationAccessException;
	
}
