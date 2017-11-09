<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld"%> 
<%@ taglib uri="http://www.safetys.cn/tag/pagination" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jstl/function" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>组织督查检查情况和对非法违法、违规违章行为实施处罚情况周报表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" src="${resourcePath}/js/print.js"></script>
	<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
</head>
<style type="text/css">
<!--
.table_list{
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #000;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #000;
	margin-top:5px;
	width:100%;
	background-color:#f7fcff;
}
.table_list th{FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#b4dff8);}

.table_list td {
	font-size:12px;
	color:#000;
	height:25px;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFF;
	border-right-color: #000;
	border-bottom-color: #000;
	border-left-color: #FFF;
	line-height:20px;
	padding-left:5px;
	padding-right:5px;
}
.table_list td {text-align:center;}
.table_list td p{text-align:left; padding-left:5px; line-height:15px;}
-->
</style>
<script type="text/javascript">
	var especial = "8";
</script>
<body>
<div id="page1">
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0" id="tb">
     <tr>
      <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10">&nbsp;</td>
        </tr>
		 <tr>
          <tH height=40 colspan="3" style="font-size:18px" class="head" align="center">组织督查检查情况和对非法违法、违规违章行为实施处罚情况周报表</tH>
        </tr>
		<tr style="font-size:12px">
		  <td height="20" width="80%">填报单位（章）:<c:choose><c:when test="${!empty(entity.user)}">${entity.user.fkUserInfo.userCompany}</c:when>
		  <c:otherwise>${userDetail.userCompany}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  <td height="20" width="20%" align="right" nowrap="nowrap" valign="bottom">
		 <fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy年MM月dd日'/>&nbsp;到&nbsp;
		 <fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy年MM月dd日'/>&nbsp;</td>
		</tr>
      </table>&nbsp;</td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left">
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_list">
            <tr>
              <td width="10%" rowspan="3">行业领域&nbsp;</td>
              <td colspan=3>组织督查检查情况&nbsp;</td>
              <td colspan=9>对非法违法、违规违章行为实施处罚情况&nbsp;</td>
            </tr>
            <tr>
              <td>组织检查组&nbsp;</td>
              <td>组织检查人员&nbsp;</td>
              <td>受检查企业&nbsp;</td>
              <td>警告&nbsp;</td>
              <td>责令改正、限期整改、停止违法行为&nbsp;</td>
              <td>没收违法所得、非法生产设备&nbsp;</td>
              <td>责令停产、停业、停止建设&nbsp;</td>
              <td>暂扣或吊销有关许可证、职业资格&nbsp;</td>
              <td>关闭非法违法企业&nbsp;</td>
              <td>行政拘留&nbsp;</td>
              <td>移送追究刑事责任&nbsp;</td>
              <td>处罚罚款&nbsp;</td>
            </tr>
            <tr>
              <td>（个）&nbsp;</td>
              <td>（人次）&nbsp;</td>
              <td>（个）&nbsp;</td>
              <td>（次）&nbsp;</td>
              <td>（起）&nbsp;</td>
              <td>（起）&nbsp;</td>
              <td>（家）&nbsp;</td>
              <td>（个）&nbsp;</td>
              <td>（家）&nbsp;</td>
              <td>（人）&nbsp;</td>
              <td>（人）&nbsp;</td>
              <td>（万元）&nbsp;</td>
            </tr>
            <!-- <tr>
              <td width="8%">&nbsp;&nbsp;</td>
              <td width="8%">1&nbsp;</td>
              <td width="8%">2&nbsp;</td>
              <td width="8%">3&nbsp;</td>
              <td width="8%">4&nbsp;</td>
              <td width="8%">5&nbsp;</td>
              <td width="8%">6&nbsp;</td>
              <td width="8%">7&nbsp;</td>
              <td width="8%">8&nbsp;</td>
              <td width="8%">9&nbsp;</td>
              <td width="8%">10&nbsp;</td>
              <td width="8%">11&nbsp;</td>
              <td width="8%">12&nbsp;</td>
            </tr> -->
			<tbody id="tf">
			<tr class="table_list" id="row11">
		     <td  align="center" nowrap="nowrap">合计&nbsp;</td>
		     <td align="center" id="checkTeamAll">&nbsp;</td>
		     <td  align="center" id="checkPersonAll">&nbsp;</td>
		     <td  align="center" id="companyNumAll">&nbsp;</td>
		     <td align="center" id="warnAll">&nbsp;</td>
		     <td  align="center" id="orderRectifyAll">&nbsp;</td>
		     <td  align="center" id="confiscateAll">&nbsp;</td>
		     <td  align="center" id="stopProductAll">&nbsp;</td>
		     <td  align="center" id="tempDetainAll">&nbsp;</td>
		     <td  align="center" id="closeAll">&nbsp;</td>
		     <td  align="center" id="administrativeDetainAll">&nbsp;</td>
		     <td  align="center" id="criminalResponsibilityAll">&nbsp;</td>
		     <td  align="center" id="penaltyAll">&nbsp;</td>
		   </tr>
			<c:forEach var="item" items="${types}" varStatus="status">
            <tr id="row${status.count-1}">
			  <td style="text-align:left" nowrap="nowrap">${item.value}&nbsp;</td>
              <td align="center" id="checkTeam_${status.count}">${details[status.count-1].checkTeam}&nbsp;</td>
              <td align="center" id="checkPerson_${status.count}">${details[status.count-1].checkPerson}&nbsp;</td>
              <td align="center" id="companyNum_${status.count}">${details[status.count-1].companyNum}&nbsp;</td>
			  <td align="center" id="warn_${status.count}">${details[status.count-1].warn}&nbsp;</td>
              <td align="center" id="orderRectify_${status.count}">${details[status.count-1].orderRectify}&nbsp;</td>
              <td align="center" id="confiscate_${status.count}">${details[status.count-1].confiscate}&nbsp;</td>
              <td align="center" id="stopProduct_${status.count}">${details[status.count-1].stopProduct}&nbsp;</td>
              <td align="center" id="tempDetain_${status.count}">${details[status.count-1].tempDetain}&nbsp;</td>
              <td align="center" id="close_${status.count}">${details[status.count-1].close}&nbsp;</td>
              <td align="center" id="administrativeDetain_${status.count}">${details[status.count-1].administrativeDetain}&nbsp;</td>
              <td align="center" id="criminalResponsibility_${status.count}">${details[status.count-1].criminalResponsibility}&nbsp;</td>
              <td align="center" id="penalty_${status.count}">${details[status.count-1].penalty}&nbsp;</td>
            </tr>
			</c:forEach>
			
		   </tbody>
   </table>&nbsp;</td>
    	</tr>
	<tr>
	 <td colspan="15">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr style="font-size:12px">
		  <td width="20%" style="padding-left:40px;">审核人： ${entity.chargeMan}</td>
		  <td align="center" width="30%"><br/>填表人：${entity.fillMan}&nbsp;</td>
		  <td  align="center" width="30%"><br/>联系电话：${entity.tel}&nbsp;</td>
		  <td width="40%" align="center"><br/>填报日期:<s:date name='entity.fillDate' format='yyyy-MM-dd' />&nbsp;</td>       
	   </tr>
	   <tr>
		  <td colspan=4 style="font-size:12px;padding-left:30px;">有关要求： 1.各地区要认真填写本表，并于每周五12时前以网上填报、传真（或电子邮件）等方式报送至国务院安委会办公室。
		  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  2.统计数据为专项行动开展以来的累计数据。</td>
	   </tr>
	  </table>
	 &nbsp;</td>
	</tr> 
    	</table>&nbsp;</td>
    </tr>
   
  </table>
  </div>
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
   <tr>
	 <td width="100%" style="font-size:12px;color:red;">注：如已按提示设置IE且仍未能打印者，请点击菜单“下载打印控件”！&nbsp;</td>
	</tr>
	<tr>
	 <td width="28%" align="center">
	 	 <input type="button" name="back" class="but_2" onClick="doPrint('printPreview');" value="打印预览" />
		 <input type="button" name="back" class="but_2" onClick="if(window.confirm('确定打印吗？'))doPrint('print');" value="打  印" />
		 <input name="back" type="button" class="but_2" value="返　回"  onClick="javaScript:history.back();"/>
		 &nbsp;</td>
	</tr>
</table>
<input type="hidden" id="id" name="entity.id" value="${entity.id}"/>
<input type="hidden" id="isReport" name="entity.report" value="true"/>
</form>
<script>
var j = jQuery.noConflict();
for(var i = 1; i < 13; i++){
	var spanTo = 0;
	var rows = j("#tf>tr");
	for (var z = 1; z < rows.length - 1; z++){
		var val = j("#" + rows[z].id + " td:eq(" +(i)+")").html();
		spanTo += parseFloat(val?val:0);
	}
	if (!isNaN(spanTo)){
		j("#tf tr:eq(0) td:eq("+(i)+")").html(spanTo);
	}
}	
	
print
printParam(20,20,20,20,2);
</script>
</body>
</html>

