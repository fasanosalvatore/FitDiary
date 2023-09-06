package it.fitdiary.backend.gestioneschedaallenamento.controller.dto;


import java.util.List;

public class ModificaSchedaAllenamentoDTO {
    private String name;
    private List<IstanzaEsercizioDTO> istanzeEsercizi;
    private Integer frequenza;
    private Long schedaId;

    public ModificaSchedaAllenamentoDTO(String name, List<IstanzaEsercizioDTO> istanzeEsercizi, Integer frequenza, Long schedaId) {
        this.name = name;
        this.istanzeEsercizi = istanzeEsercizi;
        this.frequenza = frequenza;
        this.schedaId = schedaId;
    }

    public ModificaSchedaAllenamentoDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IstanzaEsercizioDTO> getIstanzeEsercizi() {
        return istanzeEsercizi;
    }

    public void setIstanzeEsercizi(List<IstanzaEsercizioDTO> istanzeEsercizi) {
        this.istanzeEsercizi = istanzeEsercizi;
    }

    public Integer getFrequenza() {
        return frequenza;
    }

    public void setFrequenza(Integer frequenza) {
        this.frequenza = frequenza;
    }

    public Long getSchedaId() {
        return schedaId;
    }

    public void setSchedaId(Long schedaId) {
        this.schedaId = schedaId;
    }
}
