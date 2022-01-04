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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedaAlimentare {
    /**
     * id della scheda alimentare.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * kcalAssunte della scheda alimentare.
     */
    @NotNull(message = "Le kcal assunte non possono essere nulle")
    @Column(name = "kcal_assunte")
    @Min(value = 0, message = "Le kcal assunte non possono essere minori di 0")
    private Integer kcalAssunte;
    /**
     * listaAlimenti della scheda alimentare.
     */
    @OneToMany(mappedBy = "schedaAlimentare")
    private List<Alimento> listaAlimenti;
    /**
     * protocollo al quale è associata la scheda alimentare.
     */
    @NotNull(message = "Il protocollo non puo' essere nullo")
    @OneToOne
    @JoinColumn(name = "protocollo_id")
    private Protocollo protocollo;
}
