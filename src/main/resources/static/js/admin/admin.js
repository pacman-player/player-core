    function notification(notifyId, message, panelId) {
        let notify = document.createElement('div');
        notify.setAttribute('id', 'success-alert-' + notifyId);
        notify.classList.add("alert");
        notify.classList.add("alert-success");
        notify.classList.add("notify");
        notify.classList.add("alert-dismissible");
        notify.setAttribute("role", "alert");
        notify.setAttribute("hidden", "true");
        notify.innerHTML = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' + message;

        if (!document.querySelector('#field')) {
            let field = document.createElement('div');
            field.id = 'field';
            document.getElementById(panelId).appendChild(field);
        }
        let parent= document.querySelector('#field');
        parent.insertBefore(notify, parent.firstChild);
        //document.querySelector('#field').appendChild(notify, );
        $('#success-alert-' + notifyId).slideDown(300);
        setTimeout(() => {
            $('#success-alert-' + notifyId).fadeOut(400, "linear", $(this).remove());
        }, 3000);
    }
