package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class RecommendRequest {

    // 사용자 세션키
    private String sessionKey;
}
