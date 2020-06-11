$(document).ready(function () {
    getStatisticData();
});

function getStatisticData() {
    var period = $('.nav-tabs .active > a').attr('href');
    $.ajax({
        type: "GET",
        url: "/api/admin/statistic/getData",
        async: false,
        data: {"period": period},
        success: function (response) {
            getStatisticTable(response);
            getStatisticDiagram(response);
        }
    });
}

//функция заполнения таблицы статистики прослушиваний
function getStatisticTable(statisticData) {
    var tr;
    $('#statisticBody').html('');
    $.each(statisticData, function (index, songStatistic) {
        tr = $('<tr/>');
        tr.append("<td>" + songStatistic.songName + "</td>");
        tr.append("<td>" + songStatistic.orderCount + "</td>");
        $('#statisticBody').append(tr);
    });
}

function getStatisticDiagram(statisticData) {
    google.charts.load('current');
    google.setOnLoadCallback(drawChart);

    function drawChart() {
        let songArray = [''];
        let orderArray = [0];
        $.each(statisticData, function (index, songStatistic) {
            songArray.push(songStatistic.songName);
            orderArray.push(songStatistic.orderCount);
        });
        var wrapper = new google.visualization.ChartWrapper({
            chartType: 'ColumnChart',
            dataTable: [songArray, orderArray],
            options: {'title': 'Топ популярных песен'},
            containerId: 'statisticDiagram'
        });
        wrapper.draw();
        /*var data = google.visualization.arrayToDataTable([
            ['', 'Россия', 'США', 'canada', 'mexico', 'dwdw', 'wdwdwddw', 'dwwd', 'fasasf', 'asasdasd', 'affas'],
            ['0', 1.3, 70, 12, 24, 43, 12, 123, 54, 12, 41],
            ['', 'Россия', 'США', 'canada', 'mexico', 'dwdw', 'wdwdwddw', 'dwwd', 'fasasf', 'asasdasd', 'affas']
        ]);*/
    }
}