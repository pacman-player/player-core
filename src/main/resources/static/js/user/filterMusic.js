
    // получение всех жанров и заполнение таблицы в пункте "По жанрам"
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

        success: function (allGenre) {
            $("#forGenreTable").html(`<table class="table table-borderless">
            ${allGenre.map(renderGenre).join('')}
            </table>`);
        }
    });

    function renderGenre(genre) {
        return `
                    <tr>
                        <td>
                            ${genre.name}
                        </td>
                        
                        <!-- кнопка по добавлению в фильтр жанра -->
                        <td>
                            <button
                                type="button"
                                class="btn btn-warning"
                                onclick="addGenreToFilter(${genre.id})"
                            >
                                Заблокировать
                            </button>
                        </td>
                    </tr>
                    `;
    }
    // функция для добавления жанра в список запрещеных вызываеться при клике на кнопку
    function addGenreToFilter(genreId) {
        $.ajax({
            method: 'post',
            url: '/api/user/genreBan',
            contentType : "application/json",
            data:JSON.stringify(genreId),
            success: function () {
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
                console.log("ВЫПОЛНЕНИЕ");
            }
        });
    }

    // код для поиска и вывода авторов
    let $authorForms = $(".authorSearch");
    $authorForms.on("submit", function(searchAuthor){

        searchAuthor.preventDefault();
        const authorName = $(this).find(".nameForAuthor").val();

        $.ajax({
            type: 'get',
            url: '/api/user/allAuthorsByName/' + authorName,
            contentType: 'application/json;',

            success: function (allAuthors) {
                let tbl = `<table class="table table-borderless">`;

                for (let i = 0; i < allAuthors.length; ++i) {
                    const authorId = allAuthors[i].id;
                    tbl +=
                        `
                        <tr>
                            <td>
                                ${allAuthors[i].name}                        
                            </td> 
                            <!-- кнопка по добавлению в фильтр автора -->
                            <td>
                                <button
                                    type="button"
                                    id="buttonAuthor"
                                    class="btn btn-warning"
                                    onclick="addAuthorToFilter(${authorId})"
                                >
                                    Заблокировать
                                </button>
                            </td>
                        </tr>
                        `;
                }
                tbl += "</table>";
                $("#forAuthorTable").html(tbl);
            }
        });
    });

    // функция для добавления исполнителя в список запрещеных вызываеться при клике на кнопку
    function addAuthorToFilter(authorId) {
        $.ajax({
            method: 'post',
            url: '/api/user/authorsBan',
            contentType : "application/json",
            data:JSON.stringify(authorId),
            dataType: 'json',

            success: function () {
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
                console.log("ВЫПОЛНЕНИЕ");
                $('#buttonAuthor').toggleClass('click');
            }
        });
    }

    // код для поиска и вывода песен
    let $musicForm = $(".musicSearch");
    $musicForm.on("submit", function(searchMusics){

        searchMusics.preventDefault();
        const musicName = $(this).find(".nameForMusic").val();

        $.ajax({
            type: 'get',
            url: '/api/user/allSongsByName/' + musicName ,
            contentType: 'application/json;',

            success: function (allMusics) {
                let tbl = `<table class="table table-borderless">`;

                for (let i = 0; i < allMusics.length; ++i) {
                    const musicId = allMusics[i].id;
                    tbl +=
                        `
                        <tr>
                            <td>
                                ${allMusics[i].name}                        
                            </td> 
                            <!-- кнопка по добавлению в фильтр автора -->
                            <td>
                                <button
                                    type="button"
                                    class="btn btn-warning"
                                    onclick="addMusicToFilter(${musicId})"
                                >
                                    Заблокировать
                                </button>
                            </td>
                        
                        </tr>
                        `;
                }
                tbl += "</table>";
                $("#forMusicTable").html(tbl);
            }
        });
    });

    // функция для добавления песни(ен) в список запрещеных вызываеться при клике на кнопку
    function addMusicToFilter(musicId) {
        $.ajax({
            method: 'post',
            url: '/api/user/songsBan',
            contentType : "application/json",
            data:JSON.stringify(musicId),
            dataType: 'json',
            success: function () {
                console.log("ВЫПОЛНЕНИЕ");
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
            }
        });
    }