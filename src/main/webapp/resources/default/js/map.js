// 综合安监-电子地图js特效
//头部输入框得到焦点控制
$(function(){
$(".input_search").focus(function(){
   $(this).addClass("focus");
   {if(this.value=='请输入地名，大厦快速定位') this.value='';}
   
}).blur(function(){
   $(this).removeClass("focus");
    {if(this.value=='') this.value='请输入地名，大厦快速定位'}
     });
});

//左地图与右列表box切换控制
$(function(){
  $(".arrow").toggle(
	  function () {
		$(this).children().attr("src","../resources/default/img/map/arrow.png");
		$("#right_box").animate({ right: -260,opacity: 1}, 10).hide(200);  // 此为非框架，right_box最后必须消失掉，才能让地图实现自适应 
		$(".arrow").css("right","0")
	
	  },
	  function () {
		//$("#right_td").width(260);	
		$(this).children().attr("src","../resources/default/img/map/arrow_open.png");  	 
		$("#right_box").animate({ right: 0,opacity: 1}, 10).show(200); 
	
		$(".arrow").css("right","260")
	  }
	);

})

//头部展开与收起控制
$(function() {
	$("#top_control_arrow").toggle(
	  function () {
		$("#map_top").slideDown(500);
		$(this).children().attr("src","../resources/default/img/map/arrow_top_up.png");

	  },
	  function () {
		$("#map_top").slideUp(500);
		$(this).children().attr("src","../resources/default/img/map/arrow_top_down.png");
	  }
	);
});

/*搜索点击展开与收起*/
$(function(){ 
	$(".right_title").toggle(
	  function () {
	  $(this).css("background","url(../resources/default/img/map/title_bg.jpg) left -34px no-repeat");
	  $(".map_search_box").fadeIn("slow");
	  },
	  function () {
	  $(this).css("background","url(../resources/default/img/map/title_bg.jpg) left top no-repeat");
	  $(".map_search_box").fadeOut("slow");
	  }
	);
})

//列表经过背景色切换
$(function() {
$(".tab_list tr").hover(function() {
  $(this).addClass("blue");
 }, function() {
  $(this).removeClass("blue");
 });
});

//列表点击背景色改变
$(function(){ 
   var get_tr=$("#map_list tr");  //获取的到tr集
	  get_tr.click(function(){
		   $(this).addClass("selected").siblings().removeClass("selected");
	  })
})

var mkrTool = "";
var opts = "";
var zhjgUrl = "http://60.190.2.104/nbajj_colligation";
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
		$('markerForm').send(); //保存企业标记信息到数据库中
		$('markerForm').set('send', {onComplete: function(response) {
			if(response!=""){
	    	var point = new BMap.Point(evt.marker.point.lng,evt.marker.point.lat);//定义一个坐标
	    	addMarker(markerName, markerLink, point, index); //在地图上标记
			}else{
			   	 alert("标记操作失败!");	 	   	 
			}
		}});
	});
}

/*function governmentMarkerTool() {
	mkrTool = new BMapLib.MarkerTool(map, {autoClose: true});
	mkrTool.addEventListener("markend", function(evt){ 
		var mkr = evt.marker;
		var actionUrl="loadCompanysByMarker.xhtml?markerType=company&markerLng=" + evt.marker.point.lng + "&markerLat=" + evt.marker.point.lat;
		loadWindow('请选择要标记的企业',100,80,790,450,actionUrl,'marker-window');
		curMkr = mkr;
	});
}*/

function governmentMarkerTool(markerId,markerName,markerLink,index) {
	mkrTool = new BMapLib.MarkerTool(map, {autoClose: true});
	mkrTool.addEventListener("markend", function(evt){ 
		//保存企业标记信息到数据库中
		saveMarkerAndOpenWin(markerId,markerName,markerLink,evt.marker.point.lng,evt.marker.point.lat,index);
	});
}

function saveMarkerAndOpenWin(markerId,markerName,markerLink,markerLng,markerLat,index){
	jQuery.ajax({type : "post", method : "post", dataType : "json",
		data:"mapMarker.markerId=" + markerId + "&mapMarker.markerName=" + markerName + "&mapMarker.markerLng=" +
		markerLng + "&mapMarker.markerLat=" + markerLat + "&markerLink=" + markerLink + "&mapMarker.markerType=company", 
		url : "createMarker.xhtml",
		success : function(data) {//异步获取的数据
			if(data!=""){
				var point = new BMap.Point(markerLng,markerLat);//定义坐标
				addMarker(markerName, markerLink, point, index);
			}else{
			   	 alert("标记操作失败!");	 	   	 
			}
		}
	});	
}

//创建地图函数：
function createMap(){
    map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
    var point = new BMap.Point(121.554285,29.881501);//定义一个中心点坐标
    map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中
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

//地图控件添加标记：
function addPointControl(){
	var point = new BMap.Point(121.554285,29.881501);  
	map.centerAndZoom(point, 13);  
	var marker = new BMap.Marker(point);        // 创建标记  
	map.addOverlay(marker);       
	marker.addEventListener("click", function(){  
	alert("创建标记！"); 
	var point2 = new BMap.Point(121.592827,29.854094);  
	addMarker(point2, 1);
	}); 
	marker.enableDragging(true);  
	marker.addEventListener("dragend", function(e){  
	 alert("当前位置：" + e.point.lng + ", " + e.point.lat);  
	}) 
}

// 编写自定义函数，创建标记  
function addMarker(markerName, markerLink, point, index){  
	map.clearOverlays(); //清除地图上所有标记
	// 创建标记对象并添加到地图  
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
	// 创建标记对象并添加到地图  
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
		  title : "生产经营单位企业档案"  // 信息窗口标题
	}
	//拼接infowindow内容字串
	var html = [];
	html.push("<iframe src='" + markerLink + "' width='100%' height='100%'/>");
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
  		map.centerAndZoom(point, 13);
  		/*if (!validateUnique(value_address_search,point)) {}*/
  			map.addOverlay(new BMap.Marker(point));
    	}else{alert("抱歉,在宁波市范围内未找到相关地点!");}
		}, "宁波");
}

function validateUnique(searchParam, point){
	var isUnique = false;
	jQuery.ajax({type : "post", method : "post", dataType : "json",
		data:"company.companyName=" + searchParam + "&markerLng=" + point.lng + "&markerLat=" + point.lat, 
		url : "loadUniqueCompany.xhtml",
		success : function(data) {//异步获取的数据
			if(data && data.result){
				//直接调用标记方法
				var markerLink = zhjgUrl + "/companyInfo/loadCompanyInfoForZF.xhtml?company.id=" + data.markerId;
				addMarker(data.markerName,markerLink,point,1);//在地图上标记
				isUnique = true;
			}
			if (data.isLabel) { //已经标记过的企业直接打开企业信息窗口
				popMarker(data.markerName,data.markerId,"company");
				isUnique = true;
			}
		}
	});	
	return isUnique;
}

//通过地址拾取坐标
function pickup_point(address,id,companyName){
	myGeo.getPoint(address, function(point){
	 	if (point) {
	 		//自动保存标记相关信息
	 		autoSaveMarker(id,companyName,point);
	    	}
			}, "宁波市");
}

//自动保存标记相关信息
function autoSaveMarker(id,companyName,point) {
	jQuery.ajax({type : "post", method : "post", dataType : "json",
		data:"mapMarker.markerId=" + id + "&mapMarker.markerName=" + companyName + "&mapMarker.markerLng=" +
		point.lng + "&mapMarker.markerLat=" + point.lat + "&mapMarker.markerType=company", 
		url : "createMarker.xhtml",
		success : function(data) {//异步获取的数据
			
		}
	});	
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
//	map.clearOverlays(); //清除地图上所有标记
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

//重新标记
function rePoint(markerId, markerName, markerLink, index) {
	if (confirm("确定要在地图上重新标记该单位所在位置吗？")) {
		_marker.closeInfoWindow(); //关闭信息窗口
		map.removeOverlay(_marker); //删除当前标记
		deleteMarker(markerId); //先删除原来标记的位置
		governmentMarkerTool(markerId,markerName,markerLink,1);//加载政府端标记工具
		selectStyle(2); //调用标记样式
		//手动点击地图进行标记
	}
}

//删除标注
function deleteMarker(markerId) {	
	jQuery.ajax({type : "post", method : "post", dataType : "json",
		data:"mapMarker.id=" + markerId + "&mapMarker.markerType=company", 
		url : "deleteMarker.xhtml",
		success : function(data) {//异步获取的数据
			
		}
	});	
}

//打开已标记过的企业信息窗口
function popMarker(markerName,markerId,type){
//	map.clearOverlays(); //清除地图上所有标记
	var request = new Request.JSON({
	  		url:'loadMapMarker.xhtml?mapMarker.markerType='+type+'&mapMarker.markerId='+markerId,
	  		onComplete: function(response) { 
				if(response!=null){
					 var markerLink = zhjgUrl + "/companyInfo/loadCompanyInfoForZF.xhtml?company.id=" + markerId;
					 openInfoWindowByCompany(markerName,markerLink,response.markerLng,response.markerLat,570,500,1);
				}else{
					alert("对不起,该单位尚未标记过,请在地图上标记该单位的位置!");
					return;
				}
	        }
	}).send(); 
}

//默认标记搜索列表中的记录，创建标记  
function addMarkerInit(markerName, markerLink, point, index){  
	// 创建标记对象并添加到地图  
	var myIcon = createIcon(index);
 	var marker = new BMap.Marker(point, {icon: myIcon});  
 	var label = new BMap.Label(markerName,{"offset":new BMap.Size(30,-10)});
 	marker.setLabel(label);
 	map.addOverlay(marker);  
 	label.setStyle({
                    borderColor:"#808080",
                    color:"#333"
    });
 	var width = 570;
	var height = 500;
 	 (function(){
			    var _iw2 = createInfoWindow(markerLink, width, height);
			    window._marker = marker;
			    _marker.addEventListener("click",function(){
			        this.openInfoWindow(_iw2);
			       });
			       _iw2.addEventListener("open",function(){
			        _marker.getLabel().hide();
			       })
			       _iw2.addEventListener("close",function(){
			        _marker.getLabel().show();
			       })
			   })()
}

function setPlace(){// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();// 将地址解析成果显示在地图上，并调剂地图视野
	myGeo.getPoint(myValue, function(point){
	if (point) {
	 map.centerAndZoom(point, 13);
	 map.addOverlay(new BMap.Marker(point));
	  }}, "宁波");
}

function label(markerId,markerName,markerLink) {
	wBox.close(); //关闭wBox弹出框
	jQuery("#map_top").slideDown(500); //展开头部搜索框
	jQuery("#map_top").children().attr("src","../resources/default/img/map/arrow_top_up.png"); 
	governmentMarkerTool(markerId,markerName,markerLink,1);//加载政府端标记工具
	selectStyle(2);	
}

//调整地图视野
function adjustVisualField(pointArr){
	map.setViewport(pointArr);
}