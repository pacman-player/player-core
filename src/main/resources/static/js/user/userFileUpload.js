$("#userFileUploadForm").submit(function (e) {
    e.preventDefault();
    var form_data = new FormData(this);

    $.ajax({
        type: 'post',
        url: '/api/user/somePage/fileUpload',
        enctype: 'multipart/form-data',
        data: form_data,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            $("#error").text("");
            $("#success").text(decodeURI(data));
            $("#userFileUploadForm").trigger('reset');
        },
        error: function (e) {
            $("#success").text("");
            $("#error").text(decodeURI(e.responseText));
        }
    })
});