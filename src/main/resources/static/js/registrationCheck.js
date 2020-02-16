$(document).ready(function () {

    let login;
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
                login = data.login;
            }
        });
    }

    function getPagesToPass() {
        $.ajax({
            url: '/api/registration/getPages',
            data: {login : login},
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

