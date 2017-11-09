package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author yinghui.zhang
 * @E-mail uuhui@163.com
 * @date 创建时间: 2013年9月30日
 * @Description: 企业信息数据交互中间表类
 * @Modify 修改人：
 * @date 修改时间：
 * @modifyContent
 */
public class ETLCompany implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ID; // 编号
	private String UUID;// UUID
	private String BUSINESS_REG_NUM;// 企业工商注册号
	private String COMPANY_NAME;// 企业名称
	private String REG_ADDRESS;// 注册地址
	private Long FIRST_AREA;// 经营场所一级区域
	private Long SECOND_AREA;// 经营场所二级区域
	private Long THIRD_AREA;// 经营场所三级区域
	private Long FOUTH_AREA;// 经营场所四级区域
	private Long FIFTH_AREA;// 经营场所五级区域
	private String BUSINESS_ADDRESS;// 经营场所详细地址
	private String ZIP_CODE; // 邮编
	private String CHEMICAL_ADDRESS;// 所在化工区（集中区）放代码

	private String NATIONAL_ECONOMIC_TYPE1;// 国民经济分类一级
	private String NATIONAL_ECONOMIC_TYPE2;// 国民经济分类二级
	private String MANAGEMENT_LEVEL1;// 管理分类一级
	private String MANAGEMENT_LEVEL2;// 管理分类二级
	private String ECONOMIC_TYPE1;// 经济类型一
	private String ECONOMIC_TYPE2;// 经济类型二
	private Date ESTABLISHMENT_DAY;// 成立时间
	private String ORG_CODE;// 组织机构代码
	private String PRODUCTION_SCALE;// 生产规模

	private String LEGAL_PERSON;// 法定代表人
	private String PHONE;// 电话号码
	private Integer IS_ENTERPRISE;// 是否规模以上工业企业
	private String BUSINESS_SCOPE;// 经营范围
	
	private String IS_DELETED;// 是否删除
	private Date CREATE_TIME;// 创建时间
	private Date MODIFY_TIME;// 修改时间
	private String isSYN;// 是否与本地交互 1-交互 0-否
	private Long SYN_NO;// 同步号
	private String isUpdate;// 是否更新 0-否 1-是
	private String IS_HIDDEN;// 是否来源于隐藏系统 0－否 1－是

	// 开业和吊销状态
	// private String STATUS;

	// 开业和吊销状态
	private String STATE;
	// 企业注销时间
	private Date cancelTime;
	
	
	private String LEGAL_TELEPHONE;// 法人手机号
	
	
	private String NATINAL_REGULATORY_CODE1;//国家监管分类代码1
	private String NATINAL_REGULATORY_CODE2;//国家监管分类代码2
	
	private String PRINCIPAL_PERSON;// 主要负责人姓名
	private String PRINCIPAL_MOBILE;//主要负责人手机号码
	private String PRINCIPAL_TELEPHONE;//主要负责人联系电话
	
	private String ZJ_ID;//质监来源ID
	private String GS_ID;//工商来源ID
	
	private String DEPT_CODE;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getBUSINESS_REG_NUM() {
		return BUSINESS_REG_NUM;
	}

	public void setBUSINESS_REG_NUM(String bUSINESS_REG_NUM) {
		BUSINESS_REG_NUM = bUSINESS_REG_NUM;
	}

	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}

	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}

	public String getNATIONAL_ECONOMIC_TYPE1() {
		return NATIONAL_ECONOMIC_TYPE1;
	}

	public void setNATIONAL_ECONOMIC_TYPE1(String nATIONAL_ECONOMIC_TYPE1) {
		NATIONAL_ECONOMIC_TYPE1 = nATIONAL_ECONOMIC_TYPE1;
	}

	public String getNATIONAL_ECONOMIC_TYPE2() {
		return NATIONAL_ECONOMIC_TYPE2;
	}

	public void setNATIONAL_ECONOMIC_TYPE2(String nATIONAL_ECONOMIC_TYPE2) {
		NATIONAL_ECONOMIC_TYPE2 = nATIONAL_ECONOMIC_TYPE2;
	}

	public String getMANAGEMENT_LEVEL1() {
		return MANAGEMENT_LEVEL1;
	}

	public void setMANAGEMENT_LEVEL1(String mANAGEMENT_LEVEL1) {
		MANAGEMENT_LEVEL1 = mANAGEMENT_LEVEL1;
	}

	public String getMANAGEMENT_LEVEL2() {
		return MANAGEMENT_LEVEL2;
	}

	public void setMANAGEMENT_LEVEL2(String mANAGEMENT_LEVEL2) {
		MANAGEMENT_LEVEL2 = mANAGEMENT_LEVEL2;
	}

	public String getECONOMIC_TYPE1() {
		return ECONOMIC_TYPE1;
	}

	public void setECONOMIC_TYPE1(String eCONOMIC_TYPE1) {
		ECONOMIC_TYPE1 = eCONOMIC_TYPE1;
	}

	public String getECONOMIC_TYPE2() {
		return ECONOMIC_TYPE2;
	}

	public void setECONOMIC_TYPE2(String eCONOMIC_TYPE2) {
		ECONOMIC_TYPE2 = eCONOMIC_TYPE2;
	}

	public Long getFIRST_AREA() {
		return FIRST_AREA;
	}

	public void setFIRST_AREA(Long fIRST_AREA) {
		FIRST_AREA = fIRST_AREA;
	}

	public Long getSECOND_AREA() {
		return SECOND_AREA;
	}

	public void setSECOND_AREA(Long sECOND_AREA) {
		SECOND_AREA = sECOND_AREA;
	}

	public Long getTHIRD_AREA() {
		return THIRD_AREA;
	}

	public void setTHIRD_AREA(Long tHIRD_AREA) {
		THIRD_AREA = tHIRD_AREA;
	}

	public Long getFOUTH_AREA() {
		return FOUTH_AREA;
	}

	public void setFOUTH_AREA(Long fOUTH_AREA) {
		FOUTH_AREA = fOUTH_AREA;
	}

	public Long getFIFTH_AREA() {
		return FIFTH_AREA;
	}

	public void setFIFTH_AREA(Long fIFTH_AREA) {
		FIFTH_AREA = fIFTH_AREA;
	}

	public String getREG_ADDRESS() {
		return REG_ADDRESS;
	}

	public void setREG_ADDRESS(String rEG_ADDRESS) {
		REG_ADDRESS = rEG_ADDRESS;
	}

	public String getBUSINESS_ADDRESS() {
		return BUSINESS_ADDRESS;
	}

	public void setBUSINESS_ADDRESS(String bUSINESS_ADDRESS) {
		BUSINESS_ADDRESS = bUSINESS_ADDRESS;
	}

	public String getZIP_CODE() {
		return ZIP_CODE;
	}

	public void setZIP_CODE(String zIP_CODE) {
		ZIP_CODE = zIP_CODE;
	}

	public String getCHEMICAL_ADDRESS() {
		return CHEMICAL_ADDRESS;
	}

	public void setCHEMICAL_ADDRESS(String cHEMICAL_ADDRESS) {
		CHEMICAL_ADDRESS = cHEMICAL_ADDRESS;
	}

	public Date getESTABLISHMENT_DAY() {
		return ESTABLISHMENT_DAY;
	}

	public void setESTABLISHMENT_DAY(Date eSTABLISHMENT_DAY) {
		ESTABLISHMENT_DAY = eSTABLISHMENT_DAY;
	}

	public String getORG_CODE() {
		return ORG_CODE;
	}

	public void setORG_CODE(String oRG_CODE) {
		ORG_CODE = oRG_CODE;
	}

	public String getPRODUCTION_SCALE() {
		return PRODUCTION_SCALE;
	}

	public void setPRODUCTION_SCALE(String pRODUCTION_SCALE) {
		PRODUCTION_SCALE = pRODUCTION_SCALE;
	}

	public String getLEGAL_PERSON() {
		return LEGAL_PERSON;
	}

	public void setLEGAL_PERSON(String lEGAL_PERSON) {
		LEGAL_PERSON = lEGAL_PERSON;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public Integer getIS_ENTERPRISE() {
		return IS_ENTERPRISE;
	}

	public void setIS_ENTERPRISE(Integer iS_ENTERPRISE) {
		IS_ENTERPRISE = iS_ENTERPRISE;
	}

	public String getIS_DELETED() {
		return IS_DELETED;
	}

	public void setIS_DELETED(String iS_DELETED) {
		IS_DELETED = iS_DELETED;
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public Date getMODIFY_TIME() {
		return MODIFY_TIME;
	}

	public void setMODIFY_TIME(Date mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
	}

	public String getIsSYN() {
		return isSYN;
	}

	public void setIsSYN(String isSYN) {
		this.isSYN = isSYN;
	}

	public Long getSYN_NO() {
		return SYN_NO;
	}

	public void setSYN_NO(Long sYN_NO) {
		SYN_NO = sYN_NO;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getIS_HIDDEN() {
		return IS_HIDDEN;
	}

	public void setIS_HIDDEN(String iS_HIDDEN) {
		IS_HIDDEN = iS_HIDDEN;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getBUSINESS_SCOPE() {
		return BUSINESS_SCOPE;
	}

	public void setBUSINESS_SCOPE(String bUSINESS_SCOPE) {
		BUSINESS_SCOPE = bUSINESS_SCOPE;
	}

	/**
	 * @return the nATINAL_REGULATORY_CODE1
	 */
	public String getNATINAL_REGULATORY_CODE1() {
		return NATINAL_REGULATORY_CODE1;
	}

	/**
	 * @param nATINALREGULATORYCODE1 the nATINAL_REGULATORY_CODE1 to set
	 */
	public void setNATINAL_REGULATORY_CODE1(String nATINALREGULATORYCODE1) {
		NATINAL_REGULATORY_CODE1 = nATINALREGULATORYCODE1;
	}

	/**
	 * @return the nATINAL_REGULATORY_CODE2
	 */
	public String getNATINAL_REGULATORY_CODE2() {
		return NATINAL_REGULATORY_CODE2;
	}

	/**
	 * @param nATINALREGULATORYCODE2 the nATINAL_REGULATORY_CODE2 to set
	 */
	public void setNATINAL_REGULATORY_CODE2(String nATINALREGULATORYCODE2) {
		NATINAL_REGULATORY_CODE2 = nATINALREGULATORYCODE2;
	}

	/**
	 * @return the pRINCIPAL_PERSON
	 */
	public String getPRINCIPAL_PERSON() {
		return PRINCIPAL_PERSON;
	}

	/**
	 * @param pRINCIPALPERSON the pRINCIPAL_PERSON to set
	 */
	public void setPRINCIPAL_PERSON(String pRINCIPALPERSON) {
		PRINCIPAL_PERSON = pRINCIPALPERSON;
	}

	/**
	 * @return the pRINCIPAL_MOBILE
	 */
	public String getPRINCIPAL_MOBILE() {
		return PRINCIPAL_MOBILE;
	}

	/**
	 * @param pRINCIPALMOBILE the pRINCIPAL_MOBILE to set
	 */
	public void setPRINCIPAL_MOBILE(String pRINCIPALMOBILE) {
		PRINCIPAL_MOBILE = pRINCIPALMOBILE;
	}

	/**
	 * @return the pRINCIPAL_TELEPHONE
	 */
	public String getPRINCIPAL_TELEPHONE() {
		return PRINCIPAL_TELEPHONE;
	}

	/**
	 * @param pRINCIPALTELEPHONE the pRINCIPAL_TELEPHONE to set
	 */
	public void setPRINCIPAL_TELEPHONE(String pRINCIPALTELEPHONE) {
		PRINCIPAL_TELEPHONE = pRINCIPALTELEPHONE;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the lEGAL_TELEPHONE
	 */
	public String getLEGAL_TELEPHONE() {
		return LEGAL_TELEPHONE;
	}

	/**
	 * @param lEGALTELEPHONE the lEGAL_TELEPHONE to set
	 */
	public void setLEGAL_TELEPHONE(String lEGALTELEPHONE) {
		LEGAL_TELEPHONE = lEGALTELEPHONE;
	}

	/**
	 * @return the zJ_ID
	 */
	public String getZJ_ID() {
		return ZJ_ID;
	}

	/**
	 * @param zJID the zJ_ID to set
	 */
	public void setZJ_ID(String zJID) {
		ZJ_ID = zJID;
	}

	/**
	 * @return the gS_ID
	 */
	public String getGS_ID() {
		return GS_ID;
	}

	/**
	 * @param gSID the gS_ID to set
	 */
	public void setGS_ID(String gSID) {
		GS_ID = gSID;
	}

	/**
	 * @return the dEPT_CODE
	 */
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}

	/**
	 * @param dEPTCODE the dEPT_CODE to set
	 */
	public void setDEPT_CODE(String dEPTCODE) {
		DEPT_CODE = dEPTCODE;
	}


}
