package com.safetys.nbyhpc.util;

import java.util.Calendar;
import java.util.Date;

import com.safetys.nbyhpc.domain.model.Statistic;

/**
 * 根据年份、季度、月份，生成查询的开始时间、结束时间；
 * 
 * 使用方法： MonthQueryHelper helper = new MonthQueryHelper(year, quarter, month);
 * helper.getBeginDate();//获取查询的开始时间 helper.getEndDate();//获取查询的结束时间
 * 
 * @author qfc
 * 
 */
public class MonthQueryHelper {

	private Date beginDate;
	private Date endDate;
	private int year;
	private int quarter;
	private int month;
	private Date nowDate;

	public MonthQueryHelper(int year, int quarter, int month) {
		super();

//		System.out.println("month: " + month);

		this.year = year;
		this.quarter = quarter;
		this.month = month;
		compute();
	}

	public MonthQueryHelper(Statistic st) {
		super();

		this.year = st.getYear();
		this.quarter = st.getQuarter();
		this.month = st.getMonth();

//		System.out.println("this.quarter: " + this.quarter);
		compute();
	}

	/**
	 * 计算开始时间，结束时间
	 * 
	 */
	private void compute() {
		Calendar beginDateCal = Calendar.getInstance();
    	Calendar endDateCal = Calendar.getInstance();
    	Calendar nowDateCal = Calendar.getInstance();
    	beginDateCal.set(Calendar.DAY_OF_MONTH, 1);//默认1号开始
    	endDateCal.set(Calendar.DAY_OF_MONTH, 1);//默认1号开始
		
    	
    	
    	if (quarter == 0){//如果是按年查询
    		if (month > 0){//如果是按年查询，并且是选择了月份
    			beginDateCal.set(Calendar.YEAR, year);
    			beginDateCal.set(Calendar.MONTH, month - 1);
    			if (month == 12){
    				endDateCal.set(Calendar.YEAR, year + 1);
    				endDateCal.set(Calendar.MONTH, 0);
    			}else{
    				endDateCal.set(Calendar.YEAR, year);
    				endDateCal.set(Calendar.MONTH, month);
    			}
    		}else{
    			beginDateCal.set(year, 0, 1);//当年第一天
            	endDateCal.set(year + 1, 0, 1);//第二年第一天
    		}
    		
    		nowDateCal.set(Calendar.YEAR, year);
			nowDateCal.set(Calendar.MONTH, month);
			nowDateCal.set(Calendar.DAY_OF_MONTH, 1);
    		
    		
    	}else{
    		if (month > 0){
    			beginDateCal.set(Calendar.YEAR, year);
    			beginDateCal.set(Calendar.MONTH, month - 1);//calendar中，月从0开始
    			if (month == 12){
    				endDateCal.set(Calendar.YEAR, year + 1);
    				endDateCal.set(Calendar.MONTH, 0);
    			}else{
    				endDateCal.set(Calendar.YEAR, year);
    				endDateCal.set(Calendar.MONTH, month);
    			}
    			
    			
    			nowDateCal.set(Calendar.YEAR, year);
    			nowDateCal.set(Calendar.MONTH, month-1);
    			nowDateCal.set(Calendar.DAY_OF_MONTH, 1);
    		}else{
    			int beginMonth = quarter * 3 - 3;
        		int endMonth = quarter * 3;
        		
        		beginDateCal.set(Calendar.YEAR, year);
        		beginDateCal.set(Calendar.MONTH, beginMonth);
    			if (endMonth >= 12){
    				endDateCal.set(Calendar.YEAR, year + 1);
    				endDateCal.set(Calendar.MONTH, 0);
    			}else{
    				endDateCal.set(Calendar.YEAR, year);
    				endDateCal.set(Calendar.MONTH, endMonth);
    			}
    			
    			nowDateCal.set(Calendar.YEAR, year);
    			nowDateCal.set(Calendar.MONTH, endMonth-1);
    			nowDateCal.set(Calendar.DAY_OF_MONTH, 1);

    		}
    	}
    	
    	this.beginDate = beginDateCal.getTime();
    	this.endDate = endDateCal.getTime();
    	this.nowDate = nowDateCal.getTime();
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getBeginDate(String pattern) {
		return DateUtils.date2Str(this.getBeginDate(), pattern);
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEndDate(String pattern) {
		return DateUtils.date2Str(this.getEndDate(), pattern);
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

}
