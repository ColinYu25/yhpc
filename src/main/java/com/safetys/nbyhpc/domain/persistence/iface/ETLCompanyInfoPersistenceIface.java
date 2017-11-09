package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ETLCompany;
import com.safetys.nbyhpc.domain.model.TCompany;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public interface ETLCompanyInfoPersistenceIface {
	
	public void executeSQLUpdate(String sql) throws ApplicationAccessException;
	public void executeHQLUpdate(String hql) throws ApplicationAccessException;
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
	public void save(ETLCompany entity) throws Exception;
	
	
}


