<@fkMacros.pageHeader />
<#escape x as (x)!>
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
</script>
</head>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">公安（道路交通）、农机、质监、海事等安全生产隐患排查治理情况统计表</th>
  </tr>
</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="loadSeasonReportOtherList.xhtml" method="post" name="companyForm" id="companyForm">
	<tr>
	 	<th style="width:80px;">行业</th>
	    <td>
			<select name="seasonReportOther.daIndustryParameter.id" id="dipId" style="width:158px;">
         	 <option value="">　　　--请选择--</option>
				<#if daIndustryParameters?exists>
				  	<#list daIndustryParameters?if_exists as di>
						<option value="${di.id}" <#if daIndustryParameters?size==1>selected</#if>>${di.name}</option>
					</#list>
			  	</#if>
        </select> 
</td>
<th>区域：</th>
  <td><div id="div-area"></div></td>
	    <td colspan="4"><div align="center"><input type="submit" id="sub" value="搜　索" onClick="submitForm('companyForm');"/></div></td></tr>
	  </tr>
	</form>
	</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <input type="hidden" name="id" id="id">
  <tr>
    <th colspan="3">填报单位</th>
    <th rowspan="2">行业分类</th>
    <th colspan="2">一般隐患</th>
    <th colspan="2">重大隐患</th>
  </tr>
  <tr>
    <th>单位负责人</th>
    <th>填报人</th>
    <th>填报时间</th>
    <th>一般隐患数</th>
    <th>已经整改数</th>
    <th>重大隐患数</th>
    <th>已整改销号</th>
  </tr>
    <#if seasonReportOthers?exists>
  <#list seasonReportOthers?if_exists as t>
    <tr style="cursor:pointer" >
      <td align="center">${t.chargePerson}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.fillMan}&nbsp;</td>
      <td nowrap="nowrap" align="center">${t.fillDate?date}&nbsp;</td>
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
</table>
<script>
<#if seasonReportOther?exists>
	get("dipId").value="${seasonReportOther.daIndustryParameter.id}";
</#if>
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />