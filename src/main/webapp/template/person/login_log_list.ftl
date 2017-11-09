<@fkMacros.pageHeader />
<#escape x as (x)!> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${contextPath}/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${contextPath}/ext/ext-all.js"></script>
<link type="text/css" rel="stylesheet" href="${contextPath}/ext/resources/css/ext-all.css"/>
<script type="text/javascript">
Ext.onReady(function(){    
	var store = new Ext.data.Store({ 
		proxy: new Ext.data.HttpProxy({url:'loadGridPersonLogs.xhtml'}),
		reader: new Ext.data.JsonReader({totalProperty: 'count', root: 'fkSafeLogs'},[ {name: 'createTime'}, {name: 'ipAddress'}, 
		{name: 'ipArea'}, {name: 'operType'}]) 
	}); 
  
 	var sm = new Ext.grid.CheckboxSelectionModel();   
 	var bbar = new Ext.PagingToolbar({   
   		pageSize: 30, store: store, displayInfo: true, displayMsg: '当前显示 {0} - {1}条记录 /共 {2}条记录',beforePageText: '当前第',afterPageText: '页/共{0}页',
   		emptyMsg: "没有记录"
 	});
 	
 	function renderType(value) {
 		if (value == '5')  return "成功";else if(value == '7')return "失败";else return "未知";
 	}
 	
 	var cm = new Ext.grid.ColumnModel([
  		new Ext.grid.RowNumberer(),//第一列为编号列
		sm,//第二列为复选列
		{header:"登录时间",dataIndex:"createTime",sortable:true,align: 'center'},
		{header:"登录IP",dataIndex:"ipAddress",sortable:true,align: 'center'},
		{header:"登录区域",dataIndex:"ipArea",sortable:true,align: 'center'},
		{header:"登录事件",dataIndex:"operType",sortable:true,align: 'center',renderer:renderType}
 	 ]);
  	var grid = new Ext.grid.GridPanel({   
	  renderTo:"main",   
	  height:530,   
	  width:850, 
	  frame:true,
      collapsible: true,
      animCollapse: false,
	  trackMouseOver:true,   
	  cm:cm,//定义表格显示列头   
	  sm:sm,//复选方式按sm定义方式   
	  title:'最近一个月登录日志列表',
	  layout: 'fit',//表格内列内容填充满,按列头比例填充
	  store:store, //数据源为store   
	  bbar: bbar,//底端 工具条
	  //tbar:bbar,
	  loadMask:{msg:'正在加载数据，请稍侯……'}, 
	  autoExpandColumn:8,
			viewConfig: {
				sortAscText:'升序',
				sortDescText:'降序',
				columnsText:'显示列',
	            forceFit:true
	        }
  	});
 	store.load({params:{start:0,limit:30},
	 	callback: function(r, options, success){   
		    if(success){   
		      //Ext.Msg.alert('操作','成功！');   
		    }else{   
		      Ext.Msg.alert('操作','失败！');   
		    }   
	    }
 	}); 
 	store.on('beforeload',function(){
      Ext.apply(
      this.baseParams,
      {countTotal:store.getTotalCount()});
    });
    grid.render();    
});  

</script>
<div id="main">
</div>

<body>

<script language="javascript">
//displayTable(searchTable);
</script>
</#escape> 
<@fkMacros.pageFooter />
</body>