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

	String  usertype= request.getParameter("usertype");
	String  aw= request.getParameter("aw");
	String uDecode = "";
	String regEx = "[\u4e00-\u9fa5]";
	
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
	  if ("<%=type%>"=="null"){  
		  window.location.href = "workspace/loadNavigatorWorkSpace.xhtml?usertype=<%=usertype%>&aw=<%=aw%>";
	  }else  if ("<%=type%>"=="nomalDangers"){
		  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=nomalDangers&usertype=<%=usertype%>&aw=<%=aw%>";
	  }else  if ("<%=type%>"=="dangers"){
		  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=Danger&DangerId=<%=dangerId%>&usertype=<%=usertype%>&aw=<%=aw%>";
	  }else  if ("<%=type%>"=="dangersTimeOut"){
		  window.location.href = "workspace/loadMainWorkSpace1.xhtml?flag=dangersTimeOut&companyId=<%=companyId%>&usertype=<%=usertype%>&aw=<%=aw%>";
	  }else  if ("<%=type%>"=="yhjb"){
		  window.location.href = "statistic/loadReportByCompany.xhtml?statistic.year=<%=request.getParameter("year")%>&statistic.quarter=<%=request.getParameter("quarter")%>&uuid=<%=request.getParameter("uuid")%>&usertype=<%=usertype%>&aw=<%=aw%>";
	  }else  if ("<%=type%>"=="yearReport"){
		  window.location.href = "wh/company_view.xhtml?entity.id=<%=entityId%>&usertype=<%=usertype%>&aw=<%=aw%>";
	  }else{
		  window.location.href = "workspace/loadNavigatorWorkSpace.xhtml?usertype=<%=usertype%>&aw=<%=aw%>";
	  }
});
</script>
</head>
<body>
</body>

</html>