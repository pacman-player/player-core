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
        success: function() {
            $('#answer').val("Файл загружен");
        },
        error: function () {
            $('#answer').val("Ошибка загрузки файла");
        }
    })
})