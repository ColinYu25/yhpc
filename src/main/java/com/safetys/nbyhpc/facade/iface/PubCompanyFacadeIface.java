package com.safetys.nbyhpc.facade.iface;

import java.util.Map;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.TCoreBzh;
import com.safetys.nbyhpc.domain.model.TCoreXzxk;

public interface PubCompanyFacadeIface {
	/**
	 * 更新
	 * @param tableName 表名
	 * @param fieldName 字段名
	 * @param id 记录ID
	 * @param value 赋值
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean updateSqlVal(String tableName, String fieldName, int id, String value) throws ApplicationAccessException;
	
	public int executeSQLUpdate(String sql)throws ApplicationAccessException;
	
	/**
	 * 获取企业档案所有信息
	 */
	public Map<String, Object> loadCoreCompanyInfo(Long companyId) throws Exception;
	
	/**
	 * 新增行政许可证
	 * @param xzxk
	 * @return
	 * @throws ApplicationAccessException
	 */
	public long ajaxAddXzxk(TCoreXzxk xzxk)throws ApplicationAccessException;
	
	/**
	 * 新增行政许可证
	 * @param xzxk
	 * @return
	 * @throws ApplicationAccessException
	 */
	public long ajaxAddBzh(TCoreBzh bzh)throws ApplicationAccessException;
}
