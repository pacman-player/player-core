$(document).ready(() => {
    getTemplateProcessNotify("default");
});

function getTemplateProcessNotify(templateName) {
    $.ajax({
        type: 'GET',
        url: "/api/notification/template/name/" + templateName,
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        cache: true,
        success:
            function (template) {
                $(".notificationElement").each((i, elem) => {
                    $(elem).html(processNotification($(elem).html(), template.template));
                });
            },
        error:
            function (xhr, status, error) {
                alert(xhr.responseText + '|\n' + status + '|\n' + error);
            }
    });
}

function processNotification(notification, template) {
    if (!notification.includes("{patterned}")) return notification;
    notification = notification.replace("{patterned}", "");
    let meta, obj, details;
    meta = template.substr(template.indexOf("{link:"), template.indexOf(":}") + 2);
    details = meta.substr(6, meta.length - 2).split(":");
    obj = `<a href="${details[0]}">${details[1]}</a>`;
    template = template.replace(meta, obj);
    template = template.replace("{subject}", notification);
    return template;
}
