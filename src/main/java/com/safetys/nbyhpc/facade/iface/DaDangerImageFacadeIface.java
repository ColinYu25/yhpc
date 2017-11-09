package com.safetys.nbyhpc.facade.iface;

import java.io.File;
import java.util.List;

import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;

public interface DaDangerImageFacadeIface extends BaseFacadeIface<DaDangerImage> {

	public void createByNormalDanger(DaNomalDanger danger);
	
	public void createByDanger(DaDanger danger);
	
	public List<DaDangerImage> findByDanger(DaDanger danger);
	
	public List<DaDangerImage> findByNormalDanger(DaNomalDanger danger);
	
	public void delete(DaDangerImage entity);
	
	public void createByApp(DaNomalDanger nomalDanger, List<File> imageFileList) throws Exception;
}
