<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if item?exists>
  	<#assign url='updateItem.xhtml'>
  <#else>
  	<#assign url='createItem.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
 		<#--<#if flag='jianwei'>
	 		if(jQuery("[name='item.secondArea']").val() == 0) {
	 			alert("请选择二级区域");
	 			return;
	 		}
 		</#if>-->
  		var obj=get("itemForm");
  		obj.action="${url}";
  		
 		obj.submit();
 	}
}
	
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
	</#if>工程项目基本情况表</strong></td>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'itemForm' />
<form id="itemForm" name="itemForm" method="post" action="">
      <table width="100%" border="0"  cellpadding="0" cellspacing="0" class="table_input">
        <tr>
          <td rowSpan="4"  style="background:#F0F0F0;color:#333;" align="center"><strong>工程项目</strong></td>
		  <td width="8%"  style="background:#F0F0F0;color:#333;" align="center"><strong>名　　称</strong></td>
          <td align="left" colspan="4">
		  <input name="item.itemname" type="text"  id="itemname" size="73" maxlength="100" value="${item.itemname}"/>
            &nbsp;<span style=color:red>*</span><ui:v for="itemname"  rule="require" empty="名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
        </tr>
		<tr>
          <td style="background:#F0F0F0;color:#333;" align="center"><strong>地　　址</strong></td>
          <td align="left" colspan="4">
		  <input name="item.itemaddress"  type="text"  id="itemaddress"  size="73" maxlength="100" value="${item.itemaddress}"/>
            &nbsp;<span style=color:red>*</span><ui:v for="itemaddress"  rule="require" empty="地址不允许为空" pass="&nbsp;" tips="&nbsp;"/></td></td>
        </tr>
		<tr><td style="background:#F0F0F0;color:#333;" align="center"><strong>建筑面积</strong></td>
		<td><input name="item.buildArea" id="buildArea" style="ime-mode:disabled"  type="text" size="30" maxlength="10" value="${item.buildArea.toString()}"/>（万平方米）&nbsp;<ui:v for="buildArea" require="false" rule="double"  warn="&nbsp;" pass="&nbsp;"></td>
		<td style="background:#F0F0F0;color:#333;" align="center"><strong>是否竣工</strong></td> 
			<td align="left" colspan="2">
		    <input type="radio" name="item.iscompleted" value="1" id="iscompleted1"/>是　　　
		   <input type="radio" name="item.iscompleted" value="0" id="iscompleted2"  checked="checked"/>否</td></tr>
		<tr>
          <td style="background:#F0F0F0;color:#333;" align="center"><strong>区　　域</strong></td>
          <td colspan="4"><div id="div-area"></div></td> 
        </tr>
		<tr>
		<th colspan="2" align="center">建设单位名称</strong></td>
		<td colspan="4">
		<textarea name="item.ownername" cols="70" rows="4" id="ownername">${item.ownername}</textarea>
        &nbsp;<span style=color:red>*</span><ui:v for="ownername"  rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
		</tr>				
		<tr>
          <td width="10%" rowSpan="3"  style="background:#F0F0F0;color:#333;" align="center"><strong>总承包单位</strong></td>
		  <td style="background:#F0F0F0;color:#333;" align="center"><strong>名　　称</strong></td>
          <td align="left" colspan="4">
		  <input name="item.zcbName"  type="text" id="zcbName" size="73" maxlength="100" value="${item.zcbName}"/>
            &nbsp;<span style=color:red>*</span><ui:v for="zcbName"  rule="require" empty="名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
        </tr>
		<tr>
          <td style="background:#F0F0F0;color:#333;" align="center"><strong>注册地址</strong></td>
          <td align="left" colspan="4">
		  <input name="item.zcbAdd"  type="text" id="zcbAdd" size="73" maxlength="100"  value="${item.zcbAdd}"/>
            &nbsp;<span style=color:red>*</span><ui:v for="zcbAdd"  rule="require" empty="注册地址不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
        </tr>
		<tr>
          <td style="background:#F0F0F0;color:#333;" align="center"><strong>联 系 人</strong></td>
		  <td  align="left">
		  <input name="item.linkman" type="text" class="input" maxlength="20" size="30" value="${item.linkman}"/>　</td>
		  <td style="background:#F0F0F0;color:#333;" align="center"><strong>联系电话</strong></td>
          <td align="left" colspan="2">
		  <input name="item.tel" id="tel" type="text" class="input" maxlength="13" style="ime-mode:disabled" size="30" value="${item.tel}" />　<ui:v for="tel" rule="phone_mobile" require="false" warn="电话正确格式如0574-87364008。" pass="&nbsp;"/></td>
        </tr>
</table>
<input type="hidden" name="item.id" id="id" value="${item.id}"/>
 </form>
 <script type="text/javascript">
 	<#if item?exists>
 		<#if item.iscompleted='1'>
 			get("iscompleted1").checked=true;
 		</#if>
 	</#if>
 </script>
<@fkMacros.muilt_select_js />
<#if item?has_content>
<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "item."/>
</#if>
</#escape>
<@fkMacros.pageFooter />