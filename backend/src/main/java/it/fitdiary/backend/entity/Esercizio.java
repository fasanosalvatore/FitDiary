package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Esercizio {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = 50)
    @Size(min=1,max=50, message="Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    @NotNull(message = "La serie non può essere nulla")
    @Column(length = 20)
    @Size(min=1,max=20, message="Lunghezza serie non valida")
    @NotBlank(message = "La serie non può essere vuota")
    private String serie;
    @NotNull(message = "La ripetizione non può essere nulla")
    @Column(length = 10)
    @Size(min=1,max=10, message="Lunghezza ripetizione non è valida")
    @NotBlank(message = "La ripetizione non può essere vuota")
    private String ripetizioni;
    @NotNull(message = "Il recupero non può essere nulla")
    @Column(length = 10)
    @Size(min=1,max=10, message="Lunghezza recupero non valida")
    @NotBlank(message = "Il recupero non può essere vuoto")
    private String recupero;
    @NotNull(message = "Il numero allenamento non può essere nulla")
    @Column(length = 10, name="numero_allenamento")
    @Size(min=1,max=10, message="Lunghezza numero allenamento non valida")
    @NotBlank(message = "Il numero allenamento non può essere vuoto")
    private String numeroAllenamento;
    @NotNull(message = "La categoria non può essere nulla")
    @Column(length = 20)
    @Size(min=1,max=20, message="Lunghezza categoria non valida")
    @NotBlank(message = "La categoria non può essere vuota")
    private String categoria;
    @NotNull(message = "Scheda allenamento non può essere vuoto")
    @ManyToOne
    @JoinColumn (name = "scheda_allenamento_id")
    private SchedaAllenamento schedaAllenamento;
}
