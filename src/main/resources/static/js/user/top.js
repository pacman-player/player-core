$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();
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
                        "<td>" + topId + "</td><td>"
                        + key + "</td><td>"
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
                        "<td>" + topId + "</td><td>"
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
                        "<td>" + topId + "</td><td>"
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
                        "<td>" + topId + "</td><td>"
                        + key + "</td><td>"
                        + value + "</td>")
                        .appendTo('#myTableYear');
                });

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