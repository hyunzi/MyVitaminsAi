package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.*;
import com.mva.api.myvitamin.repository.SupplementRepository;
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
    private final SupplementRepository supplementRepository;

    @Override
    public ConsultResponse consulting(ConsultRequest consultRequest, String sessionId) throws Exception {
        String sessionKey = (consultRequest.getSessionKey() != null ? consultRequest.getSessionKey() : sessionId);
        String question = getQuestion(consultRequest);
        String answer = getGeminiResponse(question);
        return parseResponse(answer, consultRequest.getType(), sessionKey);
    }

    private String getQuestion(ConsultRequest consultRequest) {
        ConsultEnum consultType = ConsultEnum.fromTermsValue(consultRequest.getType());
        String symptom = consultRequest.getSymptom();
        String comment = consultRequest.getComment();
        String supplements = consultRequest.getSupplement();

        StringBuilder question = new StringBuilder();
        question.append("아래 ")
                .append(consultType == ConsultEnum.NEW_CONSULT ? "증상과 특이사항" : "기존 복용하고 있는 영양제와 증상, 특이사항")
                .append("을 참고해서 영양제를 추천해줘.\n\n");

        if (consultType == ConsultEnum.EXISTING_CONSULT) {
            question.append("복용하고 있는 영양제들: ").append(supplements).append("\n");
        }

        question.append("증상: ").append(symptom).append("\n");
        question.append("특이사항: ").append(comment).append("\n\n")
                .append("영양제들 간의 조합을 고려해 알려주는데,\n")
                .append("영양제 명, 영양제 효능, 복용하면 좋은 시간대, 주의사항을 list로 정리해서 JSON구조로 알려주고\n")
                .append("추가로, 영양제 조합과 제외시킨 영양제가 있다면 그 이유, 복용 시 주의사항 및 추가조언을 알려줘.\n\n")
                .append("먼저 list 안에 있는 것들은 각각 name, effect, time, caution 이고 list이름은 supplements야.\n")
                .append("그리고 추가 데이터는 각각 reason, totalOpinion으로 알려줘.");

        log.info("question.toString() :: -> {},",question.toString());
        return question.toString();
    }

    private String getGeminiResponse(String question) {
        long start = System.currentTimeMillis();
        String answer = geminiService.getCompletion(question);
        log.info("Consult 소요 시간 : {}초", (System.currentTimeMillis() - start) / 1000);

        if (answer != null) {
            answer = answer.replaceAll("(?s)```(?:json|JSON)?\\s*", "");
            answer = answer.replaceFirst("(?s)```\\s*$", "");
        }
        return answer;
    }

    private ConsultResponse parseResponse(String answer, String type, String sessionKey) throws Exception {
        try {
            if(answer == null || !answer.trim().startsWith("{")) {
                log.error("Invalid JSON response :: " + answer);
                throw new Exception("Invalid JSON response :: " + answer);
            }

            long start = System.currentTimeMillis();
            JSONObject jsonObject = new JSONObject(answer);

            JSONArray supplementsArray = jsonObject.getJSONArray(JSONConstants.SUPPLIEMENTS);
            List<Supplement> supplementList = combineService.combineData(supplementsArray != null ? supplementsArray : new JSONArray());

            String reason = jsonObject.get(JSONConstants.REASON).toString();
            reason = reason.replace("\\", "");
            String totalOpinion = jsonObject.get(JSONConstants.TOTAL_OPINION).toString();
            totalOpinion = totalOpinion.replace("\\", "");

            System.out.println("Consult - json 가공 및 imageUrl 소요 시간 : "+(System.currentTimeMillis() - start)/1000);

            supplementRepository.addData(SessionInfo.builder()
                    .sessionKey(sessionKey)
                    .supplements(supplementList)
                    .build());

            return ConsultResponse.builder()
                    .type(type)
                    .supplements(supplementList)
                    .reason(reason)
                    .totalOpinion(totalOpinion)
                    .build();
        } catch (Exception ex) {
            log.error("Error parsing JSON response", ex);
            throw ex;
            //return createErrorResponse(type);
        }
    }

    private ConsultResponse createErrorResponse(String type) {
        return ConsultResponse.builder()
                .type(type)
                .supplements(new ArrayList<>())
                .reason("Error occurred while processing the request.")
                .totalOpinion("Please try again later.")
                .build();
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
