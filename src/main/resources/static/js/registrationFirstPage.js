let registrationFirstForm;
$(function () {
    registrationFirstForm = $("#registrationFirstForm");
    registrationFirstForm.submit(function (event) {
        event.preventDefault();
        let password = $("#password").val();
        let confirmPassword = $("#confirmPassword").val();
        if (password === confirmPassword) {
            $.post("/api/registration/first", registrationFirstForm.serialize(), function (answer) {
                if (answer === "exist") {
                    alert("User with this login or email already exist! Please choose something another!")
                } else if (answer === "success") {
                    let login = $("#login").val();
                    location.assign(location.origin + "/registration/second?login=" + login);
                }
            });
        } else alert("Your passwords don't match! Please reenter passwords.");

    })
});