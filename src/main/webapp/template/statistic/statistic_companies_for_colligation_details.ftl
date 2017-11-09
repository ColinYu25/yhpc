<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}/css/public.css" />
<link rel="stylesheet" type="text/css" href="${resourcePath}/css/other.css" />
<!--wbox-->
<link href="${resourcePath}/wbox/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script> 
<script type="text/javascript" src="${resourcePath}/js/mapapi.js"></script> 
<script type="text/javascript" src="${resourcePath}/js/wbox.js"></script> 
<script type="text/javascript"> 
	function OpenCompanyWindow(id,companyId){		
	   	$(id).wBox({
			title: "生产经营单位企业档案&nbsp;",
		 	requestType:"iframe",
		 	iframeWH:{width:570,height:520},
		 	show:true,
		 	target:"http://60.190.2.104/nbajj_colligation/companyInfo/loadCompanyInfo.xhtml?company.id=" + companyId
		 });
	}
</script>
<!--wbox-->
</head>
<body>
<div class="sub_main" >
<div id="main"  style="height:800px;">
<div class="list_title_blue" >	${area.areaName}生产经营单位统计  
</div>
	<div class="main_content padd10">
	<div id="main_content">
					<form  style="margin-bottom:0px;margin-top:0px;" action="loadCompanysForColligationShowDetails.xhtml?area.areaCode=${area.areaCode}&areaLevel=${areaLevel}&type1=${type1}" method="post" name="companyForm" id="companyForm"> 
				    <table class="table02" style="background:#ffffff;">
					    <tr>
					    	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:center">&nbsp;</div></td> 
					      	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><div style="text-align:right">企业名称:</div></td> 
					      	<td nowrap width="20%" style="text-align:left;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;"><input name="company.companyName" id="company.companyName" value="<#if company??><#if company.companyName?exists>${company.companyName}</#if></#if>" type="text" class="input" size="40"></td>
					      	<td nowrap width="20%" style="border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;text-align:left;"><input type="submit" id="sub" value="查 询" onClick="get('companyForm').submit();" style="input_submit"/></td>
					      	<td nowrap width="20%" style="font-size:16px;border-right:0px solid #AED4F3;font-weight:bold;border-bottom:0px solid #AED4F3;">&nbsp;</td> 
					     </tr>
				    </table>
				    </form>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
				      <tr class="tbbg" style="font-size:16px;font-weight:bold;">
				       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       	<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><b><img style="border:0px" src="${resourcePath}/images/02.gif" /></b></a>
			       	</td>
					  </tr>
					  <tr>
						<td colspan="4">
					   	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main_tab">
							  	<tr class="tit" id="tr_area" >
									<td width="3%" nowrap>序号</td>
									<td width="35%">企业名称</td>
									<td width="30%">企业地址</td>
									<td width="8%">法人代表</td>
									<td width="12%" >联系电话</td>
								</tr>
							  <#if (companies?if_exists?size > 0)>
								<#list companies?if_exists as co>
								<tr class="table03" <#if co_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
									<td><#if pagination.itemCount?exists>${pagination.itemCount+co_index+1}<#else>${co_index+1}</#if></td>
									<td><div align="left"><a href="#" id="isFrame${co.id}" onClick="OpenCompanyWindow('isFrame${co.id}','${co.id}');"><#if co.companyName?exists>${co.companyName}<#else>&nbsp;</#if></a></td>
									<td><div align="left"><#if co.regAddress?exists>${co.regAddress}<#else>&nbsp;</#if></td>
									<td><#if co.fddelegate?exists>${co.fddelegate}<#else>&nbsp;</#if></td>
									<td><#if co.phoneCode?exists>${co.phoneCode}<#else>&nbsp;</#if></td>
								</tr>
								</#list>
							</#if>
								<tr class="table03" nowrap>
									<td  colspan="5">
										<@p.navigation pagination=pagination/>
									</td>
								</tr>
					     </table>	
						</td>
					  </tr>
					</table>
				</div>
			</div>



<#include "statistic_foot.ftl">