let Music = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '                <H3>Музыка</H3>\n' +
            ' <H4>Плейлист</H4>\n' +
            '            </div>\n' +

            '<div class="col-fhd-4 col-xl-5 col-lg-6">\n' +
            '<ul class="nav nav-tabs nav-content " role="tablist">\n' +
            '<li id="morning-music-nav" class="active">\n' +
            '<a href="#morning" aria-controls="morning-music-nav" role="tab"\n' +
            'data-toggle="tab">\n' +
            '<h5>Утро</h5>8:00 - 12:00</a>\n' +
            '</li>\n' +
            '<li id="midday-music-nav">\n' +
            '<a href="#midday" aria-controls="midday-music-nav" role="tab"\n' +
            'data-toggle="tab">\n' +
            '<h5>День</h5>12:00 - 18:00</a>\n' +
            '</li>\n' +
            '<li id="evening-music-nav">\n' +
            '<a href="#evening" aria-controls="evening-music-nav" role="tab"\n' +
            'data-toggle="tab">\n' +
            '<h5>Вечер</h5>18:00 - 8:00</a>\n' +
            '</li>\n' +
            '</ul>\n' +

            '<div class="tab-content">\n' +
            '<div role="tabpanel" class="tab-pane active" id="morning">\n' +
            '<div class="panel panel-default table-panel">\n' +
            '<div class="tab-content">\n' +
            '<div class="panel-body">\n' +
            '<h3>Подборки утреннего плейлиста</h3>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '<div role="tabpanel" class="tab-pane" id="midday">\n' +
            '<div class="panel panel-default table-panel">\n' +
            '<div class="tab-content">\n' +
            '<div class="panel-body">\n' +
            '<h3>Подборки дневного плейлиста</h3>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '<div role="tabpanel" class="tab-pane" id="evening">\n' +
            '<div class="panel panel-default table-panel">\n' +
            '<div class="tab-content">\n' +
            'div class="panel-body">\n' +
            '<h3>Подборки вечернего плейлиста</h3>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '<!--подборка по жанрам-->\n' +
            '<div id="getGenres" class="col-fhd-8 col-xl-7 col-lg-6 row">\n' +
            '<button class="btn button bullet" id="allGenBtn" aria-pressed="false">Подборки по жанрам</button>\n' +
            '</div>\n'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {

    }
}


export default Music;