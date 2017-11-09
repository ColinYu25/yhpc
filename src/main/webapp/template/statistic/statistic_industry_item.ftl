<#include "statistic_head.ftl">
				<div class="box-con">
					<div class="box-title">
						<div class="left"></div>
						<div class="center">
						   <#list statistics?if_exists as s>
	  	 					<#assign allParam=s.sonNumber1+s.sonNumber2+3>
							<div class="mlist">
								<#include "statistic_industry_head.ftl">
							</div>
							<div class="fn">
								<div class="left">
									<input type="text" id="year" onchange="year_onchange();" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
						    		<b><#assign title=""/>
						             <#if statistic.month gt 0>
						             <#assign title=statistic.month +"月份"/>
						             <#else>
							             <#if statistic.quarter==1>
								             <#assign title="第一季度"/>	             
							             <#elseif statistic.quarter==2>
							             	<#assign title="第二季度"/>	 
							             <#elseif statistic.quarter==3>
								             <#assign title="第三季度"/>
							             <#elseif statistic.quarter==4>
								             <#assign title="第四季度"/>             
							             <#else>
							                 <#assign title="全年"/>             
							             </#if>
						             </#if>
						            ${title}${s.enumName}月报情况<#assign name='${s.enumName}'></b>
								</div>
								<div class="right">
									<ul class="fnlist">
										<li><a <#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
										<li><a id="one" onclick="changeQUrl(1);" <#if statistic.quarter == 1>class="hover"</#if> href="#" >第一季度</a></li>
										<li><a id="two" onclick="changeQUrl(2);" <#if statistic.quarter == 2>class="hover"</#if> href="#" >第二季度</a></li>
										<li><a id="three" onclick="changeQUrl(3);" <#if statistic.quarter == 3>class="hover"</#if> href="#" >第三季度</a></li>
										<li><a id="four" onclick="changeQUrl(4);" <#if statistic.quarter == 4>class="hover"</#if> href="#" >第四季度</a></li>
									</ul>
								</div>
							</div>
							<div class="yf">
								<table  border="0" cellspacing="0" cellpadding="0" >
							  		<tr>
									 <#assign beginMonth = "1"/>
						              <#assign endMonth = "12"/> 
						              <#if statistic.quarter == 0>
							              <#assign beginMonth = 1/>
						    	          <#assign endMonth = 12/>               
						              <#else >
							              <#assign beginMonth = (statistic.quarter * 3) -2/>
						    	          <#assign endMonth = (statistic.quarter * 3)/>                             
						              </#if>
			 
						              <#list beginMonth..endMonth as item>
						             	<td style="text-align:right;" class="shun"><a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a>
						             	</td>
						              </#list>
								  	</tr>	
								 </table>
							</div>						
						</div>
						<div class="right"></div>
						</div>
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b>${s.enumName}</b>（${title}）
							<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
										&nbsp;&nbsp;
							<#if refresh==1 >
									<a href="loadCompanyAndItemByIndustry.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
							</#if>							
							</td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr class="table02">
							  <td colspan="${s.sonNumber1+s.sonNumber2+2}"><#if s_index==0><#break></#if></#list>区域</td>
							  	<#list areas?if_exists as a>
									<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
					    		</#list>
					    		<td width="${85/(areas?size+1)}%"><strong>合计</strong></td></tr>
							    <#list statistics?if_exists as hy>
								  <#list 0..2 as i>
								  <tr class="table03" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
								  	<#if i_index==0>
								  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
								  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
								  		<td rowspan="${hy.sonCount*3}" width="3%"><strong>
								  			<#list 0..hy.enumName?length-1 as hl>${hy.enumName?substring(hl_index,hl_index+1)}<br></#list>
								  		</strong></td>
								  		<td rowspan="3" colspan="${colParam}"><strong>合计</strong></td>
								  		<#else><td rowspan="3" <#if colParam?exists>colspan="${colParam}"<#else>colspan="1"</#if>><strong>${hy.enumName}</strong></td>
								  		</#if>
									  	<td nowrap="true" width="6%"><strong>应录入数</strong></td><#assign ptype='p'>
								  	<#elseif i_index==1>
								  		<td nowrap="true" width="6%"><strong>${menuName}未录入数</strong></td><#assign ptype='w'>
								  	<#elseif i_index==2>
								  		<td nowrap="true" width="6%"><strong>${menuName}录入率</strong></td>
								  	</#if>
								  	<#assign rowTotal=0>
								  	<#list areas?if_exists as a>
								    <td <#if i_index!=2>onClick="showDetail(${a.areaCode},'${hy.industryId}','${ptype}');"  style="cursor:pointer;"</#if>>
								    <#list statistics?if_exists as s>
								    <#if hy.industryId==s.industryId><#if a.areaCode==s.areaCode>
								    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
								    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
								    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if></#if></#if></#if></#list></td>
								    </#list>
								    <td <#if i_index!=2>onClick="showDetail(${area.areaCode},'${hy.industryId}','${ptype}');"  style="cursor:pointer;"</#if>>
								    <#if i_index==0>${rowTotal}<#assign inhere=rowTotal>
								    <#elseif i_index==1>${rowTotal}<#assign number=rowTotal>
								    <#elseif i_index==2><#if inhere==0>/<#else>#{((inhere-number)/inhere*100);M1}%</#if></#if></td>
								  </tr>
								 </#list>
								 <#if hy_index==(statistics?size/areas?size-1)>
								 <#break>
								 </#if>
								 </#list>
							  </tr>  
							</table>
								
							</td>
						  </tr>
						</table>
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b>${name}工程</b></td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							 <#if area.areaCode==330200000000&&statistic.remark!='shuili'>
							  <tr class="table02">
							  <td width="15%" height="28" colspan="2" rowspan="2">区域</td>
							  	<#list areasByGroupNum?if_exists as a>
							  		<#if (a_index>4)>
										<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')" rowspan="2"><strong>${a.areaName}</strong></td>
									<#else>
										<td width="${85/(areas?size+1)}%"  id="roleName"><strong>市　级　维　护</strong></td>
									</#if>
								</#list>
								<td width="${85/(areas?size+1)}%" rowspan="2" nowrap="true"><strong>合计</strong></td></tr>
							    <tr class="table02">
								<#list areasByGroupNum?if_exists as a>
								<#if (a_index<5)>
									
								    
								    <#if (a.areaCode==0)>
										   <td width="${85/(areas?size+1)}%"><strong>${a.areaName}</strong></td>
									<#else>
										   <td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
									</#if>
								
								</#if>
								</#list>
								</tr>
								<#else>
								<tr class="table02"><td colspan="2">区域</td>
							  	<#list areasByGroupNum?if_exists as a>
								    <#if (a.areaCode==0)>
										   <td width="${85/(areas?size+1)}%"><strong>${a.areaName}</strong></td>
									<#else>
										   <td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
									</#if>
								</#list>
								<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td></tr>
								</#if>
								<#list 0..3 as i>
									  <tr class="table03" bgcolor="#ffffff">
									  <#if i_index==0><td rowspan="4"><strong>排<br>查<br>进<br>度</strong></td><td width="6%" nowrap="true"><strong>项目基数</strong></td><#assign ptype='paicha'><#assign isGorver=''>
									  <#elseif i_index==1><td width="6%" nowrap="true"><strong>${menuName}未录入数</strong></td><#assign ptype='paicha'><#assign isGorver='0'>
									  <#elseif i_index==2><td width="6%" nowrap="true"><strong>${menuName}录入率</strong></td>
									  <#elseif i_index==3><td width="6%" nowrap="true"><strong>已竣工数</strong></td><#assign ptype='paicha'><#assign isGorver='2'></#if>
									  <#assign rowTotal=0>
									  	<#list areasByGroupNum?if_exists as a>
									    <td <#if i_index!=2>onClick="showDetailItem(${a.areaCode},'${isGorver}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#list statisticsForPaicha?if_exists as s><#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
									    <#elseif i_index==1>${s.inhere-s.number}<#assign rowTotal=rowTotal+s.inhere-s.number>
									    <#elseif i_index==2><#if s.inhere==0>/<#else>#{(s.number/s.inhere*100);M1}%</#if>
									    <#elseif i_index==3>${s.anum}<#assign rowTotal=rowTotal+s.anum>
									    </#if></#if></#list></td>
									    </#list>
									    <td <#if i_index!=2>onClick="showDetailItem(${area.areaCode},'${isGorver}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
									    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
									    <#elseif i_index==2><#if anum==0>/<#else>#{((anum-bnum)/anum*100);M1}%</#if>
									    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal></#if></td>
									  </tr>
								  </#list>
								<#list 0..5 as i>
									  <tr class="table03" <#if i_index==0 || i_index==1 || i_index==2>bgcolor="#F5F5F5"<#else>bgcolor="#ffffff"</#if> align="center">
									  <#if i_index==0><td rowspan="3"><strong>一般隐患</strong></td><td width="6%" nowrap="true"><strong>隐 患 数</strong></td>
									  <#elseif i_index==1><td width="6%" nowrap="true"><strong>未整改数</strong></td>
									  <#elseif i_index==2><td width="6%" nowrap="true"><strong>整 改 率</strong></td>
									  <#elseif i_index==3><td rowspan="3"><strong>重大隐患</strong></td><td width="6%" nowrap="true" ><strong>隐 患 数</strong></td><#assign ptype='danger'><#assign isGorver=''>
									  <#elseif i_index==4><td width="6%" nowrap="true"><strong>未整改数</strong></td><#assign ptype='danger'><#assign isGorver='0'>
									  <#elseif i_index==5><td width="6%" nowrap="true"><strong>整 改 率</strong></td></#if>
									  <#assign rowTotal=0>
									  	<#list areasByGroupNum?if_exists as a>
									    <td <#if i_index==3 || i_index==4>onClick="showDetailItem(${a.areaCode},'${isGorver}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#list statisticsForDanger?if_exists as s><#if a.areaCode==s.areaCode>
									    <#if i_index==0>${s.inhere}<#assign rowTotal=rowTotal+s.inhere>
									    <#elseif i_index==1>${s.number}<#assign rowTotal=rowTotal+s.number>
									    <#elseif i_index==2><#if s.inhere==0>/<#else>#{((s.inhere-s.number)/s.inhere*100);M1}%</#if>
									    <#elseif i_index==3>${s.anum}<#assign rowTotal=rowTotal+s.anum>
									    <#elseif i_index==4>${s.anum-s.bnum}<#assign rowTotal=rowTotal+s.anum-s.bnum>
									    <#elseif i_index==5><#if s.anum==0>/<#else>#{(s.bnum/s.anum*100);M1}%</#if>
									    </#if></#if></#list></td>
									    </#list>
									    <td <#if i_index==3 || i_index==4>onClick="showDetailItem(${area.areaCode},'${isGorver}','${ptype}');" style="cursor:pointer;"</#if>>
									    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
									    <#elseif i_index==1>${rowTotal}<#assign bnum=rowTotal>
									    <#elseif i_index==2><#if anum==0>/<#else>#{((anum-bnum)/anum*100);M1}%</#if>
									    <#elseif i_index==3>${rowTotal}<#assign aanum=rowTotal>
									    <#elseif i_index==4>${rowTotal}<#assign bbnum=rowTotal>
									    <#elseif i_index==5><#if aanum==0>/<#else>#{((aanum-bbnum)/aanum*100);M1}%</#if></#if></td>
									  </tr>
								  </#list>
								  </tr>	  
							</table>
								
							</td>
						  </tr>
						</table>
					
				</div>
			</div>
						
	<script type="text/javascript">
		var date = new Date();
	    var nowYear=${statistic.year};
	    var month=date.getMonth()+1;
	    var year=date.getYear();
	    var nowQuarter=${current_quarter};
	    
	    if (year==nowYear){
		    if(month<=3){
				get("two").disabled=true;
				get("three").disabled=true;
				get("four").disabled=true;
			}else if(month<=6){
			    get("three").disabled=true;
				get("four").disabled=true;
			}else if(month<=9){
			    get("four").disabled=true;
			}	    
		}
		 
    function year_onchange() {
       var changeYear=document.getElementById('year').value;
       var changeYear1=changeYear.split("年",1);
       
       if(year==changeYear1){
           if(nowQuarter==1){
				get("two").disabled=true;
				get("three").disabled=true;
				get("four").disabled=true;
			}else if(nowQuarter==2){
			    get("three").disabled=true;
				get("four").disabled=true;
			}else if(nowQuarter==3){
			    get("four").disabled=true;
			}	 
       }else{
            get("one").disabled=false;
            get("two").disabled=false;
			get("three").disabled=false;
			get("four").disabled=false;
       }
    }
    
	function roleName(){
		var roleName = document.getElementsByName("roleName");
		for(var i = 0 ; i<roleName.length-1;i ++){
			if (roleName[i].innerHTML == roleName[i+1].innerHTML ){
				var k = 0;
				for(var j = 0 ; j<roleName.length-1;j ++){
					if (roleName[j].innerHTML == roleName[j+1].innerHTML && roleName[j].innerHTML == roleName[i].innerHTML ){
						k = k+1;
					}
				}
				roleName[i].colSpan = parseInt(roleName[i].colSpan) + k;			
				roleName[i+1].style.display = "none";
			}
		}
	}
	<#if area.areaCode==330200000000>roleName();</#if>
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "loadCompanyAndItemByIndustry.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyByIndustryList.xhtml?company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		
		function showDetailItem(areaCode,isGorver,ptype) {
			if(ptype == 'danger'){
				window.location = "loadItemTroubleByIndustryList.xhtml?area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.remark=${statistic.remark}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}";
			}else{
				window.location = "loadItemByIndustryList.xhtml?area.areaCode="+areaCode+"&statistic.isGorver="+isGorver+"&statistic.remark=${statistic.remark}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=${statistic.month}";
			}
		}
		function changeQUrl() {
			window.location = "loadCompanyAndItemByIndustry.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
		
		function showMonthData(month) {
			window.location = "loadCompanyAndItemByIndustry.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
		}
	</script>
<#include "statistic_foot.ftl">