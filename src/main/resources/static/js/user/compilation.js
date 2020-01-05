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
                var htmlCompilation = "Need to add Compilation";
                if (0 < listSongCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');
                    for (var i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += ('<div id="songCompilation" class="card-deck">');
                        htmlCompilation += ('<div class="card pt-10">');
                        htmlCompilation += ('<a href="#" onclick="showAllSongInSongCompilation(' + listSongCompilation[i].id + ')" data-toggle="modal"' +
                            ' data-target="#modalShowAllSong"class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlCompilation += ('<img src="/img/' + listSongCompilation[i].id + '.svg" width="50" height="50" alt="' +
                            listSongCompilation[i].name + '" >');
                        htmlCompilation += ('</img><p>Песни подборки</p></a>');
                        htmlCompilation += ('<div class="card-body">');
                        htmlCompilation += ('<h4 class="card-title">Title:' + listSongCompilation[i].name + '</h4>');
                        htmlCompilation += ('<p class="card-text">Discription: Some text</p>');
                        htmlCompilation += ('</div>');
                        htmlCompilation += ('<div class="card-footer">');
                        htmlCompilation += ('<p class="card-text"><small class="text-muted">Footer: Some text</small></p>');
                        htmlCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + listSongCompilation[i].id + '" onclick="addMorningPlaylist(' + listSongCompilation[i].id + ')">Утро</button>');
                        htmlCompilation += ('&nbsp;');
                        htmlCompilation += ('<button class="btn btn-secondary" id="addMiddayPlaylist-' + listSongCompilation[i].id + '" onclick="addMiddayPlaylist(' + listSongCompilation[i].id + ')">День</button>');
                        htmlCompilation += ('&nbsp;');
                        htmlCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist-' + listSongCompilation[i].id + '" onclick="addEveningPlaylist(' + listSongCompilation[i].id + ')">Вечер</button>');
                        htmlCompilation += ('</div>');
                        htmlCompilation += ('</div>');
                        htmlCompilation += ('</div>');
                    }
                }
                $("#getGenres #genres").remove();
                $("#getGenres").append(htmlCompilation);
            }
        });
    });

    //назад к жанрам
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

    //получаем подборки из утреннего плейлиста
    $(document).on('click', '#morning-music-nav', function () {
        getAllCompilationsInMorningPlaylist();
    });

    //получаем подборки из дневного плейлиста
    $(document).on('click', '#midday-music-nav', function () {
        getAllCompilationsInMiddayPlaylist();
    });

    //получаем подборки из вечернего плейлиста
    $(document).on('click', '#evening-music-nav', function () {
        getAllCompilationsInEveningPlaylist();
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

//добавляем подборку в дневной плейлист
function addMiddayPlaylist(idCompilation) {
    $.ajax({
        method: 'GET',
        url: '/api/user/add_song_compilation_to_midday_playlist/' + idCompilation,
        success: function () {
            //+обновить дневной плейлист
            getAllCompilationsInMiddayPlaylist();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}

function addEveningPlaylist(idCompilation) {
    $.ajax({
        method: 'GET',
        url: '/api/user/add_song_compilation_to_evening_playlist/' + idCompilation,
        success: function () {
            //+обновить вечерний плейлист
            getAllCompilationsInEveningPlaylist();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
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
                htmlMorningCompilation += ('</img><p>Песни подборки</p></a>');
                htmlMorningCompilation += ('<div class="card-body">');
                htmlMorningCompilation += ('<h4 class="card-title">Title: ' + morningPlayList[i].name + '</h4>');
                htmlMorningCompilation += ('<p class="card-text">Discription: Some text</p>');
                htmlMorningCompilation += ('</div>');
                htmlMorningCompilation += ('<div class="card-footer">');
                htmlMorningCompilation += ('<p class="card-text"><small class="text-muted">Footer: Some text</small></p>');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + morningPlayList[i].id + '" onclick="addMorningPlaylist(' + morningPlayList[i].id + ')">Утро</button>');
                htmlMorningCompilation += ('&nbsp;');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="addMiddayPlaylist-' + morningPlayList[i].id + '" onclick="addDayPlaylist(' + morningPlayList[i].id + ')">День</button>');
                htmlMorningCompilation += ('&nbsp;');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist-' + morningPlayList[i].id + '" onclick="addEveningPlaylist(' + morningPlayList[i].id + ')">Вечер</button>');
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

//получаем все подборки в дневном плейлисте
function getAllCompilationsInMiddayPlaylist() {
    $.ajax({
        method: "GET",
        url: '/api/user/get_all_compilations_in_midday_playlist',
        success: function (middayPlayList) {
            var htmlMiddayCompilation = '';
            //bootstrap card
            htmlMiddayCompilation += ('<div class="card-deck" id="middayCompilations">');
            for (var i = 0; i < middayPlayList.length; i++) {
                htmlMiddayCompilation += ('<div class="card pt-10">');
                htmlMiddayCompilation += ('<a href="#" id="' + middayPlayList[i].id + '" onclick="showAllSongInSongCompilation(' + middayPlayList[i].id + ')" data-toggle="modal"' +
                    ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlMiddayCompilation += ('<img src="/img/' + middayPlayList[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
                    middayPlayList[i].name + '">');
                htmlMiddayCompilation += ('</img><p>Песни подборки</p></a>');
                htmlMiddayCompilation += ('<div class="card-body">');
                htmlMiddayCompilation += ('<h4 class="card-title">Title: ' + middayPlayList[i].name + '</h4>');
                htmlMiddayCompilation += ('<p class="card-text">Discription: Some text</p>');
                htmlMiddayCompilation += ('</div>');
                htmlMiddayCompilation += ('<div class="card-footer">');
                htmlMiddayCompilation += ('<p class="card-text"><small class="text-muted">Footer: Some text</small></p>');
                htmlMiddayCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + middayPlayList[i].id + '" onclick="addMorningPlaylist(' + middayPlayList[i].id + ')">Утро</button>');
                htmlMiddayCompilation += ('&nbsp;');
                htmlMiddayCompilation += ('<button class="btn btn-secondary" id="addMiddayPlaylist-' + middayPlayList[i].id + '" onclick="addDayPlaylist(' + middayPlayList[i].id + ')">День</button>');
                htmlMiddayCompilation += ('&nbsp;');
                htmlMiddayCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist-' + middayPlayList[i].id + '" onclick="addEveningPlaylist(' + middayPlayList[i].id + ')">Вечер</button>');
                htmlMiddayCompilation += ('</div>');
                htmlMiddayCompilation += ('</div>');
            }
            //закрываю bootstrap card
            htmlMiddayCompilation += ('</div>');
            $("#midday #middayCompilations").remove();
            $("#midday").append(htmlMiddayCompilation);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}

//получаем все подборки в вечернем плейлисте
function getAllCompilationsInEveningPlaylist() {
    $.ajax({
        method: "GET",
        url: '/api/user/get_all_compilations_in_evening_playlist',
        success: function (eveningPlayList) {
            var htmlEveningCompilation = '';
            //bootstrap card
            htmlEveningCompilation += ('<div class="card-deck" id="eveningCompilations">');
            for (var i = 0; i < eveningPlayList.length; i++) {
                htmlEveningCompilation += ('<div class="card pt-10">');
                htmlEveningCompilation += ('<a href="#" id="' + eveningPlayList[i].id + '" onclick="showAllSongInSongCompilation(' + eveningPlayList[i].id + ')" data-toggle="modal"' +
                    ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlEveningCompilation += ('<img src="/img/' + eveningPlayList[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
                    eveningPlayList[i].name + '">');
                htmlEveningCompilation += ('</img><p>Песни подборки</p></a>');
                htmlEveningCompilation += ('<div class="card-body">');
                htmlEveningCompilation += ('<h4 class="card-title">Title: ' + eveningPlayList[i].name + '</h4>');
                htmlEveningCompilation += ('<p class="card-text">Discription: Some text</p>');
                htmlEveningCompilation += ('</div>');
                htmlEveningCompilation += ('<div class="card-footer">');
                htmlEveningCompilation += ('<p class="card-text"><small class="text-muted">Footer: Some text</small></p>');
                htmlEveningCompilation += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + eveningPlayList[i].id + '" onclick="addMorningPlaylist(' + eveningPlayList[i].id + ')">Утро</button>');
                htmlEveningCompilation += ('&nbsp;');
                htmlEveningCompilation += ('<button class="btn btn-secondary" id="addMiddayPlaylist-' + eveningPlayList[i].id + '" onclick="addDayPlaylist(' + eveningPlayList[i].id + ')">День</button>');
                htmlEveningCompilation += ('&nbsp;');
                htmlEveningCompilation += ('<button class="btn btn-secondary" id="addEveningPlaylist-' + eveningPlayList[i].id + '" onclick="addEveningPlaylist(' + eveningPlayList[i].id + ')">Вечер</button>');
                htmlEveningCompilation += ('</div>');
                htmlEveningCompilation += ('</div>');
            }
            //закрываю bootstrap card
            htmlEveningCompilation += ('</div>');
            $("#evening #eveningCompilations").remove();
            $("#evening").append(htmlEveningCompilation);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}

//достаю все песни подборки любого плейлиста и отображаю в модалке
function showAllSongInSongCompilation(id) {
    //достаю название подборки для модалки
    $.getJSON('http://localhost:8080/api/user/song_compilation/' + id, function (songCompilation) {
        var htmlAboutSongCompilationForModal = '';
        for (var i = 0; i < songCompilation.length; i++) {
            htmlAboutSongCompilationForModal += ('<div class="card-deck" id="aboutCompilations">');
            htmlAboutSongCompilationForModal += ('<div class="card pt-10">');
            htmlAboutSongCompilationForModal += ('<a href="#" id="' + songCompilation[i].id + '" onclick="showAllSongInSongCompilation(' + eveningPlayList[i].id + ')" data-toggle="modal"' +
                ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
            htmlAboutSongCompilationForModal += ('<img src="/img/' + songCompilation[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
                songCompilation[i].name + '">');
            htmlAboutSongCompilationForModal += ('</img><p>Песни подборки</p></a>');
            htmlAboutSongCompilationForModal += ('<div class="card-body">');
            htmlAboutSongCompilationForModal += ('<h4 class="card-title">Title: ' + songCompilation[i].name + '</h4>');
            htmlAboutSongCompilationForModal += ('<p class="card-text">Discription: Some text</p>');
            htmlAboutSongCompilationForModal += ('</div>');
            htmlAboutSongCompilationForModal += ('<div class="card-footer">');
            htmlAboutSongCompilationForModal += ('<p class="card-text"><small class="text-muted">Footer: Some text</small></p>');
            htmlAboutSongCompilationForModal += ('<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + songCompilation[i].id + '" onclick="addMorningPlaylist(' + songCompilation[i].id + ')">Утро</button>');
            htmlAboutSongCompilationForModal += ('&nbsp;');
            htmlAboutSongCompilationForModal += ('<button class="btn btn-secondary" id="addMiddayPlaylist-' + songCompilation[i].id + '" onclick="addDayPlaylist(' + songCompilation[i].id + ')">День</button>');
            htmlAboutSongCompilationForModal += ('&nbsp;');
            htmlAboutSongCompilationForModal += ('<button class="btn btn-secondary" id="addEveningPlaylist-' + songCompilation[i].id + '" onclick="addEveningPlaylist(' + songCompilation[i].id + ')">Вечер</button>');
            htmlAboutSongCompilationForModal += ('</div>');
            htmlAboutSongCompilationForModal += ('</div>');
            htmlAboutSongCompilationForModal += ('</div>');
        }
        $('#titleSongCompilation').text("Подборка: " + songCompilation.name);
        $("#aboutCompilations").remove();
        $("#aboutSongCompilation").append(htmlAboutSongCompilationForModal); //в модалку почему-то выводится только текст...
    });
    //достаю все песни из подборки
    $.ajax({
        method: 'GET',
        url: '/api/user/all_song_in_song_compilation/' + id,
        success: function (dataSong) {
            var htmlSongRow = '';
            for (var i = 0; i < dataSong.length; i++) {
                htmlSongRow += ('<div id="allSong">');
                htmlSongRow += ('<div id="musictrack-name-title-' + dataSong[i].id+ '">' + dataSong[i].author.name + '</div>');
                htmlSongRow += ('<div id="musictrack-name-artist-' + dataSong[i].id+ '">' + dataSong[i].name + '</div>');
                htmlSongRow += ('</div>');
            }
            $('#allSong').remove();
            $('#musicList').append(htmlSongRow);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText, status, error);
        }
    })
}


