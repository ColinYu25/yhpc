package cn.safetys.constant;

import org.springframework.util.Assert;


public class SystemConstant {
	
	public static SystemConstant P;
	
	public void init(){
		P = this;
	}

	private boolean enterprise = true;
	
	private boolean government = true;
	
	private boolean dataSeparation = false;
	
	private String enterpriseSyncName;
	
	private String governmentSyncName;
	
	/**
	 * 附件服务器地址
	 */
	private String attachServer;
	/**
	 * 附件上传是否开启
	 */
	private boolean attachUpload;
	
	
	public void checkEnterprise(){
		Assert.isTrue(enterprise, "非企业端，无法进行此操作！");
	}
	public void checkGovernment(){
		Assert.isTrue(government, "非政府端，无法进行此操作！");
	}
	public String getSyncName(){
		if(government&&!enterprise){
			return governmentSyncName;
		}else if(enterprise&&!government){
			return enterpriseSyncName;
		}
		return null;
	}
	
	

	public boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}

	public boolean isGovernment() {
		return government;
	}

	public void setGovernment(boolean government) {
		this.government = government;
	}

	public boolean isDataSeparation() {
		return dataSeparation;
	}

	public void setDataSeparation(boolean dataSeparation) {
		this.dataSeparation = dataSeparation;
	}
	public String getEnterpriseSyncName() {
		return enterpriseSyncName;
	}
	public void setEnterpriseSyncName(String enterpriseSyncName) {
		this.enterpriseSyncName = enterpriseSyncName;
	}
	public String getGovernmentSyncName() {
		return governmentSyncName;
	}
	public void setGovernmentSyncName(String governmentSyncName) {
		this.governmentSyncName = governmentSyncName;
	}
	public String getAttachServer() {
		return attachServer;
	}
	public void setAttachServer(String attachServer) {
		this.attachServer = attachServer;
	}
	public boolean isAttachUpload() {
		return attachUpload;
	}
	public void setAttachUpload(boolean attachUpload) {
		this.attachUpload = attachUpload;
	}
	
	
	
}
