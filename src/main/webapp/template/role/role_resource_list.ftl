<@fkMacros.pageHeader />
<#escape x as (x)!> 
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>${fkRole.roleName}</th>
  </tr>
</table>
<table width="99%" cellpadding="0" cellspacing="1" class="table_list">
  <form action="updateRoleResource.xhtml" method="post">
  <tr>
    <th>序号</th>
    <th>资源名称</th>
    <th>显示菜单</th>
    <th>资源描述</th>
    <th><input type="checkbox" onclick="javascript:selectAllOrNo(this,this.form.elements['resourceIds']);"/></th>
  </tr>
  <#if fkResources?exists>
  <#list fkResources as p>
  <#if p.deleted==false>
	  <#assign checked=''>
	  <#list fkRole?if_exists.fkResources?if_exists as c>
	  	<#if c.id=p.id>
	    	<#assign checked='checked="checked"'>
	    </#if>
	  </#list>    
	    <tr>
	      <td>${p_index+1}</td>
	      <td><div align="left">${p.nbsp}${p.resourceName}</div></td>
	      <td><#if p.menu==true>是<#else>否</#if></td>
	      <td><div align="left">${p.resourceDepic}&nbsp;</div></td>
	      <td align="center"><input type="checkbox" id="resourceIds" value="${p.id}" name="resourceIds" ${checked}  /></td>
	    </tr>
    </#if>
  </#list>
  </#if>
    <tr>
    	<td colspan="4" align="center" class="text_h"><input type="submit" name="save" value="提   交"  /></td>
    </tr>
    <input type="hidden" name="fkRole.id" value="${id}" />
   </form>
</table>
</#escape> 
<@fkMacros.pageFooter />
