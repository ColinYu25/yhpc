<#escape x as (x)!>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>宁波市安全生产事故隐患排查治理信息系统</title>
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/new_css.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<link href="${resourcePath}/css/top_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.top_shadow{height:0}
</style>
<script type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/top.js"></script>
<#if casUser?? || esUser??>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/cas_roll.js"></script>
</#if>
</head>
<body>
<div id="top">
	<#if casUser?? && aw?? && aw =="false" >
	<#include "cas_top.ftl"/>
	</#if>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" height="500px;" style="background-image:url(${resourcePath}/img/wel_bg.jpg);">
	  <tr>
		<th width="50%" height="100%">
			<table width="70%" border="0" cellpadding="0" cellspacing="0" height="500px;" style="background-image:url(${resourcePath}/img/no_type_bg3.jpg);margin:0 auto;">
	  			<tr><th>&nbsp;</th></tr>
	  			<tr><th>&nbsp;</th></tr>
	  			<tr><th>&nbsp;</th></tr>
			  	<tr>
	  				<th style=" height:30px; line-height:30px;">
	  				    <input type="hidden" name="companyIsDelete" id="companyIsDelete" value="${companyIsDelete}" />
						<a title="点击进去企业隐患管理" style="FONT-SIZE: 27px; height:30px; color:#484848;" href="javascript:checkCompanyIsDelete();"><img src="${resourcePath}/img/p1_05.png" /></a>
						<a title="点击进去管道隐患管理" style="margin-left:70px;FONT-SIZE: 27px; height:30px; color:#484848;" href="javascript:window.location.href='loadMainWorkSpace2.xhtml'"><img src="${resourcePath}/img/p1_03.png" /></a>
					</th>
			  	</tr>
	  			<tr><th>&nbsp;</th></tr>
			</table>
		</th>
	  </tr>
	</table>
</div>
<div id="color_line"></div>
<div id="gray_line"></div>
</body>
<script language="javascript">
		
		function checkCompanyIsDelete(){
			var obj=document.getElementById("companyIsDelete");
			if(obj!=null){
			   var companyIsDelete=obj.value;
			   if(companyIsDelete=='1'){
			      alert("此企业已被隐患排查系统删除，不能进入！");
			      return;
			   }else{
			      window.location.href='loadMainWorkSpace1.xhtml';
			   }
			}else{
			   window.location.href='loadMainWorkSpace1.xhtml';
			}
		}
		
	</script>
	
</#escape>
<@fkMacros.pageFooter />