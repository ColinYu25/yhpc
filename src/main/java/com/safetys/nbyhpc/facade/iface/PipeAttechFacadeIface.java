package com.safetys.nbyhpc.facade.iface;


import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaPipeAttech;
import com.safetys.nbyhpc.util.OperateResult;

/**
 * @(#) CompanyFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface PipeAttechFacadeIface {
	
	/**
	 * 实际路径与施工图纸不一致处
	 */
	int NOT_SAME_TYPE = 0;
	
	/**
	 * 穿越人员密集场所情况
	 */
	int CYRYMJCS_TYPE = 1;
	
	/**
	 * 防护区施工情况
	 */
	int FHQSG_TYPE = 2;
	
	/**
	 * 安全事故
	 */
	int ACCIDENT_TYPE = 3;
	
	/**
	 * 违章占压情况
	 */
	int WZZY_TYPE = 4;
	
	/**
	 * 政府协调情况
	 */
	int ZFXT_TYPE = 5;
	
	public void create(DaPipeAttech entity) throws Exception ;
	
	public void update(DaPipeAttech entity) throws Exception ;
	
	public void delete(DaPipeAttech entity) throws Exception;
	
	public Object findById(Class clazz, long id) throws Exception;
	
	public List<DaPipeAttech> find(DaPipeAttech entity, Pagination page) throws Exception;
	
	public OperateResult uploadFile(DaPipeAttech file) throws ApplicationAccessException;
	
	/**
	 * 根据区域，类型，加载燃气管道的附件：穿越人员密集场所情况、防护区施工情况等
	 * @param area
	 * @return
	 * @throws Exception
	 */
	public List<DaPipeAttech> loadRqAttechByArea(Long area, int type) throws Exception;
	
	/**
	 * 根据区域，类型，加载油气管道的附件：穿越人员密集场所情况、防护区施工情况等
	 * @param area
	 * @return
	 * @throws Exception
	 */
	public List<DaPipeAttech> loadYqAttechByArea(Long area, int type, int lineType) throws Exception;
	
}
