package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaPipeTrouble;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeTroubleFacadeIface;

public class PipeTroubleAction extends BaseAppAction{
	
	private List result;
	private DaPipeTrouble entity;
	protected Pagination pagination;
	private PipeTroubleFacadeIface pipeTroubleFacadeIface;
	protected PipeLineFacadeIface pipeLineFacadeIface;
	
	public String save() throws Exception{
		if (entity == null){
			this.setMessageKey("没有找到要保存的对象");
			return MESSAGE_TO_BACK;
		}
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		if (entity.getId() > 0){
			pipeTroubleFacadeIface.update(entity);
		}else{
			pipeTroubleFacadeIface.create(entity);
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
		entity = (DaPipeTrouble)pipeTroubleFacadeIface.findById(DaPipeTrouble.class, entity.getId());
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		return INPUT;
	}

	public String delete() throws Exception{
		pipeTroubleFacadeIface.delete(entity);
		return SUCCESS;
	}

	public String list() throws Exception{
		if (entity == null || entity.getDaPipelineInfo() == null || entity.getDaPipelineInfo().getId() == null
				|| entity.getDaPipelineInfo().getId() <= 0){
			this.setMessageKey("请先保存管道信息");
			return MESSAGE_TO_BACK;
		}
		entity.setDaPipelineInfo(pipeLineFacadeIface.findPipelineById(entity.getDaPipelineInfo().getId()));
		result = pipeTroubleFacadeIface.find(entity, this.getPagination());
		return "list";
	}

	public DaPipeTrouble getEntity() {
		return entity;
	}

	public void setEntity(DaPipeTrouble entity) {
		this.entity = entity;
	}

	public List getResult() {
		return result;
	}

	public void setPipeTroubleFacadeIface(PipeTroubleFacadeIface pipeTroubleFacadeIface) {
		this.pipeTroubleFacadeIface = pipeTroubleFacadeIface;
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

	public void setPipeLineFacadeIface(PipeLineFacadeIface pipeLineFacadeIface) {
		this.pipeLineFacadeIface = pipeLineFacadeIface;
	}
}
