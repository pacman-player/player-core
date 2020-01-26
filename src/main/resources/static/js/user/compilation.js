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
                allSongCompilationsInNewGenre = listSongCompilation;
                var htmlCompilation = "Need to add Compilation";
                if (0 < listSongCompilation.length) {
                    htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');
                    for (var i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += '<div id="songCompilation" class="card-deck">'
                            + '<div class="card pt-10">'
                            + '<a href="#" onclick="showAllSongInSongCompilation(' + listSongCompilation[i].id + ')" data-toggle="modal"' +
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
                        let musicButton = `<button class="media-button" data-playlist_id="${listSongCompilation[i].id}" onclick="playOrPausePlaylist(${listSongCompilation[i].id})"><img class="media-img" alt="Play" src="/img/play.png"></button>`;
                        if (lastPlayedPlaylistId === listSongCompilation[i].id && !playerElement.paused) {
                            musicButton = `<td><button class="media-button" data-playing_state="on_play" data-playlist_id="${listSongCompilation[i].id}" onclick="playOrPausePlaylist(${listSongCompilation[i].id})"><img class="media-img" alt="Pause" src="/img/pause.png"></button></td>`;
                        } else if (lastPlayedPlaylistId === listSongCompilation[i].id && playerElement.paused && playerElement.currentTime > 0) {
                            musicButton = `<td><button class="media-button" data-playing_state="on_pause" data-playlist_id="${listSongCompilation[i].id}" onclick="playOrPausePlaylist(${listSongCompilation[i].id})"><img class="media-img" alt="Resume" src="/img/resume.png"></button></td>`;
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

    //код с дева
    // $(document).on('click', '#songCompilation', function () {
    //     let compilationName = $(this).text();
    //     $("#compilationModalCompilationName").text(compilationName);
    //     $("#modalPlaylist").trigger("click");
    //     $.get("/api/user/song-compilation/songsBySongCompilation?compilationName=" + compilationName, function (songList) {
    //             $("#compilationModalTableBody").empty();
    //             console.log(songList);
    //             for (let i = 0; i < songList.length; i++) {
    //                 let newTr = $(`<tr></tr>`);
    //                 let td = ``;
    //                 td += `<td>${songList[i].name}</td>`
    //                     + `<td>${songList[i].author}</td>`
    //                     + `<td>${songList[i].genre}</td>`
    //                     + `<td><div class="btn-group"><button id="playMusic${songList[i].id}" onclick="playOrPauseMusic(${songList[i].id})"><img height="20" width="20" alt="Play/Pause" src="/img/play.svg"/></button>`
    //                     + `<button id="downloadMusic${songList[i].id}" onclick="downloadMusic(${songList[i].id})"><img height="20" width="20"  alt="Download" src="/img/download.svg"/></button></div></td>`;
    //                 newTr.html(td);
    //                 $("#compilationModalTableBody").append(newTr);
    //             }
    //         }
    //     )
    //     ;
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
    allSongCompilationsInNewGenre = playlist;
    $(`#${firstId}`).empty();
    var htmlCompilation = '';
    //bootstrap card
    htmlCompilation += ('<div class="card-deck" id="eveningCompilations">');
    for (var i = 0; i < playlist.length; i++) {
        htmlCompilation += '<div class="card pt-10">'
            + '<a href="#" id="' + playlist[i].id + '" onclick="showAllSongInSongCompilation(' + playlist[i].id + ')" data-toggle="modal"'
            + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
            + '<img src="/img/compilation/compilation' + playlist[i].id + '.svg" width="50" height="50" class="card-img-top" alt="' + playlist[i].name + '">'
            + '<p>Песни подборки</p></a>'
            + '<div class="card-body">'
            + '<h4 class="card-title">Title: ' + playlist[i].name + '</h4>'
            + '<p class="card-text">Description: Some text</p>'
            + '</div>'
            + '<div class="card-footer">'
            + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
        let musicButton = `<button class="media-button" data-playlist_id="${playlist[i].id}" onclick="isNewCompilationsList=true;playOrPausePlaylist(${playlist[i].id})"><img class="media-img" alt="Play" src="/img/play.png"></button>`;
        if (lastPlayedPlaylistId === playlist[i].id && !playerElement.paused) {
            musicButton = `<td><button class="media-button" data-playing_state="on_play" data-playlist_id="${playlist[i].id}" onclick="playOrPausePlaylist(${playlist[i].id})"><img class="media-img" alt="Pause" src="/img/pause.png"></button></td>`;
        } else if (lastPlayedPlaylistId === playlist[i].id && playerElement.paused && playerElement.currentTime > 0) {
            musicButton = `<td><button class="media-button" data-playing_state="on_pause" data-playlist_id="${playlist[i].id}" onclick="playOrPausePlaylist(${playlist[i].id})"><img class="media-img" alt="Resume" src="/img/resume.png"></button></td>`;
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
function showAllSongInSongCompilation(id) {
    //достаю инфу о подборке (название, картинку,  пр.) для модалки
    $.getJSON('/api/user/song-compilation/get/song-compilation/' + id, function (songCompilation) {
        var htmlAboutSongCompilationForModal = '';
        for (var i = 0; i < songCompilation.length; i++) {
            htmlAboutSongCompilationForModal = '<div class="card-deck" id="aboutCompilations">'
                + '<div class="card pt-10">'
                + '<a href="#" id="' + songCompilation[i].id + '" onclick="showAllSongInSongCompilation(' + eveningPlayList[i].id + ')" data-toggle="modal"'
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
            fillModalTableWithPlaylist('modalPlaylistTableBody', songCompilation, compilationSongs);
            $('#modalPlaylistName').text(songCompilation.name);
        });
    });
}

//fill modal table with playlist
function fillModalTableWithPlaylist(modalId, songCompilation, songCompilationSongs) {
    $(`#${modalId}`).empty();
    newPlaylist = songCompilation;
    newSongList = songCompilationSongs;
    for (let i = 0; i < songCompilationSongs.length; i++) {
        let song = songCompilationSongs[i];
        let musicTr = $(`<tr></tr>`);
        let musicTd = `<td>${song.name}</td><td>${song.author.name}</td><td>${song.genre.name}</td>`;
        let musicTdButton = `<td><button class="media-button" data-playing_state="on_stop" data-music_id="${songCompilation.id}_${i}" onclick="playOrPause(${songCompilation.id}, ${i})"><img class="media-img" alt="Play" src="/img/play.png"></button></td>`;
        let lastPlayedMusicId = lastPlayedPlaylistId + '_' + lastPlayedMusicIndex;
        if (lastPlayedMusicId === songCompilation.id + '_' + i && !playerElement.paused) {
            musicTdButton = `<td><button class="media-button" data-playing_state="on_play" data-music_id="${songCompilation.id}_${i}" onclick="playOrPause(${songCompilation.id}, ${i})"><img class="media-img" alt="Pause" src="/img/pause.png"></button></td>`;
        } else if (lastPlayedMusicId === songCompilation.id + '_' + i && playerElement.paused && playerElement.currentTime > 0) {
            musicTdButton = `<td><button class="media-button" data-playing_state="on_pause" data-music_id="${songCompilation.id}_${i}" onclick="playOrPause(${songCompilation.id}, ${i})"><img class="media-img" alt="Resume" src="/img/resume.png"></button></td>`;
        }
        musicTd += musicTdButton;
        musicTr.html(musicTd);
        $(`#${modalId}`).append(musicTr);
    }
}

//load all music from current playlist to modal-table
function fillModalCurrentPlaylistTable(songCompilation, songCompilationSongs) {
    fillModalTableWithPlaylist('modalCurrentPlaylistTableBody', songCompilation, songCompilationSongs);
}

// #####################################################################################################
// #####################################################################################################
// #####################################################################################################

// PLAYER

let player;
let playerElement = document.getElementById("player");
let musicUrl = "/api/music/play/";
let shuffle = false;
let lastPlayedMusicIndex = -1;
let lastPlayedPlaylistId = -1;
let lastPlayedPlaylist;
let lastPlayedSongList;
let newPlaylist;
let newSongList;
let allSongCompilationsInNewGenre;
let allSongCompilationsInCurrentGenre;

let isNewCompilationsList = false;

$(function () {
    player = $('#player');

    //play previous music in current playlist
    $("#previousAudioButton").on("click", function () {
        playPrevious();
    });
    //play or pause current music
    $("#playOrPauseAudioButton").on("click", function () {
        playOrPause(lastPlayedPlaylistId, lastPlayedMusicIndex);
    });
    //play next music in current playlist
    $("#nextAudioButton").on("click", function () {
        playNext();
    });
    //get list of music from current playlist
    $("#currentPlaylistButton").on("click", function () {
        fillModalCurrentPlaylistTable(lastPlayedPlaylist, lastPlayedSongList);
    });
    //stop music
    $("#stopAudioButton").on("click", function () {
        let lastPlayedMusicsButtons = $(`button[data-music_id='${lastPlayedPlaylistId}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }
        lastPlayedMusicIndex = 0;
        let music = lastPlayedSongList[0];
        player.prop('src', musicUrl + music.name);
        $('#globalPlayButton').prop('src', '/img/play.png');
    });
    //shuffle music
    $("#shuffleAudioButton").on("click", function () {
        if (shuffle) {
            $("#shuffleImg").prop('src', '/img/shuffleOff.png');
            shuffle = false;
        } else {
            $("#shuffleImg").prop('src', '/img/shuffleOn.png');
            shuffle = true;
        }
    });
    //on playing
    playerElement.addEventListener("play", function () {
        let currentPlayingButtons = $(`button[data-music_id='${lastPlayedPlaylistId}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < currentPlayingButtons.length; i++) {
            setButtonOnPlay(currentPlayingButtons[i]);
        }
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedPlaylistId}']`);
        for (let i = 0; i < lastPlayedPlaylistsPlayButtons.length; i++) {
            setButtonOnPlay(lastPlayedPlaylistsPlayButtons[i]);
        }
        $('#globalPlayButton').prop('src', '/img/pause.png');
    });
    //on pause
    playerElement.addEventListener("pause", function () {
        let currentPlayingButtons = $(`button[data-music_id='${lastPlayedPlaylistId}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < currentPlayingButtons.length; i++) {
            setButtonOnPause(currentPlayingButtons[i]);
        }
        let lastPlayedPlaylistsPlayButtons = $(`button[data-playlist_id='${lastPlayedPlaylistId}']`);
        for (let i = 0; i < lastPlayedPlaylistsPlayButtons.length; i++) {
            setButtonOnPause(lastPlayedPlaylistsPlayButtons[i]);
        }
        $('#globalPlayButton').prop('src', '/img/resume.png');
    });
    //on end
    playerElement.addEventListener("ended", function () {
        playNext();
    });
    //on changing volume
    playerElement.addEventListener("volumechange", function () {
        if (playerElement.volume === 0 || playerElement.muted) {
            $('#soundImg').prop('src', '/img/soundOff.png');
        } else {
            $('#soundImg').prop('src', '/img/soundOn.png');
        }
    });
    //mute/unmute
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


function setButtonOnPlay(button) {
    button.dataset.playing_state = 'on_play';
    button.childNodes[0].setAttribute('src', '/img/pause.png');
}

function setButtonOnPause(button) {
    button.dataset.playing_state = 'on_pause';
    button.childNodes[0].setAttribute('src', '/img/resume.png');
}

function setButtonOnStop(button) {
    button.dataset.playing_state = 'on_stop';
    button.childNodes[0].setAttribute('src', '/img/play.png');
}

function playOrPause(playlistId, musicIndex) {
    let clickedButtons = $(`button[data-music_id='${playlistId}_${musicIndex}']`);
    let clickedButton = clickedButtons[0];
    let playingState = clickedButton.dataset.playing_state;
    if (playingState === 'on_stop') {
        let lastPlayedMusicsButtons = $(`button[data-music_id='${lastPlayedPlaylistId}_${lastPlayedMusicIndex}']`);
        for (let i = 0; i < lastPlayedMusicsButtons.length; i++) {
            setButtonOnStop(lastPlayedMusicsButtons[i]);
        }
        if (playlistId !== lastPlayedPlaylistId) {
            if (isNewCompilationsList) {
                allSongCompilationsInCurrentGenre = allSongCompilationsInNewGenre;
                isNewCompilationsList = false;
            }
            lastPlayedPlaylist = newPlaylist;
            lastPlayedSongList = newSongList;
            let lastPlayedPlaylistsButtons = $(`button[data-playlist_id='${lastPlayedPlaylistId}']`);
            if (lastPlayedPlaylistsButtons[0] !== undefined) {
                for (let i = 0; i < lastPlayedPlaylistsButtons.length; i++) {
                    setButtonOnStop(lastPlayedPlaylistsButtons[i]);
                }
            }
        }
        lastPlayedPlaylistId = newPlaylist.id;
        lastPlayedMusicIndex = musicIndex;
        let music = lastPlayedSongList[musicIndex];
        player.attr('src', musicUrl + music.name);
        playerElement.play();
    } else if (playingState === 'on_play') {
        playerElement.pause();
    } else if (playingState === 'on_pause') {
        playerElement.play();
    }
}

function playOrPausePlaylist(playlistId) {
    if (lastPlayedPlaylistId === playlistId) {
        playOrPause(playlistId, lastPlayedMusicIndex);
    } else {
        $.get('/api/user/song-compilation/get/song-compilation/' + playlistId, function (songCompilation) {
            $.get('/api/user/song/get/all-song/song-compilation/' + playlistId, function (compilationSongs) {
                fillModalTableWithPlaylist('modalPlaylistTableBody', songCompilation, compilationSongs);
                $('#modalPlaylistName').text(songCompilation.name);
                playOrPause(playlistId, 0);
            });
        });
    }
}

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
            playOrPause(lastPlayedPlaylist, lastPlayedMusicIndex + 1);
        } else {
            if (shuffle) {
                let playlistsLength = lastPlayedSongList.length;
                let nextMusicIndex;
                do {
                    nextMusicIndex = Math.floor(Math.random() * playlistsLength);
                } while (nextMusicIndex === lastPlayedMusicIndex);
                playOrPause(lastPlayedPlaylistId, nextMusicIndex);
                return;
            }
            if (lastPlayedMusicIndex < lastPlayedSongList.length - 1) {
                playOrPause(lastPlayedPlaylistId, lastPlayedMusicIndex + 1);
            } else {
                let countCounter = false;
                let countOfPlaylistsAfter = 0;
                let currentPlaylistsIndex = 0;
                for (let i = 0; i < allSongCompilationsInCurrentGenre.length; i++) {
                    if (countCounter) {
                        countOfPlaylistsAfter++;
                    }
                    if (lastPlayedPlaylistId === allSongCompilationsInCurrentGenre[i].id) {
                        currentPlaylistsIndex = i;
                        countCounter = true;
                    }
                }
                if (countOfPlaylistsAfter > 0) {
                    let index = allSongCompilationsInCurrentGenre.length - countOfPlaylistsAfter;
                    playOrPausePlaylist(allSongCompilationsInCurrentGenre[index]);
                } else {
                    playOrPausePlaylist(allSongCompilationsInCurrentGenre[0].id);
                }
            }
        }
    });
}

//play previous music in list. if this is the last music in current playlist, than play next playlist
function playPrevious() {
    if (playerElement.currentTime > 5) {
        playerElement.currentTime = 0;
        return;
    }
    if (lastPlayedMusicIndex > 0) {
        playOrPause(lastPlayedPlaylistId, lastPlayedMusicIndex - 1);
    } else {
        playOrPause(lastPlayedPlaylistId, lastPlayedSongList.length - 1);
    }
}