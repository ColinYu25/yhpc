<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if itemDanger?exists>
  	<#assign url='updateItemDanger.xhtml'>
  <#else>
  	<#assign url='createItemDanger.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("itemDangerForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}	
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>宁波市重大事故隐患情况表(
	<#if flag='jianwei'>
		房屋建筑工程
	<#elseif flag='jiaotong'>
		交通工程
	<#elseif flag='shuili'>
		水利工程
	<#elseif flag='chengguan'>
		市政工程
	</#if>
	)</th>
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
<@fkMacros.formValidator 'itemDangerForm' />
<form id="itemDangerForm" name="itemDangerForm" method="post" action="">
            <table width="100%" border="0"  cellpadding="0" cellspacing="0" class="table_input">
                <tr>
                  <th rowspan="3" align="center">工　程<br>
                    项　目</th>
				  <th align="center" valign="middle">名　　称</th>
                  <td align="left" colspan="2">${item.itemname}</td>
                  <th>市级以上重点工程</th>
                  <td><input type="radio" name="itemDanger.emphasisProject" id="emphasisProject1" value="true" checked="checked"/>是
                  &nbsp;<input type="radio" name="itemDanger.emphasisProject" id="emphasisProject2" value="false" />否</td>
                </tr>
				<tr>
                  <th align="center">地　　址</th>
                  <td align="left" colspan="4">${item.itemaddress}</td>
                </tr>
                  <th align="center">区　　域</th>
                  <td colspan="4"><span id="area"></span></td> 
                </tr>
				<tr>
                  <th rowspan="3" align="center">总承包<br>
                    单位</th>
				  <th align="center" valign="middle">名　　称</th>
                  <td align="left" colspan="4">${item.zcbName}</td>
                </tr>
				<tr>
                  <th align="center">注册地址</th>
                  <td align="left" colspan="4">${item.zcbAdd}</td>
                </tr>
				<tr>
                  <th align="center">联系人</th>
				  <td  align="left">${item.linkman}&nbsp;</td>
				  <th align="center">联系电话</th>
                  <td align="left" colspan="2">${item.tel}&nbsp;</td>
                </tr>
                <tr>				  
                  <th>整治责任单位</th>
                  <td colspan="5"><input name="itemDanger.zzCompany"  type="text"  id="zzCompany" size="96" maxlength="90"  value="${itemDanger.zzCompany}"/></td>
                </tr>
				<tr>
                  <th>整治责任单位负责人</th>
				  <td colspan="2"><input name="itemDanger.zzChargeman" id="zzChargeman" type="text"  maxlength="20" size="30" value="${itemDanger.zzChargeman}"/></td> 
				  <th>整治责任单位电话</th>
                  <td  colspan="2"><input name="itemDanger.zzTel" id="zzTel" type="text"  maxlength="13" style="ime-mode:disabled" size="30" value="${itemDanger.zzTel}"/></td>                  
                </tr>
				<tr>				
				  <th>重大事故隐患<br>基本情况（简述）</th>
				  <td colspan="5"><textarea name="itemDanger.description" cols="80" rows="4" id="description" onpropertychange="checkMaxInput(this,1000);">${itemDanger.description}</textarea></td>
				</tr>
				<tr>
				<th>重大事故隐患类别</th> 
				<td colspan="5">
				<#if tradeTypes?exists>
  					<#list tradeTypes?if_exists as c>
						<input type="checkbox" name="itemDanger.industryParameters" id="industryParameter${c.id}" value="${c.id}"/>${c.name}
					</#list>
				</#if>
				</td> 
				</tr>
				<tr>
				<th rowspan="2">整治说明</th>
				<th><div align="center">是否需政府协调</div></th>
				<th colspan="2"><div align="center">是否需局部停产停业</div></th>
				<th colspan="2"><div align="center">是否需全部停产停业</div></th>
				</tr>
				<tr>
				<td align="center"><input type="radio" name="itemDanger.govCoordination" id="govCoordination1" value="true" checked="checked"/>是
				  &nbsp;<input type="radio" name="itemDanger.govCoordination" id="govCoordination2" value="false" />否</td>
				<td colspan="2" align="center"><input type="radio" name="itemDanger.partStopProduct" id="partStopProduct1" value="true" checked="checked"/>是
                  &nbsp;<input type="radio" name="itemDanger.partStopProduct" id="partStopProduct2" value="false" />否</td>
				<td colspan="2" align="center"><input type="radio" name="itemDanger.fullStopProduct" id="fullStopProduct1" value="true" checked="checked"/>是
                  &nbsp;<input type="radio" name="itemDanger.fullStopProduct" id="fullStopProduct2" value="false" />否</td>
				</tr>
				<tr>
				<th colspan="6"><div align="center">隐患治理落实措施情况</div></th>
				</tr>
				<tr>
				<th>落实治理目标</th>
				<td><input type="radio" name="itemDanger.target" id="target1" value="true" checked="checked"/>是
				<input type="radio" name="itemDanger.target" id="target2" value="false" />否</td>
				<th width="106">落实治理<br>
				  机构人员</th>
				<td><input type="radio" name="itemDanger.worker" id="worker1" value="true" checked="checked"/>是
  				<input type="radio" name="itemDanger.worker" id="worker2" value="false" />否</td>
				<th width="120">落实安全措施<br>及应急预案</th>
				<td width="142"><input type="radio" name="itemDanger.safetyMethod" id="safetyMethod1" value="true" checked="checked"/>是
  				<input type="radio" name="itemDanger.safetyMethod" id="safetyMethod2 value="false" />否</td>
				</tr>
				<tr>
				<th>落实治理<br>
				  经费物资</th>
				<td><input type="radio" name="itemDanger.goods" id="goods1" value="true" checked="checked"/>是
  				<input type="radio" name="itemDanger.goods" id="goods2" value="false" />否</td>
				<th>计划完成<br>
				  治理时间</th>
				<td><input type="text" id="finishDate" name="itemDanger.finishDate" value="${itemDanger.finishDate.toString().substring(0,10)}" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',errDealMode:1})"/></td>
				<th>落实治理经费</th>
				<td> <input name="itemDanger.governMoney" id="governMoney" type="text"  style="ime-mode:disabled" value="${itemDanger.governMoney.toString()}" size="11" maxlength="8"/>
				  (万元)<ui:v for="governMoney" rule="double" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;" warn="&nbsp;"/></td>
				</tr>
				<tr>
				<th>总承包<br>
				  单位负责人</th>
				<td><input name="itemDanger.zcbChargeman" type="text"  id="zcbChargeman" value="${itemDanger.zcbChargeman}" size="15" maxlength="20"/></td>
				<th>总承包<br>
				  单位填表人</th>
				<td><input name="itemDanger.zcbFillman" type="text"  id="zcbFillman"  value="${itemDanger.zcbFillman}" size="12" maxlength="20"/></td>
				<th width="120" align="center" nowrap>总承包单位<br>
				  填报日期</th>
				<td><input type="text" id="zcbFilldate" name="itemDanger.zcbFilldate" value="${itemDanger.zcbFilldate}" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',errDealMode:1})"/></td>
				</tr>		
			
        </table>
         <input type="hidden" name="itemDanger.firstArea"  value="${item.firstArea}"/>
         <input type="hidden" name="itemDanger.secondArea" value="${item.secondArea}"/>
         <input type="hidden" name="itemDanger.thirdArea" value="${item.thirdArea}"/>
         <input type="hidden" name="itemDanger.daItem.id" value="${item.id}"/>
         <input type="hidden" name="itemDanger.id" value="${itemDanger.id}"/>
         <input type="hidden" name="itemDanger.govern" value="${itemDanger.govern}"/>
 </form>
<script type="text/javascript">
<#if itemDanger?exists>
	<#if itemDanger.emphasisProject=false>
		get("emphasisProject2").checked=true;
	</#if>
	<#if itemDanger.govCoordination=false>
		get("govCoordination2").checked=true;
	</#if>
	<#if itemDanger.partStopProduct=false>
		get("partStopProduct2").checked=true;
	</#if>
	<#if itemDanger.fullStopProduct=false>
		get("fullStopProduct2").checked=true;
	</#if>
	<#if itemDanger.target=false>
		get("target2").checked=true;
	</#if>
	<#if itemDanger.worker=false>
		get("worker2").checked=true;
	</#if>
	<#if itemDanger.safetyMethod=false>
		get("safetyMethod2").checked=true;
	</#if>
	
	<#if itemDanger.goods=false>
		get("goods2").checked=true;
	</#if>
	<#if itemDanger.daIndustryParameters?exists>
		<#list itemDanger.daIndustryParameters?if_exists as b>
			get("industryParameter${b.id}").checked=true;
		</#list>
	</#if>
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
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
</#if>
</#escape>
<@fkMacros.pageFooter />