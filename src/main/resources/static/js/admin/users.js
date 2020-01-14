$(document).ready(function () {

    getTable();
    getCompaniesTable();

    function getTable() {
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
            dataType: 'JSON',
            success: function (listUsers) {
                var htmlTable = "";
                $("#UserTable tbody").empty();
                for (var i = 0; i < listUsers.length; i++) {

                    if (listUsers[i].roles.length > 1) {
                        var htmlRole = listUsers[i].roles[0].name + ', ' + listUsers[i].roles[1].name;
                    } else {
                        var htmlRole = listUsers[i].roles[0].name;
                    }
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="tableId">' + listUsers[i].id + '</td>');
                    htmlTable += ('<td id="tableRole">' + htmlRole + '</td>');
                    htmlTable += ('<td id="tableName">' + listUsers[i].login + '</td>');
                    htmlTable += ('<td id="tablePass">' + listUsers[i].password + '</td>');
                    htmlTable += ('<td id="tableEmail">' + listUsers[i].email + '</td>');
                    // htmlTable += ('<td><button id="editCompanyBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                    //     ' data-target="#editCompany" onclick = "fillUpdateModalForm(${listUsers[i].id})">company</button></td>');
                    htmlTable += ('<td><button id="showEditModalCompanyBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">компания</button></td>');
                    htmlTable += ('<td><button id="editUserBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editUser">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }
                $("#UserTable tbody").append(htmlTable);
            }
        });
    }

    function getCompaniesTable() {
        $.ajax({
            type: 'GET',
            url: "/api/admin/all_companies",

            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (listCompanies) {
                var htmlTable = "";

                for (var i = 0; i < listCompanies.length; i++) {
                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgType.name + '</td>');
                    htmlTable += ('<td id="tableId">' + listCompanies[i].user.id + '</td>');
                    htmlTable += ('<td><button id="editCompanyBtn1" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }

                $("#companiesTable #list").remove();
                $("#getCompaniesTable").after(htmlTable);
            }
        });
    }

    //addUser
    $("#addUserBtn").click(function (event) {
        event.preventDefault();
        addUser();
        $(':input', '#addForm').val('');
    });

    function addUser() {
        var user = {
            'email': $("#addEmail").val(),
            'login': $("#addLogin").val(),
            'password': $("#addPassword").val(),
            'roles': $("#addRole").val()
        };
        $.ajax({
            type: 'POST',
            url: "/api/admin/add_user",

            contentType: 'application/json;',
            data: JSON.stringify(user),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-user-panel").tab('show');
                },
            success:
                function () {
                    notification("add-user" + user.login.replace(/[^\w]|_/g, ''),
                        " Пользователь " + user.login + " добавлен ",
                        'user-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //updateForm
    $("#editUserBtnm").click(function (event) {
        event.preventDefault();
        updateForm();
    });

    function updateForm() {
        var user = {
            'id': $("#updateUserId").val(),
            'email': $("#updateUserEmail").val(),
            'login': $("#updateUserName").val(),
            'password': $("#updateUserPass").val(),
            'roles': $("#updateUserRole").val()
        };
        $.ajax({
            type: 'PUT',
            url: "/api/admin/update_user",
            contentType: 'application/json;',
            data: JSON.stringify(user),
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
            success:
                function () {
                    notification("edit-user" + user.id,
                        "  Изменения пользователя c id  " + user.id + " сохранены",
                        'user-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    $(document).on('click', '#editCompanyBtn', function (e) {
        e.preventDefault();
        updateCompanyForm();
    });

    function updateCompanyForm() {
        var companyDto = {
            id: $("#updateCompanyId").val(),
            name: $("#updateNameCompany").val(),
            startTime: $("#updateStartTime").val(),
            closeTime: $("#updateCloseTime").val(),
            orgType: $("#updateOrgType").val(),
            userId: $("#updateIdUser").val()
        };
        $.ajax({
            type: 'POST',
            url: "/api/admin/company",
            contentType: 'application/json;',
            data: JSON.stringify(companyDto),
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
            success:
                function () {
                    notification("edit-company" + companyDto.id,
                        "  Изменения компании сохранены",
                        'user-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //deleteForm
    $(document).on('click', '#deleteUser', function () {
        var id = $(this).closest("tr").find("#tableId").text();
        deleteUser(id);
    });

    function deleteUser(id) {
        $.ajax({
            type: 'delete',
            url: "/api/admin/delete_user",
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
                },
            success:
                function () {
                    notification("delete-user" + id,
                        "  Пользователь c id " + id + " удален",
                        'user-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //modal form заполнение
    $(document).on('click', '#editUserBtn', function () {

        $("#updateUserId").val($(this).closest("tr").find("#tableId").text());
        $("#updateUserName").val($(this).closest("tr").find("#tableName").text());
        $("#updateUserPass").val($(this).closest("tr").find("#tablePass").text());
        $("#updateUserEmail").val($(this).closest("tr").find("#tableEmail").text());

        switch ($(this).closest("tr").find("#tableRole").text()) {
            case 'USER':
                $("#updateUserRole").val("user");
                break;
            case 'ADMIN':
                // $('#updateUserRole option:contains("ADMIN")').prop("selected", true);
                $("#updateUserRole").val("admin");
                break;
            default:
                // $('#updateUserRole option:contains("ADMIN, USER")').prop("selected", true);
                $("#updateUserRole").val("admin, user");
                break;
        }
    });

    //modal company form заполнение
    $(document).on('click', '#showEditModalCompanyBtn', function () {

        // $(this).trigger('form').reset();

        $('#updateCompanyId').val('');
        $('#updateIdUser').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        var companyList;
        $.ajax({
            url: "/api/admin/all_establishments",
            method: "GET",
            dataType: "json",
            success: function (data) {
                /*var selectBody = $('#updateOrgType');*/
                $(data).each(function (i, org) {
                    companyList = companyList + `<option value="${org.id}" >${org.name}</option>`;
                })
            },

        });

        $.ajax({
            url: '/api/admin/company/' + $(this).closest("tr").find("#tableId").text(),
            method: "GET",
            dataType: "json",
            success: function (data) {
                $('#company-modal-body').empty();
                var modalInnerText = '';
                modalInnerText = modalInnerText + '<label for="updateCompanyId">ID компании</label>\n' +
                    '<input id="updateCompanyId" class="form-control"\n' +
                    'disabled="disabled" type="text"\n' +
                    'name="id" required=""/>\n' +
                    '\n' +
                    '<label for="updateIdUser">ID пользователя</label>\n' +
                    '<input id="updateIdUser" class="form-control"\n' +
                    'disabled="disabled"\n' +
                    'type="text" name="id-user" required=""/>\n' +
                    '\n' +
                    '<label for="updateNameCompany">Компания</label>\n' +
                    '<input id="updateNameCompany" class="form-control" type="text"\n' +
                    'name="company"\n' +
                    'required=""/>\n' +
                    '\n' +
                    '<label for="updateStartTime">Время открытия</label>\n' +
                    '<input id="updateStartTime" class="form-control" type="time"\n' +
                    'name="start-time"\n' +
                    'required=""/>\n' +
                    '\n' +
                    '<label for="updateCloseTime">Время закрытия</label>\n' +
                    '<input id="updateCloseTime" class="form-control" type="time"\n' +
                    'name="close-time"\n' +
                    'required=""/>\n' +
                    '\n' +
                    '<label for="updateOrgType">Тип компании</label>\n' +
                    '<select id="updateOrgType" class="form-control" name="role">\n' +
                    '</select>\n';

                $('#company-modal-body').append(modalInnerText);

                $('#updateCompanyId').val(data.id);
                $('#updateNameCompany').val(data.name);
                $('#updateStartTime').val(data.startTime);
                $('#updateCloseTime').val(data.closeTime);
                $('#updateIdUser').val(data.user.id);
                $('#updateOrgType').append(companyList);

                $("#updateOrgType option[value='" + data.orgType.id + "'] ").prop("selected", true);

                $('#modal-footer').empty();
                var buttons = '<button type="button" class="btn btn-default" data-dismiss="modal" id = "modal-footer-clsbtn">Close</button>\n' +
                    '<button id="editCompanyBtn" class="btn btn-primary" type="submit">Edit company</button>';

                $('#modal-footer').append(buttons);


            },
            error: function () {
                $('#company-modal-body').empty().append("No company assigned yet");
                var buttons = '<button type="button" class="btn btn-default" data-dismiss="modal" id = "modal-footer-clsbtn">Close</button>';
                $('#modal-footer').empty().append(buttons);
            }
        })
    });
    // function clicKTable() {//для обновления таблицы юзеров
    //     getTable();
    //     $("#tab-user-panel").click();
    // }
});