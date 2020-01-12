$(function () {

    getAllGenre();
    showLinkAdmin();

    //получение и вывод подборок
    $(document).on('click', '#genres', function () {
        let genre = $(this).text();
        $.ajax({
            type: 'GET',
            url: '/api/user/compilation/song_compilation',
            data: {genre: genre},
            success: function (listSongCompilation) {
                let htmlCompilation = "Need to add Compilation";
                if (0 < listSongCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');
                    for (let i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += ('<div id="songCompilation" class="card-deck">');
                        htmlCompilation += ('<div class="card pt-10">');
                        htmlCompilation += ('<a href="#" onclick="showAllSongInSongCompilation(' + listSongCompilation[i].id + ')" data-toggle="modal"' +
                            ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlCompilation += ('<img src="/img/compilation/compilation' + listSongCompilation[i].id + '.svg" width="50" height="50" alt="' +
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
                        htmlCompilation += ('<button class="btn btn-secondary" id="btnMiddayPlaylist-' + listSongCompilation[i].id + '" onclick="addMiddayPlaylist(' + listSongCompilation[i].id + ')">День</button>');
                        htmlCompilation += ('&nbsp;');
                        htmlCompilation += ('<button class="btn btn-secondary" id="btndEveningPlaylist-' + listSongCompilation[i].id + '" onclick="addEveningPlaylist(' + listSongCompilation[i].id + ')">Вечер</button>');
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
        $("#getGenres").empty();
        getAllGenre();
    });

    $(document).on('click', '#songCompilation', function () {
        let compilationName = $(this).text();
        $("#compilationModalCompilationName").text(compilationName);
        $("#openCompilationModal").trigger("click");
        $.get("/api/user/compilation/songsBySongCompilation?compilationName=" + compilationName, function (songList) {
                $("#compilationModalTableBody").empty();
                console.log(songList);
                for (let i = 0; i < songList.length; i++) {
                    let newTr = $(`<tr></tr>`);
                    let td = ``;
                    td += `<td>${songList[i].name}</td>`
                        + `<td>${songList[i].author}</td>`
                        + `<td>${songList[i].genre}</td>`
                        + `<td><div class="btn-group"><button id="playMusic${songList[i].id}" onclick="playOrPauseMusic(${songList[i].id})"><img height="20" width="20" alt="Play/Pause" src="/img/play.svg"/></button>`
                        + `<button id="downloadMusic${songList[i].id}" onclick="downloadMusic(${songList[i].id})"><img height="20" width="20"  alt="Download" src="/img/download.svg"/></button></div></td>`;
                    newTr.html(td);
                    $("#compilationModalTableBody").append(newTr);
                }
            }
        )
        ;
    });

    function getAllGenre() {
        $.ajax({
            type: 'get',
            url: '/api/user/genre/get/all-genre',
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (listGenre) {
                let htmlGenres = "Need to add genres";
                if (0 < listGenre.length) {
                    htmlGenres = ('<h3 id="genres">Жанры</h3>');
                    htmlGenres += ('<div id="genres" class="col-3 pt-3">');
                    htmlGenres += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                    htmlGenres += ('<img src="/img/all.svg" width="50" height="50" alt="Все подборки" >');
                    htmlGenres += ('</img><p>' + "Все подборки" + '</p></a></div>');
                    for (let i = 0; i < listGenre.length; i++) {
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
        url: '/api/user/compilation/morning-playlist/add/song-compilation/' + idCompilation,
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
        url: '/api/user/compilation/midday-playlist/add/song-compilation/' + idCompilation,
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
        url: '/api/user/compilation/evening-playlist/add/song-compilation/' + idCompilation,
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
        url: '/api/user/play-list/morning-playlist/get/all-song-compilation',
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
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="btnMiddayPlaylist-' + morningPlayList[i].id + '" onclick="addMiddayPlaylist(' + morningPlayList[i].id + ')">День</button>');
                htmlMorningCompilation += ('&nbsp;');
                htmlMorningCompilation += ('<button class="btn btn-secondary" id="btnEveningPlaylist-' + morningPlayList[i].id + '" onclick="addEveningPlaylist(' + morningPlayList[i].id + ')">Вечер</button>');
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
        url: '/api/user/play-list/midday-playlist/get/all-song-compilation',
        success: function (middayPlayList) {
            var htmlMiddayCompilation = '';
            //bootstrap card
            htmlMiddayCompilation += ('<div class="card-deck" id="middayCompilations">');
            for (var i = 0; i < middayPlayList.length; i++) {
                htmlMiddayCompilation += ('<div class="card pt-10">');
                htmlMiddayCompilation += ('<a href="#" id="' + middayPlayList[i].id + '" onclick="showAllSongInSongCompilation(' + middayPlayList[i].id + ')" data-toggle="modal"' +
                    ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlMiddayCompilation += ('<img src="/img/compilation/compilation' + middayPlayList[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
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
                htmlMiddayCompilation += ('<button class="btn btn-secondary" id="btnMiddayPlaylist-' + middayPlayList[i].id + '" onclick="addMiddayPlaylist(' + middayPlayList[i].id + ')">День</button>');
                htmlMiddayCompilation += ('&nbsp;');
                htmlMiddayCompilation += ('<button class="btn btn-secondary" id="btnEveningPlaylist-' + middayPlayList[i].id + '" onclick="addEveningPlaylist(' + middayPlayList[i].id + ')">Вечер</button>');
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
        url: '/api/user/play-list/evening-playlist/get/all-song-compilation',
        success: function (eveningPlayList) {
            var htmlEveningCompilation = '';
            //bootstrap card
            htmlEveningCompilation += ('<div class="card-deck" id="eveningCompilations">');
            for (var i = 0; i < eveningPlayList.length; i++) {
                htmlEveningCompilation += ('<div class="card pt-10">');
                htmlEveningCompilation += ('<a href="#" id="' + eveningPlayList[i].id + '" onclick="showAllSongInSongCompilation(' + eveningPlayList[i].id + ')" data-toggle="modal"' +
                    ' data-target="#modalShowAllSong" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                htmlEveningCompilation += ('<img src="/img/compilation/compilation' + eveningPlayList[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' +
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
                htmlEveningCompilation += ('<button class="btn btn-secondary" id="btnMiddayPlaylist-' + eveningPlayList[i].id + '" onclick="addMiddayPlaylist(' + eveningPlayList[i].id + ')">День</button>');
                htmlEveningCompilation += ('&nbsp;');
                htmlEveningCompilation += ('<button class="btn btn-secondary" id="btnEveningPlaylist-' + eveningPlayList[i].id + '" onclick="addEveningPlaylist(' + eveningPlayList[i].id + ')">Вечер</button>');
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
    $.getJSON('/api/user/compilation/get/song-compilation/' + id, function (songCompilation) {
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
            htmlAboutSongCompilationForModal += ('<button class="btn btn-secondary" id="btnMiddayPlaylist-' + songCompilation[i].id + '" onclick="addMiddayPlaylist(' + songCompilation[i].id + ')">День</button>');
            htmlAboutSongCompilationForModal += ('&nbsp;');
            htmlAboutSongCompilationForModal += ('<button class="btn btn-secondary" id="btnEveningPlaylist-' + songCompilation[i].id + '" onclick="addEveningPlaylist(' + songCompilation[i].id + ')">Вечер</button>');
            htmlAboutSongCompilationForModal += ('</div>');
            htmlAboutSongCompilationForModal += ('</div>');
            htmlAboutSongCompilationForModal += ('</div>');
        }
        $('#titleSongCompilation').text("Подборка: " + songCompilation.name);
        $("#aboutCompilations").remove();
        $("#aboutSongCompilation").append(htmlAboutSongCompilationForModal); //в модалке картинки не отображаются...
    });
    //достаю все песни из подборки
    $.ajax({
        method: 'GET',
        url: '/api/user/song/get/all-song/song-compilation/' + id,
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


function playOrPauseMusic(musicId) {
    let currentImage = $("#playMusic" + musicId + " img");
    let currentImageSrc = currentImage.prop("src");
    let currentUrlHost = location.origin;
    if (currentImageSrc === currentUrlHost + "/img/play.svg") {
        currentImage.attr("src", currentUrlHost + "/img/pause.svg")
    } else currentImage.attr("src", currentUrlHost + "/img/play.svg")

    // код для проигрывания музыки
}

function downloadMusic(musicId) {
    alert("downloading...")
    // код для загрузки
}