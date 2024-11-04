package com.integration.bde.Repositories;

import com.integration.bde.Entities.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
    List<VoteResult> findByVoteId(Long idVote);
}