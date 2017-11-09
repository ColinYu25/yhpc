package com.safetys.nbyhpc.web.action;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.GsQydj;
import com.safetys.nbyhpc.facade.iface.GssjFacadeIface;

public class GssjAction extends BaseAppAction {

	private static final long serialVersionUID = 7855126487056199328L;

	private List<GsQydj> gsQydjs;

	private GsQydj gsQydj;

	protected Pagination pagination;

	private GssjFacadeIface gssjFacadeIface;

	public String loadAll() throws Exception {
		gsQydjs = gssjFacadeIface.loadGssj(gsQydj, this.getPagination());
		return "list";
	}

	public Pagination getPagination() {
		if (pagination == null) {
			pagination = new Pagination();
			pagination.setPageSize(10);
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public GsQydj getGsQydj() {
		return gsQydj;
	}

	public void setGsQydj(GsQydj gsQydj) {
		this.gsQydj = gsQydj;
	}

	public List<GsQydj> getGsQydjs() {
		return gsQydjs;
	}

	public void setGsQydjs(List<GsQydj> gsQydjs) {
		this.gsQydjs = gsQydjs;
	}

	public void setGssjFacadeIface(GssjFacadeIface gssjFacadeIface) {
		this.gssjFacadeIface = gssjFacadeIface;
	}

}
