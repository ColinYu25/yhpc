package com.safetys.nbyhpc.facade.iface;


import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.DaPipeTrouble;

/**
 * 
 */

public interface PipeTroubleFacadeIface {
	
	public void create(DaPipeTrouble entity) throws Exception ;
	
	public void update(DaPipeTrouble entity) throws Exception ;
	
	public void delete(DaPipeTrouble entity) throws Exception;
	
	public Object findById(Class clazz, long id) throws Exception;
	
	public List<DaPipeTrouble> find(DaPipeTrouble entity, Pagination page) throws Exception;
	
	/**
	 * 根据区域，加载燃气管道隐患
	 * @param areaCode
	 * @return
	 */
	public List<DaPipeTrouble> loadRqTroublesByArea(Long areaCode, Pagination page);
	
	/**
	 * 根据区域， 管道类型，加载油气管道隐患
	 * @param areaCode
	 * @param lineType
	 * @return
	 */
	public List<DaPipeTrouble> loadYqTroublesByArea(Long areaCode, int lineType, Pagination page);
	
}
