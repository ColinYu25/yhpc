<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
function checkInput(){
	 //if(formValidator.validate()){
	          var oldUserName=document.getElementById("oldUserName").value;
	          var userName=document.getElementById("userName").value;
	          if(userName==''){
	            alert("新用户名不能为空！");
	            return false;
	          }
	          if(userName==oldUserName){
	            alert("新用户名和原用户名相同，不用修改！");
	            return false;
	          }else{
	            var obj=document.getElementById("userform");
      		    obj.action="updateCompanyUserName.xhtml";
	 		    obj.submit();
	          }
	          
	  		
	  //}
}

</script>
<@fkMacros.formValidator 'userform' />
<body>
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong>修改用户名</strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">

    <tr>
      <th width="15%">原用户名</th>
      <td colspan="4"  width="85%">${fkUser.userName}</td>
    </tr>
    
    <tr>
      <th width="15%">新用户名</th>
      <td colspan="4"  width="85%">
      <#if company?exists&&company.regNum?exists&&company.regNum!=''>
         <input id="userName" name="fkUser.userName" value="${company.regNum}" type="text" class="input" size="23"/>
      <#elseif company?exists&&company.setupNumber?exists&&company.setupNumber!=''>
         <input id="userName" name="fkUser.userName" value="${company.setupNumber}" type="text" class="input" size="23"/>
      <#else>
         <input id="userName" name="fkUser.userName" type="text" class="input" size="23"/>
      </#if>
      
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请使用本单位统一社会信用代码（工商注册号）进行注册，无统一社会信用代码（工商注册号），用组织机构代码
      <ui:v for="userName"  rule="require" empty="用户名不允许为空" pass="&nbsp" warn="&nbsp;"/>
      </td>
    </tr>
	<tr>
      <th colspan="5"><div  align="center"><input name="to_save" id="to_save" type="button" class="btn_save" value="修  改" onclick="javascript:checkInput();" /></div>
      </th>
    </tr>
     <input type="hidden" name="fkUser.id" value="${fkUser.id}" />
     <input type="hidden" name="oldUserName" id="oldUserName" value="${fkUser.userName}" />
    </form>
  </table>
 </body>
</#escape> 
<@fkMacros.pageFooter />