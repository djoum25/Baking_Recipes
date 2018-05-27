package com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpHelper {

    public static String connectToUrl (String urlAdress) throws IOException {

    /*
        this snippet (of code) is the
        work of David Gassner and can be found at the following address
        git.io/v13pg
    */

        InputStream stream = null;
        try {
            URL url = new URL(urlAdress);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Response code is: " + responseCode);
            }
            stream = connection.getInputStream();
            return readTheReturnStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return null;
    }

    private static String readTheReturnStream (InputStream stream) {
        try {
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
