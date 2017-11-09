<@fkMacros.company_pageheader />
<#escape x as (x)!> 
<body style="background:#fff;"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="about_qy_tab">
      <tr>
        <td width="18%" class="tit_bold" title="点击进入系统">
    		<a href="${contextPath}/workspace/loadNavigatorWorkSpace.xhtml" target="_blank">隐患排查治理</a>
        </td>
        <td width="82%" class="noborder">
       		<table width="100%" cellspacing="0" cellpadding="0">
       			<tr>
       				<td width="22%" class="tit">
			        	季度报表
			        </td>
			        <td width="16%">
			    		<#if count1==0>
			    			<a href="../statistic/loadReportByCompany.xhtml" target="_blank" title="点击进入上报">未上报</a>
			    		<#else>
			    			<a href="../statistic/findCompanyQuarterReport.xhtml" target="_blank">已上报</a>
			    		</#if>
			        </td>
			        <td width="22%" class="tit">
			        	未治理重大隐患数
			        </td>
			       <td width="16%">
			    		<#if count==0><#else><a href="danger/loadDanger.xhtml" target="_blank">${count}</a></#if> 个
			        </td>
       			</tr>
       		</table>
       </td>
      </tr>
</table>
</#escape>            
<@fkMacros.pageFooter />