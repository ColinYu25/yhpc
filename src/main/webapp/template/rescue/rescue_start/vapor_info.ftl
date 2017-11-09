<@fkMacros.pageHeader />
<#escape x as (x)!> 
<script language="javascript">
		if(parent.window.analyseModule==null){ //风险评价
			parent.window.resetWindow("modelWindow",200,100,545,169);//窗口重画
		}
		function calculate(){
			  if(formValidator.validate()){
			 		  $('vaporform').action="analyseCalculate.xhtml?companyId=${companyId}";
				      $('vaporform').set('send', {onComplete: function(response) { 
					  	if(response=='error'){
					  		alert("模拟计算过程出现错误，请检查输入数据！");
					  		return false;
					  	}
					  	<#if analyseModule?exists>
						  	 //应急救援
				  			  parent.window.rescueModelType="vapor";//设定全局模拟类型
				  			  parent.window.rescueModelId=response;//设定全局模拟输入保存的ID
				 		  	  parent.parent.window.resultInfo(event,'vapor','${companyId}');
					 		  document.location.href="${contextPath}/rescue/loadPrearrangeds.xhtml";
						<#else>
					 		  parent.window.resultInfo(event,'company','${companyId}');
					 		  parent.window.closeWindow('modelWindow');
						</#if>
					}});
			  	  $('vaporform').send();
			  }
		}
</script>
<@fkMacros.formValidator 'vaporform' 2 />
  <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <form id="vaporform" name="vaporform" method="post" action="">

    <tr>
      <th width="20%">危险源名称</th>
      <td colspan="3"><!--a href="#" title="设置储罐"-->${company.name}<!--/a--></td>
    </tr>   
    <!--tr>
      <th>储罐经度</th>
      <td>${phTankParam.x}</td>
      <th>储罐纬度</th>
      <td>${phTankParam.y}</td>
    </tr-->
          
    <tr>
      <th>温度</th>
      <td>
      	<input id="gnwz" name="phVapor.gnwz" value="${phVapor.gnwz?c}" type="text" class="input" size="12" maxlength="30" />&nbsp;℃&nbsp;&nbsp;&nbsp;
        <ui:v for="gnwz" rule="double" empty="温度不能为空" pass="&nbsp;" warn="温度的数据格式不正确" />          
     </td>
     <th>泄漏质量</th>
      <td>
      	<input id="xrzl" name="phVapor.xrzl" value="${phVapor.xrzl?c}" type="text" class="input" size="12" maxlength="14"/>&nbsp;kg&nbsp;&nbsp;&nbsp;
        <ui:v for="xrzl" rule="double" min="0.0001"  empty="物质质量不能为空" pass="&nbsp;" warn="物质质量的数据格式不正确" />      
      </td>
    </tr>         
    <tr>
      <th colspan="5"><div  align="center"><input name="to_edu" id="to_save" type="button" class="botton4" value="模拟计算" onclick="javascript:calculate();" />&nbsp;&nbsp;
      <#if phVapor?exists>
      	<!--input name="to_reset" id="to_reset" type="reset" onclick="javascript:del();" class="btn_save" value="删除模拟"/-->
      </#if>
      </div>
      </th>
    </tr>
    <input type="hidden" name="tankId" value="${tankId}" />
    </form>
  </table>
</#escape> 
<@fkMacros.pageFooter />