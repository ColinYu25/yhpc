package com.safetys.nbyhpc.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 中心库日常检查(隐患)中间表类
 */
public class ETLRcjc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ID; // 编号
	private Date CHECK_DATE;// 日期
	private String DEPARTMENT;// 实施部门
	private String CHECK_TYPE;// 检查类别
	private String CHECK_PERSON;// 检查人
	private String CHECK_MAJOR_ACCIDENT;// 检查情况(主要隐患)-隐患描述
	private String CHECK_CASE;// 检查情况(整改意见)-隐患处理措施
	private String REPORT_ORG;// 抄告单位
	private Date RE_CHECK_TIME;// 复查情况(时间)
	private String RE_CHECK_OPINION;// 复查意见
	private Long COMPANY_ID;// ID
	private String COMPANY_NAME;// 企业名称
	private Long IS_DELETED;// 是否删除
	private Date CREATE_TIME;// 创建时间
	private Date MODIFY_TIME;// 修改时间
	private String ASSEMBLEID;// XZJD_,XZZF_BZH_(检查ＩＤ＋隐患ＩＤ)
	private String REPORT_ORG_ID;// 抄告单ID
	private String BOOK_ID;// 书面通知ID
	private Long RECORD_STATE;// 记录状态
	private Date REC_FINISH_TIME;//整改完成日期
	private String HIDDEN_TYPE;// 隐患分类
	private String HIDDEN_SOURCE; // 隐患来源
	private String HIDDEN_LEVEL;// 隐患级别
	private Date HIDDEN_INPUT_TIME;// 隐患录入时间
	private String REC_STATUS;// 整改情况
	private String RE_CHECK_CASE;// 复查情况
	private String isSYN;// 是否与本地交互 1-交互 0-否
	private Long SYN_NO;// 同步号
	private String isUpdate;// 是否更新 0-否 1-是
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Date getCHECK_DATE() {
		return CHECK_DATE;
	}
	public void setCHECK_DATE(Date cHECK_DATE) {
		CHECK_DATE = cHECK_DATE;
	}
	public String getDEPARTMENT() {
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}
	public String getCHECK_TYPE() {
		return CHECK_TYPE;
	}
	public void setCHECK_TYPE(String cHECK_TYPE) {
		CHECK_TYPE = cHECK_TYPE;
	}
	public String getCHECK_PERSON() {
		return CHECK_PERSON;
	}
	public void setCHECK_PERSON(String cHECK_PERSON) {
		CHECK_PERSON = cHECK_PERSON;
	}
	public String getCHECK_MAJOR_ACCIDENT() {
		return CHECK_MAJOR_ACCIDENT;
	}
	public void setCHECK_MAJOR_ACCIDENT(String cHECK_MAJOR_ACCIDENT) {
		CHECK_MAJOR_ACCIDENT = cHECK_MAJOR_ACCIDENT;
	}
	public String getCHECK_CASE() {
		return CHECK_CASE;
	}
	public void setCHECK_CASE(String cHECK_CASE) {
		CHECK_CASE = cHECK_CASE;
	}
	public String getREPORT_ORG() {
		return REPORT_ORG;
	}
	public void setREPORT_ORG(String rEPORT_ORG) {
		REPORT_ORG = rEPORT_ORG;
	}
	public Date getRE_CHECK_TIME() {
		return RE_CHECK_TIME;
	}
	public void setRE_CHECK_TIME(Date rE_CHECK_TIME) {
		RE_CHECK_TIME = rE_CHECK_TIME;
	}
	public String getRE_CHECK_OPINION() {
		return RE_CHECK_OPINION;
	}
	public void setRE_CHECK_OPINION(String rE_CHECK_OPINION) {
		RE_CHECK_OPINION = rE_CHECK_OPINION;
	}
	public Long getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Long cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public Long getIS_DELETED() {
		return IS_DELETED;
	}
	public void setIS_DELETED(Long iS_DELETED) {
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
	public String getASSEMBLEID() {
		return ASSEMBLEID;
	}
	public void setASSEMBLEID(String aSSEMBLEID) {
		ASSEMBLEID = aSSEMBLEID;
	}
	public String getREPORT_ORG_ID() {
		return REPORT_ORG_ID;
	}
	public void setREPORT_ORG_ID(String rEPORT_ORG_ID) {
		REPORT_ORG_ID = rEPORT_ORG_ID;
	}
	public String getBOOK_ID() {
		return BOOK_ID;
	}
	public void setBOOK_ID(String bOOK_ID) {
		BOOK_ID = bOOK_ID;
	}
	public Long getRECORD_STATE() {
		return RECORD_STATE;
	}
	public void setRECORD_STATE(Long rECORD_STATE) {
		RECORD_STATE = rECORD_STATE;
	}
	public Date getREC_FINISH_TIME() {
		return REC_FINISH_TIME;
	}
	public void setREC_FINISH_TIME(Date rEC_FINISH_TIME) {
		REC_FINISH_TIME = rEC_FINISH_TIME;
	}
	public String getHIDDEN_TYPE() {
		return HIDDEN_TYPE;
	}
	public void setHIDDEN_TYPE(String hIDDEN_TYPE) {
		HIDDEN_TYPE = hIDDEN_TYPE;
	}
	public String getHIDDEN_SOURCE() {
		return HIDDEN_SOURCE;
	}
	public void setHIDDEN_SOURCE(String hIDDEN_SOURCE) {
		HIDDEN_SOURCE = hIDDEN_SOURCE;
	}
	public String getHIDDEN_LEVEL() {
		return HIDDEN_LEVEL;
	}
	public void setHIDDEN_LEVEL(String hIDDEN_LEVEL) {
		HIDDEN_LEVEL = hIDDEN_LEVEL;
	}
	public Date getHIDDEN_INPUT_TIME() {
		return HIDDEN_INPUT_TIME;
	}
	public void setHIDDEN_INPUT_TIME(Date hIDDEN_INPUT_TIME) {
		HIDDEN_INPUT_TIME = hIDDEN_INPUT_TIME;
	}
	public String getREC_STATUS() {
		return REC_STATUS;
	}
	public void setREC_STATUS(String rEC_STATUS) {
		REC_STATUS = rEC_STATUS;
	}
	public String getRE_CHECK_CASE() {
		return RE_CHECK_CASE;
	}
	public void setRE_CHECK_CASE(String rE_CHECK_CASE) {
		RE_CHECK_CASE = rE_CHECK_CASE;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}
	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}
	
	
	
}
