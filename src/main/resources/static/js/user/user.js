"use strict";

$(document).ready(function () {

    getAllGenre();
    showLinkAdmin();
    getCompanyData();

    $('#est-save-data').click(function () {
        updateCompany();
    });

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

            }
        });
    }

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

