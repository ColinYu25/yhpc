// JavaScript Document

//带三角bar，点击三角区域下面内容展开
//$(function(){
//$("div[tit=openorclose]").toggle(function(){
//$(this).parent().next().slideDown(500); //slideUp  fadeOut
//$(this).attr("class","list_title_arrow_up")
//},function(){
//$(this).parent().next().slideUp(500); //slideDown fadeIn
//$(this).attr("class","list_title_arrow_down")
//});  
//});

//带三角bar，点击整个bar区域下面内容展开
$(function(){
$("div[tit=tit_bar]").toggle(function(){
$(this).next().slideDown(500); //slideUp  fadeOut
$(this).children("div[tit=openorclose]").attr("class","list_title_arrow_up")
},function(){
$(this).next().slideUp(500); //slideDown fadeIn
$(this).children("div[tit=openorclose]").attr("class","list_title_arrow_down")
});  
});




$(function(){
$("div[tit=left_father]").toggle(function(){
$(this).next().slideDown(500); //slideUp  fadeOut

},function(){
$(this).next().slideUp(500); //slideDown fadeIn

});  
});





$(function(){ 
   var get_li=$(".list_title_02 ul li");
       get_li.click(function(){
$(this).addClass("selected")
   .siblings().removeClass("selected");
   
   var index = get_li.index(this);
     $(".list_tab_content > div")
    .eq(index).slideDown(500)
    .siblings().slideUp(500); 
      })

})


$(function(){ 
		   
   var drag=$(".right_drag");
       drag.click(function(){
$("#right").hide();
})
 

})



$(function(){ 
   var get_li=$(".list_title_blue_tab_zf ul li");
       get_li.click(function(){
							 
       $(this).addClass("selected").siblings().removeClass("selected");
   
   var index = get_li.index(this);
       $(".list_left_zf_content > div").eq(index).slideDown(500).siblings().slideUp(500); 
      })

})


$(function(){ 
   var get_li=$(".title_tab ul li");
       get_li.click(function(){
$(this).addClass("selected").siblings().removeClass("selected");
   
   var index = get_li.index(this);
     $(".title_tab_content > div")
    .eq(index).slideDown(500)
    .siblings().slideUp(500); 
      })

})

$(function () {
    var get_li = $(".about_menu ul li");
    get_li.click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");

        var index = get_li.index(this);
        $(".about_content > div").eq(index).slideDown(500).siblings().slideUp(500);
    })

    $("#btn_edit").click(function () {//进入编辑模式
        $("#view_dwjbqk,#view_aqscjbzk").hide();
        $("#edit_dwjbqk,#edit_aqscjbzk").show();
        $(".btnrow_top").eq(0).hide();
        $(".btnrow_top").eq(1).show();
    })

    $("#btn_cancel").click(function () {//退出编辑模式
        $("#view_dwjbqk,#view_aqscjbzk").show();
        $("#edit_dwjbqk,#edit_aqscjbzk").hide();
        $(".btnrow_top").eq(0).show();
        $(".btnrow_top").eq(1).hide();
    });

    if ($(".fixed").length != 0) {
        if ($(".fixed").parent().height() + 27 < 550) {
            $(".fixed").width(548);
            $(".qyhkb_list").width(550);
        }
        else {
            $(".fixed").width(534);
            $(".qyhkb_list").width(536);
        }
    }

    $("#view_aqscjbzk a,#edit_aqscjbzk a").click(function () {
        $(frameElement.parentElement).parent().find(".wBox_itemTitle").html($(this).text());
    })
    $(".link_back").click(function () {
        $(frameElement.parentElement).parent().find(".wBox_itemTitle").html("生产经营单位企业档案");
    })

})



$(function(){ 
  $(".close").click(function(){
							 $("#window_about_qy").animate({left: 50, opacity: 'hide'}, 500);

							 })

})

//表格单元行
$(document).ready(function() {
$(".main_tab tr").hover(function() {
		$(this).addClass("blue");
	}, function() {
		$(this).removeClass("blue");
	});
});

//表格单元格 2012年9月20日新增
$(document).ready(function() {
  $(".main_tab_td tr td").hover(function() {
  $(this).addClass("blue_td");
  }, function() {
  $(this).removeClass("blue_td");
  });
});



//表格 柱形图切换查看
$(function(){ 
   var get_li=$(".qh_icon_menu ul li");
       get_li.click(function(){
       $(this).addClass("selected").siblings().removeClass("selected");
   
   var index = get_li.index(this);
     $("#main_content > div").eq(index).slideDown(500).siblings().slideUp(500); 
      })

})


//$(function(){ 
//		   
//$(".qh_icon_main").toggle(
//
//  function () { 
//    $(".qh_icon_main > img").attr("src","icon/icon_06.gif");
//	$("#img").slideUp(500);
//	
//  },
//  function () {
//    $(".qh_icon_main > img").attr("src","icon/icon_05.gif");
//	$("#tab").slideDown(500);
//  }
//  );
//
//})


////框架切换 三栏
//function left_close_open()
//{
//	if(window.top.document.getElementById("center").cols=="0,*")
//	{
//		window.top.document.getElementById("center").cols="263,*"
//	}else
//	{
//		window.top.document.getElementById("center").cols="0,*"
//		
//	}
//}


function map()
{

		window.top.document.getElementById("center").cols="3,*,3"

}


//左右框架切换 二栏
$(function(){ 
		   
$(".jqResize").toggle(

  function () { 
    $(".jqResize > img").attr("src","img/arrow_right.gif");
	window.top.document.getElementById("center").cols="0,*"
  },
  function () {
    $(".jqResize > img").attr("src","img/arrow_left.gif");
	window.top.document.getElementById("center").cols="263,*"
  }
  
);

})
//业务点击展开与收起
$(document).ready(function(){
   $("#down_img01").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_01_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_01.jpg");
   });
});

$(document).ready(function(){
   $("#down_img02").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_02_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_02.jpg");
   });
});

$(document).ready(function(){
   $("#down_img03").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_03_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_03.jpg");
   });
});

$(document).ready(function(){
   $("#down_img04").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_04_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_04.jpg");
   });
});

$(document).ready(function(){
   $("#down_img05").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_05_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_05.jpg");
   });
});

$(document).ready(function(){
   $("#down_img06").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_zy_gz_06_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_zy_gz_06.jpg");
   });
});



//children 只对 下一级产生作用，对下一级的下一级找不到的。解决办法：先找到点击后的下一级div d_arrow，然后取它的下一级的img的src
$(document).ready(function(){
   $(".down_bar").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children(".d_arrow").children().attr("src","icon/arrow_24_down.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children(".d_arrow").children().attr("src","icon/arrow_24_up.jpg");
   });
});

//仅仅限于点击三角区域
//$(document).ready(function(){
//   $(".d_arrow").toggle(function(){
//     $(this).parent().next().slideDown('fast');
//	 $(this).children().attr("src","icon/arrow_24_down.png");
//   },function(){
//     $(this).parent().next().slideUp('fast');
//	 $(this).children().attr("src","icon/arrow_24_up.png");
//   });
//});

//children 只对 下一级产生作用，对下一级的下一级找不到的。

//$(document).ready(function(){
//   $(".down_bar").toggle(function(){
//     $(this).next().slideDown('fast');
//	 $(this).children(".d_arrow").css("border","1px solid #000");
//   },function(){
//     $(this).next().slideUp('fast');
//	 $(this).children(".d_arrow").css("border","1px solid red");
//   });
//});





$(document).ready(function(){
   $("#down_img08").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_bzh_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_bzh.jpg");
   });
});

$(document).ready(function(){
   $("#down_img09").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_yhpc_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_yhpc.jpg");
   });
});

$(document).ready(function(){
   $("#down_img10").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_wxy_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_wxy.jpg");
   });
});

$(document).ready(function(){
   $("#down_img11").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_yjya_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_yjya.jpg");
   });
});

$(document).ready(function(){
   $("#down_img12").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_apbg_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_apbg.jpg");
   });
});


$(document).ready(function(){
   $("#down_img13").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","img/img_yzd_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","img/img_yzd.jpg");
   });
});

////业务点击展开与收起
//$(document).ready(function(){
//   $(".down_img").toggle(function(){
//     $(this).next().slideDown('fast');
//	 
//   },function(){
//     $(this).next().slideUp('fast');
//	
//   });
//});

//login_btn 2012年4月9日新增
$(document).ready(function(){   
    $("input.btn").fadeTo("fast", 1.0); // This sets the opacity of the thumbs to fade down to 60% when the page loads   
  
    $("input.btn").hover(function(){   
        $(this).fadeTo("fast", 0.8); // This should set the opacity to 100% on hover   
    },function(){   
        $(this).fadeTo("fast", 1.0); // This should set the opacity back to 60% on mouseout   
    });   
}); 



$(document).ready(function(){ 
$(".user_password,.code").hover(
  function () {
    $(this).css("border","1px solid #ffba00");
  },
  function () {
    $(this).css("border","1px solid #015fb2");
  }
);

}); 

//改进后的头部菜单 -开始
$(function(){ 
var li=$("#menu_top ul li");

li.click(function(){
      $(this).addClass("selected").siblings().removeClass("selected");
});

li.hover(
function(){$(this).addClass("hover").siblings().removeClass("hover");}
,function(){$(this).removeClass("hover");}
);
})
//改进后的头部菜单  -开始

$(document).ready(function(){
   $(".down_img").toggle(function(){
     $(this).next().slideDown('fast');
   },function(){
     $(this).next().slideUp('fast');
   });
});

//地图页面左侧列表经过样式
$(document).ready(function() {
$(".map_left tr").hover(function() {
 
  $(this).addClass("blue");
 }, function() {
  $(this).removeClass("blue");
 });
});

//2012年5月7日新增
//企业户口本 多行文本框 加减图片透明度
$(document).ready(function(){   
    $(".bigger,.smaller").fadeTo("fast", 1.0);  
  
    $(".bigger,.smaller").hover(function(){   
        $(this).fadeTo("fast", 0.5); 
    },function(){   
        $(this).fadeTo("fast", 1.0); 
    });   
}); 


//2012年5月8日新增
//鼠标移上去，input边框出现，不包括类型为 radio 的input。
$(document).ready(function(){ 
$(".about_qy_tab :input[type!=radio]").css("border","1px solid #e58416");	
$(".about_qy_tab :input[type!=radio]").hover(
  function () {
    $(this).css("border","1px solid #e58416");
  },
  function () {
    $(this).css("border","1px solid #d8b791");
  }
);

}); 



//鼠标移上去，input边框出现，初步判断，无法判断类型为radio的input
//$(document).ready(function(){ 
//$(".about_qy_tab :input").css("border","1px solid #e58416");	
//$(".about_qy_tab input").hover(
//  function () {
//    $(this).css("border","1px solid #e58416");
//  },
//  function () {
//    $(this).css("border","1px solid #ffeec0");
//  }
//);
//
//}); 


//2012年9月18日新增
//户口本 标注按钮
$(function(){
	$("#biaozhu_btn").hover(
	  function () {
	  $(this).css("background","url(img/biaozhu_btn.jpg) left -22px no-repeat;");
	  },
	  function () {
	  $(this).css("background","url(img/biaozhu_btn.jpg) left top no-repeat;");
	  }
	);

});


//2012年9月20日新增  开始

//单元行和单元格 奇偶换行变色
$(function(){
     $(".main_tab,.main_tab_td tr:even").addClass("even"); //even odd 
     $("tr:odd").addClass("odd"); 
});


//头部搜索按钮 展开与收起控制
$(function() {
	$("#search_arrow").toggle(
	  function () {
		$("#search_box").slideUp(500);
		$(this).css("background","url(img/arrow_down_or_up.jpg) left -10px no-repeat;");

	  },
	  function () {
		$("#search_box").slideDown(500);
		$(this).css("background","url(img/arrow_down_or_up.jpg) left top no-repeat;");
	  }
	);
});

//2012年9月20日新增  结束



