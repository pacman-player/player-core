let registrationSecondForm;
$(function () {
    registrationSecondForm = $("#registrationSecondForm");
    registrationSecondForm.submit(function (event) {
        event.preventDefault();
        console.log("saving");
        $.post("/api/registration/second", registrationSecondForm.serialize(), function (answer) {
            console.log("in success");
            if (answer === "exist") {
                alert("Company with this name exist! Please choose something another.")
            } else if (answer === "success") {
                alert("You registered successfully, now you can sign in.");
                let login = $("#login").val();
                location.assign(location.origin + "/registration/end?login=" + login);
            }
        });
    })
});