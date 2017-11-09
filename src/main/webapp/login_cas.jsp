<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产综合监管平台_企业端</title>
<link href="resources/default/css/cas_style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css"/>
<script type="text/javascript" src="ext/adapter/prototype/prototype.js"></script>
<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="ext/ext-all.js"></script>
<style type="text/css">
<!--
body{margin:0px;padding:0px;font-size:12px;font-family:Arial, Helvetica, sans-serif; }
table{ font-size:12px;font-family:Arial, Helvetica, sans-serif;}
.shuru div{ padding:0px 0px 12px 0px;}
.shuru input{ border:1px solid #1d5181;}
.e1{}
-->
</style>
</head>
<body>
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="middle"><table width="1002" height="597" border="0" cellpadding="0" cellspacing="0" align="center" style="background:url(resources/default/images/login_bg.jpg) no-repeat center center;">
      <tr>
        <td height="147">&nbsp;</td>
      </tr>
      <tr>
        <td height="300" background="resources/default/images/login_bg3.jpg" align="right">
        <form id="loginForm" name="loginForm" action="security_check.xhtml" method="POST">
        <table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" height="119"></td>
            </tr>
            <tr>
              <td valign="top" width="220" class="shuru">
              <div><b>用户名：</b>
                   <input type="text" id="userName" style="width:140px" name="j_username" onKeyDown="doNextInput(event,this.form.userPass);"/>
              </div>
              <div><b>密　码：</b>
                  <input id="userPass" name="j_password" style="width:140px"  type="password" onKeyDown="doNextInput(event,this.form.checkNumber);" />
              </div>
              <div style="padding-top:0px; margin-top:0px;">
              	<table border="0px" cellpadding="0" cellspacing="0">
              		<tr>
              			<td><b>验证码：</b></td>
              			<td>&nbsp;&nbsp;<input type="text" id="checkNumber"  style="ime-mode:disabled;width:65px;" name="j_checknumber" maxlength="4" onKeyDown="doSubmit(event);" /></td>
              			<td>&nbsp;&nbsp;<img src="checkNumber" id="checkImg" width="60" height="20" style="cursor:pointer;" onClick="this.src='checkNumber?'+Math.random()" title="点击图片重新获得验证码"/></td>
              		</tr>
              	</table>
              </div>
                  </td>
              <td width="250" valign="top"><a href="#"><img src="resources/default/images/dengru.jpg" id="loginButton" width="68" height="60" border="0"></a></td>
            </tr>
            <tr>
              <td height="42" colspan="2" align="center" valign="middle" style="color:#FFFFFF"> 宁波市安全生产监督管理局　技术支持：<a href="http://www.safetys.cn" style="color:#FFFFFF">安生科技</a> 　<br></td>
            </tr>
        </table>
        </form>
        </td>
      </tr>
      <tr>
        <td width="1002" height="150" align="left"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0" >
          <tr>
            <td  style="color:#FFFFFF">温馨提示：<br>
              1.宁波市安监局采用了统一的单点登录功能，请在上面登录框中输入您的单点登录<br>帐号和密码， 登录一次就可以进入所有有权限访问的应用系统。<br>
              2.此单点登录系统目前只开通了宁波市安监局用户。</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
document.getElementById("userName").focus();
    function doClickEvent(obj){
	   if (document.all){//IE
           obj.fireEvent('onclick');
       }else{//FF
           var evt = document.createEvent("MouseEvents");
           evt.initEvent("click", true, true);
           obj.dispatchEvent(evt);
       }
    }
	function doSubmit(event){
	    event=event?event:(window.event?window.event:null);
		if (event.keyCode==13){
           doClickEvent(document.getElementById("loginButton"));
		}
		return;
	}
	function trimAll(data){
	  var reg=/^ +| +$/g;
	  var str=data.replace(reg,"");
	  return str;
	}
	function checkInput(){
		if(trimAll(document.getElementById("userName").value)==""){
			alert("      用户名不能为空！");
			document.getElementById("userName").focus();
			return false;
		}else if(trimAll(document.getElementById("userPass").value)==""){
			alert("      密码不能为空！");
			document.getElementById("userPass").focus();
			return false;
		}else if(trimAll(document.getElementById("checkNumber").value)==""){
			alert("      验证码不能为空！");
			document.getElementById("checkNumber").focus();
			return false;
		}
		return true;
	}
	function doNextInput(event,obj){
		event=event?event:(window.event?window.event:null);//IE/FF
		if (event.keyCode==13){
			obj.focus();
		}
		return;
	}

    Ext.get('loginButton').on('click', function(){
      if(checkInput()){
        
        Ext.MessageBox.show({
           title: '请稍等',
           msg: '正在验证......',
           progressText: 'Initializing.....',
           width:300,
           progress:true,
           closable:false,
           animEl: 'mb6'
       });

       // this hideous block creates the bogus progress
       var f = function(v){
            return function(){
                 if(v == 5){
			       var myAjax = new Ajax.Request("security_check.xhtml" ,{method: "get",parameters:Form.serialize(document.getElementById("loginForm")),onSuccess:function(transport){
							var response =transport.responseText.evalJSON();
							if (response.msg=='ok') {
							    Ext.MessageBox.hide();
								document.location='workspace/loadWorkSpaceContent.xhtml';
								return;
							}else if(response.msg=='checkNumberError') {
							    Ext.MessageBox.hide();
								Ext.Msg.alert('系统消息','&nbsp;&nbsp;&nbsp;&nbsp;验证码错误或已过期,请重新输入&nbsp;&nbsp;&nbsp;&nbsp;');
							} else {//error
							    Ext.MessageBox.hide();
								Ext.Msg.alert('系统消息','&nbsp;&nbsp;&nbsp;&nbsp;用户名或密码错误&nbsp;&nbsp;&nbsp;&nbsp;');
							}
							document.getElementById("checkImg").setAttribute("src","checkNumber?"+Math.random());
						}
					});
                  }else{
					var i = v/11;
					Ext.MessageBox.updateProgress(i, '');
                }
           };
       };
       for(var i = 1; i < 13; i++){
           setTimeout(f(i), i*250);
       }
     }
    });
</script>