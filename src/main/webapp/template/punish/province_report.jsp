<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<title>
<c:choose>
	<c:when test="${ entity.reportType eq 1}">
  		组织督查检查情况和对非法违法、违规违章行为实施处罚情况周报表
	</c:when>
	<c:otherwise>
		组织督查检查情况和对非法违法、违规违章行为实施处罚情况月报表
	</c:otherwise>
</c:choose>
</title>
<SCRIPT language=javascript> 
<!-- 
window.onerror=function(){return true;} 
// --> 
</SCRIPT> 
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<script type="text/javascript">


if (${isAllReport}==0){
	alert("尚有部门未上报!");
}else if(${isAllReport}==1){
	alert("尚有县区未上报!");
}





var j = jQuery.noConflict();
setIsZhifa(true);
var formValidator=Validator.setup({   
	form : 'dfzwPunish',
	configs : 'attribute,tag',
	warns : 'color,alert',
	color : {warn :'black', pass:'black'}
});  
function submitCreate(){
	  var objs = j.makeArray(j("#tf input:text"));
	  var rt = get("reportType").value;
	  var ds = "";
	  if(formValidator.validate()){
	  	if(objs.length) {
			for(var i=0;i<objs.length;i++) {
				if(!checkNum1(objs[i],"请填写正数！")){
					return false;
				}
			}
		}
	  	if(rt==1){
	  		ds = get("reportDateBegin").value;
			if (ds=="") {
				alert("请选择开始时间！");
				get("reportDateBegin").focus();
				return false;
			}
	  	}else{
	  		ds = get("reportDateEnd").value;
	  		if (ds=="") {
				alert("请选择时间！");
				get("reportDateEnd").focus();
				return false;
			}
	  	}
		  var obj=get("dfzwPunish");
		  //if(!chkNull(obj.chargeMan,'请填写单位负责人')){
		  	 //return false;
		  //}else 
		  if(!chkNull(obj.fillMan,'请填写填表人')){
		  	 return false;
		  }else if(!chkNull(obj.tel,'请填写联系电话')){
		  	 return false;
		  }else  if(!chkNull(obj.fillDate,'请填写填报日期')){
		  	 return false;
		  }
		  if(!confirm("您选择的日期是"+ds+",确定保存吗?")){
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
	var rt = get("reportType").value;
	if (date == "") {
		get("reportDateEnd").value = "";
		return;
	}
	if(rt == 1){
		var d = new Date(date.substring(0,4),date.substring(5,7)-1,date.substring(8,10));
		d.setDate(d.getDate()+6);
		var d_str = d.getYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
		get("reportDateEnd").value = d_str;
	}else{
		var d = new Date(date.substring(0,4),date.substring(5,7),0)
		var d2 = new Date(date.substring(0,4),date.substring(5,7)-1,date.substring(8,10));
		if((d-d2)!=0){
			alert("请选择最后月的最后一天!");
			return false;
		}
		get("reportDateBegin").value = date.substring(0,4)+"-01-01";
	}
	get("reportDateReport_hidden").value = get("reportDateBegin").value;
	get("reportDateEnd_hidden").value = get("reportDateEnd").value;
	get("reportType_hidden").value = rt;
	$('ajaxForm').set("send",{onComplete:function(response){
		get("sub").disabled = response=="true"?false:true;
		//get("div_remind").innerHTML = response=="true"?"":"对应您选择的周报日期已存在数据，请在原有数据中修改，防止重复数据。";
		if( response!="true"){
			alert("对应您选择的日期已存在数据，请在原有数据中修改，防止重复数据。");
			get("div_remind").innerHTML = "对应您选择的日期已存在数据，请在原有数据中修改，防止重复数据。";
		}else{
			get("div_remind").innerHTML = "";
		}
	}});
	get("ajaxForm").send();
	
}
</script>
</head>
<body>
<form id="ajaxForm" name="ajaxForm" method="post" action="checkAllowCreate.xhtml">
<input type="hidden" name="entity.reportDateBegin" id="reportDateReport_hidden" />
<input type="hidden" name="entity.reportType" id="reportType_hidden" />
<input type="hidden" name="entity.reportDateEnd" id="reportDateEnd_hidden" />
</form>
<form name="dfzwPunish" id="dfzwPunish" method="post">
 <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0" id="tb">
     <tr>
      <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10"></td>
        </tr>
		 <tr>
          <tH colspan="3" style="font-size:18px" class="head" align="center">
    <c:choose>
			  	<c:when test="${ entity.reportType eq 1}">
					  组织督查检查情况和对非法违法、违规违章行为实施处罚情况周报表
		  		</c:when>
		  		<c:otherwise>
		  			组织督查检查情况和对非法违法、违规违章行为实施处罚情况月报表
		  		</c:otherwise>
			  </c:choose>      
          
          </tH>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位:<c:choose><c:when test="${!empty(entity.user)}">${entity.user.fkUserInfo.userCompany}</c:when>
		  <c:otherwise>${userDetail.userCompany}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red; font-size:12px;">（如填报单位名称不对，请到个人信息修改！）</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  	<c:choose>
				  <c:when test="${ entity.reportType eq 1}">
		  			<input type="hidden" name="entity.reportType" value="1" id="reportType">
				  	<input type="text" id="reportDateBegin"
						name="entity.reportDateBegin" maxLength=15 size=15
						value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
						readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
						name="entity.reportDateEnd" maxLength=15 size=15
						value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
						readOnly="true" />
				  </c:when>
				  <c:otherwise>
		  				<input type="hidden" name="entity.reportType" value="2" id="reportType">
		  				<input type="text" id="reportDateBegin"
							name="entity.reportDateBegin" maxLength=15 size=15
							value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
							readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
							name="entity.reportDateEnd" maxLength=15 size=15
							value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
							readOnly="true" />
				  </c:otherwise>
			  </c:choose>
			</td>
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
			  <td style="text-align:left;font-weight:bold;" nowrap="nowrap">${item.value}</td><input type="hidden" name="details[${status.count-1}].type" value="${item.key}"/>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="checkTeam_${status.count-1}" value="${details[status.count-1].checkTeam}" name="details[${status.count-1}].checkTeam"  class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="closed_${status.count-1}" value="${details[status.count-1].checkPerson}" name="details[${status.count-1}].checkPerson" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="companyNum_${status.count-1}" value="${details[status.count-1].companyNum}" name="details[${status.count-1}].companyNum" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
			  <td align="center" ><input type="text" style="IME-MODE:disabled;" id="warn_${status.count-1}" value="${details[status.count-1].warn}" name="details[${status.count-1}].warn" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="orderRectify_${status.count-1}" value="${details[status.count-1].orderRectify}" name="details[${status.count-1}].orderRectify" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="confiscate_${status.count-1}" value="${details[status.count-1].confiscate}" name="details[${status.count-1}].confiscate" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="stopProduct_${status.count-1}" value="${details[status.count-1].stopProduct}" name="details[${status.count-1}].stopProduct" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="tempDetain_${status.count-1}" value="${details[status.count-1].tempDetain}" name="details[${status.count-1}].tempDetain" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="close_${status.count-1}" value="${details[status.count-1].close}" name="details[${status.count-1}].close" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="administrativeDetain_${status.count-1}" value="${details[status.count-1].administrativeDetain}" name="details[${status.count-1}].administrativeDetain" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="criminalResponsibility_${status.count-1}" value="${details[status.count-1].criminalResponsibility}" name="details[${status.count-1}].criminalResponsibility" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="penalty_${status.count-1}" value="${details[status.count-1].penalty}" name="details[${status.count-1}].penalty" class="input" maxlength="8" size="4" onchange="calculate(this,3)" onKeyDown="checkNumber(this,3)" readonly/></td>
            </tr>
			</c:forEach>
            
			
			<tr id="row11">
		     <td style="text-align:left;font-weight:bold;" nowrap="nowrap">合计</td>
		     <td align="center" id="td_bcd"><input type="text" style="IME-MODE:disabled;" id="checkTeamAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_bcg"><input type="text" style="IME-MODE:disabled;" id="closedAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_pg"><input type="text" style="IME-MODE:disabled;" id="companyNumAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td align="center" id="td_pcg"><input type="text" style="IME-MODE:disabled;" id="warnAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_pcd"><input type="text" style="IME-MODE:disabled;" id="orderRectifyAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tg"><input type="text" style="IME-MODE:disabled;" id="confiscateAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcg"><input type="text" style="IME-MODE:disabled;" id="stopProductAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcd"><input type="text" style="IME-MODE:disabled;" id="tempDetainAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcd"><input type="text" style="IME-MODE:disabled;" id="closeAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcd"><input type="text" style="IME-MODE:disabled;" id="administrativeDetainAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcd"><input type="text" style="IME-MODE:disabled;" id="criminalResponsibilityAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center" id="td_tcd"><input type="text" style="IME-MODE:disabled;" id="penaltyAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		   </tr>
		   </tbody>
   <tr>
       <td colspan="15"><table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
	   <td nowrap="nowrap">单位负责人：
	     <input type="text" id="chargeMan"  name="entity.chargeMan" value="${entity.chargeMan}" class="input" maxlength="8" size="15"/></td>
	   <td align="center">填表人：
	     <input type="text"  id="fillMan" value="${entity.fillMan}" name="entity.fillMan" class="input" maxlength="8" size="12"/></td>
	   <td width="13%" align="center">联系电话：</td>
	   <td width="15%"><input type="text" id="tel" name="entity.tel" class="input" maxlength="13" size="15" style="ime-mode:disabled;"  value="${entity.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="12%" align="center">填报日期： </td>
	   <td width="12%"><input type="text" id="fillDate" name="entity.fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"  onMouseDown="checkDateOnMouse(this)" value="<s:date name="entity.fillDate" format="yyyy-MM-dd" />"/><a href="javascript:void(0)" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a></td>
	   </tr>
	   <tr>
		  <td colspan=6 style="font-size:12px;padding-left:30px;text-align:left;">
		 <c:choose>
			  	<c:when test="${ entity.reportType eq 1}">
					 有关要求： 1.各地区要认真填写本表，并于每周五12时前以网上填报、传真（或电子邮件）等方式报送至国务院安委会办公室。
					  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  2.统计数据为专项行动开展以来的累计数据。
		  		</c:when>
		  		<c:otherwise>
		  			 有关要求：统计数据为本年1月份以来的累计数据，请于每月3日以网上填报方式报送至省安委会办公室。
		  		</c:otherwise>
		  	</c:choose>
		  </td>
	   </tr>
	   </table></td>
    </tr>
	
          </table></td>
        </tr>
		
      </table> 
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
   <tr>
	 <td width="28%" align="center">
	 <div id="div_remind" style="font-size:12px;color:red;text-align:left"></div>
	 <c:if test="${entity.view!=true}"><input id="sub" type="button" class="but_2" value="保　存" onClick="submitCreate();" /></c:if>
		 <input id="back" type="button" class="but_2" value="返　回"  onClick="javaScript:history.back();"/>
		 </td>
	</tr>
</table>
<input type="hidden" id="id" name="entity.id" value="${entity.id}"/>
<input type="hidden" id="isProvince" name="entity.province" value="${entity.province}"/>
<input type="hidden" id="roleDepic" name="entity.roleDepic" value="${entity.roleDepic}"/>
</form>
<script>
var j = jQuery.noConflict();
for(var i = 1; i < 13; i++){
	var spanTo = 0;
	var rows = j("#tf>tr");
	for (var z = 0; z < rows.length - 1; z++){
		var val = j("#" + rows[z].id + " td:eq(" +(i)+") input:first-child").val();
		spanTo += parseFloat(val?val:0);
	}
	if (!isNaN(spanTo)){
		j("#tf tr:last td:eq("+(i)+") input:first-child").val(spanTo);
	}
}

function calculate(obj,type){
	
	var idTem = obj.id.split("_")[0];
	var args3 = new Array();
	for(var i = 0; i < 11; i++){
		args3[i] = j("#" + idTem + "_" + i)[0];
	}
	var result3 = j("#" + idTem + "All")[0];
	sumSons(obj, args3, result3, type=="1"?true:false);
}

</script>
</body>
</html>

