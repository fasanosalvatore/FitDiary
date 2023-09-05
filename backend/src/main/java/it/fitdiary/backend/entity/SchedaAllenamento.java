package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchedaAllenamento {
    /**
     * Lunghezza massima della frequenza.
     */
    public static final int MAX_FREQUENZA_LENGTH = 50;
    /**
     * id della scheda allenamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * frequenza della scheda allenamento.
     */
    @NotNull(message = "Il frequenza non può essere nullo")
    @Column(length = MAX_FREQUENZA_LENGTH)
    @NotBlank(message = "Il frequenza non può essere vuoto")
    @Size(min = 1, max = MAX_FREQUENZA_LENGTH,
            message = "Lunghezza frequenza non valida")
    private String frequenza;
    /**
     * lista esercizi scheda esercizi.
     */
    @OneToMany(mappedBy = "schedaAllenamento",cascade = CascadeType.ALL)
    private List<IstanzaEsercizio> listaEsercizi;

    @ManyToOne
    @JoinColumn(name = "utente_id")
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
        SchedaAllenamento that = (SchedaAllenamento) o;
        return Objects.equal(id, that.id) && Objects.equal(frequenza, that.frequenza) && Objects.equal(listaEsercizi, that.listaEsercizi);
    }

}
