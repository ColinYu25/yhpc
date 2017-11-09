<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
function submitCreate(is_create){
	  if(formValidator.validate()){
	  		  var obj=document.getElementById("userform");
      		  obj.action="updatePerson.xhtml";
	 		  obj.submit();
	  }
}
</script>
<@fkMacros.formValidator 'userform' />
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong>注册资料</strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">
    <tr>
      <th width="15%">用户名</th>
      <td colspan="4"  width="85%">${fkUser.userName}</td>
    </tr>
    <tr>
      <th>真实姓名</th>
      <td colspan="4"><input name="fkUserInfo.factName" value="${fkUser.fkUserInfo.factName}" type="text" class="input" size="23" maxlength="20" rule="chinese" empty="姓名不允许为空" tips="" warn="您输入的姓名不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>出生日期</th>
      <td colspan="4"><input name="fkUserInfo.bornDate" value="${fkUser.fkUserInfo.bornDate?date}" type="text" class="input" size="23" maxlength="14" rule="date" require="false" tips="格式为xxxx-xx-xx" warn="您输入的日期不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>身份证号</th>
      <td colspan="4"><input name="fkUserInfo.idCard" value="${fkUser.fkUserInfo.idCard}" type="text" class="input" size="23" maxlength="19" rule="IdCard" require="false" tips="15或18位" warn="您输入的身份证号不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>联系电话</th>
      <td colspan="4"><input name="fkUserInfo.userPhone" value="${fkUser.fkUserInfo.userPhone}" type="text" class="input" size="23" maxlength="13" rule="phone" require="false" tips="格式为xxxx-xxxxxxxx" warn="您输入的电话号码不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>联系手机</th>
      <td colspan="4"><input name="fkUserInfo.userMobile" value="${fkUser.fkUserInfo.userMobile}" type="text" class="input" size="23" maxlength="13" rule="mobile" require="false" tips=""  warn="您输入的手机号码不存在或格式不正确" pass="&nbsp;" /></td>
    </tr>
    <tr>
      <th>电子邮箱</th>
      <td><input name="fkUserInfo.userEmail" value="${fkUser.fkUserInfo.userEmail}" type="text" class="input" size="23" maxlength="30" rule="email" require="false" tips="" warn="格式验证错误" pass="&nbsp;" /></td>
    </tr>
    <tr>
      <th>单位名称</th>
      <td colspan="4"><input name="fkUserInfo.userCompany" value="${fkUser.fkUserInfo.userCompany}"  type="text" class="input" size="23" maxlength="50" rule="require" empty=" " tips="请输入你的单位名称" warn="你输入的单位名称有误" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th colspan="5"><div  align="center"><input name="to_save" id="to_save" type="button" class="input_input" value="保   存" onclick="javascript:submitCreate(false);" /></div>
      </th>
    </tr>
    <input type="hidden" name="fkUser.id" value="${fkUser.id}" />
    <input type="hidden" name="fkUserInfo.id" value="${fkUser.fkUserInfo.id}" />
    <input type="hidden" name="fkUserInfo.userIndustry" value="${fkUser.fkUserInfo.userIndustry}" />
    </form>
  </table>
</#escape> 
<@fkMacros.pageFooter />