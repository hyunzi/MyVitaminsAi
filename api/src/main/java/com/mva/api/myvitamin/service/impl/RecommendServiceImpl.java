package com.mva.api.myvitamin.service.impl;

import com.mva.api.myvitamin.dto.RecommendRequest;
import com.mva.api.myvitamin.dto.RecommendResponse;
import com.mva.api.myvitamin.service.RecommendService;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Override
    public RecommendResponse getRecommendations(RecommendRequest recommendRequest) {

        //validation check


        //세션키로 USER_VITAMIN_TABLE(사용자 영양제 내역 테이블) 조회


        //정보가 있는 경우 RecommendResponse 세팅하여 반환


        //정보가 없는 경우 TOP_VITAMIN_TABLE(인기 영양제 테이블) 조회


        //TOP N개 정보 RecommendResponse 세팅하여 반환


        return null;
    }
}
