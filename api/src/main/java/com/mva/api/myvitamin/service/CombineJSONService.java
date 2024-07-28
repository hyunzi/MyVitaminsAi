package com.mva.api.myvitamin.service;

import com.mva.api.myvitamin.dto.Supplement;
import org.json.JSONArray;

import java.util.List;

public interface CombineJSONService {

    /*
     * 추천 목록 조회 메소드
     *
     * @param recommendRequest
     * @return RecommendResponse
     * */
    public List<Supplement> combineData(JSONArray supplements);
}
