$(document).ready(function () {
    // add map
    ymaps.ready(init);
    function init() {
        var placemark;

        var map = new ymaps.Map('map', {
            center: [55.753902, 37.620074],
            zoom: 12,
            controls: []
        });


        // Создаем экземпляр класса ymaps.control.SearchControl
        mySearchControl = new ymaps.control.SearchControl({
        });

        // Создаем экземпляр класса ymaps.control.ZoomControl
        myZoomControl = new ymaps.control.ZoomControl({
        });

        map.controls.add(mySearchControl);
        map.controls.add(myZoomControl);

        let alertContent;


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




                alertContent = firstGeoObject.getAddressLine();

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
    }
});