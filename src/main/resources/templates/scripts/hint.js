//
//
// function showHint(str) {
//     if (str.length == 0) {
//         document.getElementById("txtHint").innerHTML = "";
//         return;
//     } else {
//         var xmlhttp = newXMLHttpRequest();
//         xmlhttp.onreadystatechange = function () {
//             if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
//                 document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
//             }
//         }
//         xmlhttp.open("GET", "ajax.php?q=" + str, true);
//         xmlhttp.send();
//     }
// }

function getPlanetInfo(planetName) {
    $.get('/api/planets/' + planetName, function (data) {
        console.log(data)

        var table = "" +
            "<table border='1'>" +
            "<tr>" +
            "<td>" + "Name" + "</td>" +
            "<td>" + data.name + "</td>" +
            "</tr>" +
            "<td>" + "Rotation period" + "</td>" +
            "<td>" + data.rotation_period + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Orbital period" + "</td>" +
            "<td>" + data.orbital_period + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Diameter" + "</td>" +
            "<td>" + data.diameter + "</td>" +
            "/<tr>" +
            "<tr>" +
            "<td>" + "Climate" + "</td>" +
            "<td>" + data.climate + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Gravity" + "</td>" +
            "<td>" + data.gravity + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Terrain" + "</td>" +
            "<td>" + data.terrain + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Surface water" + "</td>" +
            "<td>" + data.surface_water + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Population" + "</td>" +
            "<td>" + data.population + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Residents" + "</td>" +
            "<td>" + data.residents + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Films" + "</td>" +
            "<td>" + data.films + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Created" + "</td>" +
            "<td>" + data.created + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" + "Edited" + "</td>" +
            "<td>" + data.edited + "</td>" +
            "<tr>" +
            "<tr>" +
            "<td>" + "Url" + "</td>" +
            "<td>" + data.url + "</td>" +
            "</tr>" +
            "</table>"

        $("#planet").html(table);
    })
}