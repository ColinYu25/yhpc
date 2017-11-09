package cn.safetys.mq.service;

import cn.safetys.annotation.Field_Method_Parameter_Annotation;
import cn.safetys.constant.SystemConstant;

import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLGpDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLNormalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLReportPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLWhPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;

/**
 * @author llj
 * @create_time: 2014-4-25
 * @Description:将本地数据交换到中心平台
 */
public class SwapHanlder {
	private static String TOKEN = "#NBYH#2013#";

	/** 企业基本信息业务逻辑接口 **/
	private ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface;

	/** 一般隐患信息业务逻辑接口 **/
	private ETLNormalDangerPersistenceIface etlNormalDangerPersistenceIface;

	/** 重大隐患信息业务逻辑接口 **/
	private ETLDangerPersistenceIface etlDangerPersistenceIface;

	/** 挂牌隐患信息业务逻辑接口 **/
	private ETLGpDangerPersistenceIface etlGpDangerPersistenceIface;

	/** 季报信息业务逻辑接口 **/
	private ETLReportPersistenceIface etlReportPersistenceIface;

	/** 危化信息业务逻辑接口 **/
	private ETLWhPersistenceIface etlWhPersistenceIface;

	private ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface;

	private CompanyFacadeIface companyFacadeIface;// 企业基本信息的业务接口

	private CompanyPersistenceIface companyPersistenceIface;
	
	private SystemConstant systemConstant;

	@Field_Method_Parameter_Annotation(describ = "同步功能")
	public void execute() throws Exception {
		if(systemConstant.isDataSeparation()&&systemConstant.isGovernment()){
			return ;
		}
		// 初始化企业uuid字段（一次性初始化完成后，以后可以不用再次执行）
		//IncSynService.loadUuidDaCompany(etlDaCompanyPersistenceIface);
		// 初始化企业信息
		//IncSynService.loadSynDaCompany(etlDaCompanyPersistenceIface);
		// 初始化uuid未匹配的数据的企业信息
        //IncSynService.initErrorCompanyInfo(companyFacadeIface);
		// 企业基本信息同步到中心平台
		//SwapService.swapCompanyService(etlCompanyInfoPersistenceIface,companyPersistenceIface, TOKEN);
		// 一般隐患信息同步到中心平台
		//SwapService.swapNormalDangerService(etlNormalDangerPersistenceIface,TOKEN);
		// 重大隐患信息同步到中心平台
		//SwapService.swapDangerService(etlDangerPersistenceIface, TOKEN);
		// 挂牌隐患信息同步到中心平台
		//SwapService.swapGpDangerService(etlGpDangerPersistenceIface, TOKEN);
		// 隐患季报单向同步到中心平台
		//SwapService.swapReportService(etlReportPersistenceIface, TOKEN);
		// 隐患危化单向同步到中心平台
		//SwapService.swapWhService(etlWhPersistenceIface, TOKEN);
	}

	public ETLCompanyInfoPersistenceIface getEtlCompanyInfoPersistenceIface() {
		return etlCompanyInfoPersistenceIface;
	}

	public void setEtlCompanyInfoPersistenceIface(
			ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface) {
		this.etlCompanyInfoPersistenceIface = etlCompanyInfoPersistenceIface;
	}

	public ETLNormalDangerPersistenceIface getEtlNormalDangerPersistenceIface() {
		return etlNormalDangerPersistenceIface;
	}

	public void setEtlNormalDangerPersistenceIface(
			ETLNormalDangerPersistenceIface etlNormalDangerPersistenceIface) {
		this.etlNormalDangerPersistenceIface = etlNormalDangerPersistenceIface;
	}

	public ETLDangerPersistenceIface getEtlDangerPersistenceIface() {
		return etlDangerPersistenceIface;
	}

	public void setEtlDangerPersistenceIface(
			ETLDangerPersistenceIface etlDangerPersistenceIface) {
		this.etlDangerPersistenceIface = etlDangerPersistenceIface;
	}

	public ETLGpDangerPersistenceIface getEtlGpDangerPersistenceIface() {
		return etlGpDangerPersistenceIface;
	}

	public void setEtlGpDangerPersistenceIface(
			ETLGpDangerPersistenceIface etlGpDangerPersistenceIface) {
		this.etlGpDangerPersistenceIface = etlGpDangerPersistenceIface;
	}

	public ETLReportPersistenceIface getEtlReportPersistenceIface() {
		return etlReportPersistenceIface;
	}

	public void setEtlReportPersistenceIface(
			ETLReportPersistenceIface etlReportPersistenceIface) {
		this.etlReportPersistenceIface = etlReportPersistenceIface;
	}

	public ETLWhPersistenceIface getEtlWhPersistenceIface() {
		return etlWhPersistenceIface;
	}

	public void setEtlWhPersistenceIface(
			ETLWhPersistenceIface etlWhPersistenceIface) {
		this.etlWhPersistenceIface = etlWhPersistenceIface;
	}

	public ETLDaCompanyPersistenceIface getEtlDaCompanyPersistenceIface() {
		return etlDaCompanyPersistenceIface;
	}

	public void setEtlDaCompanyPersistenceIface(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface) {
		this.etlDaCompanyPersistenceIface = etlDaCompanyPersistenceIface;
	}

	/**
	 * @return the companyFacadeIface
	 */
	public CompanyFacadeIface getCompanyFacadeIface() {
		return companyFacadeIface;
	}

	/**
	 * @param companyFacadeIface
	 *            the companyFacadeIface to set
	 */
	public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
		this.companyFacadeIface = companyFacadeIface;
	}

	/**
	 * @return the companyPersistenceIface
	 */
	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	/**
	 * @param companyPersistenceIface
	 *            the companyPersistenceIface to set
	 */
	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}

	
}
