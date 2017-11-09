<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	function showDaNomalDangers(com_id,companyName) {
		window.location = "loadNomalDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
	
	function showDaDangers(com_id,companyName) {
		window.location = "loadDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
	
	function showDangersAndNomalDangers(com_id,companyName) {
		window.location = "loadDangersAndNomalDangers.xhtml?company.id="+com_id+"&company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}&company.companyName="+companyName+"";
	}
</script>
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-top">
				<div class="box-nav" style="text-align:center;">
						<span style="font-size:23px;font-weight:bold;color:#394C86">企业安全生产隐患排查治理工作情况表</span>
				</div>
			</div>
			<div class="box-con">   
			    <form  style="margin-bottom:0px;margin-top:0px;" action="?company.year=${company.year}&company.month=${company.month}&company.quarter=${company.quarter}&area.areaCode=${area.areaCode}<#if company.industryId?exists && company.industryId!=0>&company.industryId=${company.industryId}</#if>&company.companyTrade=${company.companyTrade}" method="post" name="companyForm" id="companyForm">
			    <table class="table02" style="background:#F5F5F5;">
				    <tr>
				    	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:center">${area.areaName}&nbsp;</div></td> 
				      	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:right">企业名称:</div></td> 
				      	<td nowrap width="20%" style="text-align:left;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><input name="company.companyName" id="company.companyName" value="<#if company.companyName?exists>${company.companyName}</#if>" type="text" class="input" size="40"></td>
				      	<td nowrap width="20%" style="border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left;"><input type="submit" id="sub" value="查 询" onClick="get('companyForm').submit();" style="input_submit"/></td>
				      	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:center">${company.year}年第${company.quarter}季度<#if company.month!=0>${company.month}月</#if></div></td> 
				     </tr>
			    </table>  
			    </form>
			    <table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
			       <tr class="tbbg" style="font-size:11px;font-weight:bold;">
			       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       	<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><b><img style="border:0px" src="${resourcePath}/images/02.gif" /></b></a>
			       	</td>
					</tr>
				  <tr>
					<td colspan="2">
				   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr class="table02">
						  	<td width="5%" nowrap>序号</td>
						  	<td width="35%" nowrap>单位名称</td>
						  	<td width="15%" nowrap>发现一般事故隐患数</td>
						  	<td width="15%" nowrap>发现重大事故隐患数</td>
						  	<td width="15%" nowrap>季度统计报表报送情况</td>
						  	<td width="15%" nowrap>监管部门检查发现隐患数</td>
						  </tr>
						  <#if statistics?exists && statistics?size gt 0>
						  	<#list statistics?if_exists as s>
							  <tr class="table03" <#if s_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
							    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
							    <td ><div align="left"><#if s.companyName?exists>${s.companyName}<#else>&nbsp;</#if></div></td>
							    <td title="点击查看企业一般隐患情况列表" nowrap onClick="showDaNomalDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.anum}</td>
							    <td title="点击查看企业重大隐患情况列表" nowrap onClick="showDaDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.bnum}</td>	  
							    <td nowrap><#if s.cnum==0>ㄨ<#else>√</#if></td>
							    <td title="点击查看企业监管部门检查发现隐患情况列表" nowrap onClick="showDangersAndNomalDangers(${s.companyId},'${s.companyName}');" style="cursor:pointer;">${s.dnum}</td>	
							  </tr>
							 </#list>
						  <#else>
							<tr class="table03" bgcolor="#ffffff">
								<td colspan="10" align="center">暂无记录</td>
							</tr>
						 </#if>
							 <tr class="table03" nowrap="nowrap" >
								<td  colspan="6">
									<@p.navigation pagination=pagination/>
									<@fkMacros.remark />
								</td>
							 </tr>
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
							<table width="986" border="0" cellpadding="0" cellspacing="0" style="border: #CCCCCC solid 1px; border-collapse: collapse; background: #f6f6f6; font-size: 12px;line-height:200%;">
								<tr>
								<th width="15%" style="border: #CCCCCC solid 1px; color: #5494af; line-height: 30px; text-align: center;">
									说
									<br />
									明
								</th>
								<td width="85%" style="border: #CCCCCC solid 1px; padding-left: 10px; color: #878383; " style="text-align:left">
									<p>
										季度统计报表报送情况："√"代表本季度季报已上报，"ㄨ"代表本季度季报未上报。
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