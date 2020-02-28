
let Statistics = {

    render: async () => {
        return /*html*/ '        <!--центральный блок-->\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-6 col-md-6 col-xs-6 " id="my-selection">\n' +
            '                <H3> Статистика</H3>\n' +
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
                        songQueueHTML += '<tr><td>' + (s + 1) + '\. ' +songList[s] + '</td></tr>';
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

//Ниже закомменченный код - тоже рабочий, тот же функционал!!!
// function getQueue() {
//     $.ajax({
//         type: "get",
//         url: "/api/v1/getSongsInQueue",
//         success: function (songList) {
//             if (songList.length > 0) {
//                 let songQueueHTML = '';
//                 for (let s = 0; s < songList.length; s++) {
//                     songQueueHTML += '<tr><td>' + (s + 1) + '\. ' +songList[s] + '</td></tr>';
//                 }
//                 $("#song-queue-list").empty().append(songQueueHTML);
//             }
//         }
//     });
// }
//
// function getOrders() {
//     $.ajax({
//         type: "get",
//         url: "/api/v1/getOrders",
//         success: function (data) {
//             for (let i = 0; i < data.length; i++) {
//                 $("." + data[i].split(":")[0]).empty().append(data[i].split(":")[1]);
//             }
//         }
//     });
// }
//
// let Statistics = {
//
//     render : async () => {
//         let request = Utils.parseRequestURL();
//         let queue = await getQueue();
//         let orders = await getOrders();
//
//         return /*html*/`        <!--центральный блок-->
// <!--        <div class="tab-content">-->
//             <div role="tabpanel" class="tab-pane active col-lg-6 col-md-6 col-xs-6 " id="my-selection">
//
//                 <H3> Статистика</H3>
//
//
//             </div>
// <!--        </div>-->
//
//         <!-- right side stats bar-->
//         <div class="container col-lg-4 col-md-4 col-xs-4 col-xl-4" id="right-side-bar">
//                 <table class="table bg-white stats-table">
//                     <thead>
//                     <tr>
//                         <th class="orders-title">Заказы</th>
//                         <th class="orders-type-free text-right small text-secondary">Бесплатных</th>
//                         <th class="orders-type-total text-right">Всего</th>
//                     </tr>
//                     </thead>
//                     <tbody>
//                     <tr>
//                         <td class="orders-today-label">За сегодня</td>
//                         <td class="orders-today-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-today-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-yesterday-label">За вчера</td>
//                         <td class="orders-yesterday-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-yesterday-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-week-label">За неделю</td>
//                         <td class="orders-week-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-week-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-month-label">За месяц</td>
//                         <td class="orders-month-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-month-total text-right"><span>0</span></td>
//                     </tr>
//                     <tr>
//                         <td class="orders-total-label">За все время</td>
//                         <td class="orders-total-free text-right text-secondary"><span>0</span></td>
//                         <td class="orders-total-total text-right"><span>0</span></td>
//                     </tr>
//                     </tbody>
//                 </table>
//             <div class="text-center"><h6>Очередь заказов</h6></div>
//             <div id="scrollable-part">
//                 <table class="table bg-white song-queue-block">
//                     <thead>
//                     <tr>
//                     <th class="song-queue-block-header">
//                         <span></span>
//                     </th>
//                     </tr>
//                     </thead>
//                     <tbody id="song-queue-list">
//                     <tr>
//                     <td class="text-center">
//                             Нет песен в очереди
//                     </td>
//                     </tr>
//                     </tbody>
//                 </table>
//             </div>
//             </div>
//         </div>
//         `
//     }
//     , after_render: async () => {
//     }
// }

export default Statistics;