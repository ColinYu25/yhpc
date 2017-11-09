package com.safetys.nbyhpc.facade.iface;

import java.sql.SQLException;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.SynErrorIds;
import com.safetys.nbyhpc.domain.model.SyncNo;
import com.safetys.nbyhpc.domain.model.VDangerEx;
import com.safetys.nbyhpc.domain.model.VNormalDangerEx;

public interface HiddenIface {

	/**
	 * 根据版本号，查询指定数量的重大隐患信息
	 * 
	 * @param versionId
	 *            版本号
	 * @param count
	 *            返回数量
	 * @throws ApplicationAccessException
	 */
	public List<VDangerEx> getVDangerExListByVersionId(Long versionId,
			Long count) throws ApplicationAccessException;

	/**
	 * 根据版本号，查询指定数量的一般隐患信息
	 * 
	 * @param versionId
	 *            版本号
	 * @param count
	 *            返回数量
	 * @throws ApplicationAccessException
	 */
	public List<VNormalDangerEx> getVNormalDangerExListByVersionId(
			Long versionId, Long count) throws ApplicationAccessException;

	/**
	 * 根据编号标识获得同步版本信息
	 * 
	 * @param key
	 *            编码标识
	 * @throws ApplicationAccessException
	 */
	public SyncNo getSyncNoByKey(String key) throws ApplicationAccessException;

	/**
	 * 保存同步版本信息
	 * 
	 * @param SyncNo
	 *            同步版本信息
	 * @throws ApplicationAccessException
	 */
	public void saveSyncNo(SyncNo syncNo) throws ApplicationAccessException;

	/**
	 * 修改隐患是否同步到省局标识
	 * 
	 * @param id
	 *            修改隐患id
	 * @param SyncNo
	 *            修改隐患类型
	 * @throws ApplicationAccessException
	 */
	public void updateExFlag(Long id, String flag)
			throws ApplicationAccessException;

	public Long loadLimitVersionId(Long startVersionId, Long count, String flag)
			throws ApplicationAccessException, SQLException;

	/**
	 * 保存或修改同步失败记录信息
	 * 
	 * @param SynErrorIds
	 *            同步失败记录实体
	 * @throws ApplicationAccessException
	 */
	public void saveOrUpdateSynErrorIds(SynErrorIds synErrorIds)
			throws ApplicationAccessException;

	/**
	 * 根据编码标识，返回指定数量的同步失败记录。
	 * 
	 * @param syncKey
	 *            隐患编码标识
	 * @param count
	 *            返回指定数量
	 * @throws ApplicationAccessException
	 */
	public List<SynErrorIds> getSynErrorIdsByVersionId(String syncKey,
			Long count) throws ApplicationAccessException;

	/**
	 * 根据版本id和隐患id获得一般隐患信息
	 * 
	 * @param versionId
	 *            版本id
	 * @param id
	 *            隐患主键
	 * @throws ApplicationAccessException
	 */
	public VNormalDangerEx getVNormalDangerEx(Long versionId, Long id)
			throws ApplicationAccessException;

	/**
	 * 根据版本id和隐患id获得重大隐患信息
	 * 
	 * @param versionId
	 *            版本id
	 * @param id
	 *            隐患主键
	 * @throws ApplicationAccessException
	 */
	public VDangerEx getVDangerEx(Long versionId, Long id)
			throws ApplicationAccessException;
	
	/**
	 * 根据版本id和隐患id获得隐患错误信息
	 * 
	 * @param versionId
	 *            版本id
	 * @param id
	 *            隐患主键
	 * @throws ApplicationAccessException
	 */
	public SynErrorIds getSynErrorIdsByVersionId(Long dangerId,
			Long dangerVersionId) throws ApplicationAccessException ;
}
