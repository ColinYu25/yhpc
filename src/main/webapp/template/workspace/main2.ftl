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
					<#if p.resourceId != 580749>
						<#if p.resourceId == 1017413 || p.resourceId == 2 || p.resourceId == 1017435>
							menuNames[idVal]="${p.resourceName}";
							menuIds[idVal]="${p.resourceId}";
							idVal++;
						</#if>
					</#if>
				</#list>
			</#if>
		</script>
		<#if casUser?? || esUser??>
			<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/cas_roll.js"></script>
		</#if>
	</head>			
	<frameset rows="84,*" cols="*" frameborder="No" border="0" framespacing="0">
	  <frame src="loadTopWorkSpace2.xhtml" name="topFrame" scrolling="No" id="topFrame"></frame>
	  <frameset id="main" rows="*" cols="178,16,*" framespacing="0" frameborder="No" border="0">
	  	<frame src="" id="leftFrame" name="leftFrame"></frame>
		<FRAME name=control src="loadControlWorkSpace.xhtml" scrolling=no>	
	  	<frame src="loadRightWorkSpacePipe.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
	  </frameset>
	</frameset>
	<noframes></noframes>
	<body></body>
</html>