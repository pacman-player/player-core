$(document).ready(function () {
    getEstablishments();

    let formAddEst = $("#establishmentsAddForm");
    let fieldNameAddEstName = $("#addName");
    $("#addEstablishmentBtn").on("click", function (event) {
        event.preventDefault();

        if (formAddEst.valid()) {
            addEstablishment(formAddEst, fieldNameAddEstName);
            formAddEst.trigger("reset");
        }
    });

    $('#setDefaultEstablishmentBtn').on('click', (event) => {
       event.preventDefault();

        let option = $('#defaultEstablishmentSelect :selected');

        /* Если заведение уже устанавлено по умолчанию - не делаем AJAX запрос */
        if (option.data('default') === true) {
            return;
        }

        let id = option.val();
        setDefaultEstablishment(id);
    });

});


const errMessages = {
    required: "Укажите название",
    pattern: "Разрешенные символы: кирилица, латиница, цифры, тире",
    rangelength: "Количество символов должно быть в диапазоне [3-30]",
    remote: "Имя занято"
};

const establishmentsNameRegEx = /^[\wА-Яа-я\-]+$/;


$("#establishmentsAddForm").validate({
    rules: {
        name: {
            required: true,
            pattern: establishmentsNameRegEx,
            rangelength: [3, 30],
            remote: {
                method: "GET",
                url: "/api/admin/establishment/est_type_name_is_free",
                data: {
                    name: function () {
                        return $("#addName").val()
                    },
                }
            }
        }
    },
    messages: {
        name: errMessages
    }
});


function setDefaultEstablishment(id) {
    $.ajax({
        method: "POST",
        url: "/api/admin/set_default_establishment",
        data: JSON.stringify(id),
        dataType: 'text',
        headers: {
            "Accept": "text/plain",
            "Content-Type": "application/json",
        },
        complete: () => {
            $("#tab-establishments-panel").tab("show");
            getEstablishments();
        },
        success: (message) => {
            notification("set-default-establishment-" + id, message, "establishments-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    });
}


function addEstablishment(form, field) {
    let estMessage = field.val();
    $.ajax({
        method: "POST",
        url: "/api/admin/add_establishment",
        contentType: "application/json",
        data: JSON.stringify(field.val()),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        complete: () => {
            $("#tab-establishments-panel").tab("show");
            getEstablishments();
        },
        success: () => {
            notification(
                "add-establishment" + estMessage,
                ` Заведение ${estMessage} добавлено`,
                "establishments-panel"
            );
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name, isDefault) {
    let theModal = $("#editEstablishment");
    let form = $("#updateEstForm");
    let fieldId = $("#updateEstablishmentId");
    let fieldName = $("#updateEstablishmentName");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");

    form.validate({
        rules: {
            name: {
                required: true,
                pattern: establishmentsNameRegEx,
                rangelength: [3, 30],
                remote: {
                    method: "GET",
                    url: "/api/admin/establishment/est_type_name_is_free",
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
                contentType: "application/json",
                data: JSON.stringify({
                    id: fieldId.val(),
                    name: fieldName.val(),
                    default: isDefault
                }),
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json",
                },
                complete: () => {
                    theModal.modal("hide");
                    getEstablishments();
                },
                success: () => {
                    notification(
                        "edit-establishment" + fieldName.val(),
                        `  Изменения типа организации с id  ${fieldId.val()} сохранены`,
                        "establishments-panel");
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
        url: "/api/admin/delete_establishment",
        data: JSON.stringify(id),
        dataType: 'text',
        headers: {
            "Accept": "text/plain",
            "Content-Type": "application/json",
        },
        complete: () => {
            getEstablishments();
        },
        success: (message) => {
            notification("delete-establishment" + id, message, "establishments-panel");
        },
        error: (xhr, status, error) => {
            if (xhr.status === 403) {
                alert(xhr.responseText);
            } else {
                alert(xhr.responseText + "|\n" + status + "|\n" + error);
            }
        }
    })
}


function getEstablishments() {
    $.ajax({
        method: "GET",
        url: "/api/admin/all_establishments",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        dataType: "JSON",
        success: function (list) {
            fillEstablishmentsTable(list);
            fillDefaultEstablishmentSelect(list);
        }
    });
}


function fillEstablishmentsTable(list) {
    let tableBody = $("#establishmentsTable tbody");
    tableBody.empty();
    
    for (let i = 0; i < list.length; i++) {
        let id = list[i].id;
        let name = list[i].name;
        let isDefault = list[i].default;

        let tr = $("<tr/>");
        tr.append(`
                            <td> ${id} </td>
                            <td> ${name} </td>
                            <td>
                                <button type="submit" 
                                        class="btn btn-sm btn-info" 
                                        id="editEstBtn"
                                        onclick="editButton(${id}, '${name}', ${isDefault})">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteEstBtn" ` +
                                        (isDefault === true ? `disabled title="Нельзя удалить тип по умолчанию" ` : ``) +
                                        `onclick="deleteButton(${id})">
                                    Удалить
                                </button>
                            </td>`);
        tableBody.append(tr);
    }
}

function fillDefaultEstablishmentSelect(list) {
    let defaultEstablishmentSelect = $('#defaultEstablishmentSelect');
    defaultEstablishmentSelect.empty();

    for (let i = 0; i < list.length; i++) {
        let id = list[i].id;
        let name = list[i].name;
        let isDefault = list[i].default;

        let option = isDefault === true ? `<option value="${id}" data-default="true" selected>${name}</option>` :
                                          `<option value="${id}" data-default="false">${name}</option>`;
        defaultEstablishmentSelect.append(option);
    }
}