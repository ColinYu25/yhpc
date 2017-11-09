package com.safetys.nbyhpc.web.action.mobile.vo;

import com.safetys.nbyhpc.domain.model.DaDangerImage;


/**
 * 
 * @author yangb
 *
 */
public class DangerImageVo extends MobileVo {

	private static final long serialVersionUID = 6089829869044617802L;

	private String name;
	
	private String path;
	
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

	public void buildByDangerImage(DaDangerImage dangerImage) {
		super.buildVo(dangerImage);
		this.name = dangerImage.getName();
		this.path = dangerImage.getPath();
	}
}
