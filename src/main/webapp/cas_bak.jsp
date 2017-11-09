<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="cn.safetys.util.OaPasswordUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.regex.Pattern"%>
<html>
<head>
<%
	OaPasswordUtils op = new OaPasswordUtils();
	String u = request.getParameter("u");
	String type = request.getParameter("type");
	String dangerId = request.getParameter("dangerId");
	String companyId = request.getParameter("companyId");
	String  userType= request.getParameter("userType");
	
	String  entityId= request.getParameter("entityId");

	String uDecode = "";
	String regEx = "[\u4e00-\u9fa5]";
	
	if(u!=null&&!"".equals(u)){
		if(u.contains("%")) {
			u = URLDecoder.decode(u, "UTF-8");
		} else if(Pattern.compile(regEx).matcher(u).find()) {
			u = u;
		}else {
			u = op.decode(u);
		}
	}
	
	String  o= request.getParameter("o");
	if(o!=null&&!"".equals(o)){
		if(o.contains("%")) {
			o= URLDecoder.decode(o, "UTF-8");
		} else if(Pattern.compile(regEx).matcher(o).find()) {
			o = o;
		}else {
			o = op.decode(o);
		}
	}
%>
<script type="text/javascript"	src="resources/default/js/jquery-1.7.1.min.js"></script>
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

<script type="text/javascript">
$(function(){
	if ("<%=u%>"=="null"&&"<%=o%>"=="null"){
		  window.location.href = "login_qy.jsp?r=1";
	}else{
		var userName = encodeURI('<%=u%>');
		if ("<%=userType%>"=="ES"){
			var regNum = encodeURI('<%=u%>'); 
			var setupNumber = encodeURI('<%=o%>'); 
			$.ajax({
				url: "ajax/nosecuritycheck/loadUserName.xhtml?regNum="+regNum+"&setupNumber="+setupNumber,
				type: "POST",
			    async: false,
				success: function(html){
				      if(html!=null&&html!=""){
				    	  userName= encodeURI(html);
					  }
					}
			});
		}	
		$.ajax({
				  url: "security_check.xhtml?j_login=safetys&j_username="+userName,
				  type: "POST",
				  async: false,
				  success: function(html){
					  $.ajax({
						  url: "ajax/nosecuritycheck/checkCompanyDeleteQy.xhtml?fkUser.userName="+userName,
						  async: false,
						  success: function(html){
									   if (html=="ok"){
										      if ("<%=type%>"=="null"){  
												  window.location.href = "workspace/loadNavigatorWorkSpace.xhtml";
											  }else  if ("<%=type%>"=="nomalDangers"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=nomalDangers";
											  }else  if ("<%=type%>"=="dangers"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=Danger&DangerId=<%=dangerId%>";
											  }else  if ("<%=type%>"=="dangersTimeOut"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=dangersTimeOut&companyId=<%=companyId%>";
											  }else  if ("<%=type%>"=="yhjb"){
												  window.location.href = "statistic/loadReportByCompany.xhtml?statistic.year=<%=request.getParameter("year")%>&statistic.quarter=<%=request.getParameter("quarter")%>&uuid=<%=request.getParameter("uuid")%>";
											  }else  if ("<%=type%>"=="yearReport"){
												  window.location.href = "wh/company_view.xhtml?entity.id=<%=entityId%>";
											  }else{
												  window.location.href = "workspace/loadNavigatorWorkSpace.xhtml";
											  }
										   
									   }else  if (html=="zf"){
										   //此处分开写，便于以后政府段和企业端跳转链接不一样的处理。
										   if ("<%=type%>"=="null"){  
												  window.location.href = "workspace/loadNavigatorWorkSpace.xhtml";
											  }else  if ("<%=type%>"=="nomalDangers"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=nomalDangers";
											  }else  if ("<%=type%>"=="dangers"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=Danger&DangerId=<%=dangerId%>";
											  }else  if ("<%=type%>"=="dangersTimeOut"){
												  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=dangersTimeOut&companyId=<%=companyId%>";
											  }else  if ("<%=type%>"=="yhjb"){
												  window.location.href = "statistic/loadReportByCompany.xhtml?statistic.year=<%=request.getParameter("year")%>&statistic.quarter=<%=request.getParameter("quarter")%>&uuid=<%=request.getParameter("uuid")%>";
											  }else  if ("<%=type%>"=="yearReport"){
												  window.location.href = "wh/company_view.xhtml?entity.id=<%=entityId%>";
											  }else{
												   window.location.href = "workspace/loadNavigatorWorkSpace.xhtml";
											  }
									   }else{
										   window.location.href = "login_qy.jsp?r=1";
									   }
					
					  			}
						});
				  }
			});
	}
});
</script>
</head>
<body>
</body>

</html>