$(document).ready(function () {
    $('.money').mask('000.000.000.000.000,00', {reverse: true});

    getCompaniesTable();

    function getCompaniesTable() {
        $.ajax({
            type: 'GET',
            url: "/api/admin/all_companies",

            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            cache: false,
            dataType: 'JSON',
            success: function (listCompanies) {
                var htmlTable = "";
                $("#companiesTable tbody").empty();
                for (var i = 0; i < listCompanies.length; i++) {
                    let tariff = listCompanies[i].tariff + "";
                    if (listCompanies[i].userId === null){
                        listCompanies[i].userLogin = "не выбран";
                    }
                    if (listCompanies[i].tariff === null){
                        tariff = "не установлен";
                    } else {
                        let pennies = tariff.substr(tariff.length - 2, tariff.length);
                        tariff = tariff.substr(0, tariff.length - 2) + "," + pennies;
                        console.log(typeof tariff);
                    }
                    htmlTable += ('<tr id="listCompanies">');
                    htmlTable += ('<td id="tableCompaniesId">' + listCompanies[i].id + '</td>');
                    htmlTable += ('<td id="tableNameCompanies">' + listCompanies[i].name + '</td>');
                    htmlTable += ('<td id="tableStartTime">' + listCompanies[i].startTime + '</td>');
                    htmlTable += ('<td id="tableCloseTime">' + listCompanies[i].closeTime + '</td>');
                    htmlTable += ('<td id="tableOrgType">' + listCompanies[i].orgTypeName + '</td>');
                    htmlTable += ('<td id="tableUser">' + listCompanies[i].userLogin + '</td>');
                    htmlTable += ('<td id="tableTariff">' + tariff + '₽' + '</td>');
                    htmlTable += ('<td><button id="showEditModalCompaniesBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editCompany">изменить</button></td>');
                    // htmlTable += ('<td><button id="deleteUser" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    // htmlTable += ('</tr>');
                }
                $("#companiesTable tbody").append(htmlTable);
            }
        });
    };


    $("#editCompanyBtn").click(function (event) {
        event.preventDefault();
        if($("#company-form").valid()) {
            updateCompanyForm();
        }
    });

    function updateCompanyForm() {
        var companyDto = {
            id: $("#updateCompanyId").val(),
            name: $("#updateNameCompany").val(),
            startTime: $("#updateStartTime").val(),
            closeTime: $("#updateCloseTime").val(),
            tariff: $("#updateTariff").val().replace(/[^0-9]/g, ''),
            orgType: $("#updateOrgType").val(),
            userId: $("#updateIdUser").val()
        };


        $.ajax({
            type: 'POST',
            url: "/api/admin/company",
            contentType: 'application/json;',
            data: JSON.stringify(companyDto),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            success:
                function () {

                    // getCompaniesTable();
                    notification("edit-company" + companyDto.id,
                        "  Изменения компании c id " + companyDto.id + " сохранены",
                        'company-panel');
                    getCompaniesTable();
                    $("#tab-company-panel").tab('show');
                    $('#editCompany').modal('hide');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //modal company form заполнение
    $(document).on('click', '#showEditModalCompaniesBtn', function () {
        // $(this).trigger('form').reset();
        $('#updateCompanyId').val('');
        $('#updateNameCompany').val('');
        $('#updateStartTime').val('');
        $('#updateCloseTime').val('');

        $.ajax({
            url: "/api/admin/all_establishments",
            method: "GET",
            dataType: "json",
            success: function (data) {
                var selectBody = $('#updateOrgType');
                selectBody.empty();
                $(data).each(function (i, org) {
                    selectBody.append(`
                    <option value="${org.id}" >${org.name}</option>
                    `);
                })
            },
        });

        $.ajax({
            url: '/api/admin/companyById/' + $(this).closest("tr").find("#tableCompaniesId").text(),
            method: "GET",
            dataType: "json",
            success: function (companies) {
                $('#updateCompanyId').val(companies.id);
                $('#updateNameCompany').val(companies.name);
                $('#updateStartTime').val(companies.startTime);
                $('#updateCloseTime').val(companies.closeTime);
                $('#updateIdUser').val(companies.user.id);
                $('#updateTariff').val(companies.tariff);
                $("#updateOrgType option[value='" + companies.orgType.id + "'] ").prop("selected", true);
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        })
    });

    //добавление компании
    getOrgType();
    function addCompany(){

        var array = $('#addCompanyAddress').val().split(', ');
        var companyFormData = {
            name: $('#addCompanyName').val(),
            startTime: $('#addOpenTime').val(),
            closeTime: $('#addCloseTime').val(),
            orgType: $('#addOrgType').val(),
            tariff: $('#addTariffCompany').val(),
            userId: $("#addUserForCompany").val(),
            addressCountry: array[0],
            addressCity: array[1],
            addressStreet: array[2],
            addressHouse: array[3],
            addressLatitude: $('#latitude').val(),
            addressLongitude: $('#longitude').val()
        }

        $("#addOrgType option[value='" + companyFormData.orgType.id + "'] ").prop("selected", true);
        $.ajax({
            url: '/api/admin/add_company',
            type: "POST",
            async: true,
            cache: false,
            contentType: 'application/json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(companyFormData),

            success: function () {
                notification("add-company"+companyFormData.name,
                    "Компания " +companyFormData.name +" добавлена",
                    "company-panel")
                getCompaniesTable();
                $("#tab-company-panel").tab('show');
            },

            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                },
        })
    }

    $('#addCompanyAdminBtn').click( function (event) {
        event.preventDefault();
        if ($("#addCompany").valid()) {
            addCompany();
            $(':input', '#addCompany').val('');
        }
    })

    //карта
    ymaps.ready(init);
    function init() {
        // Подключаем поисковые подсказки к полю ввода.
        var suggestView = new ymaps.SuggestView('addCompanyAddress');
        var placemark;

        //Инициализируем карту
        var map = new ymaps.Map('mapAddCompanyAddress', {
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
                    $('#addCompanyAddress').val(alertContent);
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
            var request = $('#addCompanyAddress').val();

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

function getOrgType(){

    $.ajax({
        url: "/api/admin/all_establishments",
        method: "GET",
        dataType: "json",
        success: function (data) {
            var selectBody = $('#addOrgType');
            selectBody.empty();
            selectBody.append(`<option disabled selected value="">выберите тип заведения</option>`);
            $(data).each(function (i, org) {
                selectBody.append(`
                    <option value="${org.id}" >${org.name}</option>
                    `);
            })
        },
    });

}

$('#addCompany').validate({
    rules: {
        name: {
            required: true,
            pattern: /[a-zA-Z0-9-_.-]$/,
            remote: {
                url: "/api/registration/check/company",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    name: function () {
                        return $('#name').val()
                    },
                },
            }
        },
    },
    messages: {
        name: {
            remote: "Компания с таким именем существует",
            pattern: "Название компании может содержать только буквы, цифры, точки, тире и подчеркивание"
        },
        required: "Введите название"
    },
});

$('#company-form').validate({
    rules: {
        name: {
            remote: {
                url: "/api/registration/check/company",
                type: "GET",
                cache: false,
                dataType: "json",
                parameterData: {
                    name: function () {
                        return $('#company').val()
                    },
                },
            },
            required: true,
        },
    },
    messages: {
        name: {
            remote: "Компания с таким именем существует",
            required: "укажите название"
        }
    },
})

// //добавление адреса
// function addAddress() {
//     var array = $('#addCompanyAddress').val().split(', ');
//
//     var address = {
//         country: array[0],
//         city: array[1],
//         street: array[2],
//         house: array[3],
//         latitude: $('#latitude').val(),
//         longitude: $('#longitude').val()
//     };
//     $.ajax({
//         contentType: "application/json",
//         url: "/api/admin/company/address",
//         type: "POST",
//         async: false,
//         data: JSON.stringify(address)
//     });
// }
