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
							<span style="font-size:27px;font-weight:bold;color:#394C86">重大事故隐患治理情况表</span>
					</div>
				</div>
				<div class="box-con">  
					<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
						<tr class="tbbg"style="font-size:11px;font-weight:bold;">
				       		<td width="100%" style="text-align:left" colspan="7">
				       			<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       			<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
				       		</td>
					   	</tr>
				         <tr bgcolor="#ffffff" class="table03">
						    <td colspan="2" height="25" align="right"><strong>单位名称：</strong></td>
						    <td colSpan="2" style="text-align:left"><#if company.companyName?exists>${company.companyName}</#if>&nbsp;</td>
						    <td align="right"><strong>编　号：</strong></td>
						    <td colspan="2" style="text-align:left"><#if danger.dangerNo?exists>${danger.dangerNo}</#if>&nbsp;</td>
						  </tr>
						  <tr class="table03">
						    <td width="7%" rowSpan="2" align="right"><strong>单　位</strong></td>
						    <td width="7%" height="25" align="right"><strong>地　址：</strong></td>
						    <td colSpan="5" style="text-align:left"><#if company.regAddress?exists>${company.regAddress}</#if>&nbsp;</td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td height="25" align="right"><strong>区　域：</strong></td>
						    <td colSpan="5" height="25" id="companyArea" style="text-align:left"></td>
						  </tr>
						  <tr class="table03">
						    <td height="25" rowSpan="2" align="right"><strong>隐　患</strong></td>
						    <td height="25" align="right"><strong>地　址：</strong></td>
						    <td height="25" colSpan="5" style="text-align:left"><#if danger.dangerAdd?exists>${danger.dangerAdd}</#if>&nbsp;</td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td height="25" align="right"><strong>区　域：</strong></td>
						    <td colSpan="5" id="dangerArea" style="text-align:left">&nbsp;</td>
						  </tr>
						  <tr class="table03">
						    <td height="25" colspan="2" align="right"><strong>联 系 人：</strong></td>
						    <td width="20%"><#if dangerGorver.linkMan?exists>${dangerGorver.linkMan}</#if></td>
						    <td style="width:12%;" align="right"><strong>联系电话：</strong></td>
						    <td width="19%"><#if dangerGorver.linkMan?exists>${dangerGorver.linkMan}</#if></td>
						    <td style="width:12%;" align="right"><strong>手　　机：</strong></td>
						    <td width="28%"><#if dangerGorver.linkMobile?exists>${dangerGorver.linkMobile}</#if></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td height="25" colspan="2" rowspan="2" align="right"> <p><strong>隐患整治情况：</strong><BR>
						      （简述）</p></td>
						    <td colSpan="5" style="text-align:left"><TEXTAREA name="dangerGorver.gorverContent" rows="4" disabled id="gorverContent" cols="74"><#if dangerGorver.gorverContent?exists>${dangerGorver.gorverContent}</#if></TEXTAREA></td>
						  </tr>
						  <tr class="table03">
						  	<td height="25" width="20%" align="center"><strong>治理验收评估情况</strong></td>
						  	<td colspan="4" style="text-align:left">
						  	自　　评：　　<input type="radio" name="dangerGorver.evaluate" disabled value="true" <#if dangerGorver?if_exists.evaluate?exists&&dangerGorver.evaluate>checked</#if>>是　　　
									<input type="radio" name="dangerGorver.evaluate" disabled value="false" <#if !dangerGorver?if_exists.evaluate?exists||!dangerGorver.evaluate>checked</#if>>否<br>
							专家评估：　　<input type="radio" name="dangerGorver.evaluateExpert" disabled value="true" <#if dangerGorver?if_exists.evaluateExpert?exists&&dangerGorver.evaluateExpert>checked</#if>>是　　　
									<input type="radio" name="dangerGorver.evaluateExpert" disabled value="false" <#if !dangerGorver?if_exists.evaluateExpert?exists||!dangerGorver.evaluateExpert>checked</#if>>否<br>
							政府验收：　　<input type="radio" name="dangerGorver.evaluateGovernment" disabled value="true" <#if dangerGorver?if_exists.evaluateGovernment?exists&&dangerGorver.evaluateGovernment>checked</#if>>是　　　
									<input type="radio" name="dangerGorver.evaluateGovernment" disabled value="false" <#if !dangerGorver?if_exists.evaluateGovernment?exists||!dangerGorver.evaluateGovernment>checked</#if>>否
						</td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td height="25" colspan="2" align="right"><strong>完成整治时间：</strong></td>
						    <td colSpan="2" style="text-align:left"><#if dangerGorver.finishDate?exists>${dangerGorver.finishDate?date}</#if></td>
						    <td align="right"> <p><strong>实际投入资金：</strong></p></td>
						    <td colSpan="2" style="text-align:left"><#if dangerGorver.money?exists>${dangerGorver.money.toString()}</#if></td>
						  </tr>
						  <tr class="table03">
						    <td height="25" colspan="2" align="right"><strong>备　　　注：</strong></td>
						    <td colSpan="5" style="text-align:left"><TEXTAREA id="memo" name="dangerGorver.memo" disabled rows="4" cols="74"><#if dangerGorver.memo?exists>${dangerGorver.memo}</#if></TEXTAREA></td>
						  </tr>
						  <tr bgcolor="#ffffff" class="table03">
						    <td height="25" colspan="2" align="right"><strong>单位负责人：</strong><BR>
						    </p></td>
						    <td><#if dangerGorver.chargePerson?exists>${dangerGorver.chargePerson}</#if></td>
						    <td style="width:12%;" align="right"><strong>填 表 人：</strong></td>
						    <td><#if dangerGorver.fillMan?exists>${dangerGorver.fillMan}</#if></td>
						    <td style="width:12%;" align="right"><strong>填报日期：</strong></td>
						    <td><#if dangerGorver.fillDate?exists>${dangerGorver.fillDate?date}</#if></td>
						  </tr>
					</table>
				</div>
			</div>
			<script language="javascript">
				var areaObj = new Area("/nbyhpc/resources/default/js/nbyhpc_area.xml");
			
				var companyFirstArea="";
				var companySecondArea="";
				var companyThirdArea="";
				
				var dangerFirstArea="";
				var dangerSecondArea="";
				var dangerThirdArea="";
				
			  <#if danger?exists>
			 	<#if company.firstArea != 0>
					companyFirstArea=areaObj.getArea("${company.firstArea}")[0];
				</#if>
				<#if company.secondArea != 0>
					companySecondArea=areaObj.getArea("${company.secondArea}")[0];
				</#if>
				<#if company.thirdArea != 0>
					companyThirdArea=areaObj.getArea("${company.thirdArea}")[0];
				</#if>
			  </#if>
			 
				get("companyArea").innerHTML=companyFirstArea+companySecondArea+companyThirdArea;
				
				
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