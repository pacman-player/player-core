
let Filter = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '\n' +
            '                <H3>Страница Фильтров</H3>\n' +
            '\n' +
            '                <!-- меню выбора выриантов для блокировки -->\n' +
            '                <div class="col-fhd-4 col-xl-5 col-lg-6">\n' +
            '                    <ul class="nav nav-tabs nav-content " role="tablist">\n' +
            '\n' +
            '                        <li id="morning-music-nav" class="active">\n' +
            '                            <a href="#genre"\n' +
            '                               aria-controls="genre-music-nav"\n' +
            '                               role="tab"\n' +
            '                               data-toggle="tab"\n' +
            '                            >\n' +
            '                                По жанрам\n' +
            '                            </a>\n' +
            '                        </li>\n' +
            '\n' +
            '                        <li id="day-music-nav">\n' +
            '                            <a href="#artists"\n' +
            '                               aria-controls="artists-music"\n' +
            '                               role="tab"\n' +
            '                               data-toggle="tab"\n' +
            '                            >\n' +
            '                                По артистам\n' +
            '                            </a>\n' +
            '                        </li>\n' +
            '\n' +
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
            '\n' +
            '                    <!-- контент для меню "По жанрам" -->\n' +
            '                    <div class="tab-content active">\n' +
            '                        <div role="tabpanel" class="tab-pane active" id="genre">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '\n' +
            '                                        <div id="forGenreTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '\n' +
            '                        <!-- контент для меню "По артистам" -->\n' +
            '                        <div role="tabpanel" class="tab-pane" id="artists">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '\n' +
            '                                        <form class="form-inline authorSearch">\n' +
            '                                            <input class="form-control mr-sm-2 nameForAuthor" type="search" placeholder="Search" aria-label="Search"/>\n' +
            '                                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>\n' +
            '                                        </form>\n' +
            '\n' +
            '                                        <div id="forAuthorTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '\n' +
            '                        <!-- контент для меню "По песням" -->\n' +
            '                        <div role="tabpanel" class="tab-pane" id="songs">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '\n' +
            '                                        <form class="form-inline musicSearch">\n' +
            '                                            <input class="form-control mr-sm-2 nameForMusic" type="search" placeholder="Поиск" aria-label="Search"/>\n' +
            '                                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Поиск</button>\n' +
            '                                        </form>\n' +
            '\n' +
            '                                        <div id="forMusicTable">\n' +
            '                                            <!-- скрипт заполнения таблицы в файле filterMusic.js -->\n' +
            '                                        </div>\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '\n' +
            '        </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
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
        $(document).on('click', '.addGenreToFilter', function addGenreToFilter() {

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

// код для поиска и генерации таблицы авторов
        let $authorForms = $(".authorSearch");
// при клике на кнопку ".on" перехватывает событие и вызывает вместо него функцию
        $authorForms.on("submit", function (searchAuthor) {

            // блокирует стандартный ход исполнения события "submit"
            searchAuthor.preventDefault();

            const authorName = $(this).find(".nameForAuthor").val();

            $.ajax({
                type: 'get',
                url: '/api/author/allAuthorsByName/' + authorName,
                contentType: 'application/json;',

                success: function (allAuthors) {
                    $("#forAuthorTable").html(`<table class="table table-borderless">
                ${allAuthors.map(renderAuthors).join('')}
                </table>`);
                }
            });
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

// код для поиска и вывода песен
        let $musicForm = $(".musicSearch");
        $musicForm.on("submit", function (searchMusics) {

            searchMusics.preventDefault();
            const musicName = $(this).find(".nameForMusic").val();

            $.ajax({
                type: 'get',
                url: '/api/music/allSongsByName/' + musicName,
                contentType: 'application/json;',

                success: function (allMusics) {
                    $("#forMusicTable").html(`<table class="table table-borderless">
                ${allMusics.map(renderMusics).join('')}
                </table>`);
                }
            });
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

//Ниже закомменченный код - тоже рабочий, тот же функционал!!!
// function getQueue() {
//     $.ajax({
//         type: "get",
//         url: "/api/v1/getSongsInQueue",
//         success: function (songList) {
//             if (songList.length > 0) {
//                 let songQueueHTML = '';
//                 for (let s = 0; s < songList.length; s++) {
//                     songQueueHTML += '<tr><td>' + (s + 1) + '\. ' +songList[s] + '</td></tr>';
//                 }
//                 $("#song-queue-list").empty().append(songQueueHTML);
//             }
//         }
//     });
// }
//
// function getOrders() {
//     $.ajax({
//         type: "get",
//         url: "/api/v1/getOrders",
//         success: function (data) {
//             for (let i = 0; i < data.length; i++) {
//                 $("." + data[i].split(":")[0]).empty().append(data[i].split(":")[1]);
//             }
//         }
//     });
// }
//
// let Statistics = {
//
//     render : async () => {
//         let request = Utils.parseRequestURL();
//         let queue = await getQueue();
//         let orders = await getOrders();
//
//         return /*html*/`        <!--центральный блок-->
// <!--        <div class="tab-content">-->
//             <div role="tabpanel" class="tab-pane active col-lg-6 col-md-6 col-xs-6 " id="my-selection">
//
//                 <H3> Статистика</H3>
//
//
//             </div>
// <!--        </div>-->
//
//         <!-- right side stats bar-->
//         <div class="container col-lg-4 col-md-4 col-xs-4 col-xl-4" id="right-side-bar">
//                 <table class="table bg-white stats-table">
//                     <thead>
//                     <tr>
//                         <th class="orders-title">Заказы</th>
//                         <th class="orders-type-free text-right small text-secondary">Бесплатных</th>
//                         <th class="orders-type-total text-right">Всего</th>
//                     </tr>
//                     </thead>
//                     <tbody>
//                     <tr>
//                         <td class="orders-today-label">За сегодня</td>
//                         <td class="orders-today-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-today-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-yesterday-label">За вчера</td>
//                         <td class="orders-yesterday-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-yesterday-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-week-label">За неделю</td>
//                         <td class="orders-week-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-week-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-month-label">За месяц</td>
//                         <td class="orders-month-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-month-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-total-label">За все время</td>
//                         <td class="orders-total-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-total-total text-right"><span>0</span></td>
//                     </tr>
//                     </tbody>
//                 </table>
//             <div class="text-center"><h6>Очередь заказов</h6></div>
//             <div id="scrollable-part">
//                 <table class="table bg-white song-queue-block">
//                     <thead>
//                     <tr>
//                     <th class="song-queue-block-header">
//                         <span></span>
//                     </th>
//                     </tr>
//                     </thead>
//                     <tbody id="song-queue-list">
//                     <tr>
//                     <td class="text-center">
//                             Нет песен в очереди
//                     </td>
//                     </tr>
//                     </tbody>
//                 </table>
//             </div>
//             </div>
//         </div>
//         `
//     }
//     , after_render: async () => {
//     }
// }

export default Filter;