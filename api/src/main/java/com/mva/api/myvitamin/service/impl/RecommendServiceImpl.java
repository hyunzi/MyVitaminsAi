package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final GeminiServiceImpl geminiService;

    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {

        //validation check


        //세션키로 USER_VITAMIN_TABLE(사용자 영양제 내역 테이블) 조회


        //정보가 있는 경우 RecommendResponse 세팅하여 반환


        //정보가 없는 경우 Gemini에게 인기 영양제 TOP N개 조회 요청
        String question = "인기 영양제 TOP N개 알려줘!";
        geminiService.getCompletion(question);

        //TOP N개 정보 순회하며 Image Url 조회


        //RecommendResponse 세팅하여 반환


        return RecommendResponse.builder()
                .type("1")
                .build();
    }

    /* just return mock data */
    @Override
    public RecommendResponse getRecommendationsTest(RecommendRequest recommendRequest) {

        List<Supplement> supplements = new ArrayList<>();
        supplements.add(Supplement.builder()
                .id("1")
                .name("Vitamin A")
                .effect(List.of("effect1", "effect2"))
                .time("식사와 함께")
                .caution(List.of("Caution 1", "Caution 2"))
                .imgUrl("https://example.com/vitamin_a.jpg")
                .build());

        return RecommendResponse.builder()
                .type("2")
                .supplements(supplements)
                .build();
    }
}
