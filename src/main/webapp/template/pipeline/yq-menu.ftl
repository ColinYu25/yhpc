<#escape x as (x)!>
	<ul id="primary">
		<li>
		<#if current?? && current == 'pipeline'>
			<span style="width:60px;">单位情况</span>
		<#else>
			<li><a href="javascript:go_Info();" style="width:60px;">单位情况</a></li>
		</#if>	
		
		<#if current?? && current == 'pipe'>
			<span style="width:60px;">管道情况</span>
		<#else>
			<li><a href="javascript:go_pipeLines();">管道情况</a></li>
		</#if>			
			
		<li>
		<#if current?? && current == 'notSame'>
			<span style="width:80px;">图纸不一致处</span>
		<#else>
			<a href="javascript:go('notSame_list.xhtml?entity.type=0');;" style="width:80px;">图纸不一致处</a>
		</#if>
		</li>
		<li>
		<#if current?? && current == 'cyrymjcs'>
			<span style="width:140px;">穿越人员密集场所情况</span>
		<#else>
			<a href="javascript:go('cyrymjcs_list.xhtml?entity.type=1');" style="width:140px;">穿越人员密集场所情况</a>
		</#if>
		</li>				
		<li>
		<#if current?? && current == 'fhqsg'>
			<span style="width:100px;">防护区施工情况</span>
		<#else>
			<a href="javascript:go('fhqsg_list.xhtml?entity.type=2');;" style="width:100px;">防护区施工情况</a></li>						
		</#if>
		<li>
		<#if current?? && current == 'accident'>
			<span style="width:90px;">安全事故情况</span>
		<#else>
			<a href="javascript:go('accident_list.xhtml?entity.type=3');" style="width:90px;">安全事故情况</a>
		</#if>
		</li>						
		<li>
		<#if current?? && current == 'wzzy'>
		<span style="width:90px;">违章占压情况</span>
		<#else>
		<a href="javascript:go('wzzy.xhtml?entity.type=4');" style="width:90px;">违章占压情况</a>
		</#if>
		</li>						
		<li>
		<#if current?? && current == 'trouble'>
		<span style="width:60px;">主要隐患</span>
		<#else>
		<a href="javascript:go('trouble_list.xhtml?');" style="width:60px;">主要隐患</a>
		</#if>
		</li>		
		<li>
		<#if current?? && current == 'zfxt'>
			<span style="width:60px;">政府协调</span>
		<#else>
			<a href="javascript:go('zfxt_list.xhtml?entity.type=5');" style="width:60px;">政府协调</a>
		</#if>	
		</li>						
	</ul>
</#escape>
	
<script>
function go(url){
	var pipeLineId = document.getElementById("pipeLineId").value;
	if (pipeLineId != "" && pipeLineId > 0){
		window.location.href = url +　"&entity.daPipelineInfo.id=" + pipeLineId;	
	}else{
		alert("请先保存管道信息");
	}
}

function go_pipeLines(){
	window.location.href = "yq_pipeline_update.xhtml?entity.id=${pipeId}";
}

function go_Info(){
	window.location.href = "company_create.xhtml?company.id=${companyId}";	
}
</script>