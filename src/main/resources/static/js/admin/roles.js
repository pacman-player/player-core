$(document).ready(function () {
    getTable();

    let formAddEst = $("#rolesAddForm");
    let fieldNameAddEstName = $("#addName");
    $("#addroleBtn").on("click", function (event) {
        event.preventDefault();

        if (formAddEst.valid()) {
            addroles(formAddEst, fieldNameAddEstName);
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

const rolesNameRegEx = /[\wА-Яа-я\-]/;


$("#rolesAddForm").validate({
    rules: {
        name: {
            required: true,
            pattern: rolesNameRegEx,
            rangelength: [3, 30],
            remote: {
                method: "GET",
                url: "/api/admin/role/est_type_name_is_free",
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


function addroles(form, field) {
    $.ajax({
        method: "POST",
        url: "/api/admin/add_role",
        contentType: "application/json",
        data: JSON.stringify(field.val()),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        complete: () => {
            $("#tab-roles-panel").tab("show");
            getTable();
        },
        success: () => {
            notification(
                "add-role" + field.val(),
                ` Роль ${field.val()} добавлено`,
                "roles-panel");
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
}


function editButton(id, name) {
    let theModal = $("#editrole");
    let form = $("#updateEstForm");
    let fieldId = $("#updateroleId");
    let fieldName = $("#updateroleName");

    fieldId.val(id);
    fieldName.val(name);

    theModal.modal("show");
    form.validate({
        rules: {
            name: {
                required: true,
                pattern: rolesNameRegEx,
                rangelength: [3, 30],
                remote: {
                    method: "GET",
                    url: "/api/admin/role/est_type_name_is_free",
                }
            }
        },
        messages: {
            name: errMessages
        },
        submitHandler: () => {
            $.ajax({
                method: "PUT",
                url: "/api/admin/update_role",
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
                        "edit-role" + fieldName.val(),
                        `  Изменения типа роли с id  ${fieldId.val()} сохранены`,
                        "roles-panel");
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
        url: "/api/admin/delete_role",
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
                "delete-role" + id,
                ` Роль c id ${id} удалена`,
                "roles-panel");
        },
        error: (xhr, status, error) => {
            if (xhr.responseText.includes("DataIntegrityViolationException")) {
                let caution = "Вы не можете удалить данный тип роли, т.к. к нему относятся один или несколько пользователей";
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
        url: "/api/admin/all_roles",
        contentType: "application/json",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        dataType: "JSON",
        success: function (list) {
            let tableBody = $("#rolesTable tbody");

            tableBody.empty();
            for (let i = 0; i < list.data.length; i++) {
                // vars that contains object's fields
                let id = list.data[i].id;
                let name = list.data[i].name;

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
