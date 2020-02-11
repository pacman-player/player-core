
function getStats(period) {
    var per = [];
    var orders = [];
    if (period === "today") {
        per = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24];
        orders = [1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 5, 6, 7, 8, 6, 9, 10, 2, 3, 8, 10, 20];
    } else  if (period === "week") {
        per = ['Пн', 'Вт', 'Ср', "Чт", "Пт", "Сб", "Вс"];
        orders = [11, 24, 3, 14, 5, 6, 17];
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