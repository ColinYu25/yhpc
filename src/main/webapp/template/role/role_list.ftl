<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
	function del(chkId){
		var objForm=document.roleForm;
		if(confirm(' 确认要删除吗？')){
			objForm.action="deleteRoles.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();		
		}
	}
	function modify(chkId){
			var objForm=document.roleForm;
			objForm.action="updateRoleInit.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();	
	}
	function add(chkId){
			var objForm=document.roleForm;
			objForm.action="createRoleInit.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();	
	}
	function permissionSet(chkId){
			var objForm=document.roleForm;
			objForm.action="updateRoleResourceInit.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();		
	}

</script>
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_title">
  <tr>
    <td height="27" class="text_blue_12"><a href="#" class="blue_12">帮助</a> </td>
  </tr>  
</table>

<table width="99%" cellpadding="0" cellspacing="1" class="table_list">
 <form name="roleForm" action="" method="post">
  <tr>
    <th width="3%"><input type="checkbox" onclick="javascript:selectAllOrNo(this,this.form.elements['ids']);"/></th>
    <th width="5%">序号</th>
    <th width="10%">所属站点</th>
    <th width="22%">角色名称</th>
    <th width="27%">角色描述</th>
    <th width="14%">更新日期</th>
    <th width="18%">操作</th>
  </tr>
  <!-- 不是本站的角色没有对应的权限设置,fkSite.canDelete==false-->
  <#if fkRoles?exists>
  <#list fkRoles?if_exists as m>
    <tr>
      <td align="center"><input type="checkbox" id="ids${m.id}" value="${m.id}" name="ids"/></td>
      <td>${m_index+1}</td>
      <td>${m.fkSite.siteName}</td>
      <td><div align="left"><#if m_index!=0>${m.nbsp}└&nbsp;&nbsp;${m.roleName}<#else>${m.roleName}</#if></div></td>
      <td><div align="left">${m.roleDepic}</div></td>
      <td>${m.modifyTime}</td>
      <td><div align="center">
      <a title="修改本级角色内容" href="javascript:modify('ids${m.id}');">修改</a>
      <a title="添加本级下的子角色" href="javascript:add('ids${m.id}');">添加</a>
      <#if m.canDelete==true><a title="删除本级角色" href="javascript:del('ids${m.id}');">删除</a></#if>
      <#if m.fkSite.canDelete==false><a title="设置本级角色对应的操作权限" href="javascript:permissionSet('ids${m.id}');">权限</a></#if>
      </div>
      </td>
    </tr>
  </#list>
  <#else>
    <tr>
      <td colspan="7"  align="center">你还没有添加任何角色！</td>
    </tr>
  </#if> 
  <tr>
      <td colspan="7">&nbsp;</td>
    </tr>     
  </form>
</table>
<script language="javascript">
window.onload=function (){
        var ids=document.getElementsByName("ids")
        if(ids!=null){
        	var len=ids.length;
        	if(len!=null){
        		for(var i=0;i<len;i++){
        			ids[i].checked=false;
        		}
        	}else{
        		ids.checked=false;
        	}
        }
}
</script>
</#escape> 
<@fkMacros.pageFooter />
