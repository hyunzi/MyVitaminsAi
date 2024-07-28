package com.mva.api.myvitamin.service.impl;

import com.mva.api.googleImage.service.impl.GoogleImageServiceImpl;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.CombineJSONService;
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
public class CombineJSONServiceImpl implements CombineJSONService {

    private final GoogleImageServiceImpl googleImageServiceImpl;
    private final RestTemplate restTemplate;
    @Override
    public List<Supplement> combineData(JSONArray supplements) {
        List<Supplement> supplementList = new ArrayList<>();

        for (int i = 0; i < supplements.length(); i++) {
            JSONObject supplementJson = supplements.getJSONObject(i);

            //Gemini 응답 결과 순회하며 Image Url 조회
            String imgSearchUrl = googleImageServiceImpl.getImgSearchUrl(supplementJson.getString("name"));
            String imgJsonData = restTemplate.getForObject(imgSearchUrl, String.class);
            String imgUrl = googleImageServiceImpl.getImgUrl(imgJsonData);

            supplementJson.put("imgUrl", imgUrl != null ? imgUrl : "");

            Supplement supplement = Supplement.builder()
                    .name(supplementJson.getString("name"))
                    .effect(jsonArrayToList(supplementJson.getJSONArray("effect")))
                    .time(supplementJson.getString("time"))
                    .caution(jsonArrayToList(supplementJson.getJSONArray("caution")))
                    .imgUrl(supplementJson.getString("imgUrl"))
                    .build();

            //10개 정보 반환하기 위해 supplementList에 추가
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
}
