package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.TCoreArea;

public interface PubAreaFacadeIface {
	/**
	 *  根据区域编码取子区域
	 * @param areaCode 区域编码
	 * @return {@link List}
	 * @throws ApplicationAccessException
	 */
	public List<TCoreArea> findChildAreas(Long areaCode) throws ApplicationAccessException;
}
