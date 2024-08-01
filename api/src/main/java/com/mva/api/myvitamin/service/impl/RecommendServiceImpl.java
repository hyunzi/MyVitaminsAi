package com.mva.api.myvitamin.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.*;
import com.mva.api.myvitamin.service.RecommendService;
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
public class RecommendServiceImpl implements RecommendService {

    private static final String COLLECTION_NAME = "myVitaminAi";
    private final GeminiServiceImpl geminiService;
    private final MergeJSONServiceImpl combineService;

    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {
        UserEnum userType = (recommendRequest.getSessionKey() != null) ? UserEnum.EXISTING_USER : UserEnum.NEW_USER;
        List<Supplement> supplementList;

        if (userType == UserEnum.EXISTING_USER) {
            supplementList = getSupplementsForExistingUser(recommendRequest.getSessionKey());
        } else {
            supplementList = getRecommendationsForNewUser();
        }

        return RecommendResponse.builder()
                .type(userType.getTermsValue())
                .supplements(supplementList)
                .build();
    }

    private List<Supplement> getSupplementsForExistingUser(String sessionKey) {
        // todo db select
        // 기존 사용자의 세션 키를 이용해 데이터를 조회하고 결과를 리턴하는 로직 구현
        // 예시:
        // return getSupplementsFromDatabase(sessionKey);
        return new ArrayList<>(); // Placeholder for actual implementation
    }

    private List<Supplement> getRecommendationsForNewUser() {
        // todo db select으로 수정 필요 -> 서버 시작 시, insert된 제미나이 response 값 return
        String question = "현대 사회인들에게 필수적이고 선호되는 영양제를 순위를 매겨 10개 알고 싶어.\n" +
                "영양제 명, 영양제 효능, 복용하면 좋은 시간대, 주의사항을 list로 정리해서 JSON구조로 알려주는데,\n" +
                "각각 name, effect, time, caution 이라는 Key로 매겨주고 value값들은 한글로 줘. 그리고 이 list의 이름은 supplements야.";
        String answer = getGeminiResponse(question);

        return parseSupplementsFromJson(answer);
    }

    private String getGeminiResponse(String question) {
        long start = System.currentTimeMillis();
        String answer = geminiService.getCompletion(question);
        log.info("Recommend 소요 시간 : {}초", (System.currentTimeMillis() - start) / 1000);

        if (answer != null) {
            answer = answer.replaceAll("(?s)```(?:json|JSON)?\\s*", "");
            answer = answer.replaceFirst("(?s)```\\s*$", "");
        }
        return answer;
    }

    private List<Supplement> parseSupplementsFromJson(String answer) {
        List<Supplement> supplementList = new ArrayList<>();

        try {
            if (answer != null && answer.trim().startsWith("{")) {
                JSONObject jsonObject = new JSONObject(answer);
                JSONArray supplements = jsonObject.optJSONArray(JSONConstants.SUPPLIEMENTS);

                if (supplements != null) {
                    long start = System.currentTimeMillis();
                    supplementList = this.combineService.combineData(supplements);
                    log.info("Recommend - JSON 가공 및 이미지 URL 처리 시간 : {}초", (System.currentTimeMillis() - start) / 1000);
                }
            } else {
                log.error("Invalid JSON response :: {}", answer);
            }
        } catch (Exception e) {
            log.error("Error parsing JSON response", e);
        }

        return supplementList;
    }

    @Override
    public RecommendResponse getRecommendationsTest(RecommendRequest recommendRequest) {
        List<Supplement> supplements = createMockSupplements();

        if (recommendRequest.getSessionKey() != null) {
            addData(SessionInfo.builder()
                    .sessionKey(recommendRequest.getSessionKey())
                    .supplements(supplements)
                    .build());
        }

        return RecommendResponse.builder()
                .type("2")
                .supplements(supplements)
                .build();
    }

    private List<Supplement> createMockSupplements() {
        List<Supplement> supplements = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            supplements.add(Supplement.builder()
                    .name("Vitamin A")
                    .effect(List.of("effect1", "effect2"))
                    .time("식사와 함께")
                    .caution(List.of("Caution 1", "Caution 2"))
                    .imgUrl("https://example.com/vitamin_a.jpg")
                    .build());
        }

        return supplements;
    }

    public void addData(SessionInfo sessionInfo) {
        Firestore FIRE_STORE = FirestoreClient.getFirestore();
        String COLLECTION_NAME = "myVitaminAi";

        try {
            Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("sessionKey", sessionInfo.getSessionKey());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            log.info(querySnapshot.get().toString());

            DocumentReference document = null;
            if (true) {// isNotExistEmail(querySnapshot)) {
                document = FIRE_STORE.collection(COLLECTION_NAME).document();
                sessionInfo.setId(document.getId());
                document.set(sessionInfo);
                log.info("새로운 문서가 추가되었습니다. document ID: {}", document.getId());
            } else {
                throw new RuntimeException("이미 가입된 이메일입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error add data to firebase" + e.getMessage());
        }
    }
}
