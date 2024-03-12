package edu.eci.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RRInvoker {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final List<Integer> PORTS = Arrays.asList(35001, 35002, 35003);
    private static final List<String> DOMAINS = Arrays.asList("logservice1", "logservice2", "logservice3");
    private static final List<String> LOG_SERVICE_URLS = generateUrls();
    private static final Logger LOGGER = LoggerFactory.getLogger(RRInvoker.class);
    private static int currentLogService = 0;


    private RRInvoker() {
    }


    private static List<String> generateUrls() {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < PORTS.size(); i++) {
            urls.add("http://" + DOMAINS.get(i) + ":" + PORTS.get(i) + "/logservice");
        }
        return urls;
    }


    public static String invoke(String message) throws IOException {
        URL logService = getLogServiceUrl(message);
        LOGGER.info("GET {}", logService);
        HttpURLConnection con = (HttpURLConnection) logService.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        LOGGER.info("GET Response Code :: {}", responseCode);

        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            LOGGER.info("Response {}", response);
        } else {
            LOGGER.info("GET request not worked");
        }
        LOGGER.info("GET DONE");
        return response.toString();
    }


    private static URL getLogServiceUrl(String message) throws MalformedURLException {
        // Get the URL for the current index
        String getUrl = LOG_SERVICE_URLS.get(currentLogService);
        // Increment the index and wrap it around if it reaches the end of the list
        currentLogService = (currentLogService + 1) % LOG_SERVICE_URLS.size();
        return new URL(getUrl + "?message=" + message);
    }

}
