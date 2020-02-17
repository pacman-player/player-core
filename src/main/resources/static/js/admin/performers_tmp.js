$(document).ready(function () {
    getTable();

    let formAddAuthor = $("#addForm");
    let fieldAuthorName = $("#addAuthorName");
    let fieldAuthorGenre = $("#addAuthorGenre");
    $("#addAuthorBtn").on("click", function (event) {
        event.preventDefault();
        if (formAddAuthor.valid()) {
            addAuthor(formAddAuthor, fieldAuthorName, fieldAuthorGenre);
            formAddAuthor.trigger("reset");
        }
    })
});

// Drop-down list data
function prepareForm() {
    $("#addAuthorGenre").empty();
    let genres = getGenres();

    let selectOptions = "";
    for (let i = 0; i < genres.length; i++) {
        let option = genres[i].name;
        selectOptions += `<option value="${option}"> ${option} </option>`;
    }
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


const errMessages = {
    required: "Укажите название",
    pattern: "Разрешенные символы: кирилица, латиница, цифры, тире",
    rangelength: "Количество символов должно быть в диапазоне [3-30]",
    remote: "Имя занято"
};

const authorNameRegEx = /[\wА-Яа-я\-]/;


$("#addForm").validate({
    rules: {
        name: {
            required: true,
            pattern: authorNameRegEx,
            rangelength: [3, 30],
            remote: {
                method: "GET",
                url: "/api/admin/author/is_free",
                cache: false,
                data: {
                    name: function () {
                        return $("#addAuthorName").val()
                    },
                }
            }
        }
    },
    messages: {
        name: errMessages
    }
});

function addAuthor(form, name, genre) {
    $.ajax({
        method: "POST",
        url: "/api/admin/author/add_author",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            name: name.val(),
            genres: genre.val()
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        cache: false,
        complete: () => {
            $("#tab-author-panel").tab("show");
            getTable();
        },
        success: () => {
            notification(
                "add-author" + name.val(),
                ` Автор ${name.val()} добавлен`,
                "authors-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })

}


function deleteButton(id) {
    $.ajax({
        method: "DELETE",
        url: "/api/admin/author/delete_author",
        contentType: "application/json",
        data: JSON.stringify(id),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        cache: false,
        complete: () => {
            getTable();
        },
        success: () => {
            notification(
                "delete-author" + id,
                ` Исполнитель c id ${id} удален`,
                "authors-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function getTable() {
    $.ajax({
        method: "GET",
        url: "/api/admin/author/all_authors",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
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
                let tr = $("<tr/>");
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
