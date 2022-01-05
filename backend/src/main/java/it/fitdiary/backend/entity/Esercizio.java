package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
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
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * serie dell'esercizio.
     */
    @NotNull(message = "La serie non può essere nulla")
    @Column(length = INT20)
    @Size(min = 1, max = INT20, message = "Lunghezza serie non valida")
    @NotBlank(message = "La serie non può essere vuota")
    private String serie;
    /**
     * ripetizione degli esercizio.
     */
    @NotNull(message = "La ripetizione non può essere nulla")
    @Column(length = INT10)
    @Size(min = 1, max = INT10, message = "Lunghezza ripetizione non è valida")
    @NotBlank(message = "La ripetizione non può essere vuota")
    private String ripetizioni;
    /**
     * recupero esercizio.
     */
    @NotNull(message = "Il recupero non può essere nulla")
    @Column(length = INT10)
    @Size(min = 1, max = INT10, message = "Lunghezza recupero non valida")
    @NotBlank(message = "Il recupero non può essere vuoto")
    private String recupero;
    /**
     * numero allenamento.
     */
    @NotNull(message = "Il numero allenamento non può essere nulla")
    @Column(length = INT10, name = "numero_allenamento")
    @Size(min = 1, max = INT10, message =
            "Lunghezza numero allenamento non valida")
    @NotBlank(message = "Il numero allenamento non può essere vuoto")
    private String numeroAllenamento;
    /**
     * categoria esercizio.
     */
    @NotNull(message = "La categoria non può essere nulla")
    @Column(length = INT20)
    @Size(min = 1, max = INT20, message = "Lunghezza categoria non valida")
    @NotBlank(message = "La categoria non può essere vuota")
    private String categoria;
    /**
     * scheda allenamento.
     */
    @NotNull(message = "Scheda allenamento non può essere vuoto")
    @ManyToOne
    @JoinColumn(name = "scheda_allenamento_id")
    @JsonIgnore
    private SchedaAllenamento schedaAllenamento;
}
