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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Esercizio {
    /**
     * int 50.
     */
    private static final int INT50 = 50;
    /**
     * int 10.
     */
    private static final int INT10 = 10;
    /**
     * int 20.
     */
    private static final int INT20 = 20;
    /**
     * id report.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * nome esercizio.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = INT50)
    @Size(min = 1, max = INT50, message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    /**
     * foto path.
     */
    @NotNull(message = "Il path della foto non può essere nullo")
    @NotBlank(message = "Il path della foto non può essere vuoto")
    @Pattern(regexp = "^(.+)\\/([^\\/]+)$")
    private String pathFoto;

    /**
     * id tipo esercizio.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipoesercizio_id")
    private TipoEsercizio tipoEsercizio;
}
