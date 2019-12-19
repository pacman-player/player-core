
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
                                onclick="addToFilter(${genreId})"
                            >
                                Заблокировать
                            </button>
                        </td>
                    </tr>
                    `;
            }
            tbl += "</table>";
            $("#forJsScript").html(tbl);
        }
    });

    // функция для добавления жанра в список запрещеных вызываеться при клике на кнопку
    function addToFilter(id) {
        $.ajax({
            method: 'get',
            url: '/filterForGenre/' + id,
            dataType: 'json',
            success: function () {}
        });
    }



/*
tbl += `
                <tr>
                    <th>
                        <form class="form-inline">
                            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search"/>
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                        </form>
                    </th>
                </tr>
                    `;
 */