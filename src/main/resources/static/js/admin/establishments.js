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
    })
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

$('#setDefaultEstablishmentBtn').on('click', function (event) {
    event.preventDefault();

    let option = $('#defaultEstablishmentSelect :selected');
    let id = option.val();
    let name = option.text();
    let isDefault = option.data('default');

    if (isDefault === true) {
        return;
    }

    let establishment = {};
    establishment['id'] = id;
    establishment['name'] = name;
    establishment['default'] = isDefault;

    $.ajax({
        method: "POST",
        url: "/api/admin/set_default_establishment",
        data: JSON.stringify(establishment),
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        success: () => {
            alert('SUCCESS!!!');
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    });
});


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


function editButton(id, name) {
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
                    name: fieldName.val()
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
        contentType: "application/json",
        data: JSON.stringify(id),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        complete: () => {
            getEstablishments();
        },
        success: () => {
            notification(
                "delete-establishment" + id,
                ` Заведение c id ${id} удалено`,
                "establishments-panel");
        },
        error: (xhr, status, error) => {
            if (xhr.responseText.includes("DataIntegrityViolationException")) {
                let caution = "Вы не можете удалить данный тип заведения, т.к. к нему относятся одна или несколько компаний";
                alert(caution);
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
                                        onclick="editButton(${id}, '${name}')">
                                    Изменить
                                </button>
                            </td>
                            <td>
                                <button type="button"
                                        class="btn btn-sm btn-info"
                                        id="deleteEstBtn" ` +
                                        (isDefault === true ? `disabled ` : ``) +
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