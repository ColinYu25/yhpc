<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />   
<title></title>
<link href="${resourcePath}/css/dtree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${resourcePath}/js/dtree.js"></script>
<style type="text/css">
.style1 {font-size: 10}
.zk{
	font-size:12px;
	color:#000;
}
</STYLE>
</head>
<BODY leftMargin=0 topMargin=0 rightMargin=0 marginwidth="0" marginheight="0" class=menu 
ondragstart=self.event.returnValue=false 
onselectstart=self.event.returnValue=false style="background-color: #F5FAFE;overflow-x:hidden;background-image:url(${resourcePath}/images/pic-left.gif);">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><B><img src="${resourcePath}/images/pic-left-top.gif" width="190" height="33" align="absmiddle"></B></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10px;">
  <tr>
    <td width="100%">
    
    <table border="0" cellpadding="0" cellspacing="0" id="menuTable">
<tr><td height="22"><B><img src="${resourcePath}/images/smileface.gif" width="15" height="15" align="absmiddle">&nbsp;<span style="font-size:12px;cursor:hand" title="点击修改用户信息" onClick="javascript:window.parent.rightFrame.location.href='${contextPath}/person/updatePersonInit.xhtml'"><#if userDetail??  &&userDetail.factName?? >${userDetail.factName}</#if></span></B>   <span><a class="zk" href="javascript: d.openAll();"><u>展开</u></a>/<a class="zk" href="javascript: d.closeAll();"><u>收缩</u></a></span></td></tr></table>
		<script type="text/javascript">
		d = new dTree('d');
		d.add(${resource.resourceId},-1,'${resource.resourceName}');
		<#if casUser?? && aw?? && aw =="false" >
			<#list allResources as p>
				if('${p.resourceUrl}' != "/logout.xhtml") {
					if('${p.resourceUrl}'!=null && '${p.resourceUrl}'.indexOf(' ')==-1){
						d.add(${p.resourceId},${p.resourceFatherId},' ${p.resourceName}','${contextPath}${p.resourceUrl}','','rightFrame','','','1');
					} else {
						d.add(${p.resourceId},${p.resourceFatherId},' ${p.resourceName}','','','rightFrame','','','1');
					}
				}
			</#list>
		<#else>
			<#list allResources as p>
				if('${p.resourceUrl}'!=null && '${p.resourceUrl}'.indexOf(' ')==-1){
					if('${p.resourceUrl}' == "/logout.xhtml") {
						d.add(${p.resourceId},${p.resourceFatherId},' ${p.resourceName}','${contextPath}${p.resourceUrl}?logoutSuccessUrl=${sso_logout?default("")}','','_parent','','','1');
					} else {
						d.add(${p.resourceId},${p.resourceFatherId},' ${p.resourceName}','${contextPath}${p.resourceUrl}','','rightFrame','','','1');
					}
				} else {
					d.add(${p.resourceId},${p.resourceFatherId},' ${p.resourceName}','','','rightFrame','','','1');
				}
			</#list>
		</#if>
		document.write(d);
		</script>
</td>
  </tr>
</table>
</BODY>
</HTML>