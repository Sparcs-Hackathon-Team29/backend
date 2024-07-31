package com.team29.ArtifactV2.domain.recommend.controller;

import com.team29.ArtifactV2.domain.recommend.entity.Recommend;
import com.team29.ArtifactV2.domain.recommend.repository.RecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RecoController {
    private final RecoRepository recoRepository;

    @GetMapping("/api/recommend/{username}")
    public List<Recommend> recom(@PathVariable String username){
        return recoRepository.findByUsername(username);
    }


    @GetMapping("/api/random")
    public Optional<Recommend> randEntity() {
        return recoRepository.findRandomRecommend();
    }

    @PostMapping("/api/recommend/delete/{id}")
    public HttpStatus delete(@PathVariable Long id){
        recoRepository.deleteById(id);
        return HttpStatus.OK;
    }

}
