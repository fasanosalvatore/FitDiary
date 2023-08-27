package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Pattern;
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
    private static final int MIN_NAME_LENGTH = 1;

    private static final long MIN_KCAL_VALUE = 0;
    private static final long MIN_PROTEINE_VALUE = 0;
    private static final long MIN_GRASSI_VALUE = 0;
    private static final long MIN_CARBOIDRATI_VALUE = 0;

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
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    /**
     * kcal alimento.
     */
    @NotNull(message = "Le kcal non possono essere nulle")
    @Min(value = MIN_KCAL_VALUE,
            message = "Le kcal non possono essere minori di zero")
    private Float kcal;

    /**
     * proteine alimento.
     */
    @NotNull(message = "Le proteine non possono essere nulle")
    @Min(value = MIN_PROTEINE_VALUE,
        message = "Le proteine non possono essere minori di zero")
    private Float proteine;

    /**
     * grassi alimento.
     */
    @NotNull(message = "I grassi non possono essere nulli")
    @Min(value = MIN_GRASSI_VALUE,
        message = "I grassi non possono essere minori di zero")
    private Float grassi;

    /**
     * carboidrati alimento.
     */
    @NotNull(message = "I carboidrati non possono essere nulli")
    @Min(value = MIN_CARBOIDRATI_VALUE,
        message = "I carboidrati non possono essere minori di zero")
    private Float carboidrati;

    /**
     * foto path.
     */
    @NotNull(message = "Il path della foto non può essere nullo")
    @NotBlank(message = "Il path della foto non può essere vuoto")
    @Pattern(regexp = "^(.+)\\/([^\\/]+)$")
    private String pathFoto;



}
