<@fkMacros.pageHeader />
<#escape x as (x)!> 
<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#menu li {float:left; margin-left:1px;}
#menu li a {display:block; height:30px; line-height:30px; padding:0 20px; float:left; background:#ddd; color:#000; text-decoration:none;}
#menu li a b {text-transform:uppercase;}
.menuSelect a {background: #fff url(${resourcePath}/img/map/arrow.gif) no-repeat center bottom !important; color:#e60  !important;}
</style>
<script language="javascript">
	var accidentTankId=-1;//选择的储罐ID
	var rescueModelType="";//模拟类型
	var rescueModelId=-1;//模拟输入保存的ID
	var analyseModule="rescue";
	var rescuePrearrangedId=-1;
	function step(id){
		if(id>1){
		    var frontId=id-1;
        	$('step'+frontId).className="";
		}
		$('step'+id).className="menuSelect";
	}
</script>

<ul id="menu">
	<li id="step1"><a href="#"><b>1.定位事发危险源</b></a></li>
	<li id="step2"><a href="#"><b>2.事故后果模拟</b></a></li>
	<!--li id="step4"><a href="#"><b>3.填写事故通知单</b></a></li>
	<li id="step5"><a href="#"><b>4.启动应急救援</b></a></li-->
</ul>
<!--h1><img src="${resourcePath}/img/btding.jpg"/>&nbsp;&nbsp;<span id="step1">定位事发危险源</span>&nbsp;&nbsp;<img src="${resourcePath}/img/btding.jpg"/>&nbsp;&nbsp;<span id="step2">事故后果模拟</span>&nbsp;&nbsp;<img src="${resourcePath}/img/btding.jpg"/>&nbsp;&nbsp;<span id="step3">启动应急预案</span>&nbsp;&nbsp;<img src="${resourcePath}/img/btding.jpg"/>&nbsp;&nbsp;<span id="step4">填写事故通知单</span>&nbsp;&nbsp;<img src="${resourcePath}/img/btding.jpg"/>&nbsp;&nbsp;<span id="step5">启动应急救援</span>&nbsp;&nbsp;</h1-->
<iframe id="rescueFrame" name="rescueFrame" src='${contextPath}/map/loadCompanysByRescue.xhtml' width='673' height='360' scrolling='auto' noresize='noresize' frameborder='0'></iframe>
</#escape> 
<@fkMacros.pageFooter />