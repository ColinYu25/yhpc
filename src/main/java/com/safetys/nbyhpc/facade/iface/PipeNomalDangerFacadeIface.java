package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaPipeNomalDanger;

public interface PipeNomalDangerFacadeIface {
	public List<DaPipeNomalDanger> loadNomalDangers(DaPipelineInfo pipeLine,DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail, Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 一般隐患治理（已经治理和未治理管道隐患查询）
	 * @param entity
	 * @param com
	 * @param flag（true已治理，false未治理）
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipeLinesUnGorverOrGorver(DaPipelineInfo entity, DaCompany com, Boolean flag, Pagination pagination, UserDetailWrapper userDetail) throws Exception;

	public DaPipeNomalDanger loadNomalDanger(DaPipeNomalDanger nomaldanger)
			throws ApplicationAccessException;

	public DaPipeNomalDanger loadLinkManByBefore(DaPipelineInfo pipeLine)
			throws ApplicationAccessException;

	public void createNomalDanger(DaPipeNomalDanger nomalDanger, DaPipelineInfo pipeLine,
			UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public void createNomalDangers(DaPipeNomalDanger nomalDanger, DaPipelineInfo pipeLine,
			UserDetailWrapper userDetail,List<DaPipeNomalDanger> nomalDangers) throws ApplicationAccessException;

	public void updateNomalDanger(DaPipeNomalDanger nomalDanger)
			throws ApplicationAccessException;
	
	public List<DaPipeNomalDanger> loadNormalDangers(String nomalDangerIds) throws ApplicationAccessException;

	public void deleteNormalDanger(String nomalDangerIds)
			throws ApplicationAccessException;
	
	/**
	 * 批量保存一般隐患(企业批量添加无隐患)
	 */
	public void addWuNomalDangers(DaCompany c, String ids, UserDetailWrapper userDetail) throws ApplicationAccessException;
	
//	public List<DaPipelineInfoPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;

	public List<DaPipelineInfo> loadCompanysByIsRepair(DaPipelineInfo pipeLine,DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException;
	
	public List<DaPipelineInfo> loadCompanysByIsRepairNew(DaPipelineInfo pipeLine,DaPipeNomalDanger nomalDanger,
			UserDetailWrapper userDetail,Pagination pagination) throws ApplicationAccessException;
}
