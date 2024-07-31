package com.team29.ArtifactV2.domain.member.controller;

import com.team29.ArtifactV2.domain.member.dto.SignUpDto;
import com.team29.ArtifactV2.domain.member.service.signUp.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final SignUpService signUpService;

    @PostMapping("/join")
    public String joinProcess(SignUpDto joinDTO) {

        System.out.println(joinDTO.getUsername());
        signUpService.signUp(joinDTO);

        return "ok";
    }
}
