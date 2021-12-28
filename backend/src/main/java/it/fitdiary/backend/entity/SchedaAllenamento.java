package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedaAllenamento {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Il frequenza non può essere nullo")
    @Column(length = 50)
    @NotBlank(message = "Il frequenza non può essere vuoto")
    @Size(min=1,max=50, message="Lunghezza frequenza non valida")
    private String frequenza;
    @OneToMany(mappedBy = "schedaAllenamento")
    private List<Esercizio> listaEsercizi;
    @NotNull
    @OneToOne
    @JoinColumn(name = "protocollo_id")
    private Protocollo protocollo;
}
