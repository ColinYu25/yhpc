/**
 * @author zhaozhi3758 项目自身扩展的ui组件或常用类
 */

// --------------------------------------------------分页组件封装
var PageInfoBar = Ext.extend(Ext.PagingToolbar, {
			displayInfo : true,
			prependButtons : true,
			displayMsg : '显示{0}条至{1}条,总计<span style="font-weight:bold;font-size:14px;">{2}</span>条',
//			plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有查询到相关记录",
			initComponent : function() {
				var _this = this;
				_this.items = ['-', '&nbsp;&nbsp;', new Ext.form.ComboBox({
							hiddenName : 'pagesize',
							typeAhead : true,
							triggerAction : 'all',
							lazyRender : true,
							mode : 'local',
							store : new Ext.data.ArrayStore({
										fields : ['value', 'text'],
										data : [[5, '5条/页'], [10, '10条/页'],
												[20, '20条/页'], [30, '30条/页'],[50, '50条/页'],
												[100, '100条/页']]
									}),
							valueField : 'value',
							displayField : 'text',
							value : _this.pageSize,
							editable : false,
							width : 85,
							listeners : {
								select : function(comboBox) {
									if (!_this.pageMethod) {
										Ext.Msg.alert('提示', 'pageMethod未指定！');
									}
									var number = parseInt(comboBox.getValue());
									_this.pageSize = number;
									_this.pageMethod(number);
								}
							}
						})

				]
				PageInfoBar.superclass.initComponent.call(this);
			}
		});
Ext.reg('pageinfobar', PageInfoBar);

// -------------------------------------------------- window 组件封装

var ExtWin = Ext.extend(Ext.Window, {
			layout : 'fit',
			width : 500,
			height : 300,
			resizable : false,
			draggable : true,
			closeAction : 'hide',
			title : '',
			plain : true,
			modal : true,
			collapsible : false, //在有HTMLEditor的情况下，伸缩会有BUG，需要之后解决
			titleCollapse : false,
			maximizable : false,
			border : false,
			animCollapse : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			buttonAlign : 'center',
			initComponent : function() {
				var config = {
					pageY : 20,
					pageX : document.body.clientWidth / 2 - this.width / 2

				};
				Ext.apply(this, config);
				ExtWin.superclass.initComponent.call(this);
			}
		});
Ext.reg('extwin', ExtWin);

// -------------------------------------------------- store 组件封装

var ExtStore = Ext.extend(Ext.data.JsonStore, {
			constructor : function(_cfg) {
				var config = {
					 totalProperty : 'results',
					 root : 'list',
					 idProperty : 'id',
					 listeners : { 
						 loadexception : function(){alert('server error!');}
					 },
					 paramNames : { start : 'page.start',limit : 'page.limit',sort : 'page.sort',dir : 'page.dir' }

				};
				Ext.apply(config, _cfg);
				config.url = cfg.ctx + config.url;
				ExtStore.superclass.constructor.call(this,config);
			}
		}); 
Ext.reg('extstroe', ExtStore);

// -------------------------------------------------- grid 组件封装
var ExtGrid = Ext.extend(Ext.grid.GridPanel, {
			loadMask : { msg : '加载数据中，请稍候...'},
			autoScroll : true,
			border : false,
			stripeRows : true,
			viewConfig : { forceFit : true },
			initComponent : function() {
				ExtGrid.superclass.initComponent.call(this);
			}
		});
Ext.reg('extgrid', ExtGrid);

// -------------------------------------------------- ajax 同步调用,返回类型为json字符串

var ajaxSyncCall = function(urlStr, paramsStr) {
		var obj,res;
		if (window.ActiveXObject) {
			obj = new ActiveXObject('Microsoft.XMLHTTP');
		} else if (window.XMLHttpRequest) {
			obj = new XMLHttpRequest();
		}
		obj.open('POST', urlStr, false);
		obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		obj.send(paramsStr);
		var res = obj.responseText;
		try{
			Ext.decode(res);//如果因传递错误的url，或者服务器报错，这里会出现异常
		} catch(e){
			Ext.Msg.alert("出错","数据解析失败，或者url不可用！")
			return "";
		}
	    return res;
}

//-------------------------------------------------- 右下角弹出 TipWindow

var TipWindowMgr = {
	positions : []
};
var TipWindow = Ext.extend(Ext.Window, {
			initComponent : function() {
				Ext.apply(this, {
							iconCls : this.iconCls || 'information',
							autoScroll : true,
							autoDestroy : true,
							plain : false,
							bodyStyle : 'background-color:#FFFFFF;font-size: 13px;',
							shadow : false
						});
				//用这个功能时候，请测试一下，拖动窗口会不会出问题，或者不允许拖动窗口
				if (this.hideTask === true) {
					this.task = new Ext.util.DelayedTask(this.hide, this);
				}
				TipWindow.superclass.initComponent.call(this);
			},
			setMessage : function(msg) {
				this.body.update(msg);
			},
			setTitle : function(title, iconCls) {
				TipWindow.superclass.setTitle.call(this, title, iconCls || this.iconCls);
			},
			onRender : function(ct, position) {
				TipWindow.superclass.onRender.call(this, ct, position);
			},
			onDestroy : function() {
				TipWindowMgr.positions.remove(this.pos);
				TipWindow.superclass.onDestroy.call(this);
			},
			afterShow : function() {
				TipWindow.superclass.afterShow.call(this);
				//有BUG
//				this.on('move', function() {
//							TipWindowMgr.positions.remove(this.pos);
//							this.task.cancel();
//						}, this);
//				this.task.delay(5000);
			},
			animShow : function() {
				this.pos = 0;
				while (TipWindowMgr.positions.indexOf(this.pos) > -1)
					this.pos++;
				TipWindowMgr.positions.push(this.pos);
				this.setSize(this.width, this.height);
				this.el.alignTo(document, "br-br",
						[-20, -20 - ((this.getSize().height + 10) * this.pos)]);
				this.el.slideIn('b', {
							duration : 2,
							callback : this.afterShow,
							scope : this
						});
			},
			animHide : function() {
				TipWindowMgr.positions.remove(this.pos);
				this.el.ghost("b", {
							duration : 2,
							remove : true,
							scope : this,
							callback : this.destroy
						});
			}
});