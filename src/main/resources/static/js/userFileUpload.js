$("#userFileUploadForm").submit(function(e){
    e.preventDefault();
    var form_data = new FormData(this);

    $.ajax({
        type: 'post',
        url: '/api/user/fileUpload',
        enctype: 'multipart/form-data',
        data: form_data,
        cache: false,
        contentType: false,
        processData: false,
        success: function(ans) {
            $('#answer').text(ans);
        },
        error: function (ans) {
            $('#answer').text(ans);
        }
    })
});