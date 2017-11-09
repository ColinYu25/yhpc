<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="resources/default/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<script type="text/javascript">
<% 
	if(request.getParameter("login_error")!=null){
%>
    alert("用户名或密码错误！");
<%}%>

function trimAll(data){
  var reg=/^ +| +$/g;
  var str=data.replace(reg,"");
  return str;
}
function checkInput(){
	if(trimAll(document.getElementById("userName").value)==""){
		alert("    用户名不能为空！");
		document.getElementById("userName").focus();
		return false;
	}else if(trimAll(document.getElementById("userPass").value)==""){
		alert("    密码不能为空！");
		document.getElementById("userPass").focus();
		return false;
	}else if(trimAll(document.getElementById("checkNumber").value)==""){
		alert("    验证码不能为空！");
		document.getElementById("checkNumber").focus();
		return false;
	}
	document.getElementById("loginForm").submit();
}
function doNextInput(obj){
	if (window.event.keyCode==13){
		obj.focus();
	}
	return;
}
function doSubmit(){
	if (window.event.keyCode==13){
		checkInput();
	}
	return;
}
</script>
<form id="loginForm" name="loginForm" action="security_check.xhtml" method="POST">
<table width="1003" height="153" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="template/resources/default/img/index_001_01.gif" width="1006" height="248"></td>
  </tr>
</table>
<table width="1003" height="344" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="344" valign="top" background="resources/default/img/index_002.gif"><table width="797" height="235" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="249" valign="top" background="resources/default/img/index_005.gif"><table width="349" height="238" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="31" align="right" class="text_hui_14">&nbsp;</td>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
              <td width="81" align="right" class="text_hui_14">用户名：</td>
              <td colspan="2"><input type='text' id="userName" name='j_username' class="input_blue" size="15" maxlength="30" onKeyDown="javascript:doNextInput(this.form.j_password);" /></td>
            </tr>
            <tr>
              <td align="right" class="text_hui_14">密　码：</td>
              <td colspan="2"><input type='password' id="userPass" name='j_password'  class="input_blue" size="16" maxlength="30"  onKeyDown="javascript:doNextInput(this.form.checkNumber);"></td>
            </tr>
            <tr>
              <td align="right" class="text_hui_14">验证码：</td>
              <td colspan="2"><input type="text" class="input_blue" size="4" style="ime-mode:disabled" id="checkNumber" name="checkNumber" maxlength="4"    onKeyDown="javascript:doSubmit();" /><img src="../../checkNumber" border="0" width="60" height="20" /></td>
            </tr>
            <tr>
              <td height="35" align="center">&nbsp;</td>
              <td width="99" height="35"><img id="img" src="resources/default/img/index_12.gif" width="75" height="25" style="cursor:hand"  onClick="javascript:checkInput();"></td>
              <td width="169" class="h_14">&nbsp;</td>
            </tr>
            <tr>
              <td height="35" colspan="3" align="center">&nbsp;</td>
            </tr>
            <tr>
              <td height="36" colspan="3" align="center" class="text12_bai">技术支持：<a href="#" class="bai_12">安生科技</a> 联系电话：0574-87364008</td>
            </tr>
          </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
