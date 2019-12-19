$(document).ready(function () {

    getTable();

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
                    htmlTable += ('<td><button id="editCompanyBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">company</button></td>');
                    htmlTable += ('<td><button id="editUserBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editUser">Edit</button></td>');
                    htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">Delete</button></td>');
                    htmlTable += ('</tr>');

                }

                $("#UserTable #list").remove();
                $("#getUserTable").after(htmlTable);
            }

        });
    };

    //addUser
    $("#addUserBtn").click(function (event) {
        event.preventDefault();
        addUser();
        $(':input', '#addForm').val('');
        setTimeout(clicKTable, 300)
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
            dataType: 'JSON',

        });
    }

    //updateForm
    $("#editUserBtn").click(function (event) {
        event.preventDefault();
        updateForm();
        getTable();

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
            dataType: 'JSON',
            success: function () {
                getTable();
            }

        });
        location.reload();
    };


    $("#editCompanyBtn").click(function (event) {
        event.preventDefault();
        updateCompanyForm();
        getTable();

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
            dataType: 'JSON',
            success: function () {
                getTable();
            }

        });
        location.reload();
    };

    //deleteForm
    $(document).on('click', '#deleteUser', function () {
        var id = $(this).closest("tr").find("#tableId").text();
        deleteUser(id);
        getTable();
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
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function () {
                getTable();
            }
        });
        location.reload();

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
    $(document).on('click', '#editCompanyBtn', function () {

        // $(this).trigger('form').reset();

        $('#updateCompanyId').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        $("#updateIdUser").val($(this).closest("tr").find("#tableId").text());

        $.ajax({
            url: '/api/admin/company/' + $(this).closest("tr").find("#tableId").text(),
            method: "GET",
            dataType: "json",

            success: function (data) {
                $('#updateCompanyId').val(data.id);
                $('#updateNameCompany').val(data.name);
                $('#updateStartTime').val(data.startTime);
                $('#updateCloseTime').val(data.closeTime);
                // $('#updateOrgType').val(data.orgType.name);
                // $('#updateIdUser').val(data.user.id);

                switch ($(data.orgType.id).text()) {
                    case '1':
                        $("#updateOrgType").val("Ресторан");
                        break;
                }
            }
        })

    });

    function clicKTable() {//для обновления таблицы юзеров
        getTable();
        $("#tab-user-panel").click();
    }

});