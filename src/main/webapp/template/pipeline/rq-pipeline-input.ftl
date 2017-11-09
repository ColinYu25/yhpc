<@fkMacros.pageHeader />
<#escape x as (x)!>
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${base}/datePicker/WdatePicker.js"></script>
<style>
.b_xcjcjl:hover {text-decoration: none; background:url(${resourcePath}/img/001.gif) no-repeat;}
</style>
<@fkMacros.initAreaXML />
<SCRIPT type=text/javascript>
		  WinLIKEerrorpage='${base}/winlike/winlike/winman/hlp-error.html';
		  WinLIKEskinpath = "${base}/winlike/skins/";
		  WinLIKEfilepath = "${base}/winlike/winlike/";
</SCRIPT>
<SCRIPT type=text/javascript src="${resourcePath}/js/wininit.js"></SCRIPT>
<SCRIPT type=text/javascript src="${resourcePath}/js/winman.js"></SCRIPT>
<SCRIPT type=text/javascript>
	window.onResize=WinLIKE.resizewindows;
	window.onload=WinLIKE.init;
</SCRIPT>
<META name=GENERATOR content="MSHTML 8.00.6001.18702"></HEAD>
<BODY>
<IMG style="Z-INDEX: 4000; POSITION: absolute; WIDTH: 100%; HEIGHT: 100%; TOP: 0px; LEFT: 0px" id=ih_ src="${resourcePath}/img/trans.gif">
<div id="header">
	<ul id="primary">
		<li><a href="javascript:go_Info('${entity.daPipelineCompanyinfo.id}', '${entity.id}');" style="width:60px;">单位情况</a></li>
		<li><span style="width:60px;">管道情况</span></li>
		<li><a href="javascript:go('notSame_list.xhtml?entity.type=0', '${entity.id}');" style="width:80px;">图纸不一致处</a></li>
		<li><a href="javascript:go('fhqsg_list.xhtml?entity.type=2', '${entity.id}');" style="width:100px;">防护区施工情况</a></li>						
		<li><a href="javascript:go('accident_list.xhtml?entity.type=3', '${entity.id}');" style="width:90px;">安全事故情况</a></li>						
		<li><a href="javascript:go('wzzy.xhtml?entity.type=4', '${entity.id}');" style="width:90px;">违章占压情况</a></li>						
		<li><a href="javascript:go('trouble_list.xhtml?', '${entity.id}');" style="width:60px;">主要隐患</a></li>		
		<li><a href="javascript:go('zfxt_list.xhtml?entity.type=5', '${entity.id}');" style="width:60px;">政府协调</a></li>						
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>燃气管道基本情况调查表</th>
		  </tr>
		</table>
		<div class="menu">
			<div style="float:left;">
			  	<ul>
					<li id="img_left"></li>
					<li id="img_save"><a class="b1" href="javascript:submitCreate();"><b>保存</b></a></li>
					<li id="img_refurbish"><a class="b4" href="javascript:window.location.reload();"><b>刷新</b></a></li>
					<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
					<#if version?exists && entity.pipeVersion?exists && version != entity.pipeVersion><li id="img_logout"><a href="javascript:update2new();" class="b10"><b>更新</b></a></li></#if>
				</ul>
			</div>
		</div> 
		<@fkMacros.formValidator 'companyForm' />
		<form id="companyForm" name="companyForm" method="post" action="rq_pipeline_save.xhtml">
			<input type="hidden" name="entity.id" value="${entity.id}" id="pipeLineId"/>
			<input type="hidden" name="entity.daPipelineCompanyinfo.id" value="${entity.daPipelineCompanyinfo.id}" id="companyId" />
			<input type="hidden" name="entity.pipeVersion" value="${entity.pipeVersion}" id="pipeVersion"/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th style="text-align:center;width:14%;">所在区域</th>
		        <td colspan="4" style="width:86%">
			        <select name="entity.areaCode">
			        <option value="0">--请选择--</option>        
			        <#if areas??>
			        <#list areas as item>
			        <option value="${item.areaCode}" <#if entity?? && entity.areaCode?? && entity.areaCode == item.areaCode>selected</#if>>${item.areaName}</option>
			        </#list>
			        </#if>
			        </select>
		         	&nbsp;
		         </td>
		      </tr>
		      <tr>
		        <th rowspan="5" style="text-align:center;width:14%;"><p>管道</p>
		        <p>技术</p>
		        <p>情况</p></th>
		      </tr>
		      <tr>
		        <th style="width:15%">管道铺设方式</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>    	
			        <#if entity?? && entity.buildType?? && entity.buildType == 2>
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
				    	<#assign display=""/>    		    	
			        </#if> 
			        <label><input type="radio" name="entity.buildType" value="1" <#if entity?? && entity.buildType?? && entity.buildType == 1> checked </#if> onclick="$('mdNumSpan').style.display='none'">架空</label>
			        <label><input type="radio" name="entity.buildType" value="3" <#if entity?? && entity.buildType?? && entity.buildType == 3> checked </#if> onclick="$('mdNumSpan').style.display='none'">地面</label>
			        <br/>
			        <label><input type="radio" name="entity.buildType" value="2" <#if entity?? && entity.buildType?? && entity.buildType == 2> checked </#if> onclick="$('mdNumSpan').style.display=''">埋地</label>
			        <span style="display:${display}" id="mdNumSpan">深度：<input type="text" name="entity.mdNumSpan" value="${entity.mdNumSpan?string('####.###')}" id="mdNumSpan" maxlength="10" size="2"/>m</span>
			        &nbsp;
			    </td>
		        <th style="width:15%">管道长度</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.length" value="${entity.length?string('####.###')}" id="lineLength" maxlength="10" size="2"/>Km <ui:v for="lineLength"  rule="require" empty="管道止点不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>        
		      </tr>
		      <tr>
		        <th style="width:15%">传输介质</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.medium" value="${entity.medium}" id="medium" maxlength="50"/><ui:v for="medium"  rule="require" empty="传输介质不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:15%">管道材质</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.material" value="${entity.material}" id="material" maxlength="50"/><ui:v for="material"  rule="require" empty="管道材质不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">设计压力</th>
		        <td style="width:22%">
			        <input type="text" name="entity.pressure" value="${entity.pressure?string('####.###')}" id="pressure" maxlength="10" size="2"/> Mpa -
			        <input type="text" name="entity.pressure2" value="${entity.pressure2?string('####.###')}" id="pressure2" maxlength="10" size="2"/> Mpa      
			        <ui:v for="pressure"  rule="double" empty="管道止点不允许为空" pass="&nbsp;" warn="&nbsp;"/>
			    </td>
		        <th style="width:15%">最高工作压力</th>
		        <td style="width:22%">
			        <input type="text" name="entity.maxPressure" value="${entity.maxPressure?string('####.###')}" id="maxPressure" maxlength="10" size="2"/> Mpa -
			        <input type="text" name="entity.maxPressure2" value="${entity.maxPressure2?string('####.###')}" id="maxPressure2" maxlength="10" size="2"/> Mpa <ui:v for="maxPressure"  rule="double" empty="最高工作压力不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">管径</th>
		        <td style="width:22%">
			        <input type="text" name="entity.diameter" value="${entity.diameter?string('####.###')}" id="diameter" maxlength="10" size="2"/> mm -
			        <input type="text" name="entity.diameter2" value="${entity.diameter2?string('####.###')}" id="diameter2" maxlength="10" size="2"/> mm
			        <ui:v for="diameter"  rule="require" empty="管径不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		       </td>
		        <th style="width:15%">壁厚</th>
		        <td style="width:22%">
			        <input type="text" name="entity.deep" value="${entity.deep?string('####.###')}" id="deep" maxlength="10" size="2"/> mm -
			        <input type="text" name="entity.deep2" value="${entity.deep2?string('####.###')}" id="deep2" maxlength="10" size="2"/> mm      
			        <ui:v for="deep"  rule="require" empty="壁厚不允许为空" pass="&nbsp;" warn="&nbsp;"/>
			    </td>
		      </tr>
		    </table>
	    	<br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th rowspan="4" style="text-align:center;width:14%;">
		          <p>建设</p>
		          <p>管理</p>
		          <p>情况</p>
		        </th>
		      </tr>
		      <tr>
		        <th style="width:15%">是否办理规划许可</th>
		        <td style="width:22%">
			        <label><input type="radio" name="entity.ghxklx" value="2"  <#if entity?? && entity.ghxklx?? && entity.ghxklx == 2>checked</#if> /> 全部办理 </label>
			        <label><input type="radio" name="entity.ghxklx" value="1" <#if entity?? && entity.ghxklx?? && entity.ghxklx == 1>checked</#if>/> 部分办理 </label>
			        <label><input type="radio" name="entity.ghxklx" value="0"  <#if entity?? && entity.ghxklx?? && entity.ghxklx == 0>checked</#if>/> 没有办理 </label>
			        &nbsp;
		        </td>
		        <th style="width:15%">是否通过竣工验收</th>
		        <td style="width:22%">
			        <label><input type="radio" name="entity.jgys" value="2" <#if entity?? && entity.jgys?? && entity.jgys == 2>checked</#if>/> 全部通过 </label>
			        <label><input type="radio" name="entity.jgys" value="1" <#if entity?? && entity.jgys?? && entity.jgys == 1>checked</#if>/> 部分通过 </label>        
			        <label><input type="radio" name="entity.jgys" value="0" <#if entity?? && entity.jgys?? && entity.jgys == 0>checked</#if>/> 未通过 </label>                
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">是否办妥压力管道使用登记</th>
		        <td style="width:22%">
			        <label><input type="radio" name="entity.ylgdsydj" value="2" <#if entity?? && entity.ylgdsydj?? && entity.ylgdsydj == 2>checked</#if> /> 全部办理 </label>
			        <label><input type="radio" name="entity.ylgdsydj" value="1" <#if entity?? && entity.ylgdsydj?? && entity.ylgdsydj == 1>checked</#if>/> 部分办理 </label>
			        <label><input type="radio" name="entity.ylgdsydj" value="0" <#if entity?? && entity.ylgdsydj?? && entity.ylgdsydj == 0>checked</#if>/> 没有办理 </label>
			        &nbsp;
		        </td>
		        <th style="width:15%">是否取得管道燃气特许经营许可权</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasJyxkq?? && entity.hasJyxkq == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	        
			        <label><input type="radio" name="entity.hasJyxkq" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.hasJyxkq" value="false" ${isFalse}/> 否 </label>
			        &nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">实际路径是否与竣工图纸一致</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasSamePath?? && entity.hasSamePath == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasSamePath" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasSamePath" value="false" ${isFalse}/> 否 </label>          
		        </td>
		        <th style="width:15%">★实际路径与竣工图纸不一致处</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.notSamePathNum" value="${entity.notSamePathNum?string('####.###')}" id="notSamePathNum" maxlength="10" size="2"/>处<ui:v for="notSamePathNum"  rule="require" empty="与竣工图纸不一致处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		    </table>	
	    	<br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th rowspan="5" style="text-align:center;width:14%;"><p>使用</p>
		          <p>管理</p>
		          <p>情况</p></th>
		        <th style="width:15%">管道是否定期检验检测</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasCheck?? && entity.hasCheck == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasCheck" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasCheck" value="false" ${isFalse}/> 否 </label>            
		        </td>
		        <th style="width:15%">应急预案是否制定</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasYjya?? && entity.hasYjya == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasYjya" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasYjya" value="false" ${isFalse}/> 否 </label>            
			        &nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">★管道曾发生安全事故情况</th>
		        <td style="width:15%">
		        	<input type="text" name="entity.cfssgPathNum" value="${entity.cfssgPathNum?string('####.###')}" id="cfssgPathNum" maxlength="10" size="2"/>处<ui:v for="cfssgPathNum"  rule="require" empty="穿曾发生安全事故处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:15%">★管道安全防护区内建设施工情况</th>
		        <td style="width:15%">
		        	<input type="text" name="entity.aqfhPathNum" value="${entity.aqfhPathNum?string('####.###')}" id="aqfhPathNum" maxlength="10" size="2"/>处<ui:v for="aqfhPathNum"  rule="require" empty="安全防护区建设施工处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">★管道被违章占压情况</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.wzzyPathNum" value="${entity.wzzyPathNum?string('####.###')}" id="wzzyPathNum" maxlength="10" size="2"/>处<ui:v for="wzzyPathNum"  rule="require" empty="被违章占压处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:15%">★其它主要隐患</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.qtyhPathNum" value="${entity.qtyhPathNum?string('####.###')}" id="qtyhPathNum" maxlength="10" size="2"/>个<ui:v for="qtyhPathNum"  rule="require" empty="其他主要隐患个不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">★整改是否需要政府协调</th>
		        <td colspan="3" style="width:71%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasZfxt?? && entity.hasZfxt == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasZfxt" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.hasZfxt" value="false" ${isFalse}/> 无 </label>  
		        	&nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:15%">有无标志标识</th>
		        <td style="width:22%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasSign?? && entity.hasSign == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasSign" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.hasSign" value="false" ${isFalse}/> 无 </label>           
		        </td>
		        <th style="width:15%">巡查频率</th>
		        <td style="width:22%">
		        	<input type="text" name="entity.checkPercent" value="${entity.checkPercent}" id="checkPercent"  maxlength="20"/><ui:v for="checkPercent"  rule="require" empty="巡查频率不允许为空" pass="&nbsp;" warn="巡查频率只能输入数字&nbsp;"/>
		        </td>
		      </tr>      
		    </table>
		    <br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th style="width:15%">单位负责人</th>
		        <td style="width:22%"><input type="text" name="entity.fzr" value="${entity.fzr}" maxlength="15" id="fzr"/><ui:v for="fzr"  rule="require" empty="单位负责人不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		        <th style="width:15%">填表人</th>
		        <td style="width:22%"><input type="text" name="entity.tbr" value="${entity.tbr}" maxlength="10" id="tbr"/><ui:v for="tbr"  rule="require" empty="填表人不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		        <th style="width:15%">填表日期</th>
		        <td style="width:22%"><input type="text" name="entity.inputDate" value="${entity.inputDate?string('yyyy-MM-dd')}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" maxlength="10" id="inputDate"/><ui:v for="inputDate"  rule="require" empty="填表日期不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
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
			if(GetRadioValue("entity.buildType")==null){
				alert("管道铺设方式不能为空！");
			}else{
				get("companyForm").submit();
			}
	 	}
	}
	
	function go_Info(){
		var companyId = jQuery("#companyId").val();
		if (companyId != ""){
			window.location.href = "rqCompany_update.xhtml?entity.id=" + companyId;	
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
		window.location.href = "rq_pipeline_update.xhtml?entity.id=" + pipeId;
	}
	
	function go_Info(companyId, pipeId){
		window.location.href = "rqCompany_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;	
	}
	function GetRadioValue(RadioName){
	    var objByName;    
	    objByName=document.getElementsByName(RadioName);
	    if(objByName!=null){
	        var i;
	        for(i=0;i<objByName.length;i++){
	            if(objByName[i].checked){
	                return objByName[i].value;            
	            }
	        }
	    }
	    return null;
	}
	
	function update2new(){
		if(formValidator.validate()){
			if(GetRadioValue("entity.buildType")==null){
				alert("管道铺设方式不能为空！");
			}else{
				if(confirm("更新至最新的页面模板？")){
					jQuery("#pipeVersion").val('${version}');
					get("companyForm").submit();
				}
			}
	 	}
	}
</script>