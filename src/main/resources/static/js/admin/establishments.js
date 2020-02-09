$(document).ready(function () {

    getEstablishmentsTable();

    function getEstablishmentsTable() {
        $.ajax({
            type: 'GET',
            url: "/api/v1/admin/all_establishments",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (list) {
                var htmlTable = "";
                $("#establishmentsTable tbody").empty();
                for (var i = 0; i < list.length; i++) {
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="tableId">' + list[i].id + '</td>');
                    htmlTable += ('<td id="tableName">' + list[i].name + '</td>');
                    htmlTable += ('<td><button id="editEstablishmentBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editEstablishment">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteEstablishment" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }
                $("#establishmentsTable tbody").append(htmlTable);
            }
        });
    };

    //addEstablishment
    $("#addEstablishmentBtn").click(function (event) {
        event.preventDefault();
        addEstablishment();
        $(':input', '#addForm').val('');
    });

    function addEstablishment() {
        var establishment = {
            'name': $("#addName").val()
        };
        $.ajax({
            type: 'POST',
            url: "/api/v1/admin/add_establishment",
            contentType: 'application/json;',
            data: JSON.stringify(establishment),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getEstablishmentsTable();
                    $("#tab-establishments-panel").tab('show');
                },
            success:
                function () {
                    notification("add-establishment" + establishment.name.replace(/[^\w]|_/g,''),
                        "  Заведение " + establishment.name + " добавлено",
                        'establishments-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //updateForm
    $("#editEstablishmentBtn").click(function (event) {
        event.preventDefault();
        updateForm();
    });

    function updateForm() {
        var establishment = {
            'id': $("#updateEstablishmentId").val(),
            'name': $("#updateEstablishmentName").val()
        };
        $.ajax({
            type: 'PUT',
            url: "/api/v1/admin/update_establishment",
            contentType: 'application/json;',
            data: JSON.stringify(establishment),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getEstablishmentsTable();
                },
            success:
                function () {
                    notification("edit-establishment" + establishment.id,
                        "  Изменения сохранены",
                        'establishments-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //deleteForm
    $(document).on('click', '#deleteEstablishment', function () {
        var id = $(this).closest("tr").find("#tableId").text();
        deleteEstablishment(id);
    });

    function deleteEstablishment(id) {
        $.ajax({
            type: 'delete',
            url: "/api/v1/admin/delete_establishment",
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
                    getEstablishmentsTable();
                },
            success:
                function () {
                    notification("delete-establishment" + id,
                        "  Заведение c id " + id + " удалено",
                        'establishments-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //modal form заполнение
    $(document).on('click', '#editEstablishmentBtn', function () {
        $("#updateEstablishmentId").val($(this).closest("tr").find("#tableId").text());
        $("#updateEstablishmentName").val($(this).closest("tr").find("#tableName").text());
    });
    // function clicKTable() {//для обновления таблицы юзеров
    //     getTable();
    //     $("#tab-user-panel").click();
    // }
});