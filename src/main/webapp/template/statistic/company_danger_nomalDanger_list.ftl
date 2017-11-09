<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	function showDangerGorver(dn_id) {
		window.location = "loadDangerGorver.xhtml?dangerGorver.daDanger.id="+dn_id+"";
	}
	
	function showDanger(dn_id) {
		window.location = "loadDanger.xhtml?danger.id="+dn_id+"";
	}
	
	function showRollcallCompanies(dn_id) {
		window.location = "loadRollcallCompanies.xhtml?danger.id="+dn_id+"";
	}
</script>
<body>&nbsp;
	<div class="main">
		<div class="box">
				<div class="box-top">
					<div class="box-nav" style="text-align:center;">
							<span style="font-size:23px;font-weight:bold;color:#394C86">监管部门检查发现隐患情况列表</span>
					</div>
				</div>
				<div class="box-con">  
					<table width="986" class="table02" style="background:#F5F5F5;">
					    <tr>
					    	<td nowrap width="33%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:center">${area.areaName}&nbsp;</td> 
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
									<td width="4%">序号</td>
									<td width="25%">检查发现的问题</td>
									<td width="15%">检查单位</td>
									<td width="8%">检查时间</td>
									<td width="10%">检查人员</td>
									<td width="10%">联系方式</td>
									<td width="8%">整改完成期限</td>
									<td width="8%">治理情况确认</td>
								</tr>
							  <#assign index=0>
								<#if (daNomalDangers?if_exists?size > 0)>
									<#list daNomalDangers?if_exists as nd><#assign index=1+index>
									<tr class="table03">
										<td>${index}</td>
										<td><div align="left"><#if nd.danger>${nd.description}<#else>无隐患</#if></div></td>
										<td><#if nd.industryName?exists>${nd.industryName}</#if>&nbsp;</td>
										<td><#if nd.createTime?exists>${nd.createTime.toString().substring(0,10)}</#if>&nbsp;</td>
										<td><#if nd.linkMan?exists>${nd.linkMan}</#if>&nbsp;</td>
										<td><#if nd.linkTel?exists>${nd.linkTel}</#if>&nbsp;</td>
										<td>-</td>
										<td><#if nd?if_exists.repaired?exists&&nd.repaired>已<#else>未</#if></td>
									</tr>
									</#list>
								</#if>
								<#if (daDangers?if_exists?size > 0)>
									<#list daDangers?if_exists as d><#assign index=d_index+index>
									<tr class="table03" bgcolor="#ffffff">
										<td>${index+1}</td>
										<td><div align="left"><#if d.description?exists>${d.description}</#if>&nbsp;</div></td>
										<td><#if d.dangerAdd?exists>${d.dangerAdd}</#if>&nbsp;</td>
										<td><#if d.createTime?exists>${d.createTime.toString().substring(0,10)}</#if>&nbsp;</td>
										<td><#if d.linkMan?exists>${d.linkMan}</#if>&nbsp;</td>
										<td><#if d.linkTel?exists>${d.linkTel}</#if>&nbsp;</td>
										<td><#if d.finishDate?exists>${d.finishDate.toString().substring(0,10)}</#if>&nbsp;</td>
										<td><#if (d.daDangerGorvers?size>0)>已<#else>未</#if></td>
									</tr>
									</#list>
								<#else>
									<tr class="table03" height="25" bgcolor="#ffffff">
										<td colspan="10" align="center">暂无记录</td>
									</tr>
								</#if>
									<tr class="table03" nowrap="nowrap" align="center" bgcolor="#F5F5F5">
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
												治理情况确认："已"代表隐患已治理，"未"代表隐患未治理。
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