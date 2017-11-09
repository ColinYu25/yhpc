package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.domain.model.FkArea;

/**
 * 
 * @author yangb
 *
 */
public interface FkAreaFacadeIface {

	 public List<FkArea> loadChildrens(Long areaCode);
	 
	 public FkArea findByCode(Long areaCode);
	 
}
