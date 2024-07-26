package com.team29.ArtifactV2.domain.member.service.signUp;


import com.team29.ArtifactV2.domain.member.dto.SignUpDto;
import com.team29.ArtifactV2.domain.member.entity.Member;
import com.team29.ArtifactV2.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(SignUpDto dto){
        String username = dto.getUsername();
        String password = dto.getPassword();

        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(bCryptPasswordEncoder.encode(password));
        member.setRole("ROLE_ADMIN");
        memberRepository.save(member);
    }
}
