$(document).ready(function () {
    $("#captcha").click(function (event) {
        event.preventDefault();
        grecaptcha.execute();
    });
});