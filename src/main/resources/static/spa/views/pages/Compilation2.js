import Utils from './../../services/Utils.js'
import BottomBar from "../components/BottomBar2.js";

import {morningPlaylist} from "../components/BottomBar2.js";
import {middayPlaylist} from "../components/BottomBar2.js";
import {eveningPlaylist} from "../components/BottomBar2.js";
import {getCurrentPlaylist} from "../components/BottomBar2.js";
import {fillAllSongsPlaylist} from "../components/BottomBar2.js";
// import {allCompilationInGenre} from "../components/BottomBar2.js";
// import {allSongInGenre} from "../components/BottomBar2.js";
// import {lastPlayedCompilationIndex} from "../components/BottomBar2.js";
// import {lastPlayedPlaylistName} from "../components/BottomBar2.js";
import {obj} from "../components/BottomBar2.js";
import {test1} from "../components/BottomBar2.js";
import {testObj} from "../components/BottomBar2.js";
export {testObj}

//111
console.log("test1 in Conpilation2: " + test1)
// test1 = 222; //not work - const
//222
console.log("test1 in Conpilation2: : " + test1)

//333
console.log("test2 in Conpilation2: : " + window.test2) //undefined without error
window.test2 = 444
//444
console.log("test2 in Conpilation2: : " + window.test2)

//test3 is not defined
//555
// console.log("test3 in Conpilation2: : " + test3) //test3 is not defined
// window.test3 = 666
//666
// console.log("test3 in Conpilation2: : " + test3)

console.log("testObj in Conpilation2: " + testObj.data)
testObj.change(1)
console.log("testObj in Conpilation2: " + testObj.data)


// $(document).ready(function () {

    // getAllGenre();
    // showLinkAdmin();

    // //получение и вывод подборок
    // $(document).on('click', '#genres', function () {
    //     var genre = $(this).text();
    //     $.ajax({
    //         type: 'post',
    //         url: '/api/user/song-compilation/get/all-song-compilation',
    //         contentType: 'application/json;',
    //         data: JSON.stringify(genre),
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/json'
    //         },
    //         async: true,
    //         cache: false,
    //         dataType: 'JSON',
    //         success: function (listSongCompilation) {
    //             allCompilationInGenre = listSongCompilation;
    //             fillAllSongsPlaylist(allCompilationInGenre, allSongInGenre);
    //             let listCompilation = getCurrentPlaylist('getGenres').currentCumpilationsList
    //             var htmlCompilation = "Need to add Compilation";
    //             if (0 < listCompilation.length) {
    //                 htmlCompilation = ('<div id="songCompilation"><a href="#" style="margin-right: 10px" id="linkBack">' +
    //                     '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
    //                     '<h3 style="display:inline">Подборки песен</h3></div>');
    //                 for (var i = 0; i < listCompilation.length; i++) {
    //                     let morningIds = [];
    //                     let middayIds = [];
    //                     let eveningIds = [];
    //
    //                     let mon = $("#btnAddMorningPlaylist1-" + listCompilation[i].id).attr("class");
    //                     $.ajax({
    //                         method: "GET",
    //                         url: "/api/user/play-list/morning-playlist/get/all-song-compilation",
    //                         contentType: "application/json",
    //                         async: false,
    //                         success: function (morningCompilation) {
    //                             if (morningCompilation != null) {
    //                                 for (let k = 0; k < morningCompilation.length; k++) {
    //                                     morningIds.push(morningCompilation[k].id)
    //                                 }
    //                             }
    //                         }
    //                     });
    //                     mon = morningIds.includes(listSongCompilation[i].id) ? "btn btn-success" : "btn btn-info";
    //                     $.ajax({
    //                         method: "GET",
    //                         url: "/api/user/play-list/midday-playlist/get/all-song-compilation",
    //                         contentType: "application/json",
    //                         async: false,
    //                         success: function (middayCompilation) {
    //                             if (middayCompilation != null) {
    //                                 for (let k = 0; k < middayCompilation.length; k++) {
    //                                     middayIds.push(middayCompilation[k].id);
    //                                 }
    //                             }
    //                         }
    //                     });
    //
    //                     let mid = $("#btnMiddayPlaylist1-" + listCompilation[i].id).attr("class");
    //                     mid = middayIds.includes(listCompilation[i].id) ? "btn btn-success" : "btn btn-info";
    //
    //                     $.ajax({
    //                         method: "GET",
    //                         url: "/api/user/play-list/evening-playlist/get/all-song-compilation",
    //                         contentType: "application/json",
    //                         async: false,
    //                         success: function (eveningCompilation) {
    //                             if (eveningCompilation != null) {
    //                                 for (let k = 0; k < eveningCompilation.length; k++) {
    //                                     eveningIds.push(eveningCompilation[k].id);
    //                                 }
    //                             }
    //                         }
    //                     });
    //
    //                     let eve = $("#btnEveningPlaylist1-" + listCompilation[i].id).attr("class");
    //                     eve = eveningIds.includes(listCompilation[i].id) ? "btn btn-success" : "btn btn-info";
    //
    //
    //                     htmlCompilation += '<div id="songCompilation" class="card-deck">'
    //                         + '<div class="card pt-10">'
    //                         + '<a href="#" onclick="showAllSongInSongCompilation(\'getGenres\', ' + listCompilation[i].id + ')" data-toggle="modal"' +
    //                         ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
    //                         + '<img src="/img/compilation/compilation' + listCompilation[i].id + '.svg" width="80" height="80" alt="' +
    //                         listCompilation[i].name + '" >'
    //                         + '<p>Песни подборки</p></a>'
    //                         + '<div class="card-body">'
    //                         + '<h4 class="card-title"> ' + listCompilation[i].name + '</h4>'
    //                         + '<p class="card-text">Discription: Some text</p>'
    //                         + '</div>'
    //                         + '<div class="card-footer">'
    //                         + '<p class="card-text"><small class="text-muted">Footer: Some text</small></p>';
    //                     let playing_state = 'on_stop';
    //                     let display_play = 'inline-block';
    //                     let display_pause = 'none';
    //                     if (listCompilation[i].compilationIndex === lastPlayedCompilationIndex && 'getGenres' === lastPlayedPlaylistName) {
    //                         playing_state = 'on_play';
    //                         display_play = 'none';
    //                         display_pause = 'inline-block';
    //                     }
    //                     let playButton = `<button class="playBtn" data-playlist_id="getGenres_${listCompilation[i].compilationIndex}" onclick="playOrPausePlaylist(\'getGenres\', ${listCompilation[i].compilationIndex})"></button>`;
    //                     let pauseButton = `<button class="pauseBtn" style="display: ${display_pause}" data-playing_state="${playing_state}" data-playlist_id="getGenres_${listCompilation[i].compilationIndex}" onclick="playOrPausePlaylist(\'getGenres\', ${listCompilation[i].compilationIndex})"></button>`;
    //                     htmlCompilation += playButton;
    //                     htmlCompilation += pauseButton;
    //                     htmlCompilation += '&nbsp;' + '&nbsp;'
    //                         + '<button class="' + mon + '" id="btnAddMorningPlaylist1-' + listCompilation[i].id + '" onclick="addMorningPlaylist(' + listCompilation[i].id + ')">Утро</button>'
    //                         + '&nbsp;'
    //                         + '<button class="' + mid + '" id="btnMiddayPlaylist1-' + listCompilation[i].id + '" onclick="addMiddayPlaylist(' + listCompilation[i].id + ')">День</button>'
    //                         + '&nbsp;'
    //                         + '<button class="' + eve + '" id="btnEveningPlaylist1-' + listCompilation[i].id + '" onclick="addEveningPlaylist(' + listCompilation[i].id + ')">Вечер</button>'
    //                         + '</div>'
    //                         + '</div>'
    //                         + '</div>';
    //                 }
    //             }
    //             $("#getGenres #genres").remove();
    //             $("#getGenres").append(htmlCompilation);
    //         }
    //     });
    // });
    //
    // //назад к жанрам
    // $(document).on('click', '#linkBack', function () {
    //     $("#getGenres #songCompilation").remove();
    //     getAllGenre();
    // });


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
                    htmlGenres += ('<a href="javascript:void(0)" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                    htmlGenres += ('<img src="/img/all.svg" width="50" height="50" alt="Все подборки" >');
                    htmlGenres += ('</img><p>' + "Все подборки" + '</p></a></div>');
                    for (var i = 0; i < listGenre.length; i++) {
                        htmlGenres += ('<div id="genres" class="col-3 pt-3">');
                        htmlGenres += ('<a href="javascript:void(0)" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
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

    // //получаем подборки из утреннего плейлиста
    // $(document).on('click', '#morning-music-nav', function () {
    //     morningPlaylist();
    // });
    //
    // //получаем подборки из дневного плейлиста
    // $(document).on('click', '#midday-music-nav', function () {
    //     middayPlaylist();
    // });
    //
    // //получаем подборки из вечернего плейлиста
    // $(document).on('click', '#evening-music-nav', function () {
    //     eveningPlaylist();
    // });
// });

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
export function getAllCompilationsInMorningPlaylist() {
    fillPlaylistsTab('morning', 'morningCompilations', getCurrentPlaylist('morning').currentCumpilationsList);
}

//получаем все подборки в дневном плейлисте
export function getAllCompilationsInMiddayPlaylist() {
    fillPlaylistsTab('midday', 'middayCompilations', getCurrentPlaylist('midday').currentCumpilationsList);
}

//получаем все подборки в вечернем плейлисте, проиндексированные порядковым номером в вечернем плейлисте плеера
export function getAllCompilationsInEveningPlaylist() {
    fillPlaylistsTab('evening', 'eveningCompilations', getCurrentPlaylist('evening').currentCumpilationsList);
}

//отрисовка всех подборок в плейлисте
function fillPlaylistsTab(playListName, secondId, playlist) {
    $(`#${playListName}`).empty();
    var htmlCompilation = '';
    //bootstrap card
    htmlCompilation += (`<div class="card-deck" id="${secondId}">`);
    for (var i = 0; i < playlist.length; i++) {
        htmlCompilation += '<div class="card pt-10">'
            + '<a href="#" id="' + playlist[i].id + '" onclick="showAllSongInSongCompilation(\'' + playListName + '\', ' + playlist[i].id + ')" data-toggle="modal"'
            + ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
            + '<img src="/img/compilation/compilation' + playlist[i].id + '.svg" width="80" height="80" class="card-img-top" alt="' + playlist[i].name + '">'
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



let Compilation2 = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '\n' +
            '                <H3> Мой плейлист</H3>\n' +
            '                <div class="col-fhd-4 col-xl-5 col-lg-6">\n' +
            '                    <ul class="nav nav-tabs nav-content " role="tablist">\n' +
            '                        <li id="morning-music-nav" class="active">\n' +
            '                            <a href="#morning" aria-controls="morning-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>Утро</h5>8:00 - 12:00</a>\n' +
            '                        </li>\n' +
            '                        <li id="midday-music-nav">\n' +
            '                            <a href="#midday" aria-controls="midday-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>День</h5>12:00 - 18:00</a>\n' +
            '                        </li>\n' +
            '                        <li id="evening-music-nav">\n' +
            '                            <a href="#evening" aria-controls="evening-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>Вечер</h5>18:00 - 8:00</a>\n' +
            '                        </li>\n' +
            '                    </ul>\n' +
            '\n' +
            '                    <div class="tab-content">\n' +
            '                        <div role="tabpanel" class="tab-pane active" id="morning">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки утреннего плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div role="tabpanel" class="tab-pane" id="midday">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки дневного плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div role="tabpanel" class="tab-pane" id="evening">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки вечернего плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <!--подборка по жанрам-->\n' +
            '                <div id="getGenres" class="col-fhd-8 col-xl-7 col-lg-6 row"></div>\n' +
            '            </div>\n' +
            '        </div>' +
            '<div class="modal fade" id="currentPlaylist">\n' +
            '        <div class="modal-dialog">\n' +
            '            <div class="modal-content">\n' +
            '                <div class="modal-header text-center">\n' +
            '                    <button class="close" data-dismiss="modal">&times;</button>\n' +
            '                    <h4>Current playlist</h4>\n' +
            '                </div>\n' +
            '                <div class="modal-body">\n' +
            '                    <table class="table table-condensed table-hover table-striped table-responsive">\n' +
            '                        <thead>\n' +
            '                        <tr>\n' +
            '                            <th>Musics title</th>\n' +
            '                            <th>Author</th>\n' +
            '                            <th>Genre</th>\n' +
            '                            <th style="width: 20%">Options</th>\n' +
            '                        </tr>\n' +
            '                        </thead>\n' +
            '                        <tbody id="modalCurrentPlaylistTableBody"></tbody>\n' +
            '                    </table>\n' +
            '                </div>\n' +
            '                <div class="modal-footer">\n' +
            '                    <button class="btn" data-dismiss="modal">Close</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="modal fade" id="modalPlaylist">\n' +
            '        <div class="modal-dialog">\n' +
            '            <div class="modal-content">\n' +
            '                <div class="modal-header">\n' +
            '                    <button class="close" data-dismiss="modal">&times;</button>\n' +
            '                    <h4 id="modalPlaylistName"></h4>\n' +
            '                </div>\n' +
            '                <div class="modal-body">\n' +
            '                    <table class="table table-condensed table-hover table-striped table-responsive">\n' +
            '                        <thead>\n' +
            '                        <tr>\n' +
            '                            <th>Musics title</th>\n' +
            '                            <th>Author</th>\n' +
            '                            <th>Genre</th>\n' +
            '                            <th style="width: 20%">Options</th>\n' +
            '                        </tr>\n' +
            '                        </thead>\n' +
            '                        <tbody id="modalPlaylistTableBody"></tbody>\n' +
            '                    </table>\n' +
            '                </div>\n' +
            '                <div class="modal-footer">\n' +
            '                    <button class="btn" data-dismiss="modal">Close</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
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
                    // allCompilationInGenre = listSongCompilation;
                    // allCompilationInGenre.change(listSongCompilation);
                    // window.storage.allCompilationInGenre = listSongCompilation;
                    obj.allCompilationInGenre = listSongCompilation;
                    fillAllSongsPlaylist(obj.allCompilationInGenre, obj.allSongInGenre); //остановился здесь
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


                            htmlCompilation += '<div id="songCompilation" class="card-deck">'
                                + '<div class="card pt-10">'
                                + '<a href="#" onclick="showAllSongInSongCompilation(\'getGenres\', ' + listCompilation[i].id + ')" data-toggle="modal"' +
                                ' data-target="#modalPlaylist" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">'
                                + '<img src="/img/compilation/compilation' + listCompilation[i].id + '.svg" width="80" height="80" alt="' +
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




    }
}
export default Compilation2;