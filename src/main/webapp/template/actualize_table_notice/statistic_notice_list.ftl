<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-con">   
			    <form  style="margin-bottom:0px;margin-top:0px;" action="loadStatisticNotices.xhtml?sysComit=${sysComit?string}" method="post" name="tradeTypeForm" id="tradeTypeForm">
			    <table class="table02" style="background:#F5F5F5;">
				    <tr>
				    	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:right">标题：</td> 
				      	<td nowrap width="30%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left"><input type="text" name="daActualTableNotice.title" id="title" size="35" value="<#if daActualTableNotice?exists><#if daActualTableNotice.title?exists>${daActualTableNotice.title}</#if></#if>" maxlength="25"></td> 
				      	<td nowrap width="10%" style="border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:right">发布时间：</td>
				      	<td nowrap width="10%" style="border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left;text-align:left"><input type="text" name="daActualTableNotice.createTimeBegin" id="createTimeBegin" size="10" value="<#if daActualTableNotice?exists><#if daActualTableNotice.createTimeBegin?exists>${daActualTableNotice.createTimeBegin?date}</#if></#if>" style="ime-mode:disabled"  maxlength="10" size="14" maxlength="10"  onfocus="WdatePicker();"  class="Wdate"></td>
				      	<td nowrap width="30%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left"><input type="submit" style="margin-left:50px;" id="sub" value="查  询" onClick="this.form.submit();" style="input_submit"/></td> 
				     </tr>
			    </table>  
			    </form>
			    <table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
			      <tr class="tbbg" style="font-size:11px;font-weight:bold;">
			       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
			       	</td>
				  </tr>
				  <tr>
					<td colspan="2">
				   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr class="table02">
						    <td width="5%">序号</td>
						    <td width="75%">标题</td>
						    <td width="20%">发布时间</td>
						  </tr>
						  <#if actualizeTableNoticeList?exists>
						  	<#list actualizeTableNoticeList as actualizeTableNotice>
					  		<tr class="table03" <#if actualizeTableNotice_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
						    	<td><#if pagination.itemCount?exists>${pagination.itemCount+actualizeTableNotice_index+1}<#else>${actualizeTableNotice_index+1}</#if></td>
					  			<td><div align="left"><a href="loadStatisticNotice.xhtml?daActualTableNotice.id=${actualizeTableNotice.id}&sysComit=${sysComit?string}">${actualizeTableNotice.title}&nbsp;</a></div></td>
					  			<td>&nbsp;${actualizeTableNotice.createTimeBegin}</td>
					 		</tr>
						  	</#list>
						  </#if>
						  <tr class="table03" nowrap="nowrap" >
							<td  colspan="4">
								<#if (actualizeTableNoticeList?if_exists?size>0)>
									<@p.navigation pagination=pagination/>
								<#else>
									此处暂时没有记录！
								</#if>
							</td>
						 </tr>
				     </table>	
					</td>
				  </tr>
				</table>
			</div>
		</div>
	<#include "statistic_foot.ftl">