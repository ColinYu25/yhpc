<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
function checkInput(){
	var userPass = document.getElementById("userPass").value;
	var reg = /^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$/;
	if (userPass == "") {
		alert("密码不允许为空");
		return;
	}
	if (reg.test(userPass) === false) {
		alert("密码设置为：1、必须要有大小写的字母； 2、必须要有数字； 3、8-16位密码； 例如：Abc123456");
		return;
	}
	var confirmpass = document.getElementById("confirmpass").value;
	if (confirmpass != userPass) {
		alert("两次输入的密码不一致！");
		return;
	}
	var oldPassword = document.getElementById("oldPassword").value;
	jQuery.ajax({
   		  url:'${base}/company/updatePassword.xhtml',
    	  data:{"fkUser.userPass" : userPass, "oldPassword" : oldPassword},
    	  cache:false,
    	  async:false,
    	  error:function(XMLHttpRequest,textStatus) {
              alert('服务器连接失败，请稍候重试！');
    	  },
  		  success: function(data){
  		  	  if(data == "true") {
  		  	    alert("修改成功");
  		  	    window.location.href = "${base}/xml/loadFlashByXmlOfDanger.xhtml";
  		  	  } else {
  		  	  	alert(data);
  		  	  }
		  }
	});
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
      <th width="15%">旧密码</th>
      <td colspan="4" width="85%"><input  id="oldPassword" name="oldPassword" type="password" class="input" size="23" maxlength="30" /></td>
    </tr>
    <tr>
      <th>新密码</th>
      <td colspan="4"><input id="userPass" name="fkUser.userPass" type="password" class="input" size="23" maxlength="30"/></td>
    </tr>
    <tr>
      <th>确认密码</th>
      <td colspan="4"><input name="confirmpass" type="password" class="input" id="confirmpass" size="23" maxlength="30"  /></td>
    </tr>
    <tr>
    	<td colspan="5" style="text-align:left;">密码设置为：1、必须要有大小写的字母；  2、必须要有数字；  3、8-16位密码； 例如：Abc123456</td>
    </tr>
    <tr>
      <th colspan="5"><div  align="center"><input name="to_save" id="to_save" type="button" class="btn_save" value="修  改" onclick="javascript:checkInput();" /></div>
      </th>
    </tr>
    </form>
  </table>
</#escape> 
<@fkMacros.pageFooter />