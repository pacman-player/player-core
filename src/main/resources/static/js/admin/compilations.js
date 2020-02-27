$(document).ready(function () {
    getTable();
});

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
                // Хардкодная проверка на null, нужно УБРАТЬ НАХУЙ! но не сейчас, а когда прижмет
                let genre;
                try {
                    genre = compilations[i].genre.name;
                } catch (e) {
                    console.log(e);
                    genre = null;
                }

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

function editButton(id, name) {
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
                notification(
                    "edit-compilation" + id,
                    ` Подборка с id ${id} обновлена`,
                    "genres-panel");
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
            notification(
                "delete-compilation" + id,
                ` Подборка c id ${id} удалена`,
                "compilation-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}

function allSongsButton() {
    alert("all songs")
}