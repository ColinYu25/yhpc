<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script type="text/javascript">
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if flag='jianwei'>
		房屋建筑
	<#elseif flag='jiaotong'>
		交通
	<#elseif flag='shuili'>
		水利
	<#elseif flag='chengguan'>
		市政
	</#if>工程施工重大事故隐患未治理列表</th>
  </tr>
</table>
<form action="loadUnGoverItems.xhtml" method="post" name="itemsForm" id="itemsForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">项目名称：</th>
	    <td style="width:300px;"><input type="text" name="item.itemname" id="itemname" size="40" maxlength="50"></td>
	    <th style="width:75px;">项目地址：</th>
	    <td style="width:300px;"><input type="text" name="item.itemaddress" id="itemaddress" size="50" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th style="width:75px;">区域：</th>
	    <td><div id="div-area"></div></td>
	    <th style="width:75px;">是否竣工：</th>
	    <td>
	    <input type="radio" name="item.iscompleted" value="1" />是　　　
		<input type="radio" name="item.iscompleted" value="0"  checked="checked"/>否
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
<form action="deleteItem.xhtml" method="post" name="itemForm" id="itemForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;" nowrap>序号</th>
    <th id="th_companyName" width="20%" style="cursor:hand;" nowrap>项目名称</th>
    <th id="th_address" width="23%" style="cursor:hand;" nowrap>项目地址</th>
    <th id="th_fdDelegate" width="13%" style="cursor:hand;" nowrap>总承包单位</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" nowrap>未治理重大隐患</th>
  </tr>
  <#if items?exists>
  	<#list items?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left">${c.itemname}</div></td>
	    <td><div align="left">${c.itemaddress}&nbsp;</div></td>
	    <td><div align="left">${c.zcbName}&nbsp;</div></td>
	    <td><a href="../itemDanger/loadItemDangers.xhtml?itemDanger.govern=0&item.id=${c.id}&itemDanger.daItem.id=${c.id}">${c.unGoverDangernNum}</a></td>
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
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "item."/>
</#if>
</#escape>
<@fkMacros.pageFooter />