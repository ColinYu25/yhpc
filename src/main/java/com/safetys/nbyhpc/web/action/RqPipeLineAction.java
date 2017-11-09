package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaRqPipelineInfo;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

/**
 * 
 * @author qiufc
 * 2011-07-08 qiufc 修改查看页面的数据显示错误问题。
 */
public class RqPipeLineAction extends BaseAppAction{

	protected PipeLineFacadeIface pipeLineFacadeIface;
	protected StatisticFacadeIface statisticFacadeIface;
	private DaRqPipelineInfo entity;
	protected Pagination pagination;
	private DaCompany company;
	protected List result;
	protected List areas;
	private String ids;//ID字符串
	/**返回的页面*/
	private String resultPage;
	/**默认的最新的页面版本*/
	private String version;
	
	/**
	 * 管道列表
	 * @return
	 * @throws Exception
	 */
	public String loadPipeLines() {
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			areas = pipeLineFacadeIface.loadAreadsByParentCode(Nbyhpc.AREA_CODE);
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
				result = pipeLineFacadeIface.loadRqPipeLines(entity, company, "", getPagination(), this.getUserDetail());
			} else {
				result = pipeLineFacadeIface.loadRqPipeLines(entity, null, "", getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String create() throws Exception {
		areas = pipeLineFacadeIface.loadAreadsByParentCode(Nbyhpc.AREA_CODE);
		this.getRequest().getSession(true).setAttribute("includeMenu", PipeLineCompanyInfoAction.RQ_MENU_NAME);
		return INPUT;
	}
	
	public String update() throws Exception {
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的信息");
			return MESSAGE_TO_BACK;
		}
		entity = (DaRqPipelineInfo)pipeLineFacadeIface.findById(DaRqPipelineInfo.class, entity.getId());
		if (entity != null){
			this.getRequest().getSession(true).setAttribute(PipeLineCompanyInfoAction.PIPELINE_ID_KEY, entity.getDaPipelineCompanyinfo().getId());
			this.getRequest().getSession(true).setAttribute(PipeLineCompanyInfoAction.COMPANY_ID_KEY, entity.getDaPipelineCompanyinfo().getCompany().getId());
		}
		areas = pipeLineFacadeIface.loadAreadsByParentCode(Nbyhpc.AREA_CODE);
		this.getRequest().getSession(true).setAttribute("includeMenu", PipeLineCompanyInfoAction.RQ_MENU_NAME);
		return INPUT;
	}
	
	public String delete() throws Exception {
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要删除的信息");
			return MESSAGE_TO_BACK;
		}
		pipeLineFacadeIface.deletePipelineById(entity.getId());
		return this.list();
	}
	
	public String deleteAll() {
		try{
			pipeLineFacadeIface.deleteAll(ids);
			this.setMessageKey("删除成功!");
		} catch (Exception e){
			e.printStackTrace();
			this.setMessageKey("删除失败!");
		}
		this.getRequest().setAttribute("url", "rq_pipeline2_loadPipeLines.xhtml");
		return MESSAGE_TO_REDIRECT;
	}
	
	public String list() throws Exception {
		if (entity != null && entity.getDaPipelineCompanyinfo() != null && entity.getDaPipelineCompanyinfo().getId() > 0){
			result = pipeLineFacadeIface.findPiplineByCompanyInfoId(entity.getDaPipelineCompanyinfo().getId());
		}
		if (result == null || result.size() == 0){
			return create();
		}
		if (result.size() == 1){//只有一条记录时，直接进入编辑页面
			entity = (DaRqPipelineInfo)result.get(0);
			update();
			return INPUT;
		}
		return "list";
	}
	
	public String ajaxSave() throws Exception {
		if (entity == null){
			this.setMessageKey("没有找到要保存的信息");
			return NONE;
		}
		if (entity.getDaPipelineCompanyinfo() == null || entity.getDaPipelineCompanyinfo().getId() <= 0){
			this.setMessageKey("没有找到要保存的企业");
			return NONE;
		}
		if(entity.getAreaCode()!=null){
			FkArea area = statisticFacadeIface.loadAreaByAreaCodeZhjg(entity.getAreaCode());
			if(area!=null){
				entity.setName(area.getAreaName());
			}
		}
		if (entity.getId() > 0 ){
			pipeLineFacadeIface.updatePipeline(entity);
		}else{
			entity.setDeleted(true);
			pipeLineFacadeIface.createPipeline(entity);
		}
		this.setMessageKey("保存成功!");
		this.getResponse().getWriter().print(entity.getId());
		this.getResponse().getWriter().close();
		return NONE;
	}
	
	public String save() throws Exception {
		if (entity == null){
			this.setMessageKey("没有找到要保存的信息");
			return MESSAGE_TO_BACK;
		}
		if (entity.getDaPipelineCompanyinfo() == null || entity.getDaPipelineCompanyinfo().getId() <= 0){
			this.setMessageKey("没有找到要保存的企业");
			return MESSAGE_TO_BACK;
		}
		if(entity.getAreaCode()!=null){
			FkArea area = statisticFacadeIface.loadAreaByAreaCodeZhjg(entity.getAreaCode());
			if(area!=null){
				entity.setName(area.getAreaName());
			}
		}
		if (entity.getId() > 0 ){
			pipeLineFacadeIface.updatePipeline(entity);
		}else{
			pipeLineFacadeIface.createPipeline(entity);
		}
		this.setMessageKey("保存成功!");
		//this.getRequest().setAttribute("url", "rq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=" + entity.getDaPipelineCompanyinfo().getId());
		this.getRequest().setAttribute("url", "rq_pipeline_update.xhtml?entity.id=" + entity.getId());
		return MESSAGE_TO_REDIRECT;
	}
	
	public String view() throws Exception {
		return "view";
	}

	public String rqStatistic() throws Exception{
		areas = statisticFacadeIface.loadAreas(Nbyhpc.AREA_CODE);
		result = pipeLineFacadeIface.rqStatistic(this.entity);
		return "yq-statistic";
	}
	
	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
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

	public DaRqPipelineInfo getEntity() {
		return entity;
	}

	public void setEntity(DaRqPipelineInfo entity) {
		this.entity = entity;
	}

	public List getResult() {
		return result;
	}

	public List getAreas() {
		return areas;
	}

	public void setStatisticFacadeIface(StatisticFacadeIface statisticFacadeIface) {
		this.statisticFacadeIface = statisticFacadeIface;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String getResultPage() {
		if(resultPage == null || "".equals(resultPage)){
			if(entity == null || entity.getPipeVersion() == null || "".equals(entity.getPipeVersion())){
				entity.setPipeVersion(getVersion());
				resultPage="/template/pipeline/rq-pipeline-input-v"+getVersion()+".ftl";
			}else if("1".equals(entity.getPipeVersion())){
				resultPage="/template/pipeline/rq-pipeline-input.ftl";
			}else{
				resultPage="/template/pipeline/rq-pipeline-input-v"+entity.getPipeVersion()+".ftl";
			}
		}
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public String getVersion() {
		if(version == null || "".equals(version)){
			version = "20140116";
		}
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

