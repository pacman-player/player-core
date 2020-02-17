$(document).ready(function () {
    let registrationSecondForm;
    $(function () {
        registrationSecondForm = $("#registrationSecondForm");
        registrationSecondForm.submit(function (event) {
            event.preventDefault();
            console.log("saving");
            if ($('#registrationSecondForm').valid()) {
                $.post("/api/registration/second", registrationSecondForm.serialize(), function () {
                    location.assign(location.origin + "/reg_check");
                });
            }
        })
    });
    $('#registrationSecondForm').validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                remote: {
                    url: "/api/registration/check/company",
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    parameterData: {
                        name: function () {
                            return $("#name").val();
                        }
                    },
                }
            },
            startTime: {
                required: true
            },
            closeTime: {
                required: true
            },
            orgType: {
                required: true
            }
        },
        messages: {
            name: {
                required: "Введите название компании",
                minlength: "Название должно содержать минимум 3 символа",
                remote: "Компания с таким именем уже зарегистрирована"
            },
            startTime: {
                required: "Укажите время открытия"
            },
            closeTime: {
                required: "Укажите время закрытия"
            },
            orgType: {
                required: "Выберите тип заведения"
            }
        }
    });
});
