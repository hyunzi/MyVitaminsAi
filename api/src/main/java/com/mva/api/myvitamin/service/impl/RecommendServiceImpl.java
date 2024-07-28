package com.mva.api.myvitamin.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.googleImage.service.GoogleImageService;
import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.dto.SessionInfo;
import com.mva.api.myvitamin.dto.Supplement;
import com.mva.api.myvitamin.service.RecommendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final GeminiServiceImpl geminiService;
    private final GoogleImageService googleImageService;
    private final RestTemplate restTemplate;

    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {
        List<Supplement> supplementList = new ArrayList<>();
        String type = (recommendRequest.getSessionKey() != null) ? "2" : "1";

        // validation check
        // 사용자 데이터 가져오는 경우
        if ("2".equals(type)) {
            //세션키로 USER_VITAMIN_TABLE(사용자 영양제 내역 테이블) 조회

            //정보가 있는 경우 RecommendResponse 세팅하여 반환
        } // 신규 데이터 가져오는 경우
        else {
            //질문할 데이터 가공
            String question = "현대 사회인들이 가장 많이 선호하는 영양제를 순위를 매겨 10개 알고 싶어\n" +
                    "영양제 이름 ( name ), 영양제 효능( effect -> String 배열로 보여줘. 꼭 각 요소를 큰 따옴표로 묶어줘), 복용하면 좋은 시간 대( time ), 주의사항( caution -> String 배열로 보여줘. 꼭 각 요소를 큰 따옴표로 묶어줘 ) 를 list (supplements) 로 정리해주고,\n" +
                    "\n" +
                    "모든 대답은 JSON 구조로 응답해주는데 key값으로는 괄호 안의 단어로 주고, value값은 한글로 줘.\n" +
                    "꼭 괄호 안의 단어에 해당하는 정보만 명확하게 제공해줘.\n" +
                    "예시 속 구조를 꼭 지켜줘. key값은 꼭 JSON 구조에 맞게 큰 따옴표로 감싸주고, value값은 배열일 경우, 각 요소를 큰 따옴표로 묶어서 형식에 맞춰줘\n" +
                    "\n" +
                    "예시는 아래와 같아.\n" +
                    "{\n" +
                    " \"supplements\": [\n" +
                    "  {\n" +
                    "    \"name\": \"종합비타민\",\n" +
                    "    \"effect\": [\"다양한 비타민과 미네랄 보충\", \"면역력 강화\", \"에너지 생성\"],\n" +
                    "   \"time\": \"아침 식사 후\",\n" +
                    "   \"caution\": [\"냉장 보관 필수\", \"장 기능 저하 시 복용 전 전문가 상담\"]\n" +
                    "  },\n" +
                    "...\n" +
                    "  {\n" +
                    "   \"name\": \"마그네슘\",\n" +
                    "   \"effect\": [\"근육 이완\", \"스트레스 감소\", \"혈압 조절\"],\n" +
                    "   \"time\": \"아침 식사 후\",\n" +
                    "   \"caution\": [\"냉장 보관 필수\", \"장 기능 저하 시 복용 전 전문가 상담\"]\n" +
                    "  }\n" +
                    " ]\n" +
                    "}\n" +
                    "\n" +
                    "effect 배열의 각 요소를 꼭 큰 따옴표로 묶어줘";

            //Gemini 에게 질문 요청
            String answer = geminiService.getCompletion(question);

            //Gemini 응답 결과 json 으로 파싱
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray supplements = jsonObject.getJSONArray("supplements");

            for (int i = 0; i < supplements.length(); i++) {
                JSONObject supplementJson = supplements.getJSONObject(i);

                //Gemini 응답 결과 순회하며 Image Url 조회
                String imgSearchUrl = googleImageService.getImgSearchUrl(supplementJson.getString("name"));
                String imgJsonData = restTemplate.getForObject(imgSearchUrl, String.class);
                String imgUrl = googleImageService.getImgUrl(imgJsonData);

                supplementJson.put("imgUrl", imgUrl != null ? imgUrl : "");

                Supplement supplement = Supplement.builder()
                        .name(supplementJson.getString("name"))
                        .effect(jsonArrayToList(supplementJson.getJSONArray("effect")))
                        .time(supplementJson.getString("time"))
                        .caution(jsonArrayToList(supplementJson.getJSONArray("caution")))
                        .imgUrl(supplementJson.getString("imgUrl"))
                        .build();

                //10개 정보 반환하기 위해 supplementList에 추가
                supplementList.add(supplement);
            }
        }

        return RecommendResponse.builder()
                .type(type)
                .supplements(supplementList)
                .build();
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
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
