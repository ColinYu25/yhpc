//孙路编写 开始
//行背景色
$(function () {
    $(".windows_list tr[this=father]").hover(function () {
        $(this).addClass("w_tr_hover");
    }, function () {
        $(this).removeClass("w_tr_hover");
    });
});

$(function () {
    $(".form_list td input:text.f_input_text").hover(
        function () {
            $(this).addClass("f_i_text_hover_border");
        },
        function () {
            $(this).removeClass("f_i_text_hover_border");
        }
    );

    $(".form_list td input:text.f_input_date").hover(
        function () {
            $(this).addClass("f_i_date_hover_border");
        },
        function () {
            $(this).removeClass("f_i_date_hover_border");
        }
    );

    $(".form_list td input:image").hover(
        function () {
            $(this).fadeTo("fast", 0.8);
        },
        function () {
            $(this).fadeTo("fast", 1);
        }
    );
});

$(function () {
    if ($(".sel:checkbox").hide().length != 0) {//这个判断.避免在加载左边时获取不到右边对象而报错
        var father_tr = $("tr[this='father']")
        //列表上的单击等事件
        $(document).delegate("tr[this='father']", "click", function (e) {//用此方法可以阻止冒泡.在checkbox上点击时不收拢
            e = e || window.event;
            var obj = e.target || e.srcElement;
            try {
                if ($(this).next(".slideDown").height() == 0 || $(this).next(".slideDown").height() > 30)//关闭状态下点击展开,IE下高度为320
                    $(this).next(".slideDown").show().find("div").slideDown().parent().parent().siblings(".slideDown").find("div").slideUp();
                else if (obj.tagName != "INPUT")//展开状态下点击收拢
                    $(this).next(".slideDown").find("div").slideUp();
            }
            catch (e) { }
        });
        father_tr.hover(function () {
            $(this).find("input").show().parent().parent().siblings("tr[this='father']").find("input:checkbox:not(:checked)").hide();
        })
        .mouseleave(function () {
            $(this).find("input:not(:checked)").hide();
        })

        //为列表里的每个div定位
        var $div = $(".list_tool_in > div").width($(window).width());
        $(".list_tool_in").width(($(window).width() + $div.css("padding-left")) * $div.length).height($div.height()).offset({ left: 20 })
        $(".list_tool_content").width($(window).width() + $div.css("padding-left")).height($div.height())
        for (var i = 0; i < $div.length; i++) {
            $div.eq(i).offset({ left: $div.width() * i })
                .attr("name", $div.eq(i).height())//为列表里的每个div标记原始高度
        }


        //$(".list_tool_content").css("border" , "solid 1px #000")
        //工具栏滑动切换
        $("#list_tool_bar li").click(function () {
            var $this = $(this);
            $this.addClass("selected").siblings().removeClass();
            $list = $(".list_tool_content");
            $in = $(".list_tool_in");
            var h = $in.children("div").eq($this.index()).height(); //获取div现在的高度.以确定是否展开状态
            var nh = $in.children("div").eq($this.index()).attr("name"); //获取div的原始高度
            if (h != 0) {//在已展开的状态下.切换选项卡改变高度
                $list.animate({ height: h }, 200, function () {
                    $in.animate({ left: -$div.width() * $this.index() + 20 })
                });                
            }
            else {//在未展开的状态下.切换选项卡同时展开
                $in.animate({ left: -$div.width() * $this.index() + 20 }, 200, function () {
                    $list.height(nh).slideDown(function () {
                        $(".tool_arrow").children("img").attr("src", "img/tool_arrow.jpg");
                    });
                })
            }
            $(".slideDown div").slideUp();

        });

        //点击三角收起工具栏
        $(".tool_arrow").click(function () {
            $list_tool_content = $(".list_tool_content");
            if ($list_tool_content.offset().top != 0) {
                $list_tool_content.slideUp(function () { $(".tool_arrow").children("img").attr("src", "img/tool_arrow_down.jpg") });

            }
            else {
                $list_tool_content.slideDown(function () { $(".tool_arrow").children("img").attr("src", "img/tool_arrow.jpg") });
            }
        })

        //全选
        $("#btnSel").click(function () {
            $sel = $(".sel:checkbox");
            $this = $(this);
            for (var i = 0; i < $sel.length; i++) {
                if ($this.val() == "全    选") {
                    $sel.show().attr("checked", true);
                    $this.val("全不选");
                    break;
                }
                else if ($this.val() == "全不选") {
                    $sel.hide().attr("checked", false);
                    $this.val("全    选");
                    break;
                }
            }
        })
    }
});

$(function () {
    //默认密码
    $("#btnPwd").click(function () { $("#txtPwd").val("000000"); })

    //建立时间
    var d = new Date();
    var year = d.getYear() < 2000 ? d.getYear() + 1900 : d.getYear();
    var month = d.getMonth() + 1;
    var day = d.getDate()
    d = year + "-" + month + "-" + day;
    $("#txtCreate").val(d)

    //过期时间1年
    $("#btnExpired").click(function () { $("#txtExpired").val((year + 1) + "-" + month + "-" + day); })
    //过期时间吴旗县
    $("#btnNull").click(function () { $("#txtExpired").val(""); })
})

var w = $(window).width();
$(window).resize(function () {
    if ($(this).width() != w) {
        var $div = $(".list_tool_in > div").width($(this).width());
        var $in = $(".list_tool_in").width(($(window).width() + $div.css("padding-left")) * $div.length);
        var $list = $(".list_tool_content").width($(window).width() + $div.css("padding-left"));
        var index = $("#list_tool_bar li.selected").index();
        $in.offset({ left: - $div.width() * index + 20 })
        w = $(this).width();
    }
})

//孙路编写 结束


//柴玉龙编写 开始
//登录口input线框背景色
$(function(){ 
  $(".login_input").hover(
  function () {
    $(this).css("border","1px solid #d17702");
	$(this).css("background","#f6e9c6");
  },
  function () {
    $(this).css("border","1px solid #b9bdc5");
	$(this).css("background","#ecf1f3");
  });
}); 


//左边框架 缩起与展开
$(function () {
    var _top = top.document.getElementById('center');
    $("#mid_bg").toggle(
	function () {
	    _top.cols = "0,7,*";
	    $("#arrow_img").children().attr("src", "img/arrow_right.jpg");
	    $("#arrow_img").children().attr("title", "展开");
	},
	function () {
	    _top.cols = "152,7,*";
	    $("#arrow_img").children().attr("src", "img/arrow_left.jpg");
	    $("#arrow_img").children().attr("title", "收起");
	}
  );
}); 


//框架三角图片距离上面的距离
$(window).resize(function(){
  $("#arrow_img").css("margin-top", $(window).height()/2-30);
});







