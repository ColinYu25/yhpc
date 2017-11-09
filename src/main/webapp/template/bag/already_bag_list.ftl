<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if year?exists>
  <#assign tp='${year}'>
</#if>
<script type="text/javascript">
var enumObj=new Enum("${enumXmlUrl}");
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>已打包列表</th>
  </tr>
</table>
<form action="loadAlreadyBags.xhtml" method="post" name="bagsForm" id="bagsForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">名称：</th>
	    <td style="width:300px;"><input type="text" name="bag.name" id="name" size="40" maxlength="50"></td>
	    <th style="width:75px;">地址：</th>
	    <td style="width:300px;"><input type="text" name="bag.regAddress" id="regAddress" size="30" maxlength="50">
	    <input type="submit" id="sub" value="查  询" onClick="get('bagsForm').submit();" style="input_submit"/>
	    </td>
	  </tr>
	</table>
</form>
<div class="menu">
  	<ul>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteBag.xhtml" method="post" name="bagForm" id="bagForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;" nowrap>序号</th>
    <th id="th_companyName" width="20%" style="cursor:hand;" nowrap>名称</th>
    <th id="th_address" width="25%" style="cursor:hand;" nowrap>地址</th>
    <th id="th_fdDelegate" width="15%" style="cursor:hand;" nowrap>类型</th>
    <th id="th_phone" width="20%" style="cursor:hand;" nowrap>负责人</th>
    <th id="th_phone" width="20%" style="cursor:hand;" nowrap>联系电话</th>
  </tr>
  <#if bags?exists>
  	<#list bags?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><a href="loadBagCompanies.xhtml?bagId=${c.id}">${c.name}</a>&nbsp;</td>
	    <td>${c.regAddress}&nbsp;</td>
	    <td><span><#if c.bagType?exists><script type="text/javascript">document.write(enumObj.getSelect("${c.bagType}"));</script></#if></span></td>
	    <td>${c.fddelegate}&nbsp;</td>
	    <td>${c.phoneCode}&nbsp;</td>
	  </tr>
	 </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<script type="text/javascript">
	
</script>
</#escape>
<@fkMacros.pageFooter />