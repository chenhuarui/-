
//AjAX刷新右边展示
$(function () {
    $(".refresh").click(function () {

        var name = $(this).next(":input").val();
        document.getElementById("path").value = "/" + name + "/";

        var url = this.href;

        $.ajax( {
            url: url,
            type: "GET",
            success: function(data){
                $(".right").html(data);
            }
        });
        return false;
    });
});


