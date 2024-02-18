package org.talenthub.module.xp.service.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreatureAPIService {

    private final String BASE_URL = "http://host/api/v1";

    private final OkHttpClient httpClient = new OkHttpClient();

    public int getRandomRace(){
        Request request = new Request.Builder()
                .url(BASE_URL + "/randomrace")
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println(responseBody);
                //return JsonParser.parseString(responseBody).getAsJsonObject();
            } else {
                throw new IOException("Failed to fetch stream info: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 0;

    }

}
