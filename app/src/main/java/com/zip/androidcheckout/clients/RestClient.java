package com.zip.androidcheckout.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Bare bones rest client implementation
 */
public abstract class RestClient {

    protected ObjectWriter jsonSerializer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    protected Gson jsonDeserializer = new Gson();

    /**
     * Posts a simple message as application/json
     *
     * @param url  to post to
     * @param data data to send
     * @param type type to deserialize response as
     * @param <T>  Response object type
     * @return response object as type `type`
     * @throws IOException on http failure
     */
    public <T> T postRequest(String url, Object data, Type type) throws IOException {
        return postRequest(url, data, type, null);
    }

    /**
     * Posts a simple message as application/json
     *
     * @param url         to post to
     * @param data        data to send
     * @param type type to deserialize response as
     * @param bearerToken optional bearer token
     * @param <T>         Response object type
     * @return response object as type `type`
     * @throws IOException on http failure
     */
    public <T> T postRequest(String url, Object data, Type type, String bearerToken) throws IOException {

        URL requestUrl = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "application/json");
        if (bearerToken != null)
            conn.setRequestProperty("Authorization", "Bearer " + bearerToken);

        try (OutputStream os = conn.getOutputStream()) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(jsonSerializer.writeValueAsString(data));

            writer.flush();
            writer.close();
        }
        int responseCode = conn.getResponseCode();

        StringBuilder jsonResponse = new StringBuilder();
        if (responseCode < 300) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonResponse.append(line);
            }
        } else {
            jsonResponse = new StringBuilder("{}");
        }

        return jsonDeserializer.fromJson(jsonResponse.toString(), type);
    }
}

