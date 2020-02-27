import Compilation from "../pages/Compilation.js";
import {getAllCompilationsInMorningPlaylist} from "../pages/Compilation2.js";
import {getAllCompilationsInMiddayPlaylist} from "../pages/Compilation2.js";
import {getAllCompilationsInEveningPlaylist} from "../pages/Compilation2.js";


/**
 * ##########################
 *          PLAYER
 * ##########################
 */
// объект плеер, который при загрузке страницы сразу находится с jquery
let player;

// объект плеер, который находится javascript-ом
let playerElement = document.getElementById("player");
if (playerElement){
    playerElement.volume = 0.3;
    $('#volumebar').attr('value', playerElement.volume);
}

//=================проверяю изменяются ли переменные из этого жс в Compilation2================
//нужно заюзать local storage

//not work - const
export let test1 = 111
if (test1 === 222) {
    console.log("test1 in BottomBar2: : " + test1)
}

//undefined
let test2 = 333;
if (test2 === 444) {
    console.log("test2 in BottomBar2: " + test2)
}

//test3 is not defined
var test3 = 555
if (test3 === 666) {
    console.log("test3 in BottomBar2: " + test3)
}

export var test4 = 10;
if (test4 === 11) {
    console.log("test4 in BottomBar2: " + test4)
}

export let testObj = {
    data: [],
    change: function(prop) {
        this.data = prop;
    },
};
console.log("testObj in BottomBar2: " + testObj.data)
if (testObj.data === 1) {
    console.log("testObj in BottomBar2: " + testObj.data)
}

let test;
console.log("let test in BottomBar2: " + test);
localStorage.setItem('test', '1');
console.log("localStorage test in BottomBar2 after init: " + localStorage.getItem('test'));
if (localStorage.getItem('test') === '2') { //not work
    console.log("localStorage test in BottomBar2 after Compilation2: " + localStorage.getItem('test'))
}



//=================проверяю изменяются ли переменные из этого жс в Compilation2================
//Ответ: НЕЕЕЕЕЕЕТТТТТТ!!!!!!!!!!!!!!!!!!!!



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



// window.storage = {};
// export let allCompilationInGenre = {
//     data: [],
//     change: function(prop) {
//         this.data = prop;
//     },
// };

// window.storage.allCompilationInGenre;
let allCompilationInGenre;

//утренний, дневной, вечерний плейлисты как список песен
let allSongsInMorningPlaylist = [];
let allSongsInMiddayPlaylist = [];
let allSongsInEveningPlaylist = [];
// export let allSongInGenre = {
//     data: [],
//     change: function(prop) {
//         this.data = prop;
//     },
// };
// window.storage.allSongInGenre;
let allSongInGenre;

//текущий плейлист как список compilation
let allCompilationInCurrentPlaylist;

//текущий плейлист как список песен
let allSongsInCurrentPlaylist;

// индекс последне-проигранной песни в своем массиве
let lastPlayedMusicIndex = -1;

// индекс последне-проигранного массива песен в своем списке
export let lastPlayedCompilationIndex = -1;

// переменная, которая хранит id кнопки последне-проигранного списка
let lastPlayedPlaylistId = 'none';

// имя текущего раздела
export let lastPlayedPlaylistName = 'none';

export let obj = {allCompilationInGenre, allSongInGenre}
/**
 * Вспомогательная функция для получения утреннего, дневного или вечернего плейлистов.
 * Заполняет список всех песен данного плейлиста, помечает песни доп.информацией:
 *  compilationId - id подборки, в которой находится песня
 *  compilationIndex - индекс подборки в данном плейлисте
 *  musicIndex - индекс песни в данном плейлисте.
 * allCompilationsInPlaylist - список подборок соответствующего плейлиста,
 * allSongsInPlaylist - список песен соответствующего плейлиста, список передается пустым.
 */
export function fillAllSongsPlaylist(allCompilationsInPlaylist, allSongsInPlaylist) {
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
export function morningPlaylist() {
    $.get('/api/user/play-list/morning-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMorningPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMorningPlaylist, allSongsInMorningPlaylist)
        getAllCompilationsInMorningPlaylist();
    });
}

export function middayPlaylist() {
    $.get('/api/user/play-list/midday-playlist/get/all-song-compilation', function (playList) {
        allCompilationsInMiddayPlaylist = playList;
        fillAllSongsPlaylist(allCompilationsInMiddayPlaylist, allSongsInMiddayPlaylist)
        getAllCompilationsInMiddayPlaylist();
    });
}

export function eveningPlaylist() {
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
    if (playerElement) {
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
    }

    // при паузе плеера. ищутся кнопки песен, который играли и меняется изображение на "продолжить"
    if (playerElement) {
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
    }

    // при окончании проигрывания текущей песни
    if (playerElement) {
        playerElement.addEventListener("ended", function () {
            playNext();
        });
    }

    // при изменении громкости плеера
    if (playerElement) {
        playerElement.addEventListener("volumechange", function () {
            $('#volumebar').attr('value', playerElement.volume);
            if (playerElement.volume === 0 || playerElement.muted) {
                $('#soundImg').prop('src', '/img/soundOff.png');
            } else {
                $('#soundImg').prop('src', '/img/soundOn.png');
            }
        });
    }



    //при изменении currentTime
    if (playerElement) {
        playerElement.addEventListener('timeupdate', function () {
            $('#seekbar').attr('value', playerElement.currentTime / playerElement.duration);
            var min = Math.floor(playerElement.currentTime / 60);
            var sec = Math.floor(playerElement.currentTime % 60);
            var s = sec < 10 ? `0${sec}` : sec;
            document.getElementById('duration').innerHTML = min + ':' + s;
        });
    }

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

    if (seekBar) {
        seekBar.addEventListener('click', function (event) {
            let widthLeft = $('#seekbar').offset().left;
            let x = event.pageX - widthLeft;
            let xPersent = x / this.offsetWidth;
            playerElement.currentTime = playerElement.duration * xPersent;
        });
    }


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
if (soundButton) { //добавил
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
}


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


export function getCurrentPlaylist(playlistName) {
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


let BottomBar2 = {
    render: async () => {
        let view =  /*html*/
            `
            <footer class="footer">
                <div class="content has-text-centered">
                    <audio controls="controls" id="player" type='audio/mpeg; codecs="MP3"' src=""
                           style="min-width: 500px" hidden="hidden">
                        Your browser doesnt support the audio element/ Please, update your browser or download
                        Chrome-browser
                    </audio>
                    <div class="row" id="playerContainer">
                        <div class="col-md-3" style="padding: 37px;">
                            <button class="media-button" id="previousAudioButton">
                                <img alt="Previous" class="media-img" src="/img/previous.png"/>
                            </button>
                            <button class="media-button" id="playOrPauseAudioButton">
                                <img alt="Play/Pause" id="globalPlayButton" src="/img/play.png"/>
                            </button>
                            <button class="media-button" id="nextAudioButton">
                                <img alt="Next" class="media-img" src="/img/next.png"/>
                            </button>
                            <button class="media-button" id="currentPlaylistButton" data-toggle="modal" data-target="#currentPlaylist">
                                <img alt="Current playlist" class="media-img" src="/img/playlist.png"/>
                            </button>
                        </div>
                        <div class="col-md-6" style="padding: 5px">
                            <div class="card mb-7"
                                 style="bottom:auto; right: 3%; display: inline-block; min-width: 300px; height: 110px">
                                <div class="row no-gutters">
                                    <div class="col-md-4" style="width: 100px">
                                        <img id="albums-cover" style=" height: 100px;width: 100px;"
                                             src="/img/defaultAlbumsCover.jpg"/>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="card-body" style="height: 110px">
                                            <h5 id="song-name" class="card-title" style="margin-left: 10px"></h5>
                                            <p id="song-author" class="card-text" style="margin-left: 10px"></p>
                                            <progress id="seekbar" value="0" max="1"></progress>
                                        </div>
                                    </div>
                                    <div class="col-md-1">
                                        <h5 class="card-title" id="duration" style="margin-left: -5px"></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3" style="padding: 37px;">
                            <progress id="volumebar" value="0" max="1"></progress>
                            <button class="media-button" id="soundButton" data-sound_state="on">
                                <img alt="Sound" class="media-img" id="soundImg" src="/img/soundOn.png"/>
                            </button>
                            <button class="media-button" id="shuffleAudioButton">
                                <img alt="Shuffle" id="shuffleImg" class="media-img" src="/img/shuffleOff.png"/>
                            </button>
                        </div>
                    </div>
                </div>
            </footer>
            `

        return view
    },
    after_render: async () => {
    }
}

export default BottomBar2;