let LeftSideBar = {
    render: async () => {
        return '<!--меню слева-->\n' +
            '            <ul class="nav nav-pills nav-stacked nav-content bg-dark">\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/statistics">Статистика</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/filter">Фильтр музыки</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/company">Моё заведение</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/promo">Промо-материалы</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="/user/spa#/edit">Редактирование профиля</a>\n' +
            '                </li>\n' +
            '                <li>\n' +
            '                    <a href="javascript:PopUpShow()">Показать плеер</a>\n' +
            '                </li>\n' +
            '            </ul>\n'
    }
};

export default LeftSideBar;