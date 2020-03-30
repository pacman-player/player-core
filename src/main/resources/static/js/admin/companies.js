$(document).ready(function () {
    $('.money').mask('000.000.000.000.000,00', {reverse: true});

    getCompaniesTable();

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
                    let tariff = listCompanies[i].tariff + "";
                    let pennies = tariff.substr(tariff.length - 2, tariff.length);
                    tariff = tariff.substr(0, tariff.length - 2) + "," + pennies;
                    console.log(typeof tariff);
                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgType.name + '</td>');
                    htmlTable += ('<td id="tableUser">' + listCompanies[i].user.login + '</td>');
                    htmlTable += ('<td id="tableTariff">' + tariff + '₽' +  '</td>');
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
            tariff: $("#updateTariff").val().replace(/[^0-9]/g, ''),
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
                    getCompaniesTable();
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
            url: "/api/admin/all_establishments",
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
            url: '/api/admin/companyById/' + $(this).closest("tr").find("#tableCompaniesId").text(),
            method: "GET",
            dataType: "json",
            success: function (companies) {
                $('#updateCompanyId').val(companies.id);
                $('#updateNameCompany').val(companies.name);
                $('#updateStartTime').val(companies.startTime);
                $('#updateCloseTime').val(companies.closeTime);
                $('#updateIdUser').val(companies.user.id);
                $('#updateTariff').val(companies.tariff);
                $("#updateOrgType option[value='" + companies.orgType.id + "'] ").prop("selected", true);
            },
            error:  function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    });
});