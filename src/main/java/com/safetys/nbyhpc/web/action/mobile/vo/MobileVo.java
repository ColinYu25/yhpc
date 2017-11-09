package com.safetys.nbyhpc.web.action.mobile.vo;

import java.io.Serializable;

import com.safetys.nbyhpc.domain.model.base.DaBaseModel;

public abstract class MobileVo implements Serializable{

	private static final long serialVersionUID = 1977949369523762925L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	protected void buildVo(DaBaseModel model) {
		this.id = model.getId();
	}

}
