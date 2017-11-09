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
	</#if>工程项目重大事故隐患列表</th>
  </tr>
</table>
<form action="loadItemDangers.xhtml" method="post" name="itemDangersForm" id="itemDangersForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th>项目名称：</th>
	    <td><input type="text" name="itemDanger.daItem.itemname" id="itemname" size="40" maxlength="50">
	    <input type="submit" id="sub" value="搜　索" onClick="get('itemDangersForm').submit();" style="input_submit"/>
	    </td>
	  </tr>
	  <input type="hidden" name="itemDanger.daItem.id" value="${item.id}"/>
	  <input type="hidden" name="item.id" value="${item.id}"/>
	  <input type="hidden" name="itemDanger.govern" value="${itemDanger.govern}"/>
	</table>
</form>
<div class="menu">
  	<ul>
  	<li id="img_save">		<a href="createItemDangerInit.xhtml?item.id=${item.id}" class="b1"><b>添加</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('loadItemDanger.xhtml?itemDanger.id');"><b>修改</b></a></li>
	<li id="img_del">		<a href="#" class="b3" onClick="deleteNote(get('itemDangerForm'))"><b>删除</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="addItemDangerGover()"><b>治理</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteItemDanger.xhtml" method="post" name="itemDangerForm" id="itemDangerForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;">序号</th>
    <th id="th_companyName" width="20%" style="cursor:hand;">项目名称</th>
    <th id="th_companyName" width="20%" style="cursor:hand;">项目地址</th>
    <th id="th_companyName" width="20%" style="cursor:hand;">整治责任单位</th>
    <th id="th_address" width="25%" style="cursor:hand;">整治责任单位负责人</th>
    <th id="th_address" width="10%" style="cursor:hand;">是否治理</th>
  </tr>
  <#if itemDangers?exists>
  	<#list itemDangers?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left">${c.daItem.itemname}&nbsp;</div></td>
	    <td><div align="left">${c.daItem.itemaddress}&nbsp;</div></td>
	    <td><div align="left">${c.zzCompany}&nbsp;</div></td>
	    <td><div align="center">${c.zzChargeman}&nbsp;</div></td>
	    <td><div align="center">
	    <#if !c.govern?exists>否<#else><#if c.govern?if_exists==1>是<#else>否</#if></#if>&nbsp;</div></td>
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

  function addItemDangerGover(){
  	var j=0;
  	var ids=document.getElementsByName("ids");
  		for(var i=0;i<ids.length;i++){
			if(ids[i].checked==true){
					j=j+1;
				}
		}
		if(j>1 || j==0){
			alert("请选择一个复选框");
			return;
		}else if(j==1){
			var id = getCheckBoxValue();
			location.href="../itemDangerGover/createItemDangerGoverInit.xhtml?itemDanger.id="+id;
		}
	}
</script>
</#escape>
<@fkMacros.pageFooter />