<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<style type="text/css">
			.table_4_blue {border: 1px solid #519ccc;}
			.selectli1 {
				background: url(${resourcePath}/img/right_13.gif);
			}
			a {
				font-size: 12px;
				color: #000;
			}
			a:link {
				text-decoration: none;
			}
			a:visited {
				text-decoration: none;
				color: #000;
			}
			a:hover {
				text-decoration: none;
				color: #000;
			}
			a:active {
				text-decoration: none;
				color: #000;
			}
			.a_blue_12b {font-size: 12px;color: #1767A8; font-weight:bold;}
			.a_blue_12b:link {text-decoration: none;}
			.a_blue_12b:visited {text-decoration: none;	color: #1767A8;}
			.a_blue_12b:hover {text-decoration: none;color: #1767A8;}
			.a_blue_12b:active {text-decoration: none;color: #1767A8;}
			
			.a_blue_12 {font-size: 12px;color: #1767A8;}
			.a_blue_12:link {text-decoration: none;}
			.a_blue_12:visited {text-decoration: none;	color: #1767A8;}
			.a_blue_12:hover {text-decoration: none;color: #1767A8;}
			.a_blue_12:active {text-decoration: none;color: #1767A8;}
			body{ margin:0px; padding:0px;}
		</style>
	</head>
	<body style="background-image:url(${resourcePath}/img/wel_bg.jpg);">
		<!--<#if userDetail?? && userDetail.userIndustry?? && userDetail.userIndustry=='qiye'>
			<div align="center"><img src="${resourcePath}/img/no_type_bg1.jpg">
		<#else>
			<div align="center"><img src="${resourcePath}/img/no_type_bg.jpg">
		</#if>-->
	</body>
	<script type="text/javascript">
		<#if userDetail?? && userDetail.userIndustry?? && userDetail.userIndustry=='qiye'>
			window.location.href="${contextPath}/pipeNomalDanger/loadPipeLines.xhtml";
		<#else>
			window.location.href="${contextPath}/pipeline/yq_pipeline_yqStatistic.xhtml";
		</#if>
		
	</script>
</html>
