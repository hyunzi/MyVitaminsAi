package com.mva.api.myvitamin.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.dto.SessionInfo;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.RecommendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final GeminiServiceImpl geminiService;

    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {

        //validation check


        //세션키로 USER_VITAMIN_TABLE(사용자 영양제 내역 테이블) 조회


        //정보가 있는 경우 RecommendResponse 세팅하여 반환


        //정보가 없는 경우 Gemini에게 인기 영양제 TOP N개 조회 요청
        String question = "인기 영양제 TOP N개 알려줘!";
        geminiService.getCompletion(question);

        //TOP N개 정보 순회하며 Image Url 조회


        //RecommendResponse 세팅하여 반환


        return RecommendResponse.builder()
                .type("1")
                .build();
    }

    /* just return mock data */
    @Override
    public RecommendResponse getRecommendationsTest(RecommendRequest recommendRequest) {

        List<Supplement> supplements = new ArrayList<>();
        supplements.add(Supplement.builder()
                .name("Vitamin A")
                .effect(List.of("effect1", "effect2"))
                .time("식사와 함께")
                .caution(List.of("Caution 1", "Caution 2"))
                .imgUrl("https://example.com/vitamin_a.jpg")
                .build());

        RecommendResponse response = RecommendResponse.builder()
                .type("2")
                .supplements(supplements)
                .build();

        addData(SessionInfo.builder()
                .sessionKey(recommendRequest.getSessionKey())
                .supplements(supplements)
                .build());
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
