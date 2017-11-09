package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;


/**
 * 企业服务平台消息实体
 * @author weiyj
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = -5827739098339177282L;
	
	private String name;//标题
	
	private String releasetime;//日期
	
	private String projectName = "project_9";//信息来源项目名称（企业诚信系统默认为project_7）
	
	private String projectLike;//信息来源连接
	
	private String projectType="type_3";//信息类型(type_1 - 工作提醒; type_2 - 到期提醒 ; type_3 - 状态查询)

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReleasetime() {
		return releasetime;
	}

	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectLike() {
		return projectLike;
	}

	public void setProjectLike(String projectLike) {
		this.projectLike = projectLike;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}
