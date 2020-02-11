$(document).ready(function () {
    getTable();
});

function getTable() {
    $.ajax({
        method: 'GET',
        url: "/api/admin/all_establishments",
        contentType: 'application/json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        cache: false,
        dataType: 'JSON',
        success: function (list) {
            let tableBody = $("#establishmentsTable tbody");

            tableBody.empty();
            for (let i = 0; i < list.length; i++) {
                // vars that contains object's fields
                let id = list[i].id;
                let name = list[i].name;
                // parsing fields
                let tr = $('<tr/>');
                tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="editEstBtn"
                                        onclick="editButton(${id}, '${name}')">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteEstBtn"
                                        onclick="deleteButton(${id})">
                                    Удалить
                                </button>
                            </td>`);
                tableBody.append(tr);
            }
        }
    });
}


// err messages text JSON-object for add/edit establishment's name
const errMessages = {
    required: "Укажите название",
    pattern: "Название может содержать: кирилицу, латиницу, цифры, тире. Минимальная длина - 2 символа.",
    remote: "Такой тип заведения уже существует"
};

// regex for establishment name
const establishmentsNameRegEx = /^[\wА-Яа-я\-]{2,20}$/; // A-Z, a-z, А-Я, а-я, 0-9, _


function addButton() {
    let form = $("#establishmentsAddForm");
    let field = $("#addName");

    form.validate({
        rules: {
            name: {
                required: true,
                pattern: establishmentsNameRegEx,
                remote: {
                    method: "GET",
                    url: "/api/admin/establishment/est_type_name_is_free",
                    cache: false,
                    dataType: "JSON",
                    parameterData: {
                        author: () => {
                            return field.val();
                        }
                    }
                }
            }
        },
        messages: {
            name: errMessages
        },
        submitHandler: () => {
            $.ajax({
                method: "POST",
                url: "/api/admin/add_establishment",
                contentType: 'application/json',
                data: JSON.stringify(field.val()),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                cache: false,
                complete: () => {
                    form.trigger('reset');
                    getTable();
                    $("#tab-establishments-panel").tab('show');
                },
                success: () => {
                    notification(
                        "add-establishment" + field.val(),
                        ` Заведение ${field.val()} добавлено`,
                        "establishments-panel");
                },
                error: (xhr, status, error) => {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
            })
        }
    })
}


function editButton(id, name) {
    let theModal = $('#editEstablishment');   // modal window's selector
    let form = $("#establishment-form");
    let field = $("#updateEstablishmentName");   // field "name" selector

    // autofill
    $("#updateEstablishmentId").val(id);
    field.val(name);
    // show the modal
    theModal.modal('show');
    // validation logic
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: establishmentsNameRegEx,
                remote: {
                    method: "GET",
                    url: "/api/admin/establishment/est_type_name_is_free",
                    cache: false,
                    dataType: "JSON",
                    parameterData: {
                        author: () => {
                            return field.val();
                        }
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
                url: "/api/admin/update_establishment",
                contentType: 'application/json',
                data: JSON.stringify({
                    id: id,
                    name: field.val()
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                cache: false,
                complete: () => {
                    getTable();
                    theModal.modal('hide');
                },
                success: () => {
                    notification(
                        "edit-establishment" + field.val(),
                        `  Изменения типа организации с id  ${id} сохранены`,
                        "establishments-panel");
                },
                error: (xhr, status, error) => {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
            })
        }
    })
}


function deleteButton(id) {
    $.ajax({
        method: "DELETE",
        url: "/api/admin/delete_establishment",
        contentType: 'application/json',
        data: JSON.stringify(id),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        cache: false,
        complete: () => {
            getTable();
        },
        success: () => {
            notification(
                "delete-establishment" + id,
                ` Заведение c id ${id} удалено`,
                "establishments-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    })
}
