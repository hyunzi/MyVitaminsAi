package com.mva.api.myvitamin.service;

import com.mva.api.myvitamin.dto.ConsultRequest;
import com.mva.api.myvitamin.dto.ConsultResponse;
import org.springframework.stereotype.Service;

public interface ConsultService {

    /*
    * 컨설팅 요청 메소드
    *
    * @param consultRequest
    * @return ConsultResponse
    * */
    public ConsultResponse consulting(ConsultRequest consultRequest);
    public ConsultResponse consultingTest(ConsultRequest consultRequest);
}
