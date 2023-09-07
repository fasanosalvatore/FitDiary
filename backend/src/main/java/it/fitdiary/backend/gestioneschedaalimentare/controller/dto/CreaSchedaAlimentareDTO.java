package it.fitdiary.backend.gestioneschedaalimentare.controller.dto;

import java.util.List;

public class CreaSchedaAlimentareDTO {
  private String name;
  private List<IstanzaAlimentoDTO> istanzeAlimenti;

  public CreaSchedaAlimentareDTO(String name,
                                 List<IstanzaAlimentoDTO> istanzeAlimenti) {
    this.name = name;
    this.istanzeAlimenti = istanzeAlimenti;
  }

  public CreaSchedaAlimentareDTO() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<IstanzaAlimentoDTO> getIstanzeAlimenti() {
    return istanzeAlimenti;
  }

  public void setIstanzeAlimenti(
      List<IstanzaAlimentoDTO> istanzeAlimenti) {
    this.istanzeAlimenti = istanzeAlimenti;
  }
}
