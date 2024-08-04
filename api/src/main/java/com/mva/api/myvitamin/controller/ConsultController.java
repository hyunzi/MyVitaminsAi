package com.mva.api.myvitamin.controller;

import com.mva.api.myvitamin.dto.ConsultRequest;
import com.mva.api.myvitamin.dto.ConsultResponse;
import com.mva.api.myvitamin.service.ConsultService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/consult")
public class ConsultController {

    private final ConsultService consultService;

    /*
    * 컨설팅 요청 API
    * */
    @PostMapping
    public ResponseEntity<ConsultResponse> consulting(@RequestBody ConsultRequest consultRequest, HttpSession session) throws Exception {
        return ResponseEntity.ok(consultService.consulting(consultRequest, session.getId()));
        //return ResponseEntity.ok(consultService.consultingTest(consultRequest));
    }
}
