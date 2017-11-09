<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>宁波市安全生产事故隐患排查治理信息系统</title>
		<link rel="stylesheet" type="text/css" href="${resourcePath}/css/css.css"/>
		<script type="text/javascript">
			var menuNames=new Array();
			var menuIds=new Array();
			var idVal = 0;
			<#if resources?exists>
				<#list resources as p>
					<#if p.resourceId == 1017413>
						<#assign rFlag = true>
					</#if>
					<#if p.resourceId != 580749>
						menuNames[idVal]="${p.resourceName}";
						menuIds[idVal]="${p.resourceId}";
						idVal++;
					</#if>
				</#list>
			</#if>
			<#if whpCompanyUser>
				<#if whpPipeCompanyUser>
					alert("1.危险化学品企业请于1月15日前完成上一年\n度安全生产情况报告录入工作。\n2.危险化学品管道企业请及时录入管道隐患排\n查治理信息。");
				<#else>
					alert("危险化学品企业请于1月15日前完成上一年\n度安全生产情况报告录入工作。");
				</#if>
			</#if>
			<#if rFlag?? && rFlag>
				window.location = "loadMainWorkSpaceRedirect.xhtml";
			</#if>
		</script>
		<#if casUser?? || esUser??>
			<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/cas_roll.js"></script>
		</#if>
	</head>			
	<frameset rows="84,*" cols="*" frameborder="No" border="0" framespacing="0">
	  <frame src="loadTopWorkSpace.xhtml" name="topFrame" scrolling="No" id="topFrame"></frame>
	  <frameset id="main" rows="*" cols="178,16,*" framespacing="0" frameborder="No" border="0">
	  	<frame src="" id="leftFrame" name="leftFrame"></frame>
		<FRAME name=control src="loadControlWorkSpace.xhtml" scrolling=no>	
	  <frame src="../xml/loadFlashByXmlOfDanger.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
	  </frameset>
	</frameset>
	<noframes></noframes>
	<body></body>
</html>