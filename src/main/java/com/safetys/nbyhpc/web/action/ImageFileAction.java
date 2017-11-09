package com.safetys.nbyhpc.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.web.action.base.BaseAppAction;
import com.safetys.nbyhpc.domain.model.DaCompany;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.domain.model.DaIndustryParameter;
import com.safetys.nbyhpc.facade.iface.ImageFileFacadeIface;
import com.safetys.nbyhpc.util.FileUpload;

public class ImageFileAction extends BaseAppAction {

	private static final long serialVersionUID = 1817492539653819504L;

	/**
	 * 文件
	 */
	
	private String fileName;

	private String factName;// 文件名称
	
	private DaImageFile daImageFile;
	
	private ImageFileFacadeIface imageFileFacadeIface;
	
	private Pagination pagination;

	private List<DaImageFile> daImageFiles;
	
	private Date beginTime; //开始时间
	
	private Date endTime; //结束时间
	
	private boolean canEdit;
	
	private String url;
	
	private List<DaImageFile> imageFileList; // 图片上传列表
	
	private DaCompany company;
	
	private DaImageFile imageOne;

	private DaImageFile imageTwo;

	/**
	 * 添加文件初始化
	 * 
	 * */
	public String createFileInit(){
		return SUCCESS;
	}
	
	/**
	 * 加载图片
	 * 
	 * @param
	 * @return 返回
	 * @throws Exception
	 */
	public String loadNbajjTwoImage() {
		try {
			imageFileList = imageFileFacadeIface.loadFiles(company.getId());
			if (null != imageFileList && imageFileList.size() > 0) {
				if (null != imageFileList.get(0)) {
					if("2".equals(imageFileList.get(0).getFileDescription())){
						imageTwo = imageFileList.get(0);
					}else{
						imageOne = imageFileList.get(0);
					}
					
				}
			}
			if (null != imageFileList && imageFileList.size() > 1) {
				if (null != imageFileList.get(1)) {
					if(imageOne == null){
						imageOne = imageFileList.get(1);
					}else{
						imageTwo = imageFileList.get(1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//return ERROR;
		}
		return SUCCESS;
	}

	
	/**
	 * 添加文件
	 * 
	 * */
	public String createFile(){
		try {
			if(daImageFile==null){
				daImageFile=new DaImageFile();
			}
			daImageFile.setUserId(getUserId());
			imageFileFacadeIface.create(daImageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}	
		setMessageKey("file.upload.success");//文件上传成功
		if("old".equals(daImageFile.getUrlPath()) && daImageFile.getUrlPath() != null){
			setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId()+"&daImageFile.urlPath="+daImageFile.getUrlPath());
		}else{
			setUrl("../coreCompany/loadCoreCompanyInfo.xhtml?coreCompany.id="+daImageFile.getDaCompany().getId());
		}
		
		 //setUrl("../coreCompany/loadCoreCompanyInfo.xhtml?coreCompany.id="+daImageFile.getDaCompany().getId());
         //setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId());
         return MESSAGE_TO_REDIRECT;
	}

	/**
	 * 查看
	 * 
	 * */
	public String loadFile(){
		try {
			daImageFile=imageFileFacadeIface.loadFile(daImageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * 
	 * */
	public String delete(){
		try {
			daImageFile.setUserId(getUserId());
			setUploadRealPath();
			imageFileFacadeIface.deleteTrue(daImageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("file.upload.delsuccess");//文件删除成功
		if("old".equals(daImageFile.getUrlPath()) && daImageFile.getUrlPath() != null){
			setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId()+"&daImageFile.urlPath="+daImageFile.getUrlPath());
		}else{
			setUrl("../coreCompany/loadCoreCompanyInfo.xhtml?coreCompany.id="+daImageFile.getDaCompany().getId());
		}
		//setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId());
		return MESSAGE_TO_REDIRECT;
	}
	
	/**
	 * 修改
	 * 
	 * */
	public String updateFile(){
		try {
			daImageFile.setUserId(getUserId());
			setUploadRealPath();
			imageFileFacadeIface.update(daImageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setMessageKey("file.upload.updatesuccess");//文件修改成功
		if("old".equals(daImageFile.getUrlPath()) && daImageFile.getUrlPath() != null){
			setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId()+"&daImageFile.urlPath="+daImageFile.getUrlPath());
		}else{
			setUrl("../coreCompany/loadCoreCompanyInfo.xhtml?coreCompany.id="+daImageFile.getDaCompany().getId());
		}
		//setUrl("../imageFile/loadNbajjTwoImage.xhtml?company.id="+daImageFile.getDaCompany().getId());
		return MESSAGE_TO_REDIRECT;
	}
	
	/**
	 * 下载文件
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception {
		if (StringUtils.isEmpty(fileName)) {
			setMessageKey("file.download.noFile");
			return MESSAGE_TO_BACK;
		}
		String path = ServletActionContext.getServletContext().getRealPath(fileName);
		if (path == null || path.length() == 0){
			setMessageKey("file.download.noFile");
			return MESSAGE_TO_BACK;
		}
		File file = new File(path);
		if (!file.exists()){
			file = new File(path);
			if (!file.exists()) {
				setMessageKey("file.download.noFile");
				return MESSAGE_TO_BACK;
			}
		}
		return SUCCESS;
	}
	
	public String getDownloadFileName() {
		String downFileName = fileName.indexOf("/") > -1 ? fileName.split("/")[fileName
				.split("/").length - 1]
				: fileName;// 设置下载文件的文件名为下载文件原文件名
		if (factName != null && !"".equals(factName)) {
			downFileName = factName;// 设置下载文件的文件名
		}
		try {
			downFileName = new String(downFileName.getBytes("gbk"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downFileName;
	}
	
	public InputStream getInputStream() throws Exception {
		return ServletActionContext.getServletContext().getResourceAsStream(
				fileName);
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFactName(String factName) {
		this.factName = factName;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean getCanEdit() {
		return canEdit;
	}

	public DaImageFile getDaImageFile() {
		return daImageFile;
	}

	public void setDaImageFile(DaImageFile daImageFile) {
		this.daImageFile = daImageFile;
	}

	public List<DaImageFile> getDaImageFiles() {
		return daImageFiles;
	}

	public void setDaImageFiles(List<DaImageFile> daImageFiles) {
		this.daImageFiles = daImageFiles;
	}

	public String getFactName() {
		return factName;
	}

	public void setImageFileFacadeIface(ImageFileFacadeIface imageFileFacadeIface) {
		this.imageFileFacadeIface = imageFileFacadeIface;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 设置上传的绝对目录
	 * 
	 */
	public void setUploadRealPath() {
		if (FileUpload.getREAL_PATH() == null
				|| "".equals(FileUpload.getREAL_PATH())) {
			FileUpload.setREAL_PATH(((ServletContext) ActionContext
					.getContext().get(ServletActionContext.SERVLET_CONTEXT))
					.getRealPath(""));// 设置项目绝对路径为文件保存路径
		}
	}

	public List<DaImageFile> getImageFileList() {
		return imageFileList;
	}

	public void setImageFileList(List<DaImageFile> imageFileList) {
		this.imageFileList = imageFileList;
	}

	public DaCompany getCompany() {
		return company;
	}

	public void setCompany(DaCompany company) {
		this.company = company;
	}

	public DaImageFile getImageOne() {
		return imageOne;
	}

	public void setImageOne(DaImageFile imageOne) {
		this.imageOne = imageOne;
	}

	public DaImageFile getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(DaImageFile imageTwo) {
		this.imageTwo = imageTwo;
	}
	
	
}
