<@fkMacros.pageHeader />
<#escape x as (x)!>
<@fkMacros.evaluation_result_head_menu 'vapor'/>
<script language="javascript">
	document.parentWindow.parent.resetWindow("modelResult",0,0,800,400);//窗口重画
</script>
<img src=${contextPath}/map/loadChart.xhtml />
</#escape> 
<@fkMacros.pageFooter />