let LeftSideBar = {
    render: async () => {
        let view =  /*html*/
            '<!--меню слева-->\n' +
            '        <div class="col-lg-2 col-md-2 col-xs-2 right-bar right-menu">\n' +
            '            <ul class="nav nav-pills nav-stacked nav-content bg-dark">\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/statistics">Статистика</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/filter">Фильтр музыки</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/top">Топ популярных</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/company">Моё заведение</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/promo">Промо - материалы</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/edit">Редактирование профиля</a>\n' +
            '                </li>\n' +
            '                <li id="adminLink">\n' +
            '                    <a href="/admin/users">Администратор</a>\n' +
            '                </li>\n' +
            '            </ul>\n' +
            '        </div>'

        return view
    },
    after_render: async () => { }

}

export default LeftSideBar;