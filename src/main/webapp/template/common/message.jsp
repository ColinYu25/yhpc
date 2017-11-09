<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript">
<s:if test="url!=null&&url!=''">
if("${messageKey}"=="city.is.not.all.report") {
	var contiune = window.confirm("<s:property value='%{getText(messageKey)}'/>");
	if (contiune) {
		window.location = "loadProvinceReport.xhtml?mineReport.type=1&mineReport.reportMonth=2008-10&pass=true";
	} else {
		window.location = "loadProvinceReports.xhtml";
	}
} else {
	<s:if test="messageKey!=null">
		alert("<s:property value='%{getText(messageKey)}'/>");
	</s:if>
	window.location = "${url}";
}
setTimeout("location.replace('<s:property escape="false" value="%{url}"/>')",3000);
</script>
<body>
<s:property  value="%{getText(messageKey)}" />
<br />
&nbsp;&nbsp;3秒钟后自动跳转页面或直接点击<a href="${url}">这里</a>。
</body>
</html>
</s:if>
<s:else>
if("${messageKey}"=="city.is.not.all.report") {
	if ("${mineReport.print}"=="true") {
		window.location = "loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth=${mineReport.reportMonth}&pass=true&mineReport.print=${mineReport.print}";
	} else {
		var contiune = window.confirm("<s:property value='%{getText(messageKey)}'/>");
		if (contiune) {
			window.location = "loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth=${mineReport.reportMonth}&pass=true&mineReport.print=${mineReport.print}";
		} else {
			<s:if test="mineReport.type!=null">
				window.location = "loadProvinceReports.xhtml?mineReport.type=${mineReport.type}";
			</s:if>
			<s:else>
				window.location = "loadProvinceReports.xhtml";
			</s:else>
		}
	}
} else {
	alert("<s:property value='%{getText(messageKey)}'/>");
	history.back(-1);
	setTimeout("history.back()",3000);
}
</script>
<body>
<s:property  value="%{getText(messageKey)}" />
<br />
&nbsp;&nbsp;3秒钟后自动跳转页面或直接点击<a href="javascript:history.back();">这里</a>。
</body>
</html>
//setTimeout("location.replace('<property escape="false" value="{url}"/>')",<request.getAttribute("time")>);
</s:else>
