package it.fitdiary.backend.entity;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimento {
    /**
     * int 50.
     */
    private static final int INT50 = 50;
    /**
     * int 20.
     */
    private static final int INT20 = 20;
    /**
     * int 10.
     */
    private static final int INT10 = 10;
    /**
     * id alimento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * nome alimento.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = INT50)
    @Size(min = 1, max = INT50, message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    /**
     * pasto.
     */
    @NotNull(message = "Il pasto non può essere nullo")
    @Column(length = INT20)
    @Size(min = 1, max = INT20, message = "Lunghezza pasto non valida")
    @NotBlank(message = "Il pasto non può essere vuoto")
    private String pasto;
    /**
     * giorno.
     */
    @NotNull(message = "Il giorno non può essere nullo")
    @Column(length = INT10)
    @Size(min = 1, max = INT10, message = "Lunghezza giorno non valida")
    @NotBlank(message = "Il giorno non può essere vuoto")
    private String giorno;
    /**
     * kcal alimento.
     */
    @NotNull(message = "Le kcal non possono essere nulle")
    @Min(value = 0, message = "Le kcal non possono essere minori di zero")
    private Integer kcal;
    /**
     * grammi alimento.
     */
    @NotNull(message = "I grammi non possono essere nulli")
    @Min(value = 1, message = "I grammi non possono essere minori di uno")
    private Float grammi;
    /**
     * scheda alimantere.
     */
    @NotNull(message = "La scheda alimentare non può essere nulla")
    @ManyToOne
    @JoinColumn(name = "scheda_alimentare_id")
    private SchedaAlimentare schedaAlimentare;
}
