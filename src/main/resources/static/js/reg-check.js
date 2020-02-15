$(document).ready(function () {

    getMissedSteps();

    function getMissedSteps() {
        $.ajax({
            type: 'GET',
            url: "/api/user/get_missed_steps",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            cache: false,
            dataType: 'JSON',
            success: function (listSteps) {
            var code = '';
            var htmlPage = document.getElementById('htmlPage');
                switch (listSteps[0]) {
                    case 1:

                        break;
                    case 2:
                        code += '<div class="container">'
                        code += '<div class="row">'
                        code += '<div class="col-md-6 col-md-offset-3">'
                        code += '<h1>Регистрация: 2/2</h1>'
                        code += '<form class="text-center" id="registrationSecondForm">'
                        code += '<input type="hidden" id="login" name="login" th:value="${login}"/>'
                        code += '<div class="form-group">'
                        code += '<label for="name" class="control-label">Имя компании</label>'
                        code += '<input type="text" id="name" class="form-control" placeholder="Введите имя компании" name="name" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="startTime" class="control-label">Время открытия</label>'
                        code += '<input type="time" id="startTime" class="form-control" name="startTime" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="closeTime" class="control-label">Время закрытия</label>'
                        code += '<input type="time" id="closeTime" class="form-control" name="closeTime" required="required"/>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<label for="orgType" class="control-label">Тип организации</label>'
                        code += '<select class="form-control" name="orgType" id="orgType">'
                        code += '</select>'
                        code += '</div>'
                        code += '<div class="form-group">'
                        code += '<input type="submit" class="btn btn-success" value="Зарегестрироваться"/>'
                        code += '</div>'
                        code += '</form>'
                        code += '</div>'
                        code += '</div>'
                        code += '</div>'
                        htmlPage.insertAdjacentHTML('afterEnd', code);
                        break;
                    case 3:
                        document.write("x равен 3");
                        break;
                    default:
                        location.replace("user/statistics");
                }
            }
        });
    }

        //получаем все жанры песни из БД на выбор
        getAllGenreForAdd();

        function getAllGenreForAdd() {
            //очищаю жанры option
            $('#orgType').empty();
            var genreForAdd = '';
            $.getJSON("/api/user/orgType/get_all_orgType", function (data) {
                $.each(data, function (key, value) {
                    genreForAdd += '<option ';
                    genreForAdd += ' value="' + value.name + '">' + value.name + '</option>';
                });
                $('#orgType').append(genreForAdd);
            });
        }
});
