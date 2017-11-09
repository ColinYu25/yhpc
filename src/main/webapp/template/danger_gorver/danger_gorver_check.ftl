<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if dangerGorver?if_exists.id?if_exists!=-1>
  	<#assign url='updateCheckDangerGorver.xhtml'>
  <#else>
  	<#assign url='createDangerGorver.xhtml'>
  </#if>
<script type="text/javascript"> 
function submitCreate(){

if(formValidator.validate()){
	var obj=get("dangerGorverForm");

	obj.action="${url}";
	obj.submit();
	}
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr><th width="100%" height="22">重大事故隐患核查表</th>
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
    <th colspan="2">监管责任单位</th>   
     <td colspan="3">
     
     <input maxLength="100" id="governDepartment" name="dangerGorver.governDepartment" type="text" class="input"  value="" size="40">&nbsp; 
     <span class="red_point">*</span><ui:v for="governDepartment" rule="require" empty="监管责任单位不允许为空;" pass="&nbsp;" tips="&nbsp;"/>
    
    
    </td>
    
   
    <th> <p>监管责任人</p></th>
    <td colSpan="2">
       <input maxLength="20" id="governPerson" name="dangerGorver.governPerson" type="text" class="input"  value="" size="20" value="${dangerGorver.governPerson}" >&nbsp; 
       <span class="red_point">*</span><ui:v for="governPerson" rule="require" empty="监管责任人不允许为空;" pass="&nbsp;" tips="&nbsp;"/>
    
    </td>
       
  </tr>
  
  
  <tr>
    <th colspan="2">核查单位</th>   
     <td colspan="3"><input maxLength="50" id="checkGorverDepar" name="dangerGorver.checkGorverDepar" type="text" class="input"  value="" size="40" maxlength="50">&nbsp; 

    <span class="red_point">*</span><ui:v for="checkGorverDepar" rule="require" empty="核查单位名称不允许为空;" pass="&nbsp;" tips="&nbsp;"/></td>
    
   
    <th> <p>核查日期</p></th>
    <td colSpan="2"><input id="checkDate" maxLength="10" size="14" name="dangerGorver.checkDate" class="Wdate" value="" maxlength="10" onfocus="WDatePickerNow();" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;">
    <span class="red_point">*</span><ui:v for="checkDate" rule="date" require="false" warn="核查日期不允许为空;" pass="&nbsp;" tips="&nbsp;"/></td>
       
  </tr>
  <tr>
    <th colspan="2">核查情况</th>
    <td colSpan="5">
     <#if dangerGorver?if_exists.checkContent?exists>
       <TEXTAREA maxLength="1000" id="checkContent" name="dangerGorver.checkContent" rows="4" cols="74">${dangerGorver.checkContent}</TEXTAREA>
     <#else>
      <TEXTAREA maxLength="1000" id="checkContent" name="dangerGorver.checkContent" rows="4" cols="74">经查该隐患已完成整改，整改情况属实。</TEXTAREA>
     </#if>
    
     
    <span class="red_point">*</span><ui:v for="checkContent" rule="require" empty="核查情况不允许为空;" pass="&nbsp;" tips="&nbsp;"/></td></td>
  </tr>
</table>
<input type="hidden" name="dangerGorver.id" value="${dangerGorver.id}" id="id"/>
<input type="hidden" name="dangerGorver.daDanger.id" value="${danger.id}" id="dangerId"/>
</form>
<script>



var date = new Date();
var gorverCheckDate="";
if((date.getMonth()+1)>9){
   if(date.getDate()>9){
      gorverCheckDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
   }else{
      gorverCheckDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-0"+date.getDate();
   }
}else{
   if(date.getDate()>9){
      gorverCheckDate=date.getFullYear()+"-0"+(date.getMonth()+1)+"-"+date.getDate();
   }else{
      gorverCheckDate=date.getFullYear()+"-0"+(date.getMonth()+1)+"-0"+date.getDate();
   }

}
<#if dangerGorver?if_exists.checkContent?exists>
  get("checkDate").value="${dangerGorver.checkDate?date}";   
<#else>
    get("checkDate").value=gorverCheckDate;  
</#if>
   


var areaObj = new Area("${AreaXmlUrl}");
	var companyFirstArea="";
	var companySecondArea="";
	var companyThirdArea="";
	
	var dangerFirstArea="";
	var dangerSecondArea="";
	var dangerThirdArea="";
	var departmentCountyLevel="";

  <#if danger?exists>
 	<#if company.firstArea != 0>
		companyFirstArea=areaObj.getArea("${company.firstArea}")[0];
	</#if>
	<#if company.secondArea != 0>
		companySecondArea=areaObj.getArea("${company.secondArea}")[0];
		
	</#if>
	<#if company.thirdArea != 0>
		companyThirdArea=areaObj.getArea("${company.thirdArea}")[0];
	</#if>
  </#if>
	get("companyArea").innerHTML=companyFirstArea+companySecondArea+companyThirdArea;
	
  <#if danger?exists>
  
 	<#if danger.firstArea != 0>
		dangerFirstArea=areaObj.getArea("${danger.firstArea}")[0];
	</#if>
	<#if danger.secondArea != 0>
		dangerSecondArea=areaObj.getArea("${danger.secondArea}")[0];
	</#if>
	<#if danger.thirdArea != 0>
		dangerThirdArea=areaObj.getArea("${danger.thirdArea}")[0];
	</#if>
  </#if>  
	get("dangerArea").innerHTML=dangerFirstArea+dangerSecondArea+dangerThirdArea;
	
<#if dangerGorver?if_exists.checkGorverDepar?exists>
    get("checkGorverDepar").value="${dangerGorver.checkGorverDepar}";
	
<#else>
    departmentCountyLevel="${department.countyLevel}";
	get("checkGorverDepar").value=dangerSecondArea+departmentCountyLevel;
</#if>


<#if dangerGorver?if_exists.governDepartment?exists>
    get("governDepartment").value="${dangerGorver.governDepartment}";
	
<#else>
    departmentCountyLevel="${department.countyLevel}";
	get("governDepartment").value=dangerSecondArea+departmentCountyLevel;
</#if>
	
<#if dangerGorver?if_exists.governPerson?exists>
    get("governPerson").value="${dangerGorver.governPerson}";
</#if>
	
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