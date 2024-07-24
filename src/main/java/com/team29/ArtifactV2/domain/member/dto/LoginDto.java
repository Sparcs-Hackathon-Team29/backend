package com.team29.ArtifactV2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank   // *spring-boot-starter-validation, 필수값
    private String email;
    @NotBlank
    private  String password;

}
