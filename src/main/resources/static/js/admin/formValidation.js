//Валиадация для форма редактирования
$(document).ready(function () {
    $('#updateUserEmail').focusout(function () {
        var isValid = true;
        var email = $('#updateUserEmail').val();
        var message;
        let result = email.match(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/);
        if (result === null) {
            isValid = false;
            message = "Введите корректный email";
            document.getElementById('email-error')
                .innerHTML = '<span class="error">' + message + '</span>';
        }
        $.ajax({
            type: 'GET',
            url: "/api/admin/check/email",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': $('#updateUserId').val(),
                'email': $('#updateUserEmail').val()
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (isExistEmail) {
                if (isExistEmail) {
                    message = "Такой email уже существует";
                    isValid=false;
                    document.getElementById('email-error')
                        .innerHTML = '<span class="error">' + message + '</span>';
                }
            },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
        if(isValid){
            let error = document.getElementById('email-error');
            if (error !== null) {
                error.innerHTML = '';
            }
        }
    });

    $('#updateUserName').focusout(function () {
        var isValid = true;
        var login = $('#updateUserName').val();
        var message;
        let result = login.match(/^[a-zA-Z][a-zA-Z0-9-_\\.]{2,20}$/);
        if (result === null) {
            isValid = false;
            message = "Логин должен начинаться с буквы и содержать не менее 3 символов";
            document.getElementById('login-error')
                .innerHTML = '<span class="error">' + message + '</span>';
        }
        $.ajax({
            type: 'GET',
            url: "/api/admin/check/login",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': $('#updateUserId').val(),
                'login': $('#updateUserName').val()
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (isExistLogin) {
                if (isExistLogin) {
                    isValid = false;
                    message = "Такой логин уже существует";
                    document.getElementById('login-error')
                        .innerHTML = '<span class="error">' + message + '</span>';
                }
            },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
        if(isValid){
            let error = document.getElementById('login-error');
            if (error !== null) {
                error.innerHTML = '';
            }
        }
    });

    $('#updateUserPass').focusout(function () {
        var message;
        var password = $('#updateUserPass').val();
        let result = password.match(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{6,}$/);
        if (result === null) {
            message = "Пароль должен содержать минимум 6 символа и включать цифры и прописные и строчные буквы";
            document.getElementById('password-error')
                .innerHTML = '<span class="error">' + message + '</span>';
        } else {
            let error = document.getElementById('password-error');
            if (error !== null) {
                error.innerHTML = '';
            }
        }
    });

});
//Валиадация для формы добавления через плагин Jquery validate
$('#addForm').validate({
    rules: {
        email: {
            required: true,
            email: true,
            remote: {
                url: "/api/registration/check/email",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    email: function () {
                        return $("#email").val();
                    },

                },
            }
        },
        login: {
            required: true,
            pattern: /^[a-zA-Z][a-zA-Z0-9-_\.]{2,20}$/,
            remote: {
                url: "/api/registration/check/login",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    login: function () {
                        return $("#login").val();
                    },
                },
            }
        },
        password: {
            required: true,
            minlength: 6,
            pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/
        },
        role: {
            required: true
        }
    },
    messages: {
        email: {
            email: "Введите корректный адресс электронной почты",
            required: "Введите email",
            remote: "Пользователь с таким email уже зарегистрирван"

        },
        login: {
            pattern: "Логин должен начинаться с буквы и содержать не менее 3 символов",
            required: "Введите логин",
            remote: "Пользователь с таким логином уже зарегистрирован"
        },
        password: {
            minlength: "Пароль должен содержать минимум 6 символа и включать цифры и прописные и строчные буквы",
            pattern: "Пароль должен содержать минимум 6 символа и включать цифры и прописные и строчные буквы",
            required: "Введите пароль"
        },
        role: {
            required: "Вы должны выбрать роль для пользователя"
        }
    },
});