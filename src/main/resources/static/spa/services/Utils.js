const Utils = { 
    // --------------------------------
    //  Parse a url and break it into resource, id and verb
    //  Разобрать URL и разбить его на ресурс, идентификатор и глагол
    // --------------------------------
    parseRequestURL : () => {

        let url = location.hash.slice(1).toLowerCase() || '/';
        let r = url.split("/")
        let request = {
                resource    : null,
                id          : null,
                verb        : null
            }
            request.resource    = r[1]
            request.id          = r[2]
            request.verb        = r[3]

        return request
    }

    // --------------------------------
    //  Simple sleep implementation
    //  Простая реализация сна
    // --------------------------------
    , sleep: (ms) => {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
}

export default Utils;