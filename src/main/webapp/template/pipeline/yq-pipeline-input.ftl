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
		<li><a href="javascript:go('cyrymjcs_list.xhtml?entity.type=1', '${entity.id}');" style="width:140px;">穿越人员密集场所情况</a></li>				
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
			<th>油气管道基本情况调查表</th>
		  </tr>
		</table>
		<div class="menu">
		  	<ul>
			<li id="img_save"><a class="b1" href="javaScript:submitCreate();"><b>保存</b></a></li>
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
			<#if version?exists && entity.pipeVersion?exists && version != entity.pipeVersion><li id="img_logout"><a href="javascript:update2new();" class="b10"><b>更新</b></a></li></#if>
			</ul>
		</div> 
		<@fkMacros.formValidator 'companyForm' />
		<form id="companyForm" name="companyForm" method="post" action="yq_pipeline_save.xhtml">
			<input type="hidden" name="entity.id" value="${entity.id}" id="pipeLineId"/>
			<input type="hidden" name="entity.daPipelineCompanyinfo.id" value="${entity.daPipelineCompanyinfo.id}" id="companyId" />
			<input type="hidden" name="entity.pipeVersion" value="${entity.pipeVersion}" id="pipeVersion"/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th rowspan="7" style="text-align:center;width:10%;"><p>管道</p>
			        <p>技术</p>
			        <p>情况</p>
		        </th>
		        <th style="width:10%">管道名称</th>
		        <td colspan="5" style="width:90%">
			        <input type="text" name="entity.name" value="${entity.name}" id="pipeLineName" maxlength="60" size="60"/>
			        <ui:v for="pipeLineName"  rule="require" empty="管道名称不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">管道种类</th>
		        <td colspan="3" style="width:40%">
			        <label><input type="radio" name="entity.type" value="1" <#if entity?? && entity.type?? && entity.type == 1> checked </#if>>石油天然气管道</label>
			        <label><input type="radio" name="entity.type" value="2" <#if entity?? && entity.type?? && entity.type == 2> checked </#if>>城镇燃气管道</label>
			        <br/>
			        <label><input type="radio" name="entity.type" value="3" <#if entity?? && entity.type?? && entity.type == 3> checked </#if>>危险化学品管道</label>
			        <label><input type="radio" name="entity.type" value="4" <#if entity?? && entity.type?? && entity.type == 4> checked </#if>>港区内油气管道</label>
			        &nbsp;
		        </td>
		        <th style="width:10%">管道铺设方式</th>
		        <td style="width:40%">
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
			        <label><input type="radio" name="entity.buildType" value="2" <#if entity?? && entity.buildType?? && entity.buildType == 2> checked </#if> onclick="$('mdNumSpan').style.display=''">★埋地</label>
			        <span style="display:${display}" id="mdNumSpan">深度：<input type="text" name="entity.mdNumSpan" value="${entity.mdNumSpan?string('####.###')}" id="mdNumSpan" maxlength="10" size="2"/>m</span>
			        &nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">管道起点</th>
		        <td style="width:20%">
			        <input type="text" name="entity.startPoint" value="${entity.startPoint}" id="startPoint" maxlength="50"/>
			        <ui:v for="startPoint"  rule="require" empty="管道起点不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">管道止点</th>
		        <td style="width:20%">
			        <input type="text" name="entity.endPoint" value="${entity.endPoint}" id="endPoint" maxlength="50"/>
			        <ui:v for="endPoint"  rule="require" empty="管道止点不允许为空" pass="&nbsp;" warn="&nbsp;"/>        
		        </td>
		        <th style="width:10%">管道长度</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.length" value="${entity.length?string('####.###')}" id="lineLength" maxlength="10" size="2"/>Km<ui:v for="lineLength"  rule="require" empty="管道止点不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">设计压力</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.pressure" value="${entity.pressure?string('####.###')}" id="pressure" maxlength="10" size="2"/>Mpa<ui:v for="pressure"  rule="double" empty="管道止点不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">最高工作压力</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.maxPressure" value="${entity.maxPressure?string('####.###')}" id="maxPressure" maxlength="10" size="2"/>Mpa
		        	<ui:v for="maxPressure"  rule="double" empty="最高工作压力不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">传输介质</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.medium" value="${entity.medium}" id="medium" maxlength="50" />
		        	<ui:v for="medium"  rule="require" empty="传输介质不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">管径</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.diameter" value="${entity.diameter?string('####.###')}" id="diameter" maxlength="10" size="2"/>mm
		        	<ui:v for="diameter"  rule="require" empty="管径不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">壁厚</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.deep" value="${entity.deep?string('####.###')}" id="deep" maxlength="10" size="2"/>mm<ui:v for="deep"  rule="require" empty="壁厚不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">管道材质</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.material" value="${entity.material}" id="material" maxlength="50"/><ui:v for="material"  rule="require" empty="管道材质不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">气态输送能力</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.gasTranAbility" value="${entity.gasTranAbility?string('####.###')}" id="gasTranAbility" maxlength="10" size="2"/>万m³/年
		        	<ui:v for="gasTranAbility"  rule="require" empty="气态输送能力不允许为空" pass="&nbsp;" warn="&nbsp;"/>
			    </td>
		        <th style="width:10%">液态输送能力</th>
		        <td colspan="3" style="width:50%">
		        	<input type="text" name="entity.liquidTranAbility" value="${entity.liquidTranAbility?string('####.###')}" id="liquidTranAbility" maxlength="10" size="2"/>万吨/年
		        	<ui:v for="liquidTranAbility"  rule="require" empty="液态输送能力不允许为空" pass="&nbsp;" warn="&nbsp;"/>
			    </td>                
		      </tr>
		      <tr>
		        <th style="width:10%">安装竣工时间</th>
		        <td style="width:20%">
			    	<input type="text" id="buildEndDate" name="entity.buildEndDate" value="${entity.buildEndDate?string('yyyy-MM-dd')}" maxlength="10" size="17" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
		        <th style="width:10%">投用时间</th>
		        <td colspan="3" style="width:50%">
			    	<input type="text" id="beginUseDate" name="entity.beginUseDate" value="${entity.beginUseDate?string('yyyy-MM-dd')}" maxlength="10" size="17" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			    </td>                
		      </tr>
		    </table>
		    <br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th rowspan="2" style="text-align:center;width:10%;"><p>建设</p>
		          <p>管理</p>
		          <p>情况</p>
		        </th>
		        <th style="width:10%">是否办理规划许可</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>    	
			        <#if entity?? && entity.hasGhLicence?? && entity.hasGhLicence == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
				    	<#assign display=""/>    		    	
			        </#if> 	
			        <label><input type="radio" name="entity.hasGhLicence" value="true" ${isTrue} onclick="$('ghLicenceSpan').style.display=''"/> 是 </label>
			        <label><input type="radio" name="entity.hasGhLicence" value="false" ${isFalse} onclick="$('ghLicenceSpan').style.display='none'"/> 否 </label>
			        <div style="display:${display}" id="ghLicenceSpan">许可证编号：<input type="text" name="entity.ghLicence" value="${entity.ghLicence}" id="ghLicence" maxlength="50"/></div>
		        </td>
		        <th style="width:10%">安全生产许可证</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasScLicence?? && entity.hasScLicence == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasScLicence" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.hasScLicence" value="false" ${isFalse}/> 无 </label>
		        	&nbsp;
		        </td>
		        <th style="width:10%">是否通过竣工验收</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasYs?? && entity.hasYs == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.hasYs" value="true" ${isTrue} /> 有 </label>
			        <label><input type="radio" name="entity.hasYs" value="false" ${isFalse} /> 无 </label>        
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">是否办妥压力管道使用登记</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>    
			        <#if entity?? && entity.hasDj?? && entity.hasDj == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>  
				    	<#assign display=""/>            
			        </#if> 	
			        <label><input type="radio" name="entity.hasDj" value="true" ${isTrue} onclick="$('djNumSpan').style.display=''"/> 有 </label>
			        <label><input type="radio" name="entity.hasDj" value="false" ${isFalse} onclick="$('djNumSpan').style.display='none'"/> 无 </label>         
					<div style="display:${display}" id="djNumSpan">登记编号：<input type="text" name="entity.djNum" value="${entity.djNum}" id="djNum" maxlength="50"/></div>
				</td>
				<th style="width:10%">实际路径是否与竣工图纸一致</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasSamePath?? && entity.hasSamePath == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasSamePath" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasSamePath" value="false" ${isFalse}/> 否 </label>          
		        </td>
		        <th style="width:10%">★实际路径与竣工图纸不一致</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.notSamePathNum" value="${entity.notSamePathNum}" id="notSamePathNum" maxlength="10" size="2"/>处<ui:v for="notSamePathNum" rule="integer" empty="不一致处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		      </tr>
		    </table>
		    <br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="7" style="text-align:center;width:10%;"><p>使用</p>
		          <p>管理</p>
		          <p>和安</p>
		          <p>全现</p>
		          <p>状</p>
		        </th>
		        <th style="width:10%">管道是否定期检验检测</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasCheck?? && entity.hasCheck == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasCheck" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasCheck" value="false" ${isFalse}/> 否 </label>            
		        </td>
		        <th style="width:10%">★管道穿越人员密集场所情况</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.rkmjPathNum" value="${entity.rkmjPathNum}" id="rkmjPathNum" maxlength="10" size="2"/>处<ui:v for="rkmjPathNum"  rule="integer" empty="人员密集处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
				<th style="width:10%">是否制定应急预案</th>
		        <td style="width:20%">
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
		        <th style="width:10%">★管道安全防护区建设施工情况</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.aqfhPathNum" value="${entity.aqfhPathNum}" id="aqfhPathNum" maxlength="10" size="2"/>处<ui:v for="aqfhPathNum"  rule="integer" empty="防护区施工处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">★管道穿曾发生安全事故情况</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.cfssgPathNum" value="${entity.cfssgPathNum}" id="cfssgPathNum" maxlength="10" size="2"/>处<ui:v for="cfssgPathNum"  rule="integer" empty="曾发生事故处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">有无隔离保护措施</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasGlbhcs?? && entity.hasGlbhcs == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasGlbhcs" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.hasGlbhcs" value="false" ${isFalse}/> 无 </label>  
		        	&nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">★管道被违章占压情况</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.wzzyPathNum" value="${entity.wzzyPathNum}" id="wzzyPathNum" maxlength="10" size="2"/>处<ui:v for="wzzyPathNum"  rule="integer" empty="被违章占压处不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">★其他主要隐患</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.qtyhPathNum" value="${entity.qtyhPathNum}" id="qtyhPathNum" maxlength="10" size="2"/>个<ui:v for="qtyhPathNum"  rule="integer" empty="主要隐患个不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">★整改是否需政府协调</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasZfxt?? && entity.hasZfxt == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasZfxt" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasZfxt" value="false" ${isFalse}/> 否 </label>  
		        	&nbsp;
		        </td>
		      </tr>
		      <tr>
				<th style="width:10%">是否有穿越城镇建成区管线</th>
		        <td style="width:20%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>
			        <#if entity?? && entity.hasCyczjcq?? && entity.hasCyczjcq == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>   
				    	<#assign display=""/>     
			        </#if> 	
			        <label><input type="radio" name="entity.hasCyczjcq" value="true" ${isTrue} onclick="$('yczjcqNum').style.display=''"/> 是 </label>
			        <label><input type="radio" name="entity.hasCyczjcq" value="false" ${isFalse} onclick="$('yczjcqNum').style.display='none'"/> 否 </label> 
			        <div style="display:${display}" id="yczjcqNum">穿越长度：<input type="text" name="entity.yczjcqNum" value="${entity.yczjcqNum}" id="yczjcqNum" maxlength="10" size="2"/>Km</div> 
		        	&nbsp;
		        </td>
		        <th style="width:10%">是否开展检测和安全风险评估</th>
		        <td style="width:20%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>
			        <#if entity?? && entity.hasKzjcpg?? && entity.hasKzjcpg == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>      
				    	<#assign display=""/>  
			        </#if> 	
			        <label><input type="radio" name="entity.hasKzjcpg" value="true" ${isTrue} onclick="$('kzjcpgDate').style.display=''"/> 是 </label>
			        <label><input type="radio" name="entity.hasKzjcpg" value="false" ${isFalse} onclick="$('kzjcpgDate').style.display='none'"/> 否 </label>  
		        	<div style="display:${display}" id="kzjcpgDate">评估时间：<input type="text" name="entity.kzjcpgDate" value="${entity.kzjcpgDate?string('yyyy-MM-dd')}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" maxlength="10" id="kzjcpgDate" size="7"/></div> 
		        	&nbsp;
		        </td>
		        <th style="width:10%">评估确定的安全风险等级</th>
		        <td style="width:20%">
		        	<input type="text" name="entity.safetyLevelNo" value="${entity.safetyLevelNo}" id="safetyLevelNo" maxlength="10" size="2"/>
		        	<#--ui:v for="safetyLevelNo"  rule="require" empty="风险等级不允许为空" pass="&nbsp;" warn="&nbsp;"/-->
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">有无标志标识</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasSign?? && entity.hasSign == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasSign" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.hasSign" value="false" ${isFalse}/> 无 </label>           
		        </td>
		        <th style="width:10%">巡查频率</th>
		        <td style="width:50%" colspan="3">
		        	<input type="text" name="entity.checkPercent" value="${entity.checkPercent}" id="checkPercent"  maxlength="20"/><ui:v for="checkPercent"  rule="require" empty="巡查频率不允许为空" pass="&nbsp;" warn="巡查频率只能输入数字&nbsp;"/>
		        </td>
		      </tr> 
		    </table>	
			<br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th style="width:20%" colspan="2">单位负责人</th>
		        <td style="width:20%"><input type="text" name="entity.fzr" value="${entity.fzr}" maxlength="15" id="fzr"/><ui:v for="fzr"  rule="require" empty="单位负责人不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		        <th style="width:10%">填表人</th>
		        <td style="width:20%"><input type="text" name="entity.tbr" value="${entity.tbr}" maxlength="10" id="tbr"/><ui:v for="tbr"  rule="require" empty="填表人不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		        <th style="width:10%">填表日期</th>
		        <td style="width:20%"><input type="text" name="entity.inputDate" value="${entity.inputDate?string('yyyy-MM-dd')}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" maxlength="10" id="inputDate"/><ui:v for="inputDate"  rule="require" empty="填表日期不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
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
		if(GetRadioValue("entity.type")==null){
			alert("管道类型不能为空！");
		}else{
			if(GetRadioValue("entity.buildType")==null){
				alert("管道铺设方式不能为空！");
			}else{
				get("companyForm").submit();
			}
		}
 	}
}

function GetRadioValue(RadioName){
    var obj;
    obj=document.getElementsByName(RadioName);
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                return obj[i].value;
            }
        }
    }
    return null;
} 

function go_Info(){
	var companyId = jQuery("#companyId").val();
	if (companyId != ""){
		window.location.href = "company_update.xhtml?entity.id=" + companyId;	
	}
}

function go_notSame(){
	var entityId = jQuery("#entityId").val();
	if (entityId != ""){
		window.location.href = "notSame_list.xhtml?entity.daPipelineInfo.id=" + entityId;	
	}
}

function go_troubles(){
	var entityId = jQuery("#entityId").val();
	if (entityId != ""){
		window.location.href = "trouble_list.xhtml?entity.daPipelineInfo.id=" + entityId;	
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
	window.location.href = "yq_pipeline_update.xhtml?entity.id=" + pipeId;
}

function go_Info(companyId, pipeId){
	window.location.href = "company_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;	
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
