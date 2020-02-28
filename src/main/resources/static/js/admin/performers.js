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
function prepareForm(dropDownListSelector = $("#addAuthorGenre")) {
    dropDownListSelector.empty();
    let genres = getGenres();

    let selectOptions = "";
    for (let i = 0; i < genres.length; i++) {
        let option = genres[i].name;
        selectOptions += `<option value="${option}"> ${option} </option>`;
    }

    dropDownListSelector.append(selectOptions);
}

function getGenres() {
    return $.ajax({
        method: 'GET',
        url: "/api/admin/author/all_genre",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        async: false,
        cache: false,
        dataType: "JSON"
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
        complete: () => {
            $("#tab-author-panel").tab("show");
            getTable();
        },
        success: () => {
            notification(
                "add-author" + name.val(),
                ` Автор ${name.val()} добавлен`,
                "authors-panel");
            getNotificationNumber();
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })

}


function editButton(id, name) {
    let fieldGenre = $("#editAuthorGenre");
    // подгрузка жанров в выпадающий список
    prepareForm(fieldGenre);

    let theModal = $('#editAuthor');
    let form = $("#edit-form");
    let fieldId = $("#editAuthorId");
    let fieldName = $("#editAuthorName");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: authorNameRegEx,
                rangelength: [3, 30],
                remote: {
                    method: "GET",
                    url: "/api/admin/author/is_free",
                }
            }
        },
        messages: {
            name: errMessages
        },
        submitHandler: () => {
            $.ajax({
                method: "PUT",
                url: "/api/admin/author/update_author",
                contentType: "application/json",
                data: JSON.stringify({
                    id: id,
                    name: fieldName.val(),
                    genres: fieldGenre.val()
                }),
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                cache: false,
                complete: () => {
                    theModal.modal("hide");
                    getTable();
                },
                success: () => {
                    notification(
                        "edit-author" + id,
                        ` Изменения исполнителя с id ${id} сохранены`,
                        "authors-panel");
                },
                error: (xhr, status, error) => {
                    alert(xhr.responseText + "|\n" + status + "|\n" + error);
                }
            })
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
