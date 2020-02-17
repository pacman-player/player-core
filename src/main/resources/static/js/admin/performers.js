$(document).ready(function () {
    getTable();
});


const errMessages = {
    required: "Укажите название",
    pattern: "Разрешенные символы: кирилица, латиница, цифры, тире",
    rangelength: "Количество символов должно быть в диапазоне [3-30]",
    remote: "Имя занято"
};

const authorNameRegEx = /[\wА-Яа-я\-]/;


function prepareForm() {
    $("#addAuthorGenre").empty();
    let genres = getGenres();

    let selectOptions = "";
    for (let i = 0; i < genres.length; i++) {
        let option = genres[i].name;
        selectOptions += `<option value="${option}"> ${option} </option>`;
    }
    selectOptions += `<option value="" selected> Выберите жанр </option>`

    $('#addAuthorGenre').append(selectOptions);
}


function getGenres() {
    return $.ajax({
        method: 'GET',
        url: "/api/admin/author/all_genre",
        contentType: 'application/json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        async: false,
        cache: false,
        dataType: 'JSON'
    }).responseJSON;
}


function getTable() {
    $.ajax({
        method: "GET",
        url: "/api/admin/author/all_authors",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        cache: false,
        dataType: "JSON",
        success: (authors) => {
            let tableBody = $("#AuthorTable tbody");

            tableBody.empty();
            for (let i = 0; i < authors.length; i++) {
                let id = authors[i].id;
                let name = authors[i].name;

                // list of genres ()
                let genres = authors[i].authorGenres;
                let htmlGenres = "";
                for (let gi = 0; gi < genres.length; gi++) {
                    htmlGenres += genres[gi].name + " ";
                }
                // parsing fields
                let tr = $('<tr/>');
                tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td> ${htmlGenres} </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="editAuthorBtn"
                                        onclick="editButton(${id}, '${name}')">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteAuthorBtn"
                                        onclick="deleteButton(${id})">
                                    Удалить
                                </button>
                            </td>`);
                tableBody.append(tr);
            }
        }
    });
}