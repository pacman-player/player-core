$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();
    getCompanyData();

    function updateCompany() {
        var formData = {
            name: $('#est-name').val(),
            startTime: $('#est-start-time').val(),
            closeTime: $('#est-close-time').val()
        };
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
                    notification("edit-company-data" + formData.name.replace(/[^\w]|_/g, ''),
                        "  Изменения сохранены");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
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
            }
        })
    }

    $('#est-save-data').click(function () {
        updateCompany();
    });

    function showLinkAdmin() {
        $.ajax({
            type: "post",
            url: "/api/user/show_admin",
            success: function (role) {
                if (role !== "admin") {
                    $("#adminLink").hide();
                }
            }
        });
    }

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
                confirm("You pushed on map : " + alertContent);


                $.ajax({
                    type: "post",
                    url: "/user/company",
                    data: alertContent,
                    contentType: "application/json; charset=utf-8",
                    cache: false,
                    async: true
                });

                console.log(alertContent);

            });
        }
    }
});

