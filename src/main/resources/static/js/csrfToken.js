var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

$("#logout-btn").click(function(e){
    e.preventDefault();
    $.ajax({
        url : '/logout',
        type : 'POST',
        data: token,
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            window.location.replace("http://localhost:8080/login");
        },
        error : function(data) {
            console.log(data);
        }
    });

});