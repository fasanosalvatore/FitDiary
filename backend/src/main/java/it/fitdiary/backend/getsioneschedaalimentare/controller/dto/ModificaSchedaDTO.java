package it.fitdiary.backend.getsioneschedaalimentare.controller.dto;

import java.util.List;

public class ModificaSchedaDTO {
  private String name;
  private List<IstanzaAlimentoDTO> istanzeAlimenti;
  private Long schedaId;

  public ModificaSchedaDTO() {
  }

  public ModificaSchedaDTO(String name,
                           List<IstanzaAlimentoDTO> istanzeAlimenti, Long schedaId) {
    this.name = name;
    this.istanzeAlimenti = istanzeAlimenti;
    this.schedaId = schedaId;
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

  public Long getSchedaId() {
    return schedaId;
  }

  public void setSchedaId(Long schedaId) {
    this.schedaId = schedaId;
  }
}
