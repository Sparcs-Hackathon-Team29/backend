package com.team29.ArtifactV2.domain.recommend.repository;

import com.team29.ArtifactV2.domain.recommend.entity.Recommend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecoRepository extends JpaRepository<Recommend, Long> {
    @Query(value = "SELECT * FROM recommend ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Recommend> findRandomRecommend();

    List<Recommend> findByUsername(String username);

    @Transactional
    void deleteById(Long recommendId);
}
