function get() {
	if (document.all) {
		return document.all(arguments[0]);
	} else {
		return document.getElementById(arguments[0]);
	}
}
function getTag() {
	return document.getElementsByTagName(arguments[0]);
}
function getName() {
	if(document.getElementsByName(arguments[0]).length) {
		return document.getElementsByName(arguments[0]);
	} else {
		return document.getElementsByName(arguments[0])[0];
	}
}
function createEle() {
	return document.createElement(arguments[0]);
}
function trim(data){
  var reg=/^ +| +$/g;
  var str=data.replace(reg,"");
  return str;
}
function loadWindows(hre,w,h,l,t,win_n,win_t){
	var win = WinLIKE.searchwindow(win_n);
	if(win && win != undefined && typeof(win) != "undefined") {
		WinLIKE.windows[win].close();
	}
	var default_wn = 'win_default';
	var default_w = 450;
	var default_h = 220;
	var default_l = 0;
	var default_t = 0;
	var default_wt = (win_t)?'<font size="2">'+win_t+'</font>':'<font size="2">新增页</font>';
	var j=new WinLIKE.window(default_wt,(l&&l!=0)?l:default_l,(t&&t!=0)?t:default_t,(w&&w!=0)?w:default_w,(h&&h!=0)?h:default_h);
	j.Min=false;
	j.Siz=false;
	j.Adr=hre; 
	j.LD=false;
	j.SD=false;
	j.Nam=(win_n)?win_n:default_wn;
	WinLIKE.createwindow(j,true);
}

function loadWindowsOfNoClose(hre,w,h,l,t,win_n,win_t){
	var win = WinLIKE.searchwindow(win_n);
	if(win && win != undefined && typeof(win) != "undefined") {
		WinLIKE.windows[win].close();
	}
	var default_wn = 'win_default';
	var default_w = 450;
	var default_h = 220;
	var default_l = 0;
	var default_t = 0;
	var default_wt = (win_t)?'<font size="2">'+win_t+'</font>':'<font size="2">新增页</font>';
	var j=new WinLIKE.window(default_wt,(l&&l!=0)?l:default_l,(t&&t!=0)?t:default_t,(w&&w!=0)?w:default_w,(h&&h!=0)?h:default_h);
	j.Min=false;
	j.Siz=false;
	j.Adr=hre; 
	j.LD=false;
	j.SD=false;
	j.Cls=false;
	j.Nam=(win_n)?win_n:default_wn;
	WinLIKE.createwindow(j,true);
}

function loadWindowsHeight(hre,w,h,l,t,win_n,win_t){
	var height = document.body.clientHeight;
	if(height==455)
		height=485;
	height = height-480;
	loadWindows(hre,w,h,l,height,win_n,win_t);
}
function closeWindows()  {
	var win = window.parent.WinLIKE.searchwindow(arguments[0]);
	window.parent.WinLIKE.windows[win].close();
}

function cleanSelect(obj) {
	if (obj) {
		for(var i=obj.options.length-1; i>0; i--) {
			obj.options[i] = null;
		}
	}
}

function guidang () {
	if(!chooseCheckBox())
		return false;
	var now = getNowDate('mm');
	var checkboxs = getTag('input');
	var j=0;
	for(var i=0;i<checkboxs.length; i++) {
		if(checkboxs[i].type=='checkbox'){
			j ++;
			if(checkboxs[i].checked) {
				if(get('td_s_'+j))
					get('td_s_'+j).innerHTML = '已归档';
				if(get('td_d_'+j))
					get('td_d_'+j).innerHTML = now;	
				checkboxs[i].checked = !checkboxs[i].checked;
			}
		}
	}
	alert('归档成功！');
}

function chooseCheckBox() {
	var checkboxs = getTag('input');
	var noChoose = true;
	var checkSize = 0;
	for(var i=0;i<checkboxs.length; i++) {
		if(checkboxs[i].type=='checkbox' && checkboxs[i].name=='ids'){
			if(checkboxs[i].checked) {
				noChoose = false;
				checkSize ++;
			}
		}
	}
	if(noChoose) {
		alert('请先选择记录再进行操作！');
		return false;
	}
	if(arguments[0] && arguments[0] != checkSize){
		alert('请选择'+arguments[0]+'条记录进行操作！');
		return false;
	}
	return true;
}

function chooseCheckBox1() {
	var checkboxs = getTag('input');
	var noChoose = true;
	var checkSize = 0;
	for(var i=0;i<checkboxs.length; i++) {
		if(checkboxs[i].type=='checkbox' && checkboxs[i].name=='company.tradeTypes'){
			if(checkboxs[i].checked) {
				noChoose = false;
				checkSize ++;
			}
		}
	}
	//if(noChoose) {
		//alert('请先选择企业的行业！');
		//return false;
	//}
	if(checkSize>=2){
		alert('企业只能选择一个行业！');
		return false;
	}
	return true;
}

function getNowDate() {
	var param = 'dd';
	if (arguments[0] && arguments[0] != '' && (arguments[0]=='yy'||arguments[0]=='MM'||arguments[0]=='dd'||arguments[0]=='hh'||arguments[0]=='mm'||arguments[0]=='ss'))
		param = arguments[0];
	var now = ((param=='yy'||param=='MM'||param=='dd'||param=='hh'||param=='mm'||param=='ss')?(new Date()).getYear()+((param=='MM'||param=='dd'||param=='hh'||param=='mm'||param=='ss')?'-'+((((new Date()).getMonth()+1)<=9)?('0'+((new Date()).getMonth()+1)):((new Date()).getMonth()+1))+((param=='dd'||param=='hh'||param=='mm'||param=='ss')?'-'+(((new Date()).getDate()<=9)?('0'+(new Date()).getDate()):(new Date()).getDate())+((param=='hh'||param=='mm'||param=='ss')?' '+(((new Date()).getHours()<=9)?('0'+(new Date()).getHours()):(new Date()).getHours())+((param=='mm'||param=='ss')?':'+(((new Date()).getMinutes()<=9)?('0'+(new Date()).getMinutes()):(new Date()).getMinutes())+((param=='ss')?(':'+((new Date().getSeconds()<=9)?('0'+new Date().getSeconds()):new Date().getSeconds())):''):''):''):''):''):(new Date()));
	return now;
}

function checkTextAreaSize(obj,size) {
	if(obj.value.length > size) {
		alert("此文本仅允许输入"+size+"个汉字！");
		obj.value = obj.value.substring(0,size);
		return false;
	}
}

//限制文本输入长度
//onpropertychange="checkTextSize(this,2000,1);"
function checkTextSize(obj,len,isChar) {
    var m = obj.value.trueLength(isChar);
    if (m > len) {
	    for(i = obj.value.length; i > 0; i--) {
	    	if (obj.value.substring(0,i).trueLength(isChar) <= len){
	    		break;
	    	}
	    }
	    obj.value = obj.value.substring(0,i);	   
    }
     get(obj.id+"_1").innerHTML=m+"/"+len;
}

String.prototype.trueLength = function(isChar){
	if(isChar == 1){
		return this.replace(/[^\x00-\xff]/g,"**").length;//转化为字符
	}else{
		return this.length;
	}
}
function roleName(){
	var roleName = document.getElementsByName(arguments[0]);
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
}
//去空格/全角转半角; 
//onBlur="SpacesAll(this);"
function SpacesAll(Str){
    var ResultStr = "";
    Temp=Str.value.split(" "); //双引号之间是个半角空格；
    for(i = 0; i < Temp.length; i++)
    ResultStr +=Temp[i];
	
    Temp=ResultStr.split("　"); //双引号之间是个全角空格；
    ResultStr =  "";      //全角时定义初始值为空
    for(i = 0; i < Temp.length; i++)
    ResultStr +=Temp[i];
    Str.value=ResultStr;  //得到去除空格后的值 
	
	var stro=Str.value;  //全角转半角
	var result="";
	for (var i = 0; i < stro.length; i++){
		if (stro.charCodeAt(i)==12288){
			result+= String.fromCharCode(stro.charCodeAt(i)-12256);
			continue;
		}
		if (stro.charCodeAt(i)>65280 && stro.charCodeAt(i)<65375)
			result+= String.fromCharCode(stro.charCodeAt(i)-65248);
		else result+= String.fromCharCode(stro.charCodeAt(i));
	} 
	Str.value=result;  //得到全角转半角后的值
}

	function checkedOne(obj,objName){
       var boxArray = getName(objName);
       for(var i=0;i<=boxArray.length-1;i++){
            if(boxArray[i]==obj && obj.checked){
               boxArray[i].checked = true;
            }else{
               boxArray[i].checked = false;
            }
       }
    }

Number.prototype.Fixed = function(n){   
      with(Math){   
          var tmp=pow(10,n);   
          return round(this*tmp)/tmp;   
      }   
} 


function deleteNote() {
	if(chooseCheckBox()) {
		if(window.confirm('确定删除吗？')) {
			if(arguments[1])
				arguments[0].action=arguments[1];
			if(arguments[0].action != null)
				arguments[0].submit();
		}
	}
}

function loadNote(url) {
	if(chooseCheckBox(1)){
		if (url=="delCompanyHy.xhtml?company.id"){
			if(window.confirm('是否确定删除？')){
				window.location = url+'='+getCheckBoxValue();
				alert("删除成功");
	            return true;
	         }else{
	            return false;
	        }
		}else{
			window.location = url+'='+getCheckBoxValue();
		}
		
	}
}

function getCheckBoxValue() {
	if(chooseCheckBox(1)){
		var id = "";
		for(var i=0;i<getTag("input").length; i++) {
			if(getTag("input")[i].type=='checkbox'){
				if(getTag("input")[i].checked) {
					id = getTag("input")[i].value;
				}
			}
		}
		return id;
	}
	return false;
}

function AutoFillDate(CurrDate,DateTypeLength)
{ 	
	var formatStyle = '-';
	if (!(window.event.keyCode == 8))
	{
		if(CurrDate.value.length == 4) 
			CurrDate.value += formatStyle;

		if(CurrDate.value.length == 5 || CurrDate.value.length == 8) 	
		{
			if(CurrDate.value.charAt(CurrDate.value.length - 1) != formatStyle) 
				CurrDate.value = CurrDate.value.substring(0,CurrDate.value.length - 1) + formatStyle;
		}
		if (CurrDate.value.charAt(5) == formatStyle || CurrDate.value.charAt(8) == formatStyle)
		{
			CurrDate.value = CurrDate.value.substring(0,CurrDate.value.length - 1);
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
		if (CurrDate.value.length > DateTypeLength) 
		{
			CurrDate.value = CurrDate.value.substring(0,DateTypeLength);
		}
	
	}
}
//将出生日期转化为年龄
function dateToAge(str){   
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
	if(r==null)return null;     
		var d= new Date(r[1],r[3]-1,r[4]);     
	    if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]){   
	    	var Y = new Date().getFullYear();
	    	if(Y-r[1]>0){
	    		return(Y-r[1]);
	    	}   
	        return null;
	    }   
}

function readonly(objs, converse){
	for(var i = 0 ; i < objs.length ; i ++){
		var obj = get(objs[i]);
		if(!obj){
			continue;
		}
		if (!obj.length) {
			var tagName = obj.tagName;
			if(tagName){
				var type = "";
				if (obj.type) {
					type = obj.type.toLowerCase();
				}
				tagName = tagName.toLowerCase();
				if(tagName == "input" || tagName == "textarea"){
					if(type == "submit" || type == "button"){
						if(converse){
							obj.style.display = "";
						}else{
							obj.style.display = "none";
						}
					}else if (type == "file" || type == "radio" || type == "checkbox") {
						obj.disabled = !converse;
					}else if(type == "image") {
						obj.onclick = function(){};
					}else{
						obj.readOnly = !converse;
					}
				}else if(tagName == "select"){
					obj.disabled = !converse;
				}else if(tagName == "a" || tagName == "img") {
					if (obj.location)
						obj.location.href = "#";
					obj.onclick = function (){};
				}else{
					
				}
			}
		} else {
			for (var j=0; j<obj.length;j++) {
				var tagName = obj[j].tagName;
				if(tagName){
					var type = "";
					if (obj[j].type) {
						type = obj[j].type.toLowerCase();
					}
					tagName = tagName.toLowerCase();
					if(tagName == "input" || tagName == "textarea"){
						if(type == "submit" || type == "button"){
							if(converse){
								obj[j].style.display = "";
							}else{
								obj[j].style.display = "none";
							}
						}else if (type == "file" || type == "radio" || type == "checkbox") {
							obj[j].disabled = !converse;
						}else if(type == "image") {
							obj[j].onclick = function(){};
						}else{
							obj[j].readOnly = !converse;
						}
					}else if(tagName == "select"){
						obj[j].disabled = !converse;
					}else if(tagName == "a" || tagName == "img") {
						if (obj[j].location)
							obj[j].location.href = "#";
						obj[j].onclick = function (){};
					}else{
						
					}
				}
			}
		}
		
	}
}

function temp_img_save() {
	if(get("img_save")) {
		if(get("img_save").children[0].location)
			get("img_save").children[0].location.href = "#";
		if(get("img_save").children[0].href)
			get("img_save").children[0].href = "#";
		get("img_save").children[0].onclick = function() {
			if(submitCreate())
				get("img_save").children[0].onclick = function(){};
		};
	}
}

/**
 * 关于与My97DatePicker日历空间结合
 */
 
 function WDatePickerNow() {
 	WdatePicker({minDate:'2000-01-01',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H-%m',errDealMode:1})
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
function checkNumber(obj, type) {//type=1代表正整数；type=2代表整数；type=3代表正数；type=4代表数字
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
			if (type==3)
				return (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 46;
			else 
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
