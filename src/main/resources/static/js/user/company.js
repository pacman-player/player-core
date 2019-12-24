$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();

    getCompanyData();

    function updateCompany() {
        var formData = {
            name: $('#est-name').val(),
            startTime: $('#est-start-time').val(),
            closeTime: $('#est-close-time').val()
        };
        $.ajax({
            contentType: "application/json;",
            url: "/api/user/company",
            type: "PUT",
            data: JSON.stringify(formData),
            dataType: 'json',
            complete: function () {
            }
        });
    }

    function getCompanyData() {
        $.ajax({
            url: '/api/user/company/',
            method: "GET",
            dataType: "json",
            success: function (data) {
                $('#est-name').val(data.name);
                $('#est-start-time').val(data.startTime);
                $('#est-close-time').val(data.closeTime);
            }
        })
    }

    $('#est-save-data').click(function () {
        updateCompany();
    });

    function showLinkAdmin() {
        $.ajax({
            type: "post",
            url: "/api/user/show_admin",

            success: function (role) {
                if (role !== "admin") {
                    $("#adminLink").hide();
                }
            }

        });

    }

});

