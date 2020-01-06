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
                    htmlCompilation = ('<div><a href="#" style="margin-right: 10px" id="linkBack">' +
                        '<img src="/img/back.svg" width="30" height="30" alt="Назад" ></a>' +
                        '<h3 style="display:inline">Подборки песен</h3></div>');

                    for (let i = 0; i < listSongCompilation.length; i++) {
                        htmlCompilation += ('<div id="songCompilation" class="col-3 pt-3">');
                        htmlCompilation += ('<a href="#" class="pt-5 col-fhd-2 col-xl-sm col-lg-4 col-md-6 col-sm-4 col-sm mt-5">');
                        htmlCompilation += ('<img src="/img/' + listSongCompilation[i].id + '.svg" width="50" height="50" alt="' +
                            listSongCompilation[i].name + '" >');
                        htmlCompilation += ('</img><p>' + listSongCompilation[i].name + '</p></a></div>');
                    }
                }
                $("#getGenres #genres").remove();
                $("#getGenres").append(htmlCompilation);
            }

        });

    });

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
            url: '/api/user/compilation/all_genre',
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
});

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