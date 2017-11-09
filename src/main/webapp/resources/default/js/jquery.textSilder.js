/**
 * @author feiwen
 */
(function($){
	$.fn.textSlider = function(settings){    
            settings = jQuery.extend({
                speed : "normal",
                line : 2,
                timer : 3000
            }, settings);
            return this.each(function() {
                $.fn.textSlider.scllor( $( this ), settings );
            });
        };
	$.fn.textSlider.scllor = function($this, settings){
            var ul = $("table:eq(0)",$this );
            var timerID;
            var li = ul.children();
            var liHight=$("table:eq(0) tr",$this ).height();

            var upHeight=0-settings.line*liHight;

            var scrollUp=function(){
                ul.animate({marginTop:upHeight},settings.speed,function(){
                    for(i=0;i<settings.line;i++){
                        ul.find("tr:first",$this).appendTo(ul);
                    }
                    ul.css({marginTop:0});
                });
            };
            var autoPlay=function(){
                timerID = window.setInterval(scrollUp,settings.timer);
            };
            var autoStop = function(){
                window.clearInterval(timerID);
            };
            
            ul.hover(autoStop,autoPlay).mouseout();
	};
})(jQuery);