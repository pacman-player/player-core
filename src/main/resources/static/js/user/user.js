$(document).ready(function () {


    function getAllGenre() {
        $.ajax({
            type: 'get',
            url: '/api/user/all_genre',

            success: function (listGenre) {

                var htmlGenres = "Need to add genres";
                if (0 < listGenre.length) {
                    for (var i = 0; i < listGenre.length; i++) {
                        htmlGenres = ('<div id="genres" class="col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm">');
                        htmlGenres += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlGenres += ('<img src="img/' + listGenre[i].id + '.svg\)" width="50" height="50" alt="' +
                            listGenre[i].name + '" >');
                        htmlGenres += ('</img></a></div>');
                    }
                }

                $("#getGenres #genres").remove();
                $("#getGenres del").after(htmlGenres);

            }
        });
    };

}

