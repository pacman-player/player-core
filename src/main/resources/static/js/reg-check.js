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

                        code += '<div class="form-group text-center edit-form">'
                        code += '<label for="companyAddress">Адрес</label>'
                        code += '<input id="companyAddress" type="text" placeholder="Введите адрес, или выберите дом на карте" class="form-control"/>'
                        code += '</div>'
                        code += '<br/>'
                        code += '<button type="submit" id="buttonFindAddress">Поиск</button>'
                        code += '<br/>'
                        code += '<br/>'
                        code += '<input id="latitude" type="hidden"/>'
                        code += '<input id="longitude" type="hidden"/>'
                        code += '<div id="mapCompanyAddress" style="width: auto; height: 400px"></div><br/>'

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
        var array = $('#companyAddress').val().split(', ');

        let address = {
            country: array[0],
            city: array[1],
            street: array[2],
            house: array[3],
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

    //карта
    ymaps.ready(init);
    function init() {
        // Подключаем поисковые подсказки к полю ввода.
        var suggestView = new ymaps.SuggestView('companyAddress');
        var placemark;

        //Инициализируем карту
        var map = new ymaps.Map('mapCompanyAddress', {
            center: [55.753902, 37.620074],
            zoom: 12,
            controls: []
        });

        // Создаем экземпляр класса ymaps.control.SearchControl
        let mySearchControl = new ymaps.control.SearchControl({});

        // Создаем экземпляр класса ymaps.control.ZoomControl
        let myZoomControl = new ymaps.control.ZoomControl({});

        //Добавляем в карту зум
        // map.controls.add(mySearchControl);
        map.controls.add(myZoomControl);

        // Слушаем клик на карте.
        map.events.add('click', function (e) {
            var coords = e.get('coords');

            // Если метка уже создана – просто передвигаем ее.
            if (placemark) {
                placemark.geometry.setCoordinates(coords);
            }
            // Если нет – создаем.
            else {
                placemark = createPlacemark(coords);
                map.geoObjects.add(placemark);
                // Слушаем событие окончания перетаскивания на метке.
                placemark.events.add('dragend', function () {
                    getAddress(placemark.geometry.getCoordinates());
                });
            }
            getAddress(coords);
        });

        // Создание метки.
        function createPlacemark(coords) {
            return new ymaps.Placemark(coords, {
                iconCaption: 'поиск...'
            }, {
                preset: 'islands#violetDotIconWithCaption',
                draggable: true
            });
        }

        // Определяем адрес по координатам (обратное геокодирование).
        function getAddress(coords) {
            placemark.properties.set('iconCaption', 'поиск...');
            ymaps.geocode(coords).then(function (res) {
                var firstGeoObject = res.geoObjects.get(0);

                placemark.properties.set({
                    // Формируем строку с данными об объекте.
                    iconCaption: [
                        // Название населенного пункта или вышестоящее административно-территориальное образование.
                        firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
                        // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
                        firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
                    ].filter(Boolean).join(', '),
                    // В качестве контента балуна задаем строку с адресом объекта.
                    balloonContent: firstGeoObject.getAddressLine()
                });


                var alertContent = firstGeoObject.getAddressLine();

                // Сетим адрес и координаты в поля
                function putAddressInField() {
                    $('#companyAddress').val(alertContent);
                    $('#latitude').val(coords[0]);
                    $('#longitude').val(coords[1]);
                }

                // Сетим в поля после конфирма
                if (confirm("You pushed on map : " + alertContent)) {
                    putAddressInField();
                } else {
                    console.log("cancelled");
                }
            });
        }


        // При клике по кнопке запускаем верификацию введёных данных.
        $('#buttonFindAddress').bind('click', function () {
            event.preventDefault();

            // Забираем запрос из поля ввода.
            var request = $('#companyAddress').val();

            // Поиск координат центра Нижнего Новгорода.
            ymaps.geocode(request, {
                results: 1
            }).then(function (res) {
                // Выбираем первый результат геокодирования.
                var firstGeoObject = res.geoObjects.get(0);

                // Координаты геообъекта.
                var coords = firstGeoObject.geometry.getCoordinates();
                $('#latitude').val(coords[0]);
                $('#longitude').val(coords[1]);

                // Область видимости геообъекта.
                var bounds = firstGeoObject.properties.get('boundedBy');

                firstGeoObject.options.set('preset', 'islands#darkBlueDotIconWithCaption');

                // Получаем строку с адресом и выводим в иконке геообъекта.
                firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());

                // Добавляем первый найденный геообъект на карту.
                map.geoObjects.add(firstGeoObject);

                // Масштабируем карту на область видимости геообъекта.
                map.setBounds(bounds, {
                    // Проверяем наличие тайлов на данном масштабе.
                    checkZoomRange: true
                });
            });
        });
    }
});
