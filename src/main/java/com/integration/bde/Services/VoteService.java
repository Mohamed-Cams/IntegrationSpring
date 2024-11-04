package com.integration.bde.Services;


import DTO.VoteResultsDTO;
import com.integration.bde.Entities.Depute;
import com.integration.bde.Entities.Vote;
import com.integration.bde.Entities.VoteResult;
import com.integration.bde.Repositories.DeputeRepository;
import com.integration.bde.Repositories.VoteRepository;
import com.integration.bde.Repositories.VoteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteResultRepository voteResultRepository;
    @Autowired
    private DeputeRepository deputeRepository;

    public Vote createVote(Vote vote) {
        vote.setEtat("clos");
        return voteRepository.save(vote);
    }

    public String changeVoteStatus(Long idVote) {
        Vote vote = voteRepository.findById(idVote).orElseThrow(() -> new RuntimeException("Vote not found"));
        vote.setEtat(vote.getEtat().equals("clos") ? "ouvert" : "clos");
        voteRepository.save(vote);
        return "État du vote modifié avec succès.";
    }

    public List<Vote> getOpenVotes() {
        return voteRepository.findByEtat("ouvert");
    }

    public String castVote(Long idVote, Long deputeId, String bulletin) {
        Optional<Depute> depute = deputeRepository.findById(deputeId);
        if (depute.isEmpty()) {
            return "Désolé, vous n’êtes pas autorisé à voter !";
        }

        Vote vote = voteRepository.findById(idVote).orElse(null);
        if (vote == null) {
            return "Identifiant de vote introuvable!";
        }

        if (vote.getEtat().equals("clos")) {
            return "Désolé, le vote est déjà clos !";
        }

        // Vérifier si le député a déjà voté
        List<VoteResult> existingVotes = voteResultRepository.findByVoteId(idVote);
        for (VoteResult result : existingVotes) {
            if (result.getDeputeId().equals(deputeId)) {
                return "Vous avez déjà voté !";
            }
        }

        VoteResult voteResult = new VoteResult();
        voteResult.setVoteId(idVote);
        voteResult.setDeputeId(deputeId);
        voteResult.setBulletin(bulletin);
        voteResultRepository.save(voteResult);
        return "Votre vote est bien pris en compte !";
    }

    public VoteResultsDTO getVoteResults(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote introuvable"));

        List<VoteResult> results = voteResultRepository.findByVoteId(id);

        int ouiCount = 0;
        int nonCount = 0;
        int abstentionCount = 0;

        for (VoteResult result : results) {
            switch (result.getBulletin()) {
                case "OUI":
                    ouiCount++;
                    break;
                case "NON":
                    nonCount++;
                    break;
                case "ABSTENTION":
                    abstentionCount++;
                    break;
            }
        }

        int votants = ouiCount + nonCount + abstentionCount;

        return new VoteResultsDTO(vote.getId(), vote.getDate(), vote.getEtat(), votants, ouiCount, nonCount, abstentionCount);
    }
}
