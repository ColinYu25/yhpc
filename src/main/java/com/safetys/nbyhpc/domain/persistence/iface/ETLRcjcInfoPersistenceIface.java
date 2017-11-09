package com.safetys.nbyhpc.domain.persistence.iface;

import com.safetys.nbyhpc.domain.model.ETLRcjc;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public interface ETLRcjcInfoPersistenceIface {

	/**
	 * 保存同步对象信息
	 * 
	 * @param 同步对象的实体类
	 * @throws Exception 
	 */
	public void save(ETLRcjc entity) throws Exception;

}
