package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    /**
     * id report.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * peso report.
     */
    @NotNull(message = "Il peso non può essere vuoto")
    @Min(value = 1, message = "Il peso non può essere uno")
    private Float peso;
    /**
     * peso stimato report.
     */
    @NotNull(message = "Il pesoStimato non può essere vuoto")
    @Column(name = "peso_stimato")
    @Min(value = 1, message = "Il pesoStimato non può essere uno")
    private Float pesoStimato;
    /**
     * crf bicipite report.
     */
    @NotNull(message = "Il crfBicipite non può essere vuoto")
    @Column(name = "crf_bicipite")
    @Min(value = 1, message = "Il crfBicipite non può essere uno")
    private Float crfBicipite;
    /**
     * crf addome report.
     */
    @NotNull(message = "Il crfAddome non può essere vuoto")
    @Column(name = "crf_addome")
    @Min(value = 1, message = "Il crfAddome non può essere uno")
    private Float crfAddome;
    /**
     * crf quadricipite report.
     */
    @NotNull(message = "Il crf_Quadricipide non può essere vuoto")
    @Column(name = "crf_quadricipite")
    @Min(value = 1, message = "Il crfQuadricipite non può essere uno")
    private Float crfQuadricipite;
    /**
     * cliente.
     */
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cliente_id")
    private Utente cliente;

    /**
     * La data creazione della tupla.
     */
    @Column(name = "data_creazione", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCreazione;

    /**
     * La data aggiornamento della tupla.
     */
    @Column(name = "data_aggiornamento")
    @UpdateTimestamp
    private LocalDateTime dataAggiornamento;
}
