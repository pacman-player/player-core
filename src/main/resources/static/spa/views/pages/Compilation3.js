

let Compilation3 = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '\n' +
            '                <H3> Мой плейлист</H3>\n' +
            '                <div class="col-fhd-4 col-xl-5 col-lg-6">\n' +
            '                    <ul class="nav nav-tabs nav-content " role="tablist">\n' +
            '                        <li id="morning-music-nav" class="active">\n' +
            '                            <a href="#morning" aria-controls="morning-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>Утро</h5>8:00 - 12:00</a>\n' +
            '                        </li>\n' +
            '                        <li id="midday-music-nav">\n' +
            '                            <a href="#midday" aria-controls="midday-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>День</h5>12:00 - 18:00</a>\n' +
            '                        </li>\n' +
            '                        <li id="evening-music-nav">\n' +
            '                            <a href="#evening" aria-controls="evening-music-nav" role="tab" data-toggle="tab">\n' +
            '                                <h5>Вечер</h5>18:00 - 8:00</a>\n' +
            '                        </li>\n' +
            '                    </ul>\n' +
            '\n' +
            '                    <div class="tab-content">\n' +
            '                        <div role="tabpanel" class="tab-pane active" id="morning">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки утреннего плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div role="tabpanel" class="tab-pane" id="midday">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки дневного плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div role="tabpanel" class="tab-pane" id="evening">\n' +
            '                            <div class="panel panel-default table-panel">\n' +
            '                                <div class="tab-content">\n' +
            '                                    <div class="panel-body">\n' +
            '                                        <h3>Подборки вечернего плейлиста</h3>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <!--подборка по жанрам-->\n' +
            '                <div id="getGenres" class="col-fhd-8 col-xl-7 col-lg-6 row"></div>\n' +
            '            </div>\n' +
            '        </div>' +
            '<div class="modal fade" id="currentPlaylist">\n' +
            '        <div class="modal-dialog">\n' +
            '            <div class="modal-content">\n' +
            '                <div class="modal-header text-center">\n' +
            '                    <button class="close" data-dismiss="modal">&times;</button>\n' +
            '                    <h4>Current playlist</h4>\n' +
            '                </div>\n' +
            '                <div class="modal-body">\n' +
            '                    <table class="table table-condensed table-hover table-striped table-responsive">\n' +
            '                        <thead>\n' +
            '                        <tr>\n' +
            '                            <th>Musics title</th>\n' +
            '                            <th>Author</th>\n' +
            '                            <th>Genre</th>\n' +
            '                            <th style="width: 20%">Options</th>\n' +
            '                        </tr>\n' +
            '                        </thead>\n' +
            '                        <tbody id="modalCurrentPlaylistTableBody"></tbody>\n' +
            '                    </table>\n' +
            '                </div>\n' +
            '                <div class="modal-footer">\n' +
            '                    <button class="btn" data-dismiss="modal">Close</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="modal fade" id="modalPlaylist">\n' +
            '        <div class="modal-dialog">\n' +
            '            <div class="modal-content">\n' +
            '                <div class="modal-header">\n' +
            '                    <button class="close" data-dismiss="modal">&times;</button>\n' +
            '                    <h4 id="modalPlaylistName"></h4>\n' +
            '                </div>\n' +
            '                <div class="modal-body">\n' +
            '                    <table class="table table-condensed table-hover table-striped table-responsive">\n' +
            '                        <thead>\n' +
            '                        <tr>\n' +
            '                            <th>Musics title</th>\n' +
            '                            <th>Author</th>\n' +
            '                            <th>Genre</th>\n' +
            '                            <th style="width: 20%">Options</th>\n' +
            '                        </tr>\n' +
            '                        </thead>\n' +
            '                        <tbody id="modalPlaylistTableBody"></tbody>\n' +
            '                    </table>\n' +
            '                </div>\n' +
            '                <div class="modal-footer">\n' +
            '                    <button class="btn" data-dismiss="modal">Close</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {



    }
}
export default Compilation3;