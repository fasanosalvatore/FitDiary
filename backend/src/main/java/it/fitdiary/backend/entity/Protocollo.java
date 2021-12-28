package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Protocollo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Data di creazione non può essere nullo")
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    @NotNull(message = "La data di aggiornamento non può essere nullo")
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;
    @NotNull(message = "La data di scadenza non può essere nullo")
    @Column(name = "data_scadenza")
    @Future(message = "La data di scadenza deve essere successiva alla data odierna")
    private LocalDateTime dataScadenza;
    @OneToOne(mappedBy = "protocollo")
    private SchedaAlimentare schedaAlimentare;
    @OneToOne(mappedBy = "protocollo")
    private SchedaAllenamento schedaAllenamento;
    @NotNull(message = "Il cliente non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Utente cliente;
    @NotNull(message = "Il preparatore non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "preparatore_id")
    private Utente preparatore;

}
