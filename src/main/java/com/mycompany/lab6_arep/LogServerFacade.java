/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6_arep;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class LogServerFacade {
   private static final String LOG_SERVICE_URL = "http://localhost:5000/logservice";
    public static void main(String[] args){
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(LOG_SERVICE_URL);
        port(4657);
        staticFiles.location("/public");
        get("/logservice", (req,res) -> {
            res.type("application/json");
            return invoker.Invoke(args);
        });
    }
}


