// JavaScript Document

//tab切换--2013-11-20新增
$(function(){ 
  var tab_li=$(".tab_list_menu ul li")
	tab_li.click(function(){
		
	$(this).addClass("selected").siblings().removeClass("selected");
  
  var index = tab_li.index(this);
	$(".tab_list_content > div").eq(index).show().siblings().hide(); 

  })

})

$(function () {
    $(".windows_list tr").hover(function () {
        $(this).addClass("w_tr_hover");
    }, function () {
        $(this).removeClass("w_tr_hover");
    });
});