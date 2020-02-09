$(document).ready(function () {


    $("#sendMessage").click(function (event) {
        event.preventDefault();
        sendMessage();
        $("#idConversation").val("");
        $("#message").val("");
    });

    function sendMessage() {

        var conversation = {
            id: $("#idConversation").val(),
            message: $("#message").val(),

        };

        $.ajax({
            type: 'POST',
            url: "/api/v1/admin/vk_bot/send",
            contentType: 'application/json;',
            data: JSON.stringify(conversation),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            complete:
                function () {

                },
            success:
                function () {
                    notification("",
                        "Отчет отправлен в беседу " + $("#idConversation").val(),
                        'conversation');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

});