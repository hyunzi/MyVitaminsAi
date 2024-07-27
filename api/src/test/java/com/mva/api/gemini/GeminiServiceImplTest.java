package com.mva.api.gemini;

import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeminiServiceImplTest {
    @Autowired
    private GeminiServiceImpl service;

    @Test
    void getCompletion() {
        String text = service.getCompletion("현재 시각으로부터 세 달 전까지의 기간동안 현대 사회인들이 많이 복용하는 영양제 5가지를 알려주는데,\n" +
                "순위, 영양제 이름, 사용가능한 이미지 url을 배열로 보여줘");
        //service.getCompletion("피로감이 있고 무릎이 자주 붓는데 좋은 영양제들을 추천해주는데 조합까지 생각해서 추천해줘");
        System.out.println(text);
    }
}