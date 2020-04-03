let Filter = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '                <H3>Страница Фильтров</H3>\n' +
            '                <!-- меню выбора выриантов для блокировки -->\n' +
            '                <div class="col-fhd-4 col-xl-5 col-lg-6">\n' +
            '                    <ul class="nav nav-tabs nav-content " role="tablist">\n' +
            '                        <li id="morning-music-nav" class="active">\n' +
            '                            <a href="#genre"\n' +
            '                               aria-controls="genre-music-nav"\n' +
            '                               role="tab"\n' +
            '                               data-toggle="tab"\n' +
            '                            >\n' +
            '                                По жанрам\n' +
            '                            </a>\n' +
            '                        </li>\n' +
            '                        <li id="day-music-nav">\n' +
            '                            <a href="#artists"\n' +
            '                               aria-controls="artists-music"\n' +
            '                               role="tab"\n' +
            '                               data-toggle="tab"\n' +
            '                            >\n' +
            '                                По артистам\n' +
            '                            </a>\n' +
            '                        </li>\n' +
            '                        <li id="evening-music-nav">\n' +
            '                            <a href="#songs"\n' +
            '                               aria-controls="evening-music"\n' +
            '                               role="tab"\n' +
            '                               data-toggle="tab"\n' +
            '                            >\n' +
            '                                По песням\n' +
            '                            </a>\n' +
            '                        </li>\n' +
            '                    </ul>\n' +
            '                    <!-- контент для меню "По жанрам" -->\n' +
            '                    <div class="tab-content active">\n' +
            '                        <div role="tabpanel" class="tab-pane active" id="genre">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <div id="forGenreTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <!-- контент для меню "По артистам" -->\n' +
            '                        <div role="tabpanel" class="tab-pane" id="artists">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            // '                                        <form class="form-inline authorSearch">\n' +
            // '                                            <input class="form-control mr-sm-2 nameForAuthor" type="search" placeholder="Search" aria-label="Search"/>\n' +
            // '                                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>\n' +
            // '                                        </form>\n' +
            '                                        <div id="forAuthorTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '                                        <nav aria-label="author_page_navigation">\n' +
            '                                           <ul class="pagination" id="forAuthorTablePagesNav">\n' +
            '                                               <!-- скрипт заполнения панели пагинации авторов в файле filterMusic.js -->\n' +
            '                                           </ul>\n' +
            '                                        </nav>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <!-- контент для меню "По песням" -->\n' +
            '                        <div role="tabpanel" class="tab-pane" id="songs">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            // '                                        <form class="form-inline musicSearch">\n' +
            // '                                            <input class="form-control mr-sm-2 nameForMusic" type="search" placeholder="Поиск" aria-label="Search"/>\n' +
            // '                                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Поиск</button>\n' +
            // '                                        </form>\n' +
            '                                        <div id="forMusicTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '                                        <nav aria-label="music_page_navigation">\n' +
            '                                           <ul class="pagination" id="forMusicTablePagesNav">\n' +
            '                                               <!-- скрипт заполнения панели пагинации песен в файле filterMusic.js -->\n' +
            '                                           </ul>\n' +
            '                                        </nav>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>'
    },
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен

    after_render: async () => {
        // получение всех жанров и заполнение таблицы в пункте "По жанрам"
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

            success: function (allGenre) {
                $("#forGenreTable").html(`<table class="table table-borderless">
            ${allGenre.map(renderGenre).join('')}
            </table>`);
            }
        });

        function renderGenre(genre) {
            let checkBanned = Boolean(genre.banned);
            return `
                <tr>
                    <td>
                        ${genre.name}
                    </td>
                    
                    <!-- кнопка по добавлению в фильтр жанра -->
                    <!-- если в артрибут передается название функции, то она будет вызвана с this = кнопки,
                     которую нажали -->
                    <!-- писать data перед собсвенными атрибутами - правило -->
                    <td>
                        <button
                            type="button"
                            class="addGenreToFilter btn ${checkBanned ? 'btn-success' : 'btn-warning'}"
                            data-banned="${checkBanned}"
                            data-genre-id="${genre.id}"
                        >
                            ${checkBanned ? 'Разблокировать' : 'Заблокировать'}
                        </button>
                    </td>
                </tr>
                `;
        }

// функция для добавления/удаления жанра в/из список(а) запрещеных
        $(document).off('click', '.addGenreToFilter');
        $(document).on('click', '.addGenreToFilter', function() {
            const $button = $(this);
            // ищем в кнопке атрибут и достаем его значение
            // преобразовываем их в тип соответсвующей функцией
            const genreId = Number($button.data('genre-id'));
            const banned = Boolean($button.data('banned'));

            $.ajax({
                method: 'post',
                url: banned ? '/api/user/genre/genreUnBan' : '/api/user/genre/genreBan',
                contentType: "application/json",
                data: JSON.stringify(genreId),
                success: function () {

                    // если переменная banned была true, то мы разблокировали объект и наоборот
                    if (banned) {

                        $button
                            .html('Заблокировать')
                            .removeClass('btn-success')
                            .addClass('btn-warning')
                            //устанавливаем атрибуту значение
                            .data('banned', false);
                    } else {
                        // html = поменять содержимое
                        $button
                            .html('Разблокировать')
                            .removeClass('btn-warning')
                            .addClass('btn-success')
                            //устанавливаем атрибуту значение
                            .data('banned', true);
                    }
                }
            });
        });

// // код для поиска и генерации таблицы авторов
//         let $authorForms = $(".authorSearch");
// // при клике на кнопку ".on" перехватывает событие и вызывает вместо него функцию
//         $authorForms.on("submit", function (searchAuthor) {
//
//             // блокирует стандартный ход исполнения события "submit"
//             searchAuthor.preventDefault();
//
//             const authorName = $(this).find(".nameForAuthor").val();
//
//             $.ajax({
//                 type: 'get',
//                 url: '/api/author/allAuthorsByName/' + authorName,
//                 contentType: 'application/json;',
//
//                 success: function (allAuthors) {
//                     $("#forAuthorTable").html(`<table class="table table-borderless">
//                 ${allAuthors.map(renderAuthors).join('')}
//                 </table>`);
//                 }
//             });
//         });

        //получение списка авторов и заполнение таблицы авторов
        $.ajax({
            type: 'get',
            url: '/api/author/approvedAuthorsPage',
            contentType: 'application/json',

            success: function (allAuthors) {
                $("#forAuthorTable").html(
                    `<table class="table table-borderless">
                        ${allAuthors.map(renderAuthors).join('')}
                    </table>`
                );
            }
        });

        $.ajax({
            type: 'get',
            url: '/api/author/lastApprovedAuthorsPageNumber',
            contentType: 'application/json',

            success: function (lastPageNumber) {
                for (let i = 1; i <= lastPageNumber; i++) {
                    $("#forAuthorTablePagesNav").append(
                        `<li class="page-item">
                            <a  id="author-page-number-link" class="page-link">${i}</a>
                        </li>`
                    );
                }

                $("nav #author-page-number-link").click(function () {
                    var pageNumber = $(this).text();
                    $.ajax({
                        type: 'get',
                        url: '/api/author/approvedAuthorsPage',
                        contentType: 'application/json',
                        data: 'pageNumber=' + pageNumber,

                        success: function (allAuthors) {
                            $("#forAuthorTable").html(
                                `<table class="table table-borderless">
                                    ${allAuthors.map(renderAuthors).join('')}
                                </table>`
                            );
                        }
                    });
                });

            }
        });

        function renderAuthors(author) {
            let checkBanned = Boolean(author.banned);

            return `
                 <tr>
                     <td>
                         ${author.name}                        
                     </td> 
                     <!-- кнопка по добавлению в фильтр автора -->
                     <td>
                         <button
                             type="button"
                             id="buttonAuthor"
                             class="addAuthorToFilter btn ${checkBanned ? 'btn-success' : 'btn-warning'}"
                             data-banned="${checkBanned}"
                             data-author-id="${author.id}"
                         >
                             ${checkBanned ? 'Разблокировать' : 'Заблокировать'}
                        </button>
                     </td>
                 </tr>
                `;
        }

// функция для добавления исполнителя в список запрещеных вызываеться при клике на кнопку
        $(document).off('click', '.addAuthorToFilter');
        $(document).on('click', '.addAuthorToFilter', function addAuthorToFilter() {
            const $button = $(this);
            // ищем в кнопке атрибут и достаем его значение
            // преобразовываем их в тип соответсвующей функцией
            const authorId = Number($button.data('author-id'));
            const banned = Boolean($button.data('banned'));

            $.ajax({
                method: 'post',
                url: banned ? '/api/author/authorsUnBan' : '/api/author/authorsBan',
                contentType: "application/json",
                data: JSON.stringify(authorId),

                success: function () {

                    if (banned) {

                        $button
                            .html('Заблокировать')
                            .removeClass('btn-success')
                            .addClass('btn-warning')
                            //устанавливаем атрибуту значение
                            .data('banned', false);
                    } else {
                        // html = поменять содержимое
                        $button
                            .html('Разблокировать')
                            .removeClass('btn-warning')
                            .addClass('btn-success')
                            //устанавливаем атрибуту значение
                            .data('banned', true);
                    }
                }
            });
        });

// // код для поиска и вывода песен
//         let $musicForm = $(".musicSearch");
//         $musicForm.on("submit", function (searchMusics) {
//
//             searchMusics.preventDefault();
//             const musicName = $(this).find(".nameForMusic").val();
//
//             $.ajax({
//                 type: 'get',
//                 url: '/api/music/allSongsByName/' + musicName,
//                 contentType: 'application/json;',
//
//                 success: function (allMusics) {
//                     $("#forMusicTable").html(`<table class="table table-borderless">
//                 ${allMusics.map(renderMusics).join('')}
//                 </table>`);
//                 }
//             });
//         });

        //получение списка песен и заполнение таблицы песен
        $.ajax({
            type: 'get',
            url: '/api/music/approvedSongsPage',
            contentType: 'application/json',

            success: function (allSongs) {
                $("#forMusicTable").html(
                    `<table class="table table-borderless">
                        ${allSongs.map(renderMusics).join('')}
                    </table>`
                );
            }
        });

        $.ajax({
            type: 'get',
            url: '/api/music/lastApprovedSongsPageNumber',
            contentType: 'application/json',

            success: function (lastPageNumber) {
                for (let i = 1; i <= lastPageNumber; i++) {
                    $("#forMusicTablePagesNav").append(
                        `<li class="page-item">
                            <a  id="music-page-number-link" class="page-link">${i}</a>
                        </li>`
                    );
                }

                $("nav #music-page-number-link").click(function () {
                    var pageNumber = $(this).text();
                    $.ajax({
                        type: 'get',
                        url: '/api/music/approvedSongsPage',
                        contentType: 'application/json',
                        data: 'pageNumber=' + pageNumber,

                        success: function (allSongs) {
                            $("#forMusicTable").html(
                                `<table class="table table-borderless">
                                    ${allSongs.map(renderMusics).join('')}
                                </table>`
                            );
                        }
                    });
                });

            }
        });

        function renderMusics(music) {
            let checkBanned = Boolean(music.banned);

            return `
        <tr>
            <td>
                ${music.name}                        
            </td>
            <!-- кнопка по добавлению в фильтр автора -->
            <td>
                <button
                    type="button"
                    class="addMusicToFilter btn ${checkBanned ? 'btn-success' : 'btn-warning'}"
                    data-banned="${checkBanned}"
                    data-music-id="${music.id}"
                >
                    ${checkBanned ? 'Разблокировать' : 'Заблокировать'}
                </button>
            </td> 
        </tr> 
    `;
        }

// функция для добавления песни(ен) в список запрещеных вызываеться при клике на кнопку
        $(document).off('click', '.addMusicToFilter');
        $(document).on('click', '.addMusicToFilter', function addMusicToFilter() {

            const $button = $(this);
            const musicId = Number($button.data('music-id'));
            const banned = Boolean($button.data('banned'));

            $.ajax({
                method: 'post',
                url: banned ? "/api/music/songsUnBan" : '/api/music/songsBan',
                contentType: "application/json",
                data: JSON.stringify(musicId),
                success: function () {

                    if (banned) {

                        $button
                            .html('Заблокировать')
                            .removeClass('btn-success')
                            .addClass('btn-warning')
                            //устанавливаем атрибуту значение
                            .data('banned', false);
                    } else {
                        // html = поменять содержимое
                        $button
                            .html('Разблокировать')
                            .removeClass('btn-warning')
                            .addClass('btn-success')
                            //устанавливаем атрибуту значение
                            .data('banned', true);
                    }
                }
            });
        });
    }
}


export default Filter;