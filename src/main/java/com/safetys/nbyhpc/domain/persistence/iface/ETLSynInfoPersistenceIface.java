package com.safetys.nbyhpc.domain.persistence.iface;

import com.safetys.nbyhpc.domain.model.ETLSynInfo;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public interface ETLSynInfoPersistenceIface  {

	public ETLSynInfo getSynNo(String tabName);

	/**
	 * 保存同步对象信息
	 * 
	 * @param 同步对象的实体类
	 */
	public void save(ETLSynInfo etlSynInfo);
	
	public void update(ETLSynInfo etlSynInfo);
	
	

}
