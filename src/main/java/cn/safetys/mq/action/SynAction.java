package cn.safetys.mq.action;

import cn.safetys.mq.service.IncSynService;
import cn.safetys.mq.service.SwapService;

import com.googlecode.jsonplugin.annotations.JSON;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLGpDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLNormalDangerPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLReportPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLWhPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
import com.safetys.nbyhpc.facade.iface.StaticCacheFacadeIface;

public class SynAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private StaticCacheFacadeIface staticCacheFacadeIface;
	// 操作提示
	private String msg = "ok";
	private boolean success = true;

	// 手动调用da_company同步t_company 方法
	public String initErrorCompanyInfo() {
		try {
			IncSynService.initErrorCompanyInfo(companyFacadeIface);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用t_company同步da_company方法
	public String loadSynDaCompany() throws Exception {

		try {
			IncSynService.loadSynDaCompany(etlDaCompanyPersistenceIface);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用企业基本信息同步到中心平台方法
	public String swapCompanyService() throws Exception {

		try {
			SwapService.swapCompanyService(etlCompanyInfoPersistenceIface,companyPersistenceIface,
					TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用一般隐患信息同步到中心平台方法
	public String swapNormalDangerService() throws Exception {

		try {
			SwapService.swapNormalDangerService(
					etlNormalDangerPersistenceIface, TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用重大隐患信息同步到中心平台方法
	public String swapDangerService() throws Exception {

		try {
			SwapService.swapDangerService(etlDangerPersistenceIface, TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用挂牌隐患信息同步到中心平台方法
	public String swapGpDangerService() throws Exception {

		try {
			SwapService.swapGpDangerService(etlGpDangerPersistenceIface, TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用隐患季报单向同步到中心平台方法
	public String swapReportService() throws Exception {

		try {
			SwapService.swapReportService(etlReportPersistenceIface, TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}

	// 手动调用隐患危化单向同步到中心平台方法
	public String swapWhService() throws Exception {

		try {
			SwapService.swapWhService(etlWhPersistenceIface, TOKEN);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return "success";
	}
	
	// 手动调用隐患危化单向同步到中心平台方法
	public String sendData() throws Exception {
		try {
			//非煤矿山
			System.out.println("省局报表定时发送之前先删除数据库结果数据和缓存数据");
			System.out.println("省局报表定时发送");
			staticCacheFacadeIface.reCreateCache(1);
			staticCacheFacadeIface.sendData(1);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	// 手动调用隐患危化单向同步到中心平台方法
	public String sendData2() throws Exception {
		try {
			System.out.println("省局报表定时发送之前先删除数据库结果数据和缓存数据");
			System.out.println("省局报表定时发送");
			staticCacheFacadeIface.reCreateCache(2);
			staticCacheFacadeIface.sendData(2);
		} catch (ApplicationAccessException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * @return the etlCompanyInfoPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLCompanyInfoPersistenceIface getEtlCompanyInfoPersistenceIface() {
		return etlCompanyInfoPersistenceIface;
	}

	/**
	 * @param etlCompanyInfoPersistenceIface
	 *            the etlCompanyInfoPersistenceIface to set
	 */
	public void setEtlCompanyInfoPersistenceIface(
			ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface) {
		this.etlCompanyInfoPersistenceIface = etlCompanyInfoPersistenceIface;
	}

	/**
	 * @return the etlNormalDangerPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLNormalDangerPersistenceIface getEtlNormalDangerPersistenceIface() {
		return etlNormalDangerPersistenceIface;
	}

	/**
	 * @param etlNormalDangerPersistenceIface
	 *            the etlNormalDangerPersistenceIface to set
	 */
	public void setEtlNormalDangerPersistenceIface(
			ETLNormalDangerPersistenceIface etlNormalDangerPersistenceIface) {
		this.etlNormalDangerPersistenceIface = etlNormalDangerPersistenceIface;
	}

	/**
	 * @return the etlDangerPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLDangerPersistenceIface getEtlDangerPersistenceIface() {
		return etlDangerPersistenceIface;
	}

	/**
	 * @param etlDangerPersistenceIface
	 *            the etlDangerPersistenceIface to set
	 */
	public void setEtlDangerPersistenceIface(
			ETLDangerPersistenceIface etlDangerPersistenceIface) {
		this.etlDangerPersistenceIface = etlDangerPersistenceIface;
	}

	/**
	 * @return the etlGpDangerPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLGpDangerPersistenceIface getEtlGpDangerPersistenceIface() {
		return etlGpDangerPersistenceIface;
	}

	/**
	 * @param etlGpDangerPersistenceIface
	 *            the etlGpDangerPersistenceIface to set
	 */
	public void setEtlGpDangerPersistenceIface(
			ETLGpDangerPersistenceIface etlGpDangerPersistenceIface) {
		this.etlGpDangerPersistenceIface = etlGpDangerPersistenceIface;
	}

	/**
	 * @return the etlReportPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLReportPersistenceIface getEtlReportPersistenceIface() {
		return etlReportPersistenceIface;
	}

	/**
	 * @param etlReportPersistenceIface
	 *            the etlReportPersistenceIface to set
	 */
	public void setEtlReportPersistenceIface(
			ETLReportPersistenceIface etlReportPersistenceIface) {
		this.etlReportPersistenceIface = etlReportPersistenceIface;
	}

	/**
	 * @return the etlWhPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLWhPersistenceIface getEtlWhPersistenceIface() {
		return etlWhPersistenceIface;
	}

	/**
	 * @param etlWhPersistenceIface
	 *            the etlWhPersistenceIface to set
	 */
	public void setEtlWhPersistenceIface(
			ETLWhPersistenceIface etlWhPersistenceIface) {
		this.etlWhPersistenceIface = etlWhPersistenceIface;
	}

	/**
	 * @return the etlDaCompanyPersistenceIface
	 */
	@JSON(serialize=false)
	public ETLDaCompanyPersistenceIface getEtlDaCompanyPersistenceIface() {
		return etlDaCompanyPersistenceIface;
	}

	/**
	 * @param etlDaCompanyPersistenceIface
	 *            the etlDaCompanyPersistenceIface to set
	 */
	public void setEtlDaCompanyPersistenceIface(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface) {
		this.etlDaCompanyPersistenceIface = etlDaCompanyPersistenceIface;
	}

	/**
	 * @return the companyFacadeIface
	 */
	@JSON(serialize=false)
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
	 * @return the msg
	 */
	@JSON(serialize=false)
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the companyPersistenceIface
	 */
	public CompanyPersistenceIface getCompanyPersistenceIface() {
		return companyPersistenceIface;
	}

	/**
	 * @param companyPersistenceIface the companyPersistenceIface to set
	 */
	public void setCompanyPersistenceIface(
			CompanyPersistenceIface companyPersistenceIface) {
		this.companyPersistenceIface = companyPersistenceIface;
	}

	/**
	 * @return the staticCacheFacadeIface
	 */
	public StaticCacheFacadeIface getStaticCacheFacadeIface() {
		return staticCacheFacadeIface;
	}

	/**
	 * @param staticCacheFacadeIface the staticCacheFacadeIface to set
	 */
	public void setStaticCacheFacadeIface(
			StaticCacheFacadeIface staticCacheFacadeIface) {
		this.staticCacheFacadeIface = staticCacheFacadeIface;
	}
}
