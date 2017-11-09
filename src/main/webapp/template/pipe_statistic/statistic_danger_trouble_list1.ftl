<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	function showDangerGorver(dn_id) {
		window.location = "loadDangerGorver.xhtml?dangerGorver.daPipeDanger.id=" + dn_id;
	}
	
	function showDanger(dn_id) {
		window.location = "loadDanger2.xhtml?danger.id=" + dn_id;
	}
	
	function showRollcallCompanies(dn_id) {
		window.location = "loadRollcallCompanies.xhtml?danger.id=" + dn_id;
	}
</script>
<body>&nbsp;
	<div class="main">
		<div class="box">
				<div class="box-top">
					<div class="box-nav" style="text-align:center;">
							<span style="font-size:23px;font-weight:bold;color:#394C86">管道重大事故隐患情况列表</span>
					</div>
				</div>
				<div class="box-con">  
					<form  style="margin-bottom:0px;margin-top:0px;" action="loadDangerTroubleByTypeList.xhtml?statistic.isSonType=${statistic.isSonType}&statistic.enumCode=${statistic.enumCode?default('')}&statistic.year=${statistic.year}&statistic.beg_month=${statistic.beg_month}&statistic.end_month=${statistic.end_month}&area.areaCode=${area.areaCode}&statistic.isGorver=${statistic.isGorver}&statistic.isRollcall=${statistic.isRollcall}&statistic.paramType=${statistic.paramType}" method="post" name="companyForm" id="companyForm">
				    <table class="table02" style="background:#F5F5F5;">
					    <tr>
					    	<td nowrap width="10%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:center">${area.areaName}&nbsp;</div></td> 
					      	<td nowrap width="15%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:right">企业名称:</div></td> 
					      	<td nowrap width="20%" style="text-align:left;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><input name="statistic.companyName" id="statistic.companyName" value="<#if statistic.companyName?exists>${statistic.companyName}</#if>" type="text" class="input" size="40"></td>
					      	<td nowrap width="15%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:right">管道名称:</div></td> 
					      	<td nowrap width="20%" style="text-align:left;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><input name="statistic.pipeName" id="statistic.companyName" value="<#if statistic.pipeName?exists>${statistic.pipeName}</#if>" type="text" class="input" size="40"></td>
					      	<td nowrap width="5%" style="border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left;"><input type="submit" id="sub" value="查 询" onClick="get('companyForm').submit();" style="input_submit"/></td>
					      	<td nowrap width="15%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:center">${statistic.year}年<#if statistic.beg_month!=0>${statistic.beg_month}月</#if>到<#if statistic.end_month!=0>${statistic.end_month}月</#if></div></td> 
					     </tr>
				    </table>  
				    </form>
					<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
					  <tr class="tbbg" style="font-size:16px;font-weight:bold;">
				       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       	<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><b><img style="border:0px" src="${resourcePath}/images/02.gif" /></b></a>
			       	</td>
					  </tr>
					  <tr>
						<td colspan="4">
					   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
							  	<tr class="table02">
									<td width="4%" nowrap>序号</td>
									<td width="10%" nowrap>管道名称</td>
								    <td width="20%" nowrap>企业名称</td>
								    <td width="10%" nowrap>隐患编号</td>
								    <td width="20%" nowrap>隐患地址</td>
								    <td width="10%" nowrap>计划治理期限</td>
								    <td width="7%" nowrap>治理状态</td>
								    <td width="10%" nowrap>实际治理日期</td>
								    <td width="7%" nowrap>是否超期</td>
								    <td width="10%" nowrap>是否挂牌督办</td>
								</tr>
							  <#if pipeDangers?exists && pipeDangers?size gt 0>
								  <#list pipeDangers?if_exists as c>
								   <tr class="table03" <#if c_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
								    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
								    <td><div align="center">${c.pipeLine.name?default('')}&nbsp;</div></td>
								    <td><div align="center">${c.pipeLine.daPipelineCompanyinfo.company.companyName}&nbsp;</div></td>
								    <td nowrap title="点击查看重大事故隐患情况表" nowrap onClick="showDanger(${c.id});" style="cursor:pointer;">${c.dangerNo}&nbsp;</td>
								    <td><div align="center"><#if c.dangerAdd?exists>${c.dangerAdd}<#else>&nbsp;</#if></div></td>
								    <td nowrap><#if c.finishDate?exists>${c.finishDate.toString().substring(0,10)}</#if>&nbsp;</div></td>
								    <td nowrap title="点击查看重大事故隐患治理情况表" nowrap onClick="showDangerGorver(${c.id});" style="cursor:pointer;"><#if c.daPipeDangerGorver?exists>是<#else>否</#if>&nbsp;</td>
								    <td nowrap><#if c.daPipeDangerGorver?exists><#if c.daPipeDangerGorver.finishDate?exists>${c.daPipeDangerGorver.finishDate.toString().substring(0,10)}<#else>-</#if><#else>-</#if>&nbsp;</td>
								    <td nowrap>
								    <#if c.isOver?exists>
									    <#if c.isOver=='1'>
										    <#if c.daPipeDangerGorver?exists && c.daPipeDangerGorver?size gt 0>
										    	<img src="${resourcePath}/images/cqyzl.gif" />
										    <#else>
										    	<img src="${resourcePath}/images/cqwzl.gif" />
										    </#if>
									    <#else>
									    	<img src="${resourcePath}/images/wcq.gif" />
									    </#if>
								    </#if>&nbsp;</td>
								    <td nowrap title="点击查看挂牌督办程序列表" nowrap onClick="showRollcallCompanies(${c.id});" style="cursor:pointer;"><#if (c.daPipeRollcallCompanies?size>0)>是<#else>否</#if>&nbsp;</td>
								  </tr>
								  </#list>
							  <#else>
								<tr class="table03">
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
										<td width="85%" style="border: #CCCCCC solid 1px; padding-left: 10px; color: #878383; ">
											<p>
												治理状态："是"代表重大隐患已治理，"否"代表重大隐患未治理。
											</p>
											<!--<p>
												是否超期："是"代表重大隐患已超期，"否"代表重大隐患未超期。
											</p>-->
											<p>
												是否挂牌督办："是"代表重大隐患已挂牌督办，"否"代表重大隐患未挂牌督办。
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