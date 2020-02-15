
function getStats(period) {
    var per = [];
    var orders = [];
    if (period === "today") {
        per = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24];
        orders = [1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 5, 6, 7, 8, 6, 9, 10, 2, 3, 8, 10, 20];
        fillOrders(per, orders, "day");
    } else  if (period === "yesterday") {
        per = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24];
        orders = [11, 12, 3, 4, 15, 6, 17, 8, 17, 6, 5, 4, 5, 16, 7, 8, 16, 9, 10, 12, 3, 8, 10, 20];
        fillOrders(per, orders, "day");
    } else  if (period === "week") {
        per = ['Понедельник', 'Вторник', 'Среда', "Четверг", "Пятница", "Суббота", "Воскресенье"];
        orders = [11, 24, 3, 14, 5, 6, 17];
        fillOrders(per, orders, "week");
    } else  if (period === "month") {
        {
            $.get({
                url: "/api/v1/getOrders/month",
                contentType: "application/json",
                async: false,
                success: function (data) {
                    for (let i = 0; i < data.length; i++) {
                        let current = data[i].split(":");
                        per.push(current[0]);
                        orders.push(current[1]);
                    }
                }
            });
            fillOrders(per, orders, "year");
        }
        // per = ["1.02", "2.02", "3.03", "4.02", "5.02", "6.02", "7.02", "8.02", "9.02", "10.02", "11.02", "12.02", "13.02", "14.02", "15.02", "16.02", "17.02", "18.02", "19.02", "20.02", "21.02", "22.02", "23.02", "24.02", "25.02", "26.02", "27.02", "28.02", "29.02", "01.03", "02.03"];
        // orders = [119, 125, 34, 49, 158, 61, 176, 82, 178, 62, 53, 44, 59, 161, 76, 83, 161, 98, 100, 122, 37, 82, 102, 209, 141];
        fillOrders(per, orders, "month");
    } else  if (period === "year") {
        $.get({
            url: "/api/v1/getOrders/year",
            contentType: "application/json",
            async: false,
            success: function (data) {
            for (let i = 0; i < data.length; i++) {
                let current = data[i].split(":");
                per.push(current[0]);
                orders.push(current[1]);
            }
            }
        });
        fillOrders(per, orders, "year");
    }
// For drawing the lines


    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: per,
            datasets: [
                {
                    data: orders,
                    borderColor: "#3e95cd",
                    fill: false
                }
            ]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            legend: {
                display: false
            }
        }
    });
}

function fillOrders(per, orders, type) {
    var bullet = "";
    let time = "";
    for (let r = 0; r < per.length; r++) {
        if (type === "day") {
            time = "с " + r + ":00 по " + (r + 1) + ":00";
        } else if (type === "week" || type === "month" || type === "year") {
            time = per[r];
        }
        let zak = orders[r] % 100 > 10 && orders[r] % 100 < 15 ? "заказов" : orders[r] % 10 === 1 ? "заказ" : orders[r] % 10 > 1 && orders[r] % 10 < 5 ? "заказа" : "заказов";
        let ord = orders[r] === undefined ? 0 : orders[r];
        bullet += ("<tr><td>" + time + " - " + ord + " " + zak + "</td></tr>")
    };
    $("#song-orders-list").empty().append(bullet);
}
