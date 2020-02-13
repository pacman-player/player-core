$(document).ready(function () {

    let userLogin;
    let stepNumOn;

    getUserData();
    getPagesToPass();

    getContent();

    function getUserData() {
        $.ajax({
            url: '/api/user/get_user',
            method: "GET",
            dataType: "json",
            success: function (data) {
                userLogin = data.login;
            }
        });
    }

    function getPagesToPass() {
        $.ajax({
            url: '/api/registration/getPages',
            data: {userLogin : userLogin},
            type: 'POST',
            dataType: 'json',

            success: function (data) {
                if (data.length > 0) {
                    stepNumOn = data[0];
                }
            }
        });
    }

    function getContent() {
        let step;
        //create url to request fragment
        let url;

        $.ajax({
            url: '/api/registration/getOneStep',
            data: {stepId : stepNumOn},
            type: 'POST',
            dataType: 'json',

            success: function (data) {
                if (data.length > 0) {
                    step = data[0];
                    url = /registration/+ step.name;
                }
            }
        });


        //load fragment and replace content
        $('#to-replace-div').load(url);
    }
});

