package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimento {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = 50)
    @Size(min=1,max=50, message="Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    @NotNull(message = "Il pasto non può essere nullo")
    @Column(length = 20)
    @Size(min=1,max=20, message="Lunghezza pasto non valida")
    @NotBlank(message = "Il pasto non può essere vuoto")
    private String pasto;
    @NotNull(message = "Il giorno non può essere nullo")
    @Column(length = 10)
    @Size(min=1,max=10, message="Lunghezza giorno non valida")
    @NotBlank(message = "Il giorno non può essere vuoto")
    private String giorno;
    @NotNull(message = "Le kcal non possono essere nulle")
    @Min(value=0, message = "Le kcal non possono essere minori di zero")
    private Integer kcal;
    @NotNull(message = "I grammi non possono essere nulli")
    @Min(value=1 , message = "I grammi non possono essere minori di uno")
    private Float grammi;
    @NotNull(message = "La scheda alimentare non può essere nulla")
    @ManyToOne
    @JoinColumn (name = "scheda_alimentare_id")
    private SchedaAlimentare schedaAlimentare;
}
