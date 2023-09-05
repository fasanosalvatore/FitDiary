package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Protocollo {
    /**
     * id protocollo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * data scadenza protocollo.
     */
    @NotNull(message = "La data di scadenza non può essere nullo")
    @Column(name = "data_scadenza")
    @Future(message =
            "La data di scadenza deve essere successiva alla data odierna")
    private LocalDate dataScadenza;
    /**
     * scheda alimentare.
     */
    @ManyToOne
    @JoinColumn(name = "scheda_alimentare_id")
    @EqualsAndHashCode.Exclude
    private SchedaAlimentare schedaAlimentare;
    /**
     * scheda allenamento.
     */
    @OneToOne(mappedBy = "protocollo")
    @EqualsAndHashCode.Exclude
    private SchedaAllenamento schedaAllenamento;
    /**
     * cliente.
     */
    @NotNull(message = "Il cliente non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @EqualsAndHashCode.Exclude
    private Utente cliente;
    /**
     * preparatore.
     */
    @NotNull(message = "Il preparatore non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "preparatore_id")
    @EqualsAndHashCode.Exclude
    private Utente preparatore;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocollo that = (Protocollo) o;
        return Objects.equal(id, that.id) && Objects.equal(dataScadenza, that.dataScadenza) && Objects.equal(schedaAlimentare, that.schedaAlimentare) && Objects.equal(schedaAllenamento, that.schedaAllenamento) && Objects.equal(cliente, that.cliente) && Objects.equal(preparatore, that.preparatore) && Objects.equal(dataCreazione, that.dataCreazione) && Objects.equal(dataAggiornamento, that.dataAggiornamento);
    }
}
