$(document).ready(function () {

    getAllGenre();
    showLinkAdmin();

    //получение и вывод подборок
    $(document).on('click', '#genres', function () {
        var genre = $(this).text();

        $.ajax({
            type: 'post',
            url: '/api/user/song_compilation',
            contentType: 'application/json;',
            data: JSON.stringify(genre),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',

            success: function (listSongCompilation) {
                console.log(listSongCompilation);

                //   alert(listSongCompilation[0].id + "-" + listSongCompilation[0].name);

                var htmlCompilation = "Need to add Compilation";
                if (0 < listSongCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');

                    for (var i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += ('<div id="songCompilation" class="col-3 pt-3">');
                        htmlCompilation += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlCompilation += ('<img src="/img/' + listSongCompilation[i].id + '.svg" width="80" height="80" alt="' +
                            listSongCompilation[i].name + '" >');
                        htmlCompilation += ('</img><p>' + listSongCompilation[i].name + '</p></a>');
                        // htmlCompilation += ('<a class="btn btn-primary" id="btnAddMorningPlaylist" onclick="addMorningPlaylist(' + listSongCompilation[i].id + ')" role="button">M</a>');
                        htmlCompilation += ('<a id="btnAddMorningPlaylist" onclick="addMorningPlaylist(' + listSongCompilation[i].id + ')" type="button">M</a>');
                        htmlCompilation += ('<a id="addDayPlaylist" onclick="addDayPlaylist(' + listSongCompilation[i].id + ')" role="link">D</a>');
                        htmlCompilation += ('<a id="addEveningPlaylist" onclick="addEveningPlaylist(' + listSongCompilation[i].id + ')" role="link">E</a>');
                        htmlCompilation += ('</div>');
                    }
                }
                $("#getGenres #genres").remove();
                $("#getGenres").append(htmlCompilation);
            }

        });

    });



    $(document).on('click', '#linkBack', function () {
        $("#getGenres #songCompilation").remove();
        getAllGenre();
    });

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
                    htmlGenres = ('<h3 id="genres">Жанры</h3>');
                    htmlGenres += ('<div id="genres" class="col-3 pt-3">');
                    htmlGenres += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                    htmlGenres += ('<img src="/img/all.svg" width="50" height="50" alt="Все подборки" >');
                    htmlGenres += ('</img><p>' + "Все подборки" + '</p></a></div>');

                    for (var i = 0; i < listGenre.length; i++) {
                        htmlGenres += ('<div id="genres" class="col-3 pt-3">');
                        htmlGenres += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlGenres += ('<img src="/img/' + listGenre[i].id + '.svg" width="50" height="50" alt="' +
                            listGenre[i].name + '" >');
                        htmlGenres += ('</img><p>' + listGenre[i].name + '</p></a></div>');
                    }
                }

                // $("#getGenres #genres").remove();//очистка перед выводом
                // $("#getGenres").after(htmlGenres);
                $("#getGenres").append(htmlGenres);

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

    //получаем подборки из плейлиста утро
    $(document).on('click', '#morning-music-nav', function () {
        getAllCompilationsInMorningPlaylist();
    });



});

//INSERT row to table db
//добавляем запись в бд
function addMorningPlaylist(idCompilation) {
    $.ajax({
        method: 'GET',
        url: '/api/user/add_song_compilation_to_morning_playlist/' + idCompilation,
        success: function () {
            //+обновить утренний плейлист
            getAllCompilationsInMorningPlaylist();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}

function addDayPlaylist(idCompilation) {

}

function addEveningPlaylist(idCompilation) {

}

//SELECT row from table db
//обновляем утренний плейлист
function getAllCompilationsInMorningPlaylist() {
    $.ajax({
        method: "GET",
        url: '/api/user/get_all_compilations_in_morning_playlist',
        success: function (morningPlayList) {
            var htmlMorningCompilation = '';
            for (var i = 0; i < morningPlayList.length; i++) {
                htmlMorningCompilation += ('<div id="morningCompilations" class="col-3 pt-3">');
                htmlMorningCompilation += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlMorningCompilation += ('<img src="/img/' + morningPlayList[i].id + '.svg" width="80" height="80" alt="' +
                    morningPlayList[i].name + '" >');
                htmlMorningCompilation += ('</img><p>' + morningPlayList[i].name + '</p></a>');
                htmlMorningCompilation += ('<a id="btnAddMorningPlaylist2" onclick="addMorningPlaylist2(' + morningPlayList[i].id + ')" type="button">M</a>');
                htmlMorningCompilation += ('<a id="addDayPlaylist2" onclick="addDayPlaylist2(' + morningPlayList[i].id + ')" role="link">D</a>');
                htmlMorningCompilation += ('<a id="addEveningPlaylist2" onclick="addEveningPlaylist2(' + morningPlayList[i].id + ')" role="link">E</a>');
                htmlMorningCompilation += ('</div>');
            }
            $("#morning #morningCompilations").remove();
            $("#morning").append(htmlMorningCompilation);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}


