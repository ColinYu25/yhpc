<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="/nbyhpc/resources/default/js/public.js"></script>
<script language="JavaScript" type="text/javascript" src="/nbyhpc/resources/default/js/common.js"></script>
<script type="text/javascript">
	var enumObj=new Enum("${enumXmlUrl}");
	var areaObj = new Area("${AreaXmlUrl}");
</script>
<body>&nbsp;
	<div class="main">
		<div class="box">
				<div class="box-top">
					<div class="box-nav" style="text-align:center;">
							<span style="font-size:27px;font-weight:bold;color:#394C86">管道重大事故隐患挂牌督办情况列表</span>
					</div>
				</div>
				<div class="box-con">  
					<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:0px;">
				      	<tr class="tbbg"style="font-size:11px;font-weight:bold;">
				       		<td width="100%" style="text-align:left" colspan="6">
				       			<a style="margin-left:25px;" href="#" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
				       			<a style="margin-left:10px;" href="javascript:history.go(-1);"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
				       		</td>
					   	</tr>
					   	<tr>
						<td colspan="4">
					   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
						  	<tr class="table02">
								 <td width="3%" nowrap>序号</td>
							    <td width="24%" nowrap>单位名称</td>
							    <td width="12%" nowrap>隐患编号</td>
							    <td width="18%" nowrap>隐患地址</td>
							    <td width="9%" nowrap>是否整改</td>
							    <td width="9%" nowrap>挂牌级别</td>
							    <td width="9%" nowrap>督办单位</td>
							    <td width="9%" nowrap>督办部门</td>
							    <td width="9%" nowrap>督办完成时间</td>
							  </tr>
						  	  <#if rollcallCompanies?exists>
							  <#list rollcallCompanies?if_exists as r>
								<tr class="table03" <#if r_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
							    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+r_index+1}<#else>${r_index+1}</#if></td>
							    <td id="roleName" name="roleName"><div align="center">${r.daPipeDanger.pipeLine.daPipelineCompanyinfo.company.companyName}&nbsp;</div></td>
							    <td nowrap><#if r.daPipeDanger.dangerNo?exists>${r.daPipeDanger.dangerNo}</#if>&nbsp;</td>
							    <td><div align="center"><#if r.daPipeDanger.dangerAdd?exists>${r.daPipeDanger.dangerAdd}</#if>&nbsp;</div></td>
							    <td nowrap><#if (r.daPipeDanger?if_exists.daPipeDangerGorvers?size>0)>是<#else>否</#if></td>
							    <td><#if r.level?exists><script type="text/javascript">document.write(enumObj.getSelect("${r.level}"));</script></#if>挂牌</td>
							    <td><#if r.govment?exists><script type="text/javascript">document.write(areaObj.getArea("${r.govment}")[0]);</script></#if>&nbsp;</td>
							    <td><#if r.department?exists>${r.department}</#if>&nbsp;</td>
							    <td><#if r.completeTime?exists>${r.completeTime?date}</#if>&nbsp;</td>
							  </tr>
							  </#list>
							  <#else>
								<tr height="25" bgcolor="#ffffff">
									<td colspan="10" align="center">暂无记录</td>
								</tr>
							 </#if>
							 <tr nowrap="nowrap" align="center" bgcolor="#F5F5F5">
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
												是否整改："是"代表重大隐患已整改。
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