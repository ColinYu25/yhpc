// 综合安监-电子地图js特效

var mkrTool = "";
var opts = "";
//创建和初始化地图函数：
function initMap(){
    createMap();//创建地图
    setMapEvent();//设置地图事件
    addMapControl();//向地图添加控件
//	addPointControl();
	
	//创建地址解析的实例
	window.myGeo = new BMap.Geocoder();
}

function enterpriseMarkerTool(markerName,markerLink,index) {
	mkrTool = new BMapLib.MarkerTool(map, {autoClose: true});
	mkrTool.addEventListener("markend", function(evt){ 
		$("markerLng").value = evt.marker.point.lng;
		$("markerLat").value = evt.marker.point.lat;
		$('markerForm').send(); //保存企业标注信息到数据库中
    	var point = new BMap.Point(evt.marker.point.lng,evt.marker.point.lat);//定义一个坐标
    	addMarker(markerName, markerLink, point, index); //在地图上标记
	});
}

function governmentMarkerTool() {
	mkrTool = new BMapLib.MarkerTool(map, {autoClose: true});
	mkrTool.addEventListener("markend", function(evt){ 
		var mkr = evt.marker;
		var actionUrl="loadCompanysByMarker.xhtml?markerType=company&markerLng=" + evt.marker.point.lng + "&markerLat=" + evt.marker.point.lat;
		loadWindow('请选择要标记的企业',100,80,790,450,actionUrl,'marker-window');
		curMkr = mkr;
	});
}
//创建地图函数：
function createMap(){
    map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
    var point = new BMap.Point(121.554285,29.881501);//定义一个中心点坐标
    map.centerAndZoom(point,15);//设定地图的中心点和坐标并将地图显示在地图容器中
    window.map = map;//将map变量存储在全局
	//var city=new BMap.LocalSearch("宁波市",{renderOptions:{map:map,panel:'txtResult',selectFirstResult:true,autoViewport:true}});
	//city.search("苍水街");
}

//地图事件设置函数：
function setMapEvent(){
    map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
    map.enableScrollWheelZoom();//启用地图滚轮放大缩小
    map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
    map.enableKeyboard();//启用键盘上下左右键移动地图
}

//地图控件添加函数：
function addMapControl(){
    //向地图中添加缩放控件
var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
map.addControl(ctrl_nav);
    //向地图中添加缩略图控件
var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
map.addControl(ctrl_sca);
}

//地图控件添加标注：
function addPointControl(){
	var point = new BMap.Point(121.554285,29.881501);  
	map.centerAndZoom(point, 15);  
	var marker = new BMap.Marker(point);        // 创建标注  
	map.addOverlay(marker);       
	marker.addEventListener("click", function(){  
	alert("创建标注！"); 
	var point2 = new BMap.Point(121.592827,29.854094);  
	addMarker(point2, 1);
	}); 
	marker.enableDragging(true);  
	marker.addEventListener("dragend", function(e){  
	 alert("当前位置：" + e.point.lng + ", " + e.point.lat);  
	}) 
}

// 编写自定义函数，创建标注  
function addMarker(markerName, markerLink, point, index){  
	map.clearOverlays(); //清除地图上所有标记
	// 创建标注对象并添加到地图  
	var myIcon = createIcon(index);
 	marker = new BMap.Marker(point, {icon: myIcon});  
 	var label = new BMap.Label(markerName,{"offset":new BMap.Size(30,-10)});
    marker.setLabel(label);
 	map.addOverlay(marker);  
 	label.setStyle({
                    borderColor:"#808080",
                    color:"#333",
                    cursor:"pointer"
    });
 	var width = 570;
	var height = 500;
 	openInfoWindowByCompany(markerName,markerLink,point.lng,point.lat,width,height,1);
 	 (function(){
			    var _iw = createInfoWindow(markerLink, width, height);
			    window._marker = marker;
			    _marker.addEventListener("click",function(){
			        this.openInfoWindow(_iw);
			       });
			       _iw.addEventListener("open",function(){
			        _marker.getLabel().hide();
			       })
			       _iw.addEventListener("close",function(){
			        _marker.getLabel().show();
			       })
			      label.addEventListener("click",function(){
			        _marker.openInfoWindow(_iw);
			       })
			   })()
}

//查询列表，点击企业名称后定位并打开信息窗口。
function openInfoWindowByCompany(markerName,markerLink,markerLng,markerLat,width,height,index) {
	var _iw = createInfoWindow(markerLink, width, height);
	// 创建标注对象并添加到地图  
	var myIcon = createIcon(index);
	var point = new BMap.Point(markerLng,markerLat);
	var marker = new BMap.Marker(point, {icon: myIcon});
	var label = new BMap.Label(markerName,{"offset":new BMap.Size(30,-10)});
    marker.setLabel(label);
	map.addOverlay(marker);  
	marker.openInfoWindow(_iw);
	window._marker = marker;
    _marker.addEventListener("click",function(){
        this.openInfoWindow(_iw);
       });
       _iw.addEventListener("open",function(){
        _marker.getLabel().hide();
       })
       _iw.addEventListener("close",function(){
        _marker.getLabel().show();
       })
      label.addEventListener("click",function(){
        _marker.openInfoWindow(_iw);
       })
}

 //创建InfoWindow
function createInfoWindow(markerLink, width, height){
	opts = {
		  width : width,     // 信息窗口宽度
		  height: height,     // 信息窗口高度
		  title : ""  // 信息窗口标题
	}
	//拼接infowindow内容字串
	var html = [];
	html.push("<iframe src='" + markerLink + "' width=" + width + " height=" + height +"/>");
	var iw = new BMap.InfoWindow(html.join(""), opts);
    return iw;
}
//创建一个Icon
function createIcon(index){
    // 创建图标对象  
	var icon = new BMap.Icon("../resources/default/img/map/map_point.png", new BMap.Size(26, 32));    
        return icon;
    }
   
	//地址解析的函数
function fun_geocoder_getPoint(){
	map.clearOverlays(); //清除地图上所有标记
	var value_address_search = document.getElementById("address_search").value;
	if (value_address_search == '') {
		alert("请输入地名，大厦快速定位");
		document.getElementById("address_search").focus();
		return false;
	}
 	myGeo.getPoint(value_address_search, function(point){
 	if (point) {
  		map.centerAndZoom(point, 15);
   		map.addOverlay(new BMap.Marker(point));
    	}else{alert("抱歉,在宁波市范围内未找到相关地点!");}
		}, "宁波市");
}

function input_focus(c, a, b) {
	if (!b) {
		b = ""
	}
	c.style.color = "#000";
	(c.value != a) ? null : c.value = b
}
function input_blur(c, a, b) {
	c.style.color = "#8c8c8c";
	(c.value == b || c.value.length <= 0) ? c.value = a : null
}

//选择样式
function selectStyle(index){
	map.clearOverlays(); //清除地图上所有标记
    mkrTool.open(); //打开工具 
    var icon = BMapLib.MarkerTool.SYS_ICONS[index]; //设置工具样式，使用系统提供的样式BMapLib.MarkerTool.SYS_ICONS[0] -- BMapLib.MarkerTool.SYS_ICONS[23]
	mkrTool.setIcon(icon); 
}

//弹出窗口公共方法
function loadWindow(title,left,top,width,height,adr,name){
		var winNr=WinLIKE.searchwindow("loadwindow");
		if(winNr!=undefined&&typeof(winNr)!="undefined"){
			WinLIKE.windows[winNr].close(); 	
		}
		var j=new WinLIKE.window("<font style='font-weight:800;color:#0000FF;font-size:12px;line-height:18px;font-family:Arial;'>"+title+"</font",left,top,width,height);
		j.Min=true;
		j.Siz=false;
		if(name=='autoWarnWindow'){
			j.Ski='light';
		}else{
			j.Ski='light';
		}
		j.Adr=adr; 
		j.LD=false;
		if(arguments.length>6){
			j.Nam=name;
		}else{
			j.Nam='default-window';
		}	
		WinLIKE.createwindow(j,true);
}

function closeWindow(window){
 		var winNr=WinLIKE.searchwindow(window);
  		WinLIKE.windows[winNr].close(); 
}

function loadMapMarkers(what,markerType,markerName,firstArea,secondArea,thirdArea,tradeTypes1,tradeTypes2,tradeTypes3) {
	jQuery.ajax({
			   	url: "${contextPath}/ajax/loadMapMarkers.xhtml",
			   	data: "company.companyName=" + markerName +
			   		  "&company.firstArea=" + firstArea +
			   		  "&company.secondArea=" + secondArea +
			   		  "&company.thirdArea=" + thirdArea +
			   		  "&company.tradeTypes=" + tradeTypes1 +
			   		  "&company.tradeTypes=" + tradeTypes2 +
			   		  "&company.tradeTypes=" + tradeTypes3,
			   	type: "POST",
			   	dataType:"json",
			   	success: function(data){ //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据
		   			var member = eval("("+data+")"); //包数据解析为json 格式 	  
		   			//先删除地图上所有的标记
					map.clearOverlays(); 
		   			//再将查询出的结果标记到地图上
		   			for(var i=0; i<member.length; i++){
		   				if(markerType==member[i].markerType || markerType=="all"){
		   					var point = new BMap.Point(member[i].markerLng,member[i].markerLat);
		   					addMarker(member[i].markerName, member[i].markerLink, point, i);
		   				}
		   			}
		   		}
		});
}

//重新标注
function rePoint(markerId, index) {
	if (confirm("确定要在地图上重新标注贵公司所在位置吗？")) {
		deleteMarker(markerId); //先删除原来标准的位置
		selectStyle(index); //调用标注样式
		//手动点击地图进行标注
	}
}

//默认打开已标注过的企业信息窗口
function popMarker(markerName,markerId,type){
	var request = new Request.JSON({
	  		url:'loadMapMarker.xhtml?mapMarker.markerType='+type+'&mapMarker.markerId='+markerId,
	  		onComplete: function(response) { 
				if(response!=null){
					 var markerLink = "http://60.190.2.104/nbajj_colligation/companyInfo/loadCompanyInfo.xhtml?company.id=" + markerId;
					 openInfoWindowByCompany(markerName,markerLink,response.markerLng,response.markerLat,570,500,1);
				}else{
					alert("请在地图上标注贵公司的位置!");
					return;
				}
	        }
	}).send(); 
}

//删除标注
function deleteMarker(markerId) {
	var request = new Request.JSON({
  		url:'deleteMarker.xhtml?mapMarker.id=' + markerId + '&mapMarker.markerType=company',
  		onComplete: function(response) { //没有设置返回值
        }
	}).send(); 
}