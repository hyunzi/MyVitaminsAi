package com.mva.api.myvitamin.service.impl;

import com.mva.api.gemini.service.impl.GeminiServiceImpl;
import com.mva.api.myvitamin.dto.*;
import com.mva.api.myvitamin.service.ConsultService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final GeminiServiceImpl geminiService;
    private final CombineJSONServiceImpl combineService;

    @Override
    public ConsultResponse consulting(ConsultRequest consultRequest) {
        //Gemini 질문지 생성 및 질문
        String question = getQuestion(consultRequest);
        String answer = geminiService.getCompletion(question);

        //Gemini 응답 결과 json 으로 파싱
        JSONObject jsonObject = new JSONObject(answer);
        String type = consultRequest.getType();
        JSONArray supplements = jsonObject.getJSONArray(JSONConstants.SUPPLIEMENTS);
        List<Supplement> supplementList = this.combineService.combineData(supplements);
        String reason = jsonObject.get(JSONConstants.REASON).toString();
        String totalOpinion = jsonObject.get(JSONConstants.TOTAL_OPINION).toString();

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
        String question = "";

        //질문할 데이터 가공
        if (consultType == ConsultEnum.NEW_CONSULT) {
            // 신규
            question = "아래 증상과 특이사항을 참고해서 영양제를 추천해줘.\n" +
                    "\n" +
                    "증상 : " + consultRequest.getSymptom() +
                    "\n" +
                    "특이사항 : " + consultRequest.getComment() +
                    "\n" +
                    "영양제들 간의 조합이 좋아야 하니까 고려해서 추천해줘.\n" +
                    "그리고 영양제 이름( name ), 영양제 효능( effect -> String 배열로 보여줘 ), 복용하면 좋은 시간 대( time ), 주의사항( caution -> String 배열로 보여줘 ) 를 list (supplements) 로 정리해주고, 추가로, 영양제 조합과 제외 시킨 영양제가 있다면 그 이유( reason ) , 복용 시 주의사항 및 추가조언 ( totalOpinion )을 알려줘\n" +
                    "\n" +
                    "모든 대답은 JSON 구조로 응답해주는데 key값으로는 괄호 안의 단어로 주고, value값은 한글로 줘.\n" +
                    "꼭 괄호 안의 단어에 해당하는 정보만 명확하게 제공해줘.\n" +
                    "\n" +
                    "예시는 아래와 같아.\n" +
                    "{\n" +
                    " \"supplements\": [\n" +
                    "  {\n" +
                    "   \"name\": \"종합비타민\",\n" +
                    "   \"effect\": [\"다양한 비타민과 미네랄을 공급하여 건강 유지에 도움\"],\n" +
                    "   \"time\": \"아침 식사 후\",\n" +
                    "   \"caution\": [\"냉장 보관 필수\", \"장 기능 저하 시 복용 전 전문가 상담\"]\n" +
                    "  },\n" +
                    "...\n" +
                    "{\n" +
                    "   \"name\": \"비타민 D\",\n" +
                    "   \"effect\": [\"칼슘 흡수 촉진\", \"면역력 강화\"],\n" +
                    "   \"time\": \"식사와 상관없이 언제든지\",\n" +
                    "   \"caution\": [\"과다 복용 시 고칼슘혈증을 유발할 수 있음\"]\n" +
                    "  }\n" +
                    " ]\n" +
                    "\"reason\": \"종합비타민은 이미 다양한 비타민과 미네랄을 포함하고 있어 별도로 추가하지 않았습니다.\",\n" +
                    "\"totalOpinion\": \"추천된 영양제 조합은 전반적인 건강 증진과 함께 특히 피로감, 무릎 통증, 수면 문제 개선에 도움이 될 수 있습니다. \"\n" +
                    "}\n";
        }
        else if (consultType == ConsultEnum.EXISTING_CONSULT) {
            // 기존
            question = "아래 기존 복용하고 있는 영양제들과 증상, 특이사항을 참고해서 영양제를 추천해줘.\n" +
                    "\n" +
                    "복용하고 있는 영양제들 :" + consultRequest.getSupplement() +
                    "\n" +
                    "증상 : " + consultRequest.getSymptom() +
                    "\n" +
                    "특이사항 : " + consultRequest.getComment() +
                    "\n" +
                    "기존 복용 중인 영양제들의 조합과 새로 보완해서 복용하면 좋을 영양제들을 고려해서 모두 합쳐 추천해줘.\n" +
                    "그리고 영양제 이름( name ), 영양제 효능( effect -> String 배열로 보여줘 ), 복용하면 좋은 시간 대( time ), 주의사항( caution -> String 배열로 보여줘 ) 를 list (supplements) 로 정리해주고, 추가로, 영양제 조합과 제외 시킨 영양제가 있다면 그 이유( reason ) , 복용 시 주의사항 ( totalOpinion )을 알려줘\n" +
                    "\n" +
                    "모든 대답은 JSON 데이터 구조로 응답해주는데 key값으로는 괄호 안의 단어로 주고, value값은 한글로 줘.\n" +
                    "꼭 괄호 안의 단어에 해당하는 정보만 명확하게 제공해줘.\n" +
                    "\n" +
                    "예시는 아래와 같아.\n" +
                    "{\n" +
                    "  \"supplements\": [\n" +
                    "    {\n" +
                    "      \"name\": \"종합비타민\",\n" +
                    "      \"effect\": [\"다양한 비타민과 미네랄을 공급하여 건강 유지에 도움\"],\n" +
                    "      \"time\": \"아침 식사 후\",\n" +
                    "      \"caution\": [\"냉장 보관 필수\", \"장 기능 저하 시 복용 전 전문가 상담\"]\n" +
                    "    },\n" +
                    "...\n" +
                    "{\n" +
                    "      \"name\": \"비타민 D\",\n" +
                    "      \"effect\": [\"칼슘 흡수 촉진\", \"면역력 강화\"],\n" +
                    "      \"time\": \"식사와 상관없이 언제든지\",\n" +
                    "      \"caution\": [\"과다 복용 시 고칼슘혈증을 유발할 수 있음\"]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "\"reason\": \"종합비타민은 이미 다양한 비타민과 미네랄을 포함하고 있어 별도로 추가하지 않았습니다.\",\n" +
                    "\"totalOpinion\": \"추천된 영양제 조합은 전반적인 건강 증진과 함께 특히 피로감, 무릎 통증, 수면 문제 개선에 도움이 될 수 있습니다. \"\n" +
                    "}";
        }
        return question;
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
