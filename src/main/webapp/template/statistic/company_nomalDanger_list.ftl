<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<body>&nbsp;
	<div class="main">
		<div class="box">
				<div class="box-top">
					<div class="box-nav" style="text-align:center;">
							<span style="font-size:23px;font-weight:bold;color:#394C86">企业一般事故隐患情况列表</span>
					</div>
				</div>
				<div class="box-con">
					<table width="986" class="table02" style="background:#F5F5F5;">
					    <tr>
					    	<td nowrap width="33%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:center"><#if area?exists>${area.areaName}</#if>&nbsp;</td> 
					      	<td nowrap width="33%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:center">${company.companyName}</td> 
					      	<td nowrap width="33%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:center">${company.year}年第${company.quarter}季度<#if company.month!=0>${company.month}月</#if></td> 
					    </tr>
			    	</table>   
					<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
				       <tr class="tbbg" style="font-size:16px;font-weight:bold;">
				       	<td width="100%" style="font-size:11px;text-align:left">
				       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       		<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
				       	</td>
					  </tr>
					  <tr>
						<td colspan="4">
					   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr class="table02">
									<td width="5%" rowspan="2">序号</td>
									<td width="35%" rowspan="2">隐患描述</td>
									<td width="17%" colspan="3">隐患类别</td>
									<td width="10%" rowspan="2">录入时间</td>
									<td width="17%" colspan="2">录入方式</td>
									<td width="17%" colspan="2">治理状态</td>
								</tr>
								<tr class="table02">
									<td width="6%">人</td>
									<td width="6%">物</td>
									<td width="6%">管理</td>
									<td width="6%">自报</td>
									<td width="6%">代报</td>
									<td width="6%">未治理</td>
									<td width="6%">已治理</td>
								</tr>
							  <#if (daNomalDangers?if_exists?size > 0)>
								<#list daNomalDangers?if_exists as nd>
								<tr class="table03" <#if nd_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
									<td title="隐患录入的人ID：${nd.userId}||${nd.daCompanyPass.comUserId}"><#if pagination.itemCount?exists>${pagination.itemCount+nd_index+1}<#else>${nd_index+1}</#if></td>
									<td><div align="left"><#if nd.danger && nd.description??>${nd.description}<#else>无隐患</#if>&nbsp;</div></td>
									<td><#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1327>√</#if></#if></#if>&nbsp;</td>
									<td><#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1332>√</#if></#if></#if>&nbsp;</td>
									<td><#if nd.danger><#if nd.type?exists><#if nd?if_exists.type?if_exists==1344>√</#if></#if></#if>&nbsp;</td>
									<td><#if nd.createTime?exists>${nd.createTime.toString().substring(0,10)}</#if>&nbsp;</td>
									<td><#if nd.daCompanyPass.comUserId?exists&&nd.daCompanyPass.comUserId!=0><#if nd.userId?exists&&nd.userId!=0><#if nd.daCompanyPass.comUserId==nd.userId>√</#if></#if></#if>&nbsp;</td>
									<td><#if nd.daCompanyPass.comUserId?exists&&nd.daCompanyPass.comUserId!=0><#if nd.userId?exists&&nd.userId!=0><#if nd.daCompanyPass.comUserId!=nd.userId>√</#if><#else>√</#if><#else>√</#if>&nbsp;</td>
									<td><#if nd?if_exists.danger?exists&&nd.danger><#if !nd?if_exists.repaired?exists||!nd.repaired>√</#if></#if>&nbsp;</td>
									<td><#if nd?if_exists.danger?exists&&nd.danger><#if nd?if_exists.repaired?exists&&nd.repaired>√</#if></#if>&nbsp;</td>
								</tr>
								</#list>
							<#else>
								<tr class="table03" bgcolor="#ffffff">
									<td colspan="10" align="center">暂无记录</td>
								</tr>
							</#if>
								<tr  nowrap="nowrap" class="table03">
									<td  colspan="10">
										<@p.navigation pagination=pagination/>
									</td>
								</tr>
					     </table>	
						</td>
					  </tr>
					</table>
					<div style="margin-left:-5px;text-align:left">
						<table  width="986"  cellpadding="0" cellspacing="5">
							<tr>
							<td >
								<table width="984" border="0" cellpadding="0" cellspacing="0" style="border: #CCCCCC solid 1px; border-collapse: collapse; background: #f6f6f6; font-size: 12px;line-height:200%;">
									<tr>
										<th width="15%" style="border: #CCCCCC solid 1px; color: #5494af; line-height: 30px; text-align: center;">
											说
											<br />
											明
										</th>
										<td width="85%" style="border: #CCCCCC solid 1px; padding-left: 10px; color: #878383; " style="text-align:left">
											<p>
												隐患类别："√"代表隐患所属的类别。人：人的不安全行为；管理：管理上的缺陷；物： 物的不安全状态。
											</p>
											<p>
												录入方式："√"代表隐患所属录入方式。
											</p>
											<p>
												治理状态："√"代表隐患所属治理状态。
											</p>
										</td>
									</tr>
								</table>
							</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
<#include "statistic_foot.ftl">