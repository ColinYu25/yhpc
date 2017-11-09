<#include "statistic_head.ftl">
	<div id="content">
	<table width="100%" cellspacing="1" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;; background-color:#C1DAD7">
           <tbody><tr>
            <th height="25" bgcolor="#D6EEF0" align="center" colspan="${areas?size+3}"><span class="head">            
             <input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <#if statistic.quarter==1>第一季度<#elseif statistic.quarter==2>第二季度<#elseif statistic.quarter==3>第三季度<#elseif statistic.quarter==4>第四季度<#else>全年</#if>上报进度
             　　<input name="firstQuarter" type="button"  value="全年"  onclick="changeUrl(0);"/>
               &nbsp; &nbsp;<input name="firstQuarter" type="button"  value="一季度"  onclick="changeUrl(1);"/>
               &nbsp; &nbsp;<input name="secondQuarter" type="button"  value="二季度"  onclick="changeUrl(2);"/>
               &nbsp; &nbsp;<input name="thirdQuarter" type="button"  value="三季度"  onclick="changeUrl(3);"/>
               &nbsp; &nbsp;<input name="fouthQuarter" type="button"  value="四季度"  onclick="changeUrl(4);"/></span></th>
            </tr>
		  <tr height="25" bgcolor="#dcfafa" align="center"><th width="12%" height="28" colspan="2">区域</th>
		  	<#list areas?if_exists as a>
				<th width="5%" nowrap="true" title="点击查看该区域统计"  style="cursor:pointer;" onClick="showArea('${a.areaCode}')"><strong>${a.areaName}</strong></td>
    		</#list>
    		<th width="5%" nowrap="true"><strong>合计</strong></th></tr>
    		<#list 0..9 as i>
			  <tr height="25" <#if i_index%2==0>bgcolor="#ffffff"<#else>bgcolor="#dcfafa"</#if> align="center">
			  <#if i_index==0><th rowspan="5"><strong>应<br><br>报<br><br>基<br><br>数</strong></th><th width="12%" nowrap="true">高 危 企 业</th><#assign ptype='w'>
			  <#elseif i_index==1><th nowrap="true">规模以上企业</th><#assign ptype='g'>
			  <#elseif i_index==2><th nowrap="true">人员密集场所</th><#assign ptype='r'>
			  <#elseif i_index==3><th nowrap="true" >其　　他</th><#assign ptype='t'>
			  <#elseif i_index==4><th nowrap="true" >合　　计</th><#assign ptype='h'>
			  <#elseif i_index==5><th nowrap="true" colspan="2">未上报单位数</th><#assign ptype='c'>
			  <#elseif i_index==6><th nowrap="true" colspan="2">季 　报　 率</th>
			  <!--<#elseif i_index==7><th nowrap="true" style="display:none" colspan="2">零隐患单位数</th><#assign ptype='z'>
			  <#elseif i_index==8><th nowrap="true" style="display:none" colspan="2">零　隐　患　率</th>
			  <#elseif i_index==9><th nowrap="true" style="display:none" colspan="2">企 业 填 报 数</th><#assign ptype='q'>--></#if>
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
			    <!--<#elseif i_index==7>${s.zero}<#assign rowTotal=rowTotal+s.zero>
			    <#elseif i_index==8><#if s.ccnum==0>/<#else>#{(s.zero/s.ccnum*100);M1}%</#if>
			    <#elseif i_index==9>${s.aanum}<#assign rowTotal=rowTotal+s.aanum>-->
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
			    <!--<#elseif i_index==7>${rowTotal}<#assign zero=rowTotal>
			    <#elseif i_index==8><#if (dnum-enum)==0>/<#else>#{(zero/(dnum-enum)*100);M1}%</#if>
			    <#elseif i_index==9>${rowTotal}</#if>--></td>
			  </tr>
		  </#list>
		<tr height="25" bgcolor="#D6EEF0" align="center">
            <td height="20" colspan="${areas?size+3}"></td>
            </tr>
        </tbody></table>
	</div>
	<script type="text/javascript">		
		function showArea(areaCode) {
			if("${area.gradePath}".split("/").length-1 == 4) {
				return false;
			} else{
				window.location = "loadPaiChaOfCompany.xhtml?area.areaCode="+areaCode+"&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter=${statistic.quarter}";
			}
		}
		function showDetail(areaCode,companyTrade) {
			window.location = "loadPaiChaOfCompanyList.xhtml?company.year=${statistic.year}&company.quarter=${statistic.quarter}&area.areaCode="+areaCode+"&company.companyTrade="+companyTrade;
		}
		function changeUrl() {
			window.location = "loadPaiChaOfCompany.xhtml?area.areaCode=${area.areaCode}&statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
		}
	</script>
<#include "statistic_foot.ftl">