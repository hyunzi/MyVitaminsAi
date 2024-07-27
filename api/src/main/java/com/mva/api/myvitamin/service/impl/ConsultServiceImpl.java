package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.GeminiService;
import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.ConsultRequest;
import com.mva.api.myvitamin.dto.ConsultResponse;
import com.mva.api.myvitamin.service.ConsultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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


        return null;
    }
}
