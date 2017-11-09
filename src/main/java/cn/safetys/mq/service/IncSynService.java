package cn.safetys.mq.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.safetys.center.db.cxf.WsLoader;
import cn.safetys.center.db.cxf.aqsc.IAQSC;
import cn.safetys.center.db.cxf.company.ICompany;
import cn.safetys.center.db.cxf.rcjc.IRCJC;
import cn.safetys.util.CommonUtil;

import com.safetys.nbyhpc.domain.model.ETLAqsc;
import com.safetys.nbyhpc.domain.model.ETLCompany;
import com.safetys.nbyhpc.domain.model.ETLRcjc;
import com.safetys.nbyhpc.domain.persistence.iface.ETLAqscInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLDaCompanyPersistenceIface;
import com.safetys.nbyhpc.domain.persistence.iface.ETLRcjcInfoPersistenceIface;
import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description:增量数据同步辅助类
 */
public class IncSynService {
	/**
	 * 定义日志信息变量，用于记录日志信息
	 */
	private static Logger log = LoggerFactory.getLogger(IncSynService.class);

	/**
	 * 将企业增量数据同步到企业基本信息中间表
	 * 
	 * @param serviceBean
	 *            企业基本信息业务逻辑接口
	 * @param token
	 *            令牌
	 * @param synNo
	 *            同步号
	 * @return 返回增量的同步号
	 * @throws Exception
	 *             异常
	 */
	public static Long incCompanyService(
			ETLCompanyInfoPersistenceIface etlCompanyInfoPersistenceIface,
			String token, Long synNo) throws Exception {
		String jsonStr = null;
		ETLCompany entity = null;
		int i = 0;

		do {

			jsonStr = WsLoader.getInterface(ICompany.class)
					.getNextCompanyINCInfo(token, synNo);
			i++;
			log.info("jsonStr: " + jsonStr);
			entity = CommonUtil.JsonToBean(jsonStr, new ETLCompany());
			if (entity != null) { // 将数据存入系统数据表中

				try {

					etlCompanyInfoPersistenceIface.save(entity);

				} catch (Exception e) {
					synNo = entity.getSYN_NO();
					printErrorMsg("编号: [" + entity.getID() + "]企业信息添加入库失败，原因：",
							e);
				}
				synNo = entity.getSYN_NO();
				if (i == 100) {
					WsLoader.getInterface(ICompany.class).modifySynNo(token,
							synNo);
					i = 0;
					entity = null;
				}
			} else {// 本次获取时如果数据为最后一个，以免中心平台再次发送
				WsLoader.getInterface(ICompany.class).modifySynNo(token, synNo);
			}
		} while (entity != null);
		return synNo;
	}

	/**
	 * 将企业增量数据同步到安全生产状况基本信息中间表
	 * 
	 * @param serviceBean
	 *            企业基本信息业务逻辑接口
	 * @param token
	 *            令牌
	 * @param synNo
	 *            同步号
	 * @return 返回增量的同步号
	 * @throws Exception
	 *             异常
	 */
	public static Long incAqscService(
			ETLAqscInfoPersistenceIface etlAqscInfoPersistenceIface,
			String token, Long synNo) throws Exception {
		String jsonStr = null;
		ETLAqsc entity = null;
		int i = 0;

		do {
			jsonStr = WsLoader.getInterface(IAQSC.class).getNextINCInfo(token,
					synNo);
			i++;
			log.info("jsonStr: " + jsonStr);
			entity = CommonUtil.JsonToBean(jsonStr, new ETLAqsc());
			if (entity != null) { // 将数据存入系统数据表中

				try {

					etlAqscInfoPersistenceIface.save(entity);

				} catch (Exception e) {
					synNo = entity.getSYN_NO();
					printErrorMsg("编号: [" + entity.getAQSC_ID()
							+ "]安全生产信息添加入库失败，原因：", e);
				}
				synNo = entity.getSYN_NO();
				if (i == 100) {
					WsLoader.getInterface(IAQSC.class)
							.modifySynNo(token, synNo);
					i = 0;
					entity = null;
				}
			} else {// 本次获取时如果数据为最后一个，以免中心平台再次发送
				WsLoader.getInterface(IAQSC.class).modifySynNo(token, synNo);
			}
		} while (entity != null);
		return synNo;
	}

	/**
	 * 将日常检查(隐患)增量数据同步到日常检查(隐患)中间表
	 * 
	 * @param serviceBean
	 *            日常检查(隐患)业务逻辑接口
	 * @param token
	 *            令牌
	 * @param synNo
	 *            同步号
	 * @return 返回增量的 同步号
	 */
	public static Long incRcjcService(
			ETLRcjcInfoPersistenceIface etlRcjcInfoPersistenceIface,
			String token, Long synNo) throws Exception {
		String jsonStr = null;
		ETLRcjc entity = null;
		int i = 0;

		do {

			jsonStr = WsLoader.getInterface(IRCJC.class).getNextINCInfo(token,
					synNo);
			i++;
			entity = CommonUtil.JsonToBean(jsonStr, new ETLRcjc());

			if (entity != null) { // 将数据存入系统数据表中 ,暂时只取乡镇和标准化的数据

				try {

					etlRcjcInfoPersistenceIface.save(entity);

				} catch (Exception e) {
					synNo = entity.getSYN_NO();
					printErrorMsg("编号: [" + entity.getID() + "]企业一般隐患信息添加入库失败，原因：",
							e);
				}
				synNo = entity.getSYN_NO();
				if (i == 100) {
					WsLoader.getInterface(IRCJC.class)
							.modifySynNo(token, synNo);
					i = 0;
					entity = null;
				}
			} else {// 本次获取时如果数据为最后一个，以免中心平台再次发送
				WsLoader.getInterface(IRCJC.class).modifySynNo(token, synNo);
				entity = null;
			}
		} while (entity != null);
		return synNo;
	}

	public static void printErrorMsg(String error, Exception ex) {
		log.warn(error + ex.getMessage());
		log.info("数据入库出现错误：" + ex.getMessage());
		log.info("该数据将不会入库");
	}

	public static void loadSynDaCompany(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface)
			throws Exception {

		
		// 初始化和同步同时调用。初始化时使用，初始化完成以后，去掉分段执行代码。
//		 long maxId1=1996444L;//最大有效企业id
//		 long pId1=maxId1/50;//分成50份，初始化话
//		 Long startId1=0L;
//		 Long endId1=pId1;
//		 
		 
//		 for(int i=1;i<=50+1;i++){//为了能把所有的有效企业给初始化，此处循环51次
//		 endId1=pId1*i;
//		 startId1=endId1-pId1;
//		 log.info("t_company  ==>更新  da_company 第"+i+"次，startid="+startId1+"  endId="+endId1);
//		 etlDaCompanyPersistenceIface.loadSynDaCompany(startId1,endId1); //
//		 //t_company ==>更新 da_company (定时) 不需新增 (重要)
//		 }

		//log.info("t_company  ==>更新  da_company");
		etlDaCompanyPersistenceIface.loadSynDaCompany(); // t_company ==>更新
															// da_company (定时)
															// 不需新增 (重要)
		
		// 初始化调用。初始化时使用，初始化完成以后，不需要去掉分段执行代码。
//		 long maxId2=1996195L;//最大有效企业id
//		 long pId2=maxId2/50;//分成50份，初始化话
//		 Long startId2=0L;
//		 Long endId2=pId2;
//		 for(int i=1;i<=50+1;i++){//为了能把所有的有效企业给初始化，此处循环51次
//		 endId2=pId2*i;
//		 startId2=endId2-pId2;
//		 log.info("da_company  ==>更新  t_company第"+i+"次，startid="+startId2+"  endId="+endId2);
//		 
//		 etlDaCompanyPersistenceIface.loadSynTCompanyLow(startId2,endId2);
//		 //da_company ==>更新 t_company (初始化使用)
//		 
//		 etlDaCompanyPersistenceIface.loadSynTCompany(startId2,endId2);
//		 //da_company ==>更新 t_company (初始化使用)
		
//		etlDaCompanyPersistenceIface.loadInsertSynDaCompany(0L,1L);
		
//		}
		 /****/
		return;

	}
	
	
	public static void initErrorCompanyInfo(
			CompanyFacadeIface companyFacadeIface)
			throws Exception {
		
			long maxId =2205214L;// 最大有效企业id
			long pId = maxId / 1;// 分成50份，初始化话

			Long startId = 0L;
			Long endId = pId;

//			for (int i = 1; i <= 1 + 1; i++) {// 为了能把所有的有效企业给初始化，此处循环51次
//				endId = pId * i;
//				startId = endId - pId;
//				log.info("第" + i + "次，startid=" + startId
//						+ "  endId=" + endId);
				companyFacadeIface.initErrorCompanyInfo(0L, 2211817L);

//			}
	
		   return;

	}

	public static void loadUuidDaCompany(
			ETLDaCompanyPersistenceIface etlDaCompanyPersistenceIface)
			throws Exception {

		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		log.info(year + "/" + month + "/" + date + " " + hour + ":"
				+ minute + ":" + second);

		if (year == 2014 && month == 11 && date == 25 && hour == 18
				&& minute == 05) {
			
			long maxId = 2020557L;// 最大有效企业id
			long pId = maxId / 2;// 分成30份，初始化话

			Long startId = 0L;
			Long endId = pId;

//			for (int i = 1; i <= 2 + 1; i++) {// 为了能把所有的有效企业给初始化，此处循环31次
//				endId = pId * i;
//				startId = endId - pId;
//				log.info("第" + i + "次，startid=" + startId
//						+ "  endId=" + endId);
				etlDaCompanyPersistenceIface.loadUuidDaCompany(startId, maxId); // 初始化uuid时

//			}

		}

		return;

	}

}
