package com.safetys.nbyhpc.web.action.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.StringUtils;

import com.safetys.nbyhpc.domain.model.DaDangerImage;
import com.safetys.nbyhpc.facade.iface.DaDangerImageFacadeIface;
import com.safetys.nbyhpc.util.DateUtils;
import com.safetys.nbyhpc.util.FileUpload;

public class MUploadAction extends AndroidBaseAction {

	private static final long serialVersionUID = 7508769680402397323L;

	private List<File> upFiles = new ArrayList<File>();
	
	private DaDangerImageFacadeIface daDangerImageFacadeIface;
	
	public String upload() {
		try {
			String realpathdir = ServletActionContext.getServletContext().getRealPath("/");
			String webrootpathdir = getWebRootPathDir();
			// 创建照片存放目录
			File rootRealDir = new File(realpathdir + "/" + webrootpathdir);// 父文件夹
			if (!rootRealDir.exists()) {
				rootRealDir.mkdirs();
			}
			if (upFiles != null) {
				String ids = "";
				for (File file : upFiles) {
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
						image.setCreateTime(new Date());
						image.setModifyTime(image.getCreateTime());
						image.setName(ordinaryName);
						image.setPath(webrootpathdir + "/" + fileName);
						daDangerImageFacadeIface.create(image);
						ids += image.getId() + ",";
					}
				}
				if (StringUtils.hasText(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				}
				jsonResult.setData(ids);
				jsonResult.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMsg("服务请求失败");
			jsonResult.setSuccess(false);
			jsonResult.setIdentify(M_EXCEPTION);
		}
		return SUCCESS;
	}
	
	private String getWebRootPathDir(){
		String path = FileUpload.DANGER_IMAGE_PATH;
		path +=  "/mobile";
		path += "/" + DateUtils.date2Str(new Date(), DateUtils.SHORT_TIME);
		return path;
	}

	public List<File> getUpFiles() {
		return upFiles;
	}

	public void setUpFiles(List<File> upFiles) {
		this.upFiles = upFiles;
	}

	public void setDaDangerImageFacadeIface(DaDangerImageFacadeIface daDangerImageFacadeIface) {
		this.daDangerImageFacadeIface = daDangerImageFacadeIface;
	}
	
}
