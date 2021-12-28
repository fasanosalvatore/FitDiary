package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruolo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(length = 20)
    @NotBlank
    private String nome;
    @NotNull
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    @NotNull
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;

}
