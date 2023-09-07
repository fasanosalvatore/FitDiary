package it.fitdiary.backend.gestioneschedaallenamento.controller.dto;

import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class IstanzaEsercizioDTO {
    /**
     * Giorno della settimana.
     */
    @NotNull(message = "Il giorno della settimana non può essere nullo")
    @Enumerated(EnumType.ORDINAL)
    private GIORNO_SETTIMANA giornoDellaSettimana;

    @NotNull(message = "La serie non può essere nulla")
    @Min(1)
    private Integer serie;
    @NotNull(message = "La ripetizione non può essere nulla")
    @Min(1)
    private Integer ripetizioni;
    @NotNull(message = "Il recupero non può essere nulla")
    @Min(1)
    private Integer recupero;
    @NotNull(message = "La descrizione non può essere nulla")
    @Min(1)
    private String descrizione;

    @NotNull(message = "L'id dell'esercizio non può essere nullo")
    private Long idEsercizio;

    public IstanzaEsercizioDTO(GIORNO_SETTIMANA giornoDellaSettimana, Integer serie, Integer ripetizioni,
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

    public GIORNO_SETTIMANA getGiornoDellaSettimana() {
        return giornoDellaSettimana;
    }

    public void setGiornoDellaSettimana(GIORNO_SETTIMANA giornoDellaSettimana) {
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
