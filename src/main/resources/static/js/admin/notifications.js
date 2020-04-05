$(document).ready(function () {

    getAllUsers();

    function getAllUsers() {

        $.ajax({
            type: 'GET',
            url: "/api/admin/all_users",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            async: true,
            cache: false,
            success: function (userList) {
                for(let i = 0; i < userList.length; i++)
                $("#addUser").append(`<option value="${userList[i].id}">${userList[i].login}</option>`)
            }

        });
    }

    getTemplate();

    function getTemplate() {

        $.ajax({
            type: 'GET',
            url: "/api/notification/template",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            async: true,
            cache: false,
            success: function (templates) {
                $("#templateText").val(templates[0].template);
            }

        });
    }

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
                    htmlTable += ('<td id="notificationsMessage" class="notificationElement">' + listNotifications[i].message + '</td>');
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

    $("#editTemplateBtn").click(function (event) {
        event.preventDefault();
        editTemplate();
    });

    function editTemplate() {
        let template = {
            name: "default",
            'template': $("#templateText").val()
        };

        $.ajax({
            type: 'PUT',
            url: "/api/notification/template",

            contentType: 'application/json;',
            data: JSON.stringify(template),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                    getTemplate();
                    getTemplateProcessNotify("default");
                },
            success:
                function () {
                    sendNotification();
                    notification("edit-template" + name,
                        " Шаблон изменен",
                        'notifications-template-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });

    }

    //addNotify
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
                    getTemplateProcessNotify("default");
                    $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("add-notification" + name,
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
                    getTemplateProcessNotify("default");
                    $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("delete-notification" + id,
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
                    getTemplateProcessNotify("default");
                    getTable();
                    getTemplateProcessNotify("default");
                    location.reload()
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
                getTemplateProcessNotify("default");
            },
            complete:
                function () {
                    getTable();
                    getTemplateProcessNotify("default");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    });
});

