package com.safetys.nbyhpc.domain.persistence.iface;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.TCompany;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public interface ETLDaCompanyPersistenceIface { 

	/**
	 * t_company  ==>更新  da_company
	 * @param fkUser
	 * @return 
	 * @throws ApplicationAccessException
	 */
	public boolean loadSynDaCompany() throws Exception;	
	/**
	 * da_company  ==>更新  t_company
	 * @param fkUser
	 * @return 
	 * @throws ApplicationAccessException
	 */
	public boolean loadSynTCompany() throws Exception;	
	
	/**
	 * da_company中存在   t_company不存在  插入  (初始化)
	 * @return
	 * @throws Exception
	 */
	public boolean loadInsertSynDaCompany() throws Exception;	
	
	public String ftrans(long hy) throws ApplicationAccessException;
	/**
	 * 根据t_company初始化da_company UUid字段
	 * @return
	 * @throws Exception
	 */
	public boolean loadUuidDaCompany() throws Exception;
	
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * t_company  ==>更新  da_company
	 * @param fkUser
	 * @return 
	 * @throws ApplicationAccessException
	 */
	public boolean loadSynDaCompany(Long startId,Long endId) throws Exception;	
	/**
	 * da_company  ==>更新  t_company(2014-05-01之后的数据)
	 * @param fkUser
	 * @return 
	 * @throws ApplicationAccessException
	 */
	 
	public boolean loadSynTCompany(Long startId,Long endId) throws Exception;	
	
	/**
	 * da_company  ==>更新  t_company(2014-05-01之前的数据)
	 * @param fkUser
	 * @return 
	 * @throws ApplicationAccessException
	 */
	
	public boolean loadSynTCompanyLow(Long startId,Long endId) throws Exception;
	
	/**
	 * da_company中存在   t_company不存在  插入  (初始化)
	 * @return
	 * @throws Exception
	 */
	public boolean loadInsertSynDaCompany(Long startId,Long endId) throws Exception;	
	
	/**
	 * 根据t_company初始化da_company UUid字段
	 * @return
	 * @throws Exception
	 */
	public boolean loadUuidDaCompany(Long startId,Long endId) throws Exception;
	
	
	////////////////////////////////////////////////////////////////////////////////////
	
	public boolean loadSynDaCompany(DaCompany company,TCompany tcompany) throws Exception;
	
	public boolean loadSynTCompany(DaCompany dacompany,TCompany company) throws Exception;
	
}


