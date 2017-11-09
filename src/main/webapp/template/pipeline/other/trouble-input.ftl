<@fkMacros.pageHeader />
<#escape x as (x)!>
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${base}/datePicker/WdatePicker.js"></script>
<@fkMacros.initAreaXML />
<div id="header">
	<ul id="primary">
		<li><a href="javascript:go_Info('${entity.daPipelineInfo.daPipelineCompanyinfo.id}', '${entity.daPipelineInfo.id}');" style="width:60px;">单位情况</a></li>
		<li><a href="javascript:go_pipeLines('${entity.daPipelineInfo.id}');">管道情况</a></li>
		<li><a href="javascript:go('notSame_list.xhtml?entity.type=0', '${entity.daPipelineInfo.id}');" style="width:80px;">图纸不一致处</a></li>
		<#if includeMenu?? && includeMenu=='rq-menu' ><#else>
		<li><a href="javascript:go('cyrymjcs_list.xhtml?entity.type=1', '${entity.daPipelineInfo.id}');" style="width:140px;">穿越人员密集场所情况</a></li>				
		</#if>
		<li><a href="javascript:go('fhqsg_list.xhtml?entity.type=2', '${entity.daPipelineInfo.id}');" style="width:100px;">防护区施工情况</a></li>						
		<li><a href="javascript:go('accident_list.xhtml?entity.type=3', '${entity.daPipelineInfo.id}');" style="width:90px;">安全事故情况</a></li>						
		<li><a href="javascript:go('wzzy.xhtml?entity.type=4', '${entity.daPipelineInfo.id}');" style="width:90px;">违章占压情况</a></li>						
		<li><span style="width:60px;">主要隐患</span></li>		
		<li><a href="javascript:go('zfxt_list.xhtml?entity.type=5', '${entity.daPipelineInfo.id}');" style="width:60px;">政府协调</a></li>						
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>主要隐患</th>
		  </tr>
		</table>
		<div class="menu">
		  	<ul>
			<li id="img_save"><a class="b1" href="javaScript:submitCreate();"><b>保存</b></a></li>
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
			</ul>
		</div> 
		<@fkMacros.formValidator 'companyForm' />
		<form id="companyForm" name="companyForm" method="post" action="trouble_save.xhtml">
		<input type="hidden" name="entity.id" value="${entity.id}" />
		<input type="hidden" name="entity.daPipelineInfo.id" value="${entity.daPipelineInfo.id}" id="pipeLineId" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th>隐患类型</th>
		        <td>
		        <#assign type1="checked"/>
		        <#assign type2=""/>
		        <#assign type3=""/>
		         <#assign type4=""/>
		        <#assign type5=""/>
		         <#assign type6=""/>
		        <#if entity?? && entity.type ==1010003>
			        <#assign type2="checked"/>        
		        <#elseif entity?? && entity.type ==1010004>
			        <#assign type3="checked"/>     
			    <#elseif entity?? && entity.type ==1010005>
			        <#assign type4="checked"/>   
			    <#elseif entity?? && entity.type ==1010006>
			        <#assign type5="checked"/>     
		        <#elseif entity?? && entity.type ==1010007>
			        <#assign type6="checked"/>        
		        </#if>                         
		        <input type="radio" name="entity.type" value="1010002" ${type1}/>违章占压
		        <input type="radio" name="entity.type" value="1010003" ${type2}/>检验检测        
		        <input type="radio" name="entity.type" value="1010004" ${type3}/>建设施工   
		        <input type="radio" name="entity.type" value="1010005" ${type4}/>标志标识
		        <input type="radio" name="entity.type" value="1010006" ${type5}/>设备损坏        
		        <input type="radio" name="entity.type" value="1010007" ${type6}/>其他
		      </tr>
		      <tr>
		        <th>区域</th>
		        <td>
		        <div id="div-area"></div>
		        </td>
		      </tr>        
		      <tr>
		        <th>隐患描述</th>
		        <td>
		        <input type="text" name="entity.content" id="content1" maxlength="500" value="${entity.content}"/>
		        <ui:v for="content1"  rule="require" empty="附件不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		      </tr>      
		      <tr>
		        <th>治理状态</th>
		        <td>
		        <#assign isZl=""/>
		        <#assign isWzl="checked"/>        
		        <#if entity?? && entity.status == 1>
			        <#assign isZl="checked"/>
			        <#assign isWzl=""/>                
		        </#if>
		        <input type="radio" name="entity.status" value="0" ${isWzl}/>未治理
		        <input type="radio" name="entity.status" value="1" ${isZl}/>已治理        
		        </td>
		      </tr>      
		    </table>
		</form>
	</div>
</div>
</body>
</html>
</#escape>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
	jQuery.noConflict();
	function submitCreate(){
		if(formValidator.validate()){
			get("companyForm").submit();
	  		return false;
	 	}
	}

	function go(url, pipeLineId){
		if (pipeLineId != "" && pipeLineId > 0){
			window.location.href = url +　"&entity.daPipelineInfo.id=" + pipeLineId;	
		}else{
			alert("请先保存管道信息");
		}
	}
	
	function go_pipeLines(pipeId){
		<#if includeMenu?? && includeMenu=='rq-menu'>
			window.location.href = "rq_pipeline_update.xhtml?entity.id=" + pipeId;
		<#else>
			window.location.href = "yq_pipeline_update.xhtml?entity.id=" + pipeId;	
		</#if>	
	}
	
	function go_Info(companyId, pipeId){
		<#if includeMenu?? && includeMenu=='rq-menu'>
			window.location.href = "rqCompany_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;	
		<#else>
			window.location.href = "company_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;		
		</#if>
	}

</script>
<@fkMacros.muilt_select_js />
<@fkMacros.selectAreas_fun "${entity?if_exists.firstArea?if_exists}" "${entity?if_exists.secondArea?if_exists}" "${entity?if_exists.thirdArea?if_exists}" "${entity?if_exists.fouthArea?if_exists}" "${entity?if_exists.fifthArea?if_exists}" "entity."/>