//��·��д ��ʼ
//�б���ɫ
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
    if ($(".sel:checkbox").hide().length != 0) {//����ж�.�����ڼ������ʱ��ȡ�����ұ߶��������
        var father_tr = $("tr[this='father']")
        //�б��ϵĵ������¼�
        $(document).delegate("tr[this='father']", "click", function (e) {//�ô˷���������ֹð��.��checkbox�ϵ��ʱ����£
            e = e || window.event;
            var obj = e.target || e.srcElement;
            try {
                if ($(this).next(".slideDown").height() == 0 || $(this).next(".slideDown").height() > 30)//�ر�״̬�µ��չ��,IE�¸߶�Ϊ320
                    $(this).next(".slideDown").show().find("div").slideDown().parent().parent().siblings(".slideDown").find("div").slideUp();
                else if (obj.tagName != "INPUT")//չ��״̬�µ����£
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

        //Ϊ�б����ÿ��div��λ
        var $div = $(".list_tool_in > div").width($(window).width());
        $(".list_tool_in").width(($(window).width() + $div.css("padding-left")) * $div.length).height($div.height()).offset({ left: 20 })
        $(".list_tool_content").width($(window).width() + $div.css("padding-left")).height($div.height())
        for (var i = 0; i < $div.length; i++) {
            $div.eq(i).offset({ left: $div.width() * i })
                .attr("name", $div.eq(i).height())//Ϊ�б����ÿ��div���ԭʼ�߶�
        }


        //$(".list_tool_content").css("border" , "solid 1px #000")
        //�����������л�
        $("#list_tool_bar li").click(function () {
            var $this = $(this);
            $this.addClass("selected").siblings().removeClass();
            $list = $(".list_tool_content");
            $in = $(".list_tool_in");
            var h = $in.children("div").eq($this.index()).height(); //��ȡdiv���ڵĸ߶�.��ȷ���Ƿ�չ��״̬
            var nh = $in.children("div").eq($this.index()).attr("name"); //��ȡdiv��ԭʼ�߶�
            if (h != 0) {//����չ����״̬��.�л�ѡ��ı�߶�
                $list.animate({ height: h }, 200, function () {
                    $in.animate({ left: -$div.width() * $this.index() + 20 })
                });                
            }
            else {//��δչ����״̬��.�л�ѡ�ͬʱչ��
                $in.animate({ left: -$div.width() * $this.index() + 20 }, 200, function () {
                    $list.height(nh).slideDown(function () {
                        $(".tool_arrow").children("img").attr("src", "img/tool_arrow.jpg");
                    });
                })
            }
            $(".slideDown div").slideUp();

        });

        //����������𹤾���
        $(".tool_arrow").click(function () {
            $list_tool_content = $(".list_tool_content");
            if ($list_tool_content.offset().top != 0) {
                $list_tool_content.slideUp(function () { $(".tool_arrow").children("img").attr("src", "img/tool_arrow_down.jpg") });

            }
            else {
                $list_tool_content.slideDown(function () { $(".tool_arrow").children("img").attr("src", "img/tool_arrow.jpg") });
            }
        })

        //ȫѡ
        $("#btnSel").click(function () {
            $sel = $(".sel:checkbox");
            $this = $(this);
            for (var i = 0; i < $sel.length; i++) {
                if ($this.val() == "ȫ    ѡ") {
                    $sel.show().attr("checked", true);
                    $this.val("ȫ��ѡ");
                    break;
                }
                else if ($this.val() == "ȫ��ѡ") {
                    $sel.hide().attr("checked", false);
                    $this.val("ȫ    ѡ");
                    break;
                }
            }
        })
    }
});

$(function () {
    //Ĭ������
    $("#btnPwd").click(function () { $("#txtPwd").val("000000"); })

    //����ʱ��
    var d = new Date();
    var year = d.getYear() < 2000 ? d.getYear() + 1900 : d.getYear();
    var month = d.getMonth() + 1;
    var day = d.getDate()
    d = year + "-" + month + "-" + day;
    $("#txtCreate").val(d)

    //����ʱ��1��
    $("#btnExpired").click(function () { $("#txtExpired").val((year + 1) + "-" + month + "-" + day); })
    //����ʱ��������
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

//��·��д ����


//��������д ��ʼ
//��¼��input�߿򱳾�ɫ
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


//��߿�� ������չ��
$(function () {
    var _top = top.document.getElementById('center');
    $("#mid_bg").toggle(
	function () {
	    _top.cols = "0,7,*";
	    $("#arrow_img").children().attr("src", "img/arrow_right.jpg");
	    $("#arrow_img").children().attr("title", "չ��");
	},
	function () {
	    _top.cols = "152,7,*";
	    $("#arrow_img").children().attr("src", "img/arrow_left.jpg");
	    $("#arrow_img").children().attr("title", "����");
	}
  );
}); 


//�������ͼƬ��������ľ���
$(window).resize(function(){
  $("#arrow_img").css("margin-top", $(window).height()/2-30);
});







