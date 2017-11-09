<#include "statistic_trouble_head.ftl">
<#if statistic?? && statistic.type?default(99)!=99>
	<#if statistic.type==1> 
		<#assign typeName='发改委'>
	<#elseif statistic.type==2>
		<#assign typeName='城管'>
	<#elseif statistic.type==3>
		<#assign typeName='安监'>
	<#elseif statistic.type==4>
		<#assign typeName='交通'>
	</#if>
</#if>
<#assign path=0>
<#list area.gradePath?split("/") as s>
    <#assign path=path+1>
</#list>
	<div class="box-con">
		<div class="<#if statistic.type?exists && statistic.type?default(99)!=99>box-title7<#else>box-title5</#if>">
			<div class="left"></div>
			<div class="center">
				<#if statistic.type?exists && statistic.type?default(99)!=99>
				<div class="mlist">
						 <#include "statistic_ind_trouble_head.ftl">
					</div> 
					<div class="fn2">
						<div class="left" style="width:700px;">
							<b><#if statistic?? && statistic.type?default(99)!=99>${typeName}</#if>隐患整治：</b><input type="text" id="year" value="${statistic.year}年" onChange="changeQUrl(0);" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
							<b>&nbsp;从&nbsp;</b>
							<select id="beg_month" onChange="changeQUrl(0);">
								<option value="1" <#if statistic.beg_month==1>selected</#if>>1月份</option>
								<option value="2" <#if statistic.beg_month==2>selected</#if>>2月份</option>
								<option value="3" <#if statistic.beg_month==3>selected</#if>>3月份</option>
								<option value="4" <#if statistic.beg_month==4>selected</#if>>4月份</option>
								<option value="5" <#if statistic.beg_month==5>selected</#if>>5月份</option>
								<option value="6" <#if statistic.beg_month==6>selected</#if>>6月份</option>
								<option value="7" <#if statistic.beg_month==7>selected</#if>>7月份</option>
								<option value="8" <#if statistic.beg_month==8>selected</#if>>8月份</option>
								<option value="9" <#if statistic.beg_month==9>selected</#if>>9月份</option>
								<option value="10" <#if statistic.beg_month==10>selected</#if>>10月份</option>
								<option value="11" <#if statistic.beg_month==11>selected</#if>>11月份</option>
								<option value="12" <#if statistic.beg_month==12>selected</#if>>12月份</option>
							</select>
							<b>&nbsp;到&nbsp;</b>
							<select id="end_month" onChange="changeQUrl(0);">
								<option value="1" <#if statistic.end_month==1>selected</#if>>1月份</option>
								<option value="2" <#if statistic.end_month==2>selected</#if>>2月份</option>
								<option value="3" <#if statistic.end_month==3>selected</#if>>3月份</option>
								<option value="4" <#if statistic.end_month==4>selected</#if>>4月份</option>
								<option value="5" <#if statistic.end_month==5>selected</#if>>5月份</option>
								<option value="6" <#if statistic.end_month==6>selected</#if>>6月份</option>
								<option value="7" <#if statistic.end_month==7>selected</#if>>7月份</option>
								<option value="8" <#if statistic.end_month==8>selected</#if>>8月份</option>
								<option value="9" <#if statistic.end_month==9>selected</#if>>9月份</option>
								<option value="10" <#if statistic.end_month==10>selected</#if>>10月份</option>
								<option value="11" <#if statistic.end_month==11>selected</#if>>11月份</option>
								<option value="12" <#if statistic.end_month==12>selected</#if>>12月份</option>
							</select>
						</div>
						<div class="right"></div>
					</div>	
					
				<#else>	
					<div class="fn2">
						<div class="left" style="width:700px;">
							<b><#if statistic?? && statistic.type?default(99)!=99>${typeName}</#if>隐患整治：</b><input type="text" id="year" value="${statistic.year}年" onChange="changeQUrl(0);" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
							<b>&nbsp;从&nbsp;</b>
							<select id="beg_month" onChange="changeQUrl(0);">
								<option value="1" <#if statistic.beg_month==1>selected</#if>>1月份</option>
								<option value="2" <#if statistic.beg_month==2>selected</#if>>2月份</option>
								<option value="3" <#if statistic.beg_month==3>selected</#if>>3月份</option>
								<option value="4" <#if statistic.beg_month==4>selected</#if>>4月份</option>
								<option value="5" <#if statistic.beg_month==5>selected</#if>>5月份</option>
								<option value="6" <#if statistic.beg_month==6>selected</#if>>6月份</option>
								<option value="7" <#if statistic.beg_month==7>selected</#if>>7月份</option>
								<option value="8" <#if statistic.beg_month==8>selected</#if>>8月份</option>
								<option value="9" <#if statistic.beg_month==9>selected</#if>>9月份</option>
								<option value="10" <#if statistic.beg_month==10>selected</#if>>10月份</option>
								<option value="11" <#if statistic.beg_month==11>selected</#if>>11月份</option>
								<option value="12" <#if statistic.beg_month==12>selected</#if>>12月份</option>
							</select>
							<b>&nbsp;到&nbsp;</b>
							<select id="end_month" onChange="changeQUrl(0);">
								<option value="1" <#if statistic.end_month==1>selected</#if>>1月份</option>
								<option value="2" <#if statistic.end_month==2>selected</#if>>2月份</option>
								<option value="3" <#if statistic.end_month==3>selected</#if>>3月份</option>
								<option value="4" <#if statistic.end_month==4>selected</#if>>4月份</option>
								<option value="5" <#if statistic.end_month==5>selected</#if>>5月份</option>
								<option value="6" <#if statistic.end_month==6>selected</#if>>6月份</option>
								<option value="7" <#if statistic.end_month==7>selected</#if>>7月份</option>
								<option value="8" <#if statistic.end_month==8>selected</#if>>8月份</option>
								<option value="9" <#if statistic.end_month==9>selected</#if>>9月份</option>
								<option value="10" <#if statistic.end_month==10>selected</#if>>10月份</option>
								<option value="11" <#if statistic.end_month==11>selected</#if>>11月份</option>
								<option value="12" <#if statistic.end_month==12>selected</#if>>12月份</option>
							</select>
							<!--第<#if statistic.quarter==1>一<#elseif statistic.quarter==2>二<#elseif statistic.quarter==3>三<#elseif statistic.quarter==4>四</#if>季度-->
						</div>
						<div class="right"></div>
					</div>	
				</#if>						
			</div>
			<div class="right"></div>
		</div>
		<div id="mess_box" style="width:986px;height:auto !important;height:680px;min-height:680px; OVERFLOW-X:auto; overflow-y:hidden;OVERFLOW:scroll; border:0px;">
		<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
		  <tr>
			<td class="tbbg"><b>${area.areaName}各地</b>（<#if statistic.beg_month==statistic.end_month>${statistic.beg_month}月份<#else>${statistic.beg_month}-${statistic.end_month}月份</#if>）</td>
		  </tr>
		  <tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="table02"><td width="15%" height="28" colspan="3">区域</td>
					  	<#list areas?if_exists as a>
							<td width="${75/(areas?size+1)}%" <#if path!=5>title="点击查看该区域统计"  style="cursor:hand;" onClick="showArea('${a.areaCode}')"</#if>><strong>${a.areaName}</strong></td>
						</#list>
						<td width="${75/(areas?size+1)}%"><strong>合计</strong></td></tr>
				  <#list 0..6 as i>
					  <tr class="table03" <#if i_index==0 || i_index==1 || i_index==2>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> align="center">
					  <#if i_index==0><td rowspan="3" colspan="2"><strong>一般隐患</strong></td><td width="6%" nowrap="true"><strong>隐 患 数</strong></td><#assign ptype='normal'><#assign isGorver=''>
					  <#elseif i_index==1><td width="6%" nowrap="true"><strong>未整改数</strong></td><#assign ptype='normal'><#assign isGorver='0'>
					  <#elseif i_index==2><td width="6%" nowrap="true"><strong>整改率(%)</strong></td>
					  <#elseif i_index==3><td rowspan="4" colspan="2"><strong>重<br>大<br>隐<br>患</strong></td><td width="6%" nowrap="true" ><strong>隐 患 数</strong></td><#assign ptype='danger'><#assign isGorver=''>
					  <#elseif i_index==4><td width="6%" nowrap="true"><strong>未整改数</strong></td><#assign ptype='danger'><#assign isGorver='0'>
					  <#elseif i_index==5><td width="6%" nowrap="true"><strong>到期未整改数</strong></td><#assign ptype='danger'><#assign isGorver='2'>
					  <#elseif i_index==6><td width="6%" nowrap="true"><strong>整改率(%)</strong></td></#if>
					  <#assign rowTotal=0>
					  	<#list areas?if_exists as a>
					    <td <#if i_index!=2 && i_index!=6><#if (areas?size-a_index)==1>onClick="showDetail(${a.areaCode},'${isGorver}','${ptype}','other');"<#else>onClick="showDetail(${a.areaCode},'${isGorver}','${ptype}','child');"</#if>  style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情" </#if>>
					    <#list statisticsForDanger?if_exists as s><#if a.areaCode==s.areaCode>
					    <#if i_index==0>${s.anum}<#assign rowTotal=rowTotal+s.anum>
					    <#elseif i_index==1>${s.anum-s.bnum}<#assign rowTotal=rowTotal+s.anum-s.bnum>
					    <#elseif i_index==2><#if s.anum==0>0/0<#else>#{(s.bnum/s.anum*100);M2}</#if>
					    <#elseif i_index==3>${s.aanum}<#assign rowTotal=rowTotal+s.aanum>
					    <#elseif i_index==4>${s.aanum-s.bbnum}<#assign rowTotal=rowTotal+s.aanum-s.bbnum>
					    <#elseif i_index==5>${s.ccnum}<#assign rowTotal=rowTotal+s.ccnum>
					    <#elseif i_index==6><#if s.aanum==0>0/0<#else>#{(s.bbnum/s.aanum*100);M2}</#if>
					    </#if></#if></#list></td>
					    </#list>
					    <td <#if i_index!=2 && i_index!=6>onClick="showDetail(${area.areaCode},'${isGorver}','${ptype}','all');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情" </#if>>
					    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
					    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
					    <#elseif i_index==2><#if anum==0>0/0<#else>#{((anum-bnum)/anum*100);M2}</#if>
					    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal>
					    <#elseif i_index==4>${rowTotal}<#assign bbnum=rowTotal>
					    <#elseif i_index==5>${rowTotal}
					    <#elseif i_index==6><#if aanum==0>0/0<#else>#{((aanum-bbnum)/aanum*100);M2}</#if></#if></td>
					  </tr>
				  </#list>
			      <#list statistics?if_exists as hs>
				  <#list 0..2 as i>
				  <tr class="table03" <#if hs_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> align="center">
				  <#if i_index==0>
				  	<#if hs_index==0>
				  		<td rowspan="${statistics?size/areas?size*3}" width="3%"><strong>挂</br></br>牌</br></br>隐</br></br>患</strong></td>
				  	</#if>
				  	<td rowspan="3" width="3%"><strong>${hs.enumName}</strong></td><td nowrap="nowrap" width="6%"><strong>挂 牌 数</strong></td><#assign isGorver=''>
				  	<#elseif i_index==1><td nowrap="nowrap" width="6%"><strong>未整改数</strong></td><#assign isGorver='0'>
				  	<#elseif i_index==2><td nowrap="nowrap" width="6%"><strong>整改率(%)</strong></td>
				  </#if>
				  	<#assign rowTotal=0>
				  	<#list areas?if_exists as a>
					    <td <#if i_index!=2>
					    <#if (areas?size-a_index)==1>onClick="showDetailRollcall(${a.areaCode},'${isGorver}','${hs.enumCode}','other');"<#else>onClick="showDetailRollcall(${a.areaCode},'${isGorver}','${hs.enumCode}','child');"</#if>
					     style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情" </#if>>
					    <#list statistics?if_exists as s>
					    	<#if hs.enumCode==s.enumCode>
					    		<#if a.areaCode==s.areaCode>
					    			<#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
					    			<#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
					    			<#elseif i_index==2><#if s.inhere==0>0/0<#else>#{(s.number/s.inhere*100);M2}</#if></#if>
					    		</#if>
					    	</#if>
					    </#list>
					    </td>
				    </#list>
				    <td <#if i_index!=2>onClick="showDetailRollcall(${area.areaCode},'${isGorver}','${hs.enumCode}','all');" style="cursor: pointer;" onMouseOver="this.style.backgroundColor=trMoveColor;" onMouseOut="this.style.backgroundColor='';" title="查看数据详情" </#if>>
				    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
				    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
				    <#elseif i_index==2><#if inhere==0>0/0<#else>#{((inhere-number)/inhere*100);M2}</#if></#if>
				    </td>
				  </tr>
				 </#list>
				 <#if hs_index==(statistics?size/areas?size-1)>
				 <#break>
				 </#if>
				 </#list>
				</table>
			</td>
		  </tr>
		</table>
		</div>
		</div>
	</div>
	<script type="text/javascript">
		var trMoveColor="#cae5ff";
		
		var typeParam = "";
		<#if statistic?? && statistic.type?default(99)!=99>
			typeParam = "statistic.type=${statistic.type}&";
		</#if>
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "loadDanger.xhtml?"+typeParam+"area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&&statistic.beg_month="+get("beg_month").value+"&statistic.end_month="+get("end_month").value;
			}
		}
		function showDetail(areaCode,isGorver,ptype,paramType) {
			if(ptype == 'danger'){
				window.location = "loadDangerTroubleByTypeList.xhtml?"+typeParam+"statistic.year=${statistic.year}&statistic.beg_month="+get("beg_month").value+"&statistic.paramType="+paramType+"&statistic.end_month="+get("end_month").value+"&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.isRollcall=0";
			}else{
				window.location = "loadNomalTroubleByTypeList.xhtml?"+typeParam+"statistic.year=${statistic.year}&statistic.beg_month="+get("beg_month").value+"&statistic.paramType="+paramType+"&statistic.end_month="+get("end_month").value+"&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver;
			}
		}
		
		//挂牌督办详情查询
		function showDetailRollcall(areaCode,isGorver,enumCode,paramType) {
			window.location = "loadDangerTroubleByTypeList.xhtml?"+typeParam+"statistic.year=${statistic.year}&statistic.beg_month="+get("beg_month").value+"&statistic.paramType="+paramType+"&statistic.end_month="+get("end_month").value+"&area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.enumCode="+enumCode+"&statistic.isRollcall=1";
		}
		
		function changeQUrl() {
			var beg_month = get("beg_month").value;
			var end_month = get("end_month").value;
			if(parseFloat(end_month)>=parseFloat(beg_month))
				window.location = "loadDanger.xhtml?"+typeParam+"area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.beg_month="+get("beg_month").value+"&statistic.end_month="+get("end_month").value;
			else
				alert("查询开始月不能大于结束月，请选择正确的时间段查询！");
		}
	</script>
	<#include "statistic_foot.ftl">