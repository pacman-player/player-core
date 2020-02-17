$(document).ready(function () {

    getMissedSteps();

    function getMissedSteps() {
        $.ajax({
            type: 'GET',
            url: "/api/user/get_missed_steps",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            cache: false,
            dataType: 'JSON',
            success: function (listSteps) {
                var code = '';
                var htmlPage = document.getElementById('htmlPage');
                switch (listSteps[0]) {
                    case 1:

                        break;
                    case 2:
                        code += '<div class="container">'
                        code += '<div class="row">'
                        code += '<div class="col-md-6 col-md-offset-3">'
                        code += '<h1>Регистрация: 2/2</h1>'
                        code += '<form class="text-center" id="registrationSecondForm">'
                        code += '<input type="hidden" id="login" name="login" th:value="${login}"/>'
                        code += '<div class="form-group">'
                        code += '<label for="name" class="control-label">Имя компании</label>'
                        code += '<input type="text" id="name" class="form-control" placeholder="Введите имя компании" name="name" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="startTime" class="control-label">Время открытия</label>'
                        code += '<input type="time" id="startTime" class="form-control" name="startTime" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="closeTime" class="control-label">Время закрытия</label>'
                        code += '<input type="time" id="closeTime" class="form-control" name="closeTime" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="orgType" class="control-label">Тип организации</label>'
                        code += '<select class="form-control" name="orgType" id="orgType">'
                        code += '</select>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<button id="addCompanyBtn" class="btn btn-sm btn-info" type="button">Продолжить регистрацию</button>'
                        code += '</div>'
                        code += '</form>'
                        code += '</div>'
                        code += '</div>'
                        code += '</div>'
                        htmlPage.insertAdjacentHTML('afterEnd', code);
                        getAllOrgTypeForAdd();
                        break;
                    case 3:
                        document.write("x равен 3");
                        break;
                    default:
                        location.replace("user/statistics");
                }
            }
        });
    }

    //получаем все типы заведений песни из БД на выбор
    getAllOrgTypeForAdd();

    function getAllOrgTypeForAdd() {
        //очищаю option
        $('#orgType').empty();
        var genreForAdd = '';
        $.getJSON("/api/user/orgType/get_all_orgType", function (data) {
            $.each(data, function (key, value) {
                /*<option th:value="${orgType.id}" th:text="${orgType.name}"></option>*/
                genreForAdd += '<option ';
                genreForAdd += ' value="' + value.id + '">' + value.name + '</option>';
            });
            $('#orgType').append(genreForAdd);
        });
    }


    //добавляем новую компанию POST в БД по кнопге регистрация
    $(document).on('click', '#addCompanyBtn', function () {
        if ($('#registrationSecondForm').valid()) {
            addCompanyForm();
            /*$.post("/api/registration/second", registrationSecondForm.serialize(), function () {
                location.assign(location.origin + "/reg_check");
            });*/
        }
        //addCompanyForm();
    });

    function addCompanyForm() {
        var company = {
            name: $("#name").val(),
            startTime: $("#startTime").val(),
            closeTime: $("#closeTime").val(),
            orgType: $("#orgType").val(),
        };

        $.ajax({
            type: 'POST',
            url: "/api/registration/second",
            contentType: 'application/json;',
            data: JSON.stringify(company),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            success: function () {
                window.location.reload();
            }
        });


    }

    $('#registrationSecondForm').validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                remote: {
                    url: "/api/registration/check/company",
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    parameterData: {
                        name: function () {
                            return $("#name").val();
                        }
                    },
                }
            },
            startTime: {
                required: true
            },
            closeTime: {
                required: true
            },
            orgType: {
                required: true
            }
        },
        messages: {
            name: {
                required: "Введите название компании",
                minlength: "Название должно содержать минимум 3 символа",
                remote: "Компания с таким именем уже зарегистрирована"
            },
            startTime: {
                required: "Укажите время открытия"
            },
            closeTime: {
                required: "Укажите время закрытия"
            },
            orgType: {
                required: "Выберите тип заведения"
            }
        }
    });


});
