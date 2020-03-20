$(document).ready(function () {

    getAllGenre();
    showLinkAdmin();
    //получение и вывод подборок
    $(document).on('click', '#genres', function () {
        var genre = $(this).text();
        $.ajax({
            type: 'post',
            url: '/api/user/song-compilation/get/all-song-compilation',
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
                allCompilationInGenre = listSongCompilation;
                fillAllSongsPlaylist(allCompilationInGenre, allSongInGenre);
                let listCompilation = getCurrentPlaylist('getGenres').currentCumpilationsList
                var htmlCompilation = "Need to add Compilation";
                if (0 < listCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');
                    for (var i = 0; i < listCompilation.length; i++) {
                        let morningIds = [];
                        let middayIds = [];
                        let eveningIds = [];

                        let mon = $("#btnAddMorningPlaylist1-" + listCompilation[i].id).attr("class");
                        $.ajax({
                            method: "GET",
                            url: "/api/user/play-list/morning-playlist/get/all-song-compilation",
                            contentType: "application/json",
                            async: false,
                            success: function (morningCompilation) {
                                if (morningCompilation != null) {
                                    for (let k = 0; k < morningCompilation.length; k++) {
                                        morningIds.push(morningCompilation[k].id)
                                    }
                                }
                            }
                        });
                        mon = morningIds.includes(listSongCompilation[i].id) ? "btn btn-success" : "btn btn-info";
                        $.ajax({
                            method: "GET",
                            url: "/api/user/play-list/midday-playlist/get/all-song-compilation",
                            contentType: "application/json",
                            async: false,
                            success: function (middayCompilation) {
                                if (middayCompilation != null) {
                                    for (let k = 0; k < middayCompilation.length; k++) {
                                        middayIds.push(middayCompilation[k].id);
                                    }
                                }
                            }
                        });

                        let mid = $("#btnMiddayPlaylist1-" + listCompilation[i].id).attr("class");
                        mid = middayIds.includes(listCompilation[i].id) ? "btn btn-success" : "btn btn-info";

                        $.ajax({
                            method: "GET",
                            url: "/api/user/play-list/evening-playlist/get/all-song-compilation",
                            contentType: "application/json",
                            async: false,
                            success: function (eveningCompilation) {
                                if (eveningCompilation != null) {
                                    for (let k = 0; k < eveningCompilation.length; k++) {
                                        eveningIds.push(eveningCompilation[k].id);
                                    }
                                }
                            }
                        });

                        let eve = $("#btnEveningPlaylist1-" + listCompilation[i].id).attr("class");
                        eve = eveningIds.includes(listCompilation[i].id) ? "btn btn-success" : "btn btn-info";
                        let cover = listCompilation[i].cover;
                        if (cover == null) {
                            cover = "na.jpg"
                        }

                        htmlCompilation += '<div id="songCompilation" class="card-deck">'
                            + '<div class="card pt-10">'
                            + '<a href="#" onclick="showAllSongInSongCompilation(\'getGenres\', ' + listCompilation[i].id + ')" data-toggle="modal"' +
                            ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
                            + '<img src="/cover/' + cover + '" width="80" height="80" alt="' +
                            listCompilation[i].name + '" >'
                            + '<p>Песни подборки</p></a>'
                            + '<div class="card-body">'
                            + '<h4 class="card-title"> ' + listCompilation[i].name + '</h4>'
                            + '<p class="card-text">Discription: Some text</p>'
                            + '</div>'
                            + '<div class="card-footer">'
                            + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
                        let playing_state = 'on_stop';
                        let display_play = 'inline-block';
                        let display_pause = 'none';
                        if (listCompilation[i].compilationIndex === lastPlayedCompilationIndex && 'getGenres' === lastPlayedPlaylistName) {
                            playing_state = 'on_play';
                            display_play = 'none';
                            display_pause = 'inline-block';
                        }
                        let playButton = `<button class="playBtn" data-playlist_id="getGenres_${listCompilation[i].compilationIndex}" onclick="playOrPausePlaylist(\'getGenres\', ${listCompilation[i].compilationIndex})"></button>`;
                        let pauseButton = `<button class="pauseBtn" style="display: ${display_pause}" data-playing_state="${playing_state}" data-playlist_id="getGenres_${listCompilation[i].compilationIndex}" onclick="playOrPausePlaylist(\'getGenres\', ${listCompilation[i].compilationIndex})"></button>`;
                        htmlCompilation += playButton;
                        htmlCompilation += pauseButton;
                        htmlCompilation += '&nbsp;' + '&nbsp;'
                            + '<button class="' + mon + '" id="btnAddMorningPlaylist1-' + listCompilation[i].id + '" onclick="addMorningPlaylist(' + listCompilation[i].id + ')">Утро</button>'
                            + '&nbsp;'
                            + '<button class="' + mid + '" id="btnMiddayPlaylist1-' + listCompilation[i].id + '" onclick="addMiddayPlaylist(' + listCompilation[i].id + ')">День</button>'
                            + '&nbsp;'
                            + '<button class="' + eve + '" id="btnEveningPlaylist1-' + listCompilation[i].id + '" onclick="addEveningPlaylist(' + listCompilation[i].id + ')">Вечер</button>'
                            + '</div>'
                            + '</div>'
                            + '</div>';
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
                var htmlGenres = "Need to add genres";
                if (listGenre.length > 0) {
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
        morningPlaylist();
    });

    //получаем подборки из дневного плейлиста
    $(document).on('click', '#midday-music-nav', function () {
        middayPlaylist();
    });

    //получаем подборки из вечернего плейлиста
    $(document).on('click', '#evening-music-nav', function () {
        eveningPlaylist();
    });
});

//добавляем/удаляем подборку в/из утреннего плейлиста
function addMorningPlaylist(idCompilation) {
    let buttonStateElement = $("#btnAddMorningPlaylist1-" + idCompilation);

    if (buttonStateElement.hasClass("btn-info")) {
        buttonStateElement.removeClass("btn btn-info").addClass("btn btn-success");

        $.ajax({
            method: 'POST',
            url: '/api/user/play-list/morning-playlist/add/song-compilation/',
            contentType: "application/json",
            data: JSON.stringify(idCompilation),
            success: function () {
                //+обновить утренний плейлист
                morningPlaylist();
                //getAllCompilationsInMorningPlaylist("btn btn-success", middayButtonStateElement, eveningButtonStateElement);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    } else if (buttonStateElement.hasClass("btn-success")) {

        buttonStateElement.removeClass("btn btn-success").addClass("btn btn-info");
        $.ajax({
            method: 'DELETE',
            url: '/api/user/play-list/morning-playlist/delete/song-compilation/' + idCompilation,
            contentType: "application/json",
            success: function () {
                //+обновить утренний плейлист
                morningPlaylist();
                //getAllCompilationsInMorningPlaylist("btn btn-info", middayButtonStateElement, eveningButtonStateElement);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        });
    }
}

//добавляем подборку в дневной плейлист
function addMiddayPlaylist(idCompilation) {

    let buttonStateElement = $("#btnAddMorningPlaylist1-" + idCompilation).attr("class");
    let middayButtonStateElement = $("#btnMiddayPlaylist1-" + idCompilation);
    let eveningButtonStateElement = $("#btnEveningPlaylist1-" + idCompilation).attr("class");

    if (middayButtonStateElement.hasClass("btn-info")) {
        middayButtonStateElement.removeClass("btn btn-info").addClass("btn btn-success");

        $.ajax({
            method: 'POST',
            url: '/api/user/play-list/midday-playlist/add/song-compilation/',
            contentType: "application/json",
            data: JSON.stringify(idCompilation),
            success: function () {
                //+обновить дневной плейлист
                middayPlaylist();
                //getAllCompilationsInMiddayPlaylist(buttonStateElement, "btn btn-success", eveningButtonStateElement);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    } else if (middayButtonStateElement.hasClass("btn-success")) {

        middayButtonStateElement.removeClass("btn btn-success").addClass("btn btn-info");
        $.ajax({
            method: 'DELETE',
            url: '/api/user/play-list/midday-playlist/delete/song-compilation/' + idCompilation,
            contentType: "application/json",
            success: function () {
                //+обновить утренний плейлист
                middayPlaylist();
                // getAllCompilationsInMiddayPlaylist(buttonStateElement, "btn btn-info", eveningButtonStateElement);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        });
    }
}

function addEveningPlaylist(idCompilation) {

    let buttonStateElement = $("#btnAddMorningPlaylist1-" + idCompilation).attr("class");
    let middayButtonStateElement = $("#btnMiddayPlaylist1-" + idCompilation).attr("class");
    let eveningButtonStateElement = $("#btnEveningPlaylist1-" + idCompilation);

    if (eveningButtonStateElement.hasClass("btn-info")) {
        eveningButtonStateElement.removeClass("btn btn-info").addClass("btn btn-success");

        $.ajax({
            method: 'POST',
            url: '/api/user/play-list/evening-playlist/add/song-compilation/',
            contentType: "application/json",
            data: JSON.stringify(idCompilation),
            success: function () {
                //+обновить дневной плейлист
                eveningPlaylist();
                //getAllCompilationsInEveningPlaylist(buttonStateElement, middayButtonStateElement, "btn btn-success");
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    } else if (eveningButtonStateElement.hasClass("btn-success")) {

        eveningButtonStateElement.removeClass("btn btn-success").addClass("btn btn-info");
        $.ajax({
            method: 'DELETE',
            url: '/api/user/play-list/evening-playlist/delete/song-compilation/' + idCompilation,
            contentType: "application/json",
            success: function () {
                //+обновить утренний плейлист
                eveningPlaylist();
                // getAllCompilationsInEveningPlaylist(buttonStateElement, middayButtonStateElement, "btn btn-info");
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        });
    }
}

//получаем все подборки в утреннем плейлисте
function getAllCompilationsInMorningPlaylist() {
    fillPlaylistsTab('morning', 'morningCompilations', getCurrentPlaylist('morning').currentCumpilationsList);
}

//получаем все подборки в дневном плейлисте
function getAllCompilationsInMiddayPlaylist() {
    fillPlaylistsTab('midday', 'middayCompilations', getCurrentPlaylist('midday').currentCumpilationsList);
}

//получаем все подборки в вечернем плейлисте, проиндексированные порядковым номером в вечернем плейлисте плеера
function getAllCompilationsInEveningPlaylist() {
    fillPlaylistsTab('evening', 'eveningCompilations', getCurrentPlaylist('evening').currentCumpilationsList);
}

//отрисовка всех подборок в плейлисте
function fillPlaylistsTab(playListName, secondId, playlist) {
    $(`#${playListName}`).empty();
    var htmlCompilation = '';
    //bootstrap card
    htmlCompilation += (`<div class="card-deck" id="${secondId}">`);
    for (var i = 0; i < playlist.length; i++) {
        let cover = playlist[i].cover;
        if (cover == null) {
            cover = "na.jpg"
        }
        htmlCompilation += '<div class="card pt-10">'
            + '<a href="#" id="' + playlist[i].id + '" onclick="showAllSongInSongCompilation(\'' + playListName + '\', ' + playlist[i].id + ')" data-toggle="modal"'
            + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
            + '<img src="/cover/' + cover + '" width="80" height="80" class="card-img-top" alt="' + playlist[i].name + '">'
            + '<p>Песни подборки</p></a>'
            + '<div class="card-body">'
            + '<h4 class="card-title">' + playlist[i].name + '</h4>'
            + '<p class="card-text">Description: Some text</p>'
            + '</div>'
            + '<div class="card-footer">'
            + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
        let playing_state = 'on_stop';
        let display_play = 'inline-block';
        let display_pause = 'none';
        if (playlist[i].compilationIndex === lastPlayedCompilationIndex && playListName === lastPlayedPlaylistName) {
            playing_state = 'on_play';
            display_play = 'none';
            display_pause = 'inline-block';
        }
        let playButton = `<button class="playBtn" style="display: ${display_play}" data-playlist_id="${playListName}_${playlist[i].compilationIndex}" onclick="playOrPausePlaylist(\'${playListName}\', ${playlist[i].compilationIndex})"></button>`;
        let pauseButton = `<button class="pauseBtn" style="display: ${display_pause}" data-playing_state="${playing_state}" data-playlist_id="${playListName}_${playlist[i].compilationIndex}" onclick="playOrPausePlaylist(\'${playListName}\', ${playlist[i].compilationIndex})"></button>`;
        let trackBubble = '<div class="d-track__bubble" id="bubble"></div>';
        htmlCompilation += playButton;
        htmlCompilation += pauseButton;
        htmlCompilation += trackBubble;
        htmlCompilation += '</div>'
            + '</div>';
    }
    //закрываю bootstrap card
    htmlCompilation += ('</div>');
    $(`#${playListName} #${secondId}`).remove();
    $(`#${playListName}`).append(htmlCompilation);
}

//достаю все песни подборки любого плейлиста и отображаю в модалке
function showAllSongInSongCompilation(compilationListName, id) {
    //достаю инфу о подборке (название, картинку,  пр.) для модалки
    $.getJSON('/api/user/song-compilation/get/song-compilation/' + id, function (songCompilation) {
        var htmlAboutSongCompilationForModal = '';
        for (var i = 0; i < songCompilation.length; i++) {
            htmlAboutSongCompilationForModal = '<div class="card-deck" id="aboutCompilations">'
                + '<div class="card pt-10">'
                + '<a href="#" id="' + songCompilation[i].id + '" onclick="showAllSongInSongCompilation(\'' + compilationListName + '\' ,' + songCompilation[i].id + ')" data-toggle="modal"'
                + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
                + '<img src="/img/' + songCompilation[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' + songCompilation[i].name + '"><p>Песни подборки</p></a>'
                + '<div class="card-body">'
                + '<h4 class="card-title">' + songCompilation[i].name + '</h4>'
                + '<p class="card-text">Discription: Some text</p>'
                + '</div>'
                + '<div class="card-footer">'
                + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>'
                + '<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + songCompilation[i].id + '" onclick="addMorningPlaylist(' + songCompilation[i].id + ')">Утро</button>'
                + '&nbsp;'
                + '<button class="btn btn-secondary" id="btnMiddayPlaylist-' + songCompilation[i].id + '" onclick="addMiddayPlaylist(' + songCompilation[i].id + ')">День</button>'
                + '&nbsp;'
                + '<button class="btn btn-secondary" id="btnEveningPlaylist-' + songCompilation[i].id + '" onclick="addEveningPlaylist(' + songCompilation[i].id + ')">Вечер</button>'
                + '</div>'
                + '</div>'
                + '</div>'
        }
        $('#titleSongCompilation').text("Подборка: " + songCompilation.name);
        $("#aboutCompilations").remove();
        $("#aboutSongCompilation").append(htmlAboutSongCompilationForModal); //в модалку почему-то выводится только текст...
        fillModalTableWithPlaylist('modalPlaylistTableBody', compilationListName, getCompilationOfPlaylist(compilationListName, id));
        $('#modalPlaylistName').text(songCompilation.name);
    });
}


//функция для получения из плейлиста плеера список песен определенной подборки,
// проиндексированных порядковым номером в плейлисте
function getCompilationOfPlaylist(playlistName, compilationId) {
    var songs = [];
    var playlist = getCurrentPlaylist(playlistName).currentSongsList;
    for (var i = 0, k = 0; i < playlist.length; i++) {
        if (playlist[i].compilationId === compilationId) {
            songs[k] = playlist[i];
            k++;
        }
    }
    return songs;
}

// функия для заполнения модалки песнями, id которого передается первым параметром
function fillModalTableWithPlaylist(modalId, playlistName, songs) {
    $(`#${modalId}`).empty();
    for (let i = 0; i < songs.length; i++) {
        let song = songs[i];
        let backgroundColor = song.isFromSongQueue ? 'rgb(232, 195, 195)' : '#ececec';
        let musicTr = $(`<tr style="background-color:${backgroundColor}"></tr>`);
        let musicTd = `<td>${song.name}</td><td>${song.author.name}</td><td>${song.genre.name}</td>`;
        let playing_state_play = 'on_stop';
        let playing_state_pause = 'on_play';
        let display_play = 'inline-block';
        let display_pause = 'none';
        var clickedButton;
        if (song.compilationIndex === lastPlayedCompilationIndex &&
            song.musicIndex === lastPlayedMusicIndex &&
            playlistName === lastPlayedPlaylistName) {
            let compilationsButtons = $(`button[data-playlist_id='${lastPlayedPlaylistName}_${lastPlayedCompilationIndex}']`);
            for (var j = 0; j < compilationsButtons.length; j++) {
                if ($(compilationsButtons[j]).css("display") === "inline-block") {
                    clickedButton = compilationsButtons[j]
                }
            }
            var state = clickedButton ? clickedButton.dataset.playing_state : 'on_stop';
            if (state === 'on_play') {
                display_play = 'none';
                display_pause = 'inline-block';
                playing_state_pause = 'on_play'
            } else if (state === 'on_pause') {
                display_play = 'inline-block';
                display_pause = 'none';
                playing_state_play = 'on_pause'
            }
        }
        let playButton = `<td><button class="playBtn" style="display: ${display_play}"  data-playing_state="${playing_state_play}" data-music_id="${playlistName}_${song.compilationIndex}_${song.musicIndex}" onclick="playOrPause(\'${playlistName}\', ${song.compilationIndex}, ${song.musicIndex})"></button>`;
        let pauseButton = `<button class="pauseBtn" style="display: ${display_pause}" data-playing_state="${playing_state_pause}" data-music_id="${playlistName}_${song.compilationIndex}_${song.musicIndex}" onclick="playOrPause(\'${playlistName}\', ${song.compilationIndex}, ${song.musicIndex})"></button></td>`;
        musicTd += playButton;
        musicTd += pauseButton;
        musicTr.html(musicTd);
        $(`#${modalId}`).append(musicTr);

    }
}

// функия для заполнения модалки текущего списка песен
function fillModalCurrentPlaylistTable(playlist) {
    fillModalTableWithPlaylist('modalCurrentPlaylistTableBody', lastPlayedPlaylistName, allSongsInCurrentPlaylist);
}

/**
 * ##########################
 *          PLAYER
 * ##########################
 */
// объект плеер, который при загрузке страницы сразу находится с jquery
let player;

// объект плеер, который находится javascript-ом
let playerElement = document.getElementById("player");
playerElement.volume = 0.3;
$('#volumebar').attr('value', playerElement.volume);

// адрес, по которому клиент обращается для проигрывания музыки
let musicUrl = "/api/music/play/";

// адрес, по которому клиент обращается для получения обложки песни
let albumsCoverUrl = "/api/music/albums-cover/";

// переменная, которая говорит, нужно ли играть по порядку или случайным порядком
let shuffle = false;

//утренний, дневной, вечерний плейлисты как список compilation
let allCompilationsInMorningPlaylist;
let allCompilationsInMiddayPlaylist;
let allCompilationsInEveningPlaylist;
let allCompilationInGenre;

//утренний, дневной, вечерний плейлисты как список песен
let allSongsInMorningPlaylist = [];
let allSongsInMiddayPlaylist = [];
let allSongsInEveningPlaylist = [];
let allSongInGenre = [];

//текущий плейлист как список compilation
let allCompilationInCurrentPlaylist;

//текущий плейлист как список песен
let allSongsInCurrentPlaylist;

// индекс последне-проигранной песни в своем массиве
let lastPlayedMusicIndex = -1;

// индекс последне-проигранного массива песен в своем списке
let lastPlayedCompilationIndex = -1;

// переменная, которая хранит id кнопки последне-проигранного списка
let lastPlayedPlaylistId = 'none';

// имя текущего раздела
let lastPlayedPlaylistName = 'none';

/**
 * Вспомогательная функция для получения утреннего, дневного или вечернего плейлистов.
 * Заполняет список всех песен данного плейлиста, помечает песни доп.информацией:
 *  compilationId - id подборки, в которой находится песня
 *  compilationIndex - индекс подборки в данном плейлисте
 *  musicIndex - индекс песни в данном плейлисте.
 * allCompilationsInPlaylist - список подборок соответствующего плейлиста,
 * allSongsInPlaylist - список песен соответствующего плейлиста, список передается пустым.
 */
function fillAllSongsPlaylist(allCompilationsInPlaylist, allSongsInPlaylist) {
    for (var i = 0, k = 0; i < allCompilationsInPlaylist.length; i++) {
        const id = allCompilationsInPlaylist[i].id;
        const compilationInd = i;
        allCompilationsInPlaylist[i].compilationIndex = compilationInd;
        $.getJSON('/api/user/song/get/all-song/song-compilation/' + id, function (songs) {
            for (var j = 0; j < songs.length; j++, k++) {
                const musicInd = k;
                allSongsInPlaylist[k] = songs[j];
                allSongsInPlaylist[k].compilationIndex = compilationInd;
                allSongsInPlaylist[k].compilationId = id;
                allSongsInPlaylist[k].musicIndex = musicInd;
            }
        });
    }
}

//получения утреннего плейлиста, заполнение плелиста плеера
function morningPlaylist() {
    $.get('/api/user/play-list/morning-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMorningPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMorningPlaylist, allSongsInMorningPlaylist)
        getAllCompilationsInMorningPlaylist();
    });
}

function middayPlaylist() {
    $.get('/api/user/play-list/midday-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMiddayPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMiddayPlaylist, allSongsInMiddayPlaylist)
        getAllCompilationsInMiddayPlaylist();
    });
}

function eveningPlaylist() {
    $.get('/api/user/play-list/evening-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInEveningPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInEveningPlaylist, allSongsInEveningPlaylist)
        getAllCompilationsInEveningPlaylist();
    });
}

$(function () {
    player = $('#player');
    morningPlaylist();
    middayPlaylist();
    eveningPlaylist();
    // при нажатии на кнопку "предыдущий"
    $("#previousAudioButton").on("click", function () {
        playPrevious();
    });
    // при нажатии на кнопку "играть \ пауза"
    $("#playOrPauseAudioButton").on("click", function () {
        playOrPause(lastPlayedPlaylistName, lastPlayedCompilationIndex, lastPlayedMusicIndex);
    });
    // при нажатии на кнопку "следующий"
    $("#nextAudioButton").on("click", function () {
        playNext();
    });
    // при нажатии на кнопку "список". выводит список песен, который играют на данный момент
    $("#currentPlaylistButton").on("click", function () {
        fillModalCurrentPlaylistTable(allSongsInCurrentPlaylist);
    });
    // при нажатии на кнопку "остановить". останавливает воспроизведение и музыой для воспроизведения ставит первую песню в текущем массиве песен
    $("#stopAudioButton").on("click", function () {
        let lastPlayedMusicsButtons = $(`button[data-music_id='${lastPlayedPlaylistName}_${lastPlayedCompilationIndex}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedPlaylistName}_${lastPlayedCompilationIndex}']`);
        for (let i = 0; i < lastPlayedPlaylistsPlayButtons.length; i++) {
            setButtonOnStop(lastPlayedPlaylistsPlayButtons[i]);
        }
        lastPlayedMusicIndex = 0;
        let music = allSongsInCurrentPlaylist[0];
        player.prop('src', musicUrl + music.name);
        $('#globalPlayButton').prop('src', '/img/play.png');
    });
    // при нажатии на кнопку "случайный порядок / обычный порядок"
    $("#shuffleAudioButton").on("click", function () {
        if (shuffle) {
            $("#shuffleImg").prop('src', '/img/shuffleOff.png');
            shuffle = false;
        } else {
            $("#shuffleImg").prop('src', '/img/shuffleOn.png');
            shuffle = true;
        }
    });
    // при проигрывании плеера. ищутся кнопки песен, который играли до этого и которые должны играть сейчас, и меняется изображение в соответствии с их положением
    playerElement.addEventListener("play", function () {
        //кнопки подборок
        let listCompilationPlayButtons = $('button.playBtn');
        for (let i = 0; i < listCompilationPlayButtons.length; i++) {
            if (listCompilationPlayButtons[i].dataset.playlist_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex ||
                listCompilationPlayButtons[i].dataset.music_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex + '_' + lastPlayedMusicIndex) {
                $(listCompilationPlayButtons[i]).css('display', 'none');
            } else {
                $(listCompilationPlayButtons[i]).css('display', 'inline-block');
            }
        }
        let listCompilationPauseButtons = $('button.pauseBtn');
        for (let i = 0; i < listCompilationPauseButtons.length; i++) {
            if (listCompilationPauseButtons[i].dataset.playlist_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex ||
                listCompilationPauseButtons[i].dataset.music_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex + '_' + lastPlayedMusicIndex) {
                $(listCompilationPauseButtons[i]).css('display', 'inline-block');
                setButtonOnPlay(listCompilationPauseButtons[i]);
            } else {
                $(listCompilationPauseButtons[i]).css('display', 'none');
            }

        }
        lastPlayedPlaylistId = lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex;
        $('#globalPlayButton').prop('src', '/img/pause.png');

    });
    // при паузе плеера. ищутся кнопки песен, который играли и меняется изображение на "продолжить"
    playerElement.addEventListener("pause", function () {
        let listCompilationPauseButtons = $('button.pauseBtn');
        for (let i = 0; i < listCompilationPauseButtons.length; i++) {
            if (listCompilationPauseButtons[i].dataset.playlist_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex) {
                $(listCompilationPauseButtons[i]).css('display', 'none');
            }
            if (listCompilationPauseButtons[i].dataset.music_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex + '_' + lastPlayedMusicIndex) {
                $(listCompilationPauseButtons[i]).css('display', 'none');
            }
        }
        let listCompilationPlayButtons = $('button.playBtn');
        for (let i = 0; i < listCompilationPlayButtons.length; i++) {
            if (listCompilationPlayButtons[i].dataset.playlist_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex) {
                setButtonOnPause(listCompilationPlayButtons[i]);
                $(listCompilationPlayButtons[i]).css('display', 'inline-block');
            }
            if (listCompilationPauseButtons[i].dataset.music_id === lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex + '_' + lastPlayedMusicIndex) {
                setButtonOnPause(listCompilationPlayButtons[i]);
                $(listCompilationPlayButtons[i]).css('display', 'inline-block');
            }
        }
        lastPlayedPlaylistId = lastPlayedPlaylistName + '_' + lastPlayedCompilationIndex;
        $('#globalPlayButton').prop('src', '/img/pause.png');
        $('#globalPlayButton').prop('src', '/img/resume.png');
    });
    // при окончании проигрывания текущей песни
    playerElement.addEventListener("ended", function () {
        playNext();
    });
    // при изменении громкости плеера
    playerElement.addEventListener("volumechange", function () {
        $('#volumebar').attr('value', playerElement.volume);
        if (playerElement.volume === 0 || playerElement.muted) {
            $('#soundImg').prop('src', '/img/soundOff.png');
        } else {
            $('#soundImg').prop('src', '/img/soundOn.png');
        }
    });


    //при изменении currentTime
    playerElement.addEventListener('timeupdate', function () {
        $('#seekbar').attr('value', playerElement.currentTime / playerElement.duration);
        var min = Math.floor(playerElement.currentTime / 60);
        var sec = Math.floor(playerElement.currentTime % 60);
        var s = sec < 10 ? `0${sec}` : sec;
        document.getElementById('duration').innerHTML = min + ':' + s;
    });
    var seekBar = document.getElementById('seekbar');
    if (playerElement && seekBar) {
        seekBar.addEventListener('mousedown', function () {
            isDown = true;
        }, true);
        seekBar.addEventListener('mouseup', function () {
            isDown = false;
            playerElement.play();
        }, true);

        seekBar.addEventListener('mousemove', function (event) {
            event.preventDefault();
            if (isDown) {
                playerElement.pause();
                let widthLeft = $('#seekbar').offset().left;
                let x = event.pageX - widthLeft;
                let xPersent = x / this.offsetWidth;
                playerElement.currentTime = playerElement.duration * xPersent;
            }
        }, true);
    }

    seekBar.addEventListener('click', function (event) {
        let widthLeft = $('#seekbar').offset().left;
        let x = event.pageX - widthLeft;
        let xPersent = x / this.offsetWidth;
        playerElement.currentTime = playerElement.duration * xPersent;
    });

    var volumeBar = document.getElementById('volumebar');
    var isDown = false;
    if (playerElement && volumeBar) {
        volumeBar.addEventListener('mousedown', function () {
            isDown = true;
        }, true);
        volumeBar.addEventListener('mouseup', function () {
            isDown = false;
        }, true);
        volumeBar.addEventListener('mousemove', function (event) {
            event.preventDefault();
            if (isDown) {
                let widthLeft = $('#volumebar').offset().left;
                let x = event.pageX - widthLeft;
                let xPersent = x / this.offsetWidth;
                playerElement.volume = xPersent;
            }
        }, true);
        volumeBar.addEventListener('click', function (event) {
            let widthLeft = $('#volumebar').offset().left;
            let x = event.pageX - widthLeft;
            let xPersent = x / this.offsetWidth;
            playerElement.volume = xPersent;
        });
    }
});
// при нажатии на кнопку "громкость вкл / выкл"
var soundButton = document.getElementById('soundButton');
soundButton.addEventListener('click', function (event) {
    let soundState = $("#soundButton").data("sound_state");
    if (soundState === "on") {
        $("#soundImg").prop("src", "/img/soundOff.png");
        $("#soundButton").data("sound_state", "off");
        playerElement.muted = true;
    } else {
        $("#soundImg").prop("src", "/img/soundOn.png");
        $("#soundButton").data("sound_state", "on");
        playerElement.muted = false;
    }
});

// функция для изменения изображения и data-playing_state на кнопке при проигрывании музыки
function setButtonOnPlay(button) {
    button.dataset.playing_state = 'on_play';
}

// функция для изменения изображения и data-playing_state на кнопке при паузе музыки
function setButtonOnPause(button) {
    button.dataset.playing_state = 'on_pause';
}

// функция для изменения изображения и data-playing_state на кнопке при остановке музыки
function setButtonOnStop(button) {
    button.dataset.playing_state = 'on_stop';
}

/**
 * функция для проигрывания / паузы
 * сперва находится кнопка нажатия и определяется его состояние
 * если он остановлен - то ищутся предыдуще-игранные кнопки и меняется их состояние
 * потом смотрим, это текущий плейлист, или нет
 * если нет - то последне проигранным плейлистом и списком песен отмечаются новые, меняются состояния кнопок
 * смотрим, это старый список compilation, или нет. если нет - то новый помечается текущим
 * потом меняются последне проигранные песни и плейлист, музыка на плеере и играет песню
 *      а если песня играется - то ставится на паузу
 *      а если песня на паузе - то продолжается воспроизведение
 * @param playlistName
 * @param compilationIndex
 * @param musicIndex
 * @param isFromSongQueue
 */
function playOrPause(playlistName, compilationIndex, musicIndex, isFromSongQueue) {
    if ($('#playerContainer').css('display') === 'none') {
        $('#playerContainer').css('display', 'block')
    }

    let clickedButtons = $(`button[data-music_id="${playlistName}_${compilationIndex}_${musicIndex}"]`);
    let clickedButton;
    for (var i = 0; i < clickedButtons.length; i++) {
        if ($(clickedButtons[i]).css("display") === "inline-block") {
            clickedButton = clickedButtons[i];
        }
    }
    if (!clickedButton) {
        clickedButtons = $(`button[data-playlist_id="${playlistName}_${compilationIndex}"]`);
        for (let i = 0; i < clickedButtons.length; i++) {
            if ($(clickedButtons[i]).css("display") === "inline-block") {
                clickedButton = clickedButtons[i];
            }
        }
    }
    let playingState = clickedButton ? clickedButton.dataset.playing_state : 'on_stop';
    if (playingState === 'on_play') {
        playerElement.pause();
    } else if (playingState === 'on_pause') {
        playerElement.play();
    } else {
        let lastPlayedMusicsButtons = $(`button[data-music_id="${playlistName}_${compilationIndex}_${musicIndex}"]`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }

        if (playlistName !== lastPlayedPlaylistName) {
            lastPlayedPlaylistName = playlistName;
            var playList = getCurrentPlaylist(playlistName);
            allSongsInCurrentPlaylist = playList.currentSongsList;
            allCompilationInCurrentPlaylist = playList.currentCumpilationsList;
        }

        lastPlayedCompilationIndex = compilationIndex;
        lastPlayedMusicIndex = musicIndex;
        let music = allSongsInCurrentPlaylist[musicIndex];
        player.attr('src', musicUrl + music.author.name + "/" + music.name);
        $('#albums-cover').attr('src', albumsCoverUrl + music.author.name + "/" + music.name);
        let songName = document.getElementById('song-name');
        songName.innerHTML = music.name;
        let songAuthor = document.getElementById('song-author');
        songAuthor.innerHTML = music.author.name;
        if (isFromSongQueue) {
            $('#playerContainer').css('background-color', 'rgb(232, 195, 195)')
        } else {
            $('#playerContainer').css('background-color', '#ececec')
        }
        fillModalTableWithPlaylist('modalCurrentPlaylistTableBody', lastPlayedPlaylistName, allSongsInCurrentPlaylist);
        playerElement.play();
    }
}

/**
 * функция для поигрывания плейлистов
 * обычно вызывается прямо из интерфейса
 * но иногда при окончании предыдущего плейлиста, тоже вызывается из метода playNext()
 * сначала смотрится, является желаемый плейлист для проигрывания предыдущим
 *      если да - то просто вызывает метод playOrPause() с последне игранным музыкой
 *      если нет - то качает из сервера новый compilation и его список песен, запоняет модалку песнями и играет первую его песню
 * @param playlistName
 * @param compilationIndex
 */
function playOrPausePlaylist(playlistName, compilationIndex) {
    var currentPlaylist = getCurrentPlaylist(playlistName);
    var clickedButton;
    var musicIndex = 0;
    allCompilationInCurrentPlaylist = currentPlaylist.currentCumpilationsList;
    allSongsInCurrentPlaylist = currentPlaylist.currentSongsList;
    for (var i = 0; i < allSongsInCurrentPlaylist.length; i++) {
        if (allSongsInCurrentPlaylist[i].compilationIndex === compilationIndex) {
            musicIndex = i;
            break
        }
    }
    var clickedButtons = $(`button[data-playlist_id="${playlistName}_${compilationIndex}"]`);
    for (let i = 0; i < clickedButtons.length; i++) {
        if ($(clickedButtons[i]).css("display") === "inline-block") {
            clickedButton = clickedButtons[i];
        }
    }
    let playingState = clickedButton ? clickedButton.dataset.playing_state : 'on_stop';
    if (playingState === 'on_pause' || playingState === 'on_play') {
        musicIndex = lastPlayedMusicIndex;
    }
    console.log("playlistName")
    console.log(playlistName)
    console.log("compilation index")
    console.log(compilationIndex)
    console.log("music index")
    console.log(musicIndex)
    playOrPause(playlistName, compilationIndex, musicIndex, allSongsInCurrentPlaylist[musicIndex].isFromSongQueue);
}


/**
 * функция для проигрывания следующей песни
 * сперва смотрится на сервере, есть ли на очереди заказанных песен что-то
 *      если есть - то эти песни втискиваются в массив текущих песен сразу после последне-проигранной песни
 *      если нет - то смотрится, нужно ли гирать по-порядку или нет
 *          если не по-порядку, то находится рандомное число в пределах размера текущего списка песен, который не равен
 *          индексу последе-проигранной песни и играется
 *          если по-порядку, то выясняется, последняя ли эта песня
 *              если нет, то играется следующая песня
 *              если да, то смотрится, а последний ли это compilation в текущем списке
 *                  если нет, то играется следующий по очереди compilation
 *                  если да, то играется первый compilation в следующем плейлисте
 */
function playNext() {

    //получаем наши песни в очереди - из списка заказанных песен
    $.get('/api/user/song/songsInQueue', function (songList) {

        // если в очереди заказанных песен есть песни
        if (songList.length > 0) {

            //массив "следующих песен плейлиста" который сейчас будет играть (следом за играющей)
            let songArrayAfterLastPlayedMusic = [];

            /*
            lastPlayedMusicIndex - индекс последне-проигранной песни в своем массиве (см. строку 512)
            allSongsInCurrentPlaylist - текущий плейлист как список песен (см. строку 509)
            Определяем i - индекс следующей песни на проигрывание, если этот индекс меньше общего количества песен в
            текущем плейлисте - то в массив песен который сейчас будет играть (следом за играющей) втыкается следующая
            песня из общего списка песен текущего плейлиста.
            Иными словами - здесь идет разделение - все следующие песни которые должны играть - это отдельный
            массив.
             */
            for (let i = lastPlayedMusicIndex + 1, j = 0; i < allSongsInCurrentPlaylist.length; i++, j++) {
                songArrayAfterLastPlayedMusic[j] = allSongsInCurrentPlaylist[i];
            }

            //определяем индекс следующей песни которая будет играть
            var ind = lastPlayedMusicIndex + 1;

            /*
            Пока следующая песня из текущего плейлиста является песней из очереди - увеличиваем индекс следующей
            песни???
             */
            while (allSongsInCurrentPlaylist[ind].isFromSongQueue) {
                ind++;
            }

            /*
            Для всех песен в нашей очереди заказанных песен - под индекс следующей песни (из общего списка песен в
            текущем плейлисте) втывается заказанная песня из очереди.
            musicIndex - индекс для заказанной песни из общего числа песен в текущем плейлисте
             */
            for (let i = ind, j = 0; j < songList.length; i++, j++) {

                //если есть заказанные песни - следующей песней будет играть песня из очереди
                allSongsInCurrentPlaylist[i] = songList[j];

                //сетим этой заказанной песне в поле isFromSongQueue - true
                allSongsInCurrentPlaylist[i].isFromSongQueue = true;

                //песне из очереди втыкаем индекс - какой по счету из всех песен в текущем плейлисте играет
                allSongsInCurrentPlaylist[i].musicIndex = i;

                /*
                следующей песне из очереди которая будет играть втыкается id подборки в которой она будет проиграна
                (была добавлена)???
                 */
                allSongsInCurrentPlaylist[i].compilationIndex = lastPlayedCompilationIndex;
            }

            /*
            Если есть песни в массиве "следующих песен плейлиста" - они будут играть сразу после проигрывания заказанных
            песен из нашей очереди
             */
            for (let i = ind + songList.length, j = 0; j < songArrayAfterLastPlayedMusic.length; i++, j++) {

                //после всех заказанных песен будут играть песни из массива "следующих песен плейлиста"
                allSongsInCurrentPlaylist[i] = songArrayAfterLastPlayedMusic[j];

                //и этим песням присваивается индекс musicIndex
                allSongsInCurrentPlaylist[i].musicIndex = i;
            }

            //логи в консоль
            console.log(allSongsInCurrentPlaylist);
            fillModalTableWithPlaylist('modalCurrentPlaylistTableBody', lastPlayedPlaylistName, allSongsInCurrentPlaylist);
            playOrPause(lastPlayedPlaylistName, lastPlayedCompilationIndex,
                lastPlayedMusicIndex + 1, allSongsInCurrentPlaylist[lastPlayedMusicIndex + 1].isFromSongQueue);

        //если в очереди заказанных песен нет песен
        } else {

            //если играем песни в перемешку
            if (shuffle) {
                let playlistsLength = allSongsInCurrentPlaylist.length;
                let nextMusicIndex;
                do {

                    //определяем случайный номер песни который будет играть
                    nextMusicIndex = Math.floor(Math.random() * playlistsLength);
                    console.log(nextMusicIndex)
                } while (nextMusicIndex === lastPlayedMusicIndex);
                playOrPause(lastPlayedPlaylistName, allSongsInCurrentPlaylist[nextMusicIndex].compilationIndex,
                    nextMusicIndex);
                return;
            }

            //если это последняя песня
            if (lastPlayedMusicIndex < allSongsInCurrentPlaylist.length - 1) {
                var compilationIndex = allSongsInCurrentPlaylist[lastPlayedMusicIndex + 1].compilationIndex;
                playOrPause(lastPlayedPlaylistName, compilationIndex,
                    lastPlayedMusicIndex + 1, allSongsInCurrentPlaylist[lastPlayedMusicIndex + 1].isFromSongQueue);

            //если не последняя - играем следующий плейлист
            } else {
                let nextPlaylistName = lastPlayedPlaylistName;
                if (lastPlayedPlaylistName === 'morning') {
                    middayPlaylist();
                    if (allSongsInMiddayPlaylist.length !== 0) {
                        nextPlaylistName = 'midday';
                    } else {
                        eveningPlaylist();
                        if (allSongsInEveningPlaylist.length !== 0) {
                            nextPlaylistName = 'evening';
                        }
                    }
                } else if (lastPlayedPlaylistName === "midday") {
                    eveningPlaylist();
                    if (allSongsInEveningPlaylist.length !== 0) {
                        nextPlaylistName = 'evening';
                    } else {
                        morningPlaylist();
                        if (allSongsInMorningPlaylist.length !== 0) {
                            nextPlaylistName = 'morning';
                        }
                    }
                } else {
                    morningPlaylist();
                    if (allSongsInMorningPlaylist.length !== 0) {
                        nextPlaylistName = 'morning';
                    } else {
                        middayPlaylist();
                        if (allSongsInMiddayPlaylist.length !== 0) {
                            nextPlaylistName = 'midday';
                        }
                    }
                }
                if (nextPlaylistName === lastPlayedPlaylistName) {
                    playOrPause(lastPlayedPlaylistName, 0, 0, allSongsInCurrentPlaylist[0].isFromSongQueue)
                } else {
                    lastPlayedPlaylistName = nextPlaylistName;
                    playOrPausePlaylist(lastPlayedPlaylistName, 0);
                }

            }
        }
    });
}


function getCurrentPlaylist(playlistName) {
    var result = {};
    switch (playlistName) {
        case 'morning':
            result.currentCumpilationsList = allCompilationsInMorningPlaylist;
            result.currentSongsList = allSongsInMorningPlaylist;
            return result;
        case 'midday' :
            result.currentCumpilationsList = allCompilationsInMiddayPlaylist;
            result.currentSongsList = allSongsInMiddayPlaylist;
            return result;
        case 'evening' :
            result.currentCumpilationsList = allCompilationsInEveningPlaylist;
            result.currentSongsList = allSongsInEveningPlaylist;
            return result;
        case 'getGenres':
            result.currentCumpilationsList = allCompilationInGenre;
            result.currentSongsList = allSongInGenre;
            return result;
    }
}

/**
 * функция для проигрывания предыдущей песни
 * если плеер играет больше 5 секунд, то при нажатии просто возвращается в начало песни
 * если нет, то смотрится, не первая ли эта песня
 *      если нет то играется предыдущая по индексу песня
 *      если да, то играется последняя песня текущего плейлиста
 */
function playPrevious() {
    if (playerElement.currentTime > 5) {
        playerElement.currentTime = 0;
        return;
    }
    var compilationIndex;
    if (lastPlayedMusicIndex > 0) {
        compilationIndex = allSongsInCurrentPlaylist[lastPlayedMusicIndex - 1].compilationIndex;
        playOrPause(lastPlayedPlaylistName, compilationIndex,
            lastPlayedMusicIndex - 1, allSongsInCurrentPlaylist[lastPlayedMusicIndex - 1].isFromSongQueue);
    } else {
        compilationIndex = allSongsInCurrentPlaylist[allSongsInCurrentPlaylist.length - 1].compilationIndex;
        playOrPause(lastPlayedPlaylistName, compilationIndex,
            allSongsInCurrentPlaylist.length - 1,
            allSongsInCurrentPlaylist[allSongsInCurrentPlaylist.length - 1].isFromSongQueue);
    }
}

