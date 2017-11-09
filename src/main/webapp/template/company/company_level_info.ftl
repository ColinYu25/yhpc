<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#assign url='updateCompanyLevel.xhtml'>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("companyForm");
  		  obj.action="${url}";
 		  obj.submit();
 	} 
}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业分级分类</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="submitCreate();"><b>保存/确认</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyForm' />
<form id="companyForm" name="companyForm" method="post" action="">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <th>单位名称</th>
    <td width="35%">${company.companyName}&nbsp;</td>
    <th>分级评估得分</th>
    <td id="comPoint" width="35%"></td>
  </tr>
  <tr>
  <td colspan="4"><table width="100%" border="0" cellpadding="0" cellspacing="0"  class="table_input">
  <#if tradeTypesForCompanyLevel?exists>
  	<#list tradeTypesForCompanyLevel?if_exists as tt>
  		<tr>
  			<td width="30%">${tt.name}</td>
  			<td width="70%">
  				<#if pointTypes?if_exists?size!=0>
  					<#list pointTypes?if_exists as wtpt><#assign checked=''>
  						<#if company?exists>
  							<#list company.daPointTypes?if_exists as htt>
  								<#if wtpt.id=htt.id>
  									<#assign checked='checked="checked"'>
  								</#if>
  							</#list>
  						</#if>
  						<#if wtpt.daIndustryParameter.id=tt.id>
	  						　<input type="checkbox" ${checked} name="company.pointTypes" id="wtpt_${tt.id}" value="${wtpt.id}" onClick="checkedThis(this,'wtpt_${tt.id}');sumValue();"/>　${wtpt.name}（<span id="t_${wtpt.id}">${wtpt.point}</span>分）
  						</#if>
  					</#list>
  				<#else>&nbsp;
  				</#if>
  				</td>
  		</tr>
  	</#list>
  </#if>
  </table>
  </td>
  </tr>
</table>
<input type="hidden" name="company.id" id="id" value="${company.id}"/>
<input type="hidden" name="company.companyPoint" id="companyPoint" />
</form>
<script type="text/javascript">
	function checkedThis(obj,objId){
       var boxArray = get(objId);
       for(var i=0;i<=boxArray.length-1;i++){
            if(boxArray[i]==obj && obj.checked){
               boxArray[i].checked = true;
            }else{
               boxArray[i].checked = false;
            }
       }
    }
    function sumValue(){
    	var names = getName("company.pointTypes");
    	var points = 0;
    	for(var i=0;i<=names.length-1;i++){
    		if(names[i].checked){
    			points += parseInt(get("t_"+names[i].value).innerHTML);
    		}
    	}
    	get("comPoint").innerHTML = points+"分";
    	get("companyPoint").value = points;
    }
    sumValue();
</script>
</#escape>
<@fkMacros.pageFooter />