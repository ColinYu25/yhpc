//-----------------------------全局变量---------------------//
var imagePath=resourcePath+"/img";
var calculationModel;
var calculationTankId;
var calculationRandom;
var mapScale;//地图比例
var flag;//模拟标记
var marker,markers,flg,map,mylonlat,xy,r1,polygonControl,largebutton,rybx;	
var measureControls;
var newModels,newModel;
var popup,rybx,isDeleteMarker;
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
/*重新初始化窗口大小*/
function resetWindow(name,left,top,width,height){
	var winNr=WinLIKE.searchwindow(name);
	var modelWindow=WinLIKE.windows[winNr];
	modelWindow.Left=left;
	modelWindow.Top=top;
	modelWindow.Width=width;
	modelWindow.Height=height;
	modelWindow.draw();
}

//----------------------------------模拟----------------------------------//

function analyseBegin(){
    	flag='analyse';
    	flg='analyse';
}

function resultInfo(event,v,tankId){	
		var winNr=WinLIKE.searchwindow("modelResult");
		if(winNr!=undefined&&typeof(winNr)!="undefined"){
			WinLIKE.windows[winNr].close(); 	
		}
		calculationModel=v;
		calculationTankId=tankId;
		calculationRandom=Math.round(Math.random()*10000)+new Date().getTime();
		var j=new WinLIKE.window('&nbsp;',0,0,320,205);
		j.Min=true;
		j.Siz=false;
		//j.Ski='Skin57';
		j.Ski='light';
		j.Adr=contextPath+'/map/loadCalculationFromCache.xhtml?companyId='+tankId; 
		j.LD=false;
		j.HD=false;
		j.SD=false;//刷新
		j.Nam='modelResult';
		WinLIKE.createwindow(j,true);
}

function newQueryWindow(event){	//拉框查询    
		var wkt_all_x = new Array();
		var wkt_all_y = new Array();
		var wkts = (event.geometry+'').replace("POLYGON((","").replace("))","").split(",");
    	for (var i = 0; i < wkts.length; i++) {
	       wkt_all_x.push(wkts[i].split(" ")[0]);
	       wkt_all_y.push(wkts[i].split(" ")[1]);
	    }
	    wkt_all_x.sort(function(a,b){return b-a;})
	    wkt_all_y.sort(function(a,b){return b-a;})
	    //alert(""+wkt_all_x[0]+""+wkt_all_x[wkt_all_x.length-1]);
	    //alert(""+wkt_all_y[0]+""+wkt_all_y[wkt_all_y.length-1]);
		var adr=contextPath+"/map/right/loadMapQuery.xhtml?mapQueryInfo.maxX="+wkt_all_x[0]+"&mapQueryInfo.maxY="+wkt_all_y[0]+"&mapQueryInfo.smallX="+wkt_all_x[wkt_all_x.length-1]+"&mapQueryInfo.smallY="+wkt_all_y[wkt_all_y.length-1]+""; 
		loadWindow('拉框查询',200,100,650,330,adr,'map-lkcx-window');
}
function newAllQueryWindow(event){	//查询
		var adr=contextPath+"/map/right/loadMapQuery.xhtml"; 
		loadWindow('查询',200,100,650,330,adr,'map-cx-window');
}
function openWindow() {
	var showWin = window.open(arguments[0], '_blank', 'width=938,height=700,left=86,top=0,resizable=yes,scrolbars=no');
	if(showWin) {
    } else {
	   alert("检测到弹出窗口阻止程序。您的 Web 浏览器必须允许该站点弹出窗口！");
    }
}
//构造弹出窗口的函数
function onFeatureSelect(lon,lat,width,height,content) {
	if(popup!=undefined&&typeof(popup)!="undefined"){
		popupClose();//同时只显示一个弹出框，如果存在先关闭.
	}
    popup = new OpenLayers.Popup.FramedCloud("chicken", 
         new OpenLayers.LonLat(lon,lat),
         new OpenLayers.Size(width,height),
         content,
         null, true);
    try
	{
    	map.addPopup(popup);
	}
    catch(err)
	{
    	//第一次执行方法有可能出错
    	map.addPopup(popup);
	}
}
function popupClose() {
	    map.removePopup(popup);
		popup.destroy();
	    popup = null;
}
function loadPopMarker(markerLink,lon,lat,width,height){//地图点击弹出层公用方法
	var html="<iframe src='"+markerLink+"' width='"+width+"' height='"+height+"' scrolling='no' frameborder='0'></iframe>";
	onFeatureSelect(lon,lat,width,height,html);//弹出层
}

var markerTankArray=new OpenLayers.Layer.Markers("全市企业");

//添加地图标记
function addMarker(markerId,markerType,markerImg,markerWidth,markerHeight,markerLink,markerX,markerY){
	var size = new OpenLayers.Size(markerWidth,markerHeight);
    var offset = new OpenLayers.Pixel(-(size.w/2), -(size.h)/2);
    var icon = new OpenLayers.Icon(resourcePath+"/img/map/"+markerImg,size,offset);
    var newMarker=new OpenLayers.Marker(new OpenLayers.LonLat(markerX,markerY),icon);
    if(markerType=="company"){
   		 markerTankArray.addMarker(newMarker);
   		 map.addLayer(markerTankArray);//添加标记到地图
    }
    markerLink=contextPath+"/"+markerLink;
    newMarker.events.register("click", newMarker, function(evt){
    			 document.onmousemove = mouseNotMove;
    			 //var lon = map.getLonLatFromPixel(new OpenLayers.Pixel(evt.x,evt.y)).lon;
    			 //var lat = map.getLonLatFromPixel(new OpenLayers.Pixel(evt.x,evt.y)).lat;
    			 if(isDeleteMarker){
    			 	return clearMarker(markerId,markerType,markerX,markerY);//删除地图标注
    			 }
		   		 if(markerType=="company"){
		   		 	if(flag=="analyse"){ 
				         createModelWindow(markerId);
				 		 flag=null;
				 	}else{
			             loadPopMarker(markerLink,markerX,markerY,560,820);//弹出层
				    }
		     	}
 	});
}
function delMarker(markerType,markerX,markerY){
	var markers;
	var markerArray;
	var i=0;
	if(markerType=="company"){
		markerArray=markerTankArray;
	}
	markers=markerArray.markers;
	while (i<markers.length) { 
            var marker = markers[i]; 
            if (marker.lonlat.lon == markerX&&marker.lonlat.lat==markerY) { 
                   markerArray.removeMarker(marker);
            } else { 
                 i=i+1; 
            } 
     } 
}
//----------------------------------marker Storage Farm end---------------------------------------------------//
function createModelWindow(companyId){
		var adr=contextPath+'/map/createModelInit.xhtml?companyId='+companyId;
		loadWindow('事故后果模拟',200,100,545,198,adr,'modelWindow');
}

function getMapScale(){//地图比例
	var scale = this.map.getScale();
    if (!scale) {
        return;
    }
	return scale;
} 	
//以下三个是关于测距的方法
function calcVincenty(geometry) {
    var dist = 0;
    for (var i = 1; i < geometry.components.length; i++) {
        var first = geometry.components[i-1];
        var second = geometry.components[i];
        dist += OpenLayers.Util.distVincenty(
            {lon: first.x, lat: first.y},
            {lon: second.x, lat: second.y}
        );
    }
    return dist;
}    

//--------------------------model  begin----------------------------------------------------//

var staticRadius,staticX,staticY,staticName;
function addModelRadius(r,markerX,markerY,names){ 
    	//map.zoomToMaxExtent();
    	var colors=new Array("blue","black","green","red");
    	staticRadius=r.split(",");
    	staticRadius.sort(function (a,b){return b-a});
    	var radiu_new=new Array(parseFloat(staticRadius[0])/parseFloat(mapScale),parseFloat(staticRadius[1])/parseFloat(mapScale),parseFloat(staticRadius[2])/parseFloat(mapScale),parseFloat(staticRadius[3])/parseFloat(mapScale));
		addModelLayer(names,radiu_new,colors,markerX,markerY);
    	staticX=markerX;staticY=markerY;staticName=names; 	
} 
function getPixel(x,y,radiu){
	 var a = map.getPixelFromLonLat(new OpenLayers.LonLat(parseFloat(x)+parseFloat(radiu),y)).x;
	 var b = map.getPixelFromLonLat(new OpenLayers.LonLat(x,y)).x;
	 return parseFloat(a)-parseFloat(b);
}
var modelLayerArray; 
function addModelLayer(name,radiu,color,x,y){
		   if(modelLayerArray!=undefined&&typeof(modelLayerArray)!="undefined"){//防止刷新模拟结果页面时重复画爆炸半径
           	   return false;
           }
           modelLayerArray=new Array();
           var	feature = new OpenLayers.Feature.Vector(
                	new OpenLayers.Geometry.Point(
                   	 x, y
               	 )
            );
           var myStyle = new OpenLayers.StyleMap({
            	"default": new OpenLayers.Style({
               	 	pointRadius: getPixel(x,y,radiu[0]*mapScale), //单位是像素
               		fillColor: color[0],
					fillOpacity:0.5,
                	strokeColor: color[0],
                	strokeWidth: 1
           		 })
        	});         
            var vector = new OpenLayers.Layer.Vector(
                 name[0], {styleMap: myStyle}
        	);
           vector.addFeatures(feature.clone());   
           
   
           modelLayerArray.push(vector);
           
           myStyle = new OpenLayers.StyleMap({
            	"default": new OpenLayers.Style({
               	 	pointRadius: getPixel(x,y,radiu[1]*mapScale), 
               		fillColor: color[1],
					fillOpacity:0.5,
                	strokeColor: color[1],
                	strokeWidth: 1
           		 })
        	});    
            vector = new OpenLayers.Layer.Vector(
                 name[1], {styleMap: myStyle}
        	);      
           vector.addFeatures(feature.clone());  	
           modelLayerArray.push(vector);

           myStyle = new OpenLayers.StyleMap({
            	"default": new OpenLayers.Style({
               	 	pointRadius: getPixel(x,y,radiu[2]*mapScale), 
               		fillColor: color[2],
					fillOpacity:0.5,
                	strokeColor: color[2],
                	strokeWidth: 1
           		 })
        	});    
            vector = new OpenLayers.Layer.Vector(
                 name[2], {styleMap: myStyle}
        	);    
        	vector.addFeatures(feature.clone());    	
           modelLayerArray.push(vector);
           
            myStyle = new OpenLayers.StyleMap({
            	"default": new OpenLayers.Style({
               	 	pointRadius: getPixel(x,y,radiu[3]*mapScale), 
               		fillColor: color[3],
					fillOpacity:0.5,
                	strokeColor: color[3],
                	strokeWidth: 1
           		 })
        	});   
            vector = new OpenLayers.Layer.Vector(
                 name[3], {styleMap: myStyle}
        	);
           vector.addFeatures(feature.clone());  
           modelLayerArray.push(vector);
		   map.addLayers(modelLayerArray);
		   
	      //var selectControl = new OpenLayers.Control.SelectFeature(modelLayerArray[1],
          //  {onSelect: selectFeature, onUnselect: selectFeature});
          // map.addControl(selectControl);
          // selectControl.activate();  
}function selectFeature(event){
	//alert((event.geometry+'').replace("POINT(","").replace(")","").split(" ")[0]);
	//alert((event.geometry+'').replace("POINT(","").replace(")","").split(" ")[1]);
	//var adr=contextPath+"/map/loadMapQuery.xhtml?mapQueryInfo.maxX="+wkt_all_x[0]+"&mapQueryInfo.maxY="+wkt_all_y[0]+"&mapQueryInfo.smallX="+wkt_all_x[wkt_all_x.length-1]+"&mapQueryInfo.smallY="+wkt_all_y[wkt_all_y.length-1]+""; 
	//loadWindow('拉框查询',200,100,650,330,adr,'map-lkcx-window');
	//alert(map.getLonLatFromPixel(new OpenLayers.Pixel(776,200)));
	//alert(map.getPixelFromLonLat(new OpenLayers.LonLat(4988,87158)));
}
function baozhanbanjin(x,y,banjin){
	var maxX= parseFloat(x)+parseFloat(banjin);
	var maxY= parseFloat(y)+parseFloat(banjin)
	var minX= parseFloat(x)-parseFloat(banjin)
	var minY= parseFloat(y)-parseFloat(banjin)
	var adr=contextPath+"/map/right/loadMapQuery.xhtml?mapQueryInfo.maxX="+maxX+"&mapQueryInfo.maxY="+maxY+"&mapQueryInfo.smallX="+minX+"&mapQueryInfo.smallY="+minY+""; 
	loadWindow('拉框查询',200,100,650,330,adr,'map-lkcx-window');
	//alert(map.getLonLatFromPixel(new OpenLayers.Pixel(776,200)));
	//alert(map.getPixelFromLonLat(new OpenLayers.LonLat(4988,87158)));
}
/*销毁地图上模拟显示的伤亡半径图*/
function destroyLayer(){
    if(modelLayerArray!=undefined&&typeof(modelLayerArray)!="undefined"){
		map.removeLayer(modelLayerArray[0]);
		map.removeLayer(modelLayerArray[1]);
		map.removeLayer(modelLayerArray[2]);
		map.removeLayer(modelLayerArray[3]);
		modelLayerArray=null;
	}
	var risksLayers=map.getLayersByName("风险等值线");
    if(risksLayers.length>0){
		risksLayers[0].destroy();
    }
}
//--------------------------model  end--------------------------------------------------------//

function removeMarker(mymarkers,mymarker) {//删除
 		mymarkers.removeMarker(mymarker);
}
function closeWindow(window){
 		var winNr=WinLIKE.searchwindow(window);
  		WinLIKE.windows[winNr].close(); 
}
//----------------------------map tools begin---------------------------------------------------//
function handleMeasurements(event) {
    var geometry = event.geometry;
    var units = event.units;
    var order = event.order;
    var measure = event.measure;
    var element = document.getElementById('output');
    var out = "";
    if((geometry+"").indexOf(",")!=-1){
	    	var wkt_all_x = new Array();
			var wkt_all_y = new Array();
			
			var wkts = (event.geometry+'').replace(order==1?"LINESTRING((":"POLYGON((","").replace("))","").split(",");
	    	for (var i = 0; i < wkts.length; i++) {
		       wkt_all_x.push(wkts[i].split(" ")[0]);
		       wkt_all_y.push(wkts[i].split(" ")[1]);
		    }
	    if(order == 1) {
	    	 var html = "当前距离:"+ measure.toFixed(3) + " " + units;
		     onFeatureSelect(wkt_all_x[wkt_all_x.length-1],wkt_all_y[wkt_all_y.length-1],50,50,html)
	   		//out += "　距离: " + measure.toFixed(3) + " " + units;
	    }else{
	    	 var html = "面积: " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>";
		     onFeatureSelect(wkt_all_x[wkt_all_x.length-1],wkt_all_y[wkt_all_y.length-1],50,50,html)
	         //out += "　面积: " + measure.toFixed(3) + " " + units + "<sup>2</" + "sup>";
	    }
    }
    element.innerHTML = out;
}

function toggleControl(element) {
    for(key in measureControls) {
        var control = measureControls[key];
        if(element.id == key) {
            control.activate();
        } else {
            control.deactivate();
        }
    }
}
function setOptions(options) {
    polygonControl.handler.setOptions(options);
}   
function deactivateLayers(){//释放
	polygonControl.deactivate();
	largebutton.deactivate();
	rybx.deactivate();
	for(key in measureControls) {
        var control = measureControls[key];
        control.deactivate();
    }
    document.getElementById('output').innerHTML="";//清空测距
}
function printme(){
    //var print = this.document.getElementById("map").innerHTML;
    //print = print +"<br/>"
 	//print = print +'<SCRIPT language=javascript> function printView(){hidden();document.all.WebBrowser.ExecWB(7,1); } function print(){hidden();document.all.WebBrowser.ExecWB(6,6)}function pageSetup(){hidden();document.all.WebBrowser.ExecWB(8,1);}function hidden(){document.all("printView").style.display="none"; document.all("print").style.display="none";  document.all("pageSetup").style.display="none";}<\/script>';
    //print = print + "<OBJECT   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=21   id=WebBrowser   width=87></OBJECT> <input  id=printView name=Button   onClick=printView()   type=button   value=打印预览> <input  id = print name=Button   onClick=print()   type=button   value=直接打印>   <input  id = pageSetup name=Button   onClick= pageSetup()  type=button   value=页面设置>";
    //var newWindow = window.open();
    //newWindow.document.open("text/html");   
    //newWindow.document.write(print);   
    //newWindow.document.close();   
    this.document.execCommand('print');  
}
function changButtonClass(obj){
	var buttons=document.getElementsByTagName("button");
	for(var i=0;i<buttons.length;i++){
		if(buttons[i].value==obj.value){
			obj.className="in";
		}else{
			buttons[i].className="";
		}
	}
}
//----------------------------隐藏或显示地图工具栏---------------------------------------------------//
function displayTool(){
	$('maptools').style.display==''?toolStyle('none','fd.gif','0'):toolStyle('','sx.gif','115px')
}
function toolStyle(display,img,width){
	$('maptools').style.display = display;
	$('tool_img').src=resourcePath+"/img/map/"+img;
	$('maptool').style.width=width;
}

function displaySubMenu(li) {
	var subMenu = li.getElementsByTagName("ul")[0];
	subMenu.style.display = "block";
}
function hideSubMenu(li) {
	var subMenu = li.getElementsByTagName("ul")[0];
	subMenu.style.display = "none";
}
//----------------------------end---------------------------------------------------//		

//---------------------------------地图初始化----------------------------------------------//	
function init(){
    map = new OpenLayers.Map('map');
    map.addControl(new OpenLayers.Control.LayerSwitcher());

    /*var gphy = new OpenLayers.Layer.Google(
        "Google Physical",// 地形图
        {type: google.maps.MapTypeId.TERRAIN}
    );
    var gmap = new OpenLayers.Layer.Google(
        "Google Streets", // 地理图
        {numZoomLevels: 20}
    );
    var ghyb = new OpenLayers.Layer.Google(
        "Google Hybrid",//混合地图
        {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
    );
    var gsat = new OpenLayers.Layer.Google(
        "Google Satellite",//卫星图
        {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
    );*/
    var gmap = new OpenLayers.Layer.Google(
        "Google Streets", // 创建一个google地图的层
        {numZoomLevels: 20}
    );
    map.addLayers([gmap]);//

    // Google.v3 uses EPSG:900913 as projection, so we have to
    // transform our coordinates
    map.setCenter(new OpenLayers.LonLat(13531289.899729, 3487206.7550393),11);
        //---------------------------------------初始化显示储罐、救援物质、文本信息 begin------------------------------//
        markerMarkerList(true,"all");
        //---------------------------------------end------------------------------//
	    //map.zoomToExtent(bounds);
	    //---------------------------------------拉框查询------------------------------//
        polygonControl = new OpenLayers.Control.Measure(OpenLayers.Handler.RegularPolygon,{handlerOptions: {sides: 4,persist: true}});
        map.addControl(polygonControl);	
		polygonControl.handler.setOptions({irregular: true});//设置属性：不规则
		polygonControl.events.on({ "measure": newQueryWindow});//事件完成后触发
		//---------------------------------------拉框放大------------------------------//
	    largebutton = new OpenLayers.Control.ZoomBox(OpenLayers.Handler.Path,{title:'拉框放大'});
		map.addControl(largebutton);
		//---------------------------------------画任意形状图层------------------------------//
		rybx = new OpenLayers.Control.Measure(OpenLayers.Handler.Polygon, {handlerOptions: {persist: true}});//任意边形
		map.addControl(rybx);
		rybx.events.on({ "measure": newQueryWindow});//事件完成后触发
		//---------------------------------------鹰眼------------------------------//
		var overview = new OpenLayers.Control.OverviewMap();
        map.addControl(overview);//鹰眼
//      overview.maximizeControl();//默认打开鹰眼
        //map.addControl(new OpenLayers.Control.LayerSwitcher());
		//map.addControl(new OpenLayers.Control.PanZoomBar());
        map.addControl(new OpenLayers.Control.Navigation());
//        map.addControl(new OpenLayers.Control.MousePosition({element: $('location')}));
        map.addControl(new OpenLayers.Control.Scale($('scale'))); //普通比例尺
//        map.addControl(new OpenLayers.Control.ScaleLine());
		/*
        sketchSymbolizers = {
            "Point": {
                pointRadius: 4,
                graphicName: "square",
                fillColor: "white",
                fillOpacity: 1,
                strokeWidth: 1,
                strokeOpacity: 1,
                strokeColor: "#333333"
            },
            "Line": {
                strokeWidth: 3,
                strokeOpacity: 1,
                strokeColor: "#666666",
                strokeDashstyle: "dash"
            },
            "Polygon": {
                strokeWidth: 2,
                strokeOpacity: 1,
                strokeColor: "#666666",
                fillColor: "white",
                fillOpacity: 0.3
            }
        };
        */
		//this is measure code begin
            var style = new OpenLayers.Style();
            style.addRules([
                new OpenLayers.Rule({symbolizer: this.sketchSymbolizers})
            ]);
            var styleMap = new OpenLayers.StyleMap({"default": style});
            var options = {
                handlerOptions: {
                    style: "default", // this forces default render intent
                    layerOptions: {styleMap: styleMap},
                    persist: true
                }
            };
            measureControls = {
            	line: new OpenLayers.Control.Measure(
                OpenLayers.Handler.Path, options
	            ),
	            polygon: new OpenLayers.Control.Measure(
	                OpenLayers.Handler.Polygon, options
	            )
            };
            var control;
            for(var key in measureControls) {
                control = measureControls[key];
                control.events.on({
                    "measure": handleMeasurements,
                    "measurepartial": handleMeasurements
                });
                map.addControl(control);
            }
    	map.events.register("zoomend", this, function(e){
		   if(modelLayerArray!=undefined&&typeof(modelLayerArray)!="undefined"){
				var px=map.getViewPortPxFromLonLat(mylonlat);
				var scale=getMapScale();
				closeModel();
				//地图放大缩小后的半径
				var new_r=new Array(parseFloat(staticRadius[0])/parseFloat(mapScale),parseFloat(staticRadius[1])/parseFloat(mapScale),parseFloat(staticRadius[2])/parseFloat(mapScale),parseFloat(staticRadius[3])/parseFloat(mapScale));
				var colors=new Array("blue","black","green","red");
 				addModelLayer(staticName,new_r,colors,staticX,staticY);
			}
		});
		map.events.register("moveend",this,function(e){
				//empty
		});    
		map.events.register("click", map, function(e) {
			document.onmousemove = mouseNotMove;
        	if(flag!==null){
		  		 var position = this.events.getMousePosition(e);
		   		 var lonlat = map.getLonLatFromViewPortPx(e.xy);
		   		 x=lonlat.lon;
				 y=lonlat.lat;	
				 var actionUrl;		
		   		 if(flag=="company"){
		   				actionUrl=contextPath+"/map/loadCompanysByMarker.xhtml?markerType=company&markerX="+x+"&markerY="+y;
                        loadWindow('请选择要标记的企业',100,80,790,450,actionUrl,'marker-window');
		     	  }
		     	  flag=null;//同时只标注一次
	     	 }
    	});
    	mapScale=getMapScale();//获得地图比例
} 

//清除地图上所有的标记
function removeMarkers(){
	map.removeMarkers();
}

window.onload=init;
//----------------------------end---------------------------------------------------//	
function onClearMarker(){//点击清除标注按钮
	isDeleteMarker=true;
}
function clearMarker(markerId,markerType,markerX,markerY){//清除地图标注
 	if(confirm('确定删除吗？')){
 		var url=contextPath+"/map/deleteMarker.xhtml?mapMarker.id="+markerId+"&mapMarker.markerType="+markerType;
		new Request({   
		   url: url,   
		   method: 'post' ,   
		   onSuccess: function(responseText, responseXML) {   
		   		delMarker(markerType,markerX,markerY);
		   },onFailure: function() {   
		    	alert("删除失败,请重试!"); 
		   }}).send();  
 	}
 	isDeleteMarker=false;//点一次清除只能清除一个标注
 	return;
}	
function addMarkerBegin(t){
	flag=t;
	flg=t;
}
//-------------------------------点模拟时文字跟随鼠标显示提示信息------------------------------------------------//	
function mousePosition(ev){
    //支持 火狐
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    //支持IE
    return {
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}

function mouseMove(ev){
	var obj = document.getElementById('mousemovediv');
	if(obj==null) return;
    ev = ev || window.event;
    var mousePos = mousePosition(ev);
    obj.style.display = "";
    obj.style.left = mousePos.x+15;
    obj.style.top = mousePos.y+10;
}
function mouseNotMove(ev){
	var obj = document.getElementById('mousemovediv');
	if(obj!=null){
		obj.style.display = "none";
	}
}
/**
*风险等值线
*/
function addRisksLine(){
	deactivateLayers();
    if(map.getLayersByName("风险等值线").length>0){
    	return;
    }
    map.zoomToMaxExtent();//到全图状态
    var markerRisksArray=new OpenLayers.Layer.Markers("风险等值线");
	var size = new OpenLayers.Size(580,613);
	var offset = new OpenLayers.Pixel(-(size.w/2), -(size.h/2));//都除以2，坐标在图片的居中位置
	var icon = new OpenLayers.Icon(resourcePath+"/img/risks_line.png",size,offset);
	var newMarker=new OpenLayers.Marker(new OpenLayers.LonLat(5384.09552,85231.20950),icon);
	map.setCenter(new OpenLayers.LonLat(5384.09552,85231.20950));
	markerRisksArray.addMarker(newMarker);
	map.addLayer(markerRisksArray);
}
//----------------------------end---------------------------------------------------//	