package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.GeminiService;
import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.ConsultRequest;
import com.mva.api.myvitamin.dto.ConsultResponse;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.ConsultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final GeminiServiceImpl geminiService;

    @Override
    public ConsultResponse consulting(ConsultRequest consultRequest) {

        //validation check

        //질문할 데이터 가공
        String question = consultRequest.getSymptom();

        //Gemini 에게 질문 요청
        String answer = geminiService.getCompletion(question);

        //Gemini 응답 결과 json 으로 파싱


        //Gemini 응답 결과 순회하며 Image Url 조회


        //Firebase 에 적재


        //리턴 값 세팅


        return ConsultResponse.builder()
                .type("1")
                .build();
    }

    /* just return mock data */
    @Override
    public ConsultResponse consultingTest(ConsultRequest consultRequest) {

        List<Supplement> supplements = new ArrayList<>();
        supplements.add(Supplement.builder()
                .name("Vitamin A")
                .effect(List.of("effect1", "effect2"))
                .time("식사와 함께")
                .caution(List.of("Caution 1", "Caution 2"))
                .imgUrl("https://example.com/vitamin_a.jpg")
                .build());

        return ConsultResponse.builder()
                .type("1")
                .supplements(supplements)
                .reason("this is the reason")
                .totalOpinion("this is the opinion")
                .build();
    }
}
