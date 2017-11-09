package com.safetys.nbyhpc.facade.iface;


import java.io.InputStream;
import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeAttech;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.domain.model.DaYqPipelineInfo;
import com.safetys.nbyhpc.domain.model.WhCompanyInfo;

/**
 * 
 * @author qiufc
 * 2011-07-09 qiufc loadYqCompanyListByArea函数增加管道类型字段，用于根据管道类型查询。
 */
public interface PipeLineFacadeIface {
	
	/**
	 * 油气管道
	 */
	static int YQGD = 0;
	
	/**
	 * 燃气管道
	 */
	static int RQGD = 1;
	
	public void create(DaPipelineCompanyInfo entity) throws Exception ;
	
	public void update(DaPipelineCompanyInfo entity) throws Exception ;
	
	public void delete(DaPipelineCompanyInfo entity) throws Exception;
	
	public DaPipelineCompanyInfo findById(long id) throws Exception;
	
	/**
	 * 根据id，加载DaPipelineInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DaPipelineInfo findPipelineById(long id) throws Exception;
	
	/**
	 * 根据id，加载DaPipelineInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object findPipelineById(Class clazz, long id) throws Exception;
	
	
	/**
	 * 根据id，删除DaPipelineInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deletePipelineById(long id) throws Exception;
	
	public void deleteAll(String ids) throws ApplicationAccessException;
	
	public void createPipeline(DaPipelineInfo entity) throws Exception ;
	
	public void updatePipeline(DaPipelineInfo entity) throws Exception ;
	
	public List<WhCompanyInfo> find(DaPipelineCompanyInfo entity, Pagination pagination) throws Exception;
	
	public Object findById(Class clazz, long id) throws Exception;
	
	/**
	 * 根据登用用户区域对应的企业信息
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompanyByComUserId(UserDetailWrapper userDetail)throws ApplicationAccessException;
	
	/**
	 * 根据企业id，查找DaPipelineCompanyInfo
	 * 每个企业在DaPipelineCompanyInfo中，只有一条数据
	 * @param companyId
	 * @param type 
	 * @return
	 * @throws Exception
	 */
	public DaPipelineCompanyInfo findByCompanyId(long companyId, int type) throws Exception;
	
	/**
	 * 根据DaPipelineCompanyInfo的id，查找DaPipelineInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> findPiplineByCompanyInfoId(long id) throws Exception;
	
	/**
	 * 根据上级区域，查找下一级区域
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<FkArea> loadAreadsByParentCode(Long code) throws Exception;
	
	public InputStream export(DaPipelineInfo entity, DaCompany com, Pagination pagination, UserDetailWrapper userDetail) throws Exception;
	
	/**
	 * 企业查询，不过滤行业
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyies(DaCompany company, Pagination pagination, UserDetailWrapper userDetail)throws Exception;
	
	/**
	 * 管道列表(油气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadYqPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception;
	
	/**
	 * 管道列表(燃气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadRqPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception;
	
	/**
	 * 管道列表(油气和然气)
	 * @param entity 
	 * @param company 
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadAllPipeLines(DaPipelineInfo entity, DaCompany com, String checkType, Pagination pagination, UserDetailWrapper userDetail) throws Exception;
	
	public List rqStatistic(DaPipelineInfo lineInfo) throws Exception;
	
	public List yqStatistic(DaPipelineInfo lineInfo) throws Exception;
	
	/**
	 * 根据管道类型、区域加载企业信息
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaCompany> loadCompanyListByArea(String lineType, Long areaCode, Pagination page) throws Exception;
	
	/**
	 * 根据区域、管道类型，加载管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadPipLineByArea(DaPipelineInfo entity,String type, Long areaCode, Pagination page) throws Exception;
	
	/**
	 * 根据区域代码，加载燃气管道企业列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadRqCompanyListByArea(Long areaCode, Pagination page) throws Exception;
	
	/**
	 * 根据区域代码、管道类型，加载油气管道企业列表
	 * @param areaCode
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadYqCompanyListByArea(Long areaCode, int type, Pagination page) throws Exception;
	
	
	
	/**
	 * 根据区域、管道类型，加载油气管道列表
	 * @param type
	 * @param areaCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineInfo> loadYqPipLineByArea(int type, Long areaCode, Pagination page) throws Exception;
	
	/**
	 * 根据区域，加载燃气管道类型
	 * @param propertyName 属性名称
	 * @param value  属性值
	 * @param areaCode 区域代码
	 * @param type  管道类型：0:全部， 1：长输管道 2：城镇燃气管道 3：工业管道 4：港区内油气管道 
	 * @return
	 */
	public List<DaPipelineInfo> loadRqPipLineByArea(Long areaCode, Pagination page) throws Exception;
	

	public List<DaPipeAttech> loadAttechByArea(Long areaCode, String lineType, int attechType, Pagination page) throws Exception;
	
	/**
	 * 根据油气管道属性，油气管道列表
	 * @param propertyName
	 * @param value
	 * @param areaCode
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadYqPipLineByProperty(String propertyName, boolean value, Long areaCode, String lineType, Pagination page) throws Exception;
	
	/**
	 * 根据燃气管道属性，油气管道列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadRqPipLineByProperty(String propertyName, boolean value, Long areaCode, Pagination page) throws Exception;
	
	/**
	 * 根据燃气管道属性，油气管道列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<DaPipelineCompanyInfo> loadRqPipLineByIntProperty(String propertyName, int value, Long areaCode, Pagination page) throws Exception;
	
}
