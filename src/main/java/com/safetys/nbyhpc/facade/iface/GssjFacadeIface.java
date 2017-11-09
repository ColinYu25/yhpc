package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.GsQydj;

/**
 * 
 */

public interface GssjFacadeIface {

	public List<GsQydj> loadGssj(GsQydj gs, Pagination page);
}
