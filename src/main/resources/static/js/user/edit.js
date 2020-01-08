$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();

    var password;
    getUserData();

    function getUserData() {
        $.ajax({
            url: '/api/user/edit/get_user',
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
                url: "/api/user/edit/edit_data",
                type: "PUT",
                data: JSON.stringify(newUser),
                dataType: 'json',
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
        if(password !== oldPass){
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
            url: "/api/user/edit/edit_pass",
            type: "PUT",
            data: JSON.stringify(pass2),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'json',
            async: true,
            cache: false
        });
        alert("Пароль изменен");
        location.reload();
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
            url: "/api/admin/code_check",
            contentType: 'application/json;',
            data: JSON.stringify(code),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
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
        checkForm();
    });

});