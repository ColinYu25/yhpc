<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0096)http://60.190.2.247:8086/nbyhpc/notice/nosecuritycheck/loadStatisticNotices.xhtml -->
<HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>宁波市安全生产事故隐患排查治理信息系统</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type><LINK 
rel=stylesheet type=text/css href="${resourcePath}/css/style_list1.css">
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />

<!--新增开始 样式和js-->
<style>
#tab_menu { padding-top:2px;}
#tab_menu ul li{ float:left; width:250px; font-size:16px; line-height:64px; text-align:center; font-weight:bold; cursor:pointer;}
#tab_menu ul li.selected{ color:#394c86; background:#f5f5f5; border-left:1px solid #add2ef; border-right:1px solid #add2ef; border-top:1px solid #add2ef;}
.hide{ display:none;}
</style>
<script language="javascript" src="${resourcePath}/js/jquery-1.7.1.min.js"></script>
<script language="javascript">
$(function(){ 
	 var get_li=$("#tab_menu ul li");
	 
	   get_li.click(function(){$(this).addClass("selected").siblings().removeClass("selected");
	 
	 var index = get_li.index(this);
	   $("#tab_content > div").eq(index).show().siblings().hide(); 
		})
})

</script>
<!--新增结束 样式和js-->

<META name=GENERATOR content="MSHTML 8.00.6001.23588"></HEAD>
<BODY>
<DIV class=main>
<DIV class=box>
<DIV class=box-top style="height:66px;">
  <DIV class=box-nav id="tab_menu">
    <ul>
      <li class="selected">通知通报</li>
      <li>工作动态</li>
      <li>法规标准</li>
      <li>表格下载</li>
    </ul>
  </DIV>

</DIV>

<div id="tab_content">
      <!--通知通报 开始-->
      <DIV >
  		<iframe src="${base}/notice/nosecuritycheck/loadStatisticNotices.xhtml?sysComit=false" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height=500px; id="iframepage" name="iframepage"  ></iframe>
	  </div>
      <!--通知通报 结束-->  
      
      <!--工作动态 开始-->
      <div class="hide">
         <iframe src="${base}/notice/nosecuritycheck/loadStatisticActualizeProject.xhtml?sysComit=false" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height=500px; id="iframepage" name="iframepage"  ></iframe>
      </div>
      <!--工作动态 结束-->  
      
      <!--法规标准 开始-->
      <div class="hide">
         <iframe src="${base}/notice/nosecuritycheck/loadStatisticLow.xhtml?sysComit=false" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height=500px; id="iframepage" name="iframepage"  ></iframe>
      </div>
      <!--法规标准 结束--> 
      
      <!--表格下载 开始-->
      <div class="hide">
         <iframe src="${base}/notice/nosecuritycheck/loadStatisticTableDownload.xhtml?sysComit=false" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height=500px; id="iframepage" name="iframepage"  ></iframe>
      </div>
      <!--表格下载 结束-->  

</div> <!-- tab_content end-->



        
        </DIV><!--<div id="footer">
		<p>宁波市安全生产监督管理局</p><span>技术支持：</span><a href="http://www.safetys.cn/"> 安生科技</a><span id="lxdh">联系电话：0574-87364008  0574-87362356-8611 QQ:1428357613 1527962931 
		</span>
	</div>--></DIV></BODY></HTML>
