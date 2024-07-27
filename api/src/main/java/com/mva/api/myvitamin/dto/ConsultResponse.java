package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
public class ConsultResponse {

    // 컨설팅 유형 (1: 신규 추천 / 2: 기존 영양제 컨설팅)
    private String type;

    // 영양제 리스트
    private List<Supplement> supplements;

    // 조합이유
    private String reason;

    // 종합의견
    private String totalOpinion;

}
