var _isIE	 = window.navigator.appName == "Microsoft Internet Explorer" ? true:false;
var _isOpera = window.navigator.appName == "Opera" ? true:false;
var isThree = false;
var isPrint = false;
var isZhifa = false;
var isDfzwPunish = false;
var tradeType = 0;

function setIsThree(three) {
	isThree = three;
}

function setIsZhifa(zhifa) {
	isZhifa = zhifa;
}

function setIsDfzwPunish(dfzwPunish) {
	isDfzwPunish = dfzwPunish;
}

function setIsPrint(isPrint) {
	this.isPrint = isPrint;
}

function setTradeType(tradeType) {
	this.tradeType = tradeType;
}

// outerHTML for firefox
if(typeof(HTMLElement)!="undefined" && !window.opera)
{
  HTMLElement.prototype.__defineGetter__("outerHTML",function()
  {
    var a=this.attributes, str="<"+this.tagName, i=0;for(;i<a.length;i++)
    if(a[i].specified) str+=" "+a[i].name+'="'+a[i].value+'"';
    if(!this.canHaveChildren) return str+" />";
    return str+">"+this.innerHTML+"</"+this.tagName+">";
  });
  HTMLElement.prototype.__defineSetter__("outerHTML",function(s)
  {
    var r = this.ownerDocument.createRange();
    r.setStartBefore(this);
    var df = r.createContextualFragment(s);
    this.parentNode.replaceChild(df, this);
    return s;
  });
  HTMLElement.prototype.__defineGetter__("canHaveChildren",function()
  {
    return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
  });
}

String.prototype.toDate = function(x, p) {
  if(x == null) x = "-";
  if(p == null) p = "ymd";
  var a = this.split(x);
  var y = parseInt(a[p.indexOf("y")]);
  if(y.toString().length <= 2) y += 2000;
  if(isNaN(y)) y = new Date().getFullYear();
  var m = parseInt(a[p.indexOf("m")]) ;
  var d = a[p.indexOf("d")];
	m--;
  if(isNaN(d)) d = 1;

  return new Date(y, m, d);
}


/* -------- Common Method Congregate By Desmond ------------------ */


var CommonFunctions = new Object();

CommonFunctions.Interval = [];
CommonFunctions._isIE	 = window.navigator.appName == "Microsoft Internet Explorer"?true:false;
CommonFunctions._isOpera = window.navigator.appName == "Opera"?true:false;
// trim string
CommonFunctions.trim = function(str)
{
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
// create html element

CommonFunctions.createElement = function(type, parent) {
	var el = null;
	if (document.createElementNS) {		// use the XHTML namespace;
		el = document.createElementNS("http://www.w3.org/1999/xhtml", type);
	} else {
		el = document.createElement(type);
	}
	if (typeof parent != "undefined") {
		parent.appendChild(el);
	}
	return el;
}

CommonFunctions.createSizeString = function(size){
	if(typeof(size) == "number"){
		return(size + "px");
	}
	else if((size*1).toString()==size){
		return(size + "px");
	}
	else{
		return(size);
	}
}

CommonFunctions.getElement = function(ev) {
	if (CommonFunctions._isIE) {
		return window.event.srcElement;
	} else {
		return ev.currentTarget;
	}
};

CommonFunctions.getTargetElement = function(ev) {
	if (CommonFunctions._isIE) {
		return window.event.srcElement;
	} else {
		return ev.target;
	}
};

CommonFunctions.getInnerSize = function(){
	var w = window.innerWidth  || (document.documentElement.offsetWidth  || document.body.offsetWidth);
	var h = window.innerHeight || (document.documentElement.offsetHeight || document.body.offsetHeight);
	return [w,h];
}

CommonFunctions.getScrollSize = function(){
	var w = document.documentElement.scrollLeft  || document.body.scrollLeft;
	var h = document.documentElement.scrollTop || document.body.scrollTop;
	return [w,h];
}


CommonFunctions.copyNodeInto	= function(id,newid,target){
	var newnode = $(id).cloneNode(true);
	$(id).id = id + "tmp";
	target.insertBefore(newnode,null);
	$(id).id = newid;
	$(id + "tmp").id = id;
}


CommonFunctions.cutNodeInto	= function(id,target){
	var newnode = $(id).cloneNode(true);
	$(id).outerHTML = '';
	target.insertBefore(newnode,null);
}


CommonFunctions.getCurrentStyle = function(obj, prop) {
	if (obj.currentStyle) {
		return obj.currentStyle[prop];
	}
	else if (window.getComputedStyle) {
		prop = prop.replace (/([A-Z])/g, "-$1");
		prop = prop.toLowerCase ();
		return window.getComputedStyle (obj, "").getPropertyValue(prop);
	}
	return null;
}


CommonFunctions.startDrag = function(ev,target,r,moveevent,offset,limit){
	ev 				= ev || window.event;
	var obj 		= Event.element(ev);
	var x 			= ev.layerX || ev.offsetX,y = ev.layerY || ev.offsetY;
	var offrange 	= !offset?[0,0]:offset;
	var hp 	= vp = 0;
		var hx 	= Event.pointerX(ev) - offrange[0] - x;
		var vy 	= Event.pointerY(ev) - offrange[1] - y;
		hp 		= !r?hx:(hx<r[0]?r[0]:hx>r[1]?r[1]:hx);
		vp 		= !r?vy:(vy<r[2]?r[2]:vy>r[3]?r[3]:vy);
		target.style.left 	= hp + "px";
		target.style.top 	= vp + "px";
	document.onmousemove = function(ev){
		ev 		= ev || window.event;
		var hp 	= vp = 0;
		var hx 	= Event.pointerX(ev) - offrange[0] - x;
		var vy 	= Event.pointerY(ev) - offrange[1] - y;
		hp 		= !r?hx:(hx<r[0]?r[0]:hx>r[1]?r[1]:hx);
		vp 		= !r?vy:(vy<r[2]?r[2]:vy>r[3]?r[3]:vy);
		target.style.left 	= hp + "px";
		target.style.top 	= vp + "px";
		if(moveevent)
			moveevent();

	}
	if(!limit && _isIE){
		if(document.body.setCapture)
			document.body.setCapture();
		else if(window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
	}

	if(CommonFunctions._isIE){
		document.onselectstart = function(){return false};
	}
	document.onmouseup = CommonFunctions.stopDrag;
}


CommonFunctions.stopDrag = function(ev,stopmoveevent){
	ev 		= ev || window.event;
	var obj = Event.element(ev);

	if(stopmoveevent)
		stopmoveevent();
	if(document.body.releaseCapture)
		document.body.releaseCapture();
	else if(window.captureEvents)
		window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
	document.onmousemove 	= null;
	document.onmouseup		= null;
	if(CommonFunctions._isIE){
		document.onselectstart = function(){return true};
	}
}


CommonFunctions.StartFloat = function(interid,target,floatoffset,floatevent){
	var myfoffset 	= floatoffset || 1;
	var mytarget	= target;
	var myevent		= floatevent;
	var _moveLayerBy	= function(l,x,y){
		l.style.left	= (l.offsetLeft	+ x) + "px";
 		l.style.top		= (l.offsetTop	+ y) + "px";
	}
	var _float = function(){
		if  (typeof(mytarget.sX) == "undefined")  { mytarget.sX=0; };
    	if  (typeof(mytarget.sY) == "undefined")  { mytarget.sY=0; };
		var sx = CommonFunctions.getScrollSize()[0],sy = CommonFunctions.getScrollSize()[1];
		if(mytarget.sX != sx|| mytarget.sY != sy){
			var mx 		= sx>mytarget.sX?Math.ceil:Math.floor;
			var my 		= sy>mytarget.sY?Math.ceil:Math.floor;
    	    var dx 		= mx((sx - mytarget.sX)/myfoffset);
			var	dy 		= my((sy - mytarget.sY)/myfoffset);
			mytarget.sX += dx;
			mytarget.sY += dy;
			_moveLayerBy(mytarget,dx,dy);
			if(myevent)
				myevent();
		}
	}
	CommonFunctions.Interval.push({intervalid:interid,interval:eval(interid + " = setInterval(_float,50)")});
}
CommonFunctions.StopFloat = function(interid){
	for(var i=0;i<CommonFunctions.Interval.length;i++){
		if(CommonFunctions.Interval[i].intervalid == interid)
			eval("clearInterval('" + CommonFunctions.Interval[i].interval + "')");
	}
}


CommonFunctions.getAbsPoint = function(e){
	if(!e)
	 return [0,0];
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
}


CommonFunctions.preloadImages = function() { //v3.0
	var d=document;
	if(d.images){
		if(!d.MM_p)
			d.CF_p=new Array();
   		var i,j=d.CF_p.length,a=CommonFunctions.preloadImages.arguments;
		for(i=0; i<a.length; i++)
   			if (a[i].indexOf("#")!=0){
				d.CF_p[j]=new Image;
				d.CF_p[j++].src=a[i];
			}
	}
}



function Cookie()
{
  this.SetValue=function(name,value,hours,path,domain,secure){
    var str=new String();
    var nextTime=new Date();
    nextTime.setHours(nextTime.getHours()+hours);
    str=name+"="+escape(value);
    if(hours)
      str+=";expires="+nextTime.toGMTString();
    if(path)
      str+=";path="+path;
    if(domain)
      str+=";domain="+domain;
    if(secure)
      str+=";secure";
    document.cookie=str;
    }
  this.GetValue=function(name){
    var rs=new RegExp("(^|)"+name+"=([^;]*)(;|$)","gi").exec(document.cookie),tmp;
    if(tmp=rs)
      return unescape(tmp[2]);
    return null;
    }
}







/* ----- functions for current project ---------- */

function setSelectedMenu(){
	if(typeof crtMainMenu != 'undefined'){
		if($("MainMenu01"))
			$("MainMenu01").className = "Normal";
		if($(crtMainMenu))
			$(crtMainMenu).className = "Selected";
	}

}

function setCurrentPosition(){
	var str='';
	if(strPositionText)
		str += strPositionText ;
	if(crtMainMenu){
	//	str += "&nbsp;&gt;&nbsp;";
		var mylink =$(crtMainMenu).getElementsByTagName("a");
		if(mylink[0]){
			str += "<a href='" + mylink[0].href + "'>";
			str += mylink[0].innerHTML;
			str += "</a>";
		}
	}
	if(window.location.href.indexOf("menuname=")>0){
		var mstr = getLocationString();
		var arrId = mstr.split("_");
		for(var i=0;i<arrId.length;i++){
			str += "<a href='" + $(arrId[i]).href + "'>";
			str += $(arrId[i]).innerHTML;
			str += "</a>";
			if(i != arrId.length-1)
				str += "&nbsp;&gt;&nbsp;";
		}
	}

	if($("CurrentPosition") && $(crtMainMenu))
		$("CurrentPosition").innerHTML = str;
}

function switchTable(arrTabs,arrTables,selTab,selTable){
	for(var i=0;i<arrTabs.length;i++){
		$(arrTabs[i]).className = "normal";
		$(arrTables[i]).style.display = "none";
	}
	$(selTab).className = "active";
	$(selTable).style.display = "";
}

function getLocationString(){
//	var hs = window.location.href
//	var start = hs.indexOf("menuname=") + 9;
//	var end	= hs.indexOf("&")>0?(hs.split("&")[0].length):hs.length;
//	var mstr = hs.substring(start,end);
//	return mstr	;

	var hs = window.location.href
	var start = hs.indexOf("menuname=") + 9;
	var end	= hs.indexOf("&",start)>0?(hs.indexOf("&",start)):hs.length;
	var mstr = hs.substring(start,end);
	return mstr	;

}

function initLocation(){
	if(window.location.href.indexOf("menuname=")>0){
		var mstr = getLocationString();
		var arrId = mstr.split("=");
		if(arrId[0]!=""){
			var p =$(arrId[0]);
			if(p)
				p.className = "Selected";
				//if($(arrId[i]))
					//$(arrId[i]).style.display = '';
		
		}
	}
}
function AutoFillDate(CurrDate,DateTypeLength)
{ 	
	var formatStyle = '-';
	if (!(window.event.keyCode == 8))
	{
		//'-'
		if(CurrDate.value.length == 4) 
			CurrDate.value += formatStyle;



		if(CurrDate.value.length == 5 || CurrDate.value.length == 8) 	
		{
			if(CurrDate.value.charAt(CurrDate.value.length - 1) != formatStyle) 
				CurrDate.value = CurrDate.value.substring(0,CurrDate.value.length - 1) + formatStyle;
		}
		if (CurrDate.value.charAt(5) == formatStyle || CurrDate.value.charAt(8) == formatStyle)
		{
			CurrDate.value = CurrDate.value.substring(0,tCurrDate.value.length - 1);
		}
		if (CurrDate.value.charAt(6) == formatStyle)
		{
			CurrDate.value = CurrDate.value.substring(0,5) + '0' + CurrDate.value.substring(5,CurrDate.value.length)
		}
		if (CurrDate.value.charAt(9) == formatStyle)
		{
			CurrDate.value = CurrDate.value.substring(0,8) + '0' + CurrDate.value.substring(8,9)
		}
		if(CurrDate.value.length == 7 && CurrDate.value.charAt(7)!=formatStyle) 
			CurrDate.value += formatStyle;
		//ܳ
		if (CurrDate.value.length > DateTypeLength) 
		{
			CurrDate.value = CurrDate.value.substring(0,DateTypeLength);
		}
	
	}
}

function checkDateOnMouse(obj) {
	obj.onkeypress = function() {
		return (event.keyCode >= 48 && event.keyCode <= 57);
	}
	obj.onpaste = function() {
		return !clipboardData.getData("text").match(/\D/);
	}
	obj.ondragenter = function() {
		return false;
	}
}

function checkNumber(obj) {
	obj.onkeypress = function() {
		return event.keyCode >= 48 && event.keyCode <= 57;
	}
	obj.onpaste = function() {
		return !clipboardData.getData("text").match(/\D/);
	}
	obj.ondragenter = function() {
		return false;
	}
}

function checkNumber(obj, type) {//type=1代表正整数；type=2代表整数；type=3代表正数；type=4代表数字
	obj.style.imeMode = "disabled";
	obj.value=parseFlloat(obj.value);
	obj.onkeypress = function() {
//		var strP=/^[-\+]?\d+(\.\d+)?$/;
//		if(obj.value!="" && (!strP.test(obj.value+String.fromCharCode(event.keyCode)) && !strP.test(obj.value))) return false;
		if ((obj.value.indexOf(".") > -1 && event.keyCode == 46) || (obj.value == "" && event.keyCode == 46)
			|| (obj.value.indexOf("-") > -1 && event.keyCode == 45)) {//验证无法输入2个小数点和无法以小数点开头
			return false;
		}
		if (type<3) {
			if (type==1)
				return (event.keyCode >= 48 && event.keyCode <= 57);
			else 
				return (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 45;
		} else if (type<5) {
			if (type==3){
				alert(event.keyCode);
				return (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 46;
			} else 
				return (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 46 || event.keyCode == 45;
		}
	}
	obj.onpaste = function() {
		return !clipboardData.getData("text").match(/\D/);
	}
	obj.ondragenter = function() {
		return false;
	}
}

function checkNum(txtNum,msg){
	if (txtNum.tagName=="td") {
		return true;
	}
    var reg=/^\d*$/gi;
    var isNum=reg.exec(txtNum.value); 
    if(!isNum)
    {
        alert(msg);
        txtNum.focus();
        return false;
    }
    return true;
}

function checkNum1(txtNum,msg){
	if (txtNum.tagName=="td") {
		return true;
	}
    var reg=/^[+]?\d+(\.\d+)?$/;
    if(!txtNum.value){
    	return true;
    }
    var isNum=reg.exec(txtNum.value); 
    if(!isNum){
        alert(msg);
        txtNum.focus();
        return false;
    }
    return true;
}

function checkNum3(txtNum,msg){
	if (txtNum.tagName=="td") {
		return true;
	}
    var reg=/^\d*$/gi;
    var isNum=reg.exec(txtNum.value); 
    if(!isNum)
    {
        alert(msg);
        txtNum.focus();
        return false;
    }
    return true;
}

function checkNum2(txtNum,msg){
	if (txtNum.tagName=="td") {
		return true;
	}
    var reg=/^[+]?\d+(\.\d+)?$/;
    var isNum=reg.exec(txtNum.value); 
    if(!isNum)
    {
        alert(msg);
        txtNum.focus();
        return false;
    }
    return true;
}

function get(obj) {
	return document.getElementById(obj);
}

function getAll(str) {
	return document.all(str);
}

Number.prototype.Fixed = function(n){   
      with(Math){   
          var   tmp=pow(10,n);   
          return   round(this*tmp)/tmp;   
      }   
}   
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}


function canNotExcess(obj,excess,flag) {
	if (obj.tagName.toLowerCase()=="td") {
				
	} else {
		if(flag){
			if (!checkNum(obj,"请填写正整数！")) {
				obj.focus();
				return false;
			}
		}else {
			if (!checkNum2(obj,"请填写正数！")) {
				obj.focus();
				return false;
			}
		}
	}
	var excessValue = 0;	
	var value = 0;
	if(excess.tagName.toLowerCase()=="input") {
		if (excess.value == ""){
			alert("父项为空！");
			return false;
		}
		excessValue = parseFloat(excess.value);
	} else if(excess.tagName.toLowerCase()=="td") {
		if (excess.value == ""){
			alert("父项为空！");
			return false;
		}
		excessValue = parseFloat(excess.innerHTML);
	}
	if(obj.tagName.toLowerCase()=="input") {
		if (obj.value == ""){
			obj.value = "0";
			return false;
		}
		value = parseFloat(obj.value);
	} else if(obj.tagName.toLowerCase()=="td") {
		if (obj.innerHTML == ""){
			obj.innerHTML = "0";
			return false;
		}
		value = parseFloat(obj.innerHTML);
	}
	if (value > excessValue) {
		alert("子项的值不能大于父项！");
		obj.value = flag?"0":"0.0";
		return false;
	}
	return true;
}

function sumSon() {
	//arguments[arguments.length-1]最后一个参数，代表是否是整数
	var flag = arguments[arguments.length-1];
	var sum = 0;
	for (var i=0; i < arguments.length-2; i++) {
		if(arguments[i].tagName.toLowerCase()=="input") {
			if(flag){
				if (!checkNum(arguments[i],"请填写正整数！")) {
					arguments[i].focus();
					return false;
				}
			}else {
				if (!checkNum2(arguments[i],"请填写正数！")) {
					arguments[i].focus();
					return false;
				}
			}
			if (arguments[i].value == "&nbsp;")
				continue;
			sum += parseFloat(arguments[i].value);
		} else if(arguments[i].tagName.toLowerCase()=="td") {
			if (arguments[i].innerHTML == "&nbsp;")
				continue;
			sum += parseFloat(arguments[i].innerHTML);
		}
	}
	if (arguments[arguments.length-2].tagName.toLowerCase()=="input") {
		arguments[arguments.length-2].value = parseFloat(sum).Fixed(2);
	} else if (arguments[arguments.length-2].tagName.toLowerCase()=="td") {
		if (sum==0) {
			arguments[arguments.length-2].innerHTML = "&nbsp;";
		} else {
			arguments[arguments.length-2].innerHTML = isNaN(sum)?"&nbsp;":parseFloat(sum).Fixed(2);
		}
	} else {
		
	}
	return true;
}
/**
 * 
 * @param {} obj 当前对象
 * @param {} args 累加的对象数组
 * @param {} result	显示累加结果
 * @param {} flag 是否是正整数
 * @return {Boolean}
 */
function sumSons(obj,args,result,flag) {
	//最后一个参数，代表是否是整数
	if (obj.tagName.toLowerCase()=="td") {
				
	} else {
		if(flag){
			if (!checkNum(obj,"请填写正整数！")) {
				obj.focus();
				return false;
			}
		}else {
			if (!checkNum2(obj,"请填写正数！")) {
				obj.focus();
				return false;
			}
		}
	}
	var sum = 0;
	for (var i=0; i < args.length; i++) {
		if(args[i].tagName.toLowerCase()=="input") {
			if (args[i].value == "&nbsp;" || args[i].value == "")
				continue;
			sum += parseFloat(args[i].value);
		} else if(args[i].tagName.toLowerCase()=="td") {
			if (args[i].innerHTML == "&nbsp;" || args[i].innerHTML == "")
				continue;
			sum += parseFloat(args[i].innerHTML);
		}
	}
	if(isNaN(sum)&&result.tagName.toLowerCase()=="input"){
		alert("请填写正数！");
		return false;
	}
	if (result.tagName.toLowerCase()=="input") {
		result.value = parseFloat(sum).Fixed(2);
	} else if (result.tagName.toLowerCase()=="td") {
		if (sum==0) {
			result.innerHTML = isThree?"0":"&nbsp;";
		} else {
			result.innerHTML = isNaN(sum)?"&nbsp;":parseFloat(sum).Fixed(2);
		}
	} else {
		
	}
	if (especial) {
		if (obj.id.indexOf("shouldTroubleshooting") > -1) {
			sumEspecial(result,get("shouldTroubleshooting_"+especial));
		} else if (obj.id.indexOf("company") > -1) {
			sumEspecial(result,get("company_"+especial));
		} else if (obj.id.indexOf("generalDangerGovern") > -1) {
			sumEspecial(result,get("generalDangerGovern_"+especial));
		} else if (obj.id.indexOf("generalDanger") > -1) {
			sumEspecial(result,get("generalDanger_"+especial));
		} else if (obj.id.indexOf("planMoney") > -1) {
			sumEspecial(result,get("planMoney_"+especial));
		}
	}
	if (result.id=="generalDanger_t"||result.id=="generalDangerGovern_t") {
		getDivisor(get("generalDangerGovern_t"),get("generalDanger_t"),"zg_t",true);
	}else if (result.id=="shouldTroubleshooting_t"||result.id=="company_t") {
		if(get("shouldTroubleshooting_t")&&(((get("shouldTroubleshooting_t").value&&get("shouldTroubleshooting_t").value!='0'))
				||((get("shouldTroubleshooting_t").innerHTML&&get("shouldTroubleshooting_t").innerHTML!='0')))) {
			getDivisor(get("company_t"),get("shouldTroubleshooting_t"),"zg1_t",true);
		}
	}
	return true;
}

function getDivisor(divisor,dividend,result_id,flag) {
	var result = get(result_id);
	if (!divisor || !dividend) {
		return;
	}
	if (divisor.tagName.toLowerCase()=="td") {
				
	} else {
		if(flag){
		if (!checkNum(divisor,"请填写正整数！")) {
				divisor.focus();
				return false;
			}
			if (!checkNum(dividend,"请填写正整数！")) {
				dividend.focus();
				return false;
			}		
		}else {
			if (!checkNum2(divisor,"请填写正数！")) {
				divisor.focus();
				return false;
			}
			if (!checkNum2(dividend,"请填写正数！")) {
				dividend.focus();
				return false;
			}
		}
		if(!canNotExcess(divisor,dividend,flag)){
			return false;
		}
	}
	var res = 0;
	if (divisor.tagName.toLowerCase()=="td") {		
		if (dividend.innerHTML == "0" || dividend.innerHTML == "&nbsp;" 
			|| divisor.innerHTML == "0" || divisor.innerHTML == "&nbsp;") {
			res = "&nbsp;";
		} else {
			if (dividend.innerHTML != "&nbsp;") {
				res = parseFloat(divisor.innerHTML)/parseFloat(dividend.innerHTML)*100;
			} else {
				res = "&nbsp;";
			}
		}
		if (result.tagName.toLowerCase()=="input") {
			res = (res=="&nbsp;")?"&nbsp;":parseFloat(res).Fixed(1);
			if(res==100||res==0||res=="&nbsp;") {
				result.value = res;
			} else {				
				var str = parseFloat(res).Fixed(1)+"";
				if (str.substring((str.length-2),str.length)==".0") {
					result.value = parseFloat(res).Fixed(0);
				} else {
					result.value = parseFloat(res).Fixed(1);
				}
			}
		} else if (result.tagName.toLowerCase()=="td") {
			res = (res =="&nbsp;")?"&nbsp;":parseFloat(res).Fixed(1);
			if(res==100||res==0||res=="&nbsp;") {
				result.innerHTML = res;
			} else {
				var str = parseFloat(res).Fixed(1)+"";
				if (str.substring((str.length-2),str.length)==".0") {
					result.innerHTML = parseFloat(res).Fixed(0);
				} else {
					result.innerHTML = parseFloat(res).Fixed(1);
				}
			}
		} else {
			
		}
	} else {	
		if (dividend.value == "0") {
			res = "&nbsp;";
		} else {
			res = parseFloat(divisor.value)/parseFloat(dividend.value)*100;
		}
		if (result.tagName.toLowerCase()=="input") {
			res = (res=="&nbsp;")?"&nbsp;":parseFloat(res).Fixed(1);
			if(res==100||res==0||res=="&nbsp;") {
				result.value = res;
			} else {
				result.value = parseFloat(res).Fixed(1);
			}
		} else if (result.tagName.toLowerCase()=="td") {
			res = (res=="&nbsp;")?"&nbsp;":parseFloat(res).Fixed(1);
			if(res==100||res==0||res=="&nbsp;") {
				result.innerHTML = res;
			} else {
				result.innerHTML = parseFloat(res).Fixed(1);
			}
		} else {
			
		}	
	}
	if(isNaN(res)==true||isNaN(parseFloat(res).Fixed(1))==true){
		result.innerHTML="&nbsp;"
	}
	return true;
}

function getDetract (divisor,dividend,result_id,flag) {
		var result = get(result_id);
		divisor.value=(divisor.value==""?"0":divisor.value)
		dividend.value==(dividend.value==""?"0":dividend.value)
		if (!checkNum(divisor,"请填写正整数！")) {
			divisor.focus();
			return false;
		}
		if (!checkNum(dividend,"请填写正整数！")) {
			dividend.focus();
			return false;
		}		
		if(flag){
			if(!canNotExcess(dividend,divisor,flag)){
				return false;
			}
		}else{
			if(!canNotExcess2(dividend,divisor,flag)){
				return false;
			}
		}
	var res = 0;

	res = parseFloat(divisor.value)-parseFloat(dividend.value);
	if (result.tagName.toLowerCase()=="input") {
		result.value = parseFloat(res).Fixed(1);
	} else if (result.tagName.toLowerCase()=="td") {
		result.innerHTML = parseFloat(res).Fixed(1);
	} else {
		
	}
	return true;
}

function getDetract_TD (divisor,dividend,result_id,flag) {
	var res = 0;
	var result = get(result_id);
	if(dividend.innerText==''){
		dividend.innerText=0;
	}
	if(divisor.innerText==''){
		divisor.innerText=0;
	}
	if ((divisor.innerText != "&nbsp;" || dividend.innerText != "&nbsp;") ||
		(divisor.innerText != "" || dividend.innerText != "")) {
		res = parseFloat(divisor.innerText)-parseFloat(dividend.innerText);
	}
	result.innerHTML = parseFloat(res).Fixed(1);
	return true;
}
function getDetract_TD_SF (divisor,dividend,result_id,flag) {
	var res = 0;
	var result = get(result_id);
	if(dividend.value==''){
		dividend.value=0;
	}
	if(divisor.value==''){
		divisor.value=0;
	}
	if ((divisor.value != "&nbsp;" || dividend.value != "&nbsp;") ||
		(divisor.value != "" || dividend.innerText != "")) {
		res = parseFloat(divisor.value)-parseFloat(dividend.value);
	}
	result.innerHTML = parseFloat(res).Fixed(1);
	return true;
}
function sumSons_TD(divisor,result){
	var sum = 0;
	for (var i=0; i < 9; i++) {
		sum += parseFloat(document.all.illegalBuildGetting+'_'+i.innerText);
	}
	//result='document.all.'+result;
	
	//document.getElementById(illegalBuildGettingAll).innerText = 1;
	document.all.illegalBuildGettingAll.innerText=sum;
	return true;
}
function canNotExcess2(obj,excess,flag) {
	var excessValue = 0;	
	var value = 0;
	if(excess.tagName.toLowerCase()=="input") {
		excessValue = parseFloat(excess.value);
	} else if(excess.tagName.toLowerCase()=="td") {
		excessValue = parseFloat(excess.innerHTML);
	}
	if(obj.tagName.toLowerCase()=="input") {
		if (obj.value == ""){
			obj.value = "0";
			return false;
		}
		value = parseFloat(obj.value);
	} else if(obj.tagName.toLowerCase()=="td") {
		if (obj.innerHTML == ""){
			obj.innerHTML = "0";
			return false;
		}
		value = parseFloat(obj.innerHTML);
	}
	return true;
}
function showMDialog(url,width,height)
{
	res = showModalDialog(url, window, 'Dialogwidth:' + width + 'px;Dialogheight:' + height + 'px;status:no;help:no;resizable:no');
	if(res){
		Frame.submit();
		return true;
	}
}
function sumSonInnerHTML() {
	var sum_bt = 0;
	var sum_btg = 0;
	var sum_wdw = 0;
	var sum_gpt = 0;
	var sum_pgp = 0;
	var sum_cgp = 0;
	var res = arguments[arguments.length-1];
	for (var i=0; i < arguments.length-1; i++) {
		if (getAll("bt")[arguments[i]].children[0]) {
			sum_bt += parseFloat(getAll("bt")[arguments[i]].children[0].innerHTML);
		} else {
			if (getAll("bt")[arguments[i]].innerHTML!="&nbsp;") {
				sum_bt += parseFloat(getAll("bt")[arguments[i]].innerHTML);
			}
		}
		if (getAll("btg")[arguments[i]].children[0]) {
			sum_btg += parseFloat(getAll("btg")[arguments[i]].children[0].innerHTML);
		} else {
			if (getAll("btg")[arguments[i]].innerHTML!="&nbsp;") {
				sum_btg += parseFloat(getAll("btg")[arguments[i]].innerHTML);
			}
		}
//		if (getAll("tt")[arguments[i]].innerHTML!="&nbsp;") {
//			sum_tt += parseFloat(getAll("tt")[arguments[i]].innerHTML);
//		}
//		if (getAll("g")[arguments[i]].innerHTML!="&nbsp;") {
//			sum_g += parseFloat(getAll("g")[arguments[i]].innerHTML);
//		}
//		if (getAll("w")[arguments[i]].innerHTML!="&nbsp;") {
//			sum_w += parseFloat(getAll("w")[arguments[i]].innerHTML);
//		}
//		if (getAll("gd")[arguments[i]].innerHTML!="&nbsp;") {
//			sum_gd += parseFloat(getAll("gd")[arguments[i]].innerHTML);
//		}
//		if (getAll("sm")[arguments[i]].innerHTML!="&nbsp;") {
//			sum_sm += parseFloat(getAll("sm")[arguments[i]].innerHTML);
//		}
		if (getAll("wdw")&&getAll("wdw")[arguments[i]].children[0]) {
			sum_wdw += parseFloat(getAll("wdw")[arguments[i]].children[0].innerHTML?getAll("wdw")[arguments[i]].children[0].innerHTML:0);
		} else {
			if (getAll("wdw")&&getAll("wdw")[arguments[i]].innerHTML!="&nbsp;") {
				sum_wdw += parseFloat(getAll("wdw")[arguments[i]].innerHTML?getAll("wdw")[arguments[i]].innerHTML:0);
			}
		}
		if (getAll("gpt")&&getAll("gpt")[arguments[i]].children[0]) {
			sum_gpt += parseFloat(getAll("gpt")[arguments[i]].children[0].innerHTML?getAll("gpt")[arguments[i]].children[0].innerHTML:0);
		} else {
			if (getAll("gpt")&&getAll("gpt")[arguments[i]].innerHTML!="&nbsp;") {
				sum_gpt += parseFloat(getAll("gpt")[arguments[i]].innerHTML?getAll("gpt")[arguments[i]].innerHTML:0);
			}
		}
		if (getAll("pgp")&&getAll("pgp")[arguments[i]].children[0]) {
			sum_pgp += parseFloat(getAll("pgp")[arguments[i]].children[0].innerHTML?getAll("pgp")[arguments[i]].children[0].innerHTML:0);
		} else {
			if (getAll("pgp")&&getAll("pgp")[arguments[i]].innerHTML!="&nbsp;") {
				sum_pgp += parseFloat(getAll("pgp")[arguments[i]].innerHTML?getAll("pgp")[arguments[i]].innerHTML:0);
			}
		}
		if (getAll("cgp")&&getAll("cgp")[arguments[i]].children[0]) {
			sum_cgp += parseFloat(getAll("cgp")[arguments[i]].children[0].innerHTML?getAll("cgp")[arguments[i]].children[0].innerHTML:0);
		} else {
			if (getAll("cgp")&&getAll("cgp")[arguments[i]].innerHTML!="&nbsp;") {
				sum_cgp += parseFloat(getAll("cgp")[arguments[i]].innerHTML?getAll("cgp")[arguments[i]].innerHTML:0);
			}
		}
	}
	var tradeType = res;
	if (res == "6") {
		tradeType = 45;
	} else if (res == "6_4") {
		tradeType = 46;
	} else if (res == "7") {
		tradeType = 47;
	} else if (res == "t") {
		tradeType = 48;
	}
	tradeType = this.tradeType == 0 ? tradeType : this.tradeType;
	if (isPrint == false && (res == "6" || res == "6_4" || res == "7" || res =="t")) {
		get("bt_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",1);'>"+(sum_bt==0?"&nbsp;":sum_bt)+"</a>";
		get("btg_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",2);'>"+(sum_btg==0?"&nbsp;":sum_btg)+"</a>";
		get("btng_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",3);'>"+((sum_bt-sum_btg==0)?"&nbsp":(sum_bt-sum_btg))+"</a>";
		
		if(get("wdw_"+res))get("wdw_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",4);'>"+(sum_wdw==0?"&nbsp;":sum_wdw)+"</a>";
		if(get("gpt_"+res))get("gpt_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",5);'>"+(sum_gpt==0?"&nbsp;":sum_gpt)+"</a>";
		if(get("pgp_"+res))get("pgp_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",6);'>"+((sum_pgp==0)?"&nbsp":(sum_pgp))+"</a>";
		if(get("cgp_"+res))get("cgp_"+res).innerHTML = "<a href='#' onclick='openWindow("+tradeType+",7);'>"+((sum_cgp==0)?"&nbsp":(sum_cgp))+"</a>";
	} else {
		get("bt_"+res).innerHTML = sum_bt==0?"&nbsp;":sum_bt;
		get("btg_"+res).innerHTML = sum_btg==0?"&nbsp;":sum_btg;
		get("btng_"+res).innerHTML = (sum_bt-sum_btg==0)?"&nbsp":(sum_bt-sum_btg);
		
		if(get("wdw_"+res))get("wdw_"+res).innerHTML = sum_wdw==0?"&nbsp;":sum_wdw;
		if(get("gpt_"+res))get("gpt_"+res).innerHTML = sum_gpt==0?"&nbsp;":sum_gpt;
		if(get("pgp_"+res))get("pgp_"+res).innerHTML = sum_pgp==0?"&nbsp;":sum_pgp;
		if(get("cgp_"+res))get("cgp_"+res).innerHTML = sum_cgp==0?"&nbsp;":sum_cgp;
	}
	get("bt_r_"+res).innerHTML = (sum_bt==0||sum_btg==0)?"&nbsp;":parseFloat((sum_btg/sum_bt)*100).Fixed(1);
	return true;
}
//去首尾空格函数
function trimAll(data)
{
  var reg=/^ +| +$/g;
  var str=data.replace(reg,"");
  return str;
}

//判断是否为空：
function chkNull(data,msg)
{
	if (trimAll(data.value)=='')
	{
		alert(msg);
		//data.select();
		data.focus();
		return false;
	}
	return true;
}
function tableToExcel() { 
    for(var i=0;i<document.all.tb.rows.length;i++){
	   // document.all.tb.rows(i).deleteCell(6);
	}
    window.clipboardData.setData("Text",document.all('tb').outerHTML);
    document.execCommand("Refresh");
	try
	{
		var ExApp = new ActiveXObject("Excel.Application");
        ExApp.Caption='企业详细信息报表';
		ExApp.StandardFont='宋体';
		var ExWBk = ExApp.workbooks.add();
		var ExWSh = ExWBk.worksheets(1);
		ExApp.DisplayAlerts = false;
		ExApp.visible = true;
	}  
	catch(e)
	{
		alert("请检查Microsoft Excel软件有无安装或浏览器ActiveX是否被禁止！")
		return false
	} 
	 ExWBk.worksheets(1).Paste;	
}

function sumEspecial(sum,son) {
	if(!sum || !son || son=="")
		return;
	var num = '0';
	if (sum.innerHTML) {
		if (son.innerHTML) {
			num = (((sum.innerHTML==""||sum.innerHTML=="&nbsp;")?0:parseFloat(sum.innerHTML))
				+ ((son.innerHTML==""||son.innerHTML=="&nbsp;")?0:parseFloat(son.innerHTML))).Fixed(2);
		} else if (son.value) {
			num = (((sum.innerHTML==""||sum.innerHTML=="&nbsp;")?0:parseFloat(sum.innerHTML))
				+ son.value==""?0:parseFloat(son.value)).Fixed(2);
		}
		sum.innerHTML = num=='0'?'&nbsp;':num;
	} else if (sum.value) {
		if (son.innerHTML) {
			num = (sum.value==""?0:parseFloat(sum.value)
				+ ((son.innerHTML==""||son.innerHTML=="&nbsp;")?0:parseFloat(son.innerHTML))).Fixed(2);
		} else if (son.value) {
			num = ((sum.value==""?0:parseFloat(sum.value))
				+ (son.value==""?0:parseFloat(son.value))).Fixed(2);
		}
		sum.value = num;
	}
}