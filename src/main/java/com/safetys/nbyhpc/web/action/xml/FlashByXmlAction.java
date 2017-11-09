package com.safetys.nbyhpc.web.action.xml;

import java.util.Calendar;

import com.safetys.nbyhpc.domain.model.Statistic;
import com.safetys.nbyhpc.web.action.base.DaAppAction;

public class FlashByXmlAction extends DaAppAction {

	private Statistic statistic;

	/**
	 * 用户登陆首页面统计表读取的XML文件生产
	 */
	private static final long serialVersionUID = -1596023476020065303L;

	public String loadFlashByXmlOfDanger() {
		if (statistic == null)
			statistic = new Statistic();
		Calendar cal = Calendar.getInstance();
		statistic.setYear(cal.get(Calendar.YEAR));
		return SUCCESS;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

}
