package com.safetys.nbyhpc.facade.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.safetys.nbyhpc.domain.model.DaDanger;
import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.domain.model.DaNomalDanger;
import com.safetys.nbyhpc.domain.persistence.iface.DaDangerImagePersistenceIface;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.FileUpload;

public class DaDangerImageFacadeImpl extends BaseFacadeImpl<DaDangerImage> implements DaDangerImageFacadeIface {

	private DaDangerImagePersistenceIface daDangerImagePersistenceIface;
	
	@Override
	public void createByNormalDanger(DaNomalDanger danger) {
		FileUpload.uploadByObjs(danger, null);
		List<DaDangerImage> daDangerImageList = danger.getDaDangerImageList();
		if (daDangerImageList != null && !daDangerImageList.isEmpty()) {
			for (DaDangerImage daDangerImage : daDangerImageList) {
				daDangerImage.setDaNomalDanger(danger);
				daDangerImagePersistenceIface.create(daDangerImage);
			}
		}
	}

	@Override
	public void createByDanger(DaDanger danger) {
		FileUpload.uploadByObjs(danger, null);
		List<DaDangerImage> daDangerImageList = danger.getDaDangerImageList();
		if (daDangerImageList != null && !daDangerImageList.isEmpty()) {
			for (DaDangerImage daDangerImage : daDangerImageList) {
				daDangerImage.setDaDanger(danger);
				daDangerImagePersistenceIface.create(daDangerImage);
			}
		}
	}

	@Override
	public List<DaDangerImage> findByDanger(DaDanger danger) {
		String hql = "from DaDangerImage obj where obj.deleted = false and obj.daDanger.id = ?";
		List<DaDangerImage> list = daDangerImagePersistenceIface.findByHql(hql, danger.getId());
		return list != null ? list : new ArrayList<DaDangerImage>();
	}

	@Override
	public List<DaDangerImage> findByNormalDanger(DaNomalDanger danger) {
		String hql = "from DaDangerImage obj where obj.deleted = false and obj.daNomalDanger.id = ?";
		List<DaDangerImage> list = daDangerImagePersistenceIface.findByHql(hql, danger.getId());
		return list != null ? list : new ArrayList<DaDangerImage>();
	}

	@Override
	public void createByApp(DaNomalDanger nomalDanger, List<File> imageFileList) throws Exception {
		String realpathdir = ServletActionContext.getServletContext().getRealPath("/");
		String webrootpathdir = getWebRootPathDir();
		// 创建照片存放目录
		File rootRealDir = new File(realpathdir + "/" + webrootpathdir);// 父文件夹
		if (!rootRealDir.exists()) {
			rootRealDir.mkdirs();
		}
		if (imageFileList != null) {
			for (File file : imageFileList) {
				if (file != null) {
					String ordinaryName = file.getName();
					// 构造文件唯一名称
					String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpeg";
					// 构造实体文件
					File diskFile = new File(rootRealDir, fileName);
					// 保存硬盘
					FileUtils.copyFile(file, diskFile);
					// 插入移动附件表
					DaDangerImage image = new DaDangerImage();
					image.setDaNomalDanger(nomalDanger);
					image.setCreateTime(new Date());
					image.setModifyTime(image.getCreateTime());
					image.setName(ordinaryName);
					image.setPath(webrootpathdir + "/" + fileName);
					create(image);
				}
			}
		}
	}

	private String getWebRootPathDir(){
		String path = FileUpload.DANGER_IMAGE_PATH;
		path +=  "/mobile";
		path += "/" + DateUtils.date2Str(new Date(), DateUtils.SHORT_TIME);
		return path;
	}
	
	@Override
	public void delete(DaDangerImage entity) {
		daDangerImagePersistenceIface.deleteFake(entity);
	}

	public void setDaDangerImagePersistenceIface(DaDangerImagePersistenceIface daDangerImagePersistenceIface) {
		this.daDangerImagePersistenceIface = daDangerImagePersistenceIface;
	}

	@Override
	public DaDangerImagePersistenceIface getService() {
		return daDangerImagePersistenceIface;
	}

}
