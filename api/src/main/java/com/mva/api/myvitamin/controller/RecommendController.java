package com.mva.api.myvitamin.controller;

import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    /*
    * 추천 목록 조회 API
    * */
    @RequestMapping
    public ResponseEntity<RecommendResponse> getRecommendations(@RequestBody RecommendRequest recommendRequest) {

        return ResponseEntity.ok(recommendService.getRecommendations(recommendRequest));
    }

}
