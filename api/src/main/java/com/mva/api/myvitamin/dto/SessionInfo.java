package com.mva.api.myvitamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SessionInfo {

    // firebase Id
    private String id;

    // 사용자별 sessionKey
    private String sessionKey;

    // 영양제 리스트
    private List<Supplement> supplements;
}
