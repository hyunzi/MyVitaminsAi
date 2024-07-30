package com.mva.api.myvitamin.service;

import com.mva.api.myvitamin.dto.Supplement;
import org.json.JSONArray;

import java.util.List;

public interface CombineJSONService {

    /*
     * Gemini 응답 JSON과 Google Custom Search IMAGE URL 가공
     *
     * @param supplements
     * @return JSONArray
     * */
    public List<Supplement> combineData(JSONArray supplements);
}
