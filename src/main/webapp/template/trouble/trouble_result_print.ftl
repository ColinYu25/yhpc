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
   <tr><td align="center" colspan="${areas?size+3}"><br><strong style="font-size:23px;line-height:30px;">安全生产隐患处理结果反馈表</strong></td></tr>
  </tr>
  <tr><td align="center" style="font-size:12pt;line-height:25px;">抄告编号：${trouble.troubleNo}　　　　　　　　　　　　　　　　　　　　反馈时间：${dept.submitTime?date}</td></tr>
</table>
<table width="700" cellspacing="1" cellpadding="0" border="0" align="center"  class="table_list3">
  <tr>
    <td width="9%" rowSpan="2"  align="center"><strong>反馈<br>部门</strong></td>
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
  <tr height="620" valign="top">
    <td colSpan="7"><strong>处理意见：</strong><br>${dept.description}</td>
  </tr>
</table>
</div>
<script>
	printParam(20,0,0,0,1);
	</script>
</#escape>
<@fkMacros.pageFooter />