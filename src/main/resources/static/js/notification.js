$(document).ready(function () {


    getNotificationNumber();
    //Уведомления
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
                $("#notification").text(numb + " " + text);
            }
        });
    }

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
            dataType: 'JSON',
            success: function (listNotification) {
                var notification = '';
                if (0 < listNotification.length) {
                    for (var i = 0; i < listNotification.length; i++) {
                        if (listNotification[i].flag === true) {
                            notification += ('<div class="elemNotification" id="' + listNotification[i].id + '"><input type = "button" ' +
                                'class = "btnNotificationActive" >' + listNotification[i].message + '</div>');
                        } else {
                            notification += ('<div class="elemNotification" id="' + listNotification[i].id + '"><input type = "button" ' +
                                'class = "btnNotificationNotActive" >' + listNotification[i].message + '</div>');
                        }
                    }
                    $("#listNotification").html(notification);
                }
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
            dataType: 'JSON',

        });

        let text = $('#notification').text();
        let numb = $('#notification').html().replace(/[^+\d]/g, '');
        let str = (numb - 1) + " Уведомлений";
        $("#notification").text(str);

    }

    //сокет соединение
    var socet = new SockJS('/ws');
    stompClient = Stomp.over(socet);
    stompClient.connect({}, onConnected, onError);


});