<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
  <tr align="center" ><td>
<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
			<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
			<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/>
			</td></tr></table>
<div id='page1'>
<table width="90%" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
  <tr>
   <tr><td align="center" colspan="${areas?size+3}"><br><strong style="font-size:23px;line-height:30px;">安全生产隐患<#if dept.troubleCopyType==1>抄告<#elseif dept.troubleCopyType==2>下达<#elseif dept.troubleCopyType==3>上报</#if>表</strong></td></tr>
  </tr>
  <tr><td align="center" style="font-size:12pt;line-height:25px;">抄告编号：${trouble.troubleNo}　　　　　　　　　　　　　　　　　　　　<#if dept.troubleCopyType==1>抄告<#elseif dept.troubleCopyType==2>下达<#elseif dept.troubleCopyType==3>上报</#if>时间：${dept.submitTime?date}</td></tr>
</table>
<table width="700" cellspacing="1" cellpadding="0" border="0" align="center"  class="table_list3">
  <tr>
    <td width="9%" rowSpan="3"  align="center"><strong>隐患<br><br>发现<br><br>部门</strong></td>
    <td colSpan="6" height="45">${dept.findDeptName}</td>
  </tr>
  <tr height="45">
    <td width="17%" align="center"><strong>经 办 人</strong></td>
    <td width="18%">${dept.linkMan}</td>
    <td width="14%"  align="center"><strong>联系电话</strong></td>
    <td width="18%">${dept.linkTel}</td>
    <td width="12%"  align="center"><strong>批 准 人</strong></td>
    <td width="18%">${dept.passMan}</td>
  </tr>
  <tr height="120">
    <td align="center"><strong>处理意见</strong></td>
    <td colSpan="5">${dept.description}</td>
  </tr>
  <tr>
    <td  rowSpan="7" align="center"><strong>隐<br><br>患<br><br>情<br><br>况</strong></td>
    <td height="45" align="center"><strong>隐患单位名称</strong></td>
    <td colSpan="5">${trouble.troubleCompanyName}</td>
  </tr>
  <tr height="45">
    <td align="center"><strong>所在区域</strong></td>
    <td colSpan="5">
    ${trouble.firstAreaName}${trouble.secondAreaName}${trouble.thirdAreaName}
    </td>
  </tr>
  <tr height="45">
    <td align="center"><strong>隐患地址</strong></td>
    <td colSpan="5">${trouble.dangerAdd}</td>
  </tr>
  <tr height="45">
    <td align="center"><strong>法定代表人</strong></td>
    <td>${trouble.fddelegate}</td>
    <td align="center"><strong>主要负责人</strong></td>
    <td colSpan="3">${trouble.principal}</td>
  </tr>
  <tr height="45">
    <td align="center"><strong>联系电话</strong></td>
    <td>${trouble.linkTel}</td>
    <td align="center"><strong>手　　机</strong></td>
    <td colSpan="3">${trouble.linkMobile}</td>
  </tr>
  <tr height="120">
    <td align="center"><strong>隐患描述</strong></td>
    <td colSpan="5">${trouble.description}</td>
  </tr>
  <tr height="45">
    <td align="center"><strong>相关附件</strong></td>
    <td colSpan="5">
    <#if troubleFiles?exists>
	  	<#list troubleFiles?if_exists as f>
	  	<a href="${contextPath}${f.filePath}">${f.fileName}</a><#if f_index+1 != troubleFiles?size><br></#if>
	  	</#list>
  	</#if>
	&nbsp;</td>
  </tr>
  <#if dept.troubleCopyType==1>
  <tr height="45">
    <td colSpan="2" align="center"><strong>主送单位</strong></td>
    <td colSpan="5">
	<#if enums?exists>
	  	<#list enums?if_exists as i>
	  		<#assign checked=''>
	  			<#if dept?exists>
  					<#if i.enumCode==dept.mostlyCompany>
  						<#if dept.mostlyCompanyArea==0>宁波市<#else>${dept.mostlyCompanyAreaName}</#if>${i.enumName}
  					</#if>
	  			</#if>
	  	</#list>
  	</#if>
	&nbsp;</td>
  </tr>
  <tr height="45">
    <td colSpan="2" align="center"><strong>抄送单位</strong></td>
    <td colSpan="5">
    <#if enums?exists>
	  	<#list enums?if_exists as i>
	  		<#assign checked=''>
	  			<#if dept?exists>
	  				<#if dept.copyCompany?exists>
		  				<#list dept.copyCompany?split(",") as c>
		  					<#if i.enumCode==c?trim>
		  						<#if dept.mostlyCompanyArea==0>宁波市<#else>${dept.mostlyCompanyAreaName}</#if>${i.enumName}　　
		  					</#if>
	  					</#list>
  					</#if>
	  			</#if>
	  	</#list>
  	</#if>
    &nbsp;</td>
  </tr>
  <#else>
  <tr height="45">
    <td colSpan="2" align="center"><strong><#if dept.troubleCopyType==2>下达<#elseif dept.troubleCopyType==3>上报</#if>部门（单位）</strong></td>
    <td colSpan="5"><#if dept.mostlyCompanyArea==0>宁波市<#else>${dept.mostlyCompanyAreaName}</#if>${fkEnum.enumName}&nbsp;</td>
   </tr>
  </#if>
</table>
<table width="700" border="0" height="20" cellspacing="0" align="center" cellpadding="0">
  <tr><td align="left" style="font-size:12pt;line-height:25px;">注：请在10个工作日内回复隐患处理情况。</td></tr>
</table>
</div>
<script>
	printParam(20,0,0,0,1);
	</script>
</#escape>
<@fkMacros.pageFooter />