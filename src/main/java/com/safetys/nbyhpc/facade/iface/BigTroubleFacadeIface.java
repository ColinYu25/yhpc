package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.ZjBigTrouble;
import com.safetys.nbyhpc.domain.model.ZjMineReport;
import com.safetys.nbyhpc.domain.model.ZjStatistic;

public interface BigTroubleFacadeIface {
	/**
	 * 创建一条重大隐患记录
	 * @param bigTrouble
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void createBigTrouble(ZjBigTrouble bigTrouble)
			throws ApplicationAccessException;
			
	public List<ZjBigTrouble> loadBigTroubles(Long areaCode, Integer type) throws ApplicationAccessException;
	/**
	 * 获取重大隐患列表
	 * @param pagination 
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjBigTrouble> loadBigTroubles(ZjBigTrouble bigTrouble, Integer mineType, Pagination pagination)
			throws ApplicationAccessException;
	/**
	 * 重大隐患列表打印
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjBigTrouble> loadBigTroubles_excel(ZjBigTrouble bigTrouble, Pagination pagination)
	throws ApplicationAccessException;
	
	/**
	 * 删除一条重大隐患记录
	 * @param bigTrouble
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void deleteBigTrouble(ZjBigTrouble bigTrouble, FkUser fkUser)
			throws ApplicationAccessException;
	/**
	 * 查看一条重大隐患记录
	 * @param id
	 * @return
	 * @throws ApplicationAccessException
	 */
	public ZjBigTrouble loadBigTrouble(long id)
			throws ApplicationAccessException;

	public List<ZjBigTrouble> loadAllBigTroubles(ZjBigTrouble bigTrouble, Pagination pagination)
	throws ApplicationAccessException;
	/**
	 * 修改一条重大隐患记录
	 * @param bigTrouble
	 * @throws ApplicationAccessException 
	 */
	public void updateBigTrouble(ZjBigTrouble bigTrouble) throws ApplicationAccessException;
	
	/**
	 * 修改一条重大隐患的备注
	 * @param bigTrouble
	 * @throws ApplicationAccessException
	 */
	public void updateBigTroubleRemark(ZjBigTrouble bigTrouble) throws ApplicationAccessException;
	/**
	 * 重大隐患记录销号或反销号
	 * @param bigTrouble
	 * @throws ApplicationAccessException 
	 */
	public void updateBigTroubleOut(ZjBigTrouble bigTrouble) throws ApplicationAccessException;

	/**
	 * 统计重大隐患数据
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjStatistic> loadStatisticForMine(ZjMineReport mineReport) 
			throws ApplicationAccessException;
	
	public void updateBigTroubleTime(ZjBigTrouble bigTrouble) throws ApplicationAccessException;
	
	public ZjMineReport getMineUserId(long mineId) throws ApplicationAccessException;

	/**
	 * 查询将要上报的报表中是否含有未选择挂牌督办级别的重大隐患
	 * @author lih
	 * @since 2012-6-29
	 * @param userDetail
	 * @param bigTrouble
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<ZjBigTrouble> loadBigTroubleHaveNoChooseGuapaiLevel(UserDetailWrapper userDetail, ZjBigTrouble bigTrouble, Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 检查将要上报的报表中是否含有未选择挂牌督办级别的重大隐患，如果含有，则不允许上报
	 * @author lih
	 * @since 2012-6-29
	 * @param userDetail
	 * @param mineReport
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean checkBigTroubleHaveNoChooseGuapaiLevel(UserDetailWrapper userDetail, ZjMineReport mineReport) throws ApplicationAccessException;
}
