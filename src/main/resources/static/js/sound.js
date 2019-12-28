$(() => { 

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
        src: ['http://localhost:8080/api/download/' + $('.soundBtn').click().parent().attr("id")],
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

        },
        onstop: function () {
            $('i.play').removeClass('hide');
            $('i.pause').addClass('hide');
            $('#sound').removeClass('playing');

        }
    });

// Play/Pause btn
    $('.soundBtn').click(function () {
        $(this).toggleClass('playing');
        if ($(this).hasClass('playing')) {
            sound.play();
            $("<div class=\"audio-progress\">\n" +
                "<div id=\"progress\"></div>\n" +
                "<div class=\"time\">\n" +
                "<span id=\"timer\">0:00 </span>/\n" +
                "<span id=\"duration\">0:00</span>\n" +
                "</div>\n" +
                "</div>").addClass("pl").insertAfter($(this).next());
        } else {
            sound.pause();
            $(".audio-progress").remove();

        }
    });
    $('.soundBtnStop').click(function () {
        sound.stop();
        $(".audio-progress").remove();
    });
    $('.modal').on('hidden.bs.modal', function () {
        sound.stop();
        $(".audio-progress").remove();
    }); 
});