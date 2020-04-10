let NavBar = {
    render: async () => {
        return '    <div class="row">\n' +
            '        <div class="navbar navbar-inverse navbar-static-top header">\n' +
            '            <a class="navbar-brand" href="#"><b>Pacman player</b></a>\n' +
            '        <ul class="nav navbar-nav">\n' +
            '            <li id="adminLink" style="display: none"><a href="/admin/users">Меню администратора</a></li>\n' +
            '        </ul>' +
            '            <a class="navbar-brand pull-right" href="#" id="logout-btn">Выход</a>\n' +
            '            <button id="notification" class="navbar-brand pull-right">Уведомлений</button>\n' +
            '            <div id="listNotification" class="pull-right"></div>\n' +
            '        </div>\n' +
            '    </div>\n'
    },
    after_render: async () => {

        $(document).ready(function () {
            showLinkAdmin();

            function showLinkAdmin() {
                $.ajax({
                    type: "post",
                    url: "/api/user/show_admin",
                    success: function (role) {
                        if (role === "admin") {
                            $("#adminLink").attr("style", "display: inline");
                        }
                    }
                });
            }
        })
    }
};


export default NavBar;