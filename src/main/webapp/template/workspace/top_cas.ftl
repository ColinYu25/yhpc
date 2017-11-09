<@fkMacros.pageHeaderCas />
<#escape x as (x)!> 
<link href="${resourcePath}/css/mainFrame.css" rel="stylesheet" type="text/css" />
<div class="topbg">
<table width="100%" height="66" border="0" cellspacing="0" cellpadding="0" class="topbg2">
  <tr>
    <td rowspan="2" width="275"><img src="${resourcePath}/cas_images/topleft.jpg" width="275" height="66" /></td>
    <td height="30">
    <div class="admin"><span><img src="${resourcePath}/cas_images/admin.gif" width="16" height="16" /></span><span>当前登录用户：${userDetail.factName}</span></div>
	<div class="right_nav">
	<a href="#"><img src="${resourcePath}/cas_images/cog.gif" width="14" height="12" />修改密码</a>
	<a href="${contextPath}/logout.xhtml" onclick="if(logout()==false){return false;}"><img src="${resourcePath}/cas_images/Restart.gif" width="12" height="12" />退出</a>
	</div>
	<img src="${resourcePath}/cas_images/ioc01.gif" width="24" height="7" style="float: right; margin-top:5px;"/>
	</td>
  </tr>
  <tr>
    <td align="right">
    	<a href="#" border="0" onClick="javascript:top.location.href = '${contextPath}/workspace/loadNavigatorWorkSpace.xhtml';"><img src="${resourcePath}/img/login_cas.jpg"></a>
	</td>
  </tr>
</table>
 </#escape>
<@fkMacros.pageFooter />
