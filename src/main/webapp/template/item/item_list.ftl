<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if year?exists>
  <#assign tp='${year}'>
</#if>
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
	</#if>工程项目列表</th>
  </tr>
</table>
<form action="loadItems.xhtml" method="post" name="itemsForm" id="itemsForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">项目名称</th>
	    <td style="width:300px;"><input type="text" name="item.itemname" id="itemname" value="${item.itemname}" size="40" maxlength="50"></td>
	    <th style="width:75px;">项目地址</th>
	    <td style="width:300px;"><input type="text" name="item.itemaddress" id="itemaddress" value="${item.itemaddress}" size="50" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th style="width:75px;">区　　域</th>
	    <td><#if flag='jianwei'><input type="checkbox" name="item.self" value="1" <#if item?? && item.self?? && item.self=1>checked</#if>/> 市本级 </#if><div id="div-area"></div></td>
	   <th style="width:75px;">是否竣工：</th>
	    <td>
	    <input type="radio" name="item.iscompleted" id="tiscompleted" value="1" />是　　　
		<input type="radio" name="item.iscompleted" value="0"  id="fiscompleted" checked="checked"/>否
	    <input type="submit" id="sub" value="搜　索" style="input_submit"/>
	  	</td>
	  </tr>
	 
	</table>
</form>
<div class="menu">
  	<ul>
  	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('loadItem.xhtml?item.id');"><b>修改</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteItem.xhtml" method="post" name="itemForm" id="itemForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%" rowspan="2"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;" rowspan="2" nowrap>序号</th>
    <th id="th_companyName" width="20%" style="cursor:hand;" rowspan="2" nowrap>项目名称</th>
    <th id="th_address" width="25%" style="cursor:hand;" rowspan="2" nowrap>项目地址</th>
    <th id="th_fdDelegate" width="15%" style="cursor:hand;" rowspan="2" nowrap>总承包单位</th>
    <th id="th_phone" width="20%" style="cursor:hand;" colspan="4" nowrap>${year}年季报</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" rowspan="2" nowrap>重大隐患</th>
  </tr>
  <tr>
      <th width="5%">1</th>
      <th width="5%">2</th>
      <th width="5%">3</th>
      <th width="5%">4</th>
  </tr>
  <#if items?exists>
  	<#list items?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left">${c.itemname}</div></td>
	    <td><div align="left">${c.itemaddress}&nbsp;</div></td>
	    <td><div align="left">${c.zcbName}&nbsp;</div></td>
	    <#if jd == '1'>
	    <td style="cursor:hand;" id="tp1${c.id}" onClick="createReport(${c.id}, '1', <#if c.sp1?? && c.sp1>${c.sp1_id}<#else>''</#if>, <#if c.sp1?? && c.sp1>true<#else>false</#if>);"><#if c.sp1?? && c.sp1>修改<#else>添加</#if></td>
	    <td>添加</td>
	    <td>添加</td>
	    <td>添加</td>
	    <#elseif jd == '2'>
	     <td style="cursor:hand;" id="tp1${c.id}" onClick="createReport(${c.id}, '2', <#if c.sp1?? && c.sp1>${c.sp1_id}<#else>''</#if>, <#if c.sp1?? && c.sp1>true<#else>false</#if>);"><#if c.sp1?? && c.sp1>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp2${c.id}" onClick="createReport(${c.id}, '2', <#if c.sp2?? && c.sp2>${c.sp2_id}<#else>''</#if>, <#if c.sp2?? && c.sp2>true<#else>false</#if>);"><#if c.sp2?? && c.sp2>修改<#else>添加</#if></td>
	    <td>添加</td>
	    <td>添加</td>
	    <#elseif jd == '3'>
	    <td style="cursor:hand;" id="tp1${c.id}" onClick="createReport(${c.id}, '3', <#if c.sp1?? && c.sp1>${c.sp1_id}<#else>''</#if>, <#if c.sp1?? && c.sp1>true<#else>false</#if>);"><#if c.sp1?? && c.sp1>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp2${c.id}" onClick="createReport(${c.id}, '3', <#if c.sp2?? && c.sp2>${c.sp2_id}<#else>''</#if>, <#if c.sp2?? && c.sp2>true<#else>false</#if>);"><#if c.sp2?? && c.sp2>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp3${c.id}" onClick="createReport(${c.id}, '3', <#if c.sp3?? && c.sp3>${c.sp3_id}<#else>''</#if>, <#if c.sp3?? && c.sp3>true<#else>false</#if>);"><#if c.sp3?? && c.sp3>修改<#else>添加</#if></td>
	    <td>添加</td>
	    <#elseif jd == '4'>
	    <td style="cursor:hand;" id="tp1${c.id}" onClick="createReport(${c.id}, '4', <#if c.sp1?? && c.sp1>${c.sp1_id}<#else>''</#if>, <#if c.sp1?? && c.sp1>true<#else>false</#if>);"><#if c.sp1?? && c.sp1>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp2${c.id}" onClick="createReport(${c.id}, '4', <#if c.sp2?? && c.sp2>${c.sp2_id}<#else>''</#if>, <#if c.sp2?? && c.sp2>true<#else>false</#if>);"><#if c.sp2?? && c.sp2>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp3${c.id}" onClick="createReport(${c.id}, '4', <#if c.sp3?? && c.sp3>${c.sp3_id}<#else>''</#if>, <#if c.sp3?? && c.sp3>true<#else>false</#if>);"><#if c.sp3?? && c.sp3>修改<#else>添加</#if></td>
	    <td style="cursor:hand;" id="tp4${c.id}" onClick="createReport(${c.id}, '4', <#if c.sp4?? && c.sp4>${c.sp4_id}<#else>''</#if>, <#if c.sp4?? && c.sp4>true<#else>false</#if>);"><#if c.sp4?? && c.sp4>修改<#else>添加</#if></td>
	    </#if>
	    <td><a id="tp5${c.id}" href=""></a>&nbsp;<a href="../itemDanger/createItemDangerInit.xhtml?item.id=${c.id}">添加</a></td>
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
<@fkMacros.muilt_select_js />
<#if item?has_content>
	<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
<#else>
	<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "item."/>
</#if>
<script type="text/javascript">
	<#if items?exists>
  		<#list items?if_exists as c>
  			<#list c.daItemDangers?if_exists as a>
  				<#assign num=c.daItemDangers?size>
  				<#if num !=0 >
  					get("tp5${c.id}").href="../itemDanger/loadItemDangers.xhtml?itemDanger.govern=2&item.id=${c.id}&itemDanger.daItem.id=${c.id}";
  					get("tp5${c.id}").innerHTML=${num};
  				</#if>
  			</#list>
  		</#list>
    </#if>
    <#if item?exists>
    	<#if item?if_exists.iscompleted?if_exists='1'>
    		get("tiscompleted").checked=true;
    	</#if>
    </#if>
    
    //按年统计
	function createReport(item_id, id, sp_id, flag){
		var url = "";
		if(flag){
			url = "../itemSeasonReport/createItemSeasonReportInit.xhtml?item.id=" + item_id + "&itemSeasonReport.id=" + sp_id;
		}else{
			url = "../itemSeasonReport/createItemSeasonReportInit.xhtml?item.id=" + item_id + "&type=" + id;
		}
		window.location = url;
	}
</script>
</#escape>
<@fkMacros.pageFooter />