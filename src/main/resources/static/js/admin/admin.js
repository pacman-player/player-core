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
                var htmlTable = "trewq";

                for (var i = 0; i < listUsers.length; i++) {

                    if (listUsers[i].roles.length > 1) {
                        var htmlRole = listUsers[i].roles[0].name + '-' + listUsers[i].roles[1].name;
                    } else {
                        var htmlRole = listUsers[i].roles[0].name;
                    }
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="tableId">' + listUsers[i].id + '</td>');
                    htmlTable += ('<td id="tableRole">' + htmlRole + '</td>');
                    htmlTable += ('<td id="tableName">' + listUsers[i].login + '</td>');
                    htmlTable += ('<td id="tablePass">' + listUsers[i].password + '</td>');
                    htmlTable += ('<td id="tableMess">' + listUsers[i].email + '</td>');
                    htmlTable += ('<td><button id="editUserBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editUser">Edit</button></td>');
                    htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">Delete</button></td>');
                    htmlTable += ('</tr>');

                }

                $("#UserTable #list").remove();
                $("#getUserTable thead").after(htmlTable);

            }

        });
    };

});