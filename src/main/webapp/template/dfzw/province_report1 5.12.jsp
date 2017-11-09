<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<title>
	<c:choose>
	  	<c:when test="${ entity.reportType eq 1}">
			  安全生产领域“打非治违”情况周报表
  		</c:when>
  		<c:otherwise>
  			安全生产领域“打非治违”情况月报表
  		</c:otherwise>
	  </c:choose>
</title>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
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
	  var objs = j.makeArray(j("#tf input:text"));
	  var rt = get("reportType").value;
	  var ds = "";
	  if(formValidator.validate()){
	  	if(objs.length) {
			for(var i=0;i<objs.length;i++) {
				if(!checkNum(objs[i],"请填写正整数！")){
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
		  if(!confirm("您选择的日期是"+ds+",确定保存吗?")){
			 return false;
		  }
		  <c:choose>
			  <c:when test="${!empty(entity)&&entity.id>0}">
				obj.action="updateForCity.xhtml";
			  </c:when>
			  <c:otherwise>
		 		 obj.action="createForCity.xhtml";
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
			get("sub").disabled =true;
			return false;
		}
		get("reportDateBegin").value = date.substring(0,4)+"-01-01";
	}
	get("reportDateReport_hidden").value = get("reportDateBegin").value;
	get("reportDateEnd_hidden").value = get("reportDateEnd").value;
	get("reportType_hidden").value = rt;
	$('ajaxForm').set("send",{onComplete:function(response){
		get("sub").disabled = response=="true"?false:true;
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
<form id="ajaxForm" name="ajaxForm" method="post" action="checkAllowCreate3.xhtml">
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
					  安全生产领域“打非治违”情况周报表
		  		</c:when>
		  		<c:otherwise>
		  			安全生产领域“打非治违”情况月报表
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
						value="${entity.reportDateBegin}" class="Wdate"
						onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}',disabledDays:[0,1,2,4,5,6],errDealMode:1})"
						readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
						name="entity.reportDateEnd" maxLength=15 size=15
						value="${entity.reportDateEnd}" class="Wdate"
						readOnly="true" />
				  </c:when>
				  <c:otherwise>
		  				<input type="hidden" name="entity.reportType" value="2" id="reportType">
					  	<input type="text" id="reportDateBegin"
						name="entity.reportDateBegin" maxLength=15 size=15
						value="${entity.reportDateBegin}" class="Wdate"
						readOnly="true" onchange="changeReportDateBegin(this.value);" />
						至
						<input type="text" id="reportDateEnd" name="entity.reportDateEnd" maxLength=15 size=15 value="${entity.reportDateEnd}" class="Wdate" readOnly="true" 
						onchange="changeReportDateBegin(this.value);" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M\-%d\'}',errDealMode:1})"/>
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
              <td width="10%">行业领域</td>
              <td width="7%">打击非法<br/>违法、治<br/>理纠正违<br/>规违章行<br/>为</td>
              <td width="8%">无证、证照不<br/>全或过期、超<br/>许可证范围从事<br/>生产经营建<br/>设，以及倒卖<br/>、出租、出借<br/>或以其他形式<br/>
              非法转让安全<br/>生产许可证的</td>
              <td width="7%">关闭取缔<br/>后又擅自<br/>生产经营<br/>建设的，<br/>应关未关<br/>或关闭不<br/>到位的</td>
              <td width="7%">停产整顿<br/>、整合技<br/>改未经验<br/>收擅自组<br/>织生产及<br/>违反建设<br/>项目安全<br/>设施、职<br/>业卫生“<br/>三同时”<br/>规定的</td>
              <td width="6%">瞒报谎<br/>报事<br/>故，以<br/>及重大<br/>隐患隐<br/>瞒不报<br/>或不按<br/>规定期<br/>限予以<br/>整治的</td>
              <td width="6%">拒不执<br/>行安全<br/>监管监<br/>察指令<br/>、抗拒<br/>安全执<br/>法的</td>
              <td width="6%">非法用<br/>工、无<br/>证上岗<br/>的</td>
              <td width="7%">作业规程<br/>不完善，<br/>缺乏针对<br/>性和可操<br/>作性，以<br/>及现场管<br/>理混乱、<br/>违章操作<br/>、违章指<br/>挥和违反<br/>劳动纪律<br/>的</td>
              <td width="7%">安全生产<br/>工艺系统<br/>、技术装<br/>备、监控<br/>设施、作<br/>业环境、<br/>劳动防护<br/>用品配备<br/>不符合规<br/>定要求的</td>
              <td width="6%">隐患排<br/>查治理<br/>制度不<br/>健全、<br/>责任不<br/>明确、<br/>措施不<br/>落实、<br/>整改不<br/>到位的</td>
              <td width="6%">重大基<br/>础设施<br/>建设安<br/>全制度<br/>不完善<br/>、管理<br/>措施落<br/>实不到<br/>位的</td>
              <td width="7%">应急救援<br/>队伍、装<br/>备不健<br/>全，应急<br/>预案制定<br/>修订演练<br/>不及时，<br/>以及自救<br/>装备配备<br/>不足、使<br/>用培训不<br/>够的</td>
              <td width="6%">新材料<br/>、新设<br/>计、新<br/>装备、<br/>新技术<br/>未经安<br/>全监测<br/>核准投<br/>入使用<br/>的</td>
              <td width="6%">其他违<br/>反安全<br/>生产法<br/>律、法<br/>规、规<br/>章的生<br/>产经营<br/>建设行<br/>为</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>合计</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
              <td>（起）</td>
            </tr><!-- 
            <tr>
              <td>&nbsp;</td>
              <td>0</td>
              <td>1</td>
              <td>2</td>
              <td>3</td>
              <td>4</td>
              <td>5</td>
              <td>6</td>
              <td>7</td>
              <td>8</td>
              <td>9</td>
              <td>10</td>
              <td>11</td>
              <td>12</td>
              <td>13</td>
            </tr>-->
			<tbody id="tf">
			<c:forEach var="item" items="${types}" varStatus="status">
            <tr id="row${status.count-1}">
			  <td style="text-align:left;font-weight:bold;" nowrap="nowrap">${item.value}</td><input type="hidden" name="details[${status.count-1}].type" value="${item.key}"/>
              <td align="center" ><input type="text" id="xiaoji_${status.count-1}"  class="input" size="4" readonly/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="wuzheng_${status.count-1}" value="${details[status.count-1].wuzheng}" name="details[${status.count-1}].wuzheng"  class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="guanbi_${status.count-1}" value="${details[status.count-1].guanbi}" name="details[${status.count-1}].guanbi" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="tingchan_${status.count-1}" value="${details[status.count-1].tingchan}" name="details[${status.count-1}].tingchan" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
			  <td align="center" ><input type="text" style="IME-MODE:disabled;" id="manbao_${status.count-1}" value="${details[status.count-1].manbao}" name="details[${status.count-1}].manbao" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="jubuzhixing_${status.count-1}" value="${details[status.count-1].jubuzhixing}" name="details[${status.count-1}].jubuzhixing" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="feifayonggong_${status.count-1}" value="${details[status.count-1].feifayonggong}" name="details[${status.count-1}].feifayonggong" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="zuoyeguicheng_${status.count-1}" value="${details[status.count-1].zuoyeguicheng}" name="details[${status.count-1}].zuoyeguicheng" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="gongyi_${status.count-1}" value="${details[status.count-1].gongyi}" name="details[${status.count-1}].gongyi" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="zhidubujianquan_${status.count-1}" value="${details[status.count-1].zhidubujianquan}" name="details[${status.count-1}].zhidubujianquan" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="zhongda_${status.count-1}" value="${details[status.count-1].zhongda}" name="details[${status.count-1}].zhongda" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="yingji_${status.count-1}" value="${details[status.count-1].yingji}" name="details[${status.count-1}].yingji" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="xincailiao_${status.count-1}" value="${details[status.count-1].xincailiao}" name="details[${status.count-1}].xincailiao" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
              <td align="center" ><input type="text" style="IME-MODE:disabled;" id="qita_${status.count-1}" value="${details[status.count-1].qita}" name="details[${status.count-1}].qita" class="input" maxlength="8" size="4" onchange="calculate(this,1)" onKeyDown="checkNumber(this,1)"/></td>
            </tr>
			</c:forEach>
			<tr id="row11">
		     <td style="text-align:left;font-weight:bold;" nowrap="nowrap">合计</td>
		     <td align="center" ><input type="text" id="xiaojiAll" class="input" size="4" readonly/></td>
		     <td align="center"><input type="text" style="IME-MODE:disabled;" id="wuzhengAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="guanbiAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="tingchanAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td align="center"><input type="text" style="IME-MODE:disabled;" id="manbaoAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="jubuzhixingAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="feifayonggongAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="zuoyeguichengAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="gongyiAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="zhidubujianquanAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="zhongdaAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="yingjiAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="xincailiaoAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		     <td  align="center"><input type="text" style="IME-MODE:disabled;" id="qitaAll"   class="input" maxlength="8" size="4" value="0" readonly/></td>
		   </tr>
		   </tbody>
   <tr>
       <td colspan="15"><table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
	   <td nowrap="nowrap">审核人：
	     <input type="text" id="chargeMan"  name="entity.chargeMan" value="${entity.chargeMan}" class="input" maxlength="8" size="15"/></td>
	   <td align="center">填表人：
	     <input type="text"  id="fillMan" value="${entity.fillMan}" name="entity.fillMan" class="input" maxlength="8" size="12"/></td>
	   <td width="13%" align="center">联系电话：</td>
	   <td width="15%"><input type="text" id="tel" name="entity.tel" class="input" maxlength="13" size="15" style="ime-mode:disabled;"  value="${entity.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="12%" align="center">填报日期： </td>
	   <td width="12%"><input type="text" id="fillDate" name="entity.fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"  onMouseDown="checkDateOnMouse(this)" value="<s:date name="entity.fillDate" format="yyyy-MM-dd" />"/><a href="javascript:void(0)" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a></td>
	   </tr>
	   <tr>
		  <td colspan=6 style="font-size:12px;padding-left:30px;text-align:left;padding-top: 10px;">
		<c:choose>
			  	<c:when test="${ entity.reportType eq 1}">
					  有关要求： 1.各地区要认真填写本表，并于每周五12时前以网上填报、传真（或电子邮件）等方式报送至国务院安委会办公室。
		  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  2.统计数据为<fmt:formatDate value="${entity.reportDateBegin}" pattern="yyyy"/>年1月以来的统计数据。
		  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  3.打击非法违法行为合计=1+2+3+4+5+6+7+8+9+10+11+12+13项。
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
<input type="hidden" id="cityCreate" name="entity.cityCreate" value="true"/>
</form>
<script>
var j = jQuery.noConflict();
//横行统计
for(var i = 0; i < 12; i++){
	var rowTo = 0;
	var spans = j("#row" + (i) + " td:gt(1) input:first-child");
	for (var z = 0; z < spans.length; z++){
		rowTo += parseInt(spans[z].value?spans[z].value:0);
	}
	if (!isNaN(rowTo)){
		j("#row" + (i) + " td:eq(1) input:first-child").val(rowTo);
	}
}
for(var i = 1; i < 15; i++){
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
	obj.value=parseFloat(obj.value);
	var idTem = obj.id.split("_")[0];
	var index = obj.id.split("_")[1];
	var indexRowInputs = j("#row"+index+" td:gt(1) input:first-child");
	var rowTo = 0;
	for (var z = 0; z < indexRowInputs.length; z++){
		rowTo += parseInt(indexRowInputs[z].value!=""?indexRowInputs[z].value:0);
	}
	if (!isNaN(rowTo)){
		j("#row" + (index) + " td:eq(1) input:first-child").val(rowTo);
	}
	//j("#xiaojiAll").val(parseFloat(j("#xiaojiAll").val()?j("#xiaojiAll").val():0) + parseFloat(obj.value?obj.value:0));
	
	var args3 = new Array();
	for(var i = 0; i < 11; i++){
		args3[i] = j("#" + idTem + "_" + i)[0];
	}
	var result3 = j("#" + idTem + "All")[0];
	sumSons(obj, args3, result3, type=="1"?true:false);

	var args4 = new Array();
	for(var i = 0; i < 11; i++){
		args4[i] = j("#xiaoji_" + i)[0];
	}
	var result4 = j("#xiaojiAll")[0];
	sumSons(obj, args4, result4, type=="1"?true:false);
}

</script>
</body>
</html>

