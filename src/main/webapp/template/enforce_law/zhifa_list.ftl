<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>执法列表</th>
  </tr>
</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="loadEnforceLaws.xhtml" method="post" name="enforceLawForm" id="enforceLawForm">
	  <tr>
	    <th>填报单位</th>
	    <td width="20%"><input type="text" name="daZhifaReport.unit" id="unit" value="${daZhifaReport.unit}" size="20" maxlength="50"></td>
	    <th>区　　域</th>
	    <td width="20%">
		    <select id="areaCode" name="daZhifaReport.areaCode">
		    <option value="0">-----请选择-----</option>
		    	<#list areas?if_exists as area>
		    		<option value="${area.areaCode}">${area.areaName}</option>
		    	</#list>
		    </select>
	    </td>
	    <th>填报月份</th>
	    <td width="20%"><input type="text" value="${daZhifaReport.writtenMonth}" id="writtenMonth" onfocus="WdatePicker({dateFmt:'yyyy-MM'});" name="daZhifaReport.writtenMonth" class="Wdate" size="10"/></td>
	  </tr>
		</form>
	  <tr>
<th colspan="6"><div align="center"><input type="button" id="sub" value="搜　索"  class="confirm_but" style="height:25px; width:80px"  onClick="submitForm('enforceLawForm');"/></div></th></tr>
	</table>

<div class="menu">
  	<ul>
  	<li id="img_save"><a href="createEnforceLawInit.xhtml" class="b1"><b>添加</b></a></li>
	<li id="img_amend"><a href="#" class="b2" onClick="loadNote('loadEnforceLaw.xhtml?daZhifaReport.id');"><b>修改</b></a></li>
  	<li id="img_lookup"><a href="#" class="b13" onClick="loadNote('deleteEnforceLaw.xhtml?daZhifaReport.id');"><b>删除</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="" method="post" name="enforceLawsForm" id="enforceLawsForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_auditor" width="30%" style="cursor:hand;" onClick="orderProperty('auditor');" nowrap>填报单位</th>
    <th  id="th_informanter" width="15%" style="cursor:hand;" onClick="orderProperty('informanter');" nowrap>填报人</th>
    <th  id="th_areaCode" width="15%" style="cursor:hand;" onClick="orderProperty('areaCode');" nowrap>区域</th>
    <th  id="th_phone" width="20%" style="cursor:hand;" onClick="orderProperty('phone');" nowrap>填报月份</th>
    <th  id="th_writtenDate" style="cursor:hand" onClick="orderProperty('writtenDate')" nowrap>填报日期</th>
  </tr>
  <#if daZhifaReports?exists>
  	<#list daZhifaReports?if_exists as d>
	  <tr>
	  	<td><input id="ids${d.id}" name="ids" type="checkbox" value="${d.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+d_index+1}<#else>${d_index+1}</#if></td>
	    <td><div align="left">${d.unit}</div></td>
	    <td>${d.informanter}&nbsp;</td>
	    <td>${d.areaName}&nbsp;</td>
	    <td>${d.writtenMonth}&nbsp;</td>	    
	    <td>${d.writtenDate[0..10]}&nbsp;</td>
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
<#if daZhifaReport?exists && daZhifaReport.areaCode?exists>
	get("areaCode").value="${daZhifaReport.areaCode}";
</#if>
function submitForm(formName){
	var formObj=get(formName);
	formObj.submit();
}
</script>
</#escape>
<@fkMacros.pageFooter />