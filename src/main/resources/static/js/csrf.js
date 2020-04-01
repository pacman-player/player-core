// $(document).ready(function () {
//
// });


/**
 * Функция, которая в каждый ajax-запрос отправленный одним из перечисленных
 * четырёх методов будет добавлять заголовок с CSRF-токеном вида
 * X-CSRF-TOKEN: 748cc0f4-b459-493e-86d9-38d1b89f999e, для того, чтобы наш сервер
 * со Spring Security позволял производить операции создания/удаления/изменения.
 */
$(function () {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        let methodsArray = ["POST", "PUT", "PATCH", "DELETE"];
        let method = options.type.toUpperCase();
        if (methodsArray.indexOf(method) > -1) {
            xhr.setRequestHeader(header, token);
        }
    });
});


// $(document).on('click', '#logout-btn', function () {
//     fetch('/logout', {
//         method: 'POST',
//         redirect: 'follow',
//         headers: {
//             'X-CSRF-TOKEN': csrfToken
//         },
//     }).then(response => {
//         // HTTP 301 response
//         // HOW CAN I FOLLOW THE HTTP REDIRECT RESPONSE?
//         // if (response.redirected) {
//         //     window.location.href = response.url;
//         // }
//     })
//         .catch(function(err) {
//             console.info(err + " url: " + url);
//         });
// });

// function logout() {
//     $.post('/logout');
// }

// $( document ).ajaxSend(function( event, jqxhr, settings ) {
//     if ( settings.type.toLowerCase() !== "get" ) {
//         const token = $("meta[name='_csrf']").attr("content");
//         const header = $("meta[name='_csrf_header']").attr("content");
//         jqxhr.setRequestHeader(header, token);
//     }
// });