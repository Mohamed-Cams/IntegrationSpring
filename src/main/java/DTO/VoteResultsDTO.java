package DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoteResultsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String etat;
    private int votants;
    private int oui;
    private int non;
    private int abstention;

    public VoteResultsDTO(Long id, LocalDate date, String etat, int votants, int oui, int non, int abstention) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.votants = votants;
        this.oui = oui;
        this.non = non;
        this.abstention = abstention;
    }
}
