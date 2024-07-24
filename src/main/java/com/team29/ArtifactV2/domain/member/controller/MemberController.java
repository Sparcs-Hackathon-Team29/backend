package com.team29.ArtifactV2.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @GetMapping("check")
    public String check() {
        return "Success";
    }
}
