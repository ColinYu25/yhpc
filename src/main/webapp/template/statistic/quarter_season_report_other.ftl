<#if statistic.isStType==1>
<#include "statistic_trouble_head.ftl">
<#elseif statistic.isStType==2 >
<#include "statistic_mass_head.ftl">
<#else>
<#include "quarter_head.ftl">
</#if>
<#if statistic.remark=='lishe'>
	<#assign typeName='栎社机场'>
<#elseif statistic.remark=='nongji'>
	<#assign typeName='农　机'>
<#elseif statistic.remark=='xiaofang'>
	<#assign typeName='消防安全'>
<#elseif statistic.remark=='zhijian'>
	<#assign typeName='质　监'>
<#elseif statistic.remark=='renfang'>
	<#assign typeName='人　防'>
</#if>
			<div class="box-con">
					<div class="box-title7">
						<div class="left"></div>
						<div class="center">
						<div class="mlist">
								<#if statistic.isStType==1>
								<#include "statistic_ind_trouble_head.ftl">
								<#elseif statistic.isStType==2 >
								<#include "statistic_ind_mass_head.ftl">
								<#else>
								<#include "quarter_industry_head.ftl">
								</#if>
							</div>	
						<div class="fn2">
								<div class="left">
									<input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
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
							            ${title}季报进度</b>
								</div>
								<div class="right">
									<ul class="fnlist">
										<li><a id="all"<#if statistic.quarter == 0>class="hover"</#if> href="#" onclick="changeQUrl(0);">全 年</a></li>
										<li><a id="one"<#if statistic.quarter == 1>class="hover"</#if> href="#" onclick="changeQUrl(1);">第一季度</a></li>
										<li><a id="two"<#if statistic.quarter == 2>class="hover"</#if> href="#" onclick="changeQUrl(2);">第二季度</a></li>
										<li><a id="three"<#if statistic.quarter == 3>class="hover"</#if> href="#" onclick="changeQUrl(3);">第三季度</a></li>
										<li><a id="four"<#if statistic.quarter == 4>class="hover"</#if> href="#" onclick="changeQUrl(4);">第四季度</a></li>
									</ul>
								</div>
							</div>			
						</div>
						<div class="right"></div>
						</div>
						<table width="986" border="0" cellspacing="0" cellpadding="0" class="table01">
						  <tr>
							<td class="tbbg"><b>${typeName}</b>
							<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if>	</strong>		
							&nbsp;&nbsp;
							<#if refresh==1 >
								<a href="loadQuarterOther.xhtml?<#if secondStatistic??>secondStatistic=${secondStatistic}&</#if><#if area?? &&area.areaCode??>area.areaCode=${area.areaCode}&</#if><#if statistic.year??>statistic.year=${statistic.year}&</#if><#if statistic.quarter??>statistic.quarter=${statistic.quarter}&</#if><#if statistic.month??>statistic.month=${statistic.month}&</#if><#if statistic.remark??>statistic.remark=${statistic.remark}&</#if><#if statistic.isSonType??>statistic.isSonType=${statistic.isSonType}&</#if>statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
							</#if>	
							</td>
						  </tr>
						  <tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							   <tr class="table02">
						          <td width="18%" rowspan="5" align="center" colspan="2" widtd="72">行业和领域</td>
						          <td height="10" widtd="72">&nbsp;</td>
						          <td colspan="3" widtd="216"><center>一般隐患</center></td>
						          <td colspan="10" widtd="720"><center>重大隐患</center></td>
						        </tr>
						        <tr class="table02">
						          <td width="4%" rowspan="3"><center>排查<br>单位数</center></td>
						          <td width="4%" rowspan="3"><center>隐患数</center></td>
						          <td width="4%" rowspan="3"><center>已经<br>整改数</center></td>
						          <td width="4%" rowspan="3"><center>整改率</center></td>
						          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
						          <td colspan="7"><center>列入治理计划的重大隐患</center></td>
						        </tr>
						        <tr class="table02">
						          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
						          <td width="4%" rowspan="2"><center>已整改<br>销号数</center></td>
						          <td width="4%" rowspan="2"><center>整改率</center></td>
						          <td width="4%" rowspan="2"><center>重大<br>隐患数</center></td>
						          <td height="20" colspan="5"><center>其中</center></td>
						          <td width="5%" rowspan="2"><center>累计落实<br>治理资金</center></td>
						        </tr>
						        <tr class="table02">
						          <td width="5%"><center>落实治理<br>目标任务</center></td>
						          <td width="5%"><center>落实治理<br>经费物资</center></td>
						          <td width="6%"><center>落实治理<br>机构人员</center></td>
						          <td width="5%"><center>落实治理<br>时间要求</center></td>
						          <td width="7%"><center>落实安全措<br>施应急预案</center></td>
						        </tr>        
						        <tr class="table02">
						          <td height="20"><center>(家)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>项)</center></td>
						          <td><center>(%)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(%)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(项)</center></td>
						          <td><center>(万元)</center></td>
						        </tr>
						       <#list statistics as s>
						      	<tr class="table03" <#if s_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#F5F5F5"</#if>>
						          <td colspan="2">&nbsp;<strong>${s.enumName}</strong></td>
						          <td>${s.companyNum}</td>
						          <td>${s.troubNum}</td>
						          <td>${s.troubCleanNum}</td>
						          <td><#if s.troubNum==0>/<#else>#{(s.troubCleanNum/s.troubNum*100);M1}%</#if></td>
						          <td>${s.bigTroubNum}</td>
						          <td>${s.bigTroubCleanNum}</td>
						          <td><#if s.bigTroubNum==0>/<#else>#{(s.bigTroubCleanNum/s.bigTroubNum*100);M1}%</#if></td>
						          <td>${s.bigTroubNum-s.bigTroubCleanNum}</td>
						          <td>${s.target}</td>
						          <td>${s.goods}</td>
						          <td>${s.resource}</td>
						          <td>${s.finishDate}</td>
						          <td>${s.safetyMethod}</td>
						          <td>${s.governMoney}</td>
						        </tr>
						        </#list>
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
           if(nowYear==changeYear1){
           alert(nowQuarter);
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


		
	function changeQUrl() {
		var url = "?statistic.remark=${statistic.remark}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		window.location = url;
	}	
</script>
<#include "statistic_foot.ftl">