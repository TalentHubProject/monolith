package org.talenthub.module.xp.service.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class CreatureAPIService {

    private final String BASE_URL = "http://api:5106/api/v1";

    private final OkHttpClient httpClient = new OkHttpClient();

    public int getRandomRace(){
        Request request = new Request.Builder()
                .url(BASE_URL + "/randomrace")
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return Integer.parseInt(responseBody);
            } else {
                throw new IOException("Failed to fetch stream info: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public File getCreatureImage(final int raceId, final int levelId) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/creatures/" + levelId + "/" + raceId)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                byte[] imageData = response.body().bytes();

                File imageFile = File.createTempFile("creature", ".jpg");
                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    fos.write(imageData);
                }

                return imageFile;
            } else {
                throw new IOException("Failed to fetch creature image: " + response);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
