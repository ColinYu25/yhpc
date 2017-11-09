package com.safetys.nbyhpc.facade.iface;

import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.ZjIdea;

public interface IdeaFacadeIface{
	public void createIdea(ZjIdea idea) throws ApplicationAccessException;;	
	public List<ZjIdea> loadIdeas(Long id)throws ApplicationAccessException;
}
