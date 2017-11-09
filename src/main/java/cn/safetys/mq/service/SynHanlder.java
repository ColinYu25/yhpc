package cn.safetys.mq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.safetys.center.db.mq.IHandler;
import cn.safetys.center.db.mq.cmd.CmdHelp;

import com.safetys.nbyhpc.domain.model.ETLSynInfo;
import com.safetys.nbyhpc.domain.persistence.iface.ETLAqscInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLRcjcInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLSynInfoPersistenceIface;

public class SynHanlder implements IHandler {
	private static String TOKEN = "#NBYH#2013#";
	private ETLSynInfoPersistenceIface etlSynInfoPersistenceIface;// 同步号信息业务逻辑接口
	private ETLRcjcInfoPersistenceIface etlRcjcInfoPersistenceIface;// 日常检查(隐患)业务逻辑接口
	private ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface;// 日常检查(隐患)业务逻辑接口

	private ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface;

	private ETLAqscInfoPersistenceIface etlAqscInfoPersistenceIface;
	/**
	 * 定义日志信息变量，用于记录日志信息
	 */
	private static Logger log = LoggerFactory.getLogger(SynHanlder.class);

	static {
		log.info("--------------77------");

		// TOKEN = ConfigUtil.getPropertyValue("TOKEN");
		// log.info("TOKEN: " + ConfigUtil.getPropertyValue("TOKEN"));
	}
	
	public void execute(){
		
		receive(CmdHelp._SYS_COMPANY_CMD);
		
		receive(CmdHelp._SYS_AQSC_CMD);
		
	}

	public void receive(String arg) {

		log.info("中心库同步到隐患开始。。。。。。");
		log.info("arg: " + arg);
		ETLSynInfo synInfo = null;// 定义同步对象
		Long synNo = 0l;// 定义同步号

		try {
			if (arg.equalsIgnoreCase(CmdHelp._SYS_COMPANY_CMD)) {// 企业基本信息指令
				// 获取获取企业最后一次的同步号
				synInfo = etlSynInfoPersistenceIface.getSynNo("T_COMPANY");
				log.info("企业信息同步开始……");
				log.info("企业信息同步开始……");
				synNo = synInfo.getSynNo();// 同步前的同步号
				// 获取企业基本信息增量的同步号
				synNo = IncSynService.incCompanyService(
						etlCompanyInfoPersistenceIface, TOKEN, synNo);
				log.info("企业信息同步结束……");
				log.info("企业信息同步结束……");
			} else if (arg.equalsIgnoreCase(CmdHelp._SYS_AQSC_CMD)) {// 安全生产同步指令
				synInfo = etlSynInfoPersistenceIface
						.getSynNo("T_COMPANY_SAFETY_PRODUCTION");
				log.info("安全生产同步开始……");
				synNo = synInfo.getSynNo();// 同步前的同步号
				synNo = IncSynService.incAqscService(
						etlAqscInfoPersistenceIface, TOKEN, synNo);
				log.info("安全生产同步结束……");
			} else if (arg.equalsIgnoreCase(CmdHelp._SYS_RCJC_CMD)) {// 日常检查(隐患)
			// synInfo = etlSynInfoPersistenceIface.getSynNo("T_DAILY_CHECK");
			// log.info("日常检查(隐患)信息同步开始……");
			// synNo = synInfo.getSynNo();// 同步前的同步号
			// synNo = IncSynService.incRcjcService(etlRcjcInfoPersistenceIface,
			// TOKEN, synNo);

			}

			else {
				log.info("指令: " + arg + "　错误,不能处理!");
				log.info("指令: " + arg + "　错误,不能处理!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 更新本地同步号信息
			try {
				if (synInfo != null && synInfo.getSynNo() != synNo.longValue()) {
					synInfo.setSynNo(synNo);
					etlSynInfoPersistenceIface.update(synInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		try {
			// da_company与t_company同步(定时)

			IncSynService.loadSynDaCompany(etlDaCompanyPersistenceIface);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ETLSynInfoPersistenceIface getEtlSynInfoPersistenceIface() {
		return etlSynInfoPersistenceIface;
	}

	public void setEtlSynInfoPersistenceIface(
			ETLSynInfoPersistenceIface etlSynInfoPersistenceIface) {
		this.etlSynInfoPersistenceIface = etlSynInfoPersistenceIface;
	}

	public ETLRcjcInfoPersistenceIface getEtlRcjcInfoPersistenceIface() {
		return etlRcjcInfoPersistenceIface;
	}

	public void setEtlRcjcInfoPersistenceIface(
			ETLRcjcInfoPersistenceIface etlRcjcInfoPersistenceIface) {
		this.etlRcjcInfoPersistenceIface = etlRcjcInfoPersistenceIface;
	}

	public ETLCompanyInfoPersistenceIface getEtlCompanyInfoPersistenceIface() {
		return etlCompanyInfoPersistenceIface;
	}

	public void setEtlCompanyInfoPersistenceIface(
			ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface) {
		this.etlCompanyInfoPersistenceIface = etlCompanyInfoPersistenceIface;
	}

	public ETLDaCompanyPersistenceIface getEtlDaCompanyPersistenceIface() {
		return etlDaCompanyPersistenceIface;
	}

	public void setEtlDaCompanyPersistenceIface(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface) {
		this.etlDaCompanyPersistenceIface = etlDaCompanyPersistenceIface;
	}

	/**
	 * @return the etlAqscInfoPersistenceIface
	 */
	public ETLAqscInfoPersistenceIface getEtlAqscInfoPersistenceIface() {
		return etlAqscInfoPersistenceIface;
	}

	/**
	 * @param etlAqscInfoPersistenceIface
	 *            the etlAqscInfoPersistenceIface to set
	 */
	public void setEtlAqscInfoPersistenceIface(
			ETLAqscInfoPersistenceIface etlAqscInfoPersistenceIface) {
		this.etlAqscInfoPersistenceIface = etlAqscInfoPersistenceIface;
	}

}
