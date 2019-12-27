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
                    getUserData();
                    alert("Ok");
                }
            });
        }

    $('#updateUserDataBtn').click(function (event) {
        event.preventDefault();
        updateUserData();
    });

    $('#newUserPass').click(function () {
        alert("Пароль не должен содержать символов");
    });

    function updateUserPassword() {

        getUserData();

        var old = $("#oldUserPass").val();
        if(password !== old){
            alert("Неверно введен текущий пароль");
            return;
        }

        var n = $("#newUserPass").val();
        var up = $("#updateUserPass").val();
        if (n !== up){
            alert("Новый пароль не совпадает с подтвержденным");
            return;
        }

        $.ajax({
            contentType: "application/json;",
            url: "/api/user/edit/edit_pass",
            type: "PUT",
            data: JSON.stringify(n),
            dataType: 'json',
            async: true,
            cache: false,
            success: function () {
                getUserData();
                alert("Ok");
            }
        });
    }

    $('#updateUserPasswordBtn').click(function (event) {
        event.preventDefault();
        updateUserPassword();
    });

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

});