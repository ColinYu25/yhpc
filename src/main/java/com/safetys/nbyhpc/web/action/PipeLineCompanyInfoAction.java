package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;

/**
 * 油气管道Action
 * @author qfc
 *
 */
public class PipeLineCompanyInfoAction extends BaseAppAction{
	
	protected PipeLineFacadeIface pipeLineFacadeIface;
	private CompanyFacadeIface companyFacadeIface;
	private DaCompany company;
	private Pagination pagination;
	private List result;
	private DaPipelineCompanyInfo entity;
	private List tradeTypes;
	private Long pipeId;
	
	static String COMPANY_ID_KEY = "companyId";
	static String PIPE_ID_KEY = "pipeId";
	static String PIPELINE_ID_KEY = "pipeLineId";
	static String YQ_MENU_NAME = "yq-menu";
	static String RQ_MENU_NAME = "rq-menu";
	
	public String loadCompanies() throws Exception {
		tradeTypes = companyFacadeIface.loadTradeTypesForCompany(null);
		result = pipeLineFacadeIface.loadCompanyies(company, this.getPagination(), this.getUserDetail());
		return "company-list";
	}

	public String create() throws Exception {
		if (company == null || company.getId() <= 0){
			this.setMessageKey("请选择要添加信息的企业");
			return MESSAGE_TO_BACK;
		}
		this.getRequest().getSession(true).setAttribute(COMPANY_ID_KEY, company.getId());
		entity = pipeLineFacadeIface.findByCompanyId(company.getId(), this.getType());
		company = companyFacadeIface.loadCompany(company);
		if (entity != null){
			this.getRequest().getSession(true).setAttribute(PIPELINE_ID_KEY, entity.getId());
		}
		this.getRequest().getSession(true).setAttribute("includeMenu", this.getIncludeFile());
		return INPUT;
	}
	
	public String update() throws Exception {
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的信息");
			return MESSAGE_TO_BACK;
		}
		if(entity.getPipeId()!=null && entity.getPipeId()>-1)
			pipeId = entity.getPipeId();
		else
			pipeId = -1l;
		entity = pipeLineFacadeIface.findById(entity.getId());
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的信息");
			return MESSAGE_TO_BACK;
		}
		entity.setPipeId(pipeId);
		company = companyFacadeIface.loadCompany(entity.getCompany());
		if (entity != null){
			this.getRequest().getSession(true).setAttribute(PIPELINE_ID_KEY, entity.getId());
		}
		
		if (company != null){
			this.getRequest().getSession(true).setAttribute(COMPANY_ID_KEY, company.getId());
		}
		return INPUT;
	}
	
	public String delete() throws Exception {
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要删除的信息");
			return MESSAGE_TO_BACK;
		}
		pipeLineFacadeIface.delete(entity);
		return create();
	}
	
	
	public String save() throws Exception {
		if (entity == null){
			this.setMessageKey("没有找到要保存的信息");
			return MESSAGE_TO_BACK;
		}
		if (entity.getCompany() == null || entity.getCompany().getId() <= 0){
			this.setMessageKey("没有找到要保存的企业");
			return MESSAGE_TO_BACK;
		}
		entity.setType(this.getType());
		if (entity.getId() > 0 ){
			pipeLineFacadeIface.update(entity);
		}else{
			pipeLineFacadeIface.create(entity);
		}
		this.setMessageKey("保存成功!");
		if(entity.getPipeId()!=null && entity.getPipeId()>-1)
			this.getRequest().setAttribute("url", this.getUrlPrefix() + "_update.xhtml?entity.pipeId="+entity.getPipeId()+"&entity.id=" + entity.getId());
		else
			this.getRequest().setAttribute("url", this.getUrlPrefix() + "_update.xhtml?entity.pipeId=-1&entity.id=" + entity.getId());
		return MESSAGE_TO_REDIRECT;
	}

	protected int getType(){
		return PipeLineFacadeIface.YQGD;
	}
	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}

	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	public Pagination getPagination() {
		if (pagination == null){
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public List getResult() {
		return result;
	}

	public DaPipelineCompanyInfo getEntity() {
		return entity;
	}

	public void setEntity(DaPipelineCompanyInfo entity) {
		this.entity = entity;
	}

	public List getTradeTypes() {
		return tradeTypes;
	}

	public String getUrlPrefix(){
		return "company";
	}
	
	public String getIncludeFile(){
		return YQ_MENU_NAME;
	}

	public Long getPipeId() {
		return pipeId;
	}

	public void setPipeId(Long pipeId) {
		this.pipeId = pipeId;
	}
	
}

