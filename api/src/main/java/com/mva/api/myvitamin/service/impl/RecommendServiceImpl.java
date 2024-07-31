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

    private final GeminiServiceImpl geminiService;
    private final MergeJSONServiceImpl combineService;

    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {
        UserEnum userType = (recommendRequest.getSessionKey() != null) ? UserEnum.EXISTING_USER : UserEnum.NEW_USER;
        List<Supplement> supplementList = new ArrayList<>();

        // validation check

        if (userType == UserEnum.EXISTING_USER) {
            //기존 사용자 데이터 가져오는 경우
            //세션키로 USER_VITAMIN_TABLE(사용자 영양제 내역 테이블) 조회

            //정보가 있는 경우 RecommendResponse 세팅하여 반환
        }
        else {
            //신규 사용자 데이터 가져오는 경우
            StringBuilder question = new StringBuilder();
            question.append("현대 사회인들에게 필수적이고 선호되는 영양제를 순위를 매겨 10개 알고 싶어.\n" +
                    "영양제 명, 영양제 효능, 복용하면 좋은 시간대, 주의사항을 list로 정리해서 JSON구조로 알려주는데,\n" +
                    "각각 name, effect, time, caution 이라는 Key로 매겨주고 value값들은 한글로 줘. 그리고 이 list의 이름은 supplements야.");

            long start = System.currentTimeMillis();
            String answer = geminiService.getCompletion(question.toString());
            System.out.println("Recommend 소요 시간 : "+(System.currentTimeMillis() - start)/1000);
            answer = answer.replaceFirst("(?i)```json\\s*\\{", "{");

            if (answer != null && answer.trim().startsWith("{")) {
                long start2 = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(answer);
                JSONArray supplements = jsonObject.getJSONArray(JSONConstants.SUPPLIEMENTS);
                supplementList = this.combineService.combineData(supplements);
                System.out.println("Recommend - json 가공 및 imageUrl 소요 시간 : "+(System.currentTimeMillis() - start2)/1000);
            } else {
                log.error("Invalid JSON response :: "+answer);
            }
        }

        return RecommendResponse.builder()
                .type(userType.getTermsValue())
                .supplements(supplementList)
                .build();
    }

    /* just return mock data */
    @Override
    public RecommendResponse getRecommendationsTest(RecommendRequest recommendRequest) {

        List<Supplement> supplements = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            supplements.add(Supplement.builder()
                    .name("Vitamin A")
                    .effect(List.of("effect1", "effect2"))
                    .time("식사와 함께")
                    .caution(List.of("Caution 1", "Caution 2"))
                    .imgUrl("https://example.com/vitamin_a.jpg")
                    .build());
        }

        RecommendResponse response = RecommendResponse.builder()
                .type("2")
                .supplements(supplements)
                .build();

        // FIXME sessionKey 없을 때 대응 필요
//        addData(SessionInfo.builder()
//                .sessionKey(recommendRequest.getSessionKey())
//                .supplements(supplements)
//                .build());
        return response;
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
