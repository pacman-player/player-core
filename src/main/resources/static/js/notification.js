var stompClient = null;

function connect() {
    var socket = new SockJS('/notification');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("Соединенеие установлено---" + frame);
        clientSub();
    });

}

function clientSub() {
    stompClient.subscribe("/topic/notification", function () {
        getNotificationNumber();
    });
}

function sendNotification() {
    stompClient.send("/app/notification", {}, "Send message ");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//Уведомления показать номер
function getNotificationNumber() {
    $.ajax({
        type: 'GET',
        url: "/api/admin/notification",
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        async: true,
        cache: false,
       dataType: 'JSON',
        success: function (listNotification) {
            let text = $('#notification').text();
            var numb = 0;
            for (var i = 0; i < listNotification.length; i++) {
                if (listNotification[i].flag === true) {
                    numb++;
                }
            }
            $("#notification").text(numb + "  Уведомлений");
        },
        error:
            function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
    });
}


$(document).ready(function () {

    connect();

    getNotificationNumber();

    //показать уведомления
    $(document).on('click', '#notification', function (event) {
        event.preventDefault();
        if ($("#listNotification").css("display") == "none") {
            getNotification();
        } else {
            $("#listNotification").css("display", "none");
        }

    });

    function getNotification() {
        $.ajax({
            type: 'GET',
            url: "/api/admin/notification",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
          //  dataType: 'JSON',
            success: function (listNotification) {
                let notification = '';
                if (0 < listNotification.length) {
                    let length = listNotification.length - 1;
                    for (let i = 0; i < listNotification.length; i++) {
                        let active = listNotification[length-i].flag ? "btnNotificationActive" : "btnNotificationNotActive";
                            notification += (`<div class="elemNotification" id="${listNotification[length-i].id}"><input type="button"
                                class="${active}"><span class="notificationElement">${listNotification[length-i].message}</span></div>`);
                    }
                    getTemplateProcessNotify("default");
                    $("#listNotification").html(notification);
                }
            },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
        $("#listNotification").css("display", "block");
        $(this).parent().next().slideToggle();
    }

    //скрытие выпадающего списка по клику где угодно
    $(document).mouseup(function (e) {
        var div = $("#listNotification");
        if (!div.is(e.target) // если клик был не по нашему блоку
            && div.has(e.target).length === 0) { // и не по его дочерним элементам
            div.css("display", "none");
        }
    });

    //пометить как прочитаное
    $(document).on('click', '.btnNotificationActive', function (event) {
        event.preventDefault();
        $(this).attr("class", "btnNotificationNotActive");
        readNotification($(this).closest("div").attr("id"));
       // getNotificationNumber();
    });

    //пометить как прочитаное
    function readNotification(id) {
        $.ajax({
            type: 'post',
            url: "/api/admin/notification/read",
            contentType: 'application/json;',
            data: JSON.stringify(id),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            //dataType: 'JSON',
            success: function () {
                getNotificationNumber();
            },
        });
    }
});