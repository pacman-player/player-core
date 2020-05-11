let Statistics = {
    // <h1>SPA</h1>
    render: async () => {


        return /*html*/ '        <!--центральный блок-->\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-7 col-md-6 col-xs-7 " id="my-selection">\n' +
            '                <H3> Статистика</H3>\n' +
            '<div class="container">\n' +
            '    <div class="row">\n' +
            '        <div class="col-md-5 ">\n' +
            '            <div class="layer">\n' +
            '                <div id="js-textSongDelete" class="js-textSongDelete">\n' +
            '                    <div>\n' +

            '                    </div>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>    <!--     текст   песни-->\n' +
            '        <div class="col-md-2">\n' +
            '            <div class="layer">\n' +
            '                <div class="btn-group-vertical">\n' +
            '                    <div aria-label="Vertical button group" class="btn-group-vertical" role="group">\n' +
            '                        <button class="btn btn-light js-byDay js-numberOfList" value="1" type="button">топ за день</button>\n' +
            '                        <button class="btn btn-light js-byWeek js-numberOfList" value="2" type="button">топ за неделю </button>\n' +
            '                        <button class="btn btn-light js-byMonth js-numberOfList" value="3" type="button">топ за месяц </button>\n' +
            '                        <button class="btn btn-light js-byYear js-numberOfList" value="4" type="button">топ за год</button>\n' +
            '                    </div><!--        кнопки для выбора периода-->\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div><!--        top songs-->\n' +
            '\n' +
            '\n' +
            '<div aria-hidden="true" aria-labelledby="exampleModalLabel" class="modal fade" id="myModal" role="dialog"\n' +
            '     tabindex="-1">\n' +
            '    <div class="modal-dialog" role="document">\n' +
            '        <div class="modal-content">\n' +
            '            <div class="modal-header">\n' +
            '                <h5 class="modal-title" id="exampleModalLabel">График</h5>\n' +
            '                <button aria-label="Close" class="close" data-dismiss="modal" type="button">\n' +
            '                    <span aria-hidden="true">&times;</span>\n' +
            '                </button>\n' +
            '            </div>\n' +
            '            <div class="js-modal-body" id="js-modal-body">\n' +
            '            </div>\n' +
            '\n' +
            '            <div class="modal-footer">\n' +
            '                <button class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>\n' +
            '<!--   modal window -->'+
            '            </div>\n' +
            '        <div class="container col-lg-4 col-md-4 col-xs-4 col-xl-4" id="right-side-bar">\n' +
            '                <table class="table bg-white stats-table">\n' +
            '                    <thead>\n' +
            '                    <tr>\n' +
            '                        <th class="orders-title">Заказы</th>\n' +
            '                        <th class="orders-type-free text-right small text-secondary">Бесплатных</th>\n' +
            '                        <th class="orders-type-total text-right">Всего</th>\n' +
            '                    </tr>\n' +
            '                    </thead>\n' +
            '                    <tbody>\n' +
            '                    <tr>\n' +
            '                        <td class="orders-today-label">За сегодня</td>\n' +
            '                        <td class="orders-today-free text-right text-secondary"><span>0</span></td>\n' +
            '                        <td class="orders-today-total text-right"><span>0</span></td>\n' +
            '                    </tr>\n' +
            '                    <tr>\n' +
            '                        <td class="orders-yesterday-label">За вчера</td>\n' +
            '                        <td class="orders-yesterday-free text-right text-secondary"><span>0</span></td>\n' +
            '                        <td class="orders-yesterday-total text-right"><span>0</span></td>\n' +
            '                    </tr>\n' +
            '                    <tr>\n' +
            '                        <td class="orders-week-label">За неделю</td>\n' +
            '                        <td class="orders-week-free text-right text-secondary"><span>0</span></td>\n' +
            '                        <td class="orders-week-total text-right"><span>0</span></td>\n' +
            '                    </tr>\n' +
            '                    <tr>\n' +
            '                        <td class="orders-month-label">За месяц</td>\n' +
            '                        <td class="orders-month-free text-right text-secondary"><span>0</span></td>\n' +
            '                        <td class="orders-month-total text-right"><span>0</span></td>\n' +
            '                    </tr>\n' +
            '                    <tr>\n' +
            '                        <td class="orders-total-label">За все время</td>\n' +
            '                        <td class="orders-total-free text-right text-secondary"><span>0</span></td>\n' +
            '                        <td class="orders-total-total text-right"><span>0</span></td>\n' +
            '                    </tr>\n' +
            '                    </tbody>\n' +
            '                </table>\n' +
            '            <div class="text-center"><h6>Очередь заказов</h6></div>\n' +
            '            <div id="scrollable-part">\n' +
            '                <table class="table bg-white song-queue-block">\n' +
            '                    <thead>\n' +
            '                    <tr>\n' +
            '                    <th class="song-queue-block-header">\n' +
            '                        <span></span>\n' +
            '                    </th>\n' +
            '                    </tr>\n' +
            '                    </thead>\n' +
            '                    <tbody id="song-queue-list">\n' +
            '                    <tr>\n' +
            '                    <td class="text-center">\n' +
            '                            Нет песен в очереди\n' +
            '                    </td>\n' +
            '                    </tr>\n' +
            '                    </tbody>\n' +
            '                </table>\n' +
            '            </div>\n' +
            '            </div>\n' +
            '        </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
        $.ajax({
            type: "get",
            url: "/api/v1/getSongsInQueue",
            success: function (songList) {
                if (songList.length > 0) {
                    let songQueueHTML = '';
                    for (let s = 0; s < songList.length; s++) {
                        songQueueHTML += '<tr><td>' + (s + 1) + '\. ' + songList[s] + '</td></tr>';
                    }
                    $("#song-queue-list").empty().append(songQueueHTML);
                }
            }
        });

        $.ajax({
            type: "get",
            url: "/api/v1/getOrders",
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    $("." + data[i].split(":")[0]).empty().append(data[i].split(":")[1]);
                }
            }
        });
    }
}


export default Statistics;