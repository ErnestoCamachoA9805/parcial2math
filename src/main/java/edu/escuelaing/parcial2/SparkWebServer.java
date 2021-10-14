package edu.escuelaing.parcial2;

import static spark.Spark.get;
import static spark.Spark.port;

import org.json.JSONObject;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

public class SparkWebServer {
    public static void main(String... args){
        port(getPort());

        options("/*",
        (request, response) -> {

            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        get("hello",(req, res) -> "Hello Docker!");
        get("/log", (req,res) -> mathLog(req,res));
        get("/atan", (req,res) -> mathAtan(req,res));
    }    

    private static JSONObject mathLog(Request req, Response res){
        res.type("application/json");
        JSONObject response= new JSONObject();
        response.put("operation:", "log");
        String value= req.queryParams("value");
        response.put("input:", value);
        Double number= Double.parseDouble(value);
        response.put("output:", Math.log(number));
        return response;
    }

    private static JSONObject mathAtan(Request req, Response res){
        res.type("application/json");
        JSONObject response= new JSONObject();
        response.put("operation:", "atan");
        String value= req.queryParams("value");
        response.put("input:", value);
        Double number= Double.parseDouble(value);
        response.put("output:", Math.atan(number));
        return response;
    }

    /**
     * Define el puerto de spark
     * @return
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
