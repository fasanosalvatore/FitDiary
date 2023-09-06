package it.fitdiary.backend.gestioneschedaallenamento.controller.dto;


import java.util.List;

public class CreaSchedaAllenamentoDTO {

    private String name;
    private List<IstanzaEsercizioDTO> istanzeEsercizi;
    private Integer frequenza;

    public CreaSchedaAllenamentoDTO(String name, List<IstanzaEsercizioDTO> istanzeEsercizi,Integer frequenza) {
        this.name = name;
        this.istanzeEsercizi = istanzeEsercizi;
        this.frequenza = frequenza;
    }

    public CreaSchedaAllenamentoDTO() {
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
}
