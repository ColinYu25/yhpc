<@fkMacros.pageHeader />
<#escape x as (x)!> 
  <#if company?exists>
  	<#assign url='updateCompany.xhtml'>
  <#else>
  	<#assign url='createCompany.xhtml'>
  </#if>
<script type="text/javascript">
if(parent){
	parent.company = "${company.companyName}";
}
function submitUpdate(){
 	if(formValidator.validate()){
  		  var obj=get("companyForm");
  		  obj.action="createCompanyAcount.xhtml";
 		  obj.submit();
 	}
}
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("companyForm");
  		  obj.action="${url}";
  		  for(var i=0;i<getName("company.tradeTypes").length;i++) {
  		  	getName("company.tradeTypes")[i].disabled = false;
  		  }
  		  for(var i=0;i<getTag("input").length;i++) {
  		  	if(getTag("input")[i].type=="checkbox" && getTag("input")[i].id.indexOf("fir_")>-1) {
  		  		if(!getTag("input")[i].checked) {
  		  			if(get("sp_sec_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_sec_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  			if(get("sp_thi_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_thi_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  			if(get("sp_for_"+getTag("input")[i].id.split("_")[1]))
  		  				get("sp_for_"+getTag("input")[i].id.split("_")[1]).innerHTML = "";
  		  		}
  		  	}
  		  }
 		  obj.submit();
 	}
}
function showSecondTrade(firstTrade) {
	if (firstTrade.checked) {
		if(get("sp_sec_"+firstTrade.id.split("_")[1])) {
			get("sp_sec_"+firstTrade.id.split("_")[1]).style.display = "";
			get("sel_sec_"+firstTrade.id.split("_")[1]).options[0].selected = true;
		}
	} else{
		if(get("sp_sec_"+firstTrade.id.split("_")[1])) {
			get("sp_sec_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
		if(get("sp_thi_"+firstTrade.id.split("_")[1])) {
			get("sp_thi_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
		if(get("sp_for_"+firstTrade.id.split("_")[1])) {
			get("sp_for_"+firstTrade.id.split("_")[1]).style.display = "none";
		}
	}
}
function showThirdTrade(secondTrade) {
	var fir_id = secondTrade.id.split("_")[secondTrade.id.split("_").length-1];
	var sec_id = secondTrade.value;
	var is_done = false;
	<#list tradeTypes?if_exists as fir>
		if(fir_id == "${fir.id}") {
			<#list fir.daIndustryParameters?if_exists as sec>
				if(sec_id == "${sec.id}" && "${sec.type}" == 1) {
					<#if sec.daIndustryParameters?exists&&sec.daIndustryParameters?size!=0>
						get("sp_thi_${fir.id}").style.display = "";
						get("sel_thi_${fir.id}").style.display = "";
						for(var i=get("sel_thi_${fir.id}").options.length;i>=1;i--) {
							get("sel_thi_${fir.id}").options[i] = null;
						}
						<#list sec.daIndustryParameters?if_exists as thi>
							if("${thi.type}" == 1) {
								var opt = new Option("${thi.name}","${thi.id}");
								get("sel_thi_${fir.id}").options[get("sel_thi_${fir.id}").length] = opt;
								opt = null;
								is_done = true;
							}
						</#list>
					<#else>
						if(get("sp_thi_${fir.id}")) {
							get("sel_thi_${fir.id}").style.display = "none";
						}
						if(get("sp_for_${fir.id}")) {
							get("sel_for_${fir.id}").style.display = "none";
						}
					</#if>
				}
			</#list>
		}
	</#list>
	if(!is_done) {
		if(get("sel_thi_"+fir_id)) {
			get("sel_thi_"+fir_id).style.display = "none";
		}
		if(get("sel_for_"+fir_id)) {
			get("sel_for_"+fir_id).style.display = "none";
		}
	}
}
function showForthTrade(ThirdTrade) {
	var fir_id = ThirdTrade.id.split("_")[ThirdTrade.id.split("_").length-1];
	var sec_id = get("sel_sec_"+fir_id).value;
	var thi_id = ThirdTrade.value;
	var is_done = false;
	<#list tradeTypes?if_exists as fir>
		if(fir_id == "${fir.id}") {
			<#list fir.daIndustryParameters?if_exists as sec>
				if(sec_id == "${sec.id}" && "${sec.type}" == 1) {
					<#if sec.daIndustryParameters?exists&&sec.daIndustryParameters?size!=0>
						<#list sec.daIndustryParameters?if_exists as thi>
							if(thi_id == "${thi.id}" && "${thi.type}" == 1) {
								<#if thi.daIndustryParameters?exists&&thi.daIndustryParameters?size!=0>
									get("sp_for_${fir.id}").style.display = "";
									get("sel_for_${fir.id}").style.display = "";
									for(var i=get("sel_for_${fir.id}").options.length;i>=1;i--) {
										get("sel_for_${fir.id}").options[i] = null;
									}
									<#list thi.daIndustryParameters?if_exists as for>
										if("${for.type}" == 1) {
											var opt = new Option("${for.name}","${for.id}");
											get("sel_for_${fir.id}").options[get("sel_for_${fir.id}").length] = opt;
											opt = null;
											is_done = true;
										}
									</#list>
								<#else>
									if(get("sp_for_${fir.id}")) {
										get("sel_for_${fir.id}").style.display = "none";
									}
								</#if>
							}
						</#list>
					</#if>
				}
			</#list>
		}
	</#list>
	if(!is_done) {
		if(get("sel_for_"+fir_id)) {
			get("sel_for_"+fir_id).style.display = "none";
		}
	}
}
function showSecOption() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
					if(get("sel_sec_"+fir_id)) {
						showThirdTrade(get("sel_sec_"+fir_id));
					}
					if(get("sel_thi_"+fir_id)) {
						showThiOption(get("sel_thi_"+fir_id));
					}
				}
			}
		</#list>
	</#if>
}
function showThiOption() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
					if(get("sel_thi_"+fir_id)) {
						showForthTrade(get("sel_thi_"+fir_id));
					}
					if(get("sel_for_"+fir_id)) {
						showForOption(get("sel_for_"+fir_id));
					}
				}
			}
		</#list>
	</#if>
}
function showForOption() {
	var fir_id = arguments[0].id.split("_")[arguments[0].id.split("_").length-1];
	<#if company?exists>
		<#list company.hzTradeTypes?if_exists as ht>
			for(var i=0; i<arguments[0].options.length; i++) {
				if(arguments[0].options[i].value == "${ht.id}") {
					arguments[0].options[i].selected = true;
				}
			}
		</#list>
	</#if>
}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业基本信息</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyForm' />
<form id="companyForm" name="companyForm" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th colspan="2">单位名称：</th>
        <td colspan="3"><input name="company.companyName" type="text" class="input" id="companyName" value="${company.companyName}" size="40" maxlength="50">&nbsp;<span style=color:red>*</span><ui:v for="companyName"  rule="require" empty="单位名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
      </tr>
      <tr>
       	<th colspan="2">所在地址：</th>
        <td><input name="company.regAddress" type="text" class="input" id="regAddress" value="${company.regAddress}" size="40" maxlength="50">&nbsp;<span style=color:red>*</span>
        <ui:v for="regAddress"  rule="require" empty="企业地址不允许为空" pass="&nbsp" tips="&nbsp"/></td>
        <th>机构代码：</th>
        <td><input name="company.setupNumber" type="text" class="input" id="setupNumber" value="${company.setupNumber}" size="25" maxlength="25"></td>
      	</tr>
      <tr>
        <th colspan="2">所在区域：</th>
        <td><div id="div-area"></div></td>
        <th>统一社会信用代码（工商注册号）：</th>
        <td><input name="company.regNum" type="text" class="input" id="regNum" value="${company.regNum}" size="25" maxlength="25"></td>
      </tr>
      <tr>
      	<th colspan="2">法定代表人<br />或主要负责人：</th>
        <td><input name="company.fddelegate" type="text" class="input" id="fddelegate" value="${company.fddelegate}" size="25" maxlength="20">&nbsp;<span style=color:red>*</span>
        <ui:v for="fddelegate" rule="require" empty="&nbsp;" pass="&nbsp;"/></td>
        <th>联系电话：</th>
        <td><input name="company.phoneCode" type="text" class="input" id="phoneCode" value="${company.phoneCode}" size="25" maxlength="13" style="ime-mode:disabled;">
        <ui:v for="phoneCode" rule="phone_mobile" require="false" warn="电话正确格式如0574-87364008。" pass="&nbsp;"/></td>
      </tr>
      <tr>
        <th colspan="2">规模以上单位：</th>
        <td>
        	<input type="radio" name="company.daCompanyPass.enterprise" value="true" <#if company?if_exists.daCompanyPass?if_exists.enterprise?exists && company.daCompanyPass.enterprise>checked</#if>/>是
        	&nbsp;&nbsp;<input type="radio" name="company.daCompanyPass.enterprise" value="false" <#if !company?if_exists.daCompanyPass?if_exists.enterprise?exists || !company.daCompanyPass.enterprise>checked</#if>/>否
        </td>
        <th>社会团体：</th>
        <td>
        	<input type="radio" name="company.daCompanyPass.orga" value="true" <#if company?if_exists.daCompanyPass?if_exists.orga?exists && company.daCompanyPass.orga>checked</#if>/>是
        	&nbsp;&nbsp;<input type="radio" name="company.daCompanyPass.orga" value="false" <#if !company?if_exists.daCompanyPass?if_exists.orga?exists || !company.daCompanyPass.orga>checked</#if>/>否
        </td>
      </tr>
      <tr id="tr_trade">
        <td id="td_trade" width="5%" style="background:#F0F0F0;color:#333;"><p align="right">行业分类：</p></td>
        <#list tradeTypes?if_exists as t>
        	<#if t_index!=0><tr id="tr_trade" style="display:none;"></#if>
	        <td width="12%" style="background:#F0F0F0;color:#333;display:none;" id="td_name">${t.name}</td>
	        <td colspan="3" id="td_trade_${t.id}" style="display:none;">
	        	<input type="checkbox" name="company.tradeTypes" id="fir_${t.id}" value="${t.id}" onClick="showSecondTrade(this);" disabled="true"/>&nbsp;&nbsp;
        		<#assign hasChild=false>
        		<#list t.daIndustryParameters?if_exists as t_dip><#if t_dip.type==1><#assign hasChild=true></#if></#list>
        		<#if t.daIndustryParameters?exists&&t.daIndustryParameters?size!=0&&hasChild>
		        	<span id="sp_sec_${t.id}" style="display:none;">
		        		<select name="company.tradeTypes" id="sel_sec_${t.id}" onChange="showThirdTrade(this)" disabled="true">
		        			<option value="-1">--请选择--</option>
		        			<#list t.daIndustryParameters?if_exists as sec_T>
		        				<#if sec_T.type==1>
		        					<option value="${sec_T.id}">${sec_T.name}</option>
		        				</#if>
		        			</#list>
		        		</select>
		        	</span>
		        	&nbsp;
		        	<span id="sp_thi_${t.id}" style="display:none">
		        		<select name="company.tradeTypes" id="sel_thi_${t.id}" disabled="true" onChange="showForthTrade(this);">
		        			<option value="-1">--请选择--</option>
		        		</select>
		        	</span>
		        	&nbsp;
		        	<span id="sp_for_${t.id}" style="display:none">
		        		<select name="company.tradeTypes" id="sel_for_${t.id}" disabled="true">
		        			<option value="-1">--请选择--</option>
		        		</select>
		        	</span>
        		</#if>
	        </td></tr>
        </#list>
    </table></td>
  </tr>
</table>
<input type="hidden" name="company.id" id="id" value="${company.id}"/>
<input type="hidden" name="company.daCompanyPass.id" id="id" value="${company.daCompanyPass.id}"/>
</form>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
<script type="text/javascript">
<#list tradeTypesAllow?if_exists as ta>
	get("fir_${ta.id}").disabled = false;
	if(get("sel_sec_${ta.id}")) {
		get("sel_sec_${ta.id}").disabled = false;
	}	
	if(get("sel_thi_${ta.id}")) {
		get("sel_thi_${ta.id}").disabled = false;
	}	
	if(get("sel_for_${ta.id}")) {
		get("sel_for_${ta.id}").disabled = false;
	}
</#list>
<#if (tradeTypesAllow?size>1)>
	get("td_trade").colSpan = "1";
	get("td_trade").rowSpan = "${tradeTypes.size()}";
	get("td_trade").innerHTML = "<p align='center'>行业分类</p>";
	if (get("tr_trade").length) {
		for (var i=1; i<get("tr_trade").length; i++) {
			get("tr_trade")[i].style.display = "";
		}
	}
	if (get("td_name").length) {
		for (var i=0; i<get("td_name").length; i++) {
			get("td_name")[i].style.display = "";
		}
	}
	for (var i=0; i<get("tr_trade").length; i++) {
		for (var j=0; j<get("tr_trade")[i].children.length; j++) {
			if (get("tr_trade")[i].children[j].id &&
				get("tr_trade")[i].children[j].id.indexOf("td_trade_") > -1) {
				get("tr_trade")[i].children[j].style.display = "";
			}
		}
	}
<#else>
	<#list tradeTypesAllow?if_exists as ta>
		if (get("tr_trade")[0].children[2] != get("td_trade_${ta.id}")) {
			get("tr_trade")[0].children[2].innerHTML = get("td_trade_${ta.id}").innerHTML;
			get("td_trade_${ta.id}").innerHTML = "";
		}
		get("tr_trade")[0].children[2].style.display = "";
		get("tr_trade")[0].children[0].colSpan="2";
		getName("company.tradeTypes")[0].onclick = function (){return false;};
	</#list>
</#if>
<#if company?exists>
	if ("${company.userId}"!="${userDetail.userId}" && "${admin?string}"=="false") {
		//get("img_save").style.display = "none";
	}
	<#list company.hzTradeTypes?if_exists as ht>
		<#list tradeTypes?if_exists as fir>
			<#if ht.id==fir.id>
				if(get("fir_${fir.id}")){
					get("fir_${fir.id}").checked = true;
					showSecondTrade(get("fir_${fir.id}"));
					if(get("sel_sec_${fir.id}"))
						showSecOption(get("sel_sec_${fir.id}"));
				}
			</#if>
		</#list>
	</#list>
</#if>
temp_img_save();
</script>
</#escape>
<@fkMacros.pageFooter />