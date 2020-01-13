$(document).ready(function () {
    buildTable();


    //modal form заполнение
    $(document).on('click', '#editCompilationBtn', function () {

        $("#updateCompilationId").val($(this).closest("tr").find("#compilationId").text());
        $("#updateCompilationName").val($(this).closest("tr").find("#compilationName").text());
        $("#updateGenreName").val($(this).closest("tr").find("#genreName").text());

    });
});

function buildTable() {
    $.ajax({
        type : "GET",
        url : "/api/admin/all_compilations",
        contentType : "application/json",
        headers : {
            "Accept" : "application/json",
            "Content-Type" : "application/json"
        },
        async : true,
        cache : false,
        dataType : "JSON",
        success : function (data) {
            var htmlTable = "";
            $("#CompilationTable tbody").empty();
            for (var i = 0; i < data.length; i++ ){
                htmlTable += ("<tr>");
                htmlTable += ("<td id='compilationId'>" + data[i].id + "</td>");
                htmlTable += ("<td id='compilationName'>" + data[i].name + "</td>");
                htmlTable += ("<td id='genreName'>" + data[i].genre.name + "</td>");
                htmlTable += ("<td id='compilationPic'>" + data[i].compilationPic + "</td>");
                // htmlTable += ("<td><button id='changePic' class='btn btn-sm btn-info' type='button'>Изменить обложку</button></td>");
                htmlTable += ('<td><button id="editCompilationBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' + ' data-target="#editCompilation">изменить</button></td>');
                htmlTable += ("</tr>");
            }

            $("#CompilationTable tbody").append(htmlTable);
        }
    })
}

function updateCompilationPic(id) {
    if ($("#compilationPic" + id).val().length > 0) {
        var file = $("#compilationPic" + id)[0].files[0];

        $.ajax({
            type : "PUT",
            url : "/api/admin/update_compilation_pic",
            data : file,
            contentType: "application/json",
            processData: false,
            cache: false,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            complete : function () {
                    buildTable();
                },
            success : function () {
                //TODO
            },
            error : function (xhr, status, error) {
                console.log(xhr.responseText + '|\n' + status + '|\n' + error);
            }
        });
    }

}