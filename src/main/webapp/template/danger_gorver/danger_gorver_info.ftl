<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if dangerGorver?if_exists.id?if_exists!=-1>
  	<#assign url='updateDangerGorver.xhtml'>
  <#else>
  	<#assign url='createDangerGorver.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
	  	var evaluate = GetRadioValue("dangerGorver.evaluate");
 		var expert = GetRadioValue("dangerGorver.evaluateExpert");
 		var government = GetRadioValue("dangerGorver.evaluateGovernment");
 		if(evaluate=='false' && expert=='false' && government=='false'){
 			alert("请正确选择“治理验收评估情况”！");
 			getName("dangerGorver.evaluate")[0].focus();
 		}else{
	  		var obj=get("dangerGorverForm");
	  		obj.action="${url}";
	 		obj.submit();
	 	}
 	}
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr><th width="100%" height="22">重大事故隐患情况表</th>
	<!--<th width="100%" height="22"><#if dangerGorver?if_exists.id?if_exists!=-1>修改<#else>添加</#if>重大隐患整改信息</th>-->
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !viewRole?? || !viewRole>
	<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'dangerGorverForm' />
<form id="dangerGorverForm" name="dangerGorverForm" method="post" action="">
<table cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <th colspan="2">单位名称</th>
    <td colSpan="2">${company.companyName}&nbsp;</td>
    <th>编　号</th>
    <td colspan="2">${danger.dangerNo}&nbsp;</td>
  </tr>
  <tr>
    <td width="7%" rowSpan="2"  style="background:#F0F0F0;color:#333;" align="right"><strong>单　位&nbsp;</strong></td>
    <td width="7%"  style="background:#F0F0F0;color:#333;" align="right"><strong>地　址&nbsp;</strong></td>
    <td colSpan="5">${company.regAddress}&nbsp;</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="right"><strong>区　域&nbsp;</strong></td>
    <td colSpan="5" id="companyArea">&nbsp;</td>
  </tr>
  <tr>
    <td  style="background:#F0F0F0;color:#333;" rowSpan="2" align="right"><strong>隐　患&nbsp;</strong></td>
    <td  style="background:#F0F0F0;color:#333;" align="right"><strong>地　址&nbsp;</strong></td>
    <td colSpan="5">${danger.dangerAdd}&nbsp;</td>
  </tr>
  <tr>
    <td  style="background:#F0F0F0;color:#333;" align="right"><strong>区　域&nbsp;</strong></td>
    <td colSpan="5" id="dangerArea">&nbsp;</td>
  </tr>
  <tr>
    <th colspan="2">联 系 人</th>
    <td width="20%"><input id="linkMan" maxLength="50" size="14" name="dangerGorver.linkMan" value="${dangerGorver.linkMan}"> 
    <span class="red_point">*</span><ui:v for="linkMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <th style="width:12%;">联系电话</th>
    <td width="19%"><input maxLength="13" size="13" name="dangerGorver.linkTel" id="linkTel" value="${dangerGorver.linkTel}" style="ime-mode:disabled"> 
    <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
    <th style="width:12%;">手　　机</th>
    <td width="28%"><input maxLength="12" size="12" name="dangerGorver.linkMobile" id="linkMobile" value="${dangerGorver.linkMobile}" style="ime-mode:disabled">
    <ui:v for="linkMobile" rule="mobile" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
  </tr>
  <tr>
    <th colspan="2" rowspan="2"> <p>隐患整治情况<BR>
      （简述）</p></th>
    <td colSpan="5"><TEXTAREA name="dangerGorver.gorverContent" rows="4" id="gorverContent" cols="74">${dangerGorver.gorverContent}</TEXTAREA> 
    <span class="red_point">*</span><ui:v for="gorverContent" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
  </tr>
  <tr>
  	<td width="20%" align="center">治理验收评估情况</td>
  	<td colspan="4">
  	自　　评：　　<input type="radio" name="dangerGorver.evaluate" value="true" <#if dangerGorver?if_exists.evaluate?exists&&dangerGorver.evaluate>checked</#if>>是　　　
			<input type="radio" name="dangerGorver.evaluate" value="false" <#if !dangerGorver?if_exists.evaluate?exists||!dangerGorver.evaluate>checked</#if>>否<br>
	专家评估：　　<input type="radio" name="dangerGorver.evaluateExpert" value="true" <#if dangerGorver?if_exists.evaluateExpert?exists&&dangerGorver.evaluateExpert>checked</#if>>是　　　
			<input type="radio" name="dangerGorver.evaluateExpert" value="false" <#if !dangerGorver?if_exists.evaluateExpert?exists||!dangerGorver.evaluateExpert>checked</#if>>否<br>
	政府验收：　　<input type="radio" name="dangerGorver.evaluateGovernment" value="true" <#if dangerGorver?if_exists.evaluateGovernment?exists&&dangerGorver.evaluateGovernment>checked</#if>>是　　　
			<input type="radio" name="dangerGorver.evaluateGovernment" value="false" <#if !dangerGorver?if_exists.evaluateGovernment?exists||!dangerGorver.evaluateGovernment>checked</#if>>否
</td>
  </tr>
  <tr>
    <th colspan="2">整改完成日期</th>
    <td colSpan="2"><input id="finishDate" maxLength="10" size="14" name="dangerGorver.finishDate" class="Wdate" value="${dangerGorver.finishDate?date}" maxlength="10" onfocus="WDatePickerNow();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"> 
    <span class="red_point">*</span><ui:v for="finishDate" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <th> <p>实际投入资金</p></th>
    <td colSpan="2"><input maxLength="10" size="12" name="dangerGorver.money" id="money" value="${dangerGorver.money.toString()}" style="ime-mode:disabled">
      <font color=red ><strong>万元</strong></font> <span class="red_point">*</span><ui:v for="money" rule="double" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
  </tr>
  <tr>
    <th colspan="2">备　　　注</th>
    <td colSpan="5"><TEXTAREA id="memo" name="dangerGorver.memo" rows="4" cols="74">${dangerGorver.memo}</TEXTAREA></td>
  </tr>
  <tr>
    <th colspan="2">单位负责人<BR>
    </p></th>
    <td><input id="chargePerson" maxLength="50" size="14" name="dangerGorver.chargePerson" value="${dangerGorver.chargePerson}"> 
    <span class="red_point">*</span><ui:v for="chargePerson" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <th style="width:12%;">填 表 人</th>
    <td><input id="fillMan" maxLength="50" size="13" name="dangerGorver.fillMan" value="${dangerGorver.fillMan}"> 
    <span class="red_point">*</span><ui:v for="fillMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
    <th style="width:12%;">填报日期</th>
    <td><input id="fillDate" maxLength="10" size="12" name="dangerGorver.fillDate" class="Wdate" value="${dangerGorver.fillDate?date}" maxlength="10" onfocus="WdatePicker();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"> 
    <span class="red_point">*</span><ui:v for="fillDate" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
  </tr>
</table>
<input type="hidden" name="dangerGorver.id" value="${dangerGorver.id}" id="id"/>
<input type="hidden" name="dangerGorver.daDanger.id" value="${danger.id}" id="dangerId"/>
</form>
<script>

var areaObj = new Area("${AreaXmlUrl}");
	var companyFirstArea="";
	var companySecondArea="";
	var companyThirdArea="";
	var dangerFirstArea="";
	var dangerSecondArea="";
	var dangerThirdArea="";
	
  <#if danger?exists&&company?exists>
 	<#if company.firstArea?exists&&company.firstArea!= 0>
		companyFirstArea=areaObj.getArea("${company.firstArea}")[0];
	</#if>
	<#if company.secondArea?exists&&company.secondArea!= 0>
		companySecondArea=areaObj.getArea("${company.secondArea}")[0];
	</#if>
	
	<#if company.thirdArea?exists&&company.thirdArea != 0>
		companyThirdArea=areaObj.getArea("${company.thirdArea}")[0];
	</#if>
  </#if>
	get("companyArea").innerHTML=companyFirstArea+companySecondArea+companyThirdArea;
	
  <#if danger?exists>
  
 	<#if danger.firstArea?exists&&danger.firstArea !=0>
		dangerFirstArea=areaObj.getArea("${danger.firstArea}")[0];
	</#if>
	<#if danger.secondArea?exists&&danger.secondArea != 0>
		dangerSecondArea=areaObj.getArea("${danger.secondArea}")[0];
	</#if>
	<#if danger.thirdArea?exists&&danger.thirdArea != 0>
		dangerThirdArea=areaObj.getArea("${danger.thirdArea}")[0];
	</#if>
  </#if>
	get("dangerArea").innerHTML=dangerFirstArea+dangerSecondArea+dangerThirdArea;
	
	function GetRadioValue(RadioName){
    var objByName;    
    objByName=document.getElementsByName(RadioName);
    if(objByName!=null){
        var i;
        for(i=0;i<objByName.length;i++){
            if(objByName[i].checked){
                return objByName[i].value;            
            }
        }
    }
    return null;
}
</script>
</#escape>
<@fkMacros.pageFooter />