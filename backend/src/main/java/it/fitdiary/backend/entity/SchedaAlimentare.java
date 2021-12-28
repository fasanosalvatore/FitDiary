package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedaAlimentare {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message="Le kcal assunte non possono essere nulle")
    @Column(name = "kcal_assunte")
    @Min(value=0,message="Le kcal assunte non possono essere minori di 0")
    private Integer kcalAssunte;
    @OneToMany(mappedBy = "schedaAlimentare")
    private List<Alimento> listaAlimenti;
    @NotNull(message="Il protocollo non puo' essere nullo")
    @OneToOne
    @JoinColumn(name = "protocollo_id")
    private Protocollo protocollo;
}
