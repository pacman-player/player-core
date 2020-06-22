$(document).ready(function () {

    getMissedSteps();

    function getMissedSteps() {
        $.ajax({
            type: 'GET',
            url: "/api/registration/getMissedStepsNames",
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
                    case 'registration-step-user': //1:
                        break;
                    case 'registration-step-company': //2:
                        code += '<div class="container">';
                        code += '<div class="row">';
                        code += '<div class="col-md-6 col-md-offset-3">';
                        code += '<h1>Регистрация: 2/3</h1>';
                        code += '<form class="text-center" id="registrationCompanyForm">';

                        code += '<div class="form-group">';
                        code += '<label for="name" class="control-label">Имя компании</label>';
                        code += '<input type="text" id="name" class="form-control" placeholder="Введите имя компании" name="name" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="startTime" class="control-label">Время открытия</label>';
                        code += '<input type="time" id="startTime" class="form-control" name="startTime" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="closeTime" class="control-label">Время закрытия</label>';
                        code += '<input type="time" id="closeTime" class="form-control" name="closeTime" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="orgType" class="control-label">Тип организации</label>';
                        code += '<select class="form-control" name="orgType" id="orgType">';
                        code += '</select>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<button id="addCompanyBtn" class="btn btn-sm btn-info" type="button">Продолжить регистрацию</button>';
                        code += '</div>';
                        code += '</form>';
                        code += '</div>';
                        code += '</div>';
                        code += '</div>';
                        htmlPage.insertAdjacentHTML('afterEnd', code);
                        getAllOrgTypeForAdd();
                        break;

                    case 'registration-step-address': //3:
                        /*String country, String city, String street, String house, double latitude, double longitude*/
                        code += '<div class="container">';
                        code += '<div class="row">';
                        code += '<div class="col-md-6 col-md-offset-3">';
                        code += '<h1>Регистрация: 3/3</h1>';
                        code += '<form class="text-center" id="registrationAddressForm">';

                        code += '<div class="form-group">';
                        code += '<label for="country" class="control-label">Страна</label>';
                        code += '<input type="text" id="country" class="form-control" placeholder="Введите название страны" name="country" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="city" class="control-label">Город</label>';
                        code += '<input type="text" id="city" class="form-control" placeholder="Введите название города" name="city" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="street" class="control-label">Улица</label>';
                        code += '<input type="text" id="street" class="form-control" placeholder="Введите название улицы" name="street" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<label for="house" class="control-label">Дом</label>';
                        code += '<input type="text" id="house" class="form-control" placeholder="Введите дом" name="house" required="required"/>';
                        code += '</div>';

                        code += '<div class="form-group">';
                        code += '<button id="addAddressBtn" class="btn btn-sm btn-info" type="button">Продолжить регистрацию</button>';
                        code += '</div>';
                        code += '</form>';
                        code += '</div>';
                        code += '</div>';
                        code += '</div>';
                        htmlPage.insertAdjacentHTML('afterEnd', code);
                        break;
                    default:
                        //location.replace("user/statistics");
                        location.replace("/registration/end");
                }
            }
        });
    }

    function getAllOrgTypeForAdd() {
        //очищаю option
        $('#orgType').empty();
        var genreForAdd = '';
        $.getJSON("/api/registration/get_all_orgTypes", function (data) {
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
        if ($('#registrationCompanyForm').valid()) {
            addCompanyForm();
        }
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
            url: "/api/registration/company",
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

    $('#registrationCompanyForm').validate({
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

    //добавляем новый адрес POST в БД по кнопге регистрация
    $(document).on('click', '#addAddressBtn', function () {
        if ($('#registrationAddressForm').valid()) {
            addAddressForm();
        }
    });

    function addAddressForm() {
        let address = {
            country: $("#country").val(),
            city: $("#city").val(),
            street: $("#street").val(),
            house: $("#house").val(),
        };

        $.ajax({
            type: 'POST',
            url: "/api/registration/address",
            contentType: 'application/json;',
            data: JSON.stringify(address),
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


});
