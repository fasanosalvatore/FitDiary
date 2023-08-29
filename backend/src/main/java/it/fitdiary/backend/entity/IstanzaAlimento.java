package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.entity.enums.PASTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class IstanzaAlimento {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  /**
   * Giorno della settimana.
   */
  @NotNull(message = "Il giorno della settimana non può essere nullo")
  @Column(name = "giorno_della_settimana")
  @Enumerated(EnumType.ORDINAL)
  private GIORNO_SETTIMANA giornoDellaSettimana;

  /**
   * Giorno della settimana.
   */
  @NotNull(message = "Il pasto non può essere nullo")
  @Column(name = "pasto")
  @Enumerated(EnumType.ORDINAL)
  private PASTO pasto;

  @NotNull(message = "I grammi non possono essere nulli")
  @Min(1)
  @Column(name = "grammi")
  private Integer grammi;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "alimento_id")
  private Alimento alimento;


  @ManyToOne
  @JoinColumn(name = "scheda_alimentare_id")
  private SchedaAlimentare schedaAlimentare;




}

