let Navbar = {
    render: async () => {
        let view =  /*html*/
        //     `
        //      <nav class="navbar" role="navigation" aria-label="main navigation">
        //         <div class="container">
        //             <div class="navbar-brand">
        //                 <a class="navbar-item" href="/#/">
        //                     <img src="https://bulma.io/images/bulma-logo.png" width="112" height="28">
        //                 </a>
        //
        //                 <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
        //                     <span aria-hidden="true"></span>
        //                     <span aria-hidden="true"></span>
        //                     <span aria-hidden="true"></span>
        //                 </a>
        //             </div>
        //
        //             <div id="navbarBasicExample" class="navbar-menu is-active" aria-expanded="false">
        //                 <div class="navbar-start">
        //                     <a class="navbar-item" href="/#/">
        //                         Home
        //                     </a>
        //                     <a class="navbar-item" href="/#/about">
        //                         About
        //                     </a>
        //                     <a class="navbar-item" href="/#/secret">
        //                         Secret
        //                     </a>
        //                 </div>
        //                 <div class="navbar-end">
        //                     <div class="navbar-item">
        //                         <div class="buttons">
        //                             <a class="button is-primary" href="/#/register">
        //                                 <strong>Sign up</strong>
        //                             </a>
        //                             <a class="button is-light">
        //                                 Log in
        //                             </a>
        //                         </div>
        //                     </div>
        //                 </div>
        //             </div>
        //         </div>
        //     </nav>
        // `

            '<div class="container-fluid">\n' +
            '    <div class="row">\n' +
            '        <div class="navbar navbar-inverse navbar-static-top header">\n' +
            '            <a class="navbar-brand" href="#"><b>Pacman player</b></a>\n' +
            '            <a class="navbar-brand pull-right" href="/logout">Выход</a>\n' +
            '            <button id="notification" class="navbar-brand pull-right">Уведомлений</button>\n' +
            '            <div id="listNotification" class="pull-right"></div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>'

        return view
    },
    after_render: async () => { }

}

export default Navbar;