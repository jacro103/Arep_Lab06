package edu.eci.arep;

import static spark.Spark.get;
import static spark.Spark.port;


public class LogService {


    public static void main(String[] args) {
        port(getPort());
        get("/logservice", (req, res) -> saveAndGetLogs(req.queryParams("message")));
    }


    private static String saveAndGetLogs(String message) {
        MongoUtil mongoService = new MongoUtil();
        mongoService.addLog(message);
        return mongoService.getLogs().toString();
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 6000;
    }

}
