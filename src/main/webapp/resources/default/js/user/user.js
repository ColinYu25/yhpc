/**
 * 用户管理
 * @author zhaozhi3758
 */
Ext.QuickTips.init();
Ext.ns("com.safetys.standard.web.action");
var userView = com.safetys.standard.web.action.UserView;

userView = function() {
	var store, grid;
	var fields = ["id","userName","factName","userCompany",
	              {
					name : "createTime",
					type : "date",
					dateFormat : "Y-m-d\\TH:i:s"
	              }];
	    
	return {
		init : function() {
			store = this.createStore();
			store.on('beforeload', function() {
				var qfactName = Ext.getDom('qfactName') ? Ext.getDom('qfactName').value : "";
				var quserCompany =  Ext.getDom('quserCompany') ? Ext.getDom('quserCompany').value : "";
				var firstArea =  Ext.getDom('root') ? Ext.getDom('root').value : "",
					secondArea=  Ext.getDom('parent') ? Ext.getDom('parent').value : "",
					thirdArea =  Ext.getDom('child') ? Ext.getDom('child').value : "";
				this.baseParams = {
				   "user.userName" : Ext.getCmp('pname').getValue(),
				   "user.factName" : qfactName,
				   "user.userCompany" : quserCompany,
				   "user.firstArea" : firstArea,
				   "user.secondArea": secondArea,
				   "user.thirdArea" : thirdArea
				};
			});
			
			grid = this.createGrid();
			
			new Ext.Viewport({ 
			      layout : 'fit',
				  items : [new Ext.TabPanel({
					  activeTab: 0,
					  items : [{
						  layout : 'fit',
						  title:"您现在的位置：用户代理",
						  items : grid
					  }]
				  })]
			});
			this.queryResItem();
		},
		
		createStore : function() {
		    return new ExtStore({
					     url : '/user/allUserList.xhtml',
					     fields : fields
				   });
		},
		
		createGrid : function() {
			return new ExtGrid({
						store : store,
						sm : new Ext.grid.RowSelectionModel({singleSelect : true }),  
						columns : [
					        new Ext.grid.RowNumberer(), 
						    { sortable : true,dataIndex : "userName",header : "用户名",width:55 },
						    { sortable : true,dataIndex : "factName",header : "真实姓名" },
						    { sortable : true,dataIndex : "userCompany",header : "所属单位" },
						    { sortable : true,
						      renderer : Ext.util.Format.dateRenderer("Y-m-d H:i:s"),
						      dataIndex : "createTime",header : "注册日期",width:55 ,align:'center'},
						    { sortable : true,dataIndex : "id",header : "操作",align:'center',width:55,
						    	renderer:function(v,c,r){
						    	    var x = '';
//						    	    var x = '<a onclick="userView.openPage(\'editUserInit.xhtml?fkUser.id='+v+'\');" href="javascript:;">【修改信息】</a>';
//						    	        x +='<a onclick="userView.openPage(\'editPasswordInit.xhtml?fkUser.id='+v+'\');" href="javascript:;">【修改密码】</a>';
//						    	        x +='<a onclick="userView.del('+v+')" href="javascript:;">【删除】</a>';
						    	        x +='<a onclick="userView.myproxy('+v+')" href="javascript:;">【我的代理】</a>';
							    	return x;
					            }
						    }
						],
						tbar : userView.createTbar(),
						bbar : {
						    id : 'page',
							xtype : 'pageinfobar',
							pageSize : 30,
							store : store,
							pageMethod : userView.queryResItem
						}
					});
		},
		
		myproxy : function(v){
			var record = grid.getSelectionModel().getSelected();
			$.dialog({
				content: 'url:'+cfg.ctx +'/user/loadProxyUsers.xhtml?user.id='+v,
				lock : true,
				title:'【'+record.data.factName+'】用户代理',
				opacity : 0.3,
				width : 650,
				height : 400,
				cache:false
			});
			
		},
		
		createTbar : function() {
			return ['-',
//			   {
//				text : '添加用户',
//				iconCls : 'reset',
//				handler : function() {
//				   userView.openPage("editUserInit.xhtml");
//				}
//			  },'-','->',
			   new Ext.form.TextField( {
				id : 'pname',
				emptyText : '请输入用户名',
				enableKeyEvents : true,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							userView.queryResItem();
						}
					}
				},
				width : 160
			}),'-',{
				text : '查询',
				iconCls : 'previewIcon',
				handler : function() {
				    userView.queryResItem();
				}
			},'-', {
                xtype: 'checkbox',
                checked: false,
                listeners:  //显示和折叠高级查询条件面板
                {
                    'check': function(chkbox, checked) {
                    	var dc = document.getElementById('DetailCondition');
                    	var oldh = grid.getHeight();
                        if (checked) {
                            var tid =  grid.tbar.id ;
                            var tbarDom = document.getElementById(tid);
							var son= tbarDom.firstChild;
							dc.style.display ='';
							if(tbarDom.childNodes.length <2){
								var clone = son.cloneNode(false);
								clone.id = clone.id +'_clone';
								clone.appendChild(dc);
								son.parentNode.appendChild(clone);
							}
						   var dch = Ext.fly(dc).getHeight();
                           grid.tbar.setHeight(27  + dch);
                           grid.setHeight(oldh );
                        }
                        else {
                        	var dch = Ext.fly(dc).getHeight();
                        	dc.style.display ='none';
                        	grid.setHeight(oldh + dch);
                            grid.tbar.setHeight(27);
                            $('#qfactName').val('');
                            $('#quserCompany').val('');
                        }
                    }
                }
            }, {
                xtype: 'tbtext',
                text: '高级查询'
            }];
		},
		
		del : function(id){
			if(confirm(' 确认要删除吗？')){
				$.ajax({
			    	  url:'deleteUser.xhtml',
			    	  data:"fkUser.id="+id,
			    	  type:"GET",
			    	  cache:false,
			    	  async:false,
			    	  dateType:"json",
			    	  error:function(XMLHttpRequest,textStatus) {
			              alert('服务器连接失败，请稍候重试！');
			    	  },
			  		  success: function(response){
			  		  	  if(!response.success){
			  		  	  		alert(response.msg);
			  		  	  		return;
			  		  	  }
			  		  	userView.queryResItem();
					  }
				});	
			}
		},
		
		openPage:function(url){
			  Ext.Msg.wait("页面加载中，请稍后...","提示");
			  window.location = url;
		},
		
		queryResItem : function(v) {
			if ( !v ){
			   v = Ext.getCmp('page').pageSize;
			}
			store.load({
				params : {
					"page.start" : 0,
					"page.limit" : v
				}
		    });
		}
	}
}();
Ext.onReady(userView.init, userView);