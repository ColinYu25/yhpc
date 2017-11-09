<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if userDetail.userIndustry=='lishe'>
	<#assign typeName='栎社机场'>
<#elseif userDetail.userIndustry=='nongji'>
	<#assign typeName='农机'>
<#elseif userDetail.userIndustry=='xiaofang'>
	<#assign typeName='消防安全'>
<#elseif userDetail.userIndustry=='zhijian'>
	<#assign typeName='质监'>
<#elseif userDetail.userIndustry=='renfang'>
	<#assign typeName='人防'>
</#if>
  <#if seasonReportOther?exists>
  	<#assign url='updateSeasonReportOther.xhtml'>
  <#else>
  	<#assign url='createSeasonReportOther.xhtml'>
  </#if>
<script type="text/javascript">
	function submitCreate(){
	 	if(formValidator.validate()){
  		  var obj=get("seasonReportOtherForm");
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
var areaObj = new Area("${AreaXmlUrl}");
</script>
</head>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">${typeName}安全生产隐患排查治理情况表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	<#if seasonReportOther?exists>
	<li id="img_del"><a href="javascript:document.getElementById('seasonReportOtherFormOfDelete').submit();" class="b3"><b>删除</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'seasonReportOtherForm' />
<form id="seasonReportOtherForm" name="seasonReportOtherForm" method="post" action="">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <th width="20%" align="right">填报单位</th>
    <td>填报人
      <input name="seasonReportOther.fillMan" id="fillMan"  maxlength="5" type="text" size="14" value="${seasonReportOther.fillMan}" /> 
      <span class="red_point">*</span><ui:v for="fillMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <td>填报日期 <input type="text" id="fillDate" name="seasonReportOther.fillDate" value="${seasonReportOther.fillDate?date}"  size="14" maxlength="10" onfocus="WdatePicker();" class="Wdate" style="ime-mode:disabled;"/> 
    <span class="red_point">*</span><ui:v for="fillDate" rule="date" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
                     
  <td>单位负责人
    <input name="seasonReportOther.chargePerson" id="chargePerson"  maxlength="5" type="text" size="10" value="${seasonReportOther.chargePerson}" /> 
    <span class="red_point">*</span><ui:v for="chargePerson" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
    </td>

  </tr>
  <tr>
    <th height="33" align="right">行业分类</th>
    <td><select name="seasonReportOther.daIndustryParameter.id" id="dipId" style="width:158px;">
          <option value="">　　　--请选择--</option>
				<#if daIndustryParameters?exists>
				  	<#list daIndustryParameters?if_exists as di>
						<option value="${di.id}" <#if daIndustryParameters?size==1>selected</#if>>${di.name}</option>
					</#list>
			  	</#if>
        </select> <span class="red_point">*</span><ui:v for="dipId" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
   
    <td>联系电话 <input name="seasonReportOther.linkTel" type="text" id="linkTel" value="${seasonReportOther.linkTel}" size="14" maxlength="13" style="ime-mode:disabled;"/> 
    <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
      </td>
      <td>排查单位数 <input name="seasonReportOther.companyNum" type="text" id="companyNum" value="${seasonReportOther.companyNum}" size="10" maxlength="13" style="ime-mode:disabled;"/> 
      <span class="red_point">*</span><ui:v for="companyNum" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
  </tr>
  <tr>
    <th height="33" align="right">排查一般隐患</th>
    <td>一般隐患数
      <input name="seasonReportOther.troubNum" id="troubNum" type="text" maxlength="5" size="10" onchange="checkValue('troubNum','troubCleanNum','trouble');" value="${seasonReportOther.troubNum}" style="ime-mode:disabled;"/> 
      <span class="red_point">*</span><ui:v for="troubNum" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
    <td>已经整改数
      <input name="seasonReportOther.troubCleanNum" id="troubCleanNum" type="text" maxlength="5" size="12" value="${seasonReportOther.troubCleanNum}"  onchange="checkValue('troubNum','troubCleanNum','trouble');" style="ime-mode:disabled;"/> 
      <span class="red_point">*</span><ui:v for="troubCleanNum" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
    <td>一般整改率
      <input readonly="readonly" id="trouble" type="text" size="10"/>
</td>
  </tr>
  <tr>
    <th height="33" align="right">排查治理重大隐患</th>
    <td>重大隐患数
      <input name="seasonReportOther.bigTroubNum" id="bigTroubNum" type="text" maxlength="5" size="10" value="${seasonReportOther.bigTroubNum}"  onchange="checkValue('bigTroubNum','bigTroubCleanNum','bigTrouble');" style="ime-mode:disabled;"/> 
      <span class="red_point">*</span><ui:v for="bigTroubNum" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
 </td>
    <td>已整改销号
      <input name="seasonReportOther.bigTroubCleanNum" id="bigTroubCleanNum" type="text" maxlength="5" size="12" value="${seasonReportOther.bigTroubCleanNum}" onchange="checkValue('bigTroubNum','bigTroubCleanNum','bigTrouble');" style="ime-mode:disabled;"/> 
      <span class="red_point">*</span><ui:v for="bigTroubCleanNum" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
    <td>重大整改率
      <input readonly="readonly" id="bigTrouble" type="text" size="10"/>
</td>
  </tr>                    
     <tr>
    <th height="33" align="right"  rowspan=2>列入治理计划<br>的重大隐患</th>
    <td >落实治理目标任务
  <input name="seasonReportOther.target" id="target" type="text" maxlength="5" size="4" value="${seasonReportOther.target}" style="ime-mode:disabled;"/> 
  <span class="red_point">*</span><ui:v for="target" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
 </td>
<td>落实治理经费物资
  <input name="seasonReportOther.goods" id="goods" type="text" maxlength="5" size="6" value="${seasonReportOther.goods}" style="ime-mode:disabled;"/> 
  <span class="red_point">*</span><ui:v for="goods" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
<td>落实治理机构人员

  <input name="seasonReportOther.resource" id="resource" type="text" maxlength="5" size="8" value="${seasonReportOther.resource}" style="ime-mode:disabled;"/> 
  <span class="red_point">*</span><ui:v for="resource" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
  </tr>      
  <tr>
    <td>落实治理时间要求
  <input name="seasonReportOther.finishDate" id="finishDate" type="text" size="4" maxlength="5" style="ime-mode:disabled;" value="${seasonReportOther.finishDate}" /> 
  <span class="red_point">*</span><ui:v for="finishDate" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
 </td>
    <td>落实安全措施应急预案
<input name="seasonReportOther.safetyMethod" id="safetyMethod" type="text" maxlength="5" size="2" value="${seasonReportOther.safetyMethod}" style="ime-mode:disabled;"/> 
<span class="red_point">*</span><ui:v for="safetyMethod" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
    <td>累计落实治理资金
 <input name="seasonReportOther.governMoney" id="governMoney" type="text" maxlength="5" size="8" value="${seasonReportOther.governMoney}" style="ime-mode:disabled;"/> 万元 
 <span class="red_point">*</span><ui:v for="governMoney" rule="double" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
</td>
  </tr>                               	
  </table>
<input type="hidden" name="seasonReportOther.id" value="${seasonReportOther.id}" id="id"/>
 <#if seasonReportOther?exists>
 <input type="hidden" name="seasonReportOther.secondArea" value="${seasonReportOther.secondArea}" id="secondArea"/>
 <input type="hidden" name="seasonReportOther.firstArea" value="${seasonReportOther.firstArea}" id="firstArea"/>
 <input type="hidden" name="seasonReportOther.thirdArea" value="${seasonReportOther.thirdArea}" id="thirdArea"/>
 </#if>
</form>
<form id="seasonReportOtherFormOfDelete" name="seasonReportOtherFormOfDelete" method="post" action="deleteSeasonReportOther.xhtml">
<input type="hidden" name="seasonReportOther.id" value="${seasonReportOther.id}" id="id"/>
</form>
<!-- <br>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="createSeasonReportOtherInit.xhtml" method="post" name="companyForm" id="companyForm">
	<tr>
	 	<th style="width:80px;">区　　域</th>
	    <td><div id="div-area"></div></td>
	    <td colspan="4"><div align="center"><input type="submit" id="sub" value="搜　索" onClick="submitForm('companyForm');"/></div></td></tr>
	  </tr>
	</form>
	</table>
 -->
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <input type="hidden" name="id" id="id">
  <tr>
    <th colspan="4">填报单位</th>
    <th rowspan="2">行业分类</th>
    <th colspan="2">一般隐患</th>
    <th colspan="2">重大隐患</th>
  </tr>
  <tr>
    <th>单位负责人</th>
    <th>填报人</th>
    <th>创建时间</th>
    <th>区域</th>
    <th>一般隐患数</th>
    <th>已经整改数</th>
    <th>重大隐患数</th>
    <th>已整改销号</th>
  </tr>
  <#if seasonReportOthers?exists>
  <#list seasonReportOthers?if_exists as t>
    <tr style="cursor:pointer" onClick="window.location='loadSeasonReportOther.xhtml?seasonReportOther.id=${t.id}'" title="单击此行，可编辑">
      <td align="center">${t.chargePerson}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.fillMan}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.createTime?date}&nbsp;</td>
      <td nowrap="nowrap" align="center"><#if !t.secondArea?exists||t.secondArea==0>宁波市<#else><script type="text/javascript">document.write(areaObj.getArea("${t.secondArea}")[0]);</script></#if></td>
      <td nowrap="nowrap" align="center">${t.daIndustryParameter.name}&nbsp;</td>
      <td align="center">${t.troubNum}&nbsp;</td>
      <td align="center">${t.troubCleanNum}&nbsp;</td>
      <td align="center">${t.bigTroubNum}&nbsp;</td>
      <td align="center">${t.bigTroubCleanNum}&nbsp;</td>
    </tr>
 </#list>
</#if>   
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (seasonReportOthers?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script type="text/javascript">
<#if seasonReportOther?if_exists.daIndustryParameter?if_exists.id?exists>
	get('dipId').value='${seasonReportOther.daIndustryParameter.id}';
</#if>
function checkValue(){
	var num = get(arguments[0]).value;
	var cleanNum = get(arguments[1]).value;
	if(num != null && cleanNum != null && num != '' && cleanNum != '' && num != 0){
		var value = parseInt(cleanNum)/parseInt(num)*100;
		get(arguments[2]).value = Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%"; 
	}else{
		get(arguments[2]).value = '';
	}
}

function checkNum(){
	var num = get(arguments[0]).value;
	var cleanNum = get(arguments[1]).value;
	if(num != null && cleanNum != null && num != '' && cleanNum != '' && num != 0){
		var value = parseInt(cleanNum)/parseInt(num)*100;
		get(arguments[2]).value = Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%"; 
	}else{
		get(arguments[2]).value = '';
	}
}
checkValue('troubNum','troubCleanNum','trouble');
checkValue('bigTroubNum','bigTroubCleanNum','bigTrouble');
</script>
<!--<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>-->
</#escape>
<@fkMacros.pageFooter />