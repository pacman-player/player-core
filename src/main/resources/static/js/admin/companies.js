$(document).ready(function () {

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

                for (var i = 0; i < listCompanies.length; i++) {

                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgType.name + '</td>');
                    htmlTable += ('<td id="tableId">' + listCompanies[i].user.id + '</td>');
                    htmlTable += ('<td><button id="editCompanyBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">изменить</button></td>');
                    // htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    // htmlTable += ('</tr>');
                }

                $("#companiesTable #list").remove();
                $("#getCompaniesTable").after(htmlTable);
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
            url: "/api/admin/company",
            contentType: 'application/json;',
            data: JSON.stringify(companyDto),
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

    //modal company form заполнение
    $(document).on('click', '#editCompanyBtn', function () {

        // $(this).trigger('form').reset();

        $('#updateCompanyId').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        $.ajax({
            url: '/api/admin/company/' + $(this).closest("tr").find("#tableId").text(),
            method: "GET",
            dataType: "json",

            success: function (data) {
                $('#updateCompanyId').val(data.id);
                $('#updateNameCompany').val(data.name);
                $('#updateStartTime').val(data.startTime);
                $('#updateCloseTime').val(data.closeTime);
                $('#updateIdUser').val(data.user.id);
                // $('#updateOrgType').val(data.orgType.name);


                switch ($(data.orgType.id).text()) {
                    case '1':
                        $("#updateOrgType").val("Ресторан");
                        break;
                }
            }
        })

    });

});