$(document).ready(function () {
    getTable();
    pagination();

    let formAddTag = $("#addForm");
    let fieldNameAddTag = $("#addTag");
    $("#addTagBtn").on("click", function (event) {
        event.preventDefault();

        if (formAddTag.valid()) {
            addTag(formAddTag, fieldNameAddTag);
            formAddTag.trigger("reset");
        }
    })

});


const errMessages = {
    required: "Укажите название",
    pattern: "Разрешенные символы: кирилица, латиница, цифры",
    rangelength: "Количество символов должно быть в диапазоне [3-30]",
    remote: "Имя занято"
};

const tagNameRegEx = /^[A-Za-zА-Яа-я0-9]+$/

$("#addForm").validate({
    rules: {
        name: {
            required: true,
            pattern: tagNameRegEx,
            rangelength: [3, 30],
            remote: {
                method: "GET",
                url: "/api/admin/tag/is_free",
                data: {
                    name: function () {
                        return $("#addTag").val()
                    },
                }
            }
        }
    },
    messages: {
        name: errMessages
    }
});


function addTag(form, field) {
    $.ajax({
        method: "POST",
        url: "/api/admin/tag/add_tag",
        contentType: "application/json",
        data: JSON.stringify(field.val()),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        complete: () => {
            $("#tab-tags-panel").tab('show');
            getTable();
        },
        success: () => {
            sendNotification();
            notification(
                "add-tag" + field.val(),
                ` Тэг ${field.val()} добавлен`,
                "tags-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name) {
    let theModal = $("#editTags");
    let form = $("#editForm");
    let fieldId = $("#updateTagsId");
    let fieldName = $("#updateTagsName");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: tagNameRegEx,
                rangelength: [3, 30],
                remote: {
                    method: "GET",
                    url: "/api/admin/tag/is_free",
                    data: {
                        name: () => {
                            return fieldName.val()
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
                url: "/api/admin/tag/update_tag",
                contentType: "application/json",
                data: JSON.stringify({
                    id: fieldId.val(),
                    name: fieldName.val(),
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
                        "edit-tag" + fieldId.val(),
                        ` Тэг с id ${fieldId.val()} изменен`,
                        "tags-panel");
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
        url: "/api/admin/tag/delete_tag",
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
                "delete-tag" + id,
                ` Тэг c id ${id} удален`,
                "tags-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}

let pageNo = 1;
let pageSize = 20;
let countPages;

function getTable() {
    $.ajax({
        method: "GET",
        url: "/api/admin/tag/all_tags?page=" + pageNo + "&size=" + pageSize,
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "JSON",
        success: (tags) => {

            tags.sort((a, b) => a.id - b.id);
            let tableBody = $("#tagsTable tbody");

            tableBody.empty();
            for (let i = 0; i < tags.length; i++) {
                // vars that contains object's fields
                let id = tags[i].id;
                let name = tags[i].name;
                // parsing fields
                let tr = $("<tr/>");
                tr.append(`
                            <td>${id}</td>
                            <td>${name}</td>
                            <td>
                                <button type="submit"
                                        class="btn btn-sm btn-info"
                                        id="editTagBtn"
                                        onclick="editButton(${id}, '${name}')">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteTagBtn"
                                        onclick="deleteButton(${id})">
                                    Удалить
                                </button>
                            </td>
                            <td>
                                <button type="submit"
                                        class="btn btn-sm btn-info"
                                        id="editTagBtn"
                                        onclick="editTagOfSongs(${id}, '${name}')">
                                    Управление песнями
                                </button>
                            </td>
`);
                tableBody.append(tr);
            }
        }
    });
}

function pagination() {
    $.ajax({
            method: "GET",
            url: "/api/admin/tag/count_tags",
            contentType: "application/json",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            dataType: "JSON",
            success: (countTags) => {

                countPages = countTags / pageSize;

                let numsPages = $("#pagesNumsTagsList");
                numsPages.empty();
                numsPages.append(`
                    <li 
                        class="page-item disabled" 
                        id="prevTagsPage">
                            <a 
                                class="page-link" 
                                href="#">
                                    Назад
                            </a>
                    </li>
                    <li 
                        class="page-item active">
                            <a 
                                class="page-link" 
                                href="#" 
                                onclick="changeTagsPage(1)">
                                    1
                            </a>
                    </li>
                `);

                for (let i = 1; i < countPages; i++) {
                    numsPages.append(`
                        <li 
                            class="page-item">
                                <a 
                                    class="page-link" 
                                    href="#" 
                                    onclick="changeTagsPage(${i + 1})">
                                        ${i + 1}
                                </a>
                        </li>
                                    `);
                }
                numsPages.append(`
                    <li 
                        class="page-item" 
                        id="nextTagsPage">
                            <a 
                                class="page-link" 
                                href="#">
                                    Вперёд
                            </a>
                    </li>
                    `);

                if (countPages == 1) {
                    $('#prevTagsPage').addClass('disabled');
                    $('#nextTagsPage').addClass('disabled');
                }
            }
        }
    )
}

function changeTagsPage(noPage) {
    pageNo = noPage;
    if (pageNo == 1) {
        $('#prevTagsPage').addClass('disabled');
    } else {
        $('#prevTagsPage').removeClass('disabled');
    }
    if (pageNo >= countPages) {
        $('#nextTagsPage').addClass('disabled');
    } else {
        $('#nextTagsPage').removeClass('disabled');
    }
    getTable();
}

$('#pagesNumsTagsList')
    .on('click', 'li:not(#prevTagsPage):not(#nextTagsPage)', function () {
        $('.pagination li').removeClass('active');
        $(this).not('#prevTagsPage,#nextTagsPage').addClass('active');
    })
    .on('click', 'li#prevTagsPage', function () {
        $('li.page-item.active').removeClass('active').prev().addClass('active');
        changeTagsPage(--pageNo);
    })
    .on('click', 'li#nextTagsPage', function () {
        $('li.page-item.active').removeClass('active').next().addClass('active');
        changeTagsPage(++pageNo);
    });

function editTagOfSongs(id, name) {
    $('#tagId').val(id);
    $('#tagName').val(name);

    let theModal = $("#songTagManagement");

    // выводим список песен, имеющих данный тэг, в модальное окно "songTagManagement"
    $.ajax({
        method: "GET",
        url: "/api/admin/song/songs_by_tag",
        contentType: "application/json",
        data: {
            tag: name,
        },
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success: (songs) => {
            // $("#listOfSongsByTag").empty();
            $('.trTagSongId').remove();
            let tr = [];
            for (let i = 0; i < songs.length; i++) {
                tr.push('<tr class="trTagSongId" id=' + songs[i].id + '>');
                tr.push('<td class="' + id + '" id="' + id + '" > <h3><input class="chcheck" id="check" type="checkbox" name="song" value="' + songs[i].id + '">' + ' ' + songs[i].name + '</h3></td></tr>');
            }
            $("#listOfSongsByTag").append($(tr.join('')));
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    });

    theModal.modal("show");

    $('#btnClose, #btnCloseModal').click(function () {
        $('.' + id).remove();
    });

}

let tagId = {};
let tagName = {};
let deleteObject = {};
$('#deleteTagForSongsBtn').on('click', function () {
    tagId = $('#tagId').val();
    tagName = $('#tagName').val();

    let checked = $('input:checkbox:checked');
    if (checked.length != 0) {
        checked.each(function (i) {
            if ($(this).val() == "on" || null) {

            } else {
                deleteObject[i] = ($(this).val());
            }
        });

        deleteObject[-1] = tagId;
        confirm('Удалить у выбранных песен тэг ' + tagName + '?');
    }
});

$('#deleteTagForSongsForm').on('submit', function (event) {

    if (jQuery.isEmptyObject(deleteObject)) {
        alert("Выберите хотя бы одну пеню, чтобы удалить тэг \"" + tagName + '\"');
        event.preventDefault();
        // return false;
    } else {
        $.ajax({
            url: '/api/admin/song/delete_tag_for_songs',
            data: JSON.stringify(deleteObject),
            contentType: "application/json;charset=UTF-8",
            method: 'DELETE',
            dataType: 'json',

            success: function () {
                deleteObject = [];
            },
            error: function () {
                deleteObject = [];
            },
        });
    }
});