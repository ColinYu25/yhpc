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
                                    <input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" onclick="this.myprop='year'"class="Wdate" size="8" maxlength="6">
		

									<b> <#assign title=""/>
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
							            ${title}<#if area.fatherId==3020  && s.enumName=="贸易局">商务局<#elseif area.fatherId==3020  && s.enumName=="贸易">商务<#else>${s.enumName}</#if>月报情况</b>
								</div>
								<div class="right">
									<ul class="fnlist">						
										<li ><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
										<li ><a id="one"<#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeQUrl(1);">第一季度</a></li>
										<li ><a id="two"<#if statistic.quarter == 2>class="hover" </#if> href="#" onclick="changeQUrl(2);">第二季度</a></li>
										<li ><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeQUrl(3);">第三季度</a></li>
										<li ><a id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeQUrl(4);">第四季度</a></li>				
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
						             	<td style="text-align:right;" class="shun"><a <#if statistic.month == item>class="hover"</#if> href="#" name="month" onclick="showMonthData(${item});">${item}月份</a></td><!--<#if item ==statistic.month>disabled</#if>-->
						              </#list>
								  	</tr>	
								 </table>
							</div>				
						</div>
						<div class="right"></div>
						</div>
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b><#if area.fatherId==3020  && s.enumName=="贸易局">商务局<#elseif area.fatherId==3020  && s.enumName=="贸易">商务<#else>${s.enumName}</#if></b>（${title}）
							<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
							&nbsp;&nbsp;
							<#if refresh==1 >
									<a href="loadCompanyByIndustry.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
							</#if>	
														
							</td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr class="table02">
							  <td width="15%" height="28" <#if statistic.remark=='anjian'>colspan="${s.sonNumber1+s.sonNumber2+0}"<#else>colspan="${s.sonNumber1+s.sonNumber2+2}"</#if>><#if s_index==0><#break></#if></#list><strong>区域</strong></td>
							  	<#list areas?if_exists as a>
									<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
					    		</#list>
					    		<td width="${85/(areas?size+1)}%"><strong>合计</strong></td></tr>
							   <#list statistics?if_exists as hy>
								  <#list 0..2 as i>
								  <tr class="table03" <#if hy_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if> >
								  	<#if i_index==0>
								  		<#if ((hy.sonNumber1+hy.sonNumber2)>0)>
								  		<#assign colParam=hy.sonNumber1+hy.sonNumber2>
								  		<#if statistic.remark == 'anjian'>
								  			<td rowspan="3" width="3%" colspan="1"><strong>
								  		<#else>
								  			<td rowspan="${hy.sonCount*3}" width="3%"><strong>
								  		</#if>
								  			<#list 0..hy.enumName?length-1 as hl><#if area.fatherId==3020  && hy.enumName?substring(hl_index,hl_index+1)=='贸'>商<#elseif area.fatherId==3020  && hy.enumName?substring(hl_index,hl_index+1)=='易'>务<#else>${hy.enumName?substring(hl_index,hl_index+1)}</#if><br></#list>
								  		</strong></td>
								  		<#if statistic.remark == 'anjian'>
								  		<#else>
								  			<td rowspan="3" colspan="${colParam}"><strong>合计</strong></td>
								  		</#if>
								  		
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
	

  
  document.getElementById('year').attachEvent('onpropertychange',function(o){   
      if(o.propertyName!='value'){
         //alert(o.propertyName);
         return;
         }  //不是value改变不执行下面的操作   
       else{
           var changeYear=document.getElementById('year').value
           var changeYear1=changeYear.split("年",1);
  
           var changeYear2=parseInt(changeYear1)
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
  });   


		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "loadCompanyByIndustry.xhtml?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		
		function showDetail(areaCode,industryId,companyTrade) {
			window.location = "loadCompanyByIndustryList.xhtml?company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.industryId="+industryId+"&company.companyTrade="+companyTrade;
		}
		function changeQUrl() {
			var url = "?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
			<#if statistic?? && statistic.remark?? &&statistic.remark=='anjian'>
				url = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
			</#if>
			window.location = url;
		}
		
		/**
		*根据年份查询统计表
		*/
		function showMonthData(month) {
			var url = "?statistic.isSonType=1&statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			<#if statistic?? && statistic.remark?? &&statistic.remark=='anjian'>
				url = "?statistic.remark=${statistic.remark}&area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			</#if>
			url += "&statistic.month=" + month;
			window.location = url;
		}		
	</script>
<#include "statistic_foot.ftl">
