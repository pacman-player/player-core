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
                        htmlCompilation += '<div id="songCompilation" class="card-deck">'
                            + '<div class="card pt-10">'
                            + '<a href="#" onclick="showAllSongInSongCompilation(\'getGenres\', ' + listCompilation[i].id + ')" data-toggle="modal"' +
                            ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
                            + '<img src="/img/compilation/compilation' + listCompilation[i].id + '.jpg" width="80" height="80" alt="' +
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
                            + '<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + listCompilation[i].id + '" onclick="addMorningPlaylist(' + listCompilation[i].id + ')">Утро</button>'
                            + '&nbsp;'
                            + '<button class="btn btn-secondary" id="btnMiddayPlaylist-' + listCompilation[i].id + '" onclick="addMiddayPlaylist(' + listCompilation[i].id + ')">День</button>'
                            + '&nbsp;'
                            + '<button class="btn btn-secondary" id="btndEveningPlaylist-' + listCompilation[i].id + '" onclick="addEveningPlaylist(' + listCompilation[i].id + ')">Вечер</button>'
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
    // $(document).on('click', '#morning-music-nav', function () {
    //     //getAllCompilationsInMorningPlaylist();
    // });
    //
    // //получаем подборки из дневного плейлиста
    // $(document).on('click', '#midday-music-nav', function () {
    //     //getAllCompilationsInMiddayPlaylist();
    // });
    //
    // //получаем подборки из вечернего плейлиста
    // $(document).on('click', '#evening-music-nav', function () {
    //     //getAllCompilationsInEveningPlaylist();
    // });
});

//добавляем подборку в утренний плейлист
function addMorningPlaylist(idCompilation) {
    $.ajax({
        method: 'GET',
        url: '/api/user/play-list/morning-playlist/add/song-compilation/' + idCompilation,
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
        url: '/api/user/play-list/midday-playlist/add/song-compilation/' + idCompilation,
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
        url: '/api/user/play-list/evening-playlist/add/song-compilation/' + idCompilation,
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
    //$.get('/api/user/play-list/morning-playlist/get/all-song-compilation', function (morningPlaylist) {
    fillPlaylistsTab('morning', 'morningCompilations', getCurrentPlaylist('morning').currentCumpilationsList);
    //  });
}

//получаем все подборки в дневном плейлисте
function getAllCompilationsInMiddayPlaylist() {
    fillPlaylistsTab('midday', 'middayCompilations', getCurrentPlaylist('midday').currentCumpilationsList);
}

//получаем все подборки в вечернем плейлисте
function getAllCompilationsInEveningPlaylist() {
    fillPlaylistsTab('evening', 'eveningCompilations', getCurrentPlaylist('evening').currentCumpilationsList);
}

function fillPlaylistsTab(playListName, secondId, playlist) {
    //allSongCompilationsInNewCompilationList = playlist;
    $(`#${playListName}`).empty();
    var htmlCompilation = '';
    //bootstrap card
    htmlCompilation += ('<div class="card-deck" id="eveningCompilations">');
    for (var i = 0; i < playlist.length; i++) {
        htmlCompilation += '<div class="card pt-10">'
            + '<a href="#" id="' + playlist[i].id + '" onclick="showAllSongInSongCompilation(\'' + playListName + '\', ' + playlist[i].id + ')" data-toggle="modal"'
            + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
            + '<img src="/img/compilation/compilation' + playlist[i].id + '.jpg" width="80" height="80" class="card-img-top" alt="' + playlist[i].name + '">'
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
        htmlCompilation += '&nbsp;'
            + '<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + playlist[i].id + '" onclick="addMorningPlaylist(' + playlist[i].id + ')">Утро</button>'
            + '&nbsp;'
            + '<button class="btn btn-secondary" id="btnMiddayPlaylist-' + playlist[i].id + '" onclick="addMiddayPlaylist(' + playlist[i].id + ')">День</button>'
            + '&nbsp;'
            + '<button class="btn btn-secondary" id="btnEveningPlaylist-' + playlist[i].id + '" onclick="addEveningPlaylist(' + playlist[i].id + ')">Вечер</button>'
            + '</div>'
            + '</div>';
    }
    //закрываю bootstrap card
    htmlCompilation += ('</div>');
    $(`#${playListName} #${secondId}`).remove();
    $(`#${playListName}`).append(htmlCompilation);
}

// #evening
// #eveningCompilations

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
        //достаю все песни из подборки
        // if (compilationListName === "getGenres") {
        //     $.get('/api/user/song/get/all-song/song-compilation/' + id, function (compilationSongs) {
        //         fillModalTableWithPlaylist('modalPlaylistTableBody', compilationListName, compilationSongs);
        //     });
        // } else {
        fillModalTableWithPlaylist('modalPlaylistTableBody', compilationListName, getCompilationOfPlaylist(compilationListName, id));
        // }
        $('#modalPlaylistName').text(songCompilation.name);
    });
}


//функция для получения из всего плейлиста список песен подборки по id
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
        let musicTr = $(`<tr></tr>`);
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
        let trackBubble = '<div class="d-track__bubble" id="bubble"></div>';
        musicTd += playButton;
        musicTd += pauseButton;
        musicTd += trackBubble;
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

// адрес, по которому клиент обращается для проигрывания музыки
let musicUrl = "/api/music/play/";

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

// последне-проигранный объект compilation
let lastPlayedPlaylist;

// последне-проигранный массив песен, который достаётся с помощью lastPlayedPlaylist.id
let lastPlayedSongList;

// имя текущего раздела
let lastPlayedPlaylistName = 'none';

/**
 * Вспомогательная функция для получения утреннего, дневного или вечернего плейлистов.
 * Заполняет список всех песен данного плейлиста, помечает песни доп.информацией:
 *  compilationId - id подборки, в которой находится песня
 *  compilationIndex - индекс подборки в данном плейлисте
 *  musicIndex - индекс песни в данном плейлисте.
 * allCompilationsInPlaylist - список подборок соответсующего плейлиста,
 * allSongsInPlaylist - список песен соответсвующего плелиста, список передается пустым.
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

$(function () {
    player = $('#player');

    $.get('/api/user/play-list/morning-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMorningPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMorningPlaylist, allSongsInMorningPlaylist)
        getAllCompilationsInMorningPlaylist();
    });
    $.get('/api/user/play-list/midday-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMiddayPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMiddayPlaylist, allSongsInMiddayPlaylist)
        getAllCompilationsInMiddayPlaylist();
    });
    $.get('/api/user/play-list/evening-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInEveningPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInEveningPlaylist, allSongsInEveningPlaylist)
        getAllCompilationsInEveningPlaylist();
    });

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
        let music = lastPlayedSongList[0];
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
        if (playerElement.volume === 0 || playerElement.muted) {
            $('#soundImg').prop('src', '/img/soundOff.png');
        } else {
            $('#soundImg').prop('src', '/img/soundOn.png');
        }
    });

    //при изменении currentTime
    playerElement.addEventListener('timeupdate', function () {
        $('#seekbar').attr("value", playerElement.currentTime / playerElement.duration);
    });
    var progressBar = document.getElementsByTagName('progress')[0];
    if (playerElement && progressBar) {
        progressBar.addEventListener('click', function (event) {
            var x = event.pageX - this.offsetLeft;
            var y = event.pageY - this.offsetTop;
            var clickedValue = x * this.max / this.offsetWidth;
            progressBar.value = clickedValue;
            playerElement.currentTime = playerElement.duration * clickedValue;
        });
    }
    // при нажатии на кнопку "громкость вкл / выкл"
    $("#soundButton").on("click", function () {
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
});

// функция для изменения изображения и data-playing_state на кнопке при проигрывании музыки
function setButtonOnPlay(button) {
    button.dataset.playing_state = 'on_play';
    //button.childNodes[0].setAttribute('src', '/img/pause.png');
}

// функция для изменения изображения и data-playing_state на кнопке при паузе музыки
function setButtonOnPause(button) {
    button.dataset.playing_state = 'on_pause';
    //button.childNodes[0].setAttribute('src', '/img/resume.png');
}

// функция для изменения изображения и data-playing_state на кнопке при остановке музыки
function setButtonOnStop(button) {
    button.dataset.playing_state = 'on_stop';
    //button.childNodes[0].setAttribute('src', '/img/play.png');
}

// функция для проигрывания / паузы
// сперва находится кнопка нажатия и определяется его состояние
// если он остановлен - то ищутся предыдуще-игранные кнопки и меняется их состояние
// потом смотрим, это текущий плейлист, или нет
//      если нет - то последне проигранным плейлистом и списком песен отмечаются новые, меняются состояния кнопок
// смотрим, это старый список compilation, или нет. если нет - то новый помечается текущим
// потом меняются последне проигранные песни и плейлист, музыка на плеере и играет песню
//
// а если песня играется - то ставится на паузу
// а если песня на паузе - то продолжается воспроизведение
function playOrPause(playlistName, compilationIndex, musicIndex) {
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
    console.log("ClickedButton : ");
    console.log(clickedButton);
    console.log(playlistName);
    console.log(compilationIndex);
    let playingState = clickedButton ? clickedButton.dataset.playing_state : 'on_stop';
    console.log('state : ' + playingState);
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
        $('#albums-cover').attr('src', "/img/albumsCovers/cover-" + music.id + ".jpg");
        let songName = document.getElementById('song-name');
        songName.innerHTML = music.name;
        let songAuthor = document.getElementById('song-author');
        songAuthor.innerHTML = music.author.name;
        playerElement.play();
    }
}

// функция для поигрывания плейлистов
// обычно вызывается прямо из интерфейса
// но иногда при окончании предыдущего плейлиста, тоже вызывается из метода playNext()
// сначала смотрится, является желаемый плейлист для проигрывания предыдущим
//      если да - то просто вызывает метод playOrPause() с последне игранным музыкой
//      если нет - то качает из сервера новый compilation и его список песен, запоняет модалку песнями и играет первую его песню
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
    playOrPause(playlistName, compilationIndex, musicIndex);
}


// функция для проигрывания следующей песни
// сперва смотрится на сервере, есть ли на очереди заказанных песен что-то
//      если есть - то эти песни втискиваются в массив текущих песен сразу после последне-проигранной песни
//      если нет - то смотрится, нужно ли гирать по-порядку или нет
//          если не по-порядку, то находится рандомное число в пределах размера текущего списка песен, который не равен индексу последе-проигранной песни и играется
//          если по-порядку, то выясняется, последняя ли эта песня
//               если нет, то играется следующая песня
//               если да, то смотрится, а последний ли это compilation в текущем списке
//                  если нет, то играется следующий по очереди compilation
//                  если да, то играется первый compilation в следующем плейлисте
function playNext() {
    $.get('/api/user/song/songsInQueue', function (songList) {
         if (songList.length > 0) {
             let songArrayAfterLastPlayedMusic = [];
             for (let i = lastPlayedMusicIndex + 1, j = 0; j < lastPlayedSongList.length; i++, j++) {
                 songArrayAfterLastPlayedMusic[j] = lastPlayedSongList[i];
             }
             for (let i = lastPlayedMusicIndex + 1, j = 0; j < songList.length; i++, j++) {
                 lastPlayedSongList[i] = songList[j];
             }
             for (let i = lastPlayedMusicIndex + songList.length + 1, j = 0; j < songArrayAfterLastPlayedMusic.length; i++, j++) {
                 lastPlayedSongList[i] = songArrayAfterLastPlayedMusic[j];
             }
             fillModalCurrentPlaylistTable(lastPlayedPlaylist, lastPlayedSongList);
             playOrPause(lastPlayedPlaylistName, lastPlayedCompilationIndex, lastPlayedMusicIndex + 1);
         } else {
    if (shuffle) {
        let playlistsLength = lastPlayedSongList.length;
        let nextMusicIndex;
        do {
            nextMusicIndex = Math.floor(Math.random() * playlistsLength);
        } while (nextMusicIndex === lastPlayedMusicIndex);
        playOrPause(lastPlayedPlaylistName, lastPlayedCompilationIndex, nextMusicIndex);
        return;
    }
    if (lastPlayedMusicIndex < allSongsInCurrentPlaylist.length - 1) {
        var compilationIndex = allSongsInCurrentPlaylist[lastPlayedMusicIndex + 1].compilationIndex;
        console.log(lastPlayedPlaylistName)
        console.log(lastPlayedCompilationIndex)
        console.log(lastPlayedMusicIndex + 1)
        playOrPause(lastPlayedPlaylistName, compilationIndex, lastPlayedMusicIndex + 1);
    } else {
        if (lastPlayedPlaylistName === 'morning') {
            lastPlayedPlaylistName = 'midday';
        } else if (lastPlayedPlaylistName === "midday") {
            lastPlayedPlaylistName = 'evening';
        } else {
            lastPlayedPlaylistName = 'morning';
        }
        playOrPausePlaylist(lastPlayedPlaylistName, 0);
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

// функция для проигрывания предыдущей песни
// если плеер играет больше 5 секунд, то при нажатии просто возвращается в начало песни
// если нет, то смотрится, не первая ли эта песня
//      если нет то играется предыдущая по индексу песня
//      если да, то играется последняя песня текущего плейлиста
function playPrevious() {
    if (playerElement.currentTime > 5) {
        playerElement.currentTime = 0;
        return;
    }
    var compilationIndex;
    if (lastPlayedMusicIndex > 0) {
        compilationIndex = allSongsInCurrentPlaylist[lastPlayedMusicIndex - 1].compilationIndex;
        playOrPause(lastPlayedPlaylistName, compilationIndex, lastPlayedMusicIndex - 1);
    } else {
        compilationIndex = allSongsInCurrentPlaylist[allSongsInCurrentPlaylist.length - 1].compilationIndex;
        playOrPause(lastPlayedPlaylistName, compilationIndex, allSongsInCurrentPlaylist.length - 1);
    }
}




