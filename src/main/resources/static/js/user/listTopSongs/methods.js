let numberOfList;
let item;
let s;
$(document).ready(function () {
  //  alert(jQuery.fn.jquery);


    function getBtn(value) {
        return '<button class="btn btn-light btn-xs  js-btnModal" id="js-modal"\n' +
            '        data-target="#myModal"\n' +
            '        data-toggle="modal"\n' +
            '        data-idsong='+value.id +
            '        value='+value.id +
            '        data-numberOfList='+numberOfList +
            '        type="button">График\n' +
            '</button>';
    }

    function getStringList(data) {
        console.log(data);
        var ans = "";
        $.each(data, function (index, value) {
            ans += '<div>' + value.name + '  ' + value.authorName + '  ' + value.amount +'  заказа  '+getBtn(value)+ '  </div>';
        });
        return ans;
    }

    function borderedBtn() {
        $('.js-numberOfList').css({border: 'none'});
        $(item).css({border: '2px solid blue'});

    }

    function insertListOfTopSongs(data) {
        borderedBtn();
        $('.js-textSongDelete div').remove();
        var insert = getStringList(data);
        alert('вывод списка')
        $('#js-textSongDelete').append(insert);
    }

    function getListSongAjax() {
        $.ajax({
            contentType: "application/json;",
            url: "/api/music/getTopSongs/" + numberOfList,
            type: "POST",
            //      data: JSON.stringify(newUser),
            //       async: true,
            cache: false,
            success: function (data) {
                alert(data);
                console.log(data);
                insertListOfTopSongs(data);
              //  numberOfList = numberOfListIt;
            },
            error: function () {
                alert("Не удалось получить записи песен");
            }
        });
    }

    $(document).on('click touchstart', '.js-numberOfList', function () {
        $(this).css({border: '2px solid red'});
        numberOfList = $(this).val();
        alert(numberOfList);
        item = this;
        getListSongAjax();

    });
});
