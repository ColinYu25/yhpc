package com.safetys.nbyhpc.facade.iface;

import java.util.List;


/**
 * 
 * @author yangb
 *
 */
public interface SmsInfoFacadeIface {

	/**
	 * 发送短信给当月没上报隐患情况的企业
	 */
	public void save4SendToCompanyByMonth();
	
	/**
	 * 发送短信给当季度没有上报隐患季报的企业
	 */
	public void save4SendToCompanyByQuarter();
	
	public String send(String phone, String message);
	
	/**
	 * include 是否发送的是areaCodes里的区域，如果否则发送宁波市下，出去areaCodes里的区域值
	 * @param areaCodes
	 * @param include
	 */
	public void sendByArea4Particial(List<Long> areaCodes, boolean include);
	
}
