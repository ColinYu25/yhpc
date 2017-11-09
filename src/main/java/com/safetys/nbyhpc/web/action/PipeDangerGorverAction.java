package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaPipeDanger;
import com.safetys.nbyhpc.domain.model.DaPipeDangerGorver;
import com.safetys.nbyhpc.domain.model.DaPipelineCompanyInfo;
import com.safetys.nbyhpc.domain.model.DaPipelineInfo;
import com.safetys.nbyhpc.facade.iface.PipeDangerGorverFacadeIface;
import com.safetys.nbyhpc.facade.iface.PipeLineFacadeIface;
import com.safetys.nbyhpc.web.action.base.DaAppAction;
/**
 * @(#) DangerGorverAction.java
 * @date 2009-08-17
 * @author lvx
 * @copyright (c) 2009 Safetys.cn All rights reserved.
 * 
 */
public class PipeDangerGorverAction extends DaAppAction{

	/**
	 * 重大隐患整改信息
	 */
	private static final long serialVersionUID = 6806059411616329986L;

	private PipeDangerGorverFacadeIface pipeDangerGorverFacadeIface;//重大隐患整改信息的业务接口
	
	private List<DaPipeDangerGorver> dangerGorvers;//重大隐患整改集合
	
	private DaPipeDangerGorver dangerGorver;//重大隐患整改
	
	private DaPipeDanger danger;
	
	private DaPipelineCompanyInfo pipelineCompanyInfo;// 企业单位
	
	private Pagination pagination;//分页对象
	
	private String ids;//重大隐患整改序号的集合，针对类似于批量删除操作
	
	private DaPipelineInfo entity;// 管道
	
	private PipeLineFacadeIface pipeLineFacadeIface;// 管道业务实现接口
	
	private DaCompany company;// 企业
	
	/**
	 * 加载一个重大隐患整改信息的内容
	 * @return
	 */
	public String createDangerGorverInit(){
		try {
			danger = pipeDangerGorverFacadeIface.loadDanger(new DaPipeDanger(dangerGorver.getDaPipeDanger().getId()));
			entity = pipeLineFacadeIface.findPipelineById(danger.getPipeLine().getId());
			danger.setPipeLine(entity);
			dangerGorvers = pipeDangerGorverFacadeIface.loadDangerGorversOfOne(dangerGorver);
			if (dangerGorvers.size() > 0) {
				dangerGorver = dangerGorvers.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 创建重大隐患整改信息
	 * @return
	 */
	public String createDangerGorver(){
		try {
			dangerGorver.setUserId(getUserId());
			pipeDangerGorverFacadeIface.createDangerGorver(dangerGorver);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载一个重大隐患整改信息的内容，或用于显示或预备修改
	 * @return
	 */
	public String loadDangerGorver(){
		try {
			dangerGorver = pipeDangerGorverFacadeIface.loadDangerGorver(dangerGorver);
			danger = pipeDangerGorverFacadeIface.loadDanger(new DaPipeDanger(dangerGorver.getDaPipeDanger().getId()));
			entity = pipeLineFacadeIface.findPipelineById(danger.getPipeLine().getId());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改重大隐患整改信息
	 * @return
	 */
	public String updateDangerGorver(){
		try {
			dangerGorver.setUserId(getUserId());
			pipeDangerGorverFacadeIface.updateDangerGorver(dangerGorver);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除重大隐患整改信息
	 * @return
	 */
	public String deleteDangerGorver(){
		try {
			pipeDangerGorverFacadeIface.deleteDangerGorver(dangerGorver);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public List<DaPipeDangerGorver> getDangerGorvers() {
		return dangerGorvers;
	}
	public void setDangerGorvers(List<DaPipeDangerGorver> dangerGorvers) {
		this.dangerGorvers = dangerGorvers;
	}
	public DaPipeDangerGorver getDangerGorver() {
		return dangerGorver;
	}
	public void setDangerGorver(DaPipeDangerGorver dangerGorver) {
		this.dangerGorver = dangerGorver;
	}
	public DaPipeDanger getDanger() {
		return danger;
	}
	public void setDanger(DaPipeDanger danger) {
		this.danger = danger;
	}
	public DaPipelineCompanyInfo getPipelineCompanyInfo() {
		return pipelineCompanyInfo;
	}
	public void setPipelineCompanyInfo(DaPipelineCompanyInfo pipelineCompanyInfo) {
		this.pipelineCompanyInfo = pipelineCompanyInfo;
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
	public PipeDangerGorverFacadeIface getPipeDangerGorverFacadeIface() {
		return pipeDangerGorverFacadeIface;
	}
	public void setPipeDangerGorverFacadeIface(
			PipeDangerGorverFacadeIface pipeDangerGorverFacadeIface) {
		this.pipeDangerGorverFacadeIface = pipeDangerGorverFacadeIface;
	}

}
