let NavBar = {
    render: async () => {
        let view =  /*html*/

            '    <div class="row">\n' +
            '        <div class="navbar navbar-inverse navbar-static-top header">\n' +
            '            <a class="navbar-brand" href="#"><b>Pacman player</b></a>\n' +
            '            <a class="navbar-brand pull-right" href="/logout">Выход</a>\n' +
            '            <button id="notification" class="navbar-brand pull-right">Уведомлений</button>\n' +
            '            <div id="listNotification" class="pull-right"></div>\n' +
            '        </div>\n' +
            '    </div>\n'

        return view
    },
    after_render: async () => { }
}

export default NavBar;