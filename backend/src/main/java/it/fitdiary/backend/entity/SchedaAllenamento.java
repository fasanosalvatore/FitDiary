package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
     * lista esercizi scheda allenamento.
     */
    @OneToMany(mappedBy = "schedaAllenamento")
    private List<Esercizio> listaEsercizi;
    /**
     * procotollo a cui è associata la scheda allenamento.
     */
    @OneToOne
    @JoinColumn(name = "protocollo_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Protocollo protocollo;
}
