$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();
    getOrders();
    getQueue();
    topDaySong();
    topWeekSong();
    topMonthSong();
    topYearSong();

    function topDaySong() {
        $.ajax({
            url : '/api/v1/getTopSong/1',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success : function(data) {
                let topId = 0;
                $.each(data , function( key, value) {
                    topId++;
                    $('<tr>').html(
                        "<td>" + topId + "</td><td>" +
                        '<span class="glyphicon glyphicon-play" aria-hidden="true"/> ' + key + "</td><td>"
                        + value + "</td>")
                        .appendTo('#myTableDay');
                });

            }
        });
    }

    function topWeekSong() {
        $.ajax({
            url : '/api/v1/getTopSong/7',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success : function(data) {
                let topId = 0;
                $.each(data , function( key, value ) {
                    topId++;
                    $('<tr>').html(
                        "<td>" + topId + "</td><td>" +
                        '<span class="glyphicon glyphicon-play" aria-hidden="true"/> '
                        + key + "</td><td>"
                        + value + "</td>")
                        .appendTo('#myTableWeek');
                });

            }
        });
    }

    function topMonthSong() {
        $.ajax({
            url : '/api/v1/getTopSong/30',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success : function(data) {
                let topId = 0;
                $.each(data , function( key, value ) {
                    topId++;
                    $('<tr>').html(
                        "<td>" + topId + "</td><td>" +
                        '<span class="glyphicon glyphicon-play" aria-hidden="true"/> '
                        + key + "</td><td>"
                        + value + "</td>")
                        .appendTo('#myTableMonth');
                });

            }
        });
    }

    function topYearSong() {
        $.ajax({
            url : '/api/v1/getTopSong/365',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success : function(data) {
                let topId = 0;
                $.each(data , function( key, value ) {
                    topId++;
                    $('<tr>').html(
                        "<td>" + topId + "</td><td>" +
                        '<span class="glyphicon glyphicon-play" aria-hidden="true"/> '
                        + key + "</td><td>"
                        + value + "</td>")
                        .appendTo('#myTableYear');
                });

            }
        });
    }

    function getQueue() {
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
    }

    function getOrders() {
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

});