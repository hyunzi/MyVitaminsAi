package com.mva.api.myvitamin.controller;

import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    /*
    * 추천 목록 조회 API
    * */
    @PostMapping
    public ResponseEntity<RecommendResponse> getRecommendations(HttpServletRequest request, @RequestBody RecommendRequest recommendRequest) {
        String ipAddresses = request.getRemoteAddr();
        String ipAddress = Arrays.stream(ipAddresses.split(","))  // Client IP
                .findFirst()
                .orElse("");

        return ResponseEntity.ok(recommendService.getRecommendations(recommendRequest, ipAddress));
        //return ResponseEntity.ok(recommendService.getRecommendationsTest(recommendRequest));
    }

}
