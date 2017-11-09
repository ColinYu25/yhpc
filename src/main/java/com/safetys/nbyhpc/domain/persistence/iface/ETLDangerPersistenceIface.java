package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.nbyhpc.domain.model.VDanger;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public interface ETLDangerPersistenceIface {

	public List<VDanger> getSwapList();
	/**
	 * 根据编号更新同步状态
	 * 
	 * @param idList
	 *            编号集合
	 * @param status
	 *            状态
	 */
	public void updateSynStatus(List<Object> idList);
	
}


