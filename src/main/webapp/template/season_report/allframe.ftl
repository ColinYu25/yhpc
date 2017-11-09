<@fkMacros.pageHeader />
<#escape x as (x)!> 
<link rel="stylesheet" href="../css.css">
<link href="${resourcePath}/css/cssbak.css" rel="stylesheet" TYPE="text/css">
<link href="${resourcePath}/css/navg.css" rel="stylesheet" TYPE="text/css">
<script language="javascript">
function switchTag(tag)
{
	if(document.all("dataNavg")==undefined){   
		for(var i=0; i<document.all("dataNavg").length; i++) {
			document.all("dataNavg")[i].id="";
		}
	} else {
		document.getElementById("dataNavg").id="";
	}
  tag.parentElement.id="dataNavg";
}
</script>
</head>

<body>
	<div class="navg">
		<#if !seasonReport??>
			<div id="data" class="mainNavg">
				<ul>
				<li id=""><a href="createSeasonReportInit.xhtml?<#if company??>company.id=${company.id}<#else>bag.id=${bag.id}</#if>&seasonNumber=${seasonNumber}&seasonReportType=1&companyOrBag=${companyOrBag}" target="ENTERPRISE" onClick="switchTag(this)" onFocus="this.blur()">季报1</a></li>
				<li id="dataNavg"><a href="createSeasonReportInit.xhtml?<#if company??>company.id=${company.id}<#else>bag.id=${bag.id}</#if>&seasonNumber=${seasonNumber}&seasonReportType=2&companyOrBag=${companyOrBag}" target="ENTERPRISE" onClick="switchTag(this)" onFocus="this.blur()">季报2</a></li>
			</div>
		<#else>
			<#if seasonReport.id==-1>
			<div id="data" class="mainNavg">
				<ul>
				<li id=""><a href="createSeasonReportInit.xhtml?<#if company??>company.id=${company.id}<#else>bag.id=${bag.id}</#if>&seasonNumber=${seasonNumber}&seasonReportType=1&companyOrBag=${companyOrBag}" target="ENTERPRISE" onClick="switchTag(this)" onFocus="this.blur()">季报1</a></li>
				<li id="dataNavg"><a href="createSeasonReportInit.xhtml?<#if company??>company.id=${company.id}<#else>bag.id=${bag.id}</#if>&seasonNumber=${seasonNumber}&seasonReportType=2&companyOrBag=${companyOrBag}" target="ENTERPRISE" onClick="switchTag(this)" onFocus="this.blur()">季报2</a></li>
			</div>                         
			</#if>
		</#if>
		<div id="mainContent" class="secondaryNavg">
			<table width="50"><tr><td height="1"></td></tr></table>
			<iframe src="createSeasonReportInit.xhtml?<#if company??>company.id=${company.id}<#else>bag.id=${bag.id}</#if>&seasonNumber=${seasonNumber}&companyOrBag=${companyOrBag}&seasonReportType=<#if seasonReport??><#if (seasonReport.daSeasonReportDetails?size>0)>1<#else>2</#if><#else>2</#if><#if isSee??>&isSee=1</#if>" name="ENTERPRISE" id="enterprise" style="width:100%;height:600px;overflow:visible;" frameborder="0"></iframe>
		</div>
	</div>
</#escape> 
<@fkMacros.pageFooter />