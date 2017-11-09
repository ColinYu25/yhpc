<@fkMacros.pageHeader />
<#escape x as (x)!> 
<@enum.initAreaXML/>
<style>
/*表格*/
.about_qy_tab{ border-collapse:collapse;}
.about_qy_tab td.tit{ padding-left:3px; background:#fff7ed; text-align:left; font-size:12px;}
.about_qy_tab td.tit_bold{ padding-left:3px; background:#fff7ed; text-align:left; font-weight:bold;font-size:12px;}
.about_qy_tab td{ padding:3px; line-height:22px; border:1px solid #f7e0c6; text-align:left;}


.noborder td{ border:none;font-size:12px;}
.about_qy_tab table{ border-collapse:collapse;}
.about_qy_tab table textarea{ border:1px solid #f7e0c6;}
.about_qy_tab table td{ border:1px solid #f7e0c6;}
.about_qy_tab table td.right_bot_border{ border-bottom:1px solid #fff7ed; border-top:1px solid #fff7ed;}
.about_qy_tab table td.tit{ background:background:#fff7ed; }
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="about_qy_tab">
          <tr>
           <td width="18%" class="tit_bold">标准化</td>
           <td width="82%" class="noborder">
            	<table width="100%" cellspacing="0" cellpadding="0">
       				<tr>
       					<td width="11%" class="tit" style="background:#fff7ed;">行业</td>
             			<td width="8%">&nbsp;</td>
            			<td width="11%" class="tit" style="background:#fff7ed;">证书号</td>
            			<td width="8%">&nbsp;</td>
            			<td width="11%" class="tit" style="background:#fff7ed;">等级</td>
            			<td width="8%">&nbsp;</td>
            			<td width="11%" class="tit" style="background:#fff7ed;">有效期</td>
            			<td width="8%">&nbsp;</td>
       				</tr>
       			</table>
            </td>
          </tr>
</table>
</#escape> 
<@fkMacros.pageFooter />