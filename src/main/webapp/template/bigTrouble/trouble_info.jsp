<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<title>重大隐患</title>
<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
<script>
function validateSubmit(v){
	var obj=document.getElementById("bigTrouble");
	if(!chkNull(obj.companyName,'请填写单位名称')){
	  	return false;
	}else if(!chkNull(obj.address,'请填写地址')){
		return false;
	}else if(!chkNull(obj.content,'请填写内容')){
	    return false;
	}else if(obj.guapaiLevel.value == '0'){
		alert('请选择挂牌督办级别');
		obj.guapaiLevel.focus();
	    return false;
	}
	if(v==1){
  		obj.action="createBigTrouble.xhtml";
  	}else if(v==2){
  		obj.action="updateBigTrouble.xhtml";
  	}else if(v==3){
  		obj.action="updateBigTroubleRemark.xhtml";
  	}
 	obj.but_sub.disabled=true;
 	obj.submit();
}
</script>

</head>
<body> 
<form  name="bigTrouble" action="#" method="post">
   <table border="0" cellpadding="0" cellspacing="0" class="table_input">
<tr>
  <th colspan="4" class="input_title">重大隐患</th>
  </tr>
	<tr>
        <th width="304"><div align=center>填报月份:</div></th>
	    <td colspan="3"> <select id="year" name="year" style="width:55px;" disabled>
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}" <c:if test="${status.index=='1'}">selected</c:if>>${year}</option></c:forEach>
		  </select>年<select id="month" name="month" style="width:45px;" disabled>
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month}">${month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
        <th width="304"><div align=center>行业与领域:</div></th>
	    <td colspan="3">
			<select type=text name="bigTrouble.tradeType" disabled>
				 <option value="0">--请选择--</option>
				 <option value="1" ${bigTrouble.tradeType==1 ? 'selected' : ''}>金属非金属矿山企业</option>
				 <option value="2" ${bigTrouble.tradeType==2 ? 'selected' : ''}>尾矿库</option>
				 <option value="3" ${bigTrouble.tradeType==3 ? 'selected' : ''}>冶金企业</option>
				 <option value="4" ${bigTrouble.tradeType==4 ? 'selected' : ''}>有色企业</option>
				 <option value="5" ${bigTrouble.tradeType==5 ? 'selected' : ''}>重大基础设施</option>
				 <option value="6" ${bigTrouble.tradeType==6 ? 'selected' : ''}>&nbsp;&nbsp;船舶修造企业</option>
				 <option value="7" ${bigTrouble.tradeType==7 ? 'selected' : ''}>&nbsp;&nbsp;生产企业</option>
				 <option value="8" ${bigTrouble.tradeType==8 ? 'selected' : ''}>&nbsp;&nbsp;经营企业和单位</option>
				 <option value="9" ${bigTrouble.tradeType==9 ? 'selected' : ''}>&nbsp;&nbsp;存储企业和单位(仓储业)</option>
				 <option value="10" ${bigTrouble.tradeType==10 ? 'selected' : ''}>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的化工企业</option>
				 <option value="11" ${bigTrouble.tradeType==11 ? 'selected' : ''}>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的医药生产企业</option>
				 <option value="12" ${bigTrouble.tradeType==12 ? 'selected' : ''}>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的其他企业或单位</option>
				 <option value="13" ${bigTrouble.tradeType==13 ? 'selected' : ''}>&nbsp;&nbsp;道路运输企业和单位</option>
				 <option value="14" ${bigTrouble.tradeType==14 ? 'selected' : ''}>&nbsp;&nbsp;生产企业</option>
				 <option value="15" ${bigTrouble.tradeType==15 ? 'selected' : ''}>&nbsp;&nbsp;经营(批发)企业</option>
				 <option value="16" ${bigTrouble.tradeType==16 ? 'selected' : ''}>&nbsp;&nbsp;经营(零售)企业</option>
				 <option value="17" ${bigTrouble.tradeType==17 ? 'selected' : ''}>其他企业</option>

				 <option value="18" ${bigTrouble.tradeType==18 ? 'selected' : ''}>道路运输企业</option>
				 <option value="19" ${bigTrouble.tradeType==19 ? 'selected' : ''}>公路养护施工企业</option>
				 <option value="20" ${bigTrouble.tradeType==20 ? 'selected' : ''}>水上运输企业</option>
				 <option value="21" ${bigTrouble.tradeType==21 ? 'selected' : ''}>铁路运输企业</option>
				 <option value="22" ${bigTrouble.tradeType==22 ? 'selected' : ''}>航空公司</option>
				 <option value="23" ${bigTrouble.tradeType==23 ? 'selected' : ''}>机场和油料企业</option>
				 <option value="24" ${bigTrouble.tradeType==24 ? 'selected' : ''}>渔业企业</option>
				 <option value="25" ${bigTrouble.tradeType==25 ? 'selected' : ''}>农机行业</option>
				 <option value="26" ${bigTrouble.tradeType==26 ? 'selected' : ''}>水利工程</option>
				 <option value="27" ${bigTrouble.tradeType==27 ? 'selected' : ''}>地铁施工（按项目部统计）</option>
				 <option value="28" ${bigTrouble.tradeType==28 ? 'selected' : ''}>学校</option>
				 <option value="29" ${bigTrouble.tradeType==29 ? 'selected' : ''}>商场、市场等人员密集场所</option>
				 <option value="30" ${bigTrouble.tradeType==30 ? 'selected' : ''}>建筑施工企业</option>
				 <option value="31" ${bigTrouble.tradeType==31 ? 'selected' : ''}>民爆器材生产企业</option>
				 <option value="32" ${bigTrouble.tradeType==32 ? 'selected' : ''}>电力企业</option>
				 <option value="33" ${bigTrouble.tradeType==33 ? 'selected' : ''}>机械制造企业</option>
				 <option value="34" ${bigTrouble.tradeType==34 ? 'selected' : ''}>其他单位</option>
				 <option value="35" ${bigTrouble.tradeType==35 ? 'selected' : ''}>道路交通事故多发点段</option>
				 <option value="36" ${bigTrouble.tradeType==36 ? 'selected' : ''}>道路交通安全设施</option>
				 <option value="37" ${bigTrouble.tradeType==37 ? 'selected' : ''}>临水临崖危险路段</option>
				 <option value="38" ${bigTrouble.tradeType==38 ? 'selected' : ''}>城市公共交通</option>
				 <option value="39" ${bigTrouble.tradeType==39 ? 'selected' : ''}>燃气</option>
				 <option value="40" ${bigTrouble.tradeType==40 ? 'selected' : ''}>旅游行业</option>
				 <option value="41" ${bigTrouble.tradeType==41 ? 'selected' : ''}>铁路</option>
				 <option value="42" ${bigTrouble.tradeType==42 ? 'selected' : ''}>医院</option>
				 <option value="43" ${bigTrouble.tradeType==43 ? 'selected' : ''}>"三合一"场所</option>		
				 <option value="44" ${bigTrouble.tradeType==44 ? 'selected' : ''}>出租房</option>									
			</select></td>
    </tr>
	<tr>
	   <th><div align=center>单位名称:</div></th>
      <td width="261"><input type="text" id="companyName" name="bigTrouble.companyName" value="${bigTrouble.companyName}" size="78" maxlength="50" <c:if test="${bigTrouble.reportState==1||bigTrouble.reportState==123}">disabled</c:if> ></td>
    </tr>
	 <tr>
	    <th><div align=center>地　　址:</div></th>
		<td><input type="text" id="address" name="bigTrouble.address" size="78" maxlength="50" value="${bigTrouble.address}" <c:if test="${bigTrouble.reportState==1||bigTrouble.reportState==123}">disabled</c:if>></td>
    </tr>
	<tr>
	  <th><div align=center>内　　容:</div></th>
	  <td colspan="3"><textarea id="content" name="bigTrouble.content" cols="50" rows="5" <c:if test="${bigTrouble.reportState==1||bigTrouble.reportState==123}">disabled</c:if>>${bigTrouble.content}</textarea></td>
     </tr>
     <tr>
	    <th><div align=center>落实治理目标任务:</div></th>
		<td>&nbsp;<input type=radio name="bigTrouble.targetTask" value="1" ${bigTrouble.targetTask==1 ? 'checked' : ''} />是&nbsp;&nbsp;<input type=radio name="bigTrouble.targetTask" value="0" ${bigTrouble.targetTask==0 ? 'checked' : ''} ${bigTrouble.targetTask==null ? 'checked' : ''} />否</td>
    </tr>
    <tr>
	    <th><div align=center>落实治理经费物资:</div></th>
		<td>&nbsp;<input type=radio name="bigTrouble.goods" value="1" ${bigTrouble.goods==1 ? 'checked' : ''} />是&nbsp;&nbsp;<input type=radio name="bigTrouble.goods" value="0" ${bigTrouble.goods==0 ? 'checked' : ''} ${bigTrouble.goods==null ? 'checked' : ''} />否</td>
    </tr>
    <tr>
	    <th><div align=center>落实治理机构人员:</div></th>
		<td>&nbsp;<input type=radio name="bigTrouble.worker" value="1" ${bigTrouble.worker==1 ? 'checked' : ''} />是&nbsp;&nbsp;<input type=radio name="bigTrouble.worker" value="0" ${bigTrouble.worker==0 ? 'checked' : ''} ${bigTrouble.worker==null ? 'checked' : ''} />否</td>
    </tr>
    <tr>
	    <th><div align=center>落实治理时间要求:</div></th>
		<td>&nbsp;<input type=radio name="bigTrouble.governDate" value="1" ${bigTrouble.governDate==1 ? 'checked' : ''} />是&nbsp;&nbsp;<input type=radio name="bigTrouble.governDate" value="0" ${bigTrouble.governDate==0 ? 'checked' : ''} ${bigTrouble.governDate==null ? 'checked' : ''} />否</td>
    </tr>
    <tr>
	    <th><div align=center>落实安全措施应急预案:</div></th>
		<td>&nbsp;<input type=radio name="bigTrouble.safetyMethod" value="1" ${bigTrouble.safetyMethod==1 ? 'checked' : ''} />是&nbsp;&nbsp;<input type=radio name="bigTrouble.safetyMethod" value="0" ${bigTrouble.safetyMethod==0 ? 'checked' : ''} ${bigTrouble.safetyMethod==null ? 'checked' : ''} />否</td>
    </tr>
    <tr>
	  <th><div align=center>挂牌督办:</div></th>
	  <td colspan="3"><select id="guapaiLevel" name="bigTrouble.guapaiLevel" >
				 <option value="0">--请选择--</option>
				 <option value="-1">无挂牌隐患</option>
				 <option value="1">县级挂牌</option>
				 <option value="2">市级挂牌</option>
				 <option value="3">省级挂牌</option>
				 </select></td>
     </tr>
    <tr>
	  <th><div align=center>备　　注:</div></th>
	  <td colspan="3"><textarea id="remark" name="bigTrouble.remark" cols="50" rows="5" >${bigTrouble.remark}</textarea></td>
     </tr>
     <tr>
	  <td align="center" colspan="4">
		<c:choose>
			<c:when test="${bigTrouble.id>0}">
				<c:choose>
					<c:when test="${bigTrouble.reportState==0||bigTrouble.reportState==null}">
						<input type="button" id="but_sub" value="修 改" onclick="validateSubmit(2)"/>
					</c:when>
					<c:when test="${bigTrouble.reportState2==30}">
						<input type="button" id="but_sub" value="修 改" onclick="validateSubmit(2)"/>
					</c:when>
				</c:choose>	
			</c:when>
			<c:otherwise>
				<c:if test="${bigTrouble.reportState==0||bigTrouble.reportState==null}">
					<input type="button" id="but_sub" value="保 存" onclick="validateSubmit(1)"/>
				</c:if>
			</c:otherwise>
		</c:choose>
		<c:if test="${bigTrouble.reportState==1||bigTrouble.reportState==123}">
			<input type="button" id="but_sub" value="修改" onclick="validateSubmit(3)"/>
		</c:if>	
			<input type="button" id="but_sub2" value="返 回" onclick="javaScript:history.back()"/>
	  </td>
	</tr>
 </table>
 <c:choose><c:when test="${bigTrouble.reportState==1||bigTrouble.reportState==123}">
 <div style="color:red;font-size:12px;">注：“修改”按钮可以1、落实重大隐患情况；2、修改挂牌督办级别；3、修改隐患备注。</div>
		</c:when>
			<c:otherwise>
 <div style="color:red;font-size:12px;">注：请选择挂牌督办级别，如果该隐患没有进行过挂牌操作，可以选择“无挂牌督办”项。</div>
</c:otherwise></c:choose>
 <input type="hidden" name="mineId" value="${param.mineId}"/>
 <input type="hidden" name="tableName" value="${param.tableName}"/>
 <input type="hidden" name="bigTrouble.id" value="${bigTrouble.id}"/>
 <input type="hidden" name="bigTrouble.reportState" value="${bigTrouble.reportState==null ? '0' : bigTrouble.reportState}"/>
 <input type="hidden" name="bigTrouble.tradeType" value="${bigTrouble.tradeType}"/>
 <input type="hidden" name="bigTrouble.reportMonth" value="${bigTrouble.reportMonth}"/>
 <input type="hidden" name="bigTrouble.reportMonth2" value="${bigTrouble.reportMonth2}"/>
 <input type="hidden" name="bigTrouble.tableType" value="${bigTrouble.tableType}"/>
 <input type="hidden" name="bigTrouble.troubleType" value="${bigTrouble.troubleType}"/>
</form>
<script>
  	//显示填报月份
	<c:if test="${!empty(bigTrouble.reportMonth)}">
		var year = "${bigTrouble.reportMonth}".split("-")[0];
		var month = "${bigTrouble.reportMonth}".split("-")[1];
		get("year").value=year;
		get("month").value=month;
	</c:if>
	<c:if test="${!empty(bigTrouble.guapaiLevel)}">
		get("guapaiLevel").value = "${bigTrouble.guapaiLevel}";
	</c:if>
</script>
</body>
</html>
