
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

            var tbl = "<table class=\"table table-responsive{max-width 768px} table-borderless\">";

            for (var i = 0; i < allGenre.length; i++) {
                var genreId = allGenre[i].id;
                tbl +=
                    `
                    <tr>
                        <td>
                            ${allGenre[i].name}
                        </td>
                        
                        <!-- кнопка по добавлению в фильтр жанра -->
                        <td>
                            <button
                                type="button"
                                class="btn btn-outline-danger"
                                onclick="addGenreToFilter(${genreId})"
                            >
                                Заблокировать
                            </button>
                        </td>
                    </tr>
                    `;
            }
            tbl += "</table>";
            $("#forGenreTable").html(tbl);
        }
    });

    // функция для добавления жанра в список запрещеных вызываеться при клике на кнопку
    function addGenreToFilter(id) {
        $.ajax({
            method: 'get',
            url: '/filterForGenre/' + id, //TODO сделать методы
            dataType: 'json',
            success: function () {
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
            }
        });
    }

    // код для поиска и вывода авторов
    var $authorForms = $(".authorSearch");
    $authorForms.on("submit", function(searchAuthor){

        searchAuthor.preventDefault();
        let name = $(this).find(".nameForAuthor").val();

        $.ajax({
            type: 'get',
            url: '/api/user/allAuthors',
            contentType: 'application/json;',
            data: JSON.stringify(name),

            success: function (allAuthors) {
                var tbl = "<table class=\"table table-responsive{max-width 768px} table-borderless\">";

                for (var i = 0; i < allAuthors.length; ++i) {
                    var authorId = allAuthors[i].id;
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
                                    class="btn btn-outline-danger"
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
    function addAuthorToFilter(id) {
        $.ajax({
            method: 'get',
            url: '/filterForAuthor/' + id, //TODO сделать методы
            dataType: 'json',
            success: function () {
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
            }
        });
    }

    // код для поиска и вывода песен
    var $musicForm = $(".musicSearch");
    $musicForm.on("submit", function(searchMusics){

        searchMusics.preventDefault();

        $.ajax({
            type: 'get',
            url: '/api/user/allMusic',
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',

            success: function (allMusics) {
                var tbl = "<table class=\"table table-responsive{max-width 768px} table-borderless\">";

                for (var i = 0; i < allMusics.length; ++i) {
                    var musicId = allMusics[i].id;
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
                                    class="btn btn-outline-danger"
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
    function addMusicToFilter(id) {
        $.ajax({
            method: 'get',
            url: '/filterForMusic/' + id, //TODO сделать методы
            dataType: 'json',
            success: function () {
                //TODO после добавления жанра в бан кнопка напротив этого жанра должна быть помечена
            }
        });
    }