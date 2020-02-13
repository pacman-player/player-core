$(document).ready(function () {

    //доступ к  ссылки админа
    showLinkAdmin();
    getOrders();
    getQueue();

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