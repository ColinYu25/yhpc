<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<title>安全生产领域“打非治违”情况周报表</title>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
var j = jQuery.noConflict();
setIsZhifa(true);

var formValidator=Validator.setup({   
	form : 'dfzwPunish',
	configs : 'attribute,tag',
	warns : 'color,alert',
	color : {warn :'black', pass:'black'}
});  
function submitCreate(){
	  var objs = j.makeArray(j("#tf input"));
	  if(formValidator.validate()){
	  	if(objs.length) {
			for(var i=0;i<objs.length;i++) {
				if(!checkNum(objs[i],"请填写正整数！")){
					return false;
				}
			}
		}
		if (get("reportDateBegin").value=="") {
			alert("请选择开始时间！");
			get("reportDateBegin").focus();
			return false;
		}
		  var obj=get("dfzwPunish");
		  //if(!chkNull(obj.chargeMan,'请填写审核人')){
		  	 //return false;
		  //}else 
		  if(!chkNull(obj.fillMan,'请填写填表人')){
		  	 return false;
		  }else if(!chkNull(obj.tel,'请填写联系电话')){
		  	 return false;
		  }else  if(!chkNull(obj.fillDate,'请填写填报日期')){
		  	 return false;
		  }
		  if(!confirm("您选择的日期是"+get("reportDateBegin").value+",确定保存吗?")){
			 return false;
		  }
		  <c:choose>
			  <c:when test="${!empty(entity)&&entity.id>0}">
				obj.action="update.xhtml";
			  </c:when>
			  <c:otherwise>
		 		 obj.action="create.xhtml";
			  </c:otherwise>
		  </c:choose>
		  obj.submit();
		  get("sub").disabled = true;
	  }
}
function changeReportDateBegin(date) {
	if (date == "") {
		get("reportDateEnd").value = "";
		return;
	}
	var d = new Date(date.substring(0,4),date.substring(5,7)-1,date.substring(8,10));
	d.setDate(d.getDate()+6);
	var d_str = d.getYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
	get("reportDateEnd").value = d_str;
}
</script>
</head>
<body>
<form name="dfzwPunish" id="dfzwPunish" method="post">
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0" id="tb">
     <tr>
      <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10"></td>
        </tr>
		 <tr>
          <tH colspan="3" style="font-size:18px" class="head" align="center">安全生产领域“打非治违”情况周报表</tH>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位:<c:choose><c:when test="${!empty(entity.user)}">${entity.user.fkUserInfo.userCompany}</c:when>
		  <c:otherwise>${userDetail.userCompany}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red; font-size:12px;">（如填报单位名称不对，请到个人信息修改！）</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom"><input type="text" id="reportDateBegin"
			name="entity.reportDateBegin" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
			readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
			name="entity.reportDateEnd" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
			readOnly="true" /></td>
		</tr>
      </table></td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left">
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_list">
		  	<tr>
              <td width="10%" rowspan="3">行业领域</td>
              <td colspan=3>组织督查检查情况</td>
              <td colspan=9>对非法违法、违规违章行为实施处罚情况</td>
            </tr>
            <tr>
              <td>组织检查组</td>
              <td>组织检查人员</td>
              <td>受检查企业</td>
              <td>警告</td>
              <td>责令改正、限期整改、停止违法行为</td>
              <td>没收违法所得、非法生产设备</td>
              <td>责令停产、停业、停止建设</td>
              <td>暂扣或吊销有关许可证、职业资格</td>
              <td>关闭非法违法企业</td>
              <td>行政拘留</td>
              <td>移送追究刑事责任</td>
              <td>处罚罚款</td>
            </tr>
            <tr>
              <td>（个）</td>
              <td>（人次）</td>
              <td>（个）</td>
              <td>（次）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（家）</td>
              <td>（个）</td>
              <td>（家）</td>
              <td>（人）</td>
              <td>（人）</td>
              <td>（万元）</td>
            </tr><!-- 
            <tr>
              <td width="8%">&nbsp;</td>
              <td width="8%">1</td>
              <td width="8%">2</td>
              <td width="8%">3</td>
              <td width="8%">4</td>
              <td width="8%">5</td>
              <td width="8%">6</td>
              <td width="8%">7</td>
              <td width="8%">8</td>
              <td width="8%">9</td>
              <td width="8%">10</td>
              <td width="8%">11</td>
              <td width="8%">12</td>
            </tr> -->
			<tbody id="tf">
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
		   </tbody>
   <tr>
       <td colspan="15"><table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
	   <td width="20%" nowrap="nowrap">审核人：
	     <input type="text" id="chargeMan"  name="entity.chargeMan" value="${entity.chargeMan}" class="input" maxlength="8" size="15"/></td>
	   <td width="20%" align="center">填表人：
	     <input type="text"  id="fillMan" value="${entity.fillMan}" name="entity.fillMan" class="input" maxlength="8" size="12"/></td>
	   <td width="15%" align="center">联系电话：</td>
	   <td width="15%"><input type="text" id="tel" name="entity.tel" class="input" maxlength="13" size="15" style="ime-mode:disabled;"  value="${entity.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="15%" align="center">填报日期： </td>
	   <td width="15%"><input type="text" id="fillDate" name="entity.fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"  onMouseDown="checkDateOnMouse(this)" value="<s:date name="entity.fillDate" format="yyyy-MM-dd" />"/><a href="javascript:void(0)" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a></td>
	   </tr></table></td>
    </tr>
	
          </table></td>
        </tr>
		
      </table> 
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
   <tr>
	 <td width="28%" align="center">
	 <c:if test="${entity.state==0||entity.state==null}"><input name="sub" type="button" class="but_2" value="保　存" onClick="submitCreate();" /></c:if>
		 <input name="back" type="button" class="but_2" value="返　回"  onClick="javaScript:history.back();"/>
		 </td>
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
	for (var z = 0; z < rows.length - 1; z++){
		var val = j("#" + rows[z].id + " td:eq(" +(i)+")").html();
		spanTo += parseFloat(val?val:0);
	}
	if (!isNaN(spanTo)){
		j("#tf tr:last td:eq("+(i)+")").html(spanTo);
	}
}	


</script>
</body>
</html>

