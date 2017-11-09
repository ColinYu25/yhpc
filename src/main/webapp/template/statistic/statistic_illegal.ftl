<#include "statistic_head.ftl">
<div id="content">
<table width="100%" cellspacing="1" cellpadding="0" border="0" style="font-family: '宋体'; font-size: 12px; color: #4F6B72;; background-color:#C1DAD7">
   <tbody>
   <tr>
    <th height="25" bgcolor="#D6EEF0" align="center" colspan="11"><span class="head"><input type="text" id="year" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    第<#if statistic.quarter==1>一<#elseif statistic.quarter==2>二<#elseif statistic.quarter==3>三<#elseif statistic.quarter==4>四</#if>季度打非统计
             　　<input name="firstQuarter" type="button"  value="一季度"  onclick="changeQUrl(1);"/>
               &nbsp; &nbsp;<input name="secondQuarter" type="button"  value="二季度"  onclick="changeQUrl(2);"/>
               &nbsp; &nbsp;<input name="thirdQuarter" type="button"  value="三季度"  onclick="changeQUrl(3);"/>
               &nbsp; &nbsp;<input name="fouthQuarter" type="button"  value="四季度"  onclick="changeQUrl(4);"/></span></th>
    </tr>
    
   <tr height="25" bgcolor="#dcfafa" align="center">
      <th colspan="2" rowspan="2" width="19%">行业分类</th>
      <th colspan="3" width="27%">非法建设</th>
      <th colspan="3" width="27%">非法生产</th>
      <th colspan="3" width="27%">非法经营</th>
      </tr>
    <tr height="25" bgcolor="#dcfafa" align="center">
      <th width="9%">已掌握数<br/>(个)</th>
      <th width="9%">已取缔数<br/>(个)</th>
      <th width="9%">正在打击数<br/>(个)</th>
      <th width="9%">已掌握数<br/>(个)</th>
      <th width="9%">已取缔数<br/>(个)</th>
      <th width="9%">正在打击数<br/>(个)</th>
      <th width="9%">已掌握数<br/>(个)</th>
      <th width="9%">已取缔数<br/>(个)</th>
      <th width="9%">正在打击数<br/>(个)</th>
    </tr>
    <tr height="25" align="center" bgcolor="#ffffff">
      <th colspan="2">总&nbsp;&nbsp;&nbsp;&nbsp;计</th>
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
	<#if statistics?exists>
       <#list statistics?if_exists as s>
        <tr height="25" align="center" <#if s_index%2!=0>bgcolor="#ffffff"<#else>bgcolor="#dcfafa"</#if>>
		  <th id="indName" name="indName" nowrap="nowrap">${s.name}</th>
		  <th width="82" id="roleName" name="roleName" nowrap="nowrap">${s.enumName}</th>
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
	<tr height="25" bgcolor="#D6EEF0" align="center"><td height="20" colspan="11"></td></tr>
    </tbody>
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
	function changeQUrl() {
		window.location = "?statistic.year="+get("year").value.substring(0,4)+"&statistic.quarter="+arguments[0];
	}
</script>
<#include "statistic_foot.ftl">