// "use strict";
//
// // import Home         from './views/pages/Home.js'
// // import About        from './views/pages/About.js'
// // import Error404     from './views/pages/Error404.js'
// // import PostShow     from './views/pages/PostShow.js'
// // import Register     from './views/pages/Register.js'
// // import Statistics   from "./views/pages/Statistics.js";
// // // import Compilation4  from "./views/pages/Compilation4.js";
// //
// // import NavBar       from './views/components/NavBar.js'
// // import LeftSideBar  from './views/components/LeftSideBar.js'
// import BottomBar4    from './views/components/BottomBar4.js'
//
// // import Utils        from './services/Utils.js'
//
//
//
// // List of supported routes. Any url other than these routes will throw a 404 error
// // Список поддерживаемых маршрутов. Любой URL, кроме этих маршрутов, выдаст ошибку 404
// // const routes = {
// //     '/'             : Home
// //     , '/about'      : About
// //     , '/p/:id'      : PostShow
// //     , '/register'   : Register
// //     , '/statistics' : Statistics
// //     // , '/compilation': Compilation4
// // };
//
// // The router code. Takes a URL, checks against the list of supported routes and then renders the corresponding content page.
// // Код роутера. Принимает URL, проверяет список поддерживаемых маршрутов и затем отображает соответствующую страницу содержимого.
// const router = async () => {
//
//     // Lazy load view element:
//     // Элемент просмотра отложенной загрузки:
//     // const header = null || document.getElementById('header_container');
//     // const menu = null || document.getElementById('menu_container');
//     const footer = null || document.getElementById('footer_container');
//     // const content = null || document.getElementById('page_container');
//
//     // //отображаем навбар
//     // header.innerHTML = await NavBar.render();
//     // await NavBar.after_render();
//     //
//     // //отображаем меню слева
//     // menu.innerHTML = await LeftSideBar.render();
//     // await LeftSideBar.after_render();
//
//     //отображаем футер(плеер)
//     footer.innerHTML = await BottomBar4.render();
//     await BottomBar4.after_render();
//
//
//     // Get the parsed URl from the addressbar
//     // Получить проанализированный URl из адресной строки
//     // let request = Utils.parseRequestURL()
//
//     // Parse the URL and if it has an id part, change it with the string ":id"
//     // Проанализируйте URL-адрес и, если он имеет часть id, измените его на строку «: id»
//     // let parsedURL = (request.resource ? '/' + request.resource : '/') + (request.id ? '/:id' : '') + (request.verb ? '/' + request.verb : '')
//
//     // Get the page from our hash of supported routes.
//     // If the parsed URL is not in our list of supported routes, select the 404 page instead
//     // Получить страницу из нашего хэша поддерживаемых маршрутов.
//     // Если проанализированный URL-адрес отсутствует в нашем списке поддерживаемых маршрутов, выберите страницу 404.
//     // let page = routes[parsedURL] ? routes[parsedURL] : Error404
//     // content.innerHTML = await page.render();
//     // await page.after_render();
//
// }
//
//
//
// // Listen on hash change:
// // Послушайте изменение хеша:
// // window.addEventListener('hashchange', router);
//
// // // Listen on page load:
// // // Слушайте на странице загрузки:
// window.addEventListener('load', router);
