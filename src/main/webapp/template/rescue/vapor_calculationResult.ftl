<@fkMacros.pageHeader />
<#escape x as (x)!> 
<@fkMacros.evaluation_result_head_menu 'vapor'/>
<script language="javascript">
	document.parentWindow.parent.resetWindow("modelResult",0,0,350,195);//窗口重画
</script>
<#if vaporCloudCache?exists>
<script language="javascript">
var names=new Array("财产损失半径","轻伤半径","重伤半径","死亡半径");
document.parentWindow.parent.addModelRadius("${vaporCloudCache.r}","${vaporCloudCache.markerX}","${vaporCloudCache.markerY}",names);
var staticRadius="${vaporCloudCache.r}".split(",");
staticRadius.sort(function (a,b){return b-a});
</script>
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_input">
    <tr>
      <th width="28%">财产损失半径<img width="13" height="12" src="${resourcePath}/img/map/radio_1.JPG" onClick="document.parentWindow.parent.baozhanbanjin('${vaporCloudCache.markerX}','${vaporCloudCache.markerY}',staticRadius[0]);"/></th>
      <td width="25%"><script>document.write(Math.round((Math.floor(staticRadius[0]*1000)/10))/100);</script> m</td>
      <th width="22%">轻伤半径<img width="13" height="12" src="${resourcePath}/img/map/radio_2.JPG" onClick="document.parentWindow.parent.baozhanbanjin('${vaporCloudCache.markerX}','${vaporCloudCache.markerY}',staticRadius[1]);"/></th>
      <td width="25%"><script>document.write(Math.round((Math.floor(staticRadius[1]*1000)/10))/100);</script> m</td>
    </tr>
    <tr>
      <th>重伤半径<img width="13" height="12" src="${resourcePath}/img/map/radio_3.JPG" onClick="document.parentWindow.parent.baozhanbanjin('${vaporCloudCache.markerX}','${vaporCloudCache.markerY}',staticRadius[2]);"/></th>
      <td><script>document.write(Math.round((Math.floor(staticRadius[2]*1000)/10))/100);</script> m</td>
      <th>死亡半径<img width="13" height="12" src="${resourcePath}/img/map/radio_4.JPG" onClick="document.parentWindow.parent.baozhanbanjin('${vaporCloudCache.markerX}','${vaporCloudCache.markerY}',staticRadius[3]);"/></th>
      <td><script>document.write(Math.round((Math.floor(staticRadius[3]*1000)/10))/100);</script> m</td>
    </tr>
    <tr>
      <th>闪蒸系数</th>
      <td>${vaporCloudCache.shanZhengXiShu?string("0.000")}</td>
      <th>爆炸能量</th>
      <td>${vaporCloudCache.baoZhaNengLiang?string("0.000")} J</td>
    </tr>
    <tr>
      <th>TNT当量</th>
      <td>${vaporCloudCache.tntDangLiang?string("0.000")} kg</td>
      <th>云团中燃<br />料的质量</th>
      <td>${vaporCloudCache.yunTuanZhongRanLiaoZhiLiang?string("0.000")} kg</td>
    </tr>
    <tr>
      <th>${vaporParam.x?c}米处冲击<br />波正相最大超压</th>
      <td colspan="3">${vaporCloudCache.baoZhaChongJiBo?string("0.000")} Pa</td>
    </tr>
</table>
</#if>
</#escape> 
<@fkMacros.pageFooter />