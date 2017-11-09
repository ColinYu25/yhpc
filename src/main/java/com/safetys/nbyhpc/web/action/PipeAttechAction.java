package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaPipeAttech;
import com.safetys.nbyhpc.facade.iface.PipeAttechFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.util.OperateResult;

public class PipeAttechAction extends BaseAppAction{
	
	private List result;
	private DaPipeAttech entity;
	protected Pagination pagination;
	private PipeAttechFacadeIface pipeAttechFacadeIface;
	protected PipeLineFacadeIface pipeLineFacadeIface;
	/**返回的页面*/
	private String resultPage;
	/**默认的最新的页面版本*/
	private String version = "20140116";
	
	public String save() throws Exception{
		if (entity == null){
			this.setMessageKey("没有找到要保存的对象");
			return MESSAGE_TO_BACK;
		}
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		if (entity.getUploadFile() != null && entity.getUploadFile().isFile()){
			OperateResult result = pipeAttechFacadeIface.uploadFile(entity);
			if (!result.isState()){
				this.setMessageKey(result.getMessage());
				return MESSAGE_TO_BACK;
			}
			entity.setAttechFile(result.getResult().toString());
		}
		
		if (entity.getId() > 0){
			pipeAttechFacadeIface.update(entity);
		}else{
			pipeAttechFacadeIface.create(entity);
		}
		return SUCCESS;
	}

	public String create() throws Exception{
		return INPUT;
	}
	
	public String update() throws Exception{
		if (entity == null || entity.getId() <= 0){
			this.setMessageKey("没有找到要修改的对象");
			return MESSAGE_TO_BACK;
		}
		entity = (DaPipeAttech)pipeAttechFacadeIface.findById(DaPipeAttech.class, entity.getId());
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		return INPUT;
	}

	public String delete() throws Exception{
		pipeAttechFacadeIface.delete(entity);
		return SUCCESS;
	}

	public String list() throws Exception{
		if (entity == null || entity.getDaPipelineInfo() == null || entity.getDaPipelineInfo().getId() == null
				|| entity.getDaPipelineInfo().getId() <= 0){
			this.setMessageKey("请先保存管道信息");
			return MESSAGE_TO_BACK;
		}
		result = pipeAttechFacadeIface.find(entity, this.getPagination());
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		return "list";
	}

	public DaPipeAttech getEntity() {
		return entity;
	}

	public void setEntity(DaPipeAttech entity) {
		this.entity = entity;
	}

	public List getResult() {
		return result;
	}

	public void setPipeAttechFacadeIface(PipeAttechFacadeIface pipeAttechFacadeIface) {
		this.pipeAttechFacadeIface = pipeAttechFacadeIface;
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

	public PipeLineFacadeIface getPipeLineFacadeIface() {
		return pipeLineFacadeIface;
	}

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getResultPage() {
		if(resultPage == null || "".equals(resultPage)){
			if(getVersion() == null || "".equals(getVersion()) || "1".equals(getVersion())){
				resultPage="/template/pipeline/rq-pipeline-input.ftl";
			}else{
				resultPage="/template/pipeline/rq-pipeline-v"+getVersion()+".ftl";
			}
		}
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
