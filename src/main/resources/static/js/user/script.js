
function getStats(period) {
    var per = [];
    var orders = [];
        $.get({
            url: "/api/v1/getOrders/" + period,
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
        fillOrders(per, orders, period);

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
        if (type === "today" || type === "yesterday") {
            time = "с " + r + ":00 по " + (r + 1) + ":00";
        } else if (type === "week" || type === "month" || type === "year") {
            time = per[r];
        }
        let zak = orders[r] % 100 > 10 && orders[r] % 100 < 15 ? "заказов" : orders[r] % 10 === 1 ? "заказ" : orders[r] % 10 > 1 && orders[r] % 10 < 5 ? "заказа" : "заказов";
        let ord = orders[r] === undefined ? 0 : orders[r];
        bullet += ("<tr><td>" + time + " - " + ord + " " + zak + "</td></tr>")
    };
    $("#song-orders-list").empty().append(bullet);
    let appendToOrders = "";
    switch (type) {
        case "today" :  appendToOrders = "Список заказов за сегодня по часам"; break;
        case "yesterday" :  appendToOrders = "Список заказов за вчера по часам"; break;
        case "week" :  appendToOrders = "Список заказов за неделю по дням"; break;
        case "month" :  appendToOrders = "Список заказов за месяц по дням"; break;
        case "year" :  appendToOrders = "Список заказов за год по месяцам"; break;
    }
    let total = orders.reduce(function (a, b) {
    return parseInt(a) + parseInt(b);
    }, 0);
    $("#ondemand").empty().append(" " + appendToOrders + " - всего за период " + total);
}
