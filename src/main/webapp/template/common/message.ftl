<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script type="text/javascript">
	alert("<@s.property value='%{getText(messageKey)}'/>");
	history.back(-1);
</script>
</#escape>
<@fkMacros.pageFooter />
