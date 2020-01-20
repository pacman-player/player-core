let tableBody;
let playerStandard;
let playerContainer;
let musicSource;
let musicPath;

$(function () {
    tableBody = $("#table-body");
    playerStandard = $("#playerStandard");
    playerContainer = $("#playerContainer");
    musicSource = $("#musicSource");

    $.get("/api/music/musicPath", function (musicPathString) {
        musicPath = musicPathString;
    });

    $.get("/api/music/all", function (musicList) {
        for (let music of musicList) {
            console.log(music.name)
            let musicTr = $("<tr></tr>");
            let musicTd = `<td>${music.name}</td>`
                + `<td><button id="playButton" onclick="playOrPause(${music.name}, this.text())" data-musicName="${music.name}">Play</button></td>`;
            musicTr.html(musicTd);
            tableBody.append(musicTr);
        }
    });

    // $("#saveMusicsForm").on("submit", function (event) {
    //     event.preventDefault();
    //     $.post($("#saveMusicsForm").attr("action"), $("#saveMusicsForm").serialize());
    // });
});

function playOrPause(musicName, state) {
    if (state === "Play") {
        play(musicName);
    } else pause(musicName);
}

function play(musicName) {
    console.log("play");
    $(`td:contains(${musicName})`).text("Pause");
    musicSource.attr("src", musicName);
    playerStandard.play();
}

function pause(musicName) {
    console.log("play");
    $(`td:contains(${musicName})`).text("Play");
    playerStandard.pause();
}