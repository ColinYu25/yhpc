package com.safetys.nbyhpc.facade.iface;

import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.domain.model.ZjDfzwPunish;
import com.safetys.nbyhpc.domain.model.ZjDfzwReport;
import com.safetys.nbyhpc.domain.model.ZjReportParam;
import com.safetys.nbyhpc.domain.model.ZjSend;
import com.safetys.nbyhpc.web.axis2.model.Danger;
import com.safetys.nbyhpc.web.axis2.model.NomalDanger;
import com.safetys.nbyhpc.web.axis2.model.Param;
import com.safetys.nbyhpc.web.axis2.model.ThreeIllegal;
import com.safetys.nbyhpc.web.axis2.model.ThreeParam;

public interface Axis2FacadeIface { 
	
	public List<FkArea> loadAreas(Long areaCode)throws ApplicationAccessException;
	
	public Param loadArea(Long areaCode)throws ApplicationAccessException;
	
	public ThreeParam loadAreaForThreeIllegal(Long areaCode)throws ApplicationAccessException;
	
	public List<Danger> loadDangerByParamy(Statistic st,String sendType)throws ApplicationAccessException;
	
	public List<NomalDanger> loadMineByParam(Statistic st)throws ApplicationAccessException;
	
	public List<NomalDanger> loadOtherByParam(Statistic st)throws ApplicationAccessException;
	
	public List<ThreeIllegal> loadThreeIllegalByParam(Statistic st)throws ApplicationAccessException;
	
	public List<Statistic> loadStatisticMineByParam(Statistic st)throws ApplicationAccessException;
	
	/**
	 * 查询矿山等行业的统计数据(new)
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticMineByParam2(Statistic st)throws ApplicationAccessException;
	
	public List<Statistic> loadStatisticOtherByParam(Statistic st)throws ApplicationAccessException;
	
	/**
	 * 查询其他行业的统计数据(new)
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticOtherByParam2(Statistic st)throws ApplicationAccessException;
	
	public List<Statistic> loadStatisticForAxis2ThreeIllegal(Statistic st)throws ApplicationAccessException;
	
	public List<Statistic> loadStatisticForAxis2MineByParam(Statistic st)throws ApplicationAccessException;
	
	public Map<String, Object> loadStatisticForAxis2MineByParam2(Statistic st)throws ApplicationAccessException;
	
	public List<Statistic> loadStatisticForAxis2OtherByParam(Statistic st)throws ApplicationAccessException;
	/**
	 * 其他行业报送数据汇总统计（new）
	 * @param st
	 * @return
	 * @throws ApplicationAccessException
	 */
	public Map<String, Object> loadStatisticForAxis2OtherByParam2(Statistic st)throws ApplicationAccessException;
	
	public List<Danger> loadDangerCreateBigTrouble(Statistic st) throws ApplicationAccessException;
	
	public List<Danger> createBigTroubleInit() throws ApplicationAccessException;
	
	public List<Statistic> loadThreeIllegal(Statistic st, UserDetailWrapper userDetail)throws ApplicationAccessException ;
	
	public Boolean sendDataOfMineOrOtherByOMElement(Statistic statistic,Long userId);
	
	public Boolean sendDataOfMineOrOtherByOMElement1(Statistic statistic, Long userId);
	
	public String sendDataOfIllegalByOMElement(Statistic statistic,Long userId);
	
	public ZjSend loadSend(Statistic statistic)throws ApplicationAccessException;
	
	public String sendDataDfzwByOMElement(ZjDfzwReport entity, Long userId);
	
	public String sendDataDfzwPunishByOMElement(ZjDfzwPunish entity, Long userId);
	
	public void saveSendTime(Statistic statistic);
	
	/**
	 * 添加参数
	 * @param reportParams
	 * @throws ApplicationAccessException
	 */
	public void createReportParam(List<ZjReportParam> reportParams,Long userId) throws ApplicationAccessException ;
	
	
	/**
	 * 查询所有参数
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjReportParam> loadReportParams()throws ApplicationAccessException;
	
	/**
	 * 修改参数
	 * @param params
	 * @throws ApplicationAccessException
	 */
	public void updateReportParam(List<ZjReportParam> params,Long userId) throws ApplicationAccessException;
	
	/**
	 * 为参数列表查询区域
	 * @param areaCode
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<FkArea> loadAreasForReport()throws ApplicationAccessException;
	
	/**
	 * 删除数据库缓存结果集
	 * @param catchName
	 * @param type
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void deleteStatistic(String catchName,String type);
}
