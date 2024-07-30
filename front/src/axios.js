// src/axios.js

import axios from 'axios';

// 기본 설정 생성
const instance = axios.create({
    baseURL: 'http://localhost:8080', // 기본 URL 설정
    timeout: 10000, // 요청 타임아웃 시간 설정
    headers: { 'Content-Type': 'application/json' } // 기본 헤더 설정
});

// 요청 인터셉터 설정
instance.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        // 요청 오류 처리
        return Promise.reject(error);
    }
);

// 응답 인터셉터 설정
instance.interceptors.response.use(
    response => {
        // 응답 데이터 처리
        return response;
    },
    error => {
        // 응답 오류 처리
        return Promise.reject(error);
    }
);

export default instance;