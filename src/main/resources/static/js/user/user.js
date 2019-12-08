$(document).ready(function () {

    getAllGenre();
    showLinkAdmin();

    function getAllGenre() {
        $.ajax({
            type: 'get',
            url: '/api/user/all_genre',
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',

            success: function (listGenre) {

                var htmlGenres = "Need to add genres";
                if (0 < listGenre.length) {
                    var htmlGenres = "";
                    for (var i = 0; i < listGenre.length; i++) {
                        htmlGenres += ('<div id="genres" class="col-3 pt-3">');
                        htmlGenres += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlGenres += ('<img src="img/' + listGenre[i].id + '.svg" width="50" height="50" alt="' +
                            listGenre[i].name + '" >');
                        htmlGenres += ('</img><p>' + listGenre[i].name + '</p></a></div>');
                    }
                }

                $("#getGenres #genres").remove();//очистка перед выводом
                $("#getGenres").after(htmlGenres);

            success: function (role) {
                if (role != "admin") {
                    alert(role);
                    $("#adminLink").hide();
                }
            }

        });

    };

    function showLinkAdmin() {
        $.ajax({
            type: "post",
            url: "/api/user/show_admin",

            success: function (role) {
                if (role != "admin") {
                    $("#adminLink").hide();
                }
            }

        });

    };

});

