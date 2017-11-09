<@fkMacros.pageHeader />
<#escape x as (x)!> 
<style type="text/css">
<!--
.STYLE1 {color: #FF3366}
-->
</style>
<#if seasonReport?exists>
	<#if seasonReport?if_exists.id?if_exists!=-1>
  	<#assign url='updateSeasonReport.xhtml'>
  	<#else>
  	<#assign url='createSeasonReport.xhtml'>
  	</#if>
 <#else>
  	<#assign url='createSeasonReport.xhtml'>
</#if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22" background="../img/main_04.gif">安全生产隐患排查治理情况季报表</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !isSee??>
		<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	</#if>
	<li id="img_refurbish">	<a href="#" class="b4"><b>刷新</b></a></li>
	
	<li id="img_return">	<a href="shengchan1_1.html" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
        <tr>
          <th width="14%">单位名称</th>
          <td colspan="6"><#if company??>${company.companyName}</#if><#if bag??>${bag.name}</#if></td>
          </tr>
		  <tr>
          <th>单位地址</th>
          <td colspan="6"><#if company??>${company.regAddress}</#if><#if bag??>${bag.regAddress}</#if></td>
          </tr>
		  <tr>
       <form id="seasonReportForm" name="seasonReportForm" action="" method="post">
          <input type="hidden" name="<#if company??>company.id<#else>bag.id</#if>" value="<#if company??>${company.id}<#else>${bag.id}</#if>" />
          <input type="hidden" name="seasonReport.type" value="1" />
          <#if seasonReport??><input type="hidden" name="seasonReport.id" value="${seasonReport.id}" /></#if>
          
		<tr>
		  <th>作业场所地址</th>
          <td colspan="6"><input name="seasonReport.dangerAdd" type="text"  value="${seasonReport?if_exists.dangerAdd}" size="30" maxlength="40"/>
            <span class="STYLE1">*</span></td>
		</tr>
        <tr>
          <th height="27">作业场所区域</th>
          <td colspan="6">
	        <select  style="WIDTH:100px" onChange="clearOption(this.form.regArea2);showCity(this.options[this.options.selectedIndex].value,this.form.regArea2);">
	          <option value="0">--选择--</option>
	        </select>县(市)区
			<select >
			  <option selected="selected" value="0">--选择--</option>
			</select>乡镇(街道) <span class="STYLE1">*</span></td>
        </tr>
        <tr>
          <th>行业、领域</th>
		  <td colspan="6">
		  <select name="seasonReport.industryId">
		  	<option value="0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--　请选择　--&nbsp;&nbsp;&nbsp;&nbsp;</option>
		  	<#list tradeTypes?if_exists as t>
				<optgroup label="&nbsp;${t.name}">		
				<#list t.daIndustryParameters?if_exists as ts>
					<#if (ts.daIndustryParameters?size>0)> <optgroup label="&nbsp;&nbsp;&nbsp;└${ts.name}&nbsp;&nbsp;"><#else>
					<option value="${ts.id}">&nbsp;&nbsp;&nbsp;└${ts.name}&nbsp;&nbsp;</option></#if>
					<#list ts.daIndustryParameters?if_exists as tss>
						<#if (tss.daIndustryParameters?size>0)> <optgroup label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tss.name}&nbsp;&nbsp;"><#else>
						<option value="${tss.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tss.name}&nbsp;&nbsp;</option></#if>
						<#list tss.daIndustryParameters?if_exists as tsss>
							<option value="${tsss.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tsss.name}&nbsp;&nbsp;</option>
						</#list>
					</#list>
				</#list>  
		  	</#list> 
		  </select> <span class="STYLE1">*</span></td>
          </tr>
        <tr>
          <th>联系人</th>
          <td width="14%"><input name="seasonReport.linkMan" type="text" value="${seasonReport?if_exists.linkMan}" size="12" maxlength="50" /><span class="STYLE1">*</span>		</td>
          <th width="11%">联系电话</th>
          <td width="15%">
		  <input name="seasonReport.linkTel" type="text" size="13" value="${seasonReport?if_exists.linkTel}" maxlength="13" style="ime-mode:disabled" title="例如:固定电话0574-87364008"/>
          <span class="STYLE1">*</span>			  </td>
          <th width="13%">手　　机</th>
          <td colspan="2">
		  <input name="seasonReport.linkMobile" type="text" value="${seasonReport?if_exists.linkMobile}"  size="14" maxlength="13" title="例如:手机13957421121" style="ime-mode:disabled" onBlur="testMobile(this);"/></td>
        </tr>
        <tr>
          <th>一般隐患</th>
          <td colspan="2"><span class="STYLE1">一月份至今发现</span>
            
			<input name="seasonReport.ordinaryDangerFinding" id="ordinaryDangerFinding" type="text" style="ime-mode:disabled" value="<#if seasonReport??><#if (seasonReport.ordinaryDangerFinding gt 0)>${seasonReport.ordinaryDangerFinding}<#else>${seasonReport.ordinaryDangerFindingBefore+1}</#if><#else>1</#if>" readonly = "true" size="8" maxlength="8"/>项
            <span class="STYLE1">*</span></td>
          <td colspan="4"><span class="STYLE1">当前尚未整改</span>
          <input name="seasonReport.ordinaryDangerNotGovern" id ="ordinaryDangerNotGovern" type="text" style="ime-mode:disabled" readonly = "true" size="8" maxlength="8" value="<#if seasonReport??><#if (seasonReport.ordinaryDangerFinding gt 0)>${seasonReport.ordinaryDangerFinding}<#else>${seasonReport.ordinaryDangerFindingBefore+1}</#if><#else>1</#if>" onBlur="compare(this,ordinary_danger_finding,'尚未整改一般隐患数','一月份至今发现的一般隐患数')"/>项
            <span class="STYLE1">*</span></td> 
          </tr>
<!-- add by leixl--> 
		  <tr>
			  <th>隐患内容</th>
	          <td colspan="6">
          			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" id="sqd">
					 	<input name="Submit5" type="button" onClick="add_row();count();" class="button" value=" 添 加 ">
					    <input name="Submit33" onClick="del_row();count();" type="button" class="button" value=" 删 除 ">
						<tr>
					    <td width="5%" align="center" ><div align="center"><strong>序号</strong></div></td>
						<td width="5%" align="center" ><div align="center"><strong><input name=Number type=hidden >选中</strong></div></td>
					    <td width="63%" align="center" ><div align="center"><strong>隐患详细情况</strong></div></td>
					    <td width="10%" align="center" ><div align="center"><strong>是否整改</strong></div></td>
					    <td width="8%" align="center" ><div align="center"><strong>整改时间</strong></div></td>
					 	</tr>
					 	<#if seasonReport?if_exists.seasonReportDetails?exists>
						 	<#list seasonReport.daSeasonReportDetails as srDetail>
							 	<tr>
							 	<td>${seasonReport.ordinaryDangerFindingBefore+srDetail_index+1}
							 		<input type="hidden" name="seasonReport.seasonReportDetails[${srDetail_index}].id" value="${seasonReport.seasonReportDetails[srDetail_index].id}" />
							 	</td>
							 	<td><input name=Number type=radio></td>
							 	<td><input id='contents' name='seasonReport.seasonReportDetails[${srDetail_index}].dangerContent' size=30 value="${seasonReport.seasonReportDetails[srDetail_index].dangerContent}"/></td>
							 	<td><select  id='completed' name='seasonReport.seasonReportDetails[${srDetail_index}].completed' onchange=count()> 
							 	<option value=0 <#if seasonReport.seasonReportDetails[srDetail_index].completed>selected</#if>>未整改</option>
		                        <option value=1 <#if seasonReport.seasonReportDetails[srDetail_index].completed>selected</#if>>已整改</option></select></td>
		                        <td><input id='dangerTime' name='seasonReport.seasonReportDetails[${srDetail_index}].dangerTime' value='${seasonReport.seasonReportDetails[srDetail_index].dangerTime}' readonly /></td>
		                        </tr>
						 	</#list>
					 	<#else>
						 	<tr>
						 	<td><#if seasonReport??>${seasonReport.ordinaryDangerFindingBefore+1}<#else>1</#if></td>
						 	<td><input name=Number type=radio></td>
						 	<td><input id='contents' name='seasonReport.seasonReportDetails[0].dangerContent' size=30 /></td>
						 	<td><select  id='completed' name='seasonReport.seasonReportDetails[0].completed' onchange=count()> <option value=0>未整改</option>
	                          <option value=1>已整改</option></select></td>
	                        <td><input id='dangerTime' name='seasonReport.seasonReportDetails[0].dangerTime' value='2009/8/22' readonly /></td>
	                        </tr>
                        </#if>
					</table>
				</td>
              </tr>
<!-- add by leixl--> 
			  <tr>
              <th>单位负责人</th>
              <td colspan="2"><input name="seasonReport.chargePerson" type="text" value="${company?if_exists.fddelegate}" size="12" maxlength="50" />
                <span class="STYLE1">*</span></td>
              <th width="15%">填表人</th>
              <td><input name="seasonReport.fillMan" type="text" value="${seasonReport?if_exists.fillMan}" size="10" maxlength="50" />
                <span class="STYLE1">*</span></td>
              <th width="12%">填报日期</th>
              <td>
			  <input name="seasonReport.fillDate" type="text" size="12" value="2009.03.26" style="ime-mode:disabled" onKeyUp="AutoFillDate(this,10);" onBlur="DateLoseFocus(this,10)" maxlength="10"/>
			  <span class="STYLE1">*</span></td>
            </tr>
            </form>
          </table>

<script language="javascript">


	var e= /^(([0-9]{3,4})\-[1-9]{1}([0-9]{6,7}))$/i;
	var f= /^((130|131|132|133|134|135|136|137|138|139|158|159|153)\d{8})$/i; 
	var y= /^([1-9]{1}[0-9]{5})$/i;
	
	function testPhone(p){
      while(p.value!="" && p.value.length>0){
		if(e.test(p.value)){
			  return true;
		}
		else{
			   alert("固定电话输入有误,请填写正确的格式,固定电话请加上区号!如：0574-87364008")
			   p.blur();
			   p.value="";
			   p.focus();
			   return false;
		}
	  }
	}
	
	function testMobile(p){
	 while(p.value!="" && p.value.length>0){
		if(f.test(p.value)){
			  return true;
		}
		else{
			   alert("手机号码格式输入有误,请填写正确手机号码,例如：13957421121!")
			   p.blur();
			   p.value="";
			   p.focus();
			   return false;
		}
	  }
	}
	

	
	
function checkBlank(src){
    
    if(!src.value||trim(src.value)==""){
      alert("该字段不能为空!");
      }
    return false;

 }
//The JavaScript Source!! http://smallrain.net
var j=0
function add_row(){

        k=j+1
        j=sqd.rows.length;
        newRow=document.all.sqd.insertRow(-1)
        newcell=newRow.insertCell()
        newcell.innerHTML=j<#if seasonReport??>+${seasonReport.ordinaryDangerFindingBefore}</#if>
        newcell=newRow.insertCell()
        newcell.innerHTML="<input name=Number type=radio>"
		newcell=newRow.insertCell()
        newcell.innerHTML="<input id='contents' name='seasonReport.seasonReportDetails["+(j-1)+"].dangerContent' size=30 />"
		newcell=newRow.insertCell()       
        newcell.innerHTML="<select  id='completed' name='seasonReport.seasonReportDetails["+(j-1)+"].completed' onchange=count()> <option value=0>未整改</option>"+
                          " <option value=1>已整改</option>"+"</select>"
        newcell=newRow.insertCell()
        newcell.innerHTML="<input id='dangerTime' name='seasonReport.seasonReportDetails["+(j-1)+"].dangerTime' value='2009/8/20' readonly />";                  
                 
}
function del_row() {
        if(sqd.rows.length==1) return;
        var checkit = false

        for (var i=0;i<document.all.Number.length;i++) {
                        if (document.all.Number[i].checked) {
                        checkit=true;
                        sqd.deleteRow(i)
                        break;
                        }
        }

        if (checkit) {

                for(i=1;i<sqd.rows.length;i++){
                sqd.rows[i].cells[0].innerText=i
        }


        } else
        {
        alert("请选择一个要删除的对象");
        return false}

}

function count(){

	 var isCompletes = document.all.seasonReportForm.completed
	 var dangerTime = document.all.seasonReportForm.dangerTime  
	 
		var k = 0; 
		for (var i=0; i< isCompletes.length; i++){ 
			if (isCompletes[i].value == 0){
	            k=k+1; 
			}
			
		}	

     document.all.ordinaryDangerFinding.value = sqd.rows.length-1<#if seasonReport??>+${seasonReport.ordinaryDangerFindingBefore}</#if>;
	 document.all.ordinaryDangerNotGovern.value = k<#if seasonReport??>+${seasonReport.ordinaryDangerNotGovernBefore}</#if>;

}

function submitCreate(){
	var formObj=get("seasonReportForm");
	formObj.action="${url}";
	formObj.submit();
}

</script>
<@fkMacros.muilt_select_js />
<#if fkUser?has_content>
<@fkMacros.selectAreas_fun "${fkUser?if_exists.fkUserInfo?if_exists.firstArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.secondArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.thirdArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.fouthArea?if_exists}" "${fkUser?if_exists.fkUserInfo?if_exists.fifthArea?if_exists}" />
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" />
</#if>
</#escape> 
<@fkMacros.pageFooter />