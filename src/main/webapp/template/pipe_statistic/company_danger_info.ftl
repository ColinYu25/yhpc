<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<script language="JavaScript" type="text/javascript" src="/nbyhpc/resources/default/js/public.js"></script>
<script language="JavaScript" type="text/javascript" src="/nbyhpc/resources/default/js/common.js"></script>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<body>&nbsp;
	<div class="main">
		<div class="box">
				<div class="box-top">
					<div class="box-nav" style="text-align:center;">
						<span style="font-size:23px;font-weight:bold;color:#394C86">管道重大事故隐患情况表</span>
					</div>
				</div>
				<div class="box-con">  
					<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
						<tr class="tbbg"style="font-size:11px;font-weight:bold;">
				       		<td width="100%" style="text-align:left" colspan="6">
				       			<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       			<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
				       		</td>
					   	</tr>
				         <tr bgcolor="#ffffff" class="table03">
							<td style="width:14%;" align="right" nowrap><strong>管道名称</strong></td>
							<td colspan="2">&nbsp;${danger.pipeLine.name?default('')}</td>
							<td width="11%" align="right" nowrap><strong>隐患编号</strong></td>
							<td colspan="2">&nbsp;${danger.dangerNo}</td>
						  </tr>
						  <tr class="table03">
						    <td style="width:12%;" align="right" nowrap><strong>单位名称</strong></td>
						    <td colspan="5">${danger.pipeLine.daPipelineCompanyinfo.company.companyName}</td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" align="right" nowrap><strong>单位地址</strong></td>
						    <td colspan="2">&nbsp;${danger.pipeLine.daPipelineCompanyinfo.company.regAddress}</td>
						    <td width="11%" align="right" nowrap><strong>市级以上重点工程</td>
							<td colspan="2"> <input type="radio" name="danger.emphasisProject" value="true" <#if danger?if_exists.emphasisProject?exists&&danger.emphasisProject>checked</#if>>
						      是    
						      <input type="radio" name="danger.emphasisProject" value="false" <#if !danger?if_exists.emphasisProject?exists||!danger.emphasisProject>checked</#if>>
						    否</td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>隐患地址：</strong></td>
						    <td colspan="5" style="text-align:left"><#if danger.dangerAdd?exists>${danger.dangerAdd}</#if></td>
						  </tr>
						  <tr  class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>隐患区域：</strong></td>
						    <td colspan="5" style="text-align:left"><div id="dangerArea"></div></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>联 系 人：</strong></td>
						    <td width="20%"><#if danger.linkMan?exists>${danger.linkMan}</#if></td>
						    <td style="width:12%;" align="right" nowrap><strong>联系电话：</strong></td>
						    <td width="20%"><#if danger.linkTel?exists>${danger.linkTel}</#if></td>
						    <td style="width:12%;" align="right" nowrap><strong>手机：</strong></td>
						    <td width="28%"><#if danger.linkMobile?exists>${danger.linkMobile}</#if></td>
						  </tr>
						  <tr class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>隐患基本情况：</strong><br>
						    （简述）</td>
						    <td colspan="5" style="text-align:left"><textarea name="danger.description" id="description" disabled cols="74" rows="4">${danger.description}</textarea></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>隐患类别 ：</strong></td>
						    <td colspan="5" style="text-align:left">
						    <#if industryParameters?exists>
						  	<#list industryParameters?if_exists as i>
						  		<#assign checked=''>
						  			<#if danger?exists>
						  				<#list danger.daIndustryParameters?if_exists as ddi>
						  					<#if i.id=ddi.id>
						  						<#assign checked='checked="checked"'>
						  					</#if>
						  				</#list>
						  			</#if>
						  		<input type="checkbox" ${checked} disabled name="danger.industryParameters" value="${i.id}" />&nbsp;${i.name}<#if (i_index+1)%3!=0>　　　</#if>
						  	</#list>
						  	</#if>
						  	<ui:v for="danger.industryParameters" rule="Group" min="1" pass="&nbsp;"  warn="请至少选择一项" />
						  	</td>
							</tr>
						 <tr  class="table03">
						    <td colspan="6" height="25" align="right" ><p align="center"><strong>整治说明</strong></td>
						  </tr>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>是否需政府协调：</strong></td>
						    <td width="20%" align="center" nowrap>
						      <input type="radio" name="danger.govCoordination" value="true" disabled <#if danger?if_exists.govCoordination?exists&&danger.govCoordination>checked</#if>>
						      是    
						      <input type="radio" name="danger.govCoordination" value="false" disabled <#if !danger?if_exists.govCoordination?exists||!danger.govCoordination>checked</#if>>
						    否 </td>
						    <td style="width:12%;" align="right" nowrap><strong>是否需局部停产停业：</strong></td>
						    <td width="20%" align="center" nowrap>
						      <input type="radio" name="danger.partStopProduct" value="true" disabled <#if danger?if_exists.partStopProduct?exists&&danger.partStopProduct>checked</#if>>
						      是    
						      <input type="radio" name="danger.partStopProduct" value="false" disabled <#if !danger?if_exists.partStopProduct?exists||!danger.partStopProduct>checked</#if>>
						    否 </td>
						    <td style="width:12%;" align="right" nowrap><strong>是否需全部停产停业：</strong></td>
						    <td width="20%" align="center" nowrap>    
						      <input type="radio" name="danger.fullStopProduct" value="true" disabled <#if danger?if_exists.fullStopProduct?exists&&danger.fullStopProduct>checked</#if>>
						      是    
						      <input type="radio" name="danger.fullStopProduct" value="false" disabled <#if !danger?if_exists.fullStopProduct?exists||!danger.fullStopProduct>checked</#if>>
						    否 </td>
						  </tr>
						  <tr  class="table03">
						    <td colspan="6" height="25" align="right" ><p align="center"><strong>隐患治理落实措施情况</strong></p></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>落实治理目标：</strong></td>
						    <td align="center" nowrap>
						      <input type="radio" name="danger.target" value="true" disabled <#if danger?if_exists.target?exists&&danger.target>checked</#if>>
						    是    
						    <input type="radio" name="danger.target" value="false" disabled <#if !danger?if_exists.target?exists||!danger.target>checked</#if>>
						    否 </td>
						    <td style="width:12%;" align="right"><strong>落实治理机构人员：</strong></td>
						    <td align="center" nowrap>
						      <input type="radio" name="danger.resource" value="true" disabled <#if danger?if_exists.resource?exists&&danger.resource>checked</#if>>
						    是    
						    <input type="radio" name="danger.resource" value="false" disabled <#if !danger?if_exists.resource?exists||!danger.resource>checked</#if>>
						    否 </td>
						    <td style="width:12%;" nowrap align="right"><strong>落实安全&nbsp;&nbsp;<br>措施及应急预案：</strong></td>
						    <td align="center" nowrap>
						      <input type="radio" name="danger.safetyMethod" value="true" disabled <#if danger?if_exists.safetyMethod?exists&&danger.safetyMethod>checked</#if>>
						    是    
						    <input type="radio" name="danger.safetyMethod" value="false" disabled <#if !danger?if_exists.safetyMethod?exists||!danger.safetyMethod>checked</#if>>
						    否 </td>
						  </tr>
						  <tr  class="table03">
						    <td style="width:12%;" height="25" nowrap align="right"><strong>落实治理经费物资：</strong></td>
						    <td align="center" nowrap>
						      <input type="radio" name="danger.goods" value="true" disabled <#if danger?if_exists.goods?exists&&danger.goods>checked</#if>>
						    是    
						    <input type="radio" name="danger.goods" value="false" disabled <#if !danger?if_exists.goods?exists||!danger.goods>checked</#if>>
						    否 </td>
						    <td style="width:12%;" align="right"><strong>计划完成治理时间：</strong></td>
						    <td><#if danger.finishDate?exists>${danger.finishDate?date}</#if></td>
						    <td style="width:12%;" align="right"><strong>落实治理经费：</strong></td>
						    <td><#if danger.governMoney?exists>${danger.governMoney.toString()}</#if></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td style="width:12%;" height="25" align="right" nowrap><strong>单位负责人：</strong></td>
						    <td><#if danger.chargePerson?exists>${danger.chargePerson}</#if></td>
						    <td style="width:12%;" align="right"><strong>填报时间：</strong></td>
						    <td><#if danger.fillDate?exists>${danger.fillDate?date}</#if></td>
						    <td style="width:12%;" align="right"><strong>填 报 人：</strong></td>
						    <td><#if danger.fillMan?exists>${danger.fillMan}</#if></td>
							</tr>
					</table>
				</div>
			</div>
			<script language="javascript">
				var areaObj = new Area("${resourcePath}/js/nbyhpc_area.xml");
				
				var dangerFirstArea="";
				var dangerSecondArea="";
				var dangerThirdArea="";
			  <#if danger?exists>
			  
			 	<#if danger.firstArea != 0>
					dangerFirstArea=areaObj.getArea("${danger.firstArea}")[0];
				</#if>
				<#if danger.secondArea != 0>
					dangerSecondArea=areaObj.getArea("${danger.secondArea}")[0];
				</#if>
				<#if danger.thirdArea != 0>
					dangerThirdArea=areaObj.getArea("${danger.thirdArea}")[0];
				</#if>
			  </#if>
				get("dangerArea").innerHTML=dangerFirstArea+dangerSecondArea+dangerThirdArea;
				
			</script>
<#include "statistic_foot.ftl">