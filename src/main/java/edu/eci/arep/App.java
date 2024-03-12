package edu.eci.arep;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;


public class App {


    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        get("/log", (req, res) -> RRInvoker.invoke(req.queryParams("message")));
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }

}
