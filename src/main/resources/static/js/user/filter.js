$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();

    function showLinkAdmin() {
        $.ajax({
            type: "post",
            url: "/api/v1/user/show_admin",

            success: function (role) {
                if (role !== "admin") {
                    $("#adminLink").hide();
                }
            }

        });

    }

});