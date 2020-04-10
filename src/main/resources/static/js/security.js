/**
 * Файл для внедрения элементов по обеспечению безопасности.
 */


/**
 * Объявляем глобальные переменные для HTTP-заголовка: имя и значение,
 * необходимые для обеспечения CSRF-защиты.
 */
let csrfToken = $("meta[name='_csrf']").attr("content");
let csrfHeader = $("meta[name='_csrf_header']").attr("content");


/**
 * Объявляем функцию, которая в каждый ajax-запрос, отправленный
 * одним из перечисленных четырёх методов протокола HTTP, будет добавлять
 * заголовок с CSRF-токеном вида:
 * X-CSRF-TOKEN: 748cc0f4-b459-493e-86d9-38d1b89f999e,
 * для того, чтобы наш сервер со Spring Security позволял производить
 * операции создания/удаления/изменения.
 */
$(function () {
    $(document).ajaxSend(function (e, xhr, options) {
        let methodsArray = ["POST", "PUT", "PATCH", "DELETE"];
        let method = options.type.toUpperCase();
        if (methodsArray.indexOf(method) > -1) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        }
    });
});


/**
 * При включенной CSRF-защите в Spring Security мы должны
 * разлогиниваться методом POST, а не GET, при этом, мы также
 * обязаны отправлять параметр с именем name='_csrf' и
 * значением равным нашему токену, например:
 * value='748cc0f4-b459-493e-86d9-38d1b89f999e', чтобы Spring
 * Security убедился, что разлогиниваемся именно мы, а не
 * злоумышленник разлогинивает нас. Поэтому при нажатии на
 * кнопку "Выход" будет срабатывать эта функция, которая
 * будет создавать форму с нужными параметром и значением и
 * отправлять их методом POST.
 */
$(document).on('click', '#logout-btn', function (event) {
    event.preventDefault();
    let form = document.createElement('form');
    document.body.appendChild(form);
    form.method = 'POST';
    form.action = '/logout';
    let input = document.createElement('input');
    input.type = 'hidden';
    input.name = '_csrf';
    input.value = csrfToken;
    form.appendChild(input);
    form.submit();
});