<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript">
var formValidator=Validator.setup({   
	form : 'otherForm',
	configs : 'attribute,tag',
	warns : 'color,alert',
	color : {warn :'black', pass:'black'}
});  
function submitCreate(){
	  if(formValidator.validate()){

	  if(getAll("shouldTroubleshooting").length) {
			for(var i=0;i<getAll("shouldTroubleshooting").length;i++) {
				if(!checkNum(getAll("shouldTroubleshooting")[i],"请填写正整数！")){
					getAll("shouldTroubleshooting")[i].focus();
					return false;
				}
			}
		} else {
			if(!checkNum(get("shouldTroubleshooting"),"请填写正整数！")){
				get("shouldTroubleshooting").focus();
				return false;
			}
		}
			
	  	if(getAll("company").length) {
			for(var i=0;i<getAll("company").length;i++) {
				if(!checkNum(getAll("company")[i],"请填写正整数！")){
					getAll("company")[i].focus();
					return false;
				}
			}
		} else {
			if(!checkNum(get("company"),"请填写正整数！")){
				get("company").focus();
				return false;
			}
		}
		
		if(getAll("generalDanger").length) {
			for(var i=0;i<getAll("generalDanger").length;i++) {
				if(!checkNum2(getAll("generalDanger")[i],"请填写整数！")){
					getAll("generalDanger")[i].focus();
					return false;
				}
			}
		} else {
			if(!checkNum2(get("generalDanger"),"请填写整数！")){
				get("generalDanger").focus();
				return false;
			}
		}
		
		if(getAll("generalDangerGovern").length) {
			for(var i=0;i<getAll("generalDangerGovern").length;i++) {
				if(!checkNum2(getAll("generalDangerGovern")[i],"请填写整数！")){
					getAll("generalDangerGovern")[i].focus();
					return false;
				}
			}
		} else {
			if(!checkNum2(get("generalDangerGovern"),"请填写整数！")){
				get("generalDangerGovern").focus();
				return false;
			}
		}
		if (get("year").value=="0" || get("year").value=="") {
			alert("请选择年份！");
			get("year").focus();
			return false;
		} else if(get("month").value=="0" || get("month").value=="") {
			alert("请选择月份！");
			get("month").focus();
			return false;
		}
		  var obj=get("otherForm");
		  if(!chkNull(obj.chargeMan,'请填写单位负责人')){
		  	 return false;
		  }else if(!chkNull(obj.fillMan,'请填写填表人')){
		  	 return false;
		  }else if(!chkNull(obj.tel,'请填写联系电话')){
		  	 return false;
		  }else  if(!chkNull(obj.fillDate,'请填写填报日期')){
		  	 return false;
		  }
		  if (get("shouldTroubleshooting_t") && get("company_t")) {
				if (parseFloat(get("shouldTroubleshooting_t").value) < parseFloat(get("company_t").value)) {
					alert("应排查企业数必须大于等于实际排查数！");
					return false;
				}
			}
		  if(!confirm("您选择的日期是"+get("year").value+"年"+get("month").value+"月,确定保存吗?")){
			 return false;
		  }
		  <c:choose>
			  <c:when test="${!empty(mineReport)}">
				obj.action="updateOther.xhtml";
				get("id").disabled = false;
			  </c:when>
			  <c:otherwise>
		 		 obj.action="createOther.xhtml";
			  </c:otherwise>
		  </c:choose>
		  obj.submit();
		  get("sub").disabled = true;
	  }
}
function openWindow(tradeType,troubleType) {
	if(get("id").value==""||get("id").value==null||get("id").value=="-1"||get("id").value=="0"){
		alert('请先保存报表,然后再添加重大隐患!');
		return false;
	}
	var reportMonth = get("year").value+"-"+get("month").value;
	if (get("year").value=="0" || get("year").value=="") {
		if ("${mineReport.reportMonth}"!="") {
			reportMonth = "${mineReport.reportMonth}";
		} else if("${reportMonth}"!="") {
			reportMonth = "${reportMonth}";
		}else {
			alert("请选择年份！");
			get("year").focus();
			return false;
		}
	} else if(get("month").value=="0" || get("month").value=="") {
		if ("${mineReport.reportMonth}"!="") {
			reportMonth = "${mineReport.reportMonth}";
		} else if("${reportMonth}"!="") {
			reportMonth = "${reportMonth}";
		}else {
			alert("请选择月份！");
			get("month").focus();
			return false;
		}
	}
	get("troubleType").value=troubleType;
	get("tradeType").value=tradeType;
	get("reportMonth").value=reportMonth;
	get("reportState").value="${mineReport.state}";
	get("bigTroubleForm").submit();
}
var especial = "9";
</script>
<body>
<form id="bigTroubleForm" name="bigTroubleForm" method="post" action="../bigTrouble/loadBigTroubles.xhtml">
<input type="hidden" name="mineId" id="mineId" value="${mineReport.id}"/>
<input type="hidden" name="reportState" id="reportState"/>
<input type="hidden" name="tableType" id="tableType" value="2"/>
<input type="hidden" name="troubleType" id="troubleType"/>
<input type="hidden" name="tradeType" id="tradeType"/>
<input type="hidden" name="reportMonth" id="reportMonth"/>
<input type="hidden" name="tableName" value="other"/>
</form>
<form name="otherForm" id="otherForm" method="post">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
    <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" height="50" class="head">其他重点行业领域安全生产隐患排查治理情况统计表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red; font-size:12px;">（如填报单位名称不对，请到个人信息修改！）</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  截止到<select id="year" name="mineReport.year" style="width:55px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" name="mineReport.month" style="width:45px;" onChange="window.location='createOtherInit.xhtml?mineReport.reportMonth='+get('year').value+'-'+this.value;">
		  	<c:forEach var="month" items="${months}" varStatus="status"><c:choose><c:when test="${month.choose||fn:split(mineReport.reportMonth,'-')[1]==month.month}"><option value="${month.month}">${month.month}</option></c:when><c:otherwise><optgroup label="${month.month}"></optgroup></c:otherwise></c:choose></c:forEach>
		  </select>月<span id="day"></span>日为止&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
      </table>
	  </td>
    </tr>
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" class="table_list" style="td:expression(this.noWrap=true)">
        <tr height="20">
          <td rowspan="4" align="center" width="20%">行业和领域</td>
          <td height="10" colspan=3 width="15%">开展排查治理事故<br/>隐患的生产经营单位</td>
          <td colspan="3" width="15%"><center>一般事故隐患</center></td>
          <td colspan="8" width="40%"><center> 重大事故隐患</center></td>
          <td rowspan="3" width="10%" nowrap><center>累计落实隐<br/>患治理资金</center></td>
        </tr>
        <tr height="20">
          <td rowspan="2" nowrap><center>应排查治<br/>理事故隐<br/>患的生产<br/>经营单位</center></td>
          <td rowspan="2" nowrap><center>实际排查<br/>治理事故<br/>隐患的生<br/>产经营单<br/>位</center></td>
          <td rowspan="2" nowrap><center>覆<br/>盖<br/>率</center></td>
          <td rowspan="2" nowrap><center>排查一般<br/>事故隐患</center></td>
          <td rowspan="2" nowrap><center>其中：<br/>已整改</center></td>
          <td rowspan="2" nowrap><center>整<br/>改<br/>率</center></td>
          <td height="10" colspan="3" nowrap><center>排查治理重大隐患</center></td>
          <td colspan="2" nowrap><center>列入治理计划</center></td>
          <td colspan="3" nowrap><center>其中：挂牌督办</center></td>
        </tr>
        <tr height="20">
          <td nowrap><center>排查重<br/>大事故<br/>隐患</center></td>
          <td nowrap><center>其中：<br/>已整改<br/>销号</center></td>
          <td nowrap><center>整<br/>改<br/>率</center></td>
          <td nowrap><center>列入治<br/>理计划<br/>的重大<br/>事故隐<br/>患</center></td>
          <td nowrap><center>其中：<br/>达到“<br/>五到位<br/>”要求<br/>的</center></td>
          <td nowrap><center>挂牌督<br/>办&nbsp;&nbsp;的<br/>重大事<br/>故隐患</center></td>
          <td nowrap><center>其中：<br/>省级挂<br/>牌督办</center></td>
          <td nowrap><center>其中：<br/>地市级<br/>挂牌督<br/>办</center></td>
        </tr>
        <tr height="20">
          <td height="20"><center>(家)</center></td>
          <td><center>(家)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(万元)</center></td>
   </tr><tr>
	  <td width="18%" class="td_left"><p><strong>1.道路运输企业</strong></p><input type="hidden" name="mineReportDetails[0].type" value="18"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[0].shouldTroubleshooting}" name="mineReportDetails[0].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[0],this,'zg1_0',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[0].company}" name="mineReportDetails[0].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[0],'zg1_0',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_0">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[0].generalDanger}" name="mineReportDetails[0].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[0],this,'zg_0',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[0].generalDangerGovern}" name="mineReportDetails[0].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[0],'zg_0',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_0">&nbsp;</td>
	  <td align="center"><a href="javaScript:;" onClick="openWindow(18,1);">${statistics[0].bigTrouble}</a></td>
	  <td align="center"><a href="javaScript:;" onClick="openWindow(18,2);">${statistics[0].bigTroubleGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><a href="javaScript:;" onClick="openWindow(18,3);">${statistics[0].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,4);">${statistics[0].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,5);">${statistics[0].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,6);">${statistics[0].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,7);">${statistics[0].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[0].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[0].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>2.公路养护施工企业</strong></p><input type="hidden" name="mineReportDetails[1].type" value="19"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[1].shouldTroubleshooting}" name="mineReportDetails[1].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[1],this,'zg1_1',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[1].company}" name="mineReportDetails[1].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[1],'zg1_1',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_1">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[1].generalDanger}" name="mineReportDetails[1].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[1],this,'zg_1',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[1].generalDangerGovern}" name="mineReportDetails[1].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[1],'zg_1',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_1">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(19,1);">${statistics[1].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(19,2);">${statistics[1].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(19,3);">${statistics[1].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,4);">${statistics[1].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,5);">${statistics[1].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,6);">${statistics[1].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,7);">${statistics[1].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[1].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[1].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>3.水上运输企业</strong></p><input type="hidden" name="mineReportDetails[2].type" value="20"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[2].shouldTroubleshooting}" name="mineReportDetails[2].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[2],this,'zg1_2',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[2].company}" name="mineReportDetails[2].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[2],'zg1_2',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_2">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[2].generalDanger}" name="mineReportDetails[2].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[2],this,'zg_2',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[2].generalDangerGovern}" name="mineReportDetails[2].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[2],'zg_2',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_2">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(20,1);">${statistics[2].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(20,2);">${statistics[2].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(20,3);">${statistics[2].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,4);">${statistics[2].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,5);">${statistics[2].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,6);">${statistics[2].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,7);">${statistics[2].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[2].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[2].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>4.铁路运输企业</strong></p><input type="hidden" name="mineReportDetails[3].type" value="21"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[3].shouldTroubleshooting}" name="mineReportDetails[3].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[3],this,'zg1_3',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[3].company}" name="mineReportDetails[3].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[3],'zg1_3',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_3">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[3].generalDanger}" name="mineReportDetails[3].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[3],this,'zg_3',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[3].generalDangerGovern}" name="mineReportDetails[3].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[3],'zg_3',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_3">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(21,1);">${statistics[3].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(21,2);">${statistics[3].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(21,3);">${statistics[3].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,4);">${statistics[3].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,5);">${statistics[3].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,6);">${statistics[3].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,7);">${statistics[3].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[3].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[3].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
      <td width="18%"><p><strong>5.航空公司</strong></p><input type="hidden" name="mineReportDetails[4].type" value="22"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[4].shouldTroubleshooting}" name="mineReportDetails[4].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[4],this,'zg1_4',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[4].company}" name="mineReportDetails[4].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[4],'zg1_4',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_4">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[4].generalDanger}" name="mineReportDetails[4].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[4],this,'zg_4',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
  	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[4].generalDangerGovern}" name="mineReportDetails[4].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[4],'zg_4',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
      <td align="center" id="zg_4">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(22,1);">${statistics[4].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(22,2);">${statistics[4].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(22,3);">${statistics[4].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,4);">${statistics[4].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,5);">${statistics[4].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,6);">${statistics[4].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,7);">${statistics[4].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[4].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[4].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>6.机场和油料企业</strong></p><input type="hidden" name="mineReportDetails[5].type" value="23"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[5].shouldTroubleshooting}" name="mineReportDetails[5].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[5],this,'zg1_5',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[5].company}" name="mineReportDetails[5].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[5],'zg1_5',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_5">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[5].generalDanger}" name="mineReportDetails[5].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[5],this,'zg_5',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[5].generalDangerGovern}" name="mineReportDetails[5].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[5],'zg_5',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_5">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(23,1);">${statistics[5].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(23,2);">${statistics[5].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(23,3);">${statistics[5].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,4);">${statistics[5].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,5);">${statistics[5].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,6);">${statistics[5].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,7);">${statistics[5].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[5].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[5].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td></tr><tr>
	<td width="18%"><p><strong>7.渔业企业</strong></p><input type="hidden" name="mineReportDetails[6].type" value="24"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[6].shouldTroubleshooting}" name="mineReportDetails[6].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(get('company')[6],this,'zg1_6',true)){gsumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[6].company}" name="mineReportDetails[6].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[6],'zg1_6',true)){sumSons(this,getAll('company'),get('shouldTroubleshootingGovern_t'),true);}"/></td>
	  <td align="center" id="zg1_6">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[6].generalDanger}" name="mineReportDetails[6].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(get('generalDangerGovern')[6],this,'zg_6',true)){gsumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[6].generalDangerGovern}" name="mineReportDetails[6].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[6],'zg_6',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_6">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(24,1);">${statistics[6].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(24,2);">${statistics[6].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(24,3);">${statistics[6].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,4);">${statistics[6].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,5);">${statistics[6].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,6);">${statistics[6].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,7);">${statistics[6].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[6].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[6].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>8.农机行业</strong></p><input type="hidden" name="mineReportDetails[7].type" value="25"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[7].shouldTroubleshooting}" name="mineReportDetails[7].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[7],this,'zg1_7',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[7].company}" name="mineReportDetails[7].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[7],'zg1_7',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_7">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[7].generalDanger}" name="mineReportDetails[7].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[7],this,'zg_7',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[7].generalDangerGovern}" name="mineReportDetails[7].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[7],'zg_7',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_7">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(25,1);">${statistics[7].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(25,2);">${statistics[7].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(25,3);">${statistics[7].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,4);">${statistics[7].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,5);">${statistics[7].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,6);">${statistics[7].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,7);">${statistics[7].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[7].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[7].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>9.水利工程</strong></p><input type="hidden" name="mineReportDetails[8].type" value="26"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[8].shouldTroubleshooting}" name="mineReportDetails[8].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[8],this,'zg1_8',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[8].company}" name="mineReportDetails[8].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[8],'zg1_8',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_8">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[8].generalDanger}" name="mineReportDetails[8].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[8],this,'zg_8',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[8].generalDangerGovern}" name="mineReportDetails[8].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[8],'zg_8',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_8">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(26,1);">${statistics[8].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(26,2);">${statistics[8].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(26,3);">${statistics[8].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,4);">${statistics[8].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,5);">${statistics[8].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,6);">${statistics[8].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,7);">${statistics[8].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[8].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[8].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>10.学校</strong></p><input type="hidden" name="mineReportDetails[10].type" value="28"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[10].shouldTroubleshooting}" name="mineReportDetails[10].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[9],this,'zg1_10',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[10].company}" name="mineReportDetails[10].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[9],'zg1_10',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_10">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[10].generalDanger}" name="mineReportDetails[10].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[9],this,'zg_10',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[10].generalDangerGovern}" name="mineReportDetails[10].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[9],'zg_10',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_10">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(28,1);">${statistics[10].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(28,2);">${statistics[10].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(28,3);">${statistics[10].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,4);">${statistics[10].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,5);">${statistics[10].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,6);">${statistics[10].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,7);">${statistics[10].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[10].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[10].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>11.商场、市场等人员密集场所</strong></p><input type="hidden" name="mineReportDetails[11].type" value="29"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[11].shouldTroubleshooting}" name="mineReportDetails[11].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[10],this,'zg1_11',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[11].company}" name="mineReportDetails[11].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[10],'zg1_11',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_11">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[11].generalDanger}" name="mineReportDetails[11].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[10],this,'zg_11',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[11].generalDangerGovern}" name="mineReportDetails[11].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[10],'zg_11',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_11">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(29,1);">${statistics[11].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(29,2);">${statistics[11].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*110}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(29,3);">${statistics[11].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,4);">${statistics[11].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,5);">${statistics[11].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,6);">${statistics[11].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,7);">${statistics[11].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[11].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[11].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>12.建筑施工企业</strong></p><input type="hidden" name="mineReportDetails[12].type" value="30"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[12].shouldTroubleshooting}" name="mineReportDetails[12].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[11],this,'zg1_12',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[12].company}" name="mineReportDetails[12].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[11],'zg1_12',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_12">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[12].generalDanger}" name="mineReportDetails[12].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[11],this,'zg_12',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[12].generalDangerGovern}" name="mineReportDetails[12].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[11],'zg_12',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_12">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(30,1);">${statistics[12].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(30,2);">${statistics[12].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(30,3);">${statistics[12].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,4);">${statistics[12].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,5);">${statistics[12].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,6);">${statistics[12].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,7);">${statistics[12].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[12].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[12].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>13.民爆器材生产企业</strong></p><input type="hidden" name="mineReportDetails[13].type" value="31"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[13].shouldTroubleshooting}" name="mineReportDetails[13].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[12],this,'zg1_13',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[13].company}" name="mineReportDetails[13].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[12],'zg1_13',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_13">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[13].generalDanger}" name="mineReportDetails[13].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[12],this,'zg_13',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[13].generalDangerGovern}" name="mineReportDetails[13].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[12],'zg_13',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_13">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(31,1);">${statistics[13].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(31,2);">${statistics[13].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(31,3);">${statistics[13].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,4);">${statistics[13].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,5);">${statistics[13].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,6);">${statistics[13].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,7);">${statistics[13].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[13].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[13].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
  	  <td width="18%"><p><strong>14.电力企业</strong></p><input type="hidden" name="mineReportDetails[14].type" value="32"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[14].shouldTroubleshooting}" name="mineReportDetails[14].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[13],this,'zg1_14',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[14].company}" name="mineReportDetails[14].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[13],'zg1_14',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_14">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[14].generalDanger}" name="mineReportDetails[14].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[13],this,'zg_14',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
      <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[14].generalDangerGovern}" name="mineReportDetails[14].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[13],'zg_14',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
      <td align="center" id="zg_14">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(32,1);">${statistics[14].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(32,2);">${statistics[14].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(32,3);">${statistics[14].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,4);">${statistics[14].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,5);">${statistics[14].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,6);">${statistics[14].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,7);">${statistics[14].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[14].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[14].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>15.机械制造企业</strong></p><input type="hidden" name="mineReportDetails[15].type" value="33"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[15].shouldTroubleshooting}" name="mineReportDetails[15].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[14],this,'zg1_15',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[15].company}" name="mineReportDetails[15].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[14],'zg1_15',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_15">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[15].generalDanger}" name="mineReportDetails[15].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[14],this,'zg_15',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[15].generalDangerGovern}" name="mineReportDetails[15].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[14],'zg_15',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_15">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(33,1);">${statistics[15].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(33,2);">${statistics[15].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(33,3);">${statistics[15].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,4);">${statistics[15].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,5);">${statistics[15].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,6);">${statistics[15].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,7);">${statistics[15].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[15].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[15].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" nowrap><p><strong>16.地铁施工（按项目部统计）</strong></p><input type="hidden" name="mineReportDetails[9].type" value="27"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[9].shouldTroubleshooting==null ? '0' : mineReportDetails[9].shouldTroubleshooting}" name="mineReportDetails[9].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[9+6],this,'zg1_9',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[9].company==null ? '0' : mineReportDetails[9].company}" name="mineReportDetails[9].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[9+6],'zg1_9',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>    
      <td align="center" id="zg1_9">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[9].generalDanger==null ? '0' : mineReportDetails[9].generalDanger}" name="mineReportDetails[9].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[9+6],this,'zg_9',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[9].generalDangerGovern==null ? '0' : mineReportDetails[9].generalDangerGovern}" name="mineReportDetails[9].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[9+6],'zg_9',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>    
      <td align="center" id="zg_9">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(27,1);">${statistics[9].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(27,2);">${statistics[9].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[9].bigTrouble==0||statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[9].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(27,3);">${statistics[9].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,4);">${statistics[9].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,5);">${statistics[9].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,6);">${statistics[9].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,7);">${statistics[9].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[9].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[9].planMoney==null ? '0.0' : mineReportDetails[9].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>17.道路交通事故多发点段</strong></p><input type="hidden" name="mineReportDetails[17].type" value="35"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[17].shouldTroubleshooting}" name="mineReportDetails[17].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[16],this,'zg1_17',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[17].company}" name="mineReportDetails[17].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[16],'zg1_17',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_17">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[17].generalDanger}" name="mineReportDetails[17].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[16],this,'zg_17',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[17].generalDangerGovern}" name="mineReportDetails[17].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[16],'zg_17',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_17">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(35,1);">${statistics[17].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(35,2);">${statistics[17].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[17].bigTrouble==0||statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[17].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(35,3);">${statistics[17].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,4);">${statistics[17].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,5);">${statistics[17].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,6);">${statistics[17].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,7);">${statistics[17].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[17].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[17].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>18.道路交通安全设施</strong></p><input type="hidden" name="mineReportDetails[18].type" value="36"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[18].shouldTroubleshooting}" name="mineReportDetails[18].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[17],this,'zg1_18',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[18].company}" name="mineReportDetails[18].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[17],'zg1_18',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_18">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[18].generalDanger}" name="mineReportDetails[18].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[17],this,'zg_18',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[18].generalDangerGovern}" name="mineReportDetails[18].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[17],'zg_18',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_18">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(36,1);">${statistics[18].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(36,2);">${statistics[18].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[18].bigTrouble==0||statistics[18].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[18].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(36,3);">${statistics[18].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,4);">${statistics[18].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,5);">${statistics[18].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,6);">${statistics[18].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,7);">${statistics[18].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[18].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[18].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>19.临水临崖危险路段</strong></p><input type="hidden" name="mineReportDetails[19].type" value="37"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[19].shouldTroubleshooting}" name="mineReportDetails[19].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[18],this,'zg1_19',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[19].company}" name="mineReportDetails[19].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[18],'zg1_19',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
      <td align="center" id="zg1_19">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[19].generalDanger}" name="mineReportDetails[19].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[18],this,'zg_19',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[19].generalDangerGovern}" name="mineReportDetails[19].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[18],'zg_19',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
      <td align="center" id="zg_19">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(37,1);">${statistics[19].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(37,2);">${statistics[19].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[19].bigTrouble==0||statistics[19].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[19].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(37,3);">${statistics[19].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,4);">${statistics[19].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,5);">${statistics[19].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,6);">${statistics[19].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,7);">${statistics[19].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[19].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[19].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>20.城市公共交通</strong></p><input type="hidden" name="mineReportDetails[20].type" value="38"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[20].shouldTroubleshooting}" name="mineReportDetails[20].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[19],this,'zg1_20',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[20].company}" name="mineReportDetails[20].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[19],'zg1_20',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_20">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[20].generalDanger}" name="mineReportDetails[20].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[19],this,'zg_20',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[20].generalDangerGovern}" name="mineReportDetails[20].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[19],'zg_20',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_20">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(38,1);">${statistics[20].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(38,2);">${statistics[20].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[20].bigTrouble==0||statistics[20].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[20].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(38,3);">${statistics[20].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,4);">${statistics[20].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,5);">${statistics[20].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,6);">${statistics[20].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,7);">${statistics[20].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[20].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[20].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>21.燃气</strong></p><input type="hidden" name="mineReportDetails[21].type" value="39"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[21].shouldTroubleshooting}" name="mineReportDetails[21].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[20],this,'zg1_21',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[21].company}" name="mineReportDetails[21].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[20],'zg1_21',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_21">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[21].generalDanger}" name="mineReportDetails[21].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[20],this,'zg_21',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[21].generalDangerGovern}" name="mineReportDetails[21].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[20],'zg_21',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_21">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(39,1);">${statistics[21].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(39,2);">${statistics[21].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[21].bigTrouble==0||statistics[21].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[21].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(39,3);">${statistics[21].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,4);">${statistics[21].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,5);">${statistics[21].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,6);">${statistics[21].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,7);">${statistics[21].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[21].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[21].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>22.旅游行业</strong></p><input type="hidden" name="mineReportDetails[22].type" value="40"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[22].shouldTroubleshooting}" name="mineReportDetails[22].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[21],this,'zg1_22',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[22].company}" name="mineReportDetails[22].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[21],'zg1_22',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_22">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[22].generalDanger}" name="mineReportDetails[22].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[21],this,'zg_22',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[22].generalDangerGovern}" name="mineReportDetails[22].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[21],'zg_22',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_22">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(40,1);">${statistics[22].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(40,2);">${statistics[22].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[22].bigTrouble==0||statistics[22].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[22].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(40,3);">${statistics[22].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,4);">${statistics[22].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,5);">${statistics[22].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,6);">${statistics[22].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,7);">${statistics[22].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[22].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[22].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>23.铁路</strong></p><input type="hidden" name="mineReportDetails[23].type" value="41"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[23].shouldTroubleshooting}" name="mineReportDetails[23].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[22],this,'zg1_23',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[23].company}" name="mineReportDetails[23].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[22],'zg1_23',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_23">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[23].generalDanger}" name="mineReportDetails[23].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[22],this,'zg_23',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[23].generalDangerGovern}" name="mineReportDetails[23].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[22],'zg_23',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_23">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(41,1);">${statistics[23].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(41,2);">${statistics[23].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[23].bigTrouble==0||statistics[23].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[23].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(41,3);">${statistics[23].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,4);">${statistics[23].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,5);">${statistics[23].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,6);">${statistics[23].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,7);">${statistics[23].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[23].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[23].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>24.医院</strong></p><input type="hidden" name="mineReportDetails[24].type" value="42"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[24].shouldTroubleshooting}" name="mineReportDetails[24].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[23],this,'zg1_24',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[24].company}" name="mineReportDetails[24].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[23],'zg1_24',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
      <td align="center" id="zg1_24">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[24].generalDanger}" name="mineReportDetails[24].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[23],this,'zg_24',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[24].generalDangerGovern}" name="mineReportDetails[24].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[23],'zg_24',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
      <td align="center" id="zg_24">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(42,1);">${statistics[24].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(42,2);">${statistics[24].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[24].bigTrouble==0||statistics[24].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[24].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(42,3);">${statistics[24].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,4);">${statistics[24].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,5);">${statistics[24].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,6);">${statistics[24].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,7);">${statistics[24].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[24].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[24].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>25.“三合一”场所</strong></p><input type="hidden" name="mineReportDetails[25].type" value="43"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[25].shouldTroubleshooting}" name="mineReportDetails[25].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[24],this,'zg1_25',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[25].company}" name="mineReportDetails[25].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[24],'zg1_25',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_25">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[25].generalDanger}" name="mineReportDetails[25].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[24],this,'zg_25',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[25].generalDangerGovern}" name="mineReportDetails[25].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[24],'zg_25',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_25">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(43,1);">${statistics[25].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(43,2);">${statistics[25].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[25].bigTrouble==0||statistics[25].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[25].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(43,3);">${statistics[25].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,4);">${statistics[25].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,5);">${statistics[25].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,6);">${statistics[25].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,7);">${statistics[25].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[25].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[25].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>26.出租房</strong></p><input type="hidden" name="mineReportDetails[26].type" value="44"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[26].shouldTroubleshooting}" name="mineReportDetails[26].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[25],this,'zg1_26',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[26].company}" name="mineReportDetails[26].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[25],'zg1_26',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_26">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[26].generalDanger}" name="mineReportDetails[26].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[25],this,'zg_26',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center" width="4%"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[26].generalDangerGovern}" name="mineReportDetails[26].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[25],'zg_26',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_26">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(44,1);">${statistics[26].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(44,2);">${statistics[26].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[26].bigTrouble==0||statistics[26].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[26].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(44,3);">${statistics[26].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,4);">${statistics[26].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,5);">${statistics[26].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,6);">${statistics[26].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,7);">${statistics[26].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[26].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[26].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>27.其他单位</strong></p><input type="hidden" name="mineReportDetails[16].type" value="34"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="shouldTroubleshooting" value="${mineReportDetails[16].shouldTroubleshooting}" name="mineReportDetails[16].shouldTroubleshooting" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('company')[15+11],this,'zg1_16',true)){sumSons(this,getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="company" value="${mineReportDetails[16].company}" name="mineReportDetails[16].company" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('shouldTroubleshooting')[15+11],'zg1_16',true)){sumSons(this,getAll('company'),get('company_t'),true);}"/></td>
	  <td align="center" id="zg1_16">&nbsp;</td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDanger" value="${mineReportDetails[16].generalDanger}" name="mineReportDetails[16].generalDanger" class="input" maxlength="8" size="2" onChange="if(getDivisor(getAll('generalDangerGovern')[15+11],this,'zg_16',true)){sumSons(this,getAll('generalDanger'),get('generalDanger_t'),true);}"/></td>
	  <td align="center"><input type="text" style="IME-MODE:disabled;" id="generalDangerGovern" value="${mineReportDetails[16].generalDangerGovern}" name="mineReportDetails[16].generalDangerGovern" class="input" maxlength="8" size="2" onChange="if(getDivisor(this,getAll('generalDanger')[15+11],'zg_16',true)){sumSons(this,getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);}"/></td>
	  <td align="center" id="zg_16">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(34,1);">${statistics[16].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(34,2);">${statistics[16].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(34,3);">${statistics[16].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,4);">${statistics[16].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,5);">${statistics[16].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,6);">${statistics[16].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,7);">${statistics[16].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input name="mineReportDetails[16].planMoney" type="text" class="input" id="planMoney" value="${mineReportDetails[16].planMoney}" size="2" maxlength="8" onChange="sumSons(this,getAll('planMoney'),get('planMoney_t'),false);"/></td>
	</tr><tr>
	  <td width="18%"><p><strong>合计</strong></p></td>
	  <td align="center" width="4%"><input type="text" id="shouldTroubleshooting_t" value="0" class="input" maxlength="8" size="2" readonly/></td>
	  <td align="center" width="4%"><input type="text" id="company_t" value="0" class="input" maxlength="8" size="2" readonly/></td>
	  <td align="center" id="zg1_t">&nbsp;</td>
	  <td align="center" width="4%"><input type="text" id="generalDanger_t" value="0" class="input" maxlength="8" size="2" readonly/></td>
	  <td align="center" width="4%"><input type="text" id="generalDangerGovern_t" value="0" class="input" maxlength="8" size="2" readonly/></td>
	  <td align="center" id="zg_t">&nbsp;</td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(0,1);">${statistics[27].bigTrouble}</a></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(0,2);">${statistics[27].bigTroubleGovern}</a></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTrouble==0||statistics[27].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[27].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><a href="javaScript:;" onClick="openWindow(0,3);">${statistics[27].bigTroubleNotGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,4);">${statistics[27].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,5);">${statistics[27].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,6);">${statistics[27].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,7);">${statistics[27].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center"><input type="text" id="planMoney_t" value="0.0" class="input" maxlength="8" size="2" readonly/></td></tr>
  	<tr>
       <td colspan="16">
		   <table width="100%" border="0">
			   <tr>
				   <td width="15%" nowrap="nowrap">单位负责人：</td>
				   <td width="8%"><input type="text" id="chargeMan" name="mineReport.chargeMan" class="input" maxlength="10" size="10" value="${mineReport.chargeMan}"/></td>
				   <td width="13%" align="center">填表人：</td>
				   <td width="8%"><input type="text" id="fillMan" name="mineReport.fillMan" class="input" maxlength="10" size="10" value="${mineReport.fillMan}"/></td>
				   <td width="13%" align="center">联系电话：</td>
				   <td width="15%"><input type="text" id="tel" name="mineReport.tel" class="input" maxlength="13" size="15" style="ime-mode:disabled;" value="${mineReport.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
				   <td width="16%" align="center">填报日期：</td>
				   <td width="12%"><input type="text" id="fillDate" name="mineReport.fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;" value="<s:date name="mineReport.fillDate" format="yyyy-MM-dd" />" onMouseDown="checkDateOnMouse(this)"/><a href="javascript:void(0);" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a>
				   <ui:v for="fillDate" rule="date" require="false" tips="格式为2000-01-01" warn="您输入的日期不存在或格式不正确"/></td>
			   </tr>
		   </table>
	   </td>
    </tr>	
  </table>
  
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>		 
		<td width="28%" align="center"><input id="sub" type="button" class="but_2" value="保  存"  onclick="submitCreate();"/>
		<c:if test="${!empty(mineReport)}"><input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/></c:if>
		<input type="hidden" id="type" name="mineReport.type" value="2"/>
		<input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled/>
		</td>
      </tr>
 	</table>
 	</table>
</form>
</body>
<script type="text/javascript">
<c:choose>
	<c:when test="${empty(mineReportDetails)}">
		var input = document.getElementsByTagName("input");
		for(var i = 0 ; i < input.length-9; i++) {
			if(input[i].type == "text") {
				input[i].value = "0";
			}
		}
	</c:when>
	<c:otherwise>
		//计算整改率
		for (var i=0;i<getAll('generalDanger').length;i++) {
			if(i<9) {
				if(getAll('shouldTroubleshooting')[i].value!=0)getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+i,true);
				getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+i,true);
			}else{
				if(i==9){
					if(getAll('shouldTroubleshooting')[15].value!=0)getDivisor(getAll('company')[15],getAll('shouldTroubleshooting')[15],'zg1_9',true);
					getDivisor(getAll('generalDangerGovern')[15],getAll('generalDanger')[15],'zg_9',true);
				}else if(i==26){
					if(getAll('shouldTroubleshooting')[26].value!=0)getDivisor(getAll('company')[26],getAll('shouldTroubleshooting')[26],'zg1_16',true);
					getDivisor(getAll('generalDangerGovern')[26],getAll('generalDanger')[26],'zg_16',true);
				}
				if(i<26){
					if(getAll('shouldTroubleshooting')[i].value!=0)getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+(i+1),true);
					getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+(i+1),true);
				}
			}
		}
		//合计
		sumSons(get('shouldTroubleshooting_t'),getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);
		sumSons(get('company_t'),getAll('company'),get('company_t'),true);
		sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
		sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
		sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);		
		//显示填报月份
		<c:if test="${!empty(mineReport.reportMonth)}">
			var year = "${mineReport.reportMonth}".split("-")[0];
			var month = "${mineReport.reportMonth}".split("-")[1];
			get("year").value=year;
			get("month").value=month;
		</c:if>	
		<c:if test="${mineReport.state>0}">
			for(var i=0;i<document.getElementsByTagName("input").length;i++) {
				if (document.getElementsByTagName("input")[i].type=="text") {
					if (document.getElementsByTagName("input")[i].type=="text") {
						document.getElementsByTagName("input")[i].disabled = true;	
					}	
				}
			}
			get("sub").style.display = "none";
			get("year").disabled = true;
			get("month").disabled = true;
			get("cal").style.display = "none";
		</c:if>
	</c:otherwise>
</c:choose>
<c:if test="${!empty(reportMonth)}">
	var year = "${reportMonth}".split("-")[0];
	var month = "${reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>	
if(get("month").value == ""){
	get("month").value=0;
}
/*
if (get("month").value=="") {
	var nowMonth = "${reportMonth}".split("-")[1];
	if (nowMonth=="") {
		nowMonth = "${mineReport.reportMonth}".split("-")[1];
	}
	for (var i=0;i<12;i++) {
		if (get("month").value=="") {
			get("month").value = (parseFloat(nowMonth)+1);
		} else {
			break;
		}
		if (nowMonth > 12) {
			break;
		}
	}
}
*/
</script>
</html>