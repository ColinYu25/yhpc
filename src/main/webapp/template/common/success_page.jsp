<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<title>操作成功</title>
  <style type="text/css">
  .STYLE1 {
	font-size: 14pt;
	font-weight: bold;
}
  </style>
</head>
<%
  Integer time=0 ;
%>
  <body>
<script>
window.location = "${url}";
<%
//刷新父级框架
if(request.getAttribute("frame")!=null){%> 
   parent.<%=request.getAttribute("frame")%>.location.reload();
<%}%>

<!--返回跳转页面-->
<s:if test="url!=null">
setTimeout("location.replace('<s:property escape="false" value="%{url}"/>')",0);
</s:if>
<s:else>
setTimeout("location.replace('<s:property escape="false" value="%{url}"/>')",<%=request.getAttribute("time")%>);
</s:else>

</script>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="71">&nbsp;</td>
          </tr>
          <tr>
            <td><div align="center"><img src="${resourcePath}/img/ico_suc.jpg" width="125" height="116" /></div></td>
          </tr>
          <tr>
            <td>
            <div align="center"><span class="STYLE1"><s:property  value="%{getText(messageKey)}" /></span></div></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div align="center"><span class="mid1_leftfont">页面将在<%=time%>秒钟内跳转 &nbsp;&nbsp;<a href="<s:property escape="false" value="%{url}"/>"><strong>点击直接进入</strong></a></span></div></td>
          </tr>
        </table>
  </body>
</html>
