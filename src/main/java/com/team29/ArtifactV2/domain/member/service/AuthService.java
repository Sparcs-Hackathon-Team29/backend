package com.team29.ArtifactV2.domain.member.service;


import com.team29.ArtifactV2.domain.member.dto.ResponseDto;
import com.team29.ArtifactV2.domain.member.dto.SignUpDto;
import com.team29.ArtifactV2.domain.member.entity.Member;
import com.team29.ArtifactV2.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public ResponseDto<?> signUp(SignUpDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getPassword();

        // email(id) 중복 확인
        try {
            Optional<Member> existingMember = memberRepository.findByEmail(email);
            if (existingMember.isPresent()) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.1");
        }

        // password 중복 확인
        if(!password.equals(confirmPassword)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        // UserEntity 생성
        Member member = new Member(dto);

        // UserRepository를 이용하여 DB에 Entity 저장 (데이터 적재)
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.2");
        }

        return ResponseDto.setSuccess("회원 생성에 성공했습니다.");
    }


}
