package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.*;
import com.mva.api.myvitamin.service.ConsultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final GeminiServiceImpl geminiService;
    private final MergeJSONServiceImpl combineService;

    @Override
    public ConsultResponse consulting(ConsultRequest consultRequest) {
        //Gemini 질문지 생성 및 질문
        String question = getQuestion(consultRequest);
        long start = System.currentTimeMillis();
        String answer = geminiService.getCompletion(question);
        System.out.println("Consult 소요 시간 : "+(System.currentTimeMillis() - start)/1000);
        answer = answer.replaceFirst("(?i)```json\\s*\\{", "{");

        //Gemini 응답 결과 json 으로 파싱
        String type = consultRequest.getType();
        String reason = "";
        String totalOpinion = "";
        List<Supplement> supplementList = new ArrayList<>();

        if (answer != null && answer.trim().startsWith("{")) {
            long start2 = System.currentTimeMillis();
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray supplements = jsonObject.getJSONArray(JSONConstants.SUPPLIEMENTS);
            supplementList = this.combineService.combineData(supplements);
            reason = jsonObject.get(JSONConstants.REASON).toString();
            totalOpinion = jsonObject.get(JSONConstants.TOTAL_OPINION).toString();
            System.out.println("Consult - json 가공 및 imageUrl 소요 시간 : "+(System.currentTimeMillis() - start2)/1000);
        } else {
            log.error("Invalid JSON response :: "+answer);
        }

        //Firebase 에 적재

        //리턴 값 세팅
        return ConsultResponse.builder()
                .type(type)
                .supplements(supplementList)
                .reason(reason)
                .totalOpinion(totalOpinion)
                .build();
    }

    private String getQuestion(ConsultRequest consultRequest) {
        ConsultEnum consultType = ConsultEnum.fromTermsValue(consultRequest.getType());
        StringBuilder question = new StringBuilder();

        //질문할 데이터 가공
        if (consultType == ConsultEnum.NEW_CONSULT) {
            // 신규
            question.append("아래 증상과 특이사항을 참고해서 영양제를 추천해줘.\n" +
                    "\n" +
                    "증상 : "+ consultRequest.getSymptom() + "\n" +
                    "특이사항 : "+ consultRequest.getComment() +"\n" +
                    "\n" +
                    "영양제들 간의 조합을 고려해 알려주는데,\n" +
                    "영양제 명, 영양제 효능, 복용하면 좋은 시간대, 주의사항을 list로 정리해서 JSON구조로 알려주고\n" +
                    "추가로, 영양제 조합과 제외시킨 영양제가 있다면 그 이유, 복용 시 주의사항 및 추가조언을 알려줘.\n" +
                    "\n" +
                    "먼저 list 안에 있는 것들은 각각 name, effect, time, caution 이고 list이름은 supplements야.\n" +
                    "그리고 조합과 이유 데이터는 reason, 주의사항 및 추가조언은 totalOpinion으로 알려줘");
        }
        else if (consultType == ConsultEnum.EXISTING_CONSULT) {
            // 기존
            question.append("아래 기존 복용하고 있는 영양제와 증상, 특이사항을 참고해서 영양제를 추천해줘.\n" +
                    "\n" +
                    "복용하고 있는 영양제들 :" + consultRequest.getSupplement() + "\n" +
                    "증상 : "+ consultRequest.getSymptom() + "\n" +
                    "특이사항 : "+ consultRequest.getComment() +"\n" +
                    "\n" +
                    "영양제들 간의 조합을 고려해 알려주는데,\n" +
                    "영양제 명, 영양제 효능, 복용하면 좋은 시간대, 주의사항을 list로 정리해서 JSON구조로 알려주고\n" +
                    "추가로, 영양제 조합과 제외시킨 영양제가 있다면 그 이유, 복용 시 주의사항 및 추가조언을 알려줘.\n" +
                    "\n" +
                    "먼저 list 안에 있는 것들은 각각 name, effect, time, caution 이고 list이름은 supplements야.\n" +
                    "그리고 조합과 이유 데이터는 reason, 주의사항 및 추가조언은 totalOpinion으로 알려줘");
        }
        return question.toString();
    }

    /* just return mock data */
    @Override
    public ConsultResponse consultingTest(ConsultRequest consultRequest) {

        List<Supplement> supplements = new ArrayList<>();
        supplements.add(Supplement.builder()
                .name("Vitamin A")
                .effect(List.of("effect1", "effect2"))
                .time("식사와 함께")
                .caution(List.of("Caution 1", "Caution 2"))
                .imgUrl("https://example.com/vitamin_a.jpg")
                .build());

        return ConsultResponse.builder()
                .type("1")
                .supplements(supplements)
                .reason("this is the reason")
                .totalOpinion("this is the opinion")
                .build();
    }
}
