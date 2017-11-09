<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if itemDangerGover?exists>
  	<#assign url='updateItemDangerGover.xhtml'>
  <#else>
  	<#assign url='createItemDangerGover.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("itemDangerGoverForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}	
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>宁波市重大事故隐患整治情况表(
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
	<li id="img_del">		<a href="#" class="b3" onClick="del(${itemDangerGover.id});"><b>删除</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'itemDangerGoverForm' />
<form id="itemDangerGoverForm" name="itemDangerGoverForm" method="post" action="">
<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="table_input">
                <tr>
                  <th rowspan="3" align="center">工　程<br>
                    项　目</th>
				  <th>名　　称</th>
                  <td align="left" colspan="5">${item.itemname}&nbsp;</td>
                </tr>
				<tr>
                  <th align="center">地　　址</th>
                  <td align="left" colspan="4">${item.itemaddress}&nbsp;</td>
                </tr>
                  <th align="center">区　　域</th>
                  <td colspan="4"><span id="area"></span></td> 
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
                <tr>				  
                  <th>整治责任单位</th>
                  <td colspan="5">${itemDanger.zzCompany}&nbsp;</td>
                </tr>
				<tr>
                  <th>整治责任单位负责人</th>
				  <td colspan="2">${itemDanger.zzChargeman}&nbsp;</td> 
				  <th>整治责任单位电话</th>
                  <td  colspan="2">${itemDanger.zzTel}&nbsp;</td>                  
                </tr>
				<tr>				
				  <th>重大事故隐患<br>整治情况(简述)</th>
				  <td colspan="5"><textarea name="itemDangerGover.gorverContent" cols="80" rows="4" id="gorverContent">${itemDangerGover.gorverContent}</textarea></td>
				</tr>
				
				<tr>
				<th>完成整治时间</th>
				<td colspan="2"><input type="text" id="finishDate" name="itemDangerGover.finishDate" value="${itemDangerGover.finishDate.toString().substring(0,10)}" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',errDealMode:1})"/></td>
				<th>实际投入资金</th>
				<td colspan="2"> <input name="itemDangerGover.money" id="money" type="text" value="${itemDangerGover.money.toString()}" size="11" maxlength="8"/>
				  (万元)<ui:v for="money" rule="double" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;" warn="&nbsp;"/></td>
				</tr>
				<tr>				
				  <th>备注</th>
				  <td colspan="5"><textarea name="itemDangerGover.memo" cols="80" rows="4" id="memo" onpropertychange="checkMaxInput(this,1000);">${itemDangerGover.memo}</textarea></td>
				</tr>
				<tr>
				<th>总承包<br>
				  单位负责人</th>
				<td><input name="itemDangerGover.zcbChargeman" type="text" id="zcbChargeman" value="${itemDangerGover.zcbChargeman}" size="15" maxlength="20"/></td>
				<th>总承包<br>
				  单位填表人</th>
				<td><input name="itemDangerGover.zcbFillman" type="text" id="zcbFillman"  value="${itemDangerGover.zcbFillman}" size="12" maxlength="20"/></td>
				<th width="120" align="center" nowrap>总承包单位<br>
				  填报日期</th>
				<td><input type="text" id="zcbFilldate" name="itemDangerGover.zcbFilldate" value="${itemDangerGover.zcbFilldate}" class="Wdate" style="ime-mode:disabled;" maxLength="10" size="15" onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',errDealMode:1})"/></td>
				</tr>		
			
        </table>
         <input type="hidden" name="itemDangerGover.daItemDanger.id" value="${itemDanger.id}"/>
         <input type="hidden" name="itemDangerGover.id" value="${itemDangerGover.id}"/>
         <input type="hidden" name="itemDangerGover.daItemDanger.daItem.id" value="${itemDanger.daItem.id}"/>
 </form>
<script type="text/javascript">
function del(id){
	if(id==undefined){
		alert("还没有保存隐患治理情况！");
	}else{
		location.href="../itemDangerGover/deleteItemDangerGover.xhtml?ids="+id+"&itemDangerGover.daItemDanger.daItem.id="+${itemDanger.daItem.id};
	}
}
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
  </#if>
	get("area").innerHTML=firstArea+secondArea;
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${item?if_exists.firstArea?if_exists}" "${item?if_exists.secondArea?if_exists}" "${item?if_exists.thirdArea?if_exists}" "${item?if_exists.fouthArea?if_exists}" "${item?if_exists.fifthArea?if_exists}" "item."/>
</#if>
</#escape>
<@fkMacros.pageFooter />