import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;


public class Home extends HttpServlet {

    public JSONArray getData(String city){

        String api="https://api.mapbox.com/geocoding/v5/mapbox.places";
        String token = "pk.eyJ1IjoieWF0aXNoMDQiLCJhIjoiY2tjMG9uMXBiMGF0MDJ6bGZ2aWg4NmJ0aCJ9.IJyPYcZuzGB_hQvTYNdyMw";
        String limit="1";
        String url=api+"/"+city+".json?"+"access_token="+token+"&limit="+limit;
        JSONArray latlong = new JSONArray();
        try {
            URL u = new URL(url);
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
            JSONArray features = ob.getJSONArray("features");
            if(features.length()>0) {
                JSONObject inner = new JSONObject(features.get(0).toString());
                 latlong = inner.getJSONArray("center");
                System.out.println(latlong);
            } else {
                throw new Exception("bad city");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
     return  latlong;
    }




    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestData = request.getReader().lines().collect(Collectors.joining());
        System.out.println(requestData);
        response.setContentType("application/json");
        JSONObject resp = new JSONObject();
        try {
            JSONObject data = new JSONObject(requestData);
            String city = data.getString("city");
            JSONArray latlongs = getData(city);
            if(latlongs.length()>0){
                resp.put("status","200");
                resp.put("lat",latlongs.get(1));
                resp.put("long",latlongs.get(0));

            } else {
                resp.put("status","400");
            }

        } catch (Exception e){
            try {
                resp.put("status", "500");
            } catch (Exception ex){
                System.out.println("Some error");
            }
        }
        PrintWriter pw = response.getWriter();
        pw.write(resp.toString());
    }


//    public static void main(String[] args) {
//        String city="bangalore";
//        String api="https://api.mapbox.com/geocoding/v5/mapbox.places";
//        String token = "pk.eyJ1IjoieWF0aXNoMDQiLCJhIjoiY2tjMG9uMXBiMGF0MDJ6bGZ2aWg4NmJ0aCJ9.IJyPYcZuzGB_hQvTYNdyMw";
//        String limit="1";
//        String url=api+"/"+city+".json?"+"access_token="+token+"&limit="+limit;
//        try {
//            URL u = new URL(url);
//            HttpsURLConnection conn =  (HttpsURLConnection)u.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Content-Type", "text/plain");
//            conn.setRequestProperty("charset", "utf-8");
//            conn.connect();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            String out="";
//            while ((line = rd.readLine()) != null) {
//                out+=line;
//            }
//            JSONObject ob = new JSONObject(out);
//            JSONArray features = ob.getJSONArray("features");
//            if(features.length()>0) {
//                JSONObject inner = new JSONObject(features.get(0).toString());
//                JSONArray latlong = inner.getJSONArray("center");
//                System.out.println(latlong);
//            } else {
//                throw new Exception("bad city");
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//    }

}
