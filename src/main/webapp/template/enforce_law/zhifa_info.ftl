<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if daZhifaReport?exists>
  	<#assign url='updateEnforceLaw.xhtml'>
  <#else>
  	<#assign url='createEnforceLaw.xhtml'>
  </#if>
<script>
function submitCreate(){
 	if(formValidator.validate()){
 		var obj=get("zhifaForm");
  		obj.action="${url}";
 		obj.submit();
 	}
}
</script>
<@fkMacros.formValidator 'zhifaForm' />
<form name="zhifaForm" id="zhifaForm" method="post">
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0" id="tb">
     <tr>
      <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10"></td>
        </tr>
		 <tr>
          <tH colspan="3" style="font-size:16px" class="head" align="center">安全生产执法行动情况统计表<#if daZhifaReport?exists>修改<#else>录入</#if></tH>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:<input type="text" id="unit" name="daZhifaReport.unit" value="${daZhifaReport.unit}" >
		  <ui:v for="unit" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/></td>
		  <td height="18" width="20%" align="center" nowrap="nowrap" valign="bottom">月份
		  	<input type="text" value="${daZhifaReport.writtenMonth}" id="writtenMonth" onfocus="WdatePicker({dateFmt:'yyyy-MM'});" name="daZhifaReport.writtenMonth" class="Wdate" size="10"/>
		  </td>
		</tr>
      </table></td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left">
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_list">
            <tr>
              <td width="10%" rowspan="2">行业（领域）</td>
              <td rowspan="2">开展执法行动情况合计 （起、处）</td>
              <td>无证或证照不全从事建设、生产、经营的（起）</td>
              <td>关闭取缔后又擅自建设、生产、经营的（起）</td>
              <td>小煤矿应关未关的（处 ）</td>
              <td>私采滥挖、超层越界开采、尾矿库违规排放的（起）</td>
              <td>违反建设项目安全设施“三同时”规定的（起）</td>
              <td>谎报、瞒报事故的（起）</td>
              <td>重大隐患隐瞒不报或不按规定期限予以整治的（起）</td>
              <td>不按规定进行安全培训或无证上岗的（起）</td>
              <td>拒不执行安全监管监察指令、抗拒安全执法的（起）</td>
              <td>其他非法违法建设、生产、经营的（起）</td>
              </tr>
            <tr>
              <td width="8%">1</td>
              <td width="8%">2</td>
              <td width="8%">3</td>
              <td width="8%">4</td>
              <td width="8%">5</td>
              <td width="8%">6</td>
              <td width="8%">7</td>
              <td width="8%">8</td>
              <td width="8%">9</td>
              <td width="8%">10</td>
            </tr>
			<tbody id="tf">
			<#list daIndustryParameters?if_exists as dis>
				<tr id="row1">
				  <td style="text-align:left" id="td_type" nowrap="nowrap">${dis.name}</td><input type="hidden"  name="daZhifaReportDetails[${dis_index}].industryParameterId" value="${dis.id}"/><input type="hidden" id="id_${dis_index}" name="daZhifaReportDetails[${dis_index}].id" value=""/>
				  <td align="center" id="dis_0_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_0" value="" name="daZhifaReportDetails[${dis_index}].total" class="input" maxlength="8" size="4"  readOnly  /></td>
	              <td align="center" id="dis_1_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_1" value="" name="daZhifaReportDetails[${dis_index}].data1"  class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_2_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_2" value="" name="daZhifaReportDetails[${dis_index}].data2" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_3_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_3" value="" name="daZhifaReportDetails[${dis_index}].data3" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
				  <td align="center" id="dis_4_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_4" value="" name="daZhifaReportDetails[${dis_index}].data4" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_5_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_5" value="" name="daZhifaReportDetails[${dis_index}].data5" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_6_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_6" value="" name="daZhifaReportDetails[${dis_index}].data6" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_7_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_7" value="" name="daZhifaReportDetails[${dis_index}].data7" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_8_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_8" value="" name="daZhifaReportDetails[${dis_index}].data8" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_9_${dis_index}" ><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_9" value="" name="daZhifaReportDetails[${dis_index}].data9" class="input" maxlength="8" size="4"   onblur= "checkNumber(this)"/></td>
	              <td align="center" id="dis_10_${dis_index}"><input type="text" style="IME-MODE:disabled;" id="inp_${dis_index}_10" value="" name="daZhifaReportDetails[${dis_index}].data10" class="input" maxlength="8" size="4"  onblur= "checkNumber(this)"/></td>
	            </tr>
			</#list>
            
			
		   </tbody>
   <tr>
       <td colspan="15"><table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr>
	   <td width="8%" align="center">审核人：</td>
	   <td width="16%"><input type="text" id="auditor"  name="daZhifaReport.auditor" value="${daZhifaReport.auditor}" class="input" maxlength="8" size="8"/>
	     <ui:v for="auditor" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/></td>
	   <td width="8%" align="center">填报人：</td>
	   <td width="16%"> <input type="text"  id="informanter" value="${daZhifaReport.informanter}" name="daZhifaReport.informanter" class="input" maxlength="8" size="8"/>
	     <ui:v for="informanter" rule="require" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/></td>
	   <td width="10%" align="center">联系电话：</td>
	   <td width="17%"><input type="text" id="phone" name="daZhifaReport.phone" class="input" maxlength="13" size="15" style="ime-mode:disabled;"  value="${daZhifaReport.phone}"/>
	   </td>
	   <td width="10%" align="center">填报日期： </td>
	   <td width="17%"><input type="text" id="writtenDate" name="daZhifaReport.writtenDate" maxlength="10" size="14" value="${daZhifaReport.writtenDate[0..10]}" onfocus="WdatePicker();" onKeyUp="AutoFillDate(this,10);" class="Wdate" readOnly/>
	   </td>
	   </tr></table></td>
    </tr>
	
          </table></td>
        </tr>
		
      </table> 
  <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
   <tr>
	 <td width="28%" align="center">
	 	<input type="hidden" id="id" name="daZhifaReport.id" value="${daZhifaReport.id}">
		<input name="sub" type="button" class="but_2" value="保　存" onClick="submitCreate();" />
		 <input name="back" type="button" class="but_2" value="返　回"  onClick="javaScript:history.back();"/>
		 </td>
	</tr>
</table>
</form>
<script>
<#if daZhifaReport?exists && daZhifaReport.daZhifaReportDetails?exists>
	<#list daZhifaReport.daZhifaReportDetails as dis>
		get("id_${dis_index}").value=${dis.id};
		get("inp_${dis_index}_0").value=${dis.total};
		get("inp_${dis_index}_1").value=${dis.data1};
		get("inp_${dis_index}_2").value=${dis.data2};
		get("inp_${dis_index}_3").value=${dis.data3};
		get("inp_${dis_index}_4").value=${dis.data4};
		get("inp_${dis_index}_5").value=${dis.data5};
		get("inp_${dis_index}_6").value=${dis.data6};
		get("inp_${dis_index}_7").value=${dis.data7};
		get("inp_${dis_index}_8").value=${dis.data8};
		get("inp_${dis_index}_9").value=${dis.data9};
		get("inp_${dis_index}_10").value=${dis.data10};
	</#list>
</#if>
function checkNumber(obj){
	var reg=/^\d+$/;
	if(obj.value!=""){
		if(reg.test(obj.value)==false){
			alert("请填写整数!");
			obj.focus();
		}else{
			rowSum(obj);
			//if(obj.id.split("_")[1]<9){
				//colSum(obj);
				//col0Sum();
			//}
		}
	}
}
function rowSum(obj){
	var object=obj.id.split("_");
	var para=object[0]+"_"+object[1]+"_";
	var total=0;
	for(var i=1;i<=10;i++){
		if(get(para+i).value!=""){
			total=total+parseInt(get(para+i).value);
		}
				
	}
	get(para+0).value=total;
}
function colSum(obj){
	var rowId=obj.id.substring(4,5);
	var colId=obj.id.substring(6,7);
	var total=0;
	if(rowId<9){
		for(var i=1;i<=8;i++){
			if(get("inp_"+i+"_"+colId).value!=""){
				total=total+parseInt(get("inp_"+i+"_"+colId).value);
			}
		}
		get("inp_0_"+colId).value=total;
	}
}
function col0Sum(){
	var total=0;
	for(var i=1;i<=8;i++){
		if(get("inp_"+i+"_0").value!=""){
			total=total+parseInt(get("inp_"+i+"_0").value);
		}
	}
	get("inp_0_0").value=total;
}
</script>
</#escape>
<@fkMacros.pageFooter />