package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IstanzaEsercizio {
    private static final int MIN_SERIE = 1;
    private static final int MIN_RIPETIZIONE = 1;
    private static final int MIN_RECUPERO = 1;
    /**
     * id istanza esercizio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * giorno della settimana.
     */
    @NotNull(message = "Il giorno della settimana non può essere nullo")
    @Column(name = "giorno_della_settimana")
    @Enumerated(EnumType.ORDINAL)
    private GIORNO_SETTIMANA giornoDellaSettimana;
    /**
     * numero di serie.
     */
    @NotNull(message = "La serie non può essere nulla")
    @Min(value = MIN_SERIE,
            message = "Il numero di serie non può essere minori di zero")
    private int serie;
    /**
     * numero di ripetizioni.
     */
    @NotNull(message = "Il numero di ripetizioni non può essere nullo")
    @Min(value = MIN_RIPETIZIONE,
            message = "Il numero di ripetizioni non può essere minori di zero")
    private int ripetizione;
    /**
     * recupero.
     */
    @NotNull(message = "Il recupero non può essere nullo")
    @Min(value = MIN_RECUPERO,
            message = "Il numero di recupero non può essere minori di zero")
    private int recupero;
    /**
     * descrizione dell'esecuzione dell'esercizio.
     */
    @NotNull(message = "La descrizione non può essere nulla")
    @NotBlank(message = "La descrizione non può essere vuoto")
    private String descrizione;
    /**
     * id esercizio.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "esercizio_id")
    private Esercizio esercizio;

    @ManyToOne
    @JoinColumn(name = "scheda_allenamento_id")
    private SchedaAllenamento schedaAllenamento;

}
