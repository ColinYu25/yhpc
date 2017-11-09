<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link href="${resourcePath}/css/style_list.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script>
	function get(obj) {
		return document.getElementById(obj);
	}
</script>
</head>
<body>&nbsp;
<div class="main">
	<div class="box">
			<div class="box-top">
				<div class="box-nav">
					<#if area.areaCode == 330200000000>
						<ul class="fnlist3">
							<li><a <#if !statistic?if_exists.type?exists && statistic?if_exists.type?default(99)==99>class="hover"</#if> href="loadDanger.xhtml">各地管道隐患情况</a></li>
							<li><a <#if statistic?if_exists.type?exists && statistic?if_exists.type?default(99)!=99>class="hover"</#if> href="loadDanger.xhtml?statistic.type=3">各部门管道隐患情况</a></li>
						</ul>
					<#elseif !statistic?if_exists.type?exists && statistic?if_exists.type?default(99)==99>
						<ul class="fnlist_3">
							<li><a href="loadDanger.xhtml">返回市级统计</a></li>
							<li><a class="hover" href="#">各地管道隐患情况</a></li>
							<li><a href="loadDanger.xhtml?statistic.type=3&area.areaCode=${area.areaCode}&statistic.year=${statistic.year}&statistic.beg_month=${statistic.beg_month}&statistic.end_month=${statistic.end_month}">各部门管道隐患情况</a></li>
						</ul>
					<#else>
						<ul class="fnlist_3">
							<li><a href="loadDanger.xhtml">返回市级统计</a></li>
							<li><a href="loadDanger.xhtml?area.areaCode=${area.areaCode}&statistic.year=${statistic.year}&statistic.beg_month=${statistic.beg_month}&statistic.end_month=${statistic.end_month}">各地管道隐患情况</a></li>
							<li><a class="hover" href="#">各部门管道隐患情况</a></li>
						</ul>
					</#if>
				</div>
			</div>