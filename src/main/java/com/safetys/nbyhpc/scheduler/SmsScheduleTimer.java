package com.safetys.nbyhpc.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.safetys.constant.SystemConstant;

import com.safetys.nbyhpc.facade.iface.SmsInfoFacadeIface;

/**
 * 短信定时任务
 * @author yangb
 *
 */
public class SmsScheduleTimer {

	private SmsInfoFacadeIface smsInfoFacadeIface;
	private SystemConstant systemConstant;
	
	/**
	 * 最后一天发送的区域，数量比较大
	 */
	private static List<Long> lastDayArea = new ArrayList<Long>();
	
	static {
		lastDayArea.add(330212000000l);//鄞州
		lastDayArea.add(330225000000l);//象山
		lastDayArea.add(330281000000l);//余姚
		lastDayArea.add(330282000000l);//慈溪
	}
	
	public void sendSmsByMonth() {
		if(systemConstant.isDataSeparation()&&!systemConstant.isGovernment()){
			return ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = df.format(new Date());
		Calendar lastTwoCd = Calendar.getInstance();
		//本月最后第二天
		lastTwoCd.set(Calendar.DATE, lastTwoCd.getActualMaximum(Calendar.DATE));
		lastTwoCd.add(Calendar.DAY_OF_MONTH, -1);
		String lastTwoDay = df.format(lastTwoCd.getTime());
		if (currentDate.equals(lastTwoDay)) {
			smsInfoFacadeIface.sendByArea4Particial(lastDayArea, false);
		} else {
			Calendar lastDayCd = Calendar.getInstance();
			//本月最后一天
			lastDayCd.set(Calendar.DATE, lastDayCd.getActualMaximum(Calendar.DATE));
			String lastDay = df.format(lastDayCd.getTime());
			if (currentDate.equals(lastDay)) {
				smsInfoFacadeIface.sendByArea4Particial(lastDayArea, true);
			}
		}
	}
	
	public void sendSmsByQuarter() {
		if(systemConstant.isDataSeparation()&&!systemConstant.isGovernment()){
			return ;
		}
		smsInfoFacadeIface.save4SendToCompanyByQuarter();
	}

	public void setSmsInfoFacadeIface(SmsInfoFacadeIface smsInfoFacadeIface) {
		this.smsInfoFacadeIface = smsInfoFacadeIface;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}
	
	
}
