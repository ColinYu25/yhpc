<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if itemSeasonReport?exists>
  	<#assign url='updateItemSeasonReport.xhtml'>
  <#else>
  	<#assign url='createItemSeasonReport.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
  		  var obj=get("itemReportForm");
  		  obj.action="${url}";
 		  obj.submit();
}

function add_row(){
        if(moreCompany.rows.length>19) return;
        newRow = get("moreCompany").insertRow();
		newcell=newRow.insertCell();
        newcell.innerHTML="<input name='itemSeasonReport.itemSeasonDetails["+(get("moreCompany").rows.length-1)+"].companyName'  class='input' type='text' size='70' maxlength='50' />";
        newcell=newRow.insertCell();
        newcell.innerHTML="<input type='button' onclick='del_row()'  name='subtractOne' class='input_input' value='删除此行'>";
}
function del_row(){   
      document.all.moreCompany.deleteRow(window.event.srcElement.parentElement.parentElement.rowIndex);   
    }  	
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>宁波市安全生产隐患排查治理情况季报表（
	<#if flag='jianwei'>
		房屋建筑工程
	<#elseif flag='jiaotong'>
		交通工程
	<#elseif flag='shuili'>
		水利工程
	<#elseif flag='chengguan'>
		市政工程
	</#if>
	）</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'itemReportForm' />
<form id="itemReportForm" name="itemReportForm" method="post" action="">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
                <tr>
                  <th rowspan="4" align="center">工　程<br>
                    项　目</th>
				  <th align="center" valign="middle">名　　称</th>
                  <td align="left" colspan="4">${item.itemname}&nbsp;</td>
                </tr>
				<tr>
                  <th align="center">地　　址</th>
                  <td align="left" colspan="4">${item.itemaddress}&nbsp;</td>
                </tr>
				<tr><th align="center">建筑面积</th><td>${item.buildArea}（万平方米）</td>
				<th align="center">是否竣工</th><td id="iscompleted"></td></tr>
				<tr>
                  <th align="center">区　　域</th>
                  <td colspan="4"><span id="area"></span></td> 
                </tr>
				<tr>
				<th colspan="2" align="center">建设单位名称</th>
				<td colspan="4">${item.ownername}&nbsp;</td>
				</tr>				
				<tr>
                  <th rowspan="3" align="center">总承包<br>
                    单位</th>
				  <th align="center" valign="middle">名　　称</th>
                  <td align="left" colspan="4">${item.zcbName}&nbsp;</td>
                </tr>
				<tr>
                  <th align="center">注册地址</th>
                  <td align="left" colspan="4">${item.zcbAdd}&nbsp;</td>
                </tr>
				<tr>
                  <th align="center">联系人</th>
				  <td  align="left">${item.linkman}&nbsp;</td>
				  <th align="center">联系电话</th>
                  <td align="left" colspan="2">${item.tel}&nbsp;</td>
                </tr>
                <th colspan="2" align="center">分包单位</th>
				<td colspan="5"><table width="100%" border="1" id="moreCompany">
				<td width="80%">
				<input id="dd" name="itemSeasonReport.itemSeasonDetails[0].companyName" class="input" type="text"  size="70" maxlength="50"   /></td>
				  <td width="20%" align="left">
				  <input type="button" onClick="add_row()" name="SubmitOne" class="input_input" value="增加一行">
				</td>
				
              </table></td>
            </tr>
            <tr>
				<td colspan="6" align="center">一　　般　　隐　　患</td> 
			</tr>
			<tr>
				<td colspan="3" align="center">工程项目累计发现
                  <input name="itemSeasonReport.ordinaryDangerFinding" id="ordinaryDangerFinding" type="text" value="${itemSeasonReport.ordinaryDangerFinding}" class="input" style="ime-mode:disabled" value="" size="20" maxlength="8"/>项
                  <ui:v for="ordinaryDangerFinding" require="false" rule="integer" warn="请输入正整数字类型" pass="&nbsp;"/>
                </td>
				<td colspan="3" align="center">当前尚未整改
                  <input name="itemSeasonReport.ordinaryDangerNotGovern" type="text" value="${itemSeasonReport.ordinaryDangerNotGovern}"  id ="ordinaryDangerNotGovern" style="ime-mode:disabled"  value="" size="20"  maxlength="8"/>项
                  <ui:v for="ordinaryDangerNotGovern" require="false" rule="integer" warn="请输入正整数字类型" pass="&nbsp;"/>
                 </td>
				</tr>
				<tr>
				<th colspan="2" align="center">主要隐患简述</th>
				<td colspan="4"><textarea name="itemSeasonReport.description" id="description" cols="70" rows="4">${itemSeasonReport.description}</textarea></td>
				</tr>
				<th colspan="2">单位负责人</th>
				<td ><input name="itemSeasonReport.chargePerson" id="chargePerson" type="text" class="input" value="${itemSeasonReport.chargePerson}" size="15" maxlength="5"></td>
				<th >填表人</th>
				<td ><input name="itemSeasonReport.fillMan" id="fillMan" type="text" class="input" value="${itemSeasonReport.fillMan}" size="14" maxlength="5">
				 </td>
				</tr>
				<tr>
				<th colspan="2">填报日期</th>
				<td colspan="4"><input type="text" id="fillDate" name="itemSeasonReport.fillDate" value="${itemSeasonReport.fillDate}" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H-%m',errDealMode:1})"/>
				<ui:v for="fillDate" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
				</tr>
        </table>
         <input type="hidden" name="itemSeasonReport.firstArea" value="${item.firstArea}"/>
         <input type="hidden" name="itemSeasonReport.secondArea" value="${item.secondArea}"/>
         <input type="hidden" name="itemSeasonReport.thirdArea" value="${item.thirdArea}"/>
         <input type="hidden" name="itemSeasonReport.daItem.id" value="${item.id}"/>
         <input type="hidden" name="type" value="${type}"/>
         <input type="hidden" name="itemSeasonReport.id" value="${itemSeasonReport.id}"/>
         <input type="hidden" name="itemSeasonReport.type" value="${itemSeasonReport.type}"/>
 </form>
<script type="text/javascript">
<#if itemSeasonReport?exists>
	<#list itemSeasonReport.daItemSeasonDetails?if_exists as a>
	<#if a_index=0>
		get("dd").value="${a.companyName}";
	<#else>
		newRow = get("moreCompany").insertRow();
		newcell=newRow.insertCell();
		newcell.innerHTML="<input name='itemSeasonReport.itemSeasonDetails[${a_index}].companyName' value='${a.companyName}' class='input' type='text' size='70' maxlength='50' />";
		newcell=newRow.insertCell();
		newcell.innerHTML="<input type='button' onclick='del_row()'  name='subtractOne' class='input_input' value='删除此行'>";
		</#if>
	</#list>
</#if>
var areaObj = new Area("${AreaXmlUrl}");
	var firstArea="";
	var secondArea="";
	var thirdArea="";
  <#if item?exists>
 	<#if item.firstArea != 0>
		firstArea=areaObj.getArea("${item.firstArea}")[0];
	</#if>
	<#if item.secondArea != 0>
		secondArea=areaObj.getArea("${item.secondArea}")[0];
	</#if>
	<#if item.thirdArea != 0>
		thirdArea=areaObj.getArea("${item.thirdArea}")[0];
	</#if>
  </#if>
	get("area").innerHTML=firstArea+secondArea+thirdArea;
	<#if item?exists>
 		<#if item.iscompleted='1'>
 			get("iscompleted").innerHTML="是";
 		<#else>
 			get("iscompleted").innerHTML="否";
 		</#if>
 	</#if>
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
</#if>
</#escape>
<@fkMacros.pageFooter />