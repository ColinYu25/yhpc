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
						<span style="font-size:23px;font-weight:bold;color:#394C86">项目重大隐患列表</span>
				</div>
			</div>
			<div class="box-con"> 
			    <table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
			       <tr class="tbbg" style="font-size:11px;font-weight:bold;">
			       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       	<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
			       	</td>
					</tr>
				  <tr>
					<td colspan="2">
				   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				   	    <tr class="table02">
						  <th width="5%">序号</th>
						  	<th width="35%">项目名称</th>
						  	<th width="35%">整治责任单位</th>
						  	<th width="15%">整治责任单位负责人</th>
						  </tr>
						  <#if itemDangers?exists && itemDangers?size gt 0>
						  	<#list itemDangers?if_exists as c>
							  <tr class="table03" <#if c_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
							    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
							    <td><div align="left">${c.daItem.itemname}&nbsp;</div></td>
							    <td><div align="left">${c.zzCompany}&nbsp;</div></td>
							    <td><#if c.zzChargeman?exists>${c.zzChargeman}<#else>&nbsp;</#if></td>	    
							  </tr>
							 </#list>
						   <#else>
							<tr class="table03" bgcolor="#ffffff">
								<td colspan="4" align="center">暂无记录</td>
							</tr>
						  </#if>
							<tr class="table03" bgcolor="#F5F5F5">
								<td  colspan="4">
									<@p.navigation pagination=pagination/>
								</td>
							</tr>
						</td>
					  </tr>
					</table>
					</td>
				  </tr>
				</table>
			</div>
		</div>
			
	<#include "statistic_foot.ftl">