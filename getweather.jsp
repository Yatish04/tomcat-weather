<html>
<head>
<title>WeatherInfo</title>
<script>
function sendreceive(json){
    var http = new XMLHttpRequest();
    var url = '/weather/getweather';
    http.open('POST', url, false);

    http.setRequestHeader('Content-type', 'application/json');

    http.onreadystatechange = function() {
    if(http.readyState == 4 && http.status == 200) {
        var response = JSON.parse(http.responseText);
        console.log(response)
        if(response.status=="200"){
            delete response.status
            return response
        } else{
            alert("Something wrong! Try again")
            return null
        }
    }
    }
    
    http.send(JSON.stringify(json));
    return http.onreadystatechange();
}

    function preloadFunc()
    {
        var lat =  '<%= request.getParameter("lat")%>';
        var long =  '<%= request.getParameter("long")%>';
        var json={lat:lat,long:long}
        var res = sendreceive(json)
        if(res!=null){
            console.log(document.getElementById("temp"));
            document.getElementById("temp").innerHTML=res.temp;
            document.getElementById("tmax").innerHTML=res.tmax;
            document.getElementById("tmin").innerHTML=res.tmin;
            document.getElementById("press").innerHTML=res.pressure;
            document.getElementById("flike").innerHTML=res.flike;
            document.getElementById("hum").innerHTML=res.humidity;
            document.getElementById("desc").innerHTML=res.description;
        }
    }
    // window.onload = preloadFunc();


</script>

</head>
<body bgcolor=white onload="preloadFunc()">
<div>
    <h3>WeatherInfo:</h3>
    <p>The weather information is as below for the city <b> <%= request.getParameter("city") %> :</b></p>
</div>
<div>
<table  border="1" style="border-collapse: collapse; position: relative; left: 10px;" >

    <tr><td>City:</td><td id="city"><%= request.getParameter("city") %></td></tr>
    
    <tr ><td >Latitude:</td><td id="lat"><%= request.getParameter("lat") %></td></tr>
    <tr><td>Longitude:</td><td id="long"><%= request.getParameter("long") %></td></tr>
    
    <tr><td>Temperature:</td><td id="temp"></td></tr>
    <tr ><td >Feels Like:</td><td id="flike"></td></tr>
    <tr><td>Temperature Min:</td><td id="tmin"></td></tr>
    <tr><td>Temperature Max:</td> <td id="tmax"></td> </tr>
    <tr><td>Pressure:</td> <td id="press"></td> </tr>
    <tr><td>Humidity:</td> <td id="hum"></td> </tr>
    <tr><td>Description:</td> <td id="desc"></td> </tr>
</table>
</div>

</body>
</html>

