
let Top = {

    render: async () => {
        return /*html*/ '<!--центральный блок-->\n' +
            '        <div class="tab-content">\n' +
            '            <div role="tabpanel" class="tab-pane active col-lg-10 col-md-10 col-xs-10 " id="my-selection">\n' +
            '                <H3> Топ песен</H3>\n' +
            '            </div>\n' +
            '        </div>'
    }
    // Весь код, связанный с DOM-взаимодействиями и элементами управления, находится здесь.
    // Это отдельный вызов, так как они могут быть зарегистрированы только после того, как DOM будет окрашен
    , after_render: async () => {
    }
}


export default Top;