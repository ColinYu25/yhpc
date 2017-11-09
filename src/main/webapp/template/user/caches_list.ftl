<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>缓存列表</th>
  </tr>
</table>
  <form action="loadCompaniesAffirm.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th>缓存名称</th>
	    <td width="35%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="39" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="submitForm('companyForm');" /></div></th>
	  </tr>
	</table>
	</form>
<!--<div class="menu">
  	<ul>
	<li id="img_del"><a href="#" class="b3" onClick="loadNote('delCompanyHy.xhtml?company.id');"><b>删除</b></a></li>
	</ul>
</div>-->
<form action="" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th id="th_id" width="4%"  nowrap>序号</th>
    <th id="th_companyName" width="25%" style="cursor:pointer;" onClick="orderProperty('companyName');" nowrap><b>单位名称</b></th>
    <th width="12%" nowrap>清除缓存</th>
  </tr>
  <#if caches?exists>
  	<#list caches?if_exists as c>
	  <tr> 
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left">${c.name}</td>
	  	<td>
	  	<a  href="javascript:if(confirm('确定清除吗?')){reset('${c.name}');}" ><font color=red>清除</font></a>
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
<script type="text/javascript">

function reset(name){
	var url = "delCaches.xhtml?cache.name="+name;
			jQuery.ajax({
			   type: "POST",
			   url: url,
			   dataType:"text",
			   data: "",
			   success: function(msg){
					 if(msg=='success'){
			       		 alert("清除成功");
			         }else{
			          	 alert("清除失败");
			         }
			   }
	});	
}

</script>
</#escape>
<@fkMacros.pageFooter />