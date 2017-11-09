<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if threeIllegal?exists>
  	<#assign url='updateThreeIllegal.xhtml'>
  <#else>
  	<#assign url='createThreeIllegal.xhtml'>
  </#if>
<script type="text/javascript">
	function submitCreate(){
	 	if(formValidator.validate()){
  		  var obj=get("threeIllegalForm");
  		  obj.action="${url}";
 		  obj.submit();
	 	}
	}
function compare(thisObj,lastObj,thisMsg,lastMsg){
	var data=/^\d+$/;　//非负整数 
	var thisValue =thisObj.value;
	var lastValue=lastObj.value;
    if(lastValue==""&&thisValue!=""){
		thisObj.value="";
		alert('请您先输入'+lastMsg+'！');
		lastObj.focus();
		return false;
	}else{
		if(thisValue!=""){
			if(!data.test(lastValue)){
				alert('您在'+lastMsg+'中输入的格式有误,请输入正整数或“0”!');
				lastObj.focus();
				return false;
			}
			if(!data.test(thisValue)){
				alert('您在'+thisMsg+'中输入的格式有误,请输入正整数或“0”!');
				thisObj.focus();
				return false;
			}
			var intValue=parseInt(thisValue);
			var maxV=parseInt(lastValue);			
			if(intValue<=maxV && intValue>=0){
				return true;
			}else{
			  thisObj.value="0";
			  alert(thisMsg+"应输入0-"+lastValue+"范围内的数值!");
			  thisObj.focus();
			  return false;			
			}
		}
	}
}
function qd(){
var data=/^\d+$/;
var thisValue=get("illegalBuildGetting").value;
var nextValue=get("illegalBuildCandeled").value;
if(!data.test(thisValue)){
	alert('您输入的格式有误,请输入正整数或“0”!');
	get("illegalBuildGetting").focus();
	return false;
}
if(!data.test(nextValue)){
	alert('您输入的格式有误,请输入正整数或“0”!');
	get("illegalBuildCandeled").focus();
	return false;
}
if(thisValue==""){
	alert("请输入已掌握数！");
	get("illegalBuildGetting").focus();
	return false;
}
if(nextValue==""){
	alert("请输入已取缔数！");
	get("illegalBuildCandeled").focus();
	return false;
}
var intValue=parseInt(thisValue);
var maxV=parseInt(nextValue);
if(thisValue!=""&&nextValue!=""){
	if(intValue<maxV){
		alert("已取缔数必须小于已掌握数！");
		get("illegalBuildCandeled").focus();
		return false;
	}	

}
get("illegalBuildCanceling").value=intValue-maxV;
return true;
}

function qd1(){

var thisValue=get("illegalProduceGetting").value;
var nextValue=get("illegalProduceCanceled").value;
if(thisValue==""){
	alert("请输入已掌握数！");
	get("illegalProduceGetting").focus();
	return false;
}
if(nextValue==""){
	alert("请输入已取缔数！");
	get("illegalProduceCandeled").focus();
	return false;
}
var intValue=parseInt(thisValue);
var maxV=parseInt(nextValue);
if(thisValue!=""&&nextValue!=""){
	if(intValue<maxV){
		alert("已取缔数必须小于已掌握数！");
		get("illegalProduceCanceled").focus();
		return false;
	}	

}
get("illegalProduceCanceling").value=intValue-maxV;
return true;
}

function qd2(){

var thisValue=get("illegalTradeGetting").value;
var nextValue=get("illegalTradeCalceled").value;
if(thisValue==""){
	alert("请输入已掌握数！");
	get("illegalTradeGetting").focus();
	return false;
}
if(nextValue==""){
	alert("请输入已取缔数！");
	get("illegalTradeCalceled").focus();
	return false;
}
var intValue=parseInt(thisValue);
var maxV=parseInt(nextValue);
if(thisValue!=""&&nextValue!=""){
	if(intValue<maxV){
		alert("已取缔数必须小于已掌握数！");
		get("illegalTradeCalceled").focus();
		return false;
	}	

}
get("illegalTradeCanceling").value=intValue-maxV;
return true;
}

</script>
</head>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">打击非法建设、非法生产、非法经营情况统计表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	<#if threeIllegal?exists>
	<li id="img_del"><a href="javascript:document.getElementById('threeIllegalFormOfDelete').submit();" class="b3"><b>删除</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'threeIllegalForm' />
<form id="threeIllegalForm" name="threeIllegalForm" method="post" action="">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th width="20%" align="right">填报单位</th>
        <td>${userDetail.userCompany}</td>
        <td>填报人
          <input name="threeIllegal.fillMan" id="fillMan"  maxlength="5" type="text" size="10" value="${threeIllegal.fillMan}"/> 
          <span class="red_point">*</span><ui:v for="fillMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
        <td>填报日期　　
        <input type="text" name="threeIllegal.fillDate" id="fillDate" value="${threeIllegal.fillDate?date}" size="14" maxlength="10" onfocus="WdatePicker();" class="Wdate" /> 
        <span class="red_point">*</span><ui:v for="fillDate" rule="date" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
      </tr>
      <tr>
        <th height="33" align="right">行业分类</th>
        <td><select name="threeIllegal.daIndustryParameter.id" id="dipId" style="width:134px;">
          <option value="">--请选择--</option>
				<#if daIndustryParameters?exists>
				  	<#list daIndustryParameters?if_exists as di>
						<option value="${di.id}" <#if daIndustryParameters?size==1>selected</#if>>${di.name}</option>
					</#list>
			  	</#if>
        </select> <span class="red_point">*</span><ui:v for="dipId" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
        <td>单位负责人
        <input name="threeIllegal.chargePerson" id="chargePerson"  maxlength="5" type="text" size="10" value="${threeIllegal.chargePerson}" /> 
        <span class="red_point">*</span><ui:v for="chargePerson" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
        <td>联系电话　　
          <input name="threeIllegal.tel" type="text" id="tel" value="${threeIllegal.tel}" size="14" maxlength="13" style="ime-mode:disabled;"/> 
          <span class="red_point">*</span><ui:v for="tel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
      </tr>
      <tr>
        <th height="33" align="right">非法建设</th>
        <td width="25%">已掌握
          <input name="threeIllegal.illegalBuildGetting" id="illegalBuildGetting" type="text" maxlength="5" size="10" value="${threeIllegal.illegalBuildGetting}" style="ime-mode:disabled;"/>
        个<span class="red_point">*</span><ui:v for="illegalBuildGetting" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td width="27%">已取缔
          <input name="threeIllegal.illegalBuildCandeled" id="illegalBuildCandeled" type="text" maxlength="5" size="10" value="${threeIllegal.illegalBuildCandeled}" style="ime-mode:disabled;" onBlur="return qd();"/>
        个<span class="red_point">*</span><ui:v for="illegalBuildCandeled" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td width="33%">正在打击的有
          <input name="threeIllegal.illegalBuildCanceling" id="illegalBuildCanceling" type="text" maxlength="5" size="14" value="${threeIllegal.illegalBuildCanceling}" readonly="true" onBlur="compare(this,illegalBuildGetting,'非法建设正在打击数','非法建设已掌握数')" style="ime-mode:disabled;"/>
        个<span class="red_point">*</span><ui:v for="illegalBuildCanceling" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
      </tr>
      <tr>
        <th height="33" align="right">非法生产</th>
        <td>已掌握
          <input name="threeIllegal.illegalProduceGetting" id="illegalProduceGetting" type="text" maxlength="5" size="10" value="${threeIllegal.illegalProduceGetting}" style="ime-mode:disabled;"/>
个<span class="red_point">*</span><ui:v for="illegalProduceGetting" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td>已取缔
          <input name="threeIllegal.illegalProduceCanceled" id="illegalProduceCanceled" type="text" maxlength="5" size="10" value="${threeIllegal.illegalProduceCanceled}" style="ime-mode:disabled;" onBlur="return qd1();"/>
个<span class="red_point">*</span><ui:v for="illegalProduceCanceled" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td>正在打击的有
          <input name="threeIllegal.illegalProduceCanceling" id="illegalProduceCanceling" type="text" maxlength="5" size="14" value="${threeIllegal.illegalProduceCanceling}" readonly="true" onBlur="compare(this,illegalProduceGetting,'非法生产正在打击数','非法生产已掌握数')" style="ime-mode:disabled;"/>
个<span class="red_point">*</span><ui:v for="illegalProduceCanceling" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
      </tr>
      <tr>
        <th height="33" align="right">非法经营</th>
        <td>已掌握
          <input name="threeIllegal.illegalTradeGetting" id="illegalTradeGetting" type="text" maxlength="5" size="10" value="${threeIllegal.illegalTradeGetting}" style="ime-mode:disabled;"/>
 个<span class="red_point">*</span><ui:v for="illegalTradeGetting" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td>已取缔
          <input name="threeIllegal.illegalTradeCalceled" id="illegalTradeCalceled" type="text" maxlength="5" size="10" value="${threeIllegal.illegalTradeCalceled}" style="ime-mode:disabled;" onBlur="return qd2();"/>
个<span class="red_point">*</span><ui:v for="illegalTradeCalceled" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
        <td>正在打击的有
          <input name="threeIllegal.illegalTradeCanceling" id="illegalTradeCanceling" type="text" maxlength="5" size="14" value="${threeIllegal.illegalTradeCanceling}" readonly="true" onBlur="compare(this,illegalTradeGetting,'非法经营正在打击数','非法经营已掌握数')" style="ime-mode:disabled;"/>
个<span class="red_point">*</span><ui:v for="illegalTradeCanceling" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
      </tr>                    
</table>
<input type="hidden" name="threeIllegal.userCompany" value="${userDetail.userCompany}" id="userCompany"/>
<input type="hidden" name="threeIllegal.id" value="${threeIllegal.id}" id="id"/>
</form>
<form id="threeIllegalFormOfDelete" name="threeIllegalFormOfDelete" method="post" action="deleteThreeIllegal.xhtml">
<input type="hidden" name="threeIllegal.id" value="${threeIllegal.id}" id="id"/>
</form>
<table width="98%" border="0"  cellpadding="0" cellspacing="0" class="table_list">
  <input type="hidden" name="id" id="id">
  <tr>
    <th colspan="3">填报单位</th>
    <th rowspan="2">行业分类</th>
    <th colspan="3">非法建设</th>
    <th colspan="3">非法生产</th>
    <th colspan="3">非法经营</th>
  </tr>
  <tr>
    <th>填报单位</th>
    <th>填报人</th>
    <th>填报时间</th>
    <th>已掌<br />
      握数</th>
    <th>已取<br />
      缔数<br /></th>
    <th>正在<br />
      打击数<br /></th>
    <th>已掌<br />
      握数<br /></th>
    <th>已取<br />
      缔数<br /></th>
    <th>正在<br />
      打击数<br /></th>
    <th>已掌<br />
      握数<br /></th>
    <th>已取<br />
      缔数</th>
    <th>正在<br />
      打击数</th>
  </tr>
  <#if threeIllegals?exists>
  <#list threeIllegals?if_exists as t>
    <tr style="cursor:pointer" onClick="window.location='loadThreeIllegal.xhtml?threeIllegal.id=${t.id}'" title="单击此行，可编辑">
      <td align="center">${t.userCompany}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.fillMan}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.fillDate?date}&nbsp;</td>
      <td nowrap="nowrap" align="center"><#if t.daIndustryParameter?exists>${t.daIndustryParameter.name}</#if>&nbsp;</td>
      <td align="center">${t.illegalBuildGetting}&nbsp;</td>
      <td align="center">${t.illegalBuildCandeled}&nbsp;</td>
      <td align="center">${t.illegalBuildCanceling}&nbsp;</td>
      <td align="center">${t.illegalProduceGetting}&nbsp;</td>
      <td align="center">${t.illegalProduceCanceled}&nbsp;</td>
      <td align="center">${t.illegalProduceCanceling}&nbsp;</td>
      <td align="center">${t.illegalTradeGetting}&nbsp;</td>
      <td align="center">${t.illegalTradeCalceled}&nbsp;</td>
      <td align="center">${t.illegalTradeCanceling}&nbsp;</td>
    </tr>
 </#list>
</#if>   
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (threeIllegals?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
<#if threeIllegal?if_exists.daIndustryParameter?if_exists.id?exists>
get('dipId').value='${threeIllegal.daIndustryParameter.id}';
</#if>
</script>
</#escape>
<@fkMacros.pageFooter />