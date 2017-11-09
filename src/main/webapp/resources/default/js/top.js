/* 头部的JS,对应文件:top.html */
//智慧安监按钮
$(function () {
    $(".smart_sub").width($(window).width());
    setTimeout(function () { $(".smart_sub").width($(window).width()); }, 200)
    $(window).resize(function () {
        $(".smart_sub").width($(window).width());
    })

    //从上展开
    //        var timer;
    //        $(".smart_logo").hover(function () {
    //            clearTimeout(timer);
    //            $(".smart_sub").slideDown(200);
    //            if ($.browser.msie)
    //                $(".top_shadow").show();
    //            else
    //                $(".top_shadow").fadeIn(200);
    //        }, function () {
    //            timer = setTimeout(function () {
    //                $(".smart_sub").slideUp(200);
    //                if ($.browser.msie)
    //                    $(".top_shadow").hide()
    //                else
    //                    $(".top_shadow").fadeOut(200);
    //            }, 1000);
    //        });

    //从右侧飞入
    var timer;
    $(".smart_logo").hover(function () {
        clearTimeout(timer);
        $("#logo_temp").animate({ top: -30 }, function () {
            $(".smart_sub").animate({ top: 0 });
        })
        if ($.browser.msie)
            $(".top_shadow").show();
        else
            $(".top_shadow").fadeIn(200);
    }, function () {
        timer = setTimeout(function () {
            $(".smart_sub").animate({ top: -30 }, function () {
                $("#logo_temp").animate({ top: 0 });
            });
            if ($.browser.msie)
                $(".top_shadow").hide()
            else
                $(".top_shadow").fadeOut(200);
        }, 1000);
    });



    //菜单优化
    var ul_width = 0;
    $("#ul_menu li").each(function () {
        ul_width += $(this).width() + 10;
    })
    var funResize = function () {
        var div_width = $(".smart_sub").width() - $(".smart_sub ul:first").width() - 150
        $("#div_menu").width(div_width)
        //debugger;
        //var pages = (ul_width - ul_width % div_width) / div_width + (ul_width % div_width != 0 ? 1 : 0);
        var pages = 1
        if (($("#ul_menu").height() / 21 + "").indexOf(".") != -1)
            pages = Number(($("#ul_menu").height() / 21 + "").substr(0, ($("#ul_menu").height() / 21 + "").indexOf(".")))
        else
            pages = $("#ul_menu").height() / 21;
        var index = 1;
        if (pages == 1) $("#btn_asc,#btn_desc").hide();
        else $("#btn_asc,#btn_desc").show();
        if (index == 1) $("#btn_asc").css("background", "url(../resources/default/images/un_arrow_up_smart.jpg)");

        $("#ul_menu").animate({ top: 0 });
        var menuAnimate = function (index) {
            $("#ul_menu").stop().animate({ "margin-top": -21 * (index - 1) });
            if (index == 1) { $("#btn_asc").css("background", "url(../resources/default/images/un_arrow_up_smart.jpg)"); $("#btn_desc").css("background", "url(../resources/default/images/arrow_down_smart.jpg)"); }
            else if (index == pages) { $("#btn_desc").css("background", "url(../resources/default/images/un_arrow_down_smart.jpg)"); $("#btn_asc").css("background", "url(../resources/default/images/arrow_up_smart.jpg)"); }
            else { $("#btn_asc").css("background", "url(../resources/default/images/arrow_up_smart.jpg)"); $("#btn_desc").css("background", "url(../resources/default/images/arrow_down_smart.jpg)"); }
        }
        menuAnimate(index)
        $("#btn_desc").click(function () {
            index++;
            if (index > pages) { index = pages; return; }
            menuAnimate(index)
        })
        $("#btn_asc").click(function () {
            index--;
            if (index < 1) { index = 1; return; };
            menuAnimate(index);
        })
    }
    funResize();
    $(window).resize(function () {
        funResize();
    })

    if ($.browser.msie) {
    	$(".sys_user").css("padding-top", "1px");
        $(".smart_logo").css({ "background-color": "#000", "opacity": 0 });
    }
        
});