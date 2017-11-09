<@fkMacros.company_pageheader />
<#escape x as (x)!> 
<body style="background:#fff;"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="about_qy_tab">
	  <tr >
	    <td width="18%" class="tit_bold"><span class="td1">执法检查</span></td>
	    <td width="82%" class="noborder">
	   		<table width="100%" cellspacing="0" cellpadding="0">
	   			<tr>
	   				 <td width="22%" class="tit">一年内检查数</td>
					  <td width="16%"><#if count??&&count!=0><a href="../companyPunishment/loadCompanyPunishments.xhtml" target="_blank">${count}</a><#else></#if>次</td>
					  <td width="22%" class="tit">行政处罚文书 </td>
					  <td width="16%"><#if count??&&count1!=0><a href="../companyPunishment/loadCompanyPunishmentDocumentList.xhtml" target="_blank">${count1}</a><#else></#if>份</td>
	   			</tr>
	   		</table>
	   </td>
	  </tr>
</table>
</#escape>            
<@fkMacros.pageFooter />