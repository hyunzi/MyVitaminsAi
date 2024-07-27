package com.mva.api.myvitamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Supplement {

    // 순번
    private String id;

    // 영양제명
    private String name;

    // 효능
    private List<String> effect;

    // 복용 시간
    private String time;

    // 주의사항
    private List<String> caution;

    // 이미지 Url
    private String imgUrl;
}
