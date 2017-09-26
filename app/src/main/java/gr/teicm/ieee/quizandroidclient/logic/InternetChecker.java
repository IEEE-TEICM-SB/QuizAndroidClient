package gr.teicm.ieee.quizandroidclient.logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */
class InternetChecker {
    public boolean isInternetWorking(String ServiceURL) {
        boolean status = false;
        try {
            URL url = new URL(ServiceURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            status = connection.getResponseCode() == 200;
        } catch (IOException ignored) {

        }
        return status;
    }
}
