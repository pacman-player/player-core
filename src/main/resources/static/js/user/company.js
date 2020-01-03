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
            complete:
                function () {
                    getCompanyData();
                },
            success:
                function () {
                    notification("edit-company-data" + formData.name.replace(/[^\w]|_/g, ''),
                        "  Изменения сохранены");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
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

    function notification(notifyId, message) {
        let notify = document.getElementById('notify');
        notify.innerHTML =
            '<div class="alert alert-success notify alert-dismissible"' +
            'role="alert" hidden="true" id="success-alert-' + notifyId + '">' +
            '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' + message +
            '</div>';
        $('#success-alert-' + notifyId).fadeIn(300, "linear");
        setTimeout(() => {
            $('#success-alert-' + notifyId).fadeOut(400, "linear", $(this).remove());
        }, 2000);
    }
});

