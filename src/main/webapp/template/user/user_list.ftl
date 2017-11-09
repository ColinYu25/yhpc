<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
	function displaySearch(){
		var style=$('searchTable').style;
		if(style.display=="none"){
		    style.display="";		
		}else{
			style.display="none";		
		}
	}
	function saveStyle(){
		$('searchTable').style.display=arguments[0];
	}
	function del(chkId){
		var objForm=document.userForm;
		if(confirm('如果删除此用户，与之相关联的数据同时丢失。你确认要删除此用户吗？')){
			objForm.action="deleteUsers.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();		
		}
	}
	function modifyInfo(chkId){
			var objForm=document.userForm;
			objForm.action="updateUserInit.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();	
	}
	function modifyPass(chkId){
			var objForm=document.userForm;
			objForm.action="updatePasswordInit.xhtml";
			document.getElementById(chkId).checked=true;
			objForm.submit();	
	}
</script>
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_title">
  <tr>
    <td height="27" class="text_blue_12"><a href="javascript:displaySearch();" class="blue_12">查询</a>   |   <a href="createUserInit.xhtml" class="blue_12">添加用户</a>   |   <a href="#" class="blue_12">帮助</a> </td>
  </tr>  
  <!--tr>
    <td height="27" class="text_blue_12"><a href="javascript:void(0)" onClick="javascript:displayTable(searchTable);" class="blue_12">显示查询</a>   |   <a href="createUserInit.xhtml" class="blue_12">添加用户</a>   |   <a href="#" class="blue_12">统计</a>   |   <a href="#" class="blue_12">导出</a>   |   <a href="#" class="blue_12">打印</a>    |   <a href="#" class="blue_12">布局</a>    |   <a href="#" class="blue_12">帮助</a> </td>
  </tr-->  
</table>
  <table id="searchTable"  width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form name="searchForm" action="loadUsers.xhtml" method="post" id="searchForm">
    <tr>
      <th width="110">所在地区</th>
      <td><div id="div-area"></div></td>
      <th>所属行业</th>
      <td><select id="userIndustry" name="fkUserInfo.userIndustry" style="width:170px">
          <option value="">&nbsp;&nbsp;--&nbsp;&nbsp;请选择&nbsp;&nbsp;--&nbsp;&nbsp;</option>
          <option value="anwei">安委</option>
          <option value="fagai">发改委</option>
          <option value="jiaoyu">教育局</option>
          <option value="jiaojing">交警</option>
          <option value="gongan">公安局</option>
          <option value="minzong">民宗</option> 
          <option value="jianwei">建委</option>
          <option value="chengguan">城管局</option>
          <option value="jiaotong">交通局</option> 
          <option value="shuili">水利</option>
          <option value="maoyi">商务</option>
          <option value="wenguang">文广局</option>
          <option value="weisheng">卫生局</option>
          <option value="haiyang">海洋渔业</option>
          <option value="lvyou">旅游局</option>
          <option value="zhijian">质监</option>
          <option value="anjian">安监局</option>
          <option value="nongji">农机</option>
          <option value="haishi">海事</option>
          <option value="dianli">电力</option>
          <option value="qiye">企业</option>
         
         
        
          
          
          
          
          
          
        
         
          
          
        
        </select></td>
    </tr>
    <tr>
      <th>用户名</th>
      <td><input name="fkUser.userName" value="${fkUser.userName}" type="text" class="input" size="23" maxlength="15" ></td>
      <th>真实姓名</th>
      <td><input name="fkUserInfo.factName" value="${fkUserInfo.factName}" type="text" class="input" size="23" maxlength="15"></td>
    </tr>
    <tr>
      <th>单位名称</th>
      <td><input name="fkUserInfo.userCompany" value="${fkUserInfo.userCompany}" type="text" class="input" size="23" maxlength="15" ></td>
      <th>注册日期</th>
      <td><input name="fkUserInfo.createTime" value="${fkUserInfo.createTime?date}" type="text" class="input" size="23" maxlength="30"></td>
	  <!--th>所属角色</th>
      <td><select name="roleIds" class="input">
          <option value="0">--请选择--</option>
          <if fkRoles?exists>
           <list fkRoles?if_exists as m>    
            <option value="{m.id}">{m.roleName}</option>
           <list>
          <if>
        </select></td-->
    </tr>
    <tr>
      <th colspan="4"><div align="center"><input type="button" value="查　询" class="confirm_but" style="height:25px; width:80px" onClick="javascript:submitForm('searchForm');"/></div></th>
    </tr>
	</form>
  </table>

<table width="99%" cellpadding="0" cellspacing="1" class="table_list">
 <form name="userForm" action="" method="post">
  <tr>
    <th width="4%"><input type="checkbox" onclick="javascript:selectAllOrNo(this,this.form.elements['ids']);" /></th>
    <th width="5%">序号</th>
    <th width="10%">用户名</th>
    <th width="10%">真实姓名</th>
    <th width="28%">所属单位</th>
    <th width="15%">注册日期</th>
    <th width="18%">操作</th>
  </tr>
  <#if fkUsers?exists>
  <#list fkUsers?if_exists as m>    
    <tr>
      <td align="center"><input type="checkbox" id="ids${m.id}" value="${m.id}" name="ids"/></td>
      <td>${pagination.itemCount+m_index+1}</td>
      <td>${m.userName}</td>
      <td>${m.fkUserInfo.factName}</td>
      <td><div align="left">${m.fkUserInfo.userCompany}</div></td>
      <td>${m.createTime}&nbsp;</td>
      <td>
      <a href="javascript:modifyInfo('ids${m.id}');">修改信息</a>
      <a href="javascript:modifyPass('ids${m.id}');">修改密码</a>
      <a href="javascript:del('ids${m.id}');">删除</a>
      </td>
    </tr>
  </#list>
  <#else>
    <tr>
      <td colspan="10"  align="center">此列表暂无数据！</td>
    </tr>
  </#if>   
  </form>
</table>
<table width="99%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>

<script language="javascript">
function submitForm(formName){
	var formObj=$(formName);
	formObj.submit();
}
<#if fkUserInfo?if_exists.userIndustry?exists>
	$('userIndustry').value='${fkUserInfo.userIndustry}';
</#if>
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
<@fkMacros.muilt_select_js />
<#if fkUserInfo?has_content>
	<@fkMacros.selectAreas_fun "${fkUserInfo?if_exists.firstArea?if_exists}" "${fkUserInfo?if_exists.secondArea?if_exists}" "${fkUserInfo?if_exists.thirdArea?if_exists}" "${fkUserInfo?if_exists.fouthArea?if_exists}" "${fkUserInfo?if_exists.fifthArea?if_exists}" "fkUserInfo."/>
<#else>
	<@fkMacros.selectAreas_fun "${userDetail.firstArea}" "${userDetail.secondArea}" "${userDetail.thirdArea}" "${userDetail.fouthArea}" "${userDetail.fifthArea}" "fkUserInfo."/>
</#if>
</#escape> 
<@fkMacros.pageFooter />
