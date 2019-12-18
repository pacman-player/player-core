$("#searchForm").submit(function (e) {
    e.preventDefault();

    $.ajax({
        type: "POST",
        url: "/api/user/somePage/search",
        data: {
            "artist": document.getElementById("artist").value,
            "track": document.getElementById("track").value
        },
        cache: false,
        success: function (data) {
            $("#error").text("");
            $("#success").text(decodeURI(data));
            $("#searchForm").trigger('reset');
        },
        error: function (e) {
            $("#success").text("");
            $("#error").text(decodeURI(e.responseText));
        }
    })
});