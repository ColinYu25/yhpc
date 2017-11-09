package com.safetys.nbyhpc.facade.iface;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.FkEnum;
import com.safetys.framework.domain.model.FkUser;
import com.safetys.framework.domain.model.FkUserInfo;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.security.spring.userdetails.UserDetailWrapper;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaCompanyAcount;
import com.safetys.nbyhpc.domain.model.DaCompanyPass;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPointType;
import com.safetys.nbyhpc.domain.model.Department;
import com.safetys.nbyhpc.domain.model.TCaches;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.vo.CompanyVo;

/**
 * @(#) CompanyFacadeIface.java
 * @date 2009-07-29
 * @author lihu
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */

public interface CompanyFacadeIface {

	/**
	 * 新增企业的时候同步新增中心数据库企业信息
	 * 
	 * @param companyId
	 * @throws ApplicationAccessException
	 */
	public void createCoreCompany(String companyId);

	public List<DaCompany> loadCompaniesForLevel(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void updateCompanyLevel(DaCompany company) throws ApplicationAccessException;

	public List<DaCompany> loadCompaniesForClassic(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 创建一个企业基本信息
	 * 
	 * @param company
	 * @param wrapper
	 * @return
	 * @throws ApplicationAccessException
	 */
	public String createCompany(DaCompany company, UserDetailWrapper wrapper) throws ApplicationAccessException;

	/**
	 * 删除企业所属行业，即不为隐患企业
	 * 
	 * @param company
	 * @param wrapper
	 * @return
	 * @throws ApplicationAccessException
	 */
	public String delCompanyHy(DaCompany company) throws ApplicationAccessException;

	/**
	 * 加载一个企业基本信息
	 * 
	 * @param company
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompany(DaCompany company) throws ApplicationAccessException;

	/**
	 * 添加企业用户名并关联企业信息
	 * 
	 * @param company
	 * @param fkUser
	 * @param fkUserInfo
	 * @param roleIds
	 * @throws ApplicationAccessException
	 */
	public void createUserForCompany(DaCompany company, FkUser fkUser, FkUserInfo fkUserInfo, Long[] roleIds) throws ApplicationAccessException;

	/**
	 * 修改一个企业基本信息
	 * 
	 * @param company
	 * @param wrapper
	 * @throws ApplicationAccessException
	 */
	public String updateCompany(DaCompany company, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 删除企业基本信息(可批量)
	 * 
	 * @param userDetail
	 * @param company
	 * @throws ApplicationAccessException
	 */
	public String deleteCompanies(String ids, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 加载企业行业
	 * 
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */

	public List<DaIndustryParameter> loadTradeTypesForCompany(UserDetailWrapper userDetail) throws ApplicationAccessException;
	
	public List<DaIndustryParameter> loadTradeTypesForCompany1(String userIndustry) throws ApplicationAccessException;

	public List<DaIndustryParameter> loadTradeTypesForCompanyLevel() throws ApplicationAccessException;

	public List<DaPointType> loadTradePointTypesForTradeType() throws ApplicationAccessException;

	/**
	 * 获取用户对应的区域
	 * 
	 * @param userDetail
	 * @return
	 */
	public FkArea loadArea(UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 根据code得到对应的区域
	 * 
	 * @param userDetail
	 * @return
	 */
	public FkArea loadArea(Long areaCode) throws ApplicationAccessException;

	/**
	 * 根据企业名称查询出对应的企业信息
	 * 
	 * @param company
	 * @return
	 */
	public DaCompany loadCompanyByCompanyName(DaCompany company) throws ApplicationAccessException;

	/**
	 * 根据企业名称（模糊查询）查询出对应的企业信息
	 * 
	 * @param company
	 * @return
	 */
	public List<DaCompany> loadCompanyByCompanyName(DaCompany company, Pagination pagination) throws ApplicationAccessException;

	/**
	 * 加载已确认企业集合
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompaniesAffirm(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 加载企业集合
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaCompany> loadCompanyList(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 加载待审核企业集合
	 */
	public List<DaCompany> loadUnAuditingCompanise(DaCompany company, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 审核企业集
	 */
	public void doAuditingCompanyise(String companyIds, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 根据企业信息查询企业用户名
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 */
	public FkUser loadCompanyUserName(FkUser fkUser) throws ApplicationAccessException;

	public List<DaCompany> loadUnCheckedCompanies(DaCompany daCompany, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void createCompanyAcount(DaCompanyAcount companyAcount) throws ApplicationAccessException;

	public List<DaCompanyAcount> loadCompanyAcounts(DaCompany company, DaCompanyAcount companyAcount, Pagination pagination) throws ApplicationAccessException;

	public void createCompanyPass(String companyIds) throws ApplicationAccessException;

	public List<DaCompany> loadCheckedCompanies(DaCompany daCompany, Pagination pagination, UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void deleteCompanyPass(String ids) throws ApplicationAccessException;

	public void updateAffirm(String ids, Boolean state) throws ApplicationAccessException;

	public boolean isAllowDelete(String ids) throws ApplicationAccessException;

	public List<DaCompanyPass> loadCompanyPassByComUserId(UserDetailWrapper userDetail) throws ApplicationAccessException;

	public void updateCompanyPassword(FkUser fkUser) throws ApplicationAccessException;

	public void delCache(TCaches cache) throws ApplicationAccessException;

	/**
	 * 查询该地区的企业
	 * 
	 * @param area
	 * @return
	 */
	public List<DaCompany> loadCompanysByArea(FkArea area, Pagination pagination, String type) throws ApplicationAccessException;

	/**
	 * 查询该地区的企业数量
	 * 
	 * @param area
	 * @return
	 */
	public int loadCompanyCountByArea(FkArea area);

	/**
	 * 加载中心库企业信息
	 * 
	 * @author:liulj
	 * @time:2013-10-15
	 */

	public TCompany loadCoreCompany(TCompany company) throws ApplicationAccessException;

	/**
	 * 加载隐患系统企业信息（根据中心库中的工商注册号、组织机构代码、单位名称）
	 * 
	 * @author:liulj
	 * @time:2013-10-16
	 */
	public String loadCompanyInfo(TCompany company) throws ApplicationAccessException;

	public void checkDuplicateUser(String userName) throws ApplicationAccessException;

	/**
	 * 根据父类枚举代码获得枚举集合
	 * 
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<FkEnum> getEnumByParentCode(String code);

	/**
	 * 根据编码获得行业参数类型
	 * 
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<DaIndustryParameter> getDaIndustryParameterByDepiction(String depiction);

	/**
	 * 根据父类编码获得行业参数实体
	 * 
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<DaIndustryParameter> getDaIndustryParameterByParentDepiction(String depiction);

	/**
	 * 根据父类ids获得行业参数实体
	 * 
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<DaIndustryParameter> getDaIndustryParameterByParentIds(List<DaIndustryParameter> parentDaIndustryParameters);

	/**
	 * 加载中心库企业信息
	 * 
	 * @author:huangjl
	 * @time:2014-01-8
	 */

	public TCompany getCoreCompany(TCompany company) throws ApplicationAccessException;

	/**
	 * 根据父类ids获得枚舉实体
	 * 
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<FkEnum> getFkEnumByParentIds(List<FkEnum> fkEnums);

	/**
	 * 修改一个企业基本信息
	 * 
	 * @param company
	 * @param wrapper
	 * @throws ApplicationAccessException
	 */

	public String updateCompany(DaCompany company, TCompany tcompany, UserDetailWrapper userDetail) throws ApplicationAccessException;

	/**
	 * 根据code值获得企业信息
	 * 
	 * @param company
	 * @param wrapper
	 * @throws ApplicationAccessException
	 */
	public Department getDepartmentByCode(String code) throws ApplicationAccessException;

	/**
	 * 根据编码获得行业参数实体
	 * 这方法有BUG， 表中存在CODE重复的数据，慎用
	 * @author:huangjl
	 * @time:2014-1-8
	 */
	public List<DaIndustryParameter> getDaIndustryParameterByCode(String code);

	/**
	 * 获取隐患分类
	 * @param code
	 * @return
	 */
	public DaIndustryParameter getHiddenTypeByNormalCode(String code);
	/**
	 * 根据用户id获得企业信息
	 * 
	 * @param Long
	 * @return DaCompany
	 * @throws ApplicationAccessException
	 */
	public DaCompany loadCompanyByUserId(Long userId) throws ApplicationAccessException;

	/**
	 * 修改用户名信息
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 */
	public void updateCompanyUserName(FkUser fkUser) throws ApplicationAccessException;

	/**
	 * 根据用户名称得到用户
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 */
	public boolean getUserByUserName(String userName) throws Exception;

	/**
	 * 根据用户名称得到用户
	 * 
	 * @param userName
	 * @return
	 * @throws ApplicationAccessException
	 */

	public FkUser loadCompanyUserName(String userName) throws ApplicationAccessException;

	/**
	 * 加载缓存集合
	 * 
	 * @param company
	 * @param pagination
	 * @param userDetail
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<TCaches> loadCaches(TCaches cache, Pagination pagination) throws ApplicationAccessException;

	/**
	 * 根据userName得到fkUser对象
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public Map<String, Object> loadCompanyUserInfoBySql(String userName) throws ApplicationAccessException, SQLException;

	/**
	 * 根据userName得到fkUser对象
	 * 
	 * @param fkUser
	 * @return
	 * @throws ApplicationAccessException
	 * @throws SQLException
	 */
	public String loadCompanyUserIndustry(String userName) throws ApplicationAccessException, SQLException;
	
	
	public String initErrorCompanyInfo(Long startId,Long endId) throws ApplicationAccessException;
	
	public List<DaCompany> checkSetupNumber(DaCompany company) throws ApplicationAccessException;
	
	public List<DaCompany> checkRegNum(DaCompany company) throws ApplicationAccessException;
	
	/**
	 * 获取当前月没有填写隐患的企业
	 * @return
	 */
	public List<CompanyVo> findUnReportCompany4CurrentMonth(Long secondArea);
	
	/**
	 * 1、4、7、10月份 获取上一个季度没有上报隐患季报的企业
	 * @param secondArea
	 * @return
	 */
	public List<CompanyVo> findUnReportCompany4PreQuarter(Long secondArea);
	
	/**
	 * 是否存在未上报的季报
	 * @param companyId
	 * @return
	 */
	public boolean existUnReportPreQuarter(Long companyId);
	
	public String loadCompanyUser(String regNum,String setupNumber) throws ApplicationAccessException, SQLException;
	/**
	 * 根据条件加载中心库企业基本信息
	 * @return List<TCompany>
	 */
	public List<TCompany> loadTCompany(TCompany company) throws ApplicationAccessException;
	
	/**
	 * 根据条件加载中心库企业基本信息
	 * @return TCompany
	 */
	public TCompany getTCompany(TCompany company) throws ApplicationAccessException;
	
	/**
	 * 绑定企业信息
	 * @return TCompany
	 * @throws Exception 
	 */
	public void companyBinding(DaCompany company,TCompany tcompany) throws Exception;
	
	public void updateCompany(DaCompany company) throws ApplicationAccessException;
}
