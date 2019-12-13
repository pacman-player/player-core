$(() => {
 /*
    pCount = 0;
    playlistUrls = [
        'https://freesound.org/data/previews/335/335571_5942333-lq.mp3',
        'http://localhost:8080/api/download/GorProject_LA',
        'http://localhost:8080/api/download/2_2'
    ];
    howlerBank = [],
        loop = true;
    var onEnd = function(e) {
        if (loop === true ) {
            pCount = (pCount + 1 !== howlerBank.length)? pCount + 1 : 0;
        } else {
            pCount = pCount + 1;
        }
        howlerBank[pCount].play();
    };

    // build up howlerBank:
    playlistUrls.forEach(
        function(current, i) {
            howlerBank.push(new Howl({
                urls: [playlistUrls[i]],
                onend: onEnd,
                buffer: true
            }))
    });
*/
    // initiate the whole :
 //   howlerBank[0].play();

    var utils = {
        formatTime: function (secs) {
            var minutes = Math.floor(secs / 60) || 0;
            var seconds = (secs - minutes * 60) || 0;
            return minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
        },
        updateTimeTracker: function () {
            var self = this;
            var seek = sound.seek() || 0;
            var currentTime = utils.formatTime(Math.round(seek));

            $('#timer').text(currentTime);
            progress.style.width = (((seek / self.duration()) * 100) || 0) + '%';

            if (self.playing()) {
                requestAnimationFrame(utils.updateTimeTracker.bind(self));
            }
        }
    };

    var sound = new Howl({
        src: ['http://localhost:8080/api/download/Florence'],
        format: ['mp3', 'aac'],
        onplay: function () {
            audioPlayed = true;
            var time = Math.round(sound.duration());

            $('#duration').html(utils.formatTime(time));
            requestAnimationFrame(utils.updateTimeTracker.bind(this));
            $('i.play').addClass('hide');
            $('i.pause').removeClass('hide');
            $('div.audio-progress').removeClass('hide');
        },
        onpause: function () {
            console.log('PAUSED ... ');
            $('i.play').removeClass('hide');
            $('i.pause').addClass('hide');
        },
        onend: function () {
            console.log('ENDED ...');
            $('i.play').removeClass('hide');
            $('i.pause').addClass('hide');
            $('#sound').toggleClass('playing');
            $('div.audio-progress').addClass('hide');

        }
    });

// Play/Pause btn
    $('#sound').click(function () {
        $(this).toggleClass('playing');
        if ($(this).hasClass('playing')) {
            sound.play();
        } else {
            sound.pause();
        }
    });

});