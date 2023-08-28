package it.fitdiary.backend.getsioneschedaalimentare.controller.dto;

import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.entity.enums.PASTO;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class IstanzaAlimentoDTO {
  /**
   * Giorno della settimana.
   */
  @NotNull(message = "Il giorno della settimana non può essere nullo")
  @Enumerated(EnumType.ORDINAL)
  private GIORNO_SETTIMANA giornoDellaSettimana;

  /**
   * Giorno della settimana.
   */
  @NotNull(message = "Il pasto non può essere nullo")
  @Enumerated(EnumType.ORDINAL)
  private PASTO pasto;

  @NotNull(message = "I grammi non possono essere nulli")
  @Min(1)
  private Integer grammi;

  @NotNull(message = "L'id dell'alimento non può essere nullo")
  private Long idAlimento;

  public IstanzaAlimentoDTO(GIORNO_SETTIMANA giornoDellaSettimana,
                            PASTO pasto, Integer grammi, Long idAlimento) {
    this.giornoDellaSettimana = giornoDellaSettimana;
    this.pasto = pasto;
    this.grammi = grammi;
    this.idAlimento = idAlimento;
  }

  public IstanzaAlimentoDTO() {
  }

  public GIORNO_SETTIMANA getGiornoDellaSettimana() {
    return giornoDellaSettimana;
  }

  public void setGiornoDellaSettimana(
      GIORNO_SETTIMANA giornoDellaSettimana) {
    this.giornoDellaSettimana = giornoDellaSettimana;
  }

  public PASTO getPasto() {
    return pasto;
  }

  public void setPasto(PASTO pasto) {
    this.pasto = pasto;
  }

  public Integer getGrammi() {
    return grammi;
  }

  public void setGrammi(Integer grammi) {
    this.grammi = grammi;
  }

  public Long getIdAlimento() {
    return idAlimento;
  }

  public void setIdAlimento(Long idAlimento) {
    this.idAlimento = idAlimento;
  }
}
