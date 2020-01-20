$(document).ready(function () {

    getCompaniesTable();
    getOrgTypes();

    function getCompaniesTable() {
        $.ajax({
            type: 'GET',
            url: "/api/admin/all_companies",

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

                    let userId;
                    if (listCompanies[i].user == null) {
                        userId = null;
                    } else {
                        userId = listCompanies[i].user.id;
                    }

                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgType.name + '</td>');
                    htmlTable += ('<td id="tableId">' + /*listCompanies[i].user.id*/  userId + '</td>');
                    htmlTable += ('<td><button id="showEditModalCompaniesBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">изменить</button></td>');
                    // htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    // htmlTable += ('</tr>');
                }
                $("#companiesTable tbody").append(htmlTable);
            }
        });
    }

    function getOrgTypes() {
        $.ajax({
            url: "/api/admin/all_establishments",
            method: "GET",
            dataType: "json",
            success: function (data) {
                var selectBody = $('#addCompType');
                selectBody.empty();
                $(data).each(function (i, org) {
                    selectBody.append(`
                    <option value="${org.id}" >${org.name}</option>
                    `);
                })
            },
        });
    }


    $("#editCompanyBtn").click(function (event) {
        event.preventDefault();
        updateCompanyForm();
    });

    function updateCompanyForm() {
        let companyDto = {
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
            async: false,
            cache: false,
            complete:
                function () {
                    getCompaniesTable();
                },
            success:
                function () {
                    notification("editCompany" + companyDto.id,
                        "  Изменения компании c id " + companyDto.id + " сохранены",
                        'list-companies');

                    $('#editCompany').modal('toggle');
                },


            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //modal company form заполнение
    $(document).on('click', '#showEditModalCompaniesBtn', function () {
        // $(this).trigger('form').reset();

        $('#updateCompanyId').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        $.ajax({
            url: "/api/admin/all_establishments",
            method: "GET",
            dataType: "json",
            success: function (data) {
                var selectBody = $('#updateOrgType');
                selectBody.empty();
                $(data).each(function (i, org) {
                    selectBody.append(`<option value="${org.id}" >${org.name}</option>`);
                })
            },
        });


        $.ajax({
            url: '/api/admin/companyById/' + $(this).closest("tr").find("#tableCompaniesId").text(),
            method: "GET",
            dataType: "json",
            success: function (data) {
                $('#updateCompanyId').val(data.id);
                $('#updateNameCompany').val(data.name);
                $('#updateStartTime').val(data.startTime);
                $('#updateCloseTime').val(data.closeTime);

                if (data.user != null) {
                    $('#updateIdUser').val(data.user.id);
                }

                $("#updateOrgType option[value='" + data.orgType.id + "'] ").prop("selected", true);

            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    });

    //addCompany
    $("#addCompanyBtn").click(function (event) {
        event.preventDefault();
        addCompany();
        $(':input', '#addCompanyForm').val('');
    });

    function addCompany() {
        var company = {
            'name': $("#addCompanyName").val(),
            'startTime': $("#addStart").val(),
            'closeTime': $("#addClose").val(),
            'orgType': $("#addCompType").val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/admin/add_company",
            contentType: 'application/json;',
            data: JSON.stringify(company),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getCompaniesTable();
                    $("#tab-company-panel").tab('show');
                },
            success:
                function () {
                    notification("add-company" + company.name.replace(/[^\w]|_/g, ''),
                        " Компания " + company.name + " добавлена ",
                        'company-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }
});