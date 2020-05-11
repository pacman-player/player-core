let itemGrafic;
$(document).ready(function () {
    function bildGrafic(data) {
        alert('bildGrafic');
        console.log(data);
    }


    $(document).on('click touchstart', '#js-modal', function () {
        $(this).css({border: '2px solid blue'});
        itemGrafic = this;
        const idEdit = $(this).val();
        alert(numberOfList);
        alert(idEdit);


        //    $('.js-modal-body div').remove(); //удалиь предидущий график
        $('#js-modal-body').append(getListSong());
        alert('вывод списка')

        //  $('#js-modal-body').append('aaaaaaaa');
        $('.js-modal-body').append('bbbbbbbbbbb');


        $.ajax({
            contentType: "application/json;",
            url: "/api/music/getGragicSongs/" + numberOfList + '/' + idEdit,
            type: "POST",
            //      data: JSON.stringify(newUser),
            //       async: true,
            cache: false,
            success: function (data) {
                alert(data);
                console.log(data);
                bildGrafic(data);
            },
            error: function () {
                alert("Не удалось получить данные для построения графика");
            }
        });


    });

});