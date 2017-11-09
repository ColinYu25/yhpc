<#include "statistic_head.ftl">

	<div class="box-con">
		<div class="box-title2">
			<div class="left"></div>
			<div class="center">
			<div class="fn2">
					<div class="left">
						<input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" onclick="this.myprop='year'" class="Wdate" size="8" maxlength="6">
						<b><#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>各地月报情况</b>
					</div>
					<div class="right">
						<ul class="fnlist">
							<li><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeUrl(0);">全 年</a></li>
							<li><a id="one"<#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeUrl(1);">第一季度</a></li>
							<li><a id="two"<#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeUrl(2);">第二季度</a></li>
							<li><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeUrl(3);">第三季度</a></li>
							<li><a id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeUrl(4);">第四季度</a></li>
						</ul>
						</div>
					</div>
					<div class="yf">
						<table border="0" cellspacing="0" cellpadding="0" >
					  		<tr>
							 <#assign beginMonth = "1"/>
				              <#assign endMonth = "12"/> 
				              <#if statistic.quarter == 0>
					              <#assign beginMonth = 1/>
				    	          <#assign endMonth = 12/>               
				              <#else>
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
	   <div id="mess_box" style="width:986px;height:420px;OVERFLOW-X:auto; overflow-y:hidden;OVERFLOW:scroll; border:0px;">
		<table width="986" height="245" border="0" cellspacing="0" cellpadding="0" class="table01">
		  <tr>
			<td class="tbbg"><b>${area.areaName}各地</b>（<#if statistic.month?? && statistic.month != 0>${statistic.month}月份<#else><#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if></#if>）
			<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
			&nbsp;&nbsp;
			<#if refresh==1 >
					<a href="loadPaiChaOfCompany.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
			</#if>			
 
			</td>
		  </tr>
		  <tr>
			<td valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="table02">
				  		<td colspan="2"><strong>区域</strong></td>
				  		<#list areas?if_exists as a>
							<td width="${85/(areas?size+1)}%"  title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
		    			</#list>
		    			<td width="${85/(areas?size+1)}%" ><strong>合计</strong></td>
	    			</tr>
				  <#list 0..6 as i>
					  <tr class="table03" <#if i_index==5>bgcolor="#F5F5F5"<#else>bgcolor="#FFFFFF"</#if> >
					  <#if i_index==0><td rowspan="5" width="4%"><strong>应<br>报<br>基<br>数</strong></td><td width="8%" nowrap="true"><strong>高危企业</strong></th><#assign ptype='w'>
					  <#elseif i_index==1><td nowrap="true"><strong>规模以上企业</strong></td><#assign ptype='g'>
					  <#elseif i_index==2><td nowrap="true"><strong>人员密集场所</strong></td><#assign ptype='r'>
					  <#elseif i_index==3><td nowrap="true" ><strong>其　　他</strong></td><#assign ptype='t'>
					  <#elseif i_index==4><td nowrap="true" ><strong>合　　计</strong></td><#assign ptype='h'>
					  <#elseif i_index==5><td nowrap="true" colspan="2"><strong>${menuName}未录入单位数</strong></td><#assign ptype='c'>
					  <#elseif i_index==6><td nowrap="true" colspan="2"><strong>${menuName2}录入率</strong></td>
					  </#if>
					  <#assign rowTotal=0>
					  	<#list areas?if_exists as a>
					    <td <#if i_index gt 6>style="display:none"</#if> <#if i_index!=6 && i_index!=8>onClick="showDetail(${a.areaCode},'${ptype}');" style="cursor:pointer;"</#if>>
					    <#list statistics?if_exists as s><#if a.areaCode==s.areaCode>
					    <#if i_index==0>${s.anum}<#assign rowTotal=rowTotal+s.anum>
					    <#elseif i_index==1>${s.cnum-s.dnum}<#assign rowTotal=rowTotal+s.cnum-s.dnum>
					    <#elseif i_index==2>${s.bnum}<#assign rowTotal=rowTotal+s.bnum>
					    <#elseif i_index==3>${s.bbnum-s.anum-s.bnum-s.cnum+s.dnum}<#assign rowTotal=rowTotal+s.bbnum-s.anum-s.bnum-s.cnum+s.dnum>
					    <#elseif i_index==4>${s.bbnum}<#assign rowTotal=rowTotal+s.bbnum>
					    <#elseif i_index==5>${s.bbnum-s.ccnum}<#assign rowTotal=rowTotal+s.bbnum-s.ccnum>
					    <#elseif i_index==6><#if s.bbnum==0>/<#else>#{((s.ccnum)/s.bbnum*100);M1}%</#if>
					    </#if></#if></#list></td>
					    </#list>
					    <td <#if i_index gt 6>style="display:none"</#if> <#if i_index!=6 && i_index!=8>onClick="showDetail(${area.areaCode},'${ptype}');" style="cursor:pointer;"</#if>>
					    <#if i_index==0>${rowTotal}<#assign anum=rowTotal>
					    <#elseif i_index==1>${rowTotal}<#assign cnum=rowTotal>
					    <#elseif i_index==2>${rowTotal}<#assign bnum=rowTotal>
					    <#elseif i_index==3>${rowTotal}<#assign fnum=rowTotal>
					    <#elseif i_index==4>${rowTotal}<#assign dnum=rowTotal>
					    <#elseif i_index==5>${rowTotal}<#assign enum=rowTotal>
					    <#elseif i_index==6><#if dnum==0>/<#else>#{((dnum-enum)/dnum*100);M1}%</#if>
					    </#if></td>
					  </tr>
				  </#list>
				</table>
			</td>
		  </tr>
		</table>
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
					window.location = "loadPaiChaOfCompany.xhtml?secondStatistic=2&area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
				}
			}
			function showDetail(areaCode,companyTrade) {
				window.location = "loadPaiChaOfCompanyList.xhtml?secondStatistic=${secondStatistic}&company.year=${statistic.year}&company.month=${statistic.month}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.companyTrade="+companyTrade;
			}
			function changeUrl() {
				window.location = "loadPaiChaOfCompany.xhtml?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
			}
	
			/**
			*根据年份查询统计表
			*/
			function showMonthData(month) {
				window.location = "loadPaiChaOfCompany.xhtml?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}&statistic.month=" + month;
			}		
		</script>
		</div>
	</div>
<#include "statistic_foot.ftl">