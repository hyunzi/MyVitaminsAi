package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class SessionInfo {

    // firebase Id
    private String id;

    // 세션키
    private String sessionKey;

    // 영양제 리스트
    private List<Supplement> supplements;
}
