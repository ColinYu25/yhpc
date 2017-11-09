package com.safetys.nbyhpc.domain.model;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

/**
 * 隐患图片
 * @author yangb
 *
 */
public class DaDangerImage extends DaBaseModel {
	
	private static final long serialVersionUID = 5305305145361197812L;

	private String name;
	
	private String path;
	
	private Long userId;
	
	private DaNomalDanger daNomalDanger;
	
	private DaDanger daDanger;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public DaNomalDanger getDaNomalDanger() {
		return daNomalDanger;
	}

	public void setDaNomalDanger(DaNomalDanger daNomalDanger) {
		this.daNomalDanger = daNomalDanger;
	}

	public DaDanger getDaDanger() {
		return daDanger;
	}

	public void setDaDanger(DaDanger daDanger) {
		this.daDanger = daDanger;
	}
	
}
