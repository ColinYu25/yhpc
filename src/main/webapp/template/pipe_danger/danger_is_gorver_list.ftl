<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if danger?if_exists.isGorver?if_exists=='0'>
	<#assign url='loadDangersUnGorver.xhtml'>
	<#assign letter='重大隐患未治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='1'>
	<#assign url='loadDangersGorver.xhtml'>
	<#assign letter='重大隐患已治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='2'>
	<#assign url='loadDangersTimeOut.xhtml'>
	<#assign letter='重大隐患到期提醒隐患列表'>
<#else>
	<#assign url='loadDangersRollcall.xhtml'>
	<#assign letter='重大隐患挂牌管理列表'>
</#if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">${letter}</th>
  </tr>
</table>
<form action="${url}" method="post" name="dangersForm" id="dangersForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
  <th>管道名称</th>
  <td><input type="text" name="danger.pipeLine.name" size="35" value="${danger.pipeLine.name}" onBlur="SpacesAll(this);"></td>
  <th>年　　份</th>
    <td><input type="text" id="year" value="${danger.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <input type="hidden" name="danger.nowYear" id="year_2" value="${danger.nowYear}"/></td>
  </tr>
  <tr>
    <th>隐患地址</th>
    <td width="33%"><input type="text" name="danger.dangerAdd" size="35" value="${danger.dangerAdd}" onBlur="SpacesAll(this);"></td>
	<th>隐患区域</th>
    <td width="38%"><div id="div-area"></div></td>
  </tr>
  <tr>
  <#if danger?if_exists.isGorver?if_exists=='2'>
    <th>时间范围</th>
    <td>
    <input type="text" name="danger.startTime" id="startTime" onfocus="WdatePicker();" class="Wdate" value="${danger.startTime?date}" size="12" maxlength="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;">　至　
    <input type="text" name="danger.endTime" id="endTime" onfocus="WdatePicker();" class="Wdate" value="${danger.endTime?date}" size="12" maxlength="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"></td>
   <#else>
   	<th>隐患编号</th>
    <td width="33%"><input type="text" name="danger.dangerNo" size="25" value="${danger.dangerNo}" onBlur="SpacesAll(this);"></td>
   </#if>
    <th>是否挂牌</th>
    <td><input type="checkbox" name="danger.isRollcall" onClick="checkedOne(this,'danger.isRollcall');" value="1" <#if danger?if_exists.isRollcall?exists><#if danger.isRollcall=='1'>checked</#if></#if>> 是　　　　
    <input type="checkbox" name="danger.isRollcall" onClick="checkedOne(this,'danger.isRollcall');" value="0" <#if danger?if_exists.isRollcall?exists><#if danger.isRollcall=='0'>checked</#if></#if>> 否　　　　　
    </td>
  </tr>
  <!--<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('dangersForm');"/></div></th></tr>-->
</table>
</form>
<div class="menu">
  	<ul>
	
	<#if danger?if_exists.isGorver?exists>
	<!--<li id="img_amend"><a href="javascript:loadNote('loadDanger.xhtml?entity.id=${entity.id}&danger.id');" class="b2"><b>修改</b></a></li>-->
	<#if danger.isGorver=='0'>
	<li id="img_del"><a href="javascript:deleteNote(get('dangersFormForDelete'));" class="b3"><b>删除</b></a></li>
	</#if>
	<#if danger.isGorver=='1'>
	<li id="img_zgtzs"><a href="javascript:loadNote('../pipeDangerGorver/createDangerGorverInit.xhtml?dangerGorver.daPipeDanger.id');" class="b_zgtzs"><b>查看整改信息</b></a></li>
    <#else>
	<li id="img_report"><a href="javascript:loadNote('../pipeDangerGorver/createDangerGorverInit.xhtml?dangerGorver.daPipeDanger.id');" class="b7"><b>整改</b></a></li>
	</#if>
	</#if>
	<#if !danger?if_exists.isGorver?exists>
    <li id="img_amend"><a href="javascript:loadNote('../pipeRollcallCompany/createRollcallCompanyInit.xhtml?rollcallCompany.daPipeDanger.id');" class="b2"><b>挂牌</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	<li id="img_lookup"><a class="b13" href="javaScript:submitForm('dangersForm');"><b>查询</b></a></li>
	</ul>
</div>
<form action="deleteDangers.xhtml" method="post" name="dangersFormForDelete" id="dangersFormForDelete">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="11%" nowrap>隐患编号</th>
    <th width="17%" nowrap>管道名称</th>
    <#if company?? && company.id??><th width="8%" nowrap>传输介质</th><#else><th width="21%" nowrap>所属企业</th></#if> 
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否挂牌</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if dangers?exists>
  <#list dangers?if_exists as s>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${s.id}" value="${s.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
    <td nowrap><a href="loadDanger.xhtml?danger.id=${s.id}">${s.dangerNo}</a>&nbsp;</td>
    <td nowrap><div align="center"><a href="../pipeline/yq_pipeline_update.xhtml?entity.id=${s.pipeLine.id}">${s.pipeLine.name}</a>&nbsp;</div></td>
    <td nowrap><#if company?? && company.id??>${s.medium}<#else>${s.pipeLine.daPipelineCompanyinfo.company.companyName}</#if>&nbsp;</td>
    <td nowrap><div align="center">${s.dangerAdd}&nbsp;</div></td>
    <td nowrap><#if (s.daPipeRollcallCompanies?size>0)>是<#else>否</#if>&nbsp;</td>
    <td nowrap>${s.linkMan}&nbsp;</td>
    <td nowrap>${s.linkTel}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (dangers?if_exists?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
function submitForm(formName){
	var formObj=get(formName);
	get("year_2").value=get("year_2").value.substring(0,4);
	formObj.submit();
}
<#--
<#if userDetail.userIndustry!='anwei'>
	changeTrade(get("tradeTypes1").value,1);
</#if>
function changeTrade(tradeId, type) {
	var length = 1;
	if(type == 1) {
		cleanSelect(get("tradeTypes2"));
		cleanSelect(get("tradeTypes3"));
	} else if (type == 2){
		cleanSelect(get("tradeTypes3"));
	}
	<#list tradeTypes?if_exists as t>
		<#list t.daIndustryParameters?if_exists as st>
			if (type == 1 && tradeId == "${t.id}" && "${st.type}" == 1) {
				var opt = new Option("${st.name}", "${st.id}");
				get("tradeTypes2").options[length] = opt;
				opt = null;
				length ++;
			}
			<#list st.daIndustryParameters?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}" && "${sst.type}" == 1) {
					var opt = new Option("${sst.name}", "${sst.id}");
					get("tradeTypes3").options[length] = opt;
					opt = null;
					length ++;
				}
			</#list>
		</#list>
	</#list>
}
<#if danger?? && danger.pipeLine?? && danger.pipeLine.daPipelineCompanyinfo?? && danger.pipeLine.daPipelineCompanyinfo.company?exists>
	<#if danger.pipeLine.daPipelineCompanyinfo.company.tradeTypes?exists>
		var trades = "${danger.pipeLine.daPipelineCompanyinfo.company.tradeTypes}";
		getName("danger.pipeLine.daPipelineCompanyinfo.company.tradeTypes")[getName("danger.pipeLine.daPipelineCompanyinfo.company.tradeTypes").length-1].value = trades;
		for (var i=0;i<trades.split(",").length;i++) {
			if (trim(trades.split(",")[i]) != "") {
				get("tradeTypes"+(i+1)).value = trim(trades.split(",")[i]);
				changeTrade(trim(trades.split(",")[i]), (i+1));
			}
		}
	</#if>
</#if>-->
</script>
<@fkMacros.muilt_select_js />
<#if danger?exists>
<@fkMacros.selectAreas_fun "${danger?if_exists.firstArea?if_exists}" "${danger?if_exists.secondArea?if_exists}" "${danger?if_exists.thirdArea?if_exists}" "${danger?if_exists.fouthArea?if_exists}" "${danger?if_exists.fifthArea?if_exists}" "danger."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "danger."/>
</#if>
<script>
 roleName("roleName");
</script>
</#escape>
<@fkMacros.pageFooter />