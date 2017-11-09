<@fkMacros.pageHeaderAjax />
<@fkMacros.winLIKE />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>待打包企业列表</th>
  </tr>
</table>
<form action="loadUnBagCompanies.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">单位名称：</th>
	    <td style="width:300px;"><input type="text" name="company.companyName" id="companyName" size="40" maxlength="50"></td>
	    <th style="width:75px;">地址：</th>
	    <td style="width:300px;"><input type="text" name="company.address" id="address" size="50" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th style="width:75px;">区域：</th>
	    <td><div id="div-area"></div>
	    </td>
	    <td colspan="2"><input type="submit" id="sub" value="查  询" onClick="get('companyForm').submit();" style="input_submit"/></td>
	  </tr>
	  	<input type="hidden" id="orderProperty" name="company.orderProperty" value="id"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
	</table>
</form>
<div class="menu">
  	<ul>
  	<li id="img_amend">		<a href="#" class="b2" onClick="dabao();"><b>打包</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th id="th_companyName" width="28%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>单位名称</th>
    <th id="th_address" width="25%" style="cursor:hand;" onClick="orderProperty('address');" nowrap>地址</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" onClick="orderProperty('fdDelegate');" nowrap>法人代表</th>
    <th id="th_phone" width="12%" style="cursor:hand;" onClick="orderProperty('phone');" nowrap>联系电话</th>
    <!--<th width="18%">所属行业</th>-->
  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="../company/loadCheckedCompany.xhtml?company.id=${c.id}">${c.companyName}</a>&nbsp;&nbsp;<#if c.daCompanyAcounts?size!=0><a href="../company/loadCompanyAcounts.xhtml?company.id=${c.id}"><font color='red'>异地</font></a></#if></div></td>
	    <td><div align="left">${c.regAddress}&nbsp;</div></td>
	    <td>${c.fddelegate}&nbsp;</td>
	    <td>${c.phoneCode}&nbsp;</td>
	    <!--<td><#list c.hzTradeTypes?if_exists as ct><#list tradeTypes?if_exists as t><#if t.id==ct.id>${ct.tradeName} </#if></#list></#list>&nbsp;</td>-->
	    <!--<td><#list c.hzTradeTypes?if_exists as ct>${ct.tradeName} </#list>&nbsp;</td>-->
	  <!--  <td><#list tradeTypes?if_exists as t><#list c.hzTradeTypes?if_exists as ct><#if t==ct><!-- ${ct.tradeName}--><!--</#if></#list><#list t.hzTradeTypes?if_exists as st><#list c.hzTradeTypes?if_exists as ct><#if st==ct> ${ct.tradeName}</#if></#list><#list st.hzTradeTypes?if_exists as sst><#list c.hzTradeTypes?if_exists as ct><#if sst==ct> ${ct.tradeName}</#if></#list></#list></#list><#list c.hzTradeTypes?if_exists as ct><#if (t==ct&&c.hzTradeTypes?if_exists?size>1)>;</#if></#list></#list>&nbsp;</td> -->
	  </tr>
	 </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function dabao() {
		var checkboxs = getTag('input');
		var companyIds="";
		var num=0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					companyIds=companyIds+checkboxs[i].value+","
					num=num+1;
				}
			}
		}
		if(num==0){
			alert("请选择复选框!");
			return;
		}
		var myAjax = new Ajax.Request("${contextPath}/bag/loadAllowBag.xhtml?companyIds="+companyIds.substring(0,companyIds.length-1),{method: "get",asynchronous:false,onSuccess:function(transport){
				flag= transport.responseText.evalJSON();
				if(flag){
					loadWindows('loadBagTypes.xhtml?companyIds='+companyIds.substring(0,companyIds.length-1),340,145,300,100,'dabao','打包');
				}else{
					alert("企业已取消确认,不允许打包,请刷新页面！");
				}
			}
		});
		
	}
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />