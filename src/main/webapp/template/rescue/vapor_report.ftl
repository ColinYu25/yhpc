<@fkMacros.pageHeader />
<#escape x as (x)!> 
<@fkMacros.evaluation_result_head_menu 'vapor'/>
<link href="${resourcePath}/css/map.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	document.parentWindow.parent.resetWindow("modelResult",0,0,545,325);//窗口重画
</script>
<style media=print>
    .Noprint{display:none;}
</style>
<!--object id=WebBrowser classid=clsid:8856f961-340a-11d0-a96b-00c04fd705a2 height=0 width=0></object>
<div class="menu">
		<div class="Noprint">
			<ul>
				<li id="img_print"><a href="#" onclick="javascript: window.print();" class="b5"><b>打印</b></a></li>
				<li id="img_logout"><a href="#" onclick="javascript: WebBrowser.ExecWB(7,1);" class="b10"><b>设置</b></a></li>
			</ul>
		</div>
</div-->
 <table id="tankTable" width="99%" border="1" cellpadding="0" cellspacing="0" align="center" style="font-size:14px; color:#000;">
 <tr>
 <td>  <table id="materialTable" width="99%" border="0" cellpadding="0" cellspacing="0" class="table_report">
   <tr>
	  <th height="39" colspan="4"><div  align="center"><strong>蒸气云爆炸事故后果模拟</strong></div></th>
  </tr>
  <tr>
    <th height="20" colspan="4"><div  align="left"><strong>模拟输入项：</strong></div></th>
  </tr>
  <tr>
    <th height="20" width="20%">存储物质</td>
    <td height="20" width="30%" colspan="3">汽油</td>

  </tr> 
  <tr>
    <th width="20%">罐内温度</td>
    <td width="30%">${vaporCloudCache.vapor.gnwz?c}&nbsp;℃</td>
    <th width="20%">泄漏质量</th>
    <td width="30%">${vaporCloudCache.vapor.xrzl?c}&nbsp;kg</td>
  </tr>   
  <tr>
    <th>TNT的暴热</th>
    <td>${vaporParam.tntbr?c}&nbsp;J/Kg</td>  
    <th>TNT当量系数</td>
    <td>${vaporParam.tntdlxs?c}</td>
  </tr>   
  <tr>
    <th>环境压力</td>
    <td>${vaporParam.hjyl?c}&nbsp;Pa</td>    
    <th>蒸气云当量系数</th>
    <td>${vaporParam.dlxs?c}</td>    
  </tr>  
  <tr>
    <th colspan="4"><div  align="left"><strong>模拟输出项：</strong></div></th>
  </tr>  
  <tr>
    <th>TNT当量</td>
    <td>${vaporCloudCache.tntDangLiang?string("0.000")}&nbsp;kg</td>  
    <th>爆炸能量</td>
    <td>${vaporCloudCache.baoZhaNengLiang?string("0.000")}&nbsp;MJ</td>   
  </tr>  
  <tr>
    <th>闪蒸系数</td>
    <td>${vaporCloudCache.shanZhengXiShu?string("0.000")}</td>
    <th>云团中燃料的质量</th>
    <td>${vaporCloudCache.yunTuanZhongRanLiaoZhiLiang?string("0.000")}&nbsp;kg</td>
  </tr>  
  <tr>
    <!--th>无因次距离</th>
    <td>${vaporCloudCache.wuYinCiJuLi?string("0.000")}&nbsp;m</td-->
    <th>${vaporParam.x?c}米处冲击<br />波正相最大超压</td>
    <td colspan="3">${vaporCloudCache.baoZhaChongJiBo?string("0.000")}&nbsp;Pa</td>
  </tr> 
</table>
</td></tr></table>
</#escape>            
<@fkMacros.pageFooter />