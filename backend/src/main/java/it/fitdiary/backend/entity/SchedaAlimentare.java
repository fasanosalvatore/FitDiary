package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SchedaAlimentare {

    /**
     * Lunghezza massima campo nome.
     */
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_NAME_LENGTH = 1;


    /**
     * id della scheda alimentare.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome Scheda alimentare.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
        message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    /**
     * kcalAssunte della scheda alimentare.
     */
    @NotNull(message = "Le kcal assunte non possono essere nulle")
    @Column(name = "kcal_assunte")
    @Min(value = 0, message = "Le kcal assunte non possono essere minori di 0")
    private Float kcalAssunte;
    /**
     * listaIstanzaAlimenti della scheda alimentare.
     */
    @OneToMany(mappedBy = "schedaAlimentare",cascade = CascadeType.ALL)

    private List<IstanzaAlimento> listaAlimenti;


    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente preparatore;


    /**
     * La data creazione della tupla.
     */
    @Column(name = "data_creazione", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCreazione;

    /**
     * La data aggiornamento della tupla.
     */
    @Column(name = "data_aggiornamento")
    @UpdateTimestamp
    private LocalDateTime dataAggiornamento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedaAlimentare that = (SchedaAlimentare) o;
        return Objects.equal(id, that.id) && Objects.equal(nome, that.nome) && Objects.equal(kcalAssunte, that.kcalAssunte) && Objects.equal(listaAlimenti, that.listaAlimenti) && Objects.equal(preparatore, that.preparatore) && Objects.equal(dataCreazione, that.dataCreazione) && Objects.equal(dataAggiornamento, that.dataAggiornamento);
    }

    @Override
    public String toString() {
        return "SchedaAlimentare{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", kcalAssunte=" + kcalAssunte +
                ", listaAlimenti=" + listaAlimenti +
                ", preparatore=" + preparatore +
                ", dataCreazione=" + dataCreazione +
                ", dataAggiornamento=" + dataAggiornamento +
                '}';
    }
}
