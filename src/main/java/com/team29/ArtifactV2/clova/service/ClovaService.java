package com.team29.ArtifactV2.clova.service;

import com.team29.ArtifactV2.clova.entity.dto.CompletionRequest;
import com.team29.ArtifactV2.clova.entity.dto.RequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ClovaService {

    @Value("${clova.host}")
    private String host;

    @Value("${clova.api.key}")
    private String apiKey;

    @Value("${clova.api.primary.key}")
    private String apiKeyPrimaryVal;

    @Value("${clova.request.id}")
    private String requestId;

    private String content =
            "    - 여행 일정 짜주는 system"+
            "    - 지역, 시간, 키워드를 입력하면 여행 일정을 생성합니다." +
            "    - 장소를 추천해 줄때 시간과 목적지를 고려해서 추천해 줍니다." +
            "    - 키워드를 반영해서 목적지를 추천해 줍니다"+
            "    - 목적지는 구체적인 이름으로 추천해 줍니다. "+
            "    - 일정이 적다면 최소 3개 이상을 추천합니다. "+
            "    - 일정은 길다면 5개 이하를 추천합니다. ";

    private String content2 = "대답 하지않고 Json 문자만 반환해";


    private final RestTemplate restTemplate;

    public ClovaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String execute(RequestDto requestDto) {
        CompletionRequest.RequestData requestData = CompletionRequest.RequestData.builder()
                .topP(0.6)
                .topK(0)
                .maxTokens(1000)
                .temperature(0.3)
                .repeatPenalty(1.2)
                .includeAiFilters(true)
                .seed(0) // JSON 데이터에는 seed가 없으므로 기본값을 설정해야 합니다.
                .build();

        // CompletionRequest 객체 생성 및 설정
        CompletionRequest completionRequest = CompletionRequest.builder()
                .messages(Arrays.asList(
                        CompletionRequest.Message.builder()
                                .role("assistant")
                                .content(content)
                                .build(),



                        CompletionRequest.Message.builder()
                                .role(requestDto.getRole())
                                .content(requestDto.getContent())
                                .build()
                ))
                .requestData(requestData)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Accept", "application/json");

        HttpEntity<CompletionRequest> requestEntity = new HttpEntity<>(completionRequest, headers);

        // RequestEntity 출력
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    host + "/testapp/v1/chat-completions/HCX-003",
                    requestEntity,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }


    public String execute2(String qusResponse){
        CompletionRequest.RequestData requestData = CompletionRequest.RequestData.builder()
                .topP(0.6)
                .topK(0)
                .maxTokens(1000)
                .temperature(0.3)
                .repeatPenalty(1.2)
                .includeAiFilters(true)
                .seed(0) // JSON 데이터에는 seed가 없으므로 기본값을 설정해야 합니다.
                .build();

        // CompletionRequest 객체 생성 및 설정
        CompletionRequest completionRequest = CompletionRequest.builder()
                .messages(Arrays.asList(
                        CompletionRequest.Message.builder()
                                .role("assistant")
                                .content(content2)
                                .build(),

                        CompletionRequest.Message.builder()
                                .role("user")
                                .content(qusResponse+" 추천해준 목적지와 주소를 Json 형태로 반환해줘")
                                .build()
                ))
                .requestData(requestData)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Accept", "application/json");

        HttpEntity<CompletionRequest> requestEntity = new HttpEntity<>(completionRequest, headers);

        // RequestEntity 출력
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    host + "/testapp/v1/chat-completions/HCX-003",
                    requestEntity,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

}
