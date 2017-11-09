<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="${contextPath}/resources/cas/css/public.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/cas/css/other.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${contextPath}/resources/cas/js/jquery-1.7.1.min.js"></script>
<script language="javascript" src="${contextPath}/resources/cas/js/right.js"></script>
<script type="text/javascript" src="${contextPath}/resources/cas/js/calendar.js"></script>
<script type="text/javascript" src="${contextPath}/resources/cas/js/png.js"></script>
</head>

<body>
	<script language="javascript">
		var autoHeight=function(id){
	        var iframe = document.getElementById(id);
	        if(iframe.Document){//ie
	            iframe.style.height = iframe.Document.documentElement.scrollHeight;
	        }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
	            iframe.height = iframe.contentDocument.body.offsetHeight ;
	        }
	    }
	    
	    $(document).ready(function(){
				$('#userT').click(function(){ 
					$("#userF").attr("src",$("#userT").attr("alter"));
				}); 
				$('#roleT').click(function(){ 
		
					$("#roleF").attr("src",$("#roleT").attr("alter"));
					
				}); 
				
			});
		var roleManager=function() {
			$("#roleF").attr("src","${contextPath}/role/loadRoles.xhtml");
		}	
		
	</script>
	<div id="right_tit_bar"><div class="bar_icon"><img src="${contextPath}/resources/cas/img/arrow_icon.png"/></div>当前位置：<strong>用户角色(页面跳转后点击页签可以返回)</strong></div>
	<div class="tab_list_menu">
	  <ul>
	    <li id="userT" class="selected" alter="${contextPath}/cas/user/loadUsers.xhtml">用户同步</li>
	    <li id="roleT"  alter="${contextPath}/cas/role/loadRoles.xhtml">角色配置</li>
	  </ul>
	</div>
	
	<div class="tab_list_content">
		<div>
			<iframe id="userF" src="${contextPath}/cas/user/loadUsers.xhtml" id="control1" width="100%" frameborder=0 scrolling=no  height="100%" onload="autoHeight('control1');"></iframe>
		</div>
		<div class="hide">
			<iframe id="roleF" src="${contextPath}/cas/role/loadRoles.xhtml" id="control1" width="100%" frameborder=0 scrolling=no  height="100%" onload="autoHeight('control1');"></iframe>
		</div>
	</div>
	
	
</body>
</html>