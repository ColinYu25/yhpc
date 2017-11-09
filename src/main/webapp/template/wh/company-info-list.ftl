<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>危险化学品企业安全生产工作年度报告表</th>
  </tr>
</table>

<div class="menu">
  	<ul>
	<li id="img_save"><a href="company_create.xhtml" class="b1"><b>添加</b></a></li>
	<li id="img_amend"><a href="#" class="b2" onClick="loadNote('company_update.xhtml?entity.id');"><b>修改</b></a></li>
	<li id="img_del"><a href="#" class="b3" onClick="del();"><b>删除</b></a></li>  	
	<li id="img_report"><a href="#" class="" onClick="report()"><b>上报</b></a></li>
	<li id="img_print"><a href="#" class="" onClick="doPrint()"><b>打印</b></a></li>	
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_companyName" width="25%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>年度</th>
    <th  id="th_regAddress" width="20%" style="cursor:hand;" onClick="orderProperty('regAddress');" nowrap>报告时间</th>
    <th  id="th_phoneCode"  width="12%"  style="cursor:hand" onClick="orderProperty('phoneCode')" nowrap>状态</th>    
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td><a href="company_view.xhtml?entity.id=${item.id}">${item.year}</a>&nbsp;</td>
	    <td>${item.reportdate?string('yyyy 年 MM 月 dd 日')} &nbsp;</td>	    
	    <td>
	    <#if item?? && item.state?? && item.state == 1>
	    已上报
	    <#else>
	    未上报
	    </#if>
	    </td>	    	    
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

</#escape>
<@fkMacros.pageFooter />
<script>
function report(){
	if (!window.confirm("确定要上报？上报后数据将不能再修改！")){
		return;
	}
	var ids = document.getElementsByName("ids");
	if (ids == null || ids.length == 0){
		alert("没有找到要上报的数据");
		return;
	}
	for(var i=0; i<ids.length; i++){
		if (ids[i].checked){
			window.location.href = "company_report.xhtml?entity.id=" + ids[i].value;
			return;
		}
	}
	alert("请选择要上报的数据");
}

function del(){
	var ids = document.getElementsByName("ids");
	if (ids == null || ids.length == 0){
		alert("没有找到要删除的数据");
		return;
	}
	for(var i=0; i<ids.length; i++){
		if (ids[i].checked){
			window.location.href = "company_delete.xhtml?entity.id=" + ids[i].value;
			return;
		}
	}
	alert("请选择要删除的数据");
}

function doPrint(){
	var ids = document.getElementsByName("ids");
	if (ids == null || ids.length == 0){
		alert("没有找到要打印的数据");
		return;
	}
	for(var i=0; i<ids.length; i++){
		if (ids[i].checked){
			window.location.href = "company_view.xhtml?entity.id=" + ids[i].value;
			return;
		}
	}
	alert("请选择要打印的数据");	
}
</script>