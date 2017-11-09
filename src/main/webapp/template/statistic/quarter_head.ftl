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
					<ul class="fnlist3">
						<li><a <#if !statistic.remark?exists || statistic.remark==''>class="hover"</#if> href="loadQuarter.xhtml">各地季报进度</a></li>
						<li><a <#if statistic.remark?exists && statistic.remark!=''>class="hover"</#if> href="loadQuarterByIndustry.xhtml?statistic.remark=anjian">各部门季报进度</a></li>
					</ul>
				</div>
			</div>