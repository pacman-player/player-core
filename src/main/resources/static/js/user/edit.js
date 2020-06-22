$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();

    var password;
    getUserData();

    function getUserData() {
        $.ajax({
            url: '/api/user/get_user',
            method: "GET",
            dataType: "json",
            success: function (data) {
                $('#login').val(data.login);
                $('#email').val(data.email);
                password = data.password;
            }
        });
    }
        function updateUserData() {
            var newUser = {
                "login":$('#login').val(),
                "email":$('#email').val()
            };

            if(newUser.login === "") {
                alert("Введите логин");
                return;
            }
            if(newUser.email === ""){
                alert("Введите почту");
                return;
            }

            $.ajax({
                contentType: "application/json;",
                url: "/api/user/edit_data",
                type: "PUT",
                data: JSON.stringify(newUser),
                async: true,
                cache: false,
                success: function () {
                    alert("Данные изменены");
                    getUserData();
                },
                error: function () {
                    alert("Пользователь с такими данными уже существует");
                    getUserData();
                }
            });
        }

    function updateUserPassword() {

        getUserData();

        var oldPass = $("#oldUserPass").val();
        $.ajax({
            url: "/api/user/get_encrypted_pass",
            type: "POST",
            contentType: "application/json",
            data: {"oldPass": oldPass, "newPass": password},
            success: function (isSame) {
                alert(isSame);
            oldPass = isSame;
            }
        });
        if(oldPass === false){
            alert("Неверно введен текущий пароль");
            return;
        }

        var newPass = $("#newUserPass").val();
        var checkPass = $("#updateUserPass").val();
        if (newPass !== checkPass){
            alert("Новый пароль не совпадает с подтвержденным");
            return;
        }

        var pass1 = newPass.replace(/"/g, '##@##');
        var pass2 = pass1.replace(/\\/g, '##@@##');

        $.ajax({
            contentType: "application/json;",
            url: "/api/user/edit_pass",
            type: "PUT",
            data: JSON.stringify(pass2),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            success: function () {
                alert("Пароль изменен");
                $("#editUserPass").modal('hide');
                $("#checkUserCode").modal('hide');
                // location.reload();
            },
            error: function () {
                alert("Не удалось изменить пароль");
            }
        });
    }

    function showLinkAdmin() {
        $.ajax({
            type: "post",
            url: "/api/user/show_admin",

            success: function (role) {
                if (role !== "admin") {
                    $("#adminLink").hide();
                }
            }

        });

    }

    function checkForm() {

        var code = $("#checkUserCodeId").val();

        $.ajax({
            type: 'PUT',
            url: "/api/user/code_check",
            contentType: 'application/json;',
            data: JSON.stringify(code),
            async: true,
            cache: false,
            success: function () {
                alert("Код верен");
                $("#editUserPass").modal('show');
            },
            error: function () {
                alert("Проверочный код не верен");
            }
        });

    }

        function sendMail() {
            var mess = "start";
            $.ajax({
                type: 'PUT',
                url: "/api/user/send_mail",
                contentType: 'application/json;',
                data: JSON.stringify(mess),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                async: true,
                cache: false,
            });
        }


    $('#updateUserDataBtn').click(function (event) {
        event.preventDefault();
        updateUserData();
    });

    $('#updateUserPasswordBtn').click(function (event) {
        event.preventDefault();
        updateUserPassword();
    });

    $("#checkUserPasswordBtn").click(function (event) {
        event.preventDefault();
        sendMail();
    });

    $("#checkUserCodeBtn").click(function (event) {
        event.preventDefault();
        checkForm();
    });

});