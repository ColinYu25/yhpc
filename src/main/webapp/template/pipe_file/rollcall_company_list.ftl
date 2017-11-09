<@fkMacros.pageHeader />
<#escape x as (x)!>
<script type="text/javascript">
	function createTrade(url) {
		var checkboxs = getTag('input');
		var checkSize = 0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					checkSize ++;				
				}
			}
		}
		if(checkSize <= 1){
			if ("${admin.toString()}"=="false"&&checkSize==0) {
				alert("请选择一个父行业。");
			} else {
				get("tradeTypesForm").action = url;
				get("tradeTypesForm").submit();
			}
		} else {
			alert("请将父行业选择为一个。");
		}
	}
</script>
<script type="text/javascript">
var enumObj=new Enum("${enumXmlUrl}");
var areaObj = new Area("${AreaXmlUrl}");
</script>
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">隐患挂牌列表</th>
  </tr>
</table>
<form action="loadFile.xhtml" method="post" name="rollcallCompanysForm" id="rollcallCompanysForm">
<input type="hidden" name="rollcallCompany.level" value="${rollcallCompany.level}" id="level"/>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
  <th>单位名称</th>
  <td colspan="2"><input type="text" name="company.companyName" size="50" value="${company.companyName}" onBlur="SpacesAll(this);"></td>
  <td><input type="submit" id="sub" value="查  询" onClick="get('rollcallCompanysForm').submit();" style="input_submit"/></td>
  </tr>
  <tr>
    <th width="12%">隐患地址</th>
    <td width="34%"><input type="text" name="rollcallCompany.daDanger.dangerAdd" size="50" value="${rollcallCompany.daDanger.dangerAdd}" onBlur="SpacesAll(this);"></td>
	<th width="13%">单位区域</th>
    <td width="41%"><div id="div-area"></div></td>
  </tr>
</table>
</form>

<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="loadNote('loadFileList.xhtml?type=${type?string}&rollcallCompany.id');"><b>查看</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>

<form action="deleteRollcallCompany.xhtml" method="post" name="rollcallCompanyFormForDelete" id="rollcallCompanyFormForDelete">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="24%" nowrap>单位名称</th>
    <th width="9%" nowrap>隐患编号</th>
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否整改</th>
    <th width="7%" nowrap>挂牌级别</th>
    <th width="9%" nowrap>督办单位</th>
    <th width="9%" nowrap>督办完成时间</th>
  </tr>
  <#if rollcallCompanies?exists>
  <#list rollcallCompanies?if_exists as r>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${r.id}" value="${r.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+r_index+1}<#else>${r_index+1}</#if></td>
    <td nowrap id="roleName" name="roleName"><div align="center"><a href="../company/loadCompany.xhtml?company.id=${r.daDanger.daCompanyPass.id}">${r.daDanger.daCompanyPass.daCompany.companyName}</a>&nbsp;</div></td>
    <td nowrap>${r.daDanger.dangerNo}&nbsp;</td>
    <td nowrap><div align="center"><a href="../danger/loadDanger.xhtml?danger.id=${r.daDanger.id}">${r.daDanger.dangerAdd}</a>&nbsp;</div></td>
    <td nowrap><#if (r.daDanger?if_exists.daDangerGorvers?size>0)>是<#else>否</#if></td>
    <td><#if r.level?exists><script type="text/javascript">document.write(enumObj.getSelect("${r.level}"));</script></#if>挂牌</td>
    <td><#if r.govment?exists><script type="text/javascript">document.write(areaObj.getArea("${r.govment}")[0]);</script></#if>&nbsp;</td>
    <td>${r.completeTime?date}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (rollcallCompanies?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<@fkMacros.muilt_select_js />
<#if rollcallCompany?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</body>
</#escape>
<@fkMacros.pageFooter />