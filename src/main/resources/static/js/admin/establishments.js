$(document).ready(function () {

    getEstablishmentsTable();

    function getEstablishmentsTable() {

        $.ajax({
            type: 'GET',
            url: "/api/admin/all_establishments",
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

                for (var i = 0; i < list.length; i++) {
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="tableId">' + list[i].id + '</td>');
                    htmlTable += ('<td id="tableName">' + list[i].name + '</td>');
                    htmlTable += ('<td><button id="editEstablishmentBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editEstablishment">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteEstablishment" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }

                $("#establishmentsTable #list").remove();
                $("#getEstablishmentsTable").after(htmlTable);
            }

        });
    };


    //addEstablishment
    $("#addEstablishmentBtn").click(function (event) {
        event.preventDefault();
        addEstablishment();
        $(':input', '#addForm').val('');
        setTimeout(clicKTable, 300)
    });

    function addEstablishment() {
        var establishment = {
            'name': $("#addName").val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/admin/add_establishment",
            contentType: 'application/json;',
            data: JSON.stringify(establishment),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
        });
        location.reload();
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
            url: "/api/admin/update_establishment",
            contentType: 'application/json;',
            data: JSON.stringify(establishment),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
        });
        location.reload();
    };


    //deleteForm
    $(document).on('click', '#deleteEstablishment', function () {
        var id = $(this).closest("tr").find("#tableId").text();
        deleteEstablishment(id);
    });

    function deleteEstablishment(id) {
        $.ajax({
            type: 'delete',
            url: "/api/admin/delete_establishment",
            contentType: 'application/json;',
            data: JSON.stringify(id),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            dataType: 'JSON',
        });
        location.reload();
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