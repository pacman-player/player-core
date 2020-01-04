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
                        htmlCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist" onclick="addMorningPlaylist(' + listSongCompilation[i].id + ')">M</button>');
                        htmlCompilation += ('<button class="btn btn-secondary" id="addDayPlaylist" onclick="addDayPlaylist(' + listSongCompilation[i].id + ')">D</button>');
                        htmlCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist" onclick="addEveningPlaylist(' + listSongCompilation[i].id + ')">E</button>');
                        htmlCompilation += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlCompilation += ('<img src="/img/' + listSongCompilation[i].id + '.svg" width="50" height="50" alt="' +
                            listSongCompilation[i].name + '" >');
                        htmlCompilation += ('</img><p>' + listSongCompilation[i].name + '</p></a>');
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

//добавляем подборку в утренний плейлист
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


//получаем все подборки в утреннем плейлисте
function getAllCompilationsInMorningPlaylist() {
    $.ajax({
        method: "GET",
        url: '/api/user/get_all_compilations_in_morning_playlist',
        success: function (morningPlayList) {
            var htmlMorningCompilation = '';
            //bootstrap card
            htmlMorningCompilation += ('<div class="card-deck" id="morningCompilations">');
            for (var i = 0; i < morningPlayList.length; i++) {
                htmlMorningCompilation += ('<div class="card pt-10">');
                htmlMorningCompilation += ('<a href="#" id="' + morningPlayList[i].id + '" onclick="showAllSongInSongCompilation(' + morningPlayList[i].id + ')" data-toggle="modal"' +
                    ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlMorningCompilation += ('<img src="/img/' + morningPlayList[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
                    morningPlayList[i].name + '">');
                htmlMorningCompilation += ('</img><p>' + morningPlayList[i].name + '</p></a>');
                htmlMorningCompilation += ('<div class="card-body">');
                htmlMorningCompilation += ('<h4 class="card-title">Title:' + morningPlayList[i].name + '</h4>');
                htmlMorningCompilation += ('<p class="card-text">Discription: Some text1</p>');
                htmlMorningCompilation += ('</div>');
                htmlMorningCompilation += ('<div class="card-footer">');
                htmlMorningCompilation += ('<p class="card-text"><small class="text-muted">Footer: Some text2</small></p>');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist2" onclick="addMorningPlaylist2(' + morningPlayList[i].id + ')">M</button>');
                htmlMorningCompilation += ('&nbsp;');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="addDayPlaylist2" onclick="addDayPlaylist2(' + morningPlayList[i].id + ')">D</button>');
                htmlMorningCompilation += ('&nbsp;');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist2" onclick="addEveningPlaylist2(' + morningPlayList[i].id + ')">E</button>');
                htmlMorningCompilation += ('</div>');
                htmlMorningCompilation += ('</div>');
            }
            //закрываю bootstrap card
            htmlMorningCompilation += ('</div>');

            $("#morning #morningCompilations").remove();
            $("#morning").append(htmlMorningCompilation);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}

//достаю все песни подборки и отображаю в модалке
function showAllSongInSongCompilation(id) {
    //достаю название подборки для модалки
    $.getJSON('http://localhost:8080/api/user/song_compilation/' + id, function (data) {
        $('#titleSongCompilation').text("Подборка: " + data.name);
    });
    //достаю все песни из подборки
    $.ajax({
        method: 'GET',
        url: '/api/user/all_song_in_song_compilation/' + id,
        success: function (dataSong) {
            var htmlSongRow = '';
            for (var i = 0; i < dataSong.length; i++) {
                htmlSongRow += ('<div id="allSong">');
                htmlSongRow += ('<div id="MusicTrack-name-title-' + dataSong[i].id+ '">' + dataSong[i].author.name + '</div>');
                htmlSongRow += ('<div id="MusicTrack-name-artist-' + dataSong[i].id+ '">' + dataSong[i].name + '</div>');
                htmlSongRow += ('</div>');
            }
            $('#modalBodyAllSong #allSong').remove();
            $('#musicList').append(htmlSongRow);

        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}


