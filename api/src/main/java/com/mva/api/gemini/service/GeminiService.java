package com.mva.api.gemini.service;

import com.mva.api.gemini.dto.GeminiRequest;
import com.mva.api.gemini.dto.GeminiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("v1beta/models/")
public interface GeminiService {
    @PostExchange("{model}:generateContent")
    GeminiResponse getCompletion(@PathVariable String model, @RequestBody GeminiRequest request);
}
