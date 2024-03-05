/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6_arep;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;



/**
 *
 * @author jose.correa-r
 */
public class LogService {
     public static void main(String... args) {
         port(5000);
        get("/logservice", (req, res) -> 
                {
                    res.type("aplication/jason");
                    return "{\"logid1\":\"20-2-2024-Log inicial\"}";
         });
                
    }

}
