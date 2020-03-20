$(document).ready(function () {
    getTable();

    let formAddEst = $("#establishmentsAddForm");
    let fieldNameAddEstName = $("#addName");
    $("#addEstablishmentBtn").on("click", function (event) {
        event.preventDefault();

        if (formAddEst.valid()) {
            addEstablishments(formAddEst, fieldNameAddEstName);
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

const establishmentsNameRegEx = /[\wА-Яа-я\-]/;


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


function addEstablishments(form, field) {
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
            getTable();
        },
        success: () => {
            notification(
                "add-establishment" + field.val(),
                ` Заведение ${field.val()} добавлено`,
                "establishments-panel");
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
                    getTable();
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
            getTable();
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


function getTable() {
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
            let tableBody = $("#establishmentsTable tbody");

            tableBody.empty();
            for (let i = 0; i < list.length; i++) {
                // vars that contains object's fields
                let id = list[i].id;
                let name = list[i].name;

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
