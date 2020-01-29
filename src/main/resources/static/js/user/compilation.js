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
                allSongCompilationsInNewCompilationList = listSongCompilation;
                var htmlCompilation = "Need to add Compilation";
                if (0 < listSongCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');
                    for (var i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += '<div id="songCompilation" class="card-deck">'
                            + '<div class="card pt-10">'
                            + '<a href="#" onclick="showAllSongInSongCompilation(\'getGenres\', ' + listSongCompilation[i].id + ')" data-toggle="modal"' +
                            ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
                            + '<img src="/img/compilation/compilation' + listSongCompilation[i].id + '.svg" width="50" height="50" alt="' +
                            listSongCompilation[i].name + '" >'
                            + '<p>Песни подборки</p></a>'
                            + '<div class="card-body">'
                            + '<h4 class="card-title">Title:' + listSongCompilation[i].name + '</h4>'
                            + '<p class="card-text">Discription: Some text</p>'
                            + '</div>'
                            + '<div class="card-footer">'
                            + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
                        let musicButton = `<button class="media-button" data-playlist_id="getGenres_${i}" onclick="playOrPausePlaylist('getGenres', ${listSongCompilation[i].id}, ${i})"><img class="media-img" alt="Play" src="/img/play.png"></button>`;
                        if (lastPlayedPlaylistIndex === listSongCompilation[i].id && !playerElement.paused) {
                            musicButton = `<td><button class="media-button" data-playlist_id="getGenres_${i}" onclick="playOrPausePlaylist('getGenres', ${listSongCompilation[i].id}, ${i})"><img class="media-img" alt="Pause" src="/img/pause.png"></button></td>`;
                        } else if (lastPlayedPlaylistIndex === listSongCompilation[i].id && playerElement.paused && playerElement.currentTime > 0) {
                            musicButton = `<td><button class="media-button" data-playlist_id="getGenres_${i}" onclick="playOrPausePlaylist('getGenres', ${listSongCompilation[i].id}, ${i})"><img class="media-img" alt="Resume" src="/img/resume.png"></button></td>`;
                        }
                        htmlCompilation += musicButton;
                        htmlCompilation += '&nbsp;' + '&nbsp;'
                            + '<button class="btn btn-secondary" id="btnAddMorningPlaylist-' + listSongCompilation[i].id + '" onclick="addMorningPlaylist(' + listSongCompilation[i].id + ')">Утро</button>'
                            + '&nbsp;'
                            + '<button class="btn btn-secondary" id="btnMiddayPlaylist-' + listSongCompilation[i].id + '" onclick="addMiddayPlaylist(' + listSongCompilation[i].id + ')">День</button>'
                            + '&nbsp;'
                            + '<button class="btn btn-secondary" id="btndEveningPlaylist-' + listSongCompilation[i].id + '" onclick="addEveningPlaylist(' + listSongCompilation[i].id + ')">Вечер</button>'
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
    $.get('/api/user/play-list/morning-playlist/get/all-song-compilation', function (morningPlaylist) {
        fillPlaylistsTab('morning', 'morningCompilations', morningPlaylist);
    });
}

//получаем все подборки в дневном плейлисте
function getAllCompilationsInMiddayPlaylist() {
    $.get('/api/user/play-list/midday-playlist/get/all-song-compilation', function (middayPlayList) {
        fillPlaylistsTab('midday', 'middayCompilations', middayPlayList);
    });
}

//получаем все подборки в вечернем плейлисте
function getAllCompilationsInEveningPlaylist() {
    $.get('/api/user/play-list/evening-playlist/get/all-song-compilation', function (eveningPlayList) {
        fillPlaylistsTab('evening', 'eveningCompilations', eveningPlayList);
    });
}

function fillPlaylistsTab(firstId, secondId, playlist) {
    allSongCompilationsInNewCompilationList = playlist;
    $(`#${firstId}`).empty();
    var htmlCompilation = '';
    //bootstrap card
    htmlCompilation += ('<div class="card-deck" id="eveningCompilations">');
    for (var i = 0; i < playlist.length; i++) {
        htmlCompilation += '<div class="card pt-10">'
            + '<a href="#" id="' + playlist[i].id + '" onclick="showAllSongInSongCompilation(\'' + firstId + '\', ' + playlist[i].id + ')" data-toggle="modal"'
            + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
            + '<img src="/img/compilation/compilation' + playlist[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' + playlist[i].name + '">'
            + '<p>Песни подборки</p></a>'
            + '<div class="card-body">'
            + '<h4 class="card-title">Title: ' + playlist[i].name + '</h4>'
            + '<p class="card-text">Description: Some text</p>'
            + '</div>'
            + '<div class="card-footer">'
            + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
        let musicButton = `<button class="media-button" data-playlist_id="${firstId}_${i}" onclick="playOrPausePlaylist(\'${firstId}\', ${playlist[i].id}, ${i})"><img class="media-img" alt="Play" src="/img/play.png"></button>`;
        if (firstId === lastPlayedCompilationListName && lastPlayedPlaylistIndex === playlist[i].id && !playerElement.paused) {
            musicButton = `<button class="media-button" data-playlist_id="${firstId}_${i}" onclick="playOrPausePlaylist(\'${firstId}\', ${playlist[i].id}, ${i})"><img class="media-img" alt="Pause" src="/img/pause.png"></button>`;
        } else if (firstId === lastPlayedCompilationListName && lastPlayedPlaylistIndex === playlist[i].id && playerElement.paused && playerElement.currentTime > 0) {
            musicButton = `<button class="media-button" data-playlist_id="${firstId}_${i}" onclick="playOrPausePlaylist(\'${firstId}\', ${playlist[i].id}, ${i})"><img class="media-img" alt="Resume" src="/img/resume.png"></button>`;
        }
        htmlCompilation += musicButton;
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
    $(`#${firstId} #${secondId}`).remove();
    $(`#${firstId}`).append(htmlCompilation);
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
                + '<h4 class="card-title">Title: ' + songCompilation[i].name + '</h4>'
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
        $.get('/api/user/song/get/all-song/song-compilation/' + id, function (compilationSongs) {
            fillModalTableWithPlaylist('modalPlaylistTableBody', compilationListName, songCompilation, compilationSongs);
            $('#modalPlaylistName').text(songCompilation.name);
        });
    });
}

// функия для заполнения модалки песнями, id которого передается первым параметром
function fillModalTableWithPlaylist(modalId, compilationListName, songCompilation, songCompilationSongs) {
    $(`#${modalId}`).empty();
    let compilationIndex = 0;
    for (let i = 0; i < allSongCompilationsInNewCompilationList.length; i++) {
        if (songCompilation.id === allSongCompilationsInNewCompilationList[i].id) {
            compilationIndex = i;
            i = allSongCompilationsInNewCompilationList.length;
        }
    }
    newPlaylist = songCompilation;
    newSongList = songCompilationSongs;
    for (let i = 0; i < songCompilationSongs.length; i++) {
        let song = songCompilationSongs[i];
        let musicTr = $(`<tr></tr>`);
        let musicTd = `<td>${song.name}</td><td>${song.author.name}</td><td>${song.genre.name}</td>`;
        let musicTdButton = `<td><button class="media-button" data-playing_state="on_stop" data-music_id="${compilationListName}_${compilationIndex}_${i}" onclick="playOrPause(\'${compilationListName}\', ${compilationIndex}, ${i})"><img class="media-img" alt="Play" src="/img/play.png"></button></td>`;
        let lastPlayedMusicId = lastPlayedCompilationListName + '_' + lastPlayedPlaylistIndex + '_' + lastPlayedMusicIndex;
        if (lastPlayedMusicId === compilationListName + '_' + compilationIndex + '_' + i && !playerElement.paused) {
            musicTdButton = `<td><button class="media-button" data-playing_state="on_play" data-music_id="${compilationListName}_${compilationIndex}_${i}" onclick="playOrPause(\'${compilationListName}\', ${compilationIndex}, ${i})"><img class="media-img" alt="Pause" src="/img/pause.png"></button></td>`;
        } else if (lastPlayedMusicId === compilationListName + '_' + compilationIndex + '_' + i && playerElement.paused && playerElement.currentTime > 0) {
            musicTdButton = `<td><button class="media-button" data-playing_state="on_pause" data-music_id="${compilationListName}_${compilationIndex}_${i}" onclick="playOrPause(\'${compilationListName}\', ${compilationIndex}, ${i})"><img class="media-img" alt="Resume" src="/img/resume.png"></button></td>`;
        }
        musicTd += musicTdButton;
        musicTr.html(musicTd);
        $(`#${modalId}`).append(musicTr);
    }
}

// функия для заполнения модалки текущего списка песен
function fillModalCurrentPlaylistTable(songCompilation, songCompilationSongs) {
    fillModalTableWithPlaylist('modalCurrentPlaylistTableBody', lastPlayedCompilationListName, songCompilation, songCompilationSongs);
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

// индекс последне-проигранной песни в своем массиве
let lastPlayedMusicIndex = -1;

// индекс последне-проигранного массива песен в своем списке
let lastPlayedPlaylistIndex = -1;

// переменная, которая хранит id кнопки последне-проигранного списка
let lastPlayedPlaylistId = 'none';

// последне-проигранный объект compilation
let lastPlayedPlaylist;

// последне-проигранный массив песен, который достаётся с помощью lastPlayedPlaylist.id
let lastPlayedSongList;

// новый объект compilation, песни которого открываются в модалке
let newPlaylist;

// новый массив песен, который достаётся с помощью newPlaylist.id
let newSongList;

// массив всех compilation в новом разделе (а их сейчас всего 4: getGenres, morning, midday, evening)
let allSongCompilationsInNewCompilationList;

// массив всех compilation в текущем разделе (а их сейчас всего 4: getGenres, morning, midday, evening)
let allSongCompilationsInCurrentCompilationList;

// имя текущего раздела
let lastPlayedCompilationListName = 'none';

$(function () {
    player = $('#player');

    // при нажатии на кнопку "предыдущий"
    $("#previousAudioButton").on("click", function () {
        playPrevious();
    });
    // при нажатии на кнопку "играть \ пауза"
    $("#playOrPauseAudioButton").on("click", function () {
        playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, lastPlayedMusicIndex);
    });
    // при нажатии на кнопку "следующий"
    $("#nextAudioButton").on("click", function () {
        playNext();
    });
    // при нажатии на кнопку "список". выводит список песен, который играют на данный момент
    $("#currentPlaylistButton").on("click", function () {
        fillModalCurrentPlaylistTable(lastPlayedPlaylist, lastPlayedSongList);
    });
    // при нажатии на кнопку "остановить". останавливает воспроизведение и музыой для воспроизведения ставит первую песню в текущем массиве песен
    $("#stopAudioButton").on("click", function () {
        let lastPlayedMusicsButtons = $(`button[data-music_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}']`);
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
        let currentPlayingButtons = $(`button[data-music_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < currentPlayingButtons.length; i++) {
            setButtonOnPlay(currentPlayingButtons[i]);
        }
        lastPlayedPlaylistId = lastPlayedCompilationListName + '_' + lastPlayedPlaylistIndex;
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}']`);
        for (let i = 0; i < lastPlayedPlaylistsPlayButtons.length; i++) {
            setButtonOnPlay(lastPlayedPlaylistsPlayButtons[i]);
        }
        $('#globalPlayButton').prop('src', '/img/pause.png');
    });
    // при паузе плеера. ищутся кнопки песен, который играли и меняется изображение на "продолжить"
    playerElement.addEventListener("pause", function () {
        let currentPlayingButtons = $(`button[data-music_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < currentPlayingButtons.length; i++) {
            setButtonOnPause(currentPlayingButtons[i]);
        }
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}']`);
        for (let i = 0; i < lastPlayedPlaylistsPlayButtons.length; i++) {
            setButtonOnPause(lastPlayedPlaylistsPlayButtons[i]);
        }
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
    playerElement.addEventListener('timeupdate', function() {
        $('#seekbar').attr("value", playerElement.currentTime / playerElement.duration);
    });
    var progressBar = document.getElementsByTagName('progress')[0];
    if(playerElement && progressBar){
        progressBar.addEventListener('click', function(event){
            var x = event.pageX - this.offsetLeft;
            var y = event.pageY - this.offsetTop;
            var clickedValue = x * this.max / this.offsetWidth;
            progressBar.value = clickedValue;
            playerElement.currentTime = playerElement.duration*clickedValue;
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
    button.childNodes[0].setAttribute('src', '/img/pause.png');
}

// функция для изменения изображения и data-playing_state на кнопке при паузе музыки
function setButtonOnPause(button) {
    button.dataset.playing_state = 'on_pause';
    button.childNodes[0].setAttribute('src', '/img/resume.png');
}

// функция для изменения изображения и data-playing_state на кнопке при остановке музыки
function setButtonOnStop(button) {
    button.dataset.playing_state = 'on_stop';
    button.childNodes[0].setAttribute('src', '/img/play.png');
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
function playOrPause(compilationListName, playlistIndex, musicIndex) {
    let clickedButtons = $(`button[data-music_id='${compilationListName}_${playlistIndex}_${musicIndex}']`);
    console.log(clickedButtons.length);
    let clickedButton = clickedButtons[0];
    let playingState = clickedButton.dataset.playing_state;
    if (playingState === 'on_stop') {
        let lastPlayedMusicsButtons = $(`button[data-music_id='${lastPlayedCompilationListName}_${lastPlayedPlaylistIndex}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }
        if (playlistIndex !== lastPlayedPlaylistIndex) {
            lastPlayedPlaylist = newPlaylist;
            lastPlayedSongList = newSongList;
            let lastPlayedPlaylistsButtons = $(`button[data-playlist_id='${lastPlayedPlaylistId}']`);
            if (lastPlayedPlaylistsButtons[0] !== undefined) {
                for (let i = 0; i < lastPlayedPlaylistsButtons.length; i++) {
                    setButtonOnStop(lastPlayedPlaylistsButtons[i]);
                }
            }
        }
        if (compilationListName !== lastPlayedCompilationListName) {
            lastPlayedCompilationListName = compilationListName;
            allSongCompilationsInCurrentCompilationList = allSongCompilationsInNewCompilationList;

        }
        lastPlayedPlaylistIndex = playlistIndex;
        lastPlayedMusicIndex = musicIndex;
        let music = lastPlayedSongList[musicIndex];
        player.attr('src', musicUrl + music.author.name+ "/" + music.name);
        $('#albums-cover').attr('src', "/img/albumsCovers/cover-" + music.id + ".JPEG");
        playerElement.play();
    } else if (playingState === 'on_play') {
        playerElement.pause();
    } else if (playingState === 'on_pause') {
        playerElement.play();
    }
}

// функция для поигрывания плейлистов
// обычно вызывается прямо из интерфейса
// но иногда при окончании предыдущего плейлиста, тоже вызывается из метода playNext()
// сначала смотрится, является желаемый плейлист для проигрывания предыдущим
//      если да - то просто вызывает метод playOrPause() с последне игранным музыкой
//      если нет - то качает из сервера новый compilation и его список песен, запоняет модалку песнями и играет первую его песню
function playOrPausePlaylist(compilationListName, playlistId, playlistIndex) {
    if (lastPlayedPlaylistIndex === playlistIndex && lastPlayedCompilationListName === compilationListName) {
        playOrPause(compilationListName, playlistIndex, 0);
    } else {
        $.get('/api/user/song-compilation/get/song-compilation/' + playlistId, function (songCompilation) {
            $.get('/api/user/song/get/all-song/song-compilation/' + playlistId, function (compilationSongs) {
                fillModalTableWithPlaylist('modalPlaylistTableBody', compilationListName, songCompilation, compilationSongs);
                $('#modalPlaylistName').text(songCompilation.name);
                playOrPause(compilationListName, playlistIndex, 0);
            });
        });
    }
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
//                  если да, то играется первый по очереди compilation
function playNext() {
    $.get('/api/user/song/songsInQueue', function (songList) {
        if (songList.length > 0) {
            let songArrayAfterLastPlayedMusic = [];
            for (let i = lastPlayedMusicIndex + 1, j = 0; i < lastPlayedSongList.length; i++, j++) {
                songArrayAfterLastPlayedMusic[j] = lastPlayedSongList[i];
            }
            for (let i = lastPlayedMusicIndex + 1, j = 0; i < songList.length; i++, j++) {
                lastPlayedSongList[i] = songList[j];
            }
            for (let i = lastPlayedMusicIndex + songList.length + 1, j = 0; i < songArrayAfterLastPlayedMusic.length; i++, j++) {
                lastPlayedSongList[i] = songArrayAfterLastPlayedMusic[j];
            }
            fillModalCurrentPlaylistTable(lastPlayedPlaylist, lastPlayedSongList);
            playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, lastPlayedMusicIndex + 1);
        } else {
            if (shuffle) {
                let playlistsLength = lastPlayedSongList.length;
                let nextMusicIndex;
                do {
                    nextMusicIndex = Math.floor(Math.random() * playlistsLength);
                } while (nextMusicIndex === lastPlayedMusicIndex);
                playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, nextMusicIndex);
                return;
            }
            if (lastPlayedMusicIndex < lastPlayedSongList.length - 1) {
                playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, lastPlayedMusicIndex + 1);
            } else {
                let nextPlaylistId;
                if (lastPlayedPlaylistIndex < allSongCompilationsInCurrentCompilationList.length - 1) {
                    nextPlaylistId = allSongCompilationsInCurrentCompilationList[lastPlayedPlaylistIndex + 1].id;
                    playOrPausePlaylist(lastPlayedCompilationListName, nextPlaylistId, lastPlayedPlaylistIndex + 1);
                } else {
                    nextPlaylistId = allSongCompilationsInCurrentCompilationList[0].id;
                    playOrPausePlaylist(lastPlayedCompilationListName, nextPlaylistId, 0);
                }
            }
        }
    });
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
    if (lastPlayedMusicIndex > 0) {
        playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, lastPlayedMusicIndex - 1);
    } else {
       playOrPause(lastPlayedCompilationListName, lastPlayedPlaylistIndex, lastPlayedSongList.length-1);
    }
}
