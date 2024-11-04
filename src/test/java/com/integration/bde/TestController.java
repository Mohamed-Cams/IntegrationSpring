package com.integration.bde;

import DTO.VoteResultsDTO;
import com.integration.bde.Controllers.VoteControllers;
import com.integration.bde.Entities.Vote;
import com.integration.bde.Services.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestController {

    @InjectMocks
    private VoteControllers voteController;

    @Mock
    private VoteService voteService;

    private Vote vote;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vote = new Vote();
        vote.setId(1L);
        vote.setLibelle("Test Vote");
        vote.setEtat("ouvert");
    }

    @Test
    void testCreateVote() {
        when(voteService.createVote(any(Vote.class))).thenReturn(vote);

        ResponseEntity<Vote> response = voteController.createVote(vote);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vote.getLibelle(), response.getBody().getLibelle());
    }

    @Test
    void testChangeVoteStatus() {
        when(voteService.changeVoteStatus(1L)).thenReturn("État du vote modifié avec succès.");

        ResponseEntity<String> response = voteController.changeVoteStatus(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("État du vote modifié avec succès.", response.getBody());
    }

    @Test
    void testGetOpenVotes() {
        when(voteService.getOpenVotes()).thenReturn(List.of(vote));

        ResponseEntity<List<Vote>> response = voteController.getOpenVotes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCastVote() {
        when(voteService.castVote(1L, 1L, "OUI")).thenReturn("Votre vote est bien pris en compte !");

        ResponseEntity<String> response = voteController.castVote(1L, 1L, "OUI");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Votre vote est bien pris en compte !", response.getBody());
    }

    @Test
    void testGetVoteResults() {
        VoteResultsDTO resultsDTO = new VoteResultsDTO(1L, vote.getDate(), vote.getEtat(), 100, 54, 40, 6);
        when(voteService.getVoteResults(1L)).thenReturn(resultsDTO);

        ResponseEntity<VoteResultsDTO> response = voteController.getVoteResults(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultsDTO, response.getBody());
    }
}
