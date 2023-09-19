package it.fitdiary.backend.gestioneschedaallenamento.controller.dto;

import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class IstanzaEsercizioDTO {
    /**
     * Giorno della settimana.
     */
    @NotNull(message = "Il giorno della settimana non può essere nullo")
    private int giornoDellaSettimana;

    @NotNull(message = "La serie non può essere nulla")
    @Min(1)
    private Integer serie;
    @NotNull(message = "La ripetizione non può essere nulla")
    @Min(1)
    private Integer ripetizioni;
    @NotNull(message = "Il recupero non può essere nulla")
    @Min(1)
    private Integer recupero;
    private String descrizione="";

    @NotNull(message = "L'id dell'esercizio non può essere nullo")
    private Long idEsercizio;

    public IstanzaEsercizioDTO(int giornoDellaSettimana, Integer serie, Integer ripetizioni,
                               Integer recupero, String descrizione, Long idEsercizio) {
        this.giornoDellaSettimana = giornoDellaSettimana;
        this.serie = serie;
        this.ripetizioni = ripetizioni;
        this.recupero = recupero;
        this.descrizione = descrizione;
        this.idEsercizio = idEsercizio;
    }

    public IstanzaEsercizioDTO() {
    }

    public int getGiornoDellaSettimana() {
        return giornoDellaSettimana;
    }

    public void setGiornoDellaSettimana(int giornoDellaSettimana) {
        this.giornoDellaSettimana = giornoDellaSettimana;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getRipetizioni() {
        return ripetizioni;
    }

    public void setRipetizioni(Integer ripetizioni) {
        this.ripetizioni = ripetizioni;
    }

    public Integer getRecupero() {
        return recupero;
    }

    public void setRecupero(Integer recupero) {
        this.recupero = recupero;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdEsercizio() {
        return idEsercizio;
    }

    public void setIdEsercizio(Long idEsercizio) {
        this.idEsercizio = idEsercizio;
    }
}
