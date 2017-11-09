
/**
 *表格中列表中全选或不选功能
 *thisObj   主控制复选框
 *toObj  被控制复选框
 */
function selectAllOrNo(thisObj,toObj){
	if(toObj!=null){
		var len=toObj.length;
		var res=false;
		if(thisObj.checked==true){
			res=true;
		}
		if(len!=null){
			for(var i=0;i<len;i++){
				if(!toObj[i].disabled){
					toObj[i].checked=res;
				}
			}
		}else{
			if(!toObj.disabled){
				toObj.checked=res;
			}
		}
	}
}

 /**
  *下拉列表上下排序
  *selectItemMoveUp  往上升
  *selectItemMoveDown  往下降
  *selectItemSwapPosition 私有方法
  */				 
function selectItemMoveUp(selectObj) { 
    var theObjOptions=selectObj.options;
    for(var i=1;i<theObjOptions.length;i++) {
        if( theObjOptions[i].selected && !theObjOptions[i-1].selected ) {
            swapOptionProperties(theObjOptions[i],theObjOptions[i-1]);
        }
    }
} 
function selectItemMoveDown(selectObj) { 
    var theObjOptions=selectObj.options;
    for(var i=theObjOptions.length-2;i>-1;i--) {
        if( theObjOptions[i].selected && !theObjOptions[i+1].selected ) {
            swapOptionProperties(theObjOptions[i],theObjOptions[i+1]);
        }
    }
} 
function selectItemSwapPosition(option1,option2){
    var tempStr=option1.value;
    option1.value=option2.value;
    option2.value=tempStr;
    tempStr=option1.text;
    option1.text=option2.text;
    option2.text=tempStr;
    tempStr=option1.selected;
    option1.selected=option2.selected;
    option2.selected=tempStr;
}

 /**
  *动态生成枚举下拉框(支持多级联动）
  */  
  function Enum(url){
	this.url=url
  }
  Enum.prototype = {
	 xmlhttp:false,
	 init:function() {
			 try {
				// Mozilla / Safari / IE7
				this.xmlhttp = new XMLHttpRequest();
			 } catch (e) {
				// IE
					var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
											'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
											'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
					var success = false;
					for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
						try {
						   this.xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
						   success = true;
						} catch (e) {
							//empty body
						}
					}
					if (!success) {
						throw new Error('Unable to create XMLHttpRequest.');
					 }
			  }
			  //false时为同步
			  this.xmlhttp.open('GET',this.url,false);
			  this.xmlhttp.send(null);	
			  var self=this;
			  this.xmlhttp.onreadystatechange = function() {
				  if (self.xmlhttp.readyState == 4) {
					 if (self.xmlhttp.status == 200) {
					 }
				  }	
			  }
	  },
	  /**
		  *enumCode 枚举的编码.   当onchange,级联联动时,对应的是刚才选中的值.
		  *selectId 页面元素的ID.  当onchange,级联联动时,对应的是下一级页面元素的ID
      */	
	 initSelect:function(enumCode,selectId){
		    if(!this.xmlhttp){
				this.init();
			}
			var obj=document.getElementById(selectId);
			if (obj) {
				for(var i=obj.options.length-1; i>0; i--) {
					obj.options[i] = null;
				}
			}
			if(obj!=null&&obj!=undefined){
				var parentEnums = this.xmlhttp.responseXML.getElementsByTagName(enumCode)[0];
				if(parentEnums==null || !parentEnums)
					return;
				var childEnums=parentEnums.childNodes;
				var opt = new Option("--请选择--","");
				obj.options[0] = opt ;
				opt = null ;
				for(var i=0;i<childEnums.length;i++){
						var opt = new Option(childEnums[i].getAttribute("name"),childEnums[i].tagName) ;
						obj.options[i+1] = opt ;
						opt = null ;
				}
			}
    },
    getSelect:function(enumCode) {
    	if(!enumCode || enumCode == "") {
    		return "";
    	}
		if(!this.xmlhttp){
			this.init();
		}
		var enums = this.xmlhttp.responseXML.getElementsByTagName(enumCode);
		//之前是==1 ，枚举文件里，有重复的枚举值，无法处理，只能大于0 区第一个
		if (enums && enums.length > 0) {
			return enums[0].getAttribute("name");
		}
    }
 }
 /**
  * 
  */
  function Area(url){
	this.url=url
  }
  Area.prototype = {
	 xmlhttp:false,
	 init:function() {
			 try {
				// Mozilla / Safari / IE7
				this.xmlhttp = new XMLHttpRequest();
			 } catch (e) {
				// IE
					var XMLHTTP_IDS = new Array('MSXML2.XMLHTTP.5.0',
											'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
											'MSXML2.XMLHTTP','Microsoft.XMLHTTP' );
					var success = false;
					for (var i=0;i < XMLHTTP_IDS.length && !success; i++) {
						try {
						   this.xmlhttp = new ActiveXObject(XMLHTTP_IDS[i]);
						   success = true;
						} catch (e) {
							//empty body
						}
					}
					if (!success) {
						throw new Error('Unable to create XMLHttpRequest.');
					 }
			  }
			  //false时为同步
			  this.xmlhttp.open('GET',this.url,false);
			  this.xmlhttp.send(null);	
			  var self=this;
			  this.xmlhttp.onreadystatechange = function() {
				  if (self.xmlhttp.readyState == 4) {
					 if (self.xmlhttp.status == 200) {
					 }
				  }	
			  }
	  },
	 getAreas:function(areaCode){
		 //alert("2");
		    if(!this.xmlhttp){
				this.init();
			}
			var parentArea = this.xmlhttp.responseXML.documentElement;
			if(parentArea==null || !parentArea)
				return null;
			var areas = new Array();
			var area = new Array();
			if(parentArea.getAttribute("code") == areaCode) {//一级区域
				for (var i=0; i<parentArea.childNodes.length; i++) {
					area = new Array();
					area[0] = parentArea.childNodes[i].getAttribute("name");
					area[1] = parentArea.childNodes[i].getAttribute("code");
					areas[i] = area;
				}
			} else {
				var isCounty = true;
				for (var i=0; i<parentArea.childNodes.length; i++) {//二级区域
					if (parentArea.childNodes[i].getAttribute("code") == areaCode) {
						for (var j=0; j< parentArea.childNodes[i].childNodes.length; j++) {//三级区域
							area = new Array();
							area[0] = parentArea.childNodes[i].childNodes[j].getAttribute("name");
							area[1] = parentArea.childNodes[i].childNodes[j].getAttribute("code");
							areas[j] = area;
						}
					} else if(i == parentArea.childNodes.length - 1){
						isCounty = false;
					}
				} 
				if (!isCounty) {
					for (var i=0; i<parentArea.childNodes.length; i++) {
						for (var j=0; j< parentArea.childNodes[i].childNodes.length; j++) {//三级区域
							if (parentArea.childNodes[i].childNodes[j].getAttribute("code") == areaCode) {
								for (var k=0; k< parentArea.childNodes[i].childNodes[j].childNodes.length; k++) {//四级区域
									area = new Array();
									area[0] = parentArea.childNodes[i].childNodes[j].childNodes[k].getAttribute("name");
									area[1] = parentArea.childNodes[i].childNodes[j].childNodes[k].getAttribute("code");
									areas[k] = area;
								}
							}
						}
					}
				}
			}
			return areas;
    },
    getArea:function(areaCode) {
    	if(!this.xmlhttp){
			this.init();
		}
		var area = new Array();
		var parentArea = this.xmlhttp.responseXML.documentElement;
		area[1] = areaCode;
		if(parentArea.getAttribute("code") == areaCode) {
			area[0] = parentArea.getAttribute("name");
			return area;
		} else {
			for (var i=0; i<parentArea.childNodes.length; i++) {
				try{
					if (parentArea.childNodes[i].getAttribute("code") == areaCode) {
						area[0] = parentArea.childNodes[i].getAttribute("name");
						return area;
					}
				}catch(e){}
				for (var j=0; j<parentArea.childNodes[i].childNodes.length; j++) {
					try{
						if (parentArea.childNodes[i].childNodes[j].getAttribute("code") == areaCode) {
							area[0] = parentArea.childNodes[i].childNodes[j].getAttribute("name");
							return area;
						}
					}catch(e){}
					for (var k=0; k<parentArea.childNodes[i].childNodes[j].childNodes.length; k++) {
						try{
							if (parentArea.childNodes[i].childNodes[j].childNodes[k].getAttribute("code") == areaCode) {
								area[0] = parentArea.childNodes[i].childNodes[j].childNodes[k].getAttribute("name");
								return area;
							}
						}catch(e){}
					}
				}
			}
		}
    }
 }

