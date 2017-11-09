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
						<#if p.resourceId != 1017413 && p.resourceId != 1017435>
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
	 <#if flag?? && flag="Rollcall" >
	  	<frame src="loadTopWorkSpace1.xhtml?flag=${flag}" name="topFrame" scrolling="No" id="topFrame"></frame>
	  <#else>
	  	<frame src="loadTopWorkSpace1.xhtml" name="topFrame" scrolling="No" id="topFrame"></frame>
	  </#if>
	  
	  <frameset id="main" rows="*" cols="178,16,*" framespacing="0" frameborder="No" border="0">
	  	<frame src="" id="leftFrame" name="leftFrame"></frame>
		<FRAME name=control src="loadControlWorkSpace.xhtml" scrolling=no>	
		
	  	<#if flag??>
			  	<#if flag="ReportByAj">
				  <frame src="../statistic/loadReportByAj.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
				<#elseif flag="Danger"  &&  DangerId ??>
				  <frame src="../danger/loadDanger.xhtml?danger.id=${DangerId}" id="rightFrame" name="rightFrame" noresize="noresize">
				<#elseif flag="Rollcall">
			  	  <frame src="../rollcallCompany/loadRollcallCompanies.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
			  	<#elseif flag="NomalDanger">
			  	  <frame src="../statistic/loadNomalDangerByIndustry.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
			  	<#elseif  flag="nomalDangers">
			  	  <frame src="../seasonReport/loadNomalDangers.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
			  	<#elseif flag="dangersTimeOut">
			  	  <frame src="../danger/loadDangersOfCompanyTimeOut.xhtml?company.id=${companyId}" id="rightFrame" name="rightFrame" noresize="noresize">
			  	<#elseif flag="YearReport"  &&  EntityId ??>
			  	  <frame src="../wh/company_view.xhtml?entity.id=${EntityId}" id="rightFrame" name="rightFrame" noresize="noresize">
				<#else>
			  	  <frame src="../xml/loadFlashByXmlOfDanger.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
			  	</#if>
	  	
	  	<#else>
	  	   <frame src="../xml/loadFlashByXmlOfDanger.xhtml" id="rightFrame" name="rightFrame" noresize="noresize">
	  	</#if>
	  </frameset>
	</frameset>
	<noframes></noframes>
	<body></body>
</html>