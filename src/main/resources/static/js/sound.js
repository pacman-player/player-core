$(() => {
    let sound = new Howl({
        src : '',
        format: ['mp3', 'aac'],
        volume: 0.2
    })


    $(".btn1").click(function () {
        let sound = new Howl({
            src : 'https://freesound.org/data/previews/335/335571_5942333-lq.mp3',
            format: ['mp3', 'aac'],
            volume: 0.2,
        })
        sound.play();
    $(".btnStop").click(function () {
        sound.stop();
    })
    })
    $(".btn2").click(function () {
        let sound = new Howl({
            src : 'http://localhost:8080/api/download/GorProject_LA',
            format: ['mp3', 'aac'],
            volume: 0.2
        })
        sound.play();
        $(".btnStop").click(function () {
            sound.stop();
        })
    })
    $(".btn3").click(function () {
    let sound = new Howl({
        src : 'http://localhost:8080/api/download/Florence',
        format: ['mp3', 'aac'],
        volume: 0.2
    })
    sound.play();
        $(".btnStop").click(function () {
            sound.stop();
        })
})

});
