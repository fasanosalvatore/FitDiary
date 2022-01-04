package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Ruolo dell'utente.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruolo {
    /**
     * lunghezza massima nome ruolo.
     */
    public static final int MAX_ROLE_NAME_LENGTH = 20;
    /**
     * Id del ruolo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * nome del ruolo.
     */
    @NotNull
    @Column(length = MAX_ROLE_NAME_LENGTH)
    @NotBlank
    private String nome;
    /**
     * data creazione del ruolo.
     */
    @NotNull
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    /**
     * data aggiornamento del ruolo.
     */
    @NotNull
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;

}
