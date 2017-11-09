<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管道安全生产隐患排查管道列表</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-top">
				<div class="box-nav" style="text-align:center;">
						<span style="font-size:23px;font-weight:bold;color:#394C86">管道安全生产隐患排查管道列表</span>
				</div>
			</div>
			<div class="box-con">   
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
						  	<th id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
						    <th id="th_id" width="4%" nowrap>序号</th>
						    <th id="th_companyName" width="25%" nowrap>使用单位</th>
						    <th id="th_companyName" width="14%" nowrap>管道名称</th>
						    <th id="th_companyName" width="9%" nowrap>管道长度</th>    
						    <th id="th_regAddress" width="6%" nowrap>铺设方式</th>
						    <th id="th_phoneCode"  width="14%" nowrap>管道起点</th>        
						    <th id="th_phoneCode"  width="14%" nowrap>管道止点</th>  
						    <th id="th_phoneCode"  width="8%" nowrap>竣工时间</th>                  
						  </tr>
						  <#if result?exists>
						  	<#assign color1 = '#FFFFFF'>
				  			<#assign color2 = '#F5F5F5'>
						  	<#list result?if_exists as item>
							  <tr class="table03" bgcolor=<#if item_index % 2 == 0>${color1}<#else>${color2}</#if>>
							  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
							    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
							    <td style="text-align:center" title="<#if item.daPipelineCompanyinfo.company.companyName??>${item.daPipelineCompanyinfo.company.companyName}</#if>">&nbsp;<#if item.daPipelineCompanyinfo.company.companyName??><#if item.daPipelineCompanyinfo.company.companyName?length gt 17>${item.daPipelineCompanyinfo.company.companyName?substring(0,17)}...<#else>${item.daPipelineCompanyinfo.company.companyName}</#if></#if>&nbsp;</td>	    
							    <td style="text-align:center" title="<#if item.name??>${item.name}</#if>">&nbsp;<#if item.name??><#if item.name?length gt 10>${item.name?substring(0,10)}...<#else>${item.name}</#if></#if>&nbsp;</td>
							    <td><#if item.length??>${item.length?string("####.####")} Km </#if>&nbsp;</td>
							    <td>
							    <#if item.buildType?? && item.buildType ==1>
							    	架空	    
							    <#elseif item.buildType?? && item.buildType ==2>
							    	埋地
							    <#else>
							    	地面
							    </#if>&nbsp;
							    </td>
							    <td title="<#if item.startPoint??>${item.startPoint}</#if>"><#if item.startPoint??><#if item.startPoint?length gt 10>${item.startPoint?substring(0,10)}...<#else>${item.startPoint}</#if></#if>&nbsp;</td>
							    <td title="<#if item.endPoint??>${item.endPoint}</#if>"><#if item.endPoint??><#if item.endPoint?length gt 10>${item.endPoint?substring(0,10)}...<#else>${item.endPoint}</#if></#if>&nbsp;</td>
							    <td><#if item.buildEndDate??>${item.buildEndDate?string('yyyy-MM-dd')}&nbsp;</#if></td>	    	    	    
							  </tr>
							 </#list>
						  <#else>
							<tr class="table03" bgcolor="#ffffff">
								<td colspan="10" align="center">暂无记录</td>
							</tr>
						 </#if>
							 <tr class="table03" nowrap="nowrap" >
								<td  colspan="9">
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