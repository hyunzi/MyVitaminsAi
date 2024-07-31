package com.mva.api.myvitamin.service.impl;

import com.mva.api.googleImage.service.impl.GoogleImageServiceImpl;
import com.mva.api.myvitamin.dto.JSONConstants;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.MergeJSONService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MergeJSONServiceImpl implements MergeJSONService {

    private final GoogleImageServiceImpl googleImageServiceImpl;
    private final RestTemplate restTemplate;
    @Override
    public List<Supplement> combineData(JSONArray supplements) {
        List<Supplement> supplementList = new ArrayList<>();

        for (int i = 0; i < supplements.length(); i++) {
            JSONObject supplementJson = supplements.getJSONObject(i);

            String name = supplementJson.getString(JSONConstants.NAME);
            String imgUrl = this.retrieveImageUrl(name);
            supplementJson.put("imgUrl", imgUrl != null ? imgUrl : "");

            supplementJson.put(JSONConstants.EFFECT, convertToJsonArray(supplementJson.get("effect")));
            supplementJson.put(JSONConstants.CAUTION, convertToJsonArray(supplementJson.get("caution")));

            Supplement supplement = Supplement.builder()
                    .name(supplementJson.getString(JSONConstants.NAME))
                    .effect(jsonArrayToList(supplementJson.getJSONArray(JSONConstants.EFFECT)))
                    .time(supplementJson.getString(JSONConstants.TIME))
                    .caution(jsonArrayToList(supplementJson.getJSONArray(JSONConstants.CAUTION)))
                    .imgUrl(supplementJson.getString(JSONConstants.IMG_URL))
                    .build();

            supplementList.add(supplement);
        }
        return supplementList;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    private JSONArray convertToJsonArray(Object value) {
        if (value instanceof String) {
            return parseStringToJsonArray((String) value);
        } else if (value instanceof JSONArray) {
            return (JSONArray) value;
        } else {
            return new JSONArray();
        }
    }

    private JSONArray parseStringToJsonArray(String value) {
        JSONArray jsonArray = new JSONArray();
        String[] items = value.split(",\\s*");

        int limit = Math.min(items.length, 2);
        for (int i = 0; i < limit; i++) {
            String item = items[i].trim();
            if (!item.isEmpty()) {
                jsonArray.put(item);
            }
        }

        return jsonArray;
    }

    private String retrieveImageUrl(String name) {
        String imgUrl;
        try {
            String imgSearchUrl = googleImageServiceImpl.getImgSearchUrl(name);
            String imgJsonData = restTemplate.getForObject(imgSearchUrl, String.class);
            imgUrl = googleImageServiceImpl.getImgUrl(imgJsonData);
        } catch (Exception ex) {
            imgUrl = "error-image";
        }

        return imgUrl;
    }
}
