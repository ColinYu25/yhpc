<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />   
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<title></title>
	<style>
		#sidebar-tab{border:1px solid #ccf;margin-bottom:1.5em;overflow:hidden;}
		#tab-title .current{color:#356aa0;border-bottom:0px;padding:5px 14px;}
		#tab-title h3{color:#666;font-size:15px;font-weight:400;}
		#tab-1,#tab-2,#tab-3{padding:5px 7px;border-bottom:1px solid #ccf;cursor:pointer;}
		#tab-2{border:1px solid #ccf;border-top:none;}
		.tab-2,.tab-3{display:none;} 
		#tab-content ul{padding:5px 10px;overflow:hidden;}
		#tab-content ul li{padding-top:5px;height:20px;}
	</style>
	<script language="JavaScript" type="text/javascript" src="${contextPath}/resources/default/js/jquery-1.4.2.min.js"></script>

</head>
<body>
		<script language="javascript">
			$(document).ready(function(){
				$('#tab-title span').click(function(){ 
					$(this).addClass("current").siblings().removeClass(); 	
				 	$("#control1").attr("src",$(this).attr("alter"));
				}); 
				
				$("#control1").attr("src",$(".current").attr("alter"));
			});
			
			var autoHeight=function(id){
		        var iframe = document.getElementById(id);
		        if(iframe.Document){//ie
		            iframe.style.height = iframe.Document.documentElement.scrollHeight;
		        }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
		            iframe.height = iframe.contentDocument.body.offsetHeight ;
		        }
		    }
			
		</script>
	
		<div id="sidebar-tab">
 
			<div id="tab-title">
		 
				<h3>
					<span id="tab-1" class="current" alter="${contextPath}/cas/user/loadUsers.xhtml">用户同步</span>
					<span id="tab-2" alter="${contextPath}/cas/role/loadRoles.xhtml">角色配置</span>
				</h3>
		 
			</div> 
			<div id="tab-content">
				<!-- ul class="tab-1"></ul>
				<ul class="tab-2"></ul -->
				<iframe src="about:blank" id="control1" width="100%" frameborder=0 scrolling=no  height="100%" onload="autoHeight('control1');"></iframe>
			</div> 
		 
		</div> 
		
		
	</body>
<body>