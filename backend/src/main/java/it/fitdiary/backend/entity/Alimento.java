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
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Alimento {
    /**
     * Lunghezza massima campo nome.
     */
    private static final int MAX_NAME_LENGTH = 50;
    /**
     * Lunghezza massima campo pasto.
     */
    private static final int MAX_PASTO_LENGTH = 20;
    /**
     * Lunghezza massima campo giorno.
     */
    private static final int MAX_DAY_LENGTH = 10;
    /**
     * Valore minimo KCal.
     */
    public static final int MIN_KCAL_VALUE = 0;
    /**
     * Valore Minimo Grammi.
     */
    public static final int MIN_G_VALUE = 1;
    /**
     * ID alimento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nome alimento.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_G_VALUE, max = MAX_NAME_LENGTH,
            message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    /**
     * pasto.
     */
    @NotNull(message = "Il pasto non può essere nullo")
    @Column(length = MAX_PASTO_LENGTH)
    @Size(min = MIN_G_VALUE, max = MAX_PASTO_LENGTH,
            message = "Lunghezza pasto non valida")
    @NotBlank(message = "Il pasto non può essere vuoto")
    private String pasto;
    /**
     * giorno.
     */
    @NotNull(message = "Il giorno non può essere nullo")
    @Column(length = MAX_DAY_LENGTH)
    @Size(min = MIN_G_VALUE, max = MAX_DAY_LENGTH,
            message = "Lunghezza giorno non valida")
    @NotBlank(message = "Il giorno non può essere vuoto")
    private String giorno;
    /**
     * kcal alimento.
     */
    @NotNull(message = "Le kcal non possono essere nulle")
    @Min(value = MIN_KCAL_VALUE,
            message = "Le kcal non possono essere minori di zero")
    private Float kcal;
    /**
     * grammi alimento.
     */
    @NotNull(message = "Il campo grammi non può essere nullo")
    @Column(length = MAX_PASTO_LENGTH)
    @Size(min = MIN_G_VALUE, max = MAX_PASTO_LENGTH,
            message = "Lunghezza del campo grammi non valida")
    @NotBlank(message = "Il campo grammi non può essere vuoto")
    private String grammi;
    /**
     * scheda alimantere.
     */
    @NotNull(message = "La scheda alimentare non può essere nulla")
    @ManyToOne
    @JoinColumn(name = "scheda_alimentare_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private SchedaAlimentare schedaAlimentare;
}
