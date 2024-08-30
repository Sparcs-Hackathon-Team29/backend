package com.team29.ArtifactV2.clova.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team29.ArtifactV2.clova.entity.dto.RequestDto;
import com.team29.ArtifactV2.clova.entity.dto.RecommendResponseDto;
import com.team29.ArtifactV2.domain.member.dto.CustomUserDetails;
import com.team29.ArtifactV2.domain.member.entity.Member;
import com.team29.ArtifactV2.domain.member.repository.MemberRepository;
import com.team29.ArtifactV2.domain.recommend.entity.Recommend;
import com.team29.ArtifactV2.domain.recommend.repository.RecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletionService {

    private final ClovaService clovaService;
    private final ObjectMapper objectMapper;
    private final RecoRepository recoRepository;
    private final MemberRepository memberRepository;
    private Long ctn = 0L;

    public List<RecommendResponseDto> processRequest(RequestDto request) throws JsonProcessingException {
        List<RecommendResponseDto> responseList = new ArrayList<>();

        // 전체 응답에서 'content' JSON 문자열 추출
        String content = clovaService.execute2(clovaService.execute(request));

        // 'content' JSON 문자열에서 'content' 내부의 JSON 문자열 추출
        JsonNode resultArray = extractResultArrayFromResponse(content);

        // 현재 로그인한 사용자의 이름을 가져와서 Member를 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername(); // 사용자 이름을 가져옵니다

            // 사용자 이름으로 Member를 조회합니다
            Member member = memberRepository.findByUsername(username);

            // 'result' 배열의 각 요소를 'Recommend' 엔티티로 변환하여 저장
            if (resultArray.isArray()) {
                for (JsonNode node : resultArray) {
                    Recommend recommend = new Recommend();
                    recommend.setName(node.path("name").asText());
                    recommend.setAddress(node.path("address").asText());
                    recommend.setUrl("https://map.naver.com/p/search/" + recommend.getName());
                    recommend.setCreateId(ctn);
                    recommend.setUsername(member.getUsername());
                    recoRepository.save(recommend);

                    responseList.add(new RecommendResponseDto(recommend));
                }
            }
        }

        return responseList;
    }

    private JsonNode extractResultArrayFromResponse(String response) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(response);
        String contentJson = rootNode.path("result").path("message").path("content").asText();
        JsonNode contentNode = objectMapper.readTree("[" + contentJson + "]"); // 배열로 감싸기
        ctn++;
        return contentNode;
    }
}
