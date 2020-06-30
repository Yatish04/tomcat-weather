import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.stream.Collectors;

public class WeatherAPI  extends HttpServlet{

    public JSONObject getData(String lat,String lon){

        String api="https://api.openweathermap.org/data/2.5/weather";
        String token = "b6f17e1d239fec9815bc35e4dd54ec53";

        String url=api+"?lat="+lat+"&lon="+lon+"&appid="+token;
        JSONObject result = new JSONObject();
        try {
            URL u = new URL(url);
            System.out.println("jebh");
            HttpsURLConnection conn =  (HttpsURLConnection)u.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("charset", "utf-8");
            conn.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String out="";
            while ((line = rd.readLine()) != null) {
                out+=line;
            }
            JSONObject ob = new JSONObject(out);
            JSONArray desc = ob.getJSONArray("weather");
            result.put("description",desc.getJSONObject(0).getString("description"));
            JSONObject main = ob.getJSONObject("main");
            System.out.println(ob.toString());
            result.put("tmin",ob.getJSONObject("main").getDouble("temp_min"));
            result.put("tmax",main.getDouble("temp_max"));
            result.put("flike",ob.getJSONObject("main").getDouble("feels_like"));
            result.put("temp",ob.getJSONObject("main").getDouble("temp"));
            result.put("pressure",ob.getJSONObject("main").getDouble("pressure"));
            result.put("humidity",ob.getJSONObject("main").getDouble("humidity"));

        } catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }



    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        System.out.println(requestData);
        response.setContentType("application/json");
        JSONObject resp = new JSONObject();
        try {
            JSONObject data = new JSONObject(requestData);
            String lat = data.getString("lat");
            String lon = data.getString("long");
            JSONObject result = getData(lat,lon);

            if(result.length()>0){
                result.put("status","200");
                resp=result;

            } else {
                resp.put("status","400");
            }

        } catch (Exception e){
            try {
                resp.put("status", "500");
                resp.put("message",e.toString());
            } catch (Exception ex){
                System.out.println("Some error");
            }
        }
        PrintWriter pw = response.getWriter();
        pw.write(resp.toString());
    }
}
