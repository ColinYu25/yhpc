<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-con">   
			    <table width="986" border="0" cellspacing="0" cellpadding="0" class="table01" style="margin-top:30px;">
			      <tr class="tbbg" style="font-size:11px;font-weight:bold;">
			       	<td width="100%" style="text-align:left">
			       		<a style="margin-left:25px;" href="#" class="b4" onClick="window.location.reload();"><img style="border:0px" src="${resourcePath}/images/01.gif" /></a>
			       		<a style="margin-left:10px;" href="javascript:history.go(-1);" class="b6"><img style="border:0px" src="${resourcePath}/images/02.gif" /></a>
			       	</td>
				  </tr>
				  <tr>
					<td colspan="2">
				   	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr class="table03" bgcolor="#ffffff">
					        <td ><strong>标  题</strong></td>
					        <td style="text-align:left"><#if sysComit><input name="daActualTableNotice.title" type="text" class="input" id="title" value="${daActualTableNotice.title}" size="40" maxlength="50">&nbsp;
					        <span style=color:red>*</span><ui:v for="title" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.title}</#if></td>
					        <td ><strong>起草时间</strong></td>
					        <td><#if sysComit><input name="daActualTableNotice.createTimeBegin" type="text"  id="createTimeBegin" value="${daActualTableNotice.createTimeBegin}"  style="ime-mode:disabled"  maxlength="10"size="25" onfocus="WdatePicker();" class="Wdate" >&nbsp;
					        <span style=color:red>*</span><ui:v for="createTimeBegin" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.createTimeBegin}</#if></td>
					      	</tr>
					      </tr>
					      <tr class="table03">
					       	<td><strong>起草人</strong></td>
					        <td style="text-align:left"><#if sysComit><input name="daActualTableNotice.author" type="text" class="input" id="author" value="${daActualTableNotice.author}" size="40" maxlength="25">&nbsp;
					        <span style=color:red>*</span><ui:v for="author" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.author}</#if></td>
					        <td><strong>起草部门</strong></td>
					        <td><#if sysComit><input name="daActualTableNotice.dept" type="text" class="input" id="dept" value="${daActualTableNotice.dept}" size="25" maxlength="25">&nbsp;
					        <span style=color:red>*</span><ui:v for="dept" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/><#else>${daActualTableNotice.dept}</#if></td>
					      </tr>
					      <tr class="table03" bgcolor="#ffffff">
					      	<td><strong>内容</strong></td>
					      	<td colspan="3" style="text-align:left"><#if sysComit><textarea name="daActualTableNotice.content" id="content" style="display:none" >${daActualTableNotice.content}</textarea>
					             <iframe ID="editor" src="../editor/ewebeditor.htm?id=content" frameborder=1 scrolling=no width="98%" height="405"></iframe><#else>${daActualTableNotice.content}</#if></td>
					      	</td>
					      </tr>
				     </table>	
					</td>
				  </tr>
				</table>
			</div>
		</div>
	<#include "statistic_foot.ftl">