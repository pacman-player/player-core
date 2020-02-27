$(document).ready(function () {
    getTable();

    let formAddCompilation = $("#addCompilationForm");
    let fieldCompilationName = $("#addCompilationName");
    let fieldCompilationGenre = $("#addCompilationGenre");
    let fieldCompilationCover = $("#addCompilationCover");
    $("#addCompilationBtn").on("click", function (event) {
        event.preventDefault();

        addCompilation(
            formAddCompilation,
            fieldCompilationName.val(),
            fieldCompilationCover.prop("files")[0],
            fieldCompilationGenre.val());
        formAddCompilation.trigger("reset");
    })

});



function prepareForm(dropDownListSelector = $("#addCompilationGenre")) {
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


function addCompilation(form, name, cover, genre) {
    console.log(genre);
    let formData = new FormData();
    formData.append("name", name);
    formData.append("genre", genre);
    if (cover) {
        formData.append("cover", cover);
    }

    $.ajax({
        method: "POST",
        url: "/api/admin/compilation",
        contentType: false,
        processData: false,
        mimeType: "multipart/form-data",
        data: formData,
        complete: () => {
            $("#tab-compilations-panel").tab("show");
            getTable();
        },
        success: () => {
            alert(`${name} ADDED!`)
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name) {
    let fieldGenre = $("#updateCompilationGenre");
    // подгрузка жанров в выпадающий список
    prepareForm(fieldGenre);

    let theModal = $("#updateCompilationModal");
    let fieldId = $("#updateCompilationId");
    let fieldName = $("#updateCompilationName");
    let fieldCover = $("#updateCompilationCover");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");

    $("#updateCompilationBtn").on("click", function () {
        let formData = new FormData();
        formData.append("id", id);
        formData.append("name", fieldName.val());
        formData.append("genre", fieldGenre.val());
        let file = fieldCover.prop("files")[0];
        if (file) {
            formData.append("cover", file);
        }

        $.ajax({
            method: "POST",
            url: "/api/admin/compilation/update",
            contentType: false,
            processData: false,
            mimeType: "multipart/form-data",
            data: formData,
            complete: () => {
                theModal.modal("hide");
                getTable();
            },
            success: () => {
                alert(`${id} EDITED!`)
            },
            error: (xhr, status, error) => {
                alert(xhr.responseText + "|\n" + status + "|\n" + error);
            }
        })

    });
}


function deleteButton(id) {
    $.ajax({
        method: "DELETE",
        url: "/api/admin/compilation/delete",
        contentType: "application/json",
        data: JSON.stringify(id),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        complete: () => {
            getTable();
        },
        success: () => {
           alert(`${id} DELETED!`)
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}

function allSongsButton() {
    alert("all songs")
}

function getTable() {
    $.ajax({
        method: "GET",
        url: "/api/admin/compilation",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "JSON",
        success: function (compilations) {
            let tableBody = $("#compilationsTable tbody");

            tableBody.empty();
            for (let i = 0; i < compilations.length; i++) {
                let id = compilations[i].id;
                let name = compilations[i].name;
                let genre = compilations[i].genre.name;
                let songs;
                let cover = compilations[i].cover;
                // если файлу не имеет обложки, то ему будет назначена стандартная (/covers/na.jpg)
                if (cover == null) {
                    cover = "na.jpg"
                }

                let tr = $("<tr/>");
                tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td> ${genre} </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-success"
                                        id="showSongsBtn"
                                        onclick="allSongsButton(${id}, '${name}')">
                                    Список треков
                                </button>
                            </td>
                            <td> 
                                <div class="img_wrap">
                                    <img src="/cover/${cover}">
                                </div>
                            </td>
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
