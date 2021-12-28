package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Il peso non può essere vuoto")
    @Min(value=1 , message = "Il peso non può essere uno")
    private Float peso;

    @NotNull(message = "Il pesoStimato non può essere vuoto")
    @Column(name = "peso_stimato")
    @Min(value=1 , message = "Il pesoStimato non può essere uno")
    private Float pesoStimato;

    @NotNull(message = "Il crfBicipite non può essere vuoto")
    @Column(name = "crf_bicipite")
    @Min(value=1 , message = "Il crfBicipite non può essere uno")
    private Float crfBicipite;

    @NotNull(message = "Il crfAddome non può essere vuoto")
    @Column(name = "crf_addome")
    @Min(value=1 , message = "Il crfAddome non può essere uno")
    private Float crfAddome;

    @NotNull(message = "Il crf_Quadricipide non può essere vuoto")
    @Column(name = "crf_quadricipite")
    @Min(value=1 , message = "Il crfQuadricipite non può essere uno")
    private Float crfQuadricipite;

    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;

    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Utente cliente;
}
