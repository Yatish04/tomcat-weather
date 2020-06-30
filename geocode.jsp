<html>
<head>
<title>Geocode</title>
<script>
function sendrequest(){
    var city = document.getElementById("city").innerText;
    var lat = document.getElementById("lat").innerText;
    var long = document.getElementById("long").innerText;
    console.log(city)
    console.log(lat)
    window.location="getweather.jsp?city="+city+"&lat="+lat+"&long="+long
}

</script>

</head>
<body bgcolor=white>
<div>
    <h3>Geocode:</h3>
    <p>The geocoded information is as below. Please click on Go to find the weather at <%= request.getParameter("city") %></p>
</div>
<div>
<table  border="1" style="border-collapse: collapse; position: relative; left: 10px;" >
    <tr>
        <td>City:</td>
        <td id="city"><%= request.getParameter("city") %></td>
    </tr>
    <tr >
        <td >Latitude:</td>
        <td id="lat"><%= request.getParameter("lat") %></td>
    </tr>
    
    <tr>
        <td>
            Longitude:
        </td>
        <td id="long">
            <%= request.getParameter("long") %>
        </td>
    </tr>
    
</table>
<input onclick="sendrequest()" value="Go" type="submit" style="position:relative; left:10px; top:6px;">
</div>

</body>
</html>

