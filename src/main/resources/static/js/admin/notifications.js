$(document).ready(function () {

    getTable();

    function getTable() {

        $.ajax({
            type: 'GET',
            url: "/api/admin/notification/all",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            async: true,
            cache: false,
            success: function (listNotifications) {
                let htmlTable = "";
                $("#notificationTable tbody").empty();

                for (let i = 0; i < listNotifications.length; i++) {
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="notificationsId">' + listNotifications[i].id + '</td>');
                    htmlTable += ('<td id="notificationsMessage">' + listNotifications[i].message + '</td>');
                    htmlTable += ('<td id="notificationsUser">' + listNotifications[i].user.login + '</td>');
                    htmlTable += ('<td><button id="editNotificationsBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editNotifications">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteNotifications" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }
                $("#notificationTable tbody").append(htmlTable);
            }

        });
    };

    $("#addGlobalNotificationBtn").click(function (event) {
        event.preventDefault();
        addAdminNotification();
        $(':input', '#addForm').val('');
    });

    function addAdminNotification() {

        let addNotification = {
            'message': $("#addMessage").val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/admin/notification/global",

            contentType: 'application/json;',
            data: JSON.stringify(addNotification),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("add-user" + name,
                        " Уведомление " + name + " добавлено ",
                        'messages-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //addMessage
    $("#addNotificationBtn").click(function (event) {
        event.preventDefault();
        addNotification();
        $(':input', '#addForm').val('');
    });

    function addNotification() {

        let addNotification = {
            'message': $("#addMessage").val(),
            'user': {
               id: $("#addUser").val()
            }
        };

        $.ajax({
            type: 'POST',
            url: "/api/admin/notification",

            contentType: 'application/json;',
            data: JSON.stringify(addNotification),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("add-user" + name,
                        " Уведомление " + name + " добавлено ",
                        'messages-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //deleteForm
    $(document).on('click', '#deleteNotifications', function () {
        let id = $(this).closest("tr").find("#notificationsId").text();
        deleteNotification(id);
    });

    function deleteNotification(id) {
        let deleteNotification = {
            id: id
        };

        $.ajax({
            type: 'delete',
            url: "/api/admin/notification",
            contentType: 'application/json;',
            data: JSON.stringify(deleteNotification),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("delete-user" + id,
                        "  Уведомление c id " + id + " удалено",
                        'messages-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //updateForm
    $("#editNotificationsBtn").click(function (event) {
        event.preventDefault();
        updateForm();
        $(this).parent().find("#closeEditNotifications").click();
    });

    function updateForm() {
        let notification = {
            'id': $("#updateNotificationsId").val(),
            'message': $("#updateNotificationsMessage").val(),
        };

        $.ajax({
            type: 'put',
            url: "/api/admin/notification",

            contentType: 'application/json;',
            data: JSON.stringify(notification),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //modal form заполнение
    $(document).on('click', '#editNotificationsBtn', function () {
        let id = ($(this).closest("tr").find("#notificationsId").text());
        $("#updateNotificationsId").val(id);

        $.ajax({
            type: 'get',
            url: "/api/admin/notification/" + id,

            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            success: function(notification) {
                $("#updateNotificationsUser").val(notification.user.login);
                $("#updateNotificationsMessage").val(notification.message);
            },
            complete:
                function () {
                    getTable();
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    });
});