package ru.kalinichenko.softlink_logistic.logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;

@Component
public class DistanceCalculator {

    @Value("${google.api.key}")
    private static String API_KEY;

    public static double getDistance(String origin, String destination) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="
                + origin + "&destinations=" + destination + "&key=" + API_KEY;
        try {
            URLConnection connection = URI.create(url).toURL().openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray rows = json.getJSONArray("rows");
            JSONObject row = rows.getJSONObject(0);
            JSONArray elements = row.getJSONArray("elements");
            JSONObject element = elements.getJSONObject(0);
            JSONObject distance = element.getJSONObject("distance");
            return distance.getDouble("value") / 1000.0; // расстояние в километрах
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return -1.0;
        }
    }
}
