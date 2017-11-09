<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
function checkInput(){
	  //if(formValidator.validate()){
	  
	  
	          var oldUserName=document.getElementById("oldUserName").value;
	          var userName=document.getElementById("userName").value;
	          
	          var userPass=document.getElementById("userPass").value;
	          var confirmpass=document.getElementById("confirmpass").value;
	          
	          
	          if(userName==''){
	            alert("新用户名不能为空！");
	            return false;
	          }
	          
	          
	          if(userPass==''){
	            alert("新密码不能为空！");
	            return false;
	          }
	          
	          if(confirmpass==''){
	            alert("确认密码不能为空！");
	            return false;
	          }
	          
	          
	          if(userPass!=confirmpass){
	            alert("新密码和确认密码不相同，请重新输入！");
	            return false;
	          }
	          
	  		  var obj=document.getElementById("userform");
      		  obj.action="updateCompanyPassword.xhtml";
	 		  obj.submit();
	  //}
}
</script>
<@fkMacros.formValidator 'userform' />
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong>修改账号信息</strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">
    <tr>
      <th width="15%">原用户名</th>
      <td colspan="4">${fkUser.userName}</td>
    </tr>
    <tr>
      <th width="15%">新用户名</th>
      <td colspan="4">
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
      <th width="15%">新密码</th>
      <td colspan="4"><input id="userPass" name="fkUser.userPass" type="password" class="input" size="23" maxlength="30" rule="password" level="2" empty="密码不允许为空" tips="请输入6~16位数字、字母及特殊字符的混合字符" warn="密码安全度太低" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th width="15%">确认密码</th>
      <td colspan="4"><input name="confirmpass" type="password" class="input" id="confirmpass" size="23" maxlength="30" rule="repeat"   to="userPass" empty="请确认您的新密码" tips="请重复输入一次您的新密码" warn="两次输入的密码不一致" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th colspan="5"><div  align="center"><input name="to_save" id="to_save" type="button" class="btn_save" value="修  改" onclick="javascript:checkInput();" /></div>
      </th>
    </tr>
      <input type="hidden" name="fkUser.id" value="${fkUser.id}" />
      <input type="hidden" name="oldUserName" id="oldUserName" value="${fkUser.userName}" />
    </form>
  </table>
</#escape> 
<@fkMacros.pageFooter />