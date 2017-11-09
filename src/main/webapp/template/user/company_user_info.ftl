<@fkMacros.pageHeader />
<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
<#escape x as (x)!>
<#assign url='createCompanyUserName.xhtml'>
<script language="javascript">
function submitCreate(is_create){
   // if(!formValidator.validate()){
    //	return false;
  //  }
	if($('userName')!=null){
		var req = new Request({
			url:'../user/loadCheckDuplicateUser.xhtml?fkUser.userName='+$('userName').value,
			onSuccess: function(response){
				if(eval(response)){
					alert('系统中已经存在相同的用户名!');
					return false;
				}
				$('userform').action="${url}";
				$('userform').submit();
			},
			onFailure: function(){
				alert('服务请求失败，请稍候重试');
				return false;
			}
		});
		req.send();
	} else {
		$('userform').action="${url}";
		$('userform').submit();
	}
}
function isCheck(){
	if($('userName')!=null){
		var req = new Request({
			url:'../user/loadCheckDuplicateUser.xhtml?fkUser.userName='+$('userName').value,
			onSuccess: function(response){
				if(eval(response)){
					alert('系统中已经存在相同的用户名!');
					return false;
				}
			},
			onFailure: function(){
				alert('服务请求失败，请稍候重试');
				return false;
			}
		});
		req.send();
	} 
}
</script>
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong>添加企业用户</strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">

    <tr>
      <th width="15%">用 户 名</th>
      <td colspan="4"  width="85%"><input id="userName" name="fkUser.userName" value="${company.regNum}" type="text" onBlur="isCheck();" class="input" size="23" maxlength="12" rule="username" empty="用户名不允许为空" tips="英文与下划线组成的4~20位的字符" warn="您输入的用户名不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>密　　码</th>
      <td colspan="4"><input id="userPass" name="fkUser.userPass" type="password" class="input" size="25" maxlength="30" rule="password" level="2" empty="密码不允许为空" tips="6~16位数字、字母及特殊字符的混合字符" warn="密码安全度太低" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>确认密码</th>
      <td colspan="4"><input name="confirmpass" type="password" class="input" id="confirmpass" size="25" maxlength="30" rule="repeat"   to="userPass" empty="请确认您的密码" tips="重复输入一次您的密码" warn="两次输入的密码不一致" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>真实姓名</th>
      <td colspan="4"><input name="fkUserInfo.factName" value="${company.fddelegate}" type="text" class="input" size="23" maxlength="20" rule="chinese" empty="姓名不允许为空" tips=" " warn="您输入的姓名不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>联系电话</th>
      <td colspan="4"><input name="fkUserInfo.userPhone" value="${company.phoneCode}" type="text" class="input" size="23" maxlength="13" rule="phone_mobile" require="false" tips="格式为xxxx-xxxxxxxx" warn="您输入的电话号码不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>联系手机</th>
      <td colspan="4"><input name="fkUserInfo.userMobile"  type="text" class="input" size="23" maxlength="13" rule="mobile" require="false" tips=" "  warn="您输入的手机号码不存在或格式不正确" pass="&nbsp;" /></td>
    </tr>
    <tr>
      <th>电子邮箱</th>
      <td  colspan="4"><input name="fkUserInfo.userEmail"  type="text" class="input" size="23" maxlength="30" rule="email" require="false" tips=" " warn="格式验证错误" pass="&nbsp;" /></td>
    </tr> 
    <tr>
      <th>单位名称</th>
      <td colspan="4"><input name="fkUserInfo.userCompany" value="${company.companyName}" type="text" class="input" size="23" maxlength="50" require="false" empty="单位名称不能空" tips="请输入你的单位名称" warn="你输入的单位名称有误" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>所在地区</th>
      <td colspan="4"><div id="div-area"></div></td>
    </tr>
    <input type="hidden" name="fkUserInfo.userIndustry" value="qiye" />
	<input type="hidden" id="roleIds" name="roleIds" value="413"/>
	<input type="hidden" id="companyId" name="company.id" value="${company.id}"/>
    <tr>
      <th colspan="5"><div  align="center">
      <input name="to_save" id="to_save" type="button" class="btn_save" value="保  存" onclick="javascript:submitCreate(false);" />&nbsp;&nbsp;&nbsp;&nbsp;
      <input name="cancel" onclick="history.back();" type="button" class="btn_save" value="取  消" /></div>
      </th>
    </tr>
	  
    </form>
  </table>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" />
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" />
</#if>
</#escape> 
<@fkMacros.pageFooter />