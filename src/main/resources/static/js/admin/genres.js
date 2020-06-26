$(document).ready(function () {
    getTable();

    let formAddGenre = $("#addForm");
    let fieldNameAddGenre = $("#addGenre");
    $("#addGenreBtn").on("click", function (event) {
        event.preventDefault();

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
            rangelength: [3, 30],
            remote: {
                method: "GET",
                url: "/api/admin/genre/is_free",
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


function editButton(id, name, approved) {
    let theModal = $("#editGenres");
    let form = $("#editForm");
    let fieldId = $("#updateGenresId");
    let fieldName = $("#updateGenresName");
    let fieldApproved = $("#updateGenreApproved");

    form.find('.error').removeClass("error");
    form.find('.form-control error').remove();
    form.find('#updateGenresName-error').remove();

    fieldId.val(id);
    fieldName.val(name);
    fieldApproved.prop('checked', approved);

    theModal.modal("show");
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: genreNameRegEx,
                rangelength: [3, 30],
                remote: {
                    method: "GET",
                    url: "/api/admin/genre/is_free",
                    data: {
                        id: () => {
                            return fieldId.val()
                        }
                    }
                },
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
                    id: fieldId.val(),
                    name: fieldName.val(),
                    approved: fieldApproved.prop('checked')
                }),
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                complete: () => {
                    theModal.modal("hide");
                    getTable();
                },
                success: () => {
                    sendNotification();
                    notification(
                        "edit-genre" + fieldId.val(),
                        ` Жанр с id ${fieldId.val()} изменен`,
                        "genres-panel");
                },
                error: (xhr, status, error) => {
                    alert(xhr.responseText + "|\n" + status + "|\n" + error);
                }
            })
        }
    });
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
        dataType: "JSON",
        success: (genres) => {
            let tableBody = $("#genresTable tbody");

            tableBody.empty();
            for (let i = 0; i < genres.length; i++) {
                // vars that contains object's fields
                let id = genres[i].id;
                let approved = genres[i].approved;
                let checked = genres[i].approved ? "checked" : "";
                let name = genres[i].name;
                // parsing fields
                let tr = $("<tr/>");
                tr.append(`
                            <td>${id}</td>
                            <td><input class="checkbox" type="checkbox" disabled ${checked}/></td>
                            <td>${name}</td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="editGenreBtn"
                                        onclick="editButton(${id}, '${name}', ${approved})">
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
                            </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="addAuthorsToGenreBtn"
                                        onclick="addAuthorsToGenre(${id}, '${name}')">
                                    Добавить исполнителей
                                </button>
                            </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="deleteAuthorsFromGenreBtn"
                                        onclick="deleteAuthorsFromGenre(${id}, '${name}')">
                                    Удалить исполнителей
                                </button>
                            </td>
`);
                tableBody.append(tr);
            }
        }
    });
}

// выводим список всех песен в модальное окно "addsongGenreManagement"
function getAuthorsGenre(genreId, url) {
    $.ajax({
        method: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(genreId),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: function (data) {
            $('#listOfAuthorsByGenre').html('');
            let tr;
            $.each(data, function (i, authorDto) {
                tr = $('<tr/>');
                tr.append("<td> <input class='chcheck' id='check' type='checkbox' name='song' value='" +
                    authorDto.id + "'>" + "   " + authorDto.name + "</td>");
                $("#listOfAuthorsByGenre").append(tr);
            });
        }
    });
}

function addAuthorsToGenre(id) {
    getAuthorsGenre(id, '/api/author/authors_out_of_genre');
    document.getElementById("genreId").value = id;
    document.getElementById("operationID").value = "add";
    document.getElementById("changeGenresBtn").innerText = "Добавить";
    let theModal = $("#addAuthorGenreManagement");
    // показываем строки с выбраныым ИД жанра
    $('.' + id).show();
    theModal.id = id;
    theModal.modal("show");

    $('#btnClose, #btnCloseModal').click(function () {
        $('.' + id).hide();
    });
}

function deleteAuthorsFromGenre(id) {
    getAuthorsGenre(id, '/api/author/authors_of_genre');
    document.getElementById("operationID").value = "delete";
    document.getElementById("genreId").value = id;
    document.getElementById("changeGenresBtn").innerText = "Удалить";
    let theModal = $("#addAuthorGenreManagement");
// показываем строки с выбраныым ИД жанра
    $('.' + id).show();
    theModal.modal("show");
    //очищаем option в модалке
    $('#btnClose, #btnCloseModal').click(function () {
       // $('#addAuthorGenreManagement').modal('clear');
        $('.' + id).hide();
    });
}

// заполняем массив из выбранных элементов checkbox и выбранного жанра последним эллементом
function pullUpdateAuthors() {
    let updateAuthors = {};
    $('input:checkbox:checked').each(function (i) {
        if ($(this).val() == "on" || null) {

        } else {
            updateAuthors[i] = ($(this).val());
        }
    });
    return updateAuthors;
}

function getAuthorsUrl() {
    let url = "/api/author/update_authors";
    if ( document.getElementById("operationID").value == "delete") {
        url = "/api/author/delete_authors";
    }
    return url;
}

function saveGenreAuthors() {
    let updateAuthors = {};
    updateAuthors = pullUpdateAuthors();
    let genreID = document.getElementById("genreId").value;
    let url = getAuthorsUrl() + '?id=' + genreID;
    $.ajax({
        url: url,
        data: JSON.stringify(updateAuthors),
        contentType: "application/json;charset=UTF-8",
        method: 'PUT',
        dataType: 'json',
        async: false,
        success: function () {
            window.setTimeout(function () {
                $('.alert-success').alert('close');
            }, 5000);
            updateAuthors = [];
        },
        error: function () {
            updateAuthors = [];
        },
    });
    $('#addAuthorGenreManagement').modal('hide');
}

let changeGenresBtn = function (event) {
    let checkedItems = $('.chcheck:checked').length;
    let checkedAll = $('.chcheckAll:checked').length;

    $('#changeGenresBtn, #updateSongGenre').prop('disabled', checkedItems == 0 && checkedAll == 0);
}

$(document).on("change", ".chcheck", changeGenresBtn);
$(document).on("change", ".chcheckAll", changeGenresBtn);


