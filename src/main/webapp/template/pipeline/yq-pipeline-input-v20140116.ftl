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
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>宁波市油气管道基本情况排查表</th>
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
			
			<input type="hidden" name="entity.hasDj" value="${entity.hasDj?string('true','false')}" id="hasDj"/>
			<input type="hidden" name="entity.pipeVersion" value="${entity.pipeVersion}" id="pipeVersion"/>
			<input type="hidden" name="entity.notSamePathNum" value="${entity.notSamePathNum}" id="notSamePathNum"/>
			<input type="hidden" name="entity.hasCheck" value="${entity.hasCheck?string('true','false')}" id="hasCheck"/>
			<input type="hidden" name="entity.hasYjya" value="${entity.hasYjya?string('true','false')}" id="hasYjya"/>
			<input type="hidden" name="entity.cfssgPathNum" value="${entity.cfssgPathNum}" id="cfssgPathNum"/>
			<input type="hidden" name="entity.hasGlbhcs" value="${entity.hasGlbhcs?string('true','false')}" id="hasGlbhcs"/>
			<input type="hidden" name="entity.hasZfxt" value="${entity.hasZfxt?string('true','false')}" id="hasZfxt"/>
			<input type="hidden" name="entity.yczjcqNum" value="${entity.yczjcqNum}" id="yczjcqNum"/>
			<input type="hidden" name="entity.hasKzjcpg" value="${entity.hasKzjcpg?string('true','false')}" id="hasKzjcpg"/>
			<input type="hidden" name="entity.kzjcpgDate" value="${entity.kzjcpgDate}" id="kzjcpgDate"/>
			<input type="hidden" name="entity.safetyLevelNo" value="${entity.safetyLevelNo}" id="safetyLevelNo"/>
			<input type="hidden" name="entity.checkPercent" value="${entity.checkPercent}" id="checkPercent"/>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th rowspan="7" style="text-align:center;width:10%;"><p>管道</p>
			        <p>技术</p>
			        <p>情况</p>
		        </th>
		        <th style="width:10%">管道名称</th>
		        <td colspan="3" style="width:40%">
			        <input type="text" name="entity.name" value="${entity.name}" id="pipeLineName" maxlength="60" size="60"/>
			        <ui:v for="pipeLineName"  rule="require" empty="管道名称不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">是否在用</th>
		        <td style="width:40%">
		        <#assign isTrue=""/>
			    	<#assign isTrue="checked"/>
			        <#if entity?? && entity.toused?? && entity.toused == false >
				    	<#assign isTrue =""/>
				    	<#assign isFalse ="checked"/>    
			        </#if> 	
			        <label><input type="radio" name="entity.toused" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.toused" value="false" ${isFalse} /> 否 </label>       
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
		        <th rowspan="4" style="text-align:center;width:10%;">
		        	<p>规划</p>
		          	<p>选址</p>
		          	<p>及平</p>
		          	<p>面布</p>
		          	<p>置</p>
		        </th>
		        <th style="width:10%">建设和选址是否符合城乡规划</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.fhcsgh?? && entity.fhcsgh == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.fhcsgh" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.fhcsgh" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">★管道穿越人员密集场所情况</th>
		        <td style="width:20%">
		        	<input id="type1" type="text" readonly="readonly" size="2" value="${entity.rkmjPathNum}" name="entity.rkmjPathNum" 
		        	onclick="openAttech(1,'★管道穿越人员密集场所情况',350);">处
		        	<br>
		        	穿越 <input type="text" name="entity.rymjcycd" value="${entity.rymjcycd?string('####.###')}" id="rymjcycd" maxlength="10" size="2"/>Km
		        	<ui:v for="rymjcycd"  rule="require" empty="管径不允许为空" pass="&nbsp;" warn="&nbsp;"/>
		        </td>
		        <th style="width:10%">★与人员密集场所的安全距离不符合要求</th>
		        <td style="width:20%">
			        <input id="type5" type="text" readonly="readonly" size="2" value="${entity.rymjjlbfh}" name="entity.rymjjlbfh" 
		        	onclick="openAttech(5,'★与人员密集场所的安全距离不符合要求',350);">处
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">★与建（构）筑物、铁路、公路、航道、港口、市政公用地下管线等的安全保护距离不符合要求</th>
		        <td style="width:20%">
		        	<input id="type6" type="text" readonly="readonly" size="2" value="${entity.aqbhjlbfh}" name="entity.aqbhjlbfh" 
		        	onclick="openAttech(6,'★与建（构）筑物、铁路、公路、航道、港口、市政公用地下管线等的安全保护距离不符合要求',400);">处
				</td>
				<th style="width:10%">★与市政公用等其他管线交叉</th>
		        <td style="width:20%">
		        	<input id="type7" type="text" readonly="readonly" size="2" value="${entity.qtgxjc}" name="entity.qtgxjc" 
		        	onclick="openAttech(7,'★与市政公用等其他管线交叉',400);">处
		        </td>
		        <th style="width:10%">★油气管道穿越其他管线封闭空间</th>
		        <td style="width:20%">
		        	<input id="type8" type="text" readonly="readonly" size="2" value="${entity.gxfbkj}" name="entity.gxfbkj" 
		        	onclick="openAttech(8,'★油气管道穿越其他管线封闭空间',400);">处
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">新改扩是否进行“三同时”</th>
		        <td style="width:20%">
		         	 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.jxsts?? && entity.jxsts == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.jxsts" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.jxsts" value="false" ${isFalse} /> 否</label>    
				</td>
				<th style="width:10%">江、河、湖泊等敏感区域管道是否提高压力设计等级、增加防护套等</th>
		        <td style="width:20%">
		        	 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.tgfht?? && entity.tgfht == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.tgfht" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.tgfht" value="false" ${isFalse} /> 否</label>
		        </td>
		        <th style="width:10%">是否配备压力、流量等信息不间断采集和监测系统</th>
		        <td style="width:20%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.xxjcxt?? && entity.xxjcxt == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.xxjcxt" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.xxjcxt" value="false" ${isFalse} /> 否</label>
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">是否设置泄漏检测报警装置</th>
		        <td style="width:20%">
		         	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.xlbjzz?? && entity.xlbjzz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.xlbjzz" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.xlbjzz" value="false" ${isFalse} /> 否</label>
				</td>
				<th style="width:10%">是否设置紧急切断装置</th>
		        <td style="width:20%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.szqdzz?? && entity.szqdzz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.szqdzz" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.szqdzz" value="false" ${isFalse} /> 否</label>
		        </td>
		        <th style="width:10%">经过封闭空间是否设立泄漏报警</th>
		        <td style="width:20%">
		        	<#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.slxlbz?? && entity.slxlbz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.slxlbz" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.slxlbz" value="false" ${isFalse} /> 否</label>
		        </td>
		      </tr>
		    </table>
		    <br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="2" style="text-align:center;width:10%;"><p>管线</p>
		          <p>质量</p>
		        </th>
		        <th style="width:10%">是否检验检测合格</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.jyjchg?? && entity.jyjchg == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.jyjchg" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.jyjchg" value="false" ${isFalse}/> 否 </label>            
		        </td>
		        <th style="width:10%">★地面管线存在腐蚀、破损、开裂、变形、老化和跑、冒、滴、漏</th>
		        <td style="width:20%">
		        	<input id="type9" type="text" readonly="readonly" size="2" value="${entity.dmgxwt}" name="entity.dmgxwt" 
		        	onclick="openAttech(9,'★地面管线存在腐蚀、破损、开裂、变形、老化和跑、冒、滴、漏',700);">处
		        </td>
				<th style="width:10%">是否是故障下的安全保障设施和备用系统</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.gzbzxt?? && entity.gzbzxt == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.gzbzxt" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.gzbzxt" value="false" ${isFalse}/> 否 </label>            
			        &nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">有无难以发现隐患的隐蔽工程</th>
		        <td style="width:20%">
		        	 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.nfxyh?? && entity.nfxyh == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.nfxyh" value="true" ${isTrue}/> 有 </label>
			        <label><input type="radio" name="entity.nfxyh" value="false" ${isFalse}/> 无 </label>            
			        &nbsp;
		        </td>
		        <th style="width:10%">★管道被违法占压情况</th>
		        <td style="width:20%">
		        	<input id="type4" type="text" readonly="readonly" size="2" value="${entity.wzzyPathNum}" name="entity.wzzyPathNum" 
		        	onclick="openAttech(4,'★管道被违法占压情况',750);">处
		        </td>
		        <th style="width:10%">是否办理压力管道使用登记</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>
			        <#if entity?? && entity.ylgddj?? && entity.ylgddj == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
				    	<#assign display=""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.ylgddj" value="true" ${isTrue} onclick="$('ylgddjLicense').style.display=''"/> 是 </label>
			        <label><input type="radio" name="entity.ylgddj" value="false" ${isFalse} onclick="$('ylgddjLicense').style.display='none'"/> 否</label>  
			        <div style="display:${display}" id="ylgddjLicense">编号：<input type="text" name="entity.ylgddjLicense" value="${entity.ylgddjLicense}" id="ylgddjLicense" maxlength="50"/></div>
		        </td>
		      </tr>
		    </table>
		    <br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="2" style="text-align:center;width:10%;">
		      		<p>防雷</p>
			        <p>防静</p>
			        <p>电</p>
		        </th>
		        <th style="width:10%">是否按标准设计防雷、防静电设施</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.flfjdss?? && entity.flfjdss == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.flfjdss" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.flfjdss" value="false" ${isFalse}/> 否 </label>            
		        </td>
		        <th style="width:10%">防雷、防静电设施是否定期检查和检测</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.flfjdssjc?? && entity.flfjdssjc == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.flfjdssjc" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.flfjdssjc" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">是否采取有效的防腐绝缘与阴极保护措施</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.ffyjbhss?? && entity.ffyjbhss == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.ffyjbhss" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.ffyjbhss" value="false" ${isFalse}/> 否 </label>            
			        &nbsp;
		        </td>
		      </tr>
		      <tr>
		        <th colspan="3" style="width:40%">输油泵、加热炉、压缩机组等输油气生产设备、设施是否设置自动保护装置</th>
		        <td style="width:20%">
		        	 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.zdbhzz?? && entity.zdbhzz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.zdbhzz" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.zdbhzz" value="false" ${isFalse}/> 否 </label>            
			        &nbsp;
		        </td>
		        <th style="width:10%">自动保护装置是否定期检测</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.zdbhzzjc?? && entity.zdbhzzjc == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.zdbhzzjc" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.zdbhzzjc" value="false" ${isFalse}/> 否</label>  
		        </td>
		      </tr>
		    </table>
		    <br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="2" style="text-align:center;width:10%;">
		      		<p>日常</p>
			        <p>维护</p>
		        </th>
		        <th style="width:10%">是否建立巡护制度</th>
		        <td style="width:20%">
		        <#assign isTrue=""/>
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			    	<#assign display="none"/>
			        <#if entity?? && entity.xhzd?? && entity.xhzd == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>
				    	<#assign display=""/>
			        </#if> 	
			        <label><input type="radio" name="entity.xhzd" value="true" ${isTrue} onclick="$('xhzdRate').style.display=''"/> 是 </label>
			        <label><input type="radio" name="entity.xhzd" value="false" ${isFalse} onclick="$('xhzdRate').style.display='none'"/> 否 </label>
			        <div style="display:${display}" id="xhzdRate">频率:<input type="text" name="entity.xhzdRate" value="${entity.xhzdRate}" id="ylgddjLicense" maxlength="3" size="2"/>次/天</div>
		        </td>
		        <th style="width:10%">是否设置明显标志标示</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasSign?? && entity.hasSign == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.hasSign" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasSign" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">抢修队伍</th>
		        <td style="width:20%">
			        <label><input type="checkbox" name="entity.qxdw" value="zb" <#if entity.qxdw?exists && entity.qxdw?contains('zb')>checked</#if>/> 自备 </label>
			        <label><input type="checkbox" name="entity.qxdw" value="wt" <#if entity.qxdw?exists && entity.qxdw?contains('wt')>checked</#if>/> 委托 </label>    
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">是否制定危险性作业审批制度并严格执行</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.spzd?? && entity.spzd == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.spzd" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.spzd" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">转产、停产、停用管道是否妥善处置</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.gdtscl?? && entity.gdtscl == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.gdtscl" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.gdtscl" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">发现第三方危害管道安全是否向政府部门报告</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.whbz?? && entity.whbz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.whbz" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.whbz" value="false" ${isFalse}/> 否 </label>      
		        </td>
		      </tr>
		    </table>	
		    <br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="2" style="text-align:center;width:10%;">
		      		<p>许可</p>
			        <p>监管</p>
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
		        <th style="width:10%">是否委托有资质单位进行设计、安装</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.wtsjaz?? && entity.wtsjaz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.wtsjaz" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.wtsjaz" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">是否取得安全生产许可证</th>
		        <td style="width:20%">
			         <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasScLicence?? && entity.hasScLicence == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if>
			        <label><input type="radio" name="entity.hasScLicence" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.hasScLicence" value="false" ${isFalse}/> 否 </label>    
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">安全预评价是否备案</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.aqybjba?? && entity.aqybjba == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.aqybjba" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.aqybjba" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">是否通过竣工验收</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.hasYs?? && entity.hasYs == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.hasYs" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.hasYs" value="false" ${isFalse} /> 否</label>    
		        </td>
				<th style="width:10%">是否开展安全风险评估</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.aqfxpg?? && entity.aqfxpg == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.aqfxpg" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.aqfxpg" value="false" ${isFalse}/> 否 </label>      
		        </td>
		      </tr>
		    </table>
		    <br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="2" style="text-align:center;width:10%;">
		      		<p>应急</p>
			        <p>管理</p>
		        </th>
		         <th style="width:10%">是否制定应急预案</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.zdyjya?? && entity.zdyjya == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.zdyjya" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.zdyjya" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">应急预案是否备案</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.yjyaba?? && entity.yjyaba == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.yjyaba" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.yjyaba" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">是否定期组织演练</th>
		        <td style="width:20%">
			         <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.dqzzyl?? && entity.dqzzyl == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if>
			        <label><input type="radio" name="entity.dqzzyl" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.dqzzyl" value="false" ${isFalse}/> 否 </label>    
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">是否建立应急救援队伍</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.yjjydw?? && entity.yjjydw == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.yjjydw" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.yjjydw" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">是否配备相关抢险设备、器材和设施</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.pbqx?? && entity.pbqx == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.pbqx" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.pbqx" value="false" ${isFalse} /> 否</label>    
		        </td>
				<th style="width:10%">&nbsp;</th>
		        <td style="width:20%">
		        &nbsp;
		        </td>
		      </tr>
		    </table>
		    <br/>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		      	<th rowspan="3" style="text-align:center;width:10%;">
		      		<p>基础</p>
			        <p>管理</p>
		        </th>
		         <th style="width:10%">是否建立并落实安全生产责任制</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.aqsczrz?? && entity.aqsczrz == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.aqsczrz" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.aqsczrz" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">是否定期开展隐患排查治理</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.yhpczl?? && entity.yhpczl == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.yhpczl" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.yhpczl" value="false" ${isFalse}/> 否 </label>      
		        </td>
				<th style="width:10%">是否将隐患排查治理信息录入信息系统</th>
		        <td style="width:20%">
			         <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.xxllxt?? && entity.xxllxt == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if>
			        <label><input type="radio" name="entity.xxllxt" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.xxllxt" value="false" ${isFalse}/> 否 </label>    
		        </td>
		      </tr>
		      <tr>
		        <th style="width:10%">是否对相关人员进行培训</th>
		        <td style="width:20%">
			        <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.rypx?? && entity.rypx == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>        
			        </#if> 	
			        <label><input type="radio" name="entity.rypx" value="true" ${isTrue}/> 是 </label>
			        <label><input type="radio" name="entity.rypx" value="false" ${isFalse}/> 否 </label>
		        </td>
		        <th style="width:10%">是否建立管道档案</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.gdda?? && entity.gdda == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.gdda" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.gdda" value="false" ${isFalse} /> 否</label>    
		        </td>
				<th style="width:10%">是否进入规划部门管道数据库</th>
		        <td style="width:20%">
					 <#assign isTrue=""/>
			    	<#assign isFalse="checked"/>
			        <#if entity?? && entity.gdsjk?? && entity.gdsjk == true >
				    	<#assign isTrue ="checked"/>
				    	<#assign isFalse =""/>    
			        </#if> 	
			        <label><input type="radio" name="entity.gdsjk" value="true" ${isTrue} /> 是 </label>
			        <label><input type="radio" name="entity.gdsjk" value="false" ${isFalse} /> 否</label>    
		        </td>
		      </tr>
		      <tr>
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
		        <th style="width:10%">★管道安全防护区内建设施工情况</th>
		        <td style="width:20%">
		        	<input id="type2" type="text" readonly="readonly" size="2" value="${entity.aqfhPathNum}" name="entity.aqfhPathNum" 
		        	onclick="openAttech(2,'★管道安全防护区内建设施工情况',1300);">处
		        </td>
				<th style="width:10%">★其它存在的隐患</th>
		        <td style="width:20%">
		        	<input id="qtyhPathNum" type="text" readonly="readonly" size="2" value="${entity.qtyhPathNum}" name="entity.qtyhPathNum" 
		        	onclick="openTrouble('★其它存在的隐患',1300);">处
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
	
function openAttech(type,title,top){
	var pipeLineId = jQuery("#pipeLineId").val();
	if(pipeLineId<0){
		var f = jQuery("#companyForm");
		jQuery.post(
			"yq_pipeline_ajaxSave.xhtml",
			f.serialize(),
			function(result,msg){
				if(msg && msg == 'success' && result && result > 0){
					jQuery("#pipeLineId").val(result);
					loadWindowsOfNoClose('attech_list.xhtml?entity.type='+type+'&entity.daPipelineInfo.id='+result,750,400,150,top,'companies',title,true);
				}
			},"json");
	}else{
		loadWindowsOfNoClose('attech_list.xhtml?entity.type='+type+'&entity.daPipelineInfo.id='+pipeLineId,750,400,150,top,'companies',title,true);
	}
}
function setAttechValue(type,value){
	jQuery("#type"+type).val(value);
}
function openTrouble(title,top){
	var pipeLineId = jQuery("#pipeLineId").val();
	if(pipeLineId<0){
		var f = jQuery("#companyForm");
		jQuery.post(
			"yq_pipeline_ajaxSave.xhtml",
			f.serialize(),
			function(result,msg){
				if(msg && msg == 'success' && result && result > 0){
					jQuery("#pipeLineId").val(result);
					loadWindowsOfNoClose('attech_list.xhtml?entity.type='+type+'&entity.daPipelineInfo.id='+result,750,400,150,top,'companies',title,true);
				}
			},"json");
	}else{
		loadWindowsOfNoClose('pipeTrouble_list.xhtml?entity.daPipelineInfo.id='+pipeLineId,750,400,150,top,'companies',title,true);
	}
}
function setTroubleValue(value){
	jQuery("#qtyhPathNum").val(value);
}
</script>
