<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
function checkInput(){
	  if(formValidator.validate()){
	  		  var obj=document.getElementById("userform");
      		  obj.action="updatePassword2.xhtml";
	 		  obj.submit();
	  }
}
</script>
<@fkMacros.formValidator 'userform' />
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong>修改密码</strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">
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
    <input type="hidden" name="fkUser.id" value="${id}" />
    </form>
  </table>
</#escape> 
<@fkMacros.pageFooter />