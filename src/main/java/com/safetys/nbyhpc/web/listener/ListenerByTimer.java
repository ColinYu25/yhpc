package com.safetys.nbyhpc.web.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.util.ConfigUtil;
import com.safetys.nbyhpc.facade.iface.DangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.NomalDangerFacadeIface;
import com.safetys.nbyhpc.facade.iface.StaticCacheFacadeIface;
import com.safetys.nbyhpc.facade.iface.StatisticFacadeIface;
import com.safetys.nbyhpc.util.Nbyhpc;

public class ListenerByTimer implements ServletContextListener {

	/**
	 * 开始执行时间
	 */
	long START_TIME = 0L;

	/**
	 * 执行间隔时间
	 */
	long SPAN_TIEM = 0L;

	/**
	 * 执行间隔时间2
	 */
	long SPAN_TIEM2 = 0L;

	/**
	 * (报表数据缓存时间)执行间隔时间
	 */
	long SPAN_TIEM3 = 0L;
	private Timer timer = null;
	private Timer timer2 = null;
	private Timer timer3 = null;
	private Timer timer4 = null;
	private Timer timer5 = null;
	private Timer timer6 = null;
//	private Timer timer7 = null;
	ApplicationContext appctx;

	StatisticFacadeIface statisticFacadeIface;

	StaticCacheFacadeIface staticCacheFacadeIface;

	NomalDangerFacadeIface nomalDangerFacadeIface;

	DangerFacadeIface dangerFacadeIface;

	/**
	 * 在Web应用启动时初始化任务
	 */
	public void contextInitialized(ServletContextEvent event) {
		START_TIME = Long.parseLong(ConfigUtil.getPropertyValue("local.timer.start_time"));
		SPAN_TIEM = Long.parseLong(ConfigUtil.getPropertyValue("local.timer.span_time"));
		SPAN_TIEM2 = Long.parseLong(ConfigUtil.getPropertyValue("local.timer.span_time2"));
		SPAN_TIEM3 = Long.parseLong(ConfigUtil.getPropertyValue("local.timer.span_time3"));
		appctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		statisticFacadeIface = (StatisticFacadeIface) appctx.getBean("statisticFacadeIface");
		staticCacheFacadeIface = (StaticCacheFacadeIface) appctx.getBean("staticCacheFacadeIface");
		nomalDangerFacadeIface = (NomalDangerFacadeIface) appctx.getBean("nomalDangerFacadeIface");
		dangerFacadeIface = (DangerFacadeIface) appctx.getBean("dangerFacadeIface");
		// 定义定时器
//		timer = new Timer(true);
//		// 启动任务
//		timer.schedule(new XmlTimerTask(), START_TIME, SPAN_TIEM);
//
//		 // 定义定时器2
//		timer2 = new Timer(true);
//		 // 启动任务
//		 timer2.schedule(new ChangeNomalDanger(), START_TIME, SPAN_TIEM2);
//
//		// 隐患治理优化定时器
//		timer3 = new Timer(true);
//		 timer3.schedule(new StatisticCache6(), 0, SPAN_TIEM3);
//
//		// 报送质量、行业、省局报表优化定时器
//		timer4 = new Timer(true);
//		 timer4.schedule(new StatisticCache5(), 0, SPAN_TIEM3);
//
//		// 月报优化定时器
//		timer5 = new Timer(true);
//		 timer5.schedule(new StatisticCache3(), 0, SPAN_TIEM3);
//
//		// 季报优化定时器
//		timer6 = new Timer(true);
//		 timer6.schedule(new StatisticCache4(), 0, SPAN_TIEM3);

		// // 省局报表定时发送
//		timer7 = new Timer(true);
//		timer7.schedule(new StatisticCache7(), 0, SPAN_TIEM3); // 晚22点

	}

	/**
	 * 在Web应用结束时停止任务
	 */
	public void contextDestroyed(ServletContextEvent event) {
		if (timer != null) {
			timer.cancel(); // 定时器销毁
		}
		if (timer2 != null) {
			timer2.cancel();
		}
		if (timer3 != null) {
			timer3.cancel();
		}
		if (timer4 != null) {
			timer4.cancel();
		}
		if (timer5 != null) {
			timer5.cancel();
		}
		if (timer6 != null) {
			timer6.cancel();
		}
	}

	/**
	 * 月报报表缓存数据生成
	 * 
	 * @author llj
	 * @create_time: 2014-9-13
	 * @Description:
	 */
	class StatisticCache3 extends TimerTask {
		public void run() {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (hour == 2) {
				try {
					System.out.println("月报报表缓存数据生成");
					staticCacheFacadeIface.loadCacheHis(Nbyhpc.Cname3);
				} catch (ApplicationAccessException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * 季报报表缓存数据生成
	 * 
	 * @author llj
	 * @create_time: 2014-9-13
	 * @Description:
	 */
	class StatisticCache4 extends TimerTask {
		public void run() {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (hour == 3) {
				try {
					System.out.println("季报报表缓存数据生成");
					staticCacheFacadeIface.loadCacheHis(Nbyhpc.Cname4);
				} catch (ApplicationAccessException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * 行业、省局报表、报送质量报表缓存数据生成
	 * 
	 * @author llj
	 * @create_time: 2014-9-13
	 * @Description:
	 */
	class StatisticCache5 extends TimerTask {
		public void run() {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (hour == 4) {

				try {
					System.out.println("报送质量报表缓存数据生成");
					staticCacheFacadeIface.loadCacheHis(Nbyhpc.Cname5);
					System.out.println("行业缓存数据生成");
					staticCacheFacadeIface.loadCacheTradeTypes();
					System.out.println("省局报表缓存数据生成");
					staticCacheFacadeIface.loadAxis2Client();

				} catch (ApplicationAccessException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * 隐患治理缓存数据生成
	 * 
	 * @author llj
	 * @create_time: 2014-9-13
	 * @Description:
	 */
	class StatisticCache6 extends TimerTask {
		public void run() {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (hour == 5) {

				try {

					System.out.println("隐患治理报表缓存数据生成");
					staticCacheFacadeIface.loadCacheHis(Nbyhpc.Cname6);

				} catch (ApplicationAccessException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * 省局报表定时发送
	 * 
	 * @author llj
	 * @create_time: 2014-9-22
	 * @Description:
	 */
	class StatisticCache7 extends TimerTask {
		public void run() {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if(hour == 22) { 

				try {

					System.out.println("省局报表定时发送之前先删除数据库结果数据和缓存数据");
					staticCacheFacadeIface.reCreateCache(1);
					staticCacheFacadeIface.reCreateCache(2);
					
					System.out.println("省局报表定时发送");
					staticCacheFacadeIface.sendData(1);
					staticCacheFacadeIface.sendData(2);

				} catch (ApplicationAccessException e) {
					e.printStackTrace();
				}

			}

		}
	}

	class ChangeNomalDanger extends TimerTask {
		public void run() {
			System.out.println("未治理隐患录入日期已达7天，自动转为已治理状态开始！");
			try {
				nomalDangerFacadeIface.updateNomalDangerRepaired(7);
			} catch (ApplicationAccessException e) {
				System.out.println("未治理隐患录入日期已达7天，自动转为已治理状态失败！");
				e.printStackTrace();
			}
			System.out.println("未治理隐患录入日期已达7天，自动转为已治理状态结束！");
		}
	}

	class XmlTimerTask extends TimerTask {
		public void run() {
			try {
				Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR_OF_DAY);
				if (hour == 23) {
					// anwei
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "anwei", "");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "anwei", "");
					// anjian
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "anjian", "（安监）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "anjian", "（安监）");
					// lvyou
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "lvyou", "（旅游）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "lvyou", "（旅游）");
					// jiaotong
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "jiaotong", "（交通）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "jiaotong", "（交通）");
					// jiaoyu
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "jiaoyu", "（教育）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "jiaoyu", "（教育）");
					// gongan
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "gongan", "（公安）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "gongan", "（公安）");
					// shuili
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "shuili", "（水利）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "shuili", "（水利）");
					// haiyang
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "haiyang", "（海洋渔业）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "haiyang", "（海洋渔业）");
					// wenguang
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "wenguang", "（文广）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "wenguang", "（文广）");
					// weisheng
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "weisheng", "（卫生）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "weisheng", "（卫生）");
					// minzong
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "minzong", "（民宗）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "minzong", "（民宗）");
					// dianli
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "dianli", "（电业）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "dianli", "（电业）");
					// chengguan
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "chengguan", "（城管）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "chengguan", "（城管）");
					// jianwei
					statisticFacadeIface.loadNormalDangerByIndustryAndArea(null, "jianwei", "（建委）");
					statisticFacadeIface.loadDangerByIndustryAndArea(null, "jianwei", "（建委）");

					// 隐患分类统计图
					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("anwei");
					statisticFacadeIface.loadDangerByIndustryAndArea1("anwei");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("anjian");
					statisticFacadeIface.loadDangerByIndustryAndArea1("anjian");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("lvyou");
					statisticFacadeIface.loadDangerByIndustryAndArea1("lvyou");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("jiaotong");
					statisticFacadeIface.loadDangerByIndustryAndArea1("jiaotong");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("jiaoyu");
					statisticFacadeIface.loadDangerByIndustryAndArea1("jiaoyu");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("gongan");
					statisticFacadeIface.loadDangerByIndustryAndArea1("gongan");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("shuili");
					statisticFacadeIface.loadDangerByIndustryAndArea1("shuili");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("haiyang");
					statisticFacadeIface.loadDangerByIndustryAndArea1("haiyang");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("wenguang");
					statisticFacadeIface.loadDangerByIndustryAndArea1("wenguang");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("weisheng");
					statisticFacadeIface.loadDangerByIndustryAndArea1("weisheng");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("minzong");
					statisticFacadeIface.loadDangerByIndustryAndArea1("minzong");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("dianli");
					statisticFacadeIface.loadDangerByIndustryAndArea1("dianli");

					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("chengguan");
					statisticFacadeIface.loadDangerByIndustryAndArea1("chengguan");
					
					statisticFacadeIface.loadNormalDangerByIndustryAndArea2("jianwei");
					statisticFacadeIface.loadDangerByIndustryAndArea1("jianwei");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
