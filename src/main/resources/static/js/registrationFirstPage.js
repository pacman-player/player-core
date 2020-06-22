let registrationFirstForm;
$(function () {
    registrationFirstForm = $("#registrationFirstForm");
    registrationFirstForm.submit(function (event) {
        event.preventDefault();
        if ($('#registrationFirstForm').valid()) {
            $.post("/api/registration/user", registrationFirstForm.serialize(), function() {
//                location.assign(location.origin + "/reg_check");
                    window.location.href = '/registration/reg_check?login=' + $("#login").val();
            });
        }
    })
});

$('#registrationFirstForm').validate({
    rules: {
        email: {
            email: true,
            required: true,
            remote: {
                url: "/api/registration/check/email",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    email: function () {
                        return $("#email").val();
                    }
                },
            }
        },
        login: {
            required: true,
            pattern: /^[a-zA-Z][a-zA-Z0-9-_\\.]{2,20}$/,
            remote: {
                url: "/api/registration/check/login",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    login: function () {
                        return $("#login").val();
                    }
                },
            }
        },
        password: {
            required: true,
            minlength: 6,
            pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/
        },
        confirmPassword: {
            required: true,
            equalTo: "#password"
        },
        terms: {
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
        confirmPassword: {
            equalTo: "Пароли должны совпадать",
            required: "Подтвердите пароль"
        },
        terms: {
            required: "Вы должны принять условия пользования"
        }
    }
});