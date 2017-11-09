<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产隐患排查治理信息平台</title>
<#global p=JspTaglibs["/WEB-INF/tlds/p.tld"]>
<link href="${resourcePath}/css/style_stable.css" rel="stylesheet" type="text/css" />
<div id="wrap">
	<div id="content">
	<table width="1003" height="398" cellspacing="1" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;; background-color:#C1DAD7">
           <tbody><tr>
            <th height="25" bgcolor="#D6EEF0" align="center" colspan="4"><span class="head">企业列表</span></th>
            </tr>
			
		  <tr height="28" bgcolor="#dcfafa" align="center">
		  	<th width="5%">序号</th>
		  	<th width="35%">单位名称</th>
		  	<th width="35%">单位地址</th>
		  	<th width="15%">法定代表人</th>
		  </tr>
		  <#if companies?exists>
		  	<#list companies?if_exists as c>
			  <tr align="center" <#if c_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#dcfafa"</#if>>
			    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
			    <td><div align="left">${c.companyName}&nbsp;</div></td>
			    <td><div align="left">${c.regAddress}&nbsp;</div></td>
			    <td>${c.fddelegate}&nbsp;</td>	    
			  </tr>
			 </#list>
		  </#if>
		<tr  nowrap="nowrap" align="middle">
			<td  colspan="4">
				<@p.navigation pagination=pagination/>
				<@fkMacros.remark />
			</td>
		</tr>
		<tr height="25" bgcolor="#D6EEF0" align="center">
            <td height="20" nowrap="nowrap" align="middle" colspan="4"></td>
            </tr>
        </tbody></table>
	</div>
	<div id="footer">
		<p>宁波市安全生产监督管理局</p><span>技术支持：</span><a href="http://www.safetys.cn/"> 安生科技</a><span id="lxdh">联系电话：0574-87364008</span>
	</div>
</div>
</body>
</html>