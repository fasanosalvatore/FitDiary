package it.fitdiary.backend.entity;

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
     * ruolo preparatore.
     */
    public static final String RUOLOPREPARATORE = "Preparatore";
    /**
     * ruolo preparatore.
     */
    public static final String RUOLOCLIENTE = "Cliente";
    /**
     * ruolo admin.
     */
    public static final String RUOLOADMIN = "Admin";
    /**
     * lunghezza massima nome ruolo.
     */
    public static final int MAX_ROLE_NAME_LENGTH = 20;
    /**
     * Id del ruolo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * nome del ruolo.
     */
    @NotNull
    @Column(length = MAX_ROLE_NAME_LENGTH)
    @NotBlank
    private String nome;

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
