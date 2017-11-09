package com.safetys.nbyhpc.web.action.mobile;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.nbyhpc.domain.model.android.ClientChangeLog;
import com.safetys.nbyhpc.facade.iface.android.ClientChangeLogFacadeIface;
import com.safetys.nbyhpc.util.OsType;
import com.safetys.nbyhpc.util.OsUserType;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

/**
 * 版本信息
 * @author yangb
 *
 */
public class ClientChangeLogAction extends DaAppAction {

	private static final long serialVersionUID = 6892978816004768478L;

	private ClientChangeLog entity;
	
	private File file;
	
	private String fileFileName;
	
	private EnumSet<OsType> osTypeList;
	
	private EnumSet<OsUserType> osUserTypeList;
	
	protected Pagination pagination;
	
	private String type;
	
	private List<ClientChangeLog> clientChangeLogList;
	
	private ClientChangeLogFacadeIface clientChangeLogFacadeIface;
	
	private long maxVersionNumES;
	private long maxVersionNumGOV;

	public String list() {
		osUserTypeList = OsUserType.getOsUserTypes();
		clientChangeLogList = clientChangeLogFacadeIface.loadClientChangeLogs(entity, getPagination());
		return SUCCESS;
	}
	
	public String show() {
		osTypeList = OsType.getOsTypes();
		osUserTypeList = OsUserType.getOsUserTypes();
		maxVersionNumES = clientChangeLogFacadeIface.loadMaxVersionNum(OsUserType.ES.getCode());
		maxVersionNumGOV = clientChangeLogFacadeIface.loadMaxVersionNum(OsUserType.GOV.getCode());
		return SUCCESS;
	}
	
	public String create() {
		clientChangeLogFacadeIface.create(entity, file, fileFileName);
		return SUCCESS;
	}
	
	public void generateBarCode() throws WriterException, IOException {
		ClientChangeLog entity = clientChangeLogFacadeIface.loadLastedClientChangeLog(OsType.ANDROID.getCode(), type);
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		String filePath = realPath + entity.getFileInfo();
		File file = new File(filePath);
		if (file.exists()) {
			//拷贝一份以文件名命名的apk到客户端备份目录
			File rootRealDir = new File(realPath +"/android");//父文件夹
			if(!rootRealDir.exists()) {
				rootRealDir.mkdirs();
			}
			// 构造实体文件
			File diskFile = new File(rootRealDir, entity.getOrignalFileName());
			// 保存到硬盘
			FileUtils.copyFile(file, diskFile);
			
			//生成新二维码
			String path = realPath +"/barcode";
			File qrFile = new File(path, "generate_auto.gif");
			
			String requestContext = getRequest().getContextPath();  
			String basePath = getBaseURL()+requestContext;
			String content = basePath +"/android/" + entity.getOrignalFileName();
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 128, 128, hints);
			MatrixToImageWriter.writeToFile(bitMatrix, "gif", qrFile);
		}
		getResponse().getWriter().print("true");
	}
	
	public List<ClientChangeLog> getClientChangeLogList() {
		return clientChangeLogList;
	}

	public void setClientChangeLogList(List<ClientChangeLog> clientChangeLogList) {
		this.clientChangeLogList = clientChangeLogList;
	}

	public ClientChangeLog getEntity() {
		return entity;
	}

	public void setEntity(ClientChangeLog entity) {
		this.entity = entity;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public EnumSet<OsType> getOsTypeList() {
		return osTypeList;
	}

	public void setOsTypeList(EnumSet<OsType> osTypeList) {
		this.osTypeList = osTypeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EnumSet<OsUserType> getOsUserTypeList() {
		return osUserTypeList;
	}

	public void setOsUserTypeList(EnumSet<OsUserType> osUserTypeList) {
		this.osUserTypeList = osUserTypeList;
	}

	public void setClientChangeLogFacadeIface(ClientChangeLogFacadeIface clientChangeLogFacadeIface) {
		this.clientChangeLogFacadeIface = clientChangeLogFacadeIface;
	}

	public long getMaxVersionNumES() {
		return maxVersionNumES;
	}

	public void setMaxVersionNumES(long maxVersionNumES) {
		this.maxVersionNumES = maxVersionNumES;
	}

	public long getMaxVersionNumGOV() {
		return maxVersionNumGOV;
	}

	public void setMaxVersionNumGOV(long maxVersionNumGOV) {
		this.maxVersionNumGOV = maxVersionNumGOV;
	}
	
}
