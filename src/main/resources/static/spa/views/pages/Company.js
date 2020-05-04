
let Company = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div class="col-lg-6 col-md-10 col-xs-10 " id="my-company">\n' +
            '                <H3> Моё заведение</H3>\n' +
            '                <form class="form-group">\n' +
            '                    <label for="est-name">Наименование организации</label>\n' +
            '                    <input id="est-name" required="required" type="text"/><br/>\n' +
            '                    <label for="est-start-time">Время открытия&nbsp;</label>\n' +
            '                    <input id="est-start-time" required="required" type="time"/><br/>\n' +
            '                    <label for="est-close-time">Время закрытия&nbsp;</label>\n' +
            '                    <input id="est-close-time" required="required" type="time"/><br/>\n' +
            '                    <label for="est-tariff">Тариф за песню&nbsp;</label>\n' +
            '                    <input id="est-tariff" class="money" required="required" type="text"/><br/>\n' +
            '                    <label for="est-address">Адрес&nbsp;</label>\n' +
            '                    <input style="width: 700px" id="est-address" type="text" placeholder="Введите адрес, или выберите дом на карте"/>\n' +
            '                    <button type="submit" id="button">Поиск</button>\n' +
            '                    <br/>\n' +
            '                    <input id="latitude" type="hidden"/>\n' +
            '                    <input id="longitude" type="hidden"/>\n' +
            '                    <div id="map" style="width: 800px; height: 400px"></div><br/>\n' +
            '                    <button class="btn btn-primary" style="margin-left:0" id="est-save-data" type="button">\n' +
            '                        Сохранить\n' +
            '                    </button>\n' +
            '                    <div id="notify"></div>\n' +
            '                </form>\n' +
            '            </div>\n' +
            '        </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
        $(document).ready(function () {

            // //доступ к  ссылки админа
            // showLinkAdmin();
            getCompanyData();
            // getCompanyAddress();

            function updateCompany() {
                let formData = {
                    name: $('#est-name').val(),
                    startTime: $('#est-start-time').val(),
                    tariff: $('#est-tariff').val().replace(/[^0-9]/g, ''),
                    closeTime: $('#est-close-time').val()
                };
                console.log(formData.tariff);
                $.ajax({
                    contentType: "application/json;",
                    url: "/api/user/company",
                    type: "PUT",
                    data: JSON.stringify(formData),
                    complete:
                        function () {
                            getCompanyData();
                        },
                    success:
                        function () {
                        console.log(formData.tariff);
                            notification("edit-company-data" + formData.name.replace(/[^\w]|_/g, ''),
                                "  Изменения сохранены");
                        },
                    error:
                        function (xhr, status, error) {
                            alert(xhr.responseText + '|\n' + status + '|\n' + error);
                        }
                });
            }


            function updateAddress() {
                var array = $('#est-address').val().split(', ');

                var address = {
                    country: array[0],
                    city: array[1],
                    street: array[2],
                    house: array[3],
                    latitude: $('#latitude').val(),
                    longitude: $('#longitude').val()
                };
                $.ajax({
                    contentType: "application/json;",
                    url: "/api/user/company/address",
                    type: "PUT",
                    data: JSON.stringify(address)
                });
            }

            function getCompanyData() {
                $.ajax({
                    url: '/api/user/company/',
                    method: "GET",
                    dataType: "json",
                    success: function (data) {
                        $('#est-name').val(data.name);
                        $('#est-start-time').val(data.startTime);
                        $('#est-close-time').val(data.closeTime);
                        $('#est-tariff').val(data.tariff);
                        $('#est-address').val(data.addressCountry + ', ' + data.addressCity + ', ' + data.addressStreet + ', ' + data.addressHouse);
                    }
                })
            }

            // function getCompanyAddress() {
            //     $.ajax({
            //         url: '/api/user/company/address',
            //         method: "GET",
            //         dataType: "json",
            //         success: function (data) {
            //             $('#est-address').val(data.address.country + ', ' + data.address.city + ', ' + data.address.street + ', ' + data.address.house);
            //         }
            //     })
            // }

            $('#est-save-data').click(function () {
                updateCompany();
                updateAddress();
            });

            // function showLinkAdmin() {
            //     $.ajax({
            //         type: "post",
            //         url: "/api/user/show_admin",
            //         success: function (role) {
            //             if (role !== "admin") {
            //                 $("#adminLink").hide();
            //             }
            //         }
            //     });
            // }

            function notification(notifyId, message) {
                let notify = document.getElementById('notify');
                notify.innerHTML =
                    '<div class="alert alert-success notify alert-dismissible"' +
                    'role="alert" hidden="true" id="success-alert-' + notifyId + '">' +
                    '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' + message +
                    '</div>';
                $('#success-alert-' + notifyId).fadeIn(300, "linear");
                setTimeout(() => {
                    $('#success-alert-' + notifyId).fadeOut(400, "linear", $(this).remove());
                }, 2000);
            }



            ymaps.ready(init);
            function init() {
                // Подключаем поисковые подсказки к полю ввода.
                var suggestView = new ymaps.SuggestView('est-address');
                var placemark;

                //Инициализируем карту
                var map = new ymaps.Map('map', {
                    center: [55.753902, 37.620074],
                    zoom: 12,
                    controls: []
                });

                // Создаем экземпляр класса ymaps.control.SearchControl
                let mySearchControl = new ymaps.control.SearchControl({
                });

                // Создаем экземпляр класса ymaps.control.ZoomControl
                let myZoomControl = new ymaps.control.ZoomControl({
                });

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
                        function putAddressInField () {
                            $('#est-address').val(alertContent);
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
                $('#button').bind('click', function () {
                    event.preventDefault();

                    // Забираем запрос из поля ввода.
                    var request = $('#est-address').val();

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

        $('.money').mask('000.000.000.000.000,00', {reverse: true});
    }

};


export default Company;