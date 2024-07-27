package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.GeminiService;
import com.mva.api.myvitamin.dto.ConsultRequest;
import com.mva.api.myvitamin.dto.ConsultResponse;
import com.mva.api.myvitamin.service.ConsultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final GeminiService geminiService;

    @Override
    public ConsultResponse consulting(ConsultRequest consultRequest) {

        //validation check


        //Gemini 에게 질문 요청


        //Gemini 응답 결과로 Image Url 조회


        //Firebase 에 적재


        //리턴 값 세팅


        return null;
    }
}
