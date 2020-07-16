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


function prepareForm(dropDownListSelector = $("#addCompilationGenre"), selectedGenre) {
    dropDownListSelector.empty();
    // let genres = getGenres();
    let genres = getApprovedGenre();

    let selectOptions = "";
    for (let i = 0; i < genres.length; i++) {
        let option = genres[i].name;
        selectOptions += `<option `;
        //если жанр из таблицы песен совпадает с жанром из БД - устанавлваем в selected
        if (selectedGenre === option) {
            selectOptions += `selected`;
        }
        selectOptions += ` value="${option}">${option}</option>`;
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

function getApprovedGenre(){
    return $.ajax({
        method: 'GET',
        url: "/api/admin/author/approvedGenre",
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
                let genre = compilations[i].genre;
                let genreId = compilations[i].genre.id;
                let cover = compilations[i].coverView;
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
                                <div class="img_wrap">
                                    <img src="/cover/${cover}" alt="IN YOUR FACE!">
                                </div>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-success"
                                        id="showSongsBtn"
                                        onclick="allSongsButton(${id})">
                                    Посмотреть текущие
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-success"
                                        id="showAvailableSongsBtn"
                                        onclick="availableSongsButton(${id})">
                                    Добавить песни
                                </button>
                            </td>
                            <td>
                                <button type="submit"
                                        class="btn btn-sm btn-info"
                                        id="editGenreBtn"
                                        onclick="editButton(${id}, '${name}', '${genre}', '${cover}')">
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


function getCompilationContentById(compilationId) {
    $.ajax({
        method: "GET",
        url: `/api/admin/compilation/content/${compilationId}`,
        success: function (songs) {
            let tableBody = $("#songsTable tbody");

            tableBody.empty();
            for (let i = 0; i < songs.length; i++) {
                let id = songs[i].id;
                let name = songs[i].name;
                let author = songs[i].authorName;
                let genre = songs[i].genreName;

                let tr = $("<tr/>");
                tr.append(`
                             <td> ${id} </td>
                             <td> ${name} </td>
                             <td> ${author} </td>
                             <td> ${genre} </td>
                             <td>
                                 <button type="button"
                                         class="btn btn-sm btn-info"
                                         id="removeSongBtn"
                                         onclick="removeSongFromCompilation(${compilationId}, ${id})">
                                     Удалить
                                 </button>
                             </td>`);
                tableBody.append(tr);
            }
        }
    });
}


function getAvailableCompilationContentById(compilationId) {
    $.ajax({
        method: "GET",
        url: `/api/admin/compilation/content/available/${compilationId}`,
        success: function (songs) {
            let tableBody = $("#availableSongsTable tbody");

            tableBody.empty();
            for (let i = 0; i < songs.length; i++) {
                let id = songs[i].id;
                let name = songs[i].name;
                let author = songs[i].authorName;
                let genre = songs[i].genreName;

                let tr = $("<tr/>");
                tr.append(`
                             <td> ${id} </td>
                             <td> ${name} </td>
                             <td> ${author} </td>
                             <td> ${genre} </td>
                             <td>
                                 <button type="button"
                                         class="btn btn-sm btn-info"
                                         id="addSongBtn"
                                         onclick="addSongToCompilation(${compilationId}, ${id})">
                                     Добавить
                                 </button>
                             </td>`);
                tableBody.append(tr);
            }
        }
    });
}


function addCompilation(form, name, cover, genre) {
    let formData = new FormData();
    formData.append("name", name);
    formData.append("genre", genre);

    if (cover) {
        if (validate_fileupload(cover)) {
            formData.append("cover", cover);
        }
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
            console.log(`SONG COMPILATION ${name} ADDED!`)
        },
        error: function () {
            alert("Подборка с таким именем уже существует");

        }
    })
}


function addSongToCompilation(compilationId, songId) {
    $.ajax({
        method: "POST",
        url: `/api/admin/compilation/content/add/${compilationId}/${songId}`,
        complete: () => {
            getAvailableCompilationContentById(compilationId)
        },
        success: (response) => {
            if (response.success === true) {
                notification("addSongInCompilations", response.data, "compilations-panel");
            } else if(response.hasOwnProperty('errorMessage') && response.errorMessage.hasOwnProperty('textMessage')) {
                alert(response.errorMessage.textMessage);
            } else {
                alert(response);
            }
            console.log(`SONG ${songId} ADDED TO SONG COMPILATION ${compilationId}!`)
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name, genre, cover) {
    let fieldGenre = $("#updateCompilationGenre");
    // подгрузка жанров в выпадающий список
    prepareForm(fieldGenre, genre);
    let theModal = $("#updateCompilationModal");
    let fieldId = $("#updateCompilationId");
    let fieldName = $("#updateCompilationName");
    let fieldCover = $("#compilationCover");////текущая картинка
    let fieldCoverForUpdate = $("#updateCompilationCover");//новая картинка

    fieldId.val(id);
    fieldName.val(name);

    let coverUrl = '/cover/' + cover;//путьдо текущей картинки-обложки
    fieldCover.replaceWith('<div >\n    <img class="img_wrap" src="'+coverUrl+'" alt="IN YOUR FACE!">\n               </div>');

    theModal.modal("show");

    $("#updateCompilationBtn").on("click", function () {
        let formData = new FormData();
        formData.append("id", fieldId.val());
        formData.append("name", fieldName.val());
        formData.append("genre", fieldGenre.val());

        let file = fieldCoverForUpdate.prop("files")[0];//тут ошибка
        if (file) {
            if (validate_fileupload(file)) {
                formData.append("cover", file);
            }
        }
        $.ajax({
            method: "POST",
            url: "/api/admin/compilation/update",
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            complete: () => {
                theModal.modal("hide");
                getTable();
            },
            success: () => {
                console.log(`SONG COMPILATION ${fieldId.val()} EDITED!`)
            },
            error: function () {
                alert("Подборка с таким именем уже существует");

            }
        })

    });
}

function validate_fileupload(file){

    var allowed_extensions = new Array("jpg","png","gif");
    let fileName = file.name;
    var file_extension = fileName.split('.').pop().toLowerCase();
    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }
    alert("Sorry, file format is invalid, allowed extensions are: jpg, png, gif");
    return false;
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
            console.log(`SONG COMPILATION ${id} DELETED!`)
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function removeSongFromCompilation(compilationId, songId) {
    $.ajax({
        method: "DELETE",
        url: `/api/admin/compilation/content/remove/${compilationId}/${songId}`,
        complete: () => {
            getCompilationContentById(compilationId)
        },
        success: () => {
            console.log(`SONG ${songId} REMOVED FROM COMPILATION ${compilationId}!`)
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function allSongsButton(compilationId) {
    $("#songs").modal("show");
    getCompilationContentById(compilationId)
}


function availableSongsButton(compilationId) {
    $("#availableSongs").modal("show");
    getAvailableCompilationContentById(compilationId)
}
