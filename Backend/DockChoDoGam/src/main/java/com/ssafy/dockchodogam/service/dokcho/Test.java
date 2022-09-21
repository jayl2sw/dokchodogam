package com.ssafy.dockchodogam.service.dokcho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Test {
    private static String base64EncodeFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        String res = Base64.getEncoder().encodeToString(bytes);
        fis.close();
        return res;
    }

    public static String sendPostRequest(String urlString, JSONObject data) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes());
        os.close();

        InputStream is = con.getInputStream();
        byte[] bytes = new byte[(int) is.toString().length()];
        is.read(bytes);
        String response = new String(bytes);

        System.out.println("Response code : " + con.getResponseCode());
        System.out.println("Response : " + response);
        con.disconnect();
        return response;
    }

    public static void main(String[] args) throws Exception{
        String apiKey = "8Yab0RFu91HES7019iRjYTcWYhZDDAKSFdtWUZGnxNO6BIs4Tn";


        // read image from local file system and encode
        String path = "/test.jpg";

        JSONObject data = new JSONObject();
        data.put("api_key", apiKey);

        // add images
        JSONArray images = new  JSONArray();
        String fileData = base64EncodeFromFile(path);
        images.put(fileData);

        data.put("images", images);

        // add modifiers
        // modifiers info: https://github.com/flowerchecker/Plant-id-API/wiki/Modifiers
        JSONArray modifiers = new JSONArray()
                .put("crops_fast")
                .put("similar_images");
        data.put("modifiers", modifiers);

        // add language
        data.put("plant_language", "ko");

        // add plant details
        // more info here: https://github.com/flowerchecker/Plant-id-API/wiki/Plant-details
        JSONArray plantDetails = new JSONArray()
                .put("common_names")
                .put("url")
                .put("name_authority")
                .put("wiki_description")
                .put("taxonomy")
                .put("synonyms");
        data.put("plant_details", plantDetails);

        sendPostRequest("https://api.plant.id/v2/identify", data);

    }
}
