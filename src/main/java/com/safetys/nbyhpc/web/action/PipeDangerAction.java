package com.safetys.nbyhpc.web.action;

import java.util.Calendar;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.facade.iface.PipeDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * @(#) PipeDangerAction.java
 * @date 2012-11-16
 * @author lisl
 * @copyright (c) 2012 Safetys.cn All rights reserved.
 * 
 */
public class PipeDangerAction extends DaAppAction{

	/**
	 * 管道重大隐患信息
	 */
	private static final long serialVersionUID = -8970302687851485075L;

	private PipeDangerFacadeIface pipeDangerFacadeIface;//重大隐患信息的业务接口
	
	private List<DaPipeDanger> dangers;//重大隐患集合
	
	private DaPipeDanger danger;//重大隐患
	
	private List result; // 查询结果集
	
	private DaPipelineInfo entity;// 管道
	
	private DaPipelineCompanyInfo pipelineCompanyInfo;// 管道企业
	
	private List<DaIndustryParameter> industryParameters;// 行业集合
	
	private List<DaIndustryParameter> tradeTypes;// 行业集合
	
	private List statistics;
	
	private Pagination pagination;//分页对象
	
	private String ids;//重大隐患序号的集合，针对类似于批量删除操作
	
	private long count; //未治理重大隐患数
	
	private long count1;//季度报表数量
	
	private PipeLineFacadeIface pipeLineFacadeIface;// 管道业务实现接口
	
	private DaCompany company;// 企业
	
	/**
	 * 管道列表（一般隐患录入）
	 * @return
	 * @throws Exception
	 */
	public String loadPipeLines() {
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				result = pipeLineFacadeIface.loadAllPipeLines(entity, company, "danger", getPagination(), this.getUserDetail());
			} else {
				result = pipeLineFacadeIface.loadAllPipeLines(entity, null, "danger", getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 加载一个重大隐患信息的内容
	 * @return
	 */
	public String createDangerInit(){
		try {
			industryParameters = pipeDangerFacadeIface.loadIndustrysForDanger();
			if(null == entity || entity.getId()<=0){
				return MESSAGE_TO_BACK;
			}
			if(danger == null){
				danger = new DaPipeDanger();
				Calendar cal = Calendar.getInstance();
				danger.setNowYear(cal.get(Calendar.YEAR));
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			danger.setPipeLine(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 创建重大隐患信息
	 * @return
	 */
	public String createDanger(){
		try {
			danger.setUserId(getUserId());
			pipeDangerFacadeIface.createDanger(danger);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
//		if(getUserDetail().getUserIndustry().equals("qiye"))
//			return "qiye";
//		else
		return "success";
	}
	
	/**
	 * 加载一个重大隐患信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadDanger(){
		try {
			industryParameters = pipeDangerFacadeIface.loadIndustrysForDanger();
			danger = pipeDangerFacadeIface.loadDanger(danger);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改重大隐患信息
	 * @return
	 */
	public String updateDanger(){
		try {
			danger.setUserId(getUserId());
			pipeDangerFacadeIface.updateDanger(danger);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除重大隐患信息
	 * @return
	 */
	public String deleteDanger(){
		try {
			pipeDangerFacadeIface.deleteDanger(danger);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String deleteDangers() {
		try {
			pipeDangerFacadeIface.deleteDangers(ids);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载重大隐患信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadDangers(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(null == entity || entity.getId()<=0){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			dangers = pipeDangerFacadeIface.loadDangers(danger, entity, pagination,getUserDetail());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String loadDangersOfCompanyUnGorver(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
//		try {
//			if(company == null){
//				company = new DaCompany();
//				company.setId(pipeDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
//			}
//			if(danger == null){
//				danger = new DaDanger();
//			}
//			danger.setIsGorver("0");
//			company = pipeDangerFacadeIface.loadCompany(company);
//			dangers = pipeDangerFacadeIface.loadDangers(danger,company,pagination,getUserDetail());
//		} catch (ApplicationAccessException e) {
//			e.printStackTrace();
//			return ERROR;
//		}
		return SUCCESS;
	}
	
	public String loadDangersOfCompanyGorver(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
//		try {
//			if(company == null){
//				company = new DaCompany();
//				company.setId(pipeDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
//			}
//			if(danger == null){
//				danger = new DaDanger();
//			}
//			danger.setIsGorver("1");
//			company = pipeDangerFacadeIface.loadCompany(company);
//			dangers = pipeDangerFacadeIface.loadDangers(danger,company,pagination,getUserDetail());
//		} catch (ApplicationAccessException e) {
//			e.printStackTrace();
//			return ERROR;
//		}
		return SUCCESS;
	}
	
	public String loadDangersOfCompanyTimeOut(){
//		if(pagination == null) {
//			pagination = new Pagination();
//			pagination.setPageSize(10);
//		}
//		try {
//			if(company == null){
//				company = new DaCompany();
//				company.setId(pipeDangerFacadeIface.loadCompanyPassByComUserId(getUserDetail()).get(0).getId());
//			}
//			if(danger == null){
//				danger = new DaDanger();
//			}
//			danger.setIsGorver("2");
//			company = pipeDangerFacadeIface.loadCompany(company);
//			dangers = pipeDangerFacadeIface.loadDangers(danger,company,pagination,getUserDetail());
//		} catch (ApplicationAccessException e) {
//			e.printStackTrace();
//			return ERROR;
//		}
		return SUCCESS;
	}
	
	/**
	 * 加载未治理重大隐患企业信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadDangersUnGorver(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(danger == null){
				danger = new DaPipeDanger();
				Calendar cal = Calendar.getInstance();
		        danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("0");
			tradeTypes = pipeDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, company, entity,pagination, getUserDetail());
			} else {
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, null, entity,pagination, getUserDetail());
			}
		} catch (Exception e) { 
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 加载已治理重大隐患企业信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadDangersGorver(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(danger == null){
				danger = new DaPipeDanger();
				Calendar cal = Calendar.getInstance();
		        danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("1");
			tradeTypes = pipeDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, company, entity, pagination, getUserDetail());
			} else {
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, null, entity, pagination, getUserDetail());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载重大隐患将到期企业信息集合，或搜索或全部显示
	 * @return
	 */
	public String loadDangersTimeOut(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(danger == null){
				danger = new DaPipeDanger();
				Calendar cal = Calendar.getInstance();
		        danger.setNowYear(cal.get(Calendar.YEAR));
			}
			danger.setIsGorver("2");
			tradeTypes = pipeDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
//			dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, entity, pagination, getUserDetail());
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, company, entity, pagination, getUserDetail());
			} else {
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, null, entity, pagination, getUserDetail());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载重大隐患挂牌列表，或搜索或全部显示
	 * @return
	 */
	public String loadDangersRollcall(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(danger == null){
				danger = new DaPipeDanger();
				Calendar cal = Calendar.getInstance();
		        danger.setNowYear(cal.get(Calendar.YEAR));
			}
			tradeTypes = pipeDangerFacadeIface.loadTradeTypesForCompany(getUserDetail());
//			dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, entity, pagination, getUserDetail());
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, company, entity,pagination, getUserDetail());
			} else {
				dangers = pipeDangerFacadeIface.loadDangersOfCompanies(danger, null, entity,pagination, getUserDetail());
			}
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载重大隐患排查数量
	 * @return
	 */
	public String loadDangercount(){
		if(pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		try {
			if(null == entity || entity.getId()<=0){
				return MESSAGE_TO_BACK;
			}
			entity = pipeLineFacadeIface.findPipelineById(entity.getId());
			dangers = pipeDangerFacadeIface.loadDangers(danger, entity, pagination, getUserDetail());
//			count1 = pipeDangerFacadeIface.loadCompanyQuarterlyAccount(company);
//			count = pagination.getTotalCount();
		} catch (Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public List getStatistics() {
		return statistics;
	}
	
	public void setStatistics(List statistics) {
		this.statistics = statistics;
	}
	
	public List<DaIndustryParameter> getIndustryParameters() {
		return industryParameters;
	}
	public void setIndustryParameters(List<DaIndustryParameter> industryParameters) {
		this.industryParameters = industryParameters;
	}
	public List<DaIndustryParameter> getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(List<DaIndustryParameter> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getCount1() {
		return count1;
	}
	public void setCount1(long count1) {
		this.count1 = count1;
	}
	public List<DaPipeDanger> getDangers() {
		return dangers;
	}
	public void setDangers(List<DaPipeDanger> dangers) {
		this.dangers = dangers;
	}
	public DaPipelineCompanyInfo getPipelineCompanyInfo() {
		return pipelineCompanyInfo;
	}
	public void setPipelineCompanyInfo(DaPipelineCompanyInfo pipelineCompanyInfo) {
		this.pipelineCompanyInfo = pipelineCompanyInfo;
	}
	public DaPipeDanger getDanger() {
		return danger;
	}
	public void setDanger(DaPipeDanger danger) {
		this.danger = danger;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public DaPipelineInfo getEntity() {
		return entity;
	}

	public void setEntity(DaPipelineInfo entity) {
		this.entity = entity;
	}

	public PipeLineFacadeIface getPipeLineFacadeIface() {
		return pipeLineFacadeIface;
	}

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public PipeDangerFacadeIface getPipeDangerFacadeIface() {
		return pipeDangerFacadeIface;
	}

	public void setPipeDangerFacadeIface(PipeDangerFacadeIface pipeDangerFacadeIface) {
		this.pipeDangerFacadeIface = pipeDangerFacadeIface;
	}
	
}
