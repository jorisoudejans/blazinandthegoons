
function blazeit() {

    for (var i = 0; i < 15; i++) {
        var top = Math.random() * 500 + 100;
        var left = i * 100;

        var element = $('<img src="http://localhost:9000/assets/images/420blazeit.png" class="blazer" style="position: absolute; top: ' + top + 'px; left: ' + left + 'px; width: 100px; height: 100px;" />');

        $('body').append(element);

    }

    rotation();

}

var rotation = function (){
    $(".blazer").each(function () {
        $(this).rotate({
            angle:0,
            animateTo:360,
            callback: rotation,
            easing: function (x,t,b,c,d){        // t: current time, b: begInnIng value, c: change In value, d: duration
                return c*(t/d)+b;
            }
        });
    });
};