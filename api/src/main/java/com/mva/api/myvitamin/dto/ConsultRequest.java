package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ConsultRequest {

    // 컨설팅 유형 (1: 신규 추천 / 2: 기존 영양제 컨설팅)
    private String type;

    // 복용 중인 영양제
    private String supplement;

    // 증상
    private String symptom;

    // 특이사항
    private String comment;
}
