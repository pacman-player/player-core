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
                let genre = compilations[i].genre.name;
                let songs;
                let cover = compilations[i].cover;

                let tr = $("<tr/>");
                tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td> ${genre} </td>
                            <td> songs </td>
                            <td> ${cover} </td>
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