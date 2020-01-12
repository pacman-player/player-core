$(document).ready(function () {
    ymaps.ready(init);
    function init() {
        // Подключаем поисковые подсказки к полю ввода.
        var suggestView = new ymaps.SuggestView('suggest');

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





                let alertContent = firstGeoObject.getAddressLine();

                function putAddressInField () {
                    $('#suggest').val(alertContent);
                }

                if (confirm("You pushed on map : " + alertContent)) {
                    putAddressInField();

                    console.log(alertContent);
                } else {
                    console.log("failed");
                }
            });
        }


        // При клике по кнопке запускаем верификацию введёных данных.
        $('#button').bind('click', function () {
            event.preventDefault();

            // Забираем запрос из поля ввода.
            var request = $('#suggest').val();

            // Поиск координат центра Нижнего Новгорода.
            ymaps.geocode(request, {
                results: 1
            }).then(function (res) {
                // Выбираем первый результат геокодирования.
                var firstGeoObject = res.geoObjects.get(0);

                // Координаты геообъекта.
                var coords = firstGeoObject.geometry.getCoordinates();

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