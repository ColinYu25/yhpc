<@fkMacros.pageHeader />
<#global authz=JspTaglibs["/WEB-INF/tlds/security.tld"]>
<#escape x as (x)!> 
<#if fkUser?exists>
  	<#assign url='updateUser2.xhtml'>
<#else>
  	<#assign url='createUser.xhtml'>
</#if>
<script language="javascript">
function submitCreate(is_create){

    if(!formValidator.validate()){
    	return false;
    }
	if($('userName')!=null){
		var req = new Request({
			url:'loadCheckDuplicateUser.xhtml?fkUser.userName='+$('userName').value,
			onSuccess: function(response){
				if(eval(response)){
					alert('系统中已经存在相同的用户名!');
					return false;
				}
				$('saveCreate').value=is_create;
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
		$('saveCreate').value=is_create;
		$('userform').action="${url}";
		$('userform').submit();
	}
}
</script>
<@fkMacros.formValidator 'userform' />
  <table width="99%" height="25" border="0" cellpadding="0" cellspacing="0" class="table_title">
    <tr>
      <td align="center"><strong><#if fkUser?has_content>修改用户<#else>新增用户</#if></strong></td>
    </tr>
  </table>
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="userform" name="userform" method="post" action="">

    <#if fkUser?has_content>
    <tr>
      <th width="15%">用 户 名</th>
      <td colspan="4"  width="85%">${fkUser.userName}</td>
    </tr>
    <#else>
    <tr>
      <th width="15%">用 户 名</th>
      <td colspan="4"  width="85%"><input id="userName" name="fkUser.userName" value="${fkUser.userName}" type="text"  size="23" maxlength="12" rule="username" empty="用户名不允许为空" tips="英文与下划线组成的4~20位的字符" warn="您输入的用户名不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>登录密码</th>
      <td colspan="4"><input id="userPass" name="fkUser.userPass" type="password"  size="25" maxlength="30" rule="password" level="2" empty="密码不允许为空" tips="6~16位数字、字母及特殊字符的混合字符" warn="密码安全度太低" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>确认密码</th>
      <td colspan="4"><input name="confirmpass" type="password"  id="confirmpass" size="25" maxlength="30" rule="repeat"   to="userPass" empty="请确认您的密码" tips="重复输入一次您的密码" warn="两次输入的密码不一致" pass="&nbsp;"/></td>
    </tr>
    </#if>
    <tr>
      <th>真实姓名</th>
      <td colspan="4"><input name="fkUserInfo.factName" value="${fkUser.fkUserInfo.factName}" type="text"  size="23" maxlength="20" rule="chinese" empty="姓名不允许为空" tips=" " warn="您输入的姓名不正确" pass="&nbsp;"/></td>
    </tr>
    <!--
    <tr>
      <th>出生日期</th>
      <td colspan="4"><input name="fkUserInfo.bornDate" value="${fkUser.fkUserInfo.bornDate?date}" type="text"  size="23" maxlength="14" rule="date" require="false" tips="格式为xxxx-xx-xx" warn="您输入的日期不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>身份证号</th>
      <td colspan="4"><input name="fkUserInfo.idCard" value="${fkUser.fkUserInfo.idCard}" type="text"  size="23" maxlength="19" rule="IdCard" require="false" tips="15或18位" warn="您输入的身份证号不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>-->
    <tr>
      <th>联系电话</th>
      <td colspan="4"><input name="fkUserInfo.userPhone" value="${fkUser.fkUserInfo.userPhone}" type="text"  size="23" maxlength="13" rule="phone" require="false" tips="格式为xxxx-xxxxxxxx" warn="您输入的电话号码不存在或格式不正确" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>联系手机</th>
      <td colspan="4"><input name="fkUserInfo.userMobile" value="${fkUser.fkUserInfo.userMobile}" type="text"  size="23" maxlength="13" rule="mobile" require="false" tips=" "  warn="您输入的手机号码不存在或格式不正确" pass="&nbsp;" /></td>
    </tr>
    <tr>
      <th>电子邮箱</th>
      <td  colspan="4"><input name="fkUserInfo.userEmail" value="${fkUser.fkUserInfo.userEmail}" type="text"  size="23" maxlength="30" rule="email" require="false" tips=" " warn="格式验证错误" pass="&nbsp;" /></td>
    </tr> 
    <tr>
      <th>单位名称</th>
      <td colspan="4"><input name="fkUserInfo.userCompany" value="${fkUser.fkUserInfo.userCompany}"  type="text"  size="23" maxlength="50" rule="require" empty="单位名称不能空" tips="请输入你的单位名称" warn="你输入的单位名称有误" pass="&nbsp;"/></td>
    </tr>
    <tr>
      <th>所属行业</th>
      <td colspan="4">
        <select id="userIndustry" name="fkUserInfo.userIndustry" style="width:149px">
          <option value="">&nbsp;&nbsp;--&nbsp;&nbsp;请选择&nbsp;&nbsp;--&nbsp;&nbsp;</option>
          <option value="anwei">安委</option>
          <option value="fagai">发改委</option>
          <option value="jiaoyu">教育局</option>
          <option value="gongan">公安局</option>
          <option value="xiaofang">消防</option>
          <option value="minzong">民宗</option>
          <option value="jianwei">建委</option>
          <option value="chengguan">城管局</option>
          <option value="jiaotong">交通局</option>
          <option value="jiaojing">交警</option>
          <option value="shuili">水利</option> 
          <option value="maoyi">商务</option>
          <option value="wenguang">文广局</option>  
          <option value="weisheng">卫生局</option> 
          <option value="haiyang">海洋渔业</option>
          <option value="lvyou">旅游局</option>
          <option value="zhijian">质监</option>
          <option value="anjian">安监局</option>
          <option value="renfang">人防</option>
          <option value="nongji">农机</option>
          <option value="dianli">电力</option>
          <option value="lishe">栎社机场</option>
          <option value="qiye">企业</option>
        </select><ui:v for="fkUserInfo.userIndustry" rule="require" empty="所属行业不能空" tips="请选择你的所属行业" pass="&nbsp;"/>
      </td>
    </tr>
    <tr>
      <th>所在地区</th>
      <td colspan="4"><div id="div-area"></div></td>
    </tr>
    <#if fkSites?exists>
    <#assign siteCount=0>
    <#list fkSites as m>
    <#assign siteCount=siteCount+1>
	<tr>
	<th>${m.siteName}所属角色</th>
	<td colspan="4"><span id="siteRole${m.id}"></span><span id="ab"><ui:v for="roleIds" rule="Group" min="1" pass="&nbsp;" warn="请至少选择一项" /></span></td>
	</tr>
    </#list>
    </#if>
    <tr>
      <th colspan="5"><div  align="center"><input name="to_save" id="to_save" type="button" class="btn_save" value="保  存" onclick="javascript:submitCreate(false);" />&nbsp;&nbsp;<input name="to_save_new" id="to_save_new" type="button" class="btn_save" value="保存并新建" onclick="submitCreate(true);" />&nbsp;&nbsp;<input name="cancel" onclick="history.back();" type="button" class="btn_save" value="取  消" /></div>
      </th>
    </tr>
	  <input type="hidden" name="fkUser.id" value="${id}" />
	  <input type="hidden" name="fkUserInfo.id" value="${fkUser.fkUserInfo.id}" />
	  <input type="hidden" id="saveCreate" name="saveCreate" value="false" />
    </form>
  </table>
  <BR />
  <#if siteCount gt 1><!--有多个站点时才显示下面的备注信息-->
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <TBODY>
  <TR>
    <TD>   
     说明：<br />
    1、"CAS本站所属角色"：只有拥有<strong>用户管理权限</strong>的用户才需要选择此栏的角色；<br />
    2、"某某子站所属角色"：只有拥有<strong>子站访问权限</strong>的用户才需要选择此栏的角色。
    </TD>
  </TR>
  </TBODY>
 </TABLE>
 </#if>	
<script language="javascript">
<#if fkUser?if_exists.fkUserInfo?if_exists.userIndustry?exists>
	$('userIndustry').value='${fkUser.fkUserInfo.userIndustry}';
</#if>

<#if fkRoles?exists>
    <#list fkRoles as m>
         <#assign checked=''>
    	 <#list fkUser?if_exists.fkRoles?if_exists as c>
    	     <#if m.id==c.id>
    	     	<#assign checked='checked="checked"'>
    	     </#if>
    	 </#list>
    	<#if m_index==0&&checked!=''>
			$('siteRole${m.fkSite.id}').innerHTML+='<input onclick="this.checked=true"  ${checked} type="checkbox" id="roleIds" name="roleIds" value="${m.id}" />${m.roleName}';
    	<!--#elseif m_index==0-->
    	<!--$('siteRole${m.fkSite.id}').innerHTML+='<input disabled="disabled"  ${checked} type="checkbox" id="roleIds" name="roleIds" value="${m.id}" />${m.roleName}';-->
    	<#elseif m_index!=0>
			$('siteRole${m.fkSite.id}').innerHTML+='<input ${checked} type="checkbox" id="roleIds" name="roleIds" value="${m.id}" />${m.roleName}';
    	</#if>
    </#list>
</#if>


if ($('siteRole1').innerHTML==''){
	get("ab").innerHTML="<input   type='text' style='display:none'  id='roleIds'  name='roleIds' />";
}
</script>
<@fkMacros.muilt_select_js />
<#if fkUser?has_content>
<@fkMacros.selectAreas_fun "${fkUser?if_exists.fkUserInfo?if_exists.firstArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.secondArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.thirdArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.fouthArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.fifthArea?if_exists}" />
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" />
</#if>
</#escape> 
<@fkMacros.pageFooter />