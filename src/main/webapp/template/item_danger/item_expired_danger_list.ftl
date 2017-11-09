<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script type="text/javascript">
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>
	<#if flag='jianwei'>
		房屋建筑
	<#elseif flag='jiaotong'>
		交通
	<#elseif flag='shuili'>
		水利
	<#elseif flag='chengguan'>
		市政
	</#if>工程项目到期未整改隐患列表</th>
  </tr>
</table>
<form action="loadItemExpiredDangers.xhtml" method="post" name="itemsForm" id="itemsForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">项目名称：</th>
	    <td style="width:300px;"><input type="text" name="itemDanger.daItem.itemname" id="itemname" size="40" maxlength="50">
	    </td>
	    <th>完成时间</th>
	    <td><input type="text" id="finishDate" name="itemDanger.finishDate" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',errDealMode:1})"/>
	    	<input type="submit" id="sub" value="搜　索" onClick="get('itemsForm').submit();" style="input_submit"/>
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
<form action="deleteItemDanger.xhtml" method="post" name="itemDangerForm" id="itemDangerForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;">序号</th>
    <th id="th_companyName" width="15%" style="cursor:hand;">项目名称</th>
    <th id="th_companyName" width="15%" style="cursor:hand;">项目地址</th>
    <th id="th_companyName" width="15%" style="cursor:hand;">整治责任单位</th>
    <th id="th_address" width="15%" style="cursor:hand;">整治责任单位负责人</th>
    <th id="th_address" width="15%" style="cursor:hand;">计划完成时间</th>
  </tr>
  <#if itemDangers?exists>
  	<#list itemDangers?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="loadItemDanger.xhtml?itemDanger.id=${c.id}">${c.daItem.itemname}</a></div></td>
	    <td><div align="left">${c.daItem.itemaddress}&nbsp;</div></td>
	    <td><div align="left">${c.zzCompany}&nbsp;</div></td>
	    <td><div align="center">${c.zzChargeman}&nbsp;</div></td>
	    <td><div align="center">${c.finishDate.toString().substring(0,10)}&nbsp;</div></td>
	  </tr>
	 </#list>
  </#if>
  <input type="hidden" name="itemDanger.daItem.id" value="${item.id}"/>
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