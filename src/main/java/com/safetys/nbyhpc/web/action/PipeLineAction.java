package com.safetys.nbyhpc.web.action;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.safetys.framework.domain.model.FkArea;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaYqPipelineInfo;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class PipeLineAction extends BaseAppAction{
	
	protected StatisticFacadeIface statisticFacadeIface;
	protected PipeLineFacadeIface pipeLineFacadeIface;
	protected Pagination pagination;
	private DaYqPipelineInfo entity;
	protected List result;
	protected List areas;
	private DaCompany company;
	private String ids;//ID字符串
	private String fileName = "";
	private InputStream inputStream = null;
	/**返回的页面*/
	private String resultPage;
	/**默认的最新的页面版本*/
	private String version;
	
	/**
	 * 管道列表 管道类型：0:全部， 1：长输管道 2：城镇燃气管道 3：工业管道 4：港区内油气管道 
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
				result = pipeLineFacadeIface.loadYqPipeLines(entity, company, "", getPagination(), this.getUserDetail());
			} else {
				result = pipeLineFacadeIface.loadYqPipeLines(entity, null, "", getPagination(), this.getUserDetail());
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String create() throws Exception {
		return INPUT;
	}
	
	public String update() throws Exception {
		if (entity == null || entity.getId() == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的信息");
			return MESSAGE_TO_BACK;
		}
		entity = (DaYqPipelineInfo)pipeLineFacadeIface.findById(DaYqPipelineInfo.class, entity.getId());
		
		if (entity != null){
			this.getRequest().getSession(true).setAttribute(PipeLineCompanyInfoAction.PIPELINE_ID_KEY, entity.getDaPipelineCompanyinfo().getId());
			this.getRequest().getSession(true).setAttribute(PipeLineCompanyInfoAction.COMPANY_ID_KEY, entity.getDaPipelineCompanyinfo().getCompany().getId());
		}
		this.getRequest().getSession(true).setAttribute("includeMenu", PipeLineCompanyInfoAction.YQ_MENU_NAME);
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
	
	public String deleteAll() throws Exception {
		try{
			pipeLineFacadeIface.deleteAll(ids);
			this.setMessageKey("删除成功!");
		} catch (Exception e){
			e.printStackTrace();
			this.setMessageKey("删除失败!");
		}
		if(null==entity){
			entity = new DaYqPipelineInfo();
		}
		this.getRequest().setAttribute("url", "yq_pipeline2_loadPipeLines.xhtml?entity.type="+entity.getType());
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
			entity = (DaYqPipelineInfo)result.get(0);
			return "to_update";
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
		if (!entity.isHasDj()){//如果没有登记，则删除登记编号
			entity.setDjNum(null);
		}
		if (!entity.isHasGhLicence()){//如果没有规划，则删除规划许可证号
			entity.setGhLicence(null);
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
		if (!entity.isHasDj()){//如果没有登记，则删除登记编号
			entity.setDjNum(null);
		}
		if (!entity.isHasGhLicence()){//如果没有规划，则删除规划许可证号
			entity.setGhLicence(null);
		}
		if (entity.getId() > 0 ){
			pipeLineFacadeIface.updatePipeline(entity);
		}else{
			pipeLineFacadeIface.createPipeline(entity);
		}
		this.setMessageKey("保存成功!");
		//this.getRequest().setAttribute("url", getListUrl()+ entity.getDaPipelineCompanyinfo().getId());
		this.getRequest().setAttribute("url", "yq_pipeline_update.xhtml?entity.id=" + entity.getId());
		return MESSAGE_TO_REDIRECT;
	}
	
	public String view() throws Exception {
		return "view";
	}

	public String yqStatistic() throws Exception{
		areas = statisticFacadeIface.loadAreas(Nbyhpc.AREA_CODE);
		FkArea a = new FkArea();a.setAreaCode(Nbyhpc.AREA_CODE); a.setAreaName("其他");
		areas.add(a);
		result = pipeLineFacadeIface.yqStatistic(this.entity);
		return "yq-statistic";
	}
	
	/**
	 * 保存成功后的跳转地址，子类可覆盖该方法，而实现跳转不同的地址
	 * @return
	 */
	protected String getListUrl(){
		return "yq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=";
	}
	
	/**
	 * 导出EXCEL
	 * @return
	 */
	public String export() {
		this.fileName = "危险化学品输送管道安全现状调查汇总表.xls";
		String realPath =((ServletContext) ActionContext.getContext().get(ServletActionContext.SERVLET_CONTEXT)).getRealPath("/");
		try {
			String userIndustry = this.getUserDetail().getUserIndustry();
			if(null!=userIndustry && "qiye".equals(userIndustry)) {
				if(company == null){
					company = pipeLineFacadeIface.loadCompanyByComUserId(this.getUserDetail());
				}
			}
			if(entity == null){
				entity = new DaYqPipelineInfo();
			}
			entity.setFileName(fileName);
			entity.setRealPath(realPath);
			inputStream = pipeLineFacadeIface.export(entity, company, getPagination(), this.getUserDetail());
			this.fileName = new String(this.fileName.getBytes("GB2312"), "iso8859-1");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
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

	public DaYqPipelineInfo getEntity() {
		return entity;
	}

	public void setEntity(DaYqPipelineInfo entity) {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getResultPage() {
		if(resultPage == null || "".equals(resultPage)){
			if(entity == null || entity.getPipeVersion() == null || "".equals(entity.getPipeVersion())){
				if(entity==null){
					entity = new DaYqPipelineInfo(); 
				}
				entity.setPipeVersion(getVersion());
				resultPage="/template/pipeline/yq-pipeline-input-v"+getVersion()+".ftl";
			}else if("1".equals(entity.getPipeVersion())){
				resultPage="/template/pipeline/yq-pipeline-input.ftl";
			}else{
				resultPage="/template/pipeline/yq-pipeline-input-v"+entity.getPipeVersion()+".ftl";
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

