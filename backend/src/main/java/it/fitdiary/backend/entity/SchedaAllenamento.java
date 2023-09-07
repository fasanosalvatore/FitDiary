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
import javax.persistence.criteria.CriteriaBuilder;
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
     * Lunghezza massima campo nome.
     */
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_NAME_LENGTH = 1;

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

    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    /**
     * frequenza della scheda allenamento.
     */
    @NotNull(message = "La frequenza non può essere nulla")
    private Integer frequenza;
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
