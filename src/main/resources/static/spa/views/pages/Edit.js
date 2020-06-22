let Edit = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div id="my-selection" role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 ">\n' +
            '                <H3> Редактирование профиля </H3>\n' +
            '            </div>\n' +
            '                <div class="col-sm-4">\n' +
            '                <form id="edit-data-form">\n' +
            '                        <div class="form-group text-center edit-form">\n' +
            '                            <h4>Персональные данные</h4>\n' +
            '                            <label for="login">Логин</label>\n' +
            '                            <input id="login" class="form-control" type="text" name="login" required="" />\n' +
            '                            <label for="email">Почта</label>\n' +
            '                            <input id="email" class="form-control" type="email" name="email" required="1"/>\n' +
            '                            <button id="updateUserDataBtn" class="btn btn-primary" type="submit">Сохранить</button>\n' +
            '                        </div>\n' +
            '                    </form>\n' +
            '                </div>\n' +
            '                <div class="col-sm-4">\n' +
            '                    <form id="edit-password-form">\n' +
            '                        <div class="form-group text-center edit-form">\n' +
            '                            <h4>Сменить пароль</h4>\n' +
            '                            <h5>Для смены пароля нажмите на кнопку ниже\n' +
            '                                и в открывшемся окне введите код подтверждения,\n' +
            '                                который мы отправили на вашу электронную почту</h5>\n' +
            '                            <button id="checkUserPasswordBtn" data-toggle="modal" data-target="#checkUserCode" class="btn btn-primary" type="button">Отправить код</button>\n' +
            '                        </div>\n' +
            '                    </form>\n' +
            '                </div>\n' +
            '                <!--модалка - проверка кода-->\n' +
            '                <div id="checkUserCode" class="modal fade">\n' +
            '                    <div class="modal-dialog">\n' +
            '                        <div class="modal-content">\n' +
            '                            <div class="modal-header">\n' +
            '                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\n' +
            '                                <h4 class="modal-title">Проверка кода</h4>\n' +
            '                            </div>\n' +
            '                            <div class="modal-body">\n' +
            '                                <div class="container-fluid">\n' +
            '                                    <div class="row">\n' +
            '                                        <div class="col-lg-6 col-md-10 col-xs-10 col-lg-offset-3 col-xs-offset-2">\n' +
            '                                            <form id="code-form">\n' +
            '                                                <div class="form-group text-center edit-form">\n' +
            '                                                    <label for="checkUserCodeId">Введите проверочный код</label>\n' +
            '                                                    <input id="checkUserCodeId" class="form-control" type="text"\n' +
            '                                                           name="code" required=""/>\n' +
            '                                                    <div class="modal-footer">\n' +
            '                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>\n' +
            '                                                    <button id="checkUserCodeBtn" class="btn btn-primary" type="submit" data-dismiss="modal" aria-hidden="true">Проверить</button>\n' +
            '                                                    </div>\n' +
            '                                                </div>\n' +
            '                                            </form>\n' +
            '                                        </div>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <!--модалка - редактирование кода-->\n' +
            '                <div id="editUserPass" class="modal fade">\n' +
            '                    <div class="modal-dialog">\n' +
            '                        <div class="modal-content">\n' +
            '                            <div class="modal-header">\n' +
            '                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\n' +
            '                                <h4 class="modal-title">Проверка кода</h4>\n' +
            '                            </div>\n' +
            '                            <div class="modal-body">\n' +
            '                                <div class="container-fluid">\n' +
            '                                    <div class="row">\n' +
            '                                        <div class="col-lg-6 col-md-10 col-xs-10 col-lg-offset-3 col-xs-offset-2">\n' +
            '                                            <form id="update-form">\n' +
            '                                                <div class="form-group text-center edit-form">\n' +
            '                                                    <label for="oldUserPass">Текущий пароль</label>\n' +
            '                                                    <input id="oldUserPass" class="form-control"  type="password" name="password"\n' +
            '                                                           required />\n' +
            '                                                    <label for="newUserPass">Новый пароль</label>\n' +
            '                                                    <input id="newUserPass" class="form-control" type="password" name="password"\n' +
            '                                                           required/>\n' +
            '                                                    <label for="updateUserPass">Подтвердите пароль</label>\n' +
            '                                                    <input id="updateUserPass" class="form-control" type="password" name="password"\n' +
            '                                                           required>\n' +
            '                                                    <button id="updateUserPasswordBtn" class="btn btn-primary" type="submit" data-dismiss="modal" aria-hidden="true">Сохранить</button>\n' +
            '                                                </div>\n' +
            '                                            </form>\n' +
            '                                        </div>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '        </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
        $(document).ready(function () {

            // //доступ к  ссылки админа
            // showLinkAdmin();

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
                    },
                    async: false
                });
            }

            function updateUserData() {
                var newUser = {
                    "login": $('#login').val(),
                    "email": $('#email').val()
                };

                if (newUser.login === "") {
                    alert("Введите логин");
                    return;
                }
                if (newUser.email === "") {
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
                var ans = true;
                $.ajax({
                    url: "/api/user/get_encrypted_pass",
                    type: "POST",
                    //               context: document.getElementById('#ajax'),
                    data: {"nextPass": oldPass, "prevPass": password},
                    success: function (isSame) {
                        if (isSame === true) {
                            passEquels();
                        }
                        if (isSame === false) {
                            alert("Неверно введен текущий пароль");
                            $("#updateUserPass").modal('hide');
                            setTimeout(function () {
                                $('.modal-backdrop').remove();
                            }, 2000);
                            return;
                        }
                    },
                    error: function () {
                        alert("Не удалось изменить пароль");
                    },
                });
            }
            function passEquels() {
                var newPass = $("#newUserPass").val();
                var checkPass = $("#updateUserPass").val();
                if (newPass !== checkPass) {
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
                    },
                    error: function () {
                        alert("Не удалось изменить пароль");
                    }
                });


            }

            // function showLinkAdmin() {
            //     $.ajax({
            //         type: "post",
            //         url: "/api/user/show_admin",
            //
            //         success: function (role) {
            //             if (role !== "admin") {
            //                 $("#adminLink").hide();
            //             }
            //         }
            //
            //     });
            //
            // }

            function checkForm() {

                var code = $("#checkUserCodeId").val();

                $.ajax({
                    type: 'PUT',
                    url: "/api/user/code_check",
                    contentType: 'application/json;',
                    data: JSON.stringify(code),
                    // async: true,
                    cache: false,
                    success: function () {
                        alert("Код верен");
                        $("#checkUserCode").modal('hide');
                        setTimeout(function () {
                            $('#editUserPass').modal('show');
                        }, 2000);
                    },
                    error: function () {
                        alert("Проверочный код не верен");
                        return;
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
                alert("Mail seded")
            });

            $("#checkUserCodeBtn").click(function (event) {
                event.preventDefault();
                checkForm();
            });


        });
        //функции
    }
}


export default Edit;

