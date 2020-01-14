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