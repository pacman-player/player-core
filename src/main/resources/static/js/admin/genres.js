$(document).ready(function () {
    getTable();

    let formAddGenre = $("#addForm");
    let fieldNameAddGenre = $("#addGenre");
    $("#addGenreBtn").on("click", function () {
        if (formAddGenre.valid()) {
            addGenre(formAddGenre, fieldNameAddGenre);
            formAddGenre.trigger("reset");
        }
    })

});


const errMessages = {
    required: "Укажите название",
    pattern: "Разрешенные символы: кирилица, латиница, цифры, тире",
    rangelength: "Количество символов должно быть в диапазоне [3-30]",
    remote: "Имя занято"
};

const genreNameRegEx = /[\wА-Яа-я\-]/;


$("#addForm").validate({
    rules: {
        name: {
            required: true,
            pattern: genreNameRegEx,
            rangelength: [3, 10],
            remote: {
                method: "GET",
                url: "/api/admin/genre/is_free",
                cache: false,
                data: {
                    name: function () {
                        return $("#addGenre").val()
                    },
                }
            }
        }
    },
    messages: {
        name: errMessages
    }
});


function addGenre(form, field) {
    $.ajax({
        method: "POST",
        url: "/api/admin/genre/add_genre",
        contentType: "application/json",
        data: JSON.stringify(field.val()),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        cache: false,
        complete: () => {
            $("#tab-genres-panel").tab('show');
            getTable();
        },
        success: () => {
            sendNotification();
            notification(
                "add-genre" + field.val(),
                ` Жанр ${field.val()} добавлен`,
                "genres-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name) {
    let theModal = $("#editGenres");
    let form = $("#editForm");
    let fieldId = $("#updateGenresId");
    let fieldName = $("#updateGenresName");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: genreNameRegEx,
                rangelength: [3, 10],
                remote: {
                    method: "GET",
                    url: "/api/admin/genre/is_free",
                    cache: false,
                    data: {
                        id: function () {
                            return fieldId.val()
                        },
                    }
                }
            }
        },
        messages: {
            name: errMessages
        },
        submitHandler: () => {
            $.ajax({
                method: "PUT",
                url: "/api/admin/genre/update_genre",
                contentType: "application/json",
                data: JSON.stringify({
                    id: id,
                    name: fieldName.val()
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
                    sendNotification();
                    notification(
                        "edit-genre" + id,
                        ` Жанр с id ${id} изменен`,
                        "genres-panel");
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
        url: "/api/admin/genre/delete_genre",
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
                "delete-genre" + id,
                ` Жанр c id ${id} удален`,
                "genres-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function getTable() {
    $.ajax({
        method: "GET",
        url: "/api/admin/genre/all_genres",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        cache: false,
        dataType: "JSON",
        success: function (genres) {
            let tableBody = $("#genresTable tbody");

            tableBody.empty();
            for (let i = 0; i < genres.length; i++) {
                // vars that contains object's fields
                let id = genres[i].id;
                let name = genres[i].name;
                // parsing fields
                let tr = $("<tr/>");
                tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="editGenreBtn"
                                        onclick="editButton(${id}, '${name}')">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteGenreBtn"
                                        onclick="deleteButton(${id})">
                                    Удалить
                                </button>
                            </td>`);
                tableBody.append(tr);
            }
        }
    });
}
