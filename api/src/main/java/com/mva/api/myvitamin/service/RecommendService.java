package com.mva.api.myvitamin.service;

import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import org.springframework.stereotype.Service;

public interface RecommendService {

    /*
     * 추천 목록 조회 메소드
     *
     * @param recommendRequest
     * @return RecommendResponse
     * */
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest);
    public RecommendResponse getRecommendationsTest(RecommendRequest recommendRequest);
}
