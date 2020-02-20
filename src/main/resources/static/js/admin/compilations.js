// $(document).ready(function () {
//     getTable();
// });
//
// function getTable() {
//     $.ajax({
//         method: "GET",
//         url: "/api/admin/compilation",
//         contentType: "application/json",
//         headers: {
//             "Accept": "application/json",
//             "Content-Type": "application/json"
//         },
//         dataType: "JSON",
//         success: function (compilations) {
//             let tableBody = $("#genresTable tbody");
//
//             tableBody.empty();
//             for (let i = 0; i < genres.length; i++) {
//                 // vars that contains object's fields
//                 let id = genres[i].id;
//                 let name = genres[i].name;
//                 // parsing fields
//                 let tr = $("<tr/>");
//                 tr.append(`
//                             <td> ${id} </td>
//                             <td> ${name} </td>
//                             <td>
//                                 <button type="submit"
//                                         class="btn btn-sm btn-info"
//                                         id="editGenreBtn"
//                                         onclick="editButton(${id}, '${name}')">
//                                     Изменить
//                                 </button>
//                             </td>
//                             <td>
//                                 <button type="button"
//                                         class="btn btn-sm btn-info"
//                                         id="deleteGenreBtn"
//                                         onclick="deleteButton(${id})">
//                                     Удалить
//                                 </button>
//                             </td>`);
//                 tableBody.append(tr);
//             }
//         }
//     });
// }