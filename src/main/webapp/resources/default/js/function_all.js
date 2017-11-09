// JavaScript Document

//�����bar��������������������չ��
//$(function(){
//$("div[tit=openorclose]").toggle(function(){
//$(this).parent().next().slideDown(500); //slideUp  fadeOut
//$(this).attr("class","list_title_arrow_up")
//},function(){
//$(this).parent().next().slideUp(500); //slideDown fadeIn
//$(this).attr("class","list_title_arrow_down")
//});  
//});

//�����bar��������bar������������չ��
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

$(function(){ 
   var get_li=$(".about_menu ul li");
       get_li.click(function(){
$(this).addClass("selected").siblings().removeClass("selected");
   
   var index = get_li.index(this);
     $(".about_content > div").eq(index).slideDown(500).siblings().slideUp(500); 
      })

})



$(function(){ 
  $(".close").click(function(){
							 $("#window_about_qy").animate({left: 50, opacity: 'hide'}, 500);

							 })

})

//���Ԫ��
$(document).ready(function() {
$(".main_tab tr").hover(function() {
		$(this).addClass("blue");
	}, function() {
		$(this).removeClass("blue");
	});
});

//���Ԫ�� 2012��9��20������
$(document).ready(function() {
  $(".main_tab_td tr td").hover(function() {
  $(this).addClass("blue_td");
  }, function() {
  $(this).removeClass("blue_td");
  });
});



//��� ����ͼ�л��鿴
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


////����л� ��8
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


//���ҿ���л� ��8
$(function(){ 
		   
$(".jqResize").toggle(

  function () { 
    $(".jqResize > img").attr("src","../resources/default/img/arrow_right.gif");
	window.top.document.getElementById("center").cols="0,*"
  },
  function () {
    $(".jqResize > img").attr("src","../resources/default/img/arrow_left.gif");
	window.top.document.getElementById("center").cols="263,*"
  }
  
);

})
//ҵ����չ��������
$(document).ready(function(){
   $("#down_img01").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_01_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_01.jpg");
   });
});

$(document).ready(function(){
   $("#down_img02").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_02_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_02.jpg");
   });
});

$(document).ready(function(){
   $("#down_img03").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_03_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_03.jpg");
   });
});

$(document).ready(function(){
   $("#down_img04").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_04_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_04.jpg");
   });
});

$(document).ready(function(){
   $("#down_img05").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_05_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_05.jpg");
   });
});

$(document).ready(function(){
   $("#down_img06").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_06_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_zy_gz_06.jpg");
   });
});



//children ֻ�� ��һ���������ã�����һ������һ���Ҳ����ġ����취�����ҵ��������һ��div d_arrow��Ȼ��ȡ�����һ����img��src
$(document).ready(function(){
   $(".down_bar").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children(".d_arrow").children().attr("src","icon/arrow_24_down.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children(".d_arrow").children().attr("src","icon/arrow_24_up.jpg");
   });
});

//������ڵ���������
//$(document).ready(function(){
//   $(".d_arrow").toggle(function(){
//     $(this).parent().next().slideDown('fast');
//	 $(this).children().attr("src","icon/arrow_24_down.png");
//   },function(){
//     $(this).parent().next().slideUp('fast');
//	 $(this).children().attr("src","icon/arrow_24_up.png");
//   });
//});

//children ֻ�� ��һ���������ã�����һ������һ���Ҳ����ġ�

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
	 $(this).children().attr("src","../resources/default/img/img_bzh_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_bzh.jpg");
   });
});

$(document).ready(function(){
   $("#down_img09").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_yhpc_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_yhpc.jpg");
   });
});

$(document).ready(function(){
   $("#down_img10").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_wxy_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_wxy.jpg");
   });
});

$(document).ready(function(){
   $("#down_img11").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_yjya_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_yjya.jpg");
   });
});

$(document).ready(function(){
   $("#down_img12").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_apbg_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_apbg.jpg");
   });
});


$(document).ready(function(){
   $("#down_img13").toggle(function(){
     $(this).next().slideDown('fast');
	 $(this).children().attr("src","../resources/default/img/img_yzd_arrow_up.jpg");
   },function(){
     $(this).next().slideUp('fast');
	 $(this).children().attr("src","../resources/default/img/img_yzd.jpg");
   });
});

////ҵ����չ��������
//$(document).ready(function(){
//   $(".down_img").toggle(function(){
//     $(this).next().slideDown('fast');
//	 
//   },function(){
//     $(this).next().slideUp('fast');
//	
//   });
//});

//login_btn 2012��4��9������
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

//�Ľ���ͷ���˵� -��ʼ
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
//�Ľ���ͷ���˵�  -��ʼ

$(document).ready(function(){
   $(".down_img").toggle(function(){
     $(this).next().slideDown('fast');
   },function(){
     $(this).next().slideUp('fast');
   });
});

//��ͼҳ������б?����ʽ
$(document).ready(function() {
$(".map_left tr").hover(function() {
 
  $(this).addClass("blue");
 }, function() {
  $(this).removeClass("blue");
 });
});

//2012��5��7������
//��ҵ���ڱ� �����ı��� �Ӽ�ͼƬ͸���
$(document).ready(function(){   
    $(".bigger,.smaller").fadeTo("fast", 1.0);  
  
    $(".bigger,.smaller").hover(function(){   
        $(this).fadeTo("fast", 0.5); 
    },function(){   
        $(this).fadeTo("fast", 1.0); 
    });   
}); 


//2012��5��8������
//�������ȥ��input�߿���֣�����(����Ϊ radio ��input��
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



//�������ȥ��input�߿���֣����жϣ��޷��ж�����Ϊradio��input
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


//2012��9��18������
//���ڱ� ��ע��ť
$(function(){
	$("#biaozhu_btn").hover(
	  function () {
	  $(this).css("background","url(../resources/default/img/biaozhu_btn.jpg) left -22px no-repeat;");
	  },
	  function () {
	  $(this).css("background","url(../resources/default/img/biaozhu_btn.jpg) left top no-repeat;");
	  }
	);

});


//2012��9��20������  ��ʼ

//��Ԫ�к͵�Ԫ�� ��ż���б�ɫ
$(function(){
     $(".main_tab,.main_tab_td tr:even").addClass("even"); //even odd 
     $("tr:odd").addClass("odd"); 
});


//ͷ������ť չ�����������
$(function() {
	$("#search_arrow").toggle(
	  function () {
		$("#search_box").slideUp(500);
		$(this).css("background","url(../resources/default/img/arrow_down_or_up.jpg) left -10px no-repeat;");

	  },
	  function () {
		$("#search_box").slideDown(500);
		$(this).css("background","url(../resources/default/img/arrow_down_or_up.jpg) left top no-repeat;");
	  }
	);
});

//2012��9��20������  ����



