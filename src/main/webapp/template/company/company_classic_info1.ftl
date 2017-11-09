<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#assign url='updateCompanyLevel.xhtml'>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("companyForm");
  		  obj.action="${url}";
 		  obj.submit();
 	} 
}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业分类</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="submitCreate();"><b>保存/确认</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyForm' />
<form id="companyForm" name="companyForm" method="post" action="">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <th>单位名称</th>
    <td width="35%">${company.companyName}&nbsp;</td>
   <!-- <th>分级评估得分</th>
    <td id="comPoint" width="35%"></td>-->
  </tr>
  <tr>
  <td colspan="4" align="center" ><table width="100%" border="0" cellpadding="0" cellspacing="0"  class="table_input">
  <tr>
    <td colspan="4" align="center"  width="100%" ><div align="center"><strong>加油站事故隐患自查标准</strong></div></td>
  </tr>
  		 <tr>
    <td rowspan="20">基础管理</td>
    <td rowspan="2">资质证照</td>
    <td>营业执照</td>
	<td >
	  			<input type="radio"  name="yh1"    onclick="show(1)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh1"  onclick="hide(1)" /> 否
	  			<textarea rows="1" cols="20" id="txt1" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td>危险化学品经营许可证</td>
	<td >
	  			<input type="radio"  name="yh2"    onclick="show(2)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh2"  onclick="hide(2)" /> 否
	  			<textarea rows="1" cols="20" id="txt2" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td>安全生产管理机构及人员</td>
    <td>应当设置安全生产管理机构或配备专职安全生产管理人员</td>
	<td >
	  			<input type="radio"  name="yh3"    onclick="show(3)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh3"  onclick="hide(3)" /> 否
	  			<textarea rows="1" cols="20" id="txt3" style="display:none"  >		</textarea>	 			</td>
  </tr>
  <tr>
    <td rowspan="2">安全生产责任制</td>
    <td>单位主要负责人</td>
		<td >
	  			<input type="radio"  name="yh4"    onclick="show(4)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh4"  onclick="hide(4)" /> 否
	  			<textarea rows="1" cols="20" id="txt4" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td>各部门、各岗位职责</td>
		<td >
	  			<input type="radio"  name="yh5"    onclick="show(5)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh5"  onclick="hide(5)" /> 否
	  			<textarea rows="1" cols="20" id="txt5" style="display:none"  >		</textarea>	  			</td>
  </tr>
  <tr>
    <td rowspan="5">安全生产规章制度</td>
	
	<td>安全生产例会制度</td>
		<td >
	  			<input type="radio"  name="yh6"    onclick="show(6)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh6"  onclick="hide(6)" /> 否
	  			<textarea rows="1" cols="20" id="txt6" style="display:none"  >		</textarea>			</td>
  </tr>
  <tr>
    <td>生产安全事故报告和处理制度</td>
		<td >
	  			<input type="radio"  name="yh7"    onclick="show(7)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh7"  onclick="hide(7)" /> 否
	  			<textarea rows="1" cols="20" id="txt7" style="display:none"  >		</textarea>	 			</td>
  </tr>
  <tr>
    <td>安全生产奖励和惩罚制度</td>
		<td >
	  			<input type="radio"  name="yh8"    onclick="show(8)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh8"  onclick="hide(8)" /> 否
	  			<textarea rows="1" cols="20" id="txt8" style="display:none"  >		</textarea>	 			</td>
  </tr>
  <tr>
    <td>消防管理制度</td>
		<td >
	  			<input type="radio"  name="yh9"    onclick="show(9)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh9"  onclick="hide(9)" /> 否
	  			<textarea rows="1" cols="20" id="txt9" style="display:none"  >		</textarea>	 			</td>
  </tr><tr>
    <td>防雷、防静电安全管理制度</td>
		<td >
	  			<input type="radio"  name="yh10"    onclick="show(10)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh10"  onclick="hide(10)" /> 否
	  			<textarea rows="1" cols="20" id="txt10" style="display:none"  >		</textarea>	 			</td>
  </tr>
  <tr>
    <td rowspan="2">安全教育培训</td>
	<td>生产经营单位主要负责人、安全管理人员及教育培训</td>
		<td >
	  			<input type="radio"  name="yh11"    onclick="show(11)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh11"  onclick="hide(11)" /> 否
	  			<textarea rows="1" cols="20" id="txt11" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td>特种作业人员培训</td>
		<td >
	  			<input type="radio"  name="yh12"    onclick="show(12)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh12"  onclick="hide(12)" /> 否
	  			<textarea rows="1" cols="20" id="txt12" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td rowspan="3">安全生产管理基础档案</td>
	<td>安全生产教育培训记录</td>
	<td ><input type="radio"  name="yh13"    onclick="show(13)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh13"  onclick="hide(13)" /> 否
	  			<textarea rows="1" cols="20" id="txt13" style="display:none"  >		</textarea>	  	</td>
  </tr>
  <tr>
    <td>安全检查及事故隐患排查记录</td>
	<td ><input type="radio"  name="yh14"    onclick="show(14)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh14"  onclick="hide(14)" /> 否
	  			<textarea rows="1" cols="20" id="txt14" style="display:none"  >		</textarea>		</td>
  </tr>
  <tr>
    <td>劳动防护用品配备和管理记录档案</td>
	<td ><input type="radio"  name="yh15"    onclick="show(15)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh15"  onclick="hide(15)" /> 否
	  			<textarea rows="1" cols="20" id="txt15" style="display:none"  >		</textarea>	  	</td>
  </tr>
  <tr>
    <td rowspan="2">应急救援</td>
    <td>应急救援预案</td>
	<td ><input type="radio"  name="yh16"    onclick="show(16)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh16"  onclick="hide(16)" /> 否
	  			<textarea rows="1" cols="20" id="txt16" style="display:none"  >		</textarea>		</td>
  </tr>
  <tr>
    <td>应急救援演练记录</td>
	<td ><input type="radio"  name="yh17"    onclick="show(17)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh17"  onclick="hide(17)" /> 否
	  			<textarea rows="1" cols="20" id="txt17" style="display:none"  >		</textarea>	 	</td>
  </tr>
  <tr>
    <td rowspan="3">职业卫生基础管理</td>
    <td>危害因素检测报告</td>
	<td ><input type="radio"  name="yh18"    onclick="show(18)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh18"  onclick="hide(18)" /> 否
	  			<textarea rows="1" cols="20" id="txt18" style="display:none"  >		</textarea>	  	</td>
  </tr>
  <tr>
    <td>危害告知</td>
	<td ><input type="radio"  name="yh19"    onclick="show(19)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh19"  onclick="hide(19)" /> 否
	  			<textarea rows="1" cols="20" id="txt19" style="display:none"  >		</textarea>	 	</td>
  </tr>
  <tr>
    <td>职业卫生档案</td>
	<td ><input type="radio"  name="yh20"    onclick="show(20)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh20"  onclick="hide(20)" /> 否
	  			<textarea rows="1" cols="20" id="txt20" style="display:none"  >		</textarea>	 	</td>
  </tr>
  
  <tr>
    <td rowspan="20">现场管理</td>
    <td rowspan="2">用电安全</td>
    <td>配电箱、柜</td>
	<td >
	  			<input type="radio"  name="yh21"    onclick="show(21)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh21"  onclick="hide(21)" /> 否
	  			<textarea rows="1" cols="20" id="txt21" style="display:none"  >		</textarea>	 			</td>
  </tr>
  <tr>
    <td>临时照明</td>
	<td >
	  			<input type="radio"  name="yh22"    onclick="show(22)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh22"  onclick="hide(22)" /> 否
	  			<textarea rows="1" cols="20" id="txt22" style="display:none"  >		</textarea>				</td>
  </tr>
  <tr>
    <td rowspan="2">职业卫生现场安全</td>
    <td>生产布局</td>
	<td >
	  			<input type="radio"  name="yh23"    onclick="show(23)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh23"  onclick="hide(23)" /> 否
	  			<textarea rows="1" cols="20" id="txt23" style="display:none"  >		</textarea>	 			</td>
  </tr>
   <tr>
    <td>警示标识</td>
	<td >
	  			<input type="radio"  name="yh24"    onclick="show(24)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh24"  onclick="hide(24)" /> 否
	  			<textarea rows="1" cols="20" id="txt24" style="display:none"  >		</textarea>	 			</td>
  </tr>
   <tr>
    <td rowspan="3">场所环境</td>
    <td>站内建筑物</td>
	<td >
	  			<input type="radio"  name="yh25"    onclick="show(25)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh25"  onclick="hide(25)" /> 否
	  			<textarea rows="1" cols="20" id="txt25" style="display:none"  >		</textarea>				</td>
  </tr>
   <tr>
    <td>周边环境</td>
	<td >
	  			<input type="radio"  name="yh26"    onclick="show(26)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh26"  onclick="hide(26)" /> 否
	  			<textarea rows="1" cols="20" id="txt26" style="display:none"  >		</textarea>	 			</td>
  </tr>
   <tr>
    <td>明火距离</td>
	<td >
	  			<input type="radio"  name="yh27"    onclick="show(27)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh27"  onclick="hide(27)" /> 否
	  			<textarea rows="1" cols="20" id="txt27" style="display:none"  >		</textarea>				</td>
  </tr>
   <tr>
    <td rowspan="2">从业人员操作行为</td>
    <td>特种作业人员持证上岗</td>
	<td >
	  			<input type="radio"  name="yh28"    onclick="show(28)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh28"  onclick="hide(28)" /> 否
	  			<textarea rows="1" cols="20" id="txt28" style="display:none"  >		</textarea>	 			</td>
  </tr>
   <tr>
    <td>“三违”行为</td>
	<td >
	  			<input type="radio"  name="yh29"    onclick="show(29)"  />  是&nbsp;&nbsp;&nbsp;
	  			<input type="radio"  name="yh29"  onclick="hide(29)" /> 否
	  			<textarea rows="1" cols="20" id="txt29" style="display:none"  >		</textarea>				</td>
  </tr>
  		
  </table>
  </td>
  </tr>
</table>
<input type="hidden" name="company.id" id="id" value="${company.id}"/>
<input type="hidden" name="company.companyPoint" id="companyPoint" />
</form>
<script type="text/javascript">
	function show(id){
   		 document.getElementById("txt"+id).style.display="";
    }
 	function hide(id){
   		 document.getElementById("txt"+id).style.display="none";
    }  
</script>
</#escape>
<@fkMacros.pageFooter />