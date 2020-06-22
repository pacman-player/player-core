$(document).ready(function () {

    getTable();

    function getTable() {

        $.ajax({
            type: 'GET',
            url: "/api/admin/message/all_messages",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            success: function (listMessages) {
                var htmlTable = "";
                //  $("#MessagesTable #list").remove();
                $("#messagesTable tbody").empty();

                for (var i = 0; i < listMessages.length; i++) {
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="messagesId">' + listMessages[i].id + '</td>');
                    htmlTable += ('<td id="messagesName">' + listMessages[i].name + '</td>');
                    htmlTable += ('<td id="messagesTemplate">' + listMessages[i].template + '</td>');
                    htmlTable += ('<td><button id="editMessagesBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editMessages">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteMessages" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }
                // $("#getMessagesTable").after(htmlTable);
                $("#messagesTable tbody").append(htmlTable);
            }

        });
    };

    //addMessage
    $("#addMessageBtn").click(function (event) {
        event.preventDefault();
        addMessage();
        $(':input', '#addForm').val('');
    });

    function addMessage() {

        var message = {
            'name': $("#addName").val(),
            'template': $("#addTemplate").val(),
        };

         $.ajax({
             type: 'POST',
                url: "/api/admin/message/add_message",

                     contentType: 'application/json;',
                     data: JSON.stringify(message),
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
                                 " Сообшение " + name+ " добавлен ",
                                 'messages-panel');
                         },
                     error:
                         function (xhr, status, error) {
                             alert(xhr.responseText + '|\n' + status + '|\n' + error);
                         }
                 });

        //  $("#tab-messages-panel").tab('show');
        // location.reload();
    }

    //deleteForm
    $(document).on('click', '#deleteMessages', function () {
        var id = $(this).closest("tr").find("#messagesId").text();
        deleteUser(id);
       // getTable();
    });

    function deleteUser(id) {
        $.ajax({
            type: 'delete',
            url: "/api/admin/message/delete_message",
            contentType: 'application/json;',
            data: JSON.stringify(id),
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
                        "  сообщение c id " + id + " удален",
                        'messages-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
        // location.reload();
    };

    //updateForm
    $("#editMessagesBtn").click(function (event) {
        event.preventDefault();
        updateForm();
        $(this).parent().find("#closeEditMessages").click();
    });

    function updateForm() {
        var message = {
            'id': $("#updateMessagesId").val(),
            'name': $("#updateMessagesName").val(),
            'template': $("#updateMessagesTemplate").val()
        };

        $.ajax({
            type: 'put',
            url: "/api/admin/message/update_message",

            contentType: 'application/json;',
            data: JSON.stringify(message),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                   // $("#tab-messages-panel").tab('show');
                },
            success:
                function () {
                 sendNotification();
                    notification("add-user" + name,
                        " ообщение " + name+ " обнавлен ",
                        'messages-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });

      //  location.reload();
    };

    //modal form заполнение
    $(document).on('click', '#editMessagesBtn', function () {
        $("#updateMessagesId").val($(this).closest("tr").find("#messagesId").text());
        $("#updateMessagesName").val($(this).closest("tr").find("#messagesName").text());
        $("#updateMessagesTemplate").val($(this).closest("tr").find("#messagesTemplate").text());
    });
});