package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.nbyhpc.domain.model.ETLAqsc;
import com.safetys.nbyhpc.domain.model.TCompany;

/**
 * @author llj
 * @create_time: 2014-5-4
 * @Description:更新及获取本地同步号信息
 */
public interface ETLAqscInfoPersistenceIface {

	public List<TCompany> getSwapList();
	/**
	 * 根据编号更新同步状态
	 * 
	 * @param idList
	 *            编号集合
	 * @param status
	 *            状态
	 */
	public void updateSynStatus(List<Object> idList);
	
	/**
	 * 保存同步对象信息
	 * 
	 * @param 同步对象的实体类
	 * @throws Exception 
	 */
	public void save(ETLAqsc entity) throws Exception;
}


