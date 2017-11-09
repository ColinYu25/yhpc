<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="cn.safetys.constant.SystemConstant"%>
<%@ page contentType="text/html; charset=utf-8"%>
<% 
if(SystemConstant.P!=null
		&&!SystemConstant.P.isEnterprise()
		&&SystemConstant.P.isGovernment()){
	response.sendRedirect(request.getContextPath()+"/login_zf.jsp");
}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>

<link href="resources/default/css/style.css" rel="stylesheet"
	type="text/css">
	<link rel="stylesheet" type="text/css"
		href="resources/default/js/extjs3.4/css/ext-all.css" />
<style type="text/css">
/*覆盖style.css里的 */
#footer {
	position:absolute;
	left:250px;
	top:540px;
	text-align:center;
	color:#fff;
}
#barDiv{
	position:absolute;
	width: 102px;
	height: 124px;
	background-image: url('barcode/bar-background.png');
	left:850px;
	top:470px;
	color:#fff;
}
</style>
</head>
<body id="qy">
	<div id="wrap">
		<a href="template/workspace/qy.doc"
			style="font-size: 14px; font-weight: bold; color: #fff;">隐患系统操作说明<img
			style="width: 20px; height: 20px;"
			src="<%=request.getContextPath()%>/resources/default/img/fj_down_icon.png"></img></a>
		&nbsp;&nbsp;&nbsp; <a
			href="<%=request.getContextPath()%>/resources/default/img/qy.doc"
			style="font-size: 14px; font-weight: bold; color: #fff;">管道管理操作说明<img
			style="width: 20px; height: 20px;"
			src="<%=request.getContextPath()%>/resources/default/img/fj_down_icon.png"></img></a>

		<div class="navi" style="margin-left: 190px; text-align: center">
			<a href="javascript:"
				onclick="window.open('notice/nosecuritycheck/loadMainNotice.xhtml')">信息公开</a><span></span>
		</div>
		<div
			style="margin-left: 100px; margin-top: 210px;">
			<span style="font-size: 15px; color: red">各单位请注意:&nbsp;&nbsp;1.如已变更工商注册号为统一社会信用代码，请用原工商注册号登录进去后，将工商注册号修改为<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			统一社会信用代码，并用新号码作为登录名并保存。<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			2.为了确保信息系统使用安全性，请您设置一个高强度、不易猜测的密码作为您的系统登录密码！
			</span>
		</div>
		<form id="loginForm" name="loginForm" action="security_check.xhtml"
			method="POST">
			<input type="hidden" name="loginType" value="qy"/>
			<div id="yhm" class="input">
				<input type="text" id="userName" name="j_username"
					onKeyDown="doNextInput(event,this.form.userPass);" />
			</div>
			<div id="mm" class="input">
				<input id="userPass" name="j_password" type="password"
					onKeyDown="doNextInput(event,this.form.checkNumber);" />
			</div>
			<div id="savePwd2">
				<input type="checkBox" id="savePassWord" value="0"><font
					color="red">记住密码</font>
			</div>
			<div id="yzm" class="input">
				<input type="text" id="checkNumber" style="ime-mode: disabled"
					id="checkNumber" name="j_checknumber" maxlength="4"
					onKeyDown="doSubmit(event);" />
			</div>
			<img src="checkNumber" id="checkImg" width="60" height="20"
				onClick="this.src='checkNumber?'+Math.random()"
				style="cursor: pointer;" title="点击图片重新获得验证码" />
		</form>
		<div id="dl" class="login">
			<a href="#" id="loginButton">登陆</a>
		</div>
		<DIV class=reg>
			<A id="isFrame2" href="login_register.jsp">注册</A>
		</DIV>

		<div id="footer" >
			<p>宁波市安全生产监督管理局</p>
			<span>技术支持：</span> <a href="http://www.safetys.cn/"> 安生科技</a> <span
				id="lxdh">联系电话：0574-87364008 &nbsp; QQ:1428357613 <br /> <font
				color=#ccccff size=4>建议使用IE7、IE8、IE9浏览器，将电脑显示屏的分辨率设置为大于1280*768</font>
			</span>
		</div>
		<div id="barDiv">
			<img src="barcode/es.png" style="width:100%;"/>
		</div>
	</div>
</body>
</html>
<!--弹出框 开始-->
<script type="text/javascript"
	src="resources/default/js/extjs3.4/ext-base.js"></script>
<script type="text/javascript"
	src="resources/default/js/extjs3.4/ext-all.js"></script>
<script type="text/javascript"
	src="resources/default/wbox1.0/jquery1.4.2.js"></script>
<script type="text/javascript" src="resources/default/wbox1.0/wbox.js"></script>
<script type="text/javascript" src="resources/default/js/des.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/default/wbox1.0/wbox/wbox.css" />

<script>
var wbox= $("#isFrame2").wBox({
	title: "新用户注册",
	//noTitle:true,
   	requestType: "iframe",
	iframeWH:{width:555,height:250},
	target:"notice/nosecuritycheck/loadCompanyRegister.xhtml"
});


if (<%=request.getParameter("r")%>==1){
$("#isFrame2").wBox({
	show:true,
	title: "新用户注册",
	//noTitle:true,
   	requestType: "iframe",
	iframeWH:{width:555,height:250},
	target:"notice/nosecuritycheck/loadCompanyRegister.xhtml"});
}
	//wbox.showBox();
</script>


<script language="javascript">
   function getCookie(c_name){
　　　　if (document.cookie.length>0){　　//先查询cookie是否为空，为空就return ""
　　　　　　c_start=document.cookie.indexOf(c_name + "=")　　//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
　　　　　　if (c_start!=-1){ 
　　　　　　　　c_start=c_start + c_name.length+1　　//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
　　　　　　　　c_end=document.cookie.indexOf(";",c_start)　　//其实我刚看见indexOf()第二个参数的时候猛然有点晕，后来想起来表示指定的开始索引的位置...这句是为了得到值的结束位置。因为需要考虑是否是最后一项，所以通过";"号是否存在来判断
　　　　　　　　if (c_end==-1) c_end=document.cookie.length　　
　　　　　　　　return unescape(document.cookie.substring(c_start,c_end))　　//通过substring()得到了值。想了解unescape()得先知道escape()是做什么的，都是很重要的基础，想了解的可以搜索下，在文章结尾处也会进行讲解cookie编码细节
　　　　　　} 
　　　　}
　　　　return ""
　　}　
   var loginInfo=getCookie('nbyhpc_qy_login_info');
   if(loginInfo!=''){
	   var i=loginInfo.indexOf("$$");
	   document.getElementById("userName").value=loginInfo.substring(0,i);
	   document.getElementById("userPass").value=hexToStringForDES(loginInfo.substring(i+2,loginInfo.length));

	   //cookie有效的情况下，checkBox默认选中
	   document.getElementById("savePassWord").checked="checked";
   }else{
	   //cookie无效的情况下，checkBox默认不选中
	   document.getElementById("savePassWord").checked="";
   }
</script>

<script type="text/javascript" src="ext/adapter/prototype/prototype.js"></script>

<!--弹出框 结束-->
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
                 if(v == 1){
			       var myAjax = new Ajax.Request("security_check.xhtml" ,{method: "post",parameters:Form.serialize(document.getElementById("loginForm")),onSuccess:function(transport){
			    	   var response =transport.responseText.evalJSON();
							if (response.msg=='ok') {
						      var userName=document.getElementById("userName").value;
						      Ext.Ajax.request({
									url : "ajax/nosecuritycheck/checkCompanyDelete.xhtml",
								    params : {"fkUser.userName" : userName},
								    success : function(response) {
								    	var response =response.responseText.evalJSON();
										//先判断是否是企业用户，企业用户才能登录
										if(response.industry=='qiye'||response.industry=='admin'){
											if(response.industry=='admin'){
												document.location='workspace/loadNavigatorWorkSpace.xhtml';
												return;
											}
											 //判断企业是否删除了所有的行业
											if (response.msg=='ok') {

												  //设置cookie信息
	                                            var _date = new Date();
	                                            var savePassWord=document.getElementById("savePassWord");
	                                            if(savePassWord.checked){
	                                                //当复选框选中时表示要记住密码，设置cookie的失效时间为100年后，足够长
	                                            	//设置cookie的失效时间为100年以后
	    											_date.setYear(_date.getFullYear()+100);
	                                            }else{
	                                                //当复选框未选中时表示不需要记住密码，设置cookie的失效时间为100年前，足够长
	                                            	//设置cookie的失效时间为100年以前
	    											_date.setYear(_date.getFullYear()-100);
	                                            }
	                                            
												 var cookieStr='nbyhpc_qy_login_info='+escape(trimAll(document.getElementById("userName").value)+"$$"+stringToHexForDES(trimAll(document.getElementById("userPass").value)))+";expires="+_date.toGMTString();
												 document.cookie  = cookieStr;
												 Ext.MessageBox.hide();
												 document.location='workspace/loadNavigatorWorkSpace.xhtml';
												 return;
											}else{
												 Ext.MessageBox.hide();
												 Ext.Msg.alert('系统消息','&nbsp;&nbsp;&nbsp;&nbsp;此用户所对应的企业信息已被删除，不能登录!&nbsp;&nbsp;&nbsp;&nbsp;');
											}
										}else{
											Ext.MessageBox.hide();
											Ext.Msg.alert('系统消息','&nbsp;&nbsp;&nbsp;&nbsp;此用户不是企业用户，请从政府端登录!&nbsp;&nbsp;&nbsp;&nbsp;');
										}
								    },
								    failure: function(response, opts) {
								       	alert("无法登陆，请联系客服");
								    }
						      });
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
					var i = v/2;
					Ext.MessageBox.updateProgress(i, '');
                }
           };
       };
       for(var i = 1; i < 3; i++){
           setTimeout(f(i), i*3);
       }
     }
    });
    
   function setUser(name){
  	 // document.getElementById("userName").value=name;
    //	wbox.close(); 
   } 
   
   
  //function setUser(name){
	  
	//location.href="../../login_qy.jsp?userName="+name+"";
	 
	  
 // }
</script>


<SCRIPT language=javascript>window.onerror=function(){return true;} 

</SCRIPT>
