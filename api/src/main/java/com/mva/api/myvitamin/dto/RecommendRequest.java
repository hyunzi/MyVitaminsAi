package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendRequest {

    // 사용자 세션키
    @Nullable
    private String sessionKey;
}
