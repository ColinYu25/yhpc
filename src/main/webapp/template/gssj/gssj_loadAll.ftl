<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>工商同步数据企业列表</th>
  </tr>
</table>
<form action="load_gssj_loadAll.xhtml" method="post" name="gsQydjForm" id="gsQydjForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	
	  <tr>
	    <th>单位名称</th>
	    <td width="35%"><input type="text" name="gsQydj.qymc" id="qymc" value="${gsQydj.qymc}" size="39" maxlength="50"></td>
	    <th>地　　址</th>
	    <td width="38%"><input type="text" name="gsQydj.zs" id="zs" value="${gsQydj.zs}" size="35" maxlength="50"></td>
	  </tr>
	  
	  <tr>
	<th colspan="4"><div align="center"><input type="submit" id="sub" value="搜　索"  class="confirm_but" style="height:25px; width:80px"  onClick="submitForm('gsQydjForm');"/></div></th></tr>
	</table>
</form>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th  id="th_id" width="4%" nowrap>序号</th>
    <th  id="th_gsQydjName" width="30%" nowrap>单位名称</th>
    <th  id="th_regAddress" width="30%" nowrap>地　　址</th>
    <th  id="th_fddelegate" width="20%" nowrap>法人代表</th>
    <th  id="th_phoneCode"  nowrap>同步时间</th>
  </tr>
  <#if gsQydjs?exists>
  	<#list gsQydjs?if_exists as c>
	  <tr>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="../company/loadCompany.xhtml?company.id=${c.id}">${c.qymc}</a></div></td>
	    <td><div align="left">${c.zs}&nbsp;</div></td>
	    <td>${c.fddbr}&nbsp;</td>	    
	    <td>${c.rkrq[0..10]}&nbsp;</td>
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

function submitForm(formName){
	var formObj=get(formName);
	formObj.submit();
}
</script>
</#escape>
<@fkMacros.pageFooter />