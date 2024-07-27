package com.mva.api.gemini.service.impl;

import com.mva.api.gemini.dto.GeminiRequest;
import com.mva.api.gemini.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl {
    public static final String GEMINI_PRO = "gemini-pro";
    public static final String GEMINI_ULTIMATE = "gemini-ultimate";
    public static final String GEMINI_PRO_VISION = "gemini-pro-vision";

    private final com.mva.api.gemini.service.GeminiService geminiService;

    @Autowired
    public GeminiServiceImpl(com.mva.api.gemini.service.GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    private GeminiResponse getCompletion(GeminiRequest request) {
        return geminiService.getCompletion(GEMINI_PRO, request);
    }

    public String getCompletion(String text) {
        GeminiRequest geminiRequest = new GeminiRequest(text);
        GeminiResponse response = getCompletion(geminiRequest);

        return response.getCandidates()
                .stream()
                .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                        .stream()
                        .findFirst()
                        .map(GeminiResponse.TextPart::getText))
                .orElse(null);
    }
}