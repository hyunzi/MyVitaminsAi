package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
public class RecommendResponse {

    // 추천 유형 (1: 신규 사용자 / 2: 기존 사용자)
    private String type;

    // 영양제 리스트
    private List<Supplement> supplements;

}
