<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.table_list{
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #000;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #000;
	margin-top:5px;
	width:97%;
	background-color:#f7fcff;
}

.table_list th{FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#b4dff8);}

.table_list td {
	font-size:12px;
	color:#000;
	height:12px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-top-color: #FFF;
	border-right-color: #000;
	border-bottom-color: #000;
	border-left-color: #FFF;
	line-height:17px;
	padding-left:5px;
	padding-right:3px;
}

.table_list td p{font-size:12px;text-align:left; padding-left:5px; line-height:14px;}
-->
</style>
<center>
<table width="30%" border="0" height="30" cellspacing="0" cellpadding="0">
  <tr><td height="20">&nbsp;</td></tr>
  <tr>
	<td align="center">
	<input name="print" type="button"  value="确定发送数据" <#if send?exists>disabled="true"</#if>  onclick="javascript:if(confirm('   确定要发送数据吗?')){sendData();}"/>
	</td>
  </tr>
</table>
</center>
<div id='page1'>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center"><th colspan="2" height="50" style="font-size:20px;line-height:30px;">
    <input type="text" id="year" onChange="winLocation();" value="${statistic.yearMonth}" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2010-01',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="9" maxlength="8">
    打击非法建设、非法生产、非法经营情况统计表
    </th>
    </tr>
	<tr height="18" style="font-size:12px">
	  <td  width="70%">　　填报单位（章）:</td>
	  <td nowrap="nowrap">截止到　　　　年　　月　　日为止</td>
	</tr>
</table>
<table width="97.5%" cellspacing="0" cellpadding="0" border="0"  class="table_list">
    
   <tr height="25" style="background:#F0F0F0;color:#333;" align="center">
      <td rowspan="2" width="19%">行业分类</td>
      <td colspan="3" width="27%">非法建设</td>
      <td colspan="3" width="27%">非法生产</td>
      <td colspan="3" width="27%">非法经营</td>
      </tr>
    <tr height="25" style="background:#F0F0F0;color:#333;" align="center">
      <td width="9%">已掌握数<br/>(个)</td>
      <td width="9%">已取缔数<br/>(个)</td>
      <td width="9%">正在打击数<br/>(个)</td>
      <td width="9%">已掌握数<br/>(个)</td>
      <td width="9%">已取缔数<br/>(个)</td>
      <td width="9%">正在打击数<br/>(个)</td>
      <td width="9%">已掌握数<br/>(个)</td>
      <td width="9%">已取缔数<br/>(个)</td>
      <td width="9%">正在打击数<br/>(个)</td>
    </tr>
	<#if statistics?exists>
       <#list statistics?if_exists as s>
        <tr height="25" align="center">
		  <td width="82" id="roleName" name="roleName" nowrap="nowrap">${s.enumName}</td>
          <td id="td_bg" name="td_bg">${s.anum}</td>
          <td id="td_bcd" name="td_bcd">${s.bnum}</td>
		  <td id="td_bcg" name="td_bcg">${s.cnum}</td>			  
          <td id="td_pg" name="td_pg">${s.aanum}</td>
          <td id="td_pcg" name="td_pcg">${s.bbnum}</td>
          <td id="td_pcd" name="td_pcd">${s.ccnum}</td>			  
          <td id="td_tg" name="td_tg">${s.aaanum}</td>
          <td id="td_tcg" name="td_tcg">${s.bbbnum}</td>
          <td id="td_tcd" name="td_tcd">${s.cccnum}</td>
        </tr>
		</#list>
	</#if>
	<tr height="25" align="center">
      <td>合&nbsp;&nbsp;&nbsp;&nbsp;计</td>
	  <td id="t_td_bg" name="t_td_bg"></td>
	  <td id="t_td_bcd" name="t_td_bcd"></td>
      <td id="t_td_bcg" name="t_td_bcg"></td>
      <td id="t_td_pg" name="t_td_pg"></td>
      <td id="t_td_pcg" name="t_td_pcg"></td>
      <td id="t_td_pcd" name="t_td_pcd"></td>
      <td id="t_td_tg" name="t_td_tg"></td>
      <td id="t_td_tcg" name="t_td_tcg"></td>
      <td id="t_td_tcd" name="t_td_tcd"></td>
    </tr>
    </table>
    <table width="97.5%" border="0" height="20" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:12px;line-height:30px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
  </div>      
<script type="text/javascript">
	function get(obj) {
		return document.getElementById(obj);
	}
	function getName(obj) {
		return document.getElementsByName(obj);
	}
	function valuateTotal() {
		if(getName(arguments[0]).length) {
			var td_bg = 0 ;
			for(var i = 0 ; i < getName(arguments[0]).length ; i ++) {
				td_bg = parseInt(td_bg) + parseInt(getName(arguments[0])[i].innerHTML);
			}
			get(arguments[1]).innerHTML = td_bg;
		}else {
			get(arguments[1]).innerHTML = get(arguments[0]).innerHTML;
		}		
	}
	valuateTotal("td_bg","t_td_bg");
	valuateTotal("td_bcd","t_td_bcd");
	valuateTotal("td_bcg","t_td_bcg");
	valuateTotal("td_pg","t_td_pg");
	valuateTotal("td_pcg","t_td_pcg");
	valuateTotal("td_pcd","t_td_pcd");
	valuateTotal("td_tg","t_td_tg");
	valuateTotal("td_tcg","t_td_tcg");
	valuateTotal("td_tcd","t_td_tcd");
	
	function roleName(){
		var roleName = getName("roleName");
		for(var i = 0 ; i<roleName.length-1;i ++){
			if (roleName[i].innerHTML == roleName[i+1].innerHTML ){
				var k = 0;
				for(var j = 0 ; j<roleName.length-1;j ++){
					if (roleName[j].innerHTML == roleName[j+1].innerHTML && roleName[j].innerHTML == roleName[i].innerHTML ){
						k = k+1;
					}
				}
				roleName[i].rowSpan = parseInt(roleName[i].rowSpan) + k;
				roleName[i+1].style.display = "none";
			}
		}
		var indName = getName("indName");
		for(var i = 0 ; i<indName.length-1;i ++){
			if (indName[i].innerHTML == indName[i+1].innerHTML ){
				var k = 0;
				for(var j = 0 ; j<indName.length-1;j ++){
					if (indName[j].innerHTML == indName[j+1].innerHTML && indName[j].innerHTML == indName[i].innerHTML ){
						k = k+1;
					}
				}
				indName[i].rowSpan = parseInt(indName[i].rowSpan) + k;
				indName[i+1].style.display = "none";
			}
		}
	}
	roleName();
	function winLocation() {
		window.location = "?statistic.yearMonth="+get("year").value;
	}
	function sendData(){
		window.location="sendDataOfIllegalByOMElement.xhtml?statistic.yearMonth="+get("year").value;
	}

printParam(20,8,0,0,2);
</script>
 </#escape>
<@fkMacros.pageFooter />
