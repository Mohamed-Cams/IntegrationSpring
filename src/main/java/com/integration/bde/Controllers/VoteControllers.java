package com.integration.bde.Controllers;

import DTO.VoteResultsDTO;
import com.integration.bde.Entities.Vote;
import com.integration.bde.Services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VoteControllers {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        return ResponseEntity.ok(voteService.createVote(vote));
    }

    @PutMapping("/{idVote}/status")
    public ResponseEntity<String> changeVoteStatus(@PathVariable Long idVote) {
        return ResponseEntity.ok(voteService.changeVoteStatus(idVote));
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getOpenVotes() {
        return ResponseEntity.ok(voteService.getOpenVotes());
    }

    @PostMapping("/{idVote}/cast")
    public ResponseEntity<String> castVote(@PathVariable Long idVote, @RequestParam Long deputeId, @RequestParam String bulletin) {
        return ResponseEntity.ok(voteService.castVote(idVote, deputeId, bulletin));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<VoteResultsDTO> getVoteResults(@PathVariable Long id) {
        return ResponseEntity.ok(voteService.getVoteResults(id));
    }
}

