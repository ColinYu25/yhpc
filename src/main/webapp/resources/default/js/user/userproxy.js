/**
 *  用户代理
 *  @author zhaozhi3758  
 */
 
Ext.ns("com.safetys.standard.web.action");
var userProxy = com.safetys.standard.web.action.userProxy; 

userProxy = function() {
	var   userstore, usergrid, secondGridStore, secondGrid, userwin;
	return {
		
		init : function(){
	 	 	userstore = this.createuserStore();
	 	 	userstore.on('beforeload', function() {
	 	 		this.baseParams = {
	 	 		     "user.userName" :  Ext.getCmp('qusername').getValue()
	 	 		}
	 	 	});
	 	 	usergrid =  this.createusergrid();
	 	 	secondGridStore = this.createSecondGridStore();
	 	 	secondGrid = this.createSecondGrid();
	 	 
	 	 	
	 	 	new Ext.Viewport({ 
			      layout : 'hbox',
			      defaults     : { flex : 1 }, //auto stretch
				  layoutConfig : { align : 'stretch' },
				  items        : [
						usergrid,
						secondGrid
					]
			});
	 	 	
	 		secondGridStore.load({
				params : {
	 	 		    "page.start" : 0,
					"page.limit" : 100,
					"user.id" : userId
				}
			});
	 	 	
	 	 	this.queryuserItem();
	 	 	
	 	 	var firstGridDropTargetEl =  usergrid.getView().scroller.dom;
		    var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		                ddGroup    : 'firstGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
		                        var records =  ddSource.dragData.selections;
		                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
		                        //异步删除
		                        Ext.Ajax.request({
										url : cfg.ctx+'/user/deleteRelationUser.xhtml',
										success : function(response) {
										    var rt = Ext.decode(response.responseText);
											if (!rt.success){
												alert('操作失败！');
											}
										},
							            failure : function(){
											alert('服务器连接失败，请稍候重试！');
										},
										params : {
											"relationUser.userId" : userId,
											"relationUser.relationUserId" : records[0].id,
											"relationUser.type" : "PROXY"
										}
									});
		                        return true;
		                }
		        });
		    
		        var secondGridDropTargetEl = secondGrid.getView().scroller.dom;
		        var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
		                ddGroup    : 'secondGridDDGroup',
		                notifyDrop : function(ddSource, e, data){
		                        var records =  ddSource.dragData.selections;
		                        var s =  secondGrid.store;
		                        if(records[0].id == userId || s.indexOfId(records[0].id)>-1)return false; 
		                        s.add(records);
		                        //异步添加
		                        Ext.Ajax.request({
										url : cfg.ctx+'/user/addRelationUser.xhtml',
										success : function(response) {
											var rt = Ext.decode(response.responseText);
											if (!rt.success){
												alert('操作失败！');
											}
										},
							            failure : function(){
											alert('服务器连接失败，请稍候重试！');
										},
										params : {
											"relationUser.userId" : userId,
											"relationUser.relationUserId" : records[0].id,
											"relationUser.type" : "PROXY"
										}
									});
		                        return true;
		                }
	           });
	 	 	
		},
		
		createuserStore : function(){
			userstore =  new ExtStore({
				     url : '/user/allUserList.xhtml',
				     fields : ["id","userName","factName"]
				});
			return userstore;
		},
		
		createusergrid : function(){
			usergrid =  new Ext.grid.GridPanel(
						  {
								    autoScroll : true,
									store : userstore,
									enableDragDrop   : true,
									ddGroup          : 'secondGridDDGroup',
									loadMask : { msg : '正在加载表格数据,请稍等...' },
									stripeRows : true,
									autoExpandColumn : 'userName',
									sm : new Ext.grid.RowSelectionModel({singleSelect : true }),
									columns : [
									        new Ext.grid.RowNumberer(),
									        { sortable : true,dataIndex : "userName",header : "用户名" },
										    { sortable : true,dataIndex : "factName",header : "真实姓名"}
									],
									viewConfig : { forceFit : true },
									tbar:[
									  {
										 xtype : "label",
									     html:'<font color="blue">所有用户（添加，请拖拽匹配用户到右侧）</font>'
									  }
									],
									bbar : {
										id : 'userpage',
										xtype : 'pageinfobar',
										pageSize : 20,
										store : userstore,
										pageMethod : this.queryuserItem
									},
									listeners : {
										'render' : function(grid, rowIndex, columnIndex, e) {
											var tbar1 = new Ext.Toolbar({
														items : [{
																	xtype : "label",
																	text : "用户名"
																}, "-", {
																	xtype : "textfield",
																	value : "",
																	width : 130,
																	id : "qusername"
																}, {
																	text : '查询',
																	iconCls : 'previewIcon',
																	handler : function() {
																	    userProxy.queryuserItem();
																	}
																}]
													});
											tbar1.render(this.tbar);
										}
									}
						});
			return usergrid;
		
		},
		
		createSecondGridStore : function() {
			secondGridStore =  new ExtStore({
								     url : '/user/allRelationUserList.xhtml',
								     fields : ["id","userName","factName"]
								});
			return secondGridStore;
		},
		
		createSecondGrid : function() {
			secondGrid = new Ext.grid.GridPanel({
						    ddGroup          : 'firstGridDDGroup',
					        store            : secondGridStore,
					        loadMask : { msg : '正在加载表格数据,请稍等...' },
					        columns          : [
			            			    new Ext.grid.RowNumberer(),
			            			    { sortable : true,dataIndex : "userName",header : "用户名" },
			            			    { sortable : true,dataIndex : "factName",header : "真实姓名" }
					         ],
						    enableDragDrop   : true,
					        stripeRows       : true,
					        autoExpandColumn : 'userName',
					        viewConfig : { forceFit : true },
							tbar:[
								   {
									 xtype : "label",
								     html:'<font color="blue">已选代理用户（删除，请拖拽所选用户到左侧）</font>'
								  }
								]
				    });
			return secondGrid;
		},
		
		queryuserItem : function(v){
			if(!v) v = Ext.getCmp('userpage').pageSize;
		    userstore.load({
				params : {
		    	    "page.start" : 0,
					"page.limit" : v
				}
			});
		}
	}
}();
Ext.onReady(userProxy.init, userProxy);