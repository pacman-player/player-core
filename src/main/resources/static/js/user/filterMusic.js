
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
        let checkBanned = Boolean(genre.banned);

        return `
                    <tr>
                        <td>
                            ${genre.name}
                        </td>
                        
                        <!-- кнопка по добавлению в фильтр жанра -->
                        <!-- если в артрибут передается название функции, то она будет вызвана с this = кнопки,
                         которую нажали -->
                        <td>
                            <button
                                type="button"
                                class="btn ${checkBanned ? 'btn-success' : 'btn-warning'}"
                                onclick="addGenreToFilter"
                                <!-- писать data перед собсвенными атрибутами - правило -->
                                data-banned="${checkBanned}"
                                data-genre-id="${genre.id}"
                            >
                                ${checkBanned ? 'Разблокировать': 'Заблокировать'}
                            </button>
                        </td>
                    </tr>
                    `;
    }

    // функция для добавления/удаления жанра в/из список(а) запрещеных
    function addGenreToFilter() {

        const $button = $(this);
        // ищем в кнопке атрибут и достаем его значение
        // преобразовываем их в тип соответсвующей функцией
        const genreId = Number($button.data('genre-id'));
        const banned = Boolean($button.data('banned'));

        $.ajax({
            method: 'post',
            url: banned ? '/api/user/genreUnBan' : '/api/user/genreBan',
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
                        .removeClass('btn-success')
                        .addClass('btn-warning')
                        //устанавливаем атрибуту значение
                        .data('banned', true);
                }
            }
        });
    }

    // код для поиска и генерации таблицы авторов
    let $authorForms = $(".authorSearch");
    // при клике на кнопку ".on" перехватывает событие и вызывает вместо него функцию
    $authorForms.on("submit", function(searchAuthor){

        // блокирует стандартный ход исполнения события "submit"
        searchAuthor.preventDefault();

        const authorName = $(this).find(".nameForAuthor").val();

        $.ajax({
            type: 'get',
            url: '/api/user/allAuthorsByName/' + authorName,
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
                             class="${checkBanned ? 'btn-success' : 'btn-warning'}"
                             onclick="addAuthorToFilter"
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
    function addAuthorToFilter() {
        const $button = $(this);
        // ищем в кнопке атрибут и достаем его значение
        // преобразовываем их в тип соответсвующей функцией
        const authorId = Number($button.data('author-id'));
        const banned = Boolean($button.data('banned'));

        $.ajax({
            method: 'post',
            url: banned ? '/api/user/authorsUnBan' : '/api/user/authorsBan',
            contentType : "application/json",
            data:JSON.stringify(authorId),
            dataType: 'json',

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
                        .removeClass('btn-success')
                        .addClass('btn-warning')
                        //устанавливаем атрибуту значение
                        .data('banned', true);
                }
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
                               class="${checkBanned ? 'btn-success' : 'btn-warning'}"
                               onclick="addMusicToFilter"
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
    function addMusicToFilter() {

        const $button = $(this);
        const musicId = Number($button.data('music-id'));
        const banned = Boolean($button.data('banned'));

        $.ajax({
            method: 'post',
            url: banned ? "/api/user/songsUnBan" : '/api/user/songsBan',
            contentType : "application/json",
            data:JSON.stringify(musicId),
            dataType: 'json',
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
                        .removeClass('btn-success')
                        .addClass('btn-warning')
                        //устанавливаем атрибуту значение
                        .data('banned', true);
                }
            }
        });
    }