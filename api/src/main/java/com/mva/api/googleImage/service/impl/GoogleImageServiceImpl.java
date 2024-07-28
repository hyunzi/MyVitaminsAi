package com.mva.api.googleImage.service.impl;

import com.mva.api.config.GoogleImageProperties;
import com.mva.api.gemini.service.GeminiService;
import com.mva.api.googleImage.service.GoogleImageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleImageServiceImpl implements GoogleImageService {
    private final RestTemplate restTemplate;
    private final GoogleImageProperties googleImageProperties;

    @Autowired
    public GoogleImageServiceImpl(RestTemplate restTemplate, GoogleImageProperties googleImageProperties) {
        this.restTemplate = restTemplate;
        this.googleImageProperties = googleImageProperties;
    }

    public String getImgSearchUrl(String searchItem){
        String result = "https://www.googleapis.com/customsearch/v1?key="
                + googleImageProperties.getApikey()
                +"&cx="
                + googleImageProperties.getEngineId()
                +"&q="
                + searchItem;
        return result;
    }

    public String getImgUrl(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray items = jsonObject.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.has("pagemap")) {
                JSONObject pagemap = item.getJSONObject("pagemap");
                if (pagemap.has("cse_image")) {
                    JSONArray cseImageArray = pagemap.getJSONArray("cse_image");
                    if (cseImageArray.length() > 0) {
                        JSONObject cseImage = cseImageArray.getJSONObject(0);
                        if (cseImage.has("src")) {
                            return cseImage.getString("src");
                        }
                    }
                }
            }
        }

        return null; // 이미지 URL을 찾지 못한 경우
    }
}
