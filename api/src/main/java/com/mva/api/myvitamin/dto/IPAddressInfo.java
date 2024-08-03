package com.mva.api.myvitamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IPAddressInfo {

    // firebase Id
    private String id;

    // 사용자별 IP
    private String ipAddress;

    // 영양제 리스트
    private List<Supplement> supplements;
}
