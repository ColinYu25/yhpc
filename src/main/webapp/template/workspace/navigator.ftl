<@fkMacros.pageHeader />
<script src="${resourcePath}/js/prototype.lite.js" type="text/javascript"></script>
<script src="${resourcePath}/js/moo.fx.js" type="text/javascript"></script>
<script src="${resourcePath}/js/moo.fx.pack.js" type="text/javascript"></script>
<script type="text/javascript">
	window.location = "loadMainWorkSpace.xhtml";
</script>
<style type="text/css">
<!--
body, html {
background-color:#ddf6ff;
height:100%;
}
-->
</style>
<table width="800" height="2" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>此导航页面可以根据自身项目的需要，在自己项目中定义页面已对类包中的默认页面进行覆盖</td>
  </tr>
</table>
<table width="800" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="menu">
 <#if fkPermissions?exists>
	 <#list fkPermissions as p>
	  <tr>
	    <td width="800" valign="top">
	        <a href="${contextPath}/workspace/loadMainWorkSpace.xhtml?nodeId=${p.id}">${p.permissionName}</a>
		</td>
	  </tr>
	 </#list>
 </#if>
</table>
<@fkMacros.pageFooter />


