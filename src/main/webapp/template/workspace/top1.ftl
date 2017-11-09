<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>宁波市隐患排查治理信息平台</title>
	<!--<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	%>-->
	<script type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/top.js"></script>
	<script language="javascript">
		var menuNames = parent.menuNames;
		var menuIds = parent.menuIds;
		var li = "";
		for(var i=0;i<menuNames.length; i++) {
			li += "<li id='tag_"+menuIds[i]+"' class='t1'><a href=\"#\" onclick=\"switchTag('tag_"+menuIds[i]+"','content');this.blur();\"><span id='name_tag_"+menuIds[i]+"' >"+menuNames[i]+"</span></a></li>";
		}
		
		function switchTag(tag,content){
			cleanStyle();
			document.getElementById(tag).children[0].className="selectli1";
			document.getElementById(tag).children[0].children[0].className="selectli1";
			if(window.parent.leftFrame)
				window.parent.leftFrame.location.href="loadleftWorkSpace.xhtml?nodeId="+tag.split('tag_')[1];
			if(tag.split('tag_')[1]==475235)
				window.parent.rightFrame.location.href="../trouble/loadTroubleHome.xhtml";
		}
		
		function switchTag2(menuId){
			parent.leftFrame.loadMenuTree(menuId);
		}
		
		function cleanStyle() {
			if (getTag("li").length){
				for (var i=0; i< getTag("li").length; i++) {			
					if(getTag("li")[i].id && getTag("li")[i].id.indexOf('tag_')>-1) {
						getTag("li")[i].children[0].className = "";
						getTag("li")[i].children[0].children[0].className = "";
					}
				}
			}
		}
	</script>
	<link href="${resourcePath}/css/top_css.css" rel="stylesheet" type="text/css">
	<link href="${resourcePath}/css/top_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="top">
	<#if casUser?? && aw?? && aw =="false" >
	<#include "cas_top.ftl"/>
	</#if>
	<table width="100%" height="84" border="0" cellpadding="0" cellspacing="0" style="background:url(${resourcePath}/img/top_bg.jpg) left top repeat-x;">
	  	<tr>
	    	<td height="84" valign="bottom" align="right" style="background:url(${resourcePath}/img/top1.jpg) left top no-repeat;">
		    	<div class="top_right">
			    	<#if casUser??>
					   <a href="javascript:redirect();" style="margin-right:70px;color:#ffffff;TEXT-DECORATION:none;">切换到&nbsp;企业隐患管理</a>
					</#if>
		    		技术支持： 
		    		<a href="javascript:openWeb();">安生科技</a><br/>
		    		<a href="javascript:backToHome();">返回首页</a><br/>
		    	</div>
		      	<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
			        <tr>
			          	<td height="28">
			            	<div id="top_menu_right">
			              		<div id="title">
			                		<ul><script type="text/javascript">document.write(li);</script></ul>
				  				</div>
				  			</div>
						</td>
			        </tr>
		      	</table>
	     	</td>
	  	</tr>
	</table>
</div>
<div id="color_line"></div>
<div id="gray_line"></div>
</body>
<script type="text/javascript">
	$(function () {
		switchTag($("#title").find("li:first").attr("id"));
	})
	
	function backToHome(){
		window.parent.rightFrame.location.href="../xml/loadFlashByXmlOfDanger.xhtml";
	}
	
	function redirect(){
		window.parent.location.href="loadMainWorkSpace2.xhtml";
	}
	
	function openWeb(){
		window.open("http://www.safetys.cn");
	}
	
	function openWebWord(){
		<#if userDetail.userIndustry??>
		window.open("../template/workspace/<#if userDetail.userIndustry=='qiye'>qy.doc<#else>zf.doc</#if>");
		</#if>
	}
</script>
</html>