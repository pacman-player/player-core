$(document).ready(function () {

    getCompaniesTable();

    function getCompaniesTable() {
        $.ajax({
            type: 'GET',
            url: "/api/v1/admin/all_companies",

            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            cache: false,
            dataType: 'JSON',
            success: function (listCompanies) {
                var htmlTable = "";
                $("#companiesTable tbody").empty();
                for (var i = 0; i < listCompanies.length; i++) {

                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgType.name + '</td>');
                    htmlTable += ('<td id="tableId">' + listCompanies[i].user.id + '</td>');
                    htmlTable += ('<td><button id="showEditModalCompaniesBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">изменить</button></td>');
                    // htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    // htmlTable += ('</tr>');
                }
                $("#companiesTable tbody").append(htmlTable);
            }
        });
    };


    $("#editCompanyBtn").click(function (event) {
        event.preventDefault();
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
            url: "/api/v1/admin/company",
            contentType: 'application/json;',
            data: JSON.stringify(companyDto),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            complete:
                function () {
                    getCompaniesTable();
                },
            success:
                function () {
                    notification("edit-company" + companyDto.id,
                        "  Изменения компании c id " + companyDto.id + " сохранены",
                        'companies-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //modal company form заполнение
    $(document).on('click', '#showEditModalCompaniesBtn', function () {
        // $(this).trigger('form').reset();
        $('#updateCompanyId').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        $.ajax({
            url: "/api/v1/admin/all_establishments",
            method: "GET",
            dataType: "json",
            success: function (data) {
                var selectBody = $('#updateOrgType');
                selectBody.empty();
                $(data).each(function (i, org) {
                    selectBody.append(`
                    <option value="${org.id}" >${org.name}</option>
                    `);
                })
            },
        });

        $.ajax({
            url: '/api/v1/admin/company/' + $(this).closest("tr").find("#tableId").text(),
            method: "GET",
            dataType: "json",
            success: function (data) {
                $('#updateCompanyId').val(data.id);
                $('#updateNameCompany').val(data.name);
                $('#updateStartTime').val(data.startTime);
                $('#updateCloseTime').val(data.closeTime);
                $('#updateIdUser').val(data.user.id);
                $("#updateOrgType option[value='" + data.orgType.id + "'] ").prop("selected", true);
            },
            error:  function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    });
});