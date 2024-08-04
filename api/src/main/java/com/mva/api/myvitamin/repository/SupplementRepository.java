package com.mva.api.myvitamin.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mva.api.myvitamin.dto.SessionInfo;
import com.mva.api.myvitamin.dto.Supplement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SupplementRepository {
    private static final String COLLECTION_NAME = "supplement";
    private final Firestore FIRE_STORE = FirestoreClient.getFirestore();

    public void addData(SessionInfo SessionInfo) {
        try {
            Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("sessionKey", SessionInfo.getSessionKey());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            DocumentReference document = null;
            if (true) {// isNotExistEmail(querySnapshot)) {
                document = FIRE_STORE.collection(COLLECTION_NAME).document();
                SessionInfo.setId(document.getId());
                document.set(SessionInfo);
                log.info("새로운 문서가 추가되었습니다. document ID: {}", document.getId());
            } else {
                throw new RuntimeException("이미 가입된 이메일입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error add data to firebase" + e.getMessage());
        }
    }

    public boolean findUserBySessionKey(String sessionKey) {
        Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("sessionKey", sessionKey);
        ApiFuture<QuerySnapshot> future = query.get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("문서 조회를 실패하였습니다.");
        }
    }
    public List<Supplement> findSupplementByUser(String sessionKey) {
        Query query = FIRE_STORE.collection(COLLECTION_NAME).whereEqualTo("sessionKey", sessionKey);
        ApiFuture<QuerySnapshot> future = query.get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            Map<String, Supplement> supplementMap = new HashMap<>(); // name을 키로 하는 맵
            if (!documents.isEmpty()) {
                for (QueryDocumentSnapshot document : documents) {
                    List<Map<String, Object>> supplementsData = (List<Map<String, Object>>) document.get("supplements");
                    if (supplementsData != null) {
                        for (Map<String, Object> supplementData : supplementsData) {
                            // Map에서 각 필드를 추출하여 Supplement 객체에 설정
                            Supplement supplement = new Supplement();
                            supplement.setName((String) supplementData.get("name"));
                            supplement.setEffect((List<String>) supplementData.get("effect"));
                            supplement.setTime((String) supplementData.get("time"));
                            supplement.setCaution((List<String>) supplementData.get("caution"));
                            //getBase64Image((String) supplementData.get("imgUrl"));
                            supplement.setImgUrl((String) supplementData.get("imgUrl"));
                            // name을 키로 사용하여 Supplement 객체를 Map에 저장
                            supplementMap.put(supplement.getName(), supplement);
                        }
                    }
                }
            }
            return new ArrayList<>(supplementMap.values());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("문서 조회를 실패하였습니다.");
        }
    }
}
